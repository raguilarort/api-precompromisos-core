package mx.gob.senado.tesoreria.precompromisos.modules.avisos.service;

import mx.gob.senado.tesoreria.precompromisos.modules.avisos.dto.AvisoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.avisos.repository.AvisoRepository;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.repository.SeguimientoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvisoService {
    private final AvisoRepository repository;

    public AvisoService(AvisoRepository repository) {
        this.repository = repository;
    }

    public List<AvisoDTO> obtenerActivos() {
        List<AvisoDTO> lista = new ArrayList<>();
        return repository.consultarActivos();
    }
}
