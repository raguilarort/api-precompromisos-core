package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

public class CambioEstatusRequestDTO {
    private Integer idEstatusNuevo;
    private String comentarios;

    public Integer getIdEstatusNuevo() { return idEstatusNuevo; }
    public void setIdEstatusNuevo(Integer idEstatusNuevo) { this.idEstatusNuevo = idEstatusNuevo; }
    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}
