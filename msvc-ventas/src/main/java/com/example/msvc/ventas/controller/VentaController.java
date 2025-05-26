package com.example.msvc.venta.msvc_venta.controlador;

import com.example.msvc.venta.msvc_venta.DTO.VentaDTO;
import com.example.msvc.venta.msvc_venta.servicio.VentaServicio;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaServicio ventaServicio;

    public VentaController(VentaServicio ventaServicio) {
        this.ventaServicio = ventaServicio;
    }

    // Crear una nueva venta (con validación de stock y aplicación de descuentos)
    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta(@Valid @RequestBody VentaDTO ventaDTO) {
        return ResponseEntity.ok(ventaServicio.crearVenta(ventaDTO));
    }

    // Obtener todas las ventas
    @GetMapping
    public ResponseEntity<List<VentaDTO>> listarVentas() {
        return ResponseEntity.ok(ventaServicio.listarVentas());
    }

    // Obtener venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerVentaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaServicio.obtenerVentaPorId(id));
    }

    // Cancelar una venta (cambia estado a "CANCELADA")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<VentaDTO> cancelarVenta(@PathVariable Long id) {
        return ResponseEntity.ok(ventaServicio.cancelarVenta(id));
    }

    // Aplicar cupón de descuento a una venta existente
    @PatchMapping("/{id}/aplicar-cupon")
    public ResponseEntity<VentaDTO> aplicarCupon(
            @PathVariable Long id,
            @RequestParam String codigoCupon
    ) {
        return ResponseEntity.ok(ventaServicio.aplicarCupon(id, codigoCupon));
    }
}