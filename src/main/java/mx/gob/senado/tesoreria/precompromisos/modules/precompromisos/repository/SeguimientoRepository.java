package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.SeguimientoOperativoDTO;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class SeguimientoRepository {

    private static final String paqueteConsulta = "PKG_PRECOMPROMISO_QRY";

    private final SimpleJdbcCall consultarSeguimientoCall;

    public SeguimientoRepository(DataSource dataSource) {
        this.consultarSeguimientoCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paqueteConsulta)
                .withProcedureName("SP_CONSULTAR_SEGUIMIENTO")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_id_precompromiso", Types.NUMERIC),
                        new SqlParameter("p_id_usuario", Types.NUMERIC),
                        new SqlOutParameter("p_cursor", Types.REF_CURSOR, (rs, rowNum) -> new SeguimientoOperativoDTO(
                                rs.getLong("id_seguimiento"),
                                rs.getInt("id_estatus"),
                                rs.getString("estatus_descripcion"),
                                rs.getString("tipo_movimiento"),
                                rs.getLong("num_empleado"),
                                rs.getString("nombre_servidor_publico"),
                                rs.getString("unidad_ejecutora"),
                                rs.getString("fecha_movimiento"),
                                rs.getString("observaciones")
                        ))
                );
    }

    @SuppressWarnings("unchecked")
    public List<SeguimientoOperativoDTO> consultarPorPrecompromiso(Integer idPrecompromiso, Integer idUsuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id_precompromiso", idPrecompromiso)
                .addValue("p_id_usuario", idUsuario);

        Map<String, Object> out = consultarSeguimientoCall.execute(in);
        return (List<SeguimientoOperativoDTO>) out.get("p_cursor");
    }
}
