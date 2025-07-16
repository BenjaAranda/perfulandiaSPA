package benja.msvc_ventas.ConfigSwagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiVentas() {
        Contact contacto = new Contact()
                .name("Benjamín Aranda DEV")
                .email("be.arandaa@duocuc.cl");

        return new OpenAPI()
                .info(new Info()
                        .title("API - MSVC Ventas")
                        .version("1.0.0")
                        .description("Microservicio para registrar y gestionar ventas en Perfulandia")
                        .contact(contacto)
                        .summary("API operaciones CRUD de ventas y conexión con Inventario"));
    }
}

