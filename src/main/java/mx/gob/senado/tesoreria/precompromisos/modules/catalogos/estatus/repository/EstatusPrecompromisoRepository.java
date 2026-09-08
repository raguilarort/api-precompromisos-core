package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.estatus.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.estatus.dto.EstatusPrecompromisoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.adquisiciones.dto.TipoAdquisicionDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EstatusPrecompromisoRepository {
    private final JdbcTemplate getCatalogoEstatusPrecompromisoCall;

    public EstatusPrecompromisoRepository(DataSource dataSource) {
        this.getCatalogoEstatusPrecompromisoCall = new JdbcTemplate(dataSource);
    }

    public List<EstatusPrecompromisoDTO> getCatalogoEstatusPrecompromisos() {
        String sql = "SELECT ID_ESTATUS, DESCRIPCION FROM SAPFIN_PRECOMPROMISOS.RF_TC_ESTATUS_PRECOMP";

        return getCatalogoEstatusPrecompromisoCall.query(sql, (rs, rowNum) -> new EstatusPrecompromisoDTO(
                rs.getInt("ID_ESTATUS"),
                rs.getString("DESCRIPCION")
        ));
    }
}
