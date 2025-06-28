package com.example.msvc.inventario.msvc_inventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Estructura de error devuelta por la API ante excepciones o validaciones")
public class ErrorDTO {

    @Schema(description = "Código HTTP del error", example = "404")
    private Integer status;

    @Schema(description = "Mensaje corto del error", example = "Recurso no encontrado")
    private String error;

    @Schema(description = "Descripción detallada del error", example = "Producto no encontrado con ID: 1")
    private String message;

    @Schema(description = "Ruta donde ocurrió el error", example = "/api/v1/productos/1")
    private String path;

    @Schema(description = "Fecha y hora del error")
    private LocalDateTime timestamp;

    @Schema(description = "Lista de errores de validación si corresponde")
    private List<String> errors;
}
