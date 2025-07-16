package benja.msvc_ventas.repositorio;

import benja.msvc_ventas.modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VentaRepositorio extends JpaRepository<Venta, Long> {
    Optional<Venta> findTopByProductoIdOrderByFechaDesc(Long productoId);

}

