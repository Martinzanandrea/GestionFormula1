package vista;

import controlador.GestorFormula1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana principal de la aplicación de gestión de Fórmula 1
 * Interfaz moderna e intuitiva
 */
public class VentanaPrincipal extends JFrame {
    private GestorFormula1 gestor;
    private JLabel lblEstado;

    /**
     * Constructor de la ventana principal
     */
    public VentanaPrincipal() {
        this.gestor = new GestorFormula1();
        // Cargar datos de ejemplo
        controlador.DatosEjemplo.cargarDatos(gestor);
        inicializarComponentes();
        configurarVentana();
        actualizarEstado();
    }

    /**
     * Inicializa los componentes de la interfaz
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        // Panel de título con gradiente
        JPanel panelTitulo = crearPanelTitulo();

        // Panel de navegación con cards
        JPanel panelNavegacion = crearPanelNavegacion();

        // Panel de información lateral
        JPanel panelInfo = crearPanelInformacion();

        // Panel de estado mejorado
        JPanel panelEstado = crearPanelEstado();

        // Agregar componentes
        add(panelTitulo, BorderLayout.NORTH);
        add(panelNavegacion, BorderLayout.CENTER);
        add(panelInfo, BorderLayout.EAST);
        add(panelEstado, BorderLayout.SOUTH);
    }

    /**
     * Crea el panel de título con diseño profesional
     */
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Gradiente rojo F1
                GradientPaint gp = new GradientPaint(0, 0, new Color(220, 20, 60),
                        0, getHeight(), new Color(139, 0, 0));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(0, 100));

        // Título principal
        JLabel titulo = new JLabel("🏎️ Sistema de Gestión F1", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        // Subtítulo
        JLabel subtitulo = new JLabel("Escuderías Unidas - Temporada 2024", JLabel.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(255, 255, 255, 200));

        JPanel panelTexto = new JPanel(new BorderLayout());
        panelTexto.setOpaque(false);
        panelTexto.add(titulo, BorderLayout.CENTER);
        panelTexto.add(subtitulo, BorderLayout.SOUTH);

        panel.add(panelTexto, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel de navegación con cards modernas
     */
    private JPanel crearPanelNavegacion() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Sección Gestión de Entidades
        JLabel lblGestion = new JLabel("🏆 Gestión de Entidades");
        lblGestion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblGestion.setForeground(new Color(52, 58, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(lblGestion, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // Cards de gestión
        gbc.gridx = 0;
        panel.add(crearCard("👨‍✈️", "Pilotos", "Gestionar pilotos y estadísticas",
                new Color(0, 123, 255), e -> abrirGestionPilotos()), gbc);

        gbc.gridx = 1;
        panel.add(crearCard("🏁", "Escuderías", "Administrar equipos F1",
                new Color(220, 53, 69), e -> abrirGestionEscuderias()), gbc);

        gbc.gridx = 2;
        panel.add(crearCard("🏎️", "Autos", "Gestionar vehículos",
                new Color(40, 167, 69), e -> abrirGestionAutos()), gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(crearCard("🔧", "Mecánicos", "Personal técnico",
                new Color(255, 193, 7), e -> abrirGestionMecanicos()), gbc);

        gbc.gridx = 1;
        panel.add(crearCard("🏁", "Circuitos", "Pistas de carreras",
                new Color(108, 117, 125), e -> abrirGestionCircuitos()), gbc);

        // Sección Operaciones
        JLabel lblOperaciones = new JLabel("🏆 Operaciones de Carrera");
        lblOperaciones.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblOperaciones.setForeground(new Color(52, 58, 64));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(30, 15, 15, 15);
        panel.add(lblOperaciones, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(15, 15, 15, 15);

        gbc.gridx = 0;
        panel.add(crearCard("🏆", "Carreras", "Gestionar Grandes Premios",
                new Color(220, 20, 60), e -> abrirGestionCarreras()), gbc);

        gbc.gridx = 1;
        panel.add(crearCard("📊", "Reportes", "Estadísticas y análisis",
                new Color(102, 16, 242), e -> abrirReportes()), gbc);

        gbc.gridx = 2;
        panel.add(crearCard("🚪", "Salir", "Cerrar aplicación",
                new Color(134, 142, 150), e -> salirAplicacion()), gbc);

        return panel;
    }

    /**
     * Crea una card moderna para navegación
     */
    private JPanel crearCard(String icono, String titulo, String descripcion, Color color, ActionListener action) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Sombra
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2, 15, 15);

                // Fondo de la card
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 15, 15);

                // Borde de color
                g2d.setColor(color);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(1, 1, getWidth() - 4, getHeight() - 4, 15, 15);
            }
        };

        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(160, 120));
        card.setOpaque(false);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Contenido de la card
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setOpaque(false);
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblIcono = new JLabel(icono, JLabel.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));

        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(52, 58, 64));

        JLabel lblDesc = new JLabel("<html><center>" + descripcion + "</center></html>", JLabel.CENTER);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDesc.setForeground(new Color(108, 117, 125));

        JPanel panelTexto = new JPanel(new BorderLayout());
        panelTexto.setOpaque(false);
        panelTexto.add(lblTitulo, BorderLayout.NORTH);
        panelTexto.add(lblDesc, BorderLayout.CENTER);

        contenido.add(lblIcono, BorderLayout.NORTH);
        contenido.add(panelTexto, BorderLayout.CENTER);

        card.add(contenido, BorderLayout.CENTER);

        // Efectos hover
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.repaint();
                lblTitulo.setForeground(color);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.repaint();
                lblTitulo.setForeground(new Color(52, 58, 64));
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                action.actionPerformed(new ActionEvent(card, ActionEvent.ACTION_PERFORMED, titulo));
            }
        });

        return card;
    }

    /**
     * Crea el panel de información lateral
     */
    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));
        panel.setPreferredSize(new Dimension(250, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Título de la sección
        JLabel titulo = new JLabel("📈 Resumen del Sistema");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(new Color(52, 58, 64));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel de estadísticas
        JPanel panelStats = new JPanel(new GridLayout(7, 1, 0, 10));
        panelStats.setOpaque(false);

        // Añadir estadísticas dinámicas
        panelStats.add(crearStatItem("👨‍✈️ Pilotos:", String.valueOf(gestor.getPilotos().size())));
        panelStats.add(crearStatItem("🏁 Escuderías:", String.valueOf(gestor.getEscuderias().size())));
        panelStats.add(crearStatItem("🏎️ Autos:", String.valueOf(gestor.getAutos().size())));
        panelStats.add(crearStatItem("🔧 Mecánicos:", String.valueOf(gestor.getMecanicos().size())));
        panelStats.add(crearStatItem("🏁 Circuitos:", String.valueOf(gestor.getCircuitos().size())));
        panelStats.add(crearStatItem("🏆 Carreras:", String.valueOf(gestor.getGrandesPremios().size())));

        // Carreras finalizadas
        long carrerasFinalizadas = gestor.getGrandesPremios().stream()
                .filter(gp -> gp.isFinalizada()).count();
        panelStats.add(crearStatItem("✅ Finalizadas:", String.valueOf(carrerasFinalizadas)));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelStats, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea un elemento de estadística
     */
    private JPanel crearStatItem(String etiqueta, String valor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEtiqueta.setForeground(new Color(108, 117, 125));

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblValor.setForeground(new Color(52, 58, 64));

        panel.add(lblEtiqueta, BorderLayout.WEST);
        panel.add(lblValor, BorderLayout.EAST);

        return panel;
    }

    /**
     * Crea el panel de estado mejorado
     */
    private JPanel crearPanelEstado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 58, 64));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        lblEstado = new JLabel("🟢 Sistema iniciado - Listo para usar");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEstado.setForeground(Color.WHITE);

        JLabel lblVersion = new JLabel("v1.0");
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblVersion.setForeground(new Color(255, 255, 255, 150));

        panel.add(lblEstado, BorderLayout.WEST);
        panel.add(lblVersion, BorderLayout.EAST);

        return panel;
    }

    /**
     * Actualiza el estado del sistema
     */
    private void actualizarEstado() {
        if (lblEstado != null) {
            int totalEntidades = gestor.getPilotos().size() + gestor.getEscuderias().size() +
                    gestor.getAutos().size() + gestor.getCircuitos().size();
            lblEstado.setText(String.format("🟢 Sistema activo - %d entidades registradas", totalEntidades));
        }
    }

    /**
     * Configura la ventana principal
     */
    private void configurarVentana() {
        setTitle("🏎️ Escuderías Unidas - Sistema F1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));

        // Icono
        try {
            // Se puede agregar un icono personalizado aquí
        } catch (Exception e) {
            // Ignorar si no hay icono
        }
    }

    // MÉTODOS PARA ABRIR VENTANAS

    private void abrirGestionPilotos() {
        actualizarEstado();
        SwingUtilities.invokeLater(() -> {
            VentanaPilotos ventana = new VentanaPilotos(gestor);
            ventana.setVisible(true);
        });
    }

    private void abrirGestionEscuderias() {
        actualizarEstado();
        SwingUtilities.invokeLater(() -> {
            VentanaEscuderias ventana = new VentanaEscuderias(gestor);
            ventana.setVisible(true);
        });
    }

    private void abrirGestionAutos() {
        actualizarEstado();
        SwingUtilities.invokeLater(() -> {
            VentanaAutos ventana = new VentanaAutos(gestor);
            ventana.setVisible(true);
        });
    }

    private void abrirGestionMecanicos() {
        actualizarEstado();
        SwingUtilities.invokeLater(() -> {
            VentanaMecanicos ventana = new VentanaMecanicos(gestor);
            ventana.setVisible(true);
        });
    }

    private void abrirGestionCircuitos() {
        actualizarEstado();
        SwingUtilities.invokeLater(() -> {
            VentanaCircuitos ventana = new VentanaCircuitos(gestor);
            ventana.setVisible(true);
        });
    }

    private void abrirGestionCarreras() {
        actualizarEstado();
        SwingUtilities.invokeLater(() -> {
            try {
                VentanaCarreras ventana = new VentanaCarreras(gestor);
                ventana.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al abrir gestión de carreras: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void abrirReportes() {
        actualizarEstado();
        SwingUtilities.invokeLater(() -> {
            try {
                VentanaReportes ventana = new VentanaReportes(gestor);
                ventana.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al abrir reportes: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void salirAplicacion() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea salir de la aplicación?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            lblEstado.setText("🔴 Cerrando sistema...");
            System.exit(0);
        }
    }

    /**
     * Método principal para ejecutar la aplicación
     */
    public static void main(String[] args) {
        // Configurar Look and Feel moderno
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ex) {
                // Usar el Look and Feel por defecto
            }
        }

        // Configuraciones adicionales para mejor apariencia
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error al iniciar la aplicación: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}