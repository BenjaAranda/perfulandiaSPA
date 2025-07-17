package msvc_perfulandia_benja.msvc_boleta.feign;

import msvc_perfulandia_benja.msvc_boleta.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-inventario", url = "http://localhost:8081/api/v1/productos")
public interface ProductoClientRest {

    @GetMapping("/{id}")
    ProductoDTO obtenerProductoPorId(@PathVariable Long id);
}
