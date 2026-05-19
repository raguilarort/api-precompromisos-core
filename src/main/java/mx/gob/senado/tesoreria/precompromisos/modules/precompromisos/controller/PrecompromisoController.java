package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.controller;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.service.PrecompromisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
