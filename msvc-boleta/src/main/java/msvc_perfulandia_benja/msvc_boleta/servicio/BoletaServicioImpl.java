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
        // Validar cliente real
        ClienteDTO cliente = clienteClientRest.buscarPorId(dto.getClienteId());
        if (cliente == null) {
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID: " + dto.getClienteId());
        }

        // Actualizar items con precio unitario real y nombre producto + subtotal
        List<ItemBoletaDTO> itemsActualizados = dto.getItems().stream().map(item -> {
            VentaDTO venta = ventaClientRest.obtenerUltimaVentaPorProductoId(item.getProductoId());
            if (venta == null) {
                throw new RecursoNoEncontradoException("Venta no encontrada para producto ID: " + item.getProductoId());
            }

            ProductoDTO producto = null;
            try {
                producto = productoClientRest.obtenerProductoPorId(item.getProductoId());
            } catch (Exception ignored) {}

            double precioUnitario = venta.getTotal() / venta.getCantidad();

            return ItemBoletaDTO.builder()
                    .productoId(item.getProductoId())
                    .cantidad(item.getCantidad())
                    .precioUnitario(precioUnitario)
                    .subtotal(precioUnitario * item.getCantidad())
                    .nombreProducto(producto != null ? producto.getNombre() : null)
                    .build();
        }).collect(Collectors.toList());

        dto.setItems(itemsActualizados);
        dto.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
        dto.setCorreoCliente(cliente.getCorreo());
        dto.setFecha(LocalDateTime.now()); // Fecha automática al guardar

        Boleta boleta = toEntity(dto);
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

    // Conversión entidad -> DTO
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
                            .nombreProducto(nombreProducto)
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

    // Conversión DTO -> entidad
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
