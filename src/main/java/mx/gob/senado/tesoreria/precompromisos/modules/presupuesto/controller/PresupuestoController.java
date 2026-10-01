package mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.FiltroClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto.DesglosePresupuestalDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto.PresupuestoDisponibleDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto.PresupuestoPrecomprometidoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.service.PresupuestoService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/presupuesto")
@Tag(name = "Presupuesto", description = "Gestión de saldos, disponibilidad y bolsa alterna de precompromisos")
public class PresupuestoController {

    private final PresupuestoService service;

    public PresupuestoController(PresupuestoService service) {
        this.service = service;
    }


    @GetMapping("/disponible")
    @Operation(summary = "Obtener reporte global de disponibilidad por ejercicio")
    public ResponseEntity<List<PresupuestoDisponibleDTO>> reporteDisponible(
            @RequestParam(name = "ejercicio") Integer ejercicio) {
        // Retorna la sábana completa del año
        return ResponseEntity.ok(service.consultarPresupuestoDisponible(ejercicio));
    }

    @GetMapping("/disponible/{idClavePresupuestaria}")
    @Operation(summary = "Obtener disponibilidad mensual de una clave por su ID")
    public ResponseEntity<PresupuestoDisponibleDTO> consultarDisponiblePorId(
            @PathVariable Integer idClavePresupuestaria,
            @RequestParam Integer ejercicio) {
        return ResponseEntity.ok(service.consultarPresupuestoDisponible(ejercicio, idClavePresupuestaria));
    }

    @GetMapping(value = "/disponible", params = {"ejercicio", "unidad", "idCveProg", "idPartida", "idFuenteFin"})
    @Operation(summary = "Verificar combinación y obtener disponibilidad por estructura")
    public ResponseEntity<PresupuestoDisponibleDTO> consultarDisponiblePorFiltro(
            @ParameterObject @Valid FiltroClavePresupuestariaDTO filtro) {
        // Tu lógica exacta conservada
        return ResponseEntity.ok(service.consultarPresupuestoDisponible(filtro));
    }


    @GetMapping("/precomprometido")
    @Operation(summary = "Obtener reporte global de la bolsa de precompromisos por ejercicio")
    public ResponseEntity<List<PresupuestoPrecomprometidoDTO>> reportePrecomprometido(
            @RequestParam(name = "ejercicio") Integer ejercicio) {
        return ResponseEntity.ok(service.consultarPresupuestoPrecomprometido(ejercicio));
    }

    @GetMapping("/precomprometido/{idClavePresupuestaria}")
    @Operation(summary = "Obtener apartados mensuales de una clave por su ID")
    public ResponseEntity<PresupuestoPrecomprometidoDTO> consultarPrecomprometidoPorId(
            @PathVariable Integer idClavePresupuestaria,
            @RequestParam Integer ejercicio) {
        return ResponseEntity.ok(service.consultarPresupuestoPrecomprometido(ejercicio, idClavePresupuestaria));
    }

    @GetMapping(value = "/precomprometido", params = {"ejercicio", "unidad", "idCveProg", "idPartida", "idFuenteFin"})
    @Operation(summary = "Obtener apartados mensuales por estructura")
    public ResponseEntity<PresupuestoPrecomprometidoDTO> consultarPrecomprometidoPorFiltro(
            @ParameterObject @Valid FiltroClavePresupuestariaDTO filtro) {
        return ResponseEntity.ok(service.consultarPresupuestoPrecomprometido(filtro));
    }


    // =========================================================================
    // 3. RECURSO: DESGLOSE ORQUESTADO (Para el Frontend / Popovers / Badges)
    // =========================================================================

    @GetMapping("/desglose-saldos/{idClavePresupuestaria}")
    @Operation(summary = "Obtener desglose orquestado (GRP + Precomprometido) para la UI")
    public ResponseEntity<DesglosePresupuestalDTO> consultarDesglosePorId(
            @PathVariable Integer idClavePresupuestaria) {
        // Este endpoint llama internamente a los 2 repositorios y une el DTO como platicamos
        return ResponseEntity.ok(service.consultarPresupuestos(0, idClavePresupuestaria));
    }

    @GetMapping(value = "/desglose-saldos", params = {"ejercicio", "unidad", "idCveProg", "idPartida", "idFuenteFin"})
    @Operation(summary = "Obtener desglose orquestado por estructura para la UI")
    public ResponseEntity<DesglosePresupuestalDTO> consultarDesglosePorFiltro(
            @ParameterObject @Valid FiltroClavePresupuestariaDTO filtro) {
        // Ideal para cuando el usuario está creando el concepto y aún no conocemos el ID de la clave, solo su estructura
        return ResponseEntity.ok(service.consultarPresupuestos(filtro));
    }
}
