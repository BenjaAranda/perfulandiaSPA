// VentaDTO.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {
    private Long id;
    private String clienteId;
    private List<DetalleVentaDTO> detalles;