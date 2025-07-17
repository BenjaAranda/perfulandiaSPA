package msvc_perfulandia_benja.msvc_boleta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para transferencia de datos del Cliente")
public class ClienteDTO {

    @Schema(description = "Identificador único del cliente", example = "1")
    private Long id;

    @NotBlank(message = "El RUT es obligatorio")
    @Schema(description = "RUT del cliente", example = "12345678-9")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Schema(description = "Nombre del cliente", example = "Juan")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Schema(description = "Apellido del cliente", example = "Pérez")
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Schema(description = "Correo electrónico del cliente", example = "juan.perez@example.com")
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\+?\\d{8,15}", message = "El teléfono debe tener entre 8 y 15 dígitos")
    @Schema(description = "Teléfono del cliente", example = "+56912345678")
    private String telefono;

    @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
    @Schema(description = "Dirección del cliente", example = "Av. Siempre Viva 742, Springfield")
    private String direccion;
}
