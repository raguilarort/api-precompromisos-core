package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.repository;

import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

@Repository
public class UsuarioAdminRepository {

    private final SimpleJdbcCall upsertUsuarioCall;
    private final SimpleJdbcCall asignarRolCall;
    private final SimpleJdbcCall revocarRolCall;
    private final SimpleJdbcCall asignarUnidadCall;
    private final SimpleJdbcCall revocarUnidadCall;

    public UsuarioAdminRepository(DataSource dataSource) {
        String paquete = "PKG_PRECOMP_SEGURIDAD";

        this.upsertUsuarioCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_UPSERT_USUARIO")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlInOutParameter("p_id_usuario", Types.NUMERIC),
                        new SqlParameter("p_correo", Types.VARCHAR),
                        new SqlParameter("p_num_empleado", Types.NUMERIC),
                        new SqlParameter("p_activo", Types.NUMERIC),
                        new SqlOutParameter("p_estatus", Types.NUMERIC),
                        new SqlOutParameter("p_mensaje", Types.VARCHAR)
                );

        this.asignarRolCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_ASIGNAR_ROL")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_id_usuario", Types.NUMERIC),
                        new SqlParameter("p_id_rol", Types.NUMERIC),
                        new SqlOutParameter("p_estatus", Types.NUMERIC),
                        new SqlOutParameter("p_mensaje", Types.VARCHAR)
                );

        this.revocarRolCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_REVOCAR_ROL")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_id_asignacion", Types.NUMERIC),
                        new SqlOutParameter("p_estatus", Types.NUMERIC),
                        new SqlOutParameter("p_mensaje", Types.VARCHAR)
                );

        this.asignarUnidadCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_ASIGNAR_UNIDAD")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_id_usuario", Types.NUMERIC),
                        new SqlParameter("p_unidad", Types.VARCHAR),
                        new SqlOutParameter("p_estatus", Types.NUMERIC),
                        new SqlOutParameter("p_mensaje", Types.VARCHAR)
                );

        this.revocarUnidadCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_REVOCAR_UNIDAD")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_id_asignacion", Types.NUMERIC),
                        new SqlOutParameter("p_estatus", Types.NUMERIC),
                        new SqlOutParameter("p_mensaje", Types.VARCHAR)
                );
    }

    public Map<String, Object> upsertUsuario(Long idUsuario, String correo, Long numEmpleado, Integer activo) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id_usuario", idUsuario)
                .addValue("p_correo", correo)
                .addValue("p_num_empleado", numEmpleado)
                .addValue("p_activo", activo);

        return upsertUsuarioCall.execute(in);
    }

    public Map<String, Object> asignarRol(Long idUsuario, Long idRol) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id_usuario", idUsuario)
                .addValue("p_id_rol", idRol);

        return asignarRolCall.execute(in);
    }

    public Map<String, Object> revocarRol(Long idAsignacion) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id_asignacion", idAsignacion);

        return revocarRolCall.execute(in);
    }

    public Map<String, Object> asignarUnidad(Long idUsuario, String unidad, String usuarioAsigno) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id_usuario", idUsuario)
                .addValue("p_unidad", unidad);

        return asignarUnidadCall.execute(in);
    }

    public Map<String, Object> revocarUnidad(Long idAsignacion) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id_asignacion", idAsignacion);
        return revocarUnidadCall.execute(in);
    }
}
