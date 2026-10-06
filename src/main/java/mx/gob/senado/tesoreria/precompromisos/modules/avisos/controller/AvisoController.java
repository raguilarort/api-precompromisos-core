package mx.gob.senado.tesoreria.precompromisos.modules.avisos.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import mx.gob.senado.tesoreria.precompromisos.modules.avisos.dto.AvisoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.avisos.service.AvisoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/avisos")
@Tag(name = "Avisos", description = "Gestión de los avisos")
public class AvisoController {

    private final AvisoService service;

    public AvisoController(AvisoService service) { this.service = service; }

    @GetMapping("/activos")
    public ResponseEntity<List<AvisoDTO>> obtenerAvisosActivos() {
        List<AvisoDTO> avisos = service.obtenerActivos();
        return ResponseEntity.ok(avisos);
    }
}
