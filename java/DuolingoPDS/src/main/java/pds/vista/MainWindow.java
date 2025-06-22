package pds.vista;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;

import pds.controlador.Controlador;
import pds.controlador.ControladorCurso;
import pds.dominio.Alumno;
import pds.dominio.Curso;
import pds.dominio.Estadisticas;
import pds.dominio.Usuario;

import java.awt.*;
import java.io.File;

public class MainWindow extends JFrame {
    private JPanel contentPanel;
    private JPanel panelInicio;
    private JPanel panelCompartir;
    private JPanel panelImportar;
    private EditorCursoPanel editorCursoPanel;
    private RealizarCursoPanel panelRealizarCurso;
    private JPanel panelEstadisticas;
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
        inicializarPaneles();
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
        menuPanel.add(crearBotonMenu("Cerrar sesión", new Color(220, 53, 69), "/images/meowth.png", this::cerrarSesionYVolverAlLogin));

        // SPLITPANE
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(220);
        splitPane.setEnabled(false);
        splitPane.setLeftComponent(menuPanel);

        // PANEL DE CONTENIDO
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(new Color(30, 36, 45)); // FlatDarkLaf background
        splitPane.setRightComponent(contentPanel);
        
        contentPanel.add(panelInicio, "INICIO");
        contentPanel.add(panelCompartir, "COMPARTIR");
        contentPanel.add(panelImportar, "IMPORTAR");
        contentPanel.add(editorCursoPanel, "EDITOR_CURSO");
        contentPanel.add(panelRealizarCurso, "REALIZAR_CURSO");
        contentPanel.add(panelEstadisticas, "ESTADISTICAS");
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }
    private void inicializarPaneles() {
        // Panel de inicio
    	panelInicio = new JPanel();
    	panelInicio.setLayout(new BoxLayout(panelInicio, BoxLayout.Y_AXIS));
    	panelInicio.setBackground(new Color(30, 36, 45)); // fondo oscuro
    	panelInicio.setBorder(BorderFactory.createEmptyBorder(60, 20, 20, 20));

    	// Imagen Pokémon
    	JLabel imagen = new JLabel();
    	try {
    	    ImageIcon icon = new ImageIcon(getClass().getResource("/images/inicio.png")); // el que quieras
    	    Image img = icon.getImage().getScaledInstance(350, 300, Image.SCALE_SMOOTH);
    	    imagen.setIcon(new ImageIcon(img));
    	} catch (Exception e) {
    	    imagen.setText("🔥");
    	}
    	imagen.setAlignmentX(Component.CENTER_ALIGNMENT);

    	// Frase central
    	JLabel frase = new JLabel("¡Aprende junto a tu equipo pokemon, " + usuarioActual.getNombre() + "!");
    	frase.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
    	frase.setForeground(new Color(255, 215, 0)); // amarillo Pokémon
    	frase.setAlignmentX(Component.CENTER_ALIGNMENT);

    	// Frase pequeña
    	JLabel mini = new JLabel("Hoy es un buen día para aprender algo nuevo.");
    	mini.setFont(new Font("Comic Sans MS", Font.ITALIC, 16));
    	mini.setForeground(Color.LIGHT_GRAY);
    	mini.setAlignmentX(Component.CENTER_ALIGNMENT);

    	// Añadir
    	panelInicio.add(imagen);
    	panelInicio.add(Box.createVerticalStrut(20));
    	panelInicio.add(frase);
    	panelInicio.add(Box.createVerticalStrut(10));
    	panelInicio.add(mini);


        panelCompartir = new CompartirCursoPanel();
        
        panelImportar = new ImportarCursoPanel();

        // Panel realizar curso
        panelRealizarCurso = new RealizarCursoPanel();

        // Panel estadísticas (se actualiza dinámicamente)
        panelEstadisticas = new JPanel(new BorderLayout());

        // Panel editor de curso (ya es una clase separada)
        editorCursoPanel = new EditorCursoPanel();
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
    
    
    

    private void compartirCurso() {
        if (!Controlador.INSTANCE.esCreador()) {
            JPanel panelError = new JPanel(new BorderLayout());
            JLabel error = new JLabel("Solo los creadores pueden compartir cursos.", SwingConstants.CENTER);
            error.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
            panelError.add(error, BorderLayout.CENTER);
            contentPanel.add(panelError, "NO_CREADOR");
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, "NO_CREADOR");
            return;
        }

        // Eliminar el anterior si existe
        if (panelCompartir != null) {
            contentPanel.remove(panelCompartir);
        }

        // Crear uno nuevo con cursos actualizados
        panelCompartir = new CompartirCursoPanel();
        contentPanel.add(panelCompartir, "COMPARTIR");

        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "COMPARTIR");
    }

  
    private void importarCurso() {
        if (!Controlador.INSTANCE.estaLogueado() || Controlador.INSTANCE.esCreador()) {
            JOptionPane.showMessageDialog(
                this,
                "Solo los usuarios Alumno pueden acceder a Importar Curso.",
                "Permiso denegado",
                JOptionPane.WARNING_MESSAGE
                
            );
        }else {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "IMPORTAR");
        }
    }



    private void crearCurso() {
        if (!Controlador.INSTANCE.estaLogueado() || !Controlador.INSTANCE.esCreador()) {
            JOptionPane.showMessageDialog(
                this,
                "Solo los usuarios creadores pueden acceder a la creación de cursos.",
                "Permiso denegado",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Creamos un nuevo panel y lo sustituimos por el anterior
        EditorCursoPanel nuevoEditor = new EditorCursoPanel();
        contentPanel.remove(editorCursoPanel); // Quita el anterior del CardLayout
        editorCursoPanel = nuevoEditor; // Lo sustituimos
        contentPanel.add(editorCursoPanel, "EDITOR_CURSO");

        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "EDITOR_CURSO");
    }


    private void mostrarInicio() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "INICIO");
    }

    private void mostrarRealizarCurso() {
        if (!Controlador.INSTANCE.estaLogueado() || Controlador.INSTANCE.esCreador()) {
            JOptionPane.showMessageDialog(
                this,
                "Solo los usuarios Alumno pueden acceder a la Realizar curso.",
                "Permiso denegado",
                JOptionPane.WARNING_MESSAGE
                
            );
        }else {
	    	panelRealizarCurso.recargarCursosDisponibles(); 
	    	panelRealizarCurso.cargarCursosComenzados();
	        CardLayout cl = (CardLayout) contentPanel.getLayout();
	        cl.show(contentPanel, "REALIZAR_CURSO");
        }
    }

    private void mostrarEstadisticas() {
        Estadisticas estad = Controlador.INSTANCE.obtenerEstadisticas();
        VentanaEstadisticas nuevaVista = new VentanaEstadisticas(estad);

        // Reemplazar el panel antiguo (si existe)
        Component[] componentes = contentPanel.getComponents();
        for (Component comp : componentes) {
            if (comp instanceof VentanaEstadisticas) {
                contentPanel.remove(comp);
                break;
            }
        }

        // Añadir y mostrar la nueva vista actualizada
        contentPanel.add(nuevaVista, "ESTADISTICAS");
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "ESTADISTICAS");
    }


    /*private void actualizarPanelEstadisticas() {
        panelEstadisticas.removeAll(); // Limpiar contenido previo

        Estadisticas estadisticas = new Estadisticas(320, 5, 7); // ejemplo
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
            panelEstadisticas.add(sinDatos, BorderLayout.CENTER);
        } else {
           
            panelEstadisticas.add(titulo, BorderLayout.NORTH);
            panelEstadisticas.add(statsPanel, BorderLayout.CENTER);
        }

        panelEstadisticas.revalidate();
        panelEstadisticas.repaint();
    }*/
    
    private void actualizarPanelEstadisticas() {
        panelEstadisticas.removeAll(); // Limpiar contenido previo

        if (!Controlador.INSTANCE.esAlumno()) {
            JLabel sinAcceso = new JLabel("Solo los alumnos tienen estadísticas.", SwingConstants.CENTER);
            sinAcceso.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            panelEstadisticas.add(sinAcceso, BorderLayout.CENTER);
        } else {
            Estadisticas estadisticas = Controlador.INSTANCE.obtenerEstadisticas();

            if (estadisticas == null || (
            	    estadisticas.getCursosCompletados() == 0 &&
            	    estadisticas.getTiempoTotalMinutos() == 0 &&
            	    estadisticas.getmejorRachaPreguntasCorrectas() == 0)) {

                JLabel sinDatos = new JLabel("¡Aún no has comenzado tu aventura Pokémon!", SwingConstants.CENTER);
                sinDatos.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
                panelEstadisticas.add(sinDatos, BorderLayout.CENTER);
            } else {
                VentanaEstadisticas vista = new VentanaEstadisticas(estadisticas);
                panelEstadisticas.add(vista, BorderLayout.CENTER);
            }
        }

        panelEstadisticas.revalidate();
        panelEstadisticas.repaint();
    }

    private void cerrarSesionYVolverAlLogin() {
        Controlador.INSTANCE.cerrarSesion(); // ⏹️ Esto ya finaliza tiempo y actualiza

        JOptionPane.showMessageDialog(
            this,
            "Sesión cerrada correctamente.",
            "Logout",
            JOptionPane.INFORMATION_MESSAGE
        );

        this.dispose(); // Cierra esta ventana
        SwingUtilities.invokeLater(() -> new LoginWindow().setVisible(true));
    }

  
}
