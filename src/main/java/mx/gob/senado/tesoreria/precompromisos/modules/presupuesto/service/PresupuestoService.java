package mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.service;

import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.ClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.FiltroClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.exception.ClavePresupuestariaException;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.service.ClavesPresupuestariasService;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto.DesglosePresupuestalDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto.PresupuestoDisponibleDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto.PresupuestoPrecomprometidoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoDisponibleException;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoPrecomprometidoException;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.repository.PresupuestoPrecomprometidoRepository;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.repository.PresupuestoSAPFINRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PresupuestoService {
    private final PresupuestoSAPFINRepository presupuestoSAPFINRepository;
    private final PresupuestoPrecomprometidoRepository presupuestoPrecomprometidoRepository;
    private final ClavesPresupuestariasService clavesPresupuestariasService;

    public PresupuestoService(PresupuestoSAPFINRepository presupuestoSAPFINRepository, PresupuestoPrecomprometidoRepository presupuestoPrecomprometidoRepository, ClavesPresupuestariasService clavesPresupuestariasService) {
        this.presupuestoSAPFINRepository = presupuestoSAPFINRepository;
        this.presupuestoPrecomprometidoRepository = presupuestoPrecomprometidoRepository;
        this.clavesPresupuestariasService = clavesPresupuestariasService;
    }

    public List<PresupuestoDisponibleDTO> consultarPresupuestoDisponible(Integer ejercicio) {
        List<PresupuestoDisponibleDTO> resultadosSaldos = presupuestoSAPFINRepository.consultarDisponibilidad(ejercicio == 0 ? LocalDate.now().getYear() : ejercicio);

        if (resultadosSaldos == null || resultadosSaldos.isEmpty()) {
            throw PresupuestoDisponibleException.noHaySaldosPorMostrarEnEjercicio(ejercicio);
        }

        return resultadosSaldos;
    }

    public PresupuestoDisponibleDTO consultarPresupuestoDisponible(FiltroClavePresupuestariaDTO filtro) {

        ClavePresupuestariaDTO claveExistente = clavesPresupuestariasService.buscarClavePresupuestaria(filtro);

        return this.consultarPresupuestoDisponible(filtro.ejercicio(), claveExistente.clavePresupuestariaId());
    }

    public PresupuestoDisponibleDTO consultarPresupuestoDisponible(Integer ejercicio, Integer idClavePresupuestaria) {

        List<PresupuestoDisponibleDTO> resultadosSaldos = presupuestoSAPFINRepository.consultarDisponibilidad(ejercicio == 0 ? LocalDate.now().getYear() : ejercicio, idClavePresupuestaria);

        if (resultadosSaldos == null || resultadosSaldos.isEmpty()) {
            throw PresupuestoDisponibleException.noHaySaldosPorMostrar(idClavePresupuestaria);
        }

        if (resultadosSaldos.size() > 1) {
            throw ClavePresupuestariaException.multiplesSaldosEncontrados();
        }

        return resultadosSaldos.getFirst();
    }

    public List<PresupuestoPrecomprometidoDTO> consultarPresupuestoPrecomprometido(Integer ejercicio) {
        List<PresupuestoPrecomprometidoDTO> resultadosSaldos = presupuestoPrecomprometidoRepository.consultarPrecomprometido(ejercicio == 0 ? LocalDate.now().getYear() : ejercicio);

        if (resultadosSaldos == null || resultadosSaldos.isEmpty()) {
            throw PresupuestoPrecomprometidoException.noHaySaldosPorMostrarEnEjercicio(ejercicio);
        }

        return resultadosSaldos;
    }

    public PresupuestoPrecomprometidoDTO consultarPresupuestoPrecomprometido(FiltroClavePresupuestariaDTO filtro) {

        ClavePresupuestariaDTO claveExistente = clavesPresupuestariasService.buscarClavePresupuestaria(filtro);

        return this.consultarPresupuestoPrecomprometido(filtro.ejercicio(), claveExistente.clavePresupuestariaId());
    }

    public PresupuestoPrecomprometidoDTO consultarPresupuestoPrecomprometido(Integer ejercicio, Integer idClavePresupuestaria) {

        List<PresupuestoPrecomprometidoDTO> resultadosSaldos = presupuestoPrecomprometidoRepository.consultarPrecomprometido(ejercicio == 0 ? LocalDate.now().getYear() : ejercicio, idClavePresupuestaria);

        if (resultadosSaldos == null || resultadosSaldos.isEmpty()) {
            throw PresupuestoPrecomprometidoException.noHaySaldosPorMostrar(idClavePresupuestaria);
        }

        if (resultadosSaldos.size() > 1) {
            throw PresupuestoPrecomprometidoException.multiplesSaldosEncontrados();
        }

        return resultadosSaldos.getFirst();
    }


    public DesglosePresupuestalDTO consultarPresupuestos(Integer ejercicio, Integer idClavePresupuestaria) {

        PresupuestoDisponibleDTO presupuestoDisponibleSAPFIN = this.consultarPresupuestoDisponible(ejercicio, idClavePresupuestaria);
        PresupuestoPrecomprometidoDTO presupuestoPrecomprometido = this.consultarPresupuestoPrecomprometido(ejercicio, idClavePresupuestaria);

        return new DesglosePresupuestalDTO(
                idClavePresupuestaria,
                presupuestoDisponibleSAPFIN.disponibleEnero(), presupuestoPrecomprometido.precompEnero(),
                presupuestoDisponibleSAPFIN.disponibleFebrero(), presupuestoPrecomprometido.precompFebrero(),
                presupuestoDisponibleSAPFIN.disponibleMarzo(), presupuestoPrecomprometido.precompMarzo(),
                presupuestoDisponibleSAPFIN.disponibleAbril(), presupuestoPrecomprometido.precompAbril(),
                presupuestoDisponibleSAPFIN.disponibleMayo(), presupuestoPrecomprometido.precompMayo(),
                presupuestoDisponibleSAPFIN.disponibleJunio(), presupuestoPrecomprometido.precompJunio(),
                presupuestoDisponibleSAPFIN.disponibleJulio(), presupuestoPrecomprometido.precompJulio(),
                presupuestoDisponibleSAPFIN.disponibleAgosto(), presupuestoPrecomprometido.precompAgosto(),
                presupuestoDisponibleSAPFIN.disponibleSeptiembre(), presupuestoPrecomprometido.precompSeptiembre(),
                presupuestoDisponibleSAPFIN.disponibleOctubre(), presupuestoPrecomprometido.precompOctubre(),
                presupuestoDisponibleSAPFIN.disponibleNoviembre(), presupuestoPrecomprometido.precompNoviembre(),
                presupuestoDisponibleSAPFIN.disponibleDiciembre(), presupuestoPrecomprometido.precompDiciembre()
        );
    }

    public DesglosePresupuestalDTO consultarPresupuestos(FiltroClavePresupuestariaDTO filtro) {
        ClavePresupuestariaDTO claveExistente = clavesPresupuestariasService.buscarClavePresupuestaria(filtro);

        return this.consultarPresupuestos(filtro.ejercicio(), claveExistente.clavePresupuestariaId());
    }
}
