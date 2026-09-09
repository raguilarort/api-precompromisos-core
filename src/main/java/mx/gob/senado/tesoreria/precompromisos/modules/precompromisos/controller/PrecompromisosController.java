package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.service.PrecompromisosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/precompromisos")
@Tag(name = "Precompromisos", description = "Gestión transaccional de precompromisos presupuestales")
public class PrecompromisosController {

    private final PrecompromisosService service;

    public PrecompromisosController(PrecompromisosService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo precompromiso")
    public ResponseEntity<Map<String, Object>> registrarPrecompromiso(
            @Valid @RequestBody PrecompromisoRequestDTO payload) {

        // El servicio devolverá el ID o Folio generado
        String folioGenerado = service.registrar(payload);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensaje", "Precompromiso registrado exitosamente",
                "folio", folioGenerado
        ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un precompromiso existente")
    public ResponseEntity<Map<String, String>> actualizarPrecompromiso(
            @PathVariable Integer id,
            @Valid @RequestBody PrecompromisoRequestDTO payload) {

        service.actualizar(id, payload);

        return ResponseEntity.ok(Map.of(
                "mensaje", "Precompromiso actualizado exitosamente"
        ));
    }
}
