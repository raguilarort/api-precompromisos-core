package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.service;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.dto.FuenteFinanciamientoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.repository.FuentesFinanciamientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuentesFinanciamientoService {
    private final FuentesFinanciamientoRepository repository;

    public FuentesFinanciamientoService(FuentesFinanciamientoRepository repository) {
        this.repository = repository;
    }

    public List<FuenteFinanciamientoDTO> obtenerCatalogoFuentesFinanciamiento() {
        return repository.listarFuentesFinanciamiento();
    }

    public List<FuenteFinanciamientoDTO> consultarFuentesFinanciamiento(Integer ejercicio, String unidad, Integer idCveProg, Integer idPartida) {
        return repository.listarFuentesFinanciamiento(ejercicio, unidad, idCveProg, idPartida);
    }
}
