package benja.msvc_ventas.feign;

import benja.msvc_ventas.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-inventario", url = "http://localhost:8081/api/productos")
public interface ProductoClienteRest {

    @GetMapping("/{id}")
    ProductoDTO obtenerProductoPorId(@PathVariable Long id);
}
