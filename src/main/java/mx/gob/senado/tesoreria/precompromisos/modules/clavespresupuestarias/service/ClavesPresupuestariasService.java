package mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.service;

import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.ClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.FiltroClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.repository.ClavesPresupuestariasRepository;
import org.springframework.stereotype.Service;

@Service
public class ClavesPresupuestariasService {
    private final ClavesPresupuestariasRepository repository;

    public ClavesPresupuestariasService(ClavesPresupuestariasRepository repository) {
        this.repository = repository;
    }

    public ClavePresupuestariaDTO consultarClavePresupuestaria(Integer idClavePresupuestaria) {
        return repository.consultarClavePresupuestaria(idClavePresupuestaria);
    }

    public ClavePresupuestariaDTO buscarClavePresupuestaria(FiltroClavePresupuestariaDTO filtro) {
        return repository.buscarClavePresupuestaria(
                filtro.ejercicio(),
                filtro.unidad(),
                filtro.idCveProg(),
                filtro.idPartida(),
                filtro.idFuenteFin()
        );
    }
    /*
    public DisponibilidadClavePresupuestariaDTO consultarDisponibilidad(Integer idClavePresupuestaria) {
        return repository.consultarDisponibilidad(idClavePresupuestaria);
    }

    public DisponibilidadClavePresupuestariaDTO consultarDisponibilidad(FiltroClavePresupuestariaDTO filtro) {
        return repository.consultarDisponibilidad(
                filtro.ejercicio(),
                filtro.unidad(),
                filtro.idCveProg(),
                filtro.idPartida(),
                filtro.idFuenteFin()
        );
    }*/
}
