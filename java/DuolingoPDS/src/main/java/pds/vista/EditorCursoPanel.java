package pds.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class EditorCursoPanel extends JPanel {

    private JTextField txtNombre;
    private JComboBox<String> comboDificultad;
    private JTextArea txtDescripcion;
    private JButton btnSiguiente;

    private JPanel panelDatosCurso;
    

   

    public EditorCursoPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 36, 45));

        crearPanelDatosCurso();
        add(panelDatosCurso, BorderLayout.CENTER);
    }

    private void crearPanelDatosCurso() {
        panelDatosCurso = new JPanel(new BorderLayout());
        panelDatosCurso.setOpaque(false);

        JLabel titulo = new JLabel("¡Crea tu curso Pokémon!", SwingConstants.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        titulo.setForeground(new Color(255, 215, 0));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panelDatosCurso.add(titulo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel(new GridLayout(6, 1, 10, 10));
        panelCampos.setOpaque(false);
        panelCampos.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        txtNombre = new JTextField();
        comboDificultad = new JComboBox<>(new String[]{"Fácil", "Medio", "Difícil"});
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);

        panelCampos.add(crearLabel("Nombre del curso:"));
        panelCampos.add(txtNombre);
        panelCampos.add(crearLabel("Dificultad:"));
        panelCampos.add(comboDificultad);
        panelCampos.add(crearLabel("Descripción:"));
        panelCampos.add(scrollDescripcion);

        panelDatosCurso.add(panelCampos, BorderLayout.CENTER);

        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnSiguiente.setBackground(new Color(144, 238, 144));
        btnSiguiente.setForeground(Color.BLACK);
        btnSiguiente.setFocusPainted(false);
        btnSiguiente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSiguiente.setPreferredSize(new Dimension(180, 40));

        btnSiguiente.addActionListener(e -> pasarAEditorBloques());

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(30, 36, 45));
        panelBoton.add(btnSiguiente);
        panelDatosCurso.add(panelBoton, BorderLayout.SOUTH);
    }

    private void pasarAEditorBloques() {
        String nombre = txtNombre.getText().trim();
        String dificultad = (String) comboDificultad.getSelectedItem();
        String descripcion = txtDescripcion.getText().trim();

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Rellena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        remove(panelDatosCurso);
        EditorBloquesPanel bloque = new EditorBloquesPanel(nombre, dificultad, descripcion);
        //crearPanelEditorBloques(nombre, dificultad, descripcion);
        add(bloque, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    

    
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }
}