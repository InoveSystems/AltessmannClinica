/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.NewTelas;

import com.altessmann.AbstractTableModel.ConsultaTableModel;
import com.altessmann.AbstractTableModel.TipoExameTableModel;
import com.altessmann.Bean.ConsultaBean;
import com.altessmann.Bean.TipoExameBean;
import com.altessmann.ConnecionFactory.ConnectionFactory;
import com.altessmann.DAO.ConsultaDAO;
import com.altessmann.DAO.FuncionarioDAO;
import com.altessmann.DAO.TipoExameDAO;
import com.altessmann.Impressao.Imprimir;
import com.altessmann.Impressao.PdfCriar;
import com.altessmann.Tools.ImprimeFicha;
import com.altessmann.Tools.RandomValidator;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AbstractDocument;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Ritiele Aldeburg
 */
public class CadastroConsultaNew extends javax.swing.JFrame {

    /**
     * Creates new form CadastroConsultaNew
     *
     */
    private final int user = 0;
    private final int root = 1;
    private ConsultaDAO consultaDAO;
    private ConsultaBean consultaBean = new ConsultaBean();
    private FuncionarioDAO funcionarioDAO;
    private TipoExameDAO tipoExameDAO = new TipoExameDAO();
    private String caminhoFoto = null;
    TipoExameBean tipoExameBean = null;
    private TipoExameTableModel model;
    private ConsultaTableModel modelConsulta;
    private int funcao;
    private List listaTipoExame = new ArrayList();
    private boolean flag = false;
    private boolean flag2 = false;
    private boolean editar = false;
    private boolean revisao = false;
    private CadastroTipoExameNew cadastroTipoExameNew;
    private double adicionalExames = 0;
    private PdfCriar pdf = new PdfCriar();
    private Connection conexao;
    private int selecionado = 0;
    private JasperViewer jv;

    public CadastroConsultaNew(int funcao) {

        try {
            this.consultaDAO = new ConsultaDAO();
            this.funcionarioDAO = new FuncionarioDAO();
            setExtendedState(MAXIMIZED_BOTH);
            initComponents();
            this.conexao = ConnectionFactory.openConnection();
            this.funcao = funcao;
            this.getMedico();
            this.desativaCampo();
            this.getTipoConsulta();
            this.getConsultasDia();
            this.tabela();
            //eventoAoDigitarJTextValorDesconto();
            eventoAoDigitarJTextValorConsulta();
            listenerJTextPeso();
            listenerJTextPressao();
            listenerJTextAltura();
            //((AbstractDocument) jTextValorDesconto.getDocument()).setDocumentFilter(new RandomValidator(0, true, false, false, false, ',', '-'));
            ((AbstractDocument) jTextValorConsulta.getDocument()).setDocumentFilter(new RandomValidator(0, true, false, false, false, ',', '-'));
            ((AbstractDocument) jTextPeso.getDocument()).setDocumentFilter(new RandomValidator(0, true, false, false, false, ',', '-'));
            ((AbstractDocument) jTextPressao.getDocument()).setDocumentFilter(new RandomValidator(0, true, false, false, false, ',', '-'));
            ((AbstractDocument) jTextAltura.getDocument()).setDocumentFilter(new RandomValidator(0, true, false, false, false, ',', '-'));
            ((AbstractDocument) jTextQtdeDias.getDocument()).setDocumentFilter(new RandomValidator(0, true, false, false, false, ',', ' '));
        } catch (Exception ex) {
            // Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     *
     * @param cod
     */
    public void setNovoCodigo() {
        flag = false;
        consultaBean.setTentativas(0); // Atualizo ou insiro um dado novo? Esse loko decide!
        this.getMedico();
        this.getTipoConsulta();

    }

    public void getConsultasDia() {
        List lista = new ArrayList();
        Calendar calendar = new GregorianCalendar();
        try {
            if (!consultaDAO.getConsultaPesquisaUser(calendar.getTime()).isEmpty()) {
                for (int i = 0; i < consultaDAO.getConsultaPesquisaUser(calendar.getTime()).size(); i++) {
                    ConsultaBean c = new ConsultaBean();
                    c.setCodigo(consultaDAO.getConsultaPesquisaUser(calendar.getTime()).get(i).getCodigo());
                    c.setPacienteNome(consultaDAO.getConsultaPesquisaUser(calendar.getTime()).get(i).getPacienteNome());
                    c.setMedicoNome(consultaDAO.getConsultaPesquisaUser(calendar.getTime()).get(i).getMedicoNome());
                    c.setDtConsulta(consultaDAO.getConsultaPesquisaUser(calendar.getTime()).get(i).getDtConsulta());
                    c.setHorario(consultaDAO.getConsultaPesquisaUser(calendar.getTime()).get(i).getHorario());
                    c.setTipoConsultaNome(consultaDAO.getConsultaPesquisaUser(calendar.getTime()).get(i).getTipoConsultaNome());
                    c.setNumeroFicha(consultaDAO.getConsultaPesquisaUser(calendar.getTime()).get(i).getNumeroFicha());
                    c.setSigla(consultaDAO.getConsultaPesquisaUser(calendar.getTime()).get(i).getSigla());
                    c.setTipo(consultaDAO.getConsultaPesquisaUser(calendar.getTime()).get(i).getTipo());
                    c.setStatus(consultaDAO.getConsultaPesquisaUser(calendar.getTime()).get(i).getStatus());
                    c.setReqExame(consultaDAO.reqExames(consultaDAO.getConsultaPesquisaUser(calendar.getTime()).get(i).getCodigo()));
                    lista.add(c);
                }
                modelConsulta = new ConsultaTableModel(lista);
                jTablePesquisa.setModel(modelConsulta);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void limpaCampo() {
        jTextCodPaciente.setText(null);
        jTextNome.setText(null);
        jTextTelefone.setText(null);
        jTextEmail.setText(null);
        jTextRg.setText(null);
        jTextCpf.setText(null);
        jTextSexo.setText(null);
        jTextUltimaConsulta.setText(null);
        jComboSelecionaMedico.removeAllItems();
        jComboSelecionaMedico.addItem("Nenhum médico selecionado!");
        jComboSelecionaMedico.setSelectedIndex(0);
        jComboSelecionaTipoConsulta.removeAllItems();
        jComboSelecionaTipoConsulta.addItem("Nenhum tipo selecionado!");
        jComboSelecionaTipoConsulta.setSelectedIndex(0);
        jComboSelecionaConvenio.removeAllItems();
        jComboSelecionaConvenio.addItem("Nenhum Convenio!");
        jComboSelecionaConvenio.setSelectedIndex(0);
        jTextPeso.setText("");
        jTextPressao.setText("");
        jTextAltura.setText("");
        jTextValorDesconto.setText("");
        jTextValorConsulta.setText("");
        jTextAtestado.setText(null);
        jTextDiagnostico.setText(null);
        jTextPrescricao.setText(null);
        jComboTipoAtestado.setSelectedIndex(0);
        jTextQtdeDias.setText("");
        adicionalExames = 0;
        if (model != null) {
            model.limpaTabela();
        }
        if (!listaTipoExame.isEmpty()) {
            listaTipoExame.clear();
        }
        jLabelFoto.setIcon(null);
        BufferedImage imagem;
        try {
            imagem = ImageIO.read(new File("icones\\sem_foto.jpg"));
            Image image = imagem.getScaledInstance(136, 148, 0);
            Icon icon = new ImageIcon(image);
            jLabelFoto.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        flag = false;
    }

    public void ativaCampoUser() {
        jButtonAdicionarPaciente.setEnabled(true);
        jTextCodPaciente.setEnabled(true);
        jTextNome.setEnabled(true);
        jTextTelefone.setEnabled(true);
        jTextEmail.setEnabled(true);
        jTextRg.setEnabled(true);
        jTextCpf.setEnabled(true);
        jTextSexo.setEnabled(true);
        jTextUltimaConsulta.setEnabled(true);
        jComboSelecionaMedico.setEnabled(true);
        jComboSelecionaTipoConsulta.setEnabled(true);
        jComboSelecionaConvenio.setEnabled(true);
        jTextValorDesconto.setEnabled(true);
        jTextValorConsulta.setEnabled(true);
        jTextPeso.setEnabled(true);
        jTextPressao.setEnabled(true);
        jTextAltura.setEnabled(true);
    }

    public void ativaCampoRoot() {
        jTextCodPaciente.setEnabled(true);
        jTextNome.setEnabled(true);
        jTextTelefone.setEnabled(true);
        jTextEmail.setEnabled(true);
        jTextRg.setEnabled(true);
        jTextCpf.setEnabled(true);
        jTextSexo.setEnabled(true);
        jTextUltimaConsulta.setEnabled(true);
        jComboSelecionaMedico.setEnabled(true);
        jComboSelecionaTipoConsulta.setEnabled(true);
        jComboSelecionaConvenio.setEnabled(true);
        jTextPeso.setEnabled(true);
        jTextPressao.setEnabled(true);
        jTextAltura.setEnabled(true);
        jTextValorDesconto.setEnabled(true);
        jTextValorConsulta.setEnabled(true);
        jTextAtestado.setEnabled(true);
        jTextDiagnostico.setEnabled(true);
        jTextPrescricao.setEnabled(true);
        jComboTipoAtestado.setEnabled(true);
        jTextQtdeDias.setEnabled(true);
        jTableExame.setEnabled(true);
        jButtonAdicionarPaciente.setEnabled(true);
        jButtonExcluirExame.setEnabled(true);
        jButtonNovoExame.setEnabled(true);
    }

    public void desativaCampo() {
        jTextCodPaciente.setEnabled(false);
        jTextNome.setEnabled(false);
        jTextTelefone.setEnabled(false);
        jTextEmail.setEnabled(false);
        jTextSexo.setEnabled(false);
        jTextRg.setEnabled(false);
        jTextCpf.setEnabled(false);
        jTextUltimaConsulta.setEnabled(false);
        jComboSelecionaMedico.setEnabled(false);
        jComboSelecionaTipoConsulta.setEnabled(false);
        jComboSelecionaConvenio.setEnabled(false);
        jTextPeso.setEnabled(false);
        jTextPressao.setEnabled(false);
        jTextAltura.setEnabled(false);
        jTextValorDesconto.setEnabled(false);
        jTextValorConsulta.setEnabled(false);
        jTextAtestado.setEnabled(false);
        jTextDiagnostico.setEnabled(false);
        jTextPrescricao.setEnabled(false);
        jComboTipoAtestado.setEnabled(false);
        jTextQtdeDias.setEnabled(false);
        jButtonAdicionarPaciente.setEnabled(false);
        jButtonExcluirExame.setEnabled(false);
        jButtonNovoExame.setEnabled(false);
        jButtonGravar.setEnabled(false);
        jTableExame.setEnabled(false);
    }

    public void imprime(String fixa) {
        new Thread() {
            @Override
            public void run() {
                pdf.criarpdf(fixa);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    String diretorioUsuario = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
                    FileInputStream fis = null;

                    fis = new FileInputStream("C:/Altessmann/Temp/SENHAS.pdf");

                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException ex) {

                    }
                    Imprimir printPDFFile = new Imprimir(fis, "SENHAS.pdf");
                    printPDFFile.print();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PrinterException ex) {
                    Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

    }

    //CONVERTER BYTE[] PARA BUFFEREDIMAGE
    public BufferedImage getBufferedImage(byte[] data) {
        InputStream in = new ByteArrayInputStream(data);
        BufferedImage bImageFromConvert = null;
        try {
            bImageFromConvert = ImageIO.read(in);
        } catch (IOException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bImageFromConvert;
    }

    private static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public void historicoPacienteGetConsulta(int funcao, int codConsulta) {
        if (funcao == user) {
            getConsultaUser(codConsulta);
        } else {
            if (funcao == root) {
                getConsultaRoot(codConsulta);
            }
        }
        jTabbedPane1.setSelectedIndex(1);
        jTabbedPane1.grabFocus();
        jTabbedPane1.setEnabledAt(0, false);
        jTabbedPane1.setEnabledAt(1, true);
        jButtonEditar.setEnabled(false);
        jButtonNovo.setEnabled(false);
        jButtonCancelar.setEnabled(true);
        jButtonExcluir.setEnabled(false);

    }

    public void getPaciente(int cod) {
        editar = true;
        try {
            if (!consultaDAO.getPaciente(cod).isEmpty()) {
                jTextCodPaciente.setText(consultaDAO.getPaciente(cod).get(0).getCodigo() + "");
                jTextNome.setText(consultaDAO.getPaciente(cod).get(0).getNome());
                jTextTelefone.setText(consultaDAO.getPaciente(cod).get(0).getTelefone());
                jTextEmail.setText(consultaDAO.getPaciente(cod).get(0).getEmail());
                jTextSexo.setText(consultaDAO.getPaciente(cod).get(0).getSexo());
                jTextCpf.setText(consultaDAO.getPaciente(cod).get(0).getCpf());
                jTextRg.setText(consultaDAO.getPaciente(cod).get(0).getRg());
                if (consultaDAO.getPaciente(cod).get(0).getUltimaConsulta() != null) {
                    jTextUltimaConsulta.setText(toCalendar(consultaDAO.getPaciente(cod).get(0).getUltimaConsulta()).get(Calendar.DAY_OF_MONTH) + "/" + (toCalendar(consultaDAO.getPaciente(cod).get(0).getUltimaConsulta()).get(Calendar.MONTH) + 1) + "/" + toCalendar(consultaDAO.getPaciente(cod).get(0).getUltimaConsulta()).get(Calendar.YEAR));
                } else {
                    jTextUltimaConsulta.setText("Sem consultas!");
                }
                BufferedImage image = getBufferedImage(consultaDAO.getPaciente(cod).get(0).getFoto());
                Image imagem = image.getScaledInstance(136, 148, 0);
                Icon icon = new ImageIcon(imagem);
                jLabelFoto.setIcon(icon);
                getConvenio(cod);
                //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            } else {
                JOptionPane.showMessageDialog(null, "O código " + cod + " não está cadastrado!", "Código não cadastrado", JOptionPane.WARNING_MESSAGE);
                jButtonAdicionarPaciente.setEnabled(true);
                jTextCodPaciente.grabFocus();
//this.jButtonEditar.setEnabled(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        editar = false;
    }

    public void tabela() {
        List lista = new ArrayList();
        jTablePesquisa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    flag2 = true;
                    if (funcao == user) {
                        selecionado = Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + "");
                        getConsultaUser(Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + ""));
                    } else {
                        selecionado = Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + "");
                        getConsultaRoot(Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + ""));
                    }
                }

            }
        });
    }

    public void listenerJTextPeso() {
        jTextPeso.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    if (jTextPeso.isFocusable()) {
                        jTextPeso.selectAll();
                    }
                }
            }
        });
    }

    public void listenerJTextPressao() {
        jTextPressao.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    if (jTextPressao.isFocusable()) {
                        jTextPressao.selectAll();
                    }
                }
            }
        });
    }

    public void listenerJTextAltura() {
        List lista = new ArrayList();
        jTextAltura.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    if (jTextAltura.isFocusable()) {
                        jTextAltura.selectAll();
                    }
                }

            }
        });
    }

    public void eventoAoDigitarJTextValorDesconto() {
        jTextValorDesconto.addKeyListener(new java.awt.event.KeyListener() {

            public void keyTyped(java.awt.event.KeyEvent e) {
            }

            public void keyPressed(java.awt.event.KeyEvent e) {
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String atual, separado;
                int posicao, codTipoConsulta = 0;
                DecimalFormat formatoDois = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
                formatoDois.setMinimumFractionDigits(2);
                formatoDois.setParseBigDecimal(true);
                if (jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != -1) {
                    atual = jComboSelecionaTipoConsulta.getSelectedItem() + "";
                    posicao = 0;
                    char[] a = atual.toCharArray();
                    for (int i = 0; i < atual.length(); i++) {
                        if (a[i] == '-') {
                            posicao = i;
                        }
                    }
                    separado = atual.substring(0, posicao);
                    codTipoConsulta = Integer.parseInt(separado);
                }
                if (jTextValorDesconto.getText().length() > 0) {
                    try {
                        jTextValorConsulta.setText(truncateDecimal(consultaDAO.getValorConsulta(codTipoConsulta) - Double.parseDouble(jTextValorDesconto.getText().replace(',', '.')), 2) + "");
                    } catch (SQLException ex) {
                        Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
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
                    jTextValorDesconto.setText("0,00");
                }
            }
        });
    }

    public void getConsultaUser(int cod) {
        DecimalFormat formatoDois = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2);
        formatoDois.setParseBigDecimal(true);
        try {
            if (!consultaDAO.getConsultaUser(cod).isEmpty()) {
                getPaciente(consultaDAO.getConsultaUser(cod).get(0).getCodPaciente());
                getMedico();
                getTipoConsulta();
                if (consultaDAO.getConsultaUser(cod).get(0).getCodMedico() == 0) {
                    jComboSelecionaMedico.setSelectedIndex(0);
                } else {
                    jComboSelecionaMedico.setSelectedItem(consultaDAO.getConsultaUser(cod).get(0).getCodMedico() + "- " + consultaDAO.getConsultaUser(cod).get(0).getMedicoNome());
                }
                if (consultaDAO.getConsultaUser(cod).get(0).getCodTipoConsulta() == 0) {
                    jComboSelecionaTipoConsulta.setSelectedIndex(0);
                } else {
                    jComboSelecionaTipoConsulta.setSelectedItem(consultaDAO.getConsultaUser(cod).get(0).getCodTipoConsulta() + "- " + consultaDAO.getConsultaUser(cod).get(0).getTipoConsultaNome());
                }
                if (consultaDAO.getConsultaUser(cod).get(0).getCodConvenio() == 0) {
                    jComboSelecionaConvenio.setSelectedIndex(0);
                } else {
                    jComboSelecionaConvenio.setSelectedItem(consultaDAO.getConsultaUser(cod).get(0).getCodConvenio() + "- " + consultaDAO.getNomeConvenio(consultaDAO.getConsultaUser(cod).get(0).getCodConvenio()));
                }
                jTextPeso.setText(formatoDois.format(consultaDAO.getConsultaUser(cod).get(0).getPeso()));
                jTextPressao.setText(formatoDois.format(consultaDAO.getConsultaUser(cod).get(0).getPressao()));
                jTextAltura.setText(formatoDois.format(consultaDAO.getConsultaUser(cod).get(0).getAltura()));
                jTextValorConsulta.setText(formatoDois.format(consultaDAO.getConsultaUser(cod).get(0).getValorConsulta()));
                jTextValorDesconto.setText(formatoDois.format(consultaDAO.getConsultaUser(cod).get(0).getConvenioDesconto()));
                flag = consultaDAO.getConsultaUser(cod).get(0).getTentativas() == 0;

            } else {
                JOptionPane.showMessageDialog(null, "O código " + cod + " não está cadastrado!", "Código não cadastrado", JOptionPane.WARNING_MESSAGE);
                //this.jButtonEditar.setEnabled(false);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getConsultaPesquisa() throws ParseException {
        int codigo = 0;
        List lista = new ArrayList();
        try {
            codigo = Integer.parseInt(jTextPesquisa.getText());
            if (!consultaDAO.getConsultaPesquisaUser(codigo).isEmpty()) {
                ConsultaBean c = new ConsultaBean();
                c.setCodigo(consultaDAO.getConsultaPesquisaUser(codigo).get(0).getCodigo());
                c.setPacienteNome(consultaDAO.getConsultaPesquisaUser(codigo).get(0).getPacienteNome());
                c.setMedicoNome(consultaDAO.getConsultaPesquisaUser(codigo).get(0).getMedicoNome());
                c.setDtConsulta(consultaDAO.getConsultaPesquisaUser(codigo).get(0).getDtConsulta());
                c.setHorario(consultaDAO.getConsultaPesquisaUser(codigo).get(0).getHorario());
                c.setTipoConsultaNome(consultaDAO.getConsultaPesquisaUser(codigo).get(0).getTipoConsultaNome());
                c.setNumeroFicha(consultaDAO.getConsultaPesquisaUser(codigo).get(0).getNumeroFicha());
                c.setSigla(consultaDAO.getConsultaPesquisaUser(codigo).get(0).getSigla());
                c.setTipo(consultaDAO.getConsultaPesquisaUser(codigo).get(0).getTipo());
                c.setStatus(consultaDAO.getConsultaPesquisaUser(codigo).get(0).getStatus());
                c.setReqExame(consultaDAO.reqExames(consultaDAO.getConsultaPesquisaUser(codigo).get(0).getCodigo()));
                lista.add(c);
                modelConsulta = new ConsultaTableModel(lista);
                jTablePesquisa.setModel(modelConsulta);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
            } else {
                JOptionPane.showMessageDialog(null, "Consulta não cadastrada!\nCódigo inválido!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            try {
                if (!consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).isEmpty()) {
                    for (int i = 0; i < consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).size(); i++) {
                        ConsultaBean c = new ConsultaBean();
                        c.setCodigo(consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).get(i).getCodigo());
                        c.setPacienteNome(consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).get(i).getPacienteNome());
                        c.setMedicoNome(consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).get(i).getMedicoNome());
                        c.setDtConsulta(consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).get(i).getDtConsulta());
                        c.setHorario(consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).get(i).getHorario());
                        c.setTipoConsultaNome(consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).get(i).getTipoConsultaNome());
                        c.setNumeroFicha(consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).get(i).getNumeroFicha());
                        c.setSigla(consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).get(i).getSigla());
                        c.setTipo(consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).get(i).getTipo());
                        c.setStatus(consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).get(i).getStatus());
                        c.setReqExame(consultaDAO.reqExames(consultaDAO.getConsultaPesquisaUser(jTextPesquisa.getText()).get(i).getCodigo()));
                        lista.add(c);
                    }
                    modelConsulta = new ConsultaTableModel(lista);
                    jTablePesquisa.setModel(modelConsulta);
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                } else {                       // PESQUISAR POR DATE
                    try {
                        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
                        Date data = sd.parse(jTextPesquisa.getText());
                        for (int i = 0; i < consultaDAO.getConsultaPesquisaUser(data).size(); i++) {
                            ConsultaBean c = new ConsultaBean();
                            c.setCodigo(consultaDAO.getConsultaPesquisaUser(data).get(i).getCodigo());
                            c.setPacienteNome(consultaDAO.getConsultaPesquisaUser(data).get(i).getPacienteNome());
                            c.setMedicoNome(consultaDAO.getConsultaPesquisaUser(data).get(i).getMedicoNome());
                            c.setDtConsulta(consultaDAO.getConsultaPesquisaUser(data).get(i).getDtConsulta());
                            c.setHorario(consultaDAO.getConsultaPesquisaUser(data).get(0).getHorario());
                            c.setTipoConsultaNome(consultaDAO.getConsultaPesquisaUser(data).get(i).getTipoConsultaNome());
                            c.setNumeroFicha(consultaDAO.getConsultaPesquisaUser(data).get(i).getNumeroFicha());
                            c.setSigla(consultaDAO.getConsultaPesquisaUser(data).get(i).getSigla());
                            c.setTipo(consultaDAO.getConsultaPesquisaUser(data).get(i).getTipo());
                            c.setStatus(consultaDAO.getConsultaPesquisaUser(data).get(i).getStatus());
                            c.setReqExame(consultaDAO.reqExames(consultaDAO.getConsultaPesquisaUser(data).get(i).getCodigo()));
                            lista.add(c);
                        }
                        modelConsulta = new ConsultaTableModel(lista);
                        jTablePesquisa.setModel(modelConsulta);
                        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                        jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    } catch (java.text.ParseException e2) {
                        JOptionPane.showMessageDialog(null, "consulta não cadastrada!", "Erro", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void getExame(int codConsulta) {
        try {
            if (!consultaDAO.getExameConsulta(codConsulta).isEmpty()) {
                int codExame = 0;
                for (int i = 0; i < consultaDAO.getExameConsulta(codConsulta).size(); i++) {
                    tipoExameBean = new TipoExameBean();
                    tipoExameBean.setCodigo(consultaDAO.getExameConsulta(codConsulta).get(i).getCodigo());
                    tipoExameBean.setNome(consultaDAO.getExameConsulta(codConsulta).get(i).getNome());
                    tipoExameBean.setDescricao(consultaDAO.getExameConsulta(codConsulta).get(i).getDescricao());
                    tipoExameBean.setValor(consultaDAO.getExameConsulta(codConsulta).get(i).getValor());
                    listaTipoExame.add(tipoExameBean);
                }
                model = new TipoExameTableModel(listaTipoExame);
                jTableExame.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableExame.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(2).setCellRenderer(esquerda);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getConsultaRoot(int cod) {
        DecimalFormat formatoDois = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2);
        formatoDois.setParseBigDecimal(true);
        try {
            if (!consultaDAO.getConsultaUser(cod).isEmpty()) {
                getPaciente(consultaDAO.getConsultaUser(cod).get(0).getCodPaciente());
                getMedico();
                getTipoConsulta();
                if (consultaDAO.getConsultaUser(cod).get(0).getCodMedico() == 0) {
                    jComboSelecionaMedico.setSelectedIndex(0);
                } else {
                    jComboSelecionaMedico.setSelectedItem(consultaDAO.getConsultaUser(cod).get(0).getCodMedico() + "- " + consultaDAO.getConsultaUser(cod).get(0).getMedicoNome());
                }
                if (consultaDAO.getConsultaUser(cod).get(0).getCodTipoConsulta() == 0) {
                    jComboSelecionaTipoConsulta.setSelectedIndex(0);
                } else {
                    jComboSelecionaTipoConsulta.setSelectedItem(consultaDAO.getConsultaUser(cod).get(0).getCodTipoConsulta() + "- " + consultaDAO.getConsultaUser(cod).get(0).getTipoConsultaNome());
                }
                if (consultaDAO.getConsultaUser(cod).get(0).getCodConvenio() == 0) {
                    jComboSelecionaConvenio.setSelectedIndex(0);
                } else {
                    jComboSelecionaConvenio.setSelectedItem(consultaDAO.getConsultaUser(cod).get(0).getCodConvenio() + "- " + consultaDAO.getNomeConvenio(consultaDAO.getConsultaUser(cod).get(0).getCodConvenio()));
                }
                jTextPeso.setText(formatoDois.format(consultaDAO.getConsultaRoot(cod).get(0).getPeso()));
                jTextPressao.setText(formatoDois.format(consultaDAO.getConsultaRoot(cod).get(0).getPressao()));
                jTextAltura.setText(formatoDois.format(consultaDAO.getConsultaRoot(cod).get(0).getAltura()));
                jTextValorConsulta.setText(formatoDois.format(consultaDAO.getConsultaRoot(cod).get(0).getValorConsulta()));
                jTextValorDesconto.setText(formatoDois.format(consultaDAO.getConsultaRoot(cod).get(0).getConvenioDesconto()));
                jTextAtestado.setText(consultaDAO.getConsultaRoot(cod).get(0).getAtestadoTexto());
                jTextDiagnostico.setText(consultaDAO.getConsultaRoot(cod).get(0).getDiagnostico());
                jTextPrescricao.setText(consultaDAO.getConsultaRoot(cod).get(0).getPrescricao());
                jComboTipoAtestado.setSelectedItem(consultaDAO.getConsultaRoot(cod).get(0).getAtestadoTipo());
                jTextQtdeDias.setText(consultaDAO.getConsultaRoot(cod).get(0).getAtestadoDias() + "");
                getExame(cod);
                //  *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

                flag = consultaDAO.getConsultaUser(cod).get(0).getTentativas() == 0;

            } else {
                JOptionPane.showMessageDialog(null, "O código " + cod + " não está cadastrado!", "Código não cadastrado", JOptionPane.WARNING_MESSAGE);
                //this.jButtonEditar.setEnabled(false);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getConvenio(int cod) {
        jComboSelecionaConvenio.removeAllItems();
        jComboSelecionaConvenio.addItem("Nenhum convenio!");
        try {
            for (int i = 0; i < consultaDAO.getConvenio(cod).size(); i++) {
                jComboSelecionaConvenio.addItem(consultaDAO.getConvenio(cod).get(i).getCodConvenio() + "- " + consultaDAO.getConvenio(cod).get(i).getNomeConvenio());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getMedico() {
        try {
            jComboSelecionaMedico.removeAllItems();
            jComboSelecionaMedico.addItem("Nenhum médico selecionado!");
            for (int i = 0; i < consultaDAO.getMedico().size(); i++) {
                jComboSelecionaMedico.addItem(consultaDAO.getMedico().get(i).getCodigo() + "- " + consultaDAO.getMedico().get(i).getNome());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getTipoConsulta() {
        try {
            jComboSelecionaTipoConsulta.removeAllItems();
            jComboSelecionaTipoConsulta.addItem("Nenhum tipo selecionado!");
            for (int i = 0; i < consultaDAO.getTipoConsulta().size(); i++) {
                jComboSelecionaTipoConsulta.addItem(consultaDAO.getTipoConsulta().get(i).getCodigo() + "- " + consultaDAO.getTipoConsulta().get(i).getNome());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getTipoExame(int codigo) {
        try {
            String valorString = JOptionPane.showInputDialog("Valor do exame", tipoExameDAO.getTipoExame(codigo).get(0).getValor() + "");
            if (valorString != null) {
                double valor = 0;
                try {
                    valor = Double.parseDouble(valorString);
                } catch (NumberFormatException e) {
                    valorString = null;
                }

                if (!tipoExameDAO.getTipoExame(codigo).isEmpty()) {
                    tipoExameBean = new TipoExameBean();
                    tipoExameBean.setCodigo(tipoExameDAO.getTipoExame(codigo).get(0).getCodigo());
                    tipoExameBean.setNome(tipoExameDAO.getTipoExame(codigo).get(0).getNome());
                    tipoExameBean.setDescricao(tipoExameDAO.getTipoExame(codigo).get(0).getDescricao());
                    tipoExameBean.setValor(valor);
                    adicionalExames = adicionalExames + valor;
                    listaTipoExame.add(tipoExameBean);
                    model = new TipoExameTableModel(listaTipoExame);
                    jTableExame.setModel(model);
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    jTableExame.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    jTableExame.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    jTableExame.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    if (cadastroTipoExameNew != null) {
                        cadastroTipoExameNew.fecharJanela();
                    }
                    editar = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Não existem exames com o código '" + codigo + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPalavraPaciente(int cod) {
        this.getPaciente(cod);
        jButtonAdicionarPaciente.setEnabled(true);

    }

    public void getPalavraTipoExame(int cod) {
        this.getTipoExame(cod);
    }

    private static BigDecimal truncateDecimal(double x, int numberofDecimals) {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }

    public void gravar(double valorTotal, double desconto) {
        Calendar dataDeHoje = new GregorianCalendar(); // pega a data do dia
        String atual, separado;
        int codTipoConsulta = 0, codConvenio = 0, codMedico = 0;
        int posicao = 0;
        //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

        if (jComboSelecionaConvenio.getSelectedIndex() != 0 && jComboSelecionaConvenio.getSelectedIndex() != -1) {
            atual = jComboSelecionaConvenio.getSelectedItem() + "";
            char[] a = atual.toCharArray();
            for (int i = 0; i < atual.length(); i++) {
                if (a[i] == '-') {
                    posicao = i;
                }
            }
            separado = atual.substring(0, posicao);
            codConvenio = Integer.parseInt(separado);

        }
        //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
        if (jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != -1) {
            atual = jComboSelecionaTipoConsulta.getSelectedItem() + "";// FAZ O MESMO PARA CONSULTA
            posicao = 0;
            char[] a = atual.toCharArray();
            for (int i = 0; i < atual.length(); i++) {
                if (a[i] == '-') {
                    posicao = i;
                }
            }
            separado = atual.substring(0, posicao);
            codTipoConsulta = Integer.parseInt(separado);
        }

        //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
        if (jComboSelecionaMedico.getSelectedIndex() != 0 && jComboSelecionaMedico.getSelectedIndex() != -1) {
            atual = jComboSelecionaMedico.getSelectedItem() + "";// FAZ O MESMO PARA CONSULTA
            posicao = 0;
            char[] a = atual.toCharArray();
            for (int i = 0; i < atual.length(); i++) {
                if (a[i] == '-') {
                    posicao = i;
                }
            }
            separado = atual.substring(0, posicao);
            codMedico = Integer.parseInt(separado);
        }

        //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*AQUI OS CÓDIGOS ESTÃO FUNCIONADO!!!
        double peso, pressao, altura;
        //try {
        //desconto = Double.parseDouble(jTextValorDesconto.getText().replace(',', '.'));//DESCONTO
        //} catch (NumberFormatException e) {
        // desconto = 0;
        // }

        try {
            peso = Double.parseDouble(jTextPeso.getText().replace(',', '.'));//PESO
        } catch (NumberFormatException e) {
            peso = 0;
        }

        try {
            pressao = Double.parseDouble(jTextPressao.getText().replace(',', '.'));//PRESSAO
        } catch (NumberFormatException e) {
            pressao = 0;
        }

        try {
            altura = Double.parseDouble(jTextAltura.getText().replace(',', '.'));//ALTURA
        } catch (NumberFormatException e) {
            altura = 0;
        }

        if (jComboSelecionaMedico.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && !jTextCodPaciente.getText().equals("")) {
            if (funcao == user) {
                Object[] options = {"EMERGÊNCIA", "PRIORITÁRIO", "NORMAL"};
                String x = null;
                String fixa = "";

                char tipoConsulta = '0';
                int tipo;
                switch (JOptionPane.showOptionDialog(null, "Informe o tipo de chamada:", "Chamada", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[2])) {
                    case 0:
                        x = "Emergência";
                        tipo = 0;
                        tipoConsulta = 'E';
                        break;
                    case 1:
                        x = "Prioritário";
                        tipo = 1;
                        tipoConsulta = 'P';
                        break;
                    case 2:
                        x = "Normal";
                        tipoConsulta = 'N';
                        tipo = 2;
                        break;
                    default:
                        x = null;
                        tipo = -1;
                        break;
                }
                if (tipo != -1) {
                    if (JOptionPane.showConfirmDialog(null, "Confirma atendimento " + x + "?", "Chamada", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        //System.out.println("F000" + (consultaDAO.getUltimaChamada('F', tipo) + 1));

                        consultaDAO.setUltimaConsultaPaciente(Integer.parseInt(jTextCodPaciente.getText()));
                        if (!flag) {
                            int ultimaChamada = consultaDAO.getUltimaChamada(codMedico, tipo);
                            String sigla = funcionarioDAO.getSigla(codMedico);
                            String zeros = "";
                            if (ultimaChamada >= 100) {
                                zeros = "0";
                            } else if (ultimaChamada >= 10) {
                                zeros = "00";
                            } else {
                                zeros = "000";
                            }

                            if (codConvenio != 0) {
                                dataDeHoje.add(Calendar.DATE, 15);// se  tivcer convenio tem direito a voltar em 15 dias
                            } else {
                                dataDeHoje.add(Calendar.DATE, 30); // se não tiver convenio tem direito a voltar em 30 dias
                            }

                            consultaDAO.setConsulta(Integer.parseInt(jTextCodPaciente.getText()), codMedico, codTipoConsulta, codConvenio, desconto, valorTotal, ultimaChamada + 1, sigla, tipo, peso, pressao, altura, jTextAtestado.getText(), jTextDiagnostico.getText(), jTextPrescricao.getText(), jComboTipoAtestado.getSelectedItem().toString(), jTextQtdeDias.getText(), dataDeHoje, revisao, consultaBean.getTentativas());
                            int codigo = 0;
                            desativaCampo();
                            limpaCampo();
                            this.jButtonGravar.setEnabled(false);
                            this.jButtonNovo.setEnabled(true);
                            this.jButtonCancelar.setEnabled(false);
                            this.jButtonExcluir.setEnabled(false);
                            jTabbedPane1.setEnabledAt(0, true);
                            //ImprimeFicha imprimeFicha = new ImprimeFicha();
                            //imprimeFicha.Imprime("ultimaFicha.txt", sigla + tipoConsulta + zeros + (ultimaChamada + 1));

                            if (JOptionPane.showConfirmDialog(null, "Senha: " + sigla + tipoConsulta + zeros + (ultimaChamada + 1) + "\n" + "Deseja Imprimir senha de Atendimento?\n", "Altessmann - Impressão",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                fixa = sigla + tipoConsulta + zeros + (ultimaChamada + 1);
                                imprime(fixa);
                            }

                            //JOptionPane.showMessageDialog(null, "Chamada: " + sigla + tipoConsulta + zeros + (ultimaChamada + 1), "Chamada", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            int ultimaChamada = consultaDAO.getUltimaChamada(codMedico, tipo);
                            String sigla = funcionarioDAO.getSigla(codMedico);
                            String zeros = "";
                            if (ultimaChamada >= 100) {
                                zeros = "0";
                            } else if (ultimaChamada >= 10) {
                                zeros = "00";
                            } else {
                                zeros = "000";
                            }
                            try {
                                consultaDAO.atualizaConsultaUser((int) jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0), Integer.parseInt(jTextCodPaciente.getText()), codMedico, codTipoConsulta, codConvenio, desconto, valorTotal, ultimaChamada + 1, sigla, tipo, peso, pressao, altura, consultaBean.getTentativas());
                                desativaCampo();
                                limpaCampo();
                                flag = false;
                                this.jButtonGravar.setEnabled(false);
                                this.jButtonNovo.setEnabled(true);
                                this.jButtonCancelar.setEnabled(false);
                                this.jButtonExcluir.setEnabled(false);
                                jTabbedPane1.setEnabledAt(0, true);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "Erro\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
                            }

                            if (JOptionPane.showConfirmDialog(null, "Senha: " + sigla + tipoConsulta + zeros + (ultimaChamada + 1) + "\n" + "Deseja Imprimir senha de Atendimento?\n", "Altessmann - Impressão",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                fixa = sigla + tipoConsulta + zeros + (ultimaChamada + 1);
                                imprime(fixa);
                            }
                        }

                    }
                }
            } else {
                if (!flag) {
                    Object[] options = {"EMERGÊNCIA", "PRIORITÁRIO", "NORMAL"};
                    String x = null;
                    String fixa = "";

                    char tipoConsulta = '0';
                    int tipo;
                    switch (JOptionPane.showOptionDialog(null, "Informe o tipo de chamada:", "Chamada", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[2])) {
                        case 0:
                            x = "Emergência";
                            tipo = 0;
                            tipoConsulta = 'E';
                            break;
                        case 1:
                            x = "Prioritário";
                            tipo = 1;
                            tipoConsulta = 'P';
                            break;
                        case 2:
                            x = "Normal";
                            tipoConsulta = 'N';
                            tipo = 2;
                            break;
                        default:
                            x = null;
                            tipo = -1;
                            break;
                    }
                    if (tipo != -1) {
                        if (JOptionPane.showConfirmDialog(null, "Confirma atendimento " + x + "?", "Chamada", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            consultaDAO.setUltimaConsultaPaciente(Integer.parseInt(jTextCodPaciente.getText()));
                            int ultimaChamada = consultaDAO.getUltimaChamada(codMedico, tipo);
                            String sigla = funcionarioDAO.getSigla(codMedico);
                            String zeros = "";
                            if (ultimaChamada >= 100) {
                                zeros = "0";
                            } else if (ultimaChamada >= 10) {
                                zeros = "00";
                            } else {
                                zeros = "000";
                            }

                            //*+*+*+*+*+*+*+*+*++*+*+*+*+*+*+*+*+*+*+*+*+*+*+*++*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+* REVISAO GRATUITA COMEÇO
                            if (codConvenio != 0) {
                                dataDeHoje.add(Calendar.DATE, 15);// se  tivcer convenio tem direito a voltar em 15 dias
                            } else {
                                dataDeHoje.add(Calendar.DATE, 30); // se não tiver convenio tem direito a voltar em 30 dias
                            }
                            if (revisao) {
                                consultaDAO.setConsutlasSemRevisao(Integer.parseInt(jTextCodPaciente.getText()));
                            }
                            //*+*+*+*+*+*+*+*+*++*+*+*+*+*+*+*+*+*+*+*+*+*+*+*++*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+* REVISAO GRATUITA FIM
                            consultaDAO.setConsulta(Integer.parseInt(jTextCodPaciente.getText()), codMedico, codTipoConsulta, codConvenio, desconto, valorTotal, ultimaChamada + 1, sigla, tipo, peso, pressao, altura, jTextAtestado.getText(), jTextDiagnostico.getText(), jTextPrescricao.getText(), jComboTipoAtestado.getSelectedItem().toString(), jTextQtdeDias.getText(), dataDeHoje, revisao, consultaBean.getTentativas());
                            int codigo = 0;
                            try {
                                codigo = consultaDAO.getUltimoCodigo(); // busca ultimo codigo para poder inserir na tabela consulta_exame

                                for (int i = 0; i < jTableExame.getRowCount(); i++) {
                                    consultaDAO.setExameConsulta(codigo, (int) model.getValueAt(i, 0));
                                }

                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "Erro!...\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
                                Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            desativaCampo();
                            limpaCampo();
                            this.jButtonGravar.setEnabled(false);
                            this.jButtonNovo.setEnabled(true);
                            this.jButtonCancelar.setEnabled(false);
                            this.jButtonExcluir.setEnabled(false);
                            jTabbedPane1.setEnabledAt(0, true);
                            //ImprimeFicha imprimeFicha = new ImprimeFicha();
                            //imprimeFicha.Imprime("ultimaFicha.txt", sigla + tipoConsulta + zeros + (ultimaChamada + 1));

                            if (JOptionPane.showConfirmDialog(null, "Senha: " + sigla + tipoConsulta + zeros + (ultimaChamada + 1) + "\n" + "Deseja Imprimir senha de Atendimento?\n", "Altessmann - Impressão",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                fixa = sigla + tipoConsulta + zeros + (ultimaChamada + 1);
                                imprime(fixa);
                            }

                        }
                    }
                } else {
                    int codigo = (int) jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0);
                    boolean foiChamado = false;
                    try {
                        consultaDAO.deletaConsulta_Exame(codigo); //DELETA TODAS AS LINHAS COM CONVENIO
                        //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
                        for (int i = 0; i < jTableExame.getRowCount(); i++) {//DEPOIS INSERE TODAS DENOVO, CONFORME FOI SELECIONADO
                            consultaDAO.setExameConsulta(codigo, Integer.parseInt(model.getValueAt(i, 0) + ""));
                        }
                        if (consultaDAO.getConsultaRoot((int) jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0)).get(0).isFoiChamado()) {
                            foiChamado = true;
                        }
                        consultaDAO.atualizaConsultaRoot(codigo, Integer.parseInt(jTextCodPaciente.getText()), codMedico, codTipoConsulta, codConvenio, desconto, valorTotal, foiChamado, peso, pressao, altura, jTextAtestado.getText(), jTextDiagnostico.getText(), jTextPrescricao.getText(), jComboTipoAtestado.getSelectedItem().toString(), jTextQtdeDias.getText(), consultaBean.getTentativas());
                        desativaCampo();
                        limpaCampo();
                        flag = false;
                        this.jButtonGravar.setEnabled(false);
                        this.jButtonNovo.setEnabled(true);
                        this.jButtonCancelar.setEnabled(false);
                        this.jButtonExcluir.setEnabled(false);
                        jTabbedPane1.setEnabledAt(0, true);
                    } catch (SQLException ex) {
                        Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Dados incompletos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        editar = false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTextPesquisa = new javax.swing.JTextField();
        jButtonPesquisar = new javax.swing.JButton();
        jButtonListar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTablePesquisa = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButtonNovo = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonGravar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jComboSelecionaTipoPesquisa13 = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jButtonVisualizarConculta = new javax.swing.JButton();
        jTextCodPaciente = new javax.swing.JTextField();
        LabelValorCus125 = new javax.swing.JLabel();
        jButtonAdicionarPaciente = new javax.swing.JButton();
        LabelValorCus126 = new javax.swing.JLabel();
        jTextNome = new javax.swing.JTextField();
        LabelValorCus52 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabelFoto = new javax.swing.JLabel();
        LabelValorCus127 = new javax.swing.JLabel();
        jTextTelefone = new javax.swing.JTextField();
        LabelValorCus128 = new javax.swing.JLabel();
        jTextEmail = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jDateChooserCalendario = new datechooser.beans.DateChooserPanel();
        jComboSelecionaHorario = new javax.swing.JComboBox();
        LabelValorCus122 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jTextValorConsulta = new javax.swing.JTextField();
        LabelValorCus129 = new javax.swing.JLabel();
        jComboSelecionaTipoConsulta = new javax.swing.JComboBox();
        LabelValorCus123 = new javax.swing.JLabel();
        jComboSelecionaConvenio = new javax.swing.JComboBox();
        LabelValorCus53 = new javax.swing.JLabel();
        jTextValorDesconto = new javax.swing.JTextField();
        LabelValorCus130 = new javax.swing.JLabel();
        jComboSelecionaMedico = new javax.swing.JComboBox();
        LabelValorCus131 = new javax.swing.JLabel();
        jTextPeso = new javax.swing.JTextField();
        LabelValorCus132 = new javax.swing.JLabel();
        jTextPressao = new javax.swing.JTextField();
        LabelValorCus133 = new javax.swing.JLabel();
        LabelValorCus134 = new javax.swing.JLabel();
        jTextAltura = new javax.swing.JTextField();
        jTextRg = new javax.swing.JTextField();
        jTextCpf = new javax.swing.JTextField();
        LabelValorCus141 = new javax.swing.JLabel();
        LabelValorCus142 = new javax.swing.JLabel();
        jTextSexo = new javax.swing.JTextField();
        jTextUltimaConsulta = new javax.swing.JTextField();
        LabelValorCus143 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextDiagnostico = new javax.swing.JTextArea();
        LabelValorCus135 = new javax.swing.JLabel();
        LabelValorCus136 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPrescricao = new javax.swing.JTextArea();
        LabelValorCus137 = new javax.swing.JLabel();
        jButtonNovoExame = new javax.swing.JButton();
        jButtonExcluirExame = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAtestado = new javax.swing.JTextArea();
        LabelValorCus138 = new javax.swing.JLabel();
        jComboTipoAtestado = new javax.swing.JComboBox();
        LabelValorCus139 = new javax.swing.JLabel();
        jTextQtdeDias = new javax.swing.JTextField();
        LabelValorCus140 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableExame = new javax.swing.JTable();
        jButtonImprimirPrescricao = new javax.swing.JButton();
        jButtonImprimirAtestado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Altessmann Sistemas - Consultas");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
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
        jButtonListar.setText("Listar consultas do dia Atual");
        jButtonListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListarActionPerformed(evt);
            }
        });

        jTablePesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Médico", "Data Consulta", "Hora Consulta", "Tipo Consutla", "Ficha", "Status", "Requisição de exames"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
        jScrollPane4.setViewportView(jTablePesquisa);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonPesquisar)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonListar)))
                .addGap(5, 5, 5))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonListar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("  Pesquisar Consultas  ", jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Cadastro Consulta  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

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

        jComboSelecionaTipoPesquisa13.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboSelecionaTipoPesquisa13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Chegada", "Agendamento" }));
        jComboSelecionaTipoPesquisa13.setToolTipText("");
        jComboSelecionaTipoPesquisa13.setEnabled(false);
        jComboSelecionaTipoPesquisa13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboSelecionaTipoPesquisa13ActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButtonVisualizarConculta.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonVisualizarConculta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/print.png"))); // NOI18N
        jButtonVisualizarConculta.setEnabled(false);
        jButtonVisualizarConculta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVisualizarConcultaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
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
                .addComponent(jComboSelecionaTipoPesquisa13, 0, 1, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonVisualizarConculta, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonVisualizarConculta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboSelecionaTipoPesquisa13, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTextCodPaciente.setEditable(false);
        jTextCodPaciente.setBackground(new java.awt.Color(255, 255, 255));
        jTextCodPaciente.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextCodPaciente.setForeground(new java.awt.Color(153, 153, 153));
        jTextCodPaciente.setEnabled(false);
        jTextCodPaciente.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextCodPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCodPacienteActionPerformed(evt);
            }
        });

        LabelValorCus125.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus125.setText("Codigo:");

        jButtonAdicionarPaciente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonAdicionarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add2.png"))); // NOI18N
        jButtonAdicionarPaciente.setEnabled(false);
        jButtonAdicionarPaciente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAdicionarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarPacienteActionPerformed(evt);
            }
        });

        LabelValorCus126.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus126.setText("Nome Completo:");

        jTextNome.setEditable(false);
        jTextNome.setBackground(new java.awt.Color(255, 255, 255));
        jTextNome.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextNome.setForeground(new java.awt.Color(153, 153, 153));
        jTextNome.setEnabled(false);
        jTextNome.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNomeActionPerformed(evt);
            }
        });

        LabelValorCus52.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus52.setText("Sexo:");

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fotoadd.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 148, Short.MAX_VALUE)
        );

        LabelValorCus127.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus127.setText("Telefone:");

        jTextTelefone.setEditable(false);
        jTextTelefone.setBackground(new java.awt.Color(255, 255, 255));
        jTextTelefone.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextTelefone.setForeground(new java.awt.Color(153, 153, 153));
        jTextTelefone.setEnabled(false);
        jTextTelefone.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextTelefoneActionPerformed(evt);
            }
        });

        LabelValorCus128.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus128.setText("Email:");

        jTextEmail.setEditable(false);
        jTextEmail.setBackground(new java.awt.Color(255, 255, 255));
        jTextEmail.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextEmail.setForeground(new java.awt.Color(153, 153, 153));
        jTextEmail.setEnabled(false);
        jTextEmail.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextEmailActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jDateChooserCalendario.setEnabled(false);

        jComboSelecionaHorario.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboSelecionaHorario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sem horário Estipulado" }));
        jComboSelecionaHorario.setToolTipText("");
        jComboSelecionaHorario.setEnabled(false);

        LabelValorCus122.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus122.setText("Horario Consulta:");

        jTextValorConsulta.setEditable(false);
        jTextValorConsulta.setBackground(new java.awt.Color(255, 255, 255));
        jTextValorConsulta.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextValorConsulta.setForeground(new java.awt.Color(255, 51, 51));
        jTextValorConsulta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextValorConsulta.setText("00,00");
        jTextValorConsulta.setEnabled(false);
        jTextValorConsulta.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextValorConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextValorConsultaActionPerformed(evt);
            }
        });

        LabelValorCus129.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelValorCus129.setText("Valor final da Consulta:");

        jComboSelecionaTipoConsulta.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboSelecionaTipoConsulta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nenhum tipo selecionado!" }));
        jComboSelecionaTipoConsulta.setToolTipText("");
        jComboSelecionaTipoConsulta.setEnabled(false);
        jComboSelecionaTipoConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboSelecionaTipoConsultaActionPerformed(evt);
            }
        });

        LabelValorCus123.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus123.setText("Tipo de Consulta:");

        jComboSelecionaConvenio.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboSelecionaConvenio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nenhum Convenio!" }));
        jComboSelecionaConvenio.setToolTipText("");
        jComboSelecionaConvenio.setEnabled(false);
        jComboSelecionaConvenio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboSelecionaConvenioActionPerformed(evt);
            }
        });
        jComboSelecionaConvenio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboSelecionaConvenioKeyPressed(evt);
            }
        });

        LabelValorCus53.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus53.setText("Convenio Paciente:");

        jTextValorDesconto.setEditable(false);
        jTextValorDesconto.setBackground(new java.awt.Color(255, 255, 255));
        jTextValorDesconto.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextValorDesconto.setForeground(new java.awt.Color(0, 153, 51));
        jTextValorDesconto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextValorDesconto.setText("00,00");
        jTextValorDesconto.setEnabled(false);
        jTextValorDesconto.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextValorDesconto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextValorDescontoActionPerformed(evt);
            }
        });

        LabelValorCus130.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelValorCus130.setText("Valor desconto:");

        jComboSelecionaMedico.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboSelecionaMedico.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nenhum médico selecionado!" }));
        jComboSelecionaMedico.setToolTipText("");
        jComboSelecionaMedico.setEnabled(false);
        jComboSelecionaMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboSelecionaMedicoActionPerformed(evt);
            }
        });

        LabelValorCus131.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus131.setText("Médico escolhido para Consulta:");

        jTextPeso.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextPeso.setEnabled(false);
        jTextPeso.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPesoActionPerformed(evt);
            }
        });

        LabelValorCus132.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus132.setText("Peso:");

        jTextPressao.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextPressao.setEnabled(false);
        jTextPressao.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextPressao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPressaoActionPerformed(evt);
            }
        });

        LabelValorCus133.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus133.setText("Pressão Arterial:");

        LabelValorCus134.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus134.setText("Altura:");

        jTextAltura.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextAltura.setEnabled(false);
        jTextAltura.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextAltura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextAlturaActionPerformed(evt);
            }
        });

        jTextRg.setEditable(false);
        jTextRg.setBackground(new java.awt.Color(255, 255, 255));
        jTextRg.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextRg.setForeground(new java.awt.Color(153, 153, 153));
        jTextRg.setEnabled(false);
        jTextRg.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextRg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextRgActionPerformed(evt);
            }
        });

        jTextCpf.setEditable(false);
        jTextCpf.setBackground(new java.awt.Color(255, 255, 255));
        jTextCpf.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextCpf.setForeground(new java.awt.Color(153, 153, 153));
        jTextCpf.setEnabled(false);
        jTextCpf.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCpfActionPerformed(evt);
            }
        });

        LabelValorCus141.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus141.setText("RG:");

        LabelValorCus142.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus142.setText("CPF:");

        jTextSexo.setEditable(false);
        jTextSexo.setBackground(new java.awt.Color(255, 255, 255));
        jTextSexo.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextSexo.setForeground(new java.awt.Color(153, 153, 153));
        jTextSexo.setEnabled(false);
        jTextSexo.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextSexoActionPerformed(evt);
            }
        });

        jTextUltimaConsulta.setEditable(false);
        jTextUltimaConsulta.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextUltimaConsulta.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextUltimaConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextUltimaConsultaActionPerformed(evt);
            }
        });

        LabelValorCus143.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus143.setText("Última consulta:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LabelValorCus125)
                            .addComponent(LabelValorCus127)
                            .addComponent(jTextTelefone, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(jTextCodPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LabelValorCus126, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelValorCus128))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus141)
                                    .addComponent(jTextRg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextCpf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(LabelValorCus142)
                                        .addGap(114, 114, 114))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButtonAdicionarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(LabelValorCus52)
                                            .addComponent(jTextSexo, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))))))
                    .addComponent(jSeparator3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboSelecionaTipoConsulta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(LabelValorCus123, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboSelecionaConvenio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboSelecionaMedico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(LabelValorCus131, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(LabelValorCus132)
                                        .addGap(71, 71, 71))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jTextPeso, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                        .addGap(5, 5, 5)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(LabelValorCus133)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jTextPressao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextAltura, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(LabelValorCus130, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextValorDesconto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus134)
                                .addGap(119, 119, 119)))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextValorConsulta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(LabelValorCus129, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextUltimaConsulta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LabelValorCus143, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus122)
                            .addComponent(jComboSelecionaHorario, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooserCalendario, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(LabelValorCus128)
                                    .addGap(37, 37, 37))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(LabelValorCus126)
                                    .addGap(104, 104, 104))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(LabelValorCus125)
                                            .addGap(1, 1, 1)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jTextCodPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextNome, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(LabelValorCus127)
                                                .addComponent(LabelValorCus141))
                                            .addGap(1, 1, 1))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(LabelValorCus52)
                                            .addGap(1, 1, 1)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButtonAdicionarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(LabelValorCus142)))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextTelefone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(1, 1, 1)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jTextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextRg, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus131)
                                .addGap(1, 1, 1)
                                .addComponent(jComboSelecionaMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus132)
                                .addGap(1, 1, 1)
                                .addComponent(jTextPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus133)
                                .addGap(1, 1, 1)
                                .addComponent(jTextPressao, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelValorCus134)
                                    .addComponent(LabelValorCus143))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextAltura, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextUltimaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(LabelValorCus129)
                                    .addGap(37, 37, 37))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(LabelValorCus123)
                                                .addGap(1, 1, 1)
                                                .addComponent(jComboSelecionaTipoConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jComboSelecionaConvenio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(LabelValorCus53)
                                        .addComponent(jTextValorConsulta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextValorDesconto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(LabelValorCus130)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jDateChooserCalendario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelValorCus122)
                                .addGap(1, 1, 1)
                                .addComponent(jComboSelecionaHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Atestado Médico / Diagnostico / Exames / Prescrição Médica ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jTextDiagnostico.setColumns(20);
        jTextDiagnostico.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jTextDiagnostico.setRows(5);
        jTextDiagnostico.setEnabled(false);
        jScrollPane2.setViewportView(jTextDiagnostico);

        LabelValorCus135.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus135.setText("Diagnóstico:");

        LabelValorCus136.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus136.setText("Prescrição:");

        jTextPrescricao.setColumns(20);
        jTextPrescricao.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jTextPrescricao.setRows(5);
        jTextPrescricao.setEnabled(false);
        jScrollPane3.setViewportView(jTextPrescricao);

        LabelValorCus137.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus137.setText("Exames Solicitados:");

        jButtonNovoExame.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonNovoExame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add2.png"))); // NOI18N
        jButtonNovoExame.setEnabled(false);
        jButtonNovoExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoExameActionPerformed(evt);
            }
        });

        jButtonExcluirExame.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonExcluirExame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/close24x24.png"))); // NOI18N
        jButtonExcluirExame.setEnabled(false);
        jButtonExcluirExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirExameActionPerformed(evt);
            }
        });

        jTextAtestado.setColumns(20);
        jTextAtestado.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jTextAtestado.setRows(5);
        jTextAtestado.setEnabled(false);
        jScrollPane5.setViewportView(jTextAtestado);

        LabelValorCus138.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus138.setText("Atestado Médico:");

        jComboTipoAtestado.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboTipoAtestado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Dispensa Atestado", "1. Óbito", "2. Doença", "3. Repouso à Gestantes", "4. Acidente de Trabalho", "5. Fins de Interdição", "6. Aptidão Física", "7. Sanidade Física e Mental", "8. Amamentação", "9. Comparecimento", "10. Internação" }));
        jComboTipoAtestado.setToolTipText("");
        jComboTipoAtestado.setEnabled(false);
        jComboTipoAtestado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboTipoAtestadoActionPerformed(evt);
            }
        });

        LabelValorCus139.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus139.setText("Quantidade de dias:");

        jTextQtdeDias.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextQtdeDias.setEnabled(false);
        jTextQtdeDias.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextQtdeDias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextQtdeDiasActionPerformed(evt);
            }
        });

        LabelValorCus140.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus140.setText("Tipo de Atestado:");

        jTableExame.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Descrição", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableExame.setEnabled(false);
        jScrollPane6.setViewportView(jTableExame);

        jButtonImprimirPrescricao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonImprimirPrescricao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/print.png"))); // NOI18N
        jButtonImprimirPrescricao.setEnabled(false);
        jButtonImprimirPrescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirPrescricaoActionPerformed(evt);
            }
        });

        jButtonImprimirAtestado.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonImprimirAtestado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/print.png"))); // NOI18N
        jButtonImprimirAtestado.setEnabled(false);
        jButtonImprimirAtestado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirAtestadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus136)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(jButtonImprimirPrescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jScrollPane6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonNovoExame, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonExcluirExame, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(LabelValorCus137)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus135)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelValorCus138))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboTipoAtestado, 0, 226, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LabelValorCus139, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus140, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextQtdeDias, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(3, 3, 3)
                                .addComponent(jButtonImprimirAtestado, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(5, 5, 5))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelValorCus135)
                    .addComponent(LabelValorCus138)
                    .addComponent(LabelValorCus140))
                .addGap(1, 1, 1)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jComboTipoAtestado, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(LabelValorCus139)
                                .addGap(1, 1, 1)
                                .addComponent(jTextQtdeDias, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonImprimirAtestado, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelValorCus136)
                    .addComponent(LabelValorCus137))
                .addGap(1, 1, 1)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                        .addGap(5, 5, 5))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jButtonNovoExame, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonExcluirExame, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonImprimirPrescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        jTabbedPane1.addTab("  Cadastro Consulta  ", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPesquisaActionPerformed
        try {
            if (!jTextPesquisa.getText().equals("")) {
                getConsultaPesquisa();
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, informe um dado para pesquisa!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro com o banco de dados!\n" + ex, "Erro", JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextPesquisaActionPerformed

    private void jButtonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisarActionPerformed

        try {
            if (!jTextPesquisa.getText().equals("")) {
                getConsultaPesquisa();
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, informe um dado para pesquisa!", "Erro", JOptionPane.WARNING_MESSAGE);
            }

            //        DefaultTableModel model = (DefaultTableModel) jTableCliente.getModel();
            //        if (model.getRowCount() > 0) {
            //            while (model.getRowCount() > 0) {
            //                model.removeRow(0); // limpa a tabela
            //            }
            //        }
            //        try {           // adiciona as tabelas de acordo com a String selecionada no jComboBox
            //
            //            String terminal = jComboSelecionaTipoPesquisa.getSelectedItem() + ""; // transforma o jComboBox em uma String
            //            try {
            //                if (terminal.equals("Código")) {    // compara para ver se a String e o codigo
            //                    if (!clienteDAO.getCliente(Integer.parseInt(jTextPesquisar.getText() + "")).isEmpty()) { //testa para ver se existem clientes com o cpodigo informado
            //                        for (int i = 0; i < clienteDAO.numeroLinhas; i++) {
            //                            model.addRow(new String[]{jTextPesquisar.getText() + "", clienteDAO.getCliente(Integer.parseInt(jTextPesquisar.getText() + "")).get(i).getNome(), clienteDAO.getCliente(Integer.parseInt(jTextPesquisar.getText() + "")).get(i).getLogradouro(), clienteDAO.getCliente(Integer.parseInt(jTextPesquisar.getText() + "")).get(i).getT1(), clienteDAO.getCliente(Integer.parseInt(jTextPesquisar.getText() + "")).get(i).getStatus()});
            //                        }
            //                    } else {
            //                        JOptionPane.showMessageDialog(null, "Não existem clientes com o código " + jTextPesquisar.getText() + " cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            //                    }
            //                }
            //            } catch (Exception e) {
            //                JOptionPane.showMessageDialog(null, "Não foi possível executar a pesquisa, por favor, rivise os dados e tente novamente!", "Erro", JOptionPane.WARNING_MESSAGE);
            //
            //            }
            //            if (terminal.equals("Nome")) {
            //                if (!clienteDAO.getCliente(jTextPesquisar.getText() + "", null, null).isEmpty()) {
            //                    for (int i = 0; i < clienteDAO.numeroLinhas; i++) {
            //                        model.addRow(new String[]{clienteDAO.getCliente(jTextPesquisar.getText() + "", null, null).get(i).getCodigo() + "", clienteDAO.getCliente(jTextPesquisar.getText() + "", null, null).get(i).getNome(), clienteDAO.getCliente(jTextPesquisar.getText() + "", null, null).get(i).getLogradouro(), clienteDAO.getCliente(jTextPesquisar.getText() + "", null, null).get(i).getT1(), clienteDAO.getCliente(jTextPesquisar.getText() + "", null, null).get(i).getStatus()});
            //                    }
            //                } else {
            //                    JOptionPane.showMessageDialog(null, "Não existem clientes com o nome '" + jTextPesquisar.getText() + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            //                }
            //                //clienteDAO.getCliente(jTextPesquisar.getText() + "", null, null).get(0).getCodigo();
            //            }
            //            if (terminal.equals("RG")) {
            //                if (!clienteDAO.getCliente(null, null, jTextPesquisar.getText() + "").isEmpty()) {
            //                    System.out.println(clienteDAO.getCliente(null, null, jTextPesquisar.getText() + "").get(0).getNome());
            //
            //                    model.addRow(new String[]{clienteDAO.getCliente(null, null, jTextPesquisar.getText() + "").get(0).getCodigo() + "", clienteDAO.getCliente(null, null, jTextPesquisar.getText() + "").get(0).getNome(), clienteDAO.getCliente(null, null, jTextPesquisar.getText() + "").get(0).getLogradouro(), clienteDAO.getCliente(null, null, jTextPesquisar.getText() + "").get(0).getT1(), clienteDAO.getCliente(null, null, jTextPesquisar.getText() + "").get(0).getStatus()});
            //
            //                } else {
            //                    JOptionPane.showMessageDialog(null, "Não existem clientes com o RG '" + jTextPesquisar.getText() + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            //
            //                }
            //                //clienteDAO.getCliente(null, null, jTextPesquisar.getText() + "");
            //            }
            //
            //            if (terminal.equals("CPF")) {
            //
            //                if (!clienteDAO.getCliente(null, jTextPesquisar.getText() + "", null).isEmpty()) {
            //                    for (int i = 0; i < clienteDAO.numeroLinhas; i++) {
            //                        model.addRow(new String[]{clienteDAO.getCliente(null,jTextPesquisar.getText() + "",null).get(i).getCodigo() + "", clienteDAO.getCliente(null,jTextPesquisar.getText() + "",null).get(i).getNome(), clienteDAO.getCliente(null,jTextPesquisar.getText() + "",null).get(i).getLogradouro(), clienteDAO.getCliente(null,jTextPesquisar.getText() + "",null).get(i).getT1(), clienteDAO.getCliente(null,jTextPesquisar.getText() + "",null).get(i).getStatus()});
            //                    }
            //                } else {
            //                    JOptionPane.showMessageDialog(null, "Não existem clientes com o CPF '" + jTextPesquisar.getText() + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            //
            //                }
            //                //clienteDAO.getCliente(null, jTextPesquisar.getText() + "", null);
            //            }
            //        } catch (SQLException ex) {
            //            Logger.getLogger(ClienteNew.class.getName()).log(Level.SEVERE, null, ex);
            //            JOptionPane.showMessageDialog(null, "Não foi possível executar a pesquisa, por favor, rivise os dados e tente novamente!", "Erro", JOptionPane.WARNING_MESSAGE);
            //        }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro com o banco de dados!\n" + ex, "Erro", JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonPesquisarActionPerformed

    private void jButtonListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListarActionPerformed

        /*List lista = new ArrayList();
         try {
         if (!consultaDAO.getConsultaPesquisaUser("").isEmpty()) {
         for (int i = 0; i < consultaDAO.getConsultaPesquisaUser("").size(); i++) {
         ConsultaBean c = new ConsultaBean();
         c.setCodigo(consultaDAO.getConsultaPesquisaUser("").get(i).getCodigo());
         c.setPacienteNome(consultaDAO.getConsultaPesquisaUser("").get(i).getPacienteNome());
         c.setFuncionarioNome(consultaDAO.getConsultaPesquisaUser("").get(i).getFuncionarioNome());
         c.setDtConsulta(consultaDAO.getConsultaPesquisaUser("").get(i).getDtConsulta());
         c.setHorario(null);
         c.setTipoConsultaNome(consultaDAO.getConsultaPesquisaUser("").get(i).getTipoConsultaNome());
         c.setNumeroFicha(consultaDAO.getConsultaPesquisaUser("").get(i).getNumeroFicha());
         c.setSigla(consultaDAO.getConsultaPesquisaUser("").get(i).getSigla());
         c.setTipo(consultaDAO.getConsultaPesquisaUser("").get(i).getTipo());
         c.setStatus(consultaDAO.getConsultaPesquisaUser("").get(i).getStatus());
         c.setReqExame(consultaDAO.reqExames(0));
         lista.add(c);
         }
         modelConsulta = new ConsultaTableModel(lista);
         jTablePesquisa.setModel(modelConsulta);
         DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
         esquerda.setHorizontalAlignment(SwingConstants.LEFT);
         jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
         jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
         jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
         jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
         jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
         } else {
         JOptionPane.showMessageDialog(null, "Nenhuma consulta cadastrada!", "Erro", JOptionPane.WARNING_MESSAGE);

         }
         } catch (SQLException ex) {
         Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
         }*/
        this.getConsultasDia();

    }//GEN-LAST:event_jButtonListarActionPerformed

    private void jButtonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoActionPerformed
        editar = false;
        revisao = false;
        this.jButtonNovo.setEnabled(false);
        flag2 = false;

        if (funcao == user) {
            ativaCampoUser();
        } else if (funcao == root) {
            ativaCampoRoot();
        }
        this.limpaCampo();
        this.jButtonGravar.setEnabled(true);
        this.jButtonEditar.setEnabled(false);
        this.jButtonExcluir.setEnabled(false);
        this.setNovoCodigo();
        this.jButtonCancelar.setEnabled(true);
        jTabbedPane1.setEnabledAt(0, false);
    }//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        jButtonVisualizarConculta.setEnabled(false);
        try {
            //        this.ativaCampo();
            //        this.jButtonGravar.setEnabled(true);
            if (funcao == user) {
                if (!consultaDAO.getConsultaUser((int) jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0)).get(0).isFoiChamado()) {
                    this.ativaCampoUser();
                    this.jButtonGravar.setEnabled(true);
                    this.jButtonExcluir.setEnabled(true);
                    this.jButtonCancelar.setEnabled(true);
                    this.jButtonEditar.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Essa consulta já foi chamada!", "ERRO", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                this.ativaCampoRoot();

                this.jButtonGravar.setEnabled(true);
                this.jButtonExcluir.setEnabled(true);
                this.jButtonCancelar.setEnabled(true);
                this.jButtonEditar.setEnabled(false);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        try {
            int codigo = Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + "");
            if (JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir essa consulta?\nEsses dados serão perdidos!", "Excluir",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                consultaDAO.deletaConsulta(codigo);
                modelConsulta.limpaTabela();
                desativaCampo();
                limpaCampo();
                this.jButtonGravar.setEnabled(false);
                this.jButtonNovo.setEnabled(true);
                this.jButtonCancelar.setEnabled(false);
                this.jButtonEditar.setEnabled(false);
                this.jButtonExcluir.setEnabled(false);
                jTabbedPane1.setEnabledAt(0, true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada na tabela!\n" + e, "Erro", JOptionPane.WARNING_MESSAGE);
        }
        editar = false;
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGravarActionPerformed
        if (jComboSelecionaMedico.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && !jTextCodPaciente.getText().equals("")) {
            if (jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != -1) {
                String atual, separado;
                int codTipoConsulta = 0, codConvenio = 0, codPaciente = 0;
                int posicao = 0;
                //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
                if (jComboSelecionaConvenio.getSelectedIndex() != 0 && jComboSelecionaConvenio.getSelectedIndex() != -1) {
                    atual = jComboSelecionaConvenio.getSelectedItem() + "";
                    char[] a = atual.toCharArray();
                    for (int i = 0; i < atual.length(); i++) {
                        if (a[i] == '-') {
                            posicao = i;
                        }
                    }
                    separado = atual.substring(0, posicao);
                    codConvenio = Integer.parseInt(separado);
                }
                //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
                if (jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != -1) {
                    atual = jComboSelecionaTipoConsulta.getSelectedItem() + "";// FAZ O MESMO PARA CONSULTA
                    posicao = 0;
                    char[] a = atual.toCharArray();
                    for (int i = 0; i < atual.length(); i++) {
                        if (a[i] == '-') {
                            posicao = i;
                        }
                    }
                    separado = atual.substring(0, posicao);
                    codTipoConsulta = Integer.parseInt(separado);
                }
                //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
                if (!jTextCodPaciente.getText().equals("")) {
                    codPaciente = Integer.parseInt(jTextCodPaciente.getText());
                }
                if (!flag) {
                    if (revisao) {
                        gravar(0, 0);
                    } else {
                        DecimalFormat formatoDois = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
                        formatoDois.setMinimumFractionDigits(2);
                        formatoDois.setParseBigDecimal(true);
                        try {
                            if (jComboSelecionaConvenio.getSelectedIndex() == 0) {
                                jTextValorConsulta.setEnabled(false);
                                jTextValorConsulta.setEditable(false);
                                PagamentoConsultaNew pagamentoConsultaNew = new PagamentoConsultaNew();
                                pagamentoConsultaNew.setLocationRelativeTo(null);
                                pagamentoConsultaNew.setVisible(true);
                                pagamentoConsultaNew.setResizable(false);
                                pagamentoConsultaNew.enviaPalavra(this, 0, false, Double.parseDouble(jTextValorConsulta.getText().replace(',', '.')), adicionalExames);
                            } else {
                                if (consultaDAO.getPorcConvenio(codConvenio) == 0) {
                                    double valorDesconto = 0;
                                    jTextValorConsulta.setEnabled(false);
                                    jTextValorConsulta.setEditable(false);
                                    PagamentoConsultaNew pagamentoConsultaNew = new PagamentoConsultaNew();
                                    pagamentoConsultaNew.setLocationRelativeTo(null);
                                    pagamentoConsultaNew.setVisible(true);
                                    pagamentoConsultaNew.setResizable(false);
                                    pagamentoConsultaNew.enviaPalavra(this, 0, true, consultaDAO.getValorConfConsulta(codTipoConsulta), adicionalExames);
                                } else {
                                    jTextValorConsulta.setEnabled(false);
                                    jTextValorConsulta.setEditable(false);
                                    PagamentoConsultaNew pagamentoConsultaNew = new PagamentoConsultaNew();
                                    pagamentoConsultaNew.setLocationRelativeTo(null);
                                    pagamentoConsultaNew.setVisible(true);
                                    pagamentoConsultaNew.setResizable(false);
                                    pagamentoConsultaNew.enviaPalavra(this, Double.parseDouble(jTextValorDesconto.getText().replace(',', '.')), true, Double.parseDouble(jTextValorConsulta.getText().replace(',', '.')), adicionalExames);
                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {//atualização
                    gravar(Double.parseDouble(jTextValorConsulta.getText().replace(',', '.')), Double.parseDouble(jTextValorDesconto.getText().replace(',', '.')));
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Dados incompletos", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        jButtonVisualizarConculta.setEnabled(false);
    }//GEN-LAST:event_jButtonGravarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        jTabbedPane1.setEnabledAt(0, true);
        this.jButtonNovo.setEnabled(true);
        this.jButtonCancelar.setEnabled(false);
        this.jButtonGravar.setEnabled(false);
        this.jButtonExcluir.setEnabled(false);
        this.jButtonEditar.setEnabled(false);
        this.limpaCampo();
        this.desativaCampo();
        jButtonVisualizarConculta.setEnabled(false);
        try {
            jv.setVisible(false);
        } catch (NullPointerException ex) {

        }
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextCodPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCodPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCodPacienteActionPerformed

    private void jButtonAdicionarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarPacienteActionPerformed
        jButtonAdicionarPaciente.setEnabled(false);
        String nome = JOptionPane.showInputDialog(null, "Informe o nome ou código para pesquisa:", "Inserir paciente", 3);
        if (nome != null) {
            try {
                int codigo = Integer.parseInt(nome);
                this.getPaciente(codigo);
                System.out.println(consultaDAO.getTesteRevisao(codigo));
                if (consultaDAO.getTesteRevisao(codigo) && !flag) {
                    if (JOptionPane.showConfirmDialog(null, "Paciente realizou consulta no tempo previsto, deseja marcar como revisão?", "Retorno", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        jComboSelecionaConvenio.setSelectedItem(consultaDAO.getConvenioRevisao(codigo));
                        jComboSelecionaTipoConsulta.setSelectedItem(consultaDAO.getTipoConsultaRevisao(codigo));
                        jComboSelecionaMedico.setSelectedItem(consultaDAO.getMedicoRevisao(codigo));
                        jComboSelecionaConvenio.setEnabled(false);
                        jComboSelecionaTipoConsulta.setEnabled(false);
                        jComboSelecionaMedico.setEnabled(false);
                        jTextValorDesconto.setText("0,00");
                        jTextValorConsulta.setText("0,00");
                        jTextValorDesconto.setEnabled(false);
                        jTextValorConsulta.setEnabled(false);

                        revisao = true;
                    }
                } else {
                    jComboSelecionaConvenio.setSelectedIndex(0);
                    jComboSelecionaTipoConsulta.setSelectedIndex(0);
                    jComboSelecionaConvenio.setEnabled(true);
                    jComboSelecionaTipoConsulta.setEnabled(true);

                }
            } catch (Exception e) {
                CadastroPacienteNew cadastroPacienteNew = new CadastroPacienteNew(0);
                cadastroPacienteNew.setLocationRelativeTo(null);
                cadastroPacienteNew.setVisible(true);
                cadastroPacienteNew.setResizable(false);
                cadastroPacienteNew.enviaPalavra(this, null, nome, 1);
                cadastroPacienteNew.jButtonAdicionar.setEnabled(false);
                cadastroPacienteNew.jTabbedPaneCadastro.setEnabledAt(0, true);
                cadastroPacienteNew.jTabbedPaneCadastro.setEnabledAt(1, false);
                cadastroPacienteNew.jTabbedPaneCadastro.setEnabledAt(2, false);
                try {
                    if (consultaDAO.getTesteRevisao(Integer.parseInt(jTextCodPaciente.getText())) && !flag) {
                        if (JOptionPane.showConfirmDialog(null, "Paciente realizou consulta no tempo previsto, deseja marcar como revisão?", "Retorno", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            jComboSelecionaConvenio.setSelectedItem(consultaDAO.getConvenioRevisao(Integer.parseInt(jTextCodPaciente.getText())));
                            jComboSelecionaTipoConsulta.setSelectedItem(consultaDAO.getTipoConsultaRevisao(Integer.parseInt(jTextCodPaciente.getText())));
                            jComboSelecionaMedico.setSelectedItem(consultaDAO.getMedicoRevisao(Integer.parseInt(jTextCodPaciente.getText())));
                            jComboSelecionaConvenio.setEnabled(false);
                            jComboSelecionaTipoConsulta.setEnabled(false);
                            jComboSelecionaMedico.setEnabled(false);
                            jTextValorDesconto.setText("0,00");
                            jTextValorConsulta.setText("0,00");
                            revisao = true;
                        }
                    } else {
                        jComboSelecionaConvenio.setSelectedIndex(0);
                        jComboSelecionaTipoConsulta.setSelectedIndex(0);
                        jComboSelecionaConvenio.setEnabled(true);
                        jComboSelecionaTipoConsulta.setEnabled(true);

                    }
                } catch (NumberFormatException ex) {

                }

            }
        } else {
            jButtonAdicionarPaciente.setEnabled(true);
            jTextCodPaciente.grabFocus();
        }
    }//GEN-LAST:event_jButtonAdicionarPacienteActionPerformed

    private void jTextNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNomeActionPerformed

    private void jTextTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextTelefoneActionPerformed

    private void jTextEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextEmailActionPerformed

    private void jTextValorConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextValorConsultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextValorConsultaActionPerformed

    private void jTextValorDescontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextValorDescontoActionPerformed

    }//GEN-LAST:event_jTextValorDescontoActionPerformed

    private void jTextPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPesoActionPerformed

    private void jTextPressaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPressaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPressaoActionPerformed

    private void jTextAlturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextAlturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextAlturaActionPerformed

    private void jButtonNovoExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoExameActionPerformed
        jButtonNovoExame.setEnabled(false);
        jButtonExcluirExame.setEnabled(false);
        String nome = JOptionPane.showInputDialog(null, "Informe o código ou nome do exame para pesquisa:", "Informar dados", JOptionPane.QUESTION_MESSAGE);
        if (nome != null) {
            try {
                int codigo = Integer.parseInt(nome);
                this.getTipoExame(codigo);
                jButtonNovoExame.setEnabled(true);
                jButtonExcluirExame.setEnabled(true);
            } catch (Exception e) {
                CadastroTipoExameNew cadastroTipoExameNew = new CadastroTipoExameNew();
                this.cadastroTipoExameNew = cadastroTipoExameNew;
                cadastroTipoExameNew.setLocationRelativeTo(null);
                cadastroTipoExameNew.setVisible(true);
                cadastroTipoExameNew.setResizable(false);
                cadastroTipoExameNew.enviaPalavra(this, null, nome, 2, 2);
                cadastroTipoExameNew.jButtonAdicionar.setEnabled(false);
                cadastroTipoExameNew.jTabbedPane1.setEnabledAt(0, true);
                cadastroTipoExameNew.jTabbedPane1.setEnabledAt(1, false);

            }
        } else {
            jButtonNovoExame.setEnabled(true);
            jButtonExcluirExame.setEnabled(true);
        }
    }//GEN-LAST:event_jButtonNovoExameActionPerformed

    private void jButtonExcluirExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirExameActionPerformed
        try {
            if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover '" + model.getValueAt(jTableExame.getSelectedRow(), 1) + "'?", "Remover exame", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int linha = jTableExame.getSelectedRow();
                adicionalExames = adicionalExames - (double) model.getValueAt(jTableExame.getSelectedRow(), 3);
                model.excluir(linha);
                listaTipoExame.remove(linha);
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonExcluirExameActionPerformed

    private void jTextQtdeDiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextQtdeDiasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextQtdeDiasActionPerformed

    private void jComboSelecionaTipoConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboSelecionaTipoConsultaActionPerformed

        if (jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != -1) {
            String atual, separado;
            int codTipoConsulta = 0, codConvenio = 0, codPaciente = 0;
            int posicao = 0;
            //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            if (jComboSelecionaConvenio.getSelectedIndex() != 0 && jComboSelecionaConvenio.getSelectedIndex() != -1) {
                atual = jComboSelecionaConvenio.getSelectedItem() + "";
                char[] a = atual.toCharArray();
                for (int i = 0; i < atual.length(); i++) {
                    if (a[i] == '-') {
                        posicao = i;
                    }
                }
                separado = atual.substring(0, posicao);
                codConvenio = Integer.parseInt(separado);
            }
            //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            if (jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != -1) {
                atual = jComboSelecionaTipoConsulta.getSelectedItem() + "";// FAZ O MESMO PARA CONSULTA
                posicao = 0;
                char[] a = atual.toCharArray();
                for (int i = 0; i < atual.length(); i++) {
                    if (a[i] == '-') {
                        posicao = i;
                    }
                }
                separado = atual.substring(0, posicao);
                codTipoConsulta = Integer.parseInt(separado);
            }
            //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            if (!jTextCodPaciente.getText().equals("")) {
                codPaciente = Integer.parseInt(jTextCodPaciente.getText());
            }

            //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*AQUI OS CÓDIGOS ESTÃO FUNCIONADO!!!
            try {
                DecimalFormat formatoDois = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
                formatoDois.setMinimumFractionDigits(2);
                formatoDois.setParseBigDecimal(true);
                if (jComboSelecionaConvenio.getSelectedIndex() != -1 && jComboSelecionaConvenio.getSelectedIndex() != 0) {
                    double desconto = 0;
                    if (flag2) {
                        jTextValorConsulta.setText(consultaDAO.getConsultaUser((int) jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0)).get(0).getValorConsulta() + "");
                        jTextValorDesconto.setText(consultaDAO.getConsultaUser((int) jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0)).get(0).getConvenioDesconto() + "");
                        //flag2 = false;
                    } else {
                        jTextValorDesconto.setText("0,00");
                        jTextValorConsulta.setText(truncateDecimal((consultaDAO.getValorConsulta(codTipoConsulta) - desconto), 2).toString().replace('.', ','));
                        if (consultaDAO.getPorcConvenio(codConvenio) == 0) {
                            double valorDesconto = 0;
                            //jTextValorDesconto.setEnabled(true);
                            //jTextValorDesconto.setEditable(true);
                            //jTextValorDesconto.setText("");
                            //jTextValorDesconto.requestFocus();
                            jTextValorDesconto.setText("0,00");
                            jTextValorConsulta.setEnabled(true);
                            jTextValorConsulta.setEditable(true);
                            jTextValorConsulta.setText("");
                            jTextValorConsulta.requestFocus();
                        } else {
                            jTextValorDesconto.setEditable(false);
                            jTextValorDesconto.setText(formatoDois.format(consultaDAO.getPorcConvenio(codConvenio)));
                            desconto = consultaDAO.getPorcConvenio(codConvenio);
                            jTextValorConsulta.setText(truncateDecimal((consultaDAO.getValorConsulta(codTipoConsulta) - desconto), 2).toString().replace('.', ','));
                            jTextValorDesconto.setText(formatoDois.format(desconto) + "");
                        }
                    }
                } else {
                    jTextValorDesconto.setText("0.00");
                    jTextValorConsulta.setText(formatoDois.format(consultaDAO.getValorConsulta(codTipoConsulta)) + "");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro!\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        /*jComboSelecionaConvenio.setSelectedIndex(0);
         jTextValorConsulta.setText("0,00");
         jTextValorDesconto.setText("0,00");
         /* if (jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != -1) {
         jComboSelecionaConvenio.setEnabled(true);
         String atual, separado;
         int codTipoConsulta = 0, codConvenio = 0, codPaciente = 0;
         int posicao = 0;
         //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
         if (jComboSelecionaConvenio.getSelectedIndex() != 0 && jComboSelecionaConvenio.getSelectedIndex() != -1) {
         atual = jComboSelecionaConvenio.getSelectedItem() + "";
         char[] a = atual.toCharArray();
         for (int i = 0; i < atual.length(); i++) {
         if (a[i] == '-') {
         posicao = i;
         }
         }
         separado = atual.substring(0, posicao);
         codConvenio = Integer.parseInt(separado);
         }
         //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
         if (jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != -1) {
         atual = jComboSelecionaTipoConsulta.getSelectedItem() + "";// FAZ O MESMO PARA CONSULTA
         posicao = 0;
         char[] a = atual.toCharArray();
         for (int i = 0; i < atual.length(); i++) {
         if (a[i] == '-') {
         posicao = i;
         }
         }
         separado = atual.substring(0, posicao);
         codTipoConsulta = Integer.parseInt(separado);
         }
         //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
         if (!jTextCodPaciente.getText().equals("")) {
         codPaciente = Integer.parseInt(jTextCodPaciente.getText());
         }

         //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*AQUI OS CÓDIGOS ESTÃO FUNCIONADO!!!
         try {
         DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
         formatoDois.setMinimumFractionDigits(2);
         formatoDois.setParseBigDecimal(true);
                
         if (jComboSelecionaConvenio.getSelectedIndex() != -1 && jComboSelecionaConvenio.getSelectedIndex() != 0) {
         /*double desconto = 0;
         if (consultaDAO.getPorcConvenio(codConvenio) == 0) { /// retorna o valor que será descontando ao inves de porcentagem , erro de documentação!!!
         if (jTextValorDesconto.getText().equals("")) {
         double valorDesconto = 0;
         while (valorDesconto == 0) {
         String valor = JOptionPane.showInputDialog(null, "Informe o valor do desconto:", "Valor", JOptionPane.QUESTION_MESSAGE).replace(',', '.');
         if (valor == null) { // botão cancelar
         break;
         }
         try {
         valorDesconto = Double.parseDouble(valor);
         desconto = valorDesconto;
         } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
         }
         }
         jTextValorDesconto.setText(formatoDois.format(desconto) + "");
         }
         } else {
         //desconto = (consultaDAO.getValorConsulta(codTipoConsulta) * consultaDAO.getPorcConvenio(codConvenio)) / 100;
         desconto = consultaDAO.getPorcConvenio(codConvenio);
         }
         jTextValorConsulta.setText(formatoDois.format(consultaDAO.getValorConsulta(codTipoConsulta)));
                    
         } else {
         jTextValorDesconto.setText("0,00");
         //jTextValorConsulta.setText(formatoDois.format(consultaDAO.getValorConsulta(codTipoConsulta) + ""));       
         jTextValorConsulta.setText(formatoDois.format(consultaDAO.getValorConsulta(codTipoConsulta)) + "");
         }
         } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Erro!\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
         }
         } else {
         jComboSelecionaConvenio.setEnabled(false);
         }*/

    }//GEN-LAST:event_jComboSelecionaTipoConsultaActionPerformed

    private void jTextRgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextRgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextRgActionPerformed

    private void jTextCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCpfActionPerformed

    private void jTextSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextSexoActionPerformed

    private void jComboSelecionaTipoPesquisa13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboSelecionaTipoPesquisa13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboSelecionaTipoPesquisa13ActionPerformed

    private void jComboSelecionaMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboSelecionaMedicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboSelecionaMedicoActionPerformed

    private void jTablePesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePesquisaMouseClicked
        editar = true;
        jTabbedPane1.setSelectedIndex(1);
        jTabbedPane1.grabFocus();
        jTabbedPane1.setEnabledAt(0, false);
        jTabbedPane1.setEnabledAt(1, true);
        jButtonEditar.setEnabled(true);
        jButtonNovo.setEnabled(false);
        jButtonCancelar.setEnabled(true);
        jButtonExcluir.setEnabled(false);
        jButtonVisualizarConculta.setEnabled(true);

    }//GEN-LAST:event_jTablePesquisaMouseClicked

    private void jComboSelecionaConvenioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboSelecionaConvenioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboSelecionaConvenioKeyPressed

    private void jComboSelecionaConvenioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboSelecionaConvenioActionPerformed
        DecimalFormat formatoDois1 = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois1.setMinimumFractionDigits(2);
        formatoDois1.setParseBigDecimal(true);
        if (jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != -1) {
            String atual, separado;
            int codTipoConsulta = 0, codConvenio = 0, codPaciente = 0;
            int posicao = 0;
            //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            if (jComboSelecionaConvenio.getSelectedIndex() != 0 && jComboSelecionaConvenio.getSelectedIndex() != -1) {
                atual = jComboSelecionaConvenio.getSelectedItem() + "";
                char[] a = atual.toCharArray();
                for (int i = 0; i < atual.length(); i++) {
                    if (a[i] == '-') {
                        posicao = i;
                    }
                }
                separado = atual.substring(0, posicao);
                codConvenio = Integer.parseInt(separado);
            } else {
                //mechi aqui coloquei este if
                jTextValorDesconto.setText("0.00");
                try {
                    jTextValorConsulta.setText(formatoDois1.format(consultaDAO.getValorConsulta(codTipoConsulta)) + "");
                } catch (SQLException ex) {
                   // Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
                }
                //
            }
            //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            if (jComboSelecionaTipoConsulta.getSelectedIndex() != 0 && jComboSelecionaTipoConsulta.getSelectedIndex() != -1) {
                atual = jComboSelecionaTipoConsulta.getSelectedItem() + "";// FAZ O MESMO PARA CONSULTA
                posicao = 0;
                char[] a = atual.toCharArray();
                for (int i = 0; i < atual.length(); i++) {
                    if (a[i] == '-') {
                        posicao = i;
                    }
                }
                separado = atual.substring(0, posicao);
                codTipoConsulta = Integer.parseInt(separado);
            }
            //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            if (!jTextCodPaciente.getText().equals("")) {
                codPaciente = Integer.parseInt(jTextCodPaciente.getText());
            }

            //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*AQUI OS CÓDIGOS ESTÃO FUNCIONADO!!!
            try {
                DecimalFormat formatoDois = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
                formatoDois.setMinimumFractionDigits(2);
                formatoDois.setParseBigDecimal(true);
                if (jComboSelecionaConvenio.getSelectedIndex() != -1 && jComboSelecionaConvenio.getSelectedIndex() != 0) {
                    double desconto;
                    if (flag2) {
                        jTextValorConsulta.setText(consultaDAO.getConsultaUser((int) jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0)).get(0).getValorConsulta() + "");
                        jTextValorDesconto.setText(consultaDAO.getConsultaUser((int) jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0)).get(0).getConvenioDesconto() + "");
                        flag2 = false;
                    } else {
                        if (consultaDAO.getPorcConvenio(codConvenio) == 0) {
                            double valorDesconto = 0;
                            //jTextValorDesconto.setEnabled(true);
                            //jTextValorDesconto.setEditable(true);
                            //jTextValorDesconto.setText("");
                            //jTextValorDesconto.requestFocus();
                            jTextValorDesconto.setText("0,00");
                            jTextValorConsulta.setText("$");
                            //jTextValorConsulta.setEnabled(true);
                            //jTextValorConsulta.setEditable(true);
                            ///jTextValorConsulta.setText("");
                            //jTextValorConsulta.requestFocus();
                        } else {
                            jTextValorDesconto.setEditable(false);
                            jTextValorDesconto.setText(formatoDois.format(consultaDAO.getPorcConvenio(codConvenio)));
                            desconto = consultaDAO.getPorcConvenio(codConvenio);
                            jTextValorConsulta.setText(truncateDecimal((consultaDAO.getValorConsulta(codTipoConsulta) - desconto), 2).toString().replace('.', ','));
                            jTextValorDesconto.setText(formatoDois.format(desconto) + "");
                        }
                    }
                } else {
                    jTextValorDesconto.setText("0.00");
                    jTextValorConsulta.setText(formatoDois.format(consultaDAO.getValorConsulta(codTipoConsulta)) + "");
                }
            } catch (Exception e) {
                Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, e);
                JOptionPane.showMessageDialog(null, "Erro!\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (jComboSelecionaConvenio.getSelectedIndex() == 0) {
            jTextValorDesconto.setText("0.00");
            jTextValorDesconto.setEditable(false);
        }

    }//GEN-LAST:event_jComboSelecionaConvenioActionPerformed

    private void jComboTipoAtestadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboTipoAtestadoActionPerformed
        if (editar) {
            if (jComboTipoAtestado.getSelectedIndex() == 0) {
                jTextQtdeDias.setEnabled(false);
                jTextAtestado.setEnabled(false);
                jTextAtestado.setText("");
                jTextQtdeDias.setText("");
            }
        } else {
            if (jComboTipoAtestado.getSelectedIndex() == 0) {
                jTextAtestado.setText("");
                jTextQtdeDias.setText("");
                jTextQtdeDias.setEnabled(false);
                jTextAtestado.setEnabled(false);
            } else {
                jTextQtdeDias.setEnabled(true);
                jTextAtestado.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jComboTipoAtestadoActionPerformed

    private void jButtonImprimirPrescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirPrescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonImprimirPrescricaoActionPerformed

    private void jButtonImprimirAtestadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirAtestadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonImprimirAtestadoActionPerformed

    private void jButtonVisualizarConcultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVisualizarConcultaActionPerformed

        try {
            HashMap hm = new HashMap();
            hm.put("inv_no", selecionado);
            hm.put("SUBREPORT_DIR", "Ireport/RelatorioConcultas_subreportConcultas.jasper");
            JasperPrint jpPrint = JasperFillManager.fillReport("Ireport/RelatorioConcultas.jasper", hm, conexao);
            // JasperViewer jv = new JasperViewer(jpPrint);  
            jv = new JasperViewer(jpPrint, false);
            // jv.setDefaultCloseOperation(jv.DISPOSE_ON_CLOSE);
            jv.setTitle("  Prontuario Médico  ");
            jv.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonVisualizarConcultaActionPerformed

    private void jTextUltimaConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextUltimaConsultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextUltimaConsultaActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            jv.setVisible(false);
        } catch (NullPointerException ex) {

        }
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(CadastroConsultaNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroConsultaNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroConsultaNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroConsultaNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroConsultaNew(1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelValorCus122;
    private javax.swing.JLabel LabelValorCus123;
    private javax.swing.JLabel LabelValorCus125;
    private javax.swing.JLabel LabelValorCus126;
    private javax.swing.JLabel LabelValorCus127;
    private javax.swing.JLabel LabelValorCus128;
    private javax.swing.JLabel LabelValorCus129;
    private javax.swing.JLabel LabelValorCus130;
    private javax.swing.JLabel LabelValorCus131;
    private javax.swing.JLabel LabelValorCus132;
    private javax.swing.JLabel LabelValorCus133;
    private javax.swing.JLabel LabelValorCus134;
    private javax.swing.JLabel LabelValorCus135;
    private javax.swing.JLabel LabelValorCus136;
    private javax.swing.JLabel LabelValorCus137;
    private javax.swing.JLabel LabelValorCus138;
    private javax.swing.JLabel LabelValorCus139;
    private javax.swing.JLabel LabelValorCus140;
    private javax.swing.JLabel LabelValorCus141;
    private javax.swing.JLabel LabelValorCus142;
    private javax.swing.JLabel LabelValorCus143;
    private javax.swing.JLabel LabelValorCus52;
    private javax.swing.JLabel LabelValorCus53;
    public javax.swing.JButton jButtonAdicionarPaciente;
    public javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonExcluir;
    public javax.swing.JButton jButtonExcluirExame;
    private javax.swing.JButton jButtonGravar;
    public javax.swing.JButton jButtonImprimirAtestado;
    private javax.swing.JButton jButtonImprimirPrescricao;
    private javax.swing.JButton jButtonListar;
    private javax.swing.JButton jButtonNovo;
    public javax.swing.JButton jButtonNovoExame;
    private javax.swing.JButton jButtonPesquisar;
    public javax.swing.JButton jButtonVisualizarConculta;
    private javax.swing.JComboBox jComboSelecionaConvenio;
    private javax.swing.JComboBox jComboSelecionaHorario;
    private javax.swing.JComboBox jComboSelecionaMedico;
    private javax.swing.JComboBox jComboSelecionaTipoConsulta;
    private javax.swing.JComboBox jComboSelecionaTipoPesquisa13;
    private javax.swing.JComboBox jComboTipoAtestado;
    private datechooser.beans.DateChooserPanel jDateChooserCalendario;
    private javax.swing.JLabel jLabelFoto;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableExame;
    private javax.swing.JTable jTablePesquisa;
    private javax.swing.JTextField jTextAltura;
    private javax.swing.JTextArea jTextAtestado;
    private javax.swing.JTextField jTextCodPaciente;
    private javax.swing.JTextField jTextCpf;
    private javax.swing.JTextArea jTextDiagnostico;
    private javax.swing.JTextField jTextEmail;
    private javax.swing.JTextField jTextNome;
    private javax.swing.JTextField jTextPeso;
    private javax.swing.JTextField jTextPesquisa;
    private javax.swing.JTextArea jTextPrescricao;
    private javax.swing.JTextField jTextPressao;
    private javax.swing.JTextField jTextQtdeDias;
    private javax.swing.JTextField jTextRg;
    private javax.swing.JTextField jTextSexo;
    private javax.swing.JTextField jTextTelefone;
    private javax.swing.JTextField jTextUltimaConsulta;
    private javax.swing.JTextField jTextValorConsulta;
    private javax.swing.JTextField jTextValorDesconto;
    // End of variables declaration//GEN-END:variables
}
