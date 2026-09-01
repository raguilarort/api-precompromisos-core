package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.dto.PartidaDTO;
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
public class PartidasRepository {
    private static final String paquete = "SAPFIN_PA.PKG_COG";

    private final SimpleJdbcCall getPartidasEspecificasCall;
    private final SimpleJdbcCall getPartidasEspecificasPorEjercicioUnidadClaveProgramaticaCall;

    public PartidasRepository(DataSource dataSource) {

        // 1. Llamada para obtener TODAS las claves por ejercicio (Nivel 5)
        this.getPartidasEspecificasCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_LISTAR_PART_ESP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new PartidaDTO(
                                rs.getInt("id_partida"),
                                rs.getString("PARTIDA"),
                                rs.getString("descripcion"),
                                rs.getDate("vigencia_ini") != null ? rs.getDate("vigencia_ini").toLocalDate() : null,
                                rs.getDate("vigencia_fin") != null ? rs.getDate("vigencia_fin").toLocalDate() : null,
                                rs.getInt("nivel")
                        ))
                );

        // 2. Llamada para obtener las claves filtradas por Unidad y Ejercicio
        this.getPartidasEspecificasPorEjercicioUnidadClaveProgramaticaCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_LISTAR_PART_ESP_POR_PROG")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlParameter("p_unidad_ejecutora", Types.VARCHAR),
                        new SqlParameter("p_id_cve_prog", Types.NUMERIC),
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new PartidaDTO(
                                rs.getInt("id_partida"),
                                rs.getString("PARTIDA"),
                                rs.getString("descripcion"),
                                rs.getDate("vigencia_ini") != null ? rs.getDate("vigencia_ini").toLocalDate() : null,
                                rs.getDate("vigencia_fin") != null ? rs.getDate("vigencia_fin").toLocalDate() : null,
                                rs.getInt("nivel")
                        ))
                );
    }

    /**
     * Devuelve todas las partidas específicas del Clasificador por objeto del gasto.
     */
    public List<PartidaDTO> listarPartidasEspecificas() {
        Map<String, Object> out = getPartidasEspecificasCall.execute();
        return (List<PartidaDTO>) out.get("p_resultado");
    }

    /**
     * Devuelve todas las partidas específicas del Clasificador por objeto del gasto vinculadas a una Clave Programática. Filtra por ejercicio, unidad y clave.
     */
    public List<PartidaDTO> listarPartidasEspecificasPorEjercicioUnidadCveProg(Integer ejercicio, String unidadEjecutora, Integer idCveProg) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio)
                .addValue("p_unidad_ejecutora", unidadEjecutora)
                .addValue("p_id_cve_prog", idCveProg);

        Map<String, Object> out = getPartidasEspecificasPorEjercicioUnidadClaveProgramaticaCall.execute(in);
        return (List<PartidaDTO>) out.get("p_resultado");
    }

}
