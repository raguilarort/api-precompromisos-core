package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.service;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.repository.PrecompromisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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
}
