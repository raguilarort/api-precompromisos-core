package mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto.PresupuestoDisponibleDTO;
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
public class PresupuestoSAPFINRepository {
    private static final String paquete = "SAPFIN_PA.PKG_REPORTES_SP";

    private final SimpleJdbcCall getPresupuestoDisponibleCall;
    private final SimpleJdbcCall getPresupuestoDisponiblePorIdClavePresupuestariaCall;

    public PresupuestoSAPFINRepository(DataSource dataSource) {

        this.getPresupuestoDisponibleCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_GET_SP_ACT_CAL_PD")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new PresupuestoDisponibleDTO(
                                rs.getInt("CLAVE_PRESUPUESTARIA_ID"),
                                rs.getBigDecimal("PD1"),
                                rs.getBigDecimal("PD2"),
                                rs.getBigDecimal("PD3"),
                                rs.getBigDecimal("PD4"),
                                rs.getBigDecimal("PD5"),
                                rs.getBigDecimal("PD6"),
                                rs.getBigDecimal("PD7"),
                                rs.getBigDecimal("PD8"),
                                rs.getBigDecimal("PD9"),
                                rs.getBigDecimal("PD10"),
                                rs.getBigDecimal("PD11"),
                                rs.getBigDecimal("PD12")
                            )
                        )
                );

        this.getPresupuestoDisponiblePorIdClavePresupuestariaCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_GET_SP_ACT_CAL_PD")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlParameter("p_id_cve_presupuestaria", Types.NUMERIC),
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new PresupuestoDisponibleDTO(
                                rs.getInt("CLAVE_PRESUPUESTARIA_ID"),
                                rs.getBigDecimal("PD1"),
                                rs.getBigDecimal("PD2"),
                                rs.getBigDecimal("PD3"),
                                rs.getBigDecimal("PD4"),
                                rs.getBigDecimal("PD5"),
                                rs.getBigDecimal("PD6"),
                                rs.getBigDecimal("PD7"),
                                rs.getBigDecimal("PD8"),
                                rs.getBigDecimal("PD9"),
                                rs.getBigDecimal("PD10"),
                                rs.getBigDecimal("PD11"),
                                rs.getBigDecimal("PD12")
                            )
                        )
                );
    }

    public List<PresupuestoDisponibleDTO> consultarDisponibilidad(Integer ejercicio) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio);

        Map<String, Object> out = getPresupuestoDisponibleCall.execute(in);

        @SuppressWarnings("unchecked")
        List<PresupuestoDisponibleDTO> resultados =  (List<PresupuestoDisponibleDTO>) out.get("p_resultado");

        return resultados;
    }

    public List<PresupuestoDisponibleDTO> consultarDisponibilidad(Integer ejercicio, Integer idClavePresupuestaria) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio)
                .addValue("p_id_cve_presupuestaria", idClavePresupuestaria);

        Map<String, Object> out = getPresupuestoDisponiblePorIdClavePresupuestariaCall.execute(in);

        @SuppressWarnings("unchecked")
        List<PresupuestoDisponibleDTO> resultados =  (List<PresupuestoDisponibleDTO>) out.get("p_resultado");

        return resultados;
    }
}
