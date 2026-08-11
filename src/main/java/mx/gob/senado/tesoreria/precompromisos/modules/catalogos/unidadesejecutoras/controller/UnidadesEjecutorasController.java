package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.dto.UnidadEjecutoraDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.service.UnidadesEjecutorasService;

@RestController
@RequestMapping("/catalogos/unidadesejecutoras")
@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
public class UnidadesEjecutorasController {
    private final UnidadesEjecutorasService unidadesEjecutorasService;

    public UnidadesEjecutorasController(UnidadesEjecutorasService unidadesEjecutorasService) {
        this.unidadesEjecutorasService = unidadesEjecutorasService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<UnidadEjecutoraDTO>> obtenerCatalogoUnidadesEjecutoras() {
        return ResponseEntity.ok(unidadesEjecutorasService.obtenerCatalogoUnidadesEjecutoras());
    }
}
