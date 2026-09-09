package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.controller;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.exception.PrecompromisoException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(assignableTypes = PrecompromisosController.class)
public class PrecompromisosExceptionHandler {

    @ExceptionHandler(PrecompromisoException.class)
    public ResponseEntity<Map<String, String>> handlePrecompromisoException(PrecompromisoException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(response);
    }

    // Integramos el error de duplicidad (ORA-00001) aquí
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateKeyException(DuplicateKeyException ex) {
        String mensajeUsuario = "Error de integridad en los datos.";

        if (ex.getMessage() != null && ex.getMessage().contains("IDX_UQ_ORDEN_SERVICIO")) {
            mensajeUsuario = "Ya existe un precompromiso activo registrado con esta misma orden de servicio o número de requisición. Verifique el dato.";
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("mensaje", mensajeUsuario));
    }
}
