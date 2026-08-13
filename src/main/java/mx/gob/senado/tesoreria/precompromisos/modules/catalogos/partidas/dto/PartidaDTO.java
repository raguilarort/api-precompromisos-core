package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.partidas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record PartidaDTO(
        int idPartida,
        String partida,
        String descripcion,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate vigencia_ini,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate vigencia_fin,
        int nivel) {
}
