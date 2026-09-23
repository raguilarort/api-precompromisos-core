package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.SeguimientoOperativoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.service.SeguimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/precompromisos")
@Tag(name = "Precompromisos - Seguimiento", description = "Consulta todo el historial de un precompromiso")
public class PrecompromisosSeguimientoController {

    private final SeguimientoService seguimientoService;

    public PrecompromisosSeguimientoController(SeguimientoService seguimientoService) {
        this.seguimientoService = seguimientoService;
    }

    @GetMapping("/{id}/seguimiento")
    @Operation(summary = "Obtener la bitácora cronológica operativa de un precompromiso")
    public ResponseEntity<List<SeguimientoOperativoDTO>> obtenerSeguimiento(@PathVariable Integer id) {
        return ResponseEntity.ok(seguimientoService.obtenerHistorial(id));
    }
}
