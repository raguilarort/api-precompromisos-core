package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.service;

import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto.AdminQueriesDTOs.*;
import mx.gob.senado.tesoreria.precompromisos.modules.usuarios.repository.UsuarioConsultasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioConsultasService {

    private final UsuarioConsultasRepository repository;

    public UsuarioConsultasService(UsuarioConsultasRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return repository.getUsuarios();
    }

    public List<UsuarioRolResponseDTO> listarRolesPorUsuario(Long idUsuario) {
        return repository.getRolesUsuario(idUsuario);
    }

    public List<UsuarioUnidadResponseDTO> listarUnidadesPorUsuario(Long idUsuario) {
        return repository.getUnidadesUsuario(idUsuario);
    }

    public List<RolCatalogoDTO> obtenerCatalogoRoles() {
        return repository.getCatalogoRoles();
    }
}
