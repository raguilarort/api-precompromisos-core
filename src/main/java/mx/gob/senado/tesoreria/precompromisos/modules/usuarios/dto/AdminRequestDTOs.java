package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto;

public class AdminRequestDTOs {

    // Para Alta o Modificación de Usuario
    public record UsuarioUpsertRequestDTO(
            Long idUsuario, // Será null para Altas, y traerá valor para Actualizaciones
            String correo,
            Long numEmpleado,
            Integer activo
    ) {}

    // Para Asignar un Rol del Catálogo
    public record AsignarRolRequestDTO(
            Long idUsuario,
            Long idRol
    ) {}

    // Para Asignar una Unidad Ejecutora
    public record AsignarUnidadRequestDTO(
            Long idUsuario,
            String unidad
    ) {}

    // Para Revocar (Funciona tanto para Rol como para Unidad ya que ambas usan idAsignacion)
    public record RevocarRequestDTO(
            Long idAsignacion
    ) {}
}
