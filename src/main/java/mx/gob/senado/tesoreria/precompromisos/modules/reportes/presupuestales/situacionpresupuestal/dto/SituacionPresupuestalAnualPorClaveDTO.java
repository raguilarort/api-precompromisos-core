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
        BigDecimal totalNeto,

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
        BigDecimal netoEnero, BigDecimal netoFebrero, BigDecimal netoMarzo,
        BigDecimal netoAbril, BigDecimal netoMayo, BigDecimal netoJunio,
        BigDecimal netoJulio, BigDecimal netoAgosto, BigDecimal netoSeptiembre,
        BigDecimal netoOctubre, BigDecimal netoNoviembre, BigDecimal netoDiciembre
) {}
