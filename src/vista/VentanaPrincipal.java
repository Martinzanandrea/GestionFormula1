package vista;

import controlador.GestorFormula1;
import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
        JLabel titulo = new JLabel("SISTEMA DE GESTIÓN FÓRMULA 1", JLabel.CENTER);
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
        JLabel lblGestion = new JLabel("GESTIÓN DE ENTIDADES");
        lblGestion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblGestion.setForeground(new Color(52, 58, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(lblGestion, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // Cards de gestión con iconos visibles y tooltips informativos
        gbc.gridx = 0;
        JPanel cardPilotos = crearCardConIcono("P", "PILOTOS", "Gestionar pilotos y estadísticas",
                new Color(0, 123, 255), e -> abrirGestionPilotos());
        cardPilotos.setToolTipText("<html><b>Gestión de Pilotos</b><br>" +
                "• Registrar nuevos pilotos<br>" +
                "• Modificar información existente<br>" +
                "• Ver estadísticas de rendimiento</html>");
        panel.add(cardPilotos, gbc);

        gbc.gridx = 1;
        JPanel cardEscuderias = crearCardConIcono("E", "ESCUDERÍAS", "Administrar equipos F1",
                new Color(220, 53, 69), e -> abrirGestionEscuderias());
        cardEscuderias.setToolTipText("<html><b>Gestión de Escuderías</b><br>" +
                "• Crear y modificar equipos<br>" +
                "• Asignar pilotos y autos<br>" +
                "• Gestionar personal técnico</html>");
        panel.add(cardEscuderias, gbc);

        gbc.gridx = 2;
        JPanel cardAutos = crearCardConIcono("A", "AUTOS", "Gestionar vehículos",
                new Color(40, 167, 69), e -> abrirGestionAutos());
        cardAutos.setToolTipText("<html><b>Gestión de Autos</b><br>" +
                "• Registrar nuevos vehículos<br>" +
                "• Configurar especificaciones técnicas<br>" +
                "• Asignar a escuderías</html>");
        panel.add(cardAutos, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JPanel cardMecanicos = crearCardConIcono("M", "MECÁNICOS", "Personal técnico",
                new Color(255, 140, 0), e -> abrirGestionMecanicos());
        cardMecanicos.setToolTipText("<html><b>Gestión de Mecánicos</b><br>" +
                "• Registrar personal técnico<br>" +
                "• Definir especialidades<br>" +
                "• Asignar a escuderías</html>");
        panel.add(cardMecanicos, gbc);

        gbc.gridx = 1;
        JPanel cardCircuitos = crearCardConIcono("C", "CIRCUITOS", "Pistas de carreras",
                new Color(138, 43, 226), e -> abrirGestionCircuitos());
        cardCircuitos.setToolTipText("<html><b>Gestión de Circuitos</b><br>" +
                "• Registrar pistas de carrera<br>" +
                "• Configurar características técnicas<br>" +
                "• Definir países y ubicaciones</html>");
        panel.add(cardCircuitos, gbc);

        // Sección Operaciones
        JLabel lblOperaciones = new JLabel("OPERACIONES DE CARRERA");
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
        JPanel cardCarreras = crearCardConIcono("R", "CARRERAS", "Planificar y gestionar Grandes Premios",
                new Color(220, 20, 60), e -> abrirGestionCarreras());
        cardCarreras.setToolTipText("<html><b>Gestión de Carreras</b><br>" +
                "• Planificar Grandes Premios<br>" +
                "• Registrar participaciones<br>" +
                "• Capturar resultados y posiciones</html>");
        panel.add(cardCarreras, gbc);

        gbc.gridx = 1;
        JPanel cardReportes = crearCardConIcono("S", "REPORTES", "Estadísticas, rankings y análisis",
                new Color(102, 16, 242), e -> abrirReportes());
        cardReportes.setToolTipText("<html><b>Reportes y Análisis</b><br>" +
                "• Rankings de pilotos por puntos<br>" +
                "• Estadísticas de carreras<br>" +
                "• Informes de rendimiento</html>");
        panel.add(cardReportes, gbc);

        gbc.gridx = 2;
        JPanel cardSalir = crearCardConIcono("X", "SALIR", "Cerrar aplicación",
                new Color(134, 142, 150), e -> salirAplicacion());
        cardSalir.setToolTipText("<html><b>Salir del Sistema</b><br>" +
                "• Cerrar la aplicación<br>" +
                "• Datos se mantendrán en sesión</html>");
        panel.add(cardSalir, gbc);

        return panel;
    }

    /**
     * Crea una card moderna para navegación con iconos emoji
     */
    private JPanel crearCardConIcono(String icono, String titulo, String descripcion, Color color,
            ActionListener action) {
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
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Icono con fuente confiable
        JLabel lblIcono = new JLabel(icono, JLabel.CENTER);
        lblIcono.setFont(new Font("Arial", Font.BOLD, 42));
        lblIcono.setForeground(color);
        lblIcono.setBorder(BorderFactory.createEmptyBorder(5, 0, 8, 0));

        // Titulo
        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitulo.setForeground(new Color(52, 58, 64));

        // Descripción
        JLabel lblDesc = new JLabel("<html><center>" + descripcion + "</center></html>", JLabel.CENTER);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblDesc.setForeground(new Color(108, 117, 125));

        // Panel de texto
        JPanel panelTexto = new JPanel(new BorderLayout());
        panelTexto.setOpaque(false);
        panelTexto.add(lblTitulo, BorderLayout.NORTH);
        panelTexto.add(lblDesc, BorderLayout.CENTER);

        contenido.add(lblIcono, BorderLayout.CENTER);
        contenido.add(panelTexto, BorderLayout.SOUTH);

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
        lblIcono.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblIcono.setForeground(color);
        lblIcono.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

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
     * Crea el panel de información lateral con guía y estadísticas
     */
    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));
        panel.setPreferredSize(new Dimension(280, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Panel principal con scroll
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setOpaque(false);

        // Sección de guía rápida
        JPanel panelGuia = crearPanelGuiaRapida();

        // Sección de estadísticas
        JPanel panelEstadisticas = crearPanelEstadisticas();

        contenido.add(panelGuia, BorderLayout.NORTH);
        contenido.add(panelEstadisticas, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea panel de guía rápida para usuario
     */
    private JPanel crearPanelGuiaRapida() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        // Título
        JLabel titulo = new JLabel("GUÍA RÁPIDA");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(new Color(52, 58, 64));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Texto de ayuda
        JTextArea guia = new JTextArea();
        guia.setOpaque(false);
        guia.setEditable(false);
        guia.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        guia.setForeground(new Color(108, 117, 125));
        guia.setLineWrap(true);
        guia.setWrapStyleWord(true);
        guia.setText(
                "INSTRUCCIONES:\n\n" +
                        "1. GESTIÓN: Use los botones P, E, A, M, C para registrar entidades\n\n" +
                        "2. CARRERAS: Use el botón R para planificar y gestionar Grandes Premios\n\n" +
                        "3. REPORTES: Use el botón S para consultar estadísticas y rankings\n\n" +
                        "4. DOBLE CLIC: En las tablas para editar elementos rápidamente\n\n" +
                        "Tip: Cada botón tiene un color distintivo para facilitar la navegación");

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(guia, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea panel de estadísticas del sistema
     */
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Título de la sección
        JLabel titulo = new JLabel("ESTADO DEL SISTEMA");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(new Color(52, 58, 64));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Panel de estadísticas
        JPanel panelStats = new JPanel(new GridLayout(8, 1, 0, 8));
        panelStats.setOpaque(false);

        // Añadir estadísticas dinámicas con iconos simples
        panelStats.add(crearStatItem("P - Pilotos:", String.valueOf(gestor.getPilotos().size())));
        panelStats.add(crearStatItem("E - Escuderías:", String.valueOf(gestor.getEscuderias().size())));
        panelStats.add(crearStatItem("A - Autos:", String.valueOf(gestor.getAutos().size())));
        panelStats.add(crearStatItem("M - Mecánicos:", String.valueOf(gestor.getMecanicos().size())));
        panelStats.add(crearStatItem("C - Circuitos:", String.valueOf(gestor.getCircuitos().size())));
        panelStats.add(crearStatItem("R - Carreras:", String.valueOf(gestor.getGrandesPremios().size())));

        // Carreras finalizadas
        long carrerasFinalizadas = gestor.getGrandesPremios().stream()
                .filter(gp -> gp.isFinalizada()).count();
        panelStats.add(crearStatItem("Finalizadas:", String.valueOf(carrerasFinalizadas)));

        // Carreras pendientes
        long carrerasPendientes = gestor.getGrandesPremios().stream()
                .filter(gp -> !gp.isFinalizada()).count();
        panelStats.add(crearStatItem("Pendientes:", String.valueOf(carrerasPendientes)));

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
     * Crea el panel de estado mejorado con información útil
     */
    private JPanel crearPanelEstado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 58, 64));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Panel izquierdo con estado
        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelIzquierdo.setOpaque(false);

        lblEstado = new JLabel("🟢 Sistema listo - Seleccione una opción para comenzar");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEstado.setForeground(Color.WHITE);

        panelIzquierdo.add(lblEstado);

        // Panel derecho con información adicional
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelDerecho.setOpaque(false);

        // Próxima carrera
        String proximaCarrera = obtenerProximaCarrera();
        JLabel lblProximaCarrera = new JLabel(proximaCarrera);
        lblProximaCarrera.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblProximaCarrera.setForeground(new Color(255, 255, 255, 200));

        // Version
        JLabel lblVersion = new JLabel("Formula 1 Manager v1.0");
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblVersion.setForeground(new Color(255, 255, 255, 150));

        panelDerecho.add(lblProximaCarrera);
        panelDerecho.add(new JLabel(" | "));
        panelDerecho.add(lblVersion);

        panel.add(panelIzquierdo, BorderLayout.WEST);
        panel.add(panelDerecho, BorderLayout.EAST);

        return panel;
    }

    /**
     * Obtiene información de la próxima carrera programada
     */
    private String obtenerProximaCarrera() {
        List<GranPremio> carrerasPendientes = gestor.getGrandesPremios().stream()
                .filter(gp -> !gp.isFinalizada())
                .sorted((g1, g2) -> g1.getFechaHora().compareTo(g2.getFechaHora()))
                .collect(java.util.stream.Collectors.toList());

        if (carrerasPendientes.isEmpty()) {
            return "📅 No hay carreras programadas";
        }

        GranPremio proxima = carrerasPendientes.get(0);
        return "🏁 Próxima: " + proxima.getNombre() + " - " +
                proxima.getFechaHora().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
        setTitle("ESCUDERÍAS UNIDAS - Sistema Fórmula 1");
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