package msvc_benja.msvc_clientes.configOpenApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - MSVC - Clientes")
                        .version("1.0.0")
                        .description("Este microservicio gestiona los clientes de Perfulandia.")
                        .contact(new Contact()
                                .name("Perfulandia Support")
                                .email("soporte@perfulandia.cl"))
                );
    }
}
