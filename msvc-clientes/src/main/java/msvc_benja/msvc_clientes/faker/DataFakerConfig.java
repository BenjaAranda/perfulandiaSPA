package msvc_benja.msvc_clientes.faker;

import msvc_benja.msvc_clientes.modelo.Cliente;
import msvc_benja.msvc_clientes.repositorio.ClienteRepositorio;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Profile("dev")
@Component
public class DataFakerConfig implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataFakerConfig.class);
    private final ClienteRepositorio clienteRepositorio;

    public DataFakerConfig(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public void run(String... args) {
        if (clienteRepositorio.count() == 0) {
            Faker faker = new Faker(new Locale("es", "CL"));

            for (int i = 0; i < 30; i++) {
                String telefonoValido = faker.number().digits(9); // entre 8 y 15 dígitos
                Cliente cliente = Cliente.builder()
                        .rut(faker.idNumber().valid())
                        .nombre(faker.name().firstName())
                        .apellido(faker.name().lastName())
                        .correo(faker.internet().emailAddress())
                        .telefono(telefonoValido)
                        .direccion(faker.address().fullAddress())
                        .build();

                clienteRepositorio.save(cliente);
                log.info("Cliente creado: {} {}", cliente.getNombre(), cliente.getApellido());
            }
        }
    }
}

