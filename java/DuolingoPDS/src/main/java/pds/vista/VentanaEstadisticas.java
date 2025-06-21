package pds.vista;

import pds.dominio.Estadisticas;

import javax.swing.*;
import java.awt.*;

public class VentanaEstadisticas extends JPanel {

    public VentanaEstadisticas(Estadisticas estadisticas) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("🏆 Estadísticas de tu aventura Pokémon");
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tiempo = new JLabel("⏱ Tiempo total: " + estadisticas.getTiempoTotalMinutos() + " minutos");
        JLabel cursos = new JLabel("🎓 Cursos completados: " + estadisticas.getCursosCompletados());
        JLabel racha = new JLabel("🔥 Racha de días: " + estadisticas.getRachaDias());

        for (JLabel label : new JLabel[]{tiempo, cursos, racha}) {
            label.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        add(titulo);
        add(Box.createVerticalStrut(30));
        add(tiempo);
        add(Box.createVerticalStrut(15));
        add(cursos);
        add(Box.createVerticalStrut(15));
        add(racha);
    }
}
