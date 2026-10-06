package mx.gob.senado.tesoreria.precompromisos.modules.avisos.dto;

import java.time.LocalDateTime;

public record AvisoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        String prioridad
) {
}
