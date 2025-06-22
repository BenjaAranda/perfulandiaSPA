package msvc_benja.msvc_clientes.servicio;

import msvc_benja.msvc_clientes.dto.ClienteDTO;

import java.util.List;

public interface ClienteServicio {
    List<ClienteDTO> listar();
    ClienteDTO obtenerPorId(Long id);
    ClienteDTO guardar(ClienteDTO dto);
    ClienteDTO actualizar(Long id, ClienteDTO dto);
    void eliminar(Long id);
}
