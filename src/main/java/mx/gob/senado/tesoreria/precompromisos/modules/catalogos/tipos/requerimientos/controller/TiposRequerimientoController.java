package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.requerimientos.controller;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.requerimientos.dto.TipoRequerimientoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.requerimientos.service.TiposRequerimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogos/tipos-requerimientos")
public class TiposRequerimientoController {
    private final TiposRequerimientoService tiposRequerimientoService;

    public TiposRequerimientoController(TiposRequerimientoService tiposRequerimientoService) {
        this.tiposRequerimientoService = tiposRequerimientoService;
    }

    @GetMapping("")
    public ResponseEntity<List<TipoRequerimientoDTO>> obtenerCatalogoTiposRequerimiento() {
        return ResponseEntity.ok(tiposRequerimientoService.obtenerCatalogoTiposRequerimiento());
    }
}
