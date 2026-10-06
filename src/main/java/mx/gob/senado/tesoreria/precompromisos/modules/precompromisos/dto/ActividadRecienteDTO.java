package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

public record ActividadRecienteDTO(
        Integer idPrecompromiso,
        String folio,
        Integer idEstatus,
        String estatusDescripcion,
        String nombreServidorPublico,
        String fechaMovimiento
) {}
