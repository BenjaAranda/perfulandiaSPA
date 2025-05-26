// VentaService.java
@Service
@RequiredArgsConstructor
public class VentaService {
    private final VentaRepository ventaRepository;
    private final ProductoFeignClient productoFeignClient; // Para integrar con Inventario

    public VentaDTO crearVenta(VentaDTO ventaDTO) {
        // Validar stock usando Feign
        ventaDTO.getDetalles().forEach(detalle -> {
            ProductoDTO producto = productoFeignClient.obtenerProducto(detalle.getProductoId());
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
        });

        // Guardar venta
        Venta venta = Venta.builder()
                .clienteId(ventaDTO.getClienteId())
                .fecha(LocalDateTime.now())
                .estado("COMPLETADA")
                .build();

        // Calcular total y guardar detalles
        Double total = 0.0;
        List<DetalleVenta> detalles = new ArrayList<>();

        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            ProductoDTO producto = productoFeignClient.obtenerProducto(detalleDTO.getProductoId());
            Double subtotal = producto.getPrecio() * detalleDTO.getCantidad();

            detalles.add(DetalleVenta.builder()
                    .productoId(detalleDTO.getProductoId())
                    .cantidad(detalleDTO.getCantidad())
                    .precioUnitario(producto.getPrecio())
                    .venta(venta)
                    .build());

            total += subtotal;
            // Actualizar stock en Inventario (vía Feign)
            productoFeignClient.actualizarStock(detalleDTO.getProductoId(), detalleDTO.getCantidad());
        }

        venta.setDetalles(detalles);
        venta.setTotal(total);
        return convertirAVentaDTO(ventaRepository.save(venta));
    }
}