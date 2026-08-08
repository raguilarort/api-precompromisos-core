package mx.gob.senado.tesoreria.precompromisos.security.audit;

import mx.gob.senado.tesoreria.precompromisos.security.utils.HttpUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityAuditService {

    private static final Logger log = LoggerFactory.getLogger("SECURITY_AUDIT_LOG");
    private final ObjectMapper objectMapper;

    // Inyectamos el ObjectMapper global de Spring Boot
    //@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SecurityAuditService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Emite un evento de seguridad estandarizado en formato JSON para la ingesta en sistemas SIEM.
     */
    public void registrarAlerta(String tipoEvento, String motivo, String descripcion, HttpServletRequest request) {
        try {
            Map<String, Object> logData = new HashMap<>();
            logData.put("evento", tipoEvento); // ej. AUTH_FAILED, UNAUTHORIZED_ACCESS
            logData.put("motivo", motivo);
            logData.put("descripcion", descripcion);
            logData.put("ip", HttpUtils.extraerIpReal(request));
            logData.put("userAgent", request.getHeader("User-Agent"));
            logData.put("timestamp", Instant.now().toString());

            log.warn(objectMapper.writeValueAsString(logData));
        } catch (Exception e) {
            log.warn("FALLO_AUDITORIA | EVENTO: {} | MOTIVO: {} | IP: {}", tipoEvento, motivo, HttpUtils.extraerIpReal(request));
        }
    }
}
