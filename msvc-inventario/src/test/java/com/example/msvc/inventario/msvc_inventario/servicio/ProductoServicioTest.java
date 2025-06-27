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
    }

    @Test
    void actualizarProducto_NotFound() {
        when(repositorio.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> servicio.actualizar(999L, dto))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }
}
