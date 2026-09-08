package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.estatus.controller;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.estatus.dto.EstatusPrecompromisoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.estatus.service.EstatusPrecompromisoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogos/estatus")
public class EstatusPrecompromisoController {
    private final EstatusPrecompromisoService estatusPrecompromisoService;

    public EstatusPrecompromisoController(EstatusPrecompromisoService estatusPrecompromisoService) {
        this.estatusPrecompromisoService = estatusPrecompromisoService;
    }

    @GetMapping("")
    public ResponseEntity<List<EstatusPrecompromisoDTO>> obtenerCatalogoEstatusPrecompromisos() {
        return ResponseEntity.ok(estatusPrecompromisoService.obtenerCatalogoEstatusPrecompromisos());
    }
}
