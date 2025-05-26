// VentaFeignClient.java
package com.facturacion.clients;

import com.facturacion.DTO.VentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-ventas", url = "http://localhost:8081")
public interface VentaFeignClient {
    @GetMapping("/api/ventas/{id}")
    VentaDTO obtenerVenta(@PathVariable Long id);