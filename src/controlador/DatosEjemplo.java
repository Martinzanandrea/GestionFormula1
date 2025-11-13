package controlador;

import modelo.*;

/**
 * Clase utilitaria para cargar datos de ejemplo en el sistema de gestión de
 * Fórmula 1.
 * <p>
 * Esta clase proporciona un método estático para poblar el sistema con
 * datos representativos del mundo real de la F1, incluyendo países,
 * escuderías, pilotos, autos, mecánicos y circuitos.
 * </p>
 * 
 * @author Sistema de Gestión F1
 * @version 1.0
 * @since 1.0
 */
public class DatosEjemplo {

    /**
     * Constructor privado para evitar instanciación.
     * <p>
     * Esta es una clase utilitaria con métodos estáticos únicamente.
     * </p>
     */
    private DatosEjemplo() {
        // Clase utilitaria
    }

    /**
     * Carga un conjunto completo de datos de ejemplo en el gestor.
     * <p>
     * Incluye países, escuderías, pilotos, autos, mecánicos y circuitos
     * basados en datos reales de la Fórmula 1. Los datos se cargan en
     * el orden correcto respetando las dependencias entre entidades.
     * </p>
     * 
     * @param gestor Gestor donde cargar los datos (no puede ser null)
     * @throws IllegalArgumentException si el gestor es null
     * @throws RuntimeException         si ocurre un error durante la carga
     */
    public static void cargarDatos(GestorFormula1 gestor) {
        try {
            // Crear países
            Pais italia = new Pais("Italia", "IT");
            Pais españa = new Pais("España", "ES");
            Pais inglaterra = new Pais("Inglaterra", "GB");
            Pais alemania = new Pais("Alemania", "DE");
            Pais francia = new Pais("Francia", "FR");
            Pais monaco = new Pais("Mónaco", "MC");

            gestor.registrarPais(italia);
            gestor.registrarPais(españa);
            gestor.registrarPais(inglaterra);
            gestor.registrarPais(alemania);
            gestor.registrarPais(francia);
            gestor.registrarPais(monaco);

            // Crear escuderías
            Escuderia ferrari = new Escuderia("Scuderia Ferrari", italia);
            Escuderia redbull = new Escuderia("Red Bull Racing", inglaterra);
            Escuderia mercedes = new Escuderia("Mercedes-AMG Petronas", alemania);
            Escuderia mclaren = new Escuderia("McLaren", inglaterra);

            gestor.registrarEscuderia(ferrari);
            gestor.registrarEscuderia(redbull);
            gestor.registrarEscuderia(mercedes);
            gestor.registrarEscuderia(mclaren);

            // Crear autos (cada escudería tiene 2 autos)
            Auto ferrariF1_leclerc = new Auto("SF-23", "Ferrari", "Ferrari V6", 2023, "FER001", 798.0, 1000);
            Auto ferrariF1_sainz = new Auto("SF-23", "Ferrari", "Ferrari V6", 2023, "FER002", 798.0, 1000);
            Auto redbullF1_verstappen = new Auto("RB19", "Red Bull", "Honda RBPT", 2023, "RB001", 795.0, 1050);
            Auto redbullF1_perez = new Auto("RB19", "Red Bull", "Honda RBPT", 2023, "RB002", 795.0, 1050);
            Auto mercedesF1_hamilton = new Auto("W14", "Mercedes", "Mercedes V6", 2023, "MER001", 796.0, 1020);
            Auto mercedesF1_russell = new Auto("W14", "Mercedes", "Mercedes V6", 2023, "MER002", 796.0, 1020);
            Auto mclarenF1_norris = new Auto("MCL60", "McLaren", "Mercedes V6", 2023, "MCL001", 797.0, 1010);
            Auto mclarenF1_piastri = new Auto("MCL60", "McLaren", "Mercedes V6", 2023, "MCL002", 797.0, 1010);

            gestor.registrarAuto(ferrariF1_leclerc);
            gestor.registrarAuto(ferrariF1_sainz);
            gestor.registrarAuto(redbullF1_verstappen);
            gestor.registrarAuto(redbullF1_perez);
            gestor.registrarAuto(mercedesF1_hamilton);
            gestor.registrarAuto(mercedesF1_russell);
            gestor.registrarAuto(mclarenF1_norris);
            gestor.registrarAuto(mclarenF1_piastri);

            // Crear pilotos
            Piloto leclerc = new Piloto("Charles", "Leclerc", 26, "Monegasco", 16, 7);
            Piloto sainz = new Piloto("Carlos", "Sainz Jr.", 29, "Español", 55, 9);
            Piloto verstappen = new Piloto("Max", "Verstappen", 26, "Holandés", 1, 8);
            Piloto perez = new Piloto("Sergio", "Pérez", 33, "Mexicano", 11, 13);
            Piloto hamilton = new Piloto("Lewis", "Hamilton", 38, "Británico", 44, 17);
            Piloto russell = new Piloto("George", "Russell", 25, "Británico", 63, 4);

            gestor.registrarPiloto(leclerc);
            gestor.registrarPiloto(sainz);
            gestor.registrarPiloto(verstappen);
            gestor.registrarPiloto(perez);
            gestor.registrarPiloto(hamilton);
            gestor.registrarPiloto(russell);

            // Asignar pilotos a escuderías
            gestor.asignarPilotoAEscuderia(leclerc, ferrari);
            gestor.asignarPilotoAEscuderia(sainz, ferrari);
            gestor.asignarPilotoAEscuderia(verstappen, redbull);
            gestor.asignarPilotoAEscuderia(perez, redbull);
            gestor.asignarPilotoAEscuderia(hamilton, mercedes);
            gestor.asignarPilotoAEscuderia(russell, mercedes);

            // Asignar autos a escuderías
            ferrari.agregarAuto(ferrariF1_leclerc);
            ferrari.agregarAuto(ferrariF1_sainz);
            redbull.agregarAuto(redbullF1_verstappen);
            redbull.agregarAuto(redbullF1_perez);
            mercedes.agregarAuto(mercedesF1_hamilton);
            mercedes.agregarAuto(mercedesF1_russell);
            mclaren.agregarAuto(mclarenF1_norris);
            mclaren.agregarAuto(mclarenF1_piastri);

            // Crear mecánicos
            Mecanico mecFerrari1 = new Mecanico("Luigi", "Rossi", 15);
            mecFerrari1.agregarEspecialidad(Especialidad.MOTOR);
            mecFerrari1.agregarEspecialidad(Especialidad.ELECTRONICA);

            Mecanico mecFerrari2 = new Mecanico("Marco", "Bianchi", 12);
            mecFerrari2.agregarEspecialidad(Especialidad.CHASIS);
            mecFerrari2.agregarEspecialidad(Especialidad.NEUMATICOS);

            gestor.registrarMecanico(mecFerrari1);
            gestor.registrarMecanico(mecFerrari2);

            ferrari.agregarMecanico(mecFerrari1);
            ferrari.agregarMecanico(mecFerrari2);

            // Crear circuitos
            Circuito monza = new Circuito("Autodromo Nazionale Monza", italia, 5.793, 11, "Permanente");
            Circuito silverstone = new Circuito("Silverstone Circuit", inglaterra, 5.891, 18, "Permanente");
            Circuito monaco_circuit = new Circuito("Circuit de Monaco", monaco, 3.337, 19, "Urbano");

            gestor.registrarCircuito(monza);
            gestor.registrarCircuito(silverstone);
            gestor.registrarCircuito(monaco_circuit);

            // Crear Grandes Premios de ejemplo
            java.time.LocalDateTime fechaMonza = java.time.LocalDateTime.of(2024, 9, 1, 14, 0);
            java.time.LocalDateTime fechaSilverstone = java.time.LocalDateTime.of(2024, 7, 7, 15, 0);
            java.time.LocalDateTime fechaMonaco = java.time.LocalDateTime.of(2024, 5, 26, 14, 0);

            GranPremio gpMonza = new GranPremio("Gran Premio de Italia", fechaMonza, monza);
            GranPremio gpSilverstone = new GranPremio("Gran Premio de Gran Bretaña", fechaSilverstone, silverstone);
            GranPremio gpMonaco = new GranPremio("Gran Premio de Mónaco", fechaMonaco, monaco_circuit);

            gestor.registrarGranPremio(gpMonza);
            gestor.registrarGranPremio(gpSilverstone);
            gestor.registrarGranPremio(gpMonaco);

            // Inscribir pilotos en carreras de ejemplo
            try {
                // Gran Premio de Mónaco (finalizado con resultados)
                gestor.inscribirPilotoEnCarrera(leclerc, ferrariF1_leclerc, gpMonaco);
                gestor.inscribirPilotoEnCarrera(sainz, ferrariF1_sainz, gpMonaco);
                gestor.inscribirPilotoEnCarrera(verstappen, redbullF1_verstappen, gpMonaco);
                gestor.inscribirPilotoEnCarrera(perez, redbullF1_perez, gpMonaco);
                gestor.inscribirPilotoEnCarrera(hamilton, mercedesF1_hamilton, gpMonaco);
                gestor.inscribirPilotoEnCarrera(russell, mercedesF1_russell, gpMonaco);

                // Establecer resultados del GP de Mónaco
                java.util.Map<Piloto, Integer> resultadosMonaco = new java.util.HashMap<>();
                resultadosMonaco.put(verstappen, 1); // Max gana
                resultadosMonaco.put(leclerc, 2); // Charles segundo
                resultadosMonaco.put(perez, 3); // Pérez tercero
                resultadosMonaco.put(sainz, 4); // Sainz cuarto
                resultadosMonaco.put(hamilton, 5); // Hamilton quinto
                resultadosMonaco.put(russell, 6); // Russell sexto

                gestor.establecerResultadosCarrera(gpMonaco, resultadosMonaco, verstappen); // Max tiene vuelta rápida

                // Gran Premio de Silverstone (próximo a correr)
                gestor.inscribirPilotoEnCarrera(hamilton, mercedesF1_hamilton, gpSilverstone);
                gestor.inscribirPilotoEnCarrera(russell, mercedesF1_russell, gpSilverstone);
                gestor.inscribirPilotoEnCarrera(verstappen, redbullF1_verstappen, gpSilverstone);
                gestor.inscribirPilotoEnCarrera(perez, redbullF1_perez, gpSilverstone);

            } catch (Exception e) {
                System.err.println("Error al inscribir pilotos en carreras: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Error al cargar datos de ejemplo: " + e.getMessage());
        }
    }
}