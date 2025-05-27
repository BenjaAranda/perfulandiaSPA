package com.example.msvc.facturacion.repository;

import com.example.msvc.facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
