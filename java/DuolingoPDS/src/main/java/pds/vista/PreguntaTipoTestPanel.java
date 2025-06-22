package pds.vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import pds.dominio.PreguntaTipoTest;

public class PreguntaTipoTestPanel extends JPanel {
    private ButtonGroup grupoRespuestas;
    private JTextArea txtEnunciado;
    private JPanel opcionesPanel;

    public PreguntaTipoTestPanel() {
        setBackground(new Color(245, 248, 255));
        setLayout(new BorderLayout()); // ✅ Layout más flexible
        
        // Panel principal con scroll
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(0x0075BE), 3, true),
            BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));
        
        // Enunciado
        txtEnunciado = new JTextArea();
        txtEnunciado.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        txtEnunciado.setForeground(new Color(0x0075BE));
        txtEnunciado.setLineWrap(true);
        txtEnunciado.setWrapStyleWord(true);
        txtEnunciado.setEditable(false);
        txtEnunciado.setOpaque(false);
        txtEnunciado.setFocusable(false);
        txtEnunciado.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtEnunciado.setBorder(null);
        txtEnunciado.setMinimumSize(new Dimension(480, 40));
        txtEnunciado.setPreferredSize(new Dimension(480, 100)); 

        mainPanel.add(txtEnunciado);
        mainPanel.add(Box.createVerticalStrut(35));

        // Panel de opciones con scroll interno
        JPanel opcionesContainer = new JPanel(new BorderLayout());
        opcionesPanel = new JPanel();
        opcionesPanel.setLayout(new BoxLayout(opcionesPanel, BoxLayout.Y_AXIS));
        opcionesPanel.setBackground(Color.WHITE);
        
        JScrollPane opcionesScroll = new JScrollPane(opcionesPanel);
        opcionesScroll.setBorder(null);
        opcionesScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        opcionesContainer.add(opcionesScroll, BorderLayout.CENTER);
        
        mainPanel.add(opcionesContainer);
        
        scrollPane.setViewportView(mainPanel);
        add(scrollPane, BorderLayout.CENTER); // ✅ Scroll principal
    }


    public void setPregunta(PreguntaTipoTest pregunta) {
        txtEnunciado.setText(pregunta.getEnunciado());
        txtEnunciado.setCaretPosition(0);
        removeOldOptions();

        for (String opcion : pregunta.getRespuestas()) {
            JRadioButton rb = crearRadioButton(opcion);
            grupoRespuestas.add(rb);
            opcionesPanel.add(rb);
            opcionesPanel.add(Box.createVerticalStrut(18));
        }

        revalidate();
        repaint();
        SwingUtilities.invokeLater(() -> {
            txtEnunciado.setCaretPosition(0);
        });
    }

    private JRadioButton crearRadioButton(String texto) {
        JRadioButton rb = new JRadioButton(texto);
        rb.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        rb.setForeground(new Color(0x34495E));
        rb.setOpaque(false);
        rb.setFocusPainted(false);
        rb.setAlignmentX(Component.LEFT_ALIGNMENT);
        rb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return rb;
    }

    private void removeOldOptions() {
        opcionesPanel.removeAll();
        grupoRespuestas = new ButtonGroup();
    }
    public String getRespuestaSeleccionada() {
        for (Component c : opcionesPanel.getComponents()) {
            if (c instanceof JRadioButton) {
                JRadioButton rb = (JRadioButton) c;
                if (rb.isSelected()) {
                    return rb.getText();
                }
            }
        }
        return "";
    }
    
}
