package com.example.msvc.facturacion.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// FacturaDTO.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FacturaDTO {
    private Long id;
    private String codigo;
    private Long ventaId; // ID de la venta asociada
    private List<ItemFacturaDTO> items;
}