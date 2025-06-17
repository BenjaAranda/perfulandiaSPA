package com.example.msvc.inventario.msvc_inventario.ProductoTest;

import com.example.msvc.inventario.msvc_inventario.DTO.ProductoDTO;
import com.example.msvc.inventario.msvc_inventario.modelo.Producto;
import com.example.msvc.inventario.msvc_inventario.repositorio.ProductoRepositorio;
import com.example.msvc.inventario.msvc_inventario.servicio.ProductoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductoServicioTest {

    private ProductoRepositorio repositorio;
    private ProductoServicio servicio;

    @BeforeEach
    void setUp() {
        repositorio = mock(ProductoRepositorio.class);
        servicio = new ProductoServicio(repositorio);
    }

    @Test
    void testObtenerProductoPorId() {
        Producto producto = Producto.builder()
                .id(1L)
                .nombre("Perfume")
                .descripcion("Aroma suave")
                .stock(10)
                .precio(19990.0)
                .build();

        when(repositorio.findById(1L)).thenReturn(Optional.of(producto));

        ProductoDTO resultado = servicio.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("Perfume", resultado.getNombre());
    }

    @Test
    void testListarProductos() {
        when(repositorio.findAll()).thenReturn(List.of(
                Producto.builder().id(1L).nombre("P1").build(),
                Producto.builder().id(2L).nombre("P2").build()
        ));

        List<ProductoDTO> lista = servicio.listar();
        assertEquals(2, lista.size());
    }
}
