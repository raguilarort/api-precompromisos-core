package mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto;

public record ClavePresupuestariaDTO(
        Integer clavePresupuestariaId,
        String clavePresupuestaria,
        Integer idUnidadEstado,
        String unidadEjecutora,
        String descCortaUe,
        Integer estado,
        String descEstado,
        String ambito,
        String descAmbito,
        Integer idCveProg,
        String claveProgramatica,
        String descCveProg,
        String fechaVigIni,
        String fechaVigFin,
        Integer idPartida,
        String partida,
        String descPartida,
        Integer idFuenteFin,
        String descFuenteFin
) {}
