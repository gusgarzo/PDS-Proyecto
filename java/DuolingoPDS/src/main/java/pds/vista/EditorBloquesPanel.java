package pds.vista;

import javax.swing.*;

import pds.controlador.Controlador;
import pds.controlador.ControladorCurso;
import pds.dominio.BloqueContenido;
import pds.dominio.Curso;
import pds.dominio.Pregunta;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EditorBloquesPanel extends JPanel {

    private JTextField txtNombreBloque;
    private JButton btnAgregarBloque;
    private JPanel panelListaBloques;
    private JButton guardar;
    private List<String> bloques;

    public EditorBloquesPanel(Curso curso) {
        this.bloques = new ArrayList<>();

        setLayout(new BorderLayout());
        setBackground(new Color(30, 36, 45));

        // Título
        JLabel titulo = new JLabel("Bloques de contenido para: " + curso.getNombre(), SwingConstants.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setOpaque(false);
        panelCentral.setLayout(new BorderLayout());

        // Formulario para añadir bloque
        JPanel panelFormulario = new JPanel(new FlowLayout());
        panelFormulario.setOpaque(false);

        txtNombreBloque = new JTextField(20);
        btnAgregarBloque = new JButton("Añadir bloque");

        btnAgregarBloque.addActionListener(e -> agregarBloque(curso));

        panelFormulario.add(new JLabel("Nombre del bloque:"));
        panelFormulario.add(txtNombreBloque);
        panelFormulario.add(btnAgregarBloque);

        // Lista de bloques añadidos
        panelListaBloques = new JPanel();
        panelListaBloques.setLayout(new BoxLayout(panelListaBloques, BoxLayout.Y_AXIS));
        panelListaBloques.setOpaque(false);

        JScrollPane scroll = new JScrollPane(panelListaBloques);
        scroll.setPreferredSize(new Dimension(500, 300));
        scroll.setBorder(BorderFactory.createTitledBorder("Bloques añadidos"));

        panelCentral.add(panelFormulario, BorderLayout.NORTH);
        panelCentral.add(scroll, BorderLayout.CENTER);
   
     

        guardar = new JButton("Guardar curso");
        guardar.setBackground(new Color(0xFFCB05)); 
        guardar.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        guardar.setFocusPainted(false);
        guardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        guardar.setPreferredSize(new Dimension(160, 40));
        guardar.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        guardar.setForeground(Color.BLACK); 
        
        JPanel panelGuardar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelGuardar.setOpaque(false);
        panelGuardar.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 30)); 
        panelGuardar.add(guardar);

        add(panelGuardar, BorderLayout.SOUTH);
        add(panelCentral, BorderLayout.CENTER);
    }

    private void agregarBloque(Curso curso) {
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

            JPanel subPanel = crearPanelPreguntasBloque(nombre, curso);
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
            
            ControladorCurso.INSTANCE.agregarBloqueCurso(curso, nombre);
            
            panelBloque.add(lbl);
            panelBloque.add(subPanel);
            panelListaBloques.add(panelBloque);

            panelListaBloques.revalidate();
            panelListaBloques.repaint();
            txtNombreBloque.setText("");
        }
    }

    private JPanel crearPanelPreguntasBloque(String nombreBloque,Curso curso) {
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
        JComboBox<String> tipoPregunta = new JComboBox<>(new String[]{"Huecos", "Test", "FlashCard"});

        JLabel enunciadoLabel = new JLabel("Enunciado:");
        enunciadoLabel.setFont(labelFont);
        enunciadoLabel.setForeground(textColor);
        JTextField enunciadoField = new JTextField(30);

        TestQuestionPanel testPanel = new TestQuestionPanel(labelFont, textColor);
        testPanel.setVisible(false);

        FlashCardCreatePanel flashCardPanel = new FlashCardCreatePanel(labelFont, textColor);
        flashCardPanel.setVisible(false);

        JLabel respuestaLabel = new JLabel("Respuesta correcta:");
        respuestaLabel.setFont(labelFont);
        respuestaLabel.setForeground(textColor);
        JTextField campoRespuesta = new JTextField(30);

        tipoPregunta.addActionListener(e -> {
            String tipo = (String) tipoPregunta.getSelectedItem();
            testPanel.setVisible(false);
            flashCardPanel.setVisible(false);
            campoRespuesta.setText("");
            campoRespuesta.setEnabled(true);

            if ("Test".equals(tipo)) {
                testPanel.setVisible(true);
                campoRespuesta.setToolTipText("Introduce la letra de la opción correcta (A, B, C, D)");
            } else if ("FlashCard".equals(tipo)) {
                flashCardPanel.setVisible(true);
                campoRespuesta.setToolTipText("Introduce la respuesta de la flashcard");
                respuestaLabel.setText("Respuesta:");
            } else if ("Huecos".equals(tipo)) {
                campoRespuesta.setToolTipText("Introduce la respuesta correcta");
            } else {
                campoRespuesta.setToolTipText(null);
                campoRespuesta.setEnabled(false);
            }
        });

        JButton btnAgregar = new JButton("+ Añadir pregunta");
        btnAgregar.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        btnAgregar.setBackground(new Color(144, 238, 144));

        DefaultListModel<String> preguntas = new DefaultListModel<>();
        JList<String> lista = new JList<>(preguntas);
        JScrollPane scrollLista = new JScrollPane(lista);
        lista.setBackground(new Color(50, 50, 60));
        lista.setForeground(Color.WHITE);
        scrollLista.setPreferredSize(new Dimension(400, 100));
        scrollLista.setBorder(BorderFactory.createTitledBorder("Preguntas añadidas"));

        btnAgregar.addActionListener(e -> {
            String tipo = (String) tipoPregunta.getSelectedItem();
            String enunciado = enunciadoField.getText().trim();
            String respuesta = campoRespuesta.getText().trim();
            Pregunta pregunta = null;

            if (tipo.equals("Test")) {
                List<String> opciones = testPanel.getOpciones();
                if (enunciado.isEmpty() || opciones.isEmpty() || respuesta.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Completa el enunciado, las opciones y la respuesta correcta.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                pregunta = ControladorCurso.INSTANCE.creaPreguntaTest(enunciado, opciones, respuesta);
            } else if (tipo.equals("Huecos")) {
                if (enunciado.isEmpty() || respuesta.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Completa el enunciado y la respuesta.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                pregunta = ControladorCurso.INSTANCE.creaPreguntaHuecos(enunciado, respuesta);
            } else if (tipo.equals("FlashCard")) {
                if (enunciado.isEmpty() || respuesta.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Completa el enunciado y la respuesta.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                pregunta = ControladorCurso.INSTANCE.creaPreguntaFC(enunciado, respuesta);
            }

            if (pregunta != null) {
                try {
                    ControladorCurso.INSTANCE.agregarPreguntaBloque(curso, nombreBloque, pregunta);

                    // Mostrar resumen visual
                    String resumen = tipo + ": " + enunciado + " | Respuesta: " + respuesta;
                    preguntas.addElement(resumen);

                    // Limpiar
                    enunciadoField.setText("");
                    campoRespuesta.setText("");
                    testPanel.clearOpciones();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Panel guardar
        JPanel panelGuardar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelGuardar.setOpaque(false);
        JButton guardarBloque = new JButton("Guardar bloque");
        guardarBloque.setBackground(new Color(255, 203, 5));
        guardarBloque.setFont(labelFont);
        panelGuardar.add(guardarBloque);

        // Acción al pulsar "Guardar bloque"
        guardarBloque.addActionListener(e -> {
        	JOptionPane.showMessageDialog(panel, "Edición del bloque \"" + nombreBloque + "\" guardada (en memoria).");
            panel.setVisible(false);
            revalidate();
            repaint();
        });
        
        guardar.addActionListener(e -> {
            if (curso.getBloques() == null || curso.getBloques().isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ El curso debe tener al menos un bloque.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (BloqueContenido bloque : curso.getBloques()) {
                if (bloque.getPreguntas() == null || bloque.getPreguntas().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "❌ El bloque \"" + bloque.getNombre() + "\" no tiene preguntas.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                ControladorCurso.INSTANCE.guardarCurso(curso);
                JOptionPane.showMessageDialog(this, "✅ Curso guardado correctamente en la base de datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Error al guardar el curso: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(tipoLabel);
        panel.add(tipoPregunta);
        panel.add(Box.createVerticalStrut(10));
        panel.add(enunciadoLabel);
        panel.add(enunciadoField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(testPanel);
        panel.add(flashCardPanel);
        panel.add(respuestaLabel);
        panel.add(campoRespuesta);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnAgregar);
        panel.add(Box.createVerticalStrut(15));
        panel.add(scrollLista);
        panel.add(Box.createVerticalStrut(15));
        panel.add(panelGuardar);

        return panel;
    }

}