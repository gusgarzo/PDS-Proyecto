package pds.vista;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import pds.dominio.*;

public class PreguntasPanel extends JPanel {
    private RealizarCurso realizarCurso;
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private JButton btnAnterior;
    private JButton btnResponder;
    private JLabel lblProgreso;
    private JButton btnSiguiente;
    private List<Pregunta> preguntasActuales;
    private int indicePreguntaActual = 0;

    public PreguntasPanel(RealizarCurso realizarCurso) {
    	System.out.println("asdasdsaad");
        this.realizarCurso = realizarCurso;
        this.preguntasActuales = realizarCurso.crearListaPreguntas(); 
        setLayout(new BorderLayout());
        setBackground(new Color(0xF7F7F7));

       
        // Panel de progreso
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(new Color(0xF7F7F7));
        lblProgreso = new JLabel();
        lblProgreso.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblProgreso.setForeground(new Color(0x0075BE));
        panelSuperior.add(lblProgreso);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel de cards para tipos de pregunta
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.add(new PreguntaFlashCardPanel(), "FlashCard");
        cardsPanel.add(new PreguntaHuecosPanel(), "Huecos");
        cardsPanel.add(new PreguntaTipoTestPanel(), "TipoTest");
        add(cardsPanel, BorderLayout.CENTER);
        
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(0xF7F7F7));

        btnAnterior = crearBotonPokemon("Anterior", e -> manejarAnterior());
        btnResponder = crearBotonPokemon("Responder", e -> validarRespuesta());
        btnSiguiente = crearBotonPokemon("Siguiente", e -> manejarSiguiente());

        panelBotones.add(btnAnterior);
        panelBotones.add(btnResponder);
        panelBotones.add(btnSiguiente);
        add(panelBotones, BorderLayout.SOUTH);
        cargarPreguntasBloqueActual();
        mostrarPreguntaActual(); 
        actualizarBotones();    
       
    }
    
    private JButton crearBotonPokemon(String texto, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        boton.setBackground(new Color(0xFFCC00));
        boton.setForeground(new Color(0x0075BE));
        boton.setFocusPainted(false);
        boton.addActionListener(accion);
        return boton;
    }
    
    
    public void validarRespuesta() {
        Pregunta pregunta = preguntasActuales.get(indicePreguntaActual);
        boolean correcta = false;
        String respuestaUsuario = "";

        try {
            if (pregunta instanceof PreguntaHuecos) {
                PreguntaHuecosPanel panel = (PreguntaHuecosPanel) cardsPanel.getComponent(1);
                respuestaUsuario = panel.getRespuesta();
                correcta = ((PreguntaHuecos) pregunta).isCorrecta(respuestaUsuario);
            } 
            else if (pregunta instanceof PreguntaTipoTest) {
                PreguntaTipoTestPanel panel = (PreguntaTipoTestPanel) cardsPanel.getComponent(2);
                respuestaUsuario = panel.getRespuestaSeleccionada();
                correcta = pregunta.isCorrecta(respuestaUsuario);
            }
            else if (pregunta instanceof PreguntaFlashCard) {
                // Las flashcards no requieren validación
                correcta = true;
            }

            mostrarResultado(correcta);
            
            if (correcta) {
                manejarSiguiente(); // Avanzar automáticamente
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al validar la respuesta: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void manejarSiguiente() {
        if (indicePreguntaActual < preguntasActuales.size() - 1) {
            indicePreguntaActual++;
        } else {
            if (realizarCurso.avanzarBloque()) {
                cargarPreguntasBloqueActual();
            } else {
                // Cierra el diálogo padre
                Window parentWindow = SwingUtilities.getWindowAncestor(this);
                JOptionPane.showMessageDialog(parentWindow, 
                    "¡Has completado todo el curso!", 
                    "Curso finalizado", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                if (parentWindow != null) {
                    parentWindow.dispose(); // Cierra el diálogo
                }
            }
        }
        mostrarPreguntaActual();
        actualizarBotones();
    }
    
    private void mostrarPreguntaActual() {
        if (indicePreguntaActual < preguntasActuales.size()) {
            Pregunta pregunta = preguntasActuales.get(indicePreguntaActual);
            actualizarUIsegúnTipo(pregunta);
            actualizarProgreso();
        }
    }
    private void actualizarProgreso() {
        String progreso = String.format("Bloque %d/%d - Pregunta %d/%d",
            realizarCurso.getIndBloqueActual() + 1,
            realizarCurso.getNumBloques(),
            indicePreguntaActual + 1,
            preguntasActuales.size()
        );
        lblProgreso.setText(progreso);
    }
    
    private void mostrarResultado(boolean correcta) {
        String mensaje = correcta ? "¡Respuesta correcta! 🎉" : "Respuesta incorrecta ❌";
        Color color = correcta ? new Color(0x4CAF50) : new Color(0xF44336);
        
        JOptionPane.showMessageDialog(this, 
            "<html><div style='text-align:center; font-size:16pt; color:" + 
            String.format("#%06x", color.getRGB() & 0x00FFFFFF) + "'>" + 
            mensaje + "</div></html>", 
            "Resultado", 
            JOptionPane.PLAIN_MESSAGE
        );
    }
    private void actualizarUIsegúnTipo(Pregunta pregunta) {
    	System.out.println("asdasdasdsads");
        if (pregunta instanceof PreguntaFlashCard) {
            PreguntaFlashCardPanel panel = (PreguntaFlashCardPanel) cardsPanel.getComponent(0);
            System.out.println(pregunta.getEnunciado());
            System.out.println("asdalsdnlsandn");
            panel.setPregunta((PreguntaFlashCard) pregunta);
            cardLayout.show(cardsPanel, "FlashCard");
        } else if (pregunta instanceof PreguntaHuecos) {
            PreguntaHuecosPanel panel = (PreguntaHuecosPanel) cardsPanel.getComponent(1);
            panel.setPregunta((PreguntaHuecos) pregunta);
            cardLayout.show(cardsPanel, "Huecos");
        } else if (pregunta instanceof PreguntaTipoTest) {
            PreguntaTipoTestPanel panel = (PreguntaTipoTestPanel) cardsPanel.getComponent(2);
            panel.setPregunta((PreguntaTipoTest) pregunta);
            cardLayout.show(cardsPanel, "TipoTest");
        }
    }
    
    private void actualizarBotones() {
        // Botón Anterior
        boolean puedeRetrocederBloque = realizarCurso.puedeRetrocederBloque();
        btnAnterior.setEnabled(indicePreguntaActual > 0 || puedeRetrocederBloque);

        // Botón Siguiente
        boolean puedeAvanzarBloque = realizarCurso.puedeAvanzarBloque();
        boolean haySiguientePregunta = indicePreguntaActual < preguntasActuales.size() - 1;
        btnSiguiente.setEnabled(haySiguientePregunta || puedeAvanzarBloque);
    }
    
    private void manejarAnterior() {
    	 if (indicePreguntaActual > 0) {
             indicePreguntaActual--;
         } else {
             if (realizarCurso.retrocederBloque()) {
                 cargarPreguntasBloqueActual();
             } else {
                 JOptionPane.showMessageDialog(this, 
                     "¡Has completado todo el curso!", 
                     "Curso finalizado", 
                     JOptionPane.INFORMATION_MESSAGE
                 );
             }
         }
         mostrarPreguntaActual();
         actualizarBotones();
    }
    private void cargarPreguntasBloqueActual() {
        this.preguntasActuales = realizarCurso.crearListaPreguntas();
        indicePreguntaActual = 0; // Resetear índice al cambiar de bloque
    }
 

    
}
