// FacturaDTO.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {
    private Long id;
    private String codigo;
    private Long ventaId; // ID de la venta asociada
    private List<ItemFacturaDTO> items;
}