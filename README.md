# Validación de Seguros — Microservicios Java

## 1. Descripción del proyecto

Proyecto desarrollado en Java con Spring Boot para consultar información de seguros y validar si un cliente cumple los requisitos de elegibilidad para contratar un seguro.

La solución está compuesta por dos microservicios independientes que se comunican mediante OpenFeign.

## 2. Arquitectura

```text
                  Cliente / Postman
                         |
                         v
             mso-ts-validaciones-v1
                  Puerto 8082
                         |
                      OpenFeign
                         |
                         v
            mso-es-consulta-seguros-v1
                  Puerto 8081
                         |
                         v
                Catálogo de seguros
                 En memoria (RAM)
```

### Microservicio de consulta de seguros

**Nombre:** `mso-es-consulta-seguros-v1`

Responsabilidades:
- Consultar todos los seguros disponibles.
- Consultar un seguro específico por su identificador.
- Proporcionar los requisitos de edad y género para la validación.

### Microservicio de validaciones

**Nombre:** `mso-ts-validaciones-v1`

Responsabilidades:
- Recibir los datos del cliente y el identificador del seguro.
- Consultar los requisitos del seguro mediante OpenFeign.
- Calcular la edad del cliente a partir de su fecha de nacimiento.
- Validar edad y género cuando corresponda.
- Responder si el cliente es elegible o no.
- Gestionar errores de validación y de comunicación entre servicios.

## 3. Tecnologías utilizadas

- Java 17
- Spring Boot
- Maven
- Spring Web
- Jakarta Bean Validation
- OpenFeign
- Lombok
- JUnit y Mockito
- Docker y Docker Compose
- Postman

## 4. Seguros disponibles

| ID | Seguro | Costo anual | Edad mínima | Edad máxima |
|---|---|---:|---:|---:|
| 1 | VIDA | $700 | 18 | 75 |
| 2 | INFARTO | $400 | 15 | 64 |
| 3 | MUJER | $300 | 15 | 64 |

El seguro MUJER requiere que el identificador de género sea `2`.

Identificadores de género:
- `1`: Hombre
- `2`: Mujer

Los costos corresponden a los datos definidos en el ejercicio.

## 5. Requisitos previos

Para ejecutar el proyecto se requiere:

- Docker instalado y en ejecución.
- Docker Compose disponible mediante `docker compose`.
- Postman o una herramienta equivalente para probar las API.

Java y Maven se utilizan para compilar y ejecutar las pruebas localmente. Para levantar los contenedores ya construidos, basta con Docker y Docker Compose.

## 6. Ejecución con Docker Compose

Clonar o descargar el repositorio y abrir una terminal en la carpeta raíz, donde se encuentra `compose.yaml`.

Construir las imágenes e iniciar ambos microservicios:

```bash
docker compose up --build -d
```

Verificar el estado de los contenedores:

```bash
docker compose ps
```

Consultar los registros de ejecución:

```bash
docker compose logs
```

Para detener los servicios:

```bash
docker compose down
```

## 7. Endpoints

### 7.1. Consultar todos los seguros

**Método:** `GET`

```http
http://localhost:8081/api/seguros
```

Respuesta esperada: `200 OK`.

### 7.2. Consultar un seguro por ID

**Método:** `GET`

```http
http://localhost:8081/api/seguros/1
```

Respuesta esperada: `200 OK` si el seguro existe.

Códigos de respuesta:
- `200 OK`: consulta exitosa.
- `400 Bad Request`: identificador inválido.
- `404 Not Found`: seguro inexistente.

### 7.3. Validar elegibilidad de un cliente

**Método:** `POST`

```http
http://localhost:8082/api/validaciones
```

**Encabezado:**

```http
Content-Type: application/json
```

**Cuerpo de ejemplo:**

```json
{
  "idSeguro": 1,
  "nombre": "Juan",
  "apellido": "Perez",
  "fechaNacimiento": "1995-05-20",
  "idGenero": 1
}
```

Respuesta esperada para un cliente elegible:

```json
{
  "elegible": true,
  "mensaje": "El cliente cumple con los requisitos del seguro VIDA."
}
```

Códigos de respuesta:
- `200 OK`: el cliente cumple con los requisitos.
- `400 Bad Request`: los datos enviados son inválidos.
- `401 Unauthorized`: el cliente no cumple con los requisitos del seguro, según las reglas de este ejercicio.
- `404 Not Found`: el seguro solicitado no existe.
- `503 Service Unavailable`: no fue posible consultar el microservicio de seguros.

## 8. Pruebas

Se realizaron pruebas de los siguientes escenarios:

- Consulta de todos los seguros.
- Consulta individual de cada seguro.
- Consulta de un identificador inexistente.
- Envío de un identificador inválido.
- Validación exitosa de un cliente.
- Cliente que no cumple los requisitos de edad.
- Cliente que no cumple el requisito de género.
- Campos obligatorios inválidos.
- Seguro inexistente durante la validación.
- Indisponibilidad del microservicio de consulta.

También se implementaron pruebas unitarias con JUnit y Mockito para verificar la lógica de negocio y el manejo de excepciones.

Para ejecutar las pruebas de cada proyecto localmente, entrar a la carpeta correspondiente y ejecutar:

```bash
.\mvnw.cmd test
```

## 9. Organización del código

Los proyectos utilizan una organización por responsabilidades:

- `controller`: recepción y respuesta de peticiones HTTP.
- `service`: lógica de negocio.
- `repository`: acceso al catálogo de seguros.
- `model`: modelo de dominio.
- `dto`: objetos de transferencia de datos.
- `exception`: excepciones y manejo centralizado de errores.
- `client`: cliente OpenFeign para la comunicación entre microservicios, en el servicio de validaciones.

El catálogo de seguros se almacena en memoria, ya que el ejercicio no requiere persistencia en una base de datos.

## 10. Objetivo técnico

Aplicar principios de desarrollo de microservicios, separación de responsabilidades, validación de datos, comunicación HTTP entre servicios, manejo de excepciones, pruebas unitarias y ejecución en contenedores Docker.