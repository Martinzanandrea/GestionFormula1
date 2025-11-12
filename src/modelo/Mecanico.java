package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un mecánico de Fórmula 1
 */
public class Mecanico {
    private String nombre;
    private String apellido;
    private int experiencia; // años de experiencia
    private List<String> especialidades;

    /**
     * Constructor de la clase Mecanico
     * 
     * @param nombre      Nombre del mecánico
     * @param apellido    Apellido del mecánico
     * @param experiencia Años de experiencia
     */
    public Mecanico(String nombre, String apellido, int experiencia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.experiencia = experiencia;
        this.especialidades = new ArrayList<>();
    }

    /**
     * Agrega una especialidad al mecánico
     * 
     * @param especialidad Especialidad a agregar
     */
    public void agregarEspecialidad(String especialidad) {
        if (!especialidades.contains(especialidad)) {
            especialidades.add(especialidad);
        }
    }

    /**
     * Remueve una especialidad del mecánico
     * 
     * @param especialidad Especialidad a remover
     */
    public void removerEspecialidad(String especialidad) {
        especialidades.remove(especialidad);
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

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public List<String> getEspecialidades() {
        return new ArrayList<>(especialidades);
    }

    /**
     * Obtiene el nombre completo del mecánico
     * 
     * @return Nombre completo
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Obtiene las especialidades como string separado por comas
     * 
     * @return Especialidades concatenadas
     */
    public String getEspecialidadesString() {
        return String.join(", ", especialidades);
    }

    @Override
    public String toString() {
        return getNombreCompleto() + " - " + experiencia + " años - " + getEspecialidadesString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Mecanico mecanico = (Mecanico) obj;
        return nombre.equals(mecanico.nombre) && apellido.equals(mecanico.apellido);
    }

    @Override
    public int hashCode() {
        return (nombre + apellido).hashCode();
    }
}