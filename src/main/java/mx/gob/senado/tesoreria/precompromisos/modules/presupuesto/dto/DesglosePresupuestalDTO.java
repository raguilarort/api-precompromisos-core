package mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto;

import java.math.BigDecimal;

public record DesglosePresupuestalDTO(
        Integer idCvePresupuestaria,
        // Enero
        BigDecimal grpEnero, BigDecimal precompEnero,
        // Febrero
        BigDecimal grpFebrero, BigDecimal precompFebrero,
        // Marzo
        BigDecimal grpMarzo, BigDecimal precompMarzo,
        // Abril
        BigDecimal grpAbril, BigDecimal precompAbril,
        // Mayo
        BigDecimal grpMayo, BigDecimal precompMayo,
        // Junio
        BigDecimal grpJunio, BigDecimal precompJunio,
        // Julio
        BigDecimal grpJulio, BigDecimal precompJulio,
        // Agosto
        BigDecimal grpAgosto, BigDecimal precompAgosto,
        // Septiembre
        BigDecimal grpSeptiembre, BigDecimal precompSeptiembre,
        // Octubre
        BigDecimal grpOctubre, BigDecimal precompOctubre,
        // Noviembre
        BigDecimal grpNoviembre, BigDecimal precompNoviembre,
        // Diciembre
        BigDecimal grpDiciembre, BigDecimal precompDiciembre
) {
    public BigDecimal obtenerTotalDisponible() {
        return grpEnero.add(grpFebrero).add(grpMarzo).add(grpAbril)
                .add(grpMayo).add(grpJunio).add(grpJulio).add(grpAgosto)
                .add(grpSeptiembre).add(grpOctubre).add(grpNoviembre).add(grpDiciembre);
    }

    public BigDecimal obtenerTotalPrecomprometido() {
        return precompEnero.add(precompFebrero).add(precompMarzo).add(precompAbril)
                .add(precompMayo).add(precompJunio).add(precompJulio).add(precompAgosto)
                .add(precompSeptiembre).add(precompOctubre).add(precompNoviembre).add(precompDiciembre);
    }

    public BigDecimal obtenerTotalNeto() {
        return obtenerTotalDisponible().subtract(obtenerTotalPrecomprometido());
    }
}
