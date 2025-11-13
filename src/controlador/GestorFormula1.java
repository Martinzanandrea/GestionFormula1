package controlador;

import modelo.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador principal del sistema de gestión de Fórmula 1.
 * <p>
 * Esta clase centraliza toda la lógica de negocio del sistema, gestionando
 * las operaciones CRUD para todas las entidades del dominio F1:
 * países, escuderías, pilotos, autos, mecánicos, circuitos y grandes premios.
 * </p>
 * <p>
 * Implementa el patrón Singleton para garantizar una única instancia
 * del controlador y mantiene la consistencia de los datos durante
 * toda la sesión de la aplicación.
 * </p>
 * 
 * @author Sistema de Gestión F1
 * @version 1.0
 * @since 1.0
 */
public class GestorFormula1 {
    /** Lista de países disponibles en el sistema */
    private List<Pais> paises;

    /** Lista de escuderías registradas */
    private List<Escuderia> escuderias;

    /** Lista de pilotos activos */
    private List<Piloto> pilotos;

    /** Lista de vehículos del campeonato */
    private List<Auto> autos;

    /** Lista de personal técnico */
    private List<Mecanico> mecanicos;

    /** Lista de circuitos disponibles */
    private List<Circuito> circuitos;

    /** Lista de eventos de carrera programados */
    private List<GranPremio> grandesPremios;

    /** Lista de relaciones piloto-escudería con fechas */
    private List<PilotoEscuderia> pilotoEscuderias;

    /**
     * Constructor que inicializa todas las colecciones del sistema.
     * <p>
     * Crea las listas vacías para gestionar todas las entidades del dominio F1.
     * Todas las colecciones son implementadas como ArrayList para permitir
     * un acceso eficiente y operaciones de modificación dinámicas.
     * </p>
     * 
     * @see ArrayList
     * @since 1.0
     */
    public GestorFormula1() {
        this.paises = new ArrayList<>();
        this.escuderias = new ArrayList<>();
        this.pilotos = new ArrayList<>();
        this.autos = new ArrayList<>();
        this.mecanicos = new ArrayList<>();
        this.circuitos = new ArrayList<>();
        this.grandesPremios = new ArrayList<>();
        this.pilotoEscuderias = new ArrayList<>();
    }

    // ==================== MÉTODOS DE REGISTRO ====================

    /**
     * Registra un nuevo país en el sistema.
     * <p>
     * Valida que el país no exista previamente en el sistema antes de agregarlo.
     * La validación se realiza utilizando el método equals() de la clase Pais.
     * </p>
     * 
     * @param pais El país a registrar en el sistema
     * @throws IllegalArgumentException si el país ya está registrado en el sistema
     * @throws NullPointerException     si el país proporcionado es null
     * @see Pais#equals(Object)
     * @since 1.0
     */
    public void registrarPais(Pais pais) {
        if (pais == null) {
            throw new NullPointerException("El país no puede ser null");
        }
        if (paises.contains(pais)) {
            throw new IllegalArgumentException("El país ya está registrado");
        }
        paises.add(pais);
    }

    /**
     * Registra una nueva escudería en el sistema.
     * <p>
     * Valida que la escudería no exista previamente en el sistema antes de
     * agregarla.
     * La validación se realiza utilizando el método equals() de la clase Escuderia.
     * Una escudería debe tener un país de origen válido y un presupuesto asignado.
     * </p>
     * 
     * @param escuderia La escudería a registrar en el sistema
     * @throws IllegalArgumentException si la escudería ya está registrada en el
     *                                  sistema
     * @throws NullPointerException     si la escudería proporcionada es null
     * @see Escuderia#equals(Object)
     * @since 1.0
     */
    public void registrarEscuderia(Escuderia escuderia) {
        if (escuderia == null) {
            throw new NullPointerException("La escudería no puede ser null");
        }
        if (escuderias.contains(escuderia)) {
            throw new IllegalArgumentException("La escudería ya está registrada");
        }
        escuderias.add(escuderia);
    }

    /**
     * Registra un nuevo piloto en el sistema.
     * <p>
     * Valida que el piloto no exista previamente y que su número de carrera
     * esté disponible. En Fórmula 1, cada piloto debe tener un número único
     * que lo identifique durante toda la temporada.
     * </p>
     * <p>
     * La validación incluye:
     * <ul>
     * <li>Verificación de duplicados por equals()</li>
     * <li>Verificación de número único</li>
     * <li>Validación de datos no nulos</li>
     * </ul>
     * </p>
     * 
     * @param piloto El piloto a registrar en el sistema
     * @throws IllegalArgumentException si el piloto ya existe o el número está
     *                                  ocupado
     * @throws NullPointerException     si el piloto proporcionado es null
     * @see Piloto#getNumero()
     * @see Piloto#equals(Object)
     * @since 1.0
     */
    public void registrarPiloto(Piloto piloto) {
        if (piloto == null) {
            throw new NullPointerException("El piloto no puede ser null");
        }

        // Verificar que el número no esté ocupado
        for (Piloto p : pilotos) {
            if (p.getNumero() == piloto.getNumero()) {
                throw new IllegalArgumentException("El número " + piloto.getNumero() + " ya está ocupado");
            }
        }
        pilotos.add(piloto);
    }

    /**
     * Registra un nuevo auto en el sistema.
     * <p>
     * Valida que el auto no exista previamente en el sistema antes de agregarlo.
     * Cada auto está asociado a una escudería específica y posee características
     * técnicas únicas como motor, aerodinámica y configuración de chasis.
     * </p>
     * 
     * @param auto El auto a registrar en el sistema
     * @throws IllegalArgumentException si el auto ya está registrado en el sistema
     * @throws NullPointerException     si el auto proporcionado es null
     * @see Auto#equals(Object)
     * @since 1.0
     */
    public void registrarAuto(Auto auto) {
        if (auto == null) {
            throw new NullPointerException("El auto no puede ser null");
        }
        if (autos.contains(auto)) {
            throw new IllegalArgumentException("El auto ya está registrado");
        }
        autos.add(auto);
    }

    /**
     * Registra un nuevo mecánico en el sistema.
     * <p>
     * Valida que el mecánico no exista previamente en el sistema antes de
     * agregarlo.
     * Los mecánicos son personal especializado responsable del mantenimiento
     * y preparación de los vehículos de competición.
     * </p>
     * 
     * @param mecanico El mecánico a registrar en el sistema
     * @throws IllegalArgumentException si el mecánico ya está registrado en el
     *                                  sistema
     * @throws NullPointerException     si el mecánico proporcionado es null
     * @see Mecanico#equals(Object)
     * @since 1.0
     */
    public void registrarMecanico(Mecanico mecanico) {
        if (mecanico == null) {
            throw new NullPointerException("El mecánico no puede ser null");
        }
        if (mecanicos.contains(mecanico)) {
            throw new IllegalArgumentException("El mecánico ya está registrado");
        }
        mecanicos.add(mecanico);
    }

    /**
     * Registra un nuevo circuito en el sistema.
     * <p>
     * Valida que el circuito no exista previamente en el sistema antes de
     * agregarlo.
     * Los circuitos representan las pistas donde se realizan los Grandes Premios,
     * cada uno con características únicas de longitud, número de vueltas y país
     * sede.
     * </p>
     * 
     * @param circuito El circuito a registrar en el sistema
     * @throws IllegalArgumentException si el circuito ya está registrado en el
     *                                  sistema
     * @throws NullPointerException     si el circuito proporcionado es null
     * @see Circuito#equals(Object)
     * @since 1.0
     */
    public void registrarCircuito(Circuito circuito) {
        if (circuito == null) {
            throw new NullPointerException("El circuito no puede ser null");
        }
        if (circuitos.contains(circuito)) {
            throw new IllegalArgumentException("El circuito ya está registrado");
        }
        circuitos.add(circuito);
    }

    /**
     * Registra un nuevo Gran Premio en el sistema.
     * <p>
     * Valida que el Gran Premio no exista previamente en el sistema antes de
     * agregarlo.
     * Los Grandes Premios son los eventos principales del campeonato, cada uno
     * asociado a un circuito específico y con una fecha programada.
     * </p>
     * 
     * @param granPremio El Gran Premio a registrar en el sistema
     * @throws IllegalArgumentException si el Gran Premio ya está registrado en el
     *                                  sistema
     * @throws NullPointerException     si el Gran Premio proporcionado es null
     * @see GranPremio#equals(Object)
     * @since 1.0
     */
    public void registrarGranPremio(GranPremio granPremio) {
        if (granPremio == null) {
            throw new NullPointerException("El Gran Premio no puede ser null");
        }
        if (grandesPremios.contains(granPremio)) {
            throw new IllegalArgumentException("El Gran Premio ya está registrado");
        }
        grandesPremios.add(granPremio);
    }

    // ==================== MÉTODOS DE GESTIÓN ====================

    /**
     * Asigna un piloto a una escudería específica.
     * <p>
     * Establece la relación entre piloto y escudería, permitiendo que
     * el piloto compita representando a dicha escudería en el campeonato.
     * Esta operación es fundamental para la gestión de equipos en F1.
     * </p>
     * 
     * @param piloto    El piloto a asignar
     * @param escuderia La escudería destino que representará el piloto
     * @throws NullPointerException si piloto o escudería son null
     * @see Escuderia#agregarPiloto(Piloto)
     * @since 1.0
     */
    public void asignarPilotoAEscuderia(Piloto piloto, Escuderia escuderia) {
        if (piloto == null) {
            throw new NullPointerException("El piloto no puede ser null");
        }
        if (escuderia == null) {
            throw new NullPointerException("La escudería no puede ser null");
        }
        escuderia.agregarPiloto(piloto);
    }

    /**
     * Verifica la disponibilidad de un auto para un Gran Premio específico.
     * <p>
     * Comprueba que un auto no esté asignado a múltiples pilotos en la misma
     * carrera,
     * garantizando la integridad de las asignaciones de vehículos durante los
     * eventos.
     * En F1, cada auto puede ser utilizado por un único piloto por carrera.
     * </p>
     * 
     * @param auto       El auto a verificar
     * @param granPremio El Gran Premio donde se quiere verificar disponibilidad
     * @return true si el auto está disponible, false si ya está asignado
     * @throws NullPointerException si auto o granPremio son null
     * @see Auto
     * @see GranPremio
     * @since 1.0
     */
    public boolean autoDisponibleEnCarrera(Auto auto, GranPremio granPremio) {
        if (auto == null) {
            throw new NullPointerException("El auto no puede ser null");
        }
        if (granPremio == null) {
            throw new NullPointerException("El Gran Premio no puede ser null");
        }

        for (Participacion participacion : granPremio.getParticipaciones()) {
            if (participacion.getAuto().equals(auto)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Inscribe un piloto en un Gran Premio con un auto específico.
     * <p>
     * Crea una nueva participación que vincula piloto, auto y Gran Premio.
     * Realiza validaciones para garantizar que no haya conflictos de asignación
     * y que todos los elementos estén disponibles y registrados en el sistema.
     * </p>
     * <p>
     * Validaciones realizadas:
     * <ul>
     * <li>Disponibilidad del auto en la carrera</li>
     * <li>Existencia de piloto y auto en el sistema</li>
     * <li>Coherencia de datos para la participación</li>
     * </ul>
     * </p>
     * 
     * @param piloto     El piloto a inscribir en la carrera
     * @param auto       El auto que utilizará en la carrera
     * @param granPremio El Gran Premio en el que participará
     * @throws IllegalArgumentException si hay conflictos de asignación o datos
     *                                  inválidos
     * @throws NullPointerException     si algún parámetro es null
     * @see Participacion
     * @see GranPremio#agregarParticipacion(Participacion)
     * @since 1.0
     */
    public void inscribirPilotoEnCarrera(Piloto piloto, Auto auto, GranPremio granPremio) {
        // Usar el validador completo para verificar todas las reglas
        String error = ValidadorFormula1.validarNuevaParticipacion(piloto, auto, granPremio, this);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }

        // Crear y agregar la participación
        Participacion participacion = new Participacion(piloto, auto, granPremio);
        granPremio.agregarParticipacion(participacion);
    }

    // ==================== MÉTODOS DE CONSULTA Y REPORTES ====================

    /**
     * Obtiene el ranking de pilotos ordenado por puntos totales.
     * <p>
     * Genera una clasificación de todos los pilotos registrados en el sistema
     * basada en sus puntos acumulados durante el campeonato. La lista se ordena
     * en forma descendente, con el líder del campeonato en primer lugar.
     * </p>
     * 
     * @return Lista de pilotos ordenada por puntos totales (descendente)
     * @see Piloto#getPuntosTotales()
     * @since 1.0
     */
    public List<Piloto> getRankingPilotos() {
        return pilotos.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getPuntosTotales(), p1.getPuntosTotales()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene los resultados de carreras finalizadas en un rango de fechas.
     * <p>
     * Filtra los Grandes Premios que se realizaron dentro del período especificado
     * y que ya han finalizado. Los resultados se ordenan cronológicamente para
     * facilitar el análisis temporal del campeonato.
     * </p>
     * 
     * @param fechaInicio Fecha y hora de inicio del rango de búsqueda
     * @param fechaFin    Fecha y hora de fin del rango de búsqueda
     * @return Lista de Grandes Premios finalizados en el rango, ordenados
     *         cronológicamente
     * @throws NullPointerException si fechaInicio o fechaFin son null
     * @see GranPremio#getFechaHora()
     * @see GranPremio#isFinalizada()
     * @since 1.0
     */
    public List<GranPremio> getResultadosEnRango(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (fechaInicio == null) {
            throw new NullPointerException("La fecha de inicio no puede ser null");
        }
        if (fechaFin == null) {
            throw new NullPointerException("La fecha de fin no puede ser null");
        }

        return grandesPremios.stream()
                .filter(gp -> gp.getFechaHora().isAfter(fechaInicio) && gp.getFechaHora().isBefore(fechaFin))
                .filter(GranPremio::isFinalizada)
                .sorted(Comparator.comparing(GranPremio::getFechaHora))
                .collect(Collectors.toList());
    }

    /**
     * Cuenta el número total de podios obtenidos por un piloto específico.
     * <p>
     * Analiza todas las participaciones del piloto en Grandes Premios finalizados
     * y cuenta aquellas donde obtuvo una posición de podio (1°, 2° o 3° lugar).
     * Esta métrica es fundamental para evaluar el rendimiento histórico del piloto.
     * </p>
     * 
     * @param piloto El piloto del cual se quiere consultar el histórico de podios
     * @return El número total de podios obtenidos por el piloto
     * @throws NullPointerException si el piloto es null
     * @see Participacion#isPodio()
     * @since 1.0
     */
    public int getPodiosPiloto(Piloto piloto) {
        if (piloto == null) {
            throw new NullPointerException("El piloto no puede ser null");
        }

        return (int) grandesPremios.stream()
                .flatMap(gp -> gp.getParticipaciones().stream())
                .filter(p -> p.getPiloto().equals(piloto))
                .filter(Participacion::isPodio)
                .count();
    }

    /**
     * Cuenta el número total de victorias obtenidas por un piloto específico.
     * <p>
     * Analiza todas las participaciones del piloto en Grandes Premios finalizados
     * y cuenta aquellas donde obtuvo la primera posición. Las victorias son el
     * indicador más importante del rendimiento de un piloto en Fórmula 1.
     * </p>
     * 
     * @param piloto El piloto del cual se quiere consultar el número de victorias
     * @return El número total de victorias obtenidas por el piloto
     * @throws NullPointerException si el piloto es null
     * @see Participacion#getPosicionFinal()
     * @since 1.0
     */
    public int getVictoriasPiloto(Piloto piloto) {
        return (int) grandesPremios.stream()
                .flatMap(gp -> gp.getParticipaciones().stream())
                .filter(p -> p.getPiloto().equals(piloto))
                .filter(p -> p.getPosicionFinal() == 1)
                .count();
    }

    /**
     * Cuenta las veces que un piloto corrió en un circuito específico
     * 
     * @param piloto   Piloto a consultar
     * @param circuito Circuito a consultar
     * @return Número de participaciones
     */
    public int getParticipacionesPilotoEnCircuito(Piloto piloto, Circuito circuito) {
        return (int) grandesPremios.stream()
                .filter(gp -> gp.getCircuito().equals(circuito))
                .flatMap(gp -> gp.getParticipaciones().stream())
                .filter(p -> p.getPiloto().equals(piloto))
                .count();
    }

    /**
     * Cuenta las carreras realizadas en un circuito
     * 
     * @param circuito Circuito a consultar
     * @return Número de carreras
     */
    public int getCarrerasEnCircuito(Circuito circuito) {
        return (int) grandesPremios.stream()
                .filter(gp -> gp.getCircuito().equals(circuito))
                .filter(GranPremio::isFinalizada)
                .count();
    }

    // GETTERS PARA LAS COLECCIONES

    public List<Pais> getPaises() {
        return new ArrayList<>(paises);
    }

    public List<Escuderia> getEscuderias() {
        return new ArrayList<>(escuderias);
    }

    public List<Piloto> getPilotos() {
        return new ArrayList<>(pilotos);
    }

    public List<Auto> getAutos() {
        return new ArrayList<>(autos);
    }

    public List<Mecanico> getMecanicos() {
        return new ArrayList<>(mecanicos);
    }

    public List<Circuito> getCircuitos() {
        return new ArrayList<>(circuitos);
    }

    public List<GranPremio> getGrandesPremios() {
        return new ArrayList<>(grandesPremios);
    }

    // ==================== MÉTODOS DE GESTIÓN DE RESULTADOS ====================

    /**
     * Establece la posición final de un piloto en una carrera y actualiza
     * automáticamente los puntos
     * 
     * @param participacion Participación del piloto
     * @param posicion      Posición final (1-based)
     * @throws IllegalArgumentException si la posición no es válida
     */
    public void establecerPosicionFinal(Participacion participacion, int posicion) {
        if (posicion < 1) {
            throw new IllegalArgumentException("La posición debe ser mayor a 0");
        }

        // Establecer la posición
        participacion.setPosicionFinal(posicion);

        // Actualizar puntos automáticamente según el sistema oficial
        ValidadorFormula1.actualizarPuntosParticipacion(participacion);

        // Actualizar puntos totales del piloto
        actualizarPuntosTotalesPiloto(participacion.getPiloto());
    }

    /**
     * Marca una participación como abandono
     * 
     * @param participacion Participación del piloto
     * @param motivo        Motivo del abandono
     */
    public void marcarAbandono(Participacion participacion, String motivo) {
        participacion.marcarAbandono(motivo);
        ValidadorFormula1.actualizarPuntosParticipacion(participacion);
        actualizarPuntosTotalesPiloto(participacion.getPiloto());
    }

    /**
     * Asigna la vuelta rápida a un piloto (solo si terminó en top 10)
     * 
     * @param participacion     Participación del piloto
     * @param tieneVueltaRapida true si logró la vuelta rápida
     */
    public void asignarVueltaRapida(Participacion participacion, boolean tieneVueltaRapida) {
        participacion.setVueltaRapida(tieneVueltaRapida);
        ValidadorFormula1.actualizarPuntosParticipacion(participacion);
        actualizarPuntosTotalesPiloto(participacion.getPiloto());
    }

    /**
     * Valida y finaliza una carrera verificando que todas las posiciones sean
     * correctas
     * 
     * @param granPremio Gran Premio a finalizar
     * @throws IllegalArgumentException si el granPremio es null
     * @throws IllegalStateException    si hay inconsistencias en las posiciones o
     *                                  la carrera ya está finalizada
     */
    public void finalizarCarrera(GranPremio granPremio) {
        if (granPremio == null) {
            throw new IllegalArgumentException("El Gran Premio no puede ser null");
        }

        if (granPremio.isFinalizada()) {
            throw new IllegalStateException("La carrera ya está finalizada");
        }

        if (!ValidadorFormula1.validarPosicionesUnicas(granPremio)) {
            throw new IllegalStateException(
                    "Las posiciones de la carrera tienen inconsistencias (duplicados o huecos)");
        }

        granPremio.setFinalizada(true);

        // Actualizar puntos de todos los participantes
        if (granPremio.getParticipaciones() != null) {
            for (Participacion participacion : granPremio.getParticipaciones()) {
                ValidadorFormula1.actualizarPuntosParticipacion(participacion);
                actualizarPuntosTotalesPiloto(participacion.getPiloto());
            }
        }
    }

    /**
     * Actualiza los puntos totales de un piloto sumando todas sus participaciones
     * 
     * @param piloto Piloto cuyos puntos se actualizarán
     */
    private void actualizarPuntosTotalesPiloto(Piloto piloto) {
        int puntosTotal = grandesPremios.stream()
                .filter(GranPremio::isFinalizada)
                .flatMap(gp -> gp.getParticipaciones().stream())
                .filter(p -> p.getPiloto().equals(piloto))
                .mapToInt(Participacion::getPuntosObtenidos)
                .sum();

        piloto.setPuntosTotales(puntosTotal);
    }

    /**
     * Establece los resultados completos de una carrera.
     * <p>
     * Permite establecer las posiciones finales de todos los participantes
     * de una carrera de una sola vez.
     * </p>
     * 
     * @param granPremio         Gran Premio cuyos resultados se establecerán
     * @param resultados         Map con piloto como clave y posición como valor
     * @param pilotoVueltaRapida Piloto que obtuvo la vuelta más rápida (puede ser
     *                           null)
     * @throws IllegalArgumentException si hay errores en los resultados
     */
    public void establecerResultadosCarrera(GranPremio granPremio, java.util.Map<Piloto, Integer> resultados,
            Piloto pilotoVueltaRapida) {
        // Validar que no está finalizada
        if (granPremio.isFinalizada()) {
            throw new IllegalArgumentException("La carrera ya está finalizada");
        }

        // Establecer posiciones
        for (java.util.Map.Entry<Piloto, Integer> entrada : resultados.entrySet()) {
            Piloto piloto = entrada.getKey();
            Integer posicion = entrada.getValue();

            // Buscar participación del piloto
            Participacion participacion = granPremio.getParticipaciones().stream()
                    .filter(p -> p.getPiloto().equals(piloto))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "El piloto " + piloto.getNombreCompleto() + " no está inscrito en esta carrera"));

            establecerPosicionFinal(participacion, posicion);
        }

        // Establecer vuelta rápida si se especifica
        if (pilotoVueltaRapida != null) {
            Participacion participacionVR = granPremio.getParticipaciones().stream()
                    .filter(p -> p.getPiloto().equals(pilotoVueltaRapida))
                    .findFirst()
                    .orElseThrow(
                            () -> new IllegalArgumentException("El piloto de la vuelta rápida no está en la carrera"));

            asignarVueltaRapida(participacionVR, true);
        }

        // Finalizar carrera automáticamente
        finalizarCarrera(granPremio);
    }

    /**
     * Obtiene un resumen de resultados de una carrera finalizada.
     * 
     * @param granPremio Gran Premio del cual obtener resultados
     * @return Lista de strings con los resultados formateados
     */
    public java.util.List<String> obtenerResumenResultados(GranPremio granPremio) {
        if (!granPremio.isFinalizada()) {
            throw new IllegalArgumentException("La carrera no está finalizada");
        }

        return granPremio.getResultados().stream()
                .map(p -> String.format("P%d - %s (%s) - %d puntos%s",
                        p.getPosicionFinal(),
                        p.getPiloto().getNombreCompleto(),
                        p.getPiloto().getEscuderia() != null ? p.getPiloto().getEscuderia().getNombre()
                                : "Sin escudería",
                        p.getPuntosObtenidos(),
                        p.isVueltaRapida() ? " + VR" : ""))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Cambia la escudería de un piloto validando las restricciones
     * 
     * @param piloto         Piloto a cambiar
     * @param nuevaEscuderia Nueva escudería
     * @throws IllegalArgumentException si el cambio no es válido
     */
    public void cambiarEscuderiaPiloto(Piloto piloto, Escuderia nuevaEscuderia) {
        if (!ValidadorFormula1.validarPilotoEscuderiaUnica(piloto, nuevaEscuderia, this)) {
            throw new IllegalArgumentException("No se puede cambiar de escudería: el piloto tiene carreras pendientes");
        }

        // Remover de la escudería actual si existe
        if (piloto.getEscuderia() != null) {
            piloto.getEscuderia().removerPiloto(piloto);
        }

        // Agregar a la nueva escudería
        nuevaEscuderia.agregarPiloto(piloto);
    }

    // ==================== MÉTODOS DE GESTIÓN PILOTO-ESCUDERÍA ====================

    /**
     * Asigna un piloto a una escudería con fechas específicas.
     * 
     * @param piloto      Piloto a asignar
     * @param escuderia   Escudería de destino
     * @param fechaInicio Fecha de inicio en formato String
     * @param fechaFin    Fecha de fin en formato String (null si es indefinido)
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    public void asignarPilotoAEscuderia(Piloto piloto, Escuderia escuderia, String fechaInicio, String fechaFin) {
        if (piloto == null) {
            throw new IllegalArgumentException("El piloto no puede ser null");
        }
        if (escuderia == null) {
            throw new IllegalArgumentException("La escudería no puede ser null");
        }
        if (fechaInicio == null || fechaInicio.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de inicio no puede estar vacía");
        }

        // Finalizar relación activa actual si existe
        finalizarRelacionActivaPiloto(piloto, fechaInicio);

        // Crear nueva relación
        PilotoEscuderia relacion = new PilotoEscuderia(fechaInicio, fechaFin, piloto, escuderia);
        pilotoEscuderias.add(relacion);

        // Actualizar referencias
        piloto.setEscuderia(escuderia);
        if (!escuderia.getPilotos().contains(piloto)) {
            escuderia.agregarPiloto(piloto);
        }
    }

    /**
     * Finaliza la relación activa actual de un piloto.
     * 
     * @param piloto   Piloto cuya relación se va a finalizar
     * @param fechaFin Fecha de finalización
     */
    public void finalizarRelacionActivaPiloto(Piloto piloto, String fechaFin) {
        for (PilotoEscuderia relacion : pilotoEscuderias) {
            if (relacion.getPiloto().equals(piloto) && relacion.estaActiva()) {
                relacion.finalizarRelacion(fechaFin);
                break;
            }
        }
    }

    /**
     * Obtiene todas las relaciones piloto-escudería.
     * 
     * @return Lista de todas las relaciones
     */
    public List<PilotoEscuderia> getPilotoEscuderias() {
        return new ArrayList<>(pilotoEscuderias);
    }

    /**
     * Obtiene las relaciones vigentes de un piloto (contratos que no han expirado).
     * 
     * @param piloto Piloto del cual obtener relaciones
     * @return Lista de relaciones vigentes
     */
    public List<PilotoEscuderia> getRelacionesActivasPiloto(Piloto piloto) {
        return pilotoEscuderias.stream()
                .filter(relacion -> relacion.getPiloto().equals(piloto) && relacion.estaVigente())
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el historial completo de un piloto.
     * 
     * @param piloto Piloto del cual obtener el historial
     * @return Lista de todas las relaciones del piloto
     */
    public List<PilotoEscuderia> getHistorialPiloto(Piloto piloto) {
        return pilotoEscuderias.stream()
                .filter(relacion -> relacion.getPiloto().equals(piloto))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene los pilotos actuales de una escudería con sus fechas (solo contratos
     * vigentes).
     * 
     * @param escuderia Escudería de la cual obtener pilotos
     * @return Lista de relaciones vigentes de la escudería
     */
    public List<PilotoEscuderia> getPilotosEscuderiaConFechas(Escuderia escuderia) {
        return pilotoEscuderias.stream()
                .filter(relacion -> relacion.getEscuderia().equals(escuderia) && relacion.estaVigente())
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la lista de pilotos que están libres (sin contrato vigente).
     * 
     * @return Lista de pilotos sin escudería actual
     */
    public List<Piloto> getPilotosLibres() {
        return pilotos.stream()
                .filter(piloto -> getRelacionesActivasPiloto(piloto).isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la escudería actual de un piloto (solo si tiene contrato vigente).
     * 
     * @param piloto Piloto del cual obtener la escudería
     * @return Escudería actual o null si está libre
     */
    public Escuderia getEscuderiaActual(Piloto piloto) {
        List<PilotoEscuderia> relaciones = getRelacionesActivasPiloto(piloto);
        return relaciones.isEmpty() ? null : relaciones.get(0).getEscuderia();
    }

    /**
     * Actualiza las referencias de pilotos en escuderías basándose en contratos
     * vigentes.
     * Limpia automáticamente pilotos con contratos expirados.
     */
    public void actualizarEscuderiasSegunContratos() {
        // Limpiar todas las listas de pilotos en escuderías
        for (Escuderia escuderia : escuderias) {
            escuderia.getPilotos().clear();
        }

        // Limpiar referencias de escuderías en pilotos
        for (Piloto piloto : pilotos) {
            piloto.setEscuderia(null);
        }

        // Restablecer solo las relaciones vigentes
        for (PilotoEscuderia relacion : pilotoEscuderias) {
            if (relacion.estaVigente()) {
                relacion.getPiloto().setEscuderia(relacion.getEscuderia());
                relacion.getEscuderia().agregarPiloto(relacion.getPiloto());
            }
        }
    }
}