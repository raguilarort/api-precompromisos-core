package mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.controller;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.controller.PrecompromisosController;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoDisponibleException;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoPrecomprometidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(assignableTypes = PrecompromisosController.class)
public class PrecompromisoExceptionHandler {
    @ExceptionHandler(PresupuestoDisponibleException.class)
    public ResponseEntity<Map<String, String>> handlePresupuestoDisponibleException(PresupuestoDisponibleException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "Error en presupuesto disponible",
                "mensaje", ex.getMessage()
        ));
    }

    @ExceptionHandler(PresupuestoPrecomprometidoException.class)
    public ResponseEntity<Map<String, String>> handlePresupuestoPrecomprometidoException(PresupuestoPrecomprometidoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "Error en presupuesto precomprometido",
                "mensaje", ex.getMessage()
        ));
    }
}
