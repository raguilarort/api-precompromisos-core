package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

public record PrecompromisoResumeDTO(
        Integer idPrecompromiso, String folio, Integer ejercicio,
        String unidad,
        Integer idEstatus,
        String estatus,
        String numeroRequisicion,
        String tipoContratacion,
        String tipoRequerimiento,
        Integer cantidadConceptos,
        Double importeTotal) {
}