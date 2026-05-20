package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.ClavePresupuestariaRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.DisponibilidadDTO;
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
import java.util.List;
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

    public Integer obtenerEstatusActual(Long idPrecompromiso) {
        String sql = "SELECT id_estatus FROM RF_TR_PRECOMPROMISOS WHERE id_precompromiso = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, idPrecompromiso);
    }

    public List<ClavePresupuestariaRequestDTO> obtenerPartidasDePrecompromiso(Long idPrecompromiso) {
        String sql = """
            SELECT clave_presupuestaria_id, importe_ene, importe_feb, importe_mar, importe_abr, 
                   importe_may, importe_jun, importe_jul, importe_ago, importe_sep, importe_oct, importe_nov, importe_dic 
            FROM RF_TR_PRECOMP_CVE_PRESUP WHERE id_precompromiso = ?
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ClavePresupuestariaRequestDTO dto = new ClavePresupuestariaRequestDTO();
            dto.setClavePresupuestariaId(rs.getLong("clave_presupuestaria_id"));
            dto.setImporteEne(rs.getDouble("importe_ene"));
            // ... Mapea el resto de los meses ...
            return dto;
        }, idPrecompromiso);
    }

    public void actualizarEstatus(Long idPrecompromiso, Integer idEstatusNuevo, String numEmpleado, String comentarios) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PRECOMP_PKG")
                .withProcedureName("pr_procesar_estatus")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_id_precompromiso", Types.NUMERIC),
                        new SqlParameter("p_id_estatus_nuevo", Types.NUMERIC),
                        new SqlParameter("p_num_empleado", Types.VARCHAR),
                        new SqlParameter("p_comentarios", Types.VARCHAR)
                );

        call.execute(new MapSqlParameterSource()
                .addValue("p_id_precompromiso", idPrecompromiso)
                .addValue("p_id_estatus_nuevo", idEstatusNuevo)
                .addValue("p_num_empleado", numEmpleado)
                .addValue("p_comentarios", comentarios));
    }

    // SIMULACIÓN: Este método apuntará a la vista o tabla del SIP donde vive el saldo real.
    public DisponibilidadDTO obtenerDisponibilidadPorClave(Long clavePresupuestariaId) {
        // En un escenario real, harías un SELECT a tu vista de saldos.
        // Por ahora, simularemos que siempre hay saldo suficiente (10 millones) para poder probar el flujo.
        DisponibilidadDTO disp = new DisponibilidadDTO();
        disp.setClavePresupuestariaId(clavePresupuestariaId);
        disp.setDisponibleEne(10000000.0);
        disp.setDisponibleFeb(10000000.0);
        disp.setDisponibleMar(10000000.0);
        disp.setDisponibleAbr(10000000.0);
        disp.setDisponibleMay(10000000.0);
        disp.setDisponibleJun(10000000.0);
        disp.setDisponibleJul(10000000.0);
        disp.setDisponibleAgo(10000000.0);
        disp.setDisponibleSep(10000000.0);
        disp.setDisponibleOct(10000000.0);
        disp.setDisponibleNov(10000000.0);
        disp.setDisponibleDic(10000000.0);


        return disp;
    }
}
