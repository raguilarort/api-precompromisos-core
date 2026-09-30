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
) {}
