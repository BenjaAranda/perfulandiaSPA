package msvc_benja.msvc_clientes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de Cliente para transferencias de datos")
public class ClienteDTO {

    @Schema(description = "ID del cliente", example = "1")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del cliente", example = "Juan")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Schema(description = "Apellido del cliente", example = "Pérez")
    private String apellido;

    @Email(message = "Correo inválido")
    @Schema(description = "Correo del cliente", example = "juan.perez@example.com")
    private String correo;

    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    private String telefono;
}
