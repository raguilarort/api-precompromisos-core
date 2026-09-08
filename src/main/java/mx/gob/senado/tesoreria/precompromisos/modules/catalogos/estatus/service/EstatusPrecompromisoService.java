package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.estatus.service;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.estatus.dto.EstatusPrecompromisoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.estatus.repository.EstatusPrecompromisoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstatusPrecompromisoService {
    private final EstatusPrecompromisoRepository repository;

    public EstatusPrecompromisoService(EstatusPrecompromisoRepository repository) {
        this.repository = repository;
    }

    public List<EstatusPrecompromisoDTO> obtenerCatalogoEstatusPrecompromisos() {
        return repository.getCatalogoEstatusPrecompromisos();
    }
}
