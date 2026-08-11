package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.service;

import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto.AdminRequestDTOs.*;
import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto.AdminResponseDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.repository.UsuarioAdminRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UsuarioAdminService {

    private final UsuarioAdminRepository repository;

    public UsuarioAdminService(UsuarioAdminRepository repository) {
        this.repository = repository;
    }

    public AdminResponseDTO upsertUsuario(UsuarioUpsertRequestDTO dto) {
        Map<String, Object> out = repository.upsertUsuario(dto.idUsuario(), dto.correo(), dto.numEmpleado(), dto.activo());
        return procesarRespuesta(out, "p_id_usuario");
    }

    public AdminResponseDTO asignarRol(AsignarRolRequestDTO dto) {
        Map<String, Object> out = repository.asignarRol(dto.idUsuario(), dto.idRol());
        return procesarRespuesta(out, null);
    }

    public AdminResponseDTO revocarRol(RevocarRequestDTO dto) {
        Map<String, Object> out = repository.revocarRol(dto.idAsignacion());
        return procesarRespuesta(out, null);
    }

    public AdminResponseDTO asignarUnidad(AsignarUnidadRequestDTO dto, String adminCorreo) {
        Map<String, Object> out = repository.asignarUnidad(dto.idUsuario(), dto.unidad(), adminCorreo);
        return procesarRespuesta(out, null);
    }

    public AdminResponseDTO revocarUnidad(RevocarRequestDTO dto) {
        Map<String, Object> out = repository.revocarUnidad(dto.idAsignacion());
        return procesarRespuesta(out, null);
    }

    // Método genérico para extraer los parámetros de salida
    private AdminResponseDTO procesarRespuesta(Map<String, Object> out, String paramId) {
        Number estatus = (Number) out.getOrDefault("P_ESTATUS", out.get("p_estatus"));
        String mensaje = (String) out.getOrDefault("P_MENSAJE", out.get("p_mensaje"));

        Long idGenerado = null;
        if (paramId != null) {
            Number idOut = (Number) out.getOrDefault(paramId.toUpperCase(), out.get(paramId));
            if (idOut != null) {
                idGenerado = idOut.longValue();
            }
        }

        return new AdminResponseDTO(
                estatus != null ? estatus.intValue() : 500,
                mensaje != null ? mensaje : "Error desconocido en BD",
                idGenerado
        );
    }
}
