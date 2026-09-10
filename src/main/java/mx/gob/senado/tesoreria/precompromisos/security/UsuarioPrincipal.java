package mx.gob.senado.tesoreria.precompromisos.security;

public record UsuarioPrincipal(
        Integer idUsuario,
        String email,
        Number numEmpleado
) {}
