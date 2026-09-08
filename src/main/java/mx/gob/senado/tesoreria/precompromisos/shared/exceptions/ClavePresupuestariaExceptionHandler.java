package mx.gob.senado.tesoreria.precompromisos.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ClavePresupuestariaExceptionHandler {

    @ExceptionHandler(ClavePresupuestariaException.class)
    public ResponseEntity<Map<String, String>> handleClavePresupuestariaException(ClavePresupuestariaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "Clave Presupuestaria No Válida",
                "mensaje", ex.getMessage() // Obtiene el mensaje del Static Factory Method
        ));
    }
}
