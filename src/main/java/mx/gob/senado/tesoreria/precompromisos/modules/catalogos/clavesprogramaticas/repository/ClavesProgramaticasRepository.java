package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.clavesprogramaticas.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.clavesprogramaticas.dto.ClaveProgramaticaDTO;
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
public class ClavesProgramaticasRepository {
    private final SimpleJdbcCall getClavesProgramaticasPorEjercicioCall;
    private final SimpleJdbcCall getClavesProgramaticasPorEjercicioYUnidadCall;

    private final String paquete = "SAPFIN_PA.PKG_ESTRUCTURA_PROGRAMATICA";

    public ClavesProgramaticasRepository(DataSource dataSource) {

        // 1. Llamada para obtener TODAS las claves por ejercicio (Nivel 5)
        this.getClavesProgramaticasPorEjercicioCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_LISTAR_CLAVES_PRGRAMATICAS")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new ClaveProgramaticaDTO(
                                rs.getLong("id_cve_prog"),
                                rs.getString("clave_programatica"),
                                rs.getString("descripcion"),
                                rs.getDate("fecha_vig_ini") != null ? rs.getDate("fecha_vig_ini").toLocalDate() : null,
                                rs.getDate("fecha_vig_fin") != null ? rs.getDate("fecha_vig_fin").toLocalDate() : null
                        ))
                );

        // 2. Llamada para obtener las claves filtradas por Unidad y Ejercicio
        this.getClavesProgramaticasPorEjercicioYUnidadCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_LISTAR_CVES_PROG_POR_UNIDAD")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlParameter("p_unidad", Types.VARCHAR),
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new ClaveProgramaticaDTO(
                                rs.getLong("id_cve_prog"),
                                rs.getString("clave_programatica"),
                                rs.getString("descripcion"),
                                rs.getDate("fecha_vig_ini") != null ? rs.getDate("fecha_vig_ini").toLocalDate() : null,
                                rs.getDate("fecha_vig_fin") != null ? rs.getDate("fecha_vig_fin").toLocalDate() : null
                        ))
                );
    }

    /**
     * Devuelve todas las claves programáticas de un ejercicio específico.
     */
    public List<ClaveProgramaticaDTO> listarClavesPorEjercicio(Integer ejercicio) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio);

        Map<String, Object> out = getClavesProgramaticasPorEjercicioCall.execute(in);
        return (List<ClaveProgramaticaDTO>) out.get("p_resultado");
    }

    /**
     * Devuelve las claves programáticas permitidas para una unidad ejecutora específica en un ejercicio.
     */
    public List<ClaveProgramaticaDTO> listarClavesPorUnidadYEjercicio(String unidad, Integer ejercicio) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio)
                .addValue("p_unidad", unidad);

        Map<String, Object> out = getClavesProgramaticasPorEjercicioYUnidadCall.execute(in);
        return (List<ClaveProgramaticaDTO>) out.get("p_resultado");
    }
}