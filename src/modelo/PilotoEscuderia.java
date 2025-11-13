package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Clase que representa la relación entre un piloto y una escudería,
 * incluyendo las fechas de inicio y fin de la relación.
 * <p>
 * Esta clase maneja el historial de afiliaciones de los pilotos
 * a diferentes escuderías a lo largo de su carrera.
 * </p>
 * 
 * @author Sistema de Gestión F1
 * @version 1.0
 * @since 1.0
 */
public class PilotoEscuderia {
    /** Fecha de inicio en la escudería */
    private String desdeFecha;

    /** Fecha de fin en la escudería */
    private String hastaFecha;

    /** Piloto asociado a la escudería */
    private Piloto piloto;

    /** Escudería asociada al piloto */
    private Escuderia escuderia;

    /**
     * Constructor para crear una nueva relación piloto-escudería.
     * 
     * @param desdeFecha Fecha de inicio en formato String
     * @param hastaFecha Fecha de fin en formato String (puede ser null si está
     *                   activo)
     * @param piloto     Piloto asociado (no puede ser null)
     * @param escuderia  Escudería asociada (no puede ser null)
     * @throws IllegalArgumentException si algún parámetro requerido es null
     */
    public PilotoEscuderia(String desdeFecha, String hastaFecha, Piloto piloto, Escuderia escuderia) {
        if (desdeFecha == null || desdeFecha.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de inicio no puede estar vacía");
        }
        if (piloto == null) {
            throw new IllegalArgumentException("El piloto no puede ser null");
        }
        if (escuderia == null) {
            throw new IllegalArgumentException("La escudería no puede ser null");
        }

        this.desdeFecha = desdeFecha.trim();
        this.hastaFecha = hastaFecha != null ? hastaFecha.trim() : null;
        this.piloto = piloto;
        this.escuderia = escuderia;
    }

    // ==================== GETTERS Y SETTERS ====================

    /**
     * Obtiene la fecha de inicio en la escudería.
     * 
     * @return Fecha de inicio como String
     */
    public String getDesdeFecha() {
        return desdeFecha;
    }

    /**
     * Establece la fecha de inicio en la escudería.
     * 
     * @param desdeFecha Nueva fecha de inicio (no puede ser null o vacía)
     * @throws IllegalArgumentException si la fecha es null o vacía
     */
    public void setDesdeFecha(String desdeFecha) {
        if (desdeFecha == null || desdeFecha.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de inicio no puede estar vacía");
        }
        this.desdeFecha = desdeFecha.trim();
    }

    /**
     * Obtiene la fecha de fin en la escudería.
     * 
     * @return Fecha de fin como String, null si está activo
     */
    public String getHastaFecha() {
        return hastaFecha;
    }

    /**
     * Establece la fecha de fin en la escudería.
     * 
     * @param hastaFecha Nueva fecha de fin (puede ser null si está activo)
     */
    public void setHastaFecha(String hastaFecha) {
        this.hastaFecha = hastaFecha != null ? hastaFecha.trim() : null;
    }

    /**
     * Obtiene el piloto asociado.
     * 
     * @return Piloto asociado
     */
    public Piloto getPiloto() {
        return piloto;
    }

    /**
     * Establece el piloto asociado.
     * 
     * @param piloto Nuevo piloto (no puede ser null)
     * @throws IllegalArgumentException si el piloto es null
     */
    public void setPiloto(Piloto piloto) {
        if (piloto == null) {
            throw new IllegalArgumentException("El piloto no puede ser null");
        }
        this.piloto = piloto;
    }

    /**
     * Obtiene la escudería asociada.
     * 
     * @return Escudería asociada
     */
    public Escuderia getEscuderia() {
        return escuderia;
    }

    /**
     * Establece la escudería asociada.
     * 
     * @param escuderia Nueva escudería (no puede ser null)
     * @throws IllegalArgumentException si la escudería es null
     */
    public void setEscuderia(Escuderia escuderia) {
        if (escuderia == null) {
            throw new IllegalArgumentException("La escudería no puede ser null");
        }
        this.escuderia = escuderia;
    }

    /**
     * Verifica si la relación está activa (no tiene fecha de fin).
     * 
     * @return true si está activa, false en caso contrario
     */
    public boolean estaActiva() {
        return hastaFecha == null;
    }

    /**
     * Verifica si la relación está activa considerando la fecha actual del sistema.
     * Un contrato está vigente si no tiene fecha de fin O si la fecha de fin es
     * posterior a hoy.
     * 
     * @return true si está vigente, false si ya expiró
     */
    public boolean estaVigente() {
        if (hastaFecha == null) {
            return true; // Contrato indefinido
        }

        try {
            LocalDate fechaFin = LocalDate.parse(hastaFecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate hoy = LocalDate.now();
            return fechaFin.isAfter(hoy) || fechaFin.isEqual(hoy);
        } catch (DateTimeParseException e) {
            // Si la fecha no se puede parsear, asumimos que está vigente
            return true;
        }
    }

    /**
     * Verifica si el contrato ya expiró.
     * 
     * @return true si el contrato ya expiró, false si está vigente
     */
    public boolean haExpirado() {
        return !estaVigente();
    }

    /**
     * Finaliza la relación estableciendo una fecha de fin.
     * 
     * @param fechaFin Fecha de finalización (no puede ser null o vacía)
     * @throws IllegalArgumentException si la fecha es null o vacía
     */
    public void finalizarRelacion(String fechaFin) {
        if (fechaFin == null || fechaFin.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de finalización no puede estar vacía");
        }
        this.hastaFecha = fechaFin.trim();
    }

    /**
     * Representación textual de la relación piloto-escudería.
     * 
     * @return String con la información de la relación
     */
    @Override
    public String toString() {
        String estado;
        if (hastaFecha == null) {
            estado = "(Contrato Activo)";
        } else if (estaVigente()) {
            estado = "hasta " + hastaFecha + " (Vigente)";
        } else {
            estado = "hasta " + hastaFecha + " (EXPIRADO)";
        }

        return piloto.toString() + " en " + escuderia.getNombre() +
                " desde " + desdeFecha + " " + estado;
    }

    /**
     * Compara dos relaciones piloto-escudería basándose en el piloto y la
     * escudería.
     * 
     * @param obj Objeto a comparar
     * @return true si representan la misma relación, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        PilotoEscuderia that = (PilotoEscuderia) obj;
        return piloto.equals(that.piloto) && escuderia.equals(that.escuderia);
    }

    /**
     * Calcula el hash code basándose en el piloto y la escudería.
     * 
     * @return Hash code de la relación
     */
    @Override
    public int hashCode() {
        return piloto.hashCode() * 31 + escuderia.hashCode();
    }
}