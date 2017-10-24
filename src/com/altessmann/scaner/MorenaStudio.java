/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.scaner;

/*
 * Morena 7 - Image Acquisition Framework
 *
 * Copyright (c) 1999-2011 Gnome spol. s r.o. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Gnome spol. s r.o. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Gnome.
 */
/**
 * SimpleExample demonstrates use of the Morena Framework in both application
 * and applet environment. Upload action cant be used if it is invoked from
 * local filesystem.
 *
 * Requirements: 1. Java2 1.5 or newer 2. Morena7 for image acquisition
 *
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JApplet;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import eu.gnome.morena.Configuration;
import eu.gnome.morena.Device;
import eu.gnome.morena.DeviceListChangeListener;
import eu.gnome.morena.Manager;
import eu.gnome.morena.Scanner;
import eu.gnome.morena.TransferListener;

@SuppressWarnings("serial")
public class MorenaStudio extends JApplet {

    static public Manager manager;

    static {
        System.err.println("MorenaStudio started at " + (new Date()));
    }

    private static class MainPanel extends JPanel implements DeviceListChangeListener {

        private JTextField status = new JTextField();
        private ImagePanel selected = null;
        private SaveImageAction saveImageAction;
        private CancelAction cancelAction;
        private UploadImageAction uploadImageAction;
        private MouseListener mouseListener = new MouseListener();
        private boolean hasServer = false;
        private URL documentBase = null;
        private Scanner scanner = null;

        private class RemoveAllAction extends AbstractAction implements Runnable {

            RemoveAllAction() {
                super("Excluir Arquivo");
            }

            public synchronized void actionPerformed(ActionEvent event) {
                new Thread(this).start();
            }

            public synchronized void run() {
                removeAll();
                select(null);
                repaint();
            }
        }

        private class AcquireImageAction extends AbstractAction implements TransferListener {

            AcquireImageAction() {
                super("Escanear Imagem");
            }

            public synchronized void actionPerformed(ActionEvent event) {
                try {
                    status.setText("Working ...");
                    Device device = manager.selectDevice(MainPanel.this);
                    if (device != null) {
                        if (device instanceof Scanner) {
                            scanner = (Scanner) device;
                            if (scanner.setupDevice(MainPanel.this)) {
                                setEnabled(false);
                                cancelAction.setEnabled(true);
                                scanner.startTransfer(this);
                            }
                        } else {
                            scanner = null;
                            device.startTransfer(this);
                        }
                        status.setText(device + " Selecionada  ...");
                    } else {
                        status.setText("Falha, nenhum dispositivo encontrado ...");
                    }
                } catch (Throwable exception) {
                    JOptionPane.showMessageDialog(MainPanel.this, exception.toString(), "Erro", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                    status.setText("Falha, tente novamente ...");
                    setEnabled(true);
                    cancelAction.setEnabled(false);
                }
            }

            public void transferDone(File file) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    if (image != null) {
                        ImagePanel imagePanel = new ImagePanel(image);
                        MainPanel.this.add(imagePanel);
                        select(imagePanel);
                        int size = (int) Math.round(Math.sqrt(getComponentCount()));
                        setLayout(new GridLayout(size, size));
                        status.setText("Finalizado [" + file.getAbsolutePath() + "]...");
                        validate();
                    } else {
                        status.setText("Finalizado [" + file.getAbsolutePath() + "] - Não foi possivel exibir esse tipo de imagem!");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                setEnabled(true);
                cancelAction.setEnabled(false);
            }

            public void transferFailed(int code, String message) {
                status.setText(message + " [0x" + Integer.toHexString(code) + "]");
                setEnabled(true);
                cancelAction.setEnabled(false);
            }

            public void transferProgress(int percent) {
                status.setText(percent + "%");
            }
        }

        private class CancelAction extends AbstractAction {

            CancelAction() {
                super("Cancelar Escaneamento");
                setEnabled(false);
            }

            public synchronized void actionPerformed(ActionEvent event) {
                scanner.cancelTransfer();
            }
        }

        private class SaveImageAction extends AbstractAction implements Runnable {

            private class Filter extends FileFilter {

                String type;

                Filter(String type) {
                    this.type = type;
                }

                public boolean accept(File file) {
                    return file.getName().endsWith(type);
                }

                public String getDescription() {
                    return type + " Arquivos";
                }
            }

            SaveImageAction() {
                super("Salvar Arquivo");
            }

            public void actionPerformed(ActionEvent event) {
                new Thread(this).start();
            }

            public synchronized void run() {
                try {
                    status.setText("Working ...");
                    BufferedImage bufferedImage = selected.getImage();
                    JFileChooser chooser = new JFileChooser();
                    String e[] = ImageIO.getWriterFormatNames();
                    for (int i = 0; i < e.length; i++) {
                        chooser.addChoosableFileFilter(new Filter(e[i]));
                    }
                    int result = chooser.showSaveDialog(MainPanel.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        String ext = chooser.getFileFilter().getDescription();
                        ext = ext.substring(0, ext.indexOf(' ')).toLowerCase();
                        File file = chooser.getSelectedFile();
                        String name = file.getName();
                        if (!name.endsWith(ext)) {
                            file = new File(file.getParentFile(), name + "." + ext);
                        }
                        ImageIO.write(bufferedImage, ext, file);
                        status.setText("Finalizado - Imagem salva em " + file + "  ...");
                    } else {
                        status.setText("Cancelado  ...");
                    }
                } catch (Throwable exception) {
                    JOptionPane.showMessageDialog(MainPanel.this, exception.toString(), "Erro", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                    status.setText("Falha, tente novamente ...");
                }
            }

            public boolean isEnabled() {
                return selected != null;
            }
        }

        private class UploadImageAction extends AbstractAction implements Runnable {

            UploadImageAction() {
                super("Enviar para o BD");
            }

            public void actionPerformed(ActionEvent event) {
                new Thread(this).start();
            }

            public synchronized void run() {
                try {
                    status.setText("Working ...");
                    BufferedImage bufferedImage = selected.getImage();
                    ByteArrayOutputStream tmp = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "jpg", tmp);
                    tmp.close();
                    int contentLength = tmp.size();
                    if (contentLength > 1024 * 1024) {
                        throw new Exception("A imagem é muito grande para carregar");
                    }
                    URL uploadURL = new URL(documentBase, "upload.php");
                    HttpURLConnection connection = (HttpURLConnection) uploadURL.openConnection();
                    connection.setRequestMethod("POSTAR");
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setDefaultUseCaches(false);
                    connection.setRequestProperty("content-type", "img/jpeg");
                    connection.setRequestProperty("content-length", String.valueOf(contentLength));
                    OutputStream out = connection.getOutputStream();
                    out.write(tmp.toByteArray());
                    out.close();
                    InputStream in = connection.getInputStream();
                    int c;
                    while ((c = in.read()) != -1) {
                        System.err.write(c);
                    }
                    in.close();
                    URL imageURL = new URL(documentBase, connection.getHeaderField("Nome-Arquivo"));
                    status.setText("Feito - a imagem é carregada para" + imageURL + " (em aproximadamente 5 minutos) ...");
                } catch (Throwable exception) {
                    JOptionPane.showMessageDialog(MainPanel.this, exception.toString(), "Erro", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                    status.setText("Falha, tente novamente ...");
                }
            }

            public boolean isEnabled() {
                return hasServer && selected != null;
            }
        }

        private class MouseListener extends MouseAdapter {

            public void mouseClicked(MouseEvent event) {
                select((ImagePanel) event.getComponent());
            }
        }

        private class ImagePanel extends JPanel {

            private BufferedImage image;
            int imageWidth;
            int imageHeight;

            ImagePanel(BufferedImage image) {
                this.image = image;
                imageWidth = image.getWidth();
                imageHeight = image.getHeight();
                addMouseListener(mouseListener);
            }

            public BufferedImage getImage() {
                return image;
            }

            public void paint(Graphics g) {
                super.paint(g);
                int panelWidth = getWidth() - 6;
                int panelHeight = getHeight() - 6;
                double horizontalRatio = (double) panelWidth / imageWidth;
                double verticalRatio = (double) panelHeight / imageHeight;
                if (horizontalRatio > verticalRatio) {
                    g.drawImage(image, (int) (panelWidth - imageWidth * verticalRatio) / 2 + 3, 3, (int) (imageWidth * verticalRatio), (int) (imageHeight * verticalRatio), this);
                } else {
                    g.drawImage(image, 3, 3, (int) (imageWidth * horizontalRatio), (int) (imageHeight * horizontalRatio), this);
                }
            }

        }

        private class ToolBar extends JToolBar {

            ToolBar() {
//        List<Device> devices=manager.listDevices();
//        add(deviceCombo = new JComboBox<Device>(devices.toArray(new Device[devices.size()])));
                addSeparator();
                add(new AcquireImageAction());
                addSeparator();
                add(cancelAction = new CancelAction());
                addSeparator();
                add(saveImageAction = new SaveImageAction());
                saveImageAction.setEnabled(false);
                addSeparator();
                add(uploadImageAction = new UploadImageAction());
                uploadImageAction.setEnabled(false);
                addSeparator();
                add(new RemoveAllAction());
                setMargin(new Insets(4, 2, 2, 2));
            }
        }

        void select(ImagePanel image) {
            if (selected != null) {
                selected.setBorder(null);
            }
            selected = image;
            if (selected != null) {
                selected.setBorder(new LineBorder(Color.blue, 1));
                saveImageAction.setEnabled(true);
                uploadImageAction.setEnabled(hasServer);
            } else {
                saveImageAction.setEnabled(false);
                uploadImageAction.setEnabled(false);
            }
        }

        public void listChanged() {
            // deprecated      
        }

//    @Override
        public void deviceConnected(Device device) {
            status.setText("Dispositivo Conectado : " + device);
//      deviceCombo.addItem(device);
        }

//    @Override
        public void deviceDisconnected(Device device) {
            status.setText("Dispositivo Desconectado : " + device);
//      deviceCombo.removeItem(device);
        }

        MainPanel(Container container, URL documentBase) {
            this.documentBase = documentBase;
            status.setEditable(false);
            hasServer = documentBase != null && documentBase.getProtocol().indexOf("http") != -1;
            container.add(new ToolBar(), BorderLayout.NORTH);
            container.add(this, BorderLayout.CENTER);
            container.add(status, BorderLayout.SOUTH);
            setLayout(new GridLayout(1, 1));
            manager.addDeviceListChangeListener(this);
        }

    }

    public void init() {
        manager = Manager.getInstance();
        new MainPanel(getContentPane(), getDocumentBase());
    }

    @Override
    public void start() {
        System.err.println("Morena is available " + manager.available());
    }

    @Override
    public void stop() {
        manager.close();
    }

    public static void main(String args[]) {
        boolean nativeUI = false;
        if (args != null && args.length > 0) {
            if (args.length >= 1) {
                nativeUI = Boolean.parseBoolean(args[0]);
            }
        }

// -- Configuration settings
        System.err.println("Configuration: native UI - " + nativeUI);
        if (nativeUI) {
            Configuration.setMode(Configuration.MODE_NATIVE_UI);
        }

        Configuration.setLogLevel(Level.FINEST);
//  Configuration.setLogLevel(Level.ALL);								// - very verbose logging
//  Configuration.addDeviceType(".*fficejet.*", true);	// - workaround for HP scanners

        // -- Manager instantiation    
        manager = Manager.getInstance();
        JFrame frame = new JFrame("Altessmann - Scanner");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                manager.close();
            }
        });
        new MainPanel(frame.getContentPane(), null);
        frame.setBounds(100, 100, 800, 600);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    
}
