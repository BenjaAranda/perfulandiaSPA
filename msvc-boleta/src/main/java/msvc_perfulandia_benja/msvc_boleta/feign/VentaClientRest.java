package msvc_perfulandia_benja.msvc_boleta.feign;

import msvc_perfulandia_benja.msvc_boleta.dto.ItemBoletaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-ventas", url = "http://localhost:8082/api/ventas")
public interface VentaClientRest {

    @GetMapping("/cliente/{clienteId}")
    List<ItemBoletaDTO> obtenerItemsPorCliente(@PathVariable Long clienteId);
}
