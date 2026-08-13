package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.clavesprogramaticas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.clavesprogramaticas.dto.ClaveProgramaticaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.clavesprogramaticas.service.ClavesProgramaticasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogos/claves-programaticas")
@Tag(name = "Catálogos Presupuestales", description = "Endpoints para consultar la estructura programática")
public class ClavesProgramaticasController {
    private final ClavesProgramaticasService service;

    public ClavesProgramaticasController(ClavesProgramaticasService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Obtener claves programáticas",
            description = "Devuelve la lista de claves programáticas (Nivel 5) de un ejercicio. Soporta filtrado opcional por unidad ejecutora."
    )
    public ResponseEntity<List<ClaveProgramaticaDTO>> consultarClavesProgramaticas(
            @Parameter(description = "Año del ejercicio presupuestal", example = "2026")
            @RequestParam(name = "ejercicio") Integer ejercicio,

            @Parameter(description = "Clave de la unidad ejecutora (Ej. 101, 102). Si se omite, trae todas.", example = "101")
            @RequestParam(name = "unidad", required = false) String unidad) {

        List<ClaveProgramaticaDTO> claves = service.obtenerClavesProgramaticas(ejercicio, unidad);

        if (claves.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(claves);
    }
}
