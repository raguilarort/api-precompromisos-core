package mx.gob.senado.tesoreria.precompromisos.modules.reportes.presupuestales.situacionpresupuestal.exception;

public class ReporteSituacionPresupuestalException extends RuntimeException {
    public ReporteSituacionPresupuestalException(String message) {
        super(message);
    }

    public static ReporteSituacionPresupuestalException falloCargaPresupuestoDisponibleSAPFIN(Integer ejercicio) {
        return new ReporteSituacionPresupuestalException(
                String.format("No es posible generar el reporte: No fue posible obtener el presupuesto disponible del SAPFIN para el ejercicio %d.", ejercicio)
        );
    }
}
