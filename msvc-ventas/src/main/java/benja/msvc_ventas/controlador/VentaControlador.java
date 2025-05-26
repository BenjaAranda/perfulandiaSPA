package benja.msvc_ventas.controlador;

import benja.msvc_ventas.dto.VentaDTO;
import benja.msvc_ventas.servicio.VentaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaControlador {

    private final VentaServicio servicio;

    public VentaControlador(VentaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<VentaDTO>> listar() {
        return ResponseEntity.ok(servicio.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<VentaDTO> guardar(@RequestBody VentaDTO dto) {
        return ResponseEntity.ok(servicio.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizar(@PathVariable Long id, @RequestBody VentaDTO dto) {
        return ResponseEntity.ok(servicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
