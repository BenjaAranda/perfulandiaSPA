package msvc_perfulandia_benja.msvc_boleta.configOpenApi;

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
                        .title("API - MSVC Boleta")
                        .version("1.0.0")
                        .description("Microservicio para gestionar boletas en Perfulandia")
                        .contact(new Contact()
                                .name("Benjamin Aranda DEV")
                                .email("be.arandaa@duocuc.cl"))
                );
    }
}
