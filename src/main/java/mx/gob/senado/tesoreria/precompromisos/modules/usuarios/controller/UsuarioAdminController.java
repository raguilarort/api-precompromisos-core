package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.controller;

import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto.AdminRequestDTOs.*;
import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto.AdminResponseDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.service.UsuarioAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/usuarios")
@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')") // Blindaje estricto de Spring Security
public class UsuarioAdminController {

    private final UsuarioAdminService service;

    public UsuarioAdminController(UsuarioAdminService service) {
        this.service = service;
    }

    @PostMapping("/upsert")
    public ResponseEntity<AdminResponseDTO> upsertUsuario(@RequestBody UsuarioUpsertRequestDTO dto) {
        AdminResponseDTO response = service.upsertUsuario(dto);
        return construirRespuesta(response);
    }

    @PostMapping("/rol/asignar")
    public ResponseEntity<AdminResponseDTO> asignarRol(@RequestBody AsignarRolRequestDTO dto) {
        AdminResponseDTO response = service.asignarRol(dto);
        return construirRespuesta(response);
    }

    @PutMapping("/rol/revocar")
    public ResponseEntity<AdminResponseDTO> revocarRol(@RequestBody RevocarRequestDTO dto) {
        AdminResponseDTO response = service.revocarRol(dto);
        return construirRespuesta(response);
    }

    @PostMapping("/unidad/asignar")
    public ResponseEntity<AdminResponseDTO> asignarUnidad(@RequestBody AsignarUnidadRequestDTO dto) {
        // Extraemos el correo del administrador que está haciendo la petición desde el token activo
        String adminCorreo = SecurityContextHolder.getContext().getAuthentication().getName();

        AdminResponseDTO response = service.asignarUnidad(dto, adminCorreo);
        return construirRespuesta(response);
    }

    @PutMapping("/unidad/revocar")
    public ResponseEntity<AdminResponseDTO> revocarUnidad(@RequestBody RevocarRequestDTO dto) {
        AdminResponseDTO response = service.revocarUnidad(dto);
        return construirRespuesta(response);
    }

    // Helper para mapear el código de la BD a HTTP Status real
    private ResponseEntity<AdminResponseDTO> construirRespuesta(AdminResponseDTO response) {
        HttpStatus status = switch (response.estatus()) {
            case 200 -> HttpStatus.OK;
            case 409 -> HttpStatus.CONFLICT;
            case 403 -> HttpStatus.FORBIDDEN;
            case 404 -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        return ResponseEntity.status(status).body(response);
    }
}
