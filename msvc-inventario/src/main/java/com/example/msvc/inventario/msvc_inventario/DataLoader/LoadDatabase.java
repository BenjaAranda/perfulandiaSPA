package com.example.msvc.inventario.msvc_inventario.DataLoader;

import com.example.msvc.inventario.msvc_inventario.modelo.Producto;
import com.example.msvc.inventario.msvc_inventario.repositorio.ProductoRepositorio;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Profile("dev")
@Component
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private ProductoRepositorio repositorio;

    @Override
    public void run(String... args) throws Exception {
        if (repositorio.count() == 0) {
            Faker faker = new Faker(new Locale("es", "CL"));

            for (int i = 0; i < 100; i++) {
                Producto producto = Producto.builder()
                        .nombre(faker.commerce().productName() + " - " + faker.commerce().material())
                        .descripcion(faker.lorem().sentence())
                        .stock(faker.number().numberBetween(5, 50))
                        .precio(faker.number().randomDouble(2, 10000, 50000))
                        .build();
                repositorio.save(producto);
            }
        }
    }
}


