package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.unidadesejecutoras.dto.UnidadEjecutoraDTO;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class UnidadesEjecutorasRepository {
    private final SimpleJdbcCall getCatalogoUnidadesEjecutorasCall;

    public UnidadesEjecutorasRepository(DataSource dataSource) {
        String paquete = "PKG_PRECOMP_CATALOGOS";

        this.getCatalogoUnidadesEjecutorasCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_GET_UNI_EJECUTORAS")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("p_cursor", Types.REF_CURSOR, (rs, rowNum) -> new UnidadEjecutoraDTO(
                                rs.getString("UNIDAD_EJECUTORA"),
                                rs.getString("INICIALES"),
                                rs.getString("NOMBRE_CORTO"),
                                rs.getString("DESCRIPCION"),
                                rs.getString("AMBITO")
                        ))
                );
    }

    public List<UnidadEjecutoraDTO> getCatalogoUnidadesEjecutoras() {
        Map<String, Object> out = getCatalogoUnidadesEjecutorasCall.execute();
        return (List<UnidadEjecutoraDTO>) out.get("p_cursor");
    }
}
