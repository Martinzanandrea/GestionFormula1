# 🏎️ SISTEMA DE GESTIÓN DE FÓRMULA 1

## Documentación Técnica y Manual de Usuario

---

**Proyecto:** Sistema de Gestión de Campeonatos de Fórmula 1  
**Versión:** 1.0  
**Fecha:** Noviembre 2025  
**Desarrollado para:** Taller de Programación Orientada a Objetos

---

## 📋 ÍNDICE

1. [Introducción](#1-introducción)
2. [Objetivos del Sistema](#2-objetivos-del-sistema)
3. [Análisis y Diseño](#3-análisis-y-diseño)
4. [Arquitectura del Sistema](#4-arquitectura-del-sistema)
5. [Interfaces de Usuario](#5-interfaces-de-usuario)
6. [Funcionalidades Implementadas](#6-funcionalidades-implementadas)
7. [Bitácora de Actividades](#7-bitácora-de-actividades)
8. [Manual de Usuario](#8-manual-de-usuario)
9. [Instalación y Configuración](#9-instalación-y-configuración)
10. [Pruebas y Validaciones](#10-pruebas-y-validaciones)
11. [Conclusiones](#11-conclusiones)
12. [Anexos](#12-anexos)

---

## 1. INTRODUCCIÓN

### 1.1 Descripción General

El **Sistema de Gestión de Fórmula 1** es una aplicación de escritorio desarrollada en Java que permite la administración completa de campeonatos de Fórmula 1. El sistema gestiona todas las entidades relacionadas con el mundo de la F1: pilotos, escuderías, autos, mecánicos, circuitos y carreras.

### 1.2 Propósito

Este sistema fue desarrollado como proyecto final del Taller de Programación Orientada a Objetos, con el objetivo de aplicar los conceptos fundamentales de POO en un contexto real y complejo.

### 1.3 Alcance

El sistema abarca:

- Gestión de entidades F1 (pilotos, escuderías, autos, etc.)
- Planificación y ejecución de carreras
- Sistema de puntuación automático
- Generación de reportes y estadísticas
- Interfaz gráfica intuitiva y moderna

### 1.4 Tecnologías Utilizadas

- **Lenguaje:** Java 17+
- **Framework GUI:** Java Swing
- **Arquitectura:** Modelo-Vista-Controlador (MVC)
- **Documentación:** JavaDoc
- **Control de versiones:** Git

---

## 2. OBJETIVOS DEL SISTEMA

### 2.1 Objetivos Generales

- Crear un sistema integral para la gestión de campeonatos de Fórmula 1
- Aplicar principios de Programación Orientada a Objetos
- Desarrollar una interfaz de usuario intuitiva y funcional
- Implementar validaciones y controles de integridad de datos

### 2.2 Objetivos Específicos

#### 2.2.1 Gestión de Entidades

- Registrar y administrar pilotos con información completa
- Gestionar escuderías, autos y personal técnico
- Mantener catálogo de circuitos internacionales
- Controlar contratos y asignaciones

#### 2.2.2 Sistema de Carreras

- Planificar Grandes Premios con validaciones de fecha
- Gestionar inscripciones de participantes
- Registrar resultados y calcular puntuaciones automáticamente
- Generar estadísticas y clasificaciones

#### 2.2.3 Interfaz de Usuario

- Diseñar interfaces intuitivas y modernas
- Implementar navegación clara entre módulos
- Proporcionar feedback visual al usuario
- Optimizar la experiencia de uso

---

## 3. ANÁLISIS Y DISEÑO

### 3.1 Análisis de Requerimientos

#### 3.1.1 Requerimientos Funcionales

**RF001 - Gestión de Pilotos**

- El sistema debe permitir registrar pilotos con información personal completa
- Debe gestionar contratos con escuderías incluyendo fechas de inicio y fin
- Debe calcular puntuaciones totales automáticamente

**RF002 - Gestión de Escuderías**

- El sistema debe registrar escuderías con país de origen
- Debe gestionar personal (pilotos y mecánicos)
- Debe controlar la asignación de autos

**RF003 - Gestión de Carreras**

- El sistema debe planificar Grandes Premios con validación de fechas futuras
- Debe gestionar inscripciones con límite de 20 participantes
- Debe registrar resultados y calcular puntos según reglamento F1

**RF004 - Validaciones**

- El sistema debe validar fechas futuras para carreras
- Debe verificar posiciones únicas en resultados
- Debe controlar contratos vigentes de pilotos

#### 3.1.2 Requerimientos No Funcionales

**RNF001 - Usabilidad**

- Interfaz intuitiva con iconografía clara
- Navegación fluida entre módulos
- Mensajes informativos para el usuario

**RNF002 - Rendimiento**

- Respuesta rápida en operaciones CRUD
- Carga eficiente de datos de ejemplo
- Actualización en tiempo real de interfaces

**RNF003 - Mantenibilidad**

- Código bien documentado con JavaDoc
- Arquitectura MVC clara
- Separación de responsabilidades

### 3.2 Modelo de Dominio

#### 3.2.1 Entidades Principales

1. **Piloto**: Información personal, número, nacionalidad, experiencia, puntos
2. **Escudería**: Nombre, país, personal, autos
3. **Auto**: Modelo, especificaciones técnicas, asignación
4. **GranPremio**: Carrera con fecha, circuito, participaciones
5. **Circuito**: Trazado con características técnicas
6. **Participación**: Relación piloto-carrera con resultados

#### 3.2.2 Relaciones

- Un piloto puede tener múltiples contratos con escuderías (temporal)
- Una escudería tiene múltiples pilotos y autos
- Un Gran Premio se realiza en un circuito específico
- Una participación relaciona piloto, auto y carrera

---

## 4. ARQUITECTURA DEL SISTEMA

### 4.1 Patrón MVC (Modelo-Vista-Controlador)

#### 4.1.1 Modelo (Package: modelo)

Contiene todas las entidades del dominio F1:

- **Auto.java**: Representa los vehículos de F1
- **Piloto.java**: Información de los pilotos
- **Escuderia.java**: Gestión de equipos
- **GranPremio.java**: Eventos de carrera
- **Circuito.java**: Trazados de carrera
- **Participacion.java**: Resultados de carrera
- **Mecanico.java**: Personal técnico
- **SistemaPuntuacion.java**: Lógica de puntuación

#### 4.1.2 Vista (Package: vista)

Interfaces de usuario en Java Swing:

- **VentanaPrincipal.java**: Interfaz principal con navegación
- **VentanaCarreras.java**: Gestión completa de carreras
- **VentanaPilotos.java**: Administración de pilotos
- **VentanaEscuderias.java**: Gestión de escuderías
- **VentanaAutos.java**: Catálogo de vehículos
- **VentanaMecanicos.java**: Personal técnico
- **VentanaCircuitos.java**: Trazados disponibles

#### 4.1.3 Controlador (Package: controlador)

Lógica de negocio y coordinación:

- **GestorFormula1.java**: Controlador principal del sistema
- **ValidadorFormula1.java**: Validaciones y reglas de negocio
- **DatosEjemplo.java**: Carga de datos de prueba

### 4.2 Diagrama de Clases Simplificado

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│    GestorF1     │────│     Piloto      │────│   Escuderia     │
│ (Controlador)   │    │                 │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │               ┌─────────────────┐             │
         └───────────────│  Participacion  │─────────────┘
                         │                 │
                         └─────────────────┘
                                  │
                         ┌─────────────────┐
                         │   GranPremio    │
                         │                 │
                         └─────────────────┘
```

---

## 5. INTERFACES DE USUARIO

### 5.1 Ventana Principal

**Características:**

- Dimensiones optimizadas: 1200x800 píxeles
- Panel de navegación lateral con botones iconográficos
- Área central para mostrar información del sistema
- Diseño moderno con esquema de colores coherente

**Funcionalidades:**

- Acceso directo a todos los módulos del sistema
- Información de estado del campeonato
- Navegación intuitiva con iconos descriptivos

### 5.2 Gestión de Carreras

**Características:**

- Interfaz con pestañas para organizar funcionalidades
- Tablas con información detallada y actualización en tiempo real
- Formularios de entrada con validaciones

**Pestañas implementadas:**

#### 5.2.1 Planificar Carreras

- Lista de carreras programadas
- Formulario para crear nuevos Grandes Premios
- Validación de fechas futuras
- Selección de circuitos disponibles

#### 5.2.2 Inscribir Pilotos

- Panel de inscripción individual con combos de pilotos y autos
- Panel de inscripción masiva con tabla de pilotos disponibles
- **Funcionalidad especial:** Botón "🎯 Selección Auto (10)" para inscribir automáticamente 10 pilotos
- Tabla de participantes inscritos con información completa

#### 5.2.3 Registrar Resultados

- Tabla de participaciones con posibilidad de edición
- Formulario detallado para registrar resultados individuales
- Sistema de puntuación automático según reglamento F1
- Botón para finalizar carreras y calcular puntos

### 5.3 Gestión de Pilotos

**Características:**

- Formulario completo para registro de pilotos
- Tabla con información detallada incluyendo indicadores visuales
- Gestión de contratos con escuderías
- Indicadores de estado: activo, libre, contrato vigente

### 5.4 Gestión de Autos

**Características:**

- Catálogo completo de vehículos F1
- Sistema de asignación/desasignación a escuderías
- Formulario con especificaciones técnicas detalladas
- Validaciones de disponibilidad

### 5.5 Otras Ventanas

#### 5.5.1 Gestión de Mecánicos

- Registro con especialidades técnicas
- Asignación a escuderías
- Control de experiencia y habilidades

#### 5.5.2 Gestión de Escuderías

- Información completa de equipos
- Visualización de personal asignado
- Gestión de países de origen

#### 5.5.3 Gestión de Circuitos

- Catálogo de trazados internacionales
- Características técnicas (longitud, curvas, tipo)
- Información geográfica

---

## 6. FUNCIONALIDADES IMPLEMENTADAS

### 6.1 Sistema de Carreras Avanzado

#### 6.1.1 Planificación de Carreras

- **Validación temporal:** Solo permite fechas futuras
- **Selección de circuitos:** Catálogo completo disponible
- **Gestión de estados:** Programada vs Finalizada

#### 6.1.2 Sistema de Inscripciones

- **Inscripción individual:** Selección manual de piloto y auto
- **Inscripción masiva:** Selección múltiple de participantes
- **Selección automática:** Funcionalidad única para inscribir exactamente 10 pilotos
- **Validaciones:** Límite de 20 participantes, verificación de disponibilidad

#### 6.1.3 Gestión de Resultados

- **Registro detallado:** Posición final, mejor vuelta, vuelta más rápida
- **Gestión de abandonos:** Motivos y clasificación DNF
- **Cálculo automático:** Puntos según reglamento oficial F1
- **Finalización:** Proceso controlado con validaciones

### 6.2 Sistema de Puntuación F1

**Tabla oficial implementada:**

- P1: 25 puntos | P2: 18 puntos | P3: 15 puntos
- P4: 12 puntos | P5: 10 puntos | P6: 8 puntos
- P7: 6 puntos | P8: 4 puntos | P9: 2 puntos | P10: 1 punto
- **Bonus:** +1 punto por vuelta más rápida (solo top 10)

### 6.3 Validaciones y Controles

#### 6.3.1 Validaciones Temporales

- Fechas futuras para carreras nuevas
- Control de contratos vigentes
- Verificación de disponibilidad de recursos

#### 6.3.2 Validaciones de Integridad

- Posiciones únicas en resultados
- Límites de participantes por carrera
- Asignaciones únicas de autos

#### 6.3.3 Controles de Usuario

- Mensajes informativos y de error
- Confirmaciones para operaciones críticas
- Feedback visual en tiempo real

### 6.4 Datos de Ejemplo Realistas

#### 6.4.1 Pilotos Actuales (14 pilotos activos)

- Charles Leclerc (Ferrari)
- Carlos Sainz Jr. (Ferrari)
- Max Verstappen (Red Bull)
- Sergio Pérez (Red Bull)
- Lewis Hamilton (Mercedes)
- George Russell (Mercedes)
- Lando Norris (McLaren)
- Oscar Piastri (McLaren)
- Fernando Alonso (Aston Martin)
- Lance Stroll (Aston Martin)
- Pierre Gasly (Alpine)
- Esteban Ocon (Alpine)
- Alexander Albon (Williams)
- Logan Sargeant (Williams)

#### 6.4.2 Escuderías Oficiales (7 equipos)

- Scuderia Ferrari
- Red Bull Racing
- Mercedes-AMG Petronas
- McLaren
- Aston Martin
- Alpine F1 Team
- Williams Racing

#### 6.4.3 Circuitos Históricos

- Autodromo Nazionale Monza (Italia)
- Silverstone Circuit (Inglaterra)
- Circuit de Monaco (Mónaco)

---

## 7. BITÁCORA DE ACTIVIDADES

### 7.1 Fase de Análisis y Diseño

**Semana 1-2: Análisis de Requerimientos**

- ✅ Definición de entidades del dominio F1
- ✅ Identificación de relaciones entre entidades
- ✅ Diseño de la arquitectura MVC
- ✅ Planificación de interfaces de usuario

### 7.2 Fase de Implementación del Modelo

**Semana 3: Desarrollo de Entidades Básicas**

- ✅ Implementación de clase Persona (superclase)
- ✅ Desarrollo de Piloto con atributos específicos F1
- ✅ Creación de Escuderia con gestión de personal
- ✅ Implementación de Auto con especificaciones técnicas

**Semana 4: Entidades Complejas**

- ✅ Desarrollo de GranPremio con gestión de carreras
- ✅ Implementación de Participacion con resultados
- ✅ Creación de Circuito con características técnicas
- ✅ Sistema de Mecánico con especialidades

### 7.3 Fase de Desarrollo del Controlador

**Semana 5: Lógica de Negocio**

- ✅ Implementación de GestorFormula1 (controlador principal)
- ✅ Desarrollo de ValidadorFormula1 con reglas de negocio
- ✅ Creación de sistema de puntuación automático
- ✅ Implementación de DatosEjemplo

### 7.4 Fase de Desarrollo de Interfaces

**Semana 6-7: Interfaces Básicas**

- ✅ Desarrollo de VentanaPrincipal con navegación
- ✅ Implementación de VentanaPilotos
- ✅ Creación de VentanaEscuderias
- ✅ Desarrollo de VentanaCircuitos

**Semana 8-9: Interfaces Avanzadas**

- ✅ Implementación completa de VentanaCarreras (compleja)
- ✅ Desarrollo de VentanaAutos con gestión completa
- ✅ Creación de VentanaMecanicos con especialidades
- ✅ Implementación de VentanaContratos

### 7.5 Fase de Refinamiento y Optimización

**Semana 10-11: Mejoras de Funcionalidad**

- ✅ Implementación de selección automática de 10 pilotos
- ✅ Mejora de validaciones temporales
- ✅ Optimización de interfaces para evitar superposiciones
- ✅ Ampliación de datos de ejemplo (14 pilotos activos)

**Semana 12: Corrección de Errores**

- ✅ Resolución de error de finalización de carreras (null pointer)
- ✅ Mejora de validaciones en inscripciones
- ✅ Optimización de actualizaciones de interface
- ✅ Corrección de contratos vigentes en datos ejemplo

### 7.6 Fase de Documentación

**Semana 13: Documentación Completa**

- ✅ Generación de documentación JavaDoc completa
- ✅ Creación de README.md detallado
- ✅ Desarrollo de página de inicio para documentación
- ✅ Elaboración de este documento técnico

---

## 8. MANUAL DE USUARIO

### 8.1 Inicio del Sistema

1. **Ejecutar la aplicación:**

   ```bash
   java -cp bin vista.VentanaPrincipal
   ```

2. **Ventana principal:** Se abre la interfaz principal con:
   - Panel de navegación lateral
   - Área de información central
   - Botones de acceso a módulos

### 8.2 Gestión de Pilotos

#### 8.2.1 Registrar Nuevo Piloto

1. Hacer clic en "👤 Gestión de Pilotos"
2. Completar formulario con datos del piloto
3. Hacer clic en "Registrar Piloto"
4. Verificar en la tabla que aparezca el nuevo piloto

#### 8.2.2 Asignar Contrato

1. Seleccionar piloto en la tabla
2. Usar botón "Asignar a Escudería"
3. Completar fechas de contrato
4. Confirmar asignación

### 8.3 Gestión de Carreras

#### 8.3.1 Planificar Nueva Carrera

1. Ir a "🏁 Gestión de Carreras"
2. Pestaña "Planificar Carreras"
3. Completar formulario:
   - Nombre de la carrera
   - Fecha (solo futuras)
   - Hora
   - Circuito
4. Hacer clic en "Crear Carrera"

#### 8.3.2 Inscribir Pilotos

**Inscripción Individual:**

1. Pestaña "Inscribir Pilotos"
2. Panel "Inscripción Individual"
3. Seleccionar piloto y auto disponible
4. Hacer clic en "✅ Inscribir Piloto"

**Selección Automática (Recomendado):**

1. Pestaña "Inscribir Pilotos"
2. Panel "Inscripción Rápida Masiva"
3. Hacer clic en "🎯 Selección Auto (10)"
4. Confirmar selección automática
5. El sistema inscribirá 10 pilotos automáticamente

#### 8.3.3 Registrar Resultados

1. Pestaña "Registrar Resultados"
2. Seleccionar participante en la tabla
3. Hacer clic en "✏️ Editar Resultado"
4. Completar formulario:
   - Posición final (1-20)
   - Mejor vuelta (opcional)
   - Vuelta más rápida (checkbox)
   - Abandono y motivo (si aplica)
5. Guardar resultado

#### 8.3.4 Finalizar Carrera

1. Asegurarse de que todos los resultados estén registrados
2. Hacer clic en "🏁 Finalizar Carrera"
3. Revisar resumen previo
4. Confirmar finalización
5. Ver resultados oficiales y puntos asignados

### 8.4 Gestión de Autos

#### 8.4.1 Crear Nuevo Auto

1. Ir a "🏎️ Gestión de Autos"
2. Completar especificaciones técnicas
3. Hacer clic en "Crear Auto"
4. El auto queda disponible para asignación

#### 8.4.2 Asignar Auto a Escudería

1. Seleccionar auto libre en la tabla
2. Seleccionar escudería en combo
3. Hacer clic en "Asignar Auto"
4. Verificar asignación en la tabla

### 8.5 Otras Funcionalidades

#### 8.5.1 Gestión de Mecánicos

- Registro con especialidades técnicas
- Asignación a escuderías
- Visualización de experiencia

#### 8.5.2 Gestión de Circuitos

- Visualización de trazados disponibles
- Información técnica detallada
- Características geográficas

---

## 9. INSTALACIÓN Y CONFIGURACIÓN

### 9.1 Prerrequisitos del Sistema

**Software necesario:**

- Java Development Kit (JDK) 17 o superior
- Sistema operativo: Windows, macOS o Linux
- Memoria RAM: mínimo 2GB
- Espacio en disco: 100MB

### 9.2 Instalación

#### 9.2.1 Descargar el Proyecto

```bash
git clone https://github.com/Martinzanandrea/GestionFormula1.git
cd GestionFormula1
```

#### 9.2.2 Compilar el Código

```bash
javac -cp . -d bin src/modelo/*.java src/controlador/*.java src/vista/*.java
```

#### 9.2.3 Ejecutar la Aplicación

```bash
java -cp bin vista.VentanaPrincipal
```

### 9.3 Configuración Inicial

**El sistema incluye datos de ejemplo automáticos:**

- 14 pilotos con contratos vigentes
- 7 escuderías oficiales F1
- 14 autos asignados
- 3 circuitos históricos
- 3 carreras programadas

**No requiere configuración adicional para uso inmediato.**

### 9.4 Generación de Documentación

```bash
javadoc -d docs -cp . -sourcepath src -subpackages modelo:controlador:vista -author -version -use -windowtitle "Sistema de Gestión Formula 1"
```

---

## 10. PRUEBAS Y VALIDACIONES

### 10.1 Pruebas Funcionales Realizadas

#### 10.1.1 Gestión de Pilotos

- ✅ Registro de pilotos con validación de datos
- ✅ Asignación de contratos con fechas
- ✅ Verificación de contratos vigentes
- ✅ Cálculo automático de puntuaciones

#### 10.1.2 Sistema de Carreras

- ✅ Planificación con validación de fechas futuras
- ✅ Inscripción individual de participantes
- ✅ Selección automática de 10 pilotos
- ✅ Inscripción masiva hasta 20 participantes
- ✅ Registro de resultados completos
- ✅ Finalización con cálculo de puntos

#### 10.1.3 Validaciones de Negocio

- ✅ Fechas futuras obligatorias para carreras
- ✅ Posiciones únicas en resultados
- ✅ Límite de participantes respetado
- ✅ Contratos vigentes verificados
- ✅ Autos disponibles controlados

### 10.2 Pruebas de Interfaz

#### 10.2.1 Usabilidad

- ✅ Navegación intuitiva entre módulos
- ✅ Iconografía clara y descriptiva
- ✅ Mensajes informativos para el usuario
- ✅ Confirmaciones para operaciones críticas

#### 10.2.2 Rendimiento

- ✅ Carga rápida de interfaces
- ✅ Actualización en tiempo real de tablas
- ✅ Respuesta inmediata en operaciones CRUD
- ✅ Gestión eficiente de memoria

### 10.3 Casos de Prueba Específicos

#### 10.3.1 Caso: Inscripción Automática de 10 Pilotos

**Objetivo:** Verificar la funcionalidad de selección automática

**Pasos:**

1. Crear nueva carrera futura
2. Ir a inscripciones
3. Hacer clic en "🎯 Selección Auto (10)"
4. Confirmar selección

**Resultado esperado:** 10 pilotos inscritos automáticamente
**Estado:** ✅ APROBADO

#### 10.3.2 Caso: Error de Finalización Corregido

**Objetivo:** Verificar corrección del error null pointer

**Pasos:**

1. Seleccionar carrera con resultados
2. Intentar finalizar carrera
3. Verificar que no aparezca error null

**Resultado esperado:** Finalización exitosa sin errores
**Estado:** ✅ APROBADO

#### 10.3.3 Caso: Validación de Fechas Futuras

**Objetivo:** Verificar que solo se permitan fechas futuras

**Pasos:**

1. Intentar crear carrera con fecha pasada
2. Verificar mensaje de error
3. Crear carrera con fecha futura válida

**Resultado esperado:** Rechazo de fechas pasadas, aceptación de futuras
**Estado:** ✅ APROBADO

---

## 11. CONCLUSIONES

### 11.1 Objetivos Alcanzados

#### 11.1.1 Técnicos

- ✅ **Arquitectura MVC implementada correctamente:** Clara separación entre modelo, vista y controlador
- ✅ **Principios POO aplicados:** Herencia, encapsulación, polimorfismo
- ✅ **Sistema robusto de validaciones:** Control de integridad en todos los niveles
- ✅ **Interfaz de usuario moderna:** Diseño intuitivo con excelente usabilidad

#### 11.1.2 Funcionales

- ✅ **Gestión completa de entidades F1:** Todas las entidades del dominio implementadas
- ✅ **Sistema de carreras avanzado:** Desde planificación hasta finalización
- ✅ **Puntuación automática:** Cálculos según reglamento oficial F1
- ✅ **Funcionalidades especiales:** Selección automática de pilotos única

### 11.2 Innovaciones Implementadas

#### 11.2.1 Selección Automática de 10 Pilotos

Funcionalidad única que permite inscribir exactamente 10 pilotos de manera automática, ideal para:

- Pruebas rápidas del sistema
- Carreras de demostración
- Configuración inicial de campeonatos

#### 11.2.2 Validaciones Temporales Avanzadas

- Control estricto de fechas futuras
- Verificación de contratos vigentes
- Gestión de estados de carrera

#### 11.2.3 Sistema de Puntuación Inteligente

- Cálculo automático según posición
- Bonus por vuelta más rápida
- Actualización en tiempo real de clasificaciones

### 11.3 Aprendizajes Obtenidos

#### 11.3.1 Técnicos

- **Arquitectura MVC:** Importancia de la separación de responsabilidades
- **Java Swing:** Desarrollo de interfaces gráficas complejas
- **Validaciones:** Diseño de controles robustos de integridad
- **Documentación:** Valor del código bien documentado

#### 11.3.2 Metodológicos

- **Desarrollo iterativo:** Ventajas de mejoras incrementales
- **Pruebas continuas:** Importancia de validar cada funcionalidad
- **Refactoring:** Necesidad de mejorar código existente
- **Gestión de errores:** Manejo robusto de excepciones

### 11.3 Posibles Mejoras Futuras

#### 11.3.1 Funcionalidades Adicionales

- **Base de datos:** Persistencia en base de datos relacional
- **Reportes avanzados:** Estadísticas más detalladas
- **Import/Export:** Intercambio de datos con formatos estándar
- **Multi-idioma:** Soporte para múltiples idiomas

#### 11.3.2 Mejoras Técnicas

- **Arquitectura web:** Migración a aplicación web
- **APIs REST:** Servicios web para integración
- **Pruebas unitarias:** Cobertura completa de testing
- **Optimizaciones:** Mejoras de rendimiento

---

## 12. ANEXOS

### 12.1 Estructura Completa del Proyecto

```
GestionFormula1/
├── src/
│   ├── modelo/
│   │   ├── Auto.java
│   │   ├── Circuito.java
│   │   ├── Escuderia.java
│   │   ├── Especialidad.java
│   │   ├── GranPremio.java
│   │   ├── Mecanico.java
│   │   ├── Pais.java
│   │   ├── Participacion.java
│   │   ├── Persona.java
│   │   ├── Piloto.java
│   │   ├── PilotoEscuderia.java
│   │   └── SistemaPuntuacion.java
│   ├── controlador/
│   │   ├── DatosEjemplo.java
│   │   ├── GestorFormula1.java
│   │   └── ValidadorFormula1.java
│   └── vista/
│       ├── VentanaAutos.java
│       ├── VentanaCarreras.java
│       ├── VentanaCircuitos.java
│       ├── VentanaContratos.java
│       ├── VentanaEscuderias.java
│       ├── VentanaMecanicos.java
│       ├── VentanaPilotos.java
│       ├── VentanaPrincipal.java
│       └── VentanaReportes.java
├── bin/                    # Clases compiladas
├── docs/                   # Documentación JavaDoc
│   ├── index.html         # Documentación principal
│   ├── inicio.html        # Página de bienvenida
│   ├── modelo/            # Documentación del modelo
│   ├── controlador/       # Documentación del controlador
│   └── vista/             # Documentación de interfaces
├── README.md              # Documentación general
├── DocumentacionSistema.md # Este documento
└── .gitignore
```

### 12.2 Comandos de Compilación y Ejecución

#### 12.2.1 Compilación Completa

```bash
javac -cp . -d bin src/modelo/*.java src/controlador/*.java src/vista/*.java
```

#### 12.2.2 Ejecución de la Aplicación

```bash
java -cp bin vista.VentanaPrincipal
```

#### 12.2.3 Generación de Documentación JavaDoc

```bash
javadoc -d docs -cp . -sourcepath src -subpackages modelo:controlador:vista \
-author -version -use \
-windowtitle "Sistema de Gestión Formula 1" \
-doctitle "Documentación del Sistema de Gestión de Fórmula 1" \
-header "<b>Gestión F1</b>" \
-encoding UTF-8 -charset UTF-8
```

### 12.3 Información de Versiones

| Componente | Versión  | Descripción                 |
| ---------- | -------- | --------------------------- |
| Java       | 17+      | Lenguaje principal          |
| Swing      | Built-in | Framework GUI               |
| JavaDoc    | Built-in | Generación de documentación |
| Sistema    | 1.0      | Versión del proyecto        |

### 12.4 Enlaces Útiles

- **Código fuente:** [GitHub Repository](https://github.com/Martinzanandrea/GestionFormula1)
- **Documentación JavaDoc:** `docs/index.html`
- **Página de inicio:** `docs/inicio.html`
- **Manual de usuario:** Este documento, Sección 8

---

**🏁 FIN DEL DOCUMENTO**

_Sistema de Gestión de Fórmula 1 - Versión 1.0_  
_Desarrollado para Taller de Programación Orientada a Objetos_  
_Noviembre 2025_
