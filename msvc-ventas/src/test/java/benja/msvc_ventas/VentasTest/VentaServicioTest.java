package benja.msvc_ventas.VentasTest;

import benja.msvc_ventas.dto.ProductoDTO;
import benja.msvc_ventas.dto.VentaDTO;
import benja.msvc_ventas.feign.ProductoClienteRest;
import benja.msvc_ventas.modelo.Venta;
import benja.msvc_ventas.repositorio.VentaRepositorio;
import benja.msvc_ventas.servicio.VentaServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VentaServicioTest {

    private VentaRepositorio repositorio;
    private ProductoClienteRest clienteRest;
    private VentaServicio servicio;

    @BeforeEach
    void setUp() {
        repositorio = mock(VentaRepositorio.class);
        clienteRest = mock(ProductoClienteRest.class);
        servicio = new VentaServicio(repositorio, clienteRest);
    }

    @Test
    void testGuardar() {
        ProductoDTO producto = ProductoDTO.builder()
                .id(1L)
                .nombre("Perfume")
                .precio(10000.0)
                .build();

        when(clienteRest.obtenerProductoPorId(1L)).thenReturn(producto);

        Venta venta = Venta.builder()
                .id(1L)
                .productoId(1L)
                .cantidad(2)
                .total(20000.0)
                .build();

        when(repositorio.save(any())).thenReturn(venta);

        VentaDTO dto = VentaDTO.builder()
                .productoId(1L)
                .cantidad(2)
                .build();

        VentaDTO resultado = servicio.guardar(dto);

        assertNotNull(resultado);
        assertEquals(20000.0, resultado.getTotal());
    }

    @Test
    void testObtenerPorId() {
        Venta venta = Venta.builder()
                .id(1L)
                .productoId(1L)
                .cantidad(1)
                .total(9990.0)
                .build();

        when(repositorio.findById(1L)).thenReturn(Optional.of(venta));

        VentaDTO resultado = servicio.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getProductoId());
    }
}