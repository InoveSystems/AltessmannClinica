/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.NewTelas;

import com.altessmann.scaner.MorenaStudio;
import com.altessmann.AbstractTableModel.ArquivoTableModel;
import com.altessmann.AbstractTableModel.ExameTableModel;
import com.altessmann.Bean.ExameBean;
import com.altessmann.Bean.PacienteBean;
import com.altessmann.Bean.ArquivoBean;
import com.altessmann.DAO.ExameDAO;
import com.altessmann.DAO.FuncionarioDAO;
import com.altessmann.DAO.PacienteDAO;
import com.altessmann.DAO.TipoExameDAO;
import com.altessmann.scaner.MorenaStudio;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Ritiele Aldeburg && Guilherme Tessmann
 */
public class ExamesNew extends javax.swing.JFrame {

    private Process processo;
    private ArquivoTableModel model;
    private ExameTableModel model2;
    private PacienteBean pacienteBean = new PacienteBean();
    private PacienteDAO pacienteDAO = new PacienteDAO();
    private FuncionarioDAO funcionarioDAO;
    private TipoExameDAO tipoExameDAO = new TipoExameDAO();
    private ExameDAO exameDAO = new ExameDAO();
    private ExameBean exameBean = new ExameBean();
    private CadastroConsultaNew cadastroConsultaNew = new CadastroConsultaNew(2);

    private boolean flag = false;
    private int maiorNumeroArquivo = 0;
    private int numeroVezesEscaneado = 0;

    private List<byte[]> listaArquivos = null;
    //private List lista = new ArrayList();
    List<ArquivoBean> listaCerta = new ArrayList<>();

    /**
     * Creates new form ExamesNew
     */
    public ExamesNew() {
        try {
            this.funcionarioDAO = new FuncionarioDAO();
        } catch (Exception ex) {
            Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        jButtonEditar.setEnabled(false);
        jButtonExcluir.setEnabled(false);
        jButtonGravar.setEnabled(false);
        desativaCampo();
        limpaCampo();
        tabelaPesquisa();
        tabelaArquivo();
        jTableArquivo.setToolTipText("Para visuzalizar o arquivo, basta dar dois cliques!");
    }

    public int getNumeroVezesEscaneado() {
        return numeroVezesEscaneado;
    }

    public void setNumeroVezesEscaneado(int numeroVezesEscaneado) {
        this.numeroVezesEscaneado = numeroVezesEscaneado;
    }

    public void limpaCampo() {
        jTextCodPaciente.setText(null);
        jTextNomePaciente.setText(null);
        jTextCodMedico.setText(null);
        jTextNomeMedico.setText(null);
        jTextCodTipoExame.setText(null);
        jTextNomeTipoExame.setText(null);
        jTextDescricaoTipoExame.setText(null);
        jDateDtRequisicao.setDate(null);
        jDateDtLaudo.setDate(null);
        jTextLaudo.setText(null);
        flag = false;
        if (jTableArquivo.getRowCount() > 0) {
            model.limpaTabela();
        }
        listaCerta.clear();
        maiorNumeroArquivo = 0;
        numeroVezesEscaneado = 0;
    }

    public void ativaCampo() {
        jTextCodPaciente.setEnabled(true);
        jTextNomePaciente.setEnabled(true);
        jTextCodMedico.setEnabled(true);
        jTextNomeMedico.setEnabled(true);
        jTextCodTipoExame.setEnabled(true);
        jTextNomeTipoExame.setEnabled(true);
        jTextDescricaoTipoExame.setEnabled(true);
        jDateDtRequisicao.setEnabled(true);
        jDateDtLaudo.setEnabled(true);
        jTextLaudo.setEnabled(true);
        jButtonInserirMedico.setEnabled(true);
        jButtonInserirPaciente.setEnabled(true);
        jButtonInserirTipoExame.setEnabled(true);
        jButtonNovoArquivo.setEnabled(true);
        jButtonExcluirArquivo.setEnabled(true);
    }

    public void desativaCampo() {
        jTextCodPaciente.setEnabled(false);
        jTextNomePaciente.setEnabled(false);
        jTextCodMedico.setEnabled(false);
        jTextNomeMedico.setEnabled(false);
        jTextCodTipoExame.setEnabled(false);
        jTextNomeTipoExame.setEnabled(false);
        jTextDescricaoTipoExame.setEnabled(false);
        jDateDtRequisicao.setEnabled(false);
        jDateDtLaudo.setEnabled(false);
        jTextLaudo.setEnabled(false);
        jButtonInserirMedico.setEnabled(false);
        jButtonInserirPaciente.setEnabled(false);
        jButtonInserirTipoExame.setEnabled(false);
        jButtonNovoArquivo.setEnabled(false);
        jButtonExcluirArquivo.setEnabled(false);
    }

    public void historicoPacienteGetExame(int cod) {
        getExame(cod);
        jTableArquivo.setEnabled(true);
        jButtonNovo.setEnabled(false);
        jTabbedPane.setSelectedIndex(1);
        jTabbedPane.grabFocus();
        jTabbedPane.setEnabledAt(0, false);
        jTabbedPane.setEnabledAt(1, true);
        jButtonEditar.setEnabled(false);
        jButtonNovo.setEnabled(false);
        jButtonCancelar.setEnabled(true);
        jButtonExcluir.setEnabled(false);
    }

    public void getExame(int cod) {
        try {
            jTextCodPaciente.setText(exameDAO.getExame(cod).get(0).getCodPaciente() + "");
            jTextNomePaciente.setText(exameDAO.getExame(cod).get(0).getNomePaciente());
            jTextCodMedico.setText(exameDAO.getExame(cod).get(0).getCodMedico() + "");
            jTextNomeMedico.setText(exameDAO.getExame(cod).get(0).getNomeMedico());
            jTextCodTipoExame.setText(exameDAO.getExame(cod).get(0).getCodTipoExame() + "");
            jTextNomeTipoExame.setText(exameDAO.getExame(cod).get(0).getNomeTipoExame());
            jTextDescricaoTipoExame.setText(exameDAO.getExame(cod).get(0).getDescricaoTipoExame());
            jDateDtRequisicao.setDate(exameDAO.getExame(cod).get(0).getDtRequisicao());
            jDateDtLaudo.setDate(exameDAO.getExame(cod).get(0).getDtLaudo());
            jTextLaudo.setText(exameDAO.getExame(cod).get(0).getLaudo());
            maiorNumeroArquivo = exameDAO.getExame(cod).get(0).getMaiorNumeroArquivo();
            flag = exameDAO.getExame(cod).get(0).getTentativas() == 0;
            //if (!getArquivos(cod)) {
            if (jTableArquivo.getRowCount() > 0) {
                model.limpaTabela();
            }
            // }

            for (int i = 0; i < exameDAO.getArquivo(cod).size(); i++) {
                if (listaArquivos == null) {
                    listaArquivos = new ArrayList<byte[]>();
                }
                //listaArquivos.add(exameDAO.getArquivo(cod).get(i).getArquivo());
                ArquivoBean arquivoBean = new ArquivoBean();
                arquivoBean.setCodigoArquivo(exameDAO.getArquivo(cod).get(i).getCodigoArquivo());
                arquivoBean.setNome(exameDAO.getArquivo(cod).get(i).getNome());
                arquivoBean.setData(exameDAO.getArquivo(cod).get(i).getData());
                arquivoBean.setHora(exameDAO.getArquivo(cod).get(i).getHora());
                arquivoBean.setArquivo(exameDAO.getArquivo(cod).get(i).getArquivo());
                listaCerta.add(arquivoBean);
                model = new ArquivoTableModel(listaCerta);
                jTableArquivo.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableArquivo.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableArquivo.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableArquivo.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTableArquivo.getColumnModel().getColumn(3).setCellRenderer(esquerda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroFuncionarioNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getPaciente(int cod) {
        try {
            if (!pacienteDAO.getPaciente(cod).isEmpty()) {
                jTextCodPaciente.setText(pacienteDAO.getPaciente(cod).get(0).getCodigo() + "");
                jTextNomePaciente.setText(pacienteDAO.getPaciente(cod).get(0).getNome());

                //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            } else {
                JOptionPane.showMessageDialog(null, "O código " + cod + " não está cadastrado!", "Código não cadastrado", JOptionPane.WARNING_MESSAGE);
                this.jButtonEditar.setEnabled(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void getMedico(int cod) {
        try {
            if (!funcionarioDAO.getMedico(cod).isEmpty()) {
                jTextCodMedico.setText(funcionarioDAO.getMedico(cod).get(0).getCodigo() + "");
                jTextNomeMedico.setText(funcionarioDAO.getMedico(cod).get(0).getNome());

                //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            } else {
                JOptionPane.showMessageDialog(null, "O código " + cod + " não está cadastrado!", "Código não cadastrado", JOptionPane.WARNING_MESSAGE);
                this.jButtonEditar.setEnabled(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void getTipoExame(int cod) {
        try {
            if (!tipoExameDAO.getTipoExame(cod).isEmpty()) {
                jTextCodTipoExame.setText(tipoExameDAO.getTipoExame(cod).get(0).getCodigo() + "");
                jTextNomeTipoExame.setText(tipoExameDAO.getTipoExame(cod).get(0).getNome());
                jTextDescricaoTipoExame.setText(tipoExameDAO.getTipoExame(cod).get(0).getDescricao());
                //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            } else {
                JOptionPane.showMessageDialog(null, "O código " + cod + " não está cadastrado!", "Código não cadastrado", JOptionPane.WARNING_MESSAGE);
                this.jButtonEditar.setEnabled(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPalavraExame(int cod) {
        this.getPaciente(cod);
    }

    public void getPalavraExame2(int cod) {
        this.getMedico(cod);
    }

    public void getPalavraExame3(int cod) {
        this.getTipoExame(cod);
    }

    public byte[] getByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }

    public BufferedImage getBufferedImage(byte[] data) {
        InputStream in = new ByteArrayInputStream(data);
        BufferedImage bImageFromConvert = null;
        try {
            bImageFromConvert = ImageIO.read(in);
        } catch (IOException ex) {
            Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bImageFromConvert;
    }

    public String getDataAtualFormatada() {
        Calendar calendar = new GregorianCalendar();
        String dia = calendar.get(Calendar.DAY_OF_MONTH) + "";
        String mes = (calendar.get(Calendar.MONTH) + 1) + "";
        String ano = calendar.get(Calendar.YEAR) + "";
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            dia = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        if ((calendar.get(Calendar.MONTH) + 1) < 10) {
            mes = "0" + (calendar.get(Calendar.MONTH) + 1);
        }
        String x = dia + "/" + mes + "/" + ano;
        return x;
    }

    public String getHoraAtualFormatada() {
        Calendar calendar = new GregorianCalendar();
        String horas = calendar.get(Calendar.HOUR_OF_DAY) + "";
        String minutos = calendar.get(Calendar.MINUTE) + "";
        String segundos = calendar.get(Calendar.SECOND) + "";
        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
            horas = "0" + calendar.get(Calendar.HOUR_OF_DAY);
        }
        if (calendar.get(Calendar.MINUTE) < 10) {
            minutos = "0" + calendar.get(Calendar.MINUTE);
        }
        if (calendar.get(Calendar.SECOND) < 10) {
            segundos = "0" + calendar.get(Calendar.SECOND);
        }
        String y = horas + ":" + minutos + ":" + segundos;
        return y;
    }

    public void ativaBotaoAddArquivoEExcluirArquivo() {
        jButtonNovoArquivo.setEnabled(true);
        jButtonExcluirArquivo.setEnabled(true);
    }

    public void getArquivosByte(BufferedImage image, int numero) {
        /*if (listaArquivos == null) {
            listaArquivos = new ArrayList<byte[]>();
        }
        try {

            listaArquivos.add(getByteArray(image));
            ArquivoBean arquivoBean = new ArquivoBean();
            arquivoBean.setCodigo((numero + maiorNumeroArquivo) + "");
            arquivoBean.setNome(nomeArquivo);
            arquivoBean.setData(getDataAtualFormatada());
            arquivoBean.setHora(getHoraAtualFormatada());
            //arquivoBean.setArquivo(getByteArray(image));
            lista.add(arquivoBean);
            model = new ArquivoTableModel(lista);
            jTableArquivo.setModel(model);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTableArquivo.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTableArquivo.getColumnModel().getColumn(1).setCellRenderer(esquerda);
            jTableArquivo.getColumnModel().getColumn(2).setCellRenderer(esquerda);

         */
        String valorString = JOptionPane.showInputDialog("Nome do arquivo", "Arquivo " + (numero + maiorNumeroArquivo));
        if (valorString != null) {
            jButtonNovoArquivo.setEnabled(true);
            jButtonExcluirArquivo.setEnabled(true);
            try {
                ArquivoBean arquivoBean = new ArquivoBean();
                arquivoBean.setCodigoArquivo((numero + maiorNumeroArquivo));
                arquivoBean.setNome(valorString);
                arquivoBean.setData(getDataAtualFormatada());
                arquivoBean.setHora(getHoraAtualFormatada());
                arquivoBean.setArquivo(getByteArray(image));
                listaCerta.add(arquivoBean);
                model = new ArquivoTableModel(listaCerta);
                jTableArquivo.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableArquivo.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableArquivo.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableArquivo.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTableArquivo.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                maiorNumeroArquivo += numero;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo escaneado! <'" + valorString + "'>\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void removeArquivos(int i) {
        listaArquivos.remove(i);
        //lista.remove(i);

    }

    public void setNovoCodigo() {
        flag = false;
        exameBean.setTentativas(0); // Atualizo ou insiro um dado novo? Esse loko decide!
    }

    public void tabelaArquivo() {
        List lista = new ArrayList();
        jTableArquivo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //File file2 = new File("Exames\\" + exameDAO.getExame(cod).get(0).getCodPaciente() + "\\" + calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR) + "\\" + exameDAO.getExame(cod).get(0).getCodTipoExame() + "\\" + (i + 1) + ".jpg");
                    VisualizarExame ve = new VisualizarExame();
                    int codExame = 0;
                    if (jTextCodPaciente.getText().equals("")) {
                        jTextCodPaciente.setText("0");
                    }
                    if (jTextCodMedico.getText().equals("")) {
                        jTextCodMedico.setText("0");
                    }
                    ve.getArquivo(codExame, Integer.parseInt(jTextCodPaciente.getText()), Integer.parseInt(jTextCodMedico.getText()), getDataAtualFormatada() + " " + getHoraAtualFormatada(), listaCerta.get(jTableArquivo.getSelectedRow()).getArquivo());
                    ve.setVisible(true);

                }
            }
        });
    }

    public void tabelaPesquisa() {
        List lista = new ArrayList();
        jTablePesquisa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    getExame(Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + ""));
                    jButtonEditar.setEnabled(true);
                    jButtonExcluir.setEnabled(true);
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

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jButtonPesquisar2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTextPesquisa = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablePesquisa = new javax.swing.JTable();
        jButtonAdicionar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButtonNovo = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonGravar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButtonGravar2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTextCodPaciente = new javax.swing.JTextField();
        LabelValorCus112 = new javax.swing.JLabel();
        LabelValorCus113 = new javax.swing.JLabel();
        jTextDescricaoTipoExame = new javax.swing.JTextField();
        LabelValorCus48 = new javax.swing.JLabel();
        jDateDtRequisicao = new com.toedter.calendar.JDateChooser();
        jTextCodMedico = new javax.swing.JTextField();
        LabelValorCus115 = new javax.swing.JLabel();
        jButtonInserirPaciente = new javax.swing.JButton();
        jTextNomePaciente = new javax.swing.JTextField();
        jButtonInserirMedico = new javax.swing.JButton();
        jTextNomeMedico = new javax.swing.JTextField();
        jTextCodTipoExame = new javax.swing.JTextField();
        jButtonInserirTipoExame = new javax.swing.JButton();
        jTextNomeTipoExame = new javax.swing.JTextField();
        jDateDtLaudo = new com.toedter.calendar.JDateChooser();
        LabelValorCus49 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextLaudo = new javax.swing.JTextArea();
        LabelValorCus135 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButtonExcluirArquivo = new javax.swing.JButton();
        jButtonNovoArquivo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableArquivo = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Altessmann Sistemas - Exames / Laudos");

        jTabbedPane.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButtonPesquisar2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonPesquisar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PesquisarCli.png"))); // NOI18N
        jButtonPesquisar2.setText("Pesquisar ");
        jButtonPesquisar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPesquisar2ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/abrircli.png"))); // NOI18N
        jButton5.setText("Listar ");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextPesquisa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextPesquisa.setForeground(new java.awt.Color(153, 153, 153));
        jTextPesquisa.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextPesquisa.setPreferredSize(new java.awt.Dimension(500, 25));
        jTextPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPesquisaActionPerformed(evt);
            }
        });

        jTablePesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome paciente", "Nome exame", "Descrição", "Data requisição", "Data laudo", "Laudo gerado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePesquisaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTablePesquisa);

        jButtonAdicionar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add.png"))); // NOI18N
        jButtonAdicionar.setText("Adicionar");
        jButtonAdicionar.setEnabled(false);
        jButtonAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonPesquisar2)
                        .addGap(3, 3, 3)
                        .addComponent(jButton5)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonAdicionar)))
                .addGap(5, 5, 5))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonPesquisar2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        jTabbedPane.addTab("  Pesquisar Exames  ", jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Laudo Detalhes  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

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
        jButtonEditar.setDoubleBuffered(true);
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
        jButtonExcluir.setDoubleBuffered(true);
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
        jButtonGravar.setDoubleBuffered(true);
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
        jButtonCancelar.setDoubleBuffered(true);
        jButtonCancelar.setEnabled(false);
        jButtonCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButtonGravar2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonGravar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/print.png"))); // NOI18N
        jButtonGravar2.setText("Imprimir Laudo");
        jButtonGravar2.setDoubleBuffered(true);
        jButtonGravar2.setEnabled(false);
        jButtonGravar2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonGravar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGravar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jButtonGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonGravar2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonGravar2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Exame Detalhes  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jTextCodPaciente.setEditable(false);
        jTextCodPaciente.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextCodPaciente.setForeground(new java.awt.Color(153, 153, 153));
        jTextCodPaciente.setDoubleBuffered(true);
        jTextCodPaciente.setEnabled(false);
        jTextCodPaciente.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextCodPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCodPacienteActionPerformed(evt);
            }
        });

        LabelValorCus112.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus112.setText("Paciente:");

        LabelValorCus113.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus113.setText("Exame:");

        jTextDescricaoTipoExame.setEditable(false);
        jTextDescricaoTipoExame.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextDescricaoTipoExame.setForeground(new java.awt.Color(153, 153, 153));
        jTextDescricaoTipoExame.setDoubleBuffered(true);
        jTextDescricaoTipoExame.setEnabled(false);
        jTextDescricaoTipoExame.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextDescricaoTipoExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDescricaoTipoExameActionPerformed(evt);
            }
        });

        LabelValorCus48.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus48.setText("Data Requisição:");

        jDateDtRequisicao.setEnabled(false);

        jTextCodMedico.setEditable(false);
        jTextCodMedico.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextCodMedico.setForeground(new java.awt.Color(153, 153, 153));
        jTextCodMedico.setDoubleBuffered(true);
        jTextCodMedico.setEnabled(false);
        jTextCodMedico.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextCodMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCodMedicoActionPerformed(evt);
            }
        });

        LabelValorCus115.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus115.setText("Medico:");

        jButtonInserirPaciente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonInserirPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add2.png"))); // NOI18N
        jButtonInserirPaciente.setDoubleBuffered(true);
        jButtonInserirPaciente.setEnabled(false);
        jButtonInserirPaciente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonInserirPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserirPacienteActionPerformed(evt);
            }
        });

        jTextNomePaciente.setEditable(false);
        jTextNomePaciente.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextNomePaciente.setForeground(new java.awt.Color(153, 153, 153));
        jTextNomePaciente.setDoubleBuffered(true);
        jTextNomePaciente.setEnabled(false);
        jTextNomePaciente.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextNomePaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNomePacienteActionPerformed(evt);
            }
        });

        jButtonInserirMedico.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonInserirMedico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add2.png"))); // NOI18N
        jButtonInserirMedico.setDoubleBuffered(true);
        jButtonInserirMedico.setEnabled(false);
        jButtonInserirMedico.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonInserirMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserirMedicoActionPerformed(evt);
            }
        });

        jTextNomeMedico.setEditable(false);
        jTextNomeMedico.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextNomeMedico.setForeground(new java.awt.Color(153, 153, 153));
        jTextNomeMedico.setDoubleBuffered(true);
        jTextNomeMedico.setEnabled(false);
        jTextNomeMedico.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextNomeMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNomeMedicoActionPerformed(evt);
            }
        });

        jTextCodTipoExame.setEditable(false);
        jTextCodTipoExame.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextCodTipoExame.setForeground(new java.awt.Color(153, 153, 153));
        jTextCodTipoExame.setDoubleBuffered(true);
        jTextCodTipoExame.setEnabled(false);
        jTextCodTipoExame.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextCodTipoExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCodTipoExameActionPerformed(evt);
            }
        });

        jButtonInserirTipoExame.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonInserirTipoExame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add2.png"))); // NOI18N
        jButtonInserirTipoExame.setDoubleBuffered(true);
        jButtonInserirTipoExame.setEnabled(false);
        jButtonInserirTipoExame.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonInserirTipoExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserirTipoExameActionPerformed(evt);
            }
        });

        jTextNomeTipoExame.setEditable(false);
        jTextNomeTipoExame.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextNomeTipoExame.setForeground(new java.awt.Color(153, 153, 153));
        jTextNomeTipoExame.setDoubleBuffered(true);
        jTextNomeTipoExame.setEnabled(false);
        jTextNomeTipoExame.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextNomeTipoExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNomeTipoExameActionPerformed(evt);
            }
        });

        jDateDtLaudo.setEnabled(false);

        LabelValorCus49.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus49.setText("Data Laudo:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextCodPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(jButtonInserirPaciente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextNomePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(LabelValorCus112)
                            .addComponent(LabelValorCus113)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextCodTipoExame, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(jButtonInserirTipoExame)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextNomeTipoExame, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus115)
                                .addGap(340, 340, 340))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextDescricaoTipoExame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jTextCodMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)
                                        .addComponent(jButtonInserirMedico)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextNomeMedico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus48)
                            .addComponent(jDateDtRequisicao, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus49)
                            .addComponent(jDateDtLaudo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 681, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextCodPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextCodMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelValorCus112)
                            .addComponent(LabelValorCus115))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonInserirPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextNomePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButtonInserirMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextNomeMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextDescricaoTipoExame, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LabelValorCus113)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextCodTipoExame, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButtonInserirTipoExame, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextNomeTipoExame, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelValorCus48)
                    .addComponent(LabelValorCus49))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateDtLaudo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateDtRequisicao, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Laudo Detalhes  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jTextLaudo.setBackground(java.awt.SystemColor.menu);
        jTextLaudo.setColumns(20);
        jTextLaudo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jTextLaudo.setRows(3);
        jTextLaudo.setDoubleBuffered(true);
        jTextLaudo.setEnabled(false);
        jScrollPane2.setViewportView(jTextLaudo);

        LabelValorCus135.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus135.setText("Laudo Médico Descrição:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(LabelValorCus135)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addGap(5, 5, 5))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(LabelValorCus135)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Arquivos  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jButtonExcluirArquivo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonExcluirArquivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/close24x24.png"))); // NOI18N
        jButtonExcluirArquivo.setDoubleBuffered(true);
        jButtonExcluirArquivo.setEnabled(false);
        jButtonExcluirArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirArquivoActionPerformed(evt);
            }
        });

        jButtonNovoArquivo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonNovoArquivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add2.png"))); // NOI18N
        jButtonNovoArquivo.setDoubleBuffered(true);
        jButtonNovoArquivo.setEnabled(false);
        jButtonNovoArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoArquivoActionPerformed(evt);
            }
        });

        jTableArquivo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome do Documento", "Data", "Hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableArquivo.setDoubleBuffered(true);
        jTableArquivo.setEnabled(false);
        jTableArquivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableArquivoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableArquivo);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonNovoArquivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonExcluirArquivo))
                .addGap(5, 5, 5))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jButtonNovoArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButtonExcluirArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jTabbedPane.addTab("  Cadastro Exame / Laudos  ", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPesquisar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisar2ActionPerformed
        int codigo = 0;
        List lista = new ArrayList();
        try {
            codigo = Integer.parseInt(jTextPesquisa.getText());
            if (!exameDAO.getExame(codigo).isEmpty()) {
                boolean laudoGerado = false;
                if (!exameDAO.getExame(codigo).get(0).getDtLaudo().equals("")) {
                    laudoGerado = true;
                }
                exameBean.setCodigo(codigo);
                exameBean.setNomePaciente(exameDAO.getExame(codigo).get(0).getNomePaciente());
                exameBean.setNomeTipoExame(exameDAO.getExame(codigo).get(0).getNomeTipoExame());
                exameBean.setDescricaoTipoExame(exameDAO.getExame(codigo).get(0).getDescricaoTipoExame());
                exameBean.setDtRequisicao(exameDAO.getExame(codigo).get(0).getDtRequisicao());
                exameBean.setDtLaudo(exameDAO.getExame(codigo).get(0).getDtLaudo());
                exameBean.setLaudoGerado(laudoGerado);
                lista.add(exameBean);
                model2 = new ExameTableModel(lista);
                jTablePesquisa.setModel(model2);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(5).setCellRenderer(esquerda);

            } else {
                JOptionPane.showMessageDialog(null, "Não existem exames com o código '" + codigo + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            try {
                if (!exameDAO.getExame(jTextPesquisa.getText()).isEmpty()) {
                    for (int i = 0; i < exameDAO.getExame(jTextPesquisa.getText()).size(); i++) {
                        boolean laudoGerado = false;
                        if (exameDAO.getExame(jTextPesquisa.getText()).get(i).getLaudo().equals("")) {
                            laudoGerado = true;
                        }
                        ExameBean p = new ExameBean();
                        p.setCodigo(exameDAO.getExame(jTextPesquisa.getText()).get(i).getCodigo());
                        p.setNomePaciente(exameDAO.getExame(jTextPesquisa.getText()).get(i).getNomePaciente());
                        p.setNomeTipoExame(exameDAO.getExame(jTextPesquisa.getText()).get(i).getNomeTipoExame());
                        p.setDescricaoTipoExame(exameDAO.getExame(jTextPesquisa.getText()).get(i).getDescricaoTipoExame());
                        p.setDtRequisicao(exameDAO.getExame(jTextPesquisa.getText()).get(i).getDtRequisicao());
                        p.setDtLaudo(exameDAO.getExame(jTextPesquisa.getText()).get(i).getDtLaudo());
                        p.setLaudoGerado(laudoGerado);
                        lista.add(p);
                    }
                    model2 = new ExameTableModel(lista);
                    jTablePesquisa.setModel(model2);
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(5).setCellRenderer(esquerda);

                } else {
                    JOptionPane.showMessageDialog(null, "Não existem funcionários com o nome '" + jTextPesquisa.getText() + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jButtonPesquisar2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int codigo = 0;
        List lista = new ArrayList();
        try {
            codigo = Integer.parseInt(jTextPesquisa.getText());
            if (!exameDAO.getExame(codigo).isEmpty()) {
                boolean laudoGerado = false;
                if (!exameDAO.getExame(codigo).get(0).getDtLaudo().equals("")) {
                    laudoGerado = true;
                }
                exameBean.setCodigo(codigo);
                exameBean.setNomePaciente(exameDAO.getExame(codigo).get(0).getNomePaciente());
                exameBean.setNomeTipoExame(exameDAO.getExame(codigo).get(0).getNomeTipoExame());
                exameBean.setDescricaoTipoExame(exameDAO.getExame(codigo).get(0).getDescricaoTipoExame());
                exameBean.setDtRequisicao(exameDAO.getExame(codigo).get(0).getDtRequisicao());
                exameBean.setDtLaudo(exameDAO.getExame(codigo).get(0).getDtLaudo());
                exameBean.setLaudoGerado(laudoGerado);
                lista.add(exameBean);
                model2 = new ExameTableModel(lista);
                jTablePesquisa.setModel(model2);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(5).setCellRenderer(esquerda);

            } else {
                JOptionPane.showMessageDialog(null, "Não existem funcionários com o código '" + codigo + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            try {
                if (!exameDAO.getExame("").isEmpty()) {
                    for (int i = 0; i < exameDAO.getExame("").size(); i++) {
                        boolean laudoGerado = false;
                        if (!exameDAO.getExame("").get(i).getLaudo().equals("")) {
                            laudoGerado = true;
                        }
                        ExameBean p = new ExameBean();
                        p.setCodigo(exameDAO.getExame("").get(i).getCodigo());
                        p.setNomePaciente(exameDAO.getExame("").get(i).getNomePaciente());
                        p.setNomeTipoExame(exameDAO.getExame("").get(i).getNomeTipoExame());
                        p.setDescricaoTipoExame(exameDAO.getExame("").get(i).getDescricaoTipoExame());
                        p.setDtRequisicao(exameDAO.getExame("").get(i).getDtRequisicao());
                        p.setDtLaudo(exameDAO.getExame("").get(i).getDtLaudo());
                        p.setLaudoGerado(laudoGerado);
                        lista.add(p);
                    }
                    model2 = new ExameTableModel(lista);
                    jTablePesquisa.setModel(model2);
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(5).setCellRenderer(esquerda);

                } else {
                    JOptionPane.showMessageDialog(null, "Não existem funcionários com o nome '" + jTextPesquisa.getText() + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPesquisaActionPerformed
        int codigo = 0;
        List lista = new ArrayList();
        try {
            codigo = Integer.parseInt(jTextPesquisa.getText());
            if (!exameDAO.getExame(codigo).isEmpty()) {
                boolean laudoGerado = false;
                if (!exameDAO.getExame(codigo).get(0).getDtLaudo().equals("")) {
                    laudoGerado = true;
                }
                exameBean.setCodigo(codigo);
                exameBean.setNomePaciente(exameDAO.getExame(codigo).get(0).getNomePaciente());
                exameBean.setNomeTipoExame(exameDAO.getExame(codigo).get(0).getNomeTipoExame());
                exameBean.setDescricaoTipoExame(exameDAO.getExame(codigo).get(0).getDescricaoTipoExame());
                exameBean.setDtRequisicao(exameDAO.getExame(codigo).get(0).getDtRequisicao());
                exameBean.setDtLaudo(exameDAO.getExame(codigo).get(0).getDtLaudo());
                exameBean.setLaudoGerado(laudoGerado);
                lista.add(exameBean);
                model2 = new ExameTableModel(lista);
                jTablePesquisa.setModel(model2);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(5).setCellRenderer(esquerda);

            } else {
                JOptionPane.showMessageDialog(null, "Não existem funcionários com o código '" + codigo + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            try {
                if (!exameDAO.getExame(jTextPesquisa.getText()).isEmpty()) {
                    for (int i = 0; i < exameDAO.getExame(jTextPesquisa.getText()).size(); i++) {
                        boolean laudoGerado = false;
                        if (exameDAO.getExame(jTextPesquisa.getText()).get(i).getLaudo().equals("")) {
                            laudoGerado = true;
                        }
                        ExameBean p = new ExameBean();
                        p.setCodigo(exameDAO.getExame(jTextPesquisa.getText()).get(i).getCodigo());
                        p.setNomePaciente(exameDAO.getExame(jTextPesquisa.getText()).get(i).getNomePaciente());
                        p.setNomeTipoExame(exameDAO.getExame(jTextPesquisa.getText()).get(i).getNomeTipoExame());
                        p.setDescricaoTipoExame(exameDAO.getExame(jTextPesquisa.getText()).get(i).getDescricaoTipoExame());
                        p.setDtRequisicao(exameDAO.getExame(jTextPesquisa.getText()).get(i).getDtRequisicao());
                        p.setDtLaudo(exameDAO.getExame(jTextPesquisa.getText()).get(i).getDtLaudo());
                        p.setLaudoGerado(laudoGerado);
                        lista.add(p);
                    }
                    model2 = new ExameTableModel(lista);
                    jTablePesquisa.setModel(model2);
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(5).setCellRenderer(esquerda);

                } else {
                    JOptionPane.showMessageDialog(null, "Não existem funcionários com o nome '" + jTextPesquisa.getText() + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jTextPesquisaActionPerformed

    private void jButtonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoActionPerformed
        this.jButtonNovo.setEnabled(false);
        this.ativaCampo();
        this.limpaCampo();
        this.jButtonGravar.setEnabled(true);
        this.jButtonCancelar.setEnabled(true);
        this.jTabbedPane.setEnabledAt(0, false);
        this.setNovoCodigo();
        this.jTableArquivo.setEnabled(true);
    }//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        this.ativaCampo();
        this.jButtonGravar.setEnabled(true);
        this.jButtonExcluir.setEnabled(true);
        this.jButtonCancelar.setEnabled(true);
        this.jTableArquivo.setEnabled(true);
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        try {
            int codigo = Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + "");
            if (JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir esse exame ?\\nEsses dados serão perdidos!", "Excluir",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(exameDAO.getExame(codigo).get(0).getDtRequisicao());
                File file = new File("Exames\\" + exameDAO.getExame(codigo).get(0).getCodPaciente() + "\\" + calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR) + "\\" + exameDAO.getExame(codigo).get(0).getCodTipoExame());
                for (int i = 0; i < file.list().length; i++) {
                    File file2 = new File("Exames\\" + exameDAO.getExame(codigo).get(0).getCodPaciente() + "\\" + calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR) + "\\" + exameDAO.getExame(codigo).get(0).getCodTipoExame() + "\\" + (i + 1) + ".jpg");
                    file2.delete();
                }
                exameDAO.deletaExame(codigo);
                this.desativaCampo();
                this.limpaCampo();
                this.jButtonGravar.setEnabled(false);
                this.jButtonNovo.setEnabled(true);
                this.jButtonEditar.setEnabled(false);
                this.jButtonExcluir.setEnabled(false);
                this.jButtonCancelar.setEnabled(false);
                this.jTabbedPane.setEnabledAt(0, true);
                this.jTableArquivo.setEnabled(false);
                //tabela();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada na tabela!\n" + e, "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGravarActionPerformed
        int codExame;
        if (jTextCodTipoExame.getText().equals("") || jTextCodPaciente.getText().equals("") || jTextCodMedico.getText().equals("") || jDateDtLaudo.getDate() == null || jDateDtRequisicao.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Dados incompletos!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 0; i < jTableArquivo.getRowCount(); i++) {
                if (Integer.parseInt(jTableArquivo.getValueAt(i, 0) + "") > maiorNumeroArquivo) {
                    maiorNumeroArquivo = Integer.parseInt(jTableArquivo.getValueAt(i, 0) + "");
                }
            }
            if (!flag) {
                try {
                    codExame = exameDAO.setNovoCodigo();
                    for (int i = 0; i < jTableArquivo.getRowCount(); i++) {
                        exameDAO.setArquivo(listaCerta.get(i).getCodigoArquivo(), listaCerta.get(i).getNome(), codExame, listaCerta.get(i).getArquivo(), listaCerta.get(i).getData(), listaCerta.get(i).getHora());
                        //exameDAO.setArquivo(Integer.parseInt(jTableArquivo.getValueAt(i, 0) + ""), (String) jTableArquivo.getValueAt(i, 1), codExame, listaArquivos.get(i), (String) jTableArquivo.getValueAt(i, 2), (String) jTableArquivo.getValueAt(i, 3));
                    }
                    exameDAO.setExame(codExame, Integer.parseInt(jTextCodMedico.getText()), Integer.parseInt(jTextCodPaciente.getText()), Integer.parseInt(jTextCodTipoExame.getText()), jTextLaudo.getText(), jDateDtLaudo.getDate(), jDateDtRequisicao.getDate(), maiorNumeroArquivo, exameBean.getTentativas());
                    this.desativaCampo();
                    this.limpaCampo();
                    this.jButtonGravar.setEnabled(false);
                    this.jButtonNovo.setEnabled(true);
                    this.jButtonCancelar.setEnabled(false);
                    this.jButtonExcluir.setEnabled(false);
                    this.jButtonEditar.setEnabled(false);
                    this.jTablePesquisa.setEnabled(true);
                    this.jTabbedPane.setEnabledAt(0, true);
                    this.jTableArquivo.setEnabled(false);
                } catch (SQLException ex) {
                    Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                try {
                    codExame = Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + "");
                    exameDAO.deletaArquivos(codExame);
                    for (int i = 0; i < jTableArquivo.getRowCount(); i++) {
                        exameDAO.setArquivo(listaCerta.get(i).getCodigoArquivo(), listaCerta.get(i).getNome(), codExame, listaCerta.get(i).getArquivo(), listaCerta.get(i).getData(), listaCerta.get(i).getHora());
                        //exameDAO.setArquivo(Integer.parseInt(jTableArquivo.getValueAt(i, 0) + ""), (String) jTableArquivo.getValueAt(i, 1), codExame, listaArquivos.get(i), (String) jTableArquivo.getValueAt(i, 2), (String) jTableArquivo.getValueAt(i, 3));
                    }
                    exameDAO.atualizaExame(codExame, Integer.parseInt(jTextCodMedico.getText()), Integer.parseInt(jTextCodPaciente.getText()), Integer.parseInt(jTextCodTipoExame.getText()), jTextLaudo.getText(), jDateDtLaudo.getDate(), maiorNumeroArquivo, exameBean.getTentativas());
                    this.desativaCampo();
                    this.limpaCampo();
                    this.jButtonGravar.setEnabled(false);
                    this.jButtonNovo.setEnabled(true);
                    this.jButtonCancelar.setEnabled(false);
                    this.jButtonExcluir.setEnabled(false);
                    this.jButtonEditar.setEnabled(false);
                    this.jTabbedPane.setSelectedIndex(0);
                    this.jTabbedPane.setEnabledAt(0, true);
                    this.jTableArquivo.setEnabled(false);
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
        this.jTabbedPane.setEnabledAt(0, true);
        this.jTableArquivo.setEnabled(false);

    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonGravar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGravar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonGravar2ActionPerformed

    private void jTextCodPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCodPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCodPacienteActionPerformed

    private void jTextDescricaoTipoExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDescricaoTipoExameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDescricaoTipoExameActionPerformed

    private void jTextCodMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCodMedicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCodMedicoActionPerformed

    private void jButtonInserirPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInserirPacienteActionPerformed
        String nome = JOptionPane.showInputDialog(null, "Informe o nome ou código para pesquisa:", "Inserir paciente", 3);
        if (nome != null) {
            try {
                int codigo = Integer.parseInt(nome);
                this.getPaciente(codigo);
            } catch (Exception e) {
                CadastroPacienteNew cadastroPacienteNew = new CadastroPacienteNew(0);
                cadastroPacienteNew.setLocationRelativeTo(null);
                cadastroPacienteNew.setVisible(true);
                cadastroPacienteNew.setResizable(false);
                cadastroPacienteNew.enviaPalavra(null, this, nome, 2);
                cadastroPacienteNew.jTabbedPaneCadastro.setEnabledAt(0, true);
                cadastroPacienteNew.jTabbedPaneCadastro.setEnabledAt(1, false);
                cadastroPacienteNew.jTabbedPaneCadastro.setEnabledAt(2, false);
                cadastroPacienteNew.jButtonAdicionar.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jButtonInserirPacienteActionPerformed

    private void jTextNomePacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNomePacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNomePacienteActionPerformed

    private void jButtonInserirMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInserirMedicoActionPerformed
        String nome = JOptionPane.showInputDialog(null, "Informe o nome ou código para pesquisa:", "Inserir médico", 3);
        if (nome != null) {
            try {
                int codigo = Integer.parseInt(nome);
                this.getMedico(codigo);
            } catch (Exception e) {
                CadastroFuncionarioNew cadastroFuncionarioNew = new CadastroFuncionarioNew("");
                cadastroFuncionarioNew.setLocationRelativeTo(null);
                cadastroFuncionarioNew.setVisible(true);
                cadastroFuncionarioNew.setResizable(false);
                cadastroFuncionarioNew.enviaPalavra(this, nome, 2);
                cadastroFuncionarioNew.jTabbedPane1.setEnabledAt(0, true);
                cadastroFuncionarioNew.jTabbedPane1.setEnabledAt(1, false);
                cadastroFuncionarioNew.jButtonAdicionar.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jButtonInserirMedicoActionPerformed

    private void jTextNomeMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNomeMedicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNomeMedicoActionPerformed

    private void jTextCodTipoExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCodTipoExameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCodTipoExameActionPerformed

    private void jButtonInserirTipoExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInserirTipoExameActionPerformed
        String nome = JOptionPane.showInputDialog(null, "Informe o nome ou código para pesquisa:", "Inserir exame", 3);
        if (nome != null) {
            try {
                int codigo = Integer.parseInt(nome);
                this.getTipoExame(codigo);
            } catch (Exception e) {
                CadastroTipoExameNew cadastroTipoExameNew = new CadastroTipoExameNew();
                cadastroTipoExameNew.setLocationRelativeTo(null);
                cadastroTipoExameNew.setVisible(true);
                cadastroTipoExameNew.setResizable(false);
                cadastroTipoExameNew.enviaPalavra(null, this, nome, 1, 1);
                cadastroTipoExameNew.jTabbedPane1.setEnabledAt(0, true);
                cadastroTipoExameNew.jTabbedPane1.setEnabledAt(1, false);
                cadastroTipoExameNew.jButtonAdicionar.setEnabled(false);

            }
        }
    }//GEN-LAST:event_jButtonInserirTipoExameActionPerformed

    private void jTextNomeTipoExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNomeTipoExameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNomeTipoExameActionPerformed

    private void jButtonExcluirArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirArquivoActionPerformed
        try {
            if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover '" + model.getValueAt(jTableArquivo.getSelectedRow(), 1) + "'?", "Remover arquivo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int linha = jTableArquivo.getSelectedRow();
                model.excluir(linha);
                listaCerta.remove(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonExcluirArquivoActionPerformed

    private void jButtonNovoArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoArquivoActionPerformed

        try {
            ScannerExamesNew escaner = new ScannerExamesNew();
            escaner.getInstanceOfExame(this);
            escaner.setVisible(true);
            jButtonNovoArquivo.setEnabled(false);
            jButtonExcluirArquivo.setEnabled(false);
        } catch (IOException ex) {
            Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
        }

//        if (jDateDtRequisicao.getDate() != null) { //teste se a data e reqwuisicao nao ´pe nula
//            if ((!jTextCodPaciente.getText().equals("")) && (!jTextCodTipoExame.getText().equals(""))) {
//                Calendar calendar = new GregorianCalendar();
//                calendar.setTime(jDateDtRequisicao.getDate());
//                boolean configurado = false;
//                try {
//                    //BufferedWriter writer = new BufferedWriter(new FileWriter("exame.ini"));
//                    BufferedReader reader = new BufferedReader(new FileReader("exame.ini"));
//                    configurado = reader.readLine().charAt(6) != '0';
//                } catch (IOException ex) {
//                    System.out.println("erro no exame.ini");
//                    Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//                File file = new File("Exames\\" + jTextCodPaciente.getText());
//                if (!file.exists()) {                   //se o diretorio nao existe ele cria
//                    file.mkdir();
//                }
//
//                File file2 = new File("Exames\\" + jTextCodPaciente.getText() + "\\" + calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR));
//                if (!file2.exists()) {
//                    file2.mkdir();
//                }
//
//                File file3 = new File("Exames\\" + jTextCodPaciente.getText() + "\\" + calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR) + "\\" + jTextCodTipoExame.getText());
//                if (!file3.exists()) {
//                    file3.mkdir();
//                }
//
//                new Thread() {
//                    @Override
//                    public void run() {
//
//                        File file4 = new File("Temporario\\Image.jpg");
//                        try {
//                            processo = new ProcessBuilder("C:\\Windows\\System32\\wiaacmgr").start();
//                        } catch (IOException ex) {
//                            Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        boolean success = file4.delete();
//                        while (!file4.exists()) {
//                            //aguarda o arquivo ser digitalizado;
//                        }
//                        if (JOptionPane.showConfirmDialog(null, "Copiar arquivos?", "Cópia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) { // pergunta pois como é processamento paralelo, ele não espera terminar  de escanear para copiar o arquivo,
//                            //então a solução é um JOptionPane para esperar q ele consiga escanear tranquilamente e depois copiar o arquivo perfeitamente
//                            try {
//
//                                //copia arquivo para a pasta
//                                copyFile(file4, new File("Exames\\" + jTextCodPaciente.getText() + "\\" + calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR) + "\\" + jTextCodTipoExame.getText() + "//" + (file3.list().length + 1) + ".jpg"));
//
//                            } catch (IOException ex) {
//                                Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                        List lista = new ArrayList();
//                        for (int i = 0; i < file3.list().length; i++) {
//                            Path path = file2.toPath(); // converte file em path, na real ele pega o endereço do arquivo;
//                            BasicFileAttributes attributes = null;
//                            try {
//                                attributes = Files.readAttributes(path, BasicFileAttributes.class);
//                            } catch (IOException ex) {
//                                JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR_MESSAGE);
//                                Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            FileTime creationTime = attributes.creationTime(); // pega os atributos do file;
//                            Calendar atime = new GregorianCalendar();
//                            atime.setTimeInMillis(creationTime.toMillis()); //transforma FileTime em Calendar, como FileTime pega milisegundos, na conversão ele ajeita para segundos;
//                            ArquivoBean arquivoBean = new ArquivoBean();
//                            arquivoBean.setCodigo((i + 1) + "");
//                            arquivoBean.setLocal("Exames/" + jTextCodPaciente.getText() + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR) + "/" + file3.list()[i] + ".jpg");
//                            arquivoBean.setData(calendar.getTime());
//                            lista.add(arquivoBean);
//                        }
//
//                        model = new ArquivoTableModel(lista);
//                        jTableArquivo.setModel(model);
//                        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
//                        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
//                        jTableArquivo.getColumnModel().getColumn(0).setCellRenderer(esquerda);
//                        jTableArquivo.getColumnModel().getColumn(1).setCellRenderer(esquerda);
//                        jTableArquivo.getColumnModel().getColumn(2).setCellRenderer(esquerda);
//                        this.interrupt();
//
//                    }
//                }.start();
////JOptionPane.showMessageDialog(null, "Não foi possível abrir o aplicativo de digitalização!\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "Paciente ou tipo de exame nulo!", "Erro", JOptionPane.ERROR_MESSAGE);
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Data de requisição não pode ser nula!", "Erro", JOptionPane.ERROR_MESSAGE);
//        }
    }//GEN-LAST:event_jButtonNovoArquivoActionPerformed

    private void jButtonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarActionPerformed
        /*        try {
            examesNew.getPalavraExame2(Integer.parseInt(jTableFuncionario.getValueAt(jTableFuncionario.getSelectedRow(), 0) + ""));
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!", "Erro", JOptionPane.WARNING_MESSAGE);
        }*/
    }//GEN-LAST:event_jButtonAdicionarActionPerformed

    private void jTablePesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePesquisaMouseClicked
        jTabbedPane.grabFocus();
        jTabbedPane.setSelectedIndex(1);
        jTabbedPane.setEnabledAt(0, false);
        jButtonNovo.setEnabled(false);
        jButtonCancelar.setEnabled(true);
        jButtonExcluir.setEnabled(false);
    }//GEN-LAST:event_jTablePesquisaMouseClicked

    private void jTableArquivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableArquivoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableArquivoMouseClicked

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
            java.util.logging.Logger.getLogger(ExamesNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExamesNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExamesNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExamesNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExamesNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelValorCus112;
    private javax.swing.JLabel LabelValorCus113;
    private javax.swing.JLabel LabelValorCus115;
    private javax.swing.JLabel LabelValorCus135;
    private javax.swing.JLabel LabelValorCus48;
    private javax.swing.JLabel LabelValorCus49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonAdicionar;
    public javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonExcluirArquivo;
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JButton jButtonGravar2;
    private javax.swing.JButton jButtonInserirMedico;
    private javax.swing.JButton jButtonInserirPaciente;
    private javax.swing.JButton jButtonInserirTipoExame;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonNovoArquivo;
    private javax.swing.JButton jButtonPesquisar2;
    private com.toedter.calendar.JDateChooser jDateDtLaudo;
    private com.toedter.calendar.JDateChooser jDateDtRequisicao;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableArquivo;
    private javax.swing.JTable jTablePesquisa;
    private javax.swing.JTextField jTextCodMedico;
    private javax.swing.JTextField jTextCodPaciente;
    private javax.swing.JTextField jTextCodTipoExame;
    private javax.swing.JTextField jTextDescricaoTipoExame;
    private javax.swing.JTextArea jTextLaudo;
    private javax.swing.JTextField jTextNomeMedico;
    private javax.swing.JTextField jTextNomePaciente;
    private javax.swing.JTextField jTextNomeTipoExame;
    private javax.swing.JTextField jTextPesquisa;
    // End of variables declaration//GEN-END:variables
}
