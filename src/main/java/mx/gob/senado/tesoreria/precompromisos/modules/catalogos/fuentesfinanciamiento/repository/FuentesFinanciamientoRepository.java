package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.dto.FuenteFinanciamientoDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FuentesFinanciamientoRepository {
    private final JdbcTemplate getCatalogoFuentesFinanciamientoCall;

    public FuentesFinanciamientoRepository(DataSource dataSource) {
        this.getCatalogoFuentesFinanciamientoCall = new JdbcTemplate(dataSource);
    }

    public List<FuenteFinanciamientoDTO> getCatalogoFuentesFinanciamiento() {
        String sql = "SELECT ID_FUENTE_FIN, DESCRIPCION FROM SAPFIN_PA.RF_TC_FUENTE_FINANCIAMIENTO";

        return getCatalogoFuentesFinanciamientoCall.query(sql, (rs,rowNum) -> new FuenteFinanciamientoDTO(
                rs.getInt("ID_FUENTE_FIN"),
                rs.getString("DESCRIPCION")
        ));
    }
}
