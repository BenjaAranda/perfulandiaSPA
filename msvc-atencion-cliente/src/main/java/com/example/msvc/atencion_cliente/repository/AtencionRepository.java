package com.example.msvc.atencion_cliente.repository;

import com.example.msvc.atencion_cliente.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtencionRepository extends JpaRepository<Atencion, Long> {
    @Override
    List<Atencion> findAll();

    List<Atencion> findByEstado(String estado);

}
