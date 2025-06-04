package pds.vista;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pds.controlador.Controlador;
import pds.dominio.Usuario;

public class DuolingoLogin extends JFrame {
    private JPanel contentPane;
    private JFrame frmLogin;  // Declara frmLogin como JFrame
    private JTextField textUsuario;
    private JPasswordField textPassword;

    public DuolingoLogin() {
        frmLogin = this;  // Asigna frmLogin a la instancia actual de ChatLoginWindow

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        contentPane.add(panel1, BorderLayout.SOUTH);
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        
        JButton registrar = new JButton("Registrar");
        panel1.add(registrar);
        
        registrar.addActionListener(ev -> {
            RegisterWindow reg = new RegisterWindow();
            reg.setVisible(true);
        });
        panel1.add(Box.createHorizontalGlue());
        
        JButton aceptar = new JButton("Aceptar");
        JButton cancelar = new JButton("Cancelar");
        panel1.add(cancelar);
        panel1.add(aceptar);
        
        // Acci�n del bot�n Cancelar
        cancelar.addActionListener(ev -> {
    		setVisible(false);
    		return;
        });

        JPanel telCon = new JPanel();
        contentPane.add(telCon, BorderLayout.CENTER);
        telCon.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        textUsuario = new JTextField(20);
        JLabel userLabel = new JLabel("Usuario");
        userLabel.setHorizontalAlignment(JLabel.CENTER);
        textPassword = new JPasswordField(20);
        JLabel passwordLabel = new JLabel("Contraseña");
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        telCon.add(userLabel, gbc);

        gbc.gridy = 1;
        telCon.add(textUsuario, gbc);

        gbc.gridy = 2;
        telCon.add(passwordLabel, gbc);

        gbc.gridy = 3;
        telCon.add(textPassword, gbc);

        add(telCon, BorderLayout.CENTER);
        
        JLabel titleLabel = new JLabel("DuolingoPDS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        
        addManejadorBotonLogin(aceptar);
    }

    private void addManejadorBotonLogin(JButton aceptar) {
        aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textUsuario.getText().trim();
                String contrasena = new String(textPassword.getPassword());

                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(frmLogin, "Por favor complete todos los campos", "Campos vac�os", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean loginExitoso = Controlador.getInstancia().loginUsuario(usuario, contrasena);

                if (loginExitoso) {
                    Usuario u = Controlador.getInstancia().getUsuarioActual();
                    JOptionPane.showMessageDialog(frmLogin, "�Bienvenido, " + u.getNombre() + "!", "Login correcto", JOptionPane.INFORMATION_MESSAGE);
                    MainWindow main = new MainWindow();
                    main.setVisible(true);
                    frmLogin.dispose();
                } else {
                    JOptionPane.showMessageDialog(frmLogin, "Usuario o contrase�a incorrectos", "Login fallido", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


	
}
