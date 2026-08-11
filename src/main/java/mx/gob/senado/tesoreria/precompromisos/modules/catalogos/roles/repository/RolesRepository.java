package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.roles.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.roles.dto.RolDTO;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class RolesRepository {
    private final SimpleJdbcCall getCatalogoRolesCall;

    public RolesRepository(DataSource dataSource) {
        String paquete = "PKG_PRECOMP_CONSULTAS_ADMIN";

        this.getCatalogoRolesCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_GET_CATALOGO_ROLES")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("p_cursor", Types.REF_CURSOR, (rs, rowNum) -> new RolDTO(
                                rs.getLong("ID_ROL"),
                                rs.getString("CLAVE"),
                                rs.getString("DESCRIPCION")
                        ))
                );
    }

    public List<RolDTO> getCatalogoRoles() {
        Map<String, Object> out = getCatalogoRolesCall.execute();
        return (List<RolDTO>) out.get("p_cursor");
    }
}
