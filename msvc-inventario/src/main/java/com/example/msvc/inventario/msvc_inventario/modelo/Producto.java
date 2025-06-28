package com.example.msvc.inventario.msvc_inventario.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa un producto del inventario de perfumes")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Nombre del perfume", example = "Bleu de Chanel")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Perfume amaderado aromático para hombres")
    private String descripcion;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    @Schema(description = "Cantidad disponible en stock", example = "25")
    private int stock;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(nullable = false)
    @Schema(description = "Precio del producto en CLP", example = "89990.0")
    private Double precio;
}

