package msvc_perfulandia_benja.msvc_boleta.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearBoletaDTO {

    @NotNull(message = "El ID del cliente no puede ser nulo")
    @Min(value = 1, message = "El ID del cliente debe ser mayor que 0")
    private Long clienteId;

    @NotNull(message = "El ID de la venta no puede ser nulo")
    @Min(value = 1, message = "El ID de la venta debe ser mayor que 0")
    private Long ventaId;
}
