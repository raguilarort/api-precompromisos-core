package mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.ClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.DisponibilidadClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.FiltroClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.service.ClavesPresupuestariasService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/claves-presupuestarias")
@Tag(name = "Claves Presupuestarias", description = "\"Gestión de la estructura presupuestal y sus saldos")
public class ClavesPresupuestariasController {
    private final ClavesPresupuestariasService service;

    public ClavesPresupuestariasController(ClavesPresupuestariasService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Obtener información general de una clave presupuestaria")
    public ResponseEntity<ClavePresupuestariaDTO> consultarClavePresupuestaria(
            @Parameter(description = "Identificador de la clave presupuestaria", example = "1119112")
            @RequestParam(name = "idClavePresupuestaria") Integer idClavePresupuestaria) {
        return ResponseEntity.ok(service.consultarClavePresupuestaria(idClavePresupuestaria));
    }

    @GetMapping(params = {"ejercicio", "unidad", "idCveProg", "idPartida", "idFuenteFin"})
    @Operation(summary = "Verificar combinación y obtener información general de una clave presupuestaria")
    public ResponseEntity<ClavePresupuestariaDTO> buscarClavePresupuestaria(
            @ParameterObject @Valid FiltroClavePresupuestariaDTO filtro) {
        return ResponseEntity.ok(service.buscarClavePresupuestaria(filtro));
    }

    @GetMapping(value = "/disponibilidad", params = {"ejercicio", "unidad", "idCveProg", "idPartida", "idFuenteFin"})
    @Operation(summary = "Verificar combinación y obtener disponibilidad mensual de una clave presupuestaria")
    public ResponseEntity<DisponibilidadClavePresupuestariaDTO> consultarDisponibilidad(
            @ParameterObject @Valid FiltroClavePresupuestariaDTO filtro) {

        return ResponseEntity.ok(service.consultarDisponibilidad(filtro));
    }

    @GetMapping("/{idClavePresupuestaria}/disponibilidad")
    @Operation(summary = "Obtener disponibilidad mensual de una clave presupuestaria")
    public ResponseEntity<DisponibilidadClavePresupuestariaDTO> consultarDisponibilidadPorId(
            @Parameter(description = "Identificador de la clave presupuestaria", example = "1119112")
            @PathVariable(name = "idClavePresupuestaria") Integer idClavePresupuestaria) {

        return ResponseEntity.ok(service.consultarDisponibilidadPorId(0, idClavePresupuestaria));
    }
}
