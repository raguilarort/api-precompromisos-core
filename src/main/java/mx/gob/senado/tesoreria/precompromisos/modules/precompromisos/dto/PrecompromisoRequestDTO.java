package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PrecompromisoRequestDTO(
        @NotNull(message = "El ejercicio fiscal es obligatorio")
        Integer ejercicio,

        @NotNull(message = "La unidad ejecutora es obligatoria")
        Integer unidad,

        @NotBlank(message = "El número de requisición es obligatorio")
        String numeroRequisicion,

        @NotNull(message = "El tipo de contratación es obligatorio")
        Integer tipoContratacion,

        @NotNull(message = "El tipo de requerimiento es obligatorio")
        Integer tipoRequerimiento,

        @NotEmpty(message = "Debe incluir al menos un concepto en el precompromiso")
        @Valid // Obliga a validar cada elemento dentro de la lista
        List<ConceptoRequestDTO> conceptos
) {}
