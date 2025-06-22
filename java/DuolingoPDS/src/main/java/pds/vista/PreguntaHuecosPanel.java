package pds.vista;

import javax.swing.*;
import javax.swing.border.*;
import pds.dominio.PreguntaHuecos;
import java.awt.*;


public class PreguntaHuecosPanel extends JPanel {
    private final JLabel lblIcono;
    private final JTextArea txtEnunciado;
    private final JTextField txtRespuesta;

    public PreguntaHuecosPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 248, 255));
        setBorder(new CompoundBorder(
            new LineBorder(new Color(100, 149, 237), 2, true),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        // Icono superior
        lblIcono = new JLabel(UIManager.getIcon("OptionPane.questionIcon"));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Enunciado multilinea
        txtEnunciado = new JTextArea();
        txtEnunciado.setFont(new Font("Segoe UI", Font.BOLD, 22));
        txtEnunciado.setForeground(new Color(44, 62, 80));
        txtEnunciado.setLineWrap(true);
        txtEnunciado.setWrapStyleWord(true);
        txtEnunciado.setEditable(false);
        txtEnunciado.setOpaque(false);
        txtEnunciado.setFocusable(false);
        txtEnunciado.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtEnunciado.setMaximumSize(new Dimension(600, 120)); // Ajusta según el tamaño de tu diálogo

        // Campo de respuesta
        txtRespuesta = new JTextField(20);
        txtRespuesta.setMaximumSize(new Dimension(350, 36));
        txtRespuesta.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtRespuesta.setBackground(Color.WHITE);  
        txtRespuesta.setForeground(Color.BLACK);

        txtRespuesta.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 149, 237), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtRespuesta.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Añadir componentes con espaciado
        add(lblIcono);
        add(Box.createVerticalStrut(18));
        add(txtEnunciado);
        add(Box.createVerticalStrut(25));
        add(txtRespuesta);
    }


    public void setPregunta(PreguntaHuecos pregunta) {
        if (pregunta == null) {
            txtEnunciado.setText("");
            txtRespuesta.setText("");
            return;
        }
        String enunciado = pregunta.getEnunciado().replace("____", "__________");
        txtEnunciado.setText(enunciado);
        txtEnunciado.setCaretPosition(0); // Siempre muestra el inicio del texto
        txtRespuesta.setText("");
    }

    public String getRespuesta() {
        return txtRespuesta.getText().trim();
    }

    public void setRespuesta(String respuesta) {
        txtRespuesta.setText(respuesta != null ? respuesta : "");
    }
}
