package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un Gran Premio de Fórmula 1
 */
public class GranPremio {
    private String nombre;
    private LocalDateTime fechaHora;
    private Circuito circuito;
    private List<Participacion> participaciones;
    private boolean finalizada;

    /**
     * Constructor de la clase GranPremio
     * 
     * @param nombre    Nombre del Gran Premio
     * @param fechaHora Fecha y hora del Gran Premio
     * @param circuito  Circuito donde se realiza
     */
    public GranPremio(String nombre, LocalDateTime fechaHora, Circuito circuito) {
        this.nombre = nombre;
        this.fechaHora = fechaHora;
        this.circuito = circuito;
        this.participaciones = new ArrayList<>();
        this.finalizada = false;
    }

    /**
     * Agrega una participación al Gran Premio
     * 
     * @param participacion Participación a agregar
     * @throws IllegalArgumentException si ya existe una participación del mismo
     *                                  piloto
     */
    public void agregarParticipacion(Participacion participacion) {
        // Verificar que el piloto no esté ya inscrito
        for (Participacion p : participaciones) {
            if (p.getPiloto().equals(participacion.getPiloto())) {
                throw new IllegalArgumentException("El piloto ya está inscrito en este Gran Premio");
            }
        }
        participaciones.add(participacion);
    }

    /**
     * Finaliza el Gran Premio y asigna los puntos según las posiciones
     */
    public void finalizar() {
        if (finalizada) {
            throw new IllegalStateException("El Gran Premio ya está finalizado");
        }

        for (Participacion participacion : participaciones) {
            int posicion = participacion.getPosicionFinal();
            if (posicion > 0) {
                // Usar el sistema de puntuación oficial
                int puntosObtenidos = SistemaPuntuacion.getPuntosPorPosicion(posicion);

                // Punto adicional por vuelta rápida si termina en top 10
                if (participacion.isVueltaRapida() && posicion <= 10) {
                    puntosObtenidos += 1;
                }

                participacion.setPuntosObtenidos(puntosObtenidos);
                participacion.getPiloto().sumarPuntos(puntosObtenidos);
            }
        }

        this.finalizada = true;
    }

    /**
     * Obtiene las participaciones ordenadas por posición final
     * 
     * @return Lista de participaciones ordenadas
     */
    public List<Participacion> getResultados() {
        return participaciones.stream()
                .filter(p -> p.getPosicionFinal() > 0)
                .sorted((p1, p2) -> Integer.compare(p1.getPosicionFinal(), p2.getPosicionFinal()))
                .toList();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Circuito getCircuito() {
        return circuito;
    }

    public void setCircuito(Circuito circuito) {
        this.circuito = circuito;
    }

    public List<Participacion> getParticipaciones() {
        return new ArrayList<>(participaciones);
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    @Override
    public String toString() {
        return nombre + " - " + circuito.getNombre() + " (" + fechaHora.toLocalDate() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        GranPremio granPremio = (GranPremio) obj;
        return nombre != null && nombre.equals(granPremio.nombre) &&
                fechaHora != null && fechaHora.equals(granPremio.fechaHora);
    }

    @Override
    public int hashCode() {
        return ((nombre != null ? nombre : "") + (fechaHora != null ? fechaHora.toString() : "")).hashCode();
    }
}