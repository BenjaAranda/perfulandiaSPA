package com.example.msvc.facturacion.controller;

import com.example.msvc.facturacion.model.Factura;
import com.example.msvc.facturacion.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {
        return new ResponseEntity<>(facturaService.crearFactura(factura), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable Long id) {
        return facturaService.obtenerFactura(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Factura> listarFacturas() {
        return facturaService.listarFacturas();
    }
}
