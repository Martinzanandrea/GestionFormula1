package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana para la gestión de circuitos
 */
public class VentanaCircuitos extends JFrame {
    private GestorFormula1 gestor;
    private JTable tablaCircuitos;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtLongitud, txtNumeroCurvas, txtTipo;
    private JComboBox<Pais> comboPaises;

    /**
     * Constructor de la ventana de circuitos
     * 
     * @param gestor Gestor principal del sistema
     */
    public VentanaCircuitos(GestorFormula1 gestor) {
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
     * Crea el panel de formulario para agregar/editar circuitos
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Circuito"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Primera fila
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("País:"), gbc);
        gbc.gridx = 3;
        comboPaises = new JComboBox<>();
        panel.add(comboPaises, gbc);

        // Segunda fila
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Longitud (km):"), gbc);
        gbc.gridx = 1;
        txtLongitud = new JTextField(20);
        panel.add(txtLongitud, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Número de Curvas:"), gbc);
        gbc.gridx = 3;
        txtNumeroCurvas = new JTextField(20);
        panel.add(txtNumeroCurvas, gbc);

        // Tercera fila
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        txtTipo = new JTextField(20);
        panel.add(txtTipo, gbc);

        // Agregar etiqueta de ayuda para tipo
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JLabel lblAyuda = new JLabel("(Ej: Urbano, Permanente, Temporal)");
        lblAyuda.setFont(lblAyuda.getFont().deriveFont(Font.ITALIC, 10f));
        lblAyuda.setForeground(Color.GRAY);
        panel.add(lblAyuda, gbc);

        return panel;
    }

    /**
     * Crea el panel de tabla para mostrar los circuitos
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Circuitos"));

        // Crear modelo de tabla
        String[] columnas = { "Nombre", "País", "Longitud (km)", "Curvas", "Tipo", "Carreras Realizadas" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCircuitos = new JTable(modeloTabla);
        tablaCircuitos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Agregar listener para selección
        tablaCircuitos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarCircuitoSeleccionado();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaCircuitos);
        scrollPane.setPreferredSize(new Dimension(800, 350));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel de botones
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnEstadisticas = new JButton("Estadísticas");
        JButton btnCerrar = new JButton("Cerrar");

        // Agregar listeners
        btnAgregar.addActionListener(e -> agregarCircuito());
        btnModificar.addActionListener(e -> modificarCircuito());
        btnEliminar.addActionListener(e -> eliminarCircuito());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnAgregar);
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        panel.add(btnEstadisticas);
        panel.add(btnCerrar);

        return panel;
    }

    /**
     * Configura la ventana
     */
    private void configurarVentana() {
        setTitle("Gestión de Circuitos");
        setSize(900, 600);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Carga los datos iniciales
     */
    private void cargarDatos() {
        cargarPaises();
        actualizarTablaCircuitos();
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
     * Actualiza la tabla de circuitos
     */
    private void actualizarTablaCircuitos() {
        modeloTabla.setRowCount(0);
        for (Circuito circuito : gestor.getCircuitos()) {
            int carrerasRealizadas = gestor.getCarrerasEnCircuito(circuito);

            Object[] fila = {
                    circuito.getNombre(),
                    circuito.getPais().getNombre(),
                    circuito.getLongitud(),
                    circuito.getNumeroCurvas(),
                    circuito.getTipo(),
                    carrerasRealizadas
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Carga los datos del circuito seleccionado en el formulario
     */
    private void cargarCircuitoSeleccionado() {
        int filaSeleccionada = tablaCircuitos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String paisNombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

            Circuito circuito = gestor.getCircuitos().stream()
                    .filter(c -> c.getNombre().equals(nombre) && c.getPais().getNombre().equals(paisNombre))
                    .findFirst().orElse(null);

            if (circuito != null) {
                txtNombre.setText(circuito.getNombre());
                comboPaises.setSelectedItem(circuito.getPais());
                txtLongitud.setText(String.valueOf(circuito.getLongitud()));
                txtNumeroCurvas.setText(String.valueOf(circuito.getNumeroCurvas()));
                txtTipo.setText(circuito.getTipo());
            }
        }
    }

    /**
     * Agrega un nuevo circuito
     */
    private void agregarCircuito() {
        try {
            validarCampos();

            String nombre = txtNombre.getText().trim();
            Pais pais = (Pais) comboPaises.getSelectedItem();
            double longitud = Double.parseDouble(txtLongitud.getText().trim());
            int numeroCurvas = Integer.parseInt(txtNumeroCurvas.getText().trim());
            String tipo = txtTipo.getText().trim();

            Circuito nuevoCircuito = new Circuito(nombre, pais, longitud, numeroCurvas, tipo);
            gestor.registrarCircuito(nuevoCircuito);

            actualizarTablaCircuitos();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this, "Circuito agregado exitosamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar circuito: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica el circuito seleccionado
     */
    private void modificarCircuito() {
        int filaSeleccionada = tablaCircuitos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un circuito para modificar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            validarCampos();

            String nombreOriginal = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String paisNombreOriginal = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

            Circuito circuito = gestor.getCircuitos().stream()
                    .filter(c -> c.getNombre().equals(nombreOriginal)
                            && c.getPais().getNombre().equals(paisNombreOriginal))
                    .findFirst().orElse(null);

            if (circuito != null) {
                circuito.setNombre(txtNombre.getText().trim());
                circuito.setPais((Pais) comboPaises.getSelectedItem());
                circuito.setLongitud(Double.parseDouble(txtLongitud.getText().trim()));
                circuito.setNumeroCurvas(Integer.parseInt(txtNumeroCurvas.getText().trim()));
                circuito.setTipo(txtTipo.getText().trim());

                actualizarTablaCircuitos();
                JOptionPane.showMessageDialog(this, "Circuito modificado exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar circuito: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el circuito seleccionado
     */
    private void eliminarCircuito() {
        int filaSeleccionada = tablaCircuitos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un circuito para eliminar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        String paisNombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        Circuito circuito = gestor.getCircuitos().stream()
                .filter(c -> c.getNombre().equals(nombre) && c.getPais().getNombre().equals(paisNombre))
                .findFirst().orElse(null);

        if (circuito != null) {
            // Verificar si está en uso en carreras
            boolean enUso = gestor.getGrandesPremios().stream()
                    .anyMatch(gp -> gp.getCircuito().equals(circuito));

            if (enUso) {
                JOptionPane.showMessageDialog(this,
                        "No se puede eliminar el circuito porque tiene carreras asociadas",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este circuito?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (circuito != null) {
                gestor.getCircuitos().remove(circuito);
                actualizarTablaCircuitos();
                limpiarFormulario();

                JOptionPane.showMessageDialog(this, "Circuito eliminado exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Muestra estadísticas del circuito seleccionado
     */
    private void mostrarEstadisticas() {
        int filaSeleccionada = tablaCircuitos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un circuito para ver estadísticas", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        String paisNombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        Circuito circuito = gestor.getCircuitos().stream()
                .filter(c -> c.getNombre().equals(nombre) && c.getPais().getNombre().equals(paisNombre))
                .findFirst().orElse(null);

        if (circuito != null) {
            int carrerasRealizadas = gestor.getCarrerasEnCircuito(circuito);

            StringBuilder estadisticas = new StringBuilder();
            estadisticas.append("ESTADÍSTICAS DEL CIRCUITO\n");
            estadisticas.append("=========================\n\n");
            estadisticas.append("Nombre: ").append(circuito.getNombre()).append("\n");
            estadisticas.append("País: ").append(circuito.getPais().getNombre()).append("\n");
            estadisticas.append("Longitud: ").append(circuito.getLongitud()).append(" km\n");
            estadisticas.append("Número de curvas: ").append(circuito.getNumeroCurvas()).append("\n");
            estadisticas.append("Tipo: ").append(circuito.getTipo()).append("\n");
            estadisticas.append("Carreras realizadas: ").append(carrerasRealizadas).append("\n\n");

            if (carrerasRealizadas > 0) {
                estadisticas.append("PILOTOS QUE HAN CORRIDO EN ESTE CIRCUITO:\n");
                estadisticas.append("=========================================\n");

                for (Piloto piloto : gestor.getPilotos()) {
                    int participaciones = gestor.getParticipacionesPilotoEnCircuito(piloto, circuito);
                    if (participaciones > 0) {
                        estadisticas.append("• ").append(piloto.getNombreCompleto())
                                .append(": ").append(participaciones).append(" carrera(s)\n");
                    }
                }
            }

            JTextArea textArea = new JTextArea(estadisticas.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 400));

            JOptionPane.showMessageDialog(this, scrollPane,
                    "Estadísticas - " + circuito.getNombre(),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Limpia el formulario
     */
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtLongitud.setText("");
        txtNumeroCurvas.setText("");
        txtTipo.setText("");
        if (comboPaises.getItemCount() > 0) {
            comboPaises.setSelectedIndex(0);
        }
        tablaCircuitos.clearSelection();
    }

    /**
     * Valida los campos del formulario
     */
    private void validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
                txtLongitud.getText().trim().isEmpty() ||
                txtNumeroCurvas.getText().trim().isEmpty() ||
                txtTipo.getText().trim().isEmpty()) {

            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        if (comboPaises.getSelectedItem() == null) {
            throw new IllegalArgumentException("Debe seleccionar un país");
        }

        try {
            double longitud = Double.parseDouble(txtLongitud.getText().trim());
            if (longitud <= 0 || longitud > 20) {
                throw new IllegalArgumentException("La longitud debe estar entre 0 y 20 km");
            }

            int numeroCurvas = Integer.parseInt(txtNumeroCurvas.getText().trim());
            if (numeroCurvas < 1 || numeroCurvas > 50) {
                throw new IllegalArgumentException("El número de curvas debe estar entre 1 y 50");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los campos numéricos deben contener valores válidos");
        }
    }
}