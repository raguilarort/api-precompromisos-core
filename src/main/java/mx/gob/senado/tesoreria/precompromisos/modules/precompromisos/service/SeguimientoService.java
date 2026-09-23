package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.service;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.SeguimientoOperativoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.repository.SeguimientoRepository;
import mx.gob.senado.tesoreria.precompromisos.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguimientoService {

    private final SeguimientoRepository repository;

    public SeguimientoService(SeguimientoRepository repository) {
        this.repository = repository;
    }

    public List<SeguimientoOperativoDTO> obtenerHistorial(Integer idPrecompromiso) {
        Integer idUsuario = SecurityUtils.obtenerIdUsuarioLogueado();

        // Aquí podrías agregar validaciones de seguridad (SecurityUtils) si un usuario
        // intenta ver la bitácora de una unidad a la que no tiene acceso.
        return repository.consultarPorPrecompromiso(idPrecompromiso, idUsuario);
    }
}
