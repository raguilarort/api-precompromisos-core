package mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto;

import java.math.BigDecimal;

public record PresupuestoDisponibleDTO(
        Integer idCvePresupuestaria,
        BigDecimal disponibleEnero, BigDecimal disponibleFebrero,
        BigDecimal disponibleMarzo, BigDecimal disponibleAbril,
        BigDecimal disponibleMayo, BigDecimal disponibleJunio,
        BigDecimal disponibleJulio, BigDecimal disponibleAgosto,
        BigDecimal disponibleSeptiembre, BigDecimal disponibleOctubre,
        BigDecimal disponibleNoviembre, BigDecimal disponibleDiciembre
) {
    public BigDecimal obtenerTotal() {
        return disponibleEnero.add(disponibleFebrero).add(disponibleMarzo).add(disponibleAbril)
                .add(disponibleMayo).add(disponibleJunio).add(disponibleJulio).add(disponibleAgosto)
                .add(disponibleSeptiembre).add(disponibleOctubre).add(disponibleNoviembre).add(disponibleDiciembre);
    }
}