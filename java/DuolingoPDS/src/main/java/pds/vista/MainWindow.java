package pds.vista;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel contentPanel;

    public MainWindow() {
        setTitle("Plataforma de Aprendizaje");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(200, 221, 242));
        headerPanel.setPreferredSize(new Dimension(900, 60));
        JLabel welcomeLabel = new JLabel("Bienvenido a DuolingoPDS", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(200);
        splitPane.setEnabled(false);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1, 0, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        menuPanel.setBackground(new Color(240, 240, 240));

        String[] buttonLabels = {"Inicio", "Empezar un curso", "Crear un nuevo curso", "Ver estadísticas", "Salir"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setPreferredSize(new Dimension(180, 50));
            button.setFocusPainted(false);
            button.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            button.setAlignmentX(Component.LEFT_ALIGNMENT);

            if (label.equals("Salir")) {
                button.addActionListener(e -> System.exit(0));
            }

            menuPanel.add(button);
        }

        splitPane.setLeftComponent(menuPanel);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        splitPane.setRightComponent(contentPanel);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}