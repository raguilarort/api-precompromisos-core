package mx.gob.senado.tesoreria.precompromisos.modules.auth.service;

import mx.gob.senado.tesoreria.precompromisos.modules.auth.dto.UserInfoDTO;
import mx.gob.senado.tesoreria.precompromisos.security.JwtTokenProvider;
import mx.gob.senado.tesoreria.precompromisos.modules.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public UserInfoDTO procesarLogin(String correo, String ip, String userAgent) {
        // 1. Llamamos al Stored Procedure
        Map<String, Object> out = authRepository.ejecutarLogin(correo, ip, userAgent);

        // Dependiendo del driver JDBC, los keys pueden regresar en mayúsculas
        Number estatus = (Number) out.getOrDefault("P_ESTATUS", out.get("p_estatus"));
        String mensaje = (String) out.getOrDefault("P_MENSAJE", out.get("p_mensaje"));

        // 2. Evaluamos el estatus que dictó la base de datos
        if (estatus == null || estatus.intValue() != 200) {
            throw new SecurityException(mensaje != null ? mensaje : "Acceso denegado por la base de datos.");
        }

        // 3. Extraemos la información de los cursores y parámetros de salida
        Number idUsuarioNumber = (Number) out.getOrDefault("P_ID_USUARIO", out.get("p_id_usuario"));
        Number numEmpleadoNumber = (Number) out.getOrDefault("P_NUM_EMPLEADO", out.get("p_num_empleado"));

        List<String> roles = (List<String>) out.getOrDefault("P_ROLES", out.get("p_roles"));
        List<String> unidades = (List<String>) out.getOrDefault("P_UNIDADES", out.get("p_unidades"));

        Long idUsuario = (idUsuarioNumber != null) ? idUsuarioNumber.longValue() : null;
        Long numEmpleado = (numEmpleadoNumber != null) ? numEmpleadoNumber.longValue() : null;

        // 4. Generamos el JWT de nuestro backend
        String accessToken = tokenProvider.generateToken(correo, roles, unidades, numEmpleado);

        // 5. Retornamos el DTO ensamblado
        return new UserInfoDTO(
                idUsuario,
                numEmpleado,
                correo,
                roles,
                unidades,
                accessToken,
                "Bearer"
        );
    }
}
