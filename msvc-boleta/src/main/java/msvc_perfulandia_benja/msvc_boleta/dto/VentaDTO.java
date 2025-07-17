package msvc_perfulandia_benja.msvc_boleta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Información simplificada de una venta")
public class VentaDTO {

    @Schema(description = "ID de la venta", example = "1")
    private Long id;

    @Schema(description = "ID del producto", example = "2")
    private Long productoId;

    @Schema(description = "Cantidad comprada", example = "1")
    private Integer cantidad;

    @Schema(description = "Total de la venta", example = "26335.81")
    private Double total;

    @Schema(description = "Fecha de la venta", example = "2025-06-21T03:19:45.29398")
    private LocalDateTime fecha;
}
