package mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto;

public record DisponibilidadClavePresupuestariaDTO(
        Integer idCvePresupuestaria,
        Double disponibleEnero,
        Double disponibleFebrero,
        Double disponibleMarzo,
        Double disponibleAbril,
        Double disponibleMayo,
        Double disponibleJunio,
        Double disponibleJulio,
        Double disponibleAgosto,
        Double disponibleSeptiembre,
        Double disponibleOctubre,
        Double disponibleNoviembre,
        Double disponibleDiciembre
) {}
