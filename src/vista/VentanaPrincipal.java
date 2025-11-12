package vista;

import controlador.GestorFormula1;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación de gestión de Fórmula 1
 */
public class VentanaPrincipal extends JFrame {
    private GestorFormula1 gestor;
    private JPanel panelPrincipal;

    /**
     * Constructor de la ventana principal
     */
    public VentanaPrincipal() {
        this.gestor = new GestorFormula1();
        // Cargar datos de ejemplo
        controlador.DatosEjemplo.cargarDatos(gestor);
        inicializarComponentes();
        configurarVentana();
    }

    /**
     * Inicializa los componentes de la interfaz
     */
    private void inicializarComponentes() {
        // Panel principal
        panelPrincipal = new JPanel(new BorderLayout());

        // Panel de título
        JPanel panelTitulo = crearPanelTitulo();

        // Panel de menú principal
        JPanel panelMenu = crearPanelMenu();

        // Panel de estado
        JPanel panelEstado = crearPanelEstado();

        // Agregar componentes al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelMenu, BorderLayout.CENTER);
        panelPrincipal.add(panelEstado, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Crea el panel de título
     */
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.RED);

        JLabel titulo = new JLabel("Sistema de Gestión de Fórmula 1");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        panel.add(titulo);
        return panel;
    }

    /**
     * Crea el panel de menú principal
     */
    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel(new GridLayout(3, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones del menú
        JButton btnPilotos = crearBoton("Gestionar Pilotos", "👨‍✈️");
        JButton btnEscuderias = crearBoton("Gestionar Escuderías", "🏁");
        JButton btnAutos = crearBoton("Gestionar Autos", "🏎️");
        JButton btnMecanicos = crearBoton("Gestionar Mecánicos", "🔧");
        JButton btnCircuitos = crearBoton("Gestionar Circuitos", "🏁");
        JButton btnCarreras = crearBoton("Gestionar Carreras", "🏆");
        JButton btnReportes = crearBoton("Reportes", "📊");
        JButton btnConfiguracion = crearBoton("Configuración", "⚙️");
        JButton btnSalir = crearBoton("Salir", "🚪");

        // Agregar listeners
        btnPilotos.addActionListener(e -> abrirGestionPilotos());
        btnEscuderias.addActionListener(e -> abrirGestionEscuderias());
        btnAutos.addActionListener(e -> abrirGestionAutos());
        btnMecanicos.addActionListener(e -> abrirGestionMecanicos());
        btnCircuitos.addActionListener(e -> abrirGestionCircuitos());
        btnCarreras.addActionListener(e -> abrirGestionCarreras());
        btnReportes.addActionListener(e -> abrirReportes());
        btnConfiguracion.addActionListener(e -> abrirConfiguracion());
        btnSalir.addActionListener(e -> salirAplicacion());

        // Agregar botones al panel
        panel.add(btnPilotos);
        panel.add(btnEscuderias);
        panel.add(btnAutos);
        panel.add(btnMecanicos);
        panel.add(btnCircuitos);
        panel.add(btnCarreras);
        panel.add(btnReportes);
        panel.add(btnConfiguracion);
        panel.add(btnSalir);

        return panel;
    }

    /**
     * Crea un botón con estilo personalizado
     */
    private JButton crearBoton(String texto, String icono) {
        JButton boton = new JButton("<html><center>" + icono + "<br>" + texto + "</center></html>");
        boton.setPreferredSize(new Dimension(150, 80));
        boton.setFont(new Font("Arial", Font.PLAIN, 12));
        boton.setBackground(new Color(240, 240, 240));
        boton.setBorder(BorderFactory.createRaisedBevelBorder());

        // Efectos hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(220, 220, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(240, 240, 240));
            }
        });

        return boton;
    }

    /**
     * Crea el panel de estado
     */
    private JPanel crearPanelEstado() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel estado = new JLabel("Sistema iniciado - Listo para usar");
        estado.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(estado);
        return panel;
    }

    /**
     * Configura la ventana principal
     */
    private void configurarVentana() {
        setTitle("Escuderías Unidas - Gestión F1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(false);

        // Icono de la aplicación (opcional)
        try {
            // setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        } catch (Exception e) {
            // Ignorar si no hay icono
        }
    }

    // MÉTODOS PARA ABRIR VENTANAS

    private void abrirGestionPilotos() {
        VentanaPilotos ventana = new VentanaPilotos(gestor);
        ventana.setVisible(true);
    }

    private void abrirGestionEscuderias() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de Escuderías en desarrollo", "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirGestionAutos() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de Autos en desarrollo", "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirGestionMecanicos() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de Mecánicos en desarrollo", "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirGestionCircuitos() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de Circuitos en desarrollo", "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirGestionCarreras() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de Carreras en desarrollo", "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirReportes() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de Reportes en desarrollo", "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirConfiguracion() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de Configuración en desarrollo", "Información",
                JOptionPane.INFORMATION_MESSAGE);
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
     * Método principal para ejecutar la aplicación
     */
    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Usar el Look and Feel por defecto
        }

        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}