package com.facturacion.controller;

import com.facturacion.DTO.FacturaDTO;
import com.facturacion.servicio.FacturaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    // Generar factura a partir de una venta
    @PostMapping
    public ResponseEntity<FacturaDTO> generarFactura(@Valid @RequestBody FacturaDTO facturaDTO) {
        return ResponseEntity.ok(facturaService.generarFactura(facturaDTO));
    }

    // Obtener todas las facturas
    @GetMapping
    public ResponseEntity<List<FacturaDTO>> listarFacturas() {
        return ResponseEntity.ok(facturaService.listarFacturas());
    }

    // Obtener factura por ID
    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> obtenerFacturaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.obtenerFacturaPorId(id));
    }

    // Procesar pago (integración con Transbank)
    @PostMapping("/{id}/pagar")
    public ResponseEntity<FacturaDTO> procesarPago(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.procesarPago(id));
    }
}