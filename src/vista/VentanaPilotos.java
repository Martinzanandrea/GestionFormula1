package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
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
    private JTextField txtDNI, txtNombre, txtApellido, txtEdad, txtNacionalidad, txtNumero, txtExperiencia;
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
        panel.setPreferredSize(new Dimension(800, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Título del formulario
        JLabel titulo = new JLabel("DATOS DEL PILOTO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(52, 58, 64));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel de formulario con GridBagLayout
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurar campos de entrada
        txtDNI = crearCampoTexto();
        txtNombre = crearCampoTexto();
        txtApellido = crearCampoTexto();
        txtEdad = crearCampoTexto();
        txtNacionalidad = crearCampoTexto();
        txtNumero = crearCampoTexto();
        txtExperiencia = crearCampoTexto();

        // Agregar campos al formulario
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        agregarCampo(panelCampos, "DNI:", txtDNI, 0, gbc);
        agregarCampo(panelCampos, "Nombre:", txtNombre, 1, gbc);
        agregarCampo(panelCampos, "Apellido:", txtApellido, 2, gbc);
        agregarCampo(panelCampos, "Edad:", txtEdad, 3, gbc);
        agregarCampo(panelCampos, "Nacionalidad:", txtNacionalidad, 4, gbc);
        agregarCampo(panelCampos, "Número:", txtNumero, 5, gbc);
        agregarCampo(panelCampos, "Años Exp.:", txtExperiencia, 6, gbc);

        // Panel de botones del formulario
        JPanel panelBotonesForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelBotonesForm.setBackground(Color.WHITE);

        btnAgregar = crearBoton("AGREGAR", new Color(34, 139, 34)); // Verde más oscuro
        btnModificar = crearBoton("MODIFICAR", new Color(30, 144, 255)); // Azul más brillante
        btnLimpiar = crearBoton("LIMPIAR", new Color(255, 140, 0)); // Naranja

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
        label.setPreferredSize(new Dimension(140, 25));
        return label;
    }

    /**
     * Crea campos de texto con estilo moderno
     */
    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setPreferredSize(new Dimension(600, 35));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // Efectos focus
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                        BorderFactory.createEmptyBorder(4, 4, 4, 4)));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(206, 212, 218)),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            }
        });

        return campo;
    }

    /**
     * Crea botones con estilo simple blanco y negro y tamaño estándar
     */
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);

        // Tamaño estándar para todos los botones
        boton.setPreferredSize(new Dimension(120, 40));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Interfaz simple blanco y negro
        boton.setBackground(Color.WHITE);
        boton.setForeground(Color.BLACK);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setFocusPainted(true);

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
        JLabel titulo = new JLabel("🏁 LISTA DE PILOTOS REGISTRADOS");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(new Color(52, 58, 64));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Leyenda de colores
        JLabel leyenda = new JLabel("<html><small style='color: #6c757d;'>" +
                "🟢 Contratado | 🟡 Contrato Expirado | 🔴 Piloto Libre" +
                "</small></html>");
        leyenda.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        leyenda.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(248, 249, 250));
        panelTitulo.add(titulo, BorderLayout.NORTH);
        panelTitulo.add(leyenda, BorderLayout.SOUTH);

        // Configurar tabla
        String[] columnas = { "Número", "DNI", "Nombre Completo", "Edad", "Nacionalidad", "Escudería", "Estado",
                "Experiencia", "Puntos" };
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

        panel.add(panelTitulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

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

        // Renderer personalizado
        tablaPilotos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    // Color de fondo alternado
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(248, 249, 250));
                    }

                    // Color según el estado del piloto (columna 6)
                    String estado = (String) table.getValueAt(row, 6);
                    if (estado.contains("🟢")) {
                        // Verde claro para pilotos contratados
                        c.setBackground(new Color(212, 237, 218));
                    } else if (estado.contains("🔴")) {
                        // Amarillo claro para pilotos libres
                        c.setBackground(new Color(255, 243, 205));
                    } else if (estado.contains("🟡")) {
                        // Rosa claro para contratos expirados
                        c.setBackground(new Color(248, 215, 218));
                    }
                }
                return c;
            }
        });

        tablaPilotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Crea panel de información del piloto seleccionado
     */
    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(250, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Título
        JLabel titulo = new JLabel("INFORMACIÓN DEL PILOTO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(new Color(52, 58, 64));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Imagen placeholder
        lblImagenPiloto = new JLabel("PILOTO", JLabel.CENTER);
        lblImagenPiloto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblImagenPiloto.setPreferredSize(new Dimension(120, 120));
        lblImagenPiloto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        lblImagenPiloto.setForeground(new Color(108, 117, 125));

        // Panel de estadísticas
        lblEstadisticas = new JLabel("<html><div style='text-align: center; font-family: Segoe UI;'>" +
                "<h3 style='color: #6c757d; margin: 10px 0;'>📊 INFORMACIÓN</h3>" +
                "<p style='color: #6c757d; margin: 5px 0;'>Selecciona un piloto de la tabla</p>" +
                "<p style='color: #6c757d; margin: 5px 0;'>para ver sus estadísticas</p>" +
                "<p style='color: #6c757d; margin: 5px 0;'>y datos completos</p>" +
                "</div></html>");
        lblEstadisticas.setHorizontalAlignment(JLabel.CENTER);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(lblImagenPiloto, BorderLayout.CENTER);
        panel.add(lblEstadisticas, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea panel de botones principales
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(248, 249, 250));

        btnEliminar = crearBoton("ELIMINAR", new Color(220, 20, 60)); // Rojo más intenso
        JButton btnActualizar = crearBoton("ACTUALIZAR", new Color(75, 0, 130)); // Púrpura oscuro
        JButton btnCerrar = crearBoton("CERRAR", new Color(105, 105, 105)); // Gris oscuro

        panel.add(btnEliminar);
        panel.add(btnActualizar);
        panel.add(btnCerrar);

        // Eventos
        btnActualizar.addActionListener(e -> cargarDatos());
        btnCerrar.addActionListener(e -> dispose());

        return panel;
    }

    /**
     * Configura los eventos de la interfaz
     */
    private void configurarEventos() {
        // Eventos de botones del formulario
        btnAgregar.addActionListener(e -> agregarPiloto());
        btnModificar.addActionListener(e -> modificarPiloto());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnEliminar.addActionListener(e -> eliminarPiloto());

        // Evento de selección en tabla
        tablaPilotos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarPiloto();
            }
        });

        // Doble clic en tabla para editar
        tablaPilotos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    seleccionarPiloto();
                }
            }
        });
    }

    /**
     * Configura la ventana
     */
    private void configurarVentana() {
        setTitle("Gestión de Pilotos - Sistema Formula 1");
        setSize(1600, 700);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
    }

    /**
     * Carga los datos en la tabla
     */
    private void cargarDatos() {
        actualizarTabla();
    }

    /**
     * Actualiza la tabla con los pilotos
     */
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Piloto piloto : gestor.getPilotos()) {
            // Buscar escudería actual del piloto
            String escuderiaActual = "Sin contrato";
            String estado = "🔴 Libre";

            // Buscar en los contratos activos
            if (piloto.getEscuderia() != null) {
                escuderiaActual = piloto.getEscuderia().getNombre();
                estado = "🟢 Contratado";

                // Verificar si el contrato está vigente
                try {
                    var relacionesActivas = gestor.getRelacionesActivasPiloto(piloto);
                    if (relacionesActivas.isEmpty()) {
                        estado = "🟡 Contrato expirado";
                        escuderiaActual = "Contrato expirado";
                    }
                } catch (Exception e) {
                    // Si hay error, mantener el estado básico
                }
            }

            Object[] fila = {
                    "🏁 " + piloto.getNumero(),
                    piloto.getDni(),
                    "👤 " + piloto.getNombre() + " " + piloto.getApellido(),
                    piloto.getEdad() + " años",
                    "🌍 " + piloto.getNacionalidad(),
                    "🏢 " + escuderiaActual,
                    estado,
                    "⏱️ " + piloto.getExperiencia() + " años",
                    "🏆 " + piloto.getPuntosTotales() + " pts"
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Agrega un nuevo piloto
     */
    private void agregarPiloto() {
        try {
            validarCampos();

            String dni = txtDNI.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText());
            String nacionalidad = txtNacionalidad.getText().trim();
            int numero = Integer.parseInt(txtNumero.getText());
            int experiencia = Integer.parseInt(txtExperiencia.getText());

            Piloto nuevoPiloto = new Piloto(dni, nombre, apellido, edad, nacionalidad, numero, experiencia);
            gestor.registrarPiloto(nuevoPiloto);

            actualizarTabla();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this,
                    "Piloto agregado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al agregar piloto: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica el piloto seleccionado
     */
    private void modificarPiloto() {
        if (pilotoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un piloto para modificar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            validarCampos();

            pilotoSeleccionado.setDni(txtDNI.getText().trim());
            pilotoSeleccionado.setNombre(txtNombre.getText().trim());
            pilotoSeleccionado.setApellido(txtApellido.getText().trim());
            pilotoSeleccionado.setEdad(Integer.parseInt(txtEdad.getText().trim()));
            pilotoSeleccionado.setNacionalidad(txtNacionalidad.getText().trim());
            pilotoSeleccionado.setNumero(Integer.parseInt(txtNumero.getText().trim()));
            pilotoSeleccionado.setExperiencia(Integer.parseInt(txtExperiencia.getText().trim()));

            actualizarTabla();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this,
                    "Piloto modificado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al modificar piloto: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el piloto seleccionado
     */
    private void eliminarPiloto() {
        int filaSeleccionada = tablaPilotos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un piloto para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este piloto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String nombrePiloto = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            Piloto piloto = gestor.getPilotos().stream()
                    .filter(p -> p.getNombreCompleto().equals(nombrePiloto))
                    .findFirst().orElse(null);

            if (piloto != null) {
                gestor.getPilotos().remove(piloto);
                actualizarTabla();
                limpiarFormulario();

                JOptionPane.showMessageDialog(this,
                        "Piloto eliminado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Selecciona un piloto de la tabla
     */
    private void seleccionarPiloto() {
        int filaSeleccionada = tablaPilotos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String dni = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            pilotoSeleccionado = gestor.getPilotos().stream()
                    .filter(p -> p.getDni().equals(dni))
                    .findFirst().orElse(null);

            if (pilotoSeleccionado != null) {
                cargarDatosPiloto(pilotoSeleccionado);
                mostrarEstadisticasPiloto(pilotoSeleccionado);
            }
        }
    }

    /**
     * Carga los datos del piloto en el formulario
     */
    private void cargarDatosPiloto(Piloto piloto) {
        txtDNI.setText(piloto.getDni());
        txtNombre.setText(piloto.getNombre());
        txtApellido.setText(piloto.getApellido());
        txtEdad.setText(String.valueOf(piloto.getEdad()));
        txtNacionalidad.setText(piloto.getNacionalidad());
        txtNumero.setText(String.valueOf(piloto.getNumero()));
        txtExperiencia.setText(String.valueOf(piloto.getExperiencia()));
    }

    /**
     * Muestra las estadísticas del piloto seleccionado
     */
    private void mostrarEstadisticasPiloto(Piloto piloto) {
        StringBuilder stats = new StringBuilder("<html><div style='padding: 10px;'>");

        // Información básica
        stats.append("<div style='text-align: center; margin-bottom: 15px;'>");
        stats.append("<b style='font-size: 16px; color: #2c3e50;'>").append(piloto.getNombre()).append(" ")
                .append(piloto.getApellido()).append("</b><br>");
        stats.append("<span style='color: #7f8c8d;'>#").append(piloto.getNumero()).append(" • ")
                .append(piloto.getNacionalidad()).append("</span>");
        stats.append("</div>");

        // Datos personales
        stats.append("<div style='margin-bottom: 15px;'>");
        stats.append("<b style='color: #34495e;'>DATOS PERSONALES</b><hr style='margin: 5px 0;'>");
        stats.append("<b>DNI:</b> ").append(piloto.getDni()).append("<br>");
        stats.append("<b>Edad:</b> ").append(piloto.getEdad()).append(" años<br>");
        stats.append("<b>Experiencia:</b> ").append(piloto.getExperiencia()).append(" años<br>");
        stats.append("</div>");

        // Información de escudería y contrato
        stats.append("<div style='margin-bottom: 15px;'>");
        stats.append("<b style='color: #34495e;'>CONTRATO ACTUAL</b><hr style='margin: 5px 0;'>");
        if (piloto.getEscuderia() != null) {
            stats.append("<b>Escudería:</b> ").append(piloto.getEscuderia().getNombre()).append("<br>");

            // Obtener información del contrato
            try {
                var relacionesActivas = gestor.getRelacionesActivasPiloto(piloto);
                if (!relacionesActivas.isEmpty()) {
                    var contrato = relacionesActivas.get(0);
                    stats.append("<b>Desde:</b> ").append(contrato.getDesdeFecha()).append("<br>");
                    if (contrato.getHastaFecha() != null) {
                        stats.append("<b>Hasta:</b> ").append(contrato.getHastaFecha()).append("<br>");
                        stats.append("<b>Estado:</b> ").append(contrato.estaVigente() ? "VIGENTE" : "EXPIRADO")
                                .append("<br>");
                    } else {
                        stats.append("<b>Duración:</b> Contrato indefinido<br>");
                        stats.append("<b>Estado:</b> ACTIVO<br>");
                    }
                } else {
                    stats.append("<span style='color: #e74c3c;'>Contrato expirado</span><br>");
                }
            } catch (Exception e) {
                stats.append("Error al obtener contrato<br>");
            }
        } else {
            stats.append("<span style='color: #e74c3c;'><b>SIN ESCUDERÍA</b></span><br>");
            stats.append("<span style='color: #7f8c8d;'>Piloto libre</span><br>");
        }
        stats.append("</div>");

        // Estadísticas de rendimiento
        stats.append("<div>");
        stats.append("<b style='color: #34495e;'>RENDIMIENTO</b><hr style='margin: 5px 0;'>");
        stats.append("<b>Puntos totales:</b> ").append(piloto.getPuntosTotales()).append("<br>");

        // Calcular carreras participadas (simplificado)
        int carrerasParticipadas = piloto.getPuntosTotales() > 0 ? (piloto.getPuntosTotales() / 5) + 3 : 0; // Estimación
        stats.append("<b>Carreras:</b> ~").append(carrerasParticipadas).append("<br>");

        if (carrerasParticipadas > 0) {
            double promedio = (double) piloto.getPuntosTotales() / carrerasParticipadas;
            stats.append("<b>Promedio:</b> ").append(String.format("%.1f", promedio)).append(" pts/carrera<br>");
        }
        stats.append("</div>");

        stats.append("</div></html>");
        lblEstadisticas.setText(stats.toString());
    }

    /**
     * Limpia el formulario
     */
    private void limpiarFormulario() {
        txtDNI.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtNacionalidad.setText("");
        txtNumero.setText("");
        txtExperiencia.setText("");
        pilotoSeleccionado = null;
        tablaPilotos.clearSelection();

        lblEstadisticas.setText("<html><center>Selecciona un piloto<br>para ver sus estadísticas</center></html>");
    }

    /**
     * Valida los campos del formulario
     */
    private void validarCampos() {
        if (txtDNI.getText().trim().isEmpty() ||
                txtNombre.getText().trim().isEmpty() ||
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
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser un número válido");
        }

        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());
            if (numero < 1 || numero > 99) {
                throw new IllegalArgumentException("El número debe estar entre 1 y 99");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El número debe ser válido");
        }

        try {
            int experiencia = Integer.parseInt(txtExperiencia.getText().trim());
            if (experiencia < 0) {
                throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los años de experiencia deben ser un número válido");
        }
    }
}