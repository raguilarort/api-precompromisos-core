package mx.gob.senado.tesoreria.precompromisos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleOracleSQLExceptions(SQLException ex) {
        String mensajeOracle = ex.getMessage();

        // Buscamos si es un error controlado por nuestros Stored Procedures (Códigos -20000)
        if (mensajeOracle != null && mensajeOracle.contains("ORA-200")) {
            // Extraemos solo el mensaje limpio, quitando el código ORA y el salto de línea
            String mensajeLimpio = mensajeOracle.substring(mensajeOracle.indexOf(":") + 1).split("\n")[0].trim();

            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Regla de Negocio",
                    "mensaje", mensajeLimpio
            ));
        }

        // Si es un error de base de datos no controlado (Ej. Se cayó la red)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Error interno de base de datos. Contacte al administrador."
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error interno de base de datos. Contacte al administrador."));
    }
}