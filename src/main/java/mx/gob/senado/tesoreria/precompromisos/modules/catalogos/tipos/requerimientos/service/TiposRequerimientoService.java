package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.requerimientos.service;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.requerimientos.dto.TipoRequerimientoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.requerimientos.repository.TiposRequerimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiposRequerimientoService {
    private final TiposRequerimientoRepository repository;

    public TiposRequerimientoService(TiposRequerimientoRepository repository) {
        this.repository = repository;
    }

    public List<TipoRequerimientoDTO> obtenerCatalogoTiposRequerimiento() {
        return repository.getCatalogoTiposRequerimiento();
    }
}
