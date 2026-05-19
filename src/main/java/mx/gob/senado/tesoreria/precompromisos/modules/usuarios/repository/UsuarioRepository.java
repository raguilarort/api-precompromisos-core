package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.repository;

import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto.UsuarioLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class UsuarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UsuarioLoginDTO obtenerDatosLogin(String email) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PRECOMP_PKG") // Nombre del paquete en Oracle
                .withProcedureName("pr_get_usuario_login") // Nombre del SP
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_email", Types.VARCHAR),
                        // Mapeamos el primer cursor directamente a nuestro DTO
                        new SqlOutParameter("p_cur_usuario", Types.REF_CURSOR, BeanPropertyRowMapper.newInstance(UsuarioLoginDTO.class)),
                        // Mapeamos el segundo cursor (solo trae un String) a una lista nativa
                        new SqlOutParameter("p_cur_unidades", Types.REF_CURSOR, (rs, rowNum) -> rs.getString("unidad_ejecutora"))
                );

        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_email", email);

        // Ejecutamos la llamada a Oracle
        Map<String, Object> out = jdbcCall.execute(in);

        // Extraemos los resultados
        List<UsuarioLoginDTO> usuarios = (List<UsuarioLoginDTO>) out.get("p_cur_usuario");
        List<String> unidades = (List<String>) out.get("p_cur_unidades");

        if (usuarios != null && !usuarios.isEmpty()) {
            UsuarioLoginDTO usuario = usuarios.get(0);
            usuario.setUnidadesPermitidas(unidades);

            // Si es de DGPP, forzamos el rol autorizador para evitar errores de captura manual
            if (usuario.getEsDgpp() != null && usuario.getEsDgpp() == 1) {
                usuario.setRol("AUTORIZADOR");
            }

            return usuario;
        }

        // Si no existe o está inactivo en Oracle, retorna null
        return null;
    }
}
