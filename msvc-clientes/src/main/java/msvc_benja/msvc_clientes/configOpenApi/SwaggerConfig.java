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
        Contact contacto = new Contact()
                .name("Benjamin Aranda")
                .email("benjamin.aranda@duocuc.cl");

        return new OpenAPI()
                .info(new Info()
                        .title("API - Microservicio Clientes")
                        .version("1.0.0")
                        .description("Este microservicio permite administrar los datos de clientes de la tienda de perfumes")
                        .contact(contacto)
                        .summary("API CRUD de clientes"));
    }
}
