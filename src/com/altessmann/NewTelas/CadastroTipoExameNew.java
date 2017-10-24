/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.NewTelas;

import com.altessmann.AbstractTableModel.ConfConsultaTableModel;
import com.altessmann.AbstractTableModel.FuncionarioTableModel;
import com.altessmann.AbstractTableModel.TipoExameTableModel;
import com.altessmann.Bean.ConfConsultaBean;
import com.altessmann.Bean.TipoExameBean;
import com.altessmann.DAO.TipoExameDAO;
import com.altessmann.Tools.RandomValidator;
//import Telas.Cadastro;
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
import javax.swing.text.AbstractDocument;

/**
 *
 * @author Ritiele Aldeburg
 */
public class CadastroTipoExameNew extends javax.swing.JFrame {

    /**
     * Creates new form TipoExameNew
     */
    private final int paraExame = 1;
    private final int paraConsulta = 2;
    private int selecionaJanela;
    private int tipo;
    private TipoExameTableModel model;
    private TipoExameDAO tipoExameDAO = new TipoExameDAO();
    private TipoExameBean tipoExameBean = new TipoExameBean();
    private ExamesNew examesNew = new ExamesNew();
    private CadastroConsultaNew cadastroConsultaNew = new CadastroConsultaNew(2);
    private boolean flag = false;

    public CadastroTipoExameNew() {
        initComponents();
        tabela();
        ((AbstractDocument) jTextValor.getDocument()).setDocumentFilter(new RandomValidator(0, true, false, false, false, ',', ' '));
    }

    public void enviaPalavra(ExamesNew examesNew, String palavra, int selecionaJanela) {//EXAMES
        //this.examesNew = examesNew;
        this.selecionaJanela = selecionaJanela;
        jButtonAdicionar.setEnabled(true);
        jTextPesquisa.setText(palavra);
        List lista = new ArrayList();
        try {
            for (int i = 0; i < tipoExameDAO.getTipoExame(palavra).size(); i++) {
                TipoExameBean p = new TipoExameBean();
                p.setCodigo(tipoExameDAO.getTipoExame(palavra).get(i).getCodigo());
                p.setNome(tipoExameDAO.getTipoExame(palavra).get(i).getNome());
                p.setDescricao(tipoExameDAO.getTipoExame(palavra).get(i).getDescricao());
                p.setValor(tipoExameDAO.getTipoExame(palavra).get(i).getValor());
                lista.add(p);
            }
            model = new TipoExameTableModel(lista);
            jTablePesquisa.setModel(model);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
        } catch (SQLException ex) {
            Logger.getLogger(CadastroTipoExameNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void enviaPalavra(CadastroConsultaNew cadastroConsultaNew, ExamesNew examesNew, String palavra, int selecionaJanela, int tipo) {
        this.selecionaJanela = selecionaJanela;
        this.tipo = tipo;
        if (tipo == paraConsulta) {
            this.cadastroConsultaNew = cadastroConsultaNew;

        } else if (tipo == paraExame) {
            this.examesNew = examesNew;
        }
        jButtonAdicionar.setEnabled(true);
        jButtonAdicionar.setEnabled(true);
        jTextPesquisa.setText(palavra);
        jButtonAdicionar.setEnabled(false);
        jTabbedPane1.setEnabledAt(0, false);
        List lista = new ArrayList();
        try {
            for (int i = 0; i < tipoExameDAO.getTipoExame(palavra).size(); i++) {
                TipoExameBean p = new TipoExameBean();
                p.setCodigo(tipoExameDAO.getTipoExame(palavra).get(i).getCodigo());
                p.setNome(tipoExameDAO.getTipoExame(palavra).get(i).getNome());
                p.setDescricao(tipoExameDAO.getTipoExame(palavra).get(i).getDescricao());
                p.setValor(tipoExameDAO.getTipoExame(palavra).get(i).getValor());
                lista.add(p);
            }
            model = new TipoExameTableModel(lista);
            jTablePesquisa.setModel(model);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
        } catch (SQLException ex) {
            Logger.getLogger(CadastroTipoExameNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNovoCodigo() {
        flag = false;
        tipoExameBean.setTentativas(0); // Atualizo ou insiro um dado novo? Esse loko decide!

    }

    public void ativaCampo() {
        jTextNome.setEnabled(true);
        jTextDescricao.setEnabled(true);
        jTextValor.setEnabled(true);
    }

    public void desativaCampo() {
        jTextNome.setEnabled(false);
        jTextDescricao.setEnabled(false);
        jTextValor.setEnabled(false);
    }

    public void limpaCampo() {
        jTextNome.setText(null);
        jTextDescricao.setText(null);
        jTextValor.setText("");
        flag = false;
    }

    public void getTipoExame(int cod) {
        DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2);
        formatoDois.setParseBigDecimal(true);
        limpaCampo();
        try {
            if (!tipoExameDAO.getTipoExame(cod).isEmpty()) {
                this.jTextNome.setText(tipoExameDAO.getTipoExame(cod).get(0).getNome());
                this.jTextDescricao.setText(tipoExameDAO.getTipoExame(cod).get(0).getDescricao() + "");
                jTextValor.setText(formatoDois.format(tipoExameDAO.getTipoExame(cod).get(0).getValor()));
                if (tipoExameDAO.getTipoExame(cod).get(0).getTentativas() == 0) {
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
            Logger.getLogger(CadastroTipoExameNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tabela() {
        DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2);
        formatoDois.setParseBigDecimal(true);
        List lista = new ArrayList();
        try {
            for (int i = 0; i < tipoExameDAO.getTipoExame().size(); i++) {
                TipoExameBean tipoExameBean = new TipoExameBean();
                tipoExameBean.setCodigo(tipoExameDAO.getTipoExame().get(i).getCodigo());
                tipoExameBean.setNome(tipoExameDAO.getTipoExame().get(i).getNome());
                tipoExameBean.setDescricao(tipoExameDAO.getTipoExame().get(i).getDescricao());
                tipoExameBean.setValor(tipoExameDAO.getTipoExame().get(i).getValor());
                lista.add(tipoExameBean);
            }

            model = new TipoExameTableModel(lista);
            jTablePesquisa.setModel(model);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);

        } catch (SQLException ex) {
            Logger.getLogger(CadastroTipoExameNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTablePesquisa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    jTablePesquisa.setModel(model);
                    getTipoExame((int) model.getValueAt(jTablePesquisa.getSelectedRow(), 0));
                    jButtonEditar.setEnabled(true);
                    jButtonExcluir.setEnabled(true);
                }
            }
        });
    }
    public void fecharJanela(){
        this.dispose();
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
        jTextPesquisa = new javax.swing.JTextField();
        jButtonPesquisar = new javax.swing.JButton();
        jButtonListar = new javax.swing.JButton();
        jButtonAdicionar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablePesquisa = new javax.swing.JTable();
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
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextDescricao = new javax.swing.JTextArea();
        LabelValorCus135 = new javax.swing.JLabel();
        LabelValorCus113 = new javax.swing.JLabel();
        jTextValor = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Altessmann Sistemas - Tipo de Exame");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextPesquisa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextPesquisa.setForeground(new java.awt.Color(153, 153, 153));
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

        jButtonListar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/abrircli.png"))); // NOI18N
        jButtonListar.setText("Listar ");
        jButtonListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListarActionPerformed(evt);
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

        jTablePesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Descricao"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePesquisaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTablePesquisaMouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(jTablePesquisa);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPesquisar)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonListar)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonAdicionar)))
                .addGap(5, 5, 5))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonListar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane3)
                .addGap(5, 5, 5))
        );

        jTabbedPane1.addTab("  Pesquisar Tipos  ", jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Cadastro Tipo de Exame  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

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
        LabelValorCus112.setText("Nome Exame:");

        jTextNome.setBackground(new java.awt.Color(240, 240, 240));
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
            .addGap(0, 71, Short.MAX_VALUE)
        );

        jTextDescricao.setBackground(java.awt.SystemColor.menu);
        jTextDescricao.setColumns(20);
        jTextDescricao.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jTextDescricao.setRows(5);
        jTextDescricao.setEnabled(false);
        jScrollPane2.setViewportView(jTextDescricao);

        LabelValorCus135.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus135.setText("Descrição:");

        LabelValorCus113.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus113.setText("Valor R$:");

        jTextValor.setBackground(new java.awt.Color(240, 240, 240));
        jTextValor.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextValor.setEnabled(false);
        jTextValor.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextValorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus135)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus112)
                            .addComponent(jTextNome, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(LabelValorCus113, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextValor, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(5, 5, 5))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelValorCus112)
                    .addComponent(LabelValorCus113))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextNome, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextValor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(LabelValorCus135)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("  Cadastro Tipo de Exame  ", jPanel5);

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
        try {
            int cod = Integer.parseInt(jTextPesquisa.getText());
            DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
            formatoDois.setMinimumFractionDigits(2);
            formatoDois.setParseBigDecimal(true);
            List lista = new ArrayList();
            try {
                for (int i = 0; i < tipoExameDAO.getTipoExame(cod).size(); i++) {
                    TipoExameBean tipoExameBean = new TipoExameBean();
                    tipoExameBean.setCodigo(tipoExameDAO.getTipoExame(cod).get(i).getCodigo());
                    tipoExameBean.setNome(tipoExameDAO.getTipoExame(cod).get(i).getNome());
                    tipoExameBean.setDescricao(tipoExameDAO.getTipoExame(cod).get(i).getDescricao());
                    tipoExameBean.setValor(tipoExameDAO.getTipoExame(cod).get(i).getValor());
                    lista.add(tipoExameBean);
                }

                model = new TipoExameTableModel(lista);
                jTablePesquisa.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);

            } catch (SQLException ex) {
                Logger.getLogger(CadastroTipoExameNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {

            DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
            formatoDois.setMinimumFractionDigits(2);
            formatoDois.setParseBigDecimal(true);
            List lista = new ArrayList();
            try {
                for (int i = 0; i < tipoExameDAO.getTipoExame(jTextPesquisa.getText()).size(); i++) {
                    TipoExameBean tipoExameBean = new TipoExameBean();
                    tipoExameBean.setCodigo(tipoExameDAO.getTipoExame(jTextPesquisa.getText()).get(i).getCodigo());
                    tipoExameBean.setNome(tipoExameDAO.getTipoExame(jTextPesquisa.getText()).get(i).getNome());
                    tipoExameBean.setDescricao(tipoExameDAO.getTipoExame(jTextPesquisa.getText()).get(i).getDescricao());
                    tipoExameBean.setValor(tipoExameDAO.getTipoExame(jTextPesquisa.getText()).get(i).getValor());
                    lista.add(tipoExameBean);
                }

                model = new TipoExameTableModel(lista);
                jTablePesquisa.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);

            } catch (SQLException ex) {
                Logger.getLogger(CadastroTipoExameNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTextPesquisaActionPerformed

    private void jButtonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisarActionPerformed
        try {
            int cod = Integer.parseInt(jTextPesquisa.getText());
            DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
            formatoDois.setMinimumFractionDigits(2);
            formatoDois.setParseBigDecimal(true);
            List lista = new ArrayList();
            try {
                for (int i = 0; i < tipoExameDAO.getTipoExame(cod).size(); i++) {
                    TipoExameBean tipoExameBean = new TipoExameBean();
                    tipoExameBean.setCodigo(tipoExameDAO.getTipoExame(cod).get(i).getCodigo());
                    tipoExameBean.setNome(tipoExameDAO.getTipoExame(cod).get(i).getNome());
                    tipoExameBean.setDescricao(tipoExameDAO.getTipoExame(cod).get(i).getDescricao());
                    tipoExameBean.setValor(tipoExameDAO.getTipoExame(cod).get(i).getValor());
                    lista.add(tipoExameBean);
                }

                model = new TipoExameTableModel(lista);
                jTablePesquisa.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);

            } catch (SQLException ex) {
                Logger.getLogger(CadastroTipoExameNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {

            DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
            formatoDois.setMinimumFractionDigits(2);
            formatoDois.setParseBigDecimal(true);
            List lista = new ArrayList();
            try {
                for (int i = 0; i < tipoExameDAO.getTipoExame(jTextPesquisa.getText()).size(); i++) {
                    TipoExameBean tipoExameBean = new TipoExameBean();
                    tipoExameBean.setCodigo(tipoExameDAO.getTipoExame(jTextPesquisa.getText()).get(i).getCodigo());
                    tipoExameBean.setNome(tipoExameDAO.getTipoExame(jTextPesquisa.getText()).get(i).getNome());
                    tipoExameBean.setDescricao(tipoExameDAO.getTipoExame(jTextPesquisa.getText()).get(i).getDescricao());
                    tipoExameBean.setValor(tipoExameDAO.getTipoExame(jTextPesquisa.getText()).get(i).getValor());
                    lista.add(tipoExameBean);
                }

                model = new TipoExameTableModel(lista);
                jTablePesquisa.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);

            } catch (SQLException ex) {
                Logger.getLogger(CadastroTipoExameNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButtonPesquisarActionPerformed

    private void jButtonListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListarActionPerformed
        DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2);
        formatoDois.setParseBigDecimal(true);
        List lista = new ArrayList();
        try {
            for (int i = 0; i < tipoExameDAO.getTipoExame().size(); i++) {
                TipoExameBean tipoExameBean = new TipoExameBean();
                tipoExameBean.setCodigo(tipoExameDAO.getTipoExame().get(i).getCodigo());
                tipoExameBean.setNome(tipoExameDAO.getTipoExame().get(i).getNome());
                tipoExameBean.setDescricao(tipoExameDAO.getTipoExame().get(i).getDescricao());
                tipoExameBean.setValor(tipoExameDAO.getTipoExame().get(i).getValor());
                lista.add(tipoExameBean);
            }

            model = new TipoExameTableModel(lista);
            jTablePesquisa.setModel(model);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);

        } catch (SQLException ex) {
            Logger.getLogger(CadastroTipoExameNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButtonListarActionPerformed

    private void jButtonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoActionPerformed
        this.jButtonNovo.setEnabled(false);
        this.ativaCampo();
        this.limpaCampo();
        this.jButtonGravar.setEnabled(true);
        this.jButtonCancelar.setEnabled(true);
        jTabbedPane1.setEnabledAt(0, false);
        this.setNovoCodigo();
    }//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        this.ativaCampo();
        this.jButtonGravar.setEnabled(true);
        this.jButtonExcluir.setEnabled(true);
        this.jButtonCancelar.setEnabled(true);
        this.jButtonEditar.setEnabled(false);
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        try {
            int codigo = Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + "");
            if (JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir " + jTextNome.getText() + "?\nEsses dados serão perdidos!", "Excluir",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                tipoExameDAO.deletaTipoExame(codigo);
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
        if (!flag) {
            tipoExameDAO.setTipoExame(jTextNome.getText(), jTextDescricao.getText(), Double.parseDouble(jTextValor.getText().replace(',', '.')), tipoExameBean.getTentativas());
            this.desativaCampo();
            this.limpaCampo();
            this.jButtonGravar.setEnabled(false);
            this.jButtonNovo.setEnabled(true);
            this.jButtonCancelar.setEnabled(false);
            this.jButtonExcluir.setEnabled(false);
            this.jButtonEditar.setEnabled(false);
            jTabbedPane1.setEnabledAt(0, true);
            tabela();
        } else {
            try {
                codigo = Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + "");
                tipoExameDAO.atualizaTipoExame(codigo, jTextNome.getText(), jTextDescricao.getText(), Double.parseDouble(jTextValor.getText().replace(',', '.')), tipoExameBean.getTentativas());
                this.desativaCampo();
                this.limpaCampo();
                this.jButtonGravar.setEnabled(false);
                this.jButtonNovo.setEnabled(true);
                this.jButtonCancelar.setEnabled(false);
                this.jButtonExcluir.setEnabled(false);
                this.jButtonEditar.setEnabled(false);
                jTabbedPane1.setSelectedIndex(0);
                jTabbedPane1.setEnabledAt(0, true);
                tabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada na tabela!\n" + e, "Erro", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonGravarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        jButtonNovo.setEnabled(true);
        jButtonCancelar.setEnabled(false);
        jButtonEditar.setEnabled(false);
        jButtonExcluir.setEnabled(false);
        jButtonGravar.setEnabled(false);
        jTabbedPane1.setEnabledAt(0, true);
        this.desativaCampo();
        this.limpaCampo();

    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNomeActionPerformed

    private void jButtonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarActionPerformed
        try {
            if (selecionaJanela == paraExame) {
                examesNew.getPalavraExame3(Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + ""));
            } else if (selecionaJanela == paraConsulta) {
                cadastroConsultaNew.getPalavraTipoExame(Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + ""));
            }
            this.dispose();
            cadastroConsultaNew.jButtonExcluirExame.setEnabled(true);
            cadastroConsultaNew.jButtonNovoExame.setEnabled(true);
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!", "Erro", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jButtonAdicionarActionPerformed

    private void jTablePesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePesquisaMouseClicked
        if (tipo != paraConsulta && tipo!=paraExame) {
            jTabbedPane1.grabFocus();
            jButtonNovo.setEnabled(false);
            jButtonEditar.setEnabled(true);
            jButtonCancelar.setEnabled(true);
            jTabbedPane1.setSelectedIndex(1);
            jTabbedPane1.setEnabledAt(0, false);
            jButtonExcluir.setEnabled(false);
        } else {
            jButtonAdicionar.setEnabled(true);
        }
        jTabbedPane1.grabFocus();
    }//GEN-LAST:event_jTablePesquisaMouseClicked

    private void jTablePesquisaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePesquisaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTablePesquisaMouseEntered

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        cadastroConsultaNew.jButtonExcluirExame.setEnabled(true);
        cadastroConsultaNew.jButtonNovoExame.setEnabled(true);
    }//GEN-LAST:event_formWindowClosed

    private void jTextValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextValorActionPerformed

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
            java.util.logging.Logger.getLogger(CadastroTipoExameNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroTipoExameNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroTipoExameNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroTipoExameNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroTipoExameNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelValorCus112;
    private javax.swing.JLabel LabelValorCus113;
    private javax.swing.JLabel LabelValorCus135;
    public javax.swing.JButton jButtonAdicionar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JButton jButtonListar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonPesquisar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTablePesquisa;
    private javax.swing.JTextArea jTextDescricao;
    private javax.swing.JTextField jTextNome;
    private javax.swing.JTextField jTextPesquisa;
    private javax.swing.JTextField jTextValor;
    // End of variables declaration//GEN-END:variables
}
