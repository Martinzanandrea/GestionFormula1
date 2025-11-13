package modelo;

/**
 * Clase que representa un auto de Fórmula 1.
 * <p>
 * Contiene todas las especificaciones técnicas de un vehículo de F1,
 * incluyendo información del chasis, motor, peso y potencia.
 * Cada auto debe estar asignado a una escudería.
 * </p>
 * 
 * @author Sistema de Gestión F1
 * @version 1.0
 * @since 1.0
 */
public class Auto {
    /** Modelo comercial del auto */
    private String modelo;
    /** Tipo de chasis utilizado */
    private String chasis;
    /** Tipo de motor */
    private String motor;
    /** Año de fabricación del auto */
    private int año;
    /** Número único de identificación del chasis */
    private String numeroChasis;
    /** Peso del auto en kilogramos */
    private double peso;
    /** Potencia del motor en caballos de fuerza (HP) */
    private int potencia;

    /**
     * Constructor de la clase Auto.
     * <p>
     * Crea una nueva instancia de auto con todas las especificaciones técnicas.
     * </p>
     * 
     * @param modelo       Modelo comercial del auto (no puede ser null o vacío)
     * @param chasis       Tipo de chasis utilizado (no puede ser null o vacío)
     * @param motor        Tipo de motor (no puede ser null o vacío)
     * @param año          Año de fabricación (debe ser válido)
     * @param numeroChasis Número único de chasis (no puede ser null o vacío)
     * @param peso         Peso del auto en kg (debe ser positivo)
     * @param potencia     Potencia en HP (debe ser positivo)
     * @throws IllegalArgumentException si algún parámetro no es válido
     */
    public Auto(String modelo, String chasis, String motor, int año, String numeroChasis, double peso, int potencia) {
        this.modelo = modelo;
        this.chasis = chasis;
        this.motor = motor;
        this.año = año;
        this.numeroChasis = numeroChasis;
        this.peso = peso;
        this.potencia = potencia;
    }

    // ==================== GETTERS Y SETTERS ====================

    /**
     * Obtiene el modelo del auto.
     * 
     * @return Modelo comercial del auto
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo del auto.
     * 
     * @param modelo Nuevo modelo del auto
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el tipo de chasis.
     * 
     * @return Tipo de chasis utilizado
     */
    public String getChasis() {
        return chasis;
    }

    /**
     * Establece el tipo de chasis.
     * 
     * @param chasis Nuevo tipo de chasis
     */
    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    /**
     * Obtiene el tipo de motor.
     * 
     * @return Tipo de motor del auto
     */
    public String getMotor() {
        return motor;
    }

    /**
     * Establece el tipo de motor.
     * 
     * @param motor Nuevo tipo de motor
     */
    public void setMotor(String motor) {
        this.motor = motor;
    }

    /**
     * Obtiene el año de fabricación.
     * 
     * @return Año de fabricación del auto
     */
    public int getAño() {
        return año;
    }

    /**
     * Establece el año de fabricación.
     * 
     * @param año Nuevo año de fabricación
     */
    public void setAño(int año) {
        this.año = año;
    }

    /**
     * Obtiene el número de chasis.
     * 
     * @return Número único de identificación del chasis
     */
    public String getNumeroChasis() {
        return numeroChasis;
    }

    /**
     * Establece el número de chasis.
     * 
     * @param numeroChasis Nuevo número de chasis
     */
    public void setNumeroChasis(String numeroChasis) {
        this.numeroChasis = numeroChasis;
    }

    /**
     * Obtiene el peso del auto.
     * 
     * @return Peso en kilogramos
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Establece el peso del auto.
     * 
     * @param peso Nuevo peso en kilogramos
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Obtiene la potencia del motor.
     * 
     * @return Potencia en caballos de fuerza (HP)
     */
    public int getPotencia() {
        return potencia;
    }

    /**
     * Establece la potencia del motor.
     * 
     * @param potencia Nueva potencia en HP
     */
    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    @Override
    public String toString() {
        return modelo + " (" + año + ") - Chasis: " + numeroChasis;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Auto auto = (Auto) obj;
        return numeroChasis != null && numeroChasis.equals(auto.numeroChasis);
    }

    @Override
    public int hashCode() {
        return numeroChasis != null ? numeroChasis.hashCode() : 0;
    }
}