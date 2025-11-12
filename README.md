# Sistema de Gestión de Fórmula 1# Sistema de Gestión de Fórmula 1

## Descripción## Descripción

Sistema de gestión integral para campeonatos de Fórmula 1 desarrollado en Java utilizando Swing para la interfaz gráfica de usuario. La aplicación permite administrar todos los aspectos relacionados con un campeonato de F1, incluyendo pilotos, escuderías, vehículos, personal técnico, circuitos y carreras.Sistema desarrollado para "Escuderías Unidas" que permite gestionar competencias de Fórmula 1, incluyendo pilotos, escuderías, autos, mecánicos, circuitos y carreras.

## Características Principales## Características Principales

### Gestión de Entidades### Funcionalidades Implementadas

- **Pilotos**: Registro y administración de pilotos con información personal, estadísticas y asignación a escuderías

- **Escuderías**: Gestión de equipos con información corporativa, presupuesto y personal asociado- ✅ **Gestión de Pilotos**: Registro, modificación y eliminación de pilotos

- **Vehículos**: Administración del parque automotor con especificaciones técnicas y rendimiento- ✅ **Gestión de Escuderías**: Administración de equipos y asignación de pilotos

- **Mecánicos**: Control del personal técnico con especialidades y asignaciones- ✅ **Gestión de Autos**: Control de vehículos por escudería

- **Circuitos**: Catálogo de pistas con características técnicas y ubicación geográfica- ✅ **Gestión de Mecánicos**: Registro de mecánicos con especialidades

- ✅ **Gestión de Circuitos**: Administración de pistas de carreras

### Operaciones de Carrera- ✅ **Gestión de Carreras**: Planificación y registro de Grandes Premios

- **Grandes Premios**: Programación y gestión de eventos de carrera- ✅ **Sistema de Puntuación**: Cálculo automático según reglamento F1

- **Participaciones**: Registro de participantes por carrera con seguimiento de resultados- ✅ **Controles de Integridad**: Validaciones para mantener consistencia

- **Sistema de Puntuación**: Implementación del reglamento oficial de puntuación de F1

- **Reportes**: Generación de estadísticas detalladas y análisis de rendimiento### Funcionalidades por Implementar

## Arquitectura del Sistema- 🔄 **Gestión de Escuderías** (interfaz gráfica)

- 🔄 **Gestión de Autos** (interfaz gráfica)

### Patrón de Diseño- 🔄 **Gestión de Mecánicos** (interfaz gráfica)

El sistema implementa el patrón **Modelo-Vista-Controlador (MVC)**:- 🔄 **Gestión de Circuitos** (interfaz gráfica)

- 🔄 **Gestión de Carreras** (interfaz gráfica)

- **Modelo**: Clases de entidad que representan los objetos del dominio- 🔄 **Sistema de Reportes**

- **Vista**: Interfaces gráficas desarrolladas en Java Swing- 🔄 **Persistencia de datos**

- **Controlador**: Lógica de negocio y gestión de datos

## Arquitectura del Sistema

### Estructura del Proyecto

El sistema sigue el patrón **Modelo-Vista-Controlador (MVC)**:

````

src/```

├── controlador/src/

│   ├── DatosEjemplo.java      # Datos de prueba del sistema├── modelo/           # Clases de dominio

│   └── GestorFormula1.java    # Controlador principal│   ├── Pais.java

├── modelo/│   ├── Escuderia.java

│   ├── Auto.java              # Entidad Vehículo│   ├── Piloto.java

│   ├── Circuito.java          # Entidad Circuito│   ├── Auto.java

│   ├── Escuderia.java         # Entidad Escudería│   ├── Mecanico.java

│   ├── GranPremio.java        # Entidad Gran Premio│   ├── Circuito.java

│   ├── Mecanico.java          # Entidad Mecánico│   ├── GranPremio.java

│   ├── Pais.java              # Entidad País│   ├── Participacion.java

│   ├── Participacion.java     # Entidad Participación│   └── SistemaPuntuacion.java

│   ├── Piloto.java            # Entidad Piloto├── vista/            # Interfaz gráfica (Swing)

│   └── SistemaPuntuacion.java # Sistema de puntuación F1│   ├── VentanaPrincipal.java

└── vista/│   └── VentanaPilotos.java

    ├── VentanaAutos.java      # Interfaz gestión de vehículos└── controlador/      # Lógica de negocio

    ├── VentanaCarreras.java   # Interfaz gestión de carreras    ├── GestorFormula1.java

    ├── VentanaCircuitos.java  # Interfaz gestión de circuitos    └── DatosEjemplo.java

    ├── VentanaEscuderias.java # Interfaz gestión de escuderías```

    ├── VentanaMecanicos.java  # Interfaz gestión de mecánicos

    ├── VentanaPilotos.java    # Interfaz gestión de pilotos## Clases Principales

    ├── VentanaPrincipal.java  # Interfaz principal del sistema

    └── VentanaReportes.java   # Interfaz de reportes y estadísticas### Modelo

````

- **Pais**: Representa países con nombre y código

## Tecnologías Utilizadas- **Escuderia**: Equipos de F1 con pilotos, autos y mecánicos asociados

- **Piloto**: Corredores con información personal y estadísticas

- **Lenguaje**: Java SE 8 o superior- **Auto**: Vehículos con especificaciones técnicas

- **Interfaz Gráfica**: Java Swing- **Mecanico**: Personal técnico con especialidades

- **Paradigma**: Programación Orientada a Objetos- **Circuito**: Pistas de carrera con características específicas

- **Patrón de Diseño**: Modelo-Vista-Controlador (MVC)- **GranPremio**: Eventos de carrera con participaciones

- **Documentación**: JavaDoc- **Participacion**: Registro de participación de piloto en carrera

- **SistemaPuntuacion**: Sistema oficial de puntos F1

## Requisitos del Sistema

### Controlador

### Requisitos Mínimos

- Java Runtime Environment (JRE) 8 o superior- **GestorFormula1**: Controlador principal que gestiona todas las operaciones

- Sistema Operativo: Windows, macOS o Linux- **DatosEjemplo**: Carga datos de prueba para demostración

- Memoria RAM: 512 MB mínimo

- Espacio en disco: 100 MB### Vista

### Requisitos Recomendados- **VentanaPrincipal**: Pantalla principal con menú de navegación

- Java Development Kit (JDK) 11 o superior (para desarrollo)- **VentanaPilotos**: Gestión completa de pilotos (CRUD)

- Memoria RAM: 1 GB o superior

- Resolución de pantalla: 1024x768 o superior## Reglas de Negocio Implementadas

## Instalación y Ejecución1. **Control de números únicos**: Cada piloto tiene un número único (1-99)

2. **Asignación de autos**: Un auto no puede ser asignado a múltiples pilotos en la misma carrera

### Compilación3. **Vinculación de escuderías**: Control de pilotos por escudería

````bash4. **Sistema de puntuación**: Puntos oficiales F1 (1º=25, 2º=18, 3º=15, etc.)

javac -d bin src/**/*.java5. **Validaciones de edad**: Pilotos entre 18-50 años

```6. **Gestión de podios**: Detección automática de posiciones de podio



### Ejecución## Requisitos del Sistema

```bash

java -cp bin vista.VentanaPrincipal- **Java**: JDK 8 o superior

```- **GUI**: Swing (incluido en Java)

- **OS**: Windows, Linux, macOS

### Ejecución Alternativa (Windows)

```bash## Instalación y Ejecución

ejecutar.bat

```### Compilación



## Funcionalidades Detalladas```bash

# Crear directorio de compilación

### Sistema de Gestiónmkdir bin

- Operaciones CRUD (Create, Read, Update, Delete) para todas las entidades

- Validación de datos de entrada# Compilar el proyecto

- Relaciones entre entidades (piloto-escudería, mecánico-auto, etc.)javac -d bin -sourcepath src src/vista/VentanaPrincipal.java

- Persistencia temporal de datos durante la sesión```



### Sistema de Reportes### Ejecución

- Estadísticas de pilotos y escuderías

- Rankings y clasificaciones```bash

- Historial de carreras y resultados# Ejecutar la aplicación

- Análisis de rendimiento por circuitojava -cp bin vista.VentanaPrincipal

````

### Interfaz de Usuario

- Diseño intuitivo y profesional## Uso de la Aplicación

- Navegación clara entre módulos

- Formularios de entrada validados### Pantalla Principal

- Tablas de datos organizadas

- Paneles de información contextualAl ejecutar la aplicación, se presenta un menú principal con las siguientes opciones:

## Documentación del Código1. **Gestionar Pilotos** 👨‍✈️: Abre la ventana de gestión de pilotos

2. **Gestionar Escuderías** 🏁: (En desarrollo)

Todo el código fuente está documentado utilizando estándares JavaDoc, incluyendo:3. **Gestionar Autos** 🏎️: (En desarrollo)

- Descripción de clases y su propósito4. **Gestionar Mecánicos** 🔧: (En desarrollo)

- Documentación de métodos con parámetros y valores de retorno5. **Gestionar Circuitos** 🏁: (En desarrollo)

- Comentarios de atributos y constantes6. **Gestionar Carreras** 🏆: (En desarrollo)

- Ejemplos de uso cuando corresponde7. **Reportes** 📊: (En desarrollo)

8. **Configuración** ⚙️: (En desarrollo)

## Consideraciones de Desarrollo9. **Salir** 🚪: Cierra la aplicación

### Principios Aplicados### Gestión de Pilotos

- **Encapsulación**: Atributos privados con métodos de acceso apropiados

- **Herencia**: Jerarquías de clases cuando es apropiadoLa ventana de pilotos permite:

- **Polimorfismo**: Interfaces y métodos sobrescritos

- **Abstracción**: Separación clara entre interfaz e implementación- **Agregar**: Registrar nuevos pilotos con todos sus datos

- **Modificar**: Editar información de pilotos existentes

### Buenas Prácticas- **Eliminar**: Remover pilotos del sistema

- Nomenclatura descriptiva en español para mayor claridad- **Asignar Escudería**: Vincular pilotos a equipos

- Manejo de excepciones apropiado- **Visualizar**: Lista completa con todos los datos

- Separación de responsabilidades entre capas

- Código limpio y mantenible### Datos Precargados

## AutorEl sistema incluye datos de ejemplo:

Desarrollado como proyecto académico para la materia de Programación Orientada a Objetos.**Países**: Italia, España, Inglaterra, Alemania, Francia, Mónaco

## Licencia**Escuderías**:

Este proyecto es de uso académico y educativo.- Scuderia Ferrari (Italia)

- Red Bull Racing (Inglaterra)
- Mercedes-AMG Petronas (Alemania)
- McLaren (Inglaterra)

**Pilotos**:

- Charles Leclerc (#16) - Ferrari
- Carlos Sainz Jr. (#55) - Ferrari
- Max Verstappen (#1) - Red Bull
- Sergio Pérez (#11) - Red Bull
- Lewis Hamilton (#44) - Mercedes
- George Russell (#63) - Mercedes

**Circuitos**: Monza, Silverstone, Monaco

## Futuras Mejoras

1. **Interfaz completa**: Implementar todas las ventanas de gestión
2. **Reportes avanzados**: Estadísticas y rankings detallados
3. **Base de datos**: Persistencia en BD relacional
4. **Importación/Exportación**: Funciones de respaldo de datos
5. **Gráficos y charts**: Visualización de estadísticas
6. **Configuración avanzada**: Personalización del sistema de puntos
7. **Auditoría**: Log de cambios y operaciones

## Contribución

Para contribuir al proyecto:

1. Fork del repositorio
2. Crear rama para nueva funcionalidad
3. Implementar cambios siguiendo las convenciones del código
4. Realizar pruebas
5. Crear Pull Request

## Licencia

Proyecto académico desarrollado para el Taller de Programación Orientada a Objetos.

---

**Desarrollado por**: Martinzanandrea  
**Fecha**: Noviembre 2025  
**Versión**: 1.0.0
