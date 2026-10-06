package mx.gob.senado.tesoreria.precompromisos.modules.avisos.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.avisos.dto.AvisoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.SeguimientoOperativoDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class AvisoRepository {
    private static final String paqueteConsulta = "PKG_PRECOMPROMISO_AVISOS_QRY";

    private final SimpleJdbcCall consultarAvisosActivosCall;

    public AvisoRepository(DataSource dataSource) {
        this.consultarAvisosActivosCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paqueteConsulta)
                .withProcedureName("SP_OBTENER_AVISOS_ACTIVOS")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                    new SqlOutParameter("p_cursor", Types.REF_CURSOR, (rs, rowNum) -> new AvisoDTO(
                            rs.getLong("id"),
                            rs.getString("titulo"),
                            rs.getString("mensaje"),
                            rs.getTimestamp("fecha_registro").toLocalDateTime(),
                            rs.getString("prioridad"))
                    )
                );

    }

    @SuppressWarnings("unchecked")
    public List<AvisoDTO> consultarActivos() {
        Map<String, Object> out = consultarAvisosActivosCall.execute();
        return (List<AvisoDTO>) out.get("p_cursor");
    }
}
