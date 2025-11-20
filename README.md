TurnoSmart
Descripción

TurnoSmart es un sistema desarrollado para la gestión de turnos y citas, pensado para facilitar la organización entre usuarios y administradores.
Permite registrar, visualizar y actualizar turnos de manera sencilla, optimizando el proceso de atención.

Funcionalidades Principales

Gestión de turnos: Creación, seguimiento y actualización del estado de turnos y citas.

Monitoreo en tiempo real: Visualización del estado de los turnos activos.

Panel administrativo: Gestión de usuarios y áreas de atención.

Reportes: Información básica sobre turnos registrados.

Notificaciones: Alertas simples sobre cambios en los estados de los turnos.

Tecnologías Utilizadas

Frontend: Vue.js con Vite

Backend: Spring Boot (Java 17)

Base de Datos: PostgreSQL

Herramientas adicionales: Docker, Maven

Estructura del Proyecto

El proyecto está organizado en dos carpetas principales:

backend/ → Lógica de negocio y API REST con Spring Boot.

frontend/ → Interfaz de usuario desarrollada en Vue.js.

Instalación y Configuración
Requisitos Previos

Asegúrese de tener instalado lo siguiente:

Java 17+

Maven 3.9+
 (el proyecto incluye wrapper mvnw)

Node.js (LTS)

npm
 o yarn

PostgreSQL 15+
 o Docker

Git

Verificar instalaciones:

java -version
mvn -v
node -v
npm -v
psql --version

1. Clonar el Repositorio

SSH

git clone git@github.com:MarioPG6/TurnoSmart.git
cd TurnoSmart


HTTPS

git clone https://github.com/MarioPG6/TurnoSmart.git
cd TurnoSmart

2. Configuración de Base de Datos (PostgreSQL)
Opción A — Local

Ingresar a PostgreSQL (psql) y crear la base:

CREATE DATABASE db_turnosmart;

Opción B — Docker
docker run --name turnosmart-postgres -e POSTGRES_DB=db_turnosmart \
-e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=1348MP \
-p 5432:5432 -d postgres:15

3. Configuración del Backend (Spring Boot)

Ruta del backend:

TurnoSmart/backend


Archivo src/main/resources/application.properties:

spring.application.name=turnosmart
spring.datasource.url=jdbc:postgresql://localhost:5432/db_turnosmart
spring.datasource.username=postgres
spring.datasource.password="your_password"
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update

4. Configuración del Frontend (Vue + Vite)

Ruta del frontend:

TurnoSmart/frontend


Instalar dependencias:

cd frontend
npm install


Ejecutar en modo desarrollo:

npm run dev


Disponible en: http://localhost:5173
