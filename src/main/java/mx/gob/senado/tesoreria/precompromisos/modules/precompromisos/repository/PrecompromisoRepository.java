package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.ClavePresupuestariaRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Map;

@Repository
public class PrecompromisoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public Long crearPrecompromisoCompleto(PrecompromisoRequestDTO request, String numEmpleado) {

        // 1. Guardar la Cabecera
        SimpleJdbcCall callCabecera = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PRECOMP_PKG")
                .withProcedureName("pr_crear_precompromiso")
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlParameter("p_unidad_ejecutora", Types.VARCHAR),
                        new SqlParameter("p_no_orden_servicio", Types.VARCHAR),
                        new SqlParameter("p_num_empleado", Types.VARCHAR),
                        new SqlOutParameter("p_id_precompromiso_out", Types.NUMERIC)
                );

        MapSqlParameterSource inCabecera = new MapSqlParameterSource()
                .addValue("p_ejercicio", request.getEjercicio())
                .addValue("p_unidad_ejecutora", request.getUnidadEjecutora())
                .addValue("p_no_orden_servicio", request.getNoOrdenServicio())
                .addValue("p_num_empleado", numEmpleado);

        Map<String, Object> outCabecera = callCabecera.execute(inCabecera);

        // Oracle devuelve objetos BigDecimal o Integer dependiendo del driver, casteamos a Number
        Number idGenerado = (Number) outCabecera.get("p_id_precompromiso_out");
        Long idPrecompromiso = idGenerado.longValue();

        // 2. Guardar las Partidas iterando la lista
        if (request.getPartidas() != null && !request.getPartidas().isEmpty()) {
            SimpleJdbcCall callPartida = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("PRECOMP_PKG")
                    .withProcedureName("pr_upsert_partida");

            for (ClavePresupuestariaRequestDTO clavePrespuestaria : request.getPartidas()) {
                MapSqlParameterSource inPartida = new MapSqlParameterSource()
                        .addValue("p_id_precompromiso", idPrecompromiso)
                        .addValue("p_cve_id", clavePrespuestaria.getClavePresupuestariaId())
                        .addValue("p_ene", clavePrespuestaria.getImporteEne())
                        .addValue("p_feb", clavePrespuestaria.getImporteFeb())
                        .addValue("p_mar", clavePrespuestaria.getImporteMar())
                        .addValue("p_abr", clavePrespuestaria.getImporteAbr())
                        .addValue("p_may", clavePrespuestaria.getImporteMay())
                        .addValue("p_jun", clavePrespuestaria.getImporteJun())
                        .addValue("p_jul", clavePrespuestaria.getImporteJul())
                        .addValue("p_ago", clavePrespuestaria.getImporteAgo())
                        .addValue("p_sep", clavePrespuestaria.getImporteSep())
                        .addValue("p_oct", clavePrespuestaria.getImporteOct())
                        .addValue("p_nov", clavePrespuestaria.getImporteNov())
                        .addValue("p_dic", clavePrespuestaria.getImporteDic());

                callPartida.execute(inPartida);
            }
        }

        return idPrecompromiso;
    }
}
