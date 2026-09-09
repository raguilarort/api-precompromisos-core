package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.exception;

public class PrecompromisoException extends RuntimeException {

    private PrecompromisoException(String message) {
        super(message);
    }

    public static PrecompromisoException errorGeneracionIdCabecera() {
        return new PrecompromisoException("Error crítico: El motor de base de datos no generó el folio para la cabecera del precompromiso.");
    }

    public static PrecompromisoException errorGeneracionIdConcepto() {
        return new PrecompromisoException("Error crítico: No se pudo registrar uno de los conceptos. La transacción ha sido revertida por seguridad.");
    }

    public static PrecompromisoException edicionNoPermitida(String estatusActual) {
        return new PrecompromisoException("No es posible modificar el precompromiso porque se encuentra en estatus: " + estatusActual);
    }
}