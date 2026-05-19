package mx.gob.senado.tesoreria.precompromisos.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// Esta anotación le enseña a Swagger cómo es nuestro esquema de seguridad (JWT)
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Core de Precompromisos")
                        .version("1.0")
                        .description("Motor transaccional para la gestión presupuestal"))
                // Esto aplica el candado de seguridad a TODOS los endpoints en la UI
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
    }
}
