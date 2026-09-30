package mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto.PresupuestoPrecomprometidoDTO;
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
public class PresupuestoPrecomprometidoRepository {
    private static final String paquete = "SAPFIN_PA.PKG_CONTROL_PRESUP_PRECOMP";

    private final SimpleJdbcCall getPresupuestoPrecomprometidoCall;
    private final SimpleJdbcCall getPresupuestoPrecomprometidoPorIdClavePresupuestariaCall;

    public PresupuestoPrecomprometidoRepository(DataSource dataSource) {

        this.getPresupuestoPrecomprometidoCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_OBTENER_PRECOMPROMETIDO")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters()
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new PresupuestoPrecomprometidoDTO(
                                rs.getInt("CLAVE_PRESUPUESTARIA_ID"),
                                rs.getBigDecimal("PRECOMP_ENE"), rs.getBigDecimal("PRECOMP_FEB"),
                                rs.getBigDecimal("PRECOMP_MAR"), rs.getBigDecimal("PRECOMP_ABR"),
                                rs.getBigDecimal("PRECOMP_MAY"), rs.getBigDecimal("PRECOMP_JUN"),
                                rs.getBigDecimal("PRECOMP_JUL"), rs.getBigDecimal("PRECOMP_AGO"),
                                rs.getBigDecimal("PRECOMP_SEP"), rs.getBigDecimal("PRECOMP_OCT"),
                                rs.getBigDecimal("PRECOMP_NOV"), rs.getBigDecimal("PRECOMP_DIC")
                        ))
                );

        this.getPresupuestoPrecomprometidoPorIdClavePresupuestariaCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_OBTENER_PRECOMPROMETIDO")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlParameter("p_id_cve_presupuestaria", Types.NUMERIC),
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new PresupuestoPrecomprometidoDTO(
                                rs.getInt("CLAVE_PRESUPUESTARIA_ID"),
                                rs.getBigDecimal("PRECOMP_ENE"), rs.getBigDecimal("PRECOMP_FEB"),
                                rs.getBigDecimal("PRECOMP_MAR"), rs.getBigDecimal("PRECOMP_ABR"),
                                rs.getBigDecimal("PRECOMP_MAY"), rs.getBigDecimal("PRECOMP_JUN"),
                                rs.getBigDecimal("PRECOMP_JUL"), rs.getBigDecimal("PRECOMP_AGO"),
                                rs.getBigDecimal("PRECOMP_SEP"), rs.getBigDecimal("PRECOMP_OCT"),
                                rs.getBigDecimal("PRECOMP_NOV"), rs.getBigDecimal("PRECOMP_DIC")
                        ))
                );
    }

    public List<PresupuestoPrecomprometidoDTO> consultarPrecomprometido(Integer ejercicio) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio);

        Map<String, Object> out = getPresupuestoPrecomprometidoCall.execute(in);

        @SuppressWarnings("unchecked")
        List<PresupuestoPrecomprometidoDTO> resultados = (List<PresupuestoPrecomprometidoDTO>) out.get("p_resultado");

        return resultados;
    }

    public List<PresupuestoPrecomprometidoDTO> consultarPrecomprometido(Integer ejercicio, Integer idClavePresupuestaria) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio)
                .addValue("p_id_cve_presupuestaria", idClavePresupuestaria);

        Map<String, Object> out = getPresupuestoPrecomprometidoPorIdClavePresupuestariaCall.execute(in);

        @SuppressWarnings("unchecked")
        List<PresupuestoPrecomprometidoDTO> resultados = (List<PresupuestoPrecomprometidoDTO>) out.get("p_resultado");

        return resultados;
    }
}
