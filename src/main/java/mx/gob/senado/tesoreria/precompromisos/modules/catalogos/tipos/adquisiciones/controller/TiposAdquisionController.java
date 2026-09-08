package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.adquisiciones.controller;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.adquisiciones.dto.TipoAdquisicionDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.adquisiciones.service.TiposAdquisicionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogos/tipos-adquisiciones")
public class TiposAdquisionController {
    private final TiposAdquisicionService tiposAdquisicionService;

    public TiposAdquisionController(TiposAdquisicionService tiposAdquisicionService) {
        this.tiposAdquisicionService = tiposAdquisicionService;
    }

    @GetMapping("")
    public ResponseEntity<List<TipoAdquisicionDTO>> obtenerCatalogoTiposAdquisicion() {
        return ResponseEntity.ok(tiposAdquisicionService.obtenerCatalogoTiposAdquisicion());
    }
}

