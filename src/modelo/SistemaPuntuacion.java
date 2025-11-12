package modelo;

/**
 * Clase utilitaria para el sistema de puntuación de Fórmula 1
 */
public class SistemaPuntuacion {

    // Sistema oficial de puntuación F1 2022-actual
    private static final int[] PUNTOS_POSICION = { 25, 18, 15, 12, 10, 8, 6, 4, 2, 1 };

    /**
     * Constructor privado para evitar instanciación
     */
    private SistemaPuntuacion() {
        // Clase utilitaria
    }

    /**
     * Obtiene los puntos correspondientes a una posición
     * 
     * @param posicion Posición final (1-based)
     * @return Puntos obtenidos
     */
    public static int getPuntosPorPosicion(int posicion) {
        if (posicion >= 1 && posicion <= PUNTOS_POSICION.length) {
            return PUNTOS_POSICION[posicion - 1];
        }
        return 0;
    }

    /**
     * Verifica si una posición otorga puntos
     * 
     * @param posicion Posición a verificar
     * @return true si otorga puntos, false en caso contrario
     */
    public static boolean otorgaPuntos(int posicion) {
        return posicion >= 1 && posicion <= PUNTOS_POSICION.length;
    }

    /**
     * Obtiene el número de posiciones que otorgan puntos
     * 
     * @return Número de posiciones con puntos
     */
    public static int getNumeroPositionesPuntos() {
        return PUNTOS_POSICION.length;
    }

    /**
     * Obtiene un array con todos los puntos del sistema
     * 
     * @return Array de puntos por posición
     */
    public static int[] getSistemaPuntos() {
        return PUNTOS_POSICION.clone();
    }
}