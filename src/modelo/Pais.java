package modelo;

/**
 * Clase que representa un país en el sistema de gestión de Fórmula 1
 */
public class Pais {
    private String nombre;
    private String codigo;

    /**
     * Constructor de la clase Pais
     * 
     * @param nombre Nombre del país
     * @param codigo Código del país
     */
    public Pais(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return nombre + " (" + codigo + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Pais pais = (Pais) obj;
        return codigo.equals(pais.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}