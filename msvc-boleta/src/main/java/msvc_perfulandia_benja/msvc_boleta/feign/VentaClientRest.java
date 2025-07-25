package msvc_perfulandia_benja.msvc_boleta.feign;

import msvc_perfulandia_benja.msvc_boleta.dto.VentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-ventas", url = "http://localhost:8085/api/v1/ventas")
public interface VentaClientRest {

    @GetMapping("/{id}")
    VentaDTO obtenerVentaPorId(@PathVariable Long id);

    @GetMapping("/ultima/producto/{productoId}")
    VentaDTO obtenerUltimaVentaPorProductoId(@PathVariable("productoId") Long productoId);
}
