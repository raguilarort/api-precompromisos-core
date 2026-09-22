package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConceptoRequestDTO(
        Integer idConcepto,

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
) {
        public Double obtenerTotal() {
                return (importeEnero != null ? importeEnero : 0) + (importeFebrero != null ? importeFebrero : 0) +
                        (importeMarzo != null ? importeMarzo : 0) + (importeAbril != null ? importeAbril : 0) +
                        (importeMayo != null ? importeMayo : 0) + (importeJunio != null ? importeJunio : 0) +
                        (importeJulio != null ? importeJulio : 0) + (importeAgosto != null ? importeAgosto : 0) +
                        (importeSeptiembre != null ? importeSeptiembre : 0) + (importeOctubre != null ? importeOctubre : 0) +
                        (importeNoviembre != null ? importeNoviembre : 0) + (importeDiciembre != null ? importeDiciembre : 0);
        }
}
