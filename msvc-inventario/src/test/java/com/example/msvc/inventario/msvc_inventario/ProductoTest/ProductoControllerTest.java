package com.example.msvc.inventario.msvc_inventario.ProductoTest;


import com.example.msvc.inventario.msvc_inventario.DTO.ProductoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearProductoExitosamente() throws Exception {
        ProductoDTO dto = ProductoDTO.builder()
                .nombre("Perfume Test")
                .descripcion("Test")
                .stock(10)
                .precio(9990.0)
                .build();

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Perfume Test"));
    }

    @Test
    void crearProductoInvalido() throws Exception {
        ProductoDTO dto = ProductoDTO.builder()
                .nombre("")
                .descripcion("desc")
                .stock(-1)
                .precio(0.0)
                .build();

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarProductos() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerProductoNoExistente() throws Exception {
        mockMvc.perform(get("/api/productos/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarProductoNoExistente() throws Exception {
        mockMvc.perform(delete("/api/productos/9999"))
                .andExpect(status().isNotFound());
    }
}

