package msvc_perfulandia_benja.msvc_boleta.faker;

import jakarta.annotation.PostConstruct;
import msvc_perfulandia_benja.msvc_boleta.modelo.Boleta;
import msvc_perfulandia_benja.msvc_boleta.modelo.BoletaDetalle;
import msvc_perfulandia_benja.msvc_boleta.repositorio.BoletaRepositorio;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Profile("dev")
@Component
public class DataBoletaFakerLoader {

    @Autowired
    private BoletaRepositorio boletaRepositorio;

    private final Faker faker = new Faker(Locale.of("es", "CL"));

    @PostConstruct
    public void init() {
        if (boletaRepositorio.count() == 0) {
            for (int i = 0; i < 10; i++) {
                Boleta boleta = new Boleta();
                boleta.setClienteId(faker.number().numberBetween(1L, 20L));
                boleta.setFecha(LocalDate.now()
                        .minusDays(faker.number().numberBetween(1, 30))
                        .atStartOfDay());

                BoletaDetalle item = new BoletaDetalle();
                item.setProductoId(faker.number().numberBetween(1L, 10L));
                item.setCantidad(faker.number().numberBetween(1, 5));
                item.setPrecioUnitario(faker.number().randomDouble(2, 3000, 20000));

                boleta.setItems(List.of(item));
                boleta.calcularTotal();
                boletaRepositorio.save(boleta);
            }
        }
    }
}
