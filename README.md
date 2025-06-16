# Liga de Fútbol Chad

## Descripción

Liga de Fútbol Chad es un sistema de gestión para una liga de fútbol amateur, desarrollado en Java bajo el paradigma de Programación Orientada a Objetos y con estructura Maven. Permite registrar jugadores titulares y suplentes, crear equipos, registrar partidos, asignar goles y minutos jugados, transferir jugadores, calcular estadísticas y exportar datos a CSV.

## Funcionalidades principales

- Registrar jugadores titulares o suplentes (con validaciones de nombre y edad).
- Crear equipos e incorporar jugadores.
- Registrar partidos entre equipos.
- Asignar goles y minutos jugados a jugadores durante un partido.
- Transferir jugadores entre equipos.
- Mostrar listado de todos los jugadores y su tipo (titular o suplente).
- Calcular y mostrar el goleador de la liga.
- Mostrar el promedio de goles por partido de cada equipo.
- Mostrar el ranking de equipos por cantidad de goles anotados.
- Mostrar jugadores suplentes que nunca hayan ingresado.
- Mostrar el jugador titular con mayor cantidad de minutos jugados.
- Exportar en un archivo .csv los jugadores de un equipo dado.
- Reportes generales y por equipo (total de goles, jugador más eficiente, minutos jugados, etc).

## Estructura del proyecto

- `modelo/`: Clases de dominio (Jugador, Titular, Suplente, Equipo, Partido).
- `servicios/`: Lógica de negocio y utilidades (servicios de equipo, jugador, partido, estadísticas, reportes).
- `util/`: Utilidades y validadores.
- `app/`: Clase principal con menú interactivo por consola.
- `test/`: Pruebas unitarias.

## Requisitos técnicos

- Java 17+ (o compatible)
- Maven

## Ejecución

1. Clona el repositorio.
2. Ejecuta `mvn clean install` para compilar.
3. Ejecuta la aplicación desde la clase `Main`.

## Consideraciones

- El sistema valida los datos de entrada (nombre, edad, minutos, etc).
- El menú es interactivo y permite navegar por todas las funcionalidades.
- Los datos se mantienen en memoria durante la ejecución.
- El archivo CSV exporta los jugadores de un equipo con la cabecera solicitada.

## Diagrama de clases

El proyecto incluye un archivo `.puml` para visualizar el diagrama de clases con PlantUML.

---

Desarrollado para el Trabajo Práctico Final JAVA - Informatorio 2025.
