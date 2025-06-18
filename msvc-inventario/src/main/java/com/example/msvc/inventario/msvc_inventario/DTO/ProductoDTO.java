package com.example.msvc.inventario.msvc_inventario.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para transferencia de datos de productos")
public class ProductoDTO {

    @Schema(description = "ID del producto", example = "1")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del producto", example = "Perfume Floral")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Aroma suave de rosas")
    private String descripcion;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Stock disponible", example = "10")
    private int stock;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Schema(description = "Precio del producto", example = "9990.0")
    private Double precio;
}
