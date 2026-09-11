package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

public record ConceptoDetailDTO(
        Integer idConcepto,
        String descripcion,
        Integer idClaveProgramatica,
        Integer idPartidaEspecifica,
        Integer idFuenteFinanciamiento,
        Integer idClavePresupuestaria,
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
