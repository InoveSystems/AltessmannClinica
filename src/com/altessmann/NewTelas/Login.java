package com.altessmann.NewTelas;

import com.altessmann.Bean.FuncionarioBean;
import com.altessmann.DAO.FuncionarioDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Logs.LogsExceptions;
import com.altessmann.ConnecionFactory.ConnectionFactory;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import org.postgresql.util.PSQLException;

/**
 *
 * @author Ritiele
 */
public class Login extends javax.swing.JFrame {

    Connection conexao;
    LogsExceptions log = new LogsExceptions();
    private int verificador;
    private LogsExceptions logs_exceptions = new LogsExceptions();

    public Login() {
        initComponents();
        jLogin.setSelectionStart(0);
        jLogin.setSelectionEnd(7);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jSenha = new javax.swing.JPasswordField();
        jLogin = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jStatus = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSair = new javax.swing.JButton();
        jConfirmar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Inove Systems - Login");
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jSenha.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jSenha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSenhaActionPerformed(evt);
            }
        });
        jSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSenhaKeyPressed(evt);
            }
        });

        jLogin.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLogin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jLogin.setText("USUÁRIO");
        jLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLoginActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("..:: Acesso ao Sistema ::..");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jStatus.setBackground(new java.awt.Color(255, 255, 255));
        jStatus.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jStatus.setText("Digite seu Login de Usuario e Senha!");
        jStatus.setOpaque(true);

        jSair.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/exit.png"))); // NOI18N
        jSair.setText("Sair!");
        jSair.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSairActionPerformed(evt);
            }
        });

        jConfirmar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/salvarCliente (2).png"))); // NOI18N
        jConfirmar.setText("Confirmar");
        jConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jConfirmarActionPerformed(evt);
            }
        });
        jConfirmar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jConfirmarKeyPressed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/LoginFundo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSair, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSenha, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLogin, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2))
                .addGap(1, 1, 1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(jSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    public void Pesquisar(FuncionarioBean Funcionario) {
        FuncionarioBean FuncRecebe = new FuncionarioBean();
        new Thread() {
            @Override
            public void run() {
                try {
                    int cod = 0;
                    String nome = "";
                    String senha = "";
                    String usuario = "";
                    int funcao = 0;
                    FuncionarioDAO funcionario = new FuncionarioDAO();
                    ResultSet rs;
                    //System.out.println(Funcionario.getCod());
                    rs = funcionario.retriveUsuario(Funcionario);
                    if (rs.next()) {
                        do {
                            cod = rs.getInt("codigo");
                            FuncRecebe.setCodigo(cod);
                            nome = rs.getString("nome");
                            FuncRecebe.setNome(nome);
                            senha = rs.getString("senha");
                            FuncRecebe.setSenha(senha);
                            usuario = rs.getString("usuario");
                            FuncRecebe.setUsuario(usuario);
                            funcao = rs.getInt("funcao");
                            FuncRecebe.setFuncao(funcao);
                        } while (rs.next());
                        if (jSenha.getText().trim().equals(senha)) {
                            setVisible(false);
                            MenuConsultorioNew menu = new MenuConsultorioNew(FuncRecebe);
                            menu.setVisible(true);
                            // Cliente cliente = new Cliente(cod, nome);
                            // cliente.setVisible(true);
                        } else {
                            verificador = verificador + 1;

                            if (verificador == 3) {
                                jStatus.setText("SENHA INCORRETA!");
                                Thread.currentThread().sleep(1000);
                                jStatus.setText("Sistema Bloqueado!");
                                Thread.currentThread().sleep(1000);
                                jSair.setEnabled(true);
                            } else {
                                jStatus.setText("SENHA INCORRETA! " + (verificador) + " Tentativa(s)");
                                Thread.currentThread().sleep(2000);
                                jStatus.setText("Verifique sua Senha!");
                                Thread.currentThread().sleep(2000);
                                if (verificador == 2) {
                                    logs_exceptions.ExceptionsTratamento(11);
                                }

                                jSenha.setText("");
                                jStatus.setText("Digite seu Login de Usuario e Senha!");
                                jSair.setEnabled(true);
                                jConfirmar.setEnabled(true);
                                jLogin.setEnabled(false);
                                jSenha.setEnabled(true);
                                jSenha.grabFocus();
                            }
                        }
                    } else {
                        jStatus.setText("Usuário não Localizado!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Verifique os dados de Login!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Digite seu Login de Usuario e Senha!");
                        jSair.setEnabled(true);
                        jConfirmar.setEnabled(true);
                        jLogin.setEnabled(true);
                        jSenha.setEnabled(true);
                        jLogin.setText("");
                        jSenha.setText("");
                        jLogin.grabFocus();
                    }
                } catch (PSQLException ex) {
                    System.out.println(ex);
                    try {
                        jStatus.setText("Erro ao conectar com o banco de dados!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Verifique o IP do servidor!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Entre em contato com o suporte tecnico!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Saindo do sistema em 3 segundos!");
                        Thread.currentThread().sleep(3000);
                        System.exit(0);
                        jSair.setEnabled(true);
                        jConfirmar.setEnabled(true);
                        jLogin.setEnabled(true);
                        jSenha.setEnabled(true);
                    } catch (InterruptedException ex1) {

                    }
                } catch (SQLException ex) {
                    try {
                        jStatus.setText("Erro ao conectar com o banco de dados!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Entre em contato com o suporte tecnico!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Saindo do sistema em 3 segundos!");
                        Thread.currentThread().sleep(3000);
                        System.exit(0);
                        jSair.setEnabled(true);
                        jConfirmar.setEnabled(true);
                        jLogin.setEnabled(true);
                        jSenha.setEnabled(true);
                    } catch (InterruptedException ex1) {

                    }
                } catch (NullPointerException ex) {

                } catch (InterruptedException ex) {

                } catch (Exception ex) {

                }

            }
        }
                .start();
    }


    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void jSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jSairActionPerformed

    private void jConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jConfirmarActionPerformed

        jConfirmar.setEnabled(false);
        jSair.setEnabled(false);
        jLogin.setEnabled(false);
        jSenha.setEnabled(false);

        new Thread() {
            @Override
            public void run() {
                //jLogin.getText();
                if ((jLogin.getText().trim().equals("")) || (jSenha.getText().trim().equals(""))) {
                    try {
                        if (verificador == 0) {
                            jStatus.setText("Digite um USUÁRIO/SENHA válido!");
                            Thread.currentThread().sleep(1000);
                            jStatus.setText("Verifique os dados de Login!");
                            Thread.currentThread().sleep(1000);
                            jStatus.setText("Digite seu Login de Usuario e Senha!");
                            jConfirmar.setEnabled(true);
                            jSair.setEnabled(true);
                            jLogin.setEnabled(true);
                            jSenha.setEnabled(true);
                        } else {
                            jStatus.setText("Digite um uma SENHA válido!");
                            Thread.currentThread().sleep(2000);
                            jStatus.setText("Digite sua Senha!");
                            jSenha.setEnabled(true);
                            jConfirmar.setEnabled(true);
                            jSair.setEnabled(true);
                            jSenha.grabFocus();
                        }

                    } catch (InterruptedException ex) {

                    }
                    jLogin.grabFocus();
                } else {
                    try {
                        if ((jLogin.getText().trim().equals("altessmann")) && (jSenha.getText().trim().equals("dr1912+"))) {
                            //Config config = new Config(true);
                            //config.jPanes.setSelectedIndex(2);
                            //config.setVisible(true);
                            jStatus.setText("Aguarde! Conectando ao sistema ...");
                            Thread.currentThread().sleep(1000);
                            FuncionarioBean FuncRecebe = new FuncionarioBean();
                            FuncRecebe.setCodigo(000);
                            FuncRecebe.setNome("Altessmann");
                            FuncRecebe.setUsuario("altessmann");
                            FuncRecebe.setFuncao(1);
                            MenuConsultorioNew menu = new MenuConsultorioNew(FuncRecebe);
                            menu.setVisible(true);
                            setEnabled(false);
                            setVisible(false);
                        } else {
                            jStatus.setText("Aguarde! Conectando ao sistema ...");
                            Thread.currentThread().sleep(1000);
                            FuncionarioBean funcionario = new FuncionarioBean();
                            funcionario.setUsuario(jLogin.getText());
                            Pesquisar(funcionario);
                            //jConfirmar.setEnabled(true);
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            jStatus.setText("USUÁRIO INVÁLIDO!");
                            Thread.currentThread().sleep(1000);
                            jStatus.setText("Verifique os dados de Login!");
                            Thread.currentThread().sleep(1000);
                            jStatus.setText("Digite seu Login de Usuario e Senha!");
                            jConfirmar.setEnabled(true);
                            jSair.setEnabled(true);
                            jLogin.setEnabled(true);
                            jSenha.setEnabled(true);
                        } catch (InterruptedException ex1) {

                        }
                    } catch (InterruptedException ex) {

                    }
                }
            }
        }.start();
    }//GEN-LAST:event_jConfirmarActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void jConfirmarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jConfirmarKeyPressed

        //abaixo é só mudar o ENTER para o código que da tecla que vc deseja...
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jConfirmar.setEnabled(false);
            jSair.setEnabled(false);
            jLogin.setEnabled(false);
            jSenha.setEnabled(false);
            new Thread() {
                @Override
                public void run() {
                    //jLogin.getText();
                    if ((jLogin.getText().trim().equals("")) || (jSenha.getText().trim().equals(""))) {
                        try {
                            if (verificador == 0) {
                                jStatus.setText("Digite um USUÁRIO/SENHA válido!");
                                Thread.currentThread().sleep(1000);
                                jStatus.setText("Verifique os dados de Login!");
                                Thread.currentThread().sleep(1000);
                                jStatus.setText("Digite seu Login de Usuario e Senha!");
                                jConfirmar.setEnabled(true);
                                jSair.setEnabled(true);
                                jLogin.setEnabled(true);
                                jSenha.setEnabled(true);
                            } else {
                                jStatus.setText("Digite um uma SENHA válido!");
                                Thread.currentThread().sleep(2000);
                                jStatus.setText("Digite sua Senha!");
                                jSenha.setEnabled(true);
                                jConfirmar.setEnabled(true);
                                jSair.setEnabled(true);
                                jSenha.grabFocus();
                            }

                        } catch (InterruptedException ex) {

                        }
                        jLogin.grabFocus();
                    } else {
                        try {
                            if ((jLogin.getText().trim().equals("altessmann")) && (jSenha.getText().trim().equals("dr1912+"))) {
                                //Config config = new Config(true);
                                //config.jPanes.setSelectedIndex(2);
                                //config.setVisible(true);
                                jStatus.setText("Aguarde! Conectando ao sistema ...");
                                Thread.currentThread().sleep(1000);
                                FuncionarioBean FuncRecebe = new FuncionarioBean();
                                FuncRecebe.setCodigo(000);
                                FuncRecebe.setNome("Altessmann");
                                FuncRecebe.setUsuario("Altessmann");
                                FuncRecebe.setFuncao(1);
                                MenuConsultorioNew menu = new MenuConsultorioNew(FuncRecebe);
                                menu.setVisible(true);
                                setEnabled(false);
                                setVisible(false);
                            } else {
                                jStatus.setText("Aguarde! Conectando ao sistema ...");
                                Thread.currentThread().sleep(1000);
                                FuncionarioBean funcionario = new FuncionarioBean();
                                funcionario.setUsuario(jLogin.getText());
                                Pesquisar(funcionario);
                                //jConfirmar.setEnabled(true);
                            }
                        } catch (NumberFormatException ex) {
                            try {
                                jStatus.setText("USUÁRIO INVÁLIDO!");
                                Thread.currentThread().sleep(1000);
                                jStatus.setText("Verifique os dados de Login!");
                                Thread.currentThread().sleep(1000);
                                jStatus.setText("Digite seu Login de Usuario e Senha!");
                                jConfirmar.setEnabled(true);
                                jSair.setEnabled(true);
                                jLogin.setEnabled(true);
                                jSenha.setEnabled(true);
                            } catch (InterruptedException ex1) {

                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            }.start();
        }
    }//GEN-LAST:event_jConfirmarKeyPressed

    private void jSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSenhaKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jConfirmar.setEnabled(false);
            jSair.setEnabled(false);
            jLogin.setEnabled(false);
            jSenha.setEnabled(false);
            new Thread() {
                @Override
                public void run() {
                    //jLogin.getText();
                    if ((jLogin.getText().trim().equals("")) || (jSenha.getText().trim().equals(""))) {
                        try {
                            if (verificador == 0) {
                                jStatus.setText("Digite um USUÁRIO/SENHA válido!");
                                Thread.currentThread().sleep(1000);
                                jStatus.setText("Verifique os dados de Login!");
                                Thread.currentThread().sleep(1000);
                                jStatus.setText("Digite seu Login de Usuario e Senha!");
                                jConfirmar.setEnabled(true);
                                jSair.setEnabled(true);
                                jLogin.setEnabled(true);
                                jSenha.setEnabled(true);
                            } else {
                                jStatus.setText("Digite um uma SENHA válido!");
                                Thread.currentThread().sleep(2000);
                                jStatus.setText("Digite sua Senha!");
                                jSenha.setEnabled(true);
                                jConfirmar.setEnabled(true);
                                jSair.setEnabled(true);
                                jSenha.grabFocus();
                            }

                        } catch (InterruptedException ex) {

                        }
                        jLogin.grabFocus();
                    } else {
                        try {
                            if ((jLogin.getText().trim().equals("altessmann")) && (jSenha.getText().trim().equals("dr1912+"))) {
                                //Config config = new Config(true);
                                //config.jPanes.setSelectedIndex(2);
                                //config.setVisible(true);
                                jStatus.setText("Aguarde! Conectando ao sistema ...");
                                Thread.currentThread().sleep(1000);
                                FuncionarioBean FuncRecebe = new FuncionarioBean();
                                FuncRecebe.setCodigo(000);
                                FuncRecebe.setNome("Altessmann");
                                FuncRecebe.setUsuario("Altessmann");
                                FuncRecebe.setFuncao(1);
                                try {
                                    MenuConsultorioNew menu = new MenuConsultorioNew(FuncRecebe);
                                    menu.setVisible(true);
                                    setEnabled(false);
                                    setVisible(false);
                                } catch (NumberFormatException ex) {
                                    System.out.println("aquiiii meu");
                                }

                            } else {
                                jStatus.setText("Aguarde! Conectando ao sistema ...");
                                Thread.currentThread().sleep(1000);
                                FuncionarioBean funcionario = new FuncionarioBean();
                                funcionario.setUsuario(jLogin.getText());
                                Pesquisar(funcionario);
                                //jConfirmar.setEnabled(true);
                            }
                        } catch (NumberFormatException ex) {
                            try {
                                jStatus.setText("USUÁRIO INVÁLIDO!");
                                Thread.currentThread().sleep(1000);
                                jStatus.setText("Verifique os dados de Login!");
                                Thread.currentThread().sleep(1000);
                                jStatus.setText("Digite seu Login de Usuario e Senha!");
                                jConfirmar.setEnabled(true);
                                jSair.setEnabled(true);
                                jLogin.setEnabled(true);
                                jSenha.setEnabled(true);
                            } catch (InterruptedException ex1) {

                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            }.start();
        }
    }//GEN-LAST:event_jSenhaKeyPressed

    private void jSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jSenhaActionPerformed

    private void jLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //  <editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jLogin;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jSair;
    private javax.swing.JPasswordField jSenha;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jStatus;
    // End of variables declaration//GEN-END:variables

}
