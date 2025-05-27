package com.example.msvc.atencion_cliente.service;

import com.example.msvc.atencion_cliente.model.Atencion;

import java.util.List;
import java.util.Optional;

public interface AtencionService {
    Atencion crearAtencion(Atencion atencion);
    Optional<Atencion> obtenerAtencion(Long id);
    List<Atencion> listarAtenciones();
    List<Atencion> listarPorEstado(String estado);
    Atencion actualizarEstado(Long id, String nuevoEstado);
}
