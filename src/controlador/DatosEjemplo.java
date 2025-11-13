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

            // Crear escuderías - Ampliado
            Escuderia ferrari = new Escuderia("Scuderia Ferrari", italia);
            Escuderia redbull = new Escuderia("Red Bull Racing", inglaterra);
            Escuderia mercedes = new Escuderia("Mercedes-AMG Petronas", alemania);
            Escuderia mclaren = new Escuderia("McLaren", inglaterra);
            Escuderia aston = new Escuderia("Aston Martin", inglaterra);
            Escuderia alpine = new Escuderia("Alpine F1 Team", francia);
            Escuderia williams = new Escuderia("Williams Racing", inglaterra);

            gestor.registrarEscuderia(ferrari);
            gestor.registrarEscuderia(redbull);
            gestor.registrarEscuderia(mercedes);
            gestor.registrarEscuderia(mclaren);
            gestor.registrarEscuderia(aston);
            gestor.registrarEscuderia(alpine);
            gestor.registrarEscuderia(williams);

            // Crear autos (cada escudería tiene 2 autos) - Ampliado
            Auto ferrariF1_leclerc = new Auto("SF-23", "Ferrari", "Ferrari V6", 2023, "FER001", 798.0, 1000);
            Auto ferrariF1_sainz = new Auto("SF-23", "Ferrari", "Ferrari V6", 2023, "FER002", 798.0, 1000);
            Auto redbullF1_verstappen = new Auto("RB19", "Red Bull", "Honda RBPT", 2023, "RB001", 795.0, 1050);
            Auto redbullF1_perez = new Auto("RB19", "Red Bull", "Honda RBPT", 2023, "RB002", 795.0, 1050);
            Auto mercedesF1_hamilton = new Auto("W14", "Mercedes", "Mercedes V6", 2023, "MER001", 796.0, 1020);
            Auto mercedesF1_russell = new Auto("W14", "Mercedes", "Mercedes V6", 2023, "MER002", 796.0, 1020);
            Auto mclarenF1_norris = new Auto("MCL60", "McLaren", "Mercedes V6", 2023, "MCL001", 797.0, 1010);
            Auto mclarenF1_piastri = new Auto("MCL60", "McLaren", "Mercedes V6", 2023, "MCL002", 797.0, 1010);
            Auto astonF1_alonso = new Auto("AMR23", "Aston Martin", "Mercedes V6", 2023, "AM001", 796.5, 1015);
            Auto astonF1_stroll = new Auto("AMR23", "Aston Martin", "Mercedes V6", 2023, "AM002", 796.5, 1015);
            Auto alpineF1_gasly = new Auto("A523", "Alpine", "Renault V6", 2023, "ALP001", 797.5, 1005);
            Auto alpineF1_ocon = new Auto("A523", "Alpine", "Renault V6", 2023, "ALP002", 797.5, 1005);
            Auto williamsF1_albon = new Auto("FW45", "Williams", "Mercedes V6", 2023, "WIL001", 798.5, 995);
            Auto williamsF1_sargeant = new Auto("FW45", "Williams", "Mercedes V6", 2023, "WIL002", 798.5, 995);

            gestor.registrarAuto(ferrariF1_leclerc);
            gestor.registrarAuto(ferrariF1_sainz);
            gestor.registrarAuto(redbullF1_verstappen);
            gestor.registrarAuto(redbullF1_perez);
            gestor.registrarAuto(mercedesF1_hamilton);
            gestor.registrarAuto(mercedesF1_russell);
            gestor.registrarAuto(mclarenF1_norris);
            gestor.registrarAuto(mclarenF1_piastri);
            gestor.registrarAuto(astonF1_alonso);
            gestor.registrarAuto(astonF1_stroll);
            gestor.registrarAuto(alpineF1_gasly);
            gestor.registrarAuto(alpineF1_ocon);
            gestor.registrarAuto(williamsF1_albon);
            gestor.registrarAuto(williamsF1_sargeant);

            // Crear pilotos de ejemplo - Ampliado para tener más participantes
            Piloto leclerc = new Piloto("12345678L", "Charles", "Leclerc", 26, "Monegasco", 16, 7);
            Piloto sainz = new Piloto("87654321S", "Carlos", "Sainz Jr.", 29, "Español", 55, 9);
            Piloto verstappen = new Piloto("11111111V", "Max", "Verstappen", 26, "Holandés", 1, 8);
            Piloto perez = new Piloto("22222222P", "Sergio", "Pérez", 33, "Mexicano", 11, 13);
            Piloto hamilton = new Piloto("33333333H", "Lewis", "Hamilton", 38, "Británico", 44, 17);
            Piloto russell = new Piloto("44444444R", "George", "Russell", 25, "Británico", 63, 4);
            Piloto norris = new Piloto("55555555N", "Lando", "Norris", 24, "Británico", 4, 6);
            Piloto piastri = new Piloto("66666666P", "Oscar", "Piastri", 22, "Australiano", 81, 2);
            Piloto alonso = new Piloto("77777777A", "Fernando", "Alonso", 42, "Español", 14, 19);
            Piloto stroll = new Piloto("88888888S", "Lance", "Stroll", 24, "Canadiense", 18, 5);
            Piloto gasly = new Piloto("99999999G", "Pierre", "Gasly", 27, "Francés", 10, 7);
            Piloto ocon = new Piloto("10101010O", "Esteban", "Ocon", 27, "Francés", 31, 6);
            Piloto albon = new Piloto("11111112A", "Alexander", "Albon", 27, "Tailandés", 23, 4);
            Piloto sargeant = new Piloto("12121212S", "Logan", "Sargeant", 22, "Estadounidense", 2, 1);

            gestor.registrarPiloto(leclerc);
            gestor.registrarPiloto(sainz);
            gestor.registrarPiloto(verstappen);
            gestor.registrarPiloto(perez);
            gestor.registrarPiloto(hamilton);
            gestor.registrarPiloto(russell);
            gestor.registrarPiloto(norris);
            gestor.registrarPiloto(piastri);
            gestor.registrarPiloto(alonso);
            gestor.registrarPiloto(stroll);
            gestor.registrarPiloto(gasly);
            gestor.registrarPiloto(ocon);
            gestor.registrarPiloto(albon);
            gestor.registrarPiloto(sargeant);

            // Asignar pilotos a escuderías con fechas (considerando que estamos en
            // noviembre 2025)
            // CONTRATOS VIGENTES - Todos los pilotos tienen contrato activo
            gestor.asignarPilotoAEscuderia(leclerc, ferrari, "2019-01-01", null);
            gestor.asignarPilotoAEscuderia(sainz, ferrari, "2021-01-01", "2026-12-31"); // Contrato renovado hasta 2026
            gestor.asignarPilotoAEscuderia(verstappen, redbull, "2016-05-01", null);
            gestor.asignarPilotoAEscuderia(perez, redbull, "2021-01-01", "2026-12-31"); // Contrato renovado hasta 2026
            gestor.asignarPilotoAEscuderia(hamilton, mercedes, "2013-01-01", "2026-12-31"); // Contrato renovado hasta
                                                                                            // 2026
            gestor.asignarPilotoAEscuderia(russell, mercedes, "2022-01-01", null);
            gestor.asignarPilotoAEscuderia(norris, mclaren, "2019-01-01", null);
            gestor.asignarPilotoAEscuderia(piastri, mclaren, "2023-01-01", "2027-12-31"); // Contrato hasta 2027
            gestor.asignarPilotoAEscuderia(alonso, aston, "2023-01-01", "2026-12-31"); // Contrato hasta 2026
            gestor.asignarPilotoAEscuderia(stroll, aston, "2019-01-01", null);
            gestor.asignarPilotoAEscuderia(gasly, alpine, "2022-01-01", "2026-12-31"); // Contrato hasta 2026
            gestor.asignarPilotoAEscuderia(ocon, alpine, "2020-01-01", "2026-12-31"); // Contrato hasta 2026
            gestor.asignarPilotoAEscuderia(albon, williams, "2022-01-01", "2026-12-31"); // Contrato hasta 2026
            gestor.asignarPilotoAEscuderia(sargeant, williams, "2023-01-01", "2026-12-31"); // Contrato hasta 2026

            // Actualizar escuderías según contratos vigentes
            gestor.actualizarEscuderiasSegunContratos();

            // Asignar autos a escuderías (TODOS los pilotos tienen contrato vigente y auto)
            ferrari.agregarAuto(ferrariF1_leclerc);
            ferrari.agregarAuto(ferrariF1_sainz);
            redbull.agregarAuto(redbullF1_verstappen);
            redbull.agregarAuto(redbullF1_perez);
            mercedes.agregarAuto(mercedesF1_hamilton);
            mercedes.agregarAuto(mercedesF1_russell);
            mclaren.agregarAuto(mclarenF1_norris);
            mclaren.agregarAuto(mclarenF1_piastri);
            aston.agregarAuto(astonF1_alonso);
            aston.agregarAuto(astonF1_stroll);
            alpine.agregarAuto(alpineF1_gasly);
            alpine.agregarAuto(alpineF1_ocon);
            williams.agregarAuto(williamsF1_albon);
            williams.agregarAuto(williamsF1_sargeant);

            // Crear mecánicos de ejemplo
            Mecanico mecFerrari1 = new Mecanico("55555555M", "Luigi", "Rossi", 15);
            mecFerrari1.agregarEspecialidad(Especialidad.MOTOR);
            mecFerrari1.agregarEspecialidad(Especialidad.ELECTRONICA);

            Mecanico mecFerrari2 = new Mecanico("66666666B", "Marco", "Bianchi", 12);
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

            // Crear Grandes Premios de ejemplo con fechas futuras
            java.time.LocalDateTime fechaMonza = java.time.LocalDateTime.of(2025, 12, 15, 14, 0);
            java.time.LocalDateTime fechaSilverstone = java.time.LocalDateTime.of(2025, 12, 22, 15, 0);
            java.time.LocalDateTime fechaMonaco = java.time.LocalDateTime.of(2025, 11, 30, 14, 0);

            GranPremio gpMonza = new GranPremio("Gran Premio de Italia", fechaMonza, monza);
            GranPremio gpSilverstone = new GranPremio("Gran Premio de Gran Bretaña", fechaSilverstone, silverstone);
            GranPremio gpMonaco = new GranPremio("Gran Premio de Mónaco", fechaMonaco, monaco_circuit);

            gestor.registrarGranPremio(gpMonza);
            gestor.registrarGranPremio(gpSilverstone);
            gestor.registrarGranPremio(gpMonaco);

            // Inscribir pilotos en carreras de ejemplo
            try {
                // Gran Premio de Mónaco (carrera futura) - Algunos pilotos ya inscritos
                gestor.inscribirPilotoEnCarrera(leclerc, ferrariF1_leclerc, gpMonaco);
                gestor.inscribirPilotoEnCarrera(verstappen, redbullF1_verstappen, gpMonaco);
                gestor.inscribirPilotoEnCarrera(russell, mercedesF1_russell, gpMonaco);
                gestor.inscribirPilotoEnCarrera(hamilton, mercedesF1_hamilton, gpMonaco);
                gestor.inscribirPilotoEnCarrera(norris, mclarenF1_norris, gpMonaco);

                // Gran Premio de Silverstone (carrera futura) - Algunos pilotos ya inscritos
                gestor.inscribirPilotoEnCarrera(russell, mercedesF1_russell, gpSilverstone);
                gestor.inscribirPilotoEnCarrera(verstappen, redbullF1_verstappen, gpSilverstone);
                gestor.inscribirPilotoEnCarrera(alonso, astonF1_alonso, gpSilverstone);

                // Gran Premio de Monza (carrera futura sin inscripciones aún)
                // Se puede usar para demostrar la funcionalidad de inscripción

            } catch (Exception e) {
                System.err.println("Error al inscribir pilotos en carreras: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Error al cargar datos de ejemplo: " + e.getMessage());
        }
    }
}