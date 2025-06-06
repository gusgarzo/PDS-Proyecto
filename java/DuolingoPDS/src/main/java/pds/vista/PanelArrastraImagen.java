package pds.vista;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelArrastraImagen extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane = new JPanel();
    private List<File> archivosSubidos = new ArrayList<>();
    private String imagenDesdeURL = null;
    private JLabel imagenLabel;

    public PanelArrastraImagen(JFrame owner) {
        super(owner, "Agregar fotos", true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());

        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        getContentPane().add(contentPane, BorderLayout.CENTER);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");
        editorPane.setText("<h1>Agregar Foto</h1><br> Puedes arrastrar el fichero aquí.");
        contentPane.add(editorPane);

        imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPane.add(imagenLabel);

        editorPane.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);

                    if (!droppedFiles.isEmpty()) {
                        File file = droppedFiles.get(0);
                        archivosSubidos.clear();
                        imagenDesdeURL = null;
                        archivosSubidos.add(file);

                        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        imagenLabel.setIcon(new ImageIcon(img));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Botón para seleccionar desde PC
        JButton botonElegir = new JButton("Seleccionar de tu ordenador");
        botonElegir.setForeground(Color.WHITE);
        botonElegir.setBackground(SystemColor.textHighlight);
        botonElegir.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar Imagen");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg", "gif", "jfif", "webp"));

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    archivosSubidos.clear();
                    imagenDesdeURL = null;
                    archivosSubidos.add(selectedFile);

                    ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                    Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    imagenLabel.setIcon(new ImageIcon(img));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPane.add(botonElegir);

        // ✅ Botón para cargar desde URL
        JButton botonDesdeURL = new JButton("Desde URL");
        botonDesdeURL.setForeground(Color.WHITE);
        botonDesdeURL.setBackground(new Color(0, 120, 215));
        botonDesdeURL.addActionListener(e -> {
            String urlInput = JOptionPane.showInputDialog(this, "Pega la URL de la imagen:");
            if (urlInput != null && !urlInput.trim().isEmpty()) {
                try {
                    URL url = new URL(urlInput.trim());
                    BufferedImage image = ImageIO.read(url);
                    if (image == null) throw new Exception("No se pudo leer la imagen desde la URL.");

                    imagenDesdeURL = urlInput.trim();
                    archivosSubidos.clear();

                    Image imgEscalada = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    imagenLabel.setIcon(new ImageIcon(imgEscalada));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen desde la URL.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPane.add(botonDesdeURL);

        // Panel de botones inferior
        JPanel panelBotones = new JPanel();
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(ev -> dispose());
        btnCancelar.addActionListener(ev -> {
            archivosSubidos.clear();
            imagenDesdeURL = null;
            dispose();
        });

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
    }

    public List<File> showDialog() {
        this.setVisible(true);
        return archivosSubidos;
    }

    // ✅ NUEVO: devuelve la ruta de imagen seleccionada, ya sea de archivo o URL
    public String getRutaImagenSeleccionada() {
        if (imagenDesdeURL != null) return imagenDesdeURL;
        if (!archivosSubidos.isEmpty()) return archivosSubidos.get(0).getAbsolutePath();
        return null;
    }
   
}