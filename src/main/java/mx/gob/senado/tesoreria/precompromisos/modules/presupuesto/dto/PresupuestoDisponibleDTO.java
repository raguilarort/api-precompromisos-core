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

}