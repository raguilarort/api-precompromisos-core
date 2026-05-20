package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.service;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.CambioEstatusRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.ClavePresupuestariaRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.DisponibilidadDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.repository.PrecompromisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrecompromisoService {

    @Autowired
    private PrecompromisoRepository precompromisoRepository;

    // Blindaje de seguridad: Solo estos roles pueden iniciar la captura
    @PreAuthorize("hasAnyRole('CAPTURISTA', 'REVISOR', 'AUTORIZADOR')")
    public Long registrarNuevoPrecompromiso(PrecompromisoRequestDTO request, String numEmpleado) {

        // Aquí podrías agregar validaciones lógicas en Java (Ej. que la lista de partidas no venga vacía)
        if (request.getPartidas() == null || request.getPartidas().isEmpty()) {
            throw new IllegalArgumentException("El precompromiso debe contener al menos una clave presupuestaria.");
        }

        // Llamamos al repositorio transaccional
        return precompromisoRepository.crearPrecompromisoCompleto(request, numEmpleado);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('CAPTURISTA', 'REVISOR', 'AUTORIZADOR')")
    public void procesarCambioEstatus(Long idPrecompromiso, CambioEstatusRequestDTO request, String numEmpleado) {

        Integer estatusActual = precompromisoRepository.obtenerEstatusActual(idPrecompromiso);
        Integer estatusNuevo = request.getIdEstatusNuevo();

        // Obtenemos el rol del contexto de seguridad de Spring (JWT)
        String rolUsuario = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();

        // 1. REGLA: ELIMINADO (6) solo si está en CAPTURADO (1)
        if (estatusNuevo == 6) {
            if (estatusActual != 1) {
                throw new IllegalArgumentException("Solo se pueden eliminar precompromisos en estatus Capturado.");
            }
        }

        // 2. REGLA: CANCELADO (5) solo por AUTORIZADOR (DGPP) y si está AUTORIZADO (3)
        if (estatusNuevo == 5) {
            if (!rolUsuario.equals("ROLE_AUTORIZADOR")) {
                throw new SecurityException("No tiene permisos para cancelar este documento.");
            }
            if (estatusActual != 3) {
                throw new IllegalArgumentException("Solo se pueden cancelar precompromisos que ya han sido Autorizados.");
            }
        }

        // 3. REGLA: De CAPTURADO (1) a REVISADO (2)
        if (estatusNuevo == 2) {
            if (!rolUsuario.equals("ROLE_REVISOR") && !rolUsuario.equals("ROLE_AUTORIZADOR")) {
                throw new SecurityException("Solo un Revisor o Autorizador puede marcar como revisado.");
            }
            if (estatusActual != 1) {
                throw new IllegalArgumentException("El documento debe estar Capturado para poder ser Revisado.");
            }
        }

        // 4. REGLA: De REVISADO (2) a AUTORIZADO (3) -> REQUIERE VALIDACIÓN DE SALDOS
        if (estatusNuevo == 3) {
            if (!rolUsuario.equals("ROLE_AUTORIZADOR")) {
                throw new SecurityException("Solo el rol Autorizador (DGPP) puede aprobar precompromisos.");
            }
            if (estatusActual != 2) {
                throw new IllegalArgumentException("El documento debe ser Revisado antes de Autorizarse.");
            }
            validarDisponibilidadPresupuestal(idPrecompromiso);
        }

        // 5. REGLA: De AUTORIZADO (3) a COMPROMETIDO (4)
        if (estatusNuevo == 4) {
            if (!rolUsuario.equals("ROLE_AUTORIZADOR")) {
                throw new SecurityException("Solo el rol Autorizador (DGPP) puede comprometer.");
            }
            if (estatusActual != 3) {
                throw new IllegalArgumentException("El documento debe estar Autorizado antes de Comprometerse.");
            }
        }

        // Ejecutar el cambio en Oracle
        precompromisoRepository.actualizarEstatus(idPrecompromiso, estatusNuevo, numEmpleado, request.getComentarios());
    }

    private void validarDisponibilidadPresupuestal(Long idPrecompromiso) {
        List<ClavePresupuestariaRequestDTO> partidas = precompromisoRepository.obtenerPartidasDePrecompromiso(idPrecompromiso);

        for (ClavePresupuestariaRequestDTO partida : partidas) {
            DisponibilidadDTO disp = precompromisoRepository.obtenerDisponibilidadPorClave(partida.getClavePresupuestariaId());

            // Verificamos mes a mes que el importe capturado sea menor o igual al disponible
            if (partida.getImporteEne() > disp.getDisponibleEne()) lanzarErrorFaltaFondo("Enero", partida.getClavePresupuestariaId());
            if (partida.getImporteFeb() > disp.getDisponibleFeb()) lanzarErrorFaltaFondo("Febrero", partida.getClavePresupuestariaId());
            // ... Haz el IF para los 12 meses ...
        }
    }

    private void lanzarErrorFaltaFondo(String mes, Long claveId) {
        throw new IllegalArgumentException(
                String.format("Suficiencia presupuestal rechazada: No hay fondos suficientes en el mes de %s para la clave ID %d", mes, claveId)
        );
    }

    // Endpoint auxiliar para la interfaz gráfica (Consulta directa)
    public DisponibilidadDTO consultarDisponibilidad(Long clavePresupuestariaId) {
        return precompromisoRepository.obtenerDisponibilidadPorClave(clavePresupuestariaId);
    }
}
