package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto;

public record AdminResponseDTO(
        Integer estatus,
        String mensaje,
        Long idGenerado
) {}
