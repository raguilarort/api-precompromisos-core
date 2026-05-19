package mx.gob.senado.tesoreria.precompromisos.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto.UsuarioLoginDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.repository.UsuarioRepository;
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
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login-microsoft")
    public ResponseEntity<?> loginConEntraId(@RequestBody Map<String, String> payload) {
        String msToken = payload.get("idToken");

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

            // 2. Buscar en Oracle si el usuario existe y está activo
            UsuarioLoginDTO dbUser = usuarioRepository.obtenerDatosLogin(emailEntraId);

            if (dbUser == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Usuario no registrado o inactivo en el sistema institucional"));
            }

            // 3. Generar TU token interno con las reglas de negocio On-Premise
            String tokenInterno = tokenProvider.generateToken(
                    dbUser.getEmail(),
                    dbUser.getRol(),
                    dbUser.getUnidadesPermitidas(),
                    dbUser.getNumEmpleado()
            );

            // 4. Retornar el token y los datos básicos al frontend
            return ResponseEntity.ok(Map.of(
                    "accessToken", tokenInterno,
                    "tokenType", "Bearer",
                    "usuario", dbUser
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error procesando la autenticación: " + e.getMessage()));
        }
    }
}