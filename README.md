# API Core - Gestión de Precompromisos 🏛️

API RESTful transaccional diseñada para gestionar el ciclo de vida presupuestal de los precompromisos institucionales. Este componente actúa como el motor central de reglas de negocio, garantizando la inmutabilidad de la auditoría y la integridad de los datos presupuestarios.

## 🚀 Tecnologías y Arquitectura

Este proyecto está construido bajo una arquitectura limpia orientada a dominio (Package by Feature) y utiliza el siguiente stack tecnológico:

* **Lenguaje:** Java 25 (LTS)
* **Framework:** Spring Boot 4.0.x
* **Seguridad:** Spring Security + JSON Web Tokens (JJWT 0.12.6)
* **Persistencia:** Spring Data JDBC (`JdbcTemplate` / `SimpleJdbcCall`)
* **Base de Datos:** Oracle 11g (Driver `ojdbc8`) mediante Stored Procedures y `SYS_REFCURSOR`.
* **Documentación:** SpringDoc OpenAPI 3 (Swagger)
* **Servidor:** Tomcat embebido (Empaquetado WAR para despliegues On-Premise)

## 🔐 Flujo de Autenticación (Híbrido)

La API implementa un patrón de **Autenticación Delegada (OIDC) con Emisión de Token Interno**.
1.  El Frontend (SPA) delega el inicio de sesión a **Microsoft Entra ID**.
2.  El Frontend envía el `idToken` de Microsoft al endpoint `/api/v1/auth/login-microsoft`.
3.  La API decodifica el token, extrae el correo institucional y consulta la base de datos Oracle para validar roles y permisos.
4.  Si el usuario es válido, la API emite y firma un **JWT Local** que contiene el rol y las unidades ejecutoras permitidas.
5.  Las subsecuentes peticiones utilizan este JWT local mediante el header `Authorization: Bearer <token>`.

## 📂 Estructura del Proyecto (Package by Feature)

```text
mx.gob.senado.tesoreria
├── config/             # Configuraciones globales (Swagger, CORS)
├── exception/          # Manejo global de errores (GlobalExceptionHandler)
├── security/           # Cadenero: JwtTokenProvider, Filtros, AuthController
└── modules/
    ├── usuarios/       # Conexión con Oracle para datos de sesión y unidades
    ├── catalogos/      # Lectura de claves presupuestarias y configuraciones DGPP
    └── precompromisos/ # Motor transaccional (CRUD y Gestión de Estados)