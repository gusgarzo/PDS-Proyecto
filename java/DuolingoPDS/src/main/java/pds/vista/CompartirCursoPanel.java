package pds.vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.util.List;
import javax.swing.*;

import pds.controlador.Controlador;
import pds.dominio.Curso;

public class CompartirCursoPanel extends JPanel {

    private final JPanel contentPanel;

    public CompartirCursoPanel() {
        setLayout(new BorderLayout());
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        add(contentPanel, BorderLayout.CENTER);
        compartirCurso();
    }

    private void compartirCurso() {
        contentPanel.removeAll();

        // Verificar si el usuario es creador
        if (!Controlador.INSTANCE.esCreador()) {
            mostrarMensaje("Solo los creadores pueden compartir cursos.");
            return;
        }

        // Obtener cursos del creador
        List<Curso> cursos = Controlador.INSTANCE.getCursosDelCreador();
        if (cursos.isEmpty()) {
            mostrarMensaje("No tienes cursos para compartir todavía.");
            return;
        }

        // Crear componentes de UI
        JComboBox<Curso> comboCursos = new JComboBox<>(cursos.toArray(new Curso[0]));
        JButton btnCompartir = new JButton("Compartir curso");

        btnCompartir.addActionListener(e -> {
            Curso cursoSeleccionado = (Curso) comboCursos.getSelectedItem();
            if (cursoSeleccionado != null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar curso como JSON");
                fileChooser.setSelectedFile(new File(
                    cursoSeleccionado.getNombre().replaceAll("\\s+", "_") + ".json"));

                int seleccion = fileChooser.showSaveDialog(this);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    boolean exito = Controlador.INSTANCE.compartirCurso(cursoSeleccionado, archivo);

                    JOptionPane.showMessageDialog(
                        this,
                        exito ? "¡Curso compartido correctamente!" : "Error al compartir el curso.",
                        "Resultado",
                        exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Panel para los controles
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        JLabel texto = new JLabel("¡Comparte tu curso con tus amigos Pokémon!");
        texto.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboCursos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCompartir.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(texto);
        panel.add(Box.createVerticalStrut(20));
        panel.add(comboCursos);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnCompartir);

        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarMensaje(String mensaje) {
        JLabel label = new JLabel(mensaje, SwingConstants.CENTER);
        label.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
