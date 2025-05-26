package com.example.msvc.factura.msvc_factura.repositorio;

import com.example.msvc.factura.msvc_factura.modelo.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    // Ejemplo de consulta personalizada: Buscar facturas por estado
    List<Factura> findByEstado(String estado);

    // Ejemplo de consulta personalizada: Buscar facturas pendientes de pago
    List<Factura> findByEstadoAndTransaccionIdIsNull(String estado);

    // Ejemplo con JPQL: Buscar facturas por rango de fechas
    @Query("SELECT f FROM Factura f WHERE f.fechaEmision BETWEEN :fechaInicio AND :fechaFin")
    List<Factura> buscarPorRangoFechas(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
}