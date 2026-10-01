package mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto;

import java.math.BigDecimal;

public record PresupuestoPrecomprometidoDTO(
        Integer idCvePresupuestaria,
        BigDecimal precompEnero, BigDecimal precompFebrero,
        BigDecimal precompMarzo, BigDecimal precompAbril,
        BigDecimal precompMayo, BigDecimal precompJunio,
        BigDecimal precompJulio, BigDecimal precompAgosto,
        BigDecimal precompSeptiembre, BigDecimal precompOctubre,
        BigDecimal precompNoviembre, BigDecimal precompDiciembre
) {
    public BigDecimal obtenerTotal() {
        return precompEnero.add(precompFebrero).add(precompMarzo).add(precompAbril)
                .add(precompMayo).add(precompJunio).add(precompJulio).add(precompAgosto)
                .add(precompSeptiembre).add(precompOctubre).add(precompNoviembre).add(precompDiciembre);
    }
}
