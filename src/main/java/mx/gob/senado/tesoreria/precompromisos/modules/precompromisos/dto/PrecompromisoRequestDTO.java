package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto;

import java.util.List;

public class PrecompromisoRequestDTO {
    private Integer ejercicio;
    private String unidadEjecutora;
    private String noOrdenServicio;
    private List<ClavePresupuestariaRequestDTO> partidas;

    // Genera Getters y Setters
    public Integer getEjercicio() { return ejercicio; }
    public void setEjercicio(Integer ejercicio) { this.ejercicio = ejercicio; }
    public String getUnidadEjecutora() { return unidadEjecutora; }
    public void setUnidadEjecutora(String unidadEjecutora) { this.unidadEjecutora = unidadEjecutora; }
    public String getNoOrdenServicio() { return noOrdenServicio; }
    public void setNoOrdenServicio(String noOrdenServicio) { this.noOrdenServicio = noOrdenServicio; }
    public List<ClavePresupuestariaRequestDTO> getPartidas() { return partidas; }
    public void setPartidas(List<ClavePresupuestariaRequestDTO> partidas) { this.partidas = partidas; }
}