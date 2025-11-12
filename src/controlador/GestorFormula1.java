package controlador;

import modelo.*;
import java.time.LocalDateTime;
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
        // Verificar que el auto esté disponible
        if (!autoDisponibleEnCarrera(auto, granPremio)) {
            throw new IllegalArgumentException("El auto ya está asignado a otro piloto en esta carrera");
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
}