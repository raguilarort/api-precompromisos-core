package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.service;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.dto.PartidaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.repository.PartidasRepository;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.dto.UnidadEjecutoraDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.repository.UnidadesEjecutorasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidasService {
    private final PartidasRepository repository;

    public PartidasService(PartidasRepository repository) {
        this.repository = repository;
    }

    public List<PartidaDTO> obtenerCatalogoPartidas() {
        return repository.getCatalogoPartidas();
    }
}
