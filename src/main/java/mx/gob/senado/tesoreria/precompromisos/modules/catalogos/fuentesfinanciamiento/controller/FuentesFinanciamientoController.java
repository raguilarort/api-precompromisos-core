package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.controller;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.dto.FuenteFinanciamientoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.service.FuentesFinanciamientoService;
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
    public ResponseEntity<List<FuenteFinanciamientoDTO>> obtenerCatalogoFuentesFinanciamiento() {
        return ResponseEntity.ok(fuentesFinanciamientoService.obtenerCatalogoFuentesFinanciamiento());
    }
}