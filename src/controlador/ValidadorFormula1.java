package controlador;

import modelo.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase utilitaria para validar la consistencia de datos en el sistema de
 * Fórmula 1.
 * <p>
 * Esta clase proporciona métodos estáticos para verificar reglas de negocio
 * y mantener la integridad de los datos del campeonato.
 * </p>
 * 
 * @author Sistema de Gestión F1
 * @version 1.0
 * @since 1.0
 */
public class ValidadorFormula1 {

    /**
     * Constructor privado para evitar instanciación
     */
    private ValidadorFormula1() {
        // Clase utilitaria
    }

    /**
     * Valida que un auto no esté asignado a más de un piloto en la misma carrera
     * 
     * @param auto       Auto a verificar
     * @param granPremio Gran Premio donde se verifica
     * @param gestor     Gestor principal del sistema
     * @return true si el auto está disponible, false si ya está asignado
     */
    public static boolean validarAutoDisponibleEnCarrera(Auto auto, GranPremio granPremio, GestorFormula1 gestor) {
        if (granPremio.getParticipaciones() == null) {
            return true;
        }

        return granPremio.getParticipaciones().stream()
                .noneMatch(participacion -> participacion.getAuto().equals(auto));
    }

    /**
     * Valida que un piloto no esté asignado a más de un auto en la misma carrera
     * 
     * @param piloto     Piloto a verificar
     * @param granPremio Gran Premio donde se verifica
     * @param gestor     Gestor principal del sistema
     * @return true si el piloto no tiene auto asignado, false si ya tiene uno
     */
    public static boolean validarPilotoSinAutoEnCarrera(Piloto piloto, GranPremio granPremio, GestorFormula1 gestor) {
        if (granPremio.getParticipaciones() == null) {
            return true;
        }

        return granPremio.getParticipaciones().stream()
                .noneMatch(participacion -> participacion.getPiloto().equals(piloto));
    }

    /**
     * Valida que cada piloto esté vinculado a una única escudería en un período
     * 
     * @param piloto    Piloto a verificar
     * @param escuderia Nueva escudería a asignar
     * @param gestor    Gestor principal del sistema
     * @return true si es válida la asignación, false si hay conflicto
     */
    public static boolean validarPilotoEscuderiaUnica(Piloto piloto, Escuderia escuderia, GestorFormula1 gestor) {
        // Si el piloto no tiene escudería previa, es válido
        if (piloto.getEscuderia() == null) {
            return true;
        }

        // Si es la misma escudería, es válido
        if (piloto.getEscuderia().equals(escuderia)) {
            return true;
        }

        // Verificar si hay carreras pendientes con la escudería actual
        List<GranPremio> carrerasPendientes = gestor.getGrandesPremios().stream()
                .filter(gp -> gp.getFechaHora().isAfter(LocalDateTime.now()))
                .filter(gp -> gp.getParticipaciones() != null)
                .filter(gp -> gp.getParticipaciones().stream()
                        .anyMatch(p -> p.getPiloto().equals(piloto)))
                .collect(Collectors.toList());

        // Si hay carreras pendientes, no puede cambiar de escudería
        return carrerasPendientes.isEmpty();
    }

    /**
     * Valida que los resultados respeten el sistema oficial de puntuación
     * 
     * @param posicion Posición final del piloto
     * @param puntos   Puntos asignados
     * @return true si la puntuación es correcta, false en caso contrario
     */
    public static boolean validarPuntuacionOficial(int posicion, int puntos) {
        return SistemaPuntuacion.getPuntosPorPosicion(posicion) == puntos;
    }

    /**
     * Valida que las posiciones en una carrera sean únicas y consecutivas
     * 
     * @param granPremio Gran Premio a verificar
     * @return true si las posiciones son válidas, false si hay duplicados o
     *         inconsistencias
     */
    public static boolean validarPosicionesUnicas(GranPremio granPremio) {
        if (granPremio.getParticipaciones() == null || granPremio.getParticipaciones().isEmpty()) {
            return true;
        }

        List<Integer> posiciones = granPremio.getParticipaciones().stream()
                .filter(p -> !p.isAbandono()) // Solo pilotos que terminaron
                .map(Participacion::getPosicionFinal)
                .filter(pos -> pos > 0) // Solo posiciones válidas
                .sorted()
                .collect(Collectors.toList());

        // Verificar que no hay duplicados
        long posicionesUnicas = posiciones.stream().distinct().count();
        if (posicionesUnicas != posiciones.size()) {
            return false;
        }

        // Verificar que son consecutivas empezando desde 1
        for (int i = 0; i < posiciones.size(); i++) {
            if (posiciones.get(i) != i + 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Obtiene la escudería propietaria de un auto
     * 
     * @param auto   Auto a buscar
     * @param gestor Gestor principal del sistema
     * @return Escudería propietaria del auto, null si no se encuentra
     */
    private static Escuderia obtenerEscuderiaDelAuto(Auto auto, GestorFormula1 gestor) {
        return gestor.getEscuderias().stream()
                .filter(escuderia -> escuderia.getAutos().contains(auto))
                .findFirst()
                .orElse(null);
    }

    /**
     * Valida que la escudería del piloto coincida con la del auto asignado
     * 
     * @param piloto Piloto a verificar
     * @param auto   Auto asignado
     * @param gestor Gestor principal del sistema
     * @return true si pertenecen a la misma escudería, false en caso contrario
     */
    public static boolean validarEscuderiaConsistente(Piloto piloto, Auto auto, GestorFormula1 gestor) {
        Escuderia escuderiaPiloto = piloto.getEscuderia();
        Escuderia escuderiaAuto = obtenerEscuderiaDelAuto(auto, gestor);

        if (escuderiaPiloto == null || escuderiaAuto == null) {
            return false;
        }

        return escuderiaPiloto.equals(escuderiaAuto);
    }

    /**
     * Valida que no haya más de 2 autos por escudería en una carrera
     * 
     * @param escuderia  Escudería a verificar
     * @param granPremio Gran Premio donde se verifica
     * @param gestor     Gestor principal del sistema
     * @return true si no excede el límite, false si ya hay 2 autos
     */
    public static boolean validarLimiteAutosPorEscuderia(Escuderia escuderia, GranPremio granPremio,
            GestorFormula1 gestor) {
        if (granPremio.getParticipaciones() == null) {
            return true;
        }

        long autosEscuderia = granPremio.getParticipaciones().stream()
                .filter(p -> {
                    Escuderia escuderiaAuto = obtenerEscuderiaDelAuto(p.getAuto(), gestor);
                    return escuderiaAuto != null && escuderiaAuto.equals(escuderia);
                })
                .count();

        return autosEscuderia < 2;
    }

    /**
     * Actualiza automáticamente los puntos de una participación según la posición
     * 
     * @param participacion Participación a actualizar
     * @return true si se actualizó correctamente, false si hay error
     */
    public static boolean actualizarPuntosParticipacion(Participacion participacion) {
        if (participacion.isAbandono()) {
            participacion.setPuntosObtenidos(0);
            return true;
        }

        int puntosCorrectos = SistemaPuntuacion.getPuntosPorPosicion(participacion.getPosicionFinal());

        // Punto extra por vuelta rápida (solo si termina en top 10)
        if (participacion.isVueltaRapida() && participacion.getPosicionFinal() <= 10
                && participacion.getPosicionFinal() > 0) {
            puntosCorrectos += 1;
        }

        participacion.setPuntosObtenidos(puntosCorrectos);
        return true;
    }

    /**
     * Valida todas las reglas para una nueva participación
     * 
     * @param piloto     Piloto a inscribir
     * @param auto       Auto a asignar
     * @param granPremio Gran Premio donde participa
     * @param gestor     Gestor principal del sistema
     * @return String con el error encontrado, null si todo es válido
     */
    public static String validarNuevaParticipacion(Piloto piloto, Auto auto, GranPremio granPremio,
            GestorFormula1 gestor) {
        // Verificar que el auto esté disponible
        if (!validarAutoDisponibleEnCarrera(auto, granPremio, gestor)) {
            return "El auto ya está asignado a otro piloto en esta carrera";
        }

        // Verificar que el piloto no tenga auto asignado
        if (!validarPilotoSinAutoEnCarrera(piloto, granPremio, gestor)) {
            return "El piloto ya tiene un auto asignado en esta carrera";
        }

        // Verificar consistencia de escudería
        if (!validarEscuderiaConsistente(piloto, auto, gestor)) {
            return "El piloto y el auto deben pertenecer a la misma escudería";
        }

        // Verificar límite de autos por escudería
        Escuderia escuderiaAuto = obtenerEscuderiaDelAuto(auto, gestor);
        if (escuderiaAuto == null) {
            return "El auto no está asignado a ninguna escudería";
        }
        if (!validarLimiteAutosPorEscuderia(escuderiaAuto, granPremio, gestor)) {
            return "La escudería ya tiene 2 autos registrados en esta carrera";
        }

        // Si llegamos aquí, todo es válido
        return null;
    }
}