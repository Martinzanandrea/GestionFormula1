package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Ventana moderna e intuitiva para la gestión completa de escuderías
 * Incluye gestión de pilotos, mecánicos y autos asociados
 */
public class VentanaEscuderias extends JFrame {
    private GestorFormula1 gestor;
    private JTable tablaEscuderias;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre;
    private JComboBox<Pais> comboPaises;
    private JList<Piloto> listaPilotos, listaPilotosDisponibles;
    private JList<Mecanico> listaMecanicos, listaMecanicosDisponibles;
    private JList<Auto> listaAutos, listaAutosDisponibles;
    private JLabel lblEstadisticasEscuderia;
    private Escuderia escuderiaSeleccionada;
    private JButton btnAgregar, btnModificar, btnEliminar, btnLimpiar;
    private DefaultListModel<Piloto> modeloPilotos, modeloPilotosDisponibles;
    private DefaultListModel<Mecanico> modeloMecanicos, modeloMecanicosDisponibles;
    private DefaultListModel<Auto> modeloAutos, modeloAutosDisponibles;

    /**
     * Constructor de la ventana de escuderías
     */
    public VentanaEscuderias(GestorFormula1 gestor) {
        this.gestor = gestor;
        inicializarComponentes();
        configurarVentana();
        cargarDatos();
        configurarEventos();
    }

    /**
     * Inicializa los componentes con diseño moderno
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        // Panel principal con fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(248, 249, 250));

        // Crear paneles principales
        JPanel panelIzquierdo = crearPanelFormulario();
        JPanel panelCentro = crearPanelTabla();
        JPanel panelDerecho = crearPanelGestion();
        JPanel panelInferior = crearPanelBotones();

        // Layout con proporciones
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelDerecho, BorderLayout.EAST);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Crea el panel de formulario básico
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(600, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Título del formulario
        JLabel titulo = new JLabel("DATOS DE LA ESCUDERÍA");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(52, 58, 64));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel de formulario
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurar campos de entrada
        txtNombre = crearCampoTexto();
        comboPaises = new JComboBox<>();
        comboPaises.setPreferredSize(new Dimension(450, 35));
        comboPaises.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboPaises.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        // Agregar campos al formulario
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre de escudería
        gbc.gridy = 0;
        gbc.gridx = 0;
        JLabel lblNombre = crearEtiqueta("Nombre:");
        panelCampos.add(lblNombre, gbc);
        gbc.gridx = 1;
        panelCampos.add(txtNombre, gbc);

        // País
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel lblPais = crearEtiqueta("País:");
        panelCampos.add(lblPais, gbc);
        gbc.gridx = 1;
        panelCampos.add(comboPaises, gbc);

        // Panel de botones del formulario
        JPanel panelBotonesForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelBotonesForm.setBackground(Color.WHITE);

        btnAgregar = crearBoton("AGREGAR");
        btnModificar = crearBoton("MODIFICAR");
        btnLimpiar = crearBoton("LIMPIAR");

        panelBotonesForm.add(btnAgregar);
        panelBotonesForm.add(btnModificar);
        panelBotonesForm.add(btnLimpiar);

        // Panel de estadísticas
        lblEstadisticasEscuderia = new JLabel(
                "<html><center>Selecciona una escudería<br>para ver sus estadísticas</center></html>");
        lblEstadisticasEscuderia.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEstadisticasEscuderia.setForeground(new Color(108, 117, 125));
        lblEstadisticasEscuderia.setHorizontalAlignment(JLabel.CENTER);
        lblEstadisticasEscuderia.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelCampos, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(Color.WHITE);
        panelSur.add(panelBotonesForm, BorderLayout.NORTH);
        panelSur.add(lblEstadisticasEscuderia, BorderLayout.CENTER);

        panel.add(panelSur, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel de tabla
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));

        // Título de la sección
        JLabel titulo = new JLabel("ESCUDERÍAS REGISTRADAS");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(new Color(52, 58, 64));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Configurar tabla
        String[] columnas = { "Nombre", "País", "Pilotos", "Mecánicos", "Autos", "Puntos Total" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEscuderias = new JTable(modeloTabla);
        configurarTabla();

        JScrollPane scrollPane = new JScrollPane(tablaEscuderias);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de gestión de personal y autos
     */
    private JPanel crearPanelGestion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(350, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Título
        JLabel titulo = new JLabel("GESTIÓN DE ESCUDERÍA");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(new Color(52, 58, 64));

        // Panel con pestañas
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Pestaña de pilotos
        JPanel panelPilotos = crearPanelPilotos();
        pestanas.addTab("Pilotos", panelPilotos);

        // Pestaña de mecánicos
        JPanel panelMecanicos = crearPanelMecanicos();
        pestanas.addTab("Mecánicos", panelMecanicos);

        // Pestaña de autos
        JPanel panelAutos = crearPanelAutos();
        pestanas.addTab("Autos", panelAutos);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(pestanas, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de gestión de pilotos
     */
    private JPanel crearPanelPilotos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de pilotos asignados
        JPanel panelAsignados = new JPanel(new BorderLayout());
        panelAsignados.setBorder(BorderFactory.createTitledBorder("Pilotos de la Escudería"));

        modeloPilotos = new DefaultListModel<>();
        listaPilotos = new JList<>(modeloPilotos);
        listaPilotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPilotos = new JScrollPane(listaPilotos);
        scrollPilotos.setPreferredSize(new Dimension(150, 100));
        panelAsignados.add(scrollPilotos, BorderLayout.CENTER);

        // Panel de pilotos disponibles
        JPanel panelDisponibles = new JPanel(new BorderLayout());
        panelDisponibles.setBorder(BorderFactory.createTitledBorder("Pilotos Disponibles"));

        modeloPilotosDisponibles = new DefaultListModel<>();
        listaPilotosDisponibles = new JList<>(modeloPilotosDisponibles);
        listaPilotosDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollDisponibles = new JScrollPane(listaPilotosDisponibles);
        scrollDisponibles.setPreferredSize(new Dimension(150, 100));
        panelDisponibles.add(scrollDisponibles, BorderLayout.CENTER);

        // Botones de gestión
        JPanel panelBotonesPilotos = new JPanel(new FlowLayout());
        JButton btnAsignarPiloto = new JButton("← Asignar");
        JButton btnRemoverPiloto = new JButton("Remover →");

        btnAsignarPiloto.addActionListener(e -> asignarPiloto());
        btnRemoverPiloto.addActionListener(e -> removerPiloto());

        panelBotonesPilotos.add(btnAsignarPiloto);
        panelBotonesPilotos.add(btnRemoverPiloto);

        panel.add(panelAsignados, BorderLayout.NORTH);
        panel.add(panelBotonesPilotos, BorderLayout.CENTER);
        panel.add(panelDisponibles, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel de gestión de mecánicos
     */
    private JPanel crearPanelMecanicos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de mecánicos asignados
        JPanel panelAsignados = new JPanel(new BorderLayout());
        panelAsignados.setBorder(BorderFactory.createTitledBorder("Mecánicos de la Escudería"));

        modeloMecanicos = new DefaultListModel<>();
        listaMecanicos = new JList<>(modeloMecanicos);
        listaMecanicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollMecanicos = new JScrollPane(listaMecanicos);
        scrollMecanicos.setPreferredSize(new Dimension(150, 100));
        panelAsignados.add(scrollMecanicos, BorderLayout.CENTER);

        // Panel de mecánicos disponibles
        JPanel panelDisponibles = new JPanel(new BorderLayout());
        panelDisponibles.setBorder(BorderFactory.createTitledBorder("Mecánicos Disponibles"));

        modeloMecanicosDisponibles = new DefaultListModel<>();
        listaMecanicosDisponibles = new JList<>(modeloMecanicosDisponibles);
        listaMecanicosDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollDisponibles = new JScrollPane(listaMecanicosDisponibles);
        scrollDisponibles.setPreferredSize(new Dimension(150, 100));
        panelDisponibles.add(scrollDisponibles, BorderLayout.CENTER);

        // Botones de gestión
        JPanel panelBotonesMecanicos = new JPanel(new FlowLayout());
        JButton btnAsignarMecanico = new JButton("← Asignar");
        JButton btnRemoverMecanico = new JButton("Remover →");

        btnAsignarMecanico.addActionListener(e -> asignarMecanico());
        btnRemoverMecanico.addActionListener(e -> removerMecanico());

        panelBotonesMecanicos.add(btnAsignarMecanico);
        panelBotonesMecanicos.add(btnRemoverMecanico);

        panel.add(panelAsignados, BorderLayout.NORTH);
        panel.add(panelBotonesMecanicos, BorderLayout.CENTER);
        panel.add(panelDisponibles, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel de gestión de autos
     */
    private JPanel crearPanelAutos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de autos asignados
        JPanel panelAsignados = new JPanel(new BorderLayout());
        panelAsignados.setBorder(BorderFactory.createTitledBorder("Autos de la Escudería"));

        modeloAutos = new DefaultListModel<>();
        listaAutos = new JList<>(modeloAutos);
        listaAutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollAutos = new JScrollPane(listaAutos);
        scrollAutos.setPreferredSize(new Dimension(150, 100));
        panelAsignados.add(scrollAutos, BorderLayout.CENTER);

        // Panel de autos disponibles
        JPanel panelDisponibles = new JPanel(new BorderLayout());
        panelDisponibles.setBorder(BorderFactory.createTitledBorder("Autos Disponibles"));

        modeloAutosDisponibles = new DefaultListModel<>();
        listaAutosDisponibles = new JList<>(modeloAutosDisponibles);
        listaAutosDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollDisponibles = new JScrollPane(listaAutosDisponibles);
        scrollDisponibles.setPreferredSize(new Dimension(150, 100));
        panelDisponibles.add(scrollDisponibles, BorderLayout.CENTER);

        // Botones de gestión
        JPanel panelBotonesAutos = new JPanel(new FlowLayout());
        JButton btnAsignarAuto = new JButton("← Asignar");
        JButton btnRemoverAuto = new JButton("Remover →");

        btnAsignarAuto.addActionListener(e -> asignarAuto());
        btnRemoverAuto.addActionListener(e -> removerAuto());

        panelBotonesAutos.add(btnAsignarAuto);
        panelBotonesAutos.add(btnRemoverAuto);

        panel.add(panelAsignados, BorderLayout.NORTH);
        panel.add(panelBotonesAutos, BorderLayout.CENTER);
        panel.add(panelDisponibles, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel de botones principales
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(248, 249, 250));

        btnEliminar = crearBoton("ELIMINAR");
        JButton btnActualizar = crearBoton("ACTUALIZAR");
        JButton btnReportes = crearBoton("REPORTES");
        JButton btnCerrar = crearBoton("CERRAR");

        panel.add(btnEliminar);
        panel.add(btnActualizar);
        panel.add(btnReportes);
        panel.add(btnCerrar);

        // Eventos
        btnActualizar.addActionListener(e -> cargarDatos());
        btnReportes.addActionListener(e -> mostrarReportes());
        btnCerrar.addActionListener(e -> dispose());

        return panel;
    }

    /**
     * Crea etiquetas con estilo consistente
     */
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(73, 80, 87));
        label.setPreferredSize(new Dimension(140, 25));
        return label;
    }

    /**
     * Crea campos de texto con estilo moderno
     */
    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setPreferredSize(new Dimension(450, 35));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        // Efectos focus
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                        BorderFactory.createEmptyBorder(4, 9, 4, 9)));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(206, 212, 218)),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            }
        });

        return campo;
    }

    /**
     * Crea botones con estilo simple
     */
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(120, 40));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setBackground(Color.WHITE);
        boton.setForeground(Color.BLACK);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setFocusPainted(true);
        return boton;
    }

    /**
     * Configura la apariencia de la tabla
     */
    private void configurarTabla() {
        tablaEscuderias.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaEscuderias.setRowHeight(30);
        tablaEscuderias.setGridColor(new Color(233, 236, 239));
        tablaEscuderias.setSelectionBackground(new Color(0, 123, 255, 30));
        tablaEscuderias.setSelectionForeground(new Color(52, 58, 64));

        // Header personalizado
        tablaEscuderias.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaEscuderias.getTableHeader().setBackground(new Color(248, 249, 250));
        tablaEscuderias.getTableHeader().setForeground(new Color(52, 58, 64));
        tablaEscuderias.getTableHeader()
                .setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));

        // Renderer personalizado
        tablaEscuderias.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        tablaEscuderias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Configura los eventos de la interfaz
     */
    private void configurarEventos() {
        // Eventos de botones del formulario
        btnAgregar.addActionListener(e -> agregarEscuderia());
        btnModificar.addActionListener(e -> modificarEscuderia());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnEliminar.addActionListener(e -> eliminarEscuderia());

        // Evento de selección en tabla
        tablaEscuderias.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarEscuderia();
            }
        });

        // Doble clic en tabla para editar
        tablaEscuderias.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    seleccionarEscuderia();
                }
            }
        });
    }

    /**
     * Configura la ventana
     */
    private void configurarVentana() {
        setTitle("Gestión de Escuderías - Sistema Formula 1");
        setSize(1400, 800);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
    }

    /**
     * Carga los datos iniciales
     */
    private void cargarDatos() {
        cargarPaises();
        actualizarTabla();
        actualizarListasDisponibles();
    }

    /**
     * Carga los países en el combo
     */
    private void cargarPaises() {
        comboPaises.removeAllItems();
        comboPaises.addItem(null);
        for (Pais pais : gestor.getPaises()) {
            comboPaises.addItem(pais);
        }
    }

    /**
     * Actualiza la tabla de escuderías
     */
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Escuderia escuderia : gestor.getEscuderias()) {
            int puntosTotales = calcularPuntosTotalesEscuderia(escuderia);
            Object[] fila = {
                    escuderia.getNombre(),
                    escuderia.getPais().getNombre(),
                    escuderia.getPilotos().size(),
                    escuderia.getMecanicos().size(),
                    escuderia.getAutos().size(),
                    puntosTotales
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Actualiza las listas de elementos disponibles
     */
    private void actualizarListasDisponibles() {
        // Actualizar pilotos disponibles (sin escudería asignada)
        modeloPilotosDisponibles.clear();
        for (Piloto piloto : gestor.getPilotos()) {
            if (piloto.getEscuderia() == null) {
                modeloPilotosDisponibles.addElement(piloto);
            }
        }

        // Actualizar mecánicos disponibles
        modeloMecanicosDisponibles.clear();
        List<Mecanico> mecanicosAsignados = gestor.getEscuderias().stream()
                .flatMap(e -> e.getMecanicos().stream())
                .toList();
        for (Mecanico mecanico : gestor.getMecanicos()) {
            if (!mecanicosAsignados.contains(mecanico)) {
                modeloMecanicosDisponibles.addElement(mecanico);
            }
        }

        // Actualizar autos disponibles
        modeloAutosDisponibles.clear();
        List<Auto> autosAsignados = gestor.getEscuderias().stream()
                .flatMap(e -> e.getAutos().stream())
                .toList();
        for (Auto auto : gestor.getAutos()) {
            if (!autosAsignados.contains(auto)) {
                modeloAutosDisponibles.addElement(auto);
            }
        }
    }

    /**
     * Agrega una nueva escudería
     */
    private void agregarEscuderia() {
        try {
            validarCampos();

            String nombre = txtNombre.getText().trim();
            Pais pais = (Pais) comboPaises.getSelectedItem();

            Escuderia nuevaEscuderia = new Escuderia(nombre, pais);
            gestor.registrarEscuderia(nuevaEscuderia);

            actualizarTabla();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this,
                    "Escudería agregada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al agregar escudería: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica la escudería seleccionada
     */
    private void modificarEscuderia() {
        if (escuderiaSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una escudería para modificar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            validarCampos();

            escuderiaSeleccionada.setNombre(txtNombre.getText().trim());
            escuderiaSeleccionada.setPais((Pais) comboPaises.getSelectedItem());

            actualizarTabla();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this,
                    "Escudería modificada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al modificar escudería: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina la escudería seleccionada
     */
    private void eliminarEscuderia() {
        int filaSeleccionada = tablaEscuderias.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una escudería para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar esta escudería?\nEsto también desvinculará a sus pilotos, mecánicos y autos.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String nombreEscuderia = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            Escuderia escuderia = gestor.getEscuderias().stream()
                    .filter(e -> e.getNombre().equals(nombreEscuderia))
                    .findFirst().orElse(null);

            if (escuderia != null) {
                // Desvincular pilotos
                for (Piloto piloto : escuderia.getPilotos()) {
                    piloto.setEscuderia(null);
                }

                // Remover escudería
                gestor.getEscuderias().remove(escuderia);
                actualizarTabla();
                actualizarListasDisponibles();
                limpiarFormulario();

                JOptionPane.showMessageDialog(this,
                        "Escudería eliminada exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Selecciona una escudería de la tabla
     */
    private void seleccionarEscuderia() {
        int filaSeleccionada = tablaEscuderias.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombreEscuderia = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            escuderiaSeleccionada = gestor.getEscuderias().stream()
                    .filter(e -> e.getNombre().equals(nombreEscuderia))
                    .findFirst().orElse(null);

            if (escuderiaSeleccionada != null) {
                cargarDatosEscuderia(escuderiaSeleccionada);
                mostrarEstadisticasEscuderia(escuderiaSeleccionada);
                actualizarListasAsignadas(escuderiaSeleccionada);
            }
        }
    }

    /**
     * Carga los datos de la escudería en el formulario
     */
    private void cargarDatosEscuderia(Escuderia escuderia) {
        txtNombre.setText(escuderia.getNombre());
        comboPaises.setSelectedItem(escuderia.getPais());
    }

    /**
     * Actualiza las listas de elementos asignados a la escudería
     */
    private void actualizarListasAsignadas(Escuderia escuderia) {
        // Actualizar lista de pilotos asignados
        modeloPilotos.clear();
        for (Piloto piloto : escuderia.getPilotos()) {
            modeloPilotos.addElement(piloto);
        }

        // Actualizar lista de mecánicos asignados
        modeloMecanicos.clear();
        for (Mecanico mecanico : escuderia.getMecanicos()) {
            modeloMecanicos.addElement(mecanico);
        }

        // Actualizar lista de autos asignados
        modeloAutos.clear();
        for (Auto auto : escuderia.getAutos()) {
            modeloAutos.addElement(auto);
        }

        // Actualizar listas disponibles
        actualizarListasDisponibles();
    }

    /**
     * Muestra las estadísticas de la escudería seleccionada
     */
    private void mostrarEstadisticasEscuderia(Escuderia escuderia) {
        StringBuilder stats = new StringBuilder("<html><div style='text-align: center;'>");
        stats.append("<b>").append(escuderia.getNombre()).append("</b><br>");
        stats.append("País: ").append(escuderia.getPais().getNombre()).append("<br><br>");
        stats.append("Pilotos: ").append(escuderia.getPilotos().size()).append("<br>");
        stats.append("Mecánicos: ").append(escuderia.getMecanicos().size()).append("<br>");
        stats.append("Autos: ").append(escuderia.getAutos().size()).append("<br><br>");

        int puntosTotales = calcularPuntosTotalesEscuderia(escuderia);
        stats.append("Puntos Totales: ").append(puntosTotales).append("<br>");

        stats.append("</div></html>");
        lblEstadisticasEscuderia.setText(stats.toString());
    }

    /**
     * Asigna un piloto a la escudería seleccionada
     */
    private void asignarPiloto() {
        if (escuderiaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una escudería primero", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Piloto piloto = listaPilotosDisponibles.getSelectedValue();
        if (piloto != null) {
            escuderiaSeleccionada.agregarPiloto(piloto);
            actualizarListasAsignadas(escuderiaSeleccionada);
            actualizarTabla();
            mostrarEstadisticasEscuderia(escuderiaSeleccionada);
        }
    }

    /**
     * Remueve un piloto de la escudería seleccionada
     */
    private void removerPiloto() {
        if (escuderiaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una escudería primero", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Piloto piloto = listaPilotos.getSelectedValue();
        if (piloto != null) {
            escuderiaSeleccionada.removerPiloto(piloto);
            actualizarListasAsignadas(escuderiaSeleccionada);
            actualizarTabla();
            mostrarEstadisticasEscuderia(escuderiaSeleccionada);
        }
    }

    /**
     * Asigna un mecánico a la escudería seleccionada
     */
    private void asignarMecanico() {
        if (escuderiaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una escudería primero", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Mecanico mecanico = listaMecanicosDisponibles.getSelectedValue();
        if (mecanico != null) {
            escuderiaSeleccionada.agregarMecanico(mecanico);
            actualizarListasAsignadas(escuderiaSeleccionada);
            actualizarTabla();
            mostrarEstadisticasEscuderia(escuderiaSeleccionada);
        }
    }

    /**
     * Remueve un mecánico de la escudería seleccionada
     */
    private void removerMecanico() {
        if (escuderiaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una escudería primero", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Mecanico mecanico = listaMecanicos.getSelectedValue();
        if (mecanico != null) {
            escuderiaSeleccionada.getMecanicos().remove(mecanico);
            actualizarListasAsignadas(escuderiaSeleccionada);
            actualizarTabla();
            mostrarEstadisticasEscuderia(escuderiaSeleccionada);
        }
    }

    /**
     * Asigna un auto a la escudería seleccionada
     */
    private void asignarAuto() {
        if (escuderiaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una escudería primero", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Auto auto = listaAutosDisponibles.getSelectedValue();
        if (auto != null) {
            escuderiaSeleccionada.agregarAuto(auto);
            actualizarListasAsignadas(escuderiaSeleccionada);
            actualizarTabla();
            mostrarEstadisticasEscuderia(escuderiaSeleccionada);
        }
    }

    /**
     * Remueve un auto de la escudería seleccionada
     */
    private void removerAuto() {
        if (escuderiaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una escudería primero", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Auto auto = listaAutos.getSelectedValue();
        if (auto != null) {
            escuderiaSeleccionada.getAutos().remove(auto);
            actualizarListasAsignadas(escuderiaSeleccionada);
            actualizarTabla();
            mostrarEstadisticasEscuderia(escuderiaSeleccionada);
        }
    }

    /**
     * Calcula los puntos totales de una escudería
     */
    private int calcularPuntosTotalesEscuderia(Escuderia escuderia) {
        return escuderia.getPilotos().stream()
                .mapToInt(Piloto::getPuntosTotales)
                .sum();
    }

    /**
     * Muestra los reportes de la escudería
     */
    private void mostrarReportes() {
        if (escuderiaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una escudería para ver reportes", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE ESCUDERÍA ===\n\n");
        reporte.append("Escudería: ").append(escuderiaSeleccionada.getNombre()).append("\n");
        reporte.append("País: ").append(escuderiaSeleccionada.getPais().getNombre()).append("\n\n");

        // Reporte de pilotos
        reporte.append("--- PILOTOS ---\n");
        for (Piloto piloto : escuderiaSeleccionada.getPilotos()) {
            reporte.append("• ").append(piloto.getNombreCompleto())
                    .append(" (#").append(piloto.getNumero()).append(")")
                    .append(" - Puntos: ").append(piloto.getPuntosTotales())
                    .append("\n");
        }

        // Reporte de mecánicos
        reporte.append("\n--- MECÁNICOS ---\n");
        for (Mecanico mecanico : escuderiaSeleccionada.getMecanicos()) {
            reporte.append("• ").append(mecanico.getNombreCompleto())
                    .append(" - ").append(mecanico.getEspecialidadesString())
                    .append(" (").append(mecanico.getExperiencia()).append(" años)")
                    .append("\n");
        }

        // Reporte de autos
        reporte.append("\n--- AUTOS ---\n");
        for (Auto auto : escuderiaSeleccionada.getAutos()) {
            reporte.append("• ").append(auto.getModelo())
                    .append(" - Motor: ").append(auto.getMotor())
                    .append("\n");
        }

        // Mostrar en un diálogo
        JTextArea textArea = new JTextArea(reporte.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane,
                "Reporte - " + escuderiaSeleccionada.getNombre(),
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Limpia el formulario
     */
    private void limpiarFormulario() {
        txtNombre.setText("");
        comboPaises.setSelectedIndex(0);
        escuderiaSeleccionada = null;
        tablaEscuderias.clearSelection();

        // Limpiar listas
        modeloPilotos.clear();
        modeloMecanicos.clear();
        modeloAutos.clear();

        lblEstadisticasEscuderia
                .setText("<html><center>Selecciona una escudería<br>para ver sus estadísticas</center></html>");
    }

    /**
     * Valida los campos del formulario
     */
    private void validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la escudería es obligatorio");
        }

        if (comboPaises.getSelectedItem() == null) {
            throw new IllegalArgumentException("Debe seleccionar un país");
        }
    }
}