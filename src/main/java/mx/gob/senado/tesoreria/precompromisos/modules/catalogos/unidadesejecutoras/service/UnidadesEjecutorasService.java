package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.service;

import org.springframework.stereotype.Service;

import java.util.List;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.dto.UnidadEjecutoraDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.repository.UnidadesEjecutorasRepository;

@Service
public class UnidadesEjecutorasService {
    private final UnidadesEjecutorasRepository repository;

    public UnidadesEjecutorasService(UnidadesEjecutorasRepository repository) {
        this.repository = repository;
    }

    public List<UnidadEjecutoraDTO> obtenerCatalogoUnidadesEjecutoras() {
        return repository.getCatalogoUnidadesEjecutoras();
    }
}
