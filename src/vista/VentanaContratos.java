package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

/**
 * Ventana dedicada a la gestión completa de contratos entre pilotos y
 * escuderías
 */
public class VentanaContratos extends JFrame {
    private GestorFormula1 gestor;
    private JTable tablaContratos;
    private DefaultTableModel modeloTablaContratos;
    private JTable tablaPilotosLibres;
    private DefaultTableModel modeloTablaPilotosLibres;
    private JTextArea areaDetallesContrato;
    private JComboBox<Escuderia> comboFiltroEscuderia;
    private JCheckBox chkMostrarExpirados;
    private JLabel lblEstadisticas;

    /**
     * Constructor de la ventana de contratos
     */
    public VentanaContratos(GestorFormula1 gestor) {
        this.gestor = gestor;
        inicializarComponentes();
        configurarVentana();
        cargarDatos();
        configurarEventos();
    }

    /**
     * Inicializa todos los componentes de la ventana
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(248, 249, 250));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel superior: título y filtros
        JPanel panelSuperior = crearPanelSuperior();

        // Panel central: dividido en tres secciones
        JPanel panelCentral = crearPanelCentral();

        // Panel inferior: botones de acción
        JPanel panelInferior = crearPanelBotones();

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Crea el panel superior con título y filtros
     */
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Título
        JLabel titulo = new JLabel("GESTIÓN DE CONTRATOS F1");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(52, 58, 64));

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBackground(new Color(248, 249, 250));

        JLabel lblFiltro = new JLabel("Filtrar por escudería:");
        lblFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        comboFiltroEscuderia = new JComboBox<>();
        comboFiltroEscuderia.setPreferredSize(new Dimension(200, 30));
        comboFiltroEscuderia.addItem(null); // Opción "Todas"

        chkMostrarExpirados = new JCheckBox("Mostrar contratos expirados", false);
        chkMostrarExpirados.setBackground(new Color(248, 249, 250));
        chkMostrarExpirados.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        panelFiltros.add(lblFiltro);
        panelFiltros.add(comboFiltroEscuderia);
        panelFiltros.add(Box.createHorizontalStrut(20));
        panelFiltros.add(chkMostrarExpirados);

        panel.add(titulo, BorderLayout.WEST);
        panel.add(panelFiltros, BorderLayout.EAST);

        return panel;
    }

    /**
     * Crea el panel central con las tres secciones principales
     */
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));

        // Panel izquierdo: tabla de contratos
        JPanel panelContratos = crearPanelTablaContratos();

        // Panel central: detalles del contrato seleccionado
        JPanel panelDetalles = crearPanelDetalles();

        // Panel derecho: pilotos libres
        JPanel panelPilotosLibres = crearPanelPilotosLibres();

        // Usar JSplitPane para permitir redimensionamiento
        JSplitPane splitIzquierda = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelContratos, panelDetalles);
        splitIzquierda.setDividerLocation(400);
        splitIzquierda.setResizeWeight(0.4);

        JSplitPane splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitIzquierda, panelPilotosLibres);
        splitPrincipal.setDividerLocation(700);
        splitPrincipal.setResizeWeight(0.7);

        panel.add(splitPrincipal, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de la tabla de contratos
     */
    private JPanel crearPanelTablaContratos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Contratos Activos"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Configurar tabla de contratos
        String[] columnasContratos = { "Piloto", "DNI", "Escudería", "Desde", "Hasta", "Estado" };
        modeloTablaContratos = new DefaultTableModel(columnasContratos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaContratos = new JTable(modeloTablaContratos);
        configurarTabla(tablaContratos);

        JScrollPane scrollContratos = new JScrollPane(tablaContratos);
        scrollContratos.setPreferredSize(new Dimension(450, 300));

        panel.add(scrollContratos, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de detalles del contrato
     */
    private JPanel crearPanelDetalles() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Detalles del Contrato"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        areaDetallesContrato = new JTextArea();
        areaDetallesContrato.setEditable(false);
        areaDetallesContrato.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaDetallesContrato.setBackground(new Color(248, 249, 250));
        areaDetallesContrato.setText("Selecciona un contrato para ver los detalles");
        areaDetallesContrato.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollDetalles = new JScrollPane(areaDetallesContrato);
        scrollDetalles.setPreferredSize(new Dimension(250, 300));

        // Panel de estadísticas generales
        lblEstadisticas = new JLabel("<html><center>Cargando estadísticas...</center></html>");
        lblEstadisticas.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEstadisticas.setHorizontalAlignment(JLabel.CENTER);
        lblEstadisticas.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        panel.add(scrollDetalles, BorderLayout.CENTER);
        panel.add(lblEstadisticas, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel de pilotos libres
     */
    private JPanel crearPanelPilotosLibres() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Pilotos Libres"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Configurar tabla de pilotos libres
        String[] columnasPilotosLibres = { "Piloto", "DNI", "Última Escudería", "Contrato Finalizado" };
        modeloTablaPilotosLibres = new DefaultTableModel(columnasPilotosLibres, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPilotosLibres = new JTable(modeloTablaPilotosLibres);
        configurarTabla(tablaPilotosLibres);

        JScrollPane scrollPilotosLibres = new JScrollPane(tablaPilotosLibres);
        scrollPilotosLibres.setPreferredSize(new Dimension(300, 300));

        panel.add(scrollPilotosLibres, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Configura el estilo de las tablas
     */
    private void configurarTabla(JTable tabla) {
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(52, 58, 64));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setSelectionBackground(new Color(0, 123, 255));
        tabla.setSelectionForeground(Color.WHITE);

        // Alternar colores de filas
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(248, 249, 250));
                    }
                }
                return c;
            }
        });

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Crea el panel de botones
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(248, 249, 250));

        JButton btnNuevoContrato = crearBoton("NUEVO CONTRATO");
        JButton btnFinalizarContrato = crearBoton("FINALIZAR CONTRATO");
        JButton btnRenovarContrato = crearBoton("RENOVAR CONTRATO");
        JButton btnActualizar = crearBoton("ACTUALIZAR DATOS");
        JButton btnCerrar = crearBoton("CERRAR");

        panel.add(btnNuevoContrato);
        panel.add(btnFinalizarContrato);
        panel.add(btnRenovarContrato);
        panel.add(btnActualizar);
        panel.add(btnCerrar);

        return panel;
    }

    /**
     * Crea un botón con estilo estándar
     */
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(140, 40));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setBackground(Color.WHITE);
        boton.setForeground(Color.BLACK);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setFocusPainted(false);

        return boton;
    }

    /**
     * Configura la ventana principal
     */
    private void configurarVentana() {
        setTitle("Gestión de Contratos - Sistema F1");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    /**
     * Carga los datos en las tablas
     */
    private void cargarDatos() {
        cargarEscuderiasEnFiltro();
        actualizarTablas();
        actualizarEstadisticas();
    }

    /**
     * Carga las escuderías en el combo de filtro
     */
    private void cargarEscuderiasEnFiltro() {
        comboFiltroEscuderia.removeAllItems();
        comboFiltroEscuderia.addItem(null); // Opción "Todas"

        for (Escuderia escuderia : gestor.getEscuderias()) {
            comboFiltroEscuderia.addItem(escuderia);
        }
    }

    /**
     * Actualiza ambas tablas con los datos actuales
     */
    private void actualizarTablas() {
        // Actualizar contratos primero
        gestor.actualizarEscuderiasSegunContratos();

        // Limpiar tablas
        modeloTablaContratos.setRowCount(0);
        modeloTablaPilotosLibres.setRowCount(0);

        // Cargar contratos
        List<PilotoEscuderia> contratos = gestor.getPilotoEscuderias();
        Escuderia filtroEscuderia = (Escuderia) comboFiltroEscuderia.getSelectedItem();
        boolean mostrarExpirados = chkMostrarExpirados.isSelected();

        for (PilotoEscuderia contrato : contratos) {
            // Aplicar filtros
            if (filtroEscuderia != null && !contrato.getEscuderia().equals(filtroEscuderia)) {
                continue;
            }

            if (!mostrarExpirados && contrato.haExpirado()) {
                continue;
            }

            Object[] fila = {
                    contrato.getPiloto().getNombre() + " " + contrato.getPiloto().getApellido(),
                    contrato.getPiloto().getDni(),
                    contrato.getEscuderia().getNombre(),
                    contrato.getDesdeFecha(),
                    contrato.getHastaFecha() != null ? contrato.getHastaFecha() : "Indefinido",
                    contrato.estaVigente() ? "VIGENTE" : "EXPIRADO"
            };
            modeloTablaContratos.addRow(fila);
        }

        // Cargar pilotos libres
        List<Piloto> pilotosLibres = gestor.getPilotosLibres();
        for (Piloto piloto : pilotosLibres) {
            List<PilotoEscuderia> historial = gestor.getHistorialPiloto(piloto);

            String ultimaEscuderia = "Nunca tuvo contrato";
            String fechaFinalizacion = "N/A";

            if (!historial.isEmpty()) {
                PilotoEscuderia ultimoContrato = historial.get(historial.size() - 1);
                ultimaEscuderia = ultimoContrato.getEscuderia().getNombre();
                fechaFinalizacion = ultimoContrato.getHastaFecha() != null ? ultimoContrato.getHastaFecha()
                        : "Finalizado";
            }

            Object[] fila = {
                    piloto.getNombre() + " " + piloto.getApellido(),
                    piloto.getDni(),
                    ultimaEscuderia,
                    fechaFinalizacion
            };
            modeloTablaPilotosLibres.addRow(fila);
        }
    }

    /**
     * Actualiza las estadísticas generales
     */
    private void actualizarEstadisticas() {
        List<PilotoEscuderia> todosContratos = gestor.getPilotoEscuderias();
        long contratosVigentes = todosContratos.stream().mapToLong(c -> c.estaVigente() ? 1 : 0).sum();
        long contratosExpirados = todosContratos.size() - contratosVigentes;
        int pilotosLibres = gestor.getPilotosLibres().size();

        String stats = String.format(
                "<html><center><b>ESTADÍSTICAS</b><br>" +
                        "Contratos vigentes: %d<br>" +
                        "Contratos expirados: %d<br>" +
                        "Pilotos libres: %d</center></html>",
                contratosVigentes, contratosExpirados, pilotosLibres);

        lblEstadisticas.setText(stats);
    }

    /**
     * Configura los eventos de los componentes
     */
    private void configurarEventos() {
        // Evento de selección en tabla de contratos
        tablaContratos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetallesContrato();
            }
        });

        // Eventos de filtros
        comboFiltroEscuderia.addActionListener(e -> actualizarTablas());
        chkMostrarExpirados.addActionListener(e -> actualizarTablas());

        // Eventos de botones
        configurarEventosBotones();
    }

    /**
     * Configura los eventos de los botones
     */
    private void configurarEventosBotones() {
        Component[] componentesPanel = getContentPane().getComponents();
        if (componentesPanel.length > 0 && componentesPanel[0] instanceof JPanel) {
            JPanel panelPrincipal = (JPanel) componentesPanel[0];
            Component[] componentesInteriores = panelPrincipal.getComponents();
            if (componentesInteriores.length > 2 && componentesInteriores[2] instanceof JPanel) {
                JPanel panelBotones = (JPanel) componentesInteriores[2];
                Component[] componentes = panelBotones.getComponents();

                for (Component comp : componentes) {
                    if (comp instanceof JButton) {
                        JButton btn = (JButton) comp;
                        String texto = btn.getText();

                        switch (texto) {
                            case "NUEVO CONTRATO":
                                btn.addActionListener(e -> mostrarDialogoNuevoContrato());
                                break;
                            case "FINALIZAR CONTRATO":
                                btn.addActionListener(e -> finalizarContratoSeleccionado());
                                break;
                            case "RENOVAR CONTRATO":
                                btn.addActionListener(e -> renovarContratoSeleccionado());
                                break;
                            case "ACTUALIZAR DATOS":
                                btn.addActionListener(e -> {
                                    cargarDatos();
                                    JOptionPane.showMessageDialog(this, "Datos actualizados", "Información",
                                            JOptionPane.INFORMATION_MESSAGE);
                                });
                                break;
                            case "CERRAR":
                                btn.addActionListener(e -> dispose());
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Muestra los detalles del contrato seleccionado
     */
    private void mostrarDetallesContrato() {
        int filaSeleccionada = tablaContratos.getSelectedRow();
        if (filaSeleccionada == -1) {
            areaDetallesContrato.setText("Selecciona un contrato para ver los detalles");
            return;
        }

        String nombrePiloto = (String) modeloTablaContratos.getValueAt(filaSeleccionada, 0);
        String dni = (String) modeloTablaContratos.getValueAt(filaSeleccionada, 1);
        String nombreEscuderia = (String) modeloTablaContratos.getValueAt(filaSeleccionada, 2);
        String desde = (String) modeloTablaContratos.getValueAt(filaSeleccionada, 3);
        String hasta = (String) modeloTablaContratos.getValueAt(filaSeleccionada, 4);
        String estado = (String) modeloTablaContratos.getValueAt(filaSeleccionada, 5);

        StringBuilder detalles = new StringBuilder();
        detalles.append("═══ DETALLES DEL CONTRATO ═══\n\n");
        detalles.append("PILOTO:\n");
        detalles.append("  Nombre: ").append(nombrePiloto).append("\n");
        detalles.append("  DNI: ").append(dni).append("\n\n");
        detalles.append("ESCUDERÍA:\n");
        detalles.append("  Nombre: ").append(nombreEscuderia).append("\n\n");
        detalles.append("CONTRATO:\n");
        detalles.append("  Inicio: ").append(desde).append("\n");
        detalles.append("  Fin: ").append(hasta).append("\n");
        detalles.append("  Estado: ").append(estado).append("\n\n");

        if ("VIGENTE".equals(estado)) {
            detalles.append("✓ Este contrato está actualmente vigente\n");
            detalles.append("  El piloto puede participar en carreras\n");
            detalles.append("  con esta escudería.\n");
        } else {
            detalles.append("✗ Este contrato ha expirado\n");
            detalles.append("  El piloto está libre para firmar\n");
            detalles.append("  con otra escudería.\n");
        }

        areaDetallesContrato.setText(detalles.toString());
    }

    /**
     * Muestra el diálogo para crear un nuevo contrato
     */
    private void mostrarDialogoNuevoContrato() {
        List<Piloto> pilotosLibres = gestor.getPilotosLibres();
        if (pilotosLibres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pilotos libres disponibles",
                    "Sin pilotos disponibles", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog dialogo = new JDialog(this, "Nuevo Contrato", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(this);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Selección de piloto
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Piloto disponible:"), gbc);

        JComboBox<Piloto> comboPilotos = new JComboBox<>(pilotosLibres.toArray(new Piloto[0]));
        comboPilotos.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        panelFormulario.add(comboPilotos, gbc);

        // Selección de escudería
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Escudería:"), gbc);

        JComboBox<Escuderia> comboEscuderias = new JComboBox<>(gestor.getEscuderias().toArray(new Escuderia[0]));
        comboEscuderias.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        panelFormulario.add(comboEscuderias, gbc);

        // Fecha de inicio
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Fecha de inicio:"), gbc);

        JTextField txtFechaInicio = new JTextField("2025-11-13");
        txtFechaInicio.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        panelFormulario.add(txtFechaInicio, gbc);

        // Fecha de fin
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Fecha de fin (opcional):"), gbc);

        JTextField txtFechaFin = new JTextField();
        txtFechaFin.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        panelFormulario.add(txtFechaFin, gbc);

        // Nota informativa
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JLabel lblNota = new JLabel("<html><i>Dejar fecha de fin vacía para contrato indefinido</i></html>");
        lblNota.setForeground(Color.GRAY);
        panelFormulario.add(lblNota, gbc);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnCrear = crearBoton("CREAR CONTRATO");
        JButton btnCancelar = crearBoton("CANCELAR");

        btnCrear.addActionListener(e -> {
            try {
                Piloto piloto = (Piloto) comboPilotos.getSelectedItem();
                Escuderia escuderia = (Escuderia) comboEscuderias.getSelectedItem();
                String fechaInicio = txtFechaInicio.getText().trim();
                String fechaFin = txtFechaFin.getText().trim();

                if (fechaFin.isEmpty())
                    fechaFin = null;

                gestor.asignarPilotoAEscuderia(piloto, escuderia, fechaInicio, fechaFin);

                dialogo.dispose();
                cargarDatos();
                JOptionPane.showMessageDialog(this, "Contrato creado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnCrear);
        panelBotones.add(btnCancelar);

        dialogo.add(panelFormulario, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    /**
     * Finaliza el contrato seleccionado
     */
    private void finalizarContratoSeleccionado() {
        int filaSeleccionada = tablaContratos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un contrato para finalizar",
                    "Ningún contrato seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String estado = (String) modeloTablaContratos.getValueAt(filaSeleccionada, 5);
        if ("EXPIRADO".equals(estado)) {
            JOptionPane.showMessageDialog(this, "Este contrato ya está expirado",
                    "Contrato ya expirado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fechaFin = JOptionPane.showInputDialog(this,
                "Ingrese la fecha de finalización (YYYY-MM-DD):", "2025-11-13");

        if (fechaFin != null && !fechaFin.trim().isEmpty()) {
            try {
                String dni = (String) modeloTablaContratos.getValueAt(filaSeleccionada, 1);
                Piloto piloto = gestor.getPilotos().stream()
                        .filter(p -> p.getDni().equals(dni))
                        .findFirst().orElse(null);

                if (piloto != null) {
                    gestor.finalizarRelacionActivaPiloto(piloto, fechaFin.trim());
                    cargarDatos();
                    JOptionPane.showMessageDialog(this, "Contrato finalizado exitosamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Renueva el contrato seleccionado
     */
    private void renovarContratoSeleccionado() {
        int filaSeleccionada = tablaContratos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un contrato para renovar",
                    "Ningún contrato seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Función de renovación en desarrollo.\nPor ahora, finaliza el contrato actual y crea uno nuevo.",
                "Función en desarrollo", JOptionPane.INFORMATION_MESSAGE);
    }
}