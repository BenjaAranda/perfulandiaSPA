package msvc_perfulandia_benja.msvc_boleta.servicio;


import msvc_perfulandia_benja.msvc_boleta.dto.BoletaDTO;
import msvc_perfulandia_benja.msvc_boleta.dto.ItemBoletaDTO;
import msvc_perfulandia_benja.msvc_boleta.excepciones.RecursoNoEncontradoException;
import msvc_perfulandia_benja.msvc_boleta.modelo.Boleta;
import msvc_perfulandia_benja.msvc_boleta.modelo.BoletaDetalle;
import msvc_perfulandia_benja.msvc_boleta.repositorio.BoletaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BoletaServicioTest {

    @Mock
    private BoletaRepositorio boletaRepositorio;

    @InjectMocks
    private BoletaServicioImpl boletaServicio;

    private Boleta boletaEjemplo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        BoletaDetalle item = BoletaDetalle.builder()
                .productoId(1L)
                .cantidad(2)
                .precioUnitario(5000.0)
                .build();

        boletaEjemplo = Boleta.builder()
                .id(1L)
                .clienteId(100L)
                .fecha(LocalDateTime.now())
                .items(List.of(item))
                .total(10000.0)
                .build();

        item.setBoleta(boletaEjemplo);
    }


    @Test
    @DisplayName("Debe guardar una boleta correctamente")
    void testGuardarBoleta() {
        when(boletaRepositorio.save(any(Boleta.class))).thenReturn(boletaEjemplo);

        BoletaDTO dto = new BoletaDTO();
        dto.setClienteId(100L);
        ItemBoletaDTO itemDTO = new ItemBoletaDTO(1L,"Perfume A",2,5000.0,10000.0);
        dto.setItems(List.of(itemDTO));

        BoletaDTO result = boletaServicio.guardar(dto);

        assertThat(result.getTotal()).isEqualTo(10000.0);
        assertThat(result.getItems()).hasSize(1);
        verify(boletaRepositorio, times(1)).save(any(Boleta.class));
    }

    @Test
    @DisplayName("Debe lanzar error si la boleta no existe")
    void testBuscarBoletaNoExiste() {
        when(boletaRepositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boletaServicio.obtenerPorId(99L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Boleta no encontrada");
    }

    @Test
    @DisplayName("Debe eliminar una boleta existente")
    void testEliminarBoleta() {
        // Mostramos el ID para verificar
        System.out.println("ID de boletaEjemplo en test: " + boletaEjemplo.getId());

        when(boletaRepositorio.findById(1L)).thenReturn(Optional.of(boletaEjemplo));
        doNothing().when(boletaRepositorio).deleteById(1L);

        boletaServicio.eliminar(1L);

        verify(boletaRepositorio, times(1)).findById(1L); // Asegura que el mock se llama
        verify(boletaRepositorio, times(1)).deleteById(1L);
    }


    @Test
    @DisplayName("Debe lanzar excepción al eliminar boleta inexistente")
    void testEliminarBoletaInexistente() {
        when(boletaRepositorio.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boletaServicio.eliminar(2L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Boleta no encontrada");
    }

    @Test
    @DisplayName("Debe calcular total correctamente")
    void testCalculoTotal() {
        BoletaDetalle item1 = new BoletaDetalle(null, 1L, 2, 3000.0, null);
        BoletaDetalle item2 = new BoletaDetalle(null, 2L, 1, 4000.0, null);

        List<BoletaDetalle> items = List.of(item1, item2);

        double total = items.stream().mapToDouble(i -> i.getCantidad() * i.getPrecioUnitario()).sum();

        assertThat(total).isEqualTo(10000.0);
    }

    @Test
    @DisplayName("Debe listar todas las boletas")
    void testListarBoletas() {
        when(boletaRepositorio.findAll()).thenReturn(List.of(boletaEjemplo));

        List<BoletaDTO> result = boletaServicio.listar();

        assertThat(result).hasSize(1);
        verify(boletaRepositorio).findAll();
    }

    @Test
    @DisplayName("Debe encontrar boleta por ID")
    void testBuscarPorId() {
        when(boletaRepositorio.findById(1L)).thenReturn(Optional.of(boletaEjemplo));

        BoletaDTO result = boletaServicio.obtenerPorId(1L);
        assertThat(result.getClienteId()).isEqualTo(100L);
    }

    @Test
    @DisplayName("Debe lanzar excepción si se intenta guardar boleta sin items")
    void testGuardarBoletaSinItems() {
        BoletaDTO dto = new BoletaDTO();
        dto.setClienteId(1L);
        dto.setItems(Collections.emptyList());

        assertThatThrownBy(() -> boletaServicio.guardar(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("items");
    }

    @Test
    @DisplayName("Debe lanzar excepción si clienteId es nulo")
    void testGuardarBoletaClienteNulo() {
        BoletaDTO dto = new BoletaDTO();
        ItemBoletaDTO itemDTO = new ItemBoletaDTO(1L,"Perfume A",2,5000.0,10000.0);
        dto.setItems(List.of(itemDTO));

        assertThatThrownBy(() -> boletaServicio.guardar(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clienteId");
    }
}
