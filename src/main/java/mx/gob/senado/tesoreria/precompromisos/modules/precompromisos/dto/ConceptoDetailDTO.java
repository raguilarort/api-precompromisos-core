package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

public record ConceptoDetailDTO(
        Integer idConcepto,
        String descripcion,
        Integer idClavePresupuestaria,
        Integer idClaveProgramatica,
        String claveProgramatica,
        String descClaveProgramatica,
        Integer idPartidaEspecifica,
        String partidaEspecifica,
        String descPartidaEspecifica,
        Integer idFuenteFinanciamiento,
        String descFuenteFinanciamiento,
        Double importeEnero,
        Double importeFebrero,
        Double importeMarzo,
        Double importeAbril,
        Double importeMayo,
        Double importeJunio,
        Double importeJulio,
        Double importeAgosto,
        Double importeSeptiembre,
        Double importeOctubre,
        Double importeNoviembre,
        Double importeDiciembre
) {}
