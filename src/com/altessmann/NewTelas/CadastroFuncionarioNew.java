/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.NewTelas;

import com.altessmann.Bean.FuncionarioBean;
import com.altessmann.DAO.FuncionarioDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import com.altessmann.AbstractTableModel.FuncionarioTableModel;
//import Telas.Cadastro;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import com.altessmann.ValidaCampos.ValidaCPF;
import com.altessmann.ValidaCampos.ValidaEmail;
import com.altessmann.ValidaCampos.buscaCEP;
import java.io.IOException;
import java.util.Date;
import java.util.regex.*;

/**
 *
 * @author Ritiele Aldeburg && Guilherme Tessmann ©2017
 */
public class CadastroFuncionarioNew extends javax.swing.JFrame {

    /**
     * Creates new form CadastroFuncionarioNew
     */
    private FuncionarioTableModel model;
    private FuncionarioBean funcionarioBean = new FuncionarioBean();
    private FuncionarioDAO funcionarioDAO;
    private boolean flag = false;
    private int numeroLinhas = 0;
    private String user;
    private int tipo = 1;
    private ExamesNew examesNew = new ExamesNew();

    public CadastroFuncionarioNew(String user) {
        this.user = user;
        initComponents();
        jButtonEditar.setEnabled(false);
        jButtonExcluir.setEnabled(false);
        jButtonGravar.setEnabled(false);
        desativaCampo();
        limpaCampo();
        tabela();
        try {
            this.funcionarioDAO = new FuncionarioDAO();
        } catch (Exception ex) {
            Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualiza_tabela() {
        List lista = new ArrayList();
        try {
            if (!funcionarioDAO.getFuncionario("").isEmpty()) {
                this.numeroLinhas = funcionarioDAO.getNumeroLinhas();
                for (int i = 0; i < numeroLinhas; i++) {
                    FuncionarioBean p = new FuncionarioBean();
                    p.setCodigo(funcionarioDAO.getFuncionario("").get(i).getCodigo());
                    p.setNome(funcionarioDAO.getFuncionario("").get(i).getNome());
                    p.setTelefone(funcionarioDAO.getFuncionario("").get(i).getTelefone());
                    p.setEmail(funcionarioDAO.getFuncionario("").get(i).getEmail());
                    p.setFuncao(funcionarioDAO.getFuncionario("").get(i).getFuncao());
                    lista.add(p);
                }
                model = new FuncionarioTableModel(lista);
                jTableFuncionario.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableFuncionario.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(4).setCellRenderer(esquerda);

            } else {
                model = new FuncionarioTableModel();
                jTableFuncionario.setModel(model);
                model.limpaTabela();
                JOptionPane.showMessageDialog(null, "Não existem funcionários cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tabela() {
        List lista = new ArrayList();
        jTableFuncionario.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jButtonCancelar.setEnabled(true);
                if (e.getClickCount() == 1) {
                    jButtonCancelar.setEnabled(true);
                    jButtonNovo.setEnabled(true);
                    if (user.equals("Altessmann")) {

                        new Thread() {
                            public void run() {
                                getFuncionario(Integer.parseInt(jTableFuncionario.getValueAt(jTableFuncionario.getSelectedRow(), 0) + ""));
                                jButtonEditar.setEnabled(true);
                                jButtonCancelar.setEnabled(true);
                                jTabbedPane1.setSelectedIndex(1);
                                jTabbedPane1.setEnabledAt(0, false);
                                jTabbedPane1.setEnabledAt(1, true);
                                jButtonNovo.setEnabled(false);
                                jButtonExcluir.setEnabled(false);
                                jTabbedPane1.grabFocus();
                                desativaCampo();
                            }
                        }.start();;
                    } else {
                        try {
                            if (tipo != 2) {
                                String senha = JOptionPane.showInputDialog(null, "Digite sua senha:", "Digitar senha", JOptionPane.INFORMATION_MESSAGE);
                                if (senha == null) {
                                    senha = "";
                                } else {
                                    int codigo = (int) jTableFuncionario.getValueAt(jTableFuncionario.getSelectedRow(), 0);
                                    if (senha.equals(funcionarioDAO.getFuncionario(codigo).get(0).getSenha())) {

                                        new Thread() {
                                            public void run() {
                                                getFuncionario(Integer.parseInt(jTableFuncionario.getValueAt(jTableFuncionario.getSelectedRow(), 0) + ""));
                                                jButtonEditar.setEnabled(true);
                                                jButtonCancelar.setEnabled(true);
                                                jTabbedPane1.setSelectedIndex(1);
                                                jTabbedPane1.setEnabledAt(0, false);
                                                jTabbedPane1.setEnabledAt(1, true);
                                                jButtonNovo.setEnabled(false);
                                                jButtonExcluir.setEnabled(false);
                                                jTabbedPane1.grabFocus();
                                                desativaCampo();
                                            }
                                        }.start();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Senha inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            } else {
                                jButtonAdicionar.setEnabled(true);
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }

    public void enviaPalavra(ExamesNew examesNew, String palavra, int tipo) {
        this.examesNew = examesNew;
        this.tipo = tipo;
        jButtonAdicionar.setEnabled(true);
        jTextPesquisa.setText(palavra);
        List lista = new ArrayList();
        try {
            for (int i = 0; i < funcionarioDAO.getMedico(palavra).size(); i++) {
                FuncionarioBean p = new FuncionarioBean();
                p.setCodigo(funcionarioDAO.getMedico(palavra).get(i).getCodigo());
                p.setNome(funcionarioDAO.getMedico(palavra).get(i).getNome());
                p.setTelefone(funcionarioDAO.getMedico(palavra).get(i).getTelefone());
                p.setEmail(funcionarioDAO.getMedico(palavra).get(i).getEmail());
                p.setFuncao(funcionarioDAO.getMedico(palavra).get(i).getFuncao());
                lista.add(p);
            }
            model = new FuncionarioTableModel(lista);
            jTableFuncionario.setModel(model);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTableFuncionario.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTableFuncionario.getColumnModel().getColumn(1).setCellRenderer(esquerda);
            jTableFuncionario.getColumnModel().getColumn(2).setCellRenderer(esquerda);
            jTableFuncionario.getColumnModel().getColumn(3).setCellRenderer(esquerda);
            jTableFuncionario.getColumnModel().getColumn(4).setCellRenderer(esquerda);
        } catch (SQLException ex) {
            Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void limpaCampo() {
        Calendar dataAtual = new GregorianCalendar();
        jTextNome.setText(null);
        jComboFuncao.setSelectedIndex(0);
        jComboSigla.setSelectedIndex(0);
        jTextCrm.setText(null);
        jTextRg.setText(null);
        jTextCpf.setText(null);
        jDateDtNascimento.setDate(dataAtual.getTime());
        jTextEmail.setText(null);
        jComboSexo.setSelectedIndex(0);
        jTextEndereco.setText(null);
        jTextNumero.setText(null);
        jTextComplemento.setText(null);
        jTextBairro.setText(null);
        jTextCep.setText(null);
        jTextCidade.setText(null);
        jComboUf.setSelectedItem("RS");
        jTextUsuario.setText(null);
        jTextSenha.setText(null);
        jTextSala.setText(null);
        jTextRepetirSenha.setText(null);
        jTextTelefone.setText(null);
        flag = false;
    }

    public void ativaCampo() {
        jButtonCancelar.setEnabled(true);
        jTextNome.setEnabled(true);
        jComboFuncao.setEnabled(true);
        //jComboSigla.setEnabled(true);
        //jTextCrm.setEnabled(true);
        jTextRg.setEnabled(true);
        jTextCpf.setEnabled(true);
        jDateDtNascimento.setEnabled(true);
        jTextEmail.setEnabled(true);
        jComboSexo.setEnabled(true);
        jTextEndereco.setEnabled(true);
        jTextNumero.setEnabled(true);
        jTextComplemento.setEnabled(true);
        jTextBairro.setEnabled(true);
        jTextCep.setEnabled(true);
        jTextCidade.setEnabled(true);
        jComboUf.setEnabled(true);
        jTextUsuario.setEnabled(true);
        // jTextSala.setEnabled(true);
        jTextSenha.setEnabled(true);
        jTextRepetirSenha.setEnabled(true);
        jTextTelefone.setEnabled(true);

    }

    public void desativaCampo() {
        //jButtonCancelar.setEnabled(false);
        jTextNome.setEnabled(false);
        jComboFuncao.setEnabled(false);
        jComboSigla.setEnabled(false);
        jTextCrm.setEnabled(false);
        jTextRg.setEnabled(false);
        jTextTelefone.setEnabled(false);
        jTextCpf.setEnabled(false);
        jDateDtNascimento.setEnabled(false);
        jTextEmail.setEnabled(false);
        jComboSexo.setEnabled(false);
        jTextEndereco.setEnabled(false);
        jTextNumero.setEnabled(false);
        jTextComplemento.setEnabled(false);
        jTextBairro.setEnabled(false);
        jTextCep.setEnabled(false);
        jTextCidade.setEnabled(false);
        jComboUf.setEnabled(false);
        jTextUsuario.setEnabled(false);
        jTextSala.setEnabled(false);
        jTextSenha.setEnabled(false);
        jTextRepetirSenha.setEnabled(false);
        

    }

    public void getFuncionario(int cod) {
        try {
            jTextNome.setText(funcionarioDAO.getFuncionario(cod).get(0).getNome());
            jComboFuncao.setSelectedIndex(funcionarioDAO.getFuncionario(cod).get(0).getFuncao());
            jTextCrm.setText(funcionarioDAO.getFuncionario(cod).get(0).getCrm());
            jTextRg.setText(funcionarioDAO.getFuncionario(cod).get(0).getRg());
            jTextCpf.setText(funcionarioDAO.getFuncionario(cod).get(0).getCpf());
            jDateDtNascimento.setDate(funcionarioDAO.getFuncionario(cod).get(0).getDtNascimento());
            jTextEmail.setText(funcionarioDAO.getFuncionario(cod).get(0).getEmail());
            jTextTelefone.setText(funcionarioDAO.getFuncionario(cod).get(0).getTelefone());
            jComboSexo.setSelectedItem(funcionarioDAO.getFuncionario(cod).get(0).getSexo());
            jTextEndereco.setText(funcionarioDAO.getFuncionario(cod).get(0).getEndereco());
            jTextNumero.setText(funcionarioDAO.getFuncionario(cod).get(0).getNumero());
            jTextComplemento.setText(funcionarioDAO.getFuncionario(cod).get(0).getComplemento());
            jTextBairro.setText(funcionarioDAO.getFuncionario(cod).get(0).getBairro());
            jTextCep.setText(funcionarioDAO.getFuncionario(cod).get(0).getCep());
            jTextCidade.setText(funcionarioDAO.getFuncionario(cod).get(0).getCidade());
            jComboUf.setSelectedItem(funcionarioDAO.getFuncionario(cod).get(0).getUf());
            jTextUsuario.setText(funcionarioDAO.getFuncionario(cod).get(0).getUsuario());
            jTextSenha.setText(funcionarioDAO.getFuncionario(cod).get(0).getSenha());
            jTextRepetirSenha.setText(funcionarioDAO.getFuncionario(cod).get(0).getSenha());
            jTextSala.setText(funcionarioDAO.getFuncionario(cod).get(0).getSala());
            jComboSigla.setSelectedItem("Sigla " + funcionarioDAO.getFuncionario(cod).get(0).getSigla());
            if (funcionarioDAO.getFuncionario(cod).get(0).getTentativas() == 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setNovoCodigo() {
        flag = false;
        funcionarioBean.setTentativas(0); // Atualizo ou insiro um dado novo? Esse loko decide!
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTextPesquisa = new javax.swing.JTextField();
        jButtonPesquisar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButtonAdicionar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableFuncionario = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButtonNovo = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonGravar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jComboFuncao = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        LabelValorCus112 = new javax.swing.JLabel();
        jTextNome = new javax.swing.JTextField();
        jTextEndereco = new javax.swing.JTextField();
        LabelValorCus113 = new javax.swing.JLabel();
        jTextNumero = new javax.swing.JTextField();
        LabelValorCus114 = new javax.swing.JLabel();
        jTextComplemento = new javax.swing.JTextField();
        LabelValorCus115 = new javax.swing.JLabel();
        jTextBairro = new javax.swing.JTextField();
        LabelValorCus116 = new javax.swing.JLabel();
        jTextCidade = new javax.swing.JTextField();
        LabelValorCus117 = new javax.swing.JLabel();
        jTextCep = new javax.swing.JTextField();
        LabelValorCus118 = new javax.swing.JLabel();
        jComboUf = new javax.swing.JComboBox();
        jTextRg = new javax.swing.JTextField();
        LabelValorCus119 = new javax.swing.JLabel();
        LabelValorCus51 = new javax.swing.JLabel();
        jComboSexo = new javax.swing.JComboBox();
        jTextCpf = new javax.swing.JTextField();
        LabelValorCus120 = new javax.swing.JLabel();
        LabelValorCus48 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabelFoto = new javax.swing.JLabel();
        jTextEmail = new javax.swing.JTextField();
        LabelValorCus122 = new javax.swing.JLabel();
        jTextUsuario = new javax.swing.JTextField();
        LabelValorCus123 = new javax.swing.JLabel();
        LabelValorCus124 = new javax.swing.JLabel();
        LabelValorCus125 = new javax.swing.JLabel();
        LabelValorCus52 = new javax.swing.JLabel();
        jTextCrm = new javax.swing.JTextField();
        LabelValorCus121 = new javax.swing.JLabel();
        jDateDtNascimento = new com.toedter.calendar.JDateChooser();
        jTextTelefone = new javax.swing.JTextField();
        LabelValorCus126 = new javax.swing.JLabel();
        jTextSala = new javax.swing.JTextField();
        LabelValorCus127 = new javax.swing.JLabel();
        jComboSigla = new javax.swing.JComboBox();
        jTextSenha = new javax.swing.JPasswordField();
        jTextRepetirSenha = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Altessmann Sistemas - Colaborador");
        setResizable(false);

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextPesquisa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextPesquisa.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextPesquisa.setPreferredSize(new java.awt.Dimension(500, 25));
        jTextPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPesquisaActionPerformed(evt);
            }
        });

        jButtonPesquisar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PesquisarCli.png"))); // NOI18N
        jButtonPesquisar.setText("Pesquisar ");
        jButtonPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPesquisarActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/abrircli.png"))); // NOI18N
        jButton3.setText("Listar ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButtonAdicionar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add.png"))); // NOI18N
        jButtonAdicionar.setText("Adicionar");
        jButtonAdicionar.setEnabled(false);
        jButtonAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarActionPerformed(evt);
            }
        });

        jTableFuncionario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Telefone", "Email", "Função"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableFuncionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableFuncionarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableFuncionario);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPesquisar)
                        .addGap(3, 3, 3)
                        .addComponent(jButton3)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("  Pesquisar Colaborador  ", jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Cadastro Colaborador  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jButtonNovo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add2.png"))); // NOI18N
        jButtonNovo.setText("Novo Cadastro");
        jButtonNovo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoActionPerformed(evt);
            }
        });

        jButtonEditar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/editar24x24.png"))); // NOI18N
        jButtonEditar.setText("Editar Cadastro");
        jButtonEditar.setEnabled(false);
        jButtonEditar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });

        jButtonExcluir.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/close24x24.png"))); // NOI18N
        jButtonExcluir.setText("Excluir Cadastro");
        jButtonExcluir.setEnabled(false);
        jButtonExcluir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jButtonGravar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/save24x24.png"))); // NOI18N
        jButtonGravar.setText("Salvar Cadastro");
        jButtonGravar.setEnabled(false);
        jButtonGravar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGravarActionPerformed(evt);
            }
        });

        jButtonCancelar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cancel24x24.png"))); // NOI18N
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setEnabled(false);
        jButtonCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jComboFuncao.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboFuncao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Secretaria", "Médico" }));
        jComboFuncao.setToolTipText("");
        jComboFuncao.setEnabled(false);
        jComboFuncao.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboFuncaoItemStateChanged(evt);
            }
        });
        jComboFuncao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboFuncaoActionPerformed(evt);
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
                .addGap(3, 3, 3)
                .addComponent(jButtonEditar)
                .addGap(3, 3, 3)
                .addComponent(jButtonExcluir)
                .addGap(3, 3, 3)
                .addComponent(jButtonGravar)
                .addGap(3, 3, 3)
                .addComponent(jButtonCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboFuncao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Detalhes  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        LabelValorCus112.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus112.setText("Nome Completo:");

        jTextNome.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextNome.setEnabled(false);
        jTextNome.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNomeActionPerformed(evt);
            }
        });

        jTextEndereco.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextEndereco.setEnabled(false);
        jTextEndereco.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextEnderecoActionPerformed(evt);
            }
        });

        LabelValorCus113.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus113.setText("Logradouro:");

        jTextNumero.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextNumero.setEnabled(false);
        jTextNumero.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNumeroActionPerformed(evt);
            }
        });

        LabelValorCus114.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus114.setText("Numero:");

        jTextComplemento.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextComplemento.setEnabled(false);
        jTextComplemento.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextComplemento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextComplementoActionPerformed(evt);
            }
        });

        LabelValorCus115.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus115.setText("Complemento:");

        jTextBairro.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextBairro.setEnabled(false);
        jTextBairro.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextBairroActionPerformed(evt);
            }
        });

        LabelValorCus116.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus116.setText("Bairro:");

        jTextCidade.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextCidade.setEnabled(false);
        jTextCidade.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCidadeActionPerformed(evt);
            }
        });

        LabelValorCus117.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus117.setText("Cidade:");

        jTextCep.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextCep.setEnabled(false);
        jTextCep.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCepActionPerformed(evt);
            }
        });

        LabelValorCus118.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus118.setText("CEP:");

        jComboUf.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboUf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RS" }));
        jComboUf.setToolTipText("");
        jComboUf.setEnabled(false);

        jTextRg.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextRg.setEnabled(false);
        jTextRg.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextRg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextRgActionPerformed(evt);
            }
        });

        LabelValorCus119.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus119.setText("RG:");

        LabelValorCus51.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus51.setText("Sexo:");

        jComboSexo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Masculino", "Feminino" }));
        jComboSexo.setToolTipText("");
        jComboSexo.setEnabled(false);

        jTextCpf.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextCpf.setEnabled(false);
        jTextCpf.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCpfActionPerformed(evt);
            }
        });

        LabelValorCus120.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus120.setText("CPF:");

        LabelValorCus48.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus48.setText("Data Nascimento:");

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fotoadd.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelFoto)
        );

        jTextEmail.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextEmail.setEnabled(false);
        jTextEmail.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextEmailActionPerformed(evt);
            }
        });

        LabelValorCus122.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus122.setText("Email:");

        jTextUsuario.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextUsuario.setEnabled(false);
        jTextUsuario.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextUsuarioActionPerformed(evt);
            }
        });

        LabelValorCus123.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus123.setText("Usuario:");

        LabelValorCus124.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus124.setText("Senha:");

        LabelValorCus125.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus125.setText("Repetir Senha:");

        LabelValorCus52.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus52.setText("Estado:");

        jTextCrm.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextCrm.setEnabled(false);
        jTextCrm.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextCrm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCrmActionPerformed(evt);
            }
        });

        LabelValorCus121.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus121.setText("CRM:");

        jDateDtNascimento.setEnabled(false);

        jTextTelefone.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextTelefone.setEnabled(false);
        jTextTelefone.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextTelefoneActionPerformed(evt);
            }
        });

        LabelValorCus126.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus126.setText("Telefone:");

        jTextSala.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextSala.setEnabled(false);
        jTextSala.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextSalaActionPerformed(evt);
            }
        });

        LabelValorCus127.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus127.setText("Sala Atendimento (Médico):");

        jComboSigla.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jComboSigla.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sigla A", "Sigla B", "Sigla C", "Sigla D", "Sigla E", "Sigla F", "Sigla G", "Sigla H", "Sigla I", "Sigla J", "Sigla K", "Sigla L", "Sigla M", "Sigla N", "Sigla O", "Sigla P", "Sigla Q", "Sigla R", "Sigla S", "Sigla T", "Sigla U", "Sigla V", "Sigla W", "Sigla X", "Sigla Y", "Sigla Z" }));
        jComboSigla.setToolTipText("");
        jComboSigla.setEnabled(false);
        jComboSigla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboSiglaActionPerformed(evt);
            }
        });

        jTextSenha.setText("jPasswordField1");

        jTextRepetirSenha.setText("jPasswordField1");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboSigla, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                            .addComponent(LabelValorCus119)
                                            .addGap(0, 251, Short.MAX_VALUE))
                                        .addComponent(jTextRg, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(LabelValorCus112)
                                        .addGap(160, 160, 160))))
                            .addComponent(LabelValorCus122))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus120)
                            .addComponent(jTextTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelValorCus126, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateDtNascimento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LabelValorCus51, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextCrm, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus121, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelValorCus48, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus116)
                            .addComponent(jTextBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus118)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextCep, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(147, 147, 147)
                                .addComponent(LabelValorCus117)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus52, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                            .addComponent(jComboUf, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus113)
                                    .addComponent(jTextEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(LabelValorCus114, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(50, 50, 50))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jTextNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(6, 6, 6))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextNome, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextComplemento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus115)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LabelValorCus127, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextSala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus123)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus124)
                                .addGap(156, 156, 156))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus125)
                                .addGap(102, 102, 102))
                            .addComponent(jTextRepetirSenha))))
                .addGap(5, 5, 5))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextNome, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelValorCus112)
                                    .addComponent(LabelValorCus121))
                                .addGap(37, 37, 37))
                            .addComponent(jTextCrm, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus119)
                                .addGap(1, 1, 1)
                                .addComponent(jTextRg, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus120, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LabelValorCus48, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextCpf, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                                    .addComponent(jDateDtNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelValorCus126)
                                    .addComponent(LabelValorCus51))
                                .addGap(1, 1, 1)
                                .addComponent(jTextTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus122)
                                .addGap(1, 1, 1)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboSigla)
                                    .addComponent(jTextEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)))))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus114)
                        .addGap(1, 1, 1)
                        .addComponent(jTextNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus113)
                        .addGap(1, 1, 1)
                        .addComponent(jTextEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus115)
                        .addGap(1, 1, 1)
                        .addComponent(jTextComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus117)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboUf, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus118)
                        .addGap(1, 1, 1)
                        .addComponent(jTextCep, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus116)
                        .addGap(1, 1, 1)
                        .addComponent(jTextBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus52)
                        .addGap(37, 37, 37)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelValorCus124)
                            .addComponent(LabelValorCus123))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextRepetirSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus125)
                        .addGap(37, 37, 37))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus127)
                        .addGap(1, 1, 1)
                        .addComponent(jTextSala, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jTabbedPane1.addTab("  Cadastro Colaborador  ", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPesquisaActionPerformed
        int codigo = 0;
        List lista = new ArrayList();
        try {
            codigo = Integer.parseInt(jTextPesquisa.getText());
            if (!funcionarioDAO.getFuncionario(codigo).isEmpty()) {
                funcionarioBean.setCodigo(codigo);
                funcionarioBean.setNome(funcionarioDAO.getFuncionario(codigo).get(0).getNome());
                funcionarioBean.setTelefone(funcionarioDAO.getFuncionario(codigo).get(0).getTelefone());
                funcionarioBean.setEmail(funcionarioDAO.getFuncionario(codigo).get(0).getEmail());
                funcionarioBean.setFuncao(funcionarioDAO.getFuncionario(codigo).get(0).getFuncao());
                lista.add(funcionarioBean);
                model = new FuncionarioTableModel(lista);
                jTableFuncionario.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableFuncionario.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(4).setCellRenderer(esquerda);
            } else {
                JOptionPane.showMessageDialog(null, "Não existem funcionários com o código '" + codigo + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            try {
                if (!funcionarioDAO.getFuncionario(jTextPesquisa.getText()).isEmpty()) {
                    this.numeroLinhas = funcionarioDAO.getNumeroLinhas();
                    for (int i = 0; i < numeroLinhas; i++) {
                        FuncionarioBean p = new FuncionarioBean();
                        p.setCodigo(funcionarioDAO.getFuncionario(jTextPesquisa.getText()).get(i).getCodigo());
                        p.setNome(funcionarioDAO.getFuncionario(jTextPesquisa.getText()).get(i).getNome());
                        p.setTelefone(funcionarioDAO.getFuncionario(jTextPesquisa.getText()).get(i).getTelefone());
                        p.setEmail(funcionarioDAO.getFuncionario(jTextPesquisa.getText()).get(i).getEmail());
                        p.setFuncao(funcionarioDAO.getFuncionario(jTextPesquisa.getText()).get(i).getFuncao());
                        lista.add(p);
                    }
                    model = new FuncionarioTableModel(lista);
                    jTableFuncionario.setModel(model);
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    jTableFuncionario.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    jTableFuncionario.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    jTableFuncionario.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    jTableFuncionario.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    jTableFuncionario.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                } else {
                    model = new FuncionarioTableModel();
                    model.limpaTabela();
                    JOptionPane.showMessageDialog(null, "Não existem funcionários com o nome '" + jTextPesquisa.getText() + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jTextPesquisaActionPerformed

    private void jButtonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisarActionPerformed
        int codigo = 0;
        List lista = new ArrayList();
        try {
            codigo = Integer.parseInt(jTextPesquisa.getText());
            if (!funcionarioDAO.getFuncionario(codigo).isEmpty()) {
                funcionarioBean.setCodigo(codigo);
                funcionarioBean.setNome(funcionarioDAO.getFuncionario(codigo).get(0).getNome());
                funcionarioBean.setTelefone(funcionarioDAO.getFuncionario(codigo).get(0).getTelefone());
                funcionarioBean.setEmail(funcionarioDAO.getFuncionario(codigo).get(0).getEmail());
                funcionarioBean.setFuncao(funcionarioDAO.getFuncionario(codigo).get(0).getFuncao());
                lista.add(funcionarioBean);
                model = new FuncionarioTableModel(lista);
                jTableFuncionario.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableFuncionario.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTableFuncionario.getColumnModel().getColumn(4).setCellRenderer(esquerda);
            } else {
                JOptionPane.showMessageDialog(null, "Não existem funcionários com o código '" + codigo + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            try {
                if (!funcionarioDAO.getFuncionario(jTextPesquisa.getText()).isEmpty()) {
                    this.numeroLinhas = funcionarioDAO.getNumeroLinhas();
                    for (int i = 0; i < numeroLinhas; i++) {
                        FuncionarioBean p = new FuncionarioBean();
                        p.setCodigo(funcionarioDAO.getFuncionario(jTextPesquisa.getText()).get(i).getCodigo());
                        p.setNome(funcionarioDAO.getFuncionario(jTextPesquisa.getText()).get(i).getNome());
                        p.setTelefone(funcionarioDAO.getFuncionario(jTextPesquisa.getText()).get(i).getTelefone());
                        p.setEmail(funcionarioDAO.getFuncionario(jTextPesquisa.getText()).get(i).getEmail());
                        p.setFuncao(funcionarioDAO.getFuncionario(jTextPesquisa.getText()).get(i).getFuncao());
                        lista.add(p);
                    }
                    model = new FuncionarioTableModel(lista);
                    jTableFuncionario.setModel(model);
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    jTableFuncionario.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    jTableFuncionario.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    jTableFuncionario.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    jTableFuncionario.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    jTableFuncionario.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                } else {
                    JOptionPane.showMessageDialog(null, "Não existem funcionários com o nome '" + jTextPesquisa.getText() + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_jButtonPesquisarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        atualiza_tabela();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButtonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoActionPerformed
        this.jButtonNovo.setEnabled(false);
        this.ativaCampo();
        this.limpaCampo();
        this.jButtonGravar.setEnabled(true);
        this.jButtonCancelar.setEnabled(true);
        jTabbedPane1.setEnabledAt(0, false);
    }//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        this.jButtonEditar.setEnabled(false);
        if (jComboFuncao.getSelectedItem() == "Secretaria") {
            this.ativaCampo();
        } else {
            this.ativaCampo();
            this.jComboSigla.setEnabled(true);
            this.jTextSala.setEnabled(true);
            this.jTextCrm.setEnabled(true);
        }
        this.jButtonGravar.setEnabled(true);
        this.jButtonExcluir.setEnabled(true);
        this.jButtonCancelar.setEnabled(true);
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        try {
            int codigo = Integer.parseInt(jTableFuncionario.getValueAt(jTableFuncionario.getSelectedRow(), 0) + "");
            if (JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir " + jTextNome.getText() + "?\nEsses dados serão perdidos!", "Excluir",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                funcionarioDAO.deletaFuncionario(codigo);
                this.desativaCampo();
                this.limpaCampo();
                this.jButtonGravar.setEnabled(false);
                this.jButtonNovo.setEnabled(true);
                this.jButtonEditar.setEnabled(false);
                this.jButtonExcluir.setEnabled(false);
                this.jButtonCancelar.setEnabled(false);
                this.jTableFuncionario.setEnabled(true);
                jTabbedPane1.setEnabledAt(0, true);
                tabela();
                atualiza_tabela();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada na tabela!\n" + e, "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGravarActionPerformed
        String CPF = jTextCpf.getText();
        String Email = jTextEmail.getText();
        Date data;
        try {
            data = jDateDtNascimento.getDate();

            if ((jTextNome.getText().equals("")) || (data.equals("")) || (jTextTelefone.getText().equals("")) || (jTextRg.getText().equals("")) || (jTextCpf.getText().equals("")) || (jTextEndereco.getText().equals("")) || (jTextNumero.getText().equals("")) || (jTextCep.getText().equals("")) || (jTextBairro.getText().equals("")) || (jTextCidade.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Campo(s) vazio(s)! \nPreenchimento obrigatorio de todos os campos!", "Altessmann - Informação", JOptionPane.ERROR_MESSAGE);
            } else {
                //VERIFICA CPF
                if (ValidaCPF.isCPF(CPF) == true) {
                    jTextCpf.setText(ValidaCPF.imprimeCPF(CPF));

                    //verifica email
                    if (ValidaEmail.isEmail(Email) == true) {

                        if (jTextSenha.getText().equals(jTextRepetirSenha.getText())) {
                            String sigla = (jComboSigla.getSelectedItem() + "").substring(6, 7);
                            try {
                                if (!flag) {
                                    if (jTextSenha.getText().equals("")) {  //VERIFICA SE ALGUM CARACTÉRE FOI INSERIDO NO CAMPO SENHA   
                                        JOptionPane.showMessageDialog(null, "Nenhuma senha foi inserida!", "Erro", JOptionPane.WARNING_MESSAGE);
                                    } else if (jTextSenha.getText().equals(jTextRepetirSenha.getText())) { // CASO NAO ELE TESTA SE SENHA E REPETIR SENHA SAO IGUAIS
                                        if (!jTextUsuario.getText().equals("")) {
                                            if (!funcionarioDAO.existeUsuario(jTextUsuario.getText())) {// TESTA SE EXISTE USUARIO IGUAL AO INSERIDO
                                                if (!funcionarioDAO.existeSigla(sigla, 1, 0, jComboFuncao.getSelectedIndex())) {
                                                    if (jComboFuncao.getSelectedIndex() == 0) {
                                                        sigla = "-1";
                                                    }
                                                    funcionarioDAO.setFuncionario(jTextNome.getText(), jComboFuncao.getSelectedIndex(), jTextCrm.getText(), jTextRg.getText(), jTextCpf.getText(), jDateDtNascimento.getCalendar().getTime(), jTextEmail.getText(), jComboSexo.getSelectedItem() + "", jTextEndereco.getText(), jTextNumero.getText(), jTextComplemento.getText(), jTextBairro.getText(), jTextCep.getText(), jTextCidade.getText(), jComboUf.getSelectedItem() + "", jTextUsuario.getText(), jTextSenha.getText(), jTextSala.getText(), jTextTelefone.getText(), sigla, funcionarioBean.getTentativas());
                                                    this.desativaCampo();
                                                    this.limpaCampo();
                                                    tabela();
                                                    atualiza_tabela();
                                                    this.jButtonGravar.setEnabled(false);
                                                    this.jButtonNovo.setEnabled(true);
                                                    this.jButtonCancelar.setEnabled(false);
                                                    this.jButtonExcluir.setEnabled(false);
                                                    this.jButtonEditar.setEnabled(false);
                                                    this.jTableFuncionario.setEnabled(true);
                                                    jTabbedPane1.setEnabledAt(0, true);
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Sigla " + sigla + " já está cadastrada!", "Erro", JOptionPane.WARNING_MESSAGE);
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Usuário já existente!", "Erro", JOptionPane.WARNING_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Campo usuário não pode ser vazio!", "Erro", JOptionPane.WARNING_MESSAGE);
                                        }
                                    } else {    //CASO AS SENHAS SEJAM DIFERENTES ELE NAO INSERE
                                        JOptionPane.showMessageDialog(null, "Repetir senha possui valores diferentes ou inválidos!", "Erro", JOptionPane.WARNING_MESSAGE);
                                    }
                                } else {
                                    //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-/
                                    try {
                                        int codigo = Integer.parseInt(jTableFuncionario.getValueAt(jTableFuncionario.getSelectedRow(), 0) + "");
                                        if (!funcionarioDAO.existeSigla(sigla, 2, codigo, jComboFuncao.getSelectedIndex())) {
                                            if (jComboFuncao.getSelectedIndex() == 0) {
                                                sigla = "-1";
                                            }
                                            funcionarioDAO.atualizaFuncionario(codigo, jTextNome.getText(), jComboFuncao.getSelectedIndex(), jTextCrm.getText(), jTextRg.getText(), jTextCpf.getText(), jDateDtNascimento.getCalendar().getTime(), jTextEmail.getText(), jComboSexo.getSelectedItem() + "", jTextEndereco.getText(), jTextNumero.getText(), jTextComplemento.getText(), jTextBairro.getText(), jTextCep.getText(), jTextCidade.getText(), jComboUf.getSelectedItem() + "", jTextUsuario.getText(), jTextSenha.getText(), jTextSala.getText(), jTextTelefone.getText(), sigla, funcionarioBean.getTentativas());
                                            tabela();
                                            atualiza_tabela();
                                            this.desativaCampo();
                                            this.limpaCampo();
                                            this.jButtonGravar.setEnabled(false);
                                            this.jButtonNovo.setEnabled(true);
                                            this.jButtonCancelar.setEnabled(false);
                                            this.jButtonExcluir.setEnabled(false);
                                            this.jButtonEditar.setEnabled(false);
                                            this.jTableFuncionario.setEnabled(true);
                                            jTabbedPane1.setSelectedIndex(0);
                                            jTabbedPane1.setEnabledAt(0, true);

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Sigla " + sigla + " já está cadastrada!", "Erro", JOptionPane.WARNING_MESSAGE);
                                        }

                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada na tabela!\n" + e, "Erro", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.WARNING_MESSAGE);
                            }
                            tabela();
                        } else {
                            JOptionPane.showMessageDialog(null, "A SENHA NÃO CONFERE!" + "\n" + "VERIFIFIQUE SUA SENHA!", "Altessmann - Informação", JOptionPane.ERROR_MESSAGE);
                            jTextSenha.setText(null);
                            jTextRepetirSenha.setText(null);
                            jTextSenha.grabFocus();
                        }

                    } else { //email invalido
                        JOptionPane.showMessageDialog(null, "EMAIL INVÁLIDO!" + "\n" + "VERIFIQUE O EMAIL DIGITADO!", "Altessmann - Informação", JOptionPane.ERROR_MESSAGE);
                        jTextEmail.setText(null);
                        jTextEmail.grabFocus();
                    }

                } else { // cpf invalido
                    JOptionPane.showMessageDialog(null, "CPF INVÁLIDO!" + "\n" + "VERIFIQUE O CPF DIGITADO!", "Altessmann - Informação", JOptionPane.ERROR_MESSAGE);
                    jTextCpf.setText(null);
                    jTextCpf.grabFocus();
                }

            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "DATA DE NASCIMENTO INVÁLIDA!" + "\n" + "VERIFIQUE A DATA DIGITADA!", "Altessmann - Informação", JOptionPane.ERROR_MESSAGE);
            jDateDtNascimento.setDate(null);
            jDateDtNascimento.grabFocus();
        }
    }//GEN-LAST:event_jButtonGravarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        this.limpaCampo();
        this.desativaCampo();
        this.jButtonGravar.setEnabled(false);
        this.jButtonNovo.setEnabled(true);
        this.jButtonCancelar.setEnabled(false);
        this.jButtonEditar.setEnabled(false);
        this.jButtonExcluir.setEnabled(false);
        this.jTableFuncionario.setEnabled(true);
        jTabbedPane1.setEnabledAt(0, true);
        tabela();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNomeActionPerformed

    private void jTextEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextEnderecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextEnderecoActionPerformed

    private void jTextNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNumeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNumeroActionPerformed

    private void jTextComplementoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextComplementoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextComplementoActionPerformed

    private void jTextBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextBairroActionPerformed

    private void jTextCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCidadeActionPerformed

    private void jTextCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCepActionPerformed
        buscaCEP cep = new buscaCEP();
        String cepLer = jTextCep.getText();
        try {
            jTextCidade.setText((cep.getCidade(cepLer)));
            jComboUf.setSelectedItem(cep.getUF(cepLer));
        } catch (IOException ex) {

        }


    }//GEN-LAST:event_jTextCepActionPerformed

    private void jTextRgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextRgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextRgActionPerformed

    private void jTextCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCpfActionPerformed

    private void jTextEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextEmailActionPerformed

    private void jTextUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextUsuarioActionPerformed

    private void jTextCrmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCrmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCrmActionPerformed

    private void jTextTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextTelefoneActionPerformed

    private void jTextSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextSalaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextSalaActionPerformed

    private void jComboSiglaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboSiglaActionPerformed

    }//GEN-LAST:event_jComboSiglaActionPerformed

    private void jButtonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarActionPerformed
        try {
            examesNew.getPalavraExame2(Integer.parseInt(jTableFuncionario.getValueAt(jTableFuncionario.getSelectedRow(), 0) + ""));
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAdicionarActionPerformed

    private void jComboFuncaoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboFuncaoItemStateChanged
        if (jComboFuncao.getSelectedItem().toString().equals("Médico")) {
            jTextCrm.setEnabled(true);
            jComboSigla.setEnabled(true);
            jTextSala.setEnabled(true);
        } else if (jComboFuncao.getSelectedItem().toString().equals("Secretaria")) {
            jTextCrm.setEnabled(false);
            jComboSigla.setEnabled(false);
            jTextSala.setEnabled(false);
        }
    }//GEN-LAST:event_jComboFuncaoItemStateChanged

    private void jTableFuncionarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableFuncionarioMouseClicked


        /*String senha = JOptionPane.showInputDialog(null, "Digite sua senha:", "Digitar senha", JOptionPane.INFORMATION_MESSAGE);
         int codigo = (int) jTableFuncionario.getValueAt(jTableFuncionario.getSelectedRow(), 0);
         try {
         if (senha.equals(funcionarioDAO.getFuncionario(codigo).get(0).getSenha())) {
         desativaCampo();
         jTabbedPane1.setSelectedIndex(1);
         jTabbedPane1.setEnabledAt(0, false);
         jTabbedPane1.setEnabledAt(1, true);
         jButtonEditar.setEnabled(true);
         jButtonNovo.setEnabled(false);
         jButtonCancelar.setEnabled(true);
         jButtonExcluir.setEnabled(false);
         jTabbedPane1.grabFocus();
         } else {
         JOptionPane.showMessageDialog(null, "Senha inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
         }
         } catch (SQLException ex) {
         Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(Level.SEVERE, null, ex);
         }*/
        desativaCampo();
    }//GEN-LAST:event_jTableFuncionarioMouseClicked

    private void jComboFuncaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboFuncaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboFuncaoActionPerformed

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
            java.util.logging.Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroFuncionarioNew("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelValorCus112;
    private javax.swing.JLabel LabelValorCus113;
    private javax.swing.JLabel LabelValorCus114;
    private javax.swing.JLabel LabelValorCus115;
    private javax.swing.JLabel LabelValorCus116;
    private javax.swing.JLabel LabelValorCus117;
    private javax.swing.JLabel LabelValorCus118;
    private javax.swing.JLabel LabelValorCus119;
    private javax.swing.JLabel LabelValorCus120;
    private javax.swing.JLabel LabelValorCus121;
    private javax.swing.JLabel LabelValorCus122;
    private javax.swing.JLabel LabelValorCus123;
    private javax.swing.JLabel LabelValorCus124;
    private javax.swing.JLabel LabelValorCus125;
    private javax.swing.JLabel LabelValorCus126;
    private javax.swing.JLabel LabelValorCus127;
    private javax.swing.JLabel LabelValorCus48;
    private javax.swing.JLabel LabelValorCus51;
    private javax.swing.JLabel LabelValorCus52;
    private javax.swing.JButton jButton3;
    public javax.swing.JButton jButtonAdicionar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonPesquisar;
    private javax.swing.JComboBox jComboFuncao;
    private javax.swing.JComboBox jComboSexo;
    private javax.swing.JComboBox jComboSigla;
    private javax.swing.JComboBox jComboUf;
    private com.toedter.calendar.JDateChooser jDateDtNascimento;
    private javax.swing.JLabel jLabelFoto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableFuncionario;
    private javax.swing.JTextField jTextBairro;
    private javax.swing.JTextField jTextCep;
    private javax.swing.JTextField jTextCidade;
    private javax.swing.JTextField jTextComplemento;
    private javax.swing.JTextField jTextCpf;
    private javax.swing.JTextField jTextCrm;
    private javax.swing.JTextField jTextEmail;
    private javax.swing.JTextField jTextEndereco;
    private javax.swing.JTextField jTextNome;
    private javax.swing.JTextField jTextNumero;
    private javax.swing.JTextField jTextPesquisa;
    private javax.swing.JPasswordField jTextRepetirSenha;
    private javax.swing.JTextField jTextRg;
    private javax.swing.JTextField jTextSala;
    private javax.swing.JPasswordField jTextSenha;
    private javax.swing.JTextField jTextTelefone;
    private javax.swing.JTextField jTextUsuario;
    // End of variables declaration//GEN-END:variables
}
