package com.example.msvc.facturacion.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.example.msvc.facturacion.modelo.*;
// Factura.java
@Entity
@Table(name = "facturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder



public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO.IDENTITY)
    private Long id;

    private String codigo; // Ej: "FAC-2025-0001"
    private LocalDateTime fechaEmision;
    private Double total;
    private String estado; // "PENDIENTE", "PAGADA", "ANULADA"

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<ItemFactura> items;

    private String transaccionId; // ID de transacción en Transbank/PayPal
}