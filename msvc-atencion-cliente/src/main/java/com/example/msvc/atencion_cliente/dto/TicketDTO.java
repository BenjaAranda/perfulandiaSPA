// TicketDTO.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long clienteId;
    private List<MensajeDTO> mensajes;
}