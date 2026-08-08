package mx.gob.senado.tesoreria.precompromisos.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // 1. Registramos el módulo para soportar las nuevas clases de fecha de Java (Instant, LocalDate)
        mapper.registerModule(new JavaTimeModule());

        // 2. Apagamos la escritura de fechas como números (timestamps) para que se vean en formato ISO-8601 legible
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}
