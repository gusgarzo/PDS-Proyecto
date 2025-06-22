package pds.vista;

import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.*;
import pds.controlador.Controlador;
import pds.controlador.ControladorCurso;
import pds.dominio.Curso;

public class CompartirCursoPanel extends JPanel {

    private final JPanel contentPanel;

    public CompartirCursoPanel() {
        setLayout(new BorderLayout());
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        add(contentPanel, BorderLayout.CENTER);
        construirInterfaz();
    }

    private void construirInterfaz() {
        contentPanel.removeAll();

        List<Curso> cursos = ControladorCurso.INSTANCE.obtenerMisCursos();
        if (cursos == null || cursos.isEmpty()) {
            mostrarMensaje("No tienes cursos para compartir todavía.");
            return;
        }

        JLabel texto = new JLabel("Selecciona un curso para exportar:");
        texto.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        texto.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultListModel<Curso> listModel = new DefaultListModel<>();
        for (Curso c : cursos) listModel.addElement(c);

        JList<Curso> listaCursos = new JList<>(listModel);
        listaCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCursos.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        listaCursos.setVisibleRowCount(8);
        JScrollPane scroll = new JScrollPane(listaCursos);

        JButton btnExportar = new JButton("Exportar a JSON");
        btnExportar.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnExportar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnExportar.addActionListener(e -> {
            Curso seleccionado = listaCursos.getSelectedValue();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar un curso para exportar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar curso como JSON");
            fileChooser.setSelectedFile(new File(seleccionado.getNombre().replaceAll("\\s+", "_") + ".json"));

            int seleccion = fileChooser.showSaveDialog(this);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                boolean exito = Controlador.INSTANCE.compartirCurso(seleccionado, archivo);

                JOptionPane.showMessageDialog(
                    this,
                    exito ? "¡Curso exportado correctamente!" : "Error al exportar el curso.",
                    "Resultado",
                    exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Panel de controles inferior
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        panelInferior.setOpaque(false);
        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(btnExportar);

        contentPanel.add(texto, BorderLayout.NORTH);
        contentPanel.add(scroll, BorderLayout.CENTER);
        contentPanel.add(panelInferior, BorderLayout.SOUTH);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarMensaje(String mensaje) {
        contentPanel.removeAll();
        JLabel label = new JLabel(mensaje, SwingConstants.CENTER);
        label.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
