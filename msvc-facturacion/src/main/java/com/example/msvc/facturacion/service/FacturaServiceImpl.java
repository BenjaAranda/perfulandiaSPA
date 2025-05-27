package com.example.msvc.facturacion.service;

import com.example.msvc.facturacion.model.Factura;
import com.example.msvc.facturacion.repository.FacturaRepository;
import com.example.msvc.facturacion.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public Factura crearFactura(Factura factura) {
        factura.setFechaEmision(LocalDate.now());
        return facturaRepository.save(factura);
    }

    @Override
    public Optional<Factura> obtenerFactura(Long id) {
        return facturaRepository.findById(id);
    }

    @Override
    public List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }
}
