package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Ventana para la gestión de carreras (Grandes Premios)
 */
public class VentanaCarreras extends JFrame {
    private GestorFormula1 gestor;
    private JTabbedPane pestanas;
    private JTable tablaCarreras;
    private DefaultTableModel modeloTablaCarreras;
    private JTable tablaParticipaciones;
    private DefaultTableModel modeloTablaParticipaciones;
    private JTextField txtNombreCarrera, txtFecha, txtHora;
    private JComboBox<Circuito> comboCircuitos;
    private GranPremio carreraSeleccionada;

    /**
     * Constructor de la ventana de carreras
     * 
     * @param gestor Gestor principal del sistema
     */
    public VentanaCarreras(GestorFormula1 gestor) {
        this.gestor = gestor;
        inicializarComponentes();
        configurarVentana();
        cargarDatos();
    }

    /**
     * Inicializa los componentes de la interfaz
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        // Panel principal con pestañas
        pestanas = new JTabbedPane();

        // Pestaña de planificación de carreras
        JPanel panelPlanificacion = crearPanelPlanificacion();
        pestanas.addTab("Planificar Carreras", panelPlanificacion);

        // Pestaña de inscripción de pilotos
        JPanel panelInscripcion = crearPanelInscripcion();
        pestanas.addTab("Inscribir Pilotos", panelInscripcion);

        // Pestaña de registro de resultados
        JPanel panelResultados = crearPanelResultados();
        pestanas.addTab("Registrar Resultados", panelResultados);

        // Panel de botones
        JPanel panelBotones = crearPanelBotones();

        add(pestanas, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Crea el panel de planificación de carreras
     */
    private JPanel crearPanelPlanificacion() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Planificar Nueva Carrera"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre del Gran Premio:"), gbc);
        gbc.gridx = 1;
        txtNombreCarrera = new JTextField(20);
        panelFormulario.add(txtNombreCarrera, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Circuito:"), gbc);
        gbc.gridx = 1;
        comboCircuitos = new JComboBox<>();
        panelFormulario.add(comboCircuitos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Fecha (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        txtFecha = new JTextField(20);
        txtFecha.setToolTipText("📅 Formato: dd/MM/yyyy (ej: 25/12/2023). La fecha debe ser futura.");
        panelFormulario.add(txtFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Hora (HH:mm):"), gbc);
        gbc.gridx = 1;
        txtHora = new JTextField(20);
        txtHora.setToolTipText("🕒 Formato: HH:mm (ej: 14:00 para las 2:00 PM)");
        panelFormulario.add(txtHora, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        JButton btnHoyMas7 = new JButton("📅 +7 días");
        btnHoyMas7.setPreferredSize(new Dimension(80, 25));
        btnHoyMas7.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        btnHoyMas7.addActionListener(e -> {
            LocalDateTime fechaFutura = LocalDateTime.now().plusDays(7);
            txtFecha.setText(fechaFutura.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            txtHora.setText("15:00");
        });
        panelFormulario.add(btnHoyMas7, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        JButton btnHoraDefecto = new JButton("🕒 15:00");
        btnHoraDefecto.setPreferredSize(new Dimension(80, 25));
        btnHoraDefecto.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        btnHoraDefecto.addActionListener(e -> txtHora.setText("15:00"));
        panelFormulario.add(btnHoraDefecto, gbc);

        // Botones de planificación
        JPanel panelBotonesPlan = new JPanel(new FlowLayout());
        JButton btnPlanificar = new JButton("Planificar Carrera");
        JButton btnModificarCarrera = new JButton("Modificar");
        JButton btnEliminarCarrera = new JButton("Eliminar");
        JButton btnLimpiarPlan = new JButton("Limpiar");

        // Aplicar estilos estándar
        aplicarEstiloBoton(btnPlanificar);
        aplicarEstiloBoton(btnModificarCarrera);
        aplicarEstiloBoton(btnEliminarCarrera);
        aplicarEstiloBoton(btnLimpiarPlan);

        btnPlanificar.addActionListener(e -> planificarCarrera());
        btnModificarCarrera.addActionListener(e -> modificarCarrera());
        btnEliminarCarrera.addActionListener(e -> eliminarCarrera());
        btnLimpiarPlan.addActionListener(e -> limpiarFormularioPlanificacion());

        panelBotonesPlan.add(btnPlanificar);
        panelBotonesPlan.add(btnModificarCarrera);
        panelBotonesPlan.add(btnEliminarCarrera);
        panelBotonesPlan.add(btnLimpiarPlan);

        // Tabla de carreras planificadas
        JPanel panelTablaCarreras = new JPanel(new BorderLayout());
        panelTablaCarreras.setBorder(BorderFactory.createTitledBorder("📋 Carreras del Campeonato"));

        String[] columnasCarreras = { "Gran Premio", "Circuito", "País", "Fecha", "Hora", "Estado", "Pilotos",
                "Ganador" };
        modeloTablaCarreras = new DefaultTableModel(columnasCarreras, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCarreras = new JTable(modeloTablaCarreras);
        tablaCarreras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCarreras.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarCarreraSeleccionada();
            }
        });

        JScrollPane scrollCarreras = new JScrollPane(tablaCarreras);
        scrollCarreras.setPreferredSize(new Dimension(700, 200));

        panelTablaCarreras.add(scrollCarreras, BorderLayout.CENTER);

        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(panelBotonesPlan, BorderLayout.CENTER);
        panel.add(panelTablaCarreras, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel de inscripción de pilotos mejorado
     */
    private JPanel crearPanelInscripcion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de información de la carrera seleccionada - más compacto
        JPanel panelInfoCarrera = crearPanelInfoCarrera();
        panelInfoCarrera.setPreferredSize(new Dimension(0, 120));

        // Panel principal de inscripción dividido en dos
        JPanel panelInscripcionPrincipal = new JPanel(new GridBagLayout());
        panelInscripcionPrincipal.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel izquierdo: Inscripción individual
        JPanel panelInscripcionIndividual = crearPanelInscripcionIndividual();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 10);
        panelInscripcionPrincipal.add(panelInscripcionIndividual, gbc);

        JPanel panelInscripcionMasiva = crearPanelInscripcionMasiva();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.insets = new Insets(5, 10, 5, 5);
        panelInscripcionPrincipal.add(panelInscripcionMasiva, gbc);

        // Panel de tabla con altura fija para evitar superposición
        JPanel panelTablaParticipaciones = crearPanelTablaParticipacionesMejorada();
        panelTablaParticipaciones.setPreferredSize(new Dimension(0, 280));

        panel.add(panelInfoCarrera, BorderLayout.NORTH);
        panel.add(panelInscripcionPrincipal, BorderLayout.CENTER);
        panel.add(panelTablaParticipaciones, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel de información de la carrera seleccionada
     */
    private JPanel crearPanelInfoCarrera() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("📍 Información de la Carrera Seleccionada"));
        panel.setPreferredSize(new Dimension(0, 100));

        JLabel lblInfoCarrera = new JLabel(
                "<html><div style='text-align: center; padding: 8px;'>" +
                        "<p style='font-size: 13px; color: #dc3545;'>⚠️ <b>No hay carrera seleccionada</b></p>" +
                        "<p style='font-size: 11px; margin-top: 5px;'>Vaya a 'Planificar Carreras' → Seleccione una carrera → Regrese aquí</p>"
                        +
                        "</div></html>");
        lblInfoCarrera.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblInfoCarrera, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de inscripción individual
     */
    private JPanel crearPanelInscripcionIndividual() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("🏎️ Inscripción Individual"));
        panel.setPreferredSize(new Dimension(400, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JComboBox<Piloto> comboPilotos = new JComboBox<>();
        JComboBox<Auto> comboAutos = new JComboBox<>();

        // Hacer los combos más anchos
        comboPilotos.setPreferredSize(new Dimension(280, 25));
        comboAutos.setPreferredSize(new Dimension(280, 25));

        // Campo piloto
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("🏁 Seleccionar Piloto:"), gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(comboPilotos, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("🚗 Seleccionar Auto:"), gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(comboAutos, gbc);

        // Botón de inscripción
        JButton btnInscribirIndividual = new JButton("✅ Inscribir Piloto");
        btnInscribirIndividual.setPreferredSize(new Dimension(200, 35));
        aplicarEstiloBoton(btnInscribirIndividual);

        gbc.gridy = 4;
        gbc.insets = new Insets(10, 5, 5, 5);
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnInscribirIndividual, gbc);

        // Información adicional - más compacta
        JTextArea infoArea = new JTextArea(
                "💡 Tip: Seleccione piloto y auto disponible para inscribir.", 2, 25);
        infoArea.setEditable(false);
        infoArea.setOpaque(false);
        infoArea.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);

        gbc.gridy = 5;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(infoArea, gbc);

        btnInscribirIndividual.addActionListener(e -> inscribirPiloto(comboPilotos, comboAutos));

        // Cargar datos iniciales
        cargarPilotosYAutos(comboPilotos, comboAutos);

        return panel;
    }

    /**
     * Crea el panel de inscripción masiva
     */
    private JPanel crearPanelInscripcionMasiva() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("⚡ Inscripción Rápida Masiva"));
        panel.setPreferredSize(new Dimension(500, 300));

        // Lista de pilotos disponibles
        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBorder(BorderFactory.createTitledBorder("Pilotos Disponibles"));

        String[] columnasPilotos = { "Piloto", "Escudería", "Puntos" };
        DefaultTableModel modeloPilotosDisponibles = new DefaultTableModel(columnasPilotos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaPilotosDisponibles = new JTable(modeloPilotosDisponibles);
        tablaPilotosDisponibles.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tablaPilotosDisponibles.setRowHeight(20);

        JScrollPane scrollPilotos = new JScrollPane(tablaPilotosDisponibles);
        scrollPilotos.setPreferredSize(new Dimension(450, 180));

        panelLista.add(scrollPilotos, BorderLayout.CENTER);

        // Panel de botones de acción rápida - más compactos
        JPanel panelBotonesRapidos = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton btnInscribirSeleccionados = new JButton("📝 Inscribir Sel.");
        JButton btnSeleccionAutomatica = new JButton("🎯 Selección Auto (10)");
        JButton btnInscribirTodos = new JButton("🏁 Inscribir Todos");
        JButton btnActualizar = new JButton("🔄 Actualizar");

        // Hacer botones más pequeños
        Dimension btnSize = new Dimension(110, 28);
        btnInscribirSeleccionados.setPreferredSize(btnSize);
        btnSeleccionAutomatica.setPreferredSize(new Dimension(130, 28));
        btnInscribirTodos.setPreferredSize(btnSize);
        btnActualizar.setPreferredSize(btnSize);

        aplicarEstiloBoton(btnInscribirSeleccionados);
        aplicarEstiloBoton(btnSeleccionAutomatica);
        aplicarEstiloBoton(btnInscribirTodos);
        aplicarEstiloBoton(btnActualizar);

        btnInscribirSeleccionados.addActionListener(e -> inscribirPilotosSeleccionados(tablaPilotosDisponibles));
        btnSeleccionAutomatica.addActionListener(e -> seleccionarYInscribirAutomaticamente(tablaPilotosDisponibles));
        btnInscribirTodos.addActionListener(e -> inscribirTodosLosPilotos());
        btnActualizar.addActionListener(e -> {
            actualizarTablaPilotosDisponibles(modeloPilotosDisponibles);
            actualizarTablaParticipaciones();
        });

        panelBotonesRapidos.add(btnInscribirSeleccionados);
        panelBotonesRapidos.add(btnSeleccionAutomatica);
        panelBotonesRapidos.add(btnInscribirTodos);
        panelBotonesRapidos.add(btnActualizar);

        panel.add(panelLista, BorderLayout.CENTER);
        panel.add(panelBotonesRapidos, BorderLayout.SOUTH);

        actualizarTablaPilotosDisponibles(modeloPilotosDisponibles);

        return panel;
    }

    private JPanel crearPanelTablaParticipacionesMejorada() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(0, 260));

        JPanel panelInfoDinamica = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfoDinamica.setBorder(BorderFactory.createTitledBorder("📊 Estado de Inscripciones"));
        panelInfoDinamica.setPreferredSize(new Dimension(0, 50));

        JLabel lblContadorParticipantes = new JLabel("Participantes: 0");
        JLabel lblLimiteParticipantes = new JLabel(" | Límite máximo: 20");
        JLabel lblEspaciosDisponibles = new JLabel(" | Espacios disponibles: 20");

        lblContadorParticipantes.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblEspaciosDisponibles.setFont(new Font("Segoe UI", Font.BOLD, 11));

        panelInfoDinamica.add(lblContadorParticipantes);
        panelInfoDinamica.add(lblLimiteParticipantes);
        panelInfoDinamica.add(lblEspaciosDisponibles);

        // Tabla de participaciones
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("🏎️ Participantes Inscritos"));

        String[] columnasParticipaciones = { "Pos.", "Piloto", "Escudería", "Auto", "Puntos", "Estado" };
        modeloTablaParticipaciones = new DefaultTableModel(columnasParticipaciones, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaParticipaciones = new JTable(modeloTablaParticipaciones);
        tablaParticipaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaParticipaciones.setRowHeight(18);

        JScrollPane scrollParticipaciones = new JScrollPane(tablaParticipaciones);
        scrollParticipaciones.setPreferredSize(new Dimension(0, 120));

        panelTabla.add(scrollParticipaciones, BorderLayout.CENTER);

        JPanel panelBotonesGestion = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 3));
        panelBotonesGestion.setPreferredSize(new Dimension(0, 40));

        JButton btnRemoverSeleccionado = new JButton("❌ Remover Sel.");
        JButton btnRemoverTodos = new JButton("🗑️ Limpiar Todo");
        JButton btnAsignarAutosAutomatico = new JButton("🔄 Asignar Autos");

        // Botones más compactos
        Dimension btnSizeSmall = new Dimension(120, 30);
        btnRemoverSeleccionado.setPreferredSize(btnSizeSmall);
        btnRemoverTodos.setPreferredSize(btnSizeSmall);
        btnAsignarAutosAutomatico.setPreferredSize(btnSizeSmall);

        aplicarEstiloBoton(btnRemoverSeleccionado);
        aplicarEstiloBoton(btnRemoverTodos);
        aplicarEstiloBoton(btnAsignarAutosAutomatico);

        btnRemoverSeleccionado.addActionListener(e -> removerParticipacion());
        btnRemoverTodos.addActionListener(e -> removerTodasLasParticipaciones());
        btnAsignarAutosAutomatico.addActionListener(e -> asignarAutosAutomaticamente());

        panelBotonesGestion.add(btnRemoverSeleccionado);
        panelBotonesGestion.add(btnRemoverTodos);
        panelBotonesGestion.add(btnAsignarAutosAutomatico);

        panel.add(panelInfoDinamica, BorderLayout.NORTH);
        panel.add(panelTabla, BorderLayout.CENTER);
        panel.add(panelBotonesGestion, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel de registro de resultados
     */
    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel de información
        JPanel panelInfo = new JPanel(new FlowLayout());
        panelInfo.setBorder(BorderFactory.createTitledBorder("Registro de Resultados"));

        JLabel lblInfo = new JLabel(
                "Seleccione una carrera y haga doble clic en una participación para registrar resultados");
        panelInfo.add(lblInfo);

        // Tabla de participaciones para resultados
        JPanel panelTablaResultados = new JPanel(new BorderLayout());
        panelTablaResultados.setBorder(BorderFactory.createTitledBorder("Participaciones"));

        JScrollPane scrollResultados = new JScrollPane(tablaParticipaciones);
        scrollResultados.setPreferredSize(new Dimension(800, 300));

        panelTablaResultados.add(scrollResultados, BorderLayout.CENTER);

        tablaParticipaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editarResultadoParticipacion();
                }
            }
        });

        // Botones de resultados
        JPanel panelBotonesRes = new JPanel(new FlowLayout());
        JButton btnEstablecerResultadosRapido = new JButton("⚡ Resultados Automáticos");
        JButton btnEditarResultado = new JButton("✏️ Editar Resultado");
        JButton btnFinalizarCarrera = new JButton("🏁 Finalizar Carrera");
        JButton btnVerResultados = new JButton("📊 Ver Resultados");

        // Aplicar estilos estándar
        aplicarEstiloBoton(btnEstablecerResultadosRapido);
        aplicarEstiloBoton(btnEditarResultado);
        aplicarEstiloBoton(btnFinalizarCarrera);
        aplicarEstiloBoton(btnVerResultados);

        btnEstablecerResultadosRapido.addActionListener(e -> establecerResultadosAutomaticos());
        btnEditarResultado.addActionListener(e -> editarResultadoParticipacion());
        btnFinalizarCarrera.addActionListener(e -> finalizarCarrera());
        btnVerResultados.addActionListener(e -> verResultadosFinales());

        panelBotonesRes.add(btnEstablecerResultadosRapido);
        panelBotonesRes.add(btnEditarResultado);
        panelBotonesRes.add(btnFinalizarCarrera);
        panelBotonesRes.add(btnVerResultados);

        panel.add(panelInfo, BorderLayout.NORTH);
        panel.add(panelTablaResultados, BorderLayout.CENTER);
        panel.add(panelBotonesRes, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel de botones principales
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnCerrar = new JButton("Cerrar");
        aplicarEstiloBoton(btnCerrar);
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnCerrar);
        return panel;
    }

    /**
     * Configura la ventana
     */
    private void configurarVentana() {
        setTitle("Gestión de Carreras - Grandes Premios");
        setSize(1200, 850);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Carga los datos iniciales
     */
    private void cargarDatos() {
        cargarCircuitos();
        actualizarTablaCarreras();
    }

    /**
     * Carga los circuitos en el combo
     */
    private void cargarCircuitos() {
        comboCircuitos.removeAllItems();
        for (Circuito circuito : gestor.getCircuitos()) {
            comboCircuitos.addItem(circuito);
        }
    }

    /**
     * Carga pilotos y autos disponibles
     */
    private void cargarPilotosYAutos(JComboBox<Piloto> comboPilotos, JComboBox<Auto> comboAutos) {
        comboPilotos.removeAllItems();
        comboAutos.removeAllItems();

        for (Piloto piloto : gestor.getPilotos()) {
            comboPilotos.addItem(piloto);
        }

        for (Auto auto : gestor.getAutos()) {
            comboAutos.addItem(auto);
        }
    }

    /**
     * Actualiza los combos de inscripción con pilotos y autos disponibles
     */
    private void actualizarCombosInscripcion() {
        // Buscar los combos en el panel de inscripción individual y actualizarlos
        Component tabComponent = pestanas.getComponentAt(1);
        if (tabComponent instanceof Container) {
            buscarYActualizarCombos((Container) tabComponent);
        }
    }

    /**
     * Método auxiliar para buscar y actualizar combos en contenedores
     */
    private void buscarYActualizarCombos(Container contenedor) {
        for (Component comp : contenedor.getComponents()) {
            if (comp instanceof JComboBox) {
                JComboBox<?> combo = (JComboBox<?>) comp;
                if (combo.getItemCount() > 0) {
                    Object firstItem = combo.getItemAt(0);
                    if (firstItem instanceof Piloto) {
                        // Es combo de pilotos
                        @SuppressWarnings("unchecked")
                        JComboBox<Piloto> comboPilotos = (JComboBox<Piloto>) combo;
                        comboPilotos.removeAllItems();
                        for (Piloto piloto : gestor.getPilotos()) {
                            comboPilotos.addItem(piloto);
                        }
                    } else if (firstItem instanceof Auto) {
                        // Es combo de autos
                        @SuppressWarnings("unchecked")
                        JComboBox<Auto> comboAutos = (JComboBox<Auto>) combo;
                        comboAutos.removeAllItems();
                        for (Auto auto : gestor.getAutos()) {
                            comboAutos.addItem(auto);
                        }
                    }
                }
            } else if (comp instanceof Container) {
                buscarYActualizarCombos((Container) comp);
            }
        }
    }

    /**
     * Actualiza la tabla de carreras mostrando información más detallada
     */
    private void actualizarTablaCarreras() {
        modeloTablaCarreras.setRowCount(0);
        for (GranPremio carrera : gestor.getGrandesPremios()) {
            String ganador = "No finalizada";
            if (carrera.isFinalizada() && !carrera.getResultados().isEmpty()) {
                Participacion primerLugar = carrera.getResultados().get(0);
                ganador = primerLugar.getPiloto().getNombreCompleto() +
                        " (" + primerLugar.getPuntosObtenidos() + " pts)";
            }

            Object[] fila = {
                    carrera.getNombre(),
                    carrera.getCircuito().getNombre(),
                    carrera.getCircuito().getPais().getNombre(),
                    carrera.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    carrera.getFechaHora().format(DateTimeFormatter.ofPattern("HH:mm")),
                    carrera.isFinalizada() ? "✅ Finalizada" : "⏳ Programada",
                    carrera.getParticipaciones().size(),
                    ganador
            };
            modeloTablaCarreras.addRow(fila);
        }
    }

    /**
     * Actualiza la tabla de participaciones
     */
    /**
     * Actualiza la tabla de participaciones mostrando posiciones y puntos
     * claramente
     */
    private void actualizarTablaParticipaciones() {
        modeloTablaParticipaciones.setRowCount(0);
        if (carreraSeleccionada != null) {
            java.util.List<Participacion> participaciones = carreraSeleccionada.getParticipaciones()
                    .stream()
                    .sorted((p1, p2) -> {
                        // Los que no terminaron van al final
                        if (p1.getPosicionFinal() == 0 && p2.getPosicionFinal() == 0) {
                            return p1.getPiloto().getNombreCompleto().compareTo(p2.getPiloto().getNombreCompleto());
                        }
                        if (p1.getPosicionFinal() == 0)
                            return 1;
                        if (p2.getPosicionFinal() == 0)
                            return -1;
                        return Integer.compare(p1.getPosicionFinal(), p2.getPosicionFinal());
                    })
                    .collect(java.util.stream.Collectors.toList());

            int contador = 1;
            for (Participacion participacion : participaciones) {
                String posicion;
                String estado = "✅ Inscrito"; // Estado por defecto

                if (participacion.isAbandono()) {
                    posicion = "DNF";
                    estado = "❌ Abandono: "
                            + (participacion.getMotivoAbandono() != null ? participacion.getMotivoAbandono()
                                    : "No especificado");
                } else if (participacion.getPosicionFinal() > 0) {
                    posicion = "P" + participacion.getPosicionFinal();
                    if (participacion.getPosicionFinal() == 1) {
                        estado = "🏆 GANADOR";
                    } else if (participacion.getPosicionFinal() == 2) {
                        estado = "🥈 Segundo lugar";
                    } else if (participacion.getPosicionFinal() == 3) {
                        estado = "🥉 Tercer lugar";
                    } else {
                        estado = "🏁 Posición " + participacion.getPosicionFinal();
                    }
                    if (participacion.isVueltaRapida()) {
                        estado += " + ⚡ V.Rápida";
                    }
                } else {
                    posicion = String.valueOf(contador);
                    if (carreraSeleccionada.isFinalizada()) {
                        estado = "⚠️ No clasificado";
                    } else {
                        estado = "✅ Listo para correr";
                    }
                }

                // Obtener información de escudería
                String escuderia = "Sin escudería";
                if (participacion.getPiloto().getEscuderia() != null) {
                    escuderia = participacion.getPiloto().getEscuderia().getNombre();
                }

                // Obtener información del auto
                String autoInfo = participacion.getAuto().getModelo() + " (" + participacion.getAuto().getNumeroChasis()
                        + ")";

                Object[] fila = {
                        posicion,
                        participacion.getPiloto().getNombreCompleto(),
                        escuderia,
                        autoInfo,
                        participacion.getPuntosObtenidos() + " pts",
                        estado
                };
                modeloTablaParticipaciones.addRow(fila);
                contador++;
            }
        }

        // Actualizar contadores dinámicos
        actualizarContadoresParticipacion();
    }

    /**
     * Carga la carrera seleccionada y actualiza la información contextual
     */
    private void cargarCarreraSeleccionada() {
        int filaSeleccionada = tablaCarreras.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombreCarrera = (String) modeloTablaCarreras.getValueAt(filaSeleccionada, 0);
            carreraSeleccionada = gestor.getGrandesPremios().stream()
                    .filter(c -> c.getNombre().equals(nombreCarrera))
                    .findFirst().orElse(null);

            if (carreraSeleccionada != null) {
                // Cargar datos en el formulario
                txtNombreCarrera.setText(carreraSeleccionada.getNombre());
                comboCircuitos.setSelectedItem(carreraSeleccionada.getCircuito());
                txtFecha.setText(carreraSeleccionada.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                txtHora.setText(carreraSeleccionada.getFechaHora().format(DateTimeFormatter.ofPattern("HH:mm")));

                // Actualizar todas las vistas de datos
                actualizarTablaParticipaciones();
                actualizarInformacionCarreraSeleccionada();

                // Actualizar combos de pilotos y autos disponibles
                actualizarCombosInscripcion();

                int participantes = carreraSeleccionada.getParticipaciones().size();
                String estadoCarrera = carreraSeleccionada.isFinalizada() ? "🏁 Finalizada" : "⏳ Programada";

                // Actualizar títulos de pestañas con información dinámica
                pestanas.setTitleAt(1, String.format("Inscribir Pilotos - %s (%d/20)",
                        carreraSeleccionada.getNombre(), participantes));
                pestanas.setTitleAt(2, String.format("Resultados - %s [%s]",
                        carreraSeleccionada.getNombre(), estadoCarrera));
            }
        } else {
            // No hay carrera seleccionada
            carreraSeleccionada = null;
            actualizarTablaParticipaciones();
            pestanas.setTitleAt(1, "Inscribir Pilotos - Seleccione una carrera");
            pestanas.setTitleAt(2, "Resultados - Seleccione una carrera");
        }
    }

    /**
     * Actualiza la información de la carrera seleccionada en el panel de
     * inscripción
     */
    private void actualizarInformacionCarreraSeleccionada() {
        // Buscar el panel de información y actualizar su contenido
        Component tabComponent = pestanas.getComponentAt(1);
        if (tabComponent instanceof Container) {
            Container container = (Container) tabComponent;
            Component[] components = container.getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    if (panel.getBorder() instanceof javax.swing.border.TitledBorder) {
                        javax.swing.border.TitledBorder border = (javax.swing.border.TitledBorder) panel.getBorder();
                        if (border.getTitle().contains("📍 Información de la Carrera")) {
                            panel.removeAll();

                            if (carreraSeleccionada != null) {
                                String estado = carreraSeleccionada.isFinalizada() ? "🏁 Finalizada" : "⏳ Programada";
                                String estadoColor = carreraSeleccionada.isFinalizada() ? "#dc3545" : "#28a745";
                                int participantes = carreraSeleccionada.getParticipaciones().size();
                                int espaciosDisponibles = 20 - participantes;

                                String info = String.format(
                                        "<html><div style='text-align: center; padding: 15px;'>" +
                                                "<h3 style='color: #212529; margin: 0 0 10px 0;'>🏁 %s</h3>" +
                                                "<div style='background: #f8f9fa; padding: 10px; border-radius: 5px; margin: 10px 0;'>"
                                                +
                                                "<p style='margin: 5px 0; font-size: 13px;'><b>🏎️ Circuito:</b> %s</p>"
                                                +
                                                "<p style='margin: 5px 0; font-size: 13px;'><b>🌍 País:</b> %s</p>" +
                                                "<p style='margin: 5px 0; font-size: 13px;'><b>📅 Fecha:</b> %s</p>" +
                                                "<p style='margin: 5px 0; font-size: 13px;'><b>⏰ Hora:</b> %s</p>" +
                                                "</div>" +
                                                "<div style='background: %s; color: white; padding: 8px; border-radius: 5px; margin: 10px 0;'>"
                                                +
                                                "<p style='margin: 0; font-weight: bold;'>%s</p>" +
                                                "</div>" +
                                                "<div style='background: #e9ecef; padding: 10px; border-radius: 5px;'>"
                                                +
                                                "<p style='margin: 5px 0; font-size: 13px;'><b>👥 Participantes:</b> %d/20</p>"
                                                +
                                                "<p style='margin: 5px 0; font-size: 13px;'><b>🆓 Espacios disponibles:</b> %d</p>"
                                                +
                                                "</div>" +
                                                "</div></html>",
                                        carreraSeleccionada.getNombre(),
                                        carreraSeleccionada.getCircuito().getNombre(),
                                        carreraSeleccionada.getCircuito().getPais().getNombre(),
                                        carreraSeleccionada.getFechaHora()
                                                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                        carreraSeleccionada.getFechaHora().format(DateTimeFormatter.ofPattern("HH:mm")),
                                        estadoColor,
                                        estado,
                                        participantes,
                                        espaciosDisponibles);

                                JLabel lblInfo = new JLabel(info);
                                lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
                                panel.add(lblInfo, BorderLayout.CENTER);

                                if (!carreraSeleccionada.isFinalizada() && espaciosDisponibles > 0) {
                                    JLabel lblAccion = new JLabel(
                                            "<html><div style='text-align: center; margin-top: 10px;'>" +
                                                    "<p style='color: #28a745; font-weight: bold;'>✅ Lista para inscripciones</p>"
                                                    +
                                                    "</div></html>");
                                    lblAccion.setHorizontalAlignment(SwingConstants.CENTER);
                                    panel.add(lblAccion, BorderLayout.SOUTH);
                                } else if (carreraSeleccionada.isFinalizada()) {
                                    JLabel lblAccion = new JLabel(
                                            "<html><div style='text-align: center; margin-top: 10px;'>" +
                                                    "<p style='color: #dc3545; font-weight: bold;'>🏁 Carrera finalizada</p>"
                                                    +
                                                    "</div></html>");
                                    lblAccion.setHorizontalAlignment(SwingConstants.CENTER);
                                    panel.add(lblAccion, BorderLayout.SOUTH);
                                } else {
                                    JLabel lblAccion = new JLabel(
                                            "<html><div style='text-align: center; margin-top: 10px;'>" +
                                                    "<p style='color: #ffc107; font-weight: bold;'>⚠️ Carrera llena</p>"
                                                    +
                                                    "</div></html>");
                                    lblAccion.setHorizontalAlignment(SwingConstants.CENTER);
                                    panel.add(lblAccion, BorderLayout.SOUTH);
                                }
                            } else {
                                panel.add(crearPanelInfoCarrera().getComponent(0), BorderLayout.CENTER);
                            }

                            panel.revalidate();
                            panel.repaint();
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Actualiza los contadores de participación dinámicamente
     */
    private void actualizarContadoresParticipacion() {
        // Buscar y actualizar los labels de contadores
        Component tabComponent = pestanas.getComponentAt(1);
        if (tabComponent instanceof Container) {
            Container container = (Container) tabComponent;
            actualizarContadoresEnContenedor(container);
        }
    }

    /**
     * Método auxiliar para actualizar contadores en un contenedor
     */
    private void actualizarContadoresEnContenedor(Container contenedor) {
        for (Component comp : contenedor.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                String texto = label.getText();
                if (texto.startsWith("Participantes:")) {
                    int participantes = carreraSeleccionada != null ? carreraSeleccionada.getParticipaciones().size()
                            : 0;
                    label.setText("Participantes: " + participantes);
                } else if (texto.contains("Espacios disponibles:")) {
                    int participantes = carreraSeleccionada != null ? carreraSeleccionada.getParticipaciones().size()
                            : 0;
                    int disponibles = 20 - participantes;
                    label.setText(" | Espacios disponibles: " + disponibles);
                }
            } else if (comp instanceof Container) {
                actualizarContadoresEnContenedor((Container) comp);
            }
        }
    }

    /**
     * Planifica una nueva carrera
     */
    private void planificarCarrera() {
        try {
            validarCamposCarrera();

            String nombre = txtNombreCarrera.getText().trim();
            Circuito circuito = (Circuito) comboCircuitos.getSelectedItem();
            LocalDateTime fechaHora = parsearFechaHora();

            // Validar que no exista una carrera con el mismo nombre
            boolean nombreExiste = gestor.getGrandesPremios().stream()
                    .anyMatch(gp -> gp.getNombre().equalsIgnoreCase(nombre));

            if (nombreExiste) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ Ya existe una carrera con ese nombre.\n\n" +
                                "Por favor, elija un nombre diferente.\n" +
                                "💡 Tip: Puede agregar año o temporada al nombre.",
                        "Nombre duplicado",
                        JOptionPane.WARNING_MESSAGE);
                txtNombreCarrera.requestFocus();
                return;
            }

            // Validar que no haya conflicto de horario en el mismo circuito
            boolean conflictoHorario = gestor.getGrandesPremios().stream()
                    .anyMatch(gp -> gp.getCircuito().equals(circuito) &&
                            Math.abs(java.time.Duration.between(gp.getFechaHora(), fechaHora).toHours()) < 24);

            if (conflictoHorario) {
                int opcion = JOptionPane.showConfirmDialog(this,
                        "⚠️ Advertencia: Hay una carrera programada en el mismo circuito\n" +
                                "dentro de las 24 horas siguientes.\n\n" +
                                "¿Desea continuar de todas formas?",
                        "Posible conflicto de horario",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (opcion != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Validar que la fecha no sea en el pasado (doble verificación)
            if (fechaHora.isBefore(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(this,
                        "❌ La fecha y hora de la carrera debe ser futura.\n\n" +
                                "🗺 Fecha actual: "
                                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n" +
                                "🏁 Fecha seleccionada: "
                                + fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        "Fecha inválida",
                        JOptionPane.WARNING_MESSAGE);
                txtFecha.requestFocus();
                return;
            }

            GranPremio nuevaCarrera = new GranPremio(nombre, fechaHora, circuito);
            gestor.registrarGranPremio(nuevaCarrera);

            actualizarTablaCarreras();
            limpiarFormularioPlanificacion();

            JOptionPane.showMessageDialog(this,
                    "✅ Carrera planificada exitosamente\n\n" +
                            "📅 " + nombre + "\n" +
                            "🏁 " + circuito.getNombre() + " (" + circuito.getPais().getNombre() + ")\n" +
                            "⏰ " + fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n\n" +
                            "💡 Ya puedes inscribir pilotos en la pestaña correspondiente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error al planificar carrera:\n\n" + e.getMessage() + "\n\n" +
                            "💡 Verifique que todos los campos estén correctos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica la carrera seleccionada
     */
    private void modificarCarrera() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Seleccione una carrera para modificar\n\n" +
                            "💡 Haga clic en una fila de la tabla de carreras.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this,
                    "❌ No se puede modificar una carrera finalizada\n\n" +
                            "🏁 Carrera: " + carreraSeleccionada.getNombre() + "\n" +
                            "📅 Estado: Finalizada",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si hay participaciones inscritas
        if (!carreraSeleccionada.getParticipaciones().isEmpty()) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "⚠️ Esta carrera ya tiene " + carreraSeleccionada.getParticipaciones().size()
                            + " pilotos inscritos.\n\n" +
                            "Al modificar la carrera, las inscripciones se mantendrán.\n" +
                            "¿Desea continuar con la modificación?",
                    "Confirmar modificación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            validarCamposCarrera();

            String nuevoNombre = txtNombreCarrera.getText().trim();
            Circuito nuevoCircuito = (Circuito) comboCircuitos.getSelectedItem();
            LocalDateTime nuevaFechaHora = parsearFechaHora();

            // Validar que no exista otra carrera con el mismo nombre (excluyendo la actual)
            boolean nombreExiste = gestor.getGrandesPremios().stream()
                    .anyMatch(gp -> !gp.equals(carreraSeleccionada) && gp.getNombre().equalsIgnoreCase(nuevoNombre));

            if (nombreExiste) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ Ya existe otra carrera con ese nombre.\n\n" +
                                "Por favor, elija un nombre diferente.\n" +
                                "💡 Tip: Puede agregar año o temporada al nombre.",
                        "Nombre duplicado",
                        JOptionPane.WARNING_MESSAGE);
                txtNombreCarrera.requestFocus();
                return;
            }

            carreraSeleccionada.setNombre(nuevoNombre);
            carreraSeleccionada.setCircuito(nuevoCircuito);
            carreraSeleccionada.setFechaHora(nuevaFechaHora);

            actualizarTablaCarreras();
            actualizarInformacionCarreraSeleccionada();

            JOptionPane.showMessageDialog(this,
                    "✅ Carrera modificada exitosamente\n\n" +
                            "📅 " + nuevoNombre + "\n" +
                            "🏁 " + nuevoCircuito.getNombre() + " (" + nuevoCircuito.getPais().getNombre() + ")\n" +
                            "⏰ " + nuevaFechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error al modificar carrera:\n\n" + e.getMessage() + "\n\n" +
                            "💡 Verifique que todos los campos estén correctos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina la carrera seleccionada
     */
    private void eliminarCarrera() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Seleccione una carrera para eliminar\n\n" +
                            "💡 Haga clic en una fila de la tabla de carreras.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar si la carrera está finalizada
        if (carreraSeleccionada.isFinalizada()) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "⚠️ Esta carrera ya está finalizada.\n\n" +
                            "🏁 Carrera: " + carreraSeleccionada.getNombre() + "\n" +
                            "📅 Estado: Finalizada\n" +
                            "📋 Participantes: " + carreraSeleccionada.getParticipaciones().size() + "\n\n" +
                            "¿Está seguro que desea eliminar esta carrera finalizada?\n" +
                            "Se perderán todos los resultados y participaciones.",
                    "Confirmar eliminación de carrera finalizada",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }
        } else {
            // Para carreras no finalizadas
            String mensaje = "¿Está seguro que desea eliminar esta carrera?\n\n" +
                    "🏁 Carrera: " + carreraSeleccionada.getNombre() + "\n" +
                    "🗺 Circuito: " + carreraSeleccionada.getCircuito().getNombre() + "\n" +
                    "📅 Fecha: "
                    + carreraSeleccionada.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            if (!carreraSeleccionada.getParticipaciones().isEmpty()) {
                mensaje += "\n\n⚠️ Se eliminarán " + carreraSeleccionada.getParticipaciones().size()
                        + " inscripciones de pilotos.";
            }

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    mensaje,
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }
        }

        // Proceder con la eliminación
        String nombreCarrera = carreraSeleccionada.getNombre();
        int numParticipaciones = carreraSeleccionada.getParticipaciones().size();

        gestor.getGrandesPremios().remove(carreraSeleccionada);
        carreraSeleccionada = null;

        actualizarTablaCarreras();
        actualizarTablaParticipaciones();
        limpiarFormularioPlanificacion();

        JOptionPane.showMessageDialog(this,
                "✅ Carrera eliminada exitosamente\n\n" +
                        "🗑️ " + nombreCarrera + "\n" +
                        (numParticipaciones > 0 ? "📋 Se eliminaron " + numParticipaciones + " inscripciones"
                                : "📝 Sin inscripciones"),
                "Eliminación exitosa",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Inscribe un piloto en la carrera seleccionada
     */
    private void inscribirPiloto(JComboBox<Piloto> comboPilotos, JComboBox<Auto> comboAutos) {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Debe seleccionar una carrera primero\n\n" +
                            "📋 Vaya a la pestaña 'Planificar Carreras' y seleccione una carrera de la tabla",
                    "Sin carrera seleccionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this,
                    "🏁 No se puede inscribir pilotos en una carrera finalizada\n\n" +
                            "📊 Esta carrera ya tiene resultados registrados",
                    "Carrera finalizada",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Piloto piloto = (Piloto) comboPilotos.getSelectedItem();
            Auto auto = (Auto) comboAutos.getSelectedItem();

            if (piloto == null) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ Debe seleccionar un piloto",
                        "Piloto requerido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (auto == null) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ Debe seleccionar un auto",
                        "Auto requerido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar si el piloto ya está inscrito
            boolean yaInscrito = carreraSeleccionada.getParticipaciones().stream()
                    .anyMatch(p -> p.getPiloto().equals(piloto));

            if (yaInscrito) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ El piloto " + piloto.getNombreCompleto() + " ya está inscrito en esta carrera",
                        "Piloto ya inscrito",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar límite de participantes
            int participantesActuales = carreraSeleccionada.getParticipaciones().size();
            if (participantesActuales >= 20) {
                JOptionPane.showMessageDialog(this,
                        "🚫 La carrera ha alcanzado el límite máximo de 20 participantes",
                        "Carrera llena",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            gestor.inscribirPilotoEnCarrera(piloto, auto, carreraSeleccionada);

            actualizarTablaParticipaciones();
            actualizarTablaCarreras();

            JOptionPane.showMessageDialog(this,
                    "✅ Piloto inscrito exitosamente\n\n" +
                            "🏎️ " + piloto.getNombreCompleto() + "\n" +
                            "🚗 " + auto.getModelo() + " (" + auto.getNumeroChasis() + ")\n" +
                            "🏁 " + carreraSeleccionada.getNombre() + "\n" +
                            "👥 Participantes: " + (participantesActuales + 1) + "/20",
                    "Inscripción exitosa",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error al inscribir piloto:\n" + e.getMessage(),
                    "Error de inscripción",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Remueve una participación de la carrera
     */
    private void removerParticipacion() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera primero", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int filaSeleccionada = tablaParticipaciones.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una participación para remover", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this, "No se puede modificar una carrera finalizada", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombrePiloto = (String) modeloTablaParticipaciones.getValueAt(filaSeleccionada, 0);
        Participacion participacion = carreraSeleccionada.getParticipaciones().stream()
                .filter(p -> p.getPiloto().getNombreCompleto().equals(nombrePiloto))
                .findFirst().orElse(null);

        if (participacion != null) {
            carreraSeleccionada.getParticipaciones().remove(participacion);
            actualizarTablaParticipaciones();
            actualizarTablaCarreras();

            JOptionPane.showMessageDialog(this, "Participación removida exitosamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Edita el resultado de una participación
     */
    private void editarResultadoParticipacion() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera primero", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int filaSeleccionada = tablaParticipaciones.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una participación para editar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombrePiloto = (String) modeloTablaParticipaciones.getValueAt(filaSeleccionada, 0);
        Participacion participacion = carreraSeleccionada.getParticipaciones().stream()
                .filter(p -> p.getPiloto().getNombreCompleto().equals(nombrePiloto))
                .findFirst().orElse(null);

        if (participacion != null) {
            editarResultadoDialog(participacion);
        }
    }

    /**
     * Muestra el diálogo para editar resultado de participación
     */
    private void editarResultadoDialog(Participacion participacion) {
        JDialog dialog = new JDialog(this, "🏁 Registrar Resultado - " + participacion.getPiloto().getNombreCompleto(),
                true);
        dialog.setLayout(new BorderLayout());

        // Panel principal con información del piloto
        JPanel panelInfo = new JPanel(new FlowLayout());
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información del Piloto"));
        panelInfo.add(new JLabel("Piloto: " + participacion.getPiloto().getNombreCompleto()));
        panelInfo.add(new JLabel(" | Auto: " + participacion.getAuto().getModelo()));
        if (participacion.getPiloto().getEscuderia() != null) {
            panelInfo.add(new JLabel(" | Escudería: " + participacion.getPiloto().getEscuderia().getNombre()));
        }

        // Panel del formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Resultado de la Carrera"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos del formulario
        JTextField txtPosicion = new JTextField(10);
        JTextField txtMejorVuelta = new JTextField(10);
        JCheckBox chkVueltaRapida = new JCheckBox("🏆 Vuelta más rápida de la carrera");
        JCheckBox chkAbandono = new JCheckBox("❌ No terminó la carrera (DNF)");
        JTextField txtMotivoAbandono = new JTextField(20);

        // Cargar datos actuales
        if (participacion.getPosicionFinal() > 0) {
            txtPosicion.setText(String.valueOf(participacion.getPosicionFinal()));
        }
        if (participacion.getMejorVuelta() != null) {
            txtPosicion.setText(participacion.getMejorVuelta().toString());
        }
        chkVueltaRapida.setSelected(participacion.isVueltaRapida());
        chkAbandono.setSelected(participacion.isAbandono());
        if (participacion.getMotivoAbandono() != null) {
            txtMotivoAbandono.setText(participacion.getMotivoAbandono());
        }

        // Layout del formulario
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Posición final (1-20):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtPosicion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Mejor vuelta (mm:ss.SSS):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtMejorVuelta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelFormulario.add(chkVueltaRapida, gbc);

        gbc.gridy = 3;
        panelFormulario.add(chkAbandono, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panelFormulario.add(new JLabel("Motivo del abandono:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtMotivoAbandono, gbc);

        // Panel de información de puntos
        JPanel panelPuntos = new JPanel();
        panelPuntos.setBorder(BorderFactory.createTitledBorder("💰 Sistema de Puntuación"));
        JTextArea areaPuntos = new JTextArea(3, 50);
        areaPuntos.setEditable(false);
        areaPuntos.setText("🏆 P1: 25 pts | 🥈 P2: 18 pts | 🥉 P3: 15 pts | P4: 12 pts | P5: 10 pts\n" +
                "P6: 8 pts | P7: 6 pts | P8: 4 pts | P9: 2 pts | P10: 1 pt\n" +
                "⚡ Vuelta más rápida: +1 punto extra (solo si termina en top 10)");
        areaPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        panelPuntos.add(areaPuntos);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("💾 Guardar Resultado");
        JButton btnCancelar = new JButton("❌ Cancelar");

        // Aplicar estilos estándar
        aplicarEstiloBoton(btnGuardar);
        aplicarEstiloBoton(btnCancelar);

        // Listeners para los checkboxes
        chkAbandono.addActionListener(e -> {
            boolean abandonar = chkAbandono.isSelected();
            txtPosicion.setEnabled(!abandonar);
            chkVueltaRapida.setEnabled(!abandonar);
            txtMotivoAbandono.setEnabled(abandonar);
            if (abandonar) {
                txtPosicion.setText("");
                chkVueltaRapida.setSelected(false);
            }
        });

        btnGuardar.addActionListener(e -> {
            try {
                if (chkAbandono.isSelected()) {
                    String motivo = txtMotivoAbandono.getText().trim();
                    if (motivo.isEmpty()) {
                        motivo = "No especificado";
                    }
                    participacion.marcarAbandono(motivo);
                    participacion.setPuntosObtenidos(0); // Sin puntos por abandono
                } else {
                    String posicionText = txtPosicion.getText().trim();
                    if (posicionText.isEmpty()) {
                        throw new IllegalArgumentException("Debe especificar la posición final");
                    }

                    int posicion = Integer.parseInt(posicionText);
                    if (posicion < 1 || posicion > 20) {
                        throw new IllegalArgumentException("La posición debe estar entre 1 y 20");
                    }

                    participacion.setPosicionFinal(posicion);
                    participacion.setVueltaRapida(chkVueltaRapida.isSelected());

                    controlador.ValidadorFormula1.actualizarPuntosParticipacion(participacion);
                }

                // Mejor vuelta
                String mejorVueltaText = txtMejorVuelta.getText().trim();
                if (!mejorVueltaText.isEmpty()) {
                    try {
                        LocalTime mejorVuelta = LocalTime.parse("00:" + mejorVueltaText);
                        participacion.setMejorVuelta(mejorVuelta);
                    } catch (Exception ex) {
                        // Ignorar si el formato es incorrecto
                    }
                }

                actualizarTablaParticipaciones();
                actualizarTablaCarreras();

                String mensaje = String.format("Resultado guardado exitosamente!\n\n" +
                        "Piloto: %s\n" +
                        "Posición: %s\n" +
                        "Puntos obtenidos: %d%s",
                        participacion.getPiloto().getNombreCompleto(),
                        participacion.isAbandono() ? "DNF" : "P" + participacion.getPosicionFinal(),
                        participacion.getPuntosObtenidos(),
                        participacion.isVueltaRapida() ? " (incluye +1 por vuelta rápida)" : "");

                JOptionPane.showMessageDialog(dialog, mensaje, "✅ Resultado Guardado",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "La posición debe ser un número válido",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        dialog.add(panelInfo, BorderLayout.NORTH);
        dialog.add(panelFormulario, BorderLayout.CENTER);
        dialog.add(panelPuntos, BorderLayout.EAST);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Finaliza la carrera y calcula puntos de manera completa
     */
    private void finalizarCarrera() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this, "La carrera ya está finalizada", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (carreraSeleccionada.getParticipaciones().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay participaciones en esta carrera", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar que al menos un piloto tenga posición
        boolean hayResultados = carreraSeleccionada.getParticipaciones().stream()
                .anyMatch(p -> p.getPosicionFinal() > 0 || p.isAbandono());

        if (!hayResultados) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "No hay resultados registrados para esta carrera.\n" +
                            "¿Desea finalizar la carrera sin resultados?\n" +
                            "(Los pilotos no obtendrán puntos)",
                    "Finalizar sin resultados",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
        }

        StringBuilder resumenPrevio = new StringBuilder();
        resumenPrevio.append("RESUMEN ANTES DE FINALIZAR\n");
        resumenPrevio.append("==========================\n\n");
        resumenPrevio.append("Carrera: ").append(carreraSeleccionada.getNombre()).append("\n");
        resumenPrevio.append("Participantes: ").append(carreraSeleccionada.getParticipaciones().size()).append("\n\n");

        var participantesConPosicion = carreraSeleccionada.getParticipaciones().stream()
                .filter(p -> p.getPosicionFinal() > 0)
                .sorted((p1, p2) -> Integer.compare(p1.getPosicionFinal(), p2.getPosicionFinal()))
                .toList();

        if (!participantesConPosicion.isEmpty()) {
            resumenPrevio.append("Pilotos con posición registrada:\n");
            for (Participacion p : participantesConPosicion) {
                int puntosEstimados = modelo.SistemaPuntuacion.getPuntosPorPosicion(p.getPosicionFinal());
                if (p.isVueltaRapida() && p.getPosicionFinal() <= 10) {
                    puntosEstimados += 1;
                }
                resumenPrevio.append(String.format("  P%d - %s (%d puntos)%s\n",
                        p.getPosicionFinal(),
                        p.getPiloto().getNombreCompleto(),
                        puntosEstimados,
                        p.isVueltaRapida() ? " + VR" : ""));
            }
        }

        var abandonos = carreraSeleccionada.getParticipaciones().stream()
                .filter(Participacion::isAbandono)
                .toList();

        if (!abandonos.isEmpty()) {
            resumenPrevio.append("\nAbandonos registrados:\n");
            for (Participacion p : abandonos) {
                resumenPrevio.append(String.format("  DNF - %s\n", p.getPiloto().getNombreCompleto()));
            }
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                resumenPrevio.toString() +
                        "\n¿Está seguro que desea FINALIZAR esta carrera?\n" +
                        "Se asignarán los puntos definitivos según las posiciones registradas.\n" +
                        "Esta acción no se puede deshacer.",
                "🏁 Confirmar Finalización de Carrera",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // Validación adicional antes de finalizar
                if (carreraSeleccionada == null) {
                    throw new IllegalStateException("No hay carrera seleccionada para finalizar");
                }

                if (carreraSeleccionada.isFinalizada()) {
                    throw new IllegalStateException("La carrera ya está finalizada");
                }

                gestor.finalizarCarrera(carreraSeleccionada);

                actualizarTablaCarreras();
                actualizarTablaParticipaciones();

                java.util.List<String> resultados = gestor.obtenerResumenResultados(carreraSeleccionada);
                StringBuilder mensaje = new StringBuilder("🏁 ¡CARRERA FINALIZADA EXITOSAMENTE! 🏁\n\n");
                mensaje.append("🏆 RESULTADOS OFICIALES:\n");
                mensaje.append("========================\n");
                for (String resultado : resultados) {
                    mensaje.append(resultado).append("\n");
                }

                // Mostrar información adicional sobre puntos totales actualizados
                mensaje.append("\n💰 PUNTOS DEL CAMPEONATO ACTUALIZADOS\n");
                mensaje.append("====================================\n");
                for (Participacion p : carreraSeleccionada.getResultados()) {
                    mensaje.append(String.format("%s: %d puntos totales\n",
                            p.getPiloto().getNombreCompleto(),
                            p.getPiloto().getPuntosTotales()));
                }

                JTextArea textArea = new JTextArea(mensaje.toString());
                textArea.setEditable(false);
                textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 400));

                JOptionPane.showMessageDialog(this, scrollPane, "🏆 Carrera Finalizada",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al finalizar carrera: " + e.getMessage() +
                                "\n\nVerifique que las posiciones sean válidas y únicas.",
                        "❌ Error de Finalización",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Muestra los resultados finales de la carrera
     */
    private void verResultadosFinales() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder resultados = new StringBuilder();
        resultados.append("RESULTADOS FINALES\n");
        resultados.append("==================\n\n");
        resultados.append("Carrera: ").append(carreraSeleccionada.getNombre()).append("\n");
        resultados.append("Circuito: ").append(carreraSeleccionada.getCircuito().getNombre()).append("\n");
        resultados.append("Fecha: ")
                .append(carreraSeleccionada.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .append("\n");
        resultados.append("Estado: ").append(carreraSeleccionada.isFinalizada() ? "Finalizada" : "En curso")
                .append("\n\n");

        if (carreraSeleccionada.isFinalizada()) {
            List<Participacion> resultadosOrdenados = carreraSeleccionada.getResultados();

            resultados.append("CLASIFICACIÓN FINAL:\n");
            resultados.append("===================\n");

            for (int i = 0; i < resultadosOrdenados.size(); i++) {
                Participacion p = resultadosOrdenados.get(i);
                resultados.append(String.format("P%d - %s (%s) - %d puntos%s\n",
                        p.getPosicionFinal(),
                        p.getPiloto().getNombreCompleto(),
                        p.getPiloto().getEscuderia() != null ? p.getPiloto().getEscuderia().getNombre()
                                : "Sin escudería",
                        p.getPuntosObtenidos(),
                        p.isVueltaRapida() ? " + VR" : ""));
            }

            List<Participacion> abandonos = carreraSeleccionada.getParticipaciones().stream()
                    .filter(Participacion::isAbandono)
                    .toList();

            if (!abandonos.isEmpty()) {
                resultados.append("\nABANDONOS:\n");
                resultados.append("==========\n");
                for (Participacion p : abandonos) {
                    resultados.append(String.format("DNF - %s - %s\n",
                            p.getPiloto().getNombreCompleto(),
                            p.getMotivoAbandono()));
                }
            }
        } else {
            resultados.append("PARTICIPANTES INSCRITOS:\n");
            resultados.append("=======================\n");
            for (Participacion p : carreraSeleccionada.getParticipaciones()) {
                resultados.append(String.format("- %s (%s) - %s\n",
                        p.getPiloto().getNombreCompleto(),
                        p.getPiloto().getEscuderia() != null ? p.getPiloto().getEscuderia().getNombre()
                                : "Sin escudería",
                        p.getAuto().getModelo()));
            }
        }

        JTextArea textArea = new JTextArea(resultados.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 500));

        JOptionPane.showMessageDialog(this, scrollPane,
                "Resultados - " + carreraSeleccionada.getNombre(),
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Limpia el formulario de planificación
     */
    private void limpiarFormularioPlanificacion() {
        txtNombreCarrera.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        if (comboCircuitos.getItemCount() > 0) {
            comboCircuitos.setSelectedIndex(0);
        }
        tablaCarreras.clearSelection();
        carreraSeleccionada = null;
        actualizarTablaParticipaciones();

        pestanas.setTitleAt(1, "Inscribir Pilotos");
        pestanas.setTitleAt(2, "Registrar Resultados");
    }

    /**
     * Valida los campos de planificación de carrera
     */
    private void validarCamposCarrera() {
        if (txtNombreCarrera.getText().trim().isEmpty() ||
                txtFecha.getText().trim().isEmpty() ||
                txtHora.getText().trim().isEmpty()) {

            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        if (comboCircuitos.getSelectedItem() == null) {
            throw new IllegalArgumentException("Debe seleccionar un circuito");
        }
    }

    /**
     * Parsea fecha y hora del formulario y valida que sea en el futuro
     */
    private LocalDateTime parsearFechaHora() {
        try {
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

            String fechaStr = txtFecha.getText().trim();
            String horaStr = txtHora.getText().trim();

            LocalDateTime fechaHora = LocalDateTime.of(
                    java.time.LocalDate.parse(fechaStr, formatoFecha),
                    java.time.LocalTime.parse(horaStr, formatoHora));

            // Validar que la fecha y hora sea en el futuro
            LocalDateTime ahora = LocalDateTime.now();
            if (fechaHora.isBefore(ahora) || fechaHora.isEqual(ahora)) {
                throw new IllegalArgumentException(
                        "⚠️ La fecha y hora de la carrera debe ser posterior al momento actual.\n" +
                                "Fecha actual: " + ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n"
                                +
                                "Fecha seleccionada: "
                                + fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            }

            return fechaHora;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Formato de fecha u hora incorrecto. Use dd/MM/yyyy para fecha y HH:mm para hora");
        }
    }

    /**
     * Aplica estilo simple a botones
     */
    private void aplicarEstiloBoton(JButton boton) {
        // Tamaño estándar para todos los botones
        boton.setPreferredSize(new Dimension(120, 40));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));

        boton.setBackground(Color.WHITE);
        boton.setForeground(Color.BLACK);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setFocusPainted(true);
    }

    /**
     * Establece resultados automáticos para testing rápido
     */
    private void establecerResultadosAutomaticos() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this, "La carrera ya está finalizada", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        var participaciones = carreraSeleccionada.getParticipaciones();
        if (participaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay participaciones en esta carrera", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea establecer resultados automáticos para esta carrera?\n" +
                        "Esto asignará posiciones aleatorias a los participantes.",
                "Resultados Automáticos",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                java.util.List<Participacion> listaParticipaciones = new java.util.ArrayList<>(participaciones);
                java.util.Collections.shuffle(listaParticipaciones);

                for (int i = 0; i < listaParticipaciones.size(); i++) {
                    Participacion p = listaParticipaciones.get(i);
                    p.setPosicionFinal(i + 1);
                    controlador.ValidadorFormula1.actualizarPuntosParticipacion(p);
                }

                if (!listaParticipaciones.isEmpty()) {
                    listaParticipaciones.get(0).setVueltaRapida(true);
                    controlador.ValidadorFormula1.actualizarPuntosParticipacion(listaParticipaciones.get(0));
                }

                actualizarTablaParticipaciones();

                JOptionPane.showMessageDialog(this,
                        "✅ Resultados automáticos establecidos!\n" +
                                "Puede editarlos individualmente antes de finalizar la carrera.",
                        "Resultados Generados",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al establecer resultados: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTablaPilotosDisponibles(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        if (carreraSeleccionada == null) {
            return;
        }

        java.util.Set<Piloto> pilotosInscritos = carreraSeleccionada.getParticipaciones()
                .stream()
                .map(Participacion::getPiloto)
                .collect(java.util.stream.Collectors.toSet());

        for (Piloto piloto : gestor.getPilotos()) {
            if (!pilotosInscritos.contains(piloto)) {
                Object[] fila = {
                        piloto.getNombreCompleto(),
                        piloto.getEscuderia() != null ? piloto.getEscuderia().getNombre() : "Sin escudería",
                        piloto.getPuntosTotales() + " pts"
                };
                modelo.addRow(fila);
            }
        }
    }

    /**
     * Inscribe los pilotos seleccionados en la tabla de pilotos disponibles
     */
    private void inscribirPilotosSeleccionados(JTable tablaPilotosDisponibles) {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Seleccione una carrera primero en la pestaña 'Planificar Carreras'",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int[] filasSeleccionadas = tablaPilotosDisponibles.getSelectedRows();
        if (filasSeleccionadas.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Seleccione al menos un piloto de la lista",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this,
                    "❌ No se pueden inscribir pilotos en una carrera finalizada",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int inscripcionesExitosas = 0;
        int inscripcionesFallidas = 0;
        StringBuilder errores = new StringBuilder();

        for (int fila : filasSeleccionadas) {
            try {
                String nombrePiloto = (String) tablaPilotosDisponibles.getValueAt(fila, 0);
                Piloto piloto = gestor.getPilotos().stream()
                        .filter(p -> p.getNombreCompleto().equals(nombrePiloto))
                        .findFirst()
                        .orElse(null);

                if (piloto != null) {
                    Auto autoDisponible = buscarAutoDisponible();
                    if (autoDisponible != null) {
                        gestor.inscribirPilotoEnCarrera(piloto, autoDisponible, carreraSeleccionada);
                        inscripcionesExitosas++;
                    } else {
                        errores.append("- ").append(nombrePiloto).append(": No hay autos disponibles\n");
                        inscripcionesFallidas++;
                    }
                }
            } catch (Exception e) {
                String nombrePiloto = (String) tablaPilotosDisponibles.getValueAt(fila, 0);
                errores.append("- ").append(nombrePiloto).append(": ").append(e.getMessage()).append("\n");
                inscripcionesFallidas++;
            }
        }

        // Actualizar interfaces
        actualizarTablaParticipaciones();
        actualizarTablaCarreras();

        // Obtener el modelo de la tabla para actualizarlo
        DefaultTableModel modelo = (DefaultTableModel) tablaPilotosDisponibles.getModel();
        actualizarTablaPilotosDisponibles(modelo);

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("📊 Resultado de la inscripción masiva:\n\n");
        mensaje.append("✅ Inscripciones exitosas: ").append(inscripcionesExitosas).append("\n");
        mensaje.append("❌ Inscripciones fallidas: ").append(inscripcionesFallidas).append("\n");

        if (errores.length() > 0) {
            mensaje.append("\n🔍 Detalles de errores:\n").append(errores.toString());
        }

        JOptionPane.showMessageDialog(this, mensaje.toString(),
                "Resultado de Inscripción", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Selecciona automáticamente 10 pilotos e inscribe en la carrera
     */
    private void seleccionarYInscribirAutomaticamente(JTable tablaPilotosDisponibles) {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Seleccione una carrera primero en la pestaña 'Planificar Carreras'",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this,
                    "❌ No se pueden inscribir pilotos en una carrera finalizada",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar cuántos espacios disponibles hay
        int participantesActuales = carreraSeleccionada.getParticipaciones().size();
        int espaciosDisponibles = 20 - participantesActuales;

        if (espaciosDisponibles <= 0) {
            JOptionPane.showMessageDialog(this,
                    "🚫 La carrera ya está llena (20/20 participantes)",
                    "Carrera llena", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Determinar cuántos pilotos seleccionar (máximo 10, pero sin exceder espacios
        // disponibles)
        int pilotosASeleccionar = Math.min(10, espaciosDisponibles);
        int pilotosDisponibles = tablaPilotosDisponibles.getRowCount();

        if (pilotosDisponibles == 0) {
            JOptionPane.showMessageDialog(this,
                    "ℹ️ No hay pilotos disponibles para inscribir",
                    "Sin pilotos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        pilotosASeleccionar = Math.min(pilotosASeleccionar, pilotosDisponibles);

        int confirmacion = JOptionPane.showConfirmDialog(this,
                String.format("🎯 Selección automática:\n\n" +
                        "Se seleccionarán automáticamente %d pilotos para inscribir en la carrera.\n" +
                        "Espacios disponibles: %d/20\n" +
                        "Pilotos disponibles: %d\n\n" +
                        "¿Continuar con la selección automática?",
                        pilotosASeleccionar, espaciosDisponibles, pilotosDisponibles),
                "Confirmar Selección Automática",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        // Seleccionar las primeras N filas automáticamente
        tablaPilotosDisponibles.clearSelection();
        for (int i = 0; i < pilotosASeleccionar; i++) {
            tablaPilotosDisponibles.addRowSelectionInterval(i, i);
        }

        // Proceder con la inscripción
        inscribirPilotosSeleccionados(tablaPilotosDisponibles);
    }

    /**
     * Inscribe todos los pilotos disponibles automáticamente
     */
    private void inscribirTodosLosPilotos() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Seleccione una carrera primero en la pestaña 'Planificar Carreras'",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this,
                    "❌ No se pueden inscribir pilotos en una carrera finalizada",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.util.Set<Piloto> pilotosInscritos = carreraSeleccionada.getParticipaciones()
                .stream()
                .map(Participacion::getPiloto)
                .collect(java.util.stream.Collectors.toSet());

        java.util.List<Piloto> pilotosDisponibles = gestor.getPilotos().stream()
                .filter(p -> !pilotosInscritos.contains(p))
                .collect(java.util.stream.Collectors.toList());

        if (pilotosDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "ℹ️ Todos los pilotos disponibles ya están inscritos en esta carrera",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                String.format(
                        "¿Está seguro que desea inscribir todos los pilotos disponibles (%d pilotos) en esta carrera?\n\n"
                                +
                                "Se asignarán autos automáticamente.",
                        pilotosDisponibles.size()),
                "Confirmar Inscripción Masiva",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        int inscripcionesExitosas = 0;
        int inscripcionesFallidas = 0;
        StringBuilder errores = new StringBuilder();

        for (Piloto piloto : pilotosDisponibles) {
            try {
                Auto autoDisponible = buscarAutoDisponible();
                if (autoDisponible != null) {
                    gestor.inscribirPilotoEnCarrera(piloto, autoDisponible, carreraSeleccionada);
                    inscripcionesExitosas++;
                } else {
                    errores.append("- ").append(piloto.getNombreCompleto()).append(": No hay autos disponibles\n");
                    inscripcionesFallidas++;
                }
            } catch (Exception e) {
                errores.append("- ").append(piloto.getNombreCompleto()).append(": ").append(e.getMessage())
                        .append("\n");
                inscripcionesFallidas++;
            }
        }

        actualizarTablaParticipaciones();
        actualizarTablaCarreras();

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("🏁 Inscripción masiva completada:\n\n");
        mensaje.append("✅ Pilotos inscritos: ").append(inscripcionesExitosas).append("\n");
        mensaje.append("❌ Fallos en inscripción: ").append(inscripcionesFallidas).append("\n");

        if (errores.length() > 0) {
            mensaje.append("\n🔍 Detalles de errores:\n").append(errores.toString());
        }

        JOptionPane.showMessageDialog(this, mensaje.toString(),
                "Inscripción Masiva Completada", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Busca un auto disponible para asignación automática
     */
    private Auto buscarAutoDisponible() {
        if (carreraSeleccionada == null) {
            return null;
        }

        java.util.Set<Auto> autosAsignados = carreraSeleccionada.getParticipaciones()
                .stream()
                .map(Participacion::getAuto)
                .collect(java.util.stream.Collectors.toSet());

        return gestor.getAutos().stream()
                .filter(auto -> !autosAsignados.contains(auto))
                .findFirst()
                .orElse(null);
    }

    /**
     * Remueve todas las participaciones de la carrera
     */
    private void removerTodasLasParticipaciones() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Seleccione una carrera primero",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this,
                    "❌ No se puede modificar una carrera finalizada",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (carreraSeleccionada.getParticipaciones().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "ℹ️ No hay participaciones para remover",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                String.format(
                        "¿Está seguro que desea remover TODAS las inscripciones (%d participantes) de esta carrera?\n\n"
                                +
                                "Esta acción no se puede deshacer.",
                        carreraSeleccionada.getParticipaciones().size()),
                "Confirmar Eliminación Total",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            int participantesRemovidos = carreraSeleccionada.getParticipaciones().size();
            carreraSeleccionada.getParticipaciones().clear();

            actualizarTablaParticipaciones();
            actualizarTablaCarreras();

            JOptionPane.showMessageDialog(this,
                    String.format("✅ Se removieron %d participaciones exitosamente", participantesRemovidos),
                    "Participaciones Removidas", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Asigna autos automáticamente a pilotos que no tienen auto asignado
     */
    private void asignarAutosAutomaticamente() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Seleccione una carrera primero",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this,
                    "❌ No se puede modificar una carrera finalizada",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.util.List<Participacion> participacionesSinAuto = carreraSeleccionada.getParticipaciones()
                .stream()
                .filter(p -> p.getAuto() == null)
                .collect(java.util.stream.Collectors.toList());

        if (participacionesSinAuto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "ℹ️ Todas las participaciones ya tienen autos asignados",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int asignacionesExitosas = 0;
        int asignacionesFallidas = 0;
        StringBuilder errores = new StringBuilder();

        for (Participacion participacion : participacionesSinAuto) {
            Auto autoDisponible = buscarAutoDisponible();
            if (autoDisponible != null) {
                participacion.setAuto(autoDisponible);
                asignacionesExitosas++;
            } else {
                errores.append("- ").append(participacion.getPiloto().getNombreCompleto())
                        .append(": No hay autos disponibles\n");
                asignacionesFallidas++;
            }
        }

        actualizarTablaParticipaciones();

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("🔧 Asignación automática de autos completada:\n\n");
        mensaje.append("✅ Asignaciones exitosas: ").append(asignacionesExitosas).append("\n");
        mensaje.append("❌ Asignaciones fallidas: ").append(asignacionesFallidas).append("\n");

        if (errores.length() > 0) {
            mensaje.append("\n🔍 Detalles de errores:\n").append(errores.toString());
        }

        JOptionPane.showMessageDialog(this, mensaje.toString(),
                "Asignación Automática Completada", JOptionPane.INFORMATION_MESSAGE);
    }
}