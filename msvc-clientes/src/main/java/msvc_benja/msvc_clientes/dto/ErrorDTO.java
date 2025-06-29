package msvc_benja.msvc_clientes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Error genérico de respuesta")
public class ErrorDTO {

    @Schema(description = "Mensaje de error", example = "Cliente no encontrado con ID 10")
    private String mensaje;
}
