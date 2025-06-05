package pds.vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EditorBloquesPanel extends JPanel {

    private JTextField txtNombreBloque;
    private JButton btnAgregarBloque;
    private JPanel panelListaBloques;
    private JButton guardar;
    private List<String> bloques; // de momento solo nombres

    public EditorBloquesPanel(String nombreCurso, String dificultad, String descripcion) {
        this.bloques = new ArrayList<>();

        setLayout(new BorderLayout());
        setBackground(new Color(30, 36, 45));

        // Título
        JLabel titulo = new JLabel("Bloques de contenido para: " + nombreCurso, SwingConstants.CENTER);
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

        btnAgregarBloque.addActionListener(e -> agregarBloque());

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

        add(panelCentral, BorderLayout.CENTER);
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

        // Tipo de pregunta
        JLabel tipoLabel = new JLabel("Tipo de pregunta:");
        tipoLabel.setFont(labelFont);
        tipoLabel.setForeground(textColor);
        JComboBox<String> tipoPregunta = new JComboBox<>(new String[]{"Huecos", "Test", "FlashCard"});
       
        
        // Enunciado (siempre visible)
        JLabel enunciadoLabel = new JLabel("Enunciado:");
        enunciadoLabel.setFont(labelFont);
        enunciadoLabel.setForeground(textColor);
        JTextField enunciadoField = new JTextField(30);

        // Panel para opciones de tipo test (oculto por defecto)
        TestQuestionPanel testPanel = new TestQuestionPanel(labelFont, textColor);
        testPanel.setVisible(false);
        FlashCardCreatePanel flashCardPanel = new FlashCardCreatePanel(labelFont, textColor);
        flashCardPanel.setVisible(false);
        // Respuesta correcta (siempre visible, pero cambia según el tipo)
        JLabel respuestaLabel = new JLabel("Respuesta correcta:");
        respuestaLabel.setFont(labelFont);
        respuestaLabel.setForeground(textColor);
        JTextField campoRespuesta = new JTextField(30);

        // Listener para mostrar/ocultar panel de opciones y adaptar campo respuesta
        tipoPregunta.addActionListener(e -> {
            String tipo = (String) tipoPregunta.getSelectedItem();

            // Oculta todos los paneles específicos primero
            testPanel.setVisible(false);
            flashCardPanel.setVisible(false);

            // Limpia y ajusta el campo de respuesta
            campoRespuesta.setText("");
            campoRespuesta.setEnabled(true);

            if ("Test".equals(tipo)) {
                testPanel.setVisible(true);
                campoRespuesta.setToolTipText("Introduce la letra de la opción correcta (A, B, C, D)");
            } else if ("FlashCard".equals(tipo)) {
            	
                flashCardPanel.setVisible(true);
                campoRespuesta.setToolTipText("Introduce la respuesta de la flashcard (o usa el campo del panel)");
                respuestaLabel.setText("Respuesta:");
            } else if ("Huecos".equals(tipo)) {
                campoRespuesta.setToolTipText("Introduce la respuesta correcta");
            } else {
                campoRespuesta.setToolTipText(null);
                campoRespuesta.setEnabled(false);
            }
        });


        // Botón añadir pregunta
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

        // Acción del botón añadir
        btnAgregar.addActionListener(e -> {
            String tipo = (String) tipoPregunta.getSelectedItem();
            String enunciado = enunciadoField.getText().trim();
            String respuesta = campoRespuesta.getText().trim();
            String resumen = "";

            if (tipo.equals("Test")) {
                List<String> opciones = testPanel.getOpciones();
                if (enunciado.isEmpty() || opciones.isEmpty() || respuesta.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Completa el enunciado, las opciones y la respuesta correcta.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                resumen = "Test: " + enunciado + " | Opciones: " + opciones + " | Respuesta: " + respuesta;
            } else if (tipo.equals("Huecos") || tipo.equals("FlashCard")) {
                if (enunciado.isEmpty() || respuesta.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Completa el enunciado y la respuesta correcta.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                resumen = tipo + ": " + enunciado + " | Respuesta: " + respuesta;
            }

            if (!resumen.isEmpty()) {
                preguntas.addElement(resumen);
                enunciadoField.setText("");
                campoRespuesta.setText("");
                testPanel.clearOpciones();
            }
        });
        
        JPanel panelGuardar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelGuardar.setOpaque(false); // Para no cambiar el fondo
        guardar = new JButton("Guardar bloque");
        panelGuardar.add(guardar);
       // guardar.addActionListener();
        // Ensamblar componentes
        panel.add(tipoLabel);
        panel.add(tipoPregunta);
        panel.add(Box.createVerticalStrut(10));
        panel.add(enunciadoLabel);
        panel.add(enunciadoField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(testPanel);
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



    public List<String> getBloques() {
        return bloques;
    }
}