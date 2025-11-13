package modelo;

/**
 * Clase que representa un país en el sistema de gestión de Fórmula 1.
 * <p>
 * Almacena información básica de países que pueden ser utilizados
 * como origen de escuderías, pilotos y circuitos.
 * </p>
 * 
 * @author Sistema de Gestión F1
 * @version 1.0
 * @since 1.0
 */
public class Pais {
    /** Nombre completo del país */
    private String nombre;
    /** Código de identificación del país (ej: "ITA", "ESP") */
    private String codigo;

    /**
     * Constructor de la clase Pais.
     * <p>
     * Crea una nueva instancia de país con el nombre y código especificados.
     * </p>
     * 
     * @param nombre Nombre completo del país (no puede ser null o vacío)
     * @param codigo Código de identificación del país (no puede ser null o vacío)
     * @throws IllegalArgumentException si algún parámetro es null o vacío
     */
    public Pais(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    // ==================== GETTERS Y SETTERS ====================

    /**
     * Obtiene el nombre del país.
     * 
     * @return Nombre completo del país
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del país.
     * 
     * @param nombre Nuevo nombre del país
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el código del país.
     * 
     * @return Código de identificación del país
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del país.
     * 
     * @param codigo Nuevo código del país
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    // ==================== MÉTODOS DE OBJETO ====================

    /**
     * Representación en texto del país.
     * 
     * @return String en formato "Nombre (Código)"
     */
    @Override
    public String toString() {
        return nombre + " (" + codigo + ")";
    }

    /**
     * Compara dos países basándose en su código.
     * 
     * @param obj Objeto a comparar
     * @return true si los códigos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Pais pais = (Pais) obj;
        return codigo.equals(pais.codigo);
    }

    /**
     * Genera código hash basado en el código del país.
     * 
     * @return Código hash del objeto
     */
    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}