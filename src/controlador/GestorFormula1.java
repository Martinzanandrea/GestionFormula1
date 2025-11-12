package controlador;

import modelo.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador principal del sistema de gestión de Fórmula 1
 */
public class GestorFormula1 {
    private List<Pais> paises;
    private List<Escuderia> escuderias;
    private List<Piloto> pilotos;
    private List<Auto> autos;
    private List<Mecanico> mecanicos;
    private List<Circuito> circuitos;
    private List<GranPremio> grandesPremios;

    /**
     * Constructor del gestor
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

    // MÉTODOS DE REGISTRO

    /**
     * Registra un nuevo país
     * 
     * @param pais País a registrar
     * @throws IllegalArgumentException si el país ya existe
     */
    public void registrarPais(Pais pais) {
        if (paises.contains(pais)) {
            throw new IllegalArgumentException("El país ya está registrado");
        }
        paises.add(pais);
    }

    /**
     * Registra una nueva escudería
     * 
     * @param escuderia Escudería a registrar
     * @throws IllegalArgumentException si la escudería ya existe
     */
    public void registrarEscuderia(Escuderia escuderia) {
        if (escuderias.contains(escuderia)) {
            throw new IllegalArgumentException("La escudería ya está registrada");
        }
        escuderias.add(escuderia);
    }

    /**
     * Registra un nuevo piloto
     * 
     * @param piloto Piloto a registrar
     * @throws IllegalArgumentException si el piloto ya existe o el número está
     *                                  ocupado
     */
    public void registrarPiloto(Piloto piloto) {
        // Verificar que el número no esté ocupado
        for (Piloto p : pilotos) {
            if (p.getNumero() == piloto.getNumero()) {
                throw new IllegalArgumentException("El número " + piloto.getNumero() + " ya está ocupado");
            }
        }
        pilotos.add(piloto);
    }

    /**
     * Registra un nuevo auto
     * 
     * @param auto Auto a registrar
     * @throws IllegalArgumentException si el auto ya existe
     */
    public void registrarAuto(Auto auto) {
        if (autos.contains(auto)) {
            throw new IllegalArgumentException("El auto ya está registrado");
        }
        autos.add(auto);
    }

    /**
     * Registra un nuevo mecánico
     * 
     * @param mecanico Mecánico a registrar
     * @throws IllegalArgumentException si el mecánico ya existe
     */
    public void registrarMecanico(Mecanico mecanico) {
        if (mecanicos.contains(mecanico)) {
            throw new IllegalArgumentException("El mecánico ya está registrado");
        }
        mecanicos.add(mecanico);
    }

    /**
     * Registra un nuevo circuito
     * 
     * @param circuito Circuito a registrar
     * @throws IllegalArgumentException si el circuito ya existe
     */
    public void registrarCircuito(Circuito circuito) {
        if (circuitos.contains(circuito)) {
            throw new IllegalArgumentException("El circuito ya está registrado");
        }
        circuitos.add(circuito);
    }

    /**
     * Registra un nuevo Gran Premio
     * 
     * @param granPremio Gran Premio a registrar
     * @throws IllegalArgumentException si el Gran Premio ya existe
     */
    public void registrarGranPremio(GranPremio granPremio) {
        if (grandesPremios.contains(granPremio)) {
            throw new IllegalArgumentException("El Gran Premio ya está registrado");
        }
        grandesPremios.add(granPremio);
    }

    // MÉTODOS DE GESTIÓN

    /**
     * Asigna un piloto a una escudería
     * 
     * @param piloto    Piloto a asignar
     * @param escuderia Escudería destino
     */
    public void asignarPilotoAEscuderia(Piloto piloto, Escuderia escuderia) {
        escuderia.agregarPiloto(piloto);
    }

    /**
     * Verifica que un auto no esté asignado a múltiples pilotos en la misma carrera
     * 
     * @param auto       Auto a verificar
     * @param granPremio Gran Premio a verificar
     * @return true si el auto está disponible
     */
    public boolean autoDisponibleEnCarrera(Auto auto, GranPremio granPremio) {
        for (Participacion participacion : granPremio.getParticipaciones()) {
            if (participacion.getAuto().equals(auto)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Inscribe un piloto en un Gran Premio con un auto específico
     * 
     * @param piloto     Piloto a inscribir
     * @param auto       Auto a utilizar
     * @param granPremio Gran Premio
     * @throws IllegalArgumentException si hay conflictos de asignación
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

    // MÉTODOS DE CONSULTA Y REPORTES

    /**
     * Obtiene el ranking de pilotos por puntos
     * 
     * @return Lista de pilotos ordenada por puntos descendente
     */
    public List<Piloto> getRankingPilotos() {
        return pilotos.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getPuntosTotales(), p1.getPuntosTotales()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene los resultados de carreras en un rango de fechas
     * 
     * @param fechaInicio Fecha de inicio
     * @param fechaFin    Fecha de fin
     * @return Lista de Grandes Premios en el rango
     */
    public List<GranPremio> getResultadosEnRango(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return grandesPremios.stream()
                .filter(gp -> gp.getFechaHora().isAfter(fechaInicio) && gp.getFechaHora().isBefore(fechaFin))
                .filter(GranPremio::isFinalizada)
                .sorted(Comparator.comparing(GranPremio::getFechaHora))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el histórico de podios de un piloto
     * 
     * @param piloto Piloto a consultar
     * @return Número de podios
     */
    public int getPodiosPiloto(Piloto piloto) {
        return (int) grandesPremios.stream()
                .flatMap(gp -> gp.getParticipaciones().stream())
                .filter(p -> p.getPiloto().equals(piloto))
                .filter(Participacion::isPodio)
                .count();
    }

    /**
     * Obtiene el número de victorias de un piloto
     * 
     * @param piloto Piloto a consultar
     * @return Número de victorias
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