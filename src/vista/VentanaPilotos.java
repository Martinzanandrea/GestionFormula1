package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Ventana moderna e intuitiva para la gestión de pilotos
 */
public class VentanaPilotos extends JFrame {
    private GestorFormula1 gestor;
    private JTable tablaPilotos;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtApellido, txtEdad, txtNacionalidad, txtNumero, txtExperiencia;
    private JComboBox<Escuderia> comboEscuderias;
    private JLabel lblImagenPiloto, lblEstadisticas;
    private Piloto pilotoSeleccionado;
    private JButton btnAgregar, btnModificar, btnEliminar, btnLimpiar;

    /**
     * Constructor de la ventana de pilotos
     */
    public VentanaPilotos(GestorFormula1 gestor) {
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
        JPanel panelDerecho = crearPanelInformacion();
        JPanel panelInferior = crearPanelBotones();

        // Layout con proporciones
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelDerecho, BorderLayout.EAST);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Crea el panel de formulario con diseño moderno
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(320, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Título del formulario
        JLabel titulo = new JLabel("👨‍✈️ Datos del Piloto");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(52, 58, 64));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel de formulario con GridBagLayout
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurar campos de entrada
        txtNombre = crearCampoTexto();
        txtApellido = crearCampoTexto();
        txtEdad = crearCampoTexto();
        txtNacionalidad = crearCampoTexto();
        txtNumero = crearCampoTexto();
        txtExperiencia = crearCampoTexto();

        comboEscuderias = new JComboBox<>();
        comboEscuderias.setPreferredSize(new Dimension(200, 35));
        comboEscuderias.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboEscuderias.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        // Agregar campos al formulario
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        agregarCampo(panelCampos, "Nombre:", txtNombre, 0, gbc);
        agregarCampo(panelCampos, "Apellido:", txtApellido, 1, gbc);
        agregarCampo(panelCampos, "Edad:", txtEdad, 2, gbc);
        agregarCampo(panelCampos, "Nacionalidad:", txtNacionalidad, 3, gbc);
        agregarCampo(panelCampos, "Número:", txtNumero, 4, gbc);
        agregarCampo(panelCampos, "Años Exp.:", txtExperiencia, 5, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        JLabel lblEscuderia = crearEtiqueta("Escudería:");
        panelCampos.add(lblEscuderia, gbc);
        gbc.gridx = 1;
        panelCampos.add(comboEscuderias, gbc);

        // Panel de botones del formulario
        JPanel panelBotonesForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelBotonesForm.setBackground(Color.WHITE);

        btnAgregar = crearBoton("➕ Agregar", new Color(40, 167, 69));
        btnModificar = crearBoton("✏️ Modificar", new Color(0, 123, 255));
        btnLimpiar = crearBoton("🧹 Limpiar", new Color(108, 117, 125));

        panelBotonesForm.add(btnAgregar);
        panelBotonesForm.add(btnModificar);
        panelBotonesForm.add(btnLimpiar);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelCampos, BorderLayout.CENTER);
        panel.add(panelBotonesForm, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Helper para agregar campos al formulario
     */
    private void agregarCampo(JPanel panel, String etiqueta, JTextField campo, int fila, GridBagConstraints gbc) {
        gbc.gridy = fila;
        gbc.gridx = 0;
        JLabel lbl = crearEtiqueta(etiqueta);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    /**
     * Crea etiquetas con estilo consistente
     */
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(73, 80, 87));
        label.setPreferredSize(new Dimension(80, 25));
        return label;
    }

    /**
     * Crea campos de texto con estilo moderno
     */
    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setPreferredSize(new Dimension(200, 35));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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
     * Crea botones con estilo moderno
     */
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(90, 35));
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Efectos hover
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.darker());
            }

            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });

        return boton;
    }

    /**
     * Crea el panel de tabla con diseño mejorado
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));

        // Título de la sección
        JLabel titulo = new JLabel("🏁 Lista de Pilotos Registrados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(new Color(52, 58, 64));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Configurar tabla
        String[] columnas = { "#", "Nombre Completo", "Edad", "Nacionalidad", "Número", "Escudería", "Puntos",
                "Experiencia" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPilotos = new JTable(modeloTabla);
        configurarTabla();

        JScrollPane scrollPane = new JScrollPane(tablaPilotos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Panel de búsqueda
        JPanel panelBusqueda = crearPanelBusqueda();

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelBusqueda, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea panel de búsqueda
     */
    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(248, 249, 250));

        JLabel lblBuscar = new JLabel("🔍 Buscar:");
        lblBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JTextField txtBuscar = crearCampoTexto();
        txtBuscar.setPreferredSize(new Dimension(200, 30));

        panel.add(lblBuscar);
        panel.add(txtBuscar);

        return panel;
    }

    /**
     * Configura la apariencia de la tabla
     */
    private void configurarTabla() {
        tablaPilotos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaPilotos.setRowHeight(30);
        tablaPilotos.setGridColor(new Color(233, 236, 239));
        tablaPilotos.setSelectionBackground(new Color(0, 123, 255, 30));
        tablaPilotos.setSelectionForeground(new Color(52, 58, 64));

        // Header personalizado
        tablaPilotos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaPilotos.getTableHeader().setBackground(new Color(248, 249, 250));
        tablaPilotos.getTableHeader().setForeground(new Color(52, 58, 64));
        tablaPilotos.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));

        // Renderer personalizado para colores por escudería
        tablaPilotos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    // Colores alternos
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(248, 249, 250));
                    }
                }

                return c;
            }
        });

        tablaPilotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    add(panelPrincipal);
    }

    /**
     * Crea el panel de formulario para agregar/editar pilotos
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Piloto"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos del formulario
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        panel.add(txtNombre, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 3;
        txtApellido = new JTextField(15);
        panel.add(txtApellido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 1;
        txtEdad = new JTextField(15);
        panel.add(txtEdad, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(new JLabel("Nacionalidad:"), gbc);
        gbc.gridx = 3;
        txtNacionalidad = new JTextField(15);
        panel.add(txtNacionalidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        txtNumero = new JTextField(15);
        panel.add(txtNumero, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(new JLabel("Experiencia (años):"), gbc);
        gbc.gridx = 3;
        txtExperiencia = new JTextField(15);
        panel.add(txtExperiencia, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Escudería:"), gbc);
        gbc.gridx = 1;
        comboEscuderias = new JComboBox<>();
        panel.add(comboEscuderias, gbc);

        return panel;
    }

    /**
     * Crea el panel de tabla para mostrar los pilotos
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Pilotos"));

        // Crear modelo de tabla
        String[] columnas = { "Número", "Nombre", "Apellido", "Edad", "Nacionalidad", "Experiencia", "Escudería",
                "Puntos" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };

        tablaPilotos = new JTable(modeloTabla);
        tablaPilotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Agregar listener para selección
        tablaPilotos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarPilotoSeleccionado();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaPilotos);
        scrollPane.setPreferredSize(new Dimension(800, 300));

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
        btnAgregar.addActionListener(e -> agregarPiloto());
        btnModificar.addActionListener(e -> modificarPiloto());
        btnEliminar.addActionListener(e -> eliminarPiloto());
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
        setTitle("Gestión de Pilotos");
        setSize(900, 600);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Carga los datos iniciales
     */
    private void cargarDatos() {
        cargarEscuderias();
        actualizarTablaPilotos();
    }

    /**
     * Carga las escuderías en el combo
     */
    private void cargarEscuderias() {
        comboEscuderias.removeAllItems();
        comboEscuderias.addItem(null); // Opción sin escudería
        for (Escuderia escuderia : gestor.getEscuderias()) {
            comboEscuderias.addItem(escuderia);
        }
    }

    /**
     * Actualiza la tabla de pilotos
     */
    private void actualizarTablaPilotos() {
        modeloTabla.setRowCount(0);
        for (Piloto piloto : gestor.getPilotos()) {
            Object[] fila = {
                    piloto.getNumero(),
                    piloto.getNombre(),
                    piloto.getApellido(),
                    piloto.getEdad(),
                    piloto.getNacionalidad(),
                    piloto.getExperiencia(),
                    piloto.getEscuderia() != null ? piloto.getEscuderia().getNombre() : "Sin escudería",
                    piloto.getPuntosTotales()
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Carga los datos del piloto seleccionado en el formulario
     */
    private void cargarPilotoSeleccionado() {
        int filaSeleccionada = tablaPilotos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int numero = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
            Piloto piloto = gestor.getPilotos().stream()
                    .filter(p -> p.getNumero() == numero)
                    .findFirst().orElse(null);

            if (piloto != null) {
                txtNombre.setText(piloto.getNombre());
                txtApellido.setText(piloto.getApellido());
                txtEdad.setText(String.valueOf(piloto.getEdad()));
                txtNacionalidad.setText(piloto.getNacionalidad());
                txtNumero.setText(String.valueOf(piloto.getNumero()));
                txtExperiencia.setText(String.valueOf(piloto.getExperiencia()));
                comboEscuderias.setSelectedItem(piloto.getEscuderia());
            }
        }
    }

    /**
     * Agrega un nuevo piloto
     */
    private void agregarPiloto() {
        try {
            validarCampos();

            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText().trim());
            String nacionalidad = txtNacionalidad.getText().trim();
            int numero = Integer.parseInt(txtNumero.getText().trim());
            int experiencia = Integer.parseInt(txtExperiencia.getText().trim());

            Piloto nuevoPiloto = new Piloto(nombre, apellido, edad, nacionalidad, numero, experiencia);

            gestor.registrarPiloto(nuevoPiloto);

            // Asignar escudería si se seleccionó
            Escuderia escuderiaSeleccionada = (Escuderia) comboEscuderias.getSelectedItem();
            if (escuderiaSeleccionada != null) {
                gestor.asignarPilotoAEscuderia(nuevoPiloto, escuderiaSeleccionada);
            }

            actualizarTablaPilotos();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this, "Piloto agregado exitosamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar piloto: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica el piloto seleccionado
     */
    private void modificarPiloto() {
        int filaSeleccionada = tablaPilotos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un piloto para modificar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            validarCampos();

            int numero = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
            Piloto piloto = gestor.getPilotos().stream()
                    .filter(p -> p.getNumero() == numero)
                    .findFirst().orElse(null);

            if (piloto != null) {
                piloto.setNombre(txtNombre.getText().trim());
                piloto.setApellido(txtApellido.getText().trim());
                piloto.setEdad(Integer.parseInt(txtEdad.getText().trim()));
                piloto.setNacionalidad(txtNacionalidad.getText().trim());
                piloto.setExperiencia(Integer.parseInt(txtExperiencia.getText().trim()));

                // Verificar cambio de número
                int nuevoNumero = Integer.parseInt(txtNumero.getText().trim());
                if (nuevoNumero != piloto.getNumero()) {
                    // Verificar que el nuevo número no esté ocupado
                    boolean numeroOcupado = gestor.getPilotos().stream()
                            .anyMatch(p -> p.getNumero() == nuevoNumero);
                    if (numeroOcupado) {
                        throw new IllegalArgumentException("El número " + nuevoNumero + " ya está ocupado");
                    }
                    piloto.setNumero(nuevoNumero);
                }

                // Manejar cambio de escudería
                Escuderia nuevaEscuderia = (Escuderia) comboEscuderias.getSelectedItem();
                if (piloto.getEscuderia() != nuevaEscuderia) {
                    if (piloto.getEscuderia() != null) {
                        piloto.getEscuderia().removerPiloto(piloto);
                    }
                    if (nuevaEscuderia != null) {
                        gestor.asignarPilotoAEscuderia(piloto, nuevaEscuderia);
                    }
                }

                actualizarTablaPilotos();
                JOptionPane.showMessageDialog(this, "Piloto modificado exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar piloto: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el piloto seleccionado
     */
    private void eliminarPiloto() {
        int filaSeleccionada = tablaPilotos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un piloto para eliminar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este piloto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            int numero = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
            gestor.getPilotos().removeIf(p -> p.getNumero() == numero);
            actualizarTablaPilotos();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this, "Piloto eliminado exitosamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Limpia el formulario
     */
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtNacionalidad.setText("");
        txtNumero.setText("");
        txtExperiencia.setText("");
        comboEscuderias.setSelectedIndex(0);
        tablaPilotos.clearSelection();
    }

    /**
     * Valida los campos del formulario
     */
    private void validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
                txtApellido.getText().trim().isEmpty() ||
                txtEdad.getText().trim().isEmpty() ||
                txtNacionalidad.getText().trim().isEmpty() ||
                txtNumero.getText().trim().isEmpty() ||
                txtExperiencia.getText().trim().isEmpty()) {

            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        try {
            int edad = Integer.parseInt(txtEdad.getText().trim());
            if (edad < 18 || edad > 50) {
                throw new IllegalArgumentException("La edad debe estar entre 18 y 50 años");
            }

            int numero = Integer.parseInt(txtNumero.getText().trim());
            if (numero < 1 || numero > 99) {
                throw new IllegalArgumentException("El número debe estar entre 1 y 99");
            }

            int experiencia = Integer.parseInt(txtExperiencia.getText().trim());
            if (experiencia < 0) {
                throw new IllegalArgumentException("La experiencia no puede ser negativa");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los campos numéricos deben contener valores válidos");
        }
    }
}