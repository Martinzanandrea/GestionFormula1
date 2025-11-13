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
        panelFormulario.add(txtFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Hora (HH:mm):"), gbc);
        gbc.gridx = 1;
        txtHora = new JTextField(20);
        panelFormulario.add(txtHora, gbc);

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
     * Crea el panel de inscripción de pilotos
     */
    private JPanel crearPanelInscripcion() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel de información de la carrera
        JPanel panelInfoCarrera = new JPanel(new FlowLayout());
        panelInfoCarrera.setBorder(BorderFactory.createTitledBorder("Seleccione una carrera en la primera pestaña"));

        // Panel de inscripción
        JPanel panelInscripcionForm = new JPanel(new GridBagLayout());
        panelInscripcionForm.setBorder(BorderFactory.createTitledBorder("Inscribir Piloto"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JComboBox<Piloto> comboPilotos = new JComboBox<>();
        JComboBox<Auto> comboAutos = new JComboBox<>();

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInscripcionForm.add(new JLabel("Piloto:"), gbc);
        gbc.gridx = 1;
        panelInscripcionForm.add(comboPilotos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInscripcionForm.add(new JLabel("Auto:"), gbc);
        gbc.gridx = 1;
        panelInscripcionForm.add(comboAutos, gbc);

        JButton btnInscribir = new JButton("Inscribir Piloto");
        aplicarEstiloBoton(btnInscribir);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelInscripcionForm.add(btnInscribir, gbc);

        btnInscribir.addActionListener(e -> inscribirPiloto(comboPilotos, comboAutos));

        // Tabla de participaciones
        JPanel panelTablaParticipaciones = new JPanel(new BorderLayout());
        panelTablaParticipaciones.setBorder(BorderFactory.createTitledBorder("🏎️ Participantes de la Carrera"));

        String[] columnasParticipaciones = { "Pos.", "Piloto", "Escudería", "Auto", "Puntos", "Detalles" };
        modeloTablaParticipaciones = new DefaultTableModel(columnasParticipaciones, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaParticipaciones = new JTable(modeloTablaParticipaciones);
        tablaParticipaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollParticipaciones = new JScrollPane(tablaParticipaciones);
        scrollParticipaciones.setPreferredSize(new Dimension(600, 250));

        panelTablaParticipaciones.add(scrollParticipaciones, BorderLayout.CENTER);

        // Botones de gestión de participaciones
        JPanel panelBotonesPartic = new JPanel(new FlowLayout());
        JButton btnRemoverParticipacion = new JButton("Remover Inscripción");
        JButton btnActualizarInscritos = new JButton("Actualizar Lista");

        // Aplicar estilos estándar
        aplicarEstiloBoton(btnRemoverParticipacion);
        aplicarEstiloBoton(btnActualizarInscritos);

        btnRemoverParticipacion.addActionListener(e -> removerParticipacion());
        btnActualizarInscritos.addActionListener(e -> {
            cargarPilotosYAutos(comboPilotos, comboAutos);
            actualizarTablaParticipaciones();
        });

        panelBotonesPartic.add(btnRemoverParticipacion);
        panelBotonesPartic.add(btnActualizarInscritos);

        panel.add(panelInfoCarrera, BorderLayout.NORTH);
        panel.add(panelInscripcionForm, BorderLayout.WEST);
        panel.add(panelTablaParticipaciones, BorderLayout.CENTER);
        panel.add(panelBotonesPartic, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarPilotosYAutos(comboPilotos, comboAutos);

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

        // Agregar doble clic para editar resultados
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
        setSize(1000, 750);
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
            // Obtener participaciones ordenadas por posición
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

            for (Participacion participacion : participaciones) {
                String posicion;
                String detalles = "";

                if (participacion.isAbandono()) {
                    posicion = "DNF";
                    detalles = "Abandono: "
                            + (participacion.getMotivoAbandono() != null ? participacion.getMotivoAbandono()
                                    : "No especificado");
                } else if (participacion.getPosicionFinal() > 0) {
                    posicion = "P" + participacion.getPosicionFinal();
                    if (participacion.isVueltaRapida()) {
                        detalles = "Vuelta más rápida";
                    }
                    if (participacion.getPosicionFinal() == 1) {
                        detalles += " 🏆 GANADOR";
                    } else if (participacion.getPosicionFinal() == 2) {
                        detalles += " 🥈 Segundo";
                    } else if (participacion.getPosicionFinal() == 3) {
                        detalles += " 🥉 Tercero";
                    }
                } else {
                    posicion = "-";
                    detalles = carreraSeleccionada.isFinalizada() ? "No clasificado" : "Sin resultado";
                }

                Object[] fila = {
                        posicion,
                        participacion.getPiloto().getNombreCompleto(),
                        participacion.getPiloto().getEscuderia() != null
                                ? participacion.getPiloto().getEscuderia().getNombre()
                                : "Sin escudería",
                        participacion.getAuto().getModelo(),
                        participacion.getPuntosObtenidos() + " pts",
                        detalles
                };
                modeloTablaParticipaciones.addRow(fila);
            }
        }
    }

    /**
     * Carga la carrera seleccionada
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

                actualizarTablaParticipaciones();

                // Actualizar título de la pestaña de inscripción
                pestanas.setTitleAt(1, "Inscribir Pilotos - " + carreraSeleccionada.getNombre());
                pestanas.setTitleAt(2, "Resultados - " + carreraSeleccionada.getNombre());
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

            GranPremio nuevaCarrera = new GranPremio(nombre, fechaHora, circuito);
            gestor.registrarGranPremio(nuevaCarrera);

            actualizarTablaCarreras();
            limpiarFormularioPlanificacion();

            JOptionPane.showMessageDialog(this, "Carrera planificada exitosamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al planificar carrera: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica la carrera seleccionada
     */
    private void modificarCarrera() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera para modificar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this, "No se puede modificar una carrera finalizada", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            validarCamposCarrera();

            carreraSeleccionada.setNombre(txtNombreCarrera.getText().trim());
            carreraSeleccionada.setCircuito((Circuito) comboCircuitos.getSelectedItem());
            carreraSeleccionada.setFechaHora(parsearFechaHora());

            actualizarTablaCarreras();

            JOptionPane.showMessageDialog(this, "Carrera modificada exitosamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar carrera: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina la carrera seleccionada
     */
    private void eliminarCarrera() {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera para eliminar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar esta carrera?\nSe perderán todas las inscripciones.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            gestor.getGrandesPremios().remove(carreraSeleccionada);
            carreraSeleccionada = null;

            actualizarTablaCarreras();
            actualizarTablaParticipaciones();
            limpiarFormularioPlanificacion();

            JOptionPane.showMessageDialog(this, "Carrera eliminada exitosamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Inscribe un piloto en la carrera seleccionada
     */
    private void inscribirPiloto(JComboBox<Piloto> comboPilotos, JComboBox<Auto> comboAutos) {
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera primero", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carreraSeleccionada.isFinalizada()) {
            JOptionPane.showMessageDialog(this, "No se puede inscribir pilotos en una carrera finalizada", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Piloto piloto = (Piloto) comboPilotos.getSelectedItem();
            Auto auto = (Auto) comboAutos.getSelectedItem();

            if (piloto == null || auto == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un piloto y un auto", "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            gestor.inscribirPilotoEnCarrera(piloto, auto, carreraSeleccionada);

            actualizarTablaParticipaciones();
            actualizarTablaCarreras();

            JOptionPane.showMessageDialog(this, "Piloto inscrito exitosamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al inscribir piloto: " + e.getMessage(), "Error",
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

                    // Actualizar puntos automáticamente usando el validador
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
                actualizarTablaCarreras(); // Actualizar también la tabla de carreras

                // Mostrar mensaje de confirmación con los puntos obtenidos
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

        // Mostrar resumen antes de finalizar
        StringBuilder resumenPrevio = new StringBuilder();
        resumenPrevio.append("RESUMEN ANTES DE FINALIZAR\n");
        resumenPrevio.append("==========================\n\n");
        resumenPrevio.append("Carrera: ").append(carreraSeleccionada.getNombre()).append("\n");
        resumenPrevio.append("Participantes: ").append(carreraSeleccionada.getParticipaciones().size()).append("\n\n");

        // Mostrar participantes con posiciones
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
                // Usar el método del gestor que valida y actualiza correctamente
                gestor.finalizarCarrera(carreraSeleccionada);

                // Actualizar tablas
                actualizarTablaCarreras();
                actualizarTablaParticipaciones();

                // Mostrar resumen final de resultados
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

            // Mostrar abandonos
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
     * Parsea fecha y hora del formulario
     */
    private LocalDateTime parsearFechaHora() {
        try {
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

            String fechaStr = txtFecha.getText().trim();
            String horaStr = txtHora.getText().trim();

            return LocalDateTime.of(
                    java.time.LocalDate.parse(fechaStr, formatoFecha),
                    java.time.LocalTime.parse(horaStr, formatoHora));

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

        // Interfaz simple blanco y negro
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
                // Convertir a lista y mezclar
                java.util.List<Participacion> listaParticipaciones = new java.util.ArrayList<>(participaciones);
                java.util.Collections.shuffle(listaParticipaciones);

                // Asignar posiciones
                for (int i = 0; i < listaParticipaciones.size(); i++) {
                    Participacion p = listaParticipaciones.get(i);
                    p.setPosicionFinal(i + 1);
                    controlador.ValidadorFormula1.actualizarPuntosParticipacion(p);
                }

                // Asignar vuelta rápida al ganador
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
}