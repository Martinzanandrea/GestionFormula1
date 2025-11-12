package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana para la gestión de autos
 */
public class VentanaAutos extends JFrame {
    private GestorFormula1 gestor;
    private JTable tablaAutos;
    private DefaultTableModel modeloTabla;
    private JTextField txtModelo, txtChasis, txtMotor, txtAño, txtNumeroChasis, txtPeso, txtPotencia;

    /**
     * Constructor de la ventana de autos
     * 
     * @param gestor Gestor principal del sistema
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
     * Crea el panel de formulario para agregar/editar autos
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Auto"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Primera fila
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1;
        txtModelo = new JTextField(15);
        panel.add(txtModelo, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Chasis:"), gbc);
        gbc.gridx = 3;
        txtChasis = new JTextField(15);
        panel.add(txtChasis, gbc);

        // Segunda fila
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Motor:"), gbc);
        gbc.gridx = 1;
        txtMotor = new JTextField(15);
        panel.add(txtMotor, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Año:"), gbc);
        gbc.gridx = 3;
        txtAño = new JTextField(15);
        panel.add(txtAño, gbc);

        // Tercera fila
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Número de Chasis:"), gbc);
        gbc.gridx = 1;
        txtNumeroChasis = new JTextField(15);
        panel.add(txtNumeroChasis, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Peso (kg):"), gbc);
        gbc.gridx = 3;
        txtPeso = new JTextField(15);
        panel.add(txtPeso, gbc);

        // Cuarta fila
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Potencia (HP):"), gbc);
        gbc.gridx = 1;
        txtPotencia = new JTextField(15);
        panel.add(txtPotencia, gbc);

        return panel;
    }

    /**
     * Crea el panel de tabla para mostrar los autos
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Autos"));

        // Crear modelo de tabla
        String[] columnas = { "Modelo", "Chasis", "Motor", "Año", "Nº Chasis", "Peso (kg)", "Potencia (HP)",
                "Escudería" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaAutos = new JTable(modeloTabla);
        tablaAutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Agregar listener para selección
        tablaAutos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarAutoSeleccionado();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaAutos);
        scrollPane.setPreferredSize(new Dimension(900, 350));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel de botones
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnCerrar = new JButton("Cerrar");

        // Aplicar estilos modernos a los botones
        aplicarEstiloBoton(btnAgregar, new Color(0, 123, 255)); // Azul
        aplicarEstiloBoton(btnModificar, new Color(0, 123, 255)); // Azul
        aplicarEstiloBoton(btnEliminar, new Color(0, 123, 255)); // Azul
        aplicarEstiloBoton(btnLimpiar, new Color(0, 123, 255)); // Azul
        aplicarEstiloBoton(btnCerrar, new Color(0, 123, 255)); // Azul

        // Agregar listeners
        btnAgregar.addActionListener(e -> agregarAuto());
        btnModificar.addActionListener(e -> modificarAuto());
        btnEliminar.addActionListener(e -> eliminarAuto());
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
        setTitle("Gestión de Autos");
        setSize(1000, 650);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Carga los datos iniciales
     */
    private void cargarDatos() {
        actualizarTablaAutos();
    }

    /**
     * Actualiza la tabla de autos
     */
    private void actualizarTablaAutos() {
        modeloTabla.setRowCount(0);
        for (Auto auto : gestor.getAutos()) {
            // Buscar escudería del auto
            String escuderia = "Sin escudería";
            for (Escuderia e : gestor.getEscuderias()) {
                if (e.getAutos().contains(auto)) {
                    escuderia = e.getNombre();
                    break;
                }
            }

            Object[] fila = {
                    auto.getModelo(),
                    auto.getChasis(),
                    auto.getMotor(),
                    auto.getAño(),
                    auto.getNumeroChasis(),
                    auto.getPeso(),
                    auto.getPotencia(),
                    escuderia
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Carga los datos del auto seleccionado en el formulario
     */
    private void cargarAutoSeleccionado() {
        int filaSeleccionada = tablaAutos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String numeroChasis = (String) modeloTabla.getValueAt(filaSeleccionada, 4);
            Auto auto = gestor.getAutos().stream()
                    .filter(a -> a.getNumeroChasis().equals(numeroChasis))
                    .findFirst().orElse(null);

            if (auto != null) {
                txtModelo.setText(auto.getModelo());
                txtChasis.setText(auto.getChasis());
                txtMotor.setText(auto.getMotor());
                txtAño.setText(String.valueOf(auto.getAño()));
                txtNumeroChasis.setText(auto.getNumeroChasis());
                txtPeso.setText(String.valueOf(auto.getPeso()));
                txtPotencia.setText(String.valueOf(auto.getPotencia()));
            }
        }
    }

    /**
     * Agrega un nuevo auto
     */
    private void agregarAuto() {
        try {
            validarCampos();

            String modelo = txtModelo.getText().trim();
            String chasis = txtChasis.getText().trim();
            String motor = txtMotor.getText().trim();
            int año = Integer.parseInt(txtAño.getText().trim());
            String numeroChasis = txtNumeroChasis.getText().trim();
            double peso = Double.parseDouble(txtPeso.getText().trim());
            int potencia = Integer.parseInt(txtPotencia.getText().trim());

            Auto nuevoAuto = new Auto(modelo, chasis, motor, año, numeroChasis, peso, potencia);
            gestor.registrarAuto(nuevoAuto);

            actualizarTablaAutos();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this, "Auto agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar auto: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica el auto seleccionado
     */
    private void modificarAuto() {
        int filaSeleccionada = tablaAutos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un auto para modificar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            validarCampos();

            String numeroChasisOriginal = (String) modeloTabla.getValueAt(filaSeleccionada, 4);
            Auto auto = gestor.getAutos().stream()
                    .filter(a -> a.getNumeroChasis().equals(numeroChasisOriginal))
                    .findFirst().orElse(null);

            if (auto != null) {
                auto.setModelo(txtModelo.getText().trim());
                auto.setChasis(txtChasis.getText().trim());
                auto.setMotor(txtMotor.getText().trim());
                auto.setAño(Integer.parseInt(txtAño.getText().trim()));

                // Verificar cambio de número de chasis
                String nuevoNumeroChasis = txtNumeroChasis.getText().trim();
                if (!nuevoNumeroChasis.equals(auto.getNumeroChasis())) {
                    // Verificar que el nuevo número no esté ocupado
                    boolean numeroOcupado = gestor.getAutos().stream()
                            .anyMatch(a -> a.getNumeroChasis().equals(nuevoNumeroChasis));
                    if (numeroOcupado) {
                        throw new IllegalArgumentException(
                                "El número de chasis " + nuevoNumeroChasis + " ya está ocupado");
                    }
                    auto.setNumeroChasis(nuevoNumeroChasis);
                }

                auto.setPeso(Double.parseDouble(txtPeso.getText().trim()));
                auto.setPotencia(Integer.parseInt(txtPotencia.getText().trim()));

                actualizarTablaAutos();
                JOptionPane.showMessageDialog(this, "Auto modificado exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar auto: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el auto seleccionado
     */
    private void eliminarAuto() {
        int filaSeleccionada = tablaAutos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un auto para eliminar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar si el auto está en uso en alguna carrera
        String numeroChasis = (String) modeloTabla.getValueAt(filaSeleccionada, 4);
        Auto auto = gestor.getAutos().stream()
                .filter(a -> a.getNumeroChasis().equals(numeroChasis))
                .findFirst().orElse(null);

        if (auto != null) {
            // Verificar si está en uso en carreras
            boolean enUso = gestor.getGrandesPremios().stream()
                    .flatMap(gp -> gp.getParticipaciones().stream())
                    .anyMatch(p -> p.getAuto().equals(auto));

            if (enUso) {
                JOptionPane.showMessageDialog(this,
                        "No se puede eliminar el auto porque está siendo utilizado en carreras",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este auto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (auto != null) {
                // Remover de escuderías
                for (Escuderia escuderia : gestor.getEscuderias()) {
                    escuderia.getAutos().remove(auto);
                }

                gestor.getAutos().remove(auto);
                actualizarTablaAutos();
                limpiarFormulario();

                JOptionPane.showMessageDialog(this, "Auto eliminado exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
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
        tablaAutos.clearSelection();
    }

    /**
     * Valida los campos del formulario
     */
    private void validarCampos() {
        if (txtModelo.getText().trim().isEmpty() ||
                txtChasis.getText().trim().isEmpty() ||
                txtMotor.getText().trim().isEmpty() ||
                txtAño.getText().trim().isEmpty() ||
                txtNumeroChasis.getText().trim().isEmpty() ||
                txtPeso.getText().trim().isEmpty() ||
                txtPotencia.getText().trim().isEmpty()) {

            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        try {
            int año = Integer.parseInt(txtAño.getText().trim());
            if (año < 1950 || año > 2030) {
                throw new IllegalArgumentException("El año debe estar entre 1950 y 2030");
            }

            double peso = Double.parseDouble(txtPeso.getText().trim());
            if (peso < 500 || peso > 1000) {
                throw new IllegalArgumentException("El peso debe estar entre 500 y 1000 kg");
            }

            int potencia = Integer.parseInt(txtPotencia.getText().trim());
            if (potencia < 500 || potencia > 2000) {
                throw new IllegalArgumentException("La potencia debe estar entre 500 y 2000 HP");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los campos numéricos deben contener valores válidos");
        }
    }

    /**
     * Aplica estilo moderno a los botones con alta visibilidad
     */
    private void aplicarEstiloBoton(JButton boton, Color color) {
        // Interfaz simple blanco y negro
        boton.setBackground(Color.WHITE);
        boton.setForeground(Color.BLACK);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setFocusPainted(true);
    }
}