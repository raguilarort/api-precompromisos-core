package mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception;

public class PresupuestoPrecomprometidoException extends RuntimeException {
    private PresupuestoPrecomprometidoException(String mensaje) {
        super(mensaje);
    }

    public static mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoPrecomprometidoException noHaySaldosPorMostrarEnEjercicio(Integer ejercicio) {
        return new mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoPrecomprometidoException(
                "No existen saldos del presupuesto precomprometido para el ejercicio " + ejercicio + " proporcionado."
        );
    }

    public static mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoPrecomprometidoException noHaySaldosPorMostrar(Integer idCvePresupuestaria) {
        return new mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoPrecomprometidoException(
                "La combinación presupuestal (ID: " + idCvePresupuestaria + ") existe en SAPFIN-Precompromisos, pero aún no cuenta con disponibilidad o calendario de saldos registrado."
        );
    }

    public static mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoPrecomprometidoException multiplesClavesEncontradas() {
        return new mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoPrecomprometidoException(
                "La combinación indicada presenta inconsistencias (más de un registro coincidente) en SAPFIN-Precompromisos. Por favor, repórtelo con el administrador del sistema."
        );
    }

    public static mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoPrecomprometidoException multiplesSaldosEncontrados() {
        return new mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.exception.PresupuestoPrecomprometidoException(
                "La clave presupuestaria indicada presenta inconsistencias (más de un registro coincidente) en los saldos del presupuesto precomprometido de SAPFIN-Precompromisos. Por favor, repórtelo con el administrador del sistema."
        );
    }
}
