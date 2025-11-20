# TurnoSmart

## Descripción
**TurnoSmart** es un sistema desarrollado para la **gestión de turnos y citas**, pensado para facilitar la organización entre usuarios y administradores.
Permite registrar, visualizar y actualizar turnos de manera sencilla, optimizando el proceso de atención.

## Funcionalidades Principales
- **Gestión de turnos:** Creación, seguimiento y actualización del estado de turnos y citas.

## Tecnologías Utilizadas
## Tecnologías Utilizadas
- **Frontend:** Vue.js con Vite  
- **Backend:** Spring Boot (Java 17)  
- **Base de Datos:** PostgreSQL  
- **Herramientas adicionales:** Docker, Maven  

## Estructura del Proyecto
El proyecto está organizado en dos carpetas principales:
- **backend/** → Lógica de negocio y API REST con Spring Boot.  
- **frontend/** → Interfaz de usuario desarrollada en Vue.js.  

---

## Instalación y Configuración

### Requisitos Previos
Asegúrese de tener instalado lo siguiente:

- [Java 17+](https://adoptium.net/)  
- [Maven 3.9+](https://maven.apache.org/) (el proyecto incluye wrapper `mvnw`)  
- [Node.js (LTS)](https://nodejs.org/)  
- [npm](https://www.npmjs.com/) o [yarn](https://yarnpkg.com/)  
- [PostgreSQL 15+](https://www.postgresql.org/download/) o Docker  
- Git  

Verificar instalaciones:
```sh
java -version
mvn -v
node -v
npm -v
psql --version
```

## 1. Clonar el Repositorio

SSH
```sh
git clone git@github.com:MarioPG6/TurnoSmart.git
cd TurnoSmart
```

HTTPS
```sh
git clone https://github.com/MarioPG6/TurnoSmart.git
cd TurnoSmart
```

## 2. Configuración de Base de Datos (PostgreSQL)

# Opción A — Local
Ingresar a PostgreSQL (psql) y crear la base:
```sh
CREATE DATABASE db_TurnoSmart;
```
# Opción B — Docker
```sh
docker run --name logitrack-postgres -e POSTGRES_DB=db_TurnoSmart \
-e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=1348MP \
-p 5432:5432 -d postgres:15
```
# 3. Configuración del Backend (Spring Boot)

Ruta del backend:
```sh
TurnoSmart/backend
```

Archivo src/main/resources/application.properties:
```sh
spring.application.name=TurnoSmart
spring.datasource.url=jdbc:postgresql://localhost:5432/db_TurnoSmart
spring.datasource.username=postgres
spring.datasource.password="your_password"
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
```

## 4. Configuración del Frontend (Vue + Vite)

Ruta del frontend:
```sh
TurnoSmart/frontend
```

Instalar dependencias:
```sh
cd frontend
npm install
```

Ejecutar en modo desarrollo:
```sh
npm run dev
```
Disponible en: http://localhost:5173


