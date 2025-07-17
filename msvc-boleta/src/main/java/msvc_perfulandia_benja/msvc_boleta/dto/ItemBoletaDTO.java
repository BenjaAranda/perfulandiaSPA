package msvc_perfulandia_benja.msvc_boleta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detalle de productos en una boleta")
public class ItemBoletaDTO {

    @NotNull(message = "El ID del producto no puede ser nulo")
    @Min(value = 1, message = "El ID del producto debe ser mayor que 0")
    @Schema(description = "ID del producto", example = "3", required = true)
    private Long productoId;

    @Schema(description = "Nombre del producto (obtenido desde inventario o ventas)", example = "Perfume Floral", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private String nombreProducto;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Schema(description = "Cantidad vendida del producto", example = "2", required = true)
    private Integer cantidad;

    @Schema(description = "Precio unitario del producto", example = "19990", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private Double precioUnitario;

    @Schema(description = "Subtotal del producto (cantidad * precio)", example = "39980", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private Double subtotal;
}
