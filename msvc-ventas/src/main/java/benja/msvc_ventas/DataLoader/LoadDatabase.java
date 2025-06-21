package benja.msvc_ventas.DataLoader;

import benja.msvc_ventas.modelo.Venta;
import benja.msvc_ventas.repositorio.VentaRepositorio;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Profile("dev")
@Component
public class LoadDatabase implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    VentaRepositorio ventaRepositorio;

    private final Random random = new Random();

    @Override
    public void run(String... args) {
        Faker faker = new Faker(new Locale("es", "CL"));

        if (ventaRepositorio.count() == 0) {
            for (int i = 0; i < 50; i++) {
                Long productoId = (long) (random.nextInt(10) + 1); // IDs ficticios
                int cantidad = faker.number().numberBetween(1, 5);
                double precio = faker.number().randomDouble(2, 5000, 30000);
                double total = cantidad * precio;

                Venta venta = Venta.builder()
                        .productoId(productoId)
                        .cantidad(cantidad)
                        .total(total)
                        .build();

                ventaRepositorio.save(venta);
                log.info("Venta creada: {}", venta);
            }
        }
    }
}

