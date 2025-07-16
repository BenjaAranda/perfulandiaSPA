package benja.msvc_ventas.servicio;

import benja.msvc_ventas.dto.ProductoDTO;
import benja.msvc_ventas.dto.VentaDTO;
import benja.msvc_ventas.excepcion.RecursoNoEncontradoException;
import benja.msvc_ventas.feign.ProductoClienteRest;
import benja.msvc_ventas.modelo.Venta;
import benja.msvc_ventas.repositorio.VentaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentaServicioImpl implements VentaServicio {

    private final VentaRepositorio repositorio;
    private final ProductoClienteRest clienteRest;

    public VentaServicioImpl(VentaRepositorio repositorio, ProductoClienteRest clienteRest) {
        this.repositorio = repositorio;
        this.clienteRest = clienteRest;
    }

    @Override
    public List<VentaDTO> listar() {
        return repositorio.findAll()
                .stream()
                .map(this::convertirAVentaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VentaDTO obtenerPorId(Long id) {
        Venta venta = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + id));
        return convertirAVentaDTO(venta);
    }

    @Override
    public VentaDTO guardar(VentaDTO dto) {
        ProductoDTO producto = clienteRest.obtenerProductoPorId(dto.getProductoId());
        if (producto == null) {
            throw new RecursoNoEncontradoException("Producto no encontrado con ID: " + dto.getProductoId());
        }

        double total = producto.getPrecio() * dto.getCantidad();
        Venta venta = Venta.builder()
                .productoId(dto.getProductoId())
                .cantidad(dto.getCantidad())
                .total(total)
                .build();

        return convertirAVentaDTO(repositorio.save(venta));
    }

    @Override
    public VentaDTO actualizar(Long id, VentaDTO dto) {
        Venta venta = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + id));

        venta.setCantidad(dto.getCantidad());
        venta.setProductoId(dto.getProductoId());

        ProductoDTO producto = clienteRest.obtenerProductoPorId(dto.getProductoId());
        if (producto == null) {
            throw new RecursoNoEncontradoException("Producto no encontrado con ID: " + dto.getProductoId());
        }

        venta.setTotal(producto.getPrecio() * dto.getCantidad());

        return convertirAVentaDTO(repositorio.save(venta));
    }

    @Override
    public void eliminar(Long id) {
        Venta venta = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + id));
        repositorio.delete(venta);
    }

    @Override
    public VentaDTO obtenerUltimaVentaPorProductoId(Long productoId) {
        Venta venta = repositorio.findTopByProductoIdOrderByFechaDesc(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró venta para el producto con ID: " + productoId));
        return convertirAVentaDTO(venta);
    }

    private VentaDTO convertirAVentaDTO(Venta venta) {
        return VentaDTO.builder()
                .id(venta.getId())
                .productoId(venta.getProductoId())
                .cantidad(venta.getCantidad())
                .total(venta.getTotal())
                .fecha(venta.getFecha())
                .build();
    }
}
