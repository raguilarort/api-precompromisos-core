package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto;

public class AdminQueriesDTOs {

    public record UsuarioResponseDTO(
            Long idUsuario, String correo, Long numEmpleado, Integer activo, String fechaAlta
    ) {}

    public record UsuarioRolResponseDTO(
            Long idAsignacion, Long idRol, String clave, String descripcion, Integer activo, String fechaAsignacion
    ) {}

    public record UsuarioUnidadResponseDTO(
            Long idAsignacion, String unidadEjecutora, Integer activo, String fechaAsignacion
    ) {}
}
