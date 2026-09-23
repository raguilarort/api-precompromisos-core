package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

public record SeguimientoOperativoDTO(
        Long idSeguimiento,
        Integer idEstatus,
        String estatusDescripcion,
        String tipoMovimiento,
        Long numEmpleado,
        String nombreServidorPublico,
        String unidad,
        String fechaMovimiento,
        String observaciones
) {
}
