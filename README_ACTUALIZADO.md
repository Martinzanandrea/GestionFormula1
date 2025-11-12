# 🏎️ Sistema de Gestión de Fórmula 1

**Escuderías Unidas - Temporada 2024**

Un sistema completo de gestión para campeonatos de Fórmula 1 desarrollado en Java con interfaz gráfica moderna e intuitiva.

## 🎯 Características Principales

### 📋 Gestión Completa de Entidades

- **👨‍✈️ Pilotos**: Registro, estadísticas y gestión de números únicos
- **🏁 Escuderías**: Administración de equipos con recursos asignados
- **🏎️ Autos**: Control de inventario y asignación a equipos
- **🔧 Mecánicos**: Gestión por especialidades técnicas
- **🏁 Circuitos**: Administración de pistas con estadísticas de uso

### 🏆 Sistema Avanzado de Carreras

- **Planificación**: Creación de Grandes Premios con fecha, hora y circuito
- **Inscripciones**: Registro de pilotos con validación automática
- **Resultados**: Captura de posiciones, abandonos y vuelta más rápida
- **Puntuación F1**: Sistema oficial (25, 18, 15, 12, 10, 8, 6, 4, 2, 1)

### 📊 Sistema Integral de Reportes

- **Rankings**: Clasificaciones de pilotos y escuderías por puntos
- **Estadísticas Detalladas**: Historial completo por piloto y equipo
- **Análisis de Rendimiento**: Podios, victorias y participaciones
- **Historial de Carreras**: Registro completo de Grandes Premios
- **Dashboard**: Resumen general del sistema

## 🚀 Interfaz Moderna

### ✨ Características de la Nueva UI

- **🎨 Diseño Moderno**: Cards con gradientes y efectos hover
- **📱 Responsive**: Interfaz adaptable y redimensionable
- **🎯 Intuitiva**: Navegación clara con iconos descriptivos
- **📈 Información en Tiempo Real**: Panel lateral con estadísticas actualizadas
- **🎨 Paleta F1**: Colores inspirados en la Fórmula 1

### 🛠️ Mejoras Técnicas

- **Manejo de Errores**: Validaciones y mensajes informativos
- **Estabilidad**: Corrección de excepciones ArrayIndexOutOfBounds
- **Rendimiento**: Carga asíncrona de ventanas
- **Usabilidad**: Confirmaciones de acciones críticas

## 🏗️ Arquitectura

### 📁 Estructura MVC

```
src/
├── modelo/           # Clases de dominio (9 clases)
│   ├── Pais.java
│   ├── Escuderia.java
│   ├── Piloto.java
│   ├── Auto.java
│   ├── Mecanico.java
│   ├── Circuito.java
│   ├── GranPremio.java
│   ├── Participacion.java
│   └── SistemaPuntuacion.java
├── vista/            # Interfaces gráficas (7 ventanas)
│   ├── VentanaPrincipal.java     # Dashboard principal
│   ├── VentanaPilotos.java       # Gestión de pilotos
│   ├── VentanaEscuderias.java    # Gestión de equipos
│   ├── VentanaAutos.java         # Gestión de vehículos
│   ├── VentanaMecanicos.java     # Gestión de personal
│   ├── VentanaCircuitos.java     # Gestión de pistas
│   ├── VentanaCarreras.java      # Gestión de carreras
│   └── VentanaReportes.java      # Sistema de reportes
└── controlador/      # Lógica de negocio
    ├── GestorFormula1.java       # Gestor principal
    └── DatosEjemplo.java         # Datos de prueba
```

## 🔧 Instalación y Ejecución

### Requisitos

- Java 8 o superior
- Sistema operativo: Windows, macOS, Linux

### 🚀 Ejecución Rápida

```bash
# Opción 1: Usar el archivo batch (Windows)
ejecutar.bat

# Opción 2: Compilación manual
javac -d bin -cp src src\modelo\*.java src\controlador\*.java src\vista\*.java
java -cp bin vista.VentanaPrincipal
```

## 🎮 Guía de Uso

### 1. **Dashboard Principal**

- Vista general del sistema con estadísticas en tiempo real
- Navegación mediante cards interactivas
- Panel lateral con resumen de entidades registradas

### 2. **Gestión de Entidades**

- **CRUD Completo**: Crear, leer, actualizar y eliminar
- **Validaciones**: Control de integridad automático
- **Relaciones**: Asignación automática entre entidades

### 3. **Gestión de Carreras**

- **Planificación**: Crear nuevos Grandes Premios
- **Inscripciones**: Registrar pilotos en carreras
- **Resultados**: Capturar posiciones y estadísticas
- **Finalización**: Cálculo automático de puntos

### 4. **Sistema de Reportes**

- **Rankings**: Ver clasificaciones actualizadas
- **Estadísticas**: Análisis detallado por piloto/equipo
- **Historial**: Registro completo de carreras

## 🔒 Controles de Integridad

- ✅ **Números únicos de piloto**
- ✅ **Validación de recursos por escudería**
- ✅ **Control de participaciones en carreras**
- ✅ **Restricciones en carreras finalizadas**
- ✅ **Cálculo automático de estadísticas**

## 📊 Datos de Ejemplo

El sistema incluye datos precargados:

- **5 Países**: Argentina, Brasil, España, Italia, Reino Unido
- **4 Escuderías**: Red Bull, Ferrari, McLaren, Mercedes
- **8 Pilotos**: Verstappen, Pérez, Leclerc, Sainz, etc.
- **8 Autos**: RB19, SF-23, MCL60, W14
- **8 Mecánicos**: Especialistas en motor, chasis, estrategia
- **5 Circuitos**: Mónaco, Silverstone, Monza, Spa, Interlagos

## 🐛 Correcciones Realizadas

### Errores Solucionados

- ✅ **ArrayIndexOutOfBoundsException** en VentanaReportes
- ✅ **Validación de listas vacías** en getResultados()
- ✅ **Manejo de excepciones** en apertura de ventanas
- ✅ **Look and Feel** configuración correcta

### Mejoras de Interfaz

- ✅ **Diseño moderno** con cards y gradientes
- ✅ **Navegación intuitiva** con iconos descriptivos
- ✅ **Panel de información** en tiempo real
- ✅ **Efectos visuales** y hover states
- ✅ **Responsividad** y redimensionamiento

## 🎨 Paleta de Colores

- **🔴 Rojo F1**: `#DC143C` - Elementos principales
- **⚫ Negro Racing**: `#343A40` - Texto y bordes
- **🔵 Azul Tech**: `#007BFF` - Botones de acción
- **🟢 Verde Success**: `#28A745` - Estados positivos
- **🟡 Amarillo Warning**: `#FFC107` - Advertencias
- **⚪ Fondo**: `#F8F9FA` - Fondo general

## 📈 Estado del Proyecto

### ✅ Completado (100%)

- [x] Modelo de datos completo (9 clases)
- [x] Gestión de Pilotos (CRUD completo)
- [x] Gestión de Escuderías (CRUD completo)
- [x] Gestión de Autos (CRUD completo)
- [x] Gestión de Mecánicos (CRUD completo)
- [x] Gestión de Circuitos (CRUD completo)
- [x] Gestión de Carreras (sistema completo)
- [x] Sistema de Reportes (5 módulos)
- [x] Interfaz principal moderna
- [x] Validaciones y controles de integridad
- [x] Manejo de errores
- [x] Datos de ejemplo

### 🎯 Funcionalidades Principales

- **7 Ventanas**: Todas implementadas y funcionales
- **CRUD Completo**: En todas las entidades
- **Sistema F1**: Puntuación oficial implementada
- **Reportes**: Rankings, estadísticas, historial
- **Validaciones**: Control de integridad completo

## 📄 Licencia

Este proyecto es desarrollado como parte de un taller de Programación Orientada a Objetos.

---

**🏎️ ¡Que comience la carrera! 🏁**

_Sistema desarrollado con Java Swing - Interfaz moderna e intuitiva para la gestión completa de campeonatos de Fórmula 1._

**Desarrollado por**: Martinzanandrea  
**Fecha**: Noviembre 2025  
**Versión**: 2.0.0 - Interfaz Moderna
