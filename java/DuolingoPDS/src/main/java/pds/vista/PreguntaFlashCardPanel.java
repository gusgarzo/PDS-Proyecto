package pds.vista;

import java.awt.*;
import javax.swing.*;
import pds.dominio.PreguntaFlashCard;

public class PreguntaFlashCardPanel extends JPanel {
    private final JTextArea txtEnunciado;
    private final JTextArea txtRespuesta;
    private final JButton btnRevelar;
    private boolean respuestaVisible;

    public PreguntaFlashCardPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 248, 255));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Flashcard visual
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x0075BE), 3, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        cardPanel.setMaximumSize(new Dimension(600, 350));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Enunciado
        txtEnunciado = new JTextArea();
        txtEnunciado.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        txtEnunciado.setForeground(new Color(0x0075BE));
        txtEnunciado.setEditable(false);
        txtEnunciado.setLineWrap(true);
        txtEnunciado.setWrapStyleWord(true);
        txtEnunciado.setOpaque(false);
        txtEnunciado.setFocusable(false);
        txtEnunciado.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtEnunciado.setBorder(null);
        txtEnunciado.setMaximumSize(new Dimension(520, 100));
        txtEnunciado.setMinimumSize(new Dimension(520, 60));

        cardPanel.add(txtEnunciado);
        cardPanel.add(Box.createVerticalStrut(30));

        // Botón Revelar Respuesta
        btnRevelar = new JButton("Revelar Respuesta");
        btnRevelar.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnRevelar.setBackground(new Color(0xFFCC00));
        btnRevelar.setForeground(Color.BLACK);
        btnRevelar.setFocusPainted(false);
        btnRevelar.setMaximumSize(new Dimension(300, 50));
        btnRevelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRevelar.addActionListener(e -> toggleRespuesta());

        cardPanel.add(btnRevelar);
        cardPanel.add(Box.createVerticalStrut(25));

        // Respuesta
        txtRespuesta = new JTextArea();
        txtRespuesta.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        txtRespuesta.setForeground(new Color(0x0075BE));
        txtRespuesta.setEditable(false);
        txtRespuesta.setLineWrap(true);
        txtRespuesta.setWrapStyleWord(true);
        txtRespuesta.setOpaque(false);
        txtRespuesta.setFocusable(false);
        txtRespuesta.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtRespuesta.setBorder(null);
        txtRespuesta.setMaximumSize(new Dimension(520, 100));
        txtRespuesta.setMinimumSize(new Dimension(520, 60));
        txtRespuesta.setVisible(false);

        cardPanel.add(txtRespuesta);

        // Centrar la flashcard en el panel principal
        add(cardPanel, new GridBagConstraints());
    }

    public void setPregunta(PreguntaFlashCard pregunta) {
        txtEnunciado.setText(pregunta.getEnunciado());
        txtEnunciado.setCaretPosition(0);
        txtRespuesta.setText("Respuesta: " + pregunta.getRespuesta());
        System.out.println(pregunta.getRespuesta());
        txtRespuesta.setCaretPosition(0);
        respuestaVisible = false;
        txtRespuesta.setVisible(false);
        btnRevelar.setBackground(new Color(0xFFCC00));
        btnRevelar.setForeground(Color.BLACK);
        btnRevelar.setText("Revelar Respuesta");
    }

    private void toggleRespuesta() {
        respuestaVisible = !respuestaVisible;
        txtRespuesta.setVisible(respuestaVisible);
        if (respuestaVisible) {
            btnRevelar.setBackground(new Color(0x0075BE));
            btnRevelar.setForeground(Color.WHITE);
            btnRevelar.setText("Ocultar Respuesta");
        } else {
            btnRevelar.setBackground(new Color(0xFFCC00));
            btnRevelar.setForeground(Color.BLACK);
            btnRevelar.setText("Revelar Respuesta");
        }
    }
}
