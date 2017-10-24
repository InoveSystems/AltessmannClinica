/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.NewTelas;

import com.altessmann.AbstractTableModel.PagamentoTableModel;
import com.altessmann.AbstractTableModel.TipoExameTableModel;
import com.altessmann.Bean.ConsultaBean;
import com.altessmann.Bean.PagamentoBean;
import com.altessmann.DAO.ConsultaDAO;
import com.altessmann.Tools.RandomValidator;
import java.math.BigDecimal;
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
public class PagamentoConsultaNew extends javax.swing.JFrame {

    /**
     * Creates new form PagamentoConsultaNew
     */
    private final int user = 0;
    private final int root = 1;
    private ConsultaDAO consultaDAO;
    private ConsultaBean consultaBean = new ConsultaBean();
    private double valorConsultaBruta = 0;
    private double valorAdicional = 0;
    private double soma = 0;
    private CadastroConsultaNew cadastroConsultaNew;
    private PagamentoTableModel model;
    private List lista = new ArrayList();

    public PagamentoConsultaNew() {
        try {
            this.consultaDAO = new ConsultaDAO();
        } catch (SQLException ex) {
           // Logger.getLogger(PagamentoConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        eventoAoDigitarJTextValorConsulta();
        ((AbstractDocument) jTextValor.getDocument()).setDocumentFilter(new RandomValidator(0, true, false, false, false, ',', ' '));
        ((AbstractDocument) jTextValorConsulta.getDocument()).setDocumentFilter(new RandomValidator(0, true, false, false, false, ',', ' '));
    }

    private static BigDecimal truncateDecimal(double x, int numberofDecimals) {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }

    public void eventoAoDigitarJTextValorConsulta() {
        jTextValorConsulta.addKeyListener(new java.awt.event.KeyListener() {

            public void keyTyped(java.awt.event.KeyEvent e) {
            }

            public void keyPressed(java.awt.event.KeyEvent e) {
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (jTextValorConsulta.getText().length() > 0) {
                    DecimalFormat formatoDois = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
                    formatoDois.setMinimumFractionDigits(2);
                    formatoDois.setParseBigDecimal(true);
                    //jLabelTotalConsulta.setText(jTextValorConsulta.getText());
                    jTextValorConsulta.setText(jTextValorConsulta.getText());
                    jLabelDesconto.setText(formatoDois.format(truncateDecimal((valorConsultaBruta - Double.parseDouble(jTextValorConsulta.getText().replace(',', '.'))), 2)));
                    jLabelTotalAReceber.setText(formatoDois.format(Double.parseDouble(jTextValorConsulta.getText().replace(',', '.')) + valorAdicional));
                    jLabelTotalRecebido.setText("0,00");
                    jLabelRestante.setText(formatoDois.format(Double.parseDouble(jTextValorConsulta.getText().replace(',', '.')) + valorAdicional));
                    jLabelTroco.setText("0,00");
                }else{
                    DecimalFormat formatoDois = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
                    formatoDois.setMinimumFractionDigits(2);
                    formatoDois.setParseBigDecimal(true);
                    //jLabelTotalConsulta.setText("0,00");
                    jTextValorConsulta.setText(jTextValorConsulta.getText());
                    jLabelDesconto.setText("0,00");
                    jLabelTotalAReceber.setText("0,00");
                    jLabelTotalRecebido.setText("0,00");
                    jLabelRestante.setText("0,00");
                    jLabelTroco.setText("0,00");
                }
            }
        });
    }

    public void enviaPalavra(CadastroConsultaNew cadastroConsultaNew, double valorDesconto, boolean possuiConvenio, double valorConsultaBruta, double valorAdicional) {
        DecimalFormat formatoDois = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2);
        formatoDois.setParseBigDecimal(true);
        this.cadastroConsultaNew = cadastroConsultaNew;
        this.valorConsultaBruta = valorConsultaBruta;
        this.valorAdicional = valorAdicional;
        if (valorDesconto == 0 && possuiConvenio) {
            jTextValorConsulta.setText("");
            jTextValorConsulta.setEditable(true);
            jTextValorConsulta.setEnabled(true);
            jTextValorConsulta.requestFocus();
            jLabelValorAdicional.setText(formatoDois.format(valorAdicional));
        } else {
            if (possuiConvenio) {
                jTextValorConsulta.setText(formatoDois.format(valorConsultaBruta + valorDesconto));
                jTextValorConsulta.setEditable(false);
                //jLabelTotalConsulta.setText(formatoDois.format(valorConsultaBruta + valorDesconto));
                jLabelDesconto.setText(formatoDois.format(valorDesconto));
                jLabelTotalRecebido.setText("0,00");
                jTextValorConsulta.setEnabled(false);
                jLabelTroco.setText("0,00");
                jLabelValorAdicional.setText(formatoDois.format(valorAdicional));
                jLabelTotalAReceber.setText(formatoDois.format(valorConsultaBruta + valorAdicional));
                jLabelRestante.setText(formatoDois.format(valorConsultaBruta + valorAdicional));
            }
        }
        if (valorDesconto == 0 && !possuiConvenio) {
            jTextValorConsulta.setText(formatoDois.format(valorConsultaBruta - valorDesconto));
            jTextValorConsulta.setEditable(false);
            jLabelDesconto.setText(formatoDois.format(valorDesconto));
            //jLabelTotalConsulta.setText(formatoDois.format(valorConsultaBruta));          
            jTextValorConsulta.setEditable(false);
            jLabelTroco.setText("0,00");
            jLabelValorAdicional.setText(formatoDois.format(valorAdicional));
            jLabelTotalAReceber.setText(formatoDois.format(valorConsultaBruta + valorAdicional));
            jLabelRestante.setText(formatoDois.format(valorConsultaBruta + valorAdicional));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabelTotalDaConsulta = new javax.swing.JLabel();
        jLabelDesc = new javax.swing.JLabel();
        dsdsjLabelAcrescimo = new javax.swing.JLabel();
        jLabelReceberTotal = new javax.swing.JLabel();
        jLabelTotalQueFoiRecebido = new javax.swing.JLabel();
        jLabelRestante4 = new javax.swing.JLabel();
        jLabelrrr = new javax.swing.JLabel();
        jLabelDesconto = new javax.swing.JLabel();
        jLabelTotalAReceber = new javax.swing.JLabel();
        jLabelRestante = new javax.swing.JLabel();
        jLabelTotalRecebido = new javax.swing.JLabel();
        jLabelTroco = new javax.swing.JLabel();
        jLabelValorAdicional = new javax.swing.JLabel();
        jTextValorConsulta = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableValores = new javax.swing.JTable();
        jTextValor = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboFormaPagamento = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jButtonInserirValor = new javax.swing.JButton();
        jButtonGravar3 = new javax.swing.JButton();
        jButtonGravar2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Altessmann Sistemas - Pagamento Consulta");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Dados para Pagamento  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 16))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Resumo  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabelTotalDaConsulta.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelTotalDaConsulta.setForeground(new java.awt.Color(0, 51, 153));
        jLabelTotalDaConsulta.setText("Total Consulta:");

        jLabelDesc.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelDesc.setForeground(new java.awt.Color(255, 0, 0));
        jLabelDesc.setText("Desconto:");

        dsdsjLabelAcrescimo.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        dsdsjLabelAcrescimo.setForeground(new java.awt.Color(0, 51, 153));
        dsdsjLabelAcrescimo.setText("Acréscimo:");

        jLabelReceberTotal.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelReceberTotal.setForeground(new java.awt.Color(0, 51, 153));
        jLabelReceberTotal.setText("Total a Receber:");

        jLabelTotalQueFoiRecebido.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelTotalQueFoiRecebido.setForeground(new java.awt.Color(0, 153, 0));
        jLabelTotalQueFoiRecebido.setText("Total Recebido:");

        jLabelRestante4.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelRestante4.setForeground(new java.awt.Color(0, 51, 153));
        jLabelRestante4.setText("Restante:");

        jLabelrrr.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelrrr.setForeground(new java.awt.Color(255, 0, 0));
        jLabelrrr.setText("Troco:");

        jLabelDesconto.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelDesconto.setForeground(new java.awt.Color(255, 0, 0));
        jLabelDesconto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelDesconto.setText("0,00");

        jLabelTotalAReceber.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelTotalAReceber.setForeground(new java.awt.Color(0, 51, 153));
        jLabelTotalAReceber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotalAReceber.setText("0,00");

        jLabelRestante.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelRestante.setForeground(new java.awt.Color(0, 51, 153));
        jLabelRestante.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelRestante.setText("0,00");

        jLabelTotalRecebido.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelTotalRecebido.setForeground(new java.awt.Color(0, 153, 0));
        jLabelTotalRecebido.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotalRecebido.setText("0,00");

        jLabelTroco.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelTroco.setForeground(new java.awt.Color(255, 0, 0));
        jLabelTroco.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTroco.setText("0,00");

        jLabelValorAdicional.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelValorAdicional.setForeground(new java.awt.Color(255, 0, 0));
        jLabelValorAdicional.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelValorAdicional.setText("0,00");

        jTextValorConsulta.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jTextValorConsulta.setForeground(new java.awt.Color(153, 153, 153));
        jTextValorConsulta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextValorConsulta.setText("0,00");
        jTextValorConsulta.setBorder(null);
        jTextValorConsulta.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextValorConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextValorConsultaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelrrr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelTotalDaConsulta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextValorConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelDesc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelTotalQueFoiRecebido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(jLabelTotalRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelRestante4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelRestante, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelReceberTotal)
                            .addComponent(dsdsjLabelAcrescimo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTotalAReceber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelValorAdicional, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(5, 5, 5))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotalDaConsulta)
                    .addComponent(jTextValorConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDesc)
                    .addComponent(jLabelDesconto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dsdsjLabelAcrescimo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelValorAdicional))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelReceberTotal)
                    .addComponent(jLabelTotalAReceber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotalQueFoiRecebido)
                    .addComponent(jLabelTotalRecebido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRestante4)
                    .addComponent(jLabelRestante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelrrr)
                    .addComponent(jLabelTroco))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Informe os Valores Pagos  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jTableValores.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableValores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo Pagamento", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableValores.setMaximumSize(new java.awt.Dimension(2147483647, 200));
        jTableValores.setMinimumSize(new java.awt.Dimension(770, 800));
        jTableValores.setRowHeight(20);
        jTableValores.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableValores);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jTextValor.setFont(new java.awt.Font("Arial", 1, 19)); // NOI18N
        jTextValor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextValor.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextValorActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel14.setText("Valor:");

        jComboFormaPagamento.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jComboFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dinheiro", "Cheque", "Cartao de Credito", "Cartao de Debito" }));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel25.setText("Tipo de Pagamento:");

        jButtonInserirValor.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonInserirValor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add2.png"))); // NOI18N
        jButtonInserirValor.setDoubleBuffered(true);
        jButtonInserirValor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonInserirValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserirValorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextValor, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonInserirValor, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(1, 1, 1)
                                .addComponent(jTextValor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(1, 1, 1)
                                .addComponent(jComboFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonInserirValor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jButtonGravar3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonGravar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cancel24x24.png"))); // NOI18N
        jButtonGravar3.setText("Cancelar");
        jButtonGravar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGravar3ActionPerformed(evt);
            }
        });

        jButtonGravar2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonGravar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/save24x24.png"))); // NOI18N
        jButtonGravar2.setText("Confirmar");
        jButtonGravar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGravar2ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/moneyx64.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonGravar2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonGravar3)
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGravar3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGravar2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextValorActionPerformed

    private void jButtonGravar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGravar3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonGravar3ActionPerformed

    private void jButtonGravar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGravar2ActionPerformed
        if (Double.parseDouble(jLabelRestante.getText().replace(',', '.')) == 0) {
            if (jTableValores.getRowCount() > 0) {
                cadastroConsultaNew.gravar(Double.parseDouble(jLabelTotalAReceber.getText().replace(',', '.')), Double.parseDouble(jLabelDesconto.getText().replace(',', '.')));
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Você precisa informar o valor pago!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "O valor recebido é diferente do restante!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButtonGravar2ActionPerformed

    private void jButtonInserirValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInserirValorActionPerformed
        jTextValorConsulta.setEnabled(false);
        jTextValorConsulta.setEditable(false);
        DecimalFormat formatoDois = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2);
        formatoDois.setParseBigDecimal(true);
        PagamentoBean c = new PagamentoBean();
        if (Double.parseDouble(jTextValor.getText().replace(',', '.')) != 0) {
            c.setTipo(jComboFormaPagamento.getSelectedItem().toString());
            c.setValor(Double.parseDouble(jTextValor.getText().replace(',', '.')));
            lista.add(c);
            model = new PagamentoTableModel(lista);
            jTableValores.setModel(model);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTableValores.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTableValores.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        }
        double soma = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            soma = soma + Double.parseDouble((model.getValueAt(i, 1).toString().replace(',', '.')));
        }
        this.soma = soma;
        jTextValor.requestFocus();

        if (soma <= Double.parseDouble(jLabelTotalAReceber.getText().replace(',', '.'))) {
            jLabelTroco.setText("0,00");
        } else {
            jLabelTroco.setText(formatoDois.format(soma - Double.parseDouble(jLabelTotalAReceber.getText().replace(',', '.'))));
        }
        if (Double.parseDouble(jLabelTotalAReceber.getText().replace(',', '.')) <= soma) {
            jLabelRestante.setText("0,00");
        } else {
            jLabelRestante.setText(formatoDois.format(Double.parseDouble(jLabelTotalAReceber.getText().replace(',', '.')) - soma));
        }
        jLabelTotalRecebido.setText(formatoDois.format(soma));
        jTextValor.setText("");
        jTextValor.requestFocus();


    }//GEN-LAST:event_jButtonInserirValorActionPerformed

    private void jTextValorConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextValorConsultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextValorConsultaActionPerformed

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
            java.util.logging.Logger.getLogger(PagamentoConsultaNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PagamentoConsultaNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PagamentoConsultaNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PagamentoConsultaNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PagamentoConsultaNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dsdsjLabelAcrescimo;
    private javax.swing.JButton jButtonGravar2;
    private javax.swing.JButton jButtonGravar3;
    private javax.swing.JButton jButtonInserirValor;
    private javax.swing.JComboBox jComboFormaPagamento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabelDesc;
    private javax.swing.JLabel jLabelDesconto;
    private javax.swing.JLabel jLabelReceberTotal;
    private javax.swing.JLabel jLabelRestante;
    private javax.swing.JLabel jLabelRestante4;
    private javax.swing.JLabel jLabelTotalAReceber;
    private javax.swing.JLabel jLabelTotalDaConsulta;
    private javax.swing.JLabel jLabelTotalQueFoiRecebido;
    private javax.swing.JLabel jLabelTotalRecebido;
    private javax.swing.JLabel jLabelTroco;
    private javax.swing.JLabel jLabelValorAdicional;
    private javax.swing.JLabel jLabelrrr;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableValores;
    private javax.swing.JTextField jTextValor;
    private javax.swing.JTextField jTextValorConsulta;
    // End of variables declaration//GEN-END:variables
}
