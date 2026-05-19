package mx.gob.senado.tesoreria.precompromisos.modules.catalogos.service;

import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.dto.ClavePresupuestariaCatalogDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.catalogos.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    // Cualquier rol operativo del flujo puede leer las claves permitidas de su área
    @PreAuthorize("hasAnyRole('CAPTURISTA', 'REVISOR', 'AUTORIZADOR', 'ADMIN')")
    public List<ClavePresupuestariaCatalogDTO> getClavesActivas(String unidadEjecutora) {
        return catalogRepository.listarActivasPorUnidad(unidadEjecutora);
    }

    // Regla estricta: Solo el rol estratégico de la DGPP o el Administrador puede ver el mapeo total y bitácora de control
    @PreAuthorize("hasAnyRole('AUTORIZADOR', 'ADMIN')")
    public List<ClavePresupuestariaCatalogDTO> getClavesConfiguracion(String unidadEjecutora) {
        return catalogRepository.listarUniversoPorUnidad(unidadEjecutora);
    }
}
