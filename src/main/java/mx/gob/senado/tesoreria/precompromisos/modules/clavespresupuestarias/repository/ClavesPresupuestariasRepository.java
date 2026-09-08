package mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.ClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.DisponibilidadClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.shared.exceptions.ClavePresupuestariaException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class ClavesPresupuestariasRepository {
    private static final String paqueteCvePresupuestaria = "SAPFIN_PA.PKG_CLAVE_PRESUPUESTARIA";
    private static final String paqueteDisponibilidadCvePresupuestaria = "SAPFIN_PA.PKG_REPORTES_SP";

    private final SimpleJdbcCall getClavePresupuestariaPorIdCall;
    private final SimpleJdbcCall getClavePresupuestariaPorUEPPFFCall;
    private final SimpleJdbcCall getDisponibilidadClavePresupuestariaPorIdCall;

    public ClavesPresupuestariasRepository(DataSource dataSource) {
        this.getClavePresupuestariaPorIdCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paqueteCvePresupuestaria)
                .withProcedureName("SP_OBTENER_CP_POR_ID")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new ClavePresupuestariaDTO(
                                rs.getInt("CLAVE_PRESUPUESTARIA_ID"),
                                rs.getString("CLAVE_PRESUPUESTARIA"),
                                rs.getInt("ID_UNIDAD_ESTADO"),
                                rs.getString("UNIDAD_EJECUTORA"),
                                rs.getString("DESC_CORTA_UE"),
                                rs.getInt("ESTADO"),
                                rs.getString("DESC_ESTADO"),
                                rs.getString("AMBITO"),
                                rs.getString("DESC_AMBITO"),
                                rs.getInt("ID_CVE_PROG"),
                                rs.getString("CLAVE_PROGRAMATICA"),
                                rs.getString("DESC_CVE_PROG"),
                                rs.getString("FECHA_VIG_INI"),
                                rs.getString("FECHA_VIG_FIN"),
                                rs.getInt("ID_PARTIDA"),
                                rs.getString("PARTIDA"),
                                rs.getString("DESC_PARTIDA"),
                                rs.getInt("ID_FUENTE_FIN"),
                                rs.getString("DESC_FUENTE_FIN")
                        ))
                );

        this.getClavePresupuestariaPorUEPPFFCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paqueteCvePresupuestaria)
                .withProcedureName("SP_OBTENER_CP_POR_UEPPFF")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlParameter("p_unidad_ejecutora", Types.VARCHAR),
                        new SqlParameter("p_id_cve_prog", Types.NUMERIC),
                        new SqlParameter("p_id_partida", Types.NUMERIC),
                        new SqlParameter("p_id_fuente_fin", Types.NUMERIC),
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new ClavePresupuestariaDTO(
                                rs.getInt("CLAVE_PRESUPUESTARIA_ID"),
                                rs.getString("CLAVE_PRESUPUESTARIA"),
                                rs.getInt("ID_UNIDAD_ESTADO"),
                                rs.getString("UNIDAD_EJECUTORA"),
                                rs.getString("DESC_CORTA_UE"),
                                rs.getInt("ESTADO"),
                                rs.getString("DESC_ESTADO"),
                                rs.getString("AMBITO"),
                                rs.getString("DESC_AMBITO"),
                                rs.getInt("ID_CVE_PROG"),
                                rs.getString("CLAVE_PROGRAMATICA"),
                                rs.getString("DESC_CVE_PROG"),
                                rs.getString("FECHA_VIG_INI"),
                                rs.getString("FECHA_VIG_FIN"),
                                rs.getInt("ID_PARTIDA"),
                                rs.getString("PARTIDA"),
                                rs.getString("DESC_PARTIDA"),
                                rs.getInt("ID_FUENTE_FIN"),
                                rs.getString("DESC_FUENTE_FIN")
                        ))
                );

        this.getDisponibilidadClavePresupuestariaPorIdCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paqueteDisponibilidadCvePresupuestaria)
                .withProcedureName("SP_GET_SP_ACT_CAL_PD")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlParameter("p_id_cve_presupuestaria", Types.NUMERIC),
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new DisponibilidadClavePresupuestariaDTO(
                                rs.getInt("CLAVE_PRESUPUESTARIA_ID"),
                                rs.getDouble("PD1"),
                                rs.getDouble("PD2"),
                                rs.getDouble("PD3"),
                                rs.getDouble("PD4"),
                                rs.getDouble("PD5"),
                                rs.getDouble("PD6"),
                                rs.getDouble("PD7"),
                                rs.getDouble("PD8"),
                                rs.getDouble("PD9"),
                                rs.getDouble("PD10"),
                                rs.getDouble("PD11"),
                                rs.getDouble("PD12"),
                                rs.getDouble("TPD")
                        ))
                );
    }

    /**
     * Devuelve la información de una clave presupuestaria si es que existe.
     */
    public ClavePresupuestariaDTO consultarClavePresupuestaria(Integer idClavePresupuestaria) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id_cve_presup", idClavePresupuestaria);

        Map<String, Object> out = getClavePresupuestariaPorIdCall.execute(in);
        return (ClavePresupuestariaDTO) out.get("p_resultado");
    }

    /**
     * Devuelve la información de una clave presupuestaria si es que existe mediante la combinación recibida.
     */
    @SuppressWarnings("unchecked")
    public List<ClavePresupuestariaDTO> buscarClavePresupuestaria(Integer ejercicio, String unidadEjecutora, Integer idCveProg, Integer idPartida, Integer idFuenteFin) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio)
                .addValue("p_unidad_ejecutora", unidadEjecutora)
                .addValue("p_id_cve_prog", idCveProg)
                .addValue("p_id_partida", idPartida)
                .addValue("p_id_fuente_fin", idFuenteFin);

        Map<String, Object> out = getClavePresupuestariaPorUEPPFFCall.execute(in);

        return (List<ClavePresupuestariaDTO>) out.get("p_resultado");
    }

    /**
     * Devuelve el saldo disonible de la clave presupuestaria proporcionada.
     */
    @SuppressWarnings("unchecked")
    public List<DisponibilidadClavePresupuestariaDTO> consultarDisponibilidad(Integer ejercicio, Integer idClavePresupuestaria) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio)
                .addValue("p_id_cve_presupuestaria", idClavePresupuestaria);

        Map<String, Object> out = getDisponibilidadClavePresupuestariaPorIdCall.execute(in);
        return (List<DisponibilidadClavePresupuestariaDTO>) out.get("p_resultado");
    }
}
