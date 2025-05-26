// Factura.java
@Entity
@Table(name = "facturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo; // Ej: "FAC-2025-0001"
    private LocalDateTime fechaEmision;
    private Double total;
    private String estado; // "PENDIENTE", "PAGADA", "ANULADA"

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<ItemFactura> items;

    private String transaccionId; // ID de transacción en Transbank/PayPal
}