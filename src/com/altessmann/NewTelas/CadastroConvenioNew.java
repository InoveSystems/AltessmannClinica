/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.NewTelas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import com.altessmann.AbstractTableModel.Convenio_cadastroTableModel;
import com.altessmann.DAO.ConvenioDAO;
import com.altessmann.Bean.ConvenioBean;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Ritiele Aldeburg
 */
public class CadastroConvenioNew extends javax.swing.JFrame {
    
    private Convenio_cadastroTableModel model;
    ConvenioDAO convenioDAO = new ConvenioDAO();
    ConvenioBean convenioBean = new ConvenioBean();
    private int numeroLinhas = 0;
    private boolean flag = false;

    /**
     * Creates new form NewJFrame
     */
    public CadastroConvenioNew() {
        initComponents();
        jButtonEditar.setEnabled(false);
        jButtonExcluir.setEnabled(false);
        jButtonGravar.setEnabled(false);
        jButtonSair.setEnabled(false);
        desativaCampo();
        limpaCampo();
        //tabela();
    }
    
    public void setNovoCodigo() {
        flag = false;
        convenioBean.setTentativas(0); // Atualizo ou insiro um dado novo? Esse loko decide!
    }
    
    public void limpaCampo() {
        this.jTextNome.setText(null);
        this.jTextPorcentagem.setText(null);
        this.jDateDtAtualizacao.setDate(null);
        this.jDateDtCadastro.setDate(null);
    }
    
    public void ativaCampo() {
        this.jTextNome.setEnabled(true);
        this.jTextPorcentagem.setEnabled(true);
        
    }
    
    public void desativaCampo() {
        this.jTextNome.setEnabled(false);
        this.jTextPorcentagem.setEnabled(false);
        
    }
    
    public void getConvenio(int cod) {
        limpaCampo();
        try {
            if (!convenioDAO.getConvenio(cod).isEmpty()) {
                this.jTextNome.setText(convenioDAO.getConvenio(cod).get(0).getNome());
                this.jTextPorcentagem.setText(convenioDAO.getConvenio(cod).get(0).getPorcentagem() + "");
                this.jDateDtAtualizacao.setDate(convenioDAO.getConvenio(cod).get(0).getDtAtualizacao());
                this.jDateDtCadastro.setDate(convenioDAO.getConvenio(cod).get(0).getDtCadastro());
                flag = convenioDAO.getConvenio(cod).get(0).getTentativas() == 0;
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
            convenioDAO.getConvenio();
            numeroLinhas = convenioDAO.getNumeroLinhas();
            for (int i = 0; i < numeroLinhas; i++) {
                ConvenioBean convenio = new ConvenioBean();
                convenio.setCodigo(convenioDAO.getConvenio().get(i).getCodigo());
                convenio.setNome(convenioDAO.getConvenio().get(i).getNome());
                convenio.setPorcentagem(convenioDAO.getConvenio().get(i).getPorcentagem());
                lista.add(convenio);
            }
            
            model = new Convenio_cadastroTableModel(lista);
            jTableConvenio.setModel(model);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTableConvenio.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTableConvenio.getColumnModel().getColumn(1).setCellRenderer(esquerda);
            jTableConvenio.getColumnModel().getColumn(2).setCellRenderer(esquerda);
            
        } catch (SQLException ex) {
            Logger.getLogger(CadastroConvenioNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTableConvenio.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    jTableConvenio.setModel(model);
                    getConvenio((int) model.getValueAt(jTableConvenio.getSelectedRow(), 0));
                    jButtonEditar.setEnabled(true);
                    jButtonExcluir.setEnabled(false);
                    jButtonNovo.setEnabled(false);
                    
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableConvenio = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButtonNovo = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonGravar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        LabelValorCus112 = new javax.swing.JLabel();
        jTextNome = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jTextPorcentagem = new javax.swing.JTextField();
        LabelValorCus113 = new javax.swing.JLabel();
        jDateDtCadastro = new com.toedter.calendar.JDateChooser();
        LabelValorCus52 = new javax.swing.JLabel();
        jDateDtAtualizacao = new com.toedter.calendar.JDateChooser();
        LabelValorCus53 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Altessmann Sistemas - Convenio");
        setResizable(false);

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextPesquisar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
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

        jTableConvenio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Título", "Valor"
            }
        ));
        jTableConvenio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableConvenioMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableConvenio);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPesquisar)
                        .addGap(3, 3, 3)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane2))
                .addGap(5, 5, 5))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        jTabbedPane1.addTab("  Pesquisar Convenio  ", jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Cadastro Convenio  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

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

        jButtonSair.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cancel24x24.png"))); // NOI18N
        jButtonSair.setText("Cancelar");
        jButtonSair.setEnabled(false);
        jButtonSair.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
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
                .addComponent(jButtonSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(jButtonSair, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Detalhes  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        LabelValorCus112.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus112.setText("Nome Convenio:");

        jTextNome.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextNome.setEnabled(false);
        jTextNome.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNomeActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 93, Short.MAX_VALUE)
        );

        jTextPorcentagem.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextPorcentagem.setEnabled(false);
        jTextPorcentagem.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextPorcentagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPorcentagemActionPerformed(evt);
            }
        });

        LabelValorCus113.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus113.setText("Valor ConsultaR$:");

        jDateDtCadastro.setEnabled(false);
        jDateDtCadastro.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jDateDtCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Calendar.png")));

        LabelValorCus52.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus52.setText("Data Cadastro:");

        jDateDtAtualizacao.setEnabled(false);
        jDateDtAtualizacao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jDateDtAtualizacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Calendar.png")));

        LabelValorCus53.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus53.setText("Data Ultima Alteração:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(19, 19, 19))
                            .addComponent(jDateDtCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(19, 19, 19))
                            .addComponent(jDateDtAtualizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus112)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextPorcentagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LabelValorCus113, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelValorCus112)
                            .addComponent(LabelValorCus113))
                        .addGap(1, 1, 1)
                        .addComponent(jTextNome, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jTextPorcentagem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus52)
                        .addGap(1, 1, 1)
                        .addComponent(jDateDtCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus53)
                        .addGap(1, 1, 1)
                        .addComponent(jDateDtAtualizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("  Cadastro Convenio  ", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPesquisarActionPerformed
        tabela();
    }//GEN-LAST:event_jTextPesquisarActionPerformed

    private void jButtonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisarActionPerformed
        tabela();
    }//GEN-LAST:event_jButtonPesquisarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        tabela();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButtonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoActionPerformed
        this.jButtonNovo.setEnabled(false);
        this.ativaCampo();
        this.limpaCampo();
        setNovoCodigo();
        this.jButtonGravar.setEnabled(true);
        this.jButtonSair.setEnabled(true);
        jTabbedPane1.setEnabledAt(0, false);
    }//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        this.ativaCampo();
        this.jButtonEditar.setEnabled(false);
        this.jButtonGravar.setEnabled(true);
        this.jButtonExcluir.setEnabled(true);
        this.jButtonSair.setEnabled(true);
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        try {
            int codigo = Integer.parseInt(jTableConvenio.getValueAt(jTableConvenio.getSelectedRow(), 0) + "");
            if (JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir " + jTextNome.getText() + "?\nEsses dados serão perdidos!", "Excluir",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                convenioDAO.deletaConvenio(codigo);
            }
            this.desativaCampo();
            this.limpaCampo();
            this.jButtonGravar.setEnabled(false);
            this.jButtonNovo.setEnabled(true);
            this.jButtonEditar.setEnabled(false);
            this.jButtonExcluir.setEnabled(false);
            this.jButtonSair.setEnabled(false);
            this.jTableConvenio.setEnabled(true);
            jTabbedPane1.setEnabledAt(0, true);
            tabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada na tabela!\n" + e, "Erro", JOptionPane.WARNING_MESSAGE);
        }
        tabela();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGravarActionPerformed
        int tamanho;
        String descont = "", atualiza = "", atualizaFim = "";
        descont = jTextPorcentagem.getText();
        tamanho = jTextPorcentagem.getText().length();
        for (int i = 0; i < tamanho; i++) {
            atualiza = descont.charAt(i) + "";
            
            if (atualiza.equals(",")) {
                atualizaFim = atualizaFim + ".";
            } else {
                atualizaFim = atualizaFim + atualiza;
            }
            
        }
        jTextPorcentagem.setText(atualizaFim);
        System.out.println(atualizaFim);
        
        int codigo;
        if (jTextPorcentagem.getText().equals("")) {
            if (!flag) {
                convenioDAO.setConvenio(jTextNome.getText(), Double.parseDouble("0"), convenioBean.getTentativas());
                this.desativaCampo();
                this.limpaCampo();
                this.jButtonGravar.setEnabled(false);
                this.jButtonNovo.setEnabled(true);
                this.jButtonSair.setEnabled(false);
                this.jButtonExcluir.setEnabled(false);
                this.jButtonEditar.setEnabled(false);
                this.jTableConvenio.setEnabled(true);
                jTabbedPane1.setEnabledAt(0, true);
                tabela();
            } else {
                try {
                    codigo = Integer.parseInt(jTableConvenio.getValueAt(jTableConvenio.getSelectedRow(), 0) + "");
                    convenioDAO.atualizaConvenio(codigo, jTextNome.getText(), Double.parseDouble("0"), convenioBean.getTentativas());
                    this.desativaCampo();
                    this.limpaCampo();
                    this.jButtonGravar.setEnabled(false);
                    this.jButtonNovo.setEnabled(true);
                    this.jButtonSair.setEnabled(false);
                    this.jButtonExcluir.setEnabled(false);
                    this.jButtonEditar.setEnabled(false);
                    this.jTableConvenio.setEnabled(true);
                    jTabbedPane1.setSelectedIndex(0);
                    jTabbedPane1.setEnabledAt(0, true);
                    tabela();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada na tabela!\n" + e, "Erro", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            if (!flag) {
                try {
                    convenioDAO.setConvenio(jTextNome.getText(), Double.parseDouble(jTextPorcentagem.getText()), convenioBean.getTentativas());
                    this.desativaCampo();
                    this.limpaCampo();
                    this.jButtonGravar.setEnabled(false);
                    this.jButtonNovo.setEnabled(true);
                    this.jButtonSair.setEnabled(false);
                    this.jButtonExcluir.setEnabled(false);
                    this.jButtonEditar.setEnabled(false);
                    this.jTableConvenio.setEnabled(true);
                    jTabbedPane1.setEnabledAt(0, true);
                    tabela();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Verifique o campo Desconto! \nTipo de valor INVALIDO!", "Altessmann Systems - Informação", JOptionPane.ERROR_MESSAGE);
                    jTextPorcentagem.grabFocus();
                }
                
            } else {
                try {
                    codigo = Integer.parseInt(jTableConvenio.getValueAt(jTableConvenio.getSelectedRow(), 0) + "");
                    convenioDAO.atualizaConvenio(codigo, jTextNome.getText(), Double.parseDouble(jTextPorcentagem.getText()), convenioBean.getTentativas());
                    this.desativaCampo();
                    this.limpaCampo();
                    this.jButtonGravar.setEnabled(false);
                    this.jButtonNovo.setEnabled(true);
                    this.jButtonSair.setEnabled(false);
                    this.jButtonExcluir.setEnabled(false);
                    this.jButtonEditar.setEnabled(false);
                    this.jTableConvenio.setEnabled(true);
                    jTabbedPane1.setSelectedIndex(0);
                    jTabbedPane1.setEnabledAt(0, true);
                    tabela();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Verifique o campo Desconto! \nTipo de valor INVALIDO!", "Altessmann Systems - Informação", JOptionPane.ERROR_MESSAGE);
                    jTextPorcentagem.grabFocus();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada na tabela!\n" + e, "Erro", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

    }//GEN-LAST:event_jButtonGravarActionPerformed

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        this.desativaCampo();
        this.jButtonGravar.setEnabled(false);
        this.jButtonNovo.setEnabled(true);
        this.jButtonSair.setEnabled(false);
        this.jButtonEditar.setEnabled(false);
        this.jButtonExcluir.setEnabled(false);
        this.limpaCampo();
        jTabbedPane1.setEnabledAt(0, true);

    }//GEN-LAST:event_jButtonSairActionPerformed

    private void jTextNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNomeActionPerformed

    private void jTextPorcentagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPorcentagemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPorcentagemActionPerformed

    private void jTableConvenioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableConvenioMouseClicked
        jTabbedPane1.grabFocus();
        jTextPesquisar.setText("");
        jButtonNovo.setEnabled(false);
        jButtonEditar.setEnabled(true);
        jButtonSair.setEnabled(true);
        jTabbedPane1.setSelectedIndex(1);
        jTabbedPane1.setEnabledAt(0, false);
        

    }//GEN-LAST:event_jTableConvenioMouseClicked

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
            java.util.logging.Logger.getLogger(CadastroConvenioNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroConvenioNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroConvenioNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroConvenioNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroConvenioNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelValorCus112;
    private javax.swing.JLabel LabelValorCus113;
    private javax.swing.JLabel LabelValorCus52;
    private javax.swing.JLabel LabelValorCus53;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonPesquisar;
    private javax.swing.JButton jButtonSair;
    private com.toedter.calendar.JDateChooser jDateDtAtualizacao;
    private com.toedter.calendar.JDateChooser jDateDtCadastro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableConvenio;
    private javax.swing.JTextField jTextNome;
    private javax.swing.JTextField jTextPesquisar;
    private javax.swing.JTextField jTextPorcentagem;
    // End of variables declaration//GEN-END:variables
}
