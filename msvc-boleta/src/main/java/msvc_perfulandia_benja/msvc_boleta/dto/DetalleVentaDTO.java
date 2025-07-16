package msvc_perfulandia_benja.msvc_boleta.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaDTO {

    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;
}
