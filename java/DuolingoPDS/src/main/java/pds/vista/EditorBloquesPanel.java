package pds.vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EditorBloquesPanel extends JPanel {

    private JTextField txtNombreBloque;
    private JButton btnAgregarBloque;
    private JPanel panelListaBloques;

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
            int numeroBloque = bloques.size(); // después de añadir
            JLabel lbl = new JLabel("🧱 Bloque " + numeroBloque + ": " + nombre);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT); // que no se centre
            panelListaBloques.add(lbl);
            panelListaBloques.revalidate();
            panelListaBloques.repaint();
            txtNombreBloque.setText("");
        }
    }


    public List<String> getBloques() {
        return bloques;
    }
}
