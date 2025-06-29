package benja.msvc_ventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representa un error genérico")
public class ErrorDTO {
    @Schema(description = "Mensaje de error", example = "Venta no encontrada con ID: 5")
    private String mensaje;
}
