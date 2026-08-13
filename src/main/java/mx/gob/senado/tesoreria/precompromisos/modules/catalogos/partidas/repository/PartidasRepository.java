package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.dto.PartidaDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PartidasRepository {
    private final JdbcTemplate getCatalogoPartidasCall;

    public PartidasRepository(DataSource dataSource) {
        this.getCatalogoPartidasCall = new JdbcTemplate(dataSource);
    }

    public List<PartidaDTO> getCatalogoPartidas() {
        String sql = "SELECT ID_PARTIDA, PARTIDA, DESCRIPCION, VIGENCIA_INI, VIGENCIA_FIN, NIVEL FROM SAPFIN_PA.VW_COG_PARTIDAS_ESPECIFICAS";

        return getCatalogoPartidasCall.query(sql, (rs, rowNum) -> new PartidaDTO(
                rs.getInt("ID_PARTIDA"),
                rs.getString("PARTIDA"),
                rs.getString("DESCRIPCION"),
                rs.getDate("VIGENCIA_INI") != null ? rs.getDate("VIGENCIA_INI").toLocalDate() : null,
                rs.getDate("VIGENCIA_FIN") != null ? rs.getDate("VIGENCIA_FIN").toLocalDate() : null,
                rs.getInt("NIVEL")
        ));
    }
}
