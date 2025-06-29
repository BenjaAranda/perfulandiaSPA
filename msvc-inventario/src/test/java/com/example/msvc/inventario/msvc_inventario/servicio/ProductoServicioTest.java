package com.example.msvc.inventario.msvc_inventario.servicio;

import com.example.msvc.inventario.msvc_inventario.dto.ProductoDTO;
import com.example.msvc.inventario.msvc_inventario.excepcion.RecursoNoEncontradoException;
import com.example.msvc.inventario.msvc_inventario.modelo.Producto;
import com.example.msvc.inventario.msvc_inventario.repositorio.ProductoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServicioTest {

    @Mock
    private ProductoRepositorio repositorio;

    @InjectMocks
    private ProductoServicio servicio;

    private Producto producto;
    private ProductoDTO dto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        producto = Producto.builder()
                .id(1L).nombre("Perfume").descripcion("Descripción")
                .stock(5).precio(19990.0).build();

        dto = ProductoDTO.builder()
                .id(1L).nombre("Perfume")
                .descripcion("Descripción").stock(5).precio(19990.0)
                .build();
    }

    @Test
    void obtenerPorId_OK() {
        when(repositorio.findById(1L)).thenReturn(Optional.of(producto));
        ProductoDTO res = servicio.obtenerPorId(1L);
        assertThat(res).isNotNull();
        assertThat(res.getNombre()).isEqualTo("Perfume");
        verify(repositorio, times(1)).findById(1L);
    }

    @Test
    void obtenerPorId_NotFound() {
        when(repositorio.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> servicio.obtenerPorId(999L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Producto no encontrado");
    }

    @Test
    void guardarProducto_OK() {
        when(repositorio.save(any())).thenReturn(producto);
        ProductoDTO saved = servicio.guardar(dto);
        assertThat(saved).isNotNull();
        assertThat(saved.getNombre()).isEqualTo("Perfume");
        verify(repositorio).save(any(Producto.class));
    }

    @Test
    void actualizarProducto_OK() {
        when(repositorio.findById(1L)).thenReturn(Optional.of(producto));
        when(repositorio.save(any())).thenReturn(producto);

        ProductoDTO actualizado = servicio.actualizar(1L, dto);
        assertThat(actualizado).isNotNull();
        assertThat(actualizado.getNombre()).isEqualTo("Perfume");
        verify(repositorio).findById(1L);
        verify(repositorio).save(any(Producto.class));
    }

    @Test
    void actualizarProducto_NotFound() {
        when(repositorio.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> servicio.actualizar(999L, dto))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    void eliminarProducto_OK() {
        when(repositorio.findById(1L)).thenReturn(Optional.of(producto));
        servicio.eliminar(1L);
        verify(repositorio).delete(producto);
    }

    @Test
    void eliminarProducto_NotFound() {
        when(repositorio.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> servicio.eliminar(999L))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    void eliminarTodos_OK() {
        servicio.eliminarTodos();
        verify(repositorio, times(1)).deleteAll();
    }

    @Test
    void listar_OK_conDatos() {
        when(repositorio.findAll()).thenReturn(Arrays.asList(producto));
        List<ProductoDTO> lista = servicio.listar();
        assertThat(lista).isNotEmpty();
        assertThat(lista.get(0).getNombre()).isEqualTo("Perfume");
    }

    @Test
    void listar_OK_sinDatos() {
        when(repositorio.findAll()).thenReturn(Collections.emptyList());
        List<ProductoDTO> lista = servicio.listar();
        assertThat(lista).isEmpty();
    }
}
