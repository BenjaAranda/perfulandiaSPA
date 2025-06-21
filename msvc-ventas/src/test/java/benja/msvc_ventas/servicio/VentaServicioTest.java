package benja.msvc_ventas.servicio;

import benja.msvc_ventas.dto.ProductoDTO;
import benja.msvc_ventas.dto.VentaDTO;
import benja.msvc_ventas.excepcion.RecursoNoEncontradoException;
import benja.msvc_ventas.feign.ProductoClienteRest;
import benja.msvc_ventas.modelo.Venta;
import benja.msvc_ventas.repositorio.VentaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VentaServicioTest {

    @Mock
    private VentaRepositorio ventaRepositorio;

    @Mock
    private ProductoClienteRest productoClienteRest;

    @InjectMocks
    private VentaServicio ventaServicio;

    private Venta venta;
    private ProductoDTO productoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        venta = Venta.builder()
                .id(1L)
                .productoId(10L)
                .cantidad(2)
                .total(19980.0)
                .build();

        productoDTO = ProductoDTO.builder()
                .id(10L)
                .nombre("Perfume de prueba")
                .precio(9990.0)
                .build();
    }

    @Test
    void obtenerVentaPorIdExistente() {
        when(ventaRepositorio.findById(1L)).thenReturn(Optional.of(venta));
        VentaDTO dto = ventaServicio.obtenerPorId(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.getProductoId()).isEqualTo(10L);
        verify(ventaRepositorio, times(1)).findById(1L);
    }

    @Test
    void obtenerVentaPorIdNoExistente() {
        when(ventaRepositorio.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> ventaServicio.obtenerPorId(999L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Venta no encontrada");
    }

    @Test
    void guardarVentaConProductoExistente() {
        when(productoClienteRest.obtenerProductoPorId(10L)).thenReturn(productoDTO);
        when(ventaRepositorio.save(any())).thenReturn(venta);

        VentaDTO dto = VentaDTO.builder().productoId(10L).cantidad(2).build();
        VentaDTO result = ventaServicio.guardar(dto);

        assertThat(result.getTotal()).isEqualTo(19980.0);
    }

    @Test
    void guardarVentaConProductoNoExistente() {
        when(productoClienteRest.obtenerProductoPorId(999L)).thenReturn(null);
        VentaDTO dto = VentaDTO.builder().productoId(999L).cantidad(2).build();

        assertThatThrownBy(() -> ventaServicio.guardar(dto))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Producto no encontrado");
    }
}
