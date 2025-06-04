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
    private static final String IMAGEN_POR_DEFECTO = "/images/pokeBall.png";
    private JTextField nameField, lastNameField, phoneField, emailField, greetingField;
    private JPasswordField passwordField, confirmPasswordField;
    private JLabel imageLabel;
    private JButton cancelarButton, aceptarButton;
    private JDateChooser dateChooser;
    private String rutaImagenSeleccionada = null;

    public RegisterWindow() {
    	  setTitle("Registro de Usuario");
          setSize(750, 600);
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

          gbc.gridy = 1;
          inputPanel.add(new JLabel("Apellidos:"), gbc);

          gbc.gridy = 2;
          inputPanel.add(new JLabel("Teléfono:"), gbc);

          gbc.gridy = 3;
          inputPanel.add(new JLabel("Email:"), gbc);

          gbc.gridy = 4;
          inputPanel.add(new JLabel("Contraseña:"), gbc);

          gbc.gridy = 5;
          inputPanel.add(new JLabel("Fecha (DD/MM/AAAA):"), gbc);

          gbc.gridy = 6;
          inputPanel.add(new JLabel("Saludo:"), gbc);

          gbc.gridx = 1;
          gbc.gridy = 0;
          nameField = new JTextField(20);
          inputPanel.add(nameField, gbc);

          gbc.gridy = 1;
          lastNameField = new JTextField(20);
          inputPanel.add(lastNameField, gbc);

          gbc.gridy = 2;
          phoneField = new JTextField(15);
          inputPanel.add(phoneField, gbc);

          gbc.gridy = 3;
          emailField = new JTextField(20);
          inputPanel.add(emailField, gbc);

          gbc.gridy = 4;
          passwordField = new JPasswordField(10);
          inputPanel.add(passwordField, gbc);

          gbc.gridx = 2;
          inputPanel.add(new JLabel("Confirmar Contraseña:"), gbc);

          gbc.gridx = 3;
          confirmPasswordField = new JPasswordField(10);
          inputPanel.add(confirmPasswordField, gbc);

          gbc.gridx = 1;
          gbc.gridy = 5;
          dateChooser = new JDateChooser();
          dateChooser.setDateFormatString("dd/MM/yyyy");
          dateChooser.setPreferredSize(new Dimension(140, 25));
          inputPanel.add(dateChooser, gbc);

          gbc.gridx = 1;
          gbc.gridy = 6;
          greetingField = new JTextField(15);
          inputPanel.add(greetingField, gbc);

          gbc.gridx = 2;
          gbc.gridy = 6;
          inputPanel.add(new JLabel("Imagen:"), gbc);

          gbc.gridx = 3;
          gbc.gridy = 7;
          gbc.gridheight = 2;
          ImageIcon defaultIcon = new ImageIcon(getClass().getResource(IMAGEN_POR_DEFECTO));
          Image defaultImg = defaultIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
          imageLabel = new JLabel(new ImageIcon(defaultImg));
          imageLabel.setPreferredSize(new Dimension(120, 120));
          imageLabel.setHorizontalAlignment(JLabel.CENTER);
          

          imageLabel.addMouseListener(new MouseAdapter() {
              @Override
              public void mouseClicked(MouseEvent e) {
                  PanelArrastraImagen panelArrastraImagen = new PanelArrastraImagen(RegisterWindow.this);
                  panelArrastraImagen.setVisible(true);
                  rutaImagenSeleccionada = panelArrastraImagen.getRutaImagenSeleccionada();

                  if (rutaImagenSeleccionada != null) {
                      ImageIcon icon;
                      try {
                          if (rutaImagenSeleccionada.startsWith("http")) {
                              icon = new ImageIcon(new URL(rutaImagenSeleccionada));
                          } else {
                              icon = new ImageIcon(rutaImagenSeleccionada);
                          }
                          Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                          imageLabel.setIcon(new ImageIcon(img));
                      } catch (Exception ex) {
                          ex.printStackTrace();
                      }
                  } else {
                      ImageIcon defaultIcon = new ImageIcon(getClass().getResource(IMAGEN_POR_DEFECTO));
                      Image defaultImg = defaultIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                      imageLabel.setIcon(new ImageIcon(defaultImg));
                      rutaImagenSeleccionada = IMAGEN_POR_DEFECTO;
                  }
              }
          });

          inputPanel.add(imageLabel, gbc);
          mainPanel.add(inputPanel, BorderLayout.CENTER);

          JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
          cancelarButton = new JButton("Cancelar");
          aceptarButton = new JButton("Aceptar");

          cancelarButton.addActionListener(e -> dispose());

          aceptarButton.addActionListener(e -> {
              if (validateFields()) {
                  Date date = dateChooser.getDate();
                  LocalDateTime fechaNacimiento = date.toInstant()
                          .atZone(ZoneId.systemDefault())
                          .toLocalDateTime();

                 /* boolean success = Controlador.INSTANCE.registrarUsuario(
                          nameField.getText(),
                          lastNameField.getText(),
                          emailField.getText(),
                          phoneField.getText(),
                          new String(passwordField.getPassword()),
                          fechaNacimiento,
                          greetingField.getText(),
                          rutaImagenSeleccionada
                  );
	*/
                  if (true) {
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
        if (nameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                phoneField.getText().isEmpty() || emailField.getText().isEmpty() ||
                dateChooser.getDate() == null || passwordField.getPassword().length == 0 ||
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterWindow().setVisible(true));
    }
}


