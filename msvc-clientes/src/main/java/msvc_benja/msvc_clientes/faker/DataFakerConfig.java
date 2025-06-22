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
        Faker faker = new Faker(new Locale("es", "CL"));

        if (clienteRepositorio.count() == 0) {
            for (int i = 0; i < 50; i++) {
                Cliente cliente = Cliente.builder()
                        .nombre(faker.name().fullName())
                        .correo(faker.internet().emailAddress())
                        .telefono("+56 9 " + faker.number().digits(8))
                        .build();
                clienteRepositorio.save(cliente);
                log.info("Cliente creado: {}", cliente);
            }
        }
    }
}
