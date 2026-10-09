package mx.gob.senado.tesoreria.precompromisos.modules.reportes.presupuestales.situacionpresupuestal.dto;

import java.math.BigDecimal;

public record SituacionPresupuestalAnualPorClaveDTO(
        Integer idCvePresupuestaria,
        String clavePresupuestariaFormateada,
        String descUnidad,
        String descPrograma,
        String descPartida,
        String descFuente,

        // Totales Anuales
        BigDecimal totalDisponibleSAPFIN,
        BigDecimal totalPrecomprometido,
        BigDecimal totalDisponibleNeto,

        // Meses GRP
        BigDecimal disponibleSAPFINEnero, BigDecimal disponibleSAPFINFebrero, BigDecimal disponibleSAPFINMarzo,
        BigDecimal disponibleSAPFINAbril, BigDecimal disponibleSAPFINMayo, BigDecimal disponibleSAPFINJunio,
        BigDecimal disponibleSAPFINJulio, BigDecimal disponibleSAPFINAgosto, BigDecimal disponibleSAPFINSeptiembre,
        BigDecimal disponibleSAPFINOctubre, BigDecimal disponibleSAPFINNoviembre, BigDecimal disponibleSAPFINDiciembre,

        // Meses Precomprometido
        BigDecimal precomprometidoEnero, BigDecimal precomprometidoFebrero, BigDecimal precomprometidoMarzo,
        BigDecimal precomprometidoAbril, BigDecimal precomprometidoMayo, BigDecimal precomprometidoJunio,
        BigDecimal precomprometidoJulio, BigDecimal precomprometidoAgosto, BigDecimal precomprometidoSeptiembre,
        BigDecimal precomprometidoOctubre, BigDecimal precomprometidoNoviembre, BigDecimal precomprometidoDiciembre,

        // Meses Neto
        BigDecimal disponibleNetoEnero, BigDecimal disponibleNetoFebrero, BigDecimal disponibleNetoMarzo,
        BigDecimal disponibleNetoAbril, BigDecimal disponibleNetoMayo, BigDecimal disponibleNetoJunio,
        BigDecimal disponibleNetoJulio, BigDecimal disponibleNetoAgosto, BigDecimal disponibleNetoSeptiembre,
        BigDecimal disponibleNetoOctubre, BigDecimal disponibleNetoNoviembre, BigDecimal disponibleNetoDiciembre
) {}
