package benja.msvc_ventas.servicio;

import benja.msvc_ventas.dto.VentaDTO;

import java.util.List;

public interface VentaServicio {

    List<VentaDTO> listar();
    VentaDTO obtenerPorId(Long id);
    VentaDTO guardar(VentaDTO dto);
    VentaDTO actualizar(Long id, VentaDTO dto);
    void eliminar(Long id);

    VentaDTO obtenerUltimaVentaPorProductoId(Long productoId);

}
