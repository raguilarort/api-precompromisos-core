package mx.gob.senado.tesoreria.precompromisos.modules.auth.repository;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

@Repository
public class AuthRepository {

    private final SimpleJdbcCall loginSimpleJdbcCall;

    public AuthRepository(DataSource dataSource) {
        this.loginSimpleJdbcCall = new SimpleJdbcCall(dataSource)
                .withCatalogName("PKG_PRECOMP_SEGURIDAD")
                .withProcedureName("SP_LOGIN")
                .withoutProcedureColumnMetaDataAccess() // Apagamos la lectura de metadata de Oracle
                .declareParameters(
                        // 1. Parámetros de Entrada (IN)
                        new SqlParameter("p_correo", Types.VARCHAR),
                        new SqlParameter("p_ip", Types.VARCHAR),
                        new SqlParameter("p_user_agent", Types.VARCHAR),

                        // 2. Parámetros de Salida Simples (OUT)
                        new SqlOutParameter("p_id_usuario", Types.NUMERIC),
                        new SqlOutParameter("p_num_empleado", Types.NUMERIC),

                        // 3. Parámetros de Salida tipo Cursor (OUT SYS_REFCURSOR)
                        new SqlOutParameter("p_roles", Types.REF_CURSOR, (rs, rowNum) -> rs.getString("CLAVE")),
                        new SqlOutParameter("p_unidades", Types.REF_CURSOR, (rs, rowNum) -> rs.getString("UNIDAD_EJECUTORA")),

                        // 4. Parámetros de Estado (OUT)
                        new SqlOutParameter("p_estatus", Types.NUMERIC),
                        new SqlOutParameter("p_mensaje", Types.VARCHAR)
                );
    }

    public Map<String, Object> ejecutarLogin(String correo, String ip, String userAgent) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_correo", correo)
                .addValue("p_ip", ip)
                .addValue("p_user_agent", userAgent);

        return loginSimpleJdbcCall.execute(in);
    }
}