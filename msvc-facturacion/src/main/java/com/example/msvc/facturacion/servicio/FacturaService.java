package com.facturacion.servicio;

import com.facturacion.DTO.FacturaDTO;
import com.facturacion.DTO.ItemFacturaDTO;
import com.facturacion.clients.VentaFeignClient;
import com.facturacion.modelo.Factura;
import com.facturacion.modelo.ItemFactura;
import com.facturacion.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final VentaFeignClient ventaFeignClient;

    public FacturaDTO generarFactura(FacturaDTO facturaDTO) {
        // Obtener datos de la venta asociada (vía Feign)
        VentaDTO venta = ventaFeignClient.obtenerVenta(facturaDTO.getVentaId());

        // Crear factura
        Factura factura = Factura.builder()
                .codigo("FAC-" + LocalDateTime.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 4))
                .fechaEmision(LocalDateTime.now())
                .estado("PENDIENTE")
                .build();

        // Mapear items de la venta a la factura
        List<ItemFactura> items = venta.getDetalles().stream()
                .map(detalle -> ItemFactura.builder()
                        .productoId(detalle.getProductoId())
                        .nombreProducto(obtenerNombreProducto(detalle.getProductoId())) // Usar Feign
                        .cantidad(detalle.getCantidad())
                        .precioUnitario(detalle.getPrecioUnitario())
                        .factura(factura)
                        .build())
                .toList();

        factura.setItems(items);
        factura.setTotal(calcularTotal(items));

        return convertirAFacturaDTO(facturaRepository.save(factura));
    }

    // Integración con Transbank (ejemplo simplificado)
    public FacturaDTO procesarPago(Long facturaId) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        // Simular pago exitoso
        factura.setEstado("PAGADA");
        factura.setTransaccionId("TBK-" + UUID.randomUUID().toString());

        return convertirAFacturaDTO(facturaRepository.save(factura));
    }

    private Double calcularTotal(List<ItemFactura> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrecioUnitario() * item.getCantidad())
                .sum();
    }

    // Métodos de conversión DTO <-> Entidad (similares a los del microservicio de Ventas)
}