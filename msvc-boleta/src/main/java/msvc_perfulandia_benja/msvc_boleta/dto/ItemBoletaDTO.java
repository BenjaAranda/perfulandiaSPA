package msvc_perfulandia_benja.msvc_boleta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detalle de productos en una boleta")
public class ItemBoletaDTO {

    @Schema(description = "ID del producto", example = "3")
    private Long productoId;

    @Schema(description = "Nombre del producto (opcional, enriquecido con Feign)", example = "Perfume Floral")
    private String nombreProducto;

    @Schema(description = "Cantidad vendida del producto", example = "2")
    private Integer cantidad;

    @Schema(description = "Precio unitario del producto", example = "19990")
    private Double precioUnitario;

    @Schema(description = "Subtotal del producto (cantidad * precio)", example = "39980")
    private Double subtotal;
}
