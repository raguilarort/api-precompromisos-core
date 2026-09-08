package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.requerimientos.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.requerimientos.dto.TipoRequerimientoDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TiposRequerimientoRepository {
    private final JdbcTemplate getCatalogoTiposRequerimientoCall;

    public TiposRequerimientoRepository(DataSource dataSource) {
        this.getCatalogoTiposRequerimientoCall = new JdbcTemplate(dataSource);
    }

    public List<TipoRequerimientoDTO> getCatalogoTiposRequerimiento() {
        String sql = "SELECT ID_TIPO_REQUERIMIENTO, NOMBRE FROM RF_TC_TIPO_REQUERIMIENTO";

        return getCatalogoTiposRequerimientoCall.query(sql, (rs, rowNum) -> new TipoRequerimientoDTO(
                rs.getInt("ID_TIPO_REQUERIMIENTO"),
                rs.getString("NOMBRE")
        ));
    }
}
