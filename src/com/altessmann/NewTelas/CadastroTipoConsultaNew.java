/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.NewTelas;

import Logs.LogsExceptions;
import com.altessmann.AbstractTableModel.ConfConsultaTableModel;
import com.altessmann.Bean.ConfConsultaBean;
import com.altessmann.DAO.ConfConsultaDAO;
//import Telas.ConfiguracoesConsulta;
//import Telas.cadastroConvenio;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Ritiele Aldeburg
 */
public class CadastroTipoConsultaNew extends javax.swing.JFrame {

    /**
     * Creates new form CadastroTipoConsultaNew
     */
    private ConfConsultaTableModel model;
    ConfConsultaDAO confConsultaDAO = new ConfConsultaDAO();
    ConfConsultaBean confConsultaBean = new ConfConsultaBean();
    private int numeroLinhas = 0;
    private boolean flag = false;
    private LogsExceptions logs_exceptions = new LogsExceptions();

    public CadastroTipoConsultaNew() {
        initComponents();
        tabela();
    }

    public void setNovoCodigo() {
        flag = false;
        confConsultaBean.setTentativas(0); // Atualizo ou insiro um dado novo? Esse loko decide!

    }

    public void ativaCampo() {
        jTextNome.setEnabled(true);
        jTextValor.setEnabled(true);
    }

    public void desativaCampo() {
        jTextNome.setEnabled(false);
        jTextValor.setEnabled(false);
    }

    public void limpaCampo() {
        jTextNome.setText(null);
        jTextValor.setText(null);
        jDateDtAtualizacao.setDate(null);
        jDateDtCadastro.setDate(null);
    }

    public void getConfConsulta(int cod) {
        limpaCampo();
        try {
            if (!confConsultaDAO.getConfConsulta(cod).isEmpty()) {
                this.jTextNome.setText(confConsultaDAO.getConfConsulta(cod).get(0).getNome());
                this.jTextValor.setText(confConsultaDAO.getConfConsulta(cod).get(0).getValor() + "");
                this.jDateDtAtualizacao.setDate(confConsultaDAO.getConfConsulta(cod).get(0).getDtAtualizacao());
                this.jDateDtCadastro.setDate(confConsultaDAO.getConfConsulta(cod).get(0).getDtCriacao());
                if (confConsultaDAO.getConfConsulta(cod).get(0).getTentativas() == 0) {
                    flag = true;
                } else {
                    flag = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "O código " + cod + " não está cadastrado!", "Código não cadastrado", JOptionPane.WARNING_MESSAGE);
                this.desativaCampo();
                this.jButtonEditar.setEnabled(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tabela() {
        DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2);
        formatoDois.setParseBigDecimal(true);
        List lista = new ArrayList();
        try {
            confConsultaDAO.getConfConsulta();
            numeroLinhas = confConsultaDAO.getNumeroLinhas();
            for (int i = 0; i < numeroLinhas; i++) {
                ConfConsultaBean confConsulta = new ConfConsultaBean();
                confConsulta.setCodigo(confConsultaDAO.getConfConsulta().get(i).getCodigo());
                confConsulta.setNome(confConsultaDAO.getConfConsulta().get(i).getNome());
                confConsulta.setValor(confConsultaDAO.getConfConsulta().get(i).getValor());
                lista.add(confConsulta);
            }

            model = new ConfConsultaTableModel(lista);
            jTableConfConsulta.setModel(model);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTableConfConsulta.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTableConfConsulta.getColumnModel().getColumn(1).setCellRenderer(esquerda);
            jTableConfConsulta.getColumnModel().getColumn(2).setCellRenderer(esquerda);

        } catch (SQLException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTableConfConsulta.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    jTableConfConsulta.setModel(model);
                    System.out.println(jTableConfConsulta.getValueAt(jTableConfConsulta.getSelectedRow(), 0));
                    getConfConsulta(Integer.parseInt(jTableConfConsulta.getValueAt(jTableConfConsulta.getSelectedRow(), 0).toString()));
                    //jButtonEditar.setEnabled(true);
                    //jButtonExcluir.setEnabled(true);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTextPesquisar = new javax.swing.JTextField();
        jButtonPesquisar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableConfConsulta = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButtonNovo = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonGravar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        LabelValorCus112 = new javax.swing.JLabel();
        jTextNome = new javax.swing.JTextField();
        jTextValor = new javax.swing.JTextField();
        LabelValorCus119 = new javax.swing.JLabel();
        jDateDtCadastro = new com.toedter.calendar.JDateChooser();
        LabelValorCus48 = new javax.swing.JLabel();
        LabelValorCus49 = new javax.swing.JLabel();
        jDateDtAtualizacao = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Altessmann Sistemas - Tipo de Consulta");
        setResizable(false);

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextPesquisar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextPesquisar.setForeground(new java.awt.Color(153, 153, 153));
        jTextPesquisar.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextPesquisar.setPreferredSize(new java.awt.Dimension(500, 25));
        jTextPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPesquisarActionPerformed(evt);
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

        jTableConfConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome/Tipo", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableConfConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableConfConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableConfConsulta);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jTextPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jButtonPesquisar)
                .addGap(3, 3, 3)
                .addComponent(jButton3)
                .addGap(5, 5, 5))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("  Pesquisar Tipos  ", jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Cadastro Tipo de Consulta  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jButtonNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jButtonEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jButtonExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jButtonGravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jButtonCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Detalhes  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        LabelValorCus112.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus112.setText("Nome / Tipo de Consulta:");

        jTextNome.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextNome.setEnabled(false);
        jTextNome.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNomeActionPerformed(evt);
            }
        });

        jTextValor.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextValor.setEnabled(false);
        jTextValor.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextValorActionPerformed(evt);
            }
        });

        LabelValorCus119.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus119.setText("Valor da Consulta R$:");

        jDateDtCadastro.setEnabled(false);
        jDateDtCadastro.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jDateDtCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Calendar.png")));

        LabelValorCus48.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus48.setText("Data Cadastro:");

        LabelValorCus49.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus49.setText("Ultima Atualização:");

        jDateDtAtualizacao.setEnabled(false);
        jDateDtAtualizacao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jDateDtAtualizacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Calendar.png")));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 71, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateDtCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelValorCus48, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateDtAtualizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelValorCus49, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(LabelValorCus112))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus119)
                                    .addComponent(jTextValor, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(5, 5, 5))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextNome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus112)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus119)
                        .addGap(1, 1, 1)
                        .addComponent(jTextValor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus48)
                        .addGap(1, 1, 1)
                        .addComponent(jDateDtCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus49)
                        .addGap(1, 1, 1)
                        .addComponent(jDateDtAtualizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        jTabbedPane1.addTab("  Cadastro Tipo de Consultas  ", jPanel5);

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

    private void jTextPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPesquisarActionPerformed

    private void jButtonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisarActionPerformed

    }//GEN-LAST:event_jButtonPesquisarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        tabela();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButtonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoActionPerformed
        this.jButtonNovo.setEnabled(false);
        this.ativaCampo();
        this.limpaCampo();
        this.jButtonCancelar.setEnabled(true);
        this.jButtonGravar.setEnabled(true);
        this.jButtonEditar.setEnabled(false);
        this.jButtonExcluir.setEnabled(false);
        this.setNovoCodigo();
    }//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        this.ativaCampo();

        this.jButtonEditar.setEnabled(false);

        this.jButtonGravar.setEnabled(true);
        this.jButtonExcluir.setEnabled(true);
        this.jButtonCancelar.setEnabled(true);
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        try {
            int codigo = Integer.parseInt(jTableConfConsulta.getValueAt(jTableConfConsulta.getSelectedRow(), 0) + "");
            if (JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir " + jTextNome.getText() + "?\nEsses dados serão perdidos!", "Excluir",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                confConsultaDAO.deletaConvenio(codigo);
            }
            this.desativaCampo();
            this.limpaCampo();
            this.jButtonGravar.setEnabled(false);
            this.jButtonNovo.setEnabled(true);
            this.jButtonEditar.setEnabled(false);
            this.jButtonExcluir.setEnabled(false);
            this.jButtonCancelar.setEnabled(false);
            jTabbedPane1.setEnabledAt(0, true);
            tabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada na tabela!\n" + e, "Erro", JOptionPane.WARNING_MESSAGE);
        }
        tabela();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGravarActionPerformed
        int codigo;
        int tamanho;
        String descont = "", atualiza = "", atualizaFim = "";
        descont = jTextValor.getText();
        tamanho = jTextValor.getText().length();
        for (int i = 0; i < tamanho; i++) {
            atualiza = descont.charAt(i) + "";

            if (atualiza.equals(",")) {
                atualizaFim = atualizaFim + ".";
            } else {
                atualizaFim = atualizaFim + atualiza;
            }

        }
        jTextValor.setText(atualizaFim);

        if (jTextValor.getText().equals("")) {
            logs_exceptions.ExceptionsTratamento(13);
            jTextValor.grabFocus();
        } else {
            if (!flag) {
                try {
                    confConsultaDAO.setConfConsulta(jTextNome.getText(), Double.parseDouble(jTextValor.getText().replace(',', '.')), confConsultaBean.getTentativas());
                    this.desativaCampo();
                    this.limpaCampo();
                    this.jButtonGravar.setEnabled(false);
                    this.jButtonNovo.setEnabled(true);
                    this.jButtonCancelar.setEnabled(false);
                    this.jButtonExcluir.setEnabled(false);
                    this.jButtonEditar.setEnabled(false);
                    jTabbedPane1.setEnabledAt(0, true);
                    tabela();
                } catch (NumberFormatException ex) {
                    logs_exceptions.ExceptionsTratamento(13);
                    jTextValor.grabFocus();
                }
            } else {

                try {
                    codigo = Integer.parseInt(jTableConfConsulta.getValueAt(jTableConfConsulta.getSelectedRow(), 0) + "");
                    confConsultaDAO.atualizaConfConsulta(codigo, jTextNome.getText(), Double.parseDouble(jTextValor.getText()), confConsultaBean.getTentativas());
                    this.desativaCampo();
                    this.limpaCampo();
                    this.jButtonGravar.setEnabled(false);
                    this.jButtonNovo.setEnabled(true);
                    this.jButtonCancelar.setEnabled(false);
                    this.jButtonExcluir.setEnabled(false);
                    this.jButtonEditar.setEnabled(false);
                    jTabbedPane1.setSelectedIndex(0);
                    jTabbedPane1.setEnabledAt(0, true);
                    //tabela();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada na tabela!\n" + e, "Erro", JOptionPane.WARNING_MESSAGE);
                }
            }

        }
    }//GEN-LAST:event_jButtonGravarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        this.desativaCampo();
        this.jButtonGravar.setEnabled(false);
        this.jButtonNovo.setEnabled(true);
        this.jButtonCancelar.setEnabled(false);
        this.jButtonEditar.setEnabled(false);
        this.jButtonExcluir.setEnabled(false);
        this.limpaCampo();
        jTabbedPane1.setEnabledAt(0, true);
        
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNomeActionPerformed

    private void jTextValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextValorActionPerformed

    private void jTableConfConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableConfConsultaMouseClicked
        jButtonNovo.setEnabled(false);
        jButtonEditar.setEnabled(true);
        jButtonCancelar.setEnabled(true);
        jButtonExcluir.setEnabled(false);
        jTabbedPane1.setSelectedIndex(1);
        jTabbedPane1.setEnabledAt(0, false);
        jTabbedPane1.grabFocus();
    }//GEN-LAST:event_jTableConfConsultaMouseClicked

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
            java.util.logging.Logger.getLogger(CadastroTipoConsultaNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroTipoConsultaNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroTipoConsultaNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroTipoConsultaNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroTipoConsultaNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelValorCus112;
    private javax.swing.JLabel LabelValorCus119;
    private javax.swing.JLabel LabelValorCus48;
    private javax.swing.JLabel LabelValorCus49;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonPesquisar;
    private com.toedter.calendar.JDateChooser jDateDtAtualizacao;
    private com.toedter.calendar.JDateChooser jDateDtCadastro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableConfConsulta;
    private javax.swing.JTextField jTextNome;
    private javax.swing.JTextField jTextPesquisar;
    private javax.swing.JTextField jTextValor;
    // End of variables declaration//GEN-END:variables
}
