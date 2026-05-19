package mx.gob.senado.tesoreria.precompromisos.modules.usuarios.dto;

import java.util.List;

public class UsuarioLoginDTO {
    private Long idUsuario;
    private String numEmpleado;
    private String email;
    private Integer esDgpp;
    private Integer activo;
    private String rol;
    private List<String> unidadesPermitidas;

    // Genera los Getters y Setters tradicionales en tu IDE

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNumEmpleado() { return numEmpleado; }
    public void setNumEmpleado(String numEmpleado) { this.numEmpleado = numEmpleado; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getEsDgpp() { return esDgpp; }
    public void setEsDgpp(Integer esDgpp) { this.esDgpp = esDgpp; }
    public Integer getActivo() { return activo; }
    public void setActivo(Integer activo) { this.activo = activo; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public List<String> getUnidadesPermitidas() { return unidadesPermitidas; }
    public void setUnidadesPermitidas(List<String> unidadesPermitidas) { this.unidadesPermitidas = unidadesPermitidas; }
}
