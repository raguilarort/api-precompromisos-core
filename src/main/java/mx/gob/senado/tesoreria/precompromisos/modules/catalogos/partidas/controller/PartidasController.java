package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.dto.PartidaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.service.PartidasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Operation(summary = "Obtener todas las partidas", description = "Devuelve el catálogo completo.")
    public ResponseEntity<List<PartidaDTO>> obtenerCatalogoCompleto() {
        return ResponseEntity.ok(partidasService.obtenerCatalogoPartidas());
        // ^ Asumiendo que agregas este método a tu service
    }

    @GetMapping(params = {"ejercicio", "unidad", "idCveProg"})
    @Operation(
            summary = "Obtener todas las partidas presupuestales vinculadas a una clave programática",
            description = "Devuelve la lista de partidas específicadas del catálogo de clasificación por objeto del gasto que cumplan con el filtro por unidad ejecutora y programa presupuestal."
    )
    public ResponseEntity<List<PartidaDTO>> consultarPartidasEspecificas(
            @Parameter(description = "Año del ejercicio presupuestal", example = "2026")
            @RequestParam(name = "ejercicio") Integer ejercicio,

            @Parameter(description = "Clave de la unidad ejecutora (Ej. 101, 102).", example = "101")
            @RequestParam(name = "unidad") String unidad,

            @Parameter(description = "Identificador de la clave programática")
            @RequestParam(name="idCveProg") Integer idCveProg) {

        List<PartidaDTO> partidas = partidasService.consultarCatalogoPartidas(ejercicio, unidad, idCveProg);

        if (partidas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(partidas);
    }
}