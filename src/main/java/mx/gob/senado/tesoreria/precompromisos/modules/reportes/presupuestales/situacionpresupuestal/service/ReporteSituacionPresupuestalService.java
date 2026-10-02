package mx.gob.senado.tesoreria.precompromisos.modules.reportes.presupuestales.situacionpresupuestal.service;

import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.dto.ClavePresupuestariaDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.clavespresupuestarias.service.ClavesPresupuestariasService;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto.PresupuestoDisponibleDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.dto.PresupuestoPrecomprometidoDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.repository.PresupuestoPrecomprometidoRepository;
import mx.gob.senado.tesoreria.precompromisos.modules.presupuesto.repository.PresupuestoSAPFINRepository;
import mx.gob.senado.tesoreria.precompromisos.modules.reportes.presupuestales.situacionpresupuestal.dto.SituacionPresupuestalAnualPorClaveDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.reportes.presupuestales.situacionpresupuestal.exception.ReporteSituacionPresupuestalException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteSituacionPresupuestalService {
    private final PresupuestoSAPFINRepository presupuestoSAPFINRepository;
    private final PresupuestoPrecomprometidoRepository presupuestoPrecomprometidoRepository;
    private final ClavesPresupuestariasService clavesPresupuestariasService;

    public ReporteSituacionPresupuestalService(
            PresupuestoSAPFINRepository presupuestoSAPFINRepository,
            PresupuestoPrecomprometidoRepository presupuestoPrecomprometidoRepository,
            ClavesPresupuestariasService clavesPresupuestariasService) {
        this.presupuestoSAPFINRepository = presupuestoSAPFINRepository;
        this.presupuestoPrecomprometidoRepository = presupuestoPrecomprometidoRepository;
        this.clavesPresupuestariasService = clavesPresupuestariasService;
    }

    public List<SituacionPresupuestalAnualPorClaveDTO> generarReporte(Integer ejercicio) {

        List<ClavePresupuestariaDTO> listaClavesPresupuestarias = clavesPresupuestariasService.listarClavesPresupuestarias(ejercicio);

        List<PresupuestoDisponibleDTO> listaPresupuestoDisponibleSAPFIN = presupuestoSAPFINRepository.consultarDisponibilidad(ejercicio);

        if (listaPresupuestoDisponibleSAPFIN == null || listaPresupuestoDisponibleSAPFIN.isEmpty()) {
            throw ReporteSituacionPresupuestalException.falloCargaPresupuestoDisponibleSAPFIN(ejercicio);
        }

        List<PresupuestoPrecomprometidoDTO> listaPrecomp = presupuestoPrecomprometidoRepository.consultarPrecomprometido(ejercicio);

        // 2. Convertimos Precompromisos a Mapa para búsquedas instantáneas O(1)
        Map<Integer, PresupuestoPrecomprometidoDTO> mapaPrecomp = listaPrecomp.stream()
                .collect(Collectors.toMap(PresupuestoPrecomprometidoDTO::idCvePresupuestaria, p -> p));

        Map<Integer, ClavePresupuestariaDTO> mapaCatalogo = listaClavesPresupuestarias.stream()
                .collect(Collectors.toMap(ClavePresupuestariaDTO::clavePresupuestariaId, c -> c));

        // 3. FUSIÓN Y CÁLCULO
        return listaPresupuestoDisponibleSAPFIN.stream().map(presupuestoDisponible -> {
            Integer idClave = presupuestoDisponible.idCvePresupuestaria();

            // Buscamos si tiene apartados. Si no, DTO con ceros.
            PresupuestoPrecomprometidoDTO presupuestoPrecomprometido = mapaPrecomp.getOrDefault(idClave,
                    new PresupuestoPrecomprometidoDTO(idClave,
                            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));

            // Buscamos sus descripciones en el catálogo. Si por alguna anomalía no existe, ponemos textos genéricos
            ClavePresupuestariaDTO catalogo = mapaCatalogo.get(idClave);
            String uText = catalogo != null ? catalogo.unidadEjecutora() + " - " + catalogo.descCortaUe() : "N/D";
            String pText = catalogo != null ? catalogo.claveProgramatica() + " - " + catalogo.descCveProg() : "N/D";
            String parText = catalogo != null ? catalogo.partida() + " - " + catalogo.descPartida() : "N/D";
            String fText = catalogo != null ? catalogo.idFuenteFin() + " - " + catalogo.descFuenteFin() : "N/D";

            // Creamos la clave formateada visual: Ej "04-04R01-21601-1"
            String claveFormateada = (catalogo != null) ?
                    catalogo.clavePresupuestaria() : "N/D";

            // --- CÁLCULO DE TOTALES ANUALES ---
            BigDecimal totalDisponible = presupuestoDisponible.obtenerTotal();
            BigDecimal totalPrecomprometido = presupuestoPrecomprometido.obtenerTotal();
            BigDecimal totalNeto = totalDisponible.subtract(totalPrecomprometido);

            // --- ENSAMBLE DEL DTO FINAL ---
            return new SituacionPresupuestalAnualPorClaveDTO(
                    idClave, claveFormateada,
                    uText, pText, parText, fText, // Se inyectan las descripciones del catálogo

                    totalDisponible, totalPrecomprometido, totalNeto,

                    // GRP Mes a Mes
                    presupuestoDisponible.disponibleEnero(), presupuestoDisponible.disponibleFebrero(), presupuestoDisponible.disponibleMarzo(),
                    presupuestoDisponible.disponibleAbril(), presupuestoDisponible.disponibleMayo(), presupuestoDisponible.disponibleJunio(),
                    presupuestoDisponible.disponibleJulio(), presupuestoDisponible.disponibleAgosto(), presupuestoDisponible.disponibleSeptiembre(),
                    presupuestoDisponible.disponibleOctubre(), presupuestoDisponible.disponibleNoviembre(), presupuestoDisponible.disponibleDiciembre(),

                    // Precompromisos Mes a Mes
                    presupuestoPrecomprometido.precompEnero(), presupuestoPrecomprometido.precompFebrero(), presupuestoPrecomprometido.precompMarzo(),
                    presupuestoPrecomprometido.precompAbril(), presupuestoPrecomprometido.precompMayo(), presupuestoPrecomprometido.precompJunio(),
                    presupuestoPrecomprometido.precompJulio(), presupuestoPrecomprometido.precompAgosto(), presupuestoPrecomprometido.precompSeptiembre(),
                    presupuestoPrecomprometido.precompOctubre(), presupuestoPrecomprometido.precompNoviembre(), presupuestoPrecomprometido.precompDiciembre(),

                    // Balance Neto Matemático (GRP - Precomp)
                    presupuestoDisponible.disponibleEnero().subtract(presupuestoPrecomprometido.precompEnero()),
                    presupuestoDisponible.disponibleFebrero().subtract(presupuestoPrecomprometido.precompFebrero()),
                    presupuestoDisponible.disponibleMarzo().subtract(presupuestoPrecomprometido.precompMarzo()),
                    presupuestoDisponible.disponibleAbril().subtract(presupuestoPrecomprometido.precompAbril()),
                    presupuestoDisponible.disponibleMayo().subtract(presupuestoPrecomprometido.precompMayo()),
                    presupuestoDisponible.disponibleJunio().subtract(presupuestoPrecomprometido.precompJunio()),
                    presupuestoDisponible.disponibleJulio().subtract(presupuestoPrecomprometido.precompJulio()),
                    presupuestoDisponible.disponibleAgosto().subtract(presupuestoPrecomprometido.precompAgosto()),
                    presupuestoDisponible.disponibleSeptiembre().subtract(presupuestoPrecomprometido.precompSeptiembre()),
                    presupuestoDisponible.disponibleOctubre().subtract(presupuestoPrecomprometido.precompOctubre()),
                    presupuestoDisponible.disponibleNoviembre().subtract(presupuestoPrecomprometido.precompNoviembre()),
                    presupuestoDisponible.disponibleDiciembre().subtract(presupuestoPrecomprometido.precompDiciembre())
            );
        }).collect(Collectors.toList());
    }
}
