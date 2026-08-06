package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

@Repository
public class UsuarioAdminRepository {

    private final SimpleJdbcCall upsertUsuarioCall;
    private final SimpleJdbcCall asignarRolCall;
    private final SimpleJdbcCall revocarRolCall;
    private final SimpleJdbcCall asignarUnidadCall;
    private final SimpleJdbcCall revocarUnidadCall;

    public UsuarioAdminRepository(DataSource dataSource) {
        String paquete = "PKG_PRECOMP_ADMIN_USUARIOS";

        this.upsertUsuarioCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete).withProcedureName("SP_UPSERT_USUARIO");

        this.asignarRolCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete).withProcedureName("SP_ASIGNAR_ROL");

        this.revocarRolCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete).withProcedureName("SP_REVOCAR_ROL");

        this.asignarUnidadCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete).withProcedureName("SP_ASIGNAR_UNIDAD");

        this.revocarUnidadCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete).withProcedureName("SP_REVOCAR_UNIDAD");
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
                .addValue("p_unidad", unidad)
                .addValue("p_usuario_asigno", usuarioAsigno);
        return asignarUnidadCall.execute(in);
    }

    public Map<String, Object> revocarUnidad(Long idAsignacion) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id_asignacion", idAsignacion);
        return revocarUnidadCall.execute(in);
    }
}
