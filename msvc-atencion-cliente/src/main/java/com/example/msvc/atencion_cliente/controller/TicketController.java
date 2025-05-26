// TicketController.java
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketDTO> crearTicket(@RequestBody TicketDTO ticketDTO) {
        return ResponseEntity.ok(ticketService.crearTicket(ticketDTO));
    }

    @PostMapping("/{ticketId}/mensajes")
    public ResponseEntity<MensajeDTO> agregarMensaje(
            @PathVariable Long ticketId,
            @RequestBody MensajeDTO mensajeDTO
    ) {
        return ResponseEntity.ok(ticketService.agregarMensaje(ticketId, mensajeDTO));
    }
}