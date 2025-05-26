// ItemFactura.java
@Entity
@Table(name = "items_factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;

    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura factura;
}