/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.Backup_restore;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Ritiele Aldeburg
 */
public class BackupNew extends javax.swing.JFrame {

    final List<String> comandos = new ArrayList<String>();

    /**
     * Creates new form BackupNew
     */
    public BackupNew() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButtonIniciar = new javax.swing.JButton();
        jProgressBar = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        jLabelStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaReceive = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jCheckBoxAbrir = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelStatus1 = new javax.swing.JLabel();
        jButtonCarregar = new javax.swing.JButton();
        jTextCaminho = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaReceive1 = new javax.swing.JTextArea();
        jProgressBar1 = new javax.swing.JProgressBar();
        jButtonIniciar2 = new javax.swing.JButton();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));
        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonIniciar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jButtonIniciar.setText("Iniciar");
        jButtonIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIniciarActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, -1, 30));

        jProgressBar.setMaximum(5);
        jPanel1.add(jProgressBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 520, 30));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 102, 255));
        jLabel2.setText("Novo Backup");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 15, -1, -1));

        jLabelStatus.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelStatus.setForeground(new java.awt.Color(153, 153, 153));
        jLabelStatus.setText("Backup Progresso ...");
        jPanel1.add(jLabelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 122, 280, -1));

        txtAreaReceive.setEditable(false);
        txtAreaReceive.setColumns(20);
        txtAreaReceive.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtAreaReceive.setLineWrap(true);
        txtAreaReceive.setRows(6);
        jScrollPane1.setViewportView(txtAreaReceive);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 600, 270));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Clique em Iniciar");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        jCheckBoxAbrir.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBoxAbrir.setForeground(new java.awt.Color(51, 51, 51));
        jCheckBoxAbrir.setSelected(true);
        jCheckBoxAbrir.setText("Abrir backup ao finalizar.");
        jCheckBoxAbrir.setOpaque(false);
        jPanel1.add(jCheckBoxAbrir, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 413, -1, -1));

        jCheckBox3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jCheckBox3.setForeground(new java.awt.Color(51, 51, 51));
        jCheckBox3.setSelected(true);
        jCheckBox3.setText(" Versão: PostgreSQL 9.4 ");
        jCheckBox3.setEnabled(false);
        jCheckBox3.setOpaque(false);
        jPanel1.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 467, -1, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ImagemFundoBackup.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane1.addTab("  Efetuar Backup  ", jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 102, 255));
        jLabel5.setText("Carregar Backup");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(30, 15, 140, 19);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Clique em Carregar Arquivo, logo após, em Iniciar");
        jPanel3.add(jLabel6);
        jLabel6.setBounds(30, 40, 320, 17);

        jLabelStatus1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelStatus1.setForeground(new java.awt.Color(153, 153, 153));
        jLabelStatus1.setText("Restauração Progresso ...");
        jPanel3.add(jLabelStatus1);
        jLabelStatus1.setBounds(40, 420, 520, 20);

        jButtonCarregar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jButtonCarregar.setText("Carregar Arquivo");
        jButtonCarregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCarregarActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonCarregar);
        jButtonCarregar.setBounds(483, 90, 160, 30);

        jTextCaminho.setEditable(false);
        jTextCaminho.setBackground(new java.awt.Color(255, 255, 255));
        jTextCaminho.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextCaminho.setForeground(new java.awt.Color(153, 153, 153));
        jTextCaminho.setText("Caminho do arquivo ...");
        jTextCaminho.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextCaminho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCaminhoActionPerformed(evt);
            }
        });
        jPanel3.add(jTextCaminho);
        jTextCaminho.setBounds(40, 90, 440, 30);

        txtAreaReceive1.setEditable(false);
        txtAreaReceive1.setColumns(20);
        txtAreaReceive1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtAreaReceive1.setLineWrap(true);
        txtAreaReceive1.setRows(6);
        jScrollPane2.setViewportView(txtAreaReceive1);

        jPanel3.add(jScrollPane2);
        jScrollPane2.setBounds(39, 126, 603, 256);

        jProgressBar1.setMaximum(5);
        jPanel3.add(jProgressBar1);
        jProgressBar1.setBounds(40, 390, 525, 30);

        jButtonIniciar2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jButtonIniciar2.setText("Iniciar");
        jButtonIniciar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIniciar2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonIniciar2);
        jButtonIniciar2.setBounds(570, 390, 73, 30);

        buttonGroup1.add(jCheckBox4);
        jCheckBox4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jCheckBox4.setForeground(new java.awt.Color(51, 51, 51));
        jCheckBox4.setText(" Versão: PostgreSQL 9.4 ");
        jCheckBox4.setEnabled(false);
        jCheckBox4.setOpaque(false);
        jPanel3.add(jCheckBox4);
        jCheckBox4.setBounds(10, 466, 163, 23);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ImagemFundoBackup.png"))); // NOI18N
        jPanel3.add(jLabel3);
        jLabel3.setBounds(0, 0, 680, 490);

        jTabbedPane1.addTab("  Carregar Backup  ", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy HHmm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void realizaRestore() throws IOException, InterruptedException {

        String nomeDoArquivo = jTextCaminho.getText();//aqui pega somente o nome do arquivo
        //String pathDoArquivo = arq.toString(); //aqui pega o caminho do backup  
        //aqui você testa se a string recebeu o caminho do arquivo   

        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader("ip.ini")).useDelimiter(";");
        } catch (FileNotFoundException ex) {
            // JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo 'ip.ini' por favor, reeinstale o programa (seus dados não serão perdidos) ou entre em contanto com os programadores.", "Erro ao ler arquivo", JOptionPane.ERROR_MESSAGE);

        }
        String ip = null;
        String servidor = null;
        String user = null;
        String password = null;
        while (scanner.hasNext()) {
            ip = scanner.next();
            servidor = scanner.next();
            user = scanner.next();
            password = scanner.next();
        }

        final List<String> comandos = new ArrayList<String>();
        comandos.add(ip+"\\bin\\pg_restore.exe");
        //comandos.add("C:\\Program Files (x86)\\PostgreSQL\\9.4\\bin\\pg_restore.exe");
        //comandos.add("C:\\Program Files\\PostgreSQL\\9.2\\bin\\pg_restore.exe");  //cecom win 7
        //comandos.add("C:\\Arquivos de programas\\PostgreSQL\\9.4\\bin\\pg_restore.exe");  // windows XP notebook

        //comandos.add("C:\\Arquivos de programas\\PostgreSQL\\9.2\\bin\\pg_restore.exe");
        //comandos.add("DROP SCHEMA public CASCADE;"); 
        //comandos.add("-i");      
        comandos.add("-h");
        comandos.add(ip);
        comandos.add("-p");
        comandos.add("5432");
        comandos.add("-U");
        comandos.add(user);
        comandos.add("-c");
        comandos.add("-d");
        comandos.add(servidor);
        comandos.add("-v");

        //comandos.add("C:\\BOHib3.6.4\\Backups do Banco de Dados\\bkpBolOcor04102012.backup");   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
        // comandos.add("C:\\BKPCECOM\\" + nomeDoArquivo);
        comandos.add(nomeDoArquivo);
        ProcessBuilder pb = new ProcessBuilder(comandos);
        pb.environment().put("PGPASSWORD", password);
        jLabelStatus1.setText("Realizando Restauração ...");
        try {
            final Process process = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(process.getErrorStream(), "ISO-8859-1"));
            int i = 0;
            String line = r.readLine();
            while (line != null) {
                i = i + 1;
                jProgressBar1.setValue(i);
                txtAreaReceive1.append(line + "\n");
                txtAreaReceive1.setCaretPosition(txtAreaReceive1.getText().length());
                line = r.readLine();

            }
            r.close();

            process.waitFor();
            process.destroy();
            jLabelStatus1.setText("Restauração Finalizada!");
            jTabbedPane1.setEnabledAt(0, true);
            jButtonIniciar2.setEnabled(true);
            jButtonCarregar.setEnabled(true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }

    public void realizaBackup() throws IOException, InterruptedException {
        final List<String> comandos = new ArrayList<String>();
        String dir = "C:/Altessmann/BackupSistema";
        List<String> lista = new ArrayList<String>(9);
        File diretorio = new File(dir);
        File fList[] = diretorio.listFiles();
        if (fList.length == 0) {

            Scanner scanner = null;
            try {
                scanner = new Scanner(new FileReader("ip.ini")).useDelimiter(";");
            } catch (FileNotFoundException ex) {
                // JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo 'ip.ini' por favor, reeinstale o programa (seus dados não serão perdidos) ou entre em contanto com os programadores.", "Erro ao ler arquivo", JOptionPane.ERROR_MESSAGE);

            }
            String ip = null;
            String servidor = null;
            String user = null;
            String password = null;
            while (scanner.hasNext()) {
                ip = scanner.next();
                servidor = scanner.next();
                user = scanner.next();
                password = scanner.next();
            }
//            System.out.println(ip);
//            System.out.println(servidor);
//            System.out.println(user);
//            System.out.println(password);
            //comandos.add("C:\\Program Files (x86)\\PostgreSQL\\9.4\\bin\\pg_dump.exe"); //cecom
            //comandos.add("C:\\Arquivos de programas\\PostgreSQL\\9.2\\bin\\pg_dump.exe"); 
             comandos.add(ip+"\\bin\\pg_dump.exe");

            //comandos.add("-i");      
            comandos.add("-h");
            comandos.add(ip);
            comandos.add("-p");
            comandos.add("5432");
            comandos.add("-U");
            comandos.add(user);
            comandos.add("-F");
            comandos.add("c");
            comandos.add("-b");
            comandos.add("-v");
            comandos.add("-f");

            //comandos.add("C:\\TesteHib4\\Backups do Banco de Dados\\"+JOptionPane.showInputDialog(null,"Digite o nome do Backup")+".backup");   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
            //comandos.add("C:\\TesteHib4\\Backups do Banco de Dados\\"+(Character.getNumericValue(recebe)+1)+" "+getDateTime()+".backup");   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
            comandos.add("C:\\Altessmann\\BackupSistema\\" + 1 + " " + getDateTime() + ".backup");   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
            comandos.add(servidor);
            ProcessBuilder pb = new ProcessBuilder(comandos);
            pb.environment().put("PGPASSWORD", password);
            jLabelStatus.setText("Realizando Backup ...");
            try {
                final Process process = pb.start();

                final BufferedReader r = new BufferedReader(
                        new InputStreamReader(process.getErrorStream(), "ISO-8859-1"));

                String line = r.readLine();
                while (line != null) {

                    // System.err.println(line);
                    txtAreaReceive.append(line + "\n");
                    line = r.readLine();
                }
                r.close();

                process.waitFor();
                process.destroy();
                new RemoveBKP();
                //JOptionPane.showMessageDialog(null, "backup realizado com sucesso.");
                jLabelStatus.setText("Backup Finalizado!");
                jTabbedPane1.setEnabledAt(1, true);
                jButtonIniciar.setEnabled(true);
                jCheckBoxAbrir.setEnabled(true);
                if (jCheckBoxAbrir.isSelected()) {
                    Runtime.getRuntime().exec("explorer C:\\Altessmann\\BackupSistema");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        } else {
            for (int i = 0; i < fList.length; i++) {
                //JOptionPane.showMessageDialog(null,fList[i].getName());
                lista.add(fList[i].getName());
            }

            char recebe = Collections.max(lista).charAt(0);
            //JOptionPane.showMessageDialog(null,Character.getNumericValue(recebe)); 

            Scanner scanner = null;
            try {
                scanner = new Scanner(new FileReader("ip.ini")).useDelimiter(";");
            } catch (FileNotFoundException ex) {
                // JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo 'ip.ini' por favor, reeinstale o programa (seus dados não serão perdidos) ou entre em contanto com os programadores.", "Erro ao ler arquivo", JOptionPane.ERROR_MESSAGE);

            }
            String ip = null;
            String servidor = null;
            String user = null;
            String password = null;
            while (scanner.hasNext()) {
                ip = scanner.next();
                servidor = scanner.next();
                user = scanner.next();
                password = scanner.next();
            }
//            System.out.println(ip);
//            System.out.println(servidor);
//            System.out.println(user);
//            System.out.println(password);

            comandos.add(ip+"\\bin\\pg_dump.exe");

            //comandos.add("-i");      
            comandos.add("-h");
            comandos.add(ip);
            comandos.add("-p");
            comandos.add("5432");
            comandos.add("-U");
            comandos.add(user);
            comandos.add("-F");
            comandos.add("c");
            comandos.add("-b");
            comandos.add("-v");
            comandos.add("-f");

            //comandos.add("C:\\TesteHib4\\Backups do Banco de Dados\\"+JOptionPane.showInputDialog(null,"Digite o nome do Backup")+".backup");   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
            //comandos.add("C:\\TesteHib4\\Backups do Banco de Dados\\"+(Character.getNumericValue(recebe)+1)+" "+getDateTime()+".backup");   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
            comandos.add("C:\\Altessmann\\BackupSistema\\" + (Character.getNumericValue(recebe) + 1) + " " + getDateTime() + ".backup");   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
            comandos.add(servidor);
            ProcessBuilder pb = new ProcessBuilder(comandos);

            pb.environment().put("PGPASSWORD", password);
            jLabelStatus.setText("Realizando Backup ...");
            try {
                final Process process = pb.start();

                final BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream(), "ISO-8859-1"));

                // jProgressBar.setMaximum((int) r.lines().count());
                // r.
                int i = 0;
                String line;

                line = r.readLine();

                while (line != null) {

                    i = i + 1;
                    //System.err.println(line);

                    jProgressBar.setValue(i);
                    txtAreaReceive.append(line + "\n");
                    txtAreaReceive.setCaretPosition(txtAreaReceive.getText().length());

                    line = r.readLine();

                }

                r.close();

                process.waitFor();
                process.destroy();
                new RemoveBKP();
                //JOptionPane.showMessageDialog(null, "backup realizado com sucesso.");
                jLabelStatus.setText("Backup Finalizado!");
                jTabbedPane1.setEnabledAt(1, true);
                jButtonIniciar.setEnabled(true);
                jCheckBoxAbrir.setEnabled(true);
                if (jCheckBoxAbrir.isSelected()) {
                    Runtime.getRuntime().exec("explorer C:\\Altessmann\\BackupSistema");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }


    private void jButtonIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIniciarActionPerformed
        jTabbedPane1.setEnabledAt(1, false);
        jButtonIniciar.setEnabled(false);
        jProgressBar.grabFocus();
        jCheckBoxAbrir.setEnabled(false);
        jProgressBar.setValue(0);
        txtAreaReceive.setText("");

        new Thread() {
            public void run() {
                try {
                    jLabelStatus.setText("Iniciando Backup ...");
                    Thread.currentThread().sleep(1000);
                    realizaBackup();
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo 'ip.ini' por favor, reeinstale o programa (seus dados não serão perdidos) ou entre em contanto com os programadores.", "Erro ao ler arquivo", JOptionPane.ERROR_MESSAGE);

                } catch (IOException ex) {
                    Logger.getLogger(BackupNew.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BackupNew.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }.start();

        //    public static void main(String args[]) throws IOException, InterruptedException {
//        PostgresBackup_Curso b = new PostgresBackup_Curso();
//        try {
        //      b.realizaBackup();
//        } catch (NullPointerException ex) {
//            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo 'ip.ini' por favor, reeinstale o programa (seus dados não serão perdidos) ou entre em contanto com os programadores.", "Erro ao ler arquivo", JOptionPane.ERROR_MESSAGE);
//
//        }
//    }

    }//GEN-LAST:event_jButtonIniciarActionPerformed

    private void jButtonCarregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCarregarActionPerformed
        JFileChooser chooser = new JFileChooser();
        try {
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();

            String filename = f.getAbsolutePath();
            jTextCaminho.setText(filename);
        } catch (NullPointerException ex) {

        }
    }//GEN-LAST:event_jButtonCarregarActionPerformed

    private void jTextCaminhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCaminhoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCaminhoActionPerformed

    private void jButtonIniciar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIniciar2ActionPerformed
        jTabbedPane1.setEnabledAt(0, false);
        jButtonIniciar2.setEnabled(false);
        jButtonCarregar.setEnabled(false);
        jProgressBar1.grabFocus();
        jProgressBar1.setValue(0);
        txtAreaReceive1.setText("");

        new Thread() {
            public void run() {
                try {
                    jLabelStatus.setText("Iniciando Backup ...");
                    Thread.currentThread().sleep(1000);
                    realizaRestore();
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo 'ip.ini' por favor, reeinstale o programa (seus dados não serão perdidos) ou entre em contanto com os programadores.", "Erro ao ler arquivo", JOptionPane.ERROR_MESSAGE);

                } catch (IOException ex) {
                    Logger.getLogger(BackupNew.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BackupNew.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }.start();


    }//GEN-LAST:event_jButtonIniciar2ActionPerformed

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
            java.util.logging.Logger.getLogger(BackupNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BackupNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BackupNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BackupNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BackupNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonCarregar;
    private javax.swing.JButton jButtonIniciar;
    private javax.swing.JButton jButtonIniciar2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    public javax.swing.JCheckBox jCheckBoxAbrir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabelStatus;
    public javax.swing.JLabel jLabelStatus1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JProgressBar jProgressBar;
    public javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTextField jTextCaminho;
    public javax.swing.JTextArea txtAreaReceive;
    public javax.swing.JTextArea txtAreaReceive1;
    // End of variables declaration//GEN-END:variables
}
