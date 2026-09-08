package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.dto.FiltroFuenteFinanciamientoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.dto.FuenteFinanciamientoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.service.FuentesFinanciamientoService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            summary = "Consultar fuentes de financiamiento filtradas",
            description = "Devuelve la lista de fuentes de financiamiento. Soporta filtrado por ejercicio, unidad, programa y partida."
    )
    public ResponseEntity<List<FuenteFinanciamientoDTO>> consultarFuentesFinanciamiento(
            @ParameterObject @Valid FiltroFuenteFinanciamientoDTO filtro
    ) {
        List<FuenteFinanciamientoDTO> fuentes = fuentesFinanciamientoService.consultarFuentesFinanciamiento(filtro);

        if (fuentes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(fuentes);
    }
}