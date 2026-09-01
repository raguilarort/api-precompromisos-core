package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.service;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.dto.PartidaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.repository.PartidasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidasService {
    private final PartidasRepository repository;

    public PartidasService(PartidasRepository repository) {
        this.repository = repository;
    }

    public List<PartidaDTO> obtenerCatalogoPartidas() {
        return repository.listarPartidasEspecificas();
    }

    public List<PartidaDTO> consultarCatalogoPartidas(Integer ejercicio, String unidad, Integer idCveProg) {
        return repository.listarPartidasEspecificasPorEjercicioUnidadCveProg(ejercicio, unidad, idCveProg);
    }
}
