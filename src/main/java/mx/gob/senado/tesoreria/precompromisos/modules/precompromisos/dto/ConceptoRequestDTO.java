package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConceptoRequestDTO(
        @NotBlank(message = "La descripción del concepto es obligatoria")
        String descripcion,

        @NotNull(message = "Falta el identificador de la clave presupuestaria")
        Integer idCvePresupuestaria,

        @NotNull @Min(0) Double importeEnero,
        @NotNull @Min(0) Double importeFebrero,
        @NotNull @Min(0) Double importeMarzo,
        @NotNull @Min(0) Double importeAbril,
        @NotNull @Min(0) Double importeMayo,
        @NotNull @Min(0) Double importeJunio,
        @NotNull @Min(0) Double importeJulio,
        @NotNull @Min(0) Double importeAgosto,
        @NotNull @Min(0) Double importeSeptiembre,
        @NotNull @Min(0) Double importeOctubre,
        @NotNull @Min(0) Double importeNoviembre,
        @NotNull @Min(0) Double importeDiciembre
) {}
