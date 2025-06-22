package msvc_benja.msvc_clientes.repositorio;

import msvc_benja.msvc_clientes.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
}
