package modelo;

/**
 * Clase que representa un auto de Fórmula 1
 */
public class Auto {
    private String modelo;
    private String chasis;
    private String motor;
    private int año;
    private String numeroChasis;
    private double peso;
    private int potencia; // en HP

    /**
     * Constructor de la clase Auto
     * 
     * @param modelo       Modelo del auto
     * @param chasis       Tipo de chasis
     * @param motor        Tipo de motor
     * @param año          Año del auto
     * @param numeroChasis Número de chasis único
     * @param peso         Peso del auto en kg
     * @param potencia     Potencia en HP
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

    // Getters y Setters
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getNumeroChasis() {
        return numeroChasis;
    }

    public void setNumeroChasis(String numeroChasis) {
        this.numeroChasis = numeroChasis;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getPotencia() {
        return potencia;
    }

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
        return numeroChasis.equals(auto.numeroChasis);
    }

    @Override
    public int hashCode() {
        return numeroChasis.hashCode();
    }
}