package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.controller;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.CambioEstatusRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.DisponibilidadDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.SeguimientoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.service.PrecompromisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/precompromisos")
public class PrecompromisoController {

    @Autowired
    private PrecompromisoService precompromisoService;

    @PostMapping
    public ResponseEntity<?> crearPrecompromiso(
            @RequestBody PrecompromisoRequestDTO request,
            @RequestAttribute("numEmpleado") String numEmpleado) {

        try {
            Long idGenerado = precompromisoService.registrarNuevoPrecompromiso(request, numEmpleado);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "mensaje", "Precompromiso registrado exitosamente",
                    "idPrecompromiso", idGenerado
            ));

        } catch (IllegalArgumentException e) {
            // Maneja errores de validación de negocio en Java
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/estatus")
    public ResponseEntity<?> cambiarEstatus(
            @PathVariable("id") Long idPrecompromiso,
            @RequestBody CambioEstatusRequestDTO request,
            @RequestAttribute("numEmpleado") String numEmpleado) {

        try {
            precompromisoService.procesarCambioEstatus(idPrecompromiso, request, numEmpleado);
            return ResponseEntity.ok(Map.of("mensaje", "Estatus actualizado correctamente"));

        } catch (IllegalArgumentException e) {
            // Reglas de negocio (Ej. Falta de fondos, estado incorrecto)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (SecurityException e) {
            // Falta de permisos según el rol
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/disponibilidad/{claveId}")
    public ResponseEntity<DisponibilidadDTO> obtenerDisponibilidad(@PathVariable("claveId") Long claveId) {
        DisponibilidadDTO disponibilidad = precompromisoService.consultarDisponibilidad(claveId);
        return ResponseEntity.ok(disponibilidad);
    }

    @GetMapping("/{id}/seguimiento")
    public ResponseEntity<List<SeguimientoDTO>> obtenerHistorialPrecompromiso(@PathVariable("id") Long idPrecompromiso) {
        List<SeguimientoDTO> historial = precompromisoService.consultarHistorial(idPrecompromiso);

        if (historial.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 si no hay datos
        }

        return ResponseEntity.ok(historial);
    }
}
