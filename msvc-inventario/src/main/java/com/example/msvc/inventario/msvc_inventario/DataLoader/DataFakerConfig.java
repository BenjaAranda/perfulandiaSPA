package com.example.msvc.inventario.msvc_inventario.DataLoader;

import com.example.msvc.inventario.msvc_inventario.modelo.Producto;
import com.example.msvc.inventario.msvc_inventario.repositorio.ProductoRepositorio;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Profile("dev")
@Component
public class DataFakerConfig implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataFakerConfig.class);
    private final ProductoRepositorio productoRepositorio;

    public DataFakerConfig(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    @Override
    public void run(String... args) {
        if (productoRepositorio.count() == 0) {
            Faker faker = new Faker(new Locale("es", "CL"));

            List<String> perfumesFamosos = List.of(
                    // Diseñador
                    "Dior Sauvage", "Bleu de Chanel", "Acqua di Gio", "Versace Eros", "Yves Saint Laurent Y",
                    "Dolce & Gabbana The One", "Carolina Herrera 212", "Givenchy Gentleman", "Paco Rabanne Invictus", "Armani Code",

                    // Nicho
                    "Creed Aventus", "Maison Francis Kurkdjian Baccarat Rouge 540", "Parfums de Marly Layton",
                    "Initio Oud for Greatness", "Amouage Interlude", "Byredo Gypsy Water", "Nishane Hacivat", "Memo Irish Leather",

                    // Árabes
                    "Lattafa Raghba", "Ard Al Zaafaran Oud 24 Hours", "Swiss Arabian Shaghaf Oud", "Ajmal Amber Wood",
                    "Al Haramain Amber Oud", "Lattafa Asad", "Rasasi Hawas", "Al Rehab Silver", "Afnan Supremacy Not Only Intense"
            );

            for (int i = 0; i < 30; i++) {
                String nombre = faker.options().option(perfumesFamosos.toArray(new String[0]));
                String descripcion = faker.lorem().sentence();

                Producto producto = Producto.builder()
                        .nombre(nombre)
                        .descripcion(descripcion)
                        .stock(faker.number().numberBetween(5, 50))
                        .precio(faker.number().randomDouble(2, 15990, 89990))
                        .build();

                productoRepositorio.save(producto);
                log.info("Producto creado: {}", nombre);
            }
        }
    }
}

