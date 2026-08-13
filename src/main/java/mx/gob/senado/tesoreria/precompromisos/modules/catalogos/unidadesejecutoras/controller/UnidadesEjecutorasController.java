package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.dto.UnidadEjecutoraDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.service.UnidadesEjecutorasService;

@RestController
@RequestMapping("/catalogos/unidades-ejecutoras")
public class UnidadesEjecutorasController {
    private final UnidadesEjecutorasService unidadesEjecutorasService;

    public UnidadesEjecutorasController(UnidadesEjecutorasService unidadesEjecutorasService) {
        this.unidadesEjecutorasService = unidadesEjecutorasService;
    }

    @GetMapping
    @Operation(
            summary = "Obtener las unidades ejecutoras",
            description = "Devuelve la lista de unidades ejecutoras activas."
    )
    public ResponseEntity<List<UnidadEjecutoraDTO>> obtenerCatalogoUnidadesEjecutoras() {
        return ResponseEntity.ok(unidadesEjecutorasService.obtenerCatalogoUnidadesEjecutoras());
    }
}
