package msvc_perfulandia_benja.msvc_boleta.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {

    private Long id;

    private Long productoId;

    private Integer cantidad;

    private Double total;

    private LocalDateTime fecha;
}
