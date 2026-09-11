package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoDetailDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoResumeDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.service.PrecompromisosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/precompromisos")
@Tag(name = "Precompromisos", description = "Gestión transaccional de precompromisos presupuestales")
public class PrecompromisosController {

    private final PrecompromisosService service;

    public PrecompromisosController(PrecompromisosService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar precompromisos por ejercicio fiscal")
    public ResponseEntity<List<PrecompromisoResumeDTO>> listarPorEjercicio(
            @RequestParam Integer ejercicio) {
        // Asumiendo que inyectas la llamada del Controller -> Service -> Repository
        return ResponseEntity.ok(service.consultarPorEjercicio(ejercicio));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener el detalle completo de un precompromiso para edición")
    public ResponseEntity<PrecompromisoDetailDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo precompromiso")
    public ResponseEntity<Map<String, Object>> registrarPrecompromiso(
            @Valid @RequestBody PrecompromisoRequestDTO payload) {

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
