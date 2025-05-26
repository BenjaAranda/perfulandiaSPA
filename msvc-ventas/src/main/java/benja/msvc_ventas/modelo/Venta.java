package benja.msvc_ventas.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "producto_id")
    private Long productoId;

    private Integer cantidad;

    private Double total;

    private LocalDateTime fecha;

    @PrePersist
    public void asignarFecha() {
        this.fecha = LocalDateTime.now();
    }
}
