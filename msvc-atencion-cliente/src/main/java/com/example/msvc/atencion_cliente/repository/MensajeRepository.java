// MensajeRepository.java
@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByTicketId(Long ticketId);
}