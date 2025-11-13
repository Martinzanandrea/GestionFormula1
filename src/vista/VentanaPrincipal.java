package vista;

import controlador.GestorFormula1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana principal de la aplicación de gestión de Fórmula 1.
 * <p>
 * Esta clase representa la interfaz principal del sistema, proporcionando
 * acceso a todas las funcionalidades de gestión del campeonato F1.
 * Implementa un diseño moderno con navegación intuitiva mediante tarjetas.
 * </p>
 * 
 * @author Sistema de Gestión F1
 * @version 1.0
 * @since 1.0
 */
public class VentanaPrincipal extends JFrame {
    /** Controlador principal del sistema */
    private GestorFormula1 gestor;

    /**
     * Constructor de la ventana principal.
     * <p>
     * Inicializa el controlador, carga los datos de ejemplo y
     * configura todos los componentes de la interfaz.
     * </p>
     */
    public VentanaPrincipal() {
        this.gestor = new GestorFormula1();
        // Cargar datos de ejemplo
        controlador.DatosEjemplo.cargarDatos(gestor);
        inicializarComponentes();
        configurarVentana();
    }

    /**
     * Inicializa y configura todos los componentes de la interfaz.
     * <p>
     * Crea el layout principal con panel de título, navegación e información.
     * </p>
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        // Panel de título con gradiente
        JPanel panelTitulo = crearPanelTitulo();

        // Panel de navegación con cards
        JPanel panelNavegacion = crearPanelNavegacion();

        // Panel de información lateral
        JPanel panelInfo = crearPanelInformacion();

        // Agregar componentes
        add(panelTitulo, BorderLayout.NORTH);
        add(panelNavegacion, BorderLayout.CENTER);
        add(panelInfo, BorderLayout.EAST);
    }

    /**
     * Crea el panel de título de la aplicación.
     * <p>
     * Genera un panel con fondo rojo característico de F1 que contiene
     * el título principal del sistema.
     * </p>
     * 
     * @return Panel de título configurado
     */
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(220, 20, 60)); // Rojo F1 simple
        panel.setPreferredSize(new Dimension(0, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Título principal
        JLabel titulo = new JLabel("SISTEMA DE GESTIÓN FÓRMULA 1", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        // Subtítulo
        JLabel subtitulo = new JLabel("Escuderías Unidas - Temporada 2024", JLabel.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitulo.setForeground(Color.WHITE);

        JPanel panelTexto = new JPanel(new BorderLayout());
        panelTexto.setBackground(new Color(220, 20, 60));
        panelTexto.add(titulo, BorderLayout.CENTER);
        panelTexto.add(subtitulo, BorderLayout.SOUTH);

        panel.add(panelTexto, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel de navegación con lista apilada simple
     */
    private JPanel crearPanelNavegacion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Panel principal con lista apilada
        JPanel panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(Color.WHITE);

        // Agregar scroll para evitar superposiciones
        JScrollPane scrollPane = new JScrollPane(panelLista);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Título
        JLabel titulo = new JLabel("MENÚ PRINCIPAL");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.BLACK);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelLista.add(titulo);

        // Sección Gestión de Entidades
        panelLista.add(crearSeparador("GESTIÓN DE ENTIDADES"));
        panelLista.add(crearOpcionLista("Gestión de Pilotos", "Registrar y administrar pilotos",
                e -> abrirGestionPilotos()));
        panelLista.add(crearOpcionLista("Gestión de Escuderías", "Crear equipos y asignar personal",
                e -> abrirGestionEscuderias()));
        panelLista.add(crearOpcionLista("Gestión de Autos", "Administrar vehículos de competición",
                e -> abrirGestionAutos()));
        panelLista.add(crearOpcionLista("Gestión de Mecánicos", "Administrar personal técnico",
                e -> abrirGestionMecanicos()));
        panelLista.add(crearOpcionLista("Gestión de Circuitos", "Administrar pistas de carrera",
                e -> abrirGestionCircuitos()));

        // Espaciador
        panelLista.add(Box.createRigidArea(new Dimension(0, 15)));

        // Sección Operaciones
        panelLista.add(crearSeparador("OPERACIONES"));
        panelLista.add(crearOpcionLista("Gestión de Contratos", "Administrar contratos piloto-escudería",
                e -> abrirGestionContratos()));
        panelLista.add(crearOpcionLista("Gestión de Carreras", "Planificar y administrar Grandes Premios",
                e -> abrirGestionCarreras()));
        panelLista.add(
                crearOpcionLista("Reportes y Estadísticas", "Consultar rankings y análisis", e -> abrirReportes()));

        // Espaciador
        panelLista.add(Box.createRigidArea(new Dimension(0, 15)));

        // Sección Sistema
        panelLista.add(crearSeparador("SISTEMA"));
        panelLista.add(crearOpcionLista("Salir", "Cerrar la aplicación", e -> salirAplicacion()));

        // Agregar espacio flexible al final
        panelLista.add(Box.createVerticalGlue());

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea un separador de sección
     */
    private JComponent crearSeparador(String texto) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(100, 100, 100));
        panel.add(label);

        return panel;
    }

    /**
     * Crea una opción de la lista apilada
     */
    private JPanel crearOpcionLista(String titulo, String descripcion, ActionListener action) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        panel.setMinimumSize(new Dimension(400, 75));
        panel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Contenido
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setForeground(Color.BLACK);

        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 11));
        lblDescripcion.setForeground(new Color(120, 120, 120));

        JPanel panelTexto = new JPanel(new BorderLayout());
        panelTexto.setBackground(Color.WHITE);
        panelTexto.add(lblTitulo, BorderLayout.NORTH);
        panelTexto.add(lblDescripcion, BorderLayout.CENTER);

        panel.add(panelTexto, BorderLayout.CENTER);

        // Efectos de hover y click
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.setBackground(new Color(245, 245, 245));
                panelTexto.setBackground(new Color(245, 245, 245));
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(100, 100, 100)),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.setBackground(Color.WHITE);
                panelTexto.setBackground(Color.WHITE);
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)));
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                action.actionPerformed(new ActionEvent(panel, ActionEvent.ACTION_PERFORMED, titulo));
            }
        });

        // Espaciador entre opciones
        JPanel panelConEspacio = new JPanel(new BorderLayout());
        panelConEspacio.setBackground(Color.WHITE);
        panelConEspacio.add(panel, BorderLayout.CENTER);
        panelConEspacio.add(Box.createRigidArea(new Dimension(0, 8)), BorderLayout.SOUTH);

        return panelConEspacio;
    }

    /**
     * Crea el panel de información lateral simplificado
     */
    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(320, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Panel principal
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(Color.WHITE);

        // Sección de guía rápida
        JPanel panelGuia = crearPanelGuiaRapida();

        // Sección de estadísticas
        JPanel panelEstadisticas = crearPanelEstadisticas();

        contenido.add(panelGuia, BorderLayout.NORTH);
        contenido.add(panelEstadisticas, BorderLayout.CENTER);

        panel.add(contenido, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea panel de guía rápida simple
     */
    private JPanel crearPanelGuiaRapida() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Título
        JLabel titulo = new JLabel("GUÍA RÁPIDA");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setForeground(Color.BLACK);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Texto de ayuda
        JTextArea guia = new JTextArea();
        guia.setBackground(Color.WHITE);
        guia.setEditable(false);
        guia.setFont(new Font("Arial", Font.PLAIN, 11));
        guia.setForeground(Color.BLACK);
        guia.setLineWrap(true);
        guia.setWrapStyleWord(true);
        guia.setText(
                "INSTRUCCIONES:\n\n" +
                        "• Usa el menú para navegar\n" +
                        "• Crea entidades en orden:\n" +
                        "  1. Pilotos\n" +
                        "  2. Escuderías\n" +
                        "  3. Autos y Mecánicos\n" +
                        "  4. Circuitos\n" +
                        "  5. Carreras\n\n" +
                        "• El sistema te guía si falta algo\n" +
                        "• Doble clic en tablas para editar");

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(guia, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea panel de estadísticas simple
     */
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Título de la sección
        JLabel titulo = new JLabel("ESTADO ACTUAL");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setForeground(Color.BLACK);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Panel de estadísticas
        JPanel panelStats = new JPanel(new GridLayout(6, 1, 0, 5));
        panelStats.setBackground(Color.WHITE);

        // Añadir estadísticas simples
        panelStats.add(crearStatItem("Pilotos:", String.valueOf(gestor.getPilotos().size())));
        panelStats.add(crearStatItem("Escuderías:", String.valueOf(gestor.getEscuderias().size())));
        panelStats.add(crearStatItem("Autos:", String.valueOf(gestor.getAutos().size())));
        panelStats.add(crearStatItem("Mecánicos:", String.valueOf(gestor.getMecanicos().size())));
        panelStats.add(crearStatItem("Circuitos:", String.valueOf(gestor.getCircuitos().size())));
        panelStats.add(crearStatItem("Carreras:", String.valueOf(gestor.getGrandesPremios().size())));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelStats, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea un elemento de estadística simple
     */
    private JPanel crearStatItem(String etiqueta, String valor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Arial", Font.PLAIN, 11));
        lblEtiqueta.setForeground(Color.BLACK);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 11));
        lblValor.setForeground(Color.BLACK);

        panel.add(lblEtiqueta, BorderLayout.WEST);
        panel.add(lblValor, BorderLayout.EAST);

        return panel;
    }

    /**
     * Configura la ventana principal
     */
    private void configurarVentana() {
        setTitle("ESCUDERÍAS UNIDAS - Sistema Fórmula 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(1000, 700));

        // Icono
        try {
            // Se puede agregar un icono personalizado aquí
        } catch (Exception e) {
            // Ignorar si no hay icono
        }
    }

    // ==================== MÉTODOS DE NAVEGACIÓN ====================

    /**
     * Abre la ventana de gestión de pilotos.
     * <p>
     * Crea y muestra una nueva instancia de VentanaPilotos
     * de forma asíncrona en el hilo de eventos de Swing.
     * </p>
     */
    private void abrirGestionPilotos() {
        SwingUtilities.invokeLater(() -> {
            VentanaPilotos ventana = new VentanaPilotos(gestor);
            ventana.setVisible(true);
        });
    }

    /**
     * Abre la ventana de gestión de escuderías.
     * <p>
     * Crea y muestra una nueva instancia de VentanaEscuderias
     * de forma asíncrona en el hilo de eventos de Swing.
     * </p>
     */
    private void abrirGestionEscuderias() {
        SwingUtilities.invokeLater(() -> {
            VentanaEscuderias ventana = new VentanaEscuderias(gestor);
            ventana.setVisible(true);
        });
    }

    private void abrirGestionAutos() {
        SwingUtilities.invokeLater(() -> {
            VentanaAutos ventana = new VentanaAutos(gestor);
            ventana.setVisible(true);
        });
    }

    private void abrirGestionMecanicos() {
        SwingUtilities.invokeLater(() -> {
            VentanaMecanicos ventana = new VentanaMecanicos(gestor);
            ventana.setVisible(true);
        });
    }

    private void abrirGestionCircuitos() {
        SwingUtilities.invokeLater(() -> {
            VentanaCircuitos ventana = new VentanaCircuitos(gestor);
            ventana.setVisible(true);
        });
    }

    private void abrirGestionContratos() {
        SwingUtilities.invokeLater(() -> {
            try {
                VentanaContratos ventana = new VentanaContratos(gestor);
                ventana.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al abrir gestión de contratos: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void abrirGestionCarreras() {
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
            System.exit(0);
        }
    }

    /**
     * Método principal para ejecutar la aplicación de gestión de Fórmula 1.
     * <p>
     * Configura el Look and Feel de la aplicación e inicializa la ventana
     * principal.
     * Intenta usar el Look and Feel del sistema operativo, y como alternativa usa
     * Nimbus.
     * </p>
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
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
