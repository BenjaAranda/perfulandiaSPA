package com.example.msvc.facturacion.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ItemFacturaDTO.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemFacturaDTO {
    private Long productoId;
    private Integer cantidad;
}