package modelo;

/**
 * Clase abstracta que representa una persona en el sistema de Fórmula 1.
 * <p>
 * Esta clase define los atributos y comportamientos básicos comunes
 * a todas las personas del sistema, como pilotos y mecánicos.
 * </p>
 * 
 * @author Sistema de Gestión F1
 * @version 1.0
 * @since 1.0
 */
public abstract class Persona {
    /** Documento Nacional de Identidad */
    protected String dni;

    /** Nombre de la persona */
    protected String nombre;

    /** Apellido de la persona */
    protected String apellido;

    /**
     * Constructor de la clase Persona.
     * 
     * @param dni      Documento Nacional de Identidad (no puede ser null o vacío)
     * @param nombre   Nombre de la persona (no puede ser null o vacío)
     * @param apellido Apellido de la persona (no puede ser null o vacío)
     * @throws IllegalArgumentException si algún parámetro no es válido
     */
    public Persona(String dni, String nombre, String apellido) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede estar vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }

        this.dni = dni.trim();
        this.nombre = nombre.trim();
        this.apellido = apellido.trim();
    }

    // ==================== GETTERS Y SETTERS ====================

    /**
     * Obtiene el DNI de la persona.
     * 
     * @return DNI de la persona
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI de la persona.
     * 
     * @param dni Nuevo DNI (no puede ser null o vacío)
     * @throws IllegalArgumentException si el DNI no es válido
     */
    public void setDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede estar vacío");
        }
        this.dni = dni.trim();
    }

    /**
     * Obtiene el nombre de la persona.
     * 
     * @return Nombre de la persona
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la persona.
     * 
     * @param nombre Nuevo nombre (no puede ser null o vacío)
     * @throws IllegalArgumentException si el nombre no es válido
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    /**
     * Obtiene el apellido de la persona.
     * 
     * @return Apellido de la persona
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido de la persona.
     * 
     * @param apellido Nuevo apellido (no puede ser null o vacío)
     * @throws IllegalArgumentException si el apellido no es válido
     */
    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        this.apellido = apellido.trim();
    }

    /**
     * Obtiene el nombre completo de la persona.
     * 
     * @return Nombre completo en formato "Nombre Apellido"
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    // ==================== MÉTODOS ABSTRACTOS ====================

    /**
     * Método abstracto para obtener la representación en string específica de cada
     * tipo de persona.
     * 
     * @return Representación en string de la persona
     */
    @Override
    public abstract String toString();

    // ==================== MÉTODOS EQUALS Y HASHCODE ====================

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Persona persona = (Persona) obj;
        return dni != null && dni.equals(persona.dni);
    }

    @Override
    public int hashCode() {
        return dni != null ? dni.hashCode() : 0;
    }
}