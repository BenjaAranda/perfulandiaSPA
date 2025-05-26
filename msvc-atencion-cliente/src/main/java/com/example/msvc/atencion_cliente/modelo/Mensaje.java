// Mensaje.java
@Entity
@Table(name = "mensajes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;
    private LocalDateTime fechaEnvio;
    private boolean esAgente;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}