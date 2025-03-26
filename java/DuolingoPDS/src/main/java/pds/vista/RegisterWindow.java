package pds.vista;

import javax.swing.*;
import java.awt.*;

public class RegisterWindow extends JFrame {
    private JTextField nameField, lastNameField, phoneField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<String> accountTypeComboBox;
    private JButton cancelarButton, aceptarButton;

    public RegisterWindow() {
        setTitle("Registro de Usuario");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Nombre:"), gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Apellidos:"), gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Teléfono:"), gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Email:"), gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Tipo de cuenta:"), gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Confirmar Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);

        gbc.gridy++;
        lastNameField = new JTextField(20);
        inputPanel.add(lastNameField, gbc);

        gbc.gridy++;
        phoneField = new JTextField(15);
        inputPanel.add(phoneField, gbc);

        gbc.gridy++;
        emailField = new JTextField(20);
        inputPanel.add(emailField, gbc);

        gbc.gridy++;
        accountTypeComboBox = new JComboBox<>(new String[]{"Alumno", "Creador de cursos"});
        inputPanel.add(accountTypeComboBox, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(10);
        inputPanel.add(passwordField, gbc);

        gbc.gridy++;
        confirmPasswordField = new JPasswordField(10);
        inputPanel.add(confirmPasswordField, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        cancelarButton = new JButton("Cancelar");
        aceptarButton = new JButton("Aceptar");

        cancelarButton.addActionListener(e -> dispose());

        aceptarButton.addActionListener(e -> {
            if (validateFields()) {
                JOptionPane.showMessageDialog(this, "Registro exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        buttonPanel.add(cancelarButton);
        buttonPanel.add(aceptarButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private boolean validateFields() {
        if (nameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                phoneField.getText().isEmpty() || emailField.getText().isEmpty() ||
                passwordField.getPassword().length == 0 || confirmPasswordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!new String(passwordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error de Contraseña", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterWindow().setVisible(true));
    }
}
