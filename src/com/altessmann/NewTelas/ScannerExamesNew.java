package com.altessmann.NewTelas;

import com.altessmann.scaner.SynchronousHelper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import eu.gnome.morena.Camera;
import eu.gnome.morena.Configuration;
import eu.gnome.morena.Device;
import eu.gnome.morena.Manager;
import eu.gnome.morena.Scanner;
import eu.gnome.morena.TransferListener;
import static ij.plugin.FFT.fileName;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Ritiele Aldeburg
 */
public class ScannerExamesNew extends javax.swing.JFrame implements TransferListener {

    int c = 0;
    private ExamesNew examesNew;
    private BufferedImage bimage = ImageIO.read(getClass().getResource("/imagens/Medical.jpg").openStream());
    // img =ImageIO.read(getClass().getResource("/path/img.jpg").openStream());
    //BufferedImage bimage = ;
    
    static Manager manager = Manager.getInstance();
    static String deviceName;
    static int pages = 0; // scan number of pages in one session (0 - designates scanning until ADF is empty)
    Scanner scanner;

    /**
     * Creates new form ScanerExamesNew
     */
    public ScannerExamesNew() throws IOException {
        initComponents();
        ImageIcon image = new ImageIcon(bimage);
        jLabel1.setIcon(new ImageIcon(image.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_DEFAULT)));
    }

    private Device selectDevice() {
        try {
            List<? extends Device> devices = manager.listDevices();
            Device device = null;
            if (devices.size() > 0) {
                if (deviceName != null) // search for device name match
                {
                    for (Device dev : devices) {
                        System.err.println("connected device " + dev);
                        jLabelPorcentagem.setText("");
                        jLabelPorcentagem.setText("Conectando a " + dev);
                        if (dev.toString().startsWith(deviceName)) {
                            device = dev;
                        }
                    }
                } else {// select first device
                    device = devices.get(0);
                }
            } else {
                System.out.println("Nenhum scanner selecionado!!!");
                jLabelPorcentagem.setText("");
                jLabelPorcentagem.setText("Nenhum scanner encontrado!!!");
                try {
                    Thread.sleep(2000);
                    jLabelPorcentagem.setText("");
                    jLabelPorcentagem.setText("Verifique se o Scanner esta conectado e/ou ligado!");
                    Thread.sleep(3000);
                    jButtonNovo.setEnabled(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ScannerExamesNew.class.getName()).log(Level.SEVERE, null, ex);
                }

                return device;
            }
            jButtonCancelar.setEnabled(true);
            jLabelPorcentagem.setText("");
            jLabelPorcentagem.setText("Scanner Selecionado: " + device);
            System.err.println("device selected = " + device);
            return device;
        } catch (NullPointerException ex) {
            Device device = null;
            System.out.println("Nenhum scanner selecionado!!!");
            jLabelPorcentagem.setText("");
            jLabelPorcentagem.setText("Nenhum scanner encontrado!!!");
            try {
                Thread.sleep(2000);
                jLabelPorcentagem.setText("");
                jLabelPorcentagem.setText("Verifique se o Scanner esta conectado e/ou ligado!");
                Thread.sleep(3000);
                jButtonNovo.setEnabled(true);
            } catch (InterruptedException e) {
                // Logger.getLogger(ScannerExamesNew.class.getName()).log(Level.SEVERE, null, ex);
            }
            return device;
        }

    }

    private void simpleScan() throws Exception {
        Device device = selectDevice();

        if (device != null) { // for scanner device set the scanning parameters
            if (device instanceof Scanner) {
                scanner = (Scanner) device;
                scanner.setMode(Scanner.RGB_8);
                scanner.setResolution(75);
                //scanner.setFrame(0, 0, 622, 877);   // A4 8.3 x 11.7 ( 622 x 877 at 75 DPI) (for Lide110 - 622 x 874)
                scanner.setFrame(0, 0, 622, 874);
            } else if (device instanceof Camera) {
                // Camera specific settings
            }

            // start scan using default (0) functional unit
            bimage = SynchronousHelper.scanImage(device);
            System.err.println("scanned image info: size=(" + bimage.getWidth() + ", " + bimage.getHeight() + ")   bit mode=" + bimage.getColorModel().getPixelSize());
            jLabelPorcentagem.setText("");
            jLabelPorcentagem.setText("Finalizando Digitalização ...");
            Thread.sleep(3000);
            jLabelPorcentagem.setText("");
            jLabelPorcentagem.setText("AGUARDE UM MOMENTO... GERANDO ARQUIVO!");

            // do image processing if necessary
            // ...
            Thread.sleep(3000);
            jLabelPorcentagem.setText("");
            jLabelPorcentagem.setText("Exame Digitalizado!");
            jButtonAdicionar.setEnabled(true);

            new Thread() {
                @Override
                public void run() {
                    ImageIcon image = new ImageIcon(bimage);
                    jLabel1.setIcon(new ImageIcon(image.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_DEFAULT)));
                   //
                    
                   // jLabel1.setIcon(image);
                }
            }.start();

        }
    }

    public void getInstanceOfExame(ExamesNew examesNew) {
        this.examesNew = examesNew;
    }

    /**
     *
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonNovo = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jButtonAdicionar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelPorcentagem = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Altessmann Sistemas - Digitalizar Exames");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), " Scanear Exames  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jButtonNovo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/scaner24x24.png"))); // NOI18N
        jButtonNovo.setText("Digitalizar Exame");
        jButtonNovo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoActionPerformed(evt);
            }
        });

        jButtonCancelar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cancel24x24.png"))); // NOI18N
        jButtonCancelar.setText("Cancelar Digitalização");
        jButtonCancelar.setDoubleBuffered(true);
        jButtonCancelar.setEnabled(false);
        jButtonCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jButtonAdicionar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/save24x24.png"))); // NOI18N
        jButtonAdicionar.setText("Adicionar Documento");
        jButtonAdicionar.setDoubleBuffered(true);
        jButtonAdicionar.setEnabled(false);
        jButtonAdicionar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jButtonNovo)
                .addGap(5, 5, 5)
                .addComponent(jButtonAdicionar)
                .addGap(5, 5, 5)
                .addComponent(jButtonCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Medical.jpg"))); // NOI18N
        jLabel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jLabel1ComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelPorcentagem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelPorcentagem.setText("Status do Serviço ...");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelPorcentagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelPorcentagem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoActionPerformed

        jButtonNovo.setEnabled(false);

        new Thread() {
            @Override
            public void run() {
                try {
                    simpleScan();
                    //selectDevice();
                } catch (Exception ex) {
                    //manager.close();
                    Logger.getLogger(ScannerExamesNew.class.getName()).log(Level.SEVERE, null, ex);
                    jButtonNovo.setEnabled(true);
                    jButtonCancelar.setEnabled(false);
                }
            }
        }.start();
    }//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
//        if (examesNew == null) {
//            examesNew = new ExamesNew();
//        }
//        examesNew.ativaBotaoAddArquivoEExcluirArquivo();
        jButtonNovo.setEnabled(true);
        jButtonCancelar.setEnabled(false);
        jButtonAdicionar.setEnabled(false);
        
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarActionPerformed
        //BufferedImage teste = null;
        //c++;
        //try {
        // teste = ImageIO.read(new File("C:\\Users\\Guilherme\\Pictures\\2017-04-11 foto\\" + c + ".jpg"));
        /// } catch (IOException ex) {
        // Logger.getLogger(ScannerExamesNew.class.getName()).log(Level.SEVERE, null, ex);
        //}
        //String valorString = JOptionPane.showInputDialog("Nome do arquivo", "Arquivo " + c);
        //if (valorString != null) {
        if (examesNew == null) {
            examesNew = new ExamesNew();
        }
        examesNew.getArquivosByte(bimage, 1);
        jButtonAdicionar.setEnabled(false);
        jButtonNovo.setEnabled(true);
        jButtonCancelar.setEnabled(false);
        this.dispose();
        //}
    }//GEN-LAST:event_jButtonAdicionarActionPerformed

    private void jLabel1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabel1ComponentResized
        new Thread() {
            @Override
            public void run() {
                ImageIcon image = new ImageIcon(bimage);
                jLabel1.setIcon(new ImageIcon(image.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_DEFAULT)));
            }
        }.start();
    }//GEN-LAST:event_jLabel1ComponentResized

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        new Thread() {
            @Override
            public void run() {
                ImageIcon image = new ImageIcon(bimage);
                jLabel1.setIcon(new ImageIcon(image.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_DEFAULT)));
            }
        }.start();
    }//GEN-LAST:event_formComponentResized

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (examesNew == null) {
            examesNew = new ExamesNew();
        }
        examesNew.ativaBotaoAddArquivoEExcluirArquivo();
        dispose();
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ScannerExamesNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ScannerExamesNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ScannerExamesNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScannerExamesNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        manager = Manager.getInstance();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ScannerExamesNew().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ScannerExamesNew.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdicionar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelPorcentagem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
   @Override
    public void transferDone(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            if (image != null) {
                // ImagePanel imagePanel = new ImagePanel(image);
                // MainPanel.this.add(imagePanel);
                // select(imagePanel);
                // int size = (int) Math.round(Math.sqrt(getComponentCount()));
                // setLayout(new GridLayout(size, size));
                new Thread() {
                    @Override
                    public void run() {
                        ImageIcon image = new ImageIcon(getClass().getResource("/imagens/Medical.jpg"));
                        jLabel1.setIcon(new ImageIcon(image.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_DEFAULT)));
                    }
                }.start();
                jLabelPorcentagem.setText("Finalizado [" + file.getAbsolutePath() + "]...");
                validate();
            } else {
                jLabelPorcentagem.setText("Finalizado [" + file.getAbsolutePath() + "] - Não foi possivel exibir esse tipo de imagem!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        setEnabled(true);
        jButtonCancelar.setEnabled(false);
    }

    @Override
    public void transferFailed(int code, String message) {
        jLabelPorcentagem.setText(message + " [0x" + Integer.toHexString(code) + "]");
        setEnabled(true);
        jButtonCancelar.setEnabled(false);
    }

    @Override
    public void transferProgress(int percent) {
        jLabelPorcentagem.setText(percent + "%");
    }
}
