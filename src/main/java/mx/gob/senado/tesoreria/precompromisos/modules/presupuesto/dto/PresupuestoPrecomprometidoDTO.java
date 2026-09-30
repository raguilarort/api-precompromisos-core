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
) {}
