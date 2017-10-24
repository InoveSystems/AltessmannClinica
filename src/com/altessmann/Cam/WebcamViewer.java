package com.altessmann.Cam;

import com.altessmann.NewTelas.CadastroPacienteNew;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;

public class WebcamViewer extends JFrame implements ActionListener, Runnable, WebcamListener, WindowListener, UncaughtExceptionHandler, ItemListener, WebcamDiscoveryListener {

    private static final long serialVersionUID = 1L;
    private Webcam webcam = null;
    private WebcamPanel panel = null;
    private WebcamPicker picker = null;
    private JButton jfoto = new JButton("Tirar Foto");
    private int codigo = 0;
    private CadastroPacienteNew paciente;
    private String mensg;
    private final int tipo = 1;
    int c = 0;

    public void run(int cod) {

        //this.codigo = cod + 1;
        Webcam.addDiscoveryListener(this);
        setTitle("Cadastro Paciente - Foto");
        setLayout(new BorderLayout());
        addWindowListener(this);
        picker = new WebcamPicker();
        picker.addItemListener(this);
        webcam = picker.getSelectedWebcam();
        if (webcam == null) {
            System.out.println("Nenhuma Camera encontrada...");
            System.exit(1);
        }
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        // webcam.setViewSize(new Dimension(1024, 768));
        webcam.addWebcamListener(WebcamViewer.this);
        panel = new WebcamPanel(webcam, false);
        panel.setFPSDisplayed(true);
        add(picker, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(jfoto, BorderLayout.SOUTH);
        jfoto.addActionListener(this);
        pack();
        setVisible(true);
        Thread t = new Thread() {
            @Override
            public void run() {
                panel.start();
            }
        };
        t.setName("example-starter");
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();

    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new WebcamViewer());

    }

    public void recebeid(CadastroPacienteNew paciente1) {
        this.paciente = paciente1;

    }

    @Override
    public void webcamOpen(WebcamEvent we) {
        System.out.println("webcam open");
    }

    @Override
    public void webcamClosed(WebcamEvent we) {
        System.out.println("webcam closed");
    }

    @Override
    public void webcamDisposed(WebcamEvent we) {
        System.out.println("webcam disposed");
    }

    @Override
    public void webcamImageObtained(WebcamEvent we) {
        // do nothing
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        panel.stop();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        panel.stop();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("webcam viewer resumed");
        panel.resume();
    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("webcam viewer paused");
        panel.pause();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(String.format("Exception in thread %s", t.getName()));
        e.printStackTrace();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() != webcam) {
            if (webcam != null) {

                panel.stop();

                remove(panel);

                webcam.removeWebcamListener(this);
                webcam.close();

                webcam = (Webcam) e.getItem();
                webcam.setViewSize(WebcamResolution.VGA.getSize());
                webcam.addWebcamListener(this);

                System.out.println("selected " + webcam.getName());

                panel = new WebcamPanel(webcam, false);
                panel.setFPSDisplayed(true);

                add(panel, BorderLayout.CENTER);
                pack();

                Thread t = new Thread() {

                    @Override
                    public void run() {
                        panel.start();
                    }
                };
                t.setName("example-stoper");
                t.setDaemon(true);
                t.setUncaughtExceptionHandler(this);
                t.start();
            }
        }
    }

    @Override
    public void webcamFound(WebcamDiscoveryEvent event) {
        if (picker != null) {
            picker.addItem(event.getWebcam());
        }
    }

    @Override
    public void webcamGone(WebcamDiscoveryEvent event) {
        if (picker != null) {
            picker.removeItem(event.getWebcam());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread t = new Thread() {
            @Override
            public void run() {
                paciente = new CadastroPacienteNew(0);
            }
        };
        t.setName("abrindo outra janela");
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();

        new Thread() {
            @Override
            public void run() {

                if (e.getSource() == jfoto) {
                    // BufferedImage image = webcam.getImage();
                    /*try {
                        ImageIO.write(image, "JPG", new File("FotosPacientesCadastro\\"+codigo+".jpg"));// save image to PNG file
                    } catch (IOException ex) {
                        Logger.getLogger(WebcamViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
                    if (paciente != null) {
                        paciente.takePicture(webcam.getImage());
                        dispose();
                    }
                }
                System.gc();
            }
        }.start();
        try {
            Thread.currentThread().sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(WebcamViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override

    public void run() {
        Webcam.addDiscoveryListener(this);
        setTitle("Cadastro Paciente - Foto");
        setLayout(new BorderLayout());
        addWindowListener(this);
        picker = new WebcamPicker();
        picker.addItemListener(this);
        webcam = picker.getSelectedWebcam();
        if (webcam == null) {
            System.out.println("Nenhuma Camera encontrada...");
            System.exit(1);
        }
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        // webcam.setViewSize(new Dimension(1024, 768));
        webcam.addWebcamListener(WebcamViewer.this);
        panel = new WebcamPanel(webcam, false);
        panel.setFPSDisplayed(true);
        add(picker, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(jfoto, BorderLayout.SOUTH);
        jfoto.addActionListener(this);
        pack();
        setVisible(true);
        Thread t = new Thread() {
            @Override
            public void run() {
                panel.start();
            }
        };
        t.setName("example-starter");
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();

    }
}
