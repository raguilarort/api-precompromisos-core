package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.service;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.*;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.enums.EstatusPrecompromisoEnum;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.repository.PrecompromisosRepository;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.exception.PrecompromisoException;
import mx.gob.senado.tesoreria.precompromisos.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PrecompromisosService {

    private final PrecompromisosRepository repository;

    public PrecompromisosService(PrecompromisosRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public String registrar(PrecompromisoRequestDTO payload) {

        validarCombinacionesDuplicadas(payload.conceptos());

        // 1. Guardar cabecera y atrapar el resultado de Oracle
        ResultadoRegistroPrecompromiso resultado = repository.registrarCabecera(
                payload.ejercicio(),
                String.valueOf(payload.unidad()),
                payload.numeroRequisicion(),
                payload.tipoContratacion(),
                payload.tipoRequerimiento()
        );

        // 2. Validar que la base de datos haya generado correctamente el ID
        if (resultado.idPrecompromiso() == null) {
            throw PrecompromisoException.errorGeneracionIdCabecera();
        }

        // 3. Iterar sobre payload.conceptos() e insertar el detalle
        for (ConceptoRequestDTO concepto : payload.conceptos()) {
            if (concepto.obtenerTotal() <= 0) {
                throw new IllegalArgumentException("El concepto '" + concepto.descripcion() + "' debe tener un importe mayor a cero en al menos un mes.");
            }

            Integer idConcepto = repository.registrarConcepto(resultado.idPrecompromiso(), concepto);

            if (idConcepto == null) {
                throw PrecompromisoException.errorGeneracionIdConcepto();
            }
        }

        Integer idUsuario = SecurityUtils.obtenerIdUsuarioLogueado();
        repository.registrarSeguimiento(
                resultado.idPrecompromiso(),
                EstatusPrecompromisoEnum.CAPTURADO.getId(), // ID Estatus Capturado
                "REGISTRO_INICIAL",
                idUsuario,
                "Captura inicial del precompromiso con " + payload.conceptos().size() + " concepto(s)."
        );

        return resultado.folio();
    }

    @Transactional
    public void actualizar(Integer idPrecompromiso, PrecompromisoRequestDTO payload) {
        validarCombinacionesDuplicadas(payload.conceptos());

        Integer idUsuario = SecurityUtils.obtenerIdUsuarioLogueado();

        PrecompromisoDetailDTO actual = repository.consultarPorId(idPrecompromiso, idUsuario);

        if (actual == null) {
            throw PrecompromisoException.registroNoEncontrado(idPrecompromiso);
        }

        if (actual.idEstatus() != 1) {
            throw new IllegalStateException("El precompromiso no puede modificarse porque ya no se encuentra en estatus Capturado.");
        }

        boolean cambiosCabecera = false;
        int contNuevos = 0;
        int contEliminados = 0;
        int contCambiosClave = 0;
        int contCambiosImporte = 0;

        if (!actual.numeroRequisicion().equals(payload.numeroRequisicion()) ||
                !actual.idTipoContratacion().equals(payload.tipoContratacion()) ||
                !actual.idTipoRequerimiento().equals(payload.tipoRequerimiento())) {

            repository.actualizarCabecera(idPrecompromiso, payload);

            cambiosCabecera = true;
        }

        Map<Integer, ConceptoDetailDTO> mapaActuales = actual.conceptos().stream()
                .collect(Collectors.toMap(ConceptoDetailDTO::idConcepto, c -> c));

        for (ConceptoRequestDTO conceptoEntrante : payload.conceptos()) {
            if (conceptoEntrante.obtenerTotal() <= 0) {
                throw new IllegalArgumentException("El concepto '" + conceptoEntrante.descripcion() + "' debe tener un importe mayor a cero en al menos un mes.");
            }

            if (conceptoEntrante.idConcepto() == null) {
                Integer idConcepto = repository.registrarConcepto(idPrecompromiso, conceptoEntrante);

                if (idConcepto == null) {
                    throw new RuntimeException("La base de datos no devolvió un identificador válido al intentar registrar el nuevo concepto.");
                }

                contNuevos++;
            } else {
                ConceptoDetailDTO conceptoPrevio = mapaActuales.remove(conceptoEntrante.idConcepto());

                if (conceptoPrevio != null) {
                    boolean cambioClave = !conceptoPrevio.idClavePresupuestaria().equals(conceptoEntrante.idCvePresupuestaria());
                    boolean cambioImporte = isCambioImporte(conceptoEntrante, conceptoPrevio);

                    if (cambioClave) contCambiosClave++;
                    if (cambioImporte) contCambiosImporte++;

                    repository.actualizarConcepto(conceptoEntrante);
                }
            }
        }

        for (Integer idEliminar : mapaActuales.keySet()) {
            repository.eliminarConcepto(idEliminar);
            contEliminados++;
        }

        // 6. Construir texto para la bitácora
        StringBuilder msj = new StringBuilder();
        if (cambiosCabecera) msj.append("datos de requisición; ");
        if (contNuevos > 0) msj.append(contNuevos).append(" concepto(s) incorporado(s); ");
        if (contEliminados > 0) msj.append(contEliminados).append(" concepto(s) eliminado(s); ");
        if (contCambiosClave > 0) msj.append("cambio de clave en ").append(contCambiosClave).append(" concepto(s); ");
        if (contCambiosImporte > 0) msj.append("importes actualizados en ").append(contCambiosImporte).append(" concepto(s); ");

        String observacion;
        if (msj.isEmpty()) {
            observacion = "Edición guardada sin variaciones detectables.";
        } else {
            observacion = "Modificación de " + msj.substring(0, msj.length() - 2) + ".";
        }

        repository.registrarSeguimiento(idPrecompromiso, actual.idEstatus(), "MODIFICACION", idUsuario, observacion);
    }

    @Transactional
    public void eliminar(Integer idPrecompromiso) {
        Integer idUsuario = SecurityUtils.obtenerIdUsuarioLogueado();

        PrecompromisoDetailDTO actual = repository.consultarPorId(idPrecompromiso, idUsuario);

        if (actual == null) {
            throw PrecompromisoException.registroNoEncontrado(idPrecompromiso);
        }

        if (actual.idEstatus() != 1) {
            throw new IllegalStateException("El precompromiso no puede eliminarse porque no se encuentra en estatus Capturado.");
        }

        repository.eliminarPrecompromiso(idPrecompromiso, idUsuario);

        repository.registrarSeguimiento(
                idPrecompromiso,
                EstatusPrecompromisoEnum.ELIMINADO.getId(),
                "CAMBIO_ESTATUS",
                idUsuario,
                "El precompromiso y sus conceptos fueeron eliminado del sistema"
        );
    }

    public PrecompromisoDetailDTO consultarPorId(Integer idPrecompromiso) {
        Integer idUsuario = SecurityUtils.obtenerIdUsuarioLogueado();

        PrecompromisoDetailDTO detalle = repository.consultarPorId(idPrecompromiso, idUsuario);

        if (detalle == null) {
            throw PrecompromisoException.registroNoEncontrado(idPrecompromiso);
        }

        return detalle;
    }

    public List<PrecompromisoResumeDTO> consultarPorEjercicio(Integer ejercicio) {
        Integer idUsuario = SecurityUtils.obtenerIdUsuarioLogueado();

        return repository.consultarPorEjercicio(ejercicio, idUsuario);
    }

    private static boolean isCambioImporte(ConceptoRequestDTO conceptoEntrante, ConceptoDetailDTO conceptoPrevio) {
        Double totalPrevio = conceptoPrevio.importeEnero() + conceptoPrevio.importeFebrero() +
                conceptoPrevio.importeMarzo() + conceptoPrevio.importeAbril() +
                conceptoPrevio.importeMayo() + conceptoPrevio.importeJunio() +
                conceptoPrevio.importeJulio() + conceptoPrevio.importeAgosto() +
                conceptoPrevio.importeSeptiembre() + conceptoPrevio.importeOctubre() +
                conceptoPrevio.importeNoviembre() + conceptoPrevio.importeDiciembre();

        return Math.abs(totalPrevio - conceptoEntrante.obtenerTotal()) > 0.01;
    }

    private void validarCombinacionesDuplicadas(List<ConceptoRequestDTO> conceptos) {
        Set<Integer> clavesVistas = new HashSet<>();
        for (ConceptoRequestDTO concepto : conceptos) {
            if (!clavesVistas.add(concepto.idCvePresupuestaria())) {
                throw new IllegalArgumentException("El precompromiso contiene combinaciones presupuestales duplicadas. Consolide los importes en un solo concepto.");
            }
        }
    }
}
