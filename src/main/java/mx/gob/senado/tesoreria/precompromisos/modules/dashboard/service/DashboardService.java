package mx.gob.senado.tesoreria.precompromisos.modules.dashboard.service;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.ActividadRecienteDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.service.SeguimientoService;
import mx.gob.senado.tesoreria.precompromisos.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    private final SeguimientoService seguimientoService;

    public DashboardService(SeguimientoService seguimientoService) {
        this.seguimientoService = seguimientoService;
    }

    public List<ActividadRecienteDTO> consultarActividadReciente(Integer ejercicio, Integer limiteMax) {
        return seguimientoService.obtenerUltimosMovimientos(ejercicio, limiteMax);
    }
}
