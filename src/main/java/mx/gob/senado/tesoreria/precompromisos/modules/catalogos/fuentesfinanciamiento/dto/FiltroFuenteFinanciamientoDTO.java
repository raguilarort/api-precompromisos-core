package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.fuentesfinanciamiento.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FiltroFuenteFinanciamientoDTO(
        @Parameter(description = "Año del ejercicio presupuestal", example = "2026")
        @NotNull(message = "El ejercicio fiscal es obligatorio")
        Integer ejercicio,

        @Parameter(description = "Clave de la unidad ejecutora (Ej. 101, 102).", example = "101")
        @NotBlank(message = "La clave de la unidad ejecutora es obligatoria")
        String unidad,

        @Parameter(description = "Identificador de la clave programática")
        @NotNull(message = "El identificador de la clave programática es obligatorio")
        Integer idCveProg,

        @Parameter(description = "Identificador de la partida")
        @NotNull(message = "El identificador de la partida es obligatorio")
        Integer idPartida
) {}
