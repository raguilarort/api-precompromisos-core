package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.clavesprogramaticas.dto;

import java.time.LocalDate;

public record ClaveProgramaticaDTO(
        Long idClaveProgramatica,
        String claveProgramatica,
        String descripcion,
        LocalDate fechaVigIni,
        LocalDate fechaVigFin
) {}
