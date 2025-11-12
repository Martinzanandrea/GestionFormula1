package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

/**
 * Ventana para la gestión de reportes y estadísticas
 */
public class VentanaReportes extends JFrame {
    private GestorFormula1 gestor;
    private JTabbedPane pestanas;

    /**
     * Constructor de la ventana de reportes
     * 
     * @param gestor Gestor principal del sistema
     */
    public VentanaReportes(GestorFormula1 gestor) {
        this.gestor = gestor;
        inicializarComponentes();
        configurarVentana();
    }

    /**
     * Inicializa los componentes de la interfaz
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        // Panel principal con pestañas
        pestanas = new JTabbedPane();

        // Pestaña de rankings
        JPanel panelRankings = crearPanelRankings();
        pestanas.addTab("Rankings", panelRankings);

        // Pestaña de estadísticas por piloto
        JPanel panelEstadisticasPiloto = crearPanelEstadisticasPiloto();
        pestanas.addTab("Estadísticas Piloto", panelEstadisticasPiloto);

        // Pestaña de estadísticas por escudería
        JPanel panelEstadisticasEscuderia = crearPanelEstadisticasEscuderia();
        pestanas.addTab("Estadísticas Escudería", panelEstadisticasEscuderia);

        // Pestaña de historial de carreras
        JPanel panelHistorial = crearPanelHistorial();
        pestanas.addTab("Historial Carreras", panelHistorial);

        // Pestaña de estadísticas generales
        JPanel panelGenerales = crearPanelEstadisticasGenerales();
        pestanas.addTab("Estadísticas Generales", panelGenerales);

        // Panel de botones
        JPanel panelBotones = crearPanelBotones();

        add(pestanas, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Crea el panel de rankings
     */
    private JPanel crearPanelRankings() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel de controles
        JPanel panelControles = new JPanel(new FlowLayout());
        JButton btnActualizar = new JButton("Actualizar Rankings");
        JButton btnExportar = new JButton("Exportar");

        panelControles.add(btnActualizar);
        panelControles.add(btnExportar);

        // Panel con dos columnas para rankings
        JPanel panelRankings = new JPanel(new GridLayout(1, 2, 10, 10));

        // Ranking de Pilotos
        JPanel panelRankingPilotos = new JPanel(new BorderLayout());
        panelRankingPilotos.setBorder(BorderFactory.createTitledBorder("Ranking de Pilotos"));

        String[] columnasPilotos = { "Pos", "Piloto", "Escudería", "Puntos", "Carreras", "Podios", "Victorias" };
        DefaultTableModel modeloPilotos = new DefaultTableModel(columnasPilotos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaPilotos = new JTable(modeloPilotos);
        JScrollPane scrollPilotos = new JScrollPane(tablaPilotos);
        scrollPilotos.setPreferredSize(new Dimension(450, 400));

        panelRankingPilotos.add(scrollPilotos, BorderLayout.CENTER);

        // Ranking de Escuderías
        JPanel panelRankingEscuderias = new JPanel(new BorderLayout());
        panelRankingEscuderias.setBorder(BorderFactory.createTitledBorder("Ranking de Escuderías"));

        String[] columnasEscuderias = { "Pos", "Escudería", "País", "Puntos", "Pilotos", "Autos", "Mecánicos" };
        DefaultTableModel modeloEscuderias = new DefaultTableModel(columnasEscuderias, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaEscuderias = new JTable(modeloEscuderias);
        JScrollPane scrollEscuderias = new JScrollPane(tablaEscuderias);
        scrollEscuderias.setPreferredSize(new Dimension(450, 400));

        panelRankingEscuderias.add(scrollEscuderias, BorderLayout.CENTER);

        panelRankings.add(panelRankingPilotos);
        panelRankings.add(panelRankingEscuderias);

        // Cargar datos
        btnActualizar.addActionListener(e -> {
            cargarRankingPilotos(modeloPilotos);
            cargarRankingEscuderias(modeloEscuderias);
        });

        // Cargar inicial
        cargarRankingPilotos(modeloPilotos);
        cargarRankingEscuderias(modeloEscuderias);

        panel.add(panelControles, BorderLayout.NORTH);
        panel.add(panelRankings, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de estadísticas por piloto
     */
    private JPanel crearPanelEstadisticasPiloto() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel de selección
        JPanel panelSeleccion = new JPanel(new FlowLayout());
        panelSeleccion.setBorder(BorderFactory.createTitledBorder("Seleccionar Piloto"));

        JComboBox<Piloto> comboPilotos = new JComboBox<>();
        JButton btnMostrarStats = new JButton("Mostrar Estadísticas");

        for (Piloto piloto : gestor.getPilotos()) {
            comboPilotos.addItem(piloto);
        }

        panelSeleccion.add(new JLabel("Piloto:"));
        panelSeleccion.add(comboPilotos);
        panelSeleccion.add(btnMostrarStats);

        // Panel de estadísticas
        JPanel panelStats = new JPanel(new GridLayout(2, 1, 10, 10));

        // Panel de información general
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información General"));

        // Panel de participaciones
        JPanel panelParticipaciones = new JPanel(new BorderLayout());
        panelParticipaciones.setBorder(BorderFactory.createTitledBorder("Historial de Participaciones"));

        String[] columnasParticipaciones = { "Carrera", "Circuito", "Fecha", "Posición", "Puntos", "Vuelta Rápida" };
        DefaultTableModel modeloParticipaciones = new DefaultTableModel(columnasParticipaciones, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaParticipaciones = new JTable(modeloParticipaciones);
        JScrollPane scrollParticipaciones = new JScrollPane(tablaParticipaciones);

        panelParticipaciones.add(scrollParticipaciones, BorderLayout.CENTER);

        panelStats.add(panelInfo);
        panelStats.add(panelParticipaciones);

        btnMostrarStats.addActionListener(e -> {
            Piloto pilotoSeleccionado = (Piloto) comboPilotos.getSelectedItem();
            if (pilotoSeleccionado != null) {
                mostrarEstadisticasPiloto(pilotoSeleccionado, panelInfo, modeloParticipaciones);
            }
        });

        panel.add(panelSeleccion, BorderLayout.NORTH);
        panel.add(panelStats, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de estadísticas por escudería
     */
    private JPanel crearPanelEstadisticasEscuderia() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel de selección
        JPanel panelSeleccion = new JPanel(new FlowLayout());
        panelSeleccion.setBorder(BorderFactory.createTitledBorder("Seleccionar Escudería"));

        JComboBox<Escuderia> comboEscuderias = new JComboBox<>();
        JButton btnMostrarStats = new JButton("Mostrar Estadísticas");

        for (Escuderia escuderia : gestor.getEscuderias()) {
            comboEscuderias.addItem(escuderia);
        }

        panelSeleccion.add(new JLabel("Escudería:"));
        panelSeleccion.add(comboEscuderias);
        panelSeleccion.add(btnMostrarStats);

        // Panel principal con pestañas internas
        JTabbedPane pestanasEscuderia = new JTabbedPane();

        // Información general de la escudería
        JPanel panelInfoEscuderia = new JPanel(new GridBagLayout());

        // Rendimiento de pilotos
        JPanel panelRendimientoPilotos = new JPanel(new BorderLayout());
        String[] columnasPilotosEscuderia = { "Piloto", "Número", "Puntos", "Carreras", "Mejor Posición", "Promedio" };
        DefaultTableModel modeloPilotosEscuderia = new DefaultTableModel(columnasPilotosEscuderia, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tablaPilotosEscuderia = new JTable(modeloPilotosEscuderia);
        panelRendimientoPilotos.add(new JScrollPane(tablaPilotosEscuderia), BorderLayout.CENTER);

        // Recursos de la escudería
        JPanel panelRecursos = new JPanel(new GridLayout(1, 2, 10, 10));

        // Lista de autos
        JPanel panelAutos = new JPanel(new BorderLayout());
        panelAutos.setBorder(BorderFactory.createTitledBorder("Autos"));
        DefaultListModel<Auto> modeloAutos = new DefaultListModel<>();
        JList<Auto> listaAutos = new JList<>(modeloAutos);
        panelAutos.add(new JScrollPane(listaAutos), BorderLayout.CENTER);

        // Lista de mecánicos
        JPanel panelMecanicos = new JPanel(new BorderLayout());
        panelMecanicos.setBorder(BorderFactory.createTitledBorder("Mecánicos"));
        DefaultListModel<Mecanico> modeloMecanicos = new DefaultListModel<>();
        JList<Mecanico> listaMecanicos = new JList<>(modeloMecanicos);
        panelMecanicos.add(new JScrollPane(listaMecanicos), BorderLayout.CENTER);

        panelRecursos.add(panelAutos);
        panelRecursos.add(panelMecanicos);

        pestanasEscuderia.addTab("Información General", panelInfoEscuderia);
        pestanasEscuderia.addTab("Rendimiento Pilotos", panelRendimientoPilotos);
        pestanasEscuderia.addTab("Recursos", panelRecursos);

        btnMostrarStats.addActionListener(e -> {
            Escuderia escuderiaSeleccionada = (Escuderia) comboEscuderias.getSelectedItem();
            if (escuderiaSeleccionada != null) {
                mostrarEstadisticasEscuderia(escuderiaSeleccionada, panelInfoEscuderia,
                        modeloPilotosEscuderia, modeloAutos, modeloMecanicos);
            }
        });

        panel.add(panelSeleccion, BorderLayout.NORTH);
        panel.add(pestanasEscuderia, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de historial de carreras
     */
    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel de controles
        JPanel panelControles = new JPanel(new FlowLayout());
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnVerDetalles = new JButton("Ver Detalles");

        panelControles.add(btnActualizar);
        panelControles.add(btnVerDetalles);

        // Tabla de carreras
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Historial de Grandes Premios"));

        String[] columnasCarreras = { "Fecha", "Gran Premio", "Circuito", "País", "Ganador", "Escudería Ganadora",
                "Participantes", "Estado" };
        DefaultTableModel modeloCarreras = new DefaultTableModel(columnasCarreras, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaCarreras = new JTable(modeloCarreras);
        tablaCarreras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollCarreras = new JScrollPane(tablaCarreras);

        panelTabla.add(scrollCarreras, BorderLayout.CENTER);

        btnActualizar.addActionListener(e -> cargarHistorialCarreras(modeloCarreras));

        btnVerDetalles.addActionListener(e -> {
            int filaSeleccionada = tablaCarreras.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String nombreCarrera = (String) modeloCarreras.getValueAt(filaSeleccionada, 1);
                mostrarDetallesCarrera(nombreCarrera);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una carrera para ver los detalles",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Cargar datos inicial
        cargarHistorialCarreras(modeloCarreras);

        panel.add(panelControles, BorderLayout.NORTH);
        panel.add(panelTabla, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de estadísticas generales
     */
    private JPanel crearPanelEstadisticasGenerales() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel de controles
        JPanel panelControles = new JPanel(new FlowLayout());
        JButton btnActualizar = new JButton("Actualizar Estadísticas");
        panelControles.add(btnActualizar);

        // Panel principal con estadísticas
        JPanel panelEstadisticas = new JPanel(new GridLayout(2, 2, 10, 10));
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de totales
        JPanel panelTotales = new JPanel(new GridBagLayout());
        panelTotales.setBorder(BorderFactory.createTitledBorder("Resumen General"));

        // Panel de países más representados
        JPanel panelPaises = new JPanel(new BorderLayout());
        panelPaises.setBorder(BorderFactory.createTitledBorder("Países Más Representados"));
        DefaultTableModel modeloPaises = new DefaultTableModel(
                new String[] { "País", "Escuderías", "Pilotos", "Circuitos" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tablaPaises = new JTable(modeloPaises);
        panelPaises.add(new JScrollPane(tablaPaises), BorderLayout.CENTER);

        // Panel de circuitos más utilizados
        JPanel panelCircuitos = new JPanel(new BorderLayout());
        panelCircuitos.setBorder(BorderFactory.createTitledBorder("Circuitos Más Utilizados"));
        DefaultTableModel modeloCircuitos = new DefaultTableModel(
                new String[] { "Circuito", "País", "Carreras Celebradas", "Último GP" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tablaCircuitos = new JTable(modeloCircuitos);
        panelCircuitos.add(new JScrollPane(tablaCircuitos), BorderLayout.CENTER);

        // Panel de modelos de autos
        JPanel panelModelos = new JPanel(new BorderLayout());
        panelModelos.setBorder(BorderFactory.createTitledBorder("Modelos de Autos"));
        DefaultTableModel modeloModelos = new DefaultTableModel(new String[] { "Modelo", "Cantidad", "Escuderías" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tablaModelos = new JTable(modeloModelos);
        panelModelos.add(new JScrollPane(tablaModelos), BorderLayout.CENTER);

        panelEstadisticas.add(panelTotales);
        panelEstadisticas.add(panelPaises);
        panelEstadisticas.add(panelCircuitos);
        panelEstadisticas.add(panelModelos);

        btnActualizar.addActionListener(e -> {
            cargarEstadisticasGenerales(panelTotales, modeloPaises, modeloCircuitos, modeloModelos);
        });

        // Cargar datos inicial
        cargarEstadisticasGenerales(panelTotales, modeloPaises, modeloCircuitos, modeloModelos);
        panel.add(panelControles, BorderLayout.NORTH);
        panel.add(panelEstadisticas, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de botones principales
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnCerrar);
        return panel;
    }

    /**
     * Configura la ventana
     */
    private void configurarVentana() {
        setTitle("Sistema de Reportes y Estadísticas");
        setSize(1200, 800);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Carga el ranking de pilotos
     */
    private void cargarRankingPilotos(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        List<Piloto> pilotosOrdenados = gestor.getPilotos().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getPuntosTotales(), p1.getPuntosTotales()))
                .toList();

        int posicion = 1;
        for (Piloto piloto : pilotosOrdenados) {
            int carreras = contarCarrerasPiloto(piloto);
            int podios = contarPodiosPiloto(piloto);
            int victorias = contarVictoriasPiloto(piloto);

            Object[] fila = {
                    posicion++,
                    piloto.getNombreCompleto(),
                    piloto.getEscuderia() != null ? piloto.getEscuderia().getNombre() : "Sin escudería",
                    piloto.getPuntosTotales(),
                    carreras,
                    podios,
                    victorias
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Carga el ranking de escuderías
     */
    private void cargarRankingEscuderias(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        List<Escuderia> escuderiasOrdenadas = gestor.getEscuderias().stream()
                .sorted((e1, e2) -> {
                    int puntosE1 = e1.getPilotos().stream().mapToInt(Piloto::getPuntosTotales).sum();
                    int puntosE2 = e2.getPilotos().stream().mapToInt(Piloto::getPuntosTotales).sum();
                    return Integer.compare(puntosE2, puntosE1);
                })
                .toList();

        int posicion = 1;
        for (Escuderia escuderia : escuderiasOrdenadas) {
            int puntosTotales = escuderia.getPilotos().stream().mapToInt(Piloto::getPuntosTotales).sum();

            Object[] fila = {
                    posicion++,
                    escuderia.getNombre(),
                    escuderia.getPais().getNombre(),
                    puntosTotales,
                    escuderia.getPilotos().size(),
                    escuderia.getAutos().size(),
                    escuderia.getMecanicos().size()
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Muestra estadísticas detalladas de un piloto
     */
    private void mostrarEstadisticasPiloto(Piloto piloto, JPanel panelInfo, DefaultTableModel modeloParticipaciones) {
        // Limpiar panel de información
        panelInfo.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Información básica
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelInfo.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(piloto.getNombreCompleto()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInfo.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(String.valueOf(piloto.getNumero())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInfo.add(new JLabel("Escudería:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(piloto.getEscuderia() != null ? piloto.getEscuderia().getNombre() : "Sin escudería"),
                gbc);

        // Estadísticas
        int carreras = contarCarrerasPiloto(piloto);
        int podios = contarPodiosPiloto(piloto);
        int victorias = contarVictoriasPiloto(piloto);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelInfo.add(new JLabel("Puntos Totales:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(String.valueOf(piloto.getPuntosTotales())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelInfo.add(new JLabel("Carreras Participadas:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(String.valueOf(carreras)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panelInfo.add(new JLabel("Podios:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(String.valueOf(podios)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panelInfo.add(new JLabel("Victorias:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(String.valueOf(victorias)), gbc);

        // Cargar participaciones
        cargarParticipacionesPiloto(piloto, modeloParticipaciones);

        panelInfo.revalidate();
        panelInfo.repaint();
    }

    /**
     * Muestra estadísticas detalladas de una escudería
     */
    private void mostrarEstadisticasEscuderia(Escuderia escuderia, JPanel panelInfo, DefaultTableModel modeloPilotos,
            DefaultListModel<Auto> modeloAutos, DefaultListModel<Mecanico> modeloMecanicos) {

        // Limpiar panel de información
        panelInfo.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Información básica
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelInfo.add(new JLabel("Escudería:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(escuderia.getNombre()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInfo.add(new JLabel("País:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(escuderia.getPais().getNombre()), gbc);

        // Estadísticas
        int puntosTotales = escuderia.getPilotos().stream().mapToInt(Piloto::getPuntosTotales).sum();

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInfo.add(new JLabel("Puntos Totales:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(String.valueOf(puntosTotales)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelInfo.add(new JLabel("Pilotos:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(String.valueOf(escuderia.getPilotos().size())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelInfo.add(new JLabel("Autos:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(String.valueOf(escuderia.getAutos().size())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panelInfo.add(new JLabel("Mecánicos:"), gbc);
        gbc.gridx = 1;
        panelInfo.add(new JLabel(String.valueOf(escuderia.getMecanicos().size())), gbc);

        // Cargar datos en las tablas
        cargarRendimientoPilotos(escuderia, modeloPilotos);
        cargarRecursosEscuderia(escuderia, modeloAutos, modeloMecanicos);

        panelInfo.revalidate();
        panelInfo.repaint();
    }

    /**
     * Carga el historial de carreras
     */
    private void cargarHistorialCarreras(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        List<GranPremio> carrerasOrdenadas = gestor.getGrandesPremios().stream()
                .sorted((c1, c2) -> c2.getFechaHora().compareTo(c1.getFechaHora()))
                .toList();

        for (GranPremio carrera : carrerasOrdenadas) {
            String ganador = "TBD";
            String escuderiaGanadora = "TBD";

            if (carrera.isFinalizada() && !carrera.getParticipaciones().isEmpty()) {
                List<Participacion> resultados = carrera.getResultados();
                if (!resultados.isEmpty()) {
                    Participacion ganadorParticipacion = resultados.get(0);
                    ganador = ganadorParticipacion.getPiloto().getNombreCompleto();
                    escuderiaGanadora = ganadorParticipacion.getPiloto().getEscuderia() != null
                            ? ganadorParticipacion.getPiloto().getEscuderia().getNombre()
                            : "Sin escudería";
                }
            }

            Object[] fila = {
                    carrera.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    carrera.getNombre(),
                    carrera.getCircuito().getNombre(),
                    carrera.getCircuito().getPais().getNombre(),
                    ganador,
                    escuderiaGanadora,
                    carrera.getParticipaciones().size(),
                    carrera.isFinalizada() ? "Finalizada" : "Programada"
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Carga estadísticas generales del sistema
     */
    private void cargarEstadisticasGenerales(JPanel panelTotales, DefaultTableModel modeloPaises,
            DefaultTableModel modeloCircuitos, DefaultTableModel modeloModelos) { // Limpiar panel de totales
        panelTotales.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Totales generales
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelTotales.add(new JLabel("Pilotos Registrados:"), gbc);
        gbc.gridx = 1;
        panelTotales.add(new JLabel(String.valueOf(gestor.getPilotos().size())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelTotales.add(new JLabel("Escuderías Registradas:"), gbc);
        gbc.gridx = 1;
        panelTotales.add(new JLabel(String.valueOf(gestor.getEscuderias().size())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelTotales.add(new JLabel("Autos Registrados:"), gbc);
        gbc.gridx = 1;
        panelTotales.add(new JLabel(String.valueOf(gestor.getAutos().size())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelTotales.add(new JLabel("Mecánicos Registrados:"), gbc);
        gbc.gridx = 1;
        panelTotales.add(new JLabel(String.valueOf(gestor.getMecanicos().size())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelTotales.add(new JLabel("Circuitos Disponibles:"), gbc);
        gbc.gridx = 1;
        panelTotales.add(new JLabel(String.valueOf(gestor.getCircuitos().size())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panelTotales.add(new JLabel("Grandes Premios:"), gbc);
        gbc.gridx = 1;
        panelTotales.add(new JLabel(String.valueOf(gestor.getGrandesPremios().size())), gbc);

        long carrerasFinalizadas = gestor.getGrandesPremios().stream().filter(GranPremio::isFinalizada).count();
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelTotales.add(new JLabel("Carreras Finalizadas:"), gbc);
        gbc.gridx = 1;
        panelTotales.add(new JLabel(String.valueOf(carrerasFinalizadas)), gbc);

        panelTotales.revalidate();
        panelTotales.repaint();

        // Cargar estadísticas de países
        cargarEstadisticasPaises(modeloPaises);

        // Cargar estadísticas de circuitos
        cargarEstadisticasCircuitos(modeloCircuitos);

        // Cargar estadísticas de modelos
        cargarEstadisticasModelos(modeloModelos);
    }

    // Métodos auxiliares para cálculos estadísticos

    private int contarCarrerasPiloto(Piloto piloto) {
        return (int) gestor.getGrandesPremios().stream()
                .flatMap(gp -> gp.getParticipaciones().stream())
                .filter(p -> p.getPiloto().equals(piloto))
                .count();
    }

    private int contarPodiosPiloto(Piloto piloto) {
        return (int) gestor.getGrandesPremios().stream()
                .flatMap(gp -> gp.getParticipaciones().stream())
                .filter(p -> p.getPiloto().equals(piloto) && p.getPosicionFinal() > 0 && p.getPosicionFinal() <= 3)
                .count();
    }

    private int contarVictoriasPiloto(Piloto piloto) {
        return (int) gestor.getGrandesPremios().stream()
                .flatMap(gp -> gp.getParticipaciones().stream())
                .filter(p -> p.getPiloto().equals(piloto) && p.getPosicionFinal() == 1)
                .count();
    }

    private void cargarParticipacionesPiloto(Piloto piloto, DefaultTableModel modelo) {
        modelo.setRowCount(0);

        gestor.getGrandesPremios().stream()
                .flatMap(gp -> gp.getParticipaciones().stream())
                .filter(p -> p.getPiloto().equals(piloto))
                .sorted((p1, p2) -> {
                    // Buscar el gran premio de cada participación para ordenar por fecha
                    GranPremio gp1 = gestor.getGrandesPremios().stream()
                            .filter(gp -> gp.getParticipaciones().contains(p1))
                            .findFirst().orElse(null);
                    GranPremio gp2 = gestor.getGrandesPremios().stream()
                            .filter(gp -> gp.getParticipaciones().contains(p2))
                            .findFirst().orElse(null);

                    if (gp1 != null && gp2 != null) {
                        return gp2.getFechaHora().compareTo(gp1.getFechaHora());
                    }
                    return 0;
                })
                .forEach(participacion -> {
                    GranPremio carrera = gestor.getGrandesPremios().stream()
                            .filter(gp -> gp.getParticipaciones().contains(participacion))
                            .findFirst().orElse(null);

                    if (carrera != null) {
                        String posicion = participacion.isAbandono() ? "DNF"
                                : (participacion.getPosicionFinal() > 0 ? "P" + participacion.getPosicionFinal()
                                        : "No terminó");

                        Object[] fila = {
                                carrera.getNombre(),
                                carrera.getCircuito().getNombre(),
                                carrera.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                posicion,
                                participacion.getPuntosObtenidos(),
                                participacion.isVueltaRapida() ? "Sí" : "No"
                        };
                        modelo.addRow(fila);
                    }
                });
    }

    private void cargarRendimientoPilotos(Escuderia escuderia, DefaultTableModel modelo) {
        modelo.setRowCount(0);

        for (Piloto piloto : escuderia.getPilotos()) {
            int carreras = contarCarrerasPiloto(piloto);
            int mejorPosicion = obtenerMejorPosicion(piloto);
            double promedioPosicion = calcularPromedioPosicion(piloto);

            Object[] fila = {
                    piloto.getNombreCompleto(),
                    piloto.getNumero(),
                    piloto.getPuntosTotales(),
                    carreras,
                    mejorPosicion > 0 ? "P" + mejorPosicion : "N/A",
                    carreras > 0 ? String.format("%.1f", promedioPosicion) : "N/A"
            };
            modelo.addRow(fila);
        }
    }

    private void cargarRecursosEscuderia(Escuderia escuderia, DefaultListModel<Auto> modeloAutos,
            DefaultListModel<Mecanico> modeloMecanicos) {

        modeloAutos.clear();
        for (Auto auto : escuderia.getAutos()) {
            modeloAutos.addElement(auto);
        }

        modeloMecanicos.clear();
        for (Mecanico mecanico : escuderia.getMecanicos()) {
            modeloMecanicos.addElement(mecanico);
        }
    }

    private int obtenerMejorPosicion(Piloto piloto) {
        return gestor.getGrandesPremios().stream()
                .flatMap(gp -> gp.getParticipaciones().stream())
                .filter(p -> p.getPiloto().equals(piloto) && p.getPosicionFinal() > 0)
                .mapToInt(Participacion::getPosicionFinal)
                .min()
                .orElse(0);
    }

    private double calcularPromedioPosicion(Piloto piloto) {
        return gestor.getGrandesPremios().stream()
                .flatMap(gp -> gp.getParticipaciones().stream())
                .filter(p -> p.getPiloto().equals(piloto) && p.getPosicionFinal() > 0)
                .mapToInt(Participacion::getPosicionFinal)
                .average()
                .orElse(0.0);
    }

    private void cargarEstadisticasPaises(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        Map<String, Integer> escuderiasPorPais = gestor.getEscuderias().stream()
                .collect(Collectors.groupingBy(e -> e.getPais().getNombre(),
                        Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)));

        Map<String, Integer> pilotosPorPais = gestor.getPilotos().stream()
                .filter(p -> p.getEscuderia() != null)
                .collect(Collectors.groupingBy(p -> p.getEscuderia().getPais().getNombre(),
                        Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)));

        Map<String, Integer> circuitosPorPais = gestor.getCircuitos().stream()
                .collect(Collectors.groupingBy(c -> c.getPais().getNombre(),
                        Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)));

        escuderiasPorPais.forEach((pais, escuderias) -> {
            int pilotos = pilotosPorPais.getOrDefault(pais, 0);
            int circuitos = circuitosPorPais.getOrDefault(pais, 0);

            Object[] fila = { pais, escuderias, pilotos, circuitos };
            modelo.addRow(fila);
        });
    }

    private void cargarEstadisticasCircuitos(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        for (Circuito circuito : gestor.getCircuitos()) {
            long carrerasCelebradas = gestor.getGrandesPremios().stream()
                    .filter(gp -> gp.getCircuito().equals(circuito))
                    .count();

            String ultimoGP = gestor.getGrandesPremios().stream()
                    .filter(gp -> gp.getCircuito().equals(circuito))
                    .max(Comparator.comparing(GranPremio::getFechaHora))
                    .map(gp -> gp.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .orElse("Nunca");

            Object[] fila = {
                    circuito.getNombre(),
                    circuito.getPais().getNombre(),
                    carrerasCelebradas,
                    ultimoGP
            };
            modelo.addRow(fila);
        }
    }

    private void cargarEstadisticasModelos(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        Map<String, Long> autosPorModelo = gestor.getAutos().stream()
                .collect(Collectors.groupingBy(Auto::getModelo, Collectors.counting()));

        autosPorModelo.forEach((modeloAuto, cantidad) -> {
            long escuderias = gestor.getEscuderias().stream()
                    .filter(e -> e.getAutos().stream().anyMatch(a -> a.getModelo().equals(modeloAuto)))
                    .count();

            Object[] fila = { modeloAuto, cantidad, escuderias };
            modelo.addRow(fila);
        });
    }

    private void mostrarDetallesCarrera(String nombreCarrera) {
        GranPremio carrera = gestor.getGrandesPremios().stream()
                .filter(gp -> gp.getNombre().equals(nombreCarrera))
                .findFirst()
                .orElse(null);

        if (carrera != null) {
            StringBuilder detalles = new StringBuilder();
            detalles.append("DETALLES DEL GRAN PREMIO\n");
            detalles.append("======================\n\n");
            detalles.append("Nombre: ").append(carrera.getNombre()).append("\n");
            detalles.append("Circuito: ").append(carrera.getCircuito().getNombre()).append("\n");
            detalles.append("País: ").append(carrera.getCircuito().getPais().getNombre()).append("\n");
            detalles.append("Fecha y Hora: ")
                    .append(carrera.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                    .append("\n");
            detalles.append("Estado: ").append(carrera.isFinalizada() ? "Finalizada" : "Programada").append("\n");
            detalles.append("Participantes: ").append(carrera.getParticipaciones().size()).append("\n\n");

            if (carrera.isFinalizada()) {
                detalles.append("RESULTADOS:\n");
                detalles.append("===========\n");
                List<Participacion> resultados = carrera.getResultados();
                for (int i = 0; i < resultados.size(); i++) {
                    Participacion p = resultados.get(i);
                    detalles.append(String.format("P%d - %s (%s) - %d puntos\n",
                            p.getPosicionFinal(),
                            p.getPiloto().getNombreCompleto(),
                            p.getPiloto().getEscuderia() != null ? p.getPiloto().getEscuderia().getNombre()
                                    : "Sin escudería",
                            p.getPuntosObtenidos()));
                }
            } else {
                detalles.append("PARTICIPANTES INSCRITOS:\n");
                detalles.append("========================\n");
                for (Participacion p : carrera.getParticipaciones()) {
                    detalles.append(String.format("- %s (%s)\n",
                            p.getPiloto().getNombreCompleto(),
                            p.getPiloto().getEscuderia() != null ? p.getPiloto().getEscuderia().getNombre()
                                    : "Sin escudería"));
                }
            }

            JTextArea textArea = new JTextArea(detalles.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 500));

            JOptionPane.showMessageDialog(this, scrollPane,
                    "Detalles - " + nombreCarrera,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}