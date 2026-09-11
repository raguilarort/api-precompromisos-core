package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.*;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class PrecompromisosRepository {

    private static final String paqueteAdmin = "PKG_PRECOMPROMISO_ADMIN";
    private static final String paqueteConsulta = "PKG_PRECOMPROMISO_QRY";

    private final SimpleJdbcCall registrarPrecompromisoCall;
    private final SimpleJdbcCall registrarPrecompromisoConceptoCall;
    private final SimpleJdbcCall consultarPrecompromsisoPorIdCall;
    private final SimpleJdbcCall consultarPrecompromisosPorEjercicioCall;

    public PrecompromisosRepository(DataSource dataSource) {

        this.registrarPrecompromisoCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paqueteAdmin)
                .withProcedureName("SP_REGISTRAR_PRECOMPROMISO")
                .withoutProcedureColumnMetaDataAccess() // Optimización para Oracle
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlParameter("p_unidad_ejecutora", Types.VARCHAR),
                        new SqlParameter("p_no_orden_servicio", Types.VARCHAR),
                        new SqlParameter("p_id_tipo_contratacion", Types.NUMERIC),
                        new SqlParameter("p_id_tipo_requerimiento", Types.NUMERIC),

                        new SqlOutParameter("p_id_precompromiso", Types.NUMERIC),
                        new SqlOutParameter("p_folio", Types.VARCHAR)
                );

        this.registrarPrecompromisoConceptoCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paqueteAdmin)
                .withProcedureName("SP_REGISTRAR_CONCEPTO")
                .withoutProcedureColumnMetaDataAccess() // Optimización para Oracle
                .declareParameters(
                        new SqlParameter("p_id_precompromiso", Types.NUMERIC),
                        new SqlParameter("p_id_cve_presupuestaria", Types.NUMERIC),
                        new SqlParameter("p_descripcion", Types.VARCHAR),
                        new SqlParameter("p_importe_enero", Types.NUMERIC),
                        new SqlParameter("p_importe_febrero", Types.NUMERIC),
                        new SqlParameter("p_importe_marzo", Types.NUMERIC),
                        new SqlParameter("p_importe_abril", Types.NUMERIC),
                        new SqlParameter("p_importe_mayo", Types.NUMERIC),
                        new SqlParameter("p_importe_junio", Types.NUMERIC),
                        new SqlParameter("p_importe_julio", Types.NUMERIC),
                        new SqlParameter("p_importe_agosto", Types.NUMERIC),
                        new SqlParameter("p_importe_septiembre", Types.NUMERIC),
                        new SqlParameter("p_importe_octubre", Types.NUMERIC),
                        new SqlParameter("p_importe_noviembre", Types.NUMERIC),
                        new SqlParameter("p_importe_diciembre", Types.NUMERIC),

                        new SqlOutParameter("p_id_concepto", Types.NUMERIC)
                );

        this.consultarPrecompromsisoPorIdCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paqueteConsulta)
                .withProcedureName("SP_CONSULTAR_POR_ID")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                    new SqlParameter("p_id_precompromiso", Types.NUMERIC),
                    new SqlParameter("p_id_usuario", Types.NUMERIC),

                    // Mapeo del Primer Cursor (Cabecera)
                    new SqlOutParameter("p_cursor_cabecera", Types.REF_CURSOR, (rs, rowNum) -> new PrecompromisoDetailDTO(
                            rs.getInt("id_precompromiso"),
                            rs.getString("folio"),
                            rs.getInt("ejercicio"),
                            rs.getString("unidad_ejecutora"),
                            rs.getInt("id_estatus"),
                            rs.getString("estatus"),
                            rs.getString("no_orden_servicio"),
                            rs.getInt("id_tipo_contratacion"),
                            rs.getInt("id_tipo_requerimiento"),
                            null
                    )),

                    new SqlOutParameter("p_cursor_conceptos", Types.REF_CURSOR, (rs, rowNum) -> new ConceptoDetailDTO(
                            rs.getInt("id_concepto"),
                            rs.getString("descripcion"),
                            rs.getInt("id_clave_presupuestaria"),
                            rs.getInt("id_clave_programatica"),
                            rs.getInt("id_partida_especifica"),
                            rs.getInt("id_fuente_financiamiento"),
                            rs.getDouble("importe_enero"), rs.getDouble("importe_febrero"), rs.getDouble("importe_marzo"), rs.getDouble("importe_abril"),
                            rs.getDouble("importe_mayo"), rs.getDouble("importe_junio"), rs.getDouble("importe_julio"), rs.getDouble("importe_agosto"),
                            rs.getDouble("importe_septiembre"), rs.getDouble("importe_octubre"), rs.getDouble("importe_noviembre"), rs.getDouble("importe_diciembre")
                    ))
                );

        this.consultarPrecompromisosPorEjercicioCall = new SimpleJdbcCall(dataSource)
                .withCatalogName(paqueteConsulta)
                .withProcedureName("SP_CONSULTAR_POR_EJERCICIO")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_ejercicio", Types.NUMERIC),
                        new SqlParameter("p_id_usuario", Types.NUMERIC),
                        new SqlOutParameter("p_cursor", Types.REF_CURSOR, (rs, rowNum) -> new PrecompromisoResumeDTO(
                                rs.getInt("id_precompromiso"),
                                rs.getString("folio"),
                                rs.getInt("ejercicio"),
                                rs.getString("unidad_ejecutora"),
                                rs.getString("estatus"),
                                rs.getString("numero_requisicion"),
                                rs.getString("tipo_contratacion"),
                                rs.getString("tipo_requerimiento"),
                                rs.getInt("cantidad_conceptos"),
                                rs.getDouble("importe_total")
                        ))
                );
    }

    /**
     * Registra la cabecera del precompromiso
     */
    public ResultadoRegistroPrecompromiso registrarCabecera(Integer ejercicio, String unidadEjecutora, String noOrdenServicio, Integer idTipoContratacion, Integer idTipoRequerimiento) {

        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio)
                .addValue("p_unidad_ejecutora", unidadEjecutora)
                .addValue("p_no_orden_servicio", noOrdenServicio)
                .addValue("p_id_tipo_contratacion", idTipoContratacion)
                .addValue("p_id_tipo_requerimiento", idTipoRequerimiento);

        Map<String, Object> out = registrarPrecompromisoCall.execute(in);

        BigDecimal idPrecompromisoBd = (BigDecimal) out.get("p_id_precompromiso");
        String folioGenerado = (String) out.get("p_folio");

        return new ResultadoRegistroPrecompromiso(
                idPrecompromisoBd != null ? idPrecompromisoBd.intValue() : null,
                folioGenerado
        );
    }

    public Integer registrarConcepto(Integer idPrecompromiso, ConceptoRequestDTO concepto) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id_precompromiso", idPrecompromiso)
                .addValue("p_id_cve_presupuestaria", concepto.idCvePresupuestaria())
                .addValue("p_descripcion", concepto.descripcion())
                .addValue("p_importe_enero", concepto.importeEnero())
                .addValue("p_importe_febrero", concepto.importeFebrero())
                .addValue("p_importe_marzo", concepto.importeMarzo())
                .addValue("p_importe_abril", concepto.importeAbril())
                .addValue("p_importe_mayo", concepto.importeMayo())
                .addValue("p_importe_junio", concepto.importeJunio())
                .addValue("p_importe_julio", concepto.importeJulio())
                .addValue("p_importe_agosto", concepto.importeAgosto())
                .addValue("p_importe_septiembre", concepto.importeSeptiembre())
                .addValue("p_importe_octubre", concepto.importeOctubre())
                .addValue("p_importe_noviembre", concepto.importeNoviembre())
                .addValue("p_importe_diciembre", concepto.importeDiciembre());

        Map<String, Object> out = registrarPrecompromisoConceptoCall.execute(in);

        BigDecimal idConceptoBd = (BigDecimal) out.get("p_id_concepto");

        return idConceptoBd != null ? idConceptoBd.intValue() : null;
    }

    public PrecompromisoDetailDTO consultarPorId(Integer idPrecompromiso, Integer idUsuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_id_precompromiso", idPrecompromiso)
                .addValue("p_id_usuario", idUsuario);

        Map<String, Object> out = consultarPrecompromsisoPorIdCall.execute(in);

        List<PrecompromisoDetailDTO> precompromisosCabecera = (List<PrecompromisoDetailDTO>) out.get("p_cursor_cabecera");
        List<ConceptoDetailDTO> precompromisoConceptos = (List<ConceptoDetailDTO>) out.get("p_cursor_conceptos");

        if (precompromisosCabecera == null || precompromisosCabecera.isEmpty()) {
            return null;
        }

        PrecompromisoDetailDTO precompromisoCabecera = precompromisosCabecera.getFirst();

        return new PrecompromisoDetailDTO(
                precompromisoCabecera.idPrecompromiso(),
                precompromisoCabecera.folio(),
                precompromisoCabecera.ejercicio(),
                precompromisoCabecera.unidad(),
                precompromisoCabecera.idEstatus(),
                precompromisoCabecera.estatus(),
                precompromisoCabecera.numeroRequisicion(),
                precompromisoCabecera.idTipoContratacion(),
                precompromisoCabecera.idTipoRequerimiento(),
                precompromisoConceptos
            );
    }

    public List<PrecompromisoResumeDTO> consultarPorEjercicio(Integer ejercicio, Integer idUsuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_ejercicio", ejercicio)
                .addValue("p_id_usuario", idUsuario);

        Map<String, Object> out = consultarPrecompromisosPorEjercicioCall.execute(in);
        return (List<PrecompromisoResumeDTO>) out.get("p_cursor");
    }
}
