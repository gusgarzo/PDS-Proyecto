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
    private JPanel panelEditorBloques;
    private JTextField txtNombreBloque;
    private JPanel panelListaBloques;

    private List<String> bloques = new ArrayList<>();

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
        crearPanelEditorBloques(nombre, dificultad, descripcion);
        add(panelEditorBloques, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void crearPanelEditorBloques(String nombreCurso, String dificultad, String descripcion) {
        panelEditorBloques = new JPanel(new BorderLayout());
        panelEditorBloques.setOpaque(false);

        JLabel titulo = new JLabel("Bloques de contenido para: " + nombreCurso, SwingConstants.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panelEditorBloques.add(titulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);

        JPanel panelFormulario = new JPanel(new FlowLayout());
        panelFormulario.setOpaque(false);

        txtNombreBloque = new JTextField(20);
        JButton btnAgregarBloque = new JButton("Añadir bloque");

        btnAgregarBloque.addActionListener(e -> agregarBloque());

        panelFormulario.add(new JLabel("Nombre del bloque:"));
        panelFormulario.add(txtNombreBloque);
        panelFormulario.add(btnAgregarBloque);

        panelListaBloques = new JPanel();
        panelListaBloques.setLayout(new BoxLayout(panelListaBloques, BoxLayout.Y_AXIS));
        panelListaBloques.setOpaque(false);

        JScrollPane scroll = new JScrollPane(panelListaBloques);
        scroll.setPreferredSize(new Dimension(500, 300));
        scroll.setBorder(BorderFactory.createTitledBorder("Bloques añadidos"));

        panelCentral.add(panelFormulario, BorderLayout.NORTH);
        panelCentral.add(scroll, BorderLayout.CENTER);

        panelEditorBloques.add(panelCentral, BorderLayout.CENTER);
    }

    private void agregarBloque() {
        String nombre = txtNombreBloque.getText().trim();
        if (!nombre.isEmpty()) {
            bloques.add(nombre);
            int numeroBloque = bloques.size();

            JPanel panelBloque = new JPanel();
            panelBloque.setLayout(new BoxLayout(panelBloque, BoxLayout.Y_AXIS));
            panelBloque.setOpaque(false);

            JLabel lbl = new JLabel("Bloque " + numeroBloque + ": " + nombre);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel subPanel = crearPanelPreguntasBloque();
            subPanel.setVisible(false);

            lbl.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        subPanel.setVisible(!subPanel.isVisible());
                        revalidate();
                        repaint();
                    }
                }
            });

            panelBloque.add(lbl);
            panelBloque.add(subPanel);
            panelListaBloques.add(panelBloque);

            panelListaBloques.revalidate();
            panelListaBloques.repaint();
            txtNombreBloque.setText("");
        }
    }

    private JPanel crearPanelPreguntasBloque() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        panel.setBackground(new Color(40, 44, 52));

        Font labelFont = new Font("Comic Sans MS", Font.BOLD, 16);
        Color textColor = Color.WHITE;

        JLabel tipoLabel = new JLabel("Tipo de pregunta:");
        tipoLabel.setFont(labelFont);
        tipoLabel.setForeground(textColor);
        JComboBox<String> tipoPregunta = new JComboBox<>(new String[]{"Huecos", "Test", "Traducción"});

        JLabel enunciadoLabel = new JLabel("Enunciado:");
        enunciadoLabel.setFont(labelFont);
        enunciadoLabel.setForeground(textColor);
        JTextField enunciado = new JTextField(30);

        JPanel panelOpcionesTest = new JPanel();
        panelOpcionesTest.setLayout(new BoxLayout(panelOpcionesTest, BoxLayout.Y_AXIS));
        panelOpcionesTest.setOpaque(false);

        JButton btnAgregarOpcion = new JButton("➕ Añadir opción");
        btnAgregarOpcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        List<JTextField> camposOpciones = new ArrayList<>();
        String[] letras = {"a", "b", "c", "d"};

        btnAgregarOpcion.addActionListener((ActionEvent e) -> {
            if (camposOpciones.size() < 4) {
                JTextField nuevaOpcion = new JTextField(30);
                JLabel lbl = new JLabel("Opción " + letras[camposOpciones.size()] + ")");
                lbl.setFont(labelFont);
                lbl.setForeground(textColor);

                JPanel fila = new JPanel();
                fila.setLayout(new BoxLayout(fila, BoxLayout.Y_AXIS));
                fila.setOpaque(false);
                fila.add(lbl);
                fila.add(nuevaOpcion);

                camposOpciones.add(nuevaOpcion);
                panelOpcionesTest.add(fila);
                panelOpcionesTest.revalidate();
                panelOpcionesTest.repaint();
            }
        });

        JLabel respuestaLabel = new JLabel("Respuesta correcta:");
        respuestaLabel.setFont(labelFont);
        respuestaLabel.setForeground(textColor);
        JTextField campoRespuesta = new JTextField(30);

        JButton btnAgregar = new JButton("➕ Añadir pregunta");
        btnAgregar.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        btnAgregar.setBackground(new Color(144, 238, 144));
        btnAgregar.setFocusPainted(false);
        btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        DefaultListModel<String> preguntas = new DefaultListModel<>();
        JList<String> lista = new JList<>(preguntas);
        lista.setBackground(new Color(50, 50, 60));
        lista.setForeground(Color.WHITE);
        JScrollPane scrollLista = new JScrollPane(lista);
        scrollLista.setPreferredSize(new Dimension(400, 100));
        scrollLista.setBorder(BorderFactory.createTitledBorder("Preguntas añadidas"));

        btnAgregar.addActionListener(e -> {
            String tipo = (String) tipoPregunta.getSelectedItem();
            String resumen = "";
            if (tipo.equals("Test")) {
                resumen = "Test: " + enunciado.getText().trim() + " (" + camposOpciones.size() + " opciones)";
            } else if (tipo.equals("Huecos")) {
                resumen = "Huecos: " + enunciado.getText().trim();
            } else if (tipo.equals("Traducción")) {
                resumen = "Traducción: " + enunciado.getText().trim();
            }
            if (!resumen.isBlank()) preguntas.addElement(resumen);
        });

        tipoPregunta.addActionListener(e -> {
            String tipo = (String) tipoPregunta.getSelectedItem();
            panelOpcionesTest.setVisible(tipo.equals("Test"));
            btnAgregarOpcion.setVisible(tipo.equals("Test"));
        });

        panel.add(tipoLabel);
        panel.add(tipoPregunta);
        panel.add(Box.createVerticalStrut(10));
        panel.add(enunciadoLabel);
        panel.add(enunciado);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnAgregarOpcion);
        panel.add(panelOpcionesTest);
        panel.add(Box.createVerticalStrut(10));
        panel.add(respuestaLabel);
        panel.add(campoRespuesta);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnAgregar);
        panel.add(Box.createVerticalStrut(15));
        panel.add(scrollLista);

        panelOpcionesTest.setVisible(false);
        btnAgregarOpcion.setVisible(false);

        return panel;
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }
}