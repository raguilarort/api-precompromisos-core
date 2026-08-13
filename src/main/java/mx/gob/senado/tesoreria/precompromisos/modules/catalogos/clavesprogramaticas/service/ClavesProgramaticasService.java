package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.clavesprogramaticas.service;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.clavesprogramaticas.dto.ClaveProgramaticaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.clavesprogramaticas.repository.ClavesProgramaticasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClavesProgramaticasService {
    private final ClavesProgramaticasRepository repository;

    public ClavesProgramaticasService(ClavesProgramaticasRepository repository) {
        this.repository = repository;
    }

    /**
     * Procesa la consulta de claves programáticas. Si la unidad no es proporcionada, devuelve todas las del ejercicio.
     * @param ejercicio filtro de ejercicio
     * @param unidad filtro de unidad
     * @return Devuelve un listado de claves programáticas que coincidan con los parámetros definidos
     */
    public List<ClaveProgramaticaDTO> obtenerClavesProgramaticas(Integer ejercicio, String unidad) {
        if (ejercicio == null) {
            throw new IllegalArgumentException("El ejercicio presupuestal es obligatorio para la consulta.");
        }

        if (unidad == null || unidad.trim().isEmpty()) {
            return repository.listarClavesPorEjercicio(ejercicio);
        }

        // 3. Retorna el catálogo filtrado (Para la cascada de la clave presupuestaria)
        return repository.listarClavesPorUnidadYEjercicio(unidad, ejercicio);
    }
}