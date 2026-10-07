package mx.gob.senado.tesoreria.precompromisos.modules.dashboard.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import mx.gob.senado.tesoreria.precompromisos.modules.dashboard.service.DashboardService;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.ActividadRecienteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Manejo de datos para el dashboard")
public class DashboardController {
    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/actividad-reciente")
    public ResponseEntity<List<ActividadRecienteDTO>> obtenerActividadReciente(
            @RequestParam Integer ejercicio) {

        List<ActividadRecienteDTO> actividad = service.consultarActividadReciente(ejercicio, 10);
        return ResponseEntity.ok(actividad);
    }
}
