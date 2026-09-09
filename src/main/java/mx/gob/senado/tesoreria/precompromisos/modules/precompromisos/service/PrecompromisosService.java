package mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.service;

import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.ConceptoRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.PrecompromisoRequestDTO;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.dto.ResultadoRegistroPrecompromiso;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.repository.PrecompromisosRepository;
import mx.gob.senado.tesoreria.precompromisos.modules.precompromisos.exception.PrecompromisoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrecompromisosService {

    private final PrecompromisosRepository repository;

    public PrecompromisosService(PrecompromisosRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public String registrar(PrecompromisoRequestDTO payload) {

        // 1. Guardar cabecera y atrapar el resultado de Oracle
        ResultadoRegistroPrecompromiso resultado = repository.registrarCabecera(
                payload.ejercicio(),
                String.valueOf(payload.unidad()),
                payload.numeroRequisicion(),
                payload.tipoContratacion(),
                payload.tipoRequerimiento()
        );

        // 2. Validar que la base de datos haya generado correctamente el ID
        if (resultado.idPrecompromiso() == null) {
            throw PrecompromisoException.errorGeneracionIdCabecera();
        }

        // 3. Iterar sobre payload.conceptos() e insertar el detalle
        for (ConceptoRequestDTO concepto : payload.conceptos()) {
            Integer idConcepto = repository.registrarConcepto(resultado.idPrecompromiso(), concepto);

            if (idConcepto == null) {
                throw PrecompromisoException.errorGeneracionIdConcepto();
            }
        }

        return resultado.folio();
    }

    @Transactional
    public void actualizar(Integer idPrecompromiso, PrecompromisoRequestDTO payload) {
        // 1. Verificar que el precompromiso exista y esté en un Estatus permitido para edición (Ej. "Capturado")

        // 2. Borrar o actualizar los conceptos anteriores

        // 3. Insertar los conceptos nuevos
    }

}
