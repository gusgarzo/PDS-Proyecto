package pds.vista;

import pds.controlador.Controlador;
import pds.dominio.Curso;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImportarCursoPanel extends JPanel {

    private JLabel infoLabel;
    private JButton btnSeleccionarArchivo;
    private JTextArea areaResultado;

    public ImportarCursoPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 36, 45));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Importar curso desde archivo JSON", SwingConstants.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        titulo.setForeground(new Color(255, 215, 0));
        add(titulo, BorderLayout.NORTH);

        // Zona central
        JPanel central = new JPanel();
        central.setOpaque(false);
        central.setLayout(new BoxLayout(central, BoxLayout.Y_AXIS));

        infoLabel = new JLabel("Selecciona un archivo .json válido:");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnSeleccionarArchivo = new JButton("📂 Elegir archivo");
        btnSeleccionarArchivo.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSeleccionarArchivo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSeleccionarArchivo.addActionListener(e -> seleccionarArchivo());

        areaResultado = new JTextArea(6, 40);
        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        areaResultado.setBackground(new Color(50, 50, 60));
        areaResultado.setForeground(Color.WHITE);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(areaResultado);

        central.add(infoLabel);
        central.add(Box.createVerticalStrut(10));
        central.add(btnSeleccionarArchivo);
        central.add(Box.createVerticalStrut(20));
        central.add(scroll);

        add(central, BorderLayout.CENTER);
    }

    private void seleccionarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            Curso cursoImportado = Controlador.INSTANCE.importarCurso(archivo);

            if (cursoImportado != null) {
                areaResultado.setText("✅ Curso importado:\n" +
                    "- Nombre: " + cursoImportado.getNombre() + "\n" +
                    "- Dificultad: " + cursoImportado.getDificultad() + "\n" +
                    "- Nº de bloques: " + cursoImportado.getBloques().size());
            } else {
                areaResultado.setText("❌ Error al importar el curso. Asegúrate de estar logueado como alumno y de que el archivo sea válido.");
            }
        }
    }
}
