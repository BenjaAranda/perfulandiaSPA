package msvc_perfulandia_benja.msvc_boleta.feign;

import msvc_perfulandia_benja.msvc_boleta.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-clientes", url = "http://localhost:8083/api/clientes")
public interface ClienteClientRest {

    @GetMapping("/{id}")
    ClienteDTO buscarPorId(@PathVariable Long id);
}
