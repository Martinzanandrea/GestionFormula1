package modelo;

/**
 * Enumeración que define las especialidades técnicas de los mecánicos de
 * Fórmula 1.
 * <p>
 * Define las diferentes áreas de especialización que puede tener un mecánico
 * en un equipo de F1, cada una con responsabilidades específicas en el
 * mantenimiento
 * y configuración del auto de carrera.
 * </p>
 * 
 * @author Sistema de Gestión F1
 * @version 1.0
 * @since 1.0
 */
public enum Especialidad {
    /** Especialista en neumáticos y sistemas de ruedas */
    NEUMATICOS("Neumáticos"),
    /** Especialista en motor y sistemas de propulsión */
    MOTOR("Motor"),
    /** Especialista en sistemas electrónicos y telemetría */
    ELECTRONICA("Electrónica"),
    /** Especialista en chasis y aerodinámica */
    CHASIS("Chasis");

    /** Descripción legible de la especialidad */
    private final String descripcion;

    /**
     * Constructor privado del enum.
     * <p>
     * Inicializa una especialidad con su descripción correspondiente.
     * </p>
     * 
     * @param descripcion Descripción legible de la especialidad
     */
    Especialidad(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la descripción legible de la especialidad.
     * 
     * @return Descripción de la especialidad en español
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Representación en texto de la especialidad.
     * 
     * @return Descripción legible de la especialidad
     */
    @Override
    public String toString() {
        return descripcion;
    }
}