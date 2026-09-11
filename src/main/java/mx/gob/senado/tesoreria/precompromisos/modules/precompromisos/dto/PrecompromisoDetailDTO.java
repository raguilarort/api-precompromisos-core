package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

import java.util.List;

public record PrecompromisoDetailDTO(
        Integer idPrecompromiso, String folio, Integer ejercicio, String unidad, Integer idEstatus, String estatus, String numeroRequisicion,
        Integer idTipoContratacion, Integer idTipoRequerimiento, List<ConceptoDetailDTO> conceptos) {
}
