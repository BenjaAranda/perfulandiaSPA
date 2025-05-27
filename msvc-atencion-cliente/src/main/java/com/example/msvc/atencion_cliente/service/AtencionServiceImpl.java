package com.example.msvc.atencion_cliente.service;

import com.example.msvc.atencion_cliente.model.Atencion;
import com.example.msvc.atencion_cliente.repository.AtencionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AtencionServiceImpl implements AtencionService {

    @Autowired
    private AtencionRepository repository;

    @Override
    public Atencion crearAtencion(Atencion atencion) {
        atencion.setEstado("PENDIENTE");
        return repository.save(atencion);
    }

    @Override
    public Optional<Atencion> obtenerAtencion(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Atencion> listarAtenciones() {
        return repository.findAll();
    }

    @Override
    public List<Atencion> listarPorEstado(String estado) {
        return repository.findByEstado(estado.toUpperCase());
    }

    @Override
    public Atencion actualizarEstado(Long id, String nuevoEstado) {
        return repository.findById(id).map(atencion -> {
            atencion.setEstado(nuevoEstado.toUpperCase());
            return repository.save(atencion);
        }).orElseThrow(() -> new RuntimeException("No existe atención con ID: " + id));
    }
}
