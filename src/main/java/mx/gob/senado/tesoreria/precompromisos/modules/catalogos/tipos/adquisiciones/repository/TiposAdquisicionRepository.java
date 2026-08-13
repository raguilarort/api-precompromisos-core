package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.adquisiciones.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.tipos.adquisiciones.dto.TipoAdquisicionDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TiposAdquisicionRepository {
    private final JdbcTemplate getCatalogoTiposAdquisicionCall;

    public TiposAdquisicionRepository(DataSource dataSource) {
        this.getCatalogoTiposAdquisicionCall = new JdbcTemplate(dataSource);
    }

    public List<TipoAdquisicionDTO> getCatalogoTiposAdquisicion() {
        String sql = "SELECT ID_TIPO_ADQUISION, NOMBRE, REQUIERE_CONTRATO FROM SAPFIN_PA.RF_TC_TIPO_ADQUISICION WHERE ID_TIPO_ADQUISICION IN (0, 1, 2, 3, 5)";

        return getCatalogoTiposAdquisicionCall.query(sql, (rs, rowNum) -> new TipoAdquisicionDTO(
                rs.getInt("ID_TIPO_ADQUISION"),
                rs.getString("NOMBRE"),
                rs.getBoolean("REQUIERE_CONTRATO")
        ));
    }
}




