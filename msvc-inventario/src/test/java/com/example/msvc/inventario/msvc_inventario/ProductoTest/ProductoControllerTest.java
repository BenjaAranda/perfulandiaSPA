package com.example.msvc.inventario.msvc_inventario.ProductoTest;

import com.example.msvc.inventario.msvc_inventario.DTO.ProductoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void crearProductoTest() throws Exception {
        ProductoDTO producto = ProductoDTO.builder()
                .nombre("Perfume Test")
                .descripcion("Descripción")
                .stock(20)
                .precio(15000.0)
                .build();

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Perfume Test"));
    }
}
