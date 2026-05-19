package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.dto;

import java.time.LocalDateTime;

public class ClavePresupuestariaCatalogDTO {
    private Long clavePresupuestariaId;
    private String claveConcatenada; // Ej: "04K25-31904-1"
    private String descripcion;
    private Integer activa; // 1 = Habilitada, 0 = Deshabilitada
    private LocalDateTime fechaActivacion;
    private String numEmpleadoDgpp;

    // Getters y Setters
    public Long getClavePresupuestariaId() { return clavePresupuestariaId; }
    public void setClavePresupuestariaId(Long clavePresupuestariaId) { this.clavePresupuestariaId = clavePresupuestariaId; }
    public String getClaveConcatenada() { return claveConcatenada; }
    public void setClaveConcatenada(String claveConcatenada) { this.claveConcatenada = claveConcatenada; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getActiva() { return activa; }
    public void setActiva(Integer activa) { this.activa = activa; }
    public LocalDateTime getFechaActivacion() { return fechaActivacion; }
    public void setFechaActivacion(LocalDateTime fechaActivacion) { this.fechaActivacion = fechaActivacion; }
    public String getNumEmpleadoDgpp() { return numEmpleadoDgpp; }
    public void setNumEmpleadoDgpp(String numEmpleadoDgpp) { this.numEmpleadoDgpp = numEmpleadoDgpp; }
}
