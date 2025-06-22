package pds.vista;

import pds.controlador.Controlador;
import pds.dominio.Curso;
import pds.dominio.RealizarCurso;
import pds.dominio.Usuario;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class RealizarCursoPanel extends JPanel {
    private Usuario usuarioActual;
    private JComboBox<String> estrategiaCombo;
    private JPanel cursosPanel;
    private JPanel cursosEnProgresoPanel;
    private Curso cursoSeleccionado;
    private RealizarCurso cursoEnProgresoSeleccionado;

    public RealizarCursoPanel() {
        Color azulPokemon = new Color(0x0075BE);
        Color amarilloPokemon = new Color(0xFFCC00);
        Color fondoClaro = new Color(0xF7F7F7);
        Color textoOscuro = new Color(0x222222);

        usuarioActual = Controlador.INSTANCE.getUsuarioActual();
        setLayout(new BorderLayout());
        setBackground(fondoClaro);

        JLabel titulo = new JLabel("Selecciona un curso para aprender sobre Pokémon", SwingConstants.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        titulo.setForeground(azulPokemon);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel centroPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        centroPanel.setBackground(fondoClaro);
        centroPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        cursosPanel = new JPanel();
        cursosPanel.setLayout(new BoxLayout(cursosPanel, BoxLayout.Y_AXIS));
        cursosPanel.setBackground(Color.WHITE);

        JScrollPane scrollCursos = new JScrollPane(cursosPanel);
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(amarilloPokemon, 2),
            "Cursos disponibles"
        );
        border.setTitleColor(Color.BLACK);
        border.setTitleFont(new Font("Comic Sans MS", Font.BOLD, 16));
        scrollCursos.setBorder(border);
        scrollCursos.getViewport().setBackground(Color.WHITE);

        cursosEnProgresoPanel = new JPanel();
        cursosEnProgresoPanel.setLayout(new BoxLayout(cursosEnProgresoPanel, BoxLayout.Y_AXIS));
        cursosEnProgresoPanel.setBackground(Color.WHITE);

        JScrollPane scrollEnProgreso = new JScrollPane(cursosEnProgresoPanel);
        TitledBorder borderEnProgreso = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(amarilloPokemon, 2),
            "Cursos ya empezados"
        );
        borderEnProgreso.setTitleColor(Color.BLACK);
        borderEnProgreso.setTitleFont(new Font("Comic Sans MS", Font.BOLD, 16));
        scrollEnProgreso.setBorder(borderEnProgreso);
        scrollEnProgreso.getViewport().setBackground(Color.WHITE);

        centroPanel.add(scrollCursos);
        centroPanel.add(scrollEnProgreso);
        add(centroPanel, BorderLayout.CENTER);

        // ⚠️ Cargar los cursos al arrancar
        recargarCursosDisponibles();

        // --- PANEL DE ESTRATEGIA ---
        JPanel estrategiaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        estrategiaPanel.setOpaque(true);
        estrategiaPanel.setBackground(fondoClaro);

        TitledBorder estrategiaBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(amarilloPokemon, 2),
            "Estrategia de aprendizaje"
        );
        estrategiaBorder.setTitleColor(Color.BLACK);
        estrategiaBorder.setTitleFont(new Font("Comic Sans MS", Font.BOLD, 16));
        estrategiaPanel.setBorder(estrategiaBorder);

        JLabel lblMetodo = new JLabel("Método: ");
        lblMetodo.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        lblMetodo.setForeground(azulPokemon);
        estrategiaPanel.add(lblMetodo);

        String[] estrategias = {"Secuencial", "Repetición Espaciada", "Aleatoria"};
        estrategiaCombo = new JComboBox<>(estrategias);
        estrategiaCombo.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        estrategiaCombo.setBackground(amarilloPokemon);
        estrategiaCombo.setForeground(azulPokemon);
        estrategiaPanel.add(estrategiaCombo);

        JButton comenzarBtn = new JButton("Comenzar/Continuar curso");
        comenzarBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        comenzarBtn.setBackground(amarilloPokemon);
        comenzarBtn.setForeground(azulPokemon);
        comenzarBtn.setFocusPainted(false);
        comenzarBtn.addActionListener(e -> comenzarCurso());
        estrategiaPanel.add(comenzarBtn);

        add(estrategiaPanel, BorderLayout.SOUTH);
    }

    private void comenzarCurso() {
        if (cursoEnProgresoSeleccionado != null) {
            lanzarInterfazPreguntas(cursoEnProgresoSeleccionado);
            return;
        }
        if (cursoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un curso para continuar.", "Curso no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        RealizarCurso cursoAct = Controlador.INSTANCE.iniciarCurso(
            cursoSeleccionado,
            (String) estrategiaCombo.getSelectedItem(),
            usuarioActual
        );
        lanzarInterfazPreguntas(cursoAct);
    }

    private void lanzarInterfazPreguntas(RealizarCurso realizacionCurso) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Curso de Pokémon - " + realizacionCurso.getCurso().getNombre());
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);

        PreguntasPanel preguntasPanel = new PreguntasPanel(realizacionCurso);
        dialog.add(preguntasPanel);
        dialog.setVisible(true);
    }

    public void recargarCursosDisponibles() {
        cursosPanel.removeAll();

        List<Curso> cursos = Controlador.INSTANCE.getCursosImportadosDelAlumno();

        ButtonGroup grupoCursos = new ButtonGroup();

        for (Curso curso : cursos) {
            JRadioButton radio = new JRadioButton(curso.getNombre());
            radio.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
            radio.setBackground(Color.WHITE);
            radio.setForeground(new Color(0x222222));
            radio.addActionListener(e -> {
                cursoSeleccionado = curso;
                cursoEnProgresoSeleccionado = null;
            });
            grupoCursos.add(radio);
            cursosPanel.add(radio);
            cursosPanel.add(Box.createVerticalStrut(8));
        }

        cursosPanel.revalidate();
        cursosPanel.repaint();
    }
}
