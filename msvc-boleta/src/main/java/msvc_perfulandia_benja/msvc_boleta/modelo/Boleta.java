package msvc_perfulandia_benja.msvc_boleta.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "boletas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;

    private LocalDateTime fecha;

    private Double total;

    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)

    private List<BoletaDetalle> items;
    public void calcularTotal() {
        if (items != null && !items.isEmpty()) {
            this.total = items.stream()
                    .mapToDouble(item -> item.getPrecioUnitario() * item.getCantidad())
                    .sum();
        } else {
            this.total = 0.0;
        }
    }

}
