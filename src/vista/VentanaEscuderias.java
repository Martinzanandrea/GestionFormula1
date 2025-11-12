package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para la gestión de escuderías
 */
public class VentanaEscuderias extends JFrame {
    private GestorFormula1 gestor;
    private JTable tablaEscuderias;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre;
    private JComboBox<Pais> comboPaises;
    private JList<Piloto> listaPilotos;
    private JList<Auto> listaAutos;
    private JList<Mecanico> listaMecanicos;
    private DefaultListModel<Piloto> modeloPilotos;
    private DefaultListModel<Auto> modeloAutos;
    private DefaultListModel<Mecanico> modeloMecanicos;

    /**
     * Constructor de la ventana de escuderías
     * 
     * @param gestor Gestor principal del sistema
     */
    public VentanaEscuderias(GestorFormula1 gestor) {
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
        JTabbedPane pestanas = new JTabbedPane();

        // Pestaña de datos básicos
        JPanel panelDatos = crearPanelDatos();
        pestanas.addTab("Datos Básicos", panelDatos);

        // Pestaña de personal
        JPanel panelPersonal = crearPanelPersonal();
        pestanas.addTab("Personal", panelPersonal);

        // Panel de botones
        JPanel panelBotones = crearPanelBotones();

        add(pestanas, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Crea el panel de datos básicos
     */
    private JPanel crearPanelDatos() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Escudería"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("País:"), gbc);
        gbc.gridx = 1;
        comboPaises = new JComboBox<>();
        panelFormulario.add(comboPaises, gbc);

        // Panel de tabla
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Escuderías"));

        String[] columnas = { "Nombre", "País", "Pilotos", "Autos", "Mecánicos" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEscuderias = new JTable(modeloTabla);
        tablaEscuderias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEscuderias.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarEscuderiaSeleccionada();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaEscuderias);
        scrollPane.setPreferredSize(new Dimension(600, 200));

        panelTabla.add(scrollPane, BorderLayout.CENTER);

        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(panelTabla, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de personal (pilotos, autos, mecánicos)
     */
    private JPanel crearPanelPersonal() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de pilotos
        JPanel panelPilotos = new JPanel(new BorderLayout());
        panelPilotos.setBorder(BorderFactory.createTitledBorder("Pilotos"));

        modeloPilotos = new DefaultListModel<>();
        listaPilotos = new JList<>(modeloPilotos);
        listaPilotos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPilotos = new JScrollPane(listaPilotos);
        scrollPilotos.setPreferredSize(new Dimension(200, 300));

        JPanel panelBotonesPilotos = new JPanel(new FlowLayout());
        JButton btnAgregarPiloto = new JButton("Agregar");
        JButton btnRemoverPiloto = new JButton("Remover");

        btnAgregarPiloto.addActionListener(e -> agregarPilotoAEscuderia());
        btnRemoverPiloto.addActionListener(e -> removerPilotoDeEscuderia());

        panelBotonesPilotos.add(btnAgregarPiloto);
        panelBotonesPilotos.add(btnRemoverPiloto);

        panelPilotos.add(scrollPilotos, BorderLayout.CENTER);
        panelPilotos.add(panelBotonesPilotos, BorderLayout.SOUTH);

        // Panel de autos
        JPanel panelAutos = new JPanel(new BorderLayout());
        panelAutos.setBorder(BorderFactory.createTitledBorder("Autos"));

        modeloAutos = new DefaultListModel<>();
        listaAutos = new JList<>(modeloAutos);
        listaAutos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollAutos = new JScrollPane(listaAutos);
        scrollAutos.setPreferredSize(new Dimension(200, 300));

        JPanel panelBotonesAutos = new JPanel(new FlowLayout());
        JButton btnAgregarAuto = new JButton("Agregar");
        JButton btnRemoverAuto = new JButton("Remover");

        btnAgregarAuto.addActionListener(e -> agregarAutoAEscuderia());
        btnRemoverAuto.addActionListener(e -> removerAutoDeEscuderia());

        panelBotonesAutos.add(btnAgregarAuto);
        panelBotonesAutos.add(btnRemoverAuto);

        panelAutos.add(scrollAutos, BorderLayout.CENTER);
        panelAutos.add(panelBotonesAutos, BorderLayout.SOUTH);

        // Panel de mecánicos
        JPanel panelMecanicos = new JPanel(new BorderLayout());
        panelMecanicos.setBorder(BorderFactory.createTitledBorder("Mecánicos"));

        modeloMecanicos = new DefaultListModel<>();
        listaMecanicos = new JList<>(modeloMecanicos);
        listaMecanicos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollMecanicos = new JScrollPane(listaMecanicos);
        scrollMecanicos.setPreferredSize(new Dimension(200, 300));

        JPanel panelBotonesMecanicos = new JPanel(new FlowLayout());
        JButton btnAgregarMecanico = new JButton("Agregar");
        JButton btnRemoverMecanico = new JButton("Remover");

        btnAgregarMecanico.addActionListener(e -> agregarMecanicoAEscuderia());
        btnRemoverMecanico.addActionListener(e -> removerMecanicoDeEscuderia());

        panelBotonesMecanicos.add(btnAgregarMecanico);
        panelBotonesMecanicos.add(btnRemoverMecanico);

        panelMecanicos.add(scrollMecanicos, BorderLayout.CENTER);
        panelMecanicos.add(panelBotonesMecanicos, BorderLayout.SOUTH);

        panel.add(panelPilotos);
        panel.add(panelAutos);
        panel.add(panelMecanicos);

        return panel;
    }

    /**
     * Crea el panel de botones principales
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnAgregar = new JButton("Agregar Escudería");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnCerrar = new JButton("Cerrar");

        btnAgregar.addActionListener(e -> agregarEscuderia());
        btnModificar.addActionListener(e -> modificarEscuderia());
        btnEliminar.addActionListener(e -> eliminarEscuderia());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnAgregar);
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        panel.add(btnCerrar);

        return panel;
    }

    /**
     * Configura la ventana
     */
    private void configurarVentana() {
        setTitle("Gestión de Escuderías");
        setSize(1000, 700);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Carga los datos iniciales
     */
    private void cargarDatos() {
        cargarPaises();
        actualizarTablaEscuderias();
    }

    /**
     * Carga los países en el combo
     */
    private void cargarPaises() {
        comboPaises.removeAllItems();
        for (Pais pais : gestor.getPaises()) {
            comboPaises.addItem(pais);
        }
    }

    /**
     * Actualiza la tabla de escuderías
     */
    private void actualizarTablaEscuderias() {
        modeloTabla.setRowCount(0);
        for (Escuderia escuderia : gestor.getEscuderias()) {
            Object[] fila = {
                    escuderia.getNombre(),
                    escuderia.getPais().getNombre(),
                    escuderia.getPilotos().size(),
                    escuderia.getAutos().size(),
                    escuderia.getMecanicos().size()
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Carga los datos de la escudería seleccionada
     */
    private void cargarEscuderiaSeleccionada() {
        int filaSeleccionada = tablaEscuderias.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            Escuderia escuderia = gestor.getEscuderias().stream()
                    .filter(e -> e.getNombre().equals(nombre))
                    .findFirst().orElse(null);

            if (escuderia != null) {
                txtNombre.setText(escuderia.getNombre());
                comboPaises.setSelectedItem(escuderia.getPais());

                // Cargar personal
                cargarPersonalEscuderia(escuderia);
            }
        }
    }

    /**
     * Carga el personal de la escudería seleccionada
     */
    private void cargarPersonalEscuderia(Escuderia escuderia) {
        // Cargar pilotos
        modeloPilotos.clear();
        for (Piloto piloto : escuderia.getPilotos()) {
            modeloPilotos.addElement(piloto);
        }

        // Cargar autos
        modeloAutos.clear();
        for (Auto auto : escuderia.getAutos()) {
            modeloAutos.addElement(auto);
        }

        // Cargar mecánicos
        modeloMecanicos.clear();
        for (Mecanico mecanico : escuderia.getMecanicos()) {
            modeloMecanicos.addElement(mecanico);
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

            actualizarTablaEscuderias();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this, "Escudería agregada exitosamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar escudería: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica la escudería seleccionada
     */
    private void modificarEscuderia() {
        int filaSeleccionada = tablaEscuderias.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una escudería para modificar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            validarCampos();

            String nombreOriginal = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            Escuderia escuderia = gestor.getEscuderias().stream()
                    .filter(e -> e.getNombre().equals(nombreOriginal))
                    .findFirst().orElse(null);

            if (escuderia != null) {
                escuderia.setNombre(txtNombre.getText().trim());
                escuderia.setPais((Pais) comboPaises.getSelectedItem());

                actualizarTablaEscuderias();
                JOptionPane.showMessageDialog(this, "Escudería modificada exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar escudería: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina la escudería seleccionada
     */
    private void eliminarEscuderia() {
        int filaSeleccionada = tablaEscuderias.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una escudería para eliminar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar esta escudería?\nSe desasignarán todos sus pilotos, autos y mecánicos.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            Escuderia escuderia = gestor.getEscuderias().stream()
                    .filter(e -> e.getNombre().equals(nombre))
                    .findFirst().orElse(null);

            if (escuderia != null) {
                // Desasignar pilotos
                for (Piloto piloto : escuderia.getPilotos()) {
                    piloto.setEscuderia(null);
                }

                gestor.getEscuderias().remove(escuderia);
                actualizarTablaEscuderias();
                limpiarFormulario();

                JOptionPane.showMessageDialog(this, "Escudería eliminada exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Agrega un piloto a la escudería seleccionada
     */
    private void agregarPilotoAEscuderia() {
        Escuderia escuderia = getEscuderiaSeleccionada();
        if (escuderia == null)
            return;

        // Obtener pilotos sin escudería
        List<Piloto> pilotosSinEscuderia = gestor.getPilotos().stream()
                .filter(p -> p.getEscuderia() == null)
                .toList();

        if (pilotosSinEscuderia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pilotos disponibles para asignar", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Piloto pilotoSeleccionado = (Piloto) JOptionPane.showInputDialog(this,
                "Seleccione el piloto a agregar:",
                "Agregar Piloto",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pilotosSinEscuderia.toArray(),
                pilotosSinEscuderia.get(0));

        if (pilotoSeleccionado != null) {
            gestor.asignarPilotoAEscuderia(pilotoSeleccionado, escuderia);
            cargarPersonalEscuderia(escuderia);
            actualizarTablaEscuderias();
        }
    }

    /**
     * Remueve un piloto de la escudería seleccionada
     */
    private void removerPilotoDeEscuderia() {
        Escuderia escuderia = getEscuderiaSeleccionada();
        if (escuderia == null)
            return;

        Piloto pilotoSeleccionado = listaPilotos.getSelectedValue();
        if (pilotoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un piloto para remover", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        escuderia.removerPiloto(pilotoSeleccionado);
        cargarPersonalEscuderia(escuderia);
        actualizarTablaEscuderias();
    }

    /**
     * Agrega un auto a la escudería seleccionada
     */
    private void agregarAutoAEscuderia() {
        Escuderia escuderia = getEscuderiaSeleccionada();
        if (escuderia == null)
            return;

        // Obtener autos no asignados
        List<Auto> autosDisponibles = gestor.getAutos().stream()
                .filter(a -> gestor.getEscuderias().stream()
                        .noneMatch(e -> e.getAutos().contains(a)))
                .toList();

        if (autosDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay autos disponibles para asignar", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Auto autoSeleccionado = (Auto) JOptionPane.showInputDialog(this,
                "Seleccione el auto a agregar:",
                "Agregar Auto",
                JOptionPane.QUESTION_MESSAGE,
                null,
                autosDisponibles.toArray(),
                autosDisponibles.get(0));

        if (autoSeleccionado != null) {
            escuderia.agregarAuto(autoSeleccionado);
            cargarPersonalEscuderia(escuderia);
            actualizarTablaEscuderias();
        }
    }

    /**
     * Remueve un auto de la escudería seleccionada
     */
    private void removerAutoDeEscuderia() {
        Escuderia escuderia = getEscuderiaSeleccionada();
        if (escuderia == null)
            return;

        Auto autoSeleccionado = listaAutos.getSelectedValue();
        if (autoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un auto para remover", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        escuderia.getAutos().remove(autoSeleccionado);
        cargarPersonalEscuderia(escuderia);
        actualizarTablaEscuderias();
    }

    /**
     * Agrega un mecánico a la escudería seleccionada
     */
    private void agregarMecanicoAEscuderia() {
        Escuderia escuderia = getEscuderiaSeleccionada();
        if (escuderia == null)
            return;

        // Obtener mecánicos no asignados
        List<Mecanico> mecanicosDisponibles = gestor.getMecanicos().stream()
                .filter(m -> gestor.getEscuderias().stream()
                        .noneMatch(e -> e.getMecanicos().contains(m)))
                .toList();

        if (mecanicosDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay mecánicos disponibles para asignar", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Mecanico mecanicoSeleccionado = (Mecanico) JOptionPane.showInputDialog(this,
                "Seleccione el mecánico a agregar:",
                "Agregar Mecánico",
                JOptionPane.QUESTION_MESSAGE,
                null,
                mecanicosDisponibles.toArray(),
                mecanicosDisponibles.get(0));

        if (mecanicoSeleccionado != null) {
            escuderia.agregarMecanico(mecanicoSeleccionado);
            cargarPersonalEscuderia(escuderia);
            actualizarTablaEscuderias();
        }
    }

    /**
     * Remueve un mecánico de la escudería seleccionada
     */
    private void removerMecanicoDeEscuderia() {
        Escuderia escuderia = getEscuderiaSeleccionada();
        if (escuderia == null)
            return;

        Mecanico mecanicoSeleccionado = listaMecanicos.getSelectedValue();
        if (mecanicoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un mecánico para remover", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        escuderia.getMecanicos().remove(mecanicoSeleccionado);
        cargarPersonalEscuderia(escuderia);
        actualizarTablaEscuderias();
    }

    /**
     * Obtiene la escudería actualmente seleccionada
     */
    private Escuderia getEscuderiaSeleccionada() {
        int filaSeleccionada = tablaEscuderias.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una escudería", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        return gestor.getEscuderias().stream()
                .filter(e -> e.getNombre().equals(nombre))
                .findFirst().orElse(null);
    }

    /**
     * Limpia el formulario
     */
    private void limpiarFormulario() {
        txtNombre.setText("");
        comboPaises.setSelectedIndex(0);
        modeloPilotos.clear();
        modeloAutos.clear();
        modeloMecanicos.clear();
        tablaEscuderias.clearSelection();
    }

    /**
     * Valida los campos del formulario
     */
    private void validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (comboPaises.getSelectedItem() == null) {
            throw new IllegalArgumentException("Debe seleccionar un país");
        }
    }
}