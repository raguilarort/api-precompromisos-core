package mx.gob.senado.tesoreria.precompromisos.modules.avisos.service;

import mx.gob.senado.tesoreria.precompromisos.modules.avisos.dto.AvisoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.avisos.repository.AvisoRepository;
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
        return repository.consultarActivos();
    }
}
