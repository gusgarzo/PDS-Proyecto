package pds.vista;

import javax.swing.*;
import java.awt.*;

import pds.controlador.Controlador;



import com.toedter.calendar.JDateChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import java.net.URL;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class RegisterWindow extends JFrame {
    private JTextField nameField, lastNameField, phoneField, emailField, greetingField;
    private JPasswordField passwordField, confirmPasswordField;
    private JLabel imageLabel;
    private JButton cancelarButton, aceptarButton;
    private JDateChooser dateChooser;
    
    private String rutaImagenSeleccionada = null;
    private JComboBox<String> tipoUsuarioCombo;


    public RegisterWindow() {
        setTitle("Registro de Usuario");
        setSize(750, 400);
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
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Apellidos:"), gbc);

        gbc.gridx = 1;
        lastNameField = new JTextField(20);
        inputPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Teléfono:"), gbc);

        gbc.gridx = 1;
        phoneField = new JTextField(15);
        inputPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        inputPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(10);
        inputPanel.add(passwordField, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Confirmar Contraseña:"), gbc);

        gbc.gridx = 3;
        confirmPasswordField = new JPasswordField(10);
        inputPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Tipo de usuario:"), gbc);

        gbc.gridx = 1;
        tipoUsuarioCombo = new JComboBox<>(new String[]{"Alumno", "Creador de Curso"});
        inputPanel.add(tipoUsuarioCombo, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        cancelarButton = new JButton("Cancelar");
        aceptarButton = new JButton("Aceptar");

        cancelarButton.addActionListener(e -> dispose());

        aceptarButton.addActionListener(e -> {
            if (validateFields()) {
                String nombre = nameField.getText();
                String apellidos = lastNameField.getText();
                String telefono = phoneField.getText();
                String correo = emailField.getText();
                String contrasena = new String(passwordField.getPassword());
                String tipoUsuario = (String) tipoUsuarioCombo.getSelectedItem();
                
                boolean success = Controlador.INSTANCE.registrarUsuario(
                    nombre,
                    apellidos,
                    telefono,
                    correo,
                    contrasena,
                    tipoUsuario
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Registro exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(cancelarButton);
        buttonPanel.add(aceptarButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private boolean validateFields() {
        if (nameField.getText().isEmpty() ||
            lastNameField.getText().isEmpty() ||
            phoneField.getText().isEmpty() ||
            emailField.getText().isEmpty() ||
            passwordField.getPassword().length == 0 ||
            confirmPasswordField.getPassword().length == 0) {

            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!new String(passwordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error de Contraseña", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }


}


