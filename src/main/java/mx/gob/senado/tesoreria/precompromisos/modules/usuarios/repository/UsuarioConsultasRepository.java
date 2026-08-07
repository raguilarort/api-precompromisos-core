package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto.AdminQueriesDTOs.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class UsuarioConsultasRepository {

    private final SimpleJdbcCall getUsuariosCall;
    private final SimpleJdbcCall getRolesUsuarioCall;
    private final SimpleJdbcCall getUnidadesUsuarioCall;
    private final SimpleJdbcCall getCatalogoRolesCall;

    public UsuarioConsultasRepository(DataSource dataSource) {
        String paquete = "PKG_PRECOMP_CONSULTAS_ADMIN";

        this.getUsuariosCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete).withProcedureName("SP_GET_USUARIOS")
                .returningResultSet("p_cursor", (rs, rowNum) -> new UsuarioResponseDTO(
                        rs.getLong("ID_USUARIO"), rs.getString("CORREO_INSTITUCIONAL"),
                        rs.getLong("NUM_EMPLEADO"), rs.getInt("ACTIVO"), rs.getString("FECHA_ALTA")
                ));

        this.getRolesUsuarioCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete).withProcedureName("SP_GET_ROLES_USUARIO")
                .returningResultSet("p_cursor", (rs, rowNum) -> new UsuarioRolResponseDTO(
                        rs.getLong("ID_ASIGNACION"), rs.getLong("ID_ROL"), rs.getString("CLAVE"),
                        rs.getString("DESCRIPCION"), rs.getInt("ACTIVO"), rs.getString("FECHA_ASIGNACION")
                ));

        this.getUnidadesUsuarioCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete).withProcedureName("SP_GET_UNIDADES_USUARIO")
                .returningResultSet("p_cursor", (rs, rowNum) -> new UsuarioUnidadResponseDTO(
                        rs.getLong("ID_ASIGNACION"), rs.getString("UNIDAD_EJECUTORA"),
                        rs.getInt("ACTIVO"), rs.getString("FECHA_ASIGNACION")
                ));

        this.getCatalogoRolesCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete).withProcedureName("SP_GET_CATALOGO_ROLES")
                .returningResultSet("p_cursor", (rs, rowNum) -> new RolCatalogoDTO(
                        rs.getLong("ID_ROL"), rs.getString("CLAVE"), rs.getString("DESCRIPCION")
                ));
    }

    public List<UsuarioResponseDTO> getUsuarios() {
        Map<String, Object> out = getUsuariosCall.execute();
        return (List<UsuarioResponseDTO>) out.get("p_cursor");
    }

    public List<UsuarioRolResponseDTO> getRolesUsuario(Long idUsuario) {
        Map<String, Object> out = getRolesUsuarioCall.execute(new MapSqlParameterSource("p_id_usuario", idUsuario));
        return (List<UsuarioRolResponseDTO>) out.get("p_cursor");
    }

    public List<UsuarioUnidadResponseDTO> getUnidadesUsuario(Long idUsuario) {
        Map<String, Object> out = getUnidadesUsuarioCall.execute(new MapSqlParameterSource("p_id_usuario", idUsuario));
        return (List<UsuarioUnidadResponseDTO>) out.get("p_cursor");
    }

    public List<RolCatalogoDTO> getCatalogoRoles() {
        Map<String, Object> out = getCatalogoRolesCall.execute();
        return (List<RolCatalogoDTO>) out.get("p_cursor");
    }
}