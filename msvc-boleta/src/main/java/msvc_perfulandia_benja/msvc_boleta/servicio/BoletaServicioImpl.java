package msvc_perfulandia_benja.msvc_boleta.servicio;

import msvc_perfulandia_benja.msvc_boleta.dto.*;
import msvc_perfulandia_benja.msvc_boleta.excepciones.RecursoNoEncontradoException;
import msvc_perfulandia_benja.msvc_boleta.feign.ClienteClientRest;
import msvc_perfulandia_benja.msvc_boleta.feign.ProductoClientRest;
import msvc_perfulandia_benja.msvc_boleta.feign.VentaClientRest;
import msvc_perfulandia_benja.msvc_boleta.modelo.Boleta;
import msvc_perfulandia_benja.msvc_boleta.modelo.BoletaDetalle;
import msvc_perfulandia_benja.msvc_boleta.repositorio.BoletaRepositorio;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoletaServicioImpl implements BoletaServicio {

    private final BoletaRepositorio repositorio;
    private final ClienteClientRest clienteClientRest;
    private final VentaClientRest ventaClientRest;
    private final ProductoClientRest productoClientRest;

    public BoletaServicioImpl(BoletaRepositorio repositorio,
                              ClienteClientRest clienteClientRest,
                              VentaClientRest ventaClientRest,
                              ProductoClientRest productoClientRest) {
        this.repositorio = repositorio;
        this.clienteClientRest = clienteClientRest;
        this.ventaClientRest = ventaClientRest;
        this.productoClientRest = productoClientRest;
    }

    @Override
    public BoletaDTO generarBoletaDesdeVenta(Long ventaId) {
        VentaDTO venta = ventaClientRest.obtenerVentaPorId(ventaId);
        if (venta == null) {
            throw new RecursoNoEncontradoException("Venta no encontrada con ID: " + ventaId);
        }
        throw new UnsupportedOperationException("No se puede generar boleta desde venta porque VentaDTO no contiene clienteId ni lista de productos.");
    }

    @Override
    public List<BoletaDTO> listar() {
        return repositorio.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BoletaDTO obtenerPorId(Long id) {
        Boleta boleta = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Boleta no encontrada con id: " + id));
        return toDTO(boleta);
    }

    @Override
    public BoletaDTO guardar(BoletaDTO dto) {
        throw new UnsupportedOperationException("Usa generarBoletaDesdeClienteYVenta para crear boletas.");
    }

    @Override
    public BoletaDTO generarBoletaDesdeClienteYVenta(Long clienteId, Long ventaId) {
        // Obtener cliente por Feign
        ClienteDTO cliente = clienteClientRest.buscarPorId(clienteId);
        if (cliente == null) {
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID: " + clienteId);
        }

        // Obtener venta por Feign
        VentaDTO venta = ventaClientRest.obtenerVentaPorId(ventaId);
        if (venta == null) {
            throw new RecursoNoEncontradoException("Venta no encontrada con ID: " + ventaId);
        }

        // Obtener producto por Feign (producto que corresponde a la venta)
        ProductoDTO producto = null;
        try {
            producto = productoClientRest.obtenerProductoPorId(venta.getProductoId());
        } catch (Exception ignored) {}

        // Construir item de boleta basado en la venta simplificada
        ItemBoletaDTO item = ItemBoletaDTO.builder()
                .productoId(venta.getProductoId())
                .cantidad(venta.getCantidad() != null ? venta.getCantidad() : 1)
                .precioUnitario(
                        (venta.getTotal() != null && venta.getCantidad() != null && venta.getCantidad() != 0)
                                ? venta.getTotal() / venta.getCantidad()
                                : 0.0)
                .subtotal(venta.getTotal() != null ? venta.getTotal() : 0.0)
                .nombreProducto(producto != null ? producto.getNombre() : "Nombre no disponible")
                .build();

        List<ItemBoletaDTO> items = Collections.singletonList(item);

        // Construir DTO de boleta con cliente y items
        BoletaDTO boletaDTO = BoletaDTO.builder()
                .clienteId(clienteId)
                .nombreCliente(cliente.getNombre() + " " + cliente.getApellido())
                .correoCliente(cliente.getCorreo())
                .fecha(LocalDateTime.now())
                .items(items)
                .build();

        // Guardar en base de datos
        Boleta boleta = toEntity(boletaDTO);
        boleta.calcularTotal();
        Boleta boletaGuardada = repositorio.save(boleta);

        return toDTO(boletaGuardada);
    }

    @Override
    public void eliminar(Long id) {
        Boleta boleta = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Boleta no encontrada con id: " + id));
        repositorio.deleteById(id);
    }

    // Convertir entidad a DTO
    protected BoletaDTO toDTO(Boleta boleta) {
        ClienteDTO cliente = null;
        try {
            cliente = clienteClientRest.buscarPorId(boleta.getClienteId());
        } catch (Exception ignored) {}

        String nombreCliente = (cliente != null) ? cliente.getNombre() + " " + cliente.getApellido() : "Desconocido";
        String correoCliente = (cliente != null) ? cliente.getCorreo() : null;

        List<ItemBoletaDTO> itemsDTO = boleta.getItems().stream()
                .map(item -> {
                    String nombreProducto = null;
                    try {
                        ProductoDTO producto = productoClientRest.obtenerProductoPorId(item.getProductoId());
                        nombreProducto = producto.getNombre();
                    } catch (Exception ignored) {}

                    double subtotal = item.getPrecioUnitario() * item.getCantidad();

                    return ItemBoletaDTO.builder()
                            .productoId(item.getProductoId())
                            .cantidad(item.getCantidad())
                            .precioUnitario(item.getPrecioUnitario())
                            .subtotal(subtotal)
                            .nombreProducto(nombreProducto != null ? nombreProducto : "Nombre no disponible")
                            .build();
                }).collect(Collectors.toList());

        return BoletaDTO.builder()
                .id(boleta.getId())
                .clienteId(boleta.getClienteId())
                .nombreCliente(nombreCliente)
                .correoCliente(correoCliente)
                .fecha(boleta.getFecha())
                .total(boleta.getTotal())
                .items(itemsDTO)
                .build();
    }

    // Convertir DTO a entidad
    protected Boleta toEntity(BoletaDTO dto) {
        Boleta boleta = new Boleta();
        boleta.setClienteId(dto.getClienteId());
        boleta.setFecha(dto.getFecha());

        List<BoletaDetalle> items = dto.getItems().stream()
                .map(dtoItem -> {
                    BoletaDetalle item = new BoletaDetalle();
                    item.setProductoId(dtoItem.getProductoId());
                    item.setCantidad(dtoItem.getCantidad());
                    item.setPrecioUnitario(dtoItem.getPrecioUnitario());
                    item.setBoleta(boleta);
                    return item;
                }).collect(Collectors.toList());

        boleta.setItems(items);
        boleta.calcularTotal();

        return boleta;
    }
}
