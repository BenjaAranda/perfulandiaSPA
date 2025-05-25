package com.example.msvc.inventario.msvc_inventario.repositorio;

import com.example.msvc.inventario.msvc_inventario.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
}
