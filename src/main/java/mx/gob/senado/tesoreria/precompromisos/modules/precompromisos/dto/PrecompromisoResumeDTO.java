package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

public record PrecompromisoResumeDTO(Integer idPrecompromiso, String folio, Integer ejercicio, String unidad, String estatus, String numeroRequisicion, String tipoContratacion, String tipoRequerimiento, Integer cantidadConceptos, Double importeTotal) {
}