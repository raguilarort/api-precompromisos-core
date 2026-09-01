package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.dto.FuenteFinanciamientoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.service.FuentesFinanciamientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogos/fuentes-financiamiento")
public class FuentesFinanciamientoController {
    private final FuentesFinanciamientoService fuentesFinanciamientoService;

    public FuentesFinanciamientoController(FuentesFinanciamientoService fuentesFinanciamientoService) {
        this.fuentesFinanciamientoService = fuentesFinanciamientoService;
    }

    @GetMapping
    @Operation(
            summary = "Obtener todas las fuentes de financiamiento",
            description = "Devuelve el catálogo completo de fuentes de financiamiento"
    )
    public ResponseEntity<List<FuenteFinanciamientoDTO>> obtenerCatalogoFuentesFinanciamiento() {
        return ResponseEntity.ok(fuentesFinanciamientoService.obtenerCatalogoFuentesFinanciamiento());
    }

    @GetMapping(params = {"ejercicio", "unidad", "idCveProg", "idPartida"})
    @Operation(
            summary = "Obtener todas las fuentes de financiamiento vinculadas a una unidad, clave programática y partida",
            description = "Devuelve la lista de fuentes de financiamiento. Soporta filtrado opcional por ejercicio, unidad ejecutora, programa y partida."
    )
    public ResponseEntity<List<FuenteFinanciamientoDTO>> consultarFuentesFinanciamiento(
        @Parameter(description = "Año del ejercicio presupuestal", example = "2026")
        @RequestParam(name = "ejercicio") Integer ejercicio,

        @Parameter(description = "Clave de la unidad ejecutora (Ej. 101, 102).", example = "101")
        @RequestParam(name = "unidad") String unidad,

        @Parameter(description = "Identificador de la clave programática")
        @RequestParam(name = "idCveProg") Integer idCveProg,

        @Parameter(description = "Identificador de la partida")
        @RequestParam(name = "idPartida") Integer idPartida
    ) {
        List<FuenteFinanciamientoDTO> fuentesFinanciamiento = fuentesFinanciamientoService.consultarFuentesFinanciamiento(ejercicio, unidad, idCveProg, idPartida);

        if (fuentesFinanciamiento.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(fuentesFinanciamiento);
    }
}