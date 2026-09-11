package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoResumenDTO;
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
    public ResponseEntity<List<PrecompromisoResumenDTO>> listarPorEjercicio(
            @RequestParam Integer ejercicio) {
        // Asumiendo que inyectas la llamada del Controller -> Service -> Repository
        return ResponseEntity.ok(service.consultarPorEjercicio(ejercicio));
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
