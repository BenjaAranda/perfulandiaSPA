package msvc_perfulandia_benja.msvc_boleta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    @Schema(description = "ID de la boleta", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private Long id;

    @NotNull(message = "El ID del cliente no puede ser nulo")
    @Min(value = 1, message = "El ID del cliente debe ser mayor que 0")
    @Schema(description = "ID del cliente", example = "5", required = true)
    private Long clienteId;

    @Schema(description = "Nombre del cliente (obtenido vía Feign desde msvc-clientes)", example = "Juan Pérez", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private String nombreCliente;

    @Schema(description = "Correo del cliente (obtenido vía Feign desde msvc-clientes)", example = "juan@mail.com", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private String correoCliente;

    @Schema(description = "Fecha y hora de la emisión", example = "2025-06-24T18:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime fecha;

    @Schema(description = "Total de la boleta", example = "59970", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private Double total;

    @NotEmpty(message = "La lista de productos no puede estar vacía")
    @Valid
    @Schema(description = "Lista de productos comprados", required = true)
    private List<ItemBoletaDTO> items;
}
