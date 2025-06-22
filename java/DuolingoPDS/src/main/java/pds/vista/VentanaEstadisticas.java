package pds.vista;

import pds.controlador.Controlador;
import pds.dominio.Estadisticas;

import javax.swing.*;
import java.awt.*;

public class VentanaEstadisticas extends JPanel {

    public VentanaEstadisticas(Estadisticas estadisticas) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setBackground(new Color(30, 36, 45)); // fondo oscuro

        JLabel titulo = new JLabel("Estadísticas de tu aventura Pokémon");
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 215, 0)); // amarillo Pokémon
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Obtener tiempo total incluyendo sesión actual
        long totalMilisegundos = estadisticas.getTiempoTotalConSesionActual();
        long totalSegundos = totalMilisegundos / 1000;
        long horas = totalSegundos / 3600;
        long minutos = (totalSegundos % 3600) / 60;
        long segundos = totalSegundos % 60;

        String tiempoFormateado = horas + "h " + minutos + "m " + segundos + "s";

        // Crear etiquetas con iconos
        JLabel tiempo = crearLabelConIcono("Tiempo total: " + tiempoFormateado, "/images/tiempo (2).png");
        JLabel cursos = crearLabelConIcono("Cursos completados: " + estadisticas.getCursosCompletados(), "/images/curso.png");
        JLabel racha = crearLabelConIcono("Racha actual: " + estadisticas.getrachaActualPreguntasCorrectas(), "/images/fuego.png");
        JLabel mejor = crearLabelConIcono("Mejor racha: " + estadisticas.getmejorRachaPreguntasCorrectas(), "/images/medalla.png");

        // Añadir componentes
        add(titulo);
        add(Box.createVerticalStrut(30));
        add(tiempo);
        add(Box.createVerticalStrut(15));
        add(cursos);
        add(Box.createVerticalStrut(15));
        add(racha);
        add(Box.createVerticalStrut(15));
        add(mejor);

        add(Box.createVerticalStrut(40)); // Espacio antes del consejo

        String[] consejos = {
        	    " Consejo: ¡Sé constante como Bulbasaur y crecerás cada día!", 
        	    " Tip: Los Pokémon no se rinden... ¡tú tampoco!",
        	    " Aprende como un Alakazam: ¡usa la lógica y la memoria!",
        	    " Racha larga = más XP. ¡Mantente encendido como un Charmander!",
        	    " Tip: ¡Entrena como un Machop para convertirte en un Machamp del aprendizaje!"
        	};


        	String[] iconos = {
        	    "/images/bulbasaur.png",         // 0
        	    "/images/pikachu.png",      // 1
        	    "/images/alakazam.png",     // 2
        	    "/images/charmander.png",   // 3
        	    "/images/machump.png"       // 4
        	};

        	// Elegir uno aleatorio
        	int idx = new java.util.Random().nextInt(consejos.length);
        	String consejo = consejos[idx];
        	String rutaIcono = iconos[idx];

        	// Crear el label
        	JLabel consejoLabel = new JLabel(consejo);
        	consejoLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 16));
        	consejoLabel.setForeground(Color.LIGHT_GRAY);
        	consejoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        	// Cargar el icono correspondiente
        	try {
        	    ImageIcon icon = new ImageIcon(getClass().getResource(rutaIcono));
        	    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        	    consejoLabel.setIcon(new ImageIcon(img));
        	} catch (Exception e) {
        	    System.err.println("No se encontró el icono: " + rutaIcono);
        	}

        	add(consejoLabel);
    }

    private JLabel crearLabelConIcono(String texto, String rutaIcono) {
        JLabel label = new JLabel(texto);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(rutaIcono));
            Image scaled = icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            System.err.println("⚠️ No se encontró el icono: " + rutaIcono);
        }
        label.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}
