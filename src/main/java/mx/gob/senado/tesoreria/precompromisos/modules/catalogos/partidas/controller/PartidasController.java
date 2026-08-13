package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.controller;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.dto.PartidaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.service.PartidasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogos/partidas")
public class PartidasController {
    private final PartidasService partidasService;

    public PartidasController(PartidasService partidasService) {
        this.partidasService = partidasService;
    }

    @GetMapping
    public ResponseEntity<List<PartidaDTO>> obtenerCatalogoPartidas() {
        return ResponseEntity.ok(partidasService.obtenerCatalogoPartidas());
    }
}