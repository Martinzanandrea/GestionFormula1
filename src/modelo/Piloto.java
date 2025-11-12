package modelo;

/**
 * Clase que representa un piloto de Fórmula 1
 */
public class Piloto {
    private String nombre;
    private String apellido;
    private int edad;
    private String nacionalidad;
    private int numero;
    private int experiencia; // años de experiencia
    private Escuderia escuderia;
    private int puntosTotales;

    /**
     * Constructor de la clase Piloto
     * 
     * @param nombre       Nombre del piloto
     * @param apellido     Apellido del piloto
     * @param edad         Edad del piloto
     * @param nacionalidad Nacionalidad del piloto
     * @param numero       Número del piloto
     * @param experiencia  Años de experiencia del piloto
     */
    public Piloto(String nombre, String apellido, int edad, String nacionalidad, int numero, int experiencia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
        this.numero = numero;
        this.experiencia = experiencia;
        this.puntosTotales = 0;
    }

    /**
     * Suma puntos al total del piloto
     * 
     * @param puntos Puntos a sumar
     */
    public void sumarPuntos(int puntos) {
        this.puntosTotales += puntos;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public Escuderia getEscuderia() {
        return escuderia;
    }

    public void setEscuderia(Escuderia escuderia) {
        this.escuderia = escuderia;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    /**
     * Obtiene el nombre completo del piloto
     * 
     * @return Nombre completo
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return "#" + numero + " " + getNombreCompleto() + " (" + nacionalidad + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Piloto piloto = (Piloto) obj;
        return numero == piloto.numero;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(numero);
    }
}