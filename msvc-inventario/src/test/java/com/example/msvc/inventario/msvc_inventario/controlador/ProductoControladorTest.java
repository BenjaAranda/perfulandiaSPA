package com.example.msvc.inventario.msvc_inventario.controlador;

import com.example.msvc.inventario.msvc_inventario.DTO.ProductoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControladorTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarProductos_OK() throws Exception {
        String json = mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DocumentContext ctx = JsonPath.parse(json);
        JSONArray ids = ctx.read("$..id");
        assertThat(ids).isNotNull();
    }

    @Test
    void obtenerProductoNoExistente_NOT_FOUND() throws Exception {
        mockMvc.perform(get("/api/productos/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    void crearProductoInvalido_BAD_REQUEST() throws Exception {
        ProductoDTO dto = ProductoDTO.builder()
                .nombre("") .stock(-1).precio(-5.0).build();

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
