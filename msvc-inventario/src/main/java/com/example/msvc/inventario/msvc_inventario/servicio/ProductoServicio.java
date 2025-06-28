package com.example.msvc.inventario.msvc_inventario.servicio;

import com.example.msvc.inventario.msvc_inventario.dto.ProductoDTO;
import com.example.msvc.inventario.msvc_inventario.excepcion.RecursoNoEncontradoException;
import com.example.msvc.inventario.msvc_inventario.modelo.Producto;
import com.example.msvc.inventario.msvc_inventario.repositorio.ProductoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServicio {

    private final ProductoRepositorio repositorio;

    public ProductoServicio(ProductoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<ProductoDTO> listar() {
        return repositorio.findAll().stream()
                .map(this::convertirAProductoDTO)
                .collect(Collectors.toList());
    }


    public ProductoDTO obtenerPorId(Long id) {
        Producto producto = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));
        return convertirAProductoDTO(producto);
    }

    public ProductoDTO guardar(ProductoDTO dto) {
        Producto producto = convertirAProducto(dto);
        return convertirAProductoDTO(repositorio.save(producto));
    }

    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Producto existente = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setStock(dto.getStock());
        existente.setPrecio(dto.getPrecio());

        return convertirAProductoDTO(repositorio.save(existente));
    }

    public void eliminar(Long id) {
        Producto producto = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));
        repositorio.delete(producto);
    }

    // Métodos privados de conversión
    private ProductoDTO convertirAProductoDTO(Producto producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .stock(producto.getStock())
                .precio(producto.getPrecio())
                .build();
    }

    private Producto convertirAProducto(ProductoDTO dto) {
        return Producto.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .stock(dto.getStock())
                .precio(dto.getPrecio())
                .build();
    }

    public void eliminarTodos() {
        repositorio.deleteAll();
    }

}
