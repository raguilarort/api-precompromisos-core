package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.dto.ClavePresupuestariaCatalogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CatalogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Consulta utilizada por la pantalla de captura y revisión.
     * Muestra únicamente las claves presupuestarias vigentes y habilitadas por la DGPP para esa Unidad.
     */
    public List<ClavePresupuestariaCatalogDTO> listarActivasPorUnidad(String unidadEjecutora) {
        String sql = """
            SELECT cp.clave_presupuestaria_id,
                   (prog.clave_programatica || '-' || part.partida || '-' || cp.id_fuente_fin) AS clave_concatenada,
                   prog.descripcion AS descripcion,
                   conf.activa,
                   conf.fecha_activacion,
                   conf.num_empleado_dgpp
            FROM RF_TR_CONF_CLAVES_UNIDAD conf
            INNER JOIN RF_TC_CLAVES_PRESUPUESTARIAS cp ON conf.clave_presupuestaria_id = cp.clave_presupuestaria_id
            INNER JOIN RF_TR_REL_UEA_CVEPROG rel ON cp.id_rel_uea_cve_prog = rel.id_rel_uea_cve_prog
            INNER JOIN RF_TR_CLAVES_PROGRAMATICAS prog ON rel.id_cve_prog = prog.id_cve_prog
            INNER JOIN rf_tc_partidas_presupuestales part ON cp.id_partida = part.id_partida
            WHERE conf.unidad_ejecutora = ? 
              AND conf.activa = 1
            ORDER BY clave_concatenada ASC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ClavePresupuestariaCatalogDTO dto = new ClavePresupuestariaCatalogDTO();
            dto.setClavePresupuestariaId(rs.getLong("clave_presupuestaria_id"));
            dto.setClaveConcatenada(rs.getString("clave_concatenada"));
            dto.setDescripcion(rs.getString("descripcion"));
            dto.setActiva(rs.getInt("activa"));
            if (rs.getTimestamp("fecha_activacion") != null) {
                dto.setFechaActivacion(rs.getTimestamp("fecha_activacion").toLocalDateTime());
            }
            dto.setNumEmpleadoDgpp(rs.getString("num_empleado_dgpp"));
            return dto;
        }, unidadEjecutora);
    }

    /**
     * Consulta utilizada exclusivamente por el panel de administración de la DGPP.
     * Muestra todo el universo de claves de la unidad para permitir activarlas o desactivarlas.
     */
    public List<ClavePresupuestariaCatalogDTO> listarUniversoPorUnidad(String unidadEjecutora) {
        String sql = """
            SELECT cp.clave_presupuestaria_id,
                   (prog.clave_programatica || '-' || part.partida || '-' || cp.id_fuente_fin) AS clave_concatenada,
                   prog.descripcion AS descripcion,
                   NVL(conf.activa, 0) AS activa,
                   conf.fecha_activacion,
                   conf.num_empleado_dgpp
            FROM RF_TC_CLAVES_PRESUPUESTARIAS cp
            INNER JOIN RF_TR_REL_UEA_CVEPROG rel ON cp.id_rel_uea_cve_prog = rel.id_rel_uea_cve_prog
            INNER JOIN RF_TR_CLAVES_PROGRAMATICAS prog ON rel.id_cve_prog = prog.id_cve_prog
            INNER JOIN rf_tc_partidas_presupuestales part ON cp.id_partida = part.id_partida
            LEFT JOIN RF_TR_CONF_CLAVES_UNIDAD conf 
                   ON cp.clave_presupuestaria_id = conf.clave_presupuestaria_id 
                  AND conf.unidad_ejecutora = rel.unidad_ejecutora
            WHERE rel.unidad_ejecutora = ?
            ORDER BY clave_concatenada ASC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ClavePresupuestariaCatalogDTO dto = new ClavePresupuestariaCatalogDTO();
            dto.setClavePresupuestariaId(rs.getLong("clave_presupuestaria_id"));
            dto.setClaveConcatenada(rs.getString("clave_concatenada"));
            dto.setDescripcion(rs.getString("descripcion"));
            dto.setActiva(rs.getInt("activa"));
            if (rs.getTimestamp("fecha_activacion") != null) {
                dto.setFechaActivacion(rs.getTimestamp("fecha_activacion").toLocalDateTime());
            }
            dto.setNumEmpleadoDgpp(rs.getString("num_empleado_dgpp"));
            return dto;
        }, unidadEjecutora);
    }
}
