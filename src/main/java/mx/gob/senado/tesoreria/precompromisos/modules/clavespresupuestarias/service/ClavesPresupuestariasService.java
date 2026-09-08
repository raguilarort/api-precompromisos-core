package mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.service;

import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.ClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.DisponibilidadClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.FiltroClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.repository.ClavesPresupuestariasRepository;
import mx.gob.senado.tesoreria.precompromisos.shared.exceptions.ClavePresupuestariaException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClavesPresupuestariasService {
    private final ClavesPresupuestariasRepository repository;

    public ClavesPresupuestariasService(ClavesPresupuestariasRepository repository) {
        this.repository = repository;
    }

    public ClavePresupuestariaDTO consultarClavePresupuestaria(Integer idClavePresupuestaria) {
        ClavePresupuestariaDTO clave = repository.consultarClavePresupuestaria(idClavePresupuestaria);

        if (clave == null) {
            throw ClavePresupuestariaException.idNoEncontrado(idClavePresupuestaria);
        }

        return clave;
    }

    public ClavePresupuestariaDTO buscarClavePresupuestaria(FiltroClavePresupuestariaDTO filtro) {
        List<ClavePresupuestariaDTO> resultados = repository.buscarClavePresupuestaria(
                filtro.ejercicio(),
                filtro.unidad(),
                filtro.idCveProg(),
                filtro.idPartida(),
                filtro.idFuenteFin()
        );

        if (resultados == null || resultados.isEmpty()) {
            throw ClavePresupuestariaException.combinacionNoEncontrada();
        }

        if (resultados.size() > 1) {
            throw ClavePresupuestariaException.multiplesClavesEncontradas();
        }

        return resultados.getFirst();
    }

    public DisponibilidadClavePresupuestariaDTO consultarDisponibilidad(FiltroClavePresupuestariaDTO filtro) {

        ClavePresupuestariaDTO claveExistente = this.buscarClavePresupuestaria(filtro);

        return this.consultarDisponibilidadPorId(filtro.ejercicio(), claveExistente.clavePresupuestariaId());
    }

    public DisponibilidadClavePresupuestariaDTO consultarDisponibilidadPorId(Integer ejercicio, Integer idClavePresupuestaria) {

        List<DisponibilidadClavePresupuestariaDTO> resultadosSaldos = repository.consultarDisponibilidad(ejercicio == 0 ? LocalDate.now().getYear() : ejercicio, idClavePresupuestaria);

        if (resultadosSaldos == null || resultadosSaldos.isEmpty()) {
            throw ClavePresupuestariaException.noHaySaldosPorMostrar(idClavePresupuestaria);
        }

        if (resultadosSaldos.size() > 1) {
            throw ClavePresupuestariaException.multiplesSaldosEncontrados();
        }

        return resultadosSaldos.getFirst();
    }
}
