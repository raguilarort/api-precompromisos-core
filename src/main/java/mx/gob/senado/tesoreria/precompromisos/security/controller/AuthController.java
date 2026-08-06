package mx.gob.senado.tesoreria.precompromisos.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import mx.gob.senado.tesoreria.precompromisos.security.dto.AuthRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.security.dto.UserInfoDTO;
import mx.gob.senado.tesoreria.precompromisos.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login-microsoft")
    public ResponseEntity<?> loginConEntraId(@RequestBody AuthRequestDTO payload, HttpServletRequest request) {
        String msToken = payload.microsoftToken();

        if (msToken == null || msToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Falta el idToken de Microsoft"));
        }

        try {
            // 1. Decodificar el JWT de Microsoft (Separado por puntos: Header.Payload.Signature)
            String[] splitToken = msToken.split("\\.");
            if (splitToken.length < 2) throw new IllegalArgumentException("Token de Microsoft con formato inválido");

            // Decodificamos el Payload (Base64Url)
            String payloadJson = new String(Base64.getUrlDecoder().decode(splitToken[1]));

            // Usamos Jackson para mapear el JSON a un Map
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> tokenData = mapper.readValue(payloadJson, Map.class);

            // Microsoft Entra ID suele enviar el correo en "preferred_username" o "email"
            String emailEntraId = (String) tokenData.getOrDefault("preferred_username", tokenData.get("email"));

            if (emailEntraId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "El token de Entra ID no contiene un correo electrónico válido"));
            }

            // 2. Extraer datos de auditoría para la base de datos
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty()) ip = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");

            // 3. Orquestar el login a través del Service
            UserInfoDTO userInfo = authService.procesarLogin(emailEntraId, ip, userAgent);

            return ResponseEntity.ok(userInfo);
        } catch (SecurityException se) {
            // El usuario no existe o está inactivo (Arrojado por el Stored Procedure)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", se.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error procesando la autenticación: " + e.getMessage()));
        }
    }
}