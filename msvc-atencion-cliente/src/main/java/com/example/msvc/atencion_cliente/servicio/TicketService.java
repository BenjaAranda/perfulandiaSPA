// TicketService.java
@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final MensajeRepository mensajeRepository;

    public TicketDTO crearTicket(TicketDTO ticketDTO) {
        Ticket ticket = Ticket.builder()
                .titulo(ticketDTO.getTitulo())
                .descripcion(ticketDTO.getDescripcion())
                .estado("ABIERTO")
                .fechaCreacion(LocalDateTime.now())
                .clienteId(ticketDTO.getClienteId())
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);
        return convertirATicketDTO(savedTicket);
    }

    public MensajeDTO agregarMensaje(Long ticketId, MensajeDTO mensajeDTO) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        Mensaje mensaje = Mensaje.builder()
                .contenido(mensajeDTO.getContenido())
                .fechaEnvio(LocalDateTime.now())
                .esAgente(mensajeDTO.isEsAgente())
                .ticket(ticket)
                .build();

        Mensaje savedMensaje = mensajeRepository.save(mensaje);
        return convertirAMensajeDTO(savedMensaje);
    }
}