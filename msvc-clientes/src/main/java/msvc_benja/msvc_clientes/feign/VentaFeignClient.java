package msvc_benja.msvc_clientes.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-ventas", url = "http://localhost:8085") // Ajusta el puerto si es necesario
public interface VentaFeignClient {

    @GetMapping("/api/ventas/cliente/{id}")
    boolean tieneVentas(@PathVariable Long id);
}
