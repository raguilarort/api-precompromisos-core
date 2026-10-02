package mx.gob.senado.tesoreria.precompromisos.modules.reportes.presupuestales.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.gob.senado.tesoreria.precompromisos.modules.reportes.presupuestales.situacionpresupuestal.dto.SituacionPresupuestalAnualPorClaveDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.reportes.presupuestales.situacionpresupuestal.service.ReporteSituacionPresupuestalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reportes/presupuesto")
@Tag(name = "Reportes Presupuestales", description = "Consultas para BI")
public class ReportesPresupuestalesController {
    private final ReporteSituacionPresupuestalService situacionPresupuestalService;

    public ReportesPresupuestalesController(ReporteSituacionPresupuestalService situacionPresupuestalService) {
        this.situacionPresupuestalService = situacionPresupuestalService;
    }

    @GetMapping(value = "/situacion-presupuestal", params = "ejercicio")
    @Operation(summary = "Obtener el concentrado de situación presupuestal mensualizada de todas las claves presupuestales de un ejercicio")
    public ResponseEntity<List<SituacionPresupuestalAnualPorClaveDTO>> obtenerSituacionPresupuestalAnual(
            @RequestParam(name = "ejercicio") Integer ejercicio) {

        return ResponseEntity.ok(situacionPresupuestalService.generarReporte(ejercicio));
    }
}
