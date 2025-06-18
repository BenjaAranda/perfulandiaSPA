package com.example.msvc.inventario.msvc_inventario.DataLoader;

import com.example.msvc.inventario.msvc_inventario.modelo.Producto;
import com.example.msvc.inventario.msvc_inventario.repositorio.ProductoRepositorio;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Locale;
import java.util.Random;

@Configuration
public class DataFakerConfig {

    private final List<String> nombresPerfumes = List.of(
            "Essence of Dreams", "Noir Mystique", "Bloom Spirit", "Velvet Rose",
            "Amber Night", "Ocean Whisper", "Wild Jasmine", "Citrus Flame",
            "Vanilla Woods", "Midnight Bloom"
    );

    private final List<String> notas = List.of(
            "rosas silvestres", "jazmín árabe", "vainilla de Madagascar",
            "madera de cedro", "ámbar", "almizcle blanco", "bergamota", "pachulí"
    );

    @Bean
    public CommandLineRunner cargarPerfumes(ProductoRepositorio repositorio) {
        return args -> {
            if (repositorio.count() == 0) {
                Faker faker = new Faker(new Locale("es"));
                Random random = new Random();

                for (int i = 0; i < 10; i++) {
                    String nombre = nombresPerfumes.get(random.nextInt(nombresPerfumes.size()));
                    String notaPrincipal = notas.get(random.nextInt(notas.size()));
                    String descripcion = "Fragancia con notas de " + notaPrincipal +
                            ", perfecta para uso diario o eventos especiales.";

                    Producto perfume = Producto.builder()
                            .nombre(nombre)
                            .descripcion(descripcion)
                            .stock(faker.number().numberBetween(10, 100))
                            .precio(faker.number().randomDouble(2, 9990, 99990))
                            .build();

                    repositorio.save(perfume);
                }

                System.out.println("Perfumes generados con Faker.");
            }
        };
    }
}


