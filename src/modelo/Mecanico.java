package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un mecánico de Fórmula 1.
 * <p>
 * Un mecánico es un miembro del personal técnico de una escudería,
 * responsable del mantenimiento y configuración de los autos de carrera.
 * Puede tener múltiples especialidades técnicas.
 * </p>
 * 
 * @author Sistema de Gestión F1
 * @version 1.0
 * @since 1.0
 */
public class Mecanico extends Persona {
    /** Años de experiencia en F1 */
    private int experiencia;
    /** Lista de especialidades técnicas del mecánico */
    private List<Especialidad> especialidades;

    /**
     * Constructor de la clase Mecanico.
     * <p>
     * Crea un nuevo mecánico con la información básica.
     * La lista de especialidades se inicializa vacía.
     * </p>
     * 
     * @param dni         Documento Nacional de Identidad (no puede ser null o
     *                    vacío)
     * @param nombre      Nombre del mecánico (no puede ser null o vacío)
     * @param apellido    Apellido del mecánico (no puede ser null o vacío)
     * @param experiencia Años de experiencia (debe ser no negativo)
     * @throws IllegalArgumentException si algún parámetro no es válido
     */
    public Mecanico(String dni, String nombre, String apellido, int experiencia) {
        super(dni, nombre, apellido);

        if (experiencia < 0) {
            throw new IllegalArgumentException("La experiencia no puede ser negativa");
        }

        this.experiencia = experiencia;
        this.especialidades = new ArrayList<>();
    }

    // ==================== GESTIÓN DE ESPECIALIDADES ====================

    /**
     * Agrega una especialidad al mecánico.
     * <p>
     * No permite especialidades duplicadas.
     * </p>
     * 
     * @param especialidad Especialidad técnica a agregar
     * @throws IllegalArgumentException si la especialidad es null
     */
    public void agregarEspecialidad(Especialidad especialidad) {
        if (!especialidades.contains(especialidad)) {
            especialidades.add(especialidad);
        }
    }

    /**
     * Remueve una especialidad del mecánico
     * 
     * @param especialidad Especialidad a remover
     */
    public void removerEspecialidad(Especialidad especialidad) {
        especialidades.remove(especialidad);
    }

    // Getters y Setters
    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public List<Especialidad> getEspecialidades() {
        return new ArrayList<>(especialidades);
    }

    /**
     * Obtiene las especialidades como string separado por comas
     * 
     * @return Especialidades concatenadas
     */
    public String getEspecialidadesString() {
        return especialidades.stream()
                .map(Especialidad::toString)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Sin especialidades");
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido() + " - " + experiencia + " años - " + getEspecialidadesString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return super.equals(obj); // Usa la comparación por DNI de Persona
    }

    @Override
    public int hashCode() {
        return super.hashCode(); // Usa el hash del DNI de Persona
    }
}