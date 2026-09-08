package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.dto.FuenteFinanciamientoDTO;
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
public class FuentesFinanciamientoRepository {
    private static final String paquete = "SAPFIN_PA.PKG_FUENTE_FINANCIAMIENTO";

    private final SimpleJdbcCall getFuentesFinanciamientoCall;
    private final SimpleJdbcCall getFuentesFinanciamientoPorEjercicioUnidadClaveProgramaticaPartidaCall;

    public FuentesFinanciamientoRepository(DataSource dataSource) {

        // 1. Llamada para obtener TODAS las claves por ejercicio (Nivel 5)
        this.getFuentesFinanciamientoCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_LISTAR_FF")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new FuenteFinanciamientoDTO(
                                rs.getInt("ID_FUENTE_FIN"),
                                rs.getString("DESCRIPCION")
                        ))
                );

        this.getFuentesFinanciamientoPorEjercicioUnidadClaveProgramaticaPartidaCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paquete)
                .withProcedureName("SP_LISTAR_FF_POR_PROG_PARTIDA")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlParameter("p_unidad_ejecutora", Types.VARCHAR),
                        new SqlParameter("p_id_cve_prog", Types.NUMERIC),
                        new SqlParameter("p_id_partida", Types.NUMERIC),
                        new SqlOutParameter("p_resultado", Types.REF_CURSOR, (rs, rowNum) -> new FuenteFinanciamientoDTO(
                                rs.getInt("ID_FUENTE_FIN"),
                                rs.getString("DESCRIPCION")
                        ))
                );
    }

    /**
     * Devuelve todas las fuentes de financiamiento disponibles en el catálogo.
     */
    public List<FuenteFinanciamientoDTO> listarFuentesFinanciamiento() {
        Map<String, Object> out = getFuentesFinanciamientoCall.execute();
        return (List<FuenteFinanciamientoDTO>) out.get("p_resultado");
    }

    /**
     * Devuelve todas las fuentes de financiamiento. Filtra por las relacionadas mediante ejercicio, unidad, programa y partida.
     */
    public List<FuenteFinanciamientoDTO> listarFuentesFinanciamiento(Integer ejercicio, String unidadEjecutora, Integer idCveProg, Integer idPartida) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio)
                .addValue("p_unidad_ejecutora", unidadEjecutora)
                .addValue("p_id_cve_prog", idCveProg)
                .addValue("p_id_partida", idPartida);

        Map<String, Object> out = getFuentesFinanciamientoPorEjercicioUnidadClaveProgramaticaPartidaCall.execute(in);
        return (List<FuenteFinanciamientoDTO>) out.get("p_resultado");
    }
}
