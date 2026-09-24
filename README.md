# Perfulandia SPA · Microservicios

Backend académico para una perfumería, organizado en microservicios independientes para clientes, inventario, ventas y emisión de boletas.

## Arquitectura

| Servicio | Puerto | Responsabilidad |
|---|---:|---|
| `msvc-inventario` | 8081 | Productos y stock |
| `msvc-clientes` | 8083 | Gestión de clientes |
| `msvc-ventas` | 8085 | Registro de ventas |
| `msvc-boleta` | 8086 | Boletas y detalle de compra |

Los servicios se comunican mediante OpenFeign y exponen APIs REST documentadas con OpenAPI/Swagger. El perfil de desarrollo usa bases H2 locales.

## Tecnologías

- Java 21
- Spring Boot 3.4
- Spring Data JPA
- Spring Cloud OpenFeign
- H2 Database
- Springdoc OpenAPI
- JUnit y Mockito
- Maven

## Ejecución local

Requisitos: Java 21 y Maven.

```bash
mvn clean install
```

Inicia cada microservicio en una terminal diferente:

```bash
mvn spring-boot:run -pl msvc-inventario
mvn spring-boot:run -pl msvc-clientes
mvn spring-boot:run -pl msvc-ventas
mvn spring-boot:run -pl msvc-boleta
```

La documentación Swagger de cada servicio está disponible en `/swagger-ui.html`, por ejemplo:

- `http://localhost:8081/swagger-ui.html`
- `http://localhost:8083/swagger-ui.html`
- `http://localhost:8085/swagger-ui.html`
- `http://localhost:8086/swagger-ui.html`

## Pruebas

```bash
mvn test
```

Proyecto desarrollado con fines académicos.
