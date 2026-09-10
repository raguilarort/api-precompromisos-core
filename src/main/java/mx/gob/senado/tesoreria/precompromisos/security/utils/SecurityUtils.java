package mx.gob.senado.tesoreria.precompromisos.security.utils;

import mx.gob.senado.tesoreria.precompromisos.security.UsuarioPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
        // Evita que la clase sea instanciada
    }

    /**
     * Extrae el ID del usuario directamente del contexto de seguridad actual.
     */
    public static Integer obtenerIdUsuarioLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new SecurityException("No hay una sesión de usuario activa en el contexto.");
        }

        if (auth.getPrincipal() instanceof UsuarioPrincipal usuario) {
            return usuario.idUsuario();
        }

        throw new SecurityException("El contexto de seguridad no contiene una identidad válida de tipo UsuarioPrincipal.");
    }
}
