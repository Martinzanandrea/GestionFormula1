package modelo;

/**
 * Clase que representa un piloto de Fórmula 1.
 * <p>
 * Esta clase encapsula toda la información relacionada con un piloto de F1,
 * incluyendo datos personales, estadísticas de carrera, número asignado,
 * experiencia y afiliación a una escudería.
 * </p>
 * 
 * @author Sistema de Gestión F1
 * @version 1.0
 * @since 1.0
 */
public class Piloto {
    /** Nombre del piloto */
    private String nombre;

    /** Apellido del piloto */
    private String apellido;

    /** Edad del piloto en años */
    private int edad;

    /** Nacionalidad del piloto */
    private String nacionalidad;

    /** Número único del piloto (1-99) */
    private int numero;

    /** Años de experiencia en competición */
    private int experiencia;

    /** Escudería a la que pertenece el piloto */
    private Escuderia escuderia;

    /** Puntos totales acumulados en la temporada */
    private int puntosTotales;

    /**
     * Constructor para crear una nueva instancia de Piloto.
     * <p>
     * Inicializa un piloto con la información básica requerida.
     * Los puntos totales se inicializan en cero.
     * </p>
     * 
     * @param nombre       Nombre del piloto (no puede ser null o vacío)
     * @param apellido     Apellido del piloto (no puede ser null o vacío)
     * @param edad         Edad del piloto (debe estar entre 18 y 50 años)
     * @param nacionalidad Nacionalidad del piloto (no puede ser null o vacío)
     * @param numero       Número único del piloto (debe estar entre 1 y 99)
     * @param experiencia  Años de experiencia del piloto (no puede ser negativo)
     * 
     * @throws IllegalArgumentException si algún parámetro no cumple las
     *                                  validaciones
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
     * Suma puntos al total acumulado del piloto.
     * <p>
     * Este método se utiliza para actualizar los puntos del piloto
     * cuando finaliza una carrera y obtiene una posición puntuable.
     * </p>
     * 
     * @param puntos Cantidad de puntos a sumar (debe ser positivo)
     * @throws IllegalArgumentException si los puntos son negativos
     */
    public void sumarPuntos(int puntos) {
        if (puntos < 0) {
            throw new IllegalArgumentException("Los puntos no pueden ser negativos");
        }
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
     * Obtiene el nombre completo del piloto concatenando nombre y apellido.
     * 
     * @return Nombre completo del piloto en formato "Nombre Apellido"
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Representación textual del piloto mostrando número, nombre y nacionalidad.
     * 
     * @return Cadena en formato "#número nombre completo (nacionalidad)"
     */
    @Override
    public String toString() {
        return "#" + numero + " " + getNombreCompleto() + " (" + nacionalidad + ")";
    }

    /**
     * Compara dos pilotos basándose en su número único.
     * 
     * @param obj Objeto a comparar
     * @return true si tienen el mismo número, false en caso contrario
     */
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