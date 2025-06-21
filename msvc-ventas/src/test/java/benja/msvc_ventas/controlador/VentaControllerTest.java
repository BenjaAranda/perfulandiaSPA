package benja.msvc_ventas.controlador;

import benja.msvc_ventas.dto.VentaDTO;
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
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarVentas() throws Exception {
        String json = mockMvc.perform(get("/api/ventas"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DocumentContext context = JsonPath.parse(json);
        JSONArray ids = context.read("$..id");
        assertThat(ids).isNotNull();
    }

    @Test
    void obtenerVentaNoExistente() throws Exception {
        mockMvc.perform(get("/api/ventas/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    void crearVentaInvalida() throws Exception {
        VentaDTO venta = VentaDTO.builder()
                .productoId(null)
                .cantidad(0)
                .build();

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isBadRequest());
    }
}
