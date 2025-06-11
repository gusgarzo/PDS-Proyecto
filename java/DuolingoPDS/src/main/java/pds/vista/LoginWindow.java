package pds.vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import pds.controlador.Controlador;
import pds.controlador.ControladorCurso;
import pds.dominio.Alumno;
import pds.dominio.CreadorCurso;
import pds.dominio.Usuario;
import pds.dominio.CreadorCurso;

public class LoginWindow extends JFrame {
    private JPanel contentPane;
    private JTextField textUsuario;
    private JPasswordField textPassword;

    public LoginWindow() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 450, 500);

        // Fondo blanco y bordes suaves
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 30, 20, 30));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Panel superior (logo + título)
        JPanel northPanel = new JPanel();
     
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        // Logo Pokémon
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/PokeLogo.png"));
            Image logoImg = logoIcon.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(logoImg));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        } catch (Exception ex) {
            logoLabel.setText("PokeLingo");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 32));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        northPanel.add(logoLabel);

        // Imagen decorativa (Pokémon)
        JLabel imageLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/pokemonWall.png"));
            Image img = icon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        } catch (Exception ex) {
            imageLabel.setText("No se pudo cargar la imagen");
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        northPanel.add(Box.createVerticalStrut(10));
        northPanel.add(imageLabel);
        northPanel.add(Box.createVerticalStrut(10));

        contentPane.add(northPanel, BorderLayout.NORTH);

        // Panel central (campos de login)
        JPanel centerPanel = new JPanel(new GridBagLayout());
       
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Usuario");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        textUsuario = new JTextField(18);
        textUsuario.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel passwordLabel = new JLabel("Contraseña");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        textPassword = new JPasswordField(18);
        textPassword.setFont(new Font("Arial", Font.PLAIN, 15));

        gbc.gridwidth = 1;
        gbc.weightx = 1.0; // Distribuye espacio
        gbc.fill = GridBagConstraints.NONE; // No expandir horizontalmente

        // Espacio izquierdo
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(Box.createHorizontalStrut(10), gbc);

        // Usuario
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(userLabel, gbc);

        gbc.gridy++;
        centerPanel.add(textUsuario, gbc);

        gbc.gridy++;
        centerPanel.add(passwordLabel, gbc);

        gbc.gridy++;
        centerPanel.add(textPassword, gbc);

        // Espacio derecho
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        centerPanel.add(Box.createHorizontalStrut(10), gbc);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Panel inferior (botones)
        JPanel southPanel = new JPanel();
        
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

        JButton registrar = new JButton("Registrar");
        JButton cancelar = new JButton("Cancelar");
        JButton aceptar = new JButton("Aceptar");

        // Botones con estilo
        Font btnFont = new Font("Arial", Font.BOLD, 14);
        registrar.setFont(btnFont);
        cancelar.setFont(btnFont);
        aceptar.setFont(btnFont);

        registrar.setBackground(new Color(255, 215, 0)); // Amarillo Pokémon
        aceptar.setBackground(new Color(30, 144, 255));  // Azul Pokémon
        cancelar.setBackground(new Color(220, 53, 69));  // Rojo claro

        registrar.setForeground(Color.BLACK);
        aceptar.setForeground(Color.WHITE);
        cancelar.setForeground(Color.WHITE);

        registrar.setFocusPainted(false);
        aceptar.setFocusPainted(false);
        cancelar.setFocusPainted(false);

        registrar.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        aceptar.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        cancelar.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));

        southPanel.add(registrar);
        southPanel.add(Box.createHorizontalGlue());
        southPanel.add(cancelar);
        southPanel.add(Box.createHorizontalStrut(10));
        southPanel.add(aceptar);

        contentPane.add(southPanel, BorderLayout.SOUTH);

        // Listeners
        registrar.addActionListener(ev -> {
            RegisterWindow reg = new RegisterWindow();
            reg.setVisible(true);
        });

        cancelar.addActionListener(ev -> setVisible(false));

        addManejadorBotonLogin(aceptar);

        // Centrar la ventana en pantalla
        setLocationRelativeTo(null);
    }

    private void addManejadorBotonLogin(JButton aceptar) {
        aceptar.addActionListener(new ActionListener() {
			
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textUsuario.getText().trim();
                String contrasena = new String(textPassword.getPassword());

                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Por favor complete todos los campos", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean loginExitoso = Controlador.INSTANCE.loginUsuario(usuario, contrasena);
                loginExitoso = true;
                if (loginExitoso) {
                	Alumno u = new Alumno(
                    	    "Ash",          // nombre
                    	    "Ketchum",      // apellidos
                    	    "123456789",    // telefono
                    	    "ash@poke.com", // correo
                    	    "pikachu123"    // contrasena
                    	);//Controlador.INSTANCE.getUsuarioActual();
                    Controlador.INSTANCE.setUsuario(u);
                    ControladorCurso.INSTANCE.setUsuarioActual(u);
                    JOptionPane.showMessageDialog(LoginWindow.this, "¡Bienvenido, " + u.getNombre() + "!", "Login correcto", JOptionPane.INFORMATION_MESSAGE);
                    MainWindow main = new MainWindow();
                    main.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Usuario o contraseña incorrectos", "Login fallido", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
