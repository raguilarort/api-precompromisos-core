package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.roles.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.roles.dto.RolDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.roles.service.RolesService;

@RestController
@RequestMapping("/catalogos/roles")
@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
public class RolesController {
    private final RolesService rolesService;

    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<RolDTO>> obtenerCatalogoRoles() {
        return ResponseEntity.ok(rolesService.obtenerCatalogoRoles());
    }
}
