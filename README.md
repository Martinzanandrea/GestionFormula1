# Sistema de Gestión de Fórmula 1

## Descripción

Sistema desarrollado para "Escuderías Unidas" que permite gestionar competencias de Fórmula 1, incluyendo pilotos, escuderías, autos, mecánicos, circuitos y carreras.

## Características Principales

### Funcionalidades Implementadas

- ✅ **Gestión de Pilotos**: Registro, modificación y eliminación de pilotos
- ✅ **Gestión de Escuderías**: Administración de equipos y asignación de pilotos
- ✅ **Gestión de Autos**: Control de vehículos por escudería
- ✅ **Gestión de Mecánicos**: Registro de mecánicos con especialidades
- ✅ **Gestión de Circuitos**: Administración de pistas de carreras
- ✅ **Gestión de Carreras**: Planificación y registro de Grandes Premios
- ✅ **Sistema de Puntuación**: Cálculo automático según reglamento F1
- ✅ **Controles de Integridad**: Validaciones para mantener consistencia

### Funcionalidades por Implementar

- 🔄 **Gestión de Escuderías** (interfaz gráfica)
- 🔄 **Gestión de Autos** (interfaz gráfica)
- 🔄 **Gestión de Mecánicos** (interfaz gráfica)
- 🔄 **Gestión de Circuitos** (interfaz gráfica)
- 🔄 **Gestión de Carreras** (interfaz gráfica)
- 🔄 **Sistema de Reportes**
- 🔄 **Persistencia de datos**

## Arquitectura del Sistema

El sistema sigue el patrón **Modelo-Vista-Controlador (MVC)**:

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
├── vista/            # Interfaz gráfica (Swing)
│   ├── VentanaPrincipal.java
│   └── VentanaPilotos.java
└── controlador/      # Lógica de negocio
    ├── GestorFormula1.java
    └── DatosEjemplo.java
```

## Clases Principales

### Modelo

- **Pais**: Representa países con nombre y código
- **Escuderia**: Equipos de F1 con pilotos, autos y mecánicos asociados
- **Piloto**: Corredores con información personal y estadísticas
- **Auto**: Vehículos con especificaciones técnicas
- **Mecanico**: Personal técnico con especialidades
- **Circuito**: Pistas de carrera con características específicas
- **GranPremio**: Eventos de carrera con participaciones
- **Participacion**: Registro de participación de piloto en carrera
- **SistemaPuntuacion**: Sistema oficial de puntos F1

### Controlador

- **GestorFormula1**: Controlador principal que gestiona todas las operaciones
- **DatosEjemplo**: Carga datos de prueba para demostración

### Vista

- **VentanaPrincipal**: Pantalla principal con menú de navegación
- **VentanaPilotos**: Gestión completa de pilotos (CRUD)

## Reglas de Negocio Implementadas

1. **Control de números únicos**: Cada piloto tiene un número único (1-99)
2. **Asignación de autos**: Un auto no puede ser asignado a múltiples pilotos en la misma carrera
3. **Vinculación de escuderías**: Control de pilotos por escudería
4. **Sistema de puntuación**: Puntos oficiales F1 (1º=25, 2º=18, 3º=15, etc.)
5. **Validaciones de edad**: Pilotos entre 18-50 años
6. **Gestión de podios**: Detección automática de posiciones de podio

## Requisitos del Sistema

- **Java**: JDK 8 o superior
- **GUI**: Swing (incluido en Java)
- **OS**: Windows, Linux, macOS

## Instalación y Ejecución

### Compilación

```bash
# Crear directorio de compilación
mkdir bin

# Compilar el proyecto
javac -d bin -sourcepath src src/vista/VentanaPrincipal.java
```

### Ejecución

```bash
# Ejecutar la aplicación
java -cp bin vista.VentanaPrincipal
```

## Uso de la Aplicación

### Pantalla Principal

Al ejecutar la aplicación, se presenta un menú principal con las siguientes opciones:

1. **Gestionar Pilotos** 👨‍✈️: Abre la ventana de gestión de pilotos
2. **Gestionar Escuderías** 🏁: (En desarrollo)
3. **Gestionar Autos** 🏎️: (En desarrollo)
4. **Gestionar Mecánicos** 🔧: (En desarrollo)
5. **Gestionar Circuitos** 🏁: (En desarrollo)
6. **Gestionar Carreras** 🏆: (En desarrollo)
7. **Reportes** 📊: (En desarrollo)
8. **Configuración** ⚙️: (En desarrollo)
9. **Salir** 🚪: Cierra la aplicación

### Gestión de Pilotos

La ventana de pilotos permite:

- **Agregar**: Registrar nuevos pilotos con todos sus datos
- **Modificar**: Editar información de pilotos existentes
- **Eliminar**: Remover pilotos del sistema
- **Asignar Escudería**: Vincular pilotos a equipos
- **Visualizar**: Lista completa con todos los datos

### Datos Precargados

El sistema incluye datos de ejemplo:

**Países**: Italia, España, Inglaterra, Alemania, Francia, Mónaco

**Escuderías**:

- Scuderia Ferrari (Italia)
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
