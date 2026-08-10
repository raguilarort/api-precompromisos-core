package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.controller;

import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto.AdminQueriesDTOs.*;
import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto.AdminRequestDTOs.*;
import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto.AdminResponseDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.service.UsuarioAdminService;
import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.service.UsuarioConsultasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/usuarios")
@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')") // Blindaje estricto de Spring Security
public class UsuarioAdminController {

    private final UsuarioAdminService adminService;
    private final UsuarioConsultasService consultasService;

    public UsuarioAdminController(UsuarioAdminService adminService, UsuarioConsultasService consultasService) {
        this.adminService = adminService;
        this.consultasService = consultasService;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(consultasService.listarUsuarios());
    }

    @GetMapping("/{idUsuario}/roles")
    public ResponseEntity<List<UsuarioRolResponseDTO>> listarRolesUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(consultasService.listarRolesPorUsuario(idUsuario));
    }

    @GetMapping("/{idUsuario}/unidades")
    public ResponseEntity<List<UsuarioUnidadResponseDTO>> listarUnidadesUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(consultasService.listarUnidadesPorUsuario(idUsuario));
    }

    @PostMapping("/upsert")
    public ResponseEntity<AdminResponseDTO> upsertUsuario(@RequestBody UsuarioUpsertRequestDTO dto) {
        AdminResponseDTO response = adminService.upsertUsuario(dto);
        return construirRespuesta(response);
    }

    @PostMapping("/rol/asignar")
    public ResponseEntity<AdminResponseDTO> asignarRol(@RequestBody AsignarRolRequestDTO dto) {
        AdminResponseDTO response = adminService.asignarRol(dto);
        return construirRespuesta(response);
    }

    @PutMapping("/rol/revocar")
    public ResponseEntity<AdminResponseDTO> revocarRol(@RequestBody RevocarRequestDTO dto) {
        AdminResponseDTO response = adminService.revocarRol(dto);
        return construirRespuesta(response);
    }

    @PostMapping("/unidad/asignar")
    public ResponseEntity<AdminResponseDTO> asignarUnidad(@RequestBody AsignarUnidadRequestDTO dto) {
        // Extraemos el correo del administrador que está haciendo la petición desde el token activo
        String adminCorreo = SecurityContextHolder.getContext().getAuthentication().getName();

        AdminResponseDTO response = adminService.asignarUnidad(dto, adminCorreo);
        return construirRespuesta(response);
    }

    @PutMapping("/unidad/revocar")
    public ResponseEntity<AdminResponseDTO> revocarUnidad(@RequestBody RevocarRequestDTO dto) {
        AdminResponseDTO response = adminService.revocarUnidad(dto);
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
