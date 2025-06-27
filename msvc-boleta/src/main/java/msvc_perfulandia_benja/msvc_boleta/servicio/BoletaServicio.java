package msvc_perfulandia_benja.msvc_boleta.servicio;

import msvc_perfulandia_benja.msvc_boleta.dto.BoletaDTO;

import java.util.List;

public interface BoletaServicio {
    List<BoletaDTO> listar();
    BoletaDTO obtenerPorId(Long id);
    BoletaDTO guardar(BoletaDTO boleta);
    void eliminar(Long id);
}


