package mx.gob.senado.tesoreria.precompromisos.security.repository;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

@Repository
public class AuthRepository {

    private final SimpleJdbcCall loginJdbcCall;

    public AuthRepository(DataSource dataSource) {
        this.loginJdbcCall = new SimpleJdbcCall(dataSource)
                .withCatalogName("PKG_PRECOMP_SEGURIDAD")
                .withProcedureName("SP_LOGIN")
                .returningResultSet("p_roles", (rs, rowNum) -> rs.getString("CLAVE"))
                .returningResultSet("p_unidades", (rs, rowNum) -> rs.getString("UNIDAD_EJECUTORA"));
    }

    public Map<String, Object> ejecutarLogin(String correo, String ip, String userAgent) {
        return loginJdbcCall.execute(Map.of(
                "p_correo", correo,
                "p_ip", ip,
                "p_user_agent", userAgent
        ));
    }
}