package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.roles.service;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.roles.dto.RolDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.roles.repository.RolesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {
    private final RolesRepository repository;

    public RolesService(RolesRepository repository) {
        this.repository = repository;
    }

    public List<RolDTO> obtenerCatalogoRoles() {
        return repository.getCatalogoRoles();
    }
}
