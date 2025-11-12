package modelo;

/**
 * Clase que representa un circuito de Fórmula 1
 */
public class Circuito {
    private String nombre;
    private Pais pais;
    private double longitud; // en kilómetros
    private int numeroCurvas;
    private String tipo; // urbano, permanente, etc.

    /**
     * Constructor de la clase Circuito
     * 
     * @param nombre       Nombre del circuito
     * @param pais         País donde se encuentra el circuito
     * @param longitud     Longitud del circuito en kilómetros
     * @param numeroCurvas Número de curvas del circuito
     * @param tipo         Tipo de circuito
     */
    public Circuito(String nombre, Pais pais, double longitud, int numeroCurvas, String tipo) {
        this.nombre = nombre;
        this.pais = pais;
        this.longitud = longitud;
        this.numeroCurvas = numeroCurvas;
        this.tipo = tipo;
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

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getNumeroCurvas() {
        return numeroCurvas;
    }

    public void setNumeroCurvas(int numeroCurvas) {
        this.numeroCurvas = numeroCurvas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nombre + " - " + pais.getNombre() + " (" + longitud + " km)";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Circuito circuito = (Circuito) obj;
        return nombre.equals(circuito.nombre) && pais.equals(circuito.pais);
    }

    @Override
    public int hashCode() {
        return (nombre + pais.getNombre()).hashCode();
    }
}