package pds.vista;

import javax.swing.*;

import pds.controlador.Controlador;
import pds.controlador.ControladorCurso;
import pds.dominio.Curso;
import pds.dominio.Dificultad;

import java.awt.*;

@SuppressWarnings("serial")
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
        String descripcion = txtDescripcion.getText().trim();
        String dificultadTexto = (String) comboDificultad.getSelectedItem();

        if (!validarCampos(nombre, descripcion)) return;

        Dificultad dificultad = parsearDificultad(dificultadTexto);

        try {
            Curso curso = ControladorCurso.INSTANCE.crearCurso(nombre, descripcion, dificultad);
            cambiarAEditorBloques(curso);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    private boolean validarCampos(String nombre, String descripcion) {
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Rellena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private Dificultad parsearDificultad(String texto) {
        if (texto == null) return null;

        switch (texto.toLowerCase()) {
            case "fácil":
                return Dificultad.FACIL;
            case "medio":
                return Dificultad.NORMAL;
            case "difícil":
                return Dificultad.DIFICIL;
            default:
                return null;
        }
    }

    private void cambiarAEditorBloques(Curso curso) {
        remove(panelDatosCurso);
        add(new EditorBloquesPanel(curso), BorderLayout.CENTER);
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