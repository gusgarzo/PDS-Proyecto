package pds.vista;

import javax.swing.*;
import java.awt.*;

public class FlashCardCreatePanel extends JPanel {
    private JTextField txtAnverso;
    private JTextField txtReverso;

    public FlashCardCreatePanel(Font labelFont, Color textColor) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        JLabel lblAnverso = new JLabel("Anverso (pregunta):");
        lblAnverso.setFont(labelFont);
        lblAnverso.setForeground(textColor);

        txtAnverso = new JTextField(30);

        JLabel lblReverso = new JLabel("Reverso (respuesta):");
        lblReverso.setFont(labelFont);
        lblReverso.setForeground(textColor);

        txtReverso = new JTextField(30);

        add(lblAnverso);
        add(txtAnverso);
        add(Box.createVerticalStrut(10));
        add(lblReverso);
        add(txtReverso);
    }

    public String getAnverso() {
        return txtAnverso.getText().trim();
    }

    public String getReverso() {
        return txtReverso.getText().trim();
    }

    public void clear() {
        txtAnverso.setText("");
        txtReverso.setText("");
    }
}