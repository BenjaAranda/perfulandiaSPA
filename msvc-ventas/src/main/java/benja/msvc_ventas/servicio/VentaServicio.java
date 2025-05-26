package benja.msvc_ventas.servicio;

import benja.msvc_ventas.dto.VentaDTO;
import benja.msvc_ventas.excepcion.RecursoNoEncontradoException;
import benja.msvc_ventas.modelo.Venta;
import benja.msvc_ventas.repositorio.VentaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServicio {

    private final VentaRepositorio repositorio;

    public VentaServicio(VentaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<VentaDTO> listar() {
        return repositorio.findAll()
                .stream()
                .map(this::convertirAVentaDTO)
                .collect(Collectors.toList());
    }

    public VentaDTO obtenerPorId(Long id) {
        Venta venta = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + id));
        return convertirAVentaDTO(venta);
    }

    public VentaDTO guardar(VentaDTO dto) {
        Venta venta = convertirAVenta(dto);
        return convertirAVentaDTO(repositorio.save(venta));
    }

    public VentaDTO actualizar(Long id, VentaDTO dto) {
        Venta venta = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + id));

        venta.setProductoId(dto.getProductoId());
        venta.setCantidad(dto.getCantidad());
        venta.setTotal(dto.getTotal());

        return convertirAVentaDTO(repositorio.save(venta));
    }

    public void eliminar(Long id) {
        Venta venta = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + id));
        repositorio.delete(venta);
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

    private Venta convertirAVenta(VentaDTO dto) {
        return Venta.builder()
                .id(dto.getId())
                .productoId(dto.getProductoId())
                .cantidad(dto.getCantidad())
                .total(dto.getTotal())
                .fecha(dto.getFecha())
                .build();
    }
}
