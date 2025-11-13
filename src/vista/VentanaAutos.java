package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Ventana para la gestión de autos de Fórmula 1
 * Permite crear, asignar y desasignar autos de escuderías
 */
public class VentanaAutos extends JFrame {
    private GestorFormula1 gestor;
    private JTable tablaAutos;
    private DefaultTableModel modeloTabla;
    private JTextField txtModelo, txtChasis, txtMotor, txtAño, txtNumeroChasis, txtPeso, txtPotencia;
    private JComboBox<String> cmbEscuderia;
    private JButton btnCrearAuto, btnAsignarAuto, btnDesasignarAuto, btnLimpiar, btnCerrar;
    private Auto autoSeleccionado;

    /**
     * Constructor de la ventana de autos
     */
    public VentanaAutos(GestorFormula1 gestor) {
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

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de formulario
        JPanel panelFormulario = crearPanelFormulario();

        // Panel de tabla
        JPanel panelTabla = crearPanelTabla();

        // Panel de botones
        JPanel panelBotones = crearPanelBotones();

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Crea el panel de formulario
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Gestión de Autos"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar campos
        txtModelo = new JTextField(15);
        txtChasis = new JTextField(15);
        txtMotor = new JTextField(15);
        txtAño = new JTextField(15);
        txtNumeroChasis = new JTextField(15);
        txtPeso = new JTextField(15);
        txtPotencia = new JTextField(15);
        cmbEscuderia = new JComboBox<>();

        // Primera fila
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1;
        panel.add(txtModelo, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Chasis:"), gbc);
        gbc.gridx = 3;
        panel.add(txtChasis, gbc);

        // Segunda fila
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Motor:"), gbc);
        gbc.gridx = 1;
        panel.add(txtMotor, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Año:"), gbc);
        gbc.gridx = 3;
        panel.add(txtAño, gbc);

        // Tercera fila
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Número Chasis:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNumeroChasis, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Peso (kg):"), gbc);
        gbc.gridx = 3;
        panel.add(txtPeso, gbc);

        // Cuarta fila
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Potencia (HP):"), gbc);
        gbc.gridx = 1;
        panel.add(txtPotencia, gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Escudería:"), gbc);
        gbc.gridx = 3;
        panel.add(cmbEscuderia, gbc);

        return panel;
    }

    /**
     * Crea el panel de tabla
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Autos Registrados"));

        // Crear modelo de tabla
        String[] columnas = { "Modelo", "Chasis", "Motor", "Año", "Número Chasis", "Peso", "Potencia", "Escudería",
                "Estado" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaAutos = new JTable(modeloTabla);
        tablaAutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaAutos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarAutoSeleccionado();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaAutos);
        scrollPane.setPreferredSize(new Dimension(800, 250));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de botones
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        btnCrearAuto = new JButton("Crear Auto Libre");
        btnAsignarAuto = new JButton("Asignar a Escudería");
        btnDesasignarAuto = new JButton("Desasignar de Escudería");
        btnLimpiar = new JButton("Limpiar");
        btnCerrar = new JButton("Cerrar");

        // Configurar acciones
        btnCrearAuto.addActionListener(e -> crearAutoLibre());
        btnAsignarAuto.addActionListener(e -> asignarAutoAEscuderia());
        btnDesasignarAuto.addActionListener(e -> desasignarAutoDeEscuderia());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnCrearAuto);
        panel.add(btnAsignarAuto);
        panel.add(btnDesasignarAuto);
        panel.add(btnLimpiar);
        panel.add(btnCerrar);

        return panel;
    }

    /**
     * Configura las propiedades de la ventana
     */
    private void configurarVentana() {
        setTitle("Gestión de Autos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Carga los datos iniciales
     */
    private void cargarDatos() {
        cargarEscuderias();
        actualizarTablaAutos();
    }

    /**
     * Carga las escuderías en el combo box
     */
    private void cargarEscuderias() {
        cmbEscuderia.removeAllItems();
        cmbEscuderia.addItem("Sin asignar");

        List<Escuderia> escuderias = gestor.getEscuderias();
        for (Escuderia escuderia : escuderias) {
            cmbEscuderia.addItem(escuderia.getNombre());
        }
    }

    /**
     * Actualiza la tabla de autos
     */
    private void actualizarTablaAutos() {
        modeloTabla.setRowCount(0);
        List<Auto> autos = gestor.getAutos();
        List<Escuderia> escuderias = gestor.getEscuderias();

        for (Auto auto : autos) {
            String escuderiaAsignada = "Libre";

            // Buscar si el auto está asignado a alguna escudería
            for (Escuderia escuderia : escuderias) {
                if (escuderia.getAutos().contains(auto)) {
                    escuderiaAsignada = escuderia.getNombre();
                    break;
                }
            }

            Object[] fila = {
                    auto.getModelo(),
                    auto.getChasis(),
                    auto.getMotor(),
                    auto.getAño(),
                    auto.getNumeroChasis(),
                    auto.getPeso() + " kg",
                    auto.getPotencia() + " HP",
                    escuderiaAsignada,
                    escuderiaAsignada.equals("Libre") ? "Disponible" : "Asignado"
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Carga el auto seleccionado en el formulario
     */
    private void cargarAutoSeleccionado() {
        int filaSeleccionada = tablaAutos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            List<Auto> autos = gestor.getAutos();
            autoSeleccionado = autos.get(filaSeleccionada);

            txtModelo.setText(autoSeleccionado.getModelo());
            txtChasis.setText(autoSeleccionado.getChasis());
            txtMotor.setText(autoSeleccionado.getMotor());
            txtAño.setText(String.valueOf(autoSeleccionado.getAño()));
            txtNumeroChasis.setText(autoSeleccionado.getNumeroChasis());
            txtPeso.setText(String.valueOf(autoSeleccionado.getPeso()));
            txtPotencia.setText(String.valueOf(autoSeleccionado.getPotencia()));

            // Buscar la escudería asignada
            String escuderiaAsignada = "Sin asignar";
            List<Escuderia> escuderias = gestor.getEscuderias();
            for (Escuderia escuderia : escuderias) {
                if (escuderia.getAutos().contains(autoSeleccionado)) {
                    escuderiaAsignada = escuderia.getNombre();
                    break;
                }
            }
            cmbEscuderia.setSelectedItem(escuderiaAsignada);
        }
    }

    /**
     * Crea un nuevo auto libre
     */
    private void crearAutoLibre() {
        try {
            validarCamposAuto();

            String modelo = txtModelo.getText().trim();
            String chasis = txtChasis.getText().trim();
            String motor = txtMotor.getText().trim();
            int año = Integer.parseInt(txtAño.getText().trim());
            String numeroChasis = txtNumeroChasis.getText().trim();
            double peso = Double.parseDouble(txtPeso.getText().trim());
            int potencia = Integer.parseInt(txtPotencia.getText().trim());

            Auto nuevoAuto = new Auto(modelo, chasis, motor, año, numeroChasis, peso, potencia);
            gestor.registrarAuto(nuevoAuto);

            JOptionPane.showMessageDialog(this,
                    "Auto creado exitosamente como libre (sin asignar).",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();
            actualizarTablaAutos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Error en los datos numéricos. Verifique año, peso y potencia.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al crear el auto: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Asigna un auto libre a una escudería
     */
    private void asignarAutoAEscuderia() {
        if (autoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un auto de la tabla.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String escuderiaSeleccionada = (String) cmbEscuderia.getSelectedItem();
        if (escuderiaSeleccionada == null || escuderiaSeleccionada.equals("Sin asignar")) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar una escudería.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar si el auto ya está asignado
        if (estaAutoAsignado(autoSeleccionado)) {
            JOptionPane.showMessageDialog(this,
                    "El auto ya está asignado a una escudería.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Buscar la escudería
        Escuderia escuderia = buscarEscuderiaPorNombre(escuderiaSeleccionada);
        if (escuderia != null) {
            escuderia.agregarAuto(autoSeleccionado);

            JOptionPane.showMessageDialog(this,
                    "Auto asignado exitosamente a " + escuderia.getNombre() + ".",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            actualizarTablaAutos();
        }
    }

    /**
     * Desasigna un auto de su escudería actual
     */
    private void desasignarAutoDeEscuderia() {
        if (autoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un auto de la tabla.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Buscar la escudería a la que está asignado el auto
        Escuderia escuderiaAsignada = null;
        List<Escuderia> escuderias = gestor.getEscuderias();

        for (Escuderia escuderia : escuderias) {
            if (escuderia.getAutos().contains(autoSeleccionado)) {
                escuderiaAsignada = escuderia;
                break;
            }
        }

        if (escuderiaAsignada == null) {
            JOptionPane.showMessageDialog(this,
                    "El auto no está asignado a ninguna escudería.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de desasignar el auto de " + escuderiaAsignada.getNombre() + "?",
                "Confirmar Desasignación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            escuderiaAsignada.removerAuto(autoSeleccionado);

            JOptionPane.showMessageDialog(this,
                    "Auto desasignado exitosamente de " + escuderiaAsignada.getNombre() + ".",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            cmbEscuderia.setSelectedItem("Sin asignar");
            actualizarTablaAutos();
        }
    }

    /**
     * Verifica si un auto está asignado a alguna escudería
     */
    private boolean estaAutoAsignado(Auto auto) {
        List<Escuderia> escuderias = gestor.getEscuderias();
        for (Escuderia escuderia : escuderias) {
            if (escuderia.getAutos().contains(auto)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Busca una escudería por su nombre
     */
    private Escuderia buscarEscuderiaPorNombre(String nombre) {
        List<Escuderia> escuderias = gestor.getEscuderias();
        for (Escuderia escuderia : escuderias) {
            if (escuderia.getNombre().equals(nombre)) {
                return escuderia;
            }
        }
        return null;
    }

    /**
     * Valida los campos del formulario de auto
     */
    private void validarCamposAuto() {
        if (txtModelo.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo es obligatorio");
        }
        if (txtChasis.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El chasis es obligatorio");
        }
        if (txtMotor.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El motor es obligatorio");
        }
        if (txtAño.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El año es obligatorio");
        }
        if (txtNumeroChasis.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de chasis es obligatorio");
        }
        if (txtPeso.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El peso es obligatorio");
        }
        if (txtPotencia.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("La potencia es obligatoria");
        }

        // Validar que el número de chasis sea único
        String numeroChasis = txtNumeroChasis.getText().trim();
        List<Auto> autos = gestor.getAutos();
        for (Auto auto : autos) {
            if (auto.getNumeroChasis().equals(numeroChasis) && auto != autoSeleccionado) {
                throw new IllegalArgumentException("Ya existe un auto con ese número de chasis");
            }
        }
    }

    /**
     * Limpia el formulario
     */
    private void limpiarFormulario() {
        txtModelo.setText("");
        txtChasis.setText("");
        txtMotor.setText("");
        txtAño.setText("");
        txtNumeroChasis.setText("");
        txtPeso.setText("");
        txtPotencia.setText("");
        cmbEscuderia.setSelectedIndex(0);
        autoSeleccionado = null;
        tablaAutos.clearSelection();
    }
}