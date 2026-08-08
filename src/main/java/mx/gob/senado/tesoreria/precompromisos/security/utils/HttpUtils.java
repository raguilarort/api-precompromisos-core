package mx.gob.senado.tesoreria.precompromisos.security.utils;

import jakarta.servlet.http.HttpServletRequest;

public final class HttpUtils {

    // Constructor privado para evitar instanciación
    private HttpUtils() {
        throw new UnsupportedOperationException("Clase de utilería");
    }

    /**
     * Extrae la IP real del cliente, incluso si la petición pasó por un Proxy, WAF o Load Balancer.
     */
    public static String extraerIpReal(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // Si la petición pasa por múltiples proxies, X-Forwarded-For devuelve una lista separada por comas.
        // La primera IP es la del cliente original.
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }

        return ipAddress;
    }
}
