package mx.gob.senado.tesoreria.precompromisos.modules.auth.dto;

import java.util.List;

public record UserInfoDTO(
        Long idUsuario,
        Long numEmpleado,
        String correo,
        List<String> roles,
        List<String> unidades,
        String accessToken,
        String tokenType
) {}
