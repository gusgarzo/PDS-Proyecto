package pds.vista;

import javax.swing.*;
import java.awt.*;

public class OptionPanel extends JPanel {
    private JLabel lblOpcion;
    private String letra;
    private String texto;

    public OptionPanel(String letra, String textoInicial, Font font, Color color) {
        this.letra = letra;
        this.texto = textoInicial;
        
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        
        lblOpcion = new JLabel("Opción " + letra + ") " + textoInicial);
        lblOpcion.setFont(font);
        lblOpcion.setForeground(color);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarOpcion(font, color));
        
        add(lblOpcion);
        add(Box.createHorizontalGlue());
        add(btnEditar);
    }

    private void editarOpcion(Font font, Color color) {
        removeAll();
        JTextField txtEdit = new JTextField(texto, 20);
        JButton btnGuardar = new JButton("Guardar");
        
        btnGuardar.addActionListener(e -> {
            texto = txtEdit.getText().trim();
            lblOpcion.setText("Opción " + letra + ") " + texto);
            removeAll();
            add(lblOpcion);
            add(Box.createHorizontalGlue());
            add(btnGuardar);
            revalidate();
        });
        
        add(new JLabel("Opción " + letra + ") "));
        add(txtEdit);
        add(btnGuardar);
        revalidate();
    }

    public String getTextoOpcion() {
        return texto;
    }
}