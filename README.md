# Sistema de Gestión de Fórmula 1

## Descripción

Sistema de gestión integral para campeonatos de Fórmula 1 desarrollado en Java utilizando Swing. La aplicación permite administrar todos los aspectos relacionados con un campeonato de F1, incluyendo pilotos, escuderías, autos, mecánicos, circuitos y carreras.

## Arquitectura

### Patrón MVC

El sistema implementa el patrón Modelo-Vista-Controlador:

- **Modelo**: Clases de entidad del dominio F1
- **Vista**: Interfaces gráficas en Java Swing
- **Controlador**: Lógica de negocio y validaciones

### Estructura del Proyecto

```
src/
├── modelo/           # Clases de dominio
│   ├── Pais.java
│   ├── Escuderia.java
│   ├── Piloto.java
│   ├── Auto.java
│   ├── Mecanico.java
│   ├── Circuito.java
│   ├── GranPremio.java
│   ├── Participacion.java
│   └── SistemaPuntuacion.java
├── vista/            # Interfaz gráfica
│   ├── VentanaPrincipal.java
│   ├── VentanaPilotos.java
│   ├── VentanaEscuderias.java
│   └── [otras ventanas]
└── controlador/      # Lógica de negocio
    ├── GestorFormula1.java
    ├── ValidadorFormula1.java
    └── DatosEjemplo.java
```

## Funcionalidades

### Implementadas

- ✅ **Gestión de Pilotos**: CRUD completo con validaciones
- ✅ **Gestión de Escuderías**: Administración de equipos y asignaciones
- ✅ **Sistema de Puntuación**: Implementación oficial F1
- ✅ **Controles de Integridad**: Validaciones de consistencia de datos

### Validaciones de Integridad

- Control de números únicos de pilotos (1-99)
- Validación de asignación única de autos por carrera
- Control de vinculación piloto-escudería por período
- Sistema de puntuación oficial (1º=25, 2º=18, 3º=15, etc.)
- Validación de posiciones únicas en carreras

## Clases Principales

### Modelo

- **Piloto**: Datos personales, número, experiencia, escudería
- **Escuderia**: Equipos con pilotos, autos y mecánicos
- **Auto**: Vehículos con especificaciones técnicas
- **GranPremio**: Eventos de carrera con participaciones
- **SistemaPuntuacion**: Cálculo de puntos oficial F1

### Controlador

- **GestorFormula1**: Controlador principal del sistema
- **ValidadorFormula1**: Validaciones de reglas de negocio

### Vista

- **VentanaPrincipal**: Menú principal de navegación
- **VentanaPilotos**: Gestión completa de pilotos

## Requisitos

- **Java**: JDK 8 o superior
- **GUI**: Swing (incluido en Java)
- **OS**: Windows, Linux, macOS

## Instalación y Ejecución

### Compilación

```bash
javac -d bin -sourcepath src src/vista/VentanaPrincipal.java
```

### Ejecución

```bash
java -cp bin vista.VentanaPrincipal
```

### Ejecución Windows

```bash
ejecutar.bat
```

## Uso

### Pantalla Principal

Al ejecutar la aplicación se presenta el menú principal con opciones para gestionar:

1. **Pilotos** - Gestión completa implementada
2. **Escuderías** - Funcionalidad base implementada
3. **Autos** - En desarrollo
4. **Mecánicos** - En desarrollo
5. **Circuitos** - En desarrollo
6. **Carreras** - En desarrollo
7. **Reportes** - En desarrollo

### Gestión de Pilotos

- **Agregar**: Registro de nuevos pilotos con validaciones
- **Modificar**: Edición de datos existentes
- **Eliminar**: Remoción con controles de integridad
- **Consultar**: Visualización de información y estadísticas

## Datos de Ejemplo

El sistema incluye datos precargados:

- **6 Países**: Italia, España, Inglaterra, Alemania, Francia, Mónaco
- **3 Escuderías**: Ferrari, Red Bull Racing, Mercedes-AMG
- **6 Pilotos**: Leclerc, Sainz, Verstappen, Pérez, Hamilton, Russell
- **3 Circuitos**: Monza, Silverstone, Monaco

## Documentación

Todo el código está documentado con estándares JavaDoc incluyendo:

- Descripción de clases y responsabilidades
- Parámetros y valores de retorno de métodos
- Ejemplos de uso y restricciones

## Licencia

Proyecto académico desarrollado para el Taller de Programación Orientada a Objetos.

---

**Versión**: 1.0.0  
**Fecha**: Noviembre 2025
