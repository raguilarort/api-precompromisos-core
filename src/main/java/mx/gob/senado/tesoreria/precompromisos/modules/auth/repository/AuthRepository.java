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
    private static final String PAQUETE = "PKG_PRECOMP_SEGURIDAD";

    private final SimpleJdbcCall loginSimpleJdbcCall;
    private final SimpleJdbcCall refreshSessionJdbcCall;
    private final SimpleJdbcCall logoutSimpleJdbcCall;

    public AuthRepository(DataSource dataSource) {

        this.loginSimpleJdbcCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(PAQUETE)
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

        this.refreshSessionJdbcCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(PAQUETE)
                .withProcedureName("SP_REFRESH_TOKEN")
                .withoutProcedureColumnMetaDataAccess() // Apagamos la lectura de metadata de Oracle
                .declareParameters(
                        new SqlParameter("p_correo", Types.VARCHAR),
                        new SqlParameter("p_ip", Types.VARCHAR),
                        new SqlParameter("p_user_agent", Types.VARCHAR),
                        new SqlOutParameter("p_id_usuario", Types.NUMERIC),
                        new SqlOutParameter("p_num_empleado", Types.NUMERIC),
                        new SqlOutParameter("p_roles", Types.REF_CURSOR, (rs, rowNum) -> rs.getString("CLAVE")),
                        new SqlOutParameter("p_unidades", Types.REF_CURSOR, (rs, rowNum) -> rs.getString("UNIDAD_EJECUTORA")),
                        new SqlOutParameter("p_estatus", Types.NUMERIC),
                        new SqlOutParameter("p_mensaje", Types.VARCHAR)
                );

        this.logoutSimpleJdbcCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(PAQUETE)
                .withProcedureName("SP_LOGOUT")
                .withoutProcedureColumnMetaDataAccess() // Apagamos la lectura de metadata de Oracle
                .declareParameters(
                        // 1. Parámetros de Entrada (IN)
                        new SqlParameter("p_correo", Types.VARCHAR),
                        new SqlParameter("p_ip", Types.VARCHAR),
                        new SqlParameter("p_user_agent", Types.VARCHAR),
                        new SqlParameter("p_motivo", Types.VARCHAR),
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

    public Map<String, Object> ejecutarRefresh(String correo, String ip, String userAgent) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_correo", correo)
                .addValue("p_ip", ip)
                .addValue("p_user_agent", userAgent);
        return refreshSessionJdbcCall.execute(in);
    }

    public Map<String, Object> ejecutarLogout(String correo, String ip, String userAgent, String motivo) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_correo", correo)
                .addValue("p_ip", ip)
                .addValue("p_user_agent", userAgent)
                .addValue("p_motivo", motivo);

        return logoutSimpleJdbcCall.execute(in);
    }
}