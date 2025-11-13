package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una escudería de Fórmula 1
 */
public class Escuderia {
    private String nombre;
    private Pais pais;
    private List<Piloto> pilotos;
    private List<Mecanico> mecanicos;
    private List<Auto> autos;

    /**
     * Constructor de la clase Escuderia
     * 
     * @param nombre Nombre de la escudería
     * @param pais   País de origen de la escudería
     */
    public Escuderia(String nombre, Pais pais) {
        this.nombre = nombre;
        this.pais = pais;
        this.pilotos = new ArrayList<>();
        this.mecanicos = new ArrayList<>();
        this.autos = new ArrayList<>();
    }

    /**
     * Agrega un piloto a la escudería
     * 
     * @param piloto Piloto a agregar
     */
    public void agregarPiloto(Piloto piloto) {
        if (!pilotos.contains(piloto)) {
            pilotos.add(piloto);
            piloto.setEscuderia(this);
        }
    }

    /**
     * Remueve un piloto de la escudería
     * 
     * @param piloto Piloto a remover
     */
    public void removerPiloto(Piloto piloto) {
        if (pilotos.remove(piloto)) {
            piloto.setEscuderia(null);
        }
    }

    /**
     * Agrega un mecánico a la escudería
     * 
     * @param mecanico Mecánico a agregar
     */
    public void agregarMecanico(Mecanico mecanico) {
        if (!mecanicos.contains(mecanico)) {
            mecanicos.add(mecanico);
        }
    }

    /**
     * Remueve un mecánico de la escudería
     * 
     * @param mecanico Mecánico a remover
     */
    public void removerMecanico(Mecanico mecanico) {
        mecanicos.remove(mecanico);
    }

    /**
     * Agrega un auto a la escudería
     * 
     * @param auto Auto a agregar
     */
    public void agregarAuto(Auto auto) {
        if (!autos.contains(auto)) {
            autos.add(auto);
        }
    }

    /**
     * Remueve un auto de la escudería
     * 
     * @param auto Auto a remover
     */
    public void removerAuto(Auto auto) {
        autos.remove(auto);
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Piloto> getPilotos() {
        return new ArrayList<>(pilotos);
    }

    public List<Mecanico> getMecanicos() {
        return new ArrayList<>(mecanicos);
    }

    public List<Auto> getAutos() {
        return new ArrayList<>(autos);
    }

    @Override
    public String toString() {
        return nombre + " - " + (pais != null ? pais.getNombre() : "Sin país");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Escuderia escuderia = (Escuderia) obj;
        return nombre != null && nombre.equals(escuderia.nombre);
    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }
}