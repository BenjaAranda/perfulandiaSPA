package com.example.msvc.inventario.msvc_inventario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("Equipo Perfulandia");
        contact.setEmail("contacto@perfulandia.cl");

        return new OpenAPI()
                .info(new Info()
                        .title("API - MSVC Inventario")
                        .version("1.0.0")
                        .description("API para gestión de productos del inventario en Perfulandia")
                        .contact(contact)
                        .summary("Microservicio de inventario"));
    }
}
