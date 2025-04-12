package pds.vista;

import javax.swing.*;

import pds.dominio.Estadisticas;

import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel contentPanel;

    public MainWindow() {
        setTitle("DuolingoPDS");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(200, 221, 242));
        headerPanel.setPreferredSize(new Dimension(900, 60));
        JLabel welcomeLabel = new JLabel("Bienvenido a DuolingoPDS", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(200);
        splitPane.setEnabled(false);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1, 0, 10)); // 0 filas, 1 columna, separación vertical de 10px
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        menuPanel.setBackground(new Color(240, 240, 240));

        String[] buttonLabels = {"Inicio", "Realizar Curso","Importar Curso", "Crear un nuevo curso", "Ver estadísticas","Compartir curso", "Salir"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setPreferredSize(new Dimension(150, 30));
            button.setFocusPainted(false);
            button.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            button.setAlignmentX(Component.LEFT_ALIGNMENT);

            if (label.equals("Inicio")) {
                button.addActionListener(e -> mostrarInicio());
            } else if (label.equals("Realizar curso")) {
                button.addActionListener(e -> mostrarRealizarCurso());
            } else if (label.equals("Importar curso")) {
            	button.addActionListener(e -> importarCurso());
            } else if (label.equals("Crear un nuevo curso")) {
                button.addActionListener(e -> crearCurso());
            } else if (label.equals("Ver estadísticas")) {
                button.addActionListener(e -> mostrarEstadisticas());
            } else if (label.equals("Compartir curso")) {
            	button.addActionListener(e -> compartirCurso());
            } else if (label.equals("Salir")) {
                button.addActionListener(e -> System.exit(0));
            }


            menuPanel.add(button);
        }

        splitPane.setLeftComponent(menuPanel);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        splitPane.setRightComponent(contentPanel);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }
    
    
    private Object compartirCurso() {
		// TODO Auto-generated method stub
		return null;
	}


	private void importarCurso() {
        contentPanel.removeAll();
        JLabel inicioLabel = new JLabel("Importar curso", SwingConstants.CENTER);
        inicioLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        contentPanel.add(inicioLabel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
	}
	//SI ERES CREADOR
    private void crearCurso() {
        contentPanel.removeAll();
        JLabel inicioLabel = new JLabel("Creará curso si es creador", SwingConstants.CENTER);
        inicioLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        contentPanel.add(inicioLabel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
	}

	private void mostrarInicio() {
        contentPanel.removeAll();
        JLabel inicioLabel = new JLabel("Pantalla de inicio", SwingConstants.CENTER);
        inicioLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        contentPanel.add(inicioLabel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarRealizarCurso() {
        contentPanel.removeAll();
        JLabel cursoLabel = new JLabel("Aquí irán los cursos disponibles", SwingConstants.CENTER);
        cursoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        contentPanel.add(cursoLabel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarEstadisticas() {
    	Estadisticas estadisticas = new Estadisticas(320, 5, 7); // ejemplo
    	
    	
    	
        contentPanel.removeAll();

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Estadísticas de uso");
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        if (estadisticas.getCursosCompletados() == 0) {
            JLabel sinDatos = new JLabel("Aún no has realizado ningún curso.", SwingConstants.CENTER);
            sinDatos.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            contentPanel.add(sinDatos, BorderLayout.CENTER);
        } else {
            JLabel tiempo = new JLabel("Tiempo total de uso: " + estadisticas.getTiempoTotalMinutos() + " minutos");
            JLabel cursos = new JLabel("Cursos completados: " + estadisticas.getCursosCompletados());
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



	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}