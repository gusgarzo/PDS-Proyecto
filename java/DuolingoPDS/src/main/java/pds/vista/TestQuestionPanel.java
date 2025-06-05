package pds.vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestQuestionPanel extends JPanel {
    private final JTextField txtEnunciado;
    private final JPanel panelOpciones;
    private final List<OptionPanel> opciones;
    private final String[] letras = {"A", "B", "C", "D"};
    
    public TestQuestionPanel(Font labelFont, Color textColor) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        // Campo enunciado
        txtEnunciado = new JTextField(30);
        JLabel lblEnunciado = new JLabel("Respuesta test:");
        lblEnunciado.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblEnunciado.setFont(labelFont);
        lblEnunciado.setForeground(textColor);
       
        // Panel opciones
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.setOpaque(false);
        opciones = new ArrayList<>();

        // Botón añadir opción en un panel alineado a la izquierda
        JButton btnAddOpcion = new JButton("+ Añadir opción");
        btnAddOpcion.setFont(labelFont);
        JPanel panelBtnOpcion = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelBtnOpcion.setOpaque(false);
        panelBtnOpcion.add(btnAddOpcion);

        btnAddOpcion.addActionListener(e -> agregarOpcion(labelFont, textColor));

        add(lblEnunciado);
        add(txtEnunciado);
        add(Box.createVerticalStrut(15));
        add(panelBtnOpcion); // Este panel asegura que el botón está pegado a la izquierda
        add(panelOpciones);
    }
    private void agregarOpcion(Font font, Color color) {
        if (opciones.size() < 4 && txtEnunciado.getText() != null && !txtEnunciado.getText().isEmpty()) {
            OptionPanel opcion = new OptionPanel(
                letras[opciones.size()],
                txtEnunciado.getText(), // Texto inicial vacío
                font,
                color
            );
            opciones.add(opcion);
            panelOpciones.add(opcion);
            panelOpciones.revalidate();
            panelOpciones.repaint();
        }
    }
    
    public List<String> getOpciones() {
        return opciones.stream()
            .map(OptionPanel::getTextoOpcion)
            .toList();
    }
    public void clearOpciones() {
        panelOpciones.removeAll();
        opciones.clear();
        panelOpciones.revalidate();
        panelOpciones.repaint();
    }

    public String getEnunciado() {
        return txtEnunciado.getText().trim();
    }
}
 