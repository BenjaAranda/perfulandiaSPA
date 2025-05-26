// Ticket.java
@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String estado; // "ABIERTO", "EN_PROCESO", "CERRADO"
    private LocalDateTime fechaCreacion;
    private Long clienteId;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Mensaje> mensajes;
}