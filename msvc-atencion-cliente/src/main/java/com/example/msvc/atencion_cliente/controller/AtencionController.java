package com.example.msvc.atencion_cliente.controller;

import com.example.msvc.atencion_cliente.model.Atencion;
import com.example.msvc.atencion_cliente.service.AtencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/atenciones")
public class AtencionController {

    @Autowired
    private AtencionService servicio;

    @PostMapping
    public ResponseEntity<Atencion> crear(@RequestBody Atencion atencion) {
        return new ResponseEntity<>(servicio.crearAtencion(atencion), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Atencion> listar() {
        return servicio.listarAtenciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atencion> obtener(@PathVariable Long id) {
        return servicio.obtenerAtencion(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public List<Atencion> porEstado(@PathVariable String estado) {
        return servicio.listarPorEstado(estado);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Atencion> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        if (!body.containsKey("estado")) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(servicio.actualizarEstado(id, body.get("estado")));
    }
}
