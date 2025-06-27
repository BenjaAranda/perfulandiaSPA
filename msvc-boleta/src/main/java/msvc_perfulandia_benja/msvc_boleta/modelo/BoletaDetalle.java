package msvc_perfulandia_benja.msvc_boleta.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items_boleta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoletaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;

    private Integer cantidad;

    private Double precioUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boleta_id")
    private Boleta boleta;
}
