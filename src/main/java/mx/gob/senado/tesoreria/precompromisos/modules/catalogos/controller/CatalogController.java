package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.dto.ClavePresupuestariaCatalogDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogos")
@Tag(name = "Módulo de Catálogos", description = "Endpoints para la carga dinámica de combos y configuraciones presupuestarias.")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/claves-presupuestarias/activas/{unidad}")
    @Operation(summary = "Listar claves activas para captura", description = "Devuelve las claves concatenadas habilitadas para operar en la captura de la unidad ejecutora.")
    public ResponseEntity<List<ClavePresupuestariaCatalogDTO>> listarActivas(@PathVariable("unidad") String unidadEjecutora) {
        List<ClavePresupuestariaCatalogDTO> listado = catalogService.getClavesActivas(unidadEjecutora);
        return ResponseEntity.ok(listado);
    }

    @GetMapping("/claves-presupuestarias/configuracion/{unidad}")
    @Operation(summary = "Listar universo de claves (Solo DGPP)", description = "Devuelve el estatus de habilitación histórico y actual de todo el ecosistema de claves asignadas a una unidad.")
    public ResponseEntity<List<ClavePresupuestariaCatalogDTO>> listarConfiguracion(@PathVariable("unidad") String unidadEjecutora) {
        List<ClavePresupuestariaCatalogDTO> listado = catalogService.getClavesConfiguracion(unidadEjecutora);
        return ResponseEntity.ok(listado);
    }
}
