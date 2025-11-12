package modelo;

import java.time.LocalTime;

/**
 * Clase que representa la participación de un piloto en un Gran Premio
 */
public class Participacion {
    private Piloto piloto;
    private Auto auto;
    private GranPremio granPremio;
    private int posicionFinal;
    private LocalTime mejorVuelta; // tiempo de la mejor vuelta
    private boolean vueltaRapida; // si logró la vuelta más rápida de la carrera
    private boolean podio; // si terminó en el podio (top 3)
    private boolean abandono;
    private String motivoAbandono;
    private int puntosObtenidos;

    /**
     * Constructor de la clase Participacion
     * 
     * @param piloto     Piloto que participa
     * @param auto       Auto utilizado
     * @param granPremio Gran Premio en el que participa
     */
    public Participacion(Piloto piloto, Auto auto, GranPremio granPremio) {
        this.piloto = piloto;
        this.auto = auto;
        this.granPremio = granPremio;
        this.posicionFinal = 0; // 0 significa no terminó o no tiene posición asignada
        this.vueltaRapida = false;
        this.podio = false;
        this.abandono = false;
        this.puntosObtenidos = 0;
    }

    /**
     * Establece la posición final y determina si es podio
     * 
     * @param posicion Posición final (1-based)
     */
    public void setPosicionFinal(int posicion) {
        this.posicionFinal = posicion;
        this.podio = (posicion >= 1 && posicion <= 3);
        this.abandono = false; // Si tiene posición, no abandonó
    }

    /**
     * Marca la participación como abandono
     * 
     * @param motivo Motivo del abandono
     */
    public void marcarAbandono(String motivo) {
        this.abandono = true;
        this.motivoAbandono = motivo;
        this.posicionFinal = 0;
        this.podio = false;
    }

    // Getters y Setters
    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public GranPremio getGranPremio() {
        return granPremio;
    }

    public void setGranPremio(GranPremio granPremio) {
        this.granPremio = granPremio;
    }

    public int getPosicionFinal() {
        return posicionFinal;
    }

    public LocalTime getMejorVuelta() {
        return mejorVuelta;
    }

    public void setMejorVuelta(LocalTime mejorVuelta) {
        this.mejorVuelta = mejorVuelta;
    }

    public boolean isVueltaRapida() {
        return vueltaRapida;
    }

    public void setVueltaRapida(boolean vueltaRapida) {
        this.vueltaRapida = vueltaRapida;
    }

    public boolean isPodio() {
        return podio;
    }

    public boolean isAbandono() {
        return abandono;
    }

    public String getMotivoAbandono() {
        return motivoAbandono;
    }

    public void setMotivoAbandono(String motivoAbandono) {
        this.motivoAbandono = motivoAbandono;
    }

    public int getPuntosObtenidos() {
        return puntosObtenidos;
    }

    public void setPuntosObtenidos(int puntosObtenidos) {
        this.puntosObtenidos = puntosObtenidos;
    }

    /**
     * Obtiene el resultado como string
     * 
     * @return String descriptivo del resultado
     */
    public String getResultado() {
        if (abandono) {
            return "DNF - " + motivoAbandono;
        } else if (posicionFinal > 0) {
            return "P" + posicionFinal + (vueltaRapida ? " + VR" : "");
        } else {
            return "Sin clasificar";
        }
    }

    @Override
    public String toString() {
        return piloto.getNombreCompleto() + " - " + auto.getModelo() + " - " + getResultado();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Participacion that = (Participacion) obj;
        return piloto.equals(that.piloto) && granPremio.equals(that.granPremio);
    }

    @Override
    public int hashCode() {
        return (piloto.toString() + granPremio.toString()).hashCode();
    }
}