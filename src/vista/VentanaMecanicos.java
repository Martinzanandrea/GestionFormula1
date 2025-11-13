package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

/**
 * Ventana completa para la gestión de mecánicos de Fórmula 1
 * Permite crear mecánicos, asignar/desasignar de escuderías y gestionar
 * especialidades
 */
public class VentanaMecanicos extends JFrame {
    private GestorFormula1 gestor;
    private JTable tablaMecanicos;
    private DefaultTableModel modeloTabla;

    // Campos del formulario
    private JTextField txtDni, txtNombre, txtApellido, txtExperiencia;
    private JComboBox<String> cmbEscuderia;
    private JList<String> listaEspecialidades;
    private DefaultListModel<String> modeloEspecialidades;

    // Botones
    private JButton btnCrearMecanico, btnAsignarEscuderia, btnDesasignarEscuderia;
    private JButton btnAgregarEspecialidad, btnRemoverEspecialidad, btnActualizarEspecialidades, btnLimpiar, btnCerrar;

    // Mecánico seleccionado
    private Mecanico mecanicoSeleccionado;

    /**
     * Constructor de la ventana de mecánicos
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
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Gestión de Mecánicos"));

        // Panel izquierdo - datos personales
        JPanel panelDatos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar campos
        txtDni = new JTextField(15);
        txtNombre = new JTextField(15);
        txtApellido = new JTextField(15);
        txtExperiencia = new JTextField(15);
        cmbEscuderia = new JComboBox<>();

        // Primera fila
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        panelDatos.add(txtDni, gbc);
        gbc.gridx = 2;
        panelDatos.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3;
        panelDatos.add(txtNombre, gbc);

        // Segunda fila
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        panelDatos.add(txtApellido, gbc);
        gbc.gridx = 2;
        panelDatos.add(new JLabel("Experiencia (años):"), gbc);
        gbc.gridx = 3;
        panelDatos.add(txtExperiencia, gbc);

        // Tercera fila
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDatos.add(new JLabel("Escudería:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        panelDatos.add(cmbEscuderia, gbc);

        // Panel derecho - especialidades
        JPanel panelEspecialidades = crearPanelEspecialidades();

        panel.add(panelDatos, BorderLayout.WEST);
        panel.add(panelEspecialidades, BorderLayout.EAST);

        return panel;
    }

    /**
     * Crea el panel de especialidades
     */
    private JPanel crearPanelEspecialidades() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Especialidades"));

        // Lista de especialidades
        modeloEspecialidades = new DefaultListModel<>();
        listaEspecialidades = new JList<>(modeloEspecialidades);
        listaEspecialidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEspecialidades.setVisibleRowCount(4);

        JScrollPane scrollEspecialidades = new JScrollPane(listaEspecialidades);
        scrollEspecialidades.setPreferredSize(new Dimension(200, 100));

        // ComboBox para agregar especialidades
        JComboBox<String> cmbNuevaEspecialidad = new JComboBox<>();
        for (Especialidad esp : Especialidad.values()) {
            cmbNuevaEspecialidad.addItem(esp.getDescripcion());
        }

        // Botones de especialidades
        btnAgregarEspecialidad = new JButton("Agregar");
        btnRemoverEspecialidad = new JButton("Remover");

        btnAgregarEspecialidad.addActionListener(e -> {
            String especialidadSeleccionada = (String) cmbNuevaEspecialidad.getSelectedItem();
            if (especialidadSeleccionada != null && !modeloEspecialidades.contains(especialidadSeleccionada)) {
                modeloEspecialidades.addElement(especialidadSeleccionada);
            }
        });

        btnRemoverEspecialidad.addActionListener(e -> {
            String especialidadSeleccionada = listaEspecialidades.getSelectedValue();
            if (especialidadSeleccionada != null) {
                modeloEspecialidades.removeElement(especialidadSeleccionada);
            }
        });

        JPanel panelControlesEsp = new JPanel(new FlowLayout());
        panelControlesEsp.add(cmbNuevaEspecialidad);
        panelControlesEsp.add(btnAgregarEspecialidad);
        panelControlesEsp.add(btnRemoverEspecialidad);

        panel.add(scrollEspecialidades, BorderLayout.CENTER);
        panel.add(panelControlesEsp, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel de tabla
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Mecánicos Registrados"));

        // Crear modelo de tabla
        String[] columnas = { "DNI", "Nombre", "Apellido", "Experiencia", "Especialidades", "Escudería", "Estado" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMecanicos = new JTable(modeloTabla);
        tablaMecanicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

        btnCrearMecanico = new JButton("Crear Mecánico");
        btnAsignarEscuderia = new JButton("Asignar a Escudería");
        btnDesasignarEscuderia = new JButton("Desasignar de Escudería");
        btnActualizarEspecialidades = new JButton("Actualizar Especialidades");
        btnLimpiar = new JButton("Limpiar");
        btnCerrar = new JButton("Cerrar");

        // Configurar acciones
        btnCrearMecanico.addActionListener(e -> crearMecanico());
        btnAsignarEscuderia.addActionListener(e -> asignarMecanicoAEscuderia());
        btnDesasignarEscuderia.addActionListener(e -> desasignarMecanicoDeEscuderia());
        btnActualizarEspecialidades.addActionListener(e -> actualizarEspecialidades());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnCrearMecanico);
        panel.add(btnAsignarEscuderia);
        panel.add(btnDesasignarEscuderia);
        panel.add(btnActualizarEspecialidades);
        panel.add(btnLimpiar);
        panel.add(btnCerrar);

        return panel;
    }

    /**
     * Configura las propiedades de la ventana
     */
    private void configurarVentana() {
        setTitle("Gestión de Mecánicos");
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
        actualizarTablaMecanicos();
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
     * Actualiza la tabla de mecánicos
     */
    private void actualizarTablaMecanicos() {
        modeloTabla.setRowCount(0);
        List<Mecanico> mecanicos = gestor.getMecanicos();
        List<Escuderia> escuderias = gestor.getEscuderias();

        for (Mecanico mecanico : mecanicos) {
            String escuderiaAsignada = "Libre";

            // Buscar si el mecánico está asignado a alguna escudería
            for (Escuderia escuderia : escuderias) {
                if (escuderia.getMecanicos().contains(mecanico)) {
                    escuderiaAsignada = escuderia.getNombre();
                    break;
                }
            }

            String especialidades = String.join(", ",
                    mecanico.getEspecialidades().stream()
                            .map(Especialidad::getDescripcion)
                            .toArray(String[]::new));

            Object[] fila = {
                    mecanico.getDni(),
                    mecanico.getNombre(),
                    mecanico.getApellido(),
                    mecanico.getExperiencia() + " años",
                    especialidades.isEmpty() ? "Sin especialidades" : especialidades,
                    escuderiaAsignada,
                    escuderiaAsignada.equals("Libre") ? "Disponible" : "Asignado"
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Carga el mecánico seleccionado en el formulario
     */
    private void cargarMecanicoSeleccionado() {
        int filaSeleccionada = tablaMecanicos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            List<Mecanico> mecanicos = gestor.getMecanicos();
            mecanicoSeleccionado = mecanicos.get(filaSeleccionada);

            txtDni.setText(mecanicoSeleccionado.getDni());
            txtNombre.setText(mecanicoSeleccionado.getNombre());
            txtApellido.setText(mecanicoSeleccionado.getApellido());
            txtExperiencia.setText(String.valueOf(mecanicoSeleccionado.getExperiencia()));

            // Cargar especialidades
            modeloEspecialidades.clear();
            for (Especialidad esp : mecanicoSeleccionado.getEspecialidades()) {
                modeloEspecialidades.addElement(esp.getDescripcion());
            }

            // Buscar la escudería asignada
            String escuderiaAsignada = "Sin asignar";
            List<Escuderia> escuderias = gestor.getEscuderias();
            for (Escuderia escuderia : escuderias) {
                if (escuderia.getMecanicos().contains(mecanicoSeleccionado)) {
                    escuderiaAsignada = escuderia.getNombre();
                    break;
                }
            }
            cmbEscuderia.setSelectedItem(escuderiaAsignada);
        }
    }

    /**
     * Crea un nuevo mecánico
     */
    private void crearMecanico() {
        try {
            validarCamposMecanico();

            String dni = txtDni.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            int experiencia = Integer.parseInt(txtExperiencia.getText().trim());

            Mecanico nuevoMecanico = new Mecanico(dni, nombre, apellido, experiencia);

            // Agregar especialidades seleccionadas
            for (int i = 0; i < modeloEspecialidades.getSize(); i++) {
                String descripcionEsp = modeloEspecialidades.getElementAt(i);
                Especialidad especialidad = buscarEspecialidadPorDescripcion(descripcionEsp);
                if (especialidad != null) {
                    nuevoMecanico.agregarEspecialidad(especialidad);
                }
            }

            gestor.registrarMecanico(nuevoMecanico);

            JOptionPane.showMessageDialog(this,
                    "Mecánico creado exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();
            actualizarTablaMecanicos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Error en el campo experiencia. Debe ser un número entero.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al crear el mecánico: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Asigna un mecánico a una escudería
     */
    private void asignarMecanicoAEscuderia() {
        if (mecanicoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un mecánico de la tabla.",
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

        // Verificar si el mecánico ya está asignado
        if (estaMecanicoAsignado(mecanicoSeleccionado)) {
            JOptionPane.showMessageDialog(this,
                    "El mecánico ya está asignado a una escudería.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Buscar la escudería
        Escuderia escuderia = buscarEscuderiaPorNombre(escuderiaSeleccionada);
        if (escuderia != null) {
            escuderia.agregarMecanico(mecanicoSeleccionado);

            JOptionPane.showMessageDialog(this,
                    "Mecánico asignado exitosamente a " + escuderia.getNombre() + ".",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            actualizarTablaMecanicos();
        }
    }

    /**
     * Desasigna un mecánico de su escudería actual
     */
    private void desasignarMecanicoDeEscuderia() {
        if (mecanicoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un mecánico de la tabla.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Buscar la escudería a la que está asignado el mecánico
        Escuderia escuderiaAsignada = null;
        List<Escuderia> escuderias = gestor.getEscuderias();

        for (Escuderia escuderia : escuderias) {
            if (escuderia.getMecanicos().contains(mecanicoSeleccionado)) {
                escuderiaAsignada = escuderia;
                break;
            }
        }

        if (escuderiaAsignada == null) {
            JOptionPane.showMessageDialog(this,
                    "El mecánico no está asignado a ninguna escudería.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de desasignar el mecánico de " + escuderiaAsignada.getNombre() + "?",
                "Confirmar Desasignación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            escuderiaAsignada.removerMecanico(mecanicoSeleccionado);

            JOptionPane.showMessageDialog(this,
                    "Mecánico desasignado exitosamente de " + escuderiaAsignada.getNombre() + ".",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            cmbEscuderia.setSelectedItem("Sin asignar");
            actualizarTablaMecanicos();
        }
    }

    /**
     * Verifica si un mecánico está asignado a alguna escudería
     */
    private boolean estaMecanicoAsignado(Mecanico mecanico) {
        List<Escuderia> escuderias = gestor.getEscuderias();
        for (Escuderia escuderia : escuderias) {
            if (escuderia.getMecanicos().contains(mecanico)) {
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
     * Busca una especialidad por su descripción
     */
    private Especialidad buscarEspecialidadPorDescripcion(String descripcion) {
        for (Especialidad esp : Especialidad.values()) {
            if (esp.getDescripcion().equals(descripcion)) {
                return esp;
            }
        }
        return null;
    }

    /**
     * Valida los campos del formulario de mecánico
     */
    private void validarCamposMecanico() {
        if (txtDni.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI es obligatorio");
        }
        if (txtNombre.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (txtApellido.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }
        if (txtExperiencia.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("La experiencia es obligatoria");
        }

        // Validar que el DNI sea único
        String dni = txtDni.getText().trim();
        List<Mecanico> mecanicos = gestor.getMecanicos();
        for (Mecanico mecanico : mecanicos) {
            if (mecanico.getDni().equals(dni) && mecanico != mecanicoSeleccionado) {
                throw new IllegalArgumentException("Ya existe un mecánico con ese DNI");
            }
        }
    }

    /**
     * Actualiza las especialidades de un mecánico existente
     */
    private void actualizarEspecialidades() {
        if (mecanicoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un mecánico de la tabla para actualizar sus especialidades.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Limpiar especialidades actuales del mecánico
            List<Especialidad> especialidadesActuales = new ArrayList<>(mecanicoSeleccionado.getEspecialidades());
            for (Especialidad esp : especialidadesActuales) {
                mecanicoSeleccionado.removerEspecialidad(esp);
            }

            // Agregar las especialidades seleccionadas en la interfaz
            for (int i = 0; i < modeloEspecialidades.getSize(); i++) {
                String descripcionEsp = modeloEspecialidades.getElementAt(i);
                Especialidad especialidad = buscarEspecialidadPorDescripcion(descripcionEsp);
                if (especialidad != null) {
                    mecanicoSeleccionado.agregarEspecialidad(especialidad);
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Especialidades del mecánico actualizadas exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            actualizarTablaMecanicos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar especialidades: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Limpia el formulario
     */
    private void limpiarFormulario() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtExperiencia.setText("");
        cmbEscuderia.setSelectedIndex(0);
        modeloEspecialidades.clear();
        mecanicoSeleccionado = null;
        tablaMecanicos.clearSelection();
    }
}