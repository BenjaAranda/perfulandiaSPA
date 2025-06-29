package msvc_benja.msvc_clientes.repositorio;

import msvc_benja.msvc_clientes.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByRut(String rut);
    boolean existsByRut(String rut);

}

