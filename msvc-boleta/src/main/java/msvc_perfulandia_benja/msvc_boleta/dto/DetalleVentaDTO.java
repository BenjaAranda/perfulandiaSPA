package msvc_perfulandia_benja.msvc_boleta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detalle de un producto vendido en una venta")
public class DetalleVentaDTO {

    @Schema(description = "ID del producto", example = "2")
    private Long productoId;

    @Schema(description = "Cantidad vendida", example = "1")
    private Integer cantidad;

    @Schema(description = "Subtotal para este producto", example = "26335.81")
    private Double subtotal;
}
