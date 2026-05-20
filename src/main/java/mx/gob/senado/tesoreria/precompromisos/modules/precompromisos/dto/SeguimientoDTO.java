package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

import java.time.LocalDateTime;

public class SeguimientoDTO {
    private Long idSeguimiento;
    private Integer idEstatus;
    private String estatusDesc;
    private String numEmpleado;
    private String email;
    private LocalDateTime fechaMovimiento;
    private String comentarios;

    // Getters y Setters
    public Long getIdSeguimiento() { return idSeguimiento; }
    public void setIdSeguimiento(Long idSeguimiento) { this.idSeguimiento = idSeguimiento; }
    public Integer getIdEstatus() { return idEstatus; }
    public void setIdEstatus(Integer idEstatus) { this.idEstatus = idEstatus; }
    public String getEstatusDesc() { return estatusDesc; }
    public void setEstatusDesc(String estatusDesc) { this.estatusDesc = estatusDesc; }
    public String getNumEmpleado() { return numEmpleado; }
    public void setNumEmpleado(String numEmpleado) { this.numEmpleado = numEmpleado; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getFechaMovimiento() { return fechaMovimiento; }
    public void setFechaMovimiento(LocalDateTime fechaMovimiento) { this.fechaMovimiento = fechaMovimiento; }
    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}
