package mx.gob.senado.tesoreria.precompromisos.shared.exceptions;

public class ClavePresupuestariaException extends RuntimeException {

    // Constructor privado para obligar a usar los métodos de fábrica
    private ClavePresupuestariaException(String mensaje) {
        super(mensaje);
    }

    // 1. Método para cuando la combinación (Filtro) no arroja resultados
    public static ClavePresupuestariaException combinacionNoEncontrada() {
        return new ClavePresupuestariaException(
                "La combinación seleccionada no está registrada en el Clasificador Presupuestal para este ejercicio."
        );
    }

    // 2. Método para cuando buscan un ID directamente y no existe
    public static ClavePresupuestariaException idNoEncontrado(Integer idCvePresupuestaria) {
        return new ClavePresupuestariaException(
                "No se encontró información de saldos para la Clave Presupuestaria ID: " + idCvePresupuestaria
        );
    }

    // 3. Puedes agregar más a futuro...
    public static ClavePresupuestariaException claveInactiva() {
        return new ClavePresupuestariaException(
                "La clave presupuestaria se encuentra inactiva o dada de baja."
        );
    }

    public static ClavePresupuestariaException noHaySaldosPorMostrar(Integer idCvePresupuestaria) {
        return new ClavePresupuestariaException(
                "La combinación presupuestal (ID: " + idCvePresupuestaria + ") existe, pero aún no cuenta con disponibilidad o calendario de saldos registrado."
        );
    }

    public static ClavePresupuestariaException multiplesClavesEncontradas() {
        return new ClavePresupuestariaException(
                "La combinación indicada presenta inconsistencias (más de un registro coincidente). Por favor, repórtelo con el administrador del sistema."
        );
    }

    public static ClavePresupuestariaException multiplesSaldosEncontrados() {
        return new ClavePresupuestariaException(
                "La clave presupuestaria indicada presenta inconsistencias (más de un registro coincidente) en los saldos. Por favor, repórtelo con el administrador del sistema."
        );
    }
}
