package mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception;

public class PresupuestoDisponibleException extends RuntimeException {
    private PresupuestoDisponibleException(String mensaje) {
            super(mensaje);
        }

    public static mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoDisponibleException noHaySaldosPorMostrarEnEjercicio(Integer ejercicio) {
        return new mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoDisponibleException(
                "No existen saldos del presupuesto disponible del SAPFIN para el ejercicio " + ejercicio + " proporcionado."
        );
    }

    public static mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoDisponibleException noHaySaldosPorMostrar(Integer idCvePresupuestaria) {
        return new mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoDisponibleException(
                "La combinación presupuestal (ID: " + idCvePresupuestaria + ") existe en el SAPFIN, pero aún no cuenta con disponibilidad o calendario de saldos registrado."
        );
    }

    public static mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoDisponibleException multiplesClavesEncontradas() {
        return new mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoDisponibleException(
                "La combinación indicada presenta inconsistencias (más de un registro coincidente) en el SAPFIN. Por favor, repórtelo con el administrador del sistema."
        );
    }

    public static mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoDisponibleException multiplesSaldosEncontrados() {
        return new mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoDisponibleException(
                "La clave presupuestaria indicada presenta inconsistencias (más de un registro coincidente) en los saldos del presupuesto disponible del SAPFIN. Por favor, repórtelo con el administrador del sistema."
        );
    }

}
