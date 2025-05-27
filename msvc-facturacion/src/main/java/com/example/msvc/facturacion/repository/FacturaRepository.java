package com.example.msvc.facturacion.repository;

import com.example.msvc.facturacion.modelo.Factura;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    @Override
    List<Factura> findAll();

    // Ejemplo de consulta personalizada: Buscar facturas por estado
    <Factura> findByEstado(String estado);

    // Ejemplo de consulta personalizada: Buscar facturas pendientes de pago
    List<Factura> findByEstadoAndTransaccionIdIsNull(String estado);

    // Ejemplo con JPQL: Buscar facturas por rango de fechas
    @Query("SELECT f FROM Factura f WHERE f.fechaEmision BETWEEN :fechaInicio AND :fechaFin")
    List<Factura> buscarPorRangoFechas(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
}