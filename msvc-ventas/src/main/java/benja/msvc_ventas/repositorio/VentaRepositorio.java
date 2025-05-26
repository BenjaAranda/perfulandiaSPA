package benja.msvc_ventas.repositorio;

import benja.msvc_ventas.modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepositorio extends JpaRepository<Venta, Long> {
}
