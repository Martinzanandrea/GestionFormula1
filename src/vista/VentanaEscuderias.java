package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana simple para la gestión básica de escuderías
 * Solo maneja nombre de escudería y país
 */
public class VentanaEscuderias extends JFrame {
    private GestorFormula1 gestor;
    private JTable tablaEscuderias;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtPais;
    private JButton btnAgregar, btnModificar, btnEliminar, btnLimpiar, btnCerrar;
    private Escuderia escuderiaSeleccionada;

    /**
     * Constructor de la ventana de escuderías
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
        panel.setBorder(BorderFactory.createTitledBorder("Datos de Escudería"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar campos
        txtNombre = new JTextField(20);
        txtPais = new JTextField(20);

        // Agregar componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("País:"), gbc);
        gbc.gridx = 1;
        panel.add(txtPais, gbc);

        return panel;
    }

    /**
     * Crea el panel de tabla
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Escuderías Registradas"));

        // Crear modelo de tabla
        String[] columnas = { "Nombre", "País" };
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
        scrollPane.setPreferredSize(new Dimension(500, 200));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de botones
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnCerrar = new JButton("Cerrar");

        // Configurar acciones
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
     * Configura las propiedades de la ventana
     */
    private void configurarVentana() {
        setTitle("Gestión de Escuderías");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Carga los datos iniciales
     */
    private void cargarDatos() {
        actualizarTablaEscuderias();
    }

    /**
     * Actualiza la tabla de escuderías
     */
    private void actualizarTablaEscuderias() {
        modeloTabla.setRowCount(0);
        List<Escuderia> escuderias = gestor.getEscuderias();

        for (Escuderia escuderia : escuderias) {
            Object[] fila = {
                    escuderia.getNombre(),
                    escuderia.getPais().getNombre()
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Carga la escudería seleccionada en el formulario
     */
    private void cargarEscuderiaSeleccionada() {
        int filaSeleccionada = tablaEscuderias.getSelectedRow();
        if (filaSeleccionada >= 0) {
            List<Escuderia> escuderias = gestor.getEscuderias();
            escuderiaSeleccionada = escuderias.get(filaSeleccionada);

            txtNombre.setText(escuderiaSeleccionada.getNombre());
            txtPais.setText(escuderiaSeleccionada.getPais().getNombre());
        }
    }

    /**
     * Agrega una nueva escudería
     */
    private void agregarEscuderia() {
        try {
            validarCampos();

            String nombre = txtNombre.getText().trim();
            String nombrePais = txtPais.getText().trim();

            // Crear o buscar país
            Pais pais = new Pais(nombrePais, nombrePais.substring(0, Math.min(3, nombrePais.length())).toUpperCase());

            Escuderia nuevaEscuderia = new Escuderia(nombre, pais);
            gestor.registrarEscuderia(nuevaEscuderia);

            actualizarTablaEscuderias();
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

            String nombre = txtNombre.getText().trim();
            String nombrePais = txtPais.getText().trim();

            escuderiaSeleccionada.setNombre(nombre);

            // Crear o buscar país
            Pais pais = new Pais(nombrePais, nombrePais.substring(0, Math.min(3, nombrePais.length())).toUpperCase());
            escuderiaSeleccionada.setPais(pais);

            actualizarTablaEscuderias();

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
        if (escuderiaSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una escudería para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar la escudería: " + escuderiaSeleccionada.getNombre() + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                gestor.getEscuderias().remove(escuderiaSeleccionada);
                actualizarTablaEscuderias();
                limpiarFormulario();

                JOptionPane.showMessageDialog(this,
                        "Escudería eliminada exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar escudería: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Limpia el formulario
     */
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtPais.setText("");
        escuderiaSeleccionada = null;
        tablaEscuderias.clearSelection();
    }

    /**
     * Valida los campos del formulario
     */
    private void validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la escudería es obligatorio");
        }
        if (txtPais.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El país es obligatorio");
        }
    }
}