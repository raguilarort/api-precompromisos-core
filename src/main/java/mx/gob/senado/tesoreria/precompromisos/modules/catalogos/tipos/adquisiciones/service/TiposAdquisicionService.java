package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.adquisiciones.service;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.adquisiciones.dto.TipoAdquisicionDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.adquisiciones.repository.TiposAdquisicionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiposAdquisicionService {
    private final TiposAdquisicionRepository repository;

    public TiposAdquisicionService(TiposAdquisicionRepository repository) {
        this.repository = repository;
    }

    public List<TipoAdquisicionDTO> obtenerCatalogoTiposAdquisicion() {
        return repository.getCatalogoTiposAdquisicion();
    }
}
