package msvc_perfulandia_benja.msvc_boleta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos de la boleta generada por la compra")
public class BoletaDTO {

    @Schema(description = "ID de la boleta", example = "1")
    private Long id;

    @Schema(description = "ID del cliente", example = "5")
    private Long clienteId;

    @Schema(description = "Nombre del cliente (opcional, por Feign)", example = "Juan Pérez")
    private String nombreCliente;

    @Schema(description = "Fecha y hora de la emisión", example = "2025-06-24T18:30:00")
    private LocalDateTime fecha;

    @Schema(description = "Total de la boleta", example = "59970")
    private Double total;

    @Schema(description = "Lista de productos comprados")
    private List<ItemBoletaDTO> items;
}
