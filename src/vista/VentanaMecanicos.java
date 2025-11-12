package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para la gestión de mecánicos
 */
public class VentanaMecanicos extends JFrame {
    private GestorFormula1 gestor;
    private JTable tablaMecanicos;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtApellido, txtExperiencia;
    private JList<String> listaEspecialidades;
    private DefaultListModel<String> modeloEspecialidades;
    private JTextField txtNuevaEspecialidad;

    /**
     * Constructor de la ventana de mecánicos
     * 
     * @param gestor Gestor principal del sistema
     */
    public VentanaMecanicos(GestorFormula1 gestor) {
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
     * Crea el panel de formulario para agregar/editar mecánicos
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Mecánico"));

        // Panel de datos básicos
        JPanel panelDatos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        panelDatos.add(txtNombre, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 3;
        txtApellido = new JTextField(15);
        panelDatos.add(txtApellido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(new JLabel("Experiencia (años):"), gbc);
        gbc.gridx = 1;
        txtExperiencia = new JTextField(15);
        panelDatos.add(txtExperiencia, gbc);

        // Panel de especialidades
        JPanel panelEspecialidades = new JPanel(new BorderLayout());
        panelEspecialidades.setBorder(BorderFactory.createTitledBorder("Especialidades"));

        modeloEspecialidades = new DefaultListModel<>();
        listaEspecialidades = new JList<>(modeloEspecialidades);
        listaEspecialidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollEspecialidades = new JScrollPane(listaEspecialidades);
        scrollEspecialidades.setPreferredSize(new Dimension(200, 120));

        // Panel para agregar especialidades
        JPanel panelAgregarEsp = new JPanel(new BorderLayout());
        txtNuevaEspecialidad = new JTextField();
        JButton btnAgregarEsp = new JButton("Agregar");
        JButton btnRemoverEsp = new JButton("Remover");

        btnAgregarEsp.addActionListener(e -> agregarEspecialidad());
        btnRemoverEsp.addActionListener(e -> removerEspecialidad());

        JPanel panelBotonesEsp = new JPanel(new FlowLayout());
        panelBotonesEsp.add(btnAgregarEsp);
        panelBotonesEsp.add(btnRemoverEsp);

        panelAgregarEsp.add(new JLabel("Nueva especialidad:"), BorderLayout.WEST);
        panelAgregarEsp.add(txtNuevaEspecialidad, BorderLayout.CENTER);
        panelAgregarEsp.add(panelBotonesEsp, BorderLayout.EAST);

        panelEspecialidades.add(scrollEspecialidades, BorderLayout.CENTER);
        panelEspecialidades.add(panelAgregarEsp, BorderLayout.SOUTH);

        panel.add(panelDatos, BorderLayout.NORTH);
        panel.add(panelEspecialidades, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de tabla para mostrar los mecánicos
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Mecánicos"));

        // Crear modelo de tabla
        String[] columnas = { "Nombre", "Apellido", "Experiencia", "Especialidades", "Escudería" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMecanicos = new JTable(modeloTabla);
        tablaMecanicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Agregar listener para selección
        tablaMecanicos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarMecanicoSeleccionado();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaMecanicos);
        scrollPane.setPreferredSize(new Dimension(800, 250));

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
        JButton btnCerrar = new JButton("Cerrar");

        // Agregar listeners
        btnAgregar.addActionListener(e -> agregarMecanico());
        btnModificar.addActionListener(e -> modificarMecanico());
        btnEliminar.addActionListener(e -> eliminarMecanico());
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
        setTitle("Gestión de Mecánicos");
        setSize(900, 700);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Carga los datos iniciales
     */
    private void cargarDatos() {
        actualizarTablaMecanicos();
    }

    /**
     * Actualiza la tabla de mecánicos
     */
    private void actualizarTablaMecanicos() {
        modeloTabla.setRowCount(0);
        for (Mecanico mecanico : gestor.getMecanicos()) {
            // Buscar escudería del mecánico
            String escuderia = "Sin escudería";
            for (Escuderia e : gestor.getEscuderias()) {
                if (e.getMecanicos().contains(mecanico)) {
                    escuderia = e.getNombre();
                    break;
                }
            }

            Object[] fila = {
                    mecanico.getNombre(),
                    mecanico.getApellido(),
                    mecanico.getExperiencia(),
                    mecanico.getEspecialidadesString(),
                    escuderia
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Carga los datos del mecánico seleccionado en el formulario
     */
    private void cargarMecanicoSeleccionado() {
        int filaSeleccionada = tablaMecanicos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String apellido = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

            Mecanico mecanico = gestor.getMecanicos().stream()
                    .filter(m -> m.getNombre().equals(nombre) && m.getApellido().equals(apellido))
                    .findFirst().orElse(null);

            if (mecanico != null) {
                txtNombre.setText(mecanico.getNombre());
                txtApellido.setText(mecanico.getApellido());
                txtExperiencia.setText(String.valueOf(mecanico.getExperiencia()));

                // Cargar especialidades
                modeloEspecialidades.clear();
                for (String especialidad : mecanico.getEspecialidades()) {
                    modeloEspecialidades.addElement(especialidad);
                }
            }
        }
    }

    /**
     * Agrega una especialidad a la lista
     */
    private void agregarEspecialidad() {
        String especialidad = txtNuevaEspecialidad.getText().trim();
        if (!especialidad.isEmpty() && !modeloEspecialidades.contains(especialidad)) {
            modeloEspecialidades.addElement(especialidad);
            txtNuevaEspecialidad.setText("");
        } else if (modeloEspecialidades.contains(especialidad)) {
            JOptionPane.showMessageDialog(this, "La especialidad ya está en la lista", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Remueve una especialidad de la lista
     */
    private void removerEspecialidad() {
        String especialidadSeleccionada = listaEspecialidades.getSelectedValue();
        if (especialidadSeleccionada != null) {
            modeloEspecialidades.removeElement(especialidadSeleccionada);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una especialidad para remover", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Agrega un nuevo mecánico
     */
    private void agregarMecanico() {
        try {
            validarCampos();

            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            int experiencia = Integer.parseInt(txtExperiencia.getText().trim());

            Mecanico nuevoMecanico = new Mecanico(nombre, apellido, experiencia);

            // Agregar especialidades
            for (int i = 0; i < modeloEspecialidades.size(); i++) {
                nuevoMecanico.agregarEspecialidad(modeloEspecialidades.getElementAt(i));
            }

            gestor.registrarMecanico(nuevoMecanico);

            actualizarTablaMecanicos();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this, "Mecánico agregado exitosamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar mecánico: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica el mecánico seleccionado
     */
    private void modificarMecanico() {
        int filaSeleccionada = tablaMecanicos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un mecánico para modificar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            validarCampos();

            String nombreOriginal = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String apellidoOriginal = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

            Mecanico mecanico = gestor.getMecanicos().stream()
                    .filter(m -> m.getNombre().equals(nombreOriginal) && m.getApellido().equals(apellidoOriginal))
                    .findFirst().orElse(null);

            if (mecanico != null) {
                mecanico.setNombre(txtNombre.getText().trim());
                mecanico.setApellido(txtApellido.getText().trim());
                mecanico.setExperiencia(Integer.parseInt(txtExperiencia.getText().trim()));

                // Actualizar especialidades
                // Primero remover todas las especialidades existentes
                List<String> especialidadesActuales = mecanico.getEspecialidades();
                for (String esp : especialidadesActuales) {
                    mecanico.removerEspecialidad(esp);
                }

                // Agregar las nuevas especialidades
                for (int i = 0; i < modeloEspecialidades.size(); i++) {
                    mecanico.agregarEspecialidad(modeloEspecialidades.getElementAt(i));
                }

                actualizarTablaMecanicos();
                JOptionPane.showMessageDialog(this, "Mecánico modificado exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar mecánico: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el mecánico seleccionado
     */
    private void eliminarMecanico() {
        int filaSeleccionada = tablaMecanicos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un mecánico para eliminar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este mecánico?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String apellido = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

            Mecanico mecanico = gestor.getMecanicos().stream()
                    .filter(m -> m.getNombre().equals(nombre) && m.getApellido().equals(apellido))
                    .findFirst().orElse(null);

            if (mecanico != null) {
                // Remover de escuderías
                for (Escuderia escuderia : gestor.getEscuderias()) {
                    escuderia.getMecanicos().remove(mecanico);
                }

                gestor.getMecanicos().remove(mecanico);
                actualizarTablaMecanicos();
                limpiarFormulario();

                JOptionPane.showMessageDialog(this, "Mecánico eliminado exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Limpia el formulario
     */
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtExperiencia.setText("");
        txtNuevaEspecialidad.setText("");
        modeloEspecialidades.clear();
        tablaMecanicos.clearSelection();
    }

    /**
     * Valida los campos del formulario
     */
    private void validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
                txtApellido.getText().trim().isEmpty() ||
                txtExperiencia.getText().trim().isEmpty()) {

            throw new IllegalArgumentException("Todos los campos básicos son obligatorios");
        }

        try {
            int experiencia = Integer.parseInt(txtExperiencia.getText().trim());
            if (experiencia < 0 || experiencia > 50) {
                throw new IllegalArgumentException("La experiencia debe estar entre 0 y 50 años");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La experiencia debe ser un valor numérico válido");
        }

        if (modeloEspecialidades.isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos una especialidad");
        }
    }
}