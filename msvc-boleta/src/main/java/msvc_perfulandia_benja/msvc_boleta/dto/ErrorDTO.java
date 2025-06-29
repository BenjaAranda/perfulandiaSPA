package msvc_perfulandia_benja.msvc_boleta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Detalles del error en las respuestas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {

    @Schema(description = "Código de error HTTP", example = "404")
    private int status;

    @Schema(description = "Mensaje explicativo del error", example = "Boleta no encontrada con ID 12")
    private String mensaje;

    @Schema(description = "Ruta en la que ocurrió el error", example = "/api/v2/boletas/12")
    private String ruta;

}
