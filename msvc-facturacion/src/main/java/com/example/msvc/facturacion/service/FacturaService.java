package com.example.msvc.facturacion.service;

import com.example.msvc.facturacion.model.Factura;

import java.util.List;
import java.util.Optional;

public interface FacturaService {
    Factura crearFactura(Factura factura);
    Optional<Factura> obtenerFactura(Long id);
    List<Factura> listarFacturas();
}
