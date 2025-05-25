package com.example.msvc.inventario.msvc_inventario.configuracion;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.example.msvc.inventario.msvc_inventario.configuracion")
public class ConfiguracionOpenFeign {
}