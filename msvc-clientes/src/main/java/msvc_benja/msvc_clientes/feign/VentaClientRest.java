package msvc_benja.msvc_clientes.feign;

import msvc_benja.msvc_clientes.dto.VentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-ventas", url = "localhost:8082/api/ventas")
public interface VentaClientRest {

    @GetMapping("/cliente/{clienteId}")
    List<VentaDTO> findByClienteId(@PathVariable("clienteId") Long clienteId);

}
