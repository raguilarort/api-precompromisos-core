package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.enums;

public enum EstatusPrecompromisoEnum {
    CAPTURADO(1, "CAPTURADO"),
    REVISADO(2, "REVISADO"),
    AUTORIZADO(3, "AUTORIZADO"),
    COMPROMETIDO(4, "COMPROMETIDO"),
    RECHAZADO(5, "RECHAZADO"),
    CANCELADO(6, "CANCELADO"),
    ELIMINADO(7, "ELIMINADO");

    private final Integer id;
    private final String descripcion;

    EstatusPrecompromisoEnum(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstatusPrecompromisoEnum fromId(Integer id) {
        for (EstatusPrecompromisoEnum estatus : values()) {
            if (estatus.getId().equals(id)) {
                return estatus;
            }
        }
        throw new IllegalArgumentException("ID de estatus de precompromiso no válido: " + id);
    }
}
