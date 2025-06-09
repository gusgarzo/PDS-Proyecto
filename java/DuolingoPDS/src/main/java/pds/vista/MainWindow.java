package pds.vista;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;

import pds.controlador.Controlador;
import pds.dominio.Estadisticas;
import pds.dominio.Usuario;

import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel contentPanel;
    private Usuario usuarioActual = Controlador.INSTANCE.getUsuarioActual();
    public MainWindow() {
        setTitle("PokeLingo");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // HEADER con logo y fondo azul Pokémon
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(79, 173, 255)); // Azul Pokémon
        headerPanel.setPreferredSize(new Dimension(900, 70));
        headerPanel.setLayout(new BorderLayout());

        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/PokeLogo.png"));
            Image logoImg = logoIcon.getImage().getScaledInstance(170, 60, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(logoImg));
        } catch (Exception ex) {
            logoLabel.setText("PokeLingo");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 32));
        }
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        headerPanel.add(logoLabel, BorderLayout.WEST);

        JLabel welcomeLabel = new JLabel("¡Bienvenido a PokeLingo, "+  usuarioActual.getNombre() +"!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(255, 215, 0)); // Amarillo Pokémon
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        // MENÚ LATERAL con fondo blanco semitransparente y bordes redondeados
        JPanel menuPanel = new JPanel() ;
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        // Botones Pokémon
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(crearBotonMenu("Inicio", new Color(255, 215, 0), "/images/pokeball.png", this::mostrarInicio));
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(crearBotonMenu("Realizar curso", new Color(30, 144, 255), "/images/pikachu.png", this::mostrarRealizarCurso));
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(crearBotonMenu("Importar curso", new Color(255, 99, 71), "/images/snorlax.png", this::importarCurso));
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(crearBotonMenu("Crear un nuevo curso", new Color(144, 238, 144), "/images/bulbasaur.png", this::crearCurso));
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(crearBotonMenu("Ver estadísticas", new Color(255, 182, 193), "/images/evee.png", this::mostrarEstadisticas));
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(crearBotonMenu("Compartir curso", Color.WHITE, "/images/jigglypuff.png", this::compartirCurso));
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(crearBotonMenu("Salir", new Color(220, 53, 69), "/images/meowth.png", () -> System.exit(0)));

        // SPLITPANE
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(220);
        splitPane.setEnabled(false);
        splitPane.setLeftComponent(menuPanel);

        // PANEL DE CONTENIDO
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(30, 36, 45)); // FlatDarkLaf background
        splitPane.setRightComponent(contentPanel);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }

    // Botón Pokémon personalizado
    private JButton crearBotonMenu(String texto, Color color, String iconPath, Runnable accion) {
        JButton button = new JButton(texto);
        button.setMaximumSize(new Dimension(220, 50));
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (iconPath != null) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                Image img = icon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                // No icon, no problem
            }
        }
        button.addActionListener(e -> accion.run());
        return button;
    }

    // Métodos de acciones (igual que antes)
	private void compartirCurso() {
	    contentPanel.removeAll();
	
	    if (!Controlador.INSTANCE.esCreador()) {
	        JLabel error = new JLabel("Solo los creadores pueden compartir cursos.", SwingConstants.CENTER);
	        error.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
	        contentPanel.add(error, BorderLayout.CENTER);
	        contentPanel.revalidate();
	        contentPanel.repaint();
	        return;
	    }
	
	    java.util.List<Curso> cursos = Controlador.INSTANCE.getCursosDelCreador();
	    if (cursos.isEmpty()) {
	        JLabel sinCursos = new JLabel("No tienes cursos para compartir todavía.", SwingConstants.CENTER);
	        sinCursos.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
	        contentPanel.add(sinCursos, BorderLayout.CENTER);
	        contentPanel.revalidate();
	        contentPanel.repaint();
	        return;
	    }
	
	    JComboBox<Curso> comboCursos = new JComboBox<>(cursos.toArray(new Curso[0]));
	    JButton btnCompartir = new JButton("Compartir curso");
	
	    btnCompartir.addActionListener(e -> {
	        Curso cursoSeleccionado = (Curso) comboCursos.getSelectedItem();
	        if (cursoSeleccionado != null) {
	            JFileChooser fileChooser = new JFileChooser();
	            fileChooser.setDialogTitle("Guardar curso como JSON");
	            fileChooser.setSelectedFile(new File(cursoSeleccionado.getNombre().replaceAll("\\s+", "_") + ".json"));
	
	            int seleccion = fileChooser.showSaveDialog(this);
	            if (seleccion == JFileChooser.APPROVE_OPTION) {
	                File archivo = fileChooser.getSelectedFile();
	                boolean exito = Controlador.INSTANCE.compartirCurso(cursoSeleccionado, archivo);
	
	                JOptionPane.showMessageDialog(
	                    this,
	                    exito ? "¡Curso compartido correctamente!" : "Error al compartir el curso.",
	                    "Resultado",
	                    exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
	                );
	            }
	        }
	    });
	
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    panel.setOpaque(false);
	
	    JLabel texto = new JLabel("¡Comparte tu curso con tus amigos Pokémon!");
	    texto.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
	    texto.setAlignmentX(Component.CENTER_ALIGNMENT);
	    comboCursos.setAlignmentX(Component.CENTER_ALIGNMENT);
	    btnCompartir.setAlignmentX(Component.CENTER_ALIGNMENT);
	
	    panel.add(texto);
	    panel.add(Box.createVerticalStrut(20));
	    panel.add(comboCursos);
	    panel.add(Box.createVerticalStrut(10));
	    panel.add(btnCompartir);
	
	    contentPanel.add(panel, BorderLayout.CENTER);
	    contentPanel.revalidate();
	    contentPanel.repaint();
	}


    private void importarCurso() {
        contentPanel.removeAll();
        contentPanel.add(new ImportarCursoPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private void crearCurso() {
        if (!Controlador.INSTANCE.esCreador()) {
            JOptionPane.showMessageDialog(
                this,
                "Solo los usuarios creadores pueden acceder a la creación de cursos.",
                "Permiso denegado",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        contentPanel.removeAll();
        contentPanel.add(new EditorCursoPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarInicio() {
        contentPanel.removeAll();
        JLabel inicioLabel = new JLabel("Pantalla de inicio de PokeLingo", SwingConstants.CENTER);
        inicioLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        contentPanel.add(inicioLabel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarRealizarCurso() {
        contentPanel.removeAll();
        JLabel cursoLabel = new JLabel("¡Elige un curso y conviértete en Maestro Pokémon!", SwingConstants.CENTER);
        cursoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        contentPanel.add(cursoLabel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarEstadisticas() {
        Estadisticas estadisticas = new Estadisticas(320, 5, 7); // ejemplo
        contentPanel.removeAll();

        JPanel statsPanel = new JPanel();
        statsPanel.setOpaque(false);
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Estadísticas de tu aventura Pokémon");
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (estadisticas.getCursosCompletados() == 0) {
            JLabel sinDatos = new JLabel("¡Aún no has completado ningún gimnasio!", SwingConstants.CENTER);
            sinDatos.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            contentPanel.add(sinDatos, BorderLayout.CENTER);
        } else {
            JLabel tiempo = new JLabel("Tiempo total de juego: " + estadisticas.getTiempoTotalMinutos() + " minutos");
            JLabel cursos = new JLabel("Gimnasios superados: " + estadisticas.getCursosCompletados());
            JLabel racha = new JLabel("Mejor racha: " + estadisticas.getRachaDias() + " días seguidos");

            for (JLabel label : new JLabel[]{tiempo, cursos, racha}) {
                label.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                statsPanel.add(label);
            }
        }

        statsPanel.add(Box.createVerticalGlue());

        contentPanel.add(titulo, BorderLayout.NORTH);
        contentPanel.add(statsPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

  
}
