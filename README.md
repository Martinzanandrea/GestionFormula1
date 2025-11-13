# # 🏎️ Sistema de Gestión de Fórmula 1

## 📋 Descripción

Sistema completo de gestión de campeonatos de Fórmula 1 desarrollado en Java con interfaz gráfica Swing. Permite administrar todas las entidades relacionadas con el mundo de la F1: pilotos, escuderías, autos, circuitos, carreras y resultados.

## 🎯 Características Principales

### ✅ **Gestión Completa de Entidades**

- **🏃‍♂️ Pilotos**: Registro, contratos, estadísticas y puntuación
- **🏢 Escuderías**: Gestión de equipos, presupuestos y personal
- **🏎️ Autos**: Catálogo de vehículos, especificaciones técnicas
- **👨‍🔧 Mecánicos**: Personal técnico con especialidades
- **🏁 Circuitos**: Trazados internacionales con características detalladas
- **🌍 Países**: Base de datos de países participantes

### 🏆 **Sistema de Carreras**

- **📅 Planificación**: Programación de Grandes Premios
- **📝 Inscripciones**: Gestión de participantes con validaciones
- **⚡ Selección Automática**: Inscripción rápida de 10 pilotos
- **🏁 Resultados**: Registro de posiciones, abandonos y vueltas rápidas
- **💰 Puntuación**: Sistema automático de puntos según reglamento F1

### 📊 **Funcionalidades Avanzadas**

- **🔍 Validaciones**: Control de integridad de datos
- **📈 Reportes**: Estadísticas y clasificaciones
- **🎨 Interfaz Moderna**: Diseño intuitivo con iconografía
- **⏰ Gestión de Fechas**: Validaciones de tiempo futuro
- **🔒 Control de Estados**: Manejo de carreras finalizadas

## 🛠️ Tecnologías Utilizadas

- **Java 17+**: Lenguaje de programación principal
- **Swing**: Framework para interfaz gráfica de usuario
- **JavaDoc**: Documentación automática de código
- **MVC Pattern**: Arquitectura Modelo-Vista-Controlador

## 🏗️ Estructura del Proyecto

```
GestionFormula1/
├── src/
│   ├── modelo/           # Entidades del dominio
│   │   ├── Auto.java
│   │   ├── Piloto.java
│   │   ├── Escuderia.java
│   │   ├── GranPremio.java
│   │   └── ...
│   ├── controlador/      # Lógica de negocio
│   │   ├── GestorFormula1.java
│   │   ├── ValidadorFormula1.java
│   │   └── DatosEjemplo.java
│   └── vista/            # Interfaces gráficas
│       ├── VentanaPrincipal.java
│       ├── VentanaCarreras.java
│       └── ...
├── bin/                  # Clases compiladas
├── docs/                 # Documentación JavaDoc
└── README.md
```

## 🚀 Instalación y Ejecución

### Prerrequisitos

- Java 17 o superior
- JDK instalado y configurado

### Compilación

```bash
javac -cp . -d bin src/modelo/*.java src/controlador/*.java src/vista/*.java
```

### Ejecución

```bash
java -cp bin vista.VentanaPrincipal
```

### Generar Documentación

```bash
javadoc -d docs -cp . -sourcepath src -subpackages modelo:controlador:vista -author -version -use -windowtitle "Sistema de Gestión Formula 1" -doctitle "Documentación del Sistema de Gestión de Fórmula 1"
```

## 📚 Documentación

La documentación completa está disponible en el directorio `docs/` después de ejecutar JavaDoc.

- **Índice Principal**: `docs/index.html`
- **Paquetes**:
  - `modelo` - Entidades del dominio
  - `controlador` - Lógica de negocio
  - `vista` - Interfaces de usuario

## 🎮 Guía de Uso

### 1. **Inicio de la Aplicación**

- Ejecute la aplicación y verá la ventana principal
- Use el panel de navegación para acceder a las diferentes secciones

### 2. **Gestión de Pilotos**

- Registre pilotos con información completa
- Asigne contratos con escuderías
- Visualice estadísticas y puntuaciones

### 3. **Configuración de Carreras**

- Cree nuevos Grandes Premios
- Seleccione circuitos y fechas futuras
- Inscriba pilotos individualmente o en masa

### 4. **Ejecución de Carreras**

- Registre posiciones finales
- Marque abandonos y vueltas rápidas
- Finalice carreras para calcular puntos automáticamente

### 5. **Selección Automática**

- Use "🎯 Selección Auto (10)" para inscribir 10 pilotos aleatorios
- Ideal para pruebas rápidas del sistema

## ⚙️ Características Técnicas

### Validaciones Implementadas

- ✅ Fechas futuras para carreras
- ✅ Posiciones únicas en resultados
- ✅ Límite de 20 participantes por carrera
- ✅ Contratos vigentes de pilotos
- ✅ Disponibilidad de autos

### Sistema de Puntuación F1

- **P1**: 25 puntos
- **P2**: 18 puntos
- **P3**: 15 puntos
- **P4-P10**: 12, 10, 8, 6, 4, 2, 1 puntos
- **Vuelta Rápida**: +1 punto (solo top 10)

## 📦 Datos de Ejemplo

El sistema incluye datos de ejemplo realistas:

- **14 pilotos** con contratos vigentes
- **7 escuderías** oficiales de F1
- **14 autos** asignados a equipos
- **3 circuitos** históricos
- **3 Grandes Premios** programados

## 🐛 Solución de Problemas

### Error de Finalización

**Problema**: Error "Cannot invoke 'isFinalizada()' because parameter is null"
**Solución**: ✅ **CORREGIDO** - Agregadas validaciones null en el método de finalización

### Pocos Pilotos Disponibles

**Problema**: Solo aparecen pocos pilotos para inscribir
**Solución**: ✅ **CORREGIDO** - Los datos actualizados incluyen 14 pilotos con contratos vigentes

## 🤝 Contribuciones

Este proyecto está desarrollado como parte de un taller de Programación Orientada a Objetos.

## 📄 Licencia

Proyecto educativo desarrollado para aprendizaje de POO en Java.

---

**🏁 ¡Disfruta gestionando tu propio campeonato de Fórmula 1!** 🏆

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
