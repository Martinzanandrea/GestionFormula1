package controlador;

import modelo.*;

/**
 * Clase para cargar datos de ejemplo en el sistema
 */
public class DatosEjemplo {

    /**
     * Carga datos de ejemplo en el gestor
     * 
     * @param gestor Gestor donde cargar los datos
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

            // Crear autos
            Auto ferrariF1 = new Auto("SF-23", "Ferrari", "Ferrari V6", 2023, "FER001", 798.0, 1000);
            Auto redbullF1 = new Auto("RB19", "Red Bull", "Honda RBPT", 2023, "RB001", 795.0, 1050);
            Auto mercedesF1 = new Auto("W14", "Mercedes", "Mercedes V6", 2023, "MER001", 796.0, 1020);
            Auto mclarenF1 = new Auto("MCL60", "McLaren", "Mercedes V6", 2023, "MCL001", 797.0, 1010);

            gestor.registrarAuto(ferrariF1);
            gestor.registrarAuto(redbullF1);
            gestor.registrarAuto(mercedesF1);
            gestor.registrarAuto(mclarenF1);

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

            // Agregar autos a escuderías
            ferrari.agregarAuto(ferrariF1);
            redbull.agregarAuto(redbullF1);
            mercedes.agregarAuto(mercedesF1);
            mclaren.agregarAuto(mclarenF1);

            // Crear mecánicos
            Mecanico mecFerrari1 = new Mecanico("Luigi", "Rossi", 15);
            mecFerrari1.agregarEspecialidad("Motor");
            mecFerrari1.agregarEspecialidad("Aerodinámica");

            Mecanico mecFerrari2 = new Mecanico("Marco", "Bianchi", 12);
            mecFerrari2.agregarEspecialidad("Suspensión");
            mecFerrari2.agregarEspecialidad("Frenos");

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

        } catch (Exception e) {
            System.err.println("Error al cargar datos de ejemplo: " + e.getMessage());
        }
    }
}