/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.NewTelas;

/**
 *
 * @author Ritiele Aldeburg && Guilherme Tessmann
 */
import com.altessmann.AbstractTableModel.ConsultaTableModel;
import com.altessmann.AbstractTableModel.Convenio_cadastroTableModel;
import com.altessmann.AbstractTableModel.Convenio_na_consultaTableModel;
import com.altessmann.AbstractTableModel.ExameTableModel;
import javax.swing.JOptionPane;
import com.altessmann.Bean.PacienteBean;
import com.altessmann.AbstractTableModel.PacienteTableModel;
import com.altessmann.Bean.ConsultaBean;
import com.altessmann.Bean.ConvenioBean;
import com.altessmann.Bean.ExameBean;
import com.altessmann.Cam.WebcamViewer;
import com.altessmann.DAO.ExameDAO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import com.altessmann.DAO.PacienteDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import com.altessmann.NewTelas.CadastroConsultaNew;
import com.altessmann.ValidaCampos.ValidaCPF;
import com.altessmann.ValidaCampos.ValidaEmail;
import com.altessmann.ValidaCampos.buscaCEP;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import javax.swing.JLabel;

public class CadastroPacienteNew extends javax.swing.JFrame {

    /**
     * Creates new form CadastroPacienteNew
     */
    private final int paraExame = 2;
    private final int paraConsulta = 1;
    private int tabelaAoClicar;
    private int tipo;
    private int funcao;
    private PacienteTableModel model;
    private ConsultaTableModel consultaModel;
    private Convenio_na_consultaTableModel model2;
    private PacienteDAO pacienteDAO = new PacienteDAO();
    private ExameDAO exameDAO = new ExameDAO();
    private ExameBean exameBean;
    private ExameTableModel exameModel;
    private PacienteBean pacienteBean = new PacienteBean();
    private CadastroConsultaNew cadastroConsultasNew = new CadastroConsultaNew(1);
    private ExamesNew examesNew = new ExamesNew();
    private int numeroLinhas = 0;
    private String caminhoFoto = null;
    private BufferedImage image;
    private boolean flag = false;
    private double porcentagem = 0;
    private WebcamViewer webcam = new WebcamViewer();
    //CadastroPacienteNew Cadastro = new CadastroPacienteNew();

    public CadastroPacienteNew(int funcao) {
        initComponents();
        tipo = 0;
        this.funcao = funcao;
        jButtonEditar.setEnabled(false);
        jButtonExcluir.setEnabled(false);
        jButtonGravar.setEnabled(false);
        jTabbedPaneCadastro.setSelectedIndex(0);
        jTabbedPaneCadastro.setEnabledAt(2, false);
        desativaCampo();
        limpaCampo();
        tabela();

    }

    public void atualiza_tabela() {
        int codigo = 0;
        List lista = new ArrayList();

        try {
            if (!pacienteDAO.getPaciente("").isEmpty()) {
                for (int i = 0; i < pacienteDAO.getPaciente("").size(); i++) {
                    PacienteBean p = new PacienteBean();
                    p.setCodigo(pacienteDAO.getPaciente("").get(i).getCodigo());
                    p.setNome(pacienteDAO.getPaciente("").get(i).getNome());
                    p.setTelefone(pacienteDAO.getPaciente("").get(i).getTelefone());
                    p.setAtivo(pacienteDAO.getPaciente("").get(i).getAtivo());
                    p.setUltimaConsulta(pacienteDAO.getPaciente("").get(i).getUltimaConsulta());
                    lista.add(p);
                }
                model = new PacienteTableModel(lista);
                jTablePesquisa.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum paciente cadastrado!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tabela() {
        /*int codigo = 0;
        List lista = new ArrayList();
        try {
            codigo = Integer.parseInt(jTextPesquisa.getText());
            pacienteBean.setCodigo(codigo);
            pacienteBean.setNome(pacienteDAO.getPaciente(codigo).get(0).getNome());
            pacienteBean.setTelefone(pacienteDAO.getPaciente(codigo).get(0).getTelefone());
            pacienteBean.setAtivo(pacienteDAO.getPaciente(codigo).get(0).isAtivo());
            pacienteBean.setUltimaConsulta(pacienteDAO.getPaciente(codigo).get(0).getUltimaConsulta());
            lista.add(pacienteBean);
            model = new PacienteTableModel(lista);
            jTablePesquisa.setModel(model);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
        } catch (Exception e) {
            try {
                pacienteDAO.getPaciente(jTextPesquisa.getText());
                this.numeroLinhas = pacienteDAO.getNumeroLinhas();
                for (int i = 0; i < numeroLinhas; i++) {
                    PacienteBean p = new PacienteBean();
                    p.setCodigo(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getCodigo());
                    p.setNome(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getNome());
                    p.setTelefone(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getTelefone());
                    p.setAtivo(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).isAtivo());
                    p.setUltimaConsulta(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getUltimaConsulta());
                    lista.add(p);
                }
                model = new PacienteTableModel(lista);
                jTablePesquisa.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
            } catch (SQLException ex) {
                Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
            }

        }*/

        List lista = new ArrayList();
        jTablePesquisa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    getPaciente(Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + ""));
                    jButtonEditar.setEnabled(true);
                    jButtonExcluir.setEnabled(true);
                } else {
                }
            }
        });
    }
    //LISTENER PARA TESTAR SE PORCENTAGEM ==0, APARECE UM JOptionPane PARA INFORMAR A PORCENTAGEM

    public void setConvenio() {
        List convenios = new ArrayList();
        try {
            pacienteDAO.getConvenio();
            numeroLinhas = pacienteDAO.getNumeroLinhas();
            for (int i = 0; i < numeroLinhas; i++) {
                ConvenioBean convenio = new ConvenioBean();
                convenio.setCodigo(pacienteDAO.getConvenio().get(i).getCodigo());
                convenio.setNome(pacienteDAO.getConvenio().get(i).getNome());
                convenio.setPorcentagem(pacienteDAO.getConvenio().get(i).getPorcentagem());
                convenios.add(convenio);
            }

            model2 = new Convenio_na_consultaTableModel(convenios);
            jTableConvenio.setModel(model2);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTableConvenio.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTableConvenio.getColumnModel().getColumn(1).setCellRenderer(esquerda);
            jTableConvenio.getColumnModel().getColumn(2).setCellRenderer(esquerda);

        } catch (SQLException ex) {
            Logger.getLogger(CadastroConvenioNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviaPalavra(CadastroConsultaNew cadastroConsultaNew, ExamesNew examesNew, String palavra, int tipo) {
        this.tipo = tipo;
        if (tipo == paraConsulta) {
            this.cadastroConsultasNew = cadastroConsultaNew;

        } else if (tipo == paraExame) {
            this.examesNew = examesNew;
        }
        jButtonAdicionar.setEnabled(true);
        jTextPesquisa.setText(palavra);
        List lista = new ArrayList();
        try {
            for (int i = 0; i < pacienteDAO.getPaciente(jTextPesquisa.getText()).size(); i++) {
                PacienteBean p = new PacienteBean();
                p.setCodigo(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getCodigo());
                p.setNome(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getNome());
                p.setTelefone(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getTelefone());
                p.setAtivo(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getAtivo());
                p.setUltimaConsulta(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getUltimaConsulta());
                lista.add(p);
            }
            model = new PacienteTableModel(lista);
            jTablePesquisa.setModel(model);
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
            jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
        } catch (SQLException ex) {
            Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getPaciente(int cod) {
        limpaCampo();
        image = null;
        try {
            if (!pacienteDAO.getPaciente(cod).isEmpty()) {

                jTextNome.setText(pacienteDAO.getPaciente(cod).get(0).getNome());
                jTextEmail.setText(pacienteDAO.getPaciente(cod).get(0).getEmail());
                jDateDtNascimento.setDate(pacienteDAO.getPaciente(cod).get(0).getDtNascimento());
                jDateDtCadastro.setDate(pacienteDAO.getPaciente(cod).get(0).getDtCadastro());
                jTextTelefone.setText(pacienteDAO.getPaciente(cod).get(0).getTelefone());
                jTextRg.setText(pacienteDAO.getPaciente(cod).get(0).getRg());
                jTextCpf.setText(pacienteDAO.getPaciente(cod).get(0).getCpf());
                jComboSexo.setSelectedItem(pacienteDAO.getPaciente(cod).get(0).getSexo() + "");
                jTextEndereco.setText(pacienteDAO.getPaciente(cod).get(0).getEndereco());
                jTextNumero.setText(pacienteDAO.getPaciente(cod).get(0).getNumero());
                jTextComplemento.setText(pacienteDAO.getPaciente(cod).get(0).getComplemento());
                jTextCep.setText(pacienteDAO.getPaciente(cod).get(0).getCep());
                jTextBairro.setText(pacienteDAO.getPaciente(cod).get(0).getBairro());
                jTextCidade.setText(pacienteDAO.getPaciente(cod).get(0).getCidade());
                jComboUf.setSelectedItem(pacienteDAO.getPaciente(cod).get(0).getUf());
                jTextObs.setText(pacienteDAO.getPaciente(cod).get(0).getObs());
                flag = pacienteDAO.getPaciente(cod).get(0).getTentativas() == 0;
                jComboAtivo.setSelectedItem(pacienteDAO.getPaciente(cod).get(0).getAtivo());
                image = getBufferedImage(pacienteDAO.getPaciente(cod).get(0).getFoto());
                Image imagem = image.getScaledInstance(136, 148, 0);
                Icon icon = new ImageIcon(imagem);
                jLabelFoto.setIcon(icon);

                if (pacienteDAO.getPaciente(cod).get(0).getUltimaConsulta() == null) {
                    jTextUltimaConsulta.setText("Nenhuma consulta!");
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(pacienteDAO.getPaciente(cod).get(0).getUltimaConsulta());
                    jTextUltimaConsulta.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
                }
                //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
                setConvenio();
                for (int i = 0; i < model2.getRowCount(); i++) {
                    for (int j = 0; j < pacienteDAO.getConvenioPaciente(cod).size(); j++) {
                        int codConvenio = pacienteDAO.getConvenioPaciente(cod).get(j).getCodConvenio();
                        if (Integer.parseInt(jTableConvenio.getValueAt(i, 0) + "") == codConvenio) {
                            jTableConvenio.setValueAt(pacienteDAO.getConvenioPaciente(cod).get(j).getConvenioPorcentagem(), i, 2);
                            jTableConvenio.setValueAt(true, i, 3);
                        }
                    }
                }
                //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
            } else {
                JOptionPane.showMessageDialog(null, "O código " + cod + " não está cadastrado!", "Código não cadastrado", JOptionPane.WARNING_MESSAGE);
                this.desativaCampo();
                //this.jTextCodigo.setText(null);
                this.jButtonEditar.setEnabled(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setNovoCodigo() {
        flag = false;
        pacienteBean.setTentativas(0); // Atualizo ou insiro um dado novo? Esse loko decide!
        setConvenio();
    }

    public void ativaCampo() {

        jTextNome.setEnabled(true);
        jTextEmail.setEnabled(true);
        jDateDtNascimento.setEnabled(true);
        jTextTelefone.setEnabled(true);
        jTextRg.setEnabled(true);
        jTextCpf.setEnabled(true);
        jComboSexo.setEnabled(true);
        jTextEndereco.setEnabled(true);
        jTextNumero.setEnabled(true);
        jTextComplemento.setEnabled(true);
        jTextCep.setEnabled(true);
        jTextBairro.setEnabled(true);
        jTextCidade.setEnabled(true);
        jComboUf.setEnabled(true);
        jTextObs.setEnabled(true);
        jComboAtivo.setEnabled(true);
        jButtonNovaFoto.setEnabled(true);
        jTableConvenio.setEnabled(true);
    }

    public void desativaCampo() {
        jComboSexo.setSelectedItem("Masculino");
        jComboAtivo.setSelectedItem("Ativo");
        jComboUf.setSelectedItem("RS");
        jTextNome.setEnabled(false);
        jTextEmail.setEnabled(false);
        //jRadioFumante.setEnabled(false);
        //jRadioAlcoolatra.setEnabled(false);
        //jComboConvenio.setEnabled(false);
        jTextUltimaConsulta.setEnabled(false);
        jDateDtNascimento.setEnabled(false);
        jDateDtCadastro.setEnabled(false);
        //jDateDtAtualizacao.setEnabled(false);
        jTextTelefone.setEnabled(false);
        jTextRg.setEnabled(false);
        jTextCpf.setEnabled(false);
        jComboSexo.setEnabled(false);
        jTextEndereco.setEnabled(false);
        jTextNumero.setEnabled(false);
        jTextComplemento.setEnabled(false);
        jTextCep.setEnabled(false);
        jTextBairro.setEnabled(false);
        jTextCidade.setEnabled(false);
        jComboUf.setEnabled(false);
        jTextObs.setEnabled(false);
        jComboAtivo.setEnabled(false);
        jButtonNovaFoto.setEnabled(false);
        jTableConvenio.setEnabled(false);
        jButtonAdicionar.setEnabled(false);
    }

    public void limpaCampo() {
        //Date data = new Date();
        jDateDtNascimento.setDate(null);
        jTextNome.setText(null);
        jTextEmail.setText(null);
        //jRadioFumante.setSelected(false);
        //jRadioAlcoolatra.setSelected(false);
        jTextUltimaConsulta.setText("");
        jDateDtCadastro.setDate(null);
        //jDateDtAtualizacao.setDate(null);
        jTextTelefone.setText(null);
        jTextRg.setText(null);
        jTextCpf.setText(null);
        jComboSexo.setSelectedItem("Masculino");
        jTextEndereco.setText(null);
        jTextNumero.setText(null);
        jTextComplemento.setText(null);
        jTextCep.setText(null);
        jTextBairro.setText(null);
        jTextCidade.setText(null);
        jComboUf.setSelectedItem("RS");
        jTextObs.setText(null);
        jComboAtivo.setSelectedItem("NÃO");
        if (model2 != null) {
            model2.limpaTabela();
        }
        if (exameModel != null) {
            exameModel.limpaTabela();
        }
        if (consultaModel != null) {
            consultaModel.limpaTabela();
        }
        jLabelFoto.setIcon(null);
        BufferedImage imagem;
        try {
            imagem = ImageIO.read(new File("icones\\sem_foto.jpg"));
            Image image = imagem.getScaledInstance(136, 148, 0);
            Icon icon = new ImageIcon(image);
            jLabelFoto.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        image = null;
    }

    public void takePicture(BufferedImage image) {
        this.image = image;
        Image imagem = image.getScaledInstance(136, 148, 0);
        Icon icon1 = new ImageIcon(imagem);
        jLabelFoto.setIcon(icon1);

    }

//  
    //CONVERTER BUFFEREDIMAGE PARA BYTE[] 
    public byte[] getByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }
    //CONVERTER BYTE[] PARA BUFFEREDIMAGE

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

    public void getConsultasPaciente(int codConsulta, int codPaciente) {
        List lista = new ArrayList();
        try {
            if (!pacienteDAO.getConsulta(codConsulta, codPaciente).isEmpty()) {
                ConsultaBean c = new ConsultaBean();
                c.setCodigo(pacienteDAO.getConsulta(codConsulta, codPaciente).get(0).getCodigo());
                c.setPacienteNome(pacienteDAO.getConsulta(codConsulta, codPaciente).get(0).getPacienteNome());
                c.setMedicoNome(pacienteDAO.getConsulta(codConsulta, codPaciente).get(0).getFuncionarioNome());
                c.setDtConsulta(pacienteDAO.getConsulta(codConsulta, codPaciente).get(0).getDtConsulta());
                c.setHorario(pacienteDAO.getConsulta(codConsulta, codPaciente).get(0).getHorario());
                c.setSigla(pacienteDAO.getConsulta(codConsulta, codPaciente).get(0).getSigla());
                c.setTipo(pacienteDAO.getConsulta(codConsulta, codPaciente).get(0).getTipo());
                c.setNumeroFicha(pacienteDAO.getConsulta(codConsulta, codPaciente).get(0).getNumeroFicha());
                c.setReqExame(pacienteDAO.reqExames(pacienteDAO.getConsulta(codConsulta, codPaciente).get(0).getCodigo()));
                c.setTipoConsultaNome(pacienteDAO.getConsulta(codPaciente).get(0).getTipoConsultaNome());
                c.setStatus(pacienteDAO.getConsulta(codPaciente).get(0).getStatus());
                lista.add(c);
                consultaModel = new ConsultaTableModel(lista);
                jTableConsulta.setModel(consultaModel);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableConsulta.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(5).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(6).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(7).setCellRenderer(esquerda);

            } else {
                JOptionPane.showMessageDialog(null, "Consulta não cadastrada!\nCódigo inválido!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getConsultasPaciente(int codPaciente) {
        List lista = new ArrayList();
        try {
            if (!pacienteDAO.getConsulta(codPaciente).isEmpty()) {
                for (int i = 0; i < pacienteDAO.getConsulta(codPaciente).size(); i++) {
                    ConsultaBean c = new ConsultaBean();
                    c.setCodigo(pacienteDAO.getConsulta(codPaciente).get(i).getCodigo());
                    c.setPacienteNome(pacienteDAO.getConsulta(codPaciente).get(i).getPacienteNome());
                    c.setMedicoNome(pacienteDAO.getConsulta(codPaciente).get(i).getFuncionarioNome());
                    c.setDtConsulta(pacienteDAO.getConsulta(codPaciente).get(i).getDtConsulta());
                    c.setHorario(pacienteDAO.getConsulta(codPaciente).get(i).getHorario());
                    c.setSigla(pacienteDAO.getConsulta(codPaciente).get(i).getSigla());
                    c.setTipo(pacienteDAO.getConsulta(codPaciente).get(i).getTipo());
                    c.setNumeroFicha(pacienteDAO.getConsulta(codPaciente).get(i).getNumeroFicha());
                    c.setReqExame(pacienteDAO.reqExames(pacienteDAO.getConsulta(codPaciente).get(i).getCodigo()));
                    c.setTipoConsultaNome(pacienteDAO.getConsulta(codPaciente).get(i).getTipoConsultaNome());
                    c.setStatus(pacienteDAO.getConsulta(codPaciente).get(i).getStatus());
                    lista.add(c);
                }
                consultaModel = new ConsultaTableModel(lista);
                jTableConsulta.setModel(consultaModel);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableConsulta.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(5).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(6).setCellRenderer(esquerda);
                jTableConsulta.getColumnModel().getColumn(7).setCellRenderer(esquerda);

            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma consulta cadastrada!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
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

        jTabbedPaneCadastro = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTextPesquisa = new javax.swing.JTextField();
        jButtonPesquisar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButtonAdicionar = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTablePesquisa = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButtonNovo = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonGravar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jComboAtivo = new javax.swing.JComboBox();
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
        LabelValorCus50 = new javax.swing.JLabel();
        jTextRg = new javax.swing.JTextField();
        LabelValorCus119 = new javax.swing.JLabel();
        LabelValorCus51 = new javax.swing.JLabel();
        jComboSexo = new javax.swing.JComboBox();
        jTextCpf = new javax.swing.JTextField();
        LabelValorCus120 = new javax.swing.JLabel();
        jDateDtNascimento = new com.toedter.calendar.JDateChooser();
        LabelValorCus48 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabelFoto = new javax.swing.JLabel();
        jButtonNovaFoto = new javax.swing.JButton();
        LabelValorCus49 = new javax.swing.JLabel();
        jDateDtCadastro = new com.toedter.calendar.JDateChooser();
        LabelValorCus52 = new javax.swing.JLabel();
        jTextEmail = new javax.swing.JTextField();
        LabelValorCus122 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextObs = new javax.swing.JTextArea();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableConvenio = new javax.swing.JTable();
        jTextTelefone = new javax.swing.JTextField();
        LabelValorCus123 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTextUltimaConsulta = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jTextPesquisarConsulta = new javax.swing.JTextField();
        jButtonPesquisar1 = new javax.swing.JButton();
        jButtonListarConsultas = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableConsulta = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jTextPesquisaExame = new javax.swing.JTextField();
        jButtonPesquisar2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableExame = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Altessmann Sistemas - Cadastro Paciente");
        setResizable(false);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jTabbedPaneCadastro.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

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

        jTablePesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Telefone", "Ativo", "Última consulta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true
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
        jScrollPane6.setViewportView(jTablePesquisa);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPesquisar)
                        .addGap(3, 3, 3)
                        .addComponent(jButton3)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING))
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
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        jTabbedPaneCadastro.addTab("  Pesquisar Paciente  ", jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Cadastro Paciente  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

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

        jComboAtivo.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboAtivo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ativo", "Bloqueado", "Inativo" }));
        jComboAtivo.setToolTipText("");
        jComboAtivo.setEnabled(false);

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
                .addComponent(jButtonSair)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboAtivo, 0, 145, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonSair, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        jComboUf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        jComboUf.setToolTipText("");
        jComboUf.setEnabled(false);

        LabelValorCus50.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus50.setText("Estado:");

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

        jDateDtNascimento.setEnabled(false);
        jDateDtNascimento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jDateDtNascimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Calendar.png")));

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
            .addComponent(jLabelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButtonNovaFoto.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jButtonNovaFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Camera-icon.png"))); // NOI18N
        jButtonNovaFoto.setText("Nova Foto");
        jButtonNovaFoto.setEnabled(false);
        jButtonNovaFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovaFotoActionPerformed(evt);
            }
        });

        LabelValorCus49.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus49.setText("Data Ultima Consulta:");

        jDateDtCadastro.setEnabled(false);
        jDateDtCadastro.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jDateDtCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Calendar.png")));

        LabelValorCus52.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus52.setText("Data Cadastro:");

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

        jTextObs.setBackground(java.awt.SystemColor.control);
        jTextObs.setColumns(1);
        jTextObs.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextObs.setLineWrap(true);
        jTextObs.setRows(1);
        jTextObs.setEnabled(false);
        jScrollPane4.setViewportView(jTextObs);

        jLabel28.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel28.setText("Observações:");

        jTableConvenio.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTableConvenio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Título", "Valor Consulta", "Seleciona"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableConvenio.setEnabled(false);
        jTableConvenio.setMaximumSize(new java.awt.Dimension(2147483647, 200));
        jTableConvenio.setMinimumSize(new java.awt.Dimension(770, 800));
        jTableConvenio.setRowHeight(20);
        jTableConvenio.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTableConvenio);
        if (jTableConvenio.getColumnModel().getColumnCount() > 0) {
            jTableConvenio.getColumnModel().getColumn(0).setMinWidth(60);
            jTableConvenio.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableConvenio.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableConvenio.getColumnModel().getColumn(2).setMinWidth(70);
            jTableConvenio.getColumnModel().getColumn(2).setPreferredWidth(70);
            jTableConvenio.getColumnModel().getColumn(2).setMaxWidth(70);
            jTableConvenio.getColumnModel().getColumn(3).setMinWidth(70);
            jTableConvenio.getColumnModel().getColumn(3).setPreferredWidth(70);
            jTableConvenio.getColumnModel().getColumn(3).setMaxWidth(70);
        }

        jTextTelefone.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextTelefone.setEnabled(false);
        jTextTelefone.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextTelefoneActionPerformed(evt);
            }
        });

        LabelValorCus123.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus123.setText("Telefone:");

        jLabel29.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel29.setText("Convênios:");

        jTextUltimaConsulta.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextUltimaConsulta.setEnabled(false);
        jTextUltimaConsulta.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextUltimaConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextUltimaConsultaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonNovaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus119)
                                    .addComponent(jTextRg, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelValorCus120))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus48, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDateDtNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus112)
                                .addGap(437, 437, 437))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(LabelValorCus122)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jTextEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus123)
                                    .addComponent(jTextTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(LabelValorCus52, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                        .addGap(19, 19, 19))
                                    .addComponent(jDateDtCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jTextNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboSexo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LabelValorCus51, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus49, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 26, Short.MAX_VALUE))
                            .addComponent(jTextUltimaConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel28)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus116)
                                    .addComponent(jTextBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus118)
                                    .addComponent(jTextCep, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelValorCus117))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(LabelValorCus50, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 5, Short.MAX_VALUE))
                                    .addComponent(jComboUf, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus113)
                            .addComponent(jTextEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus114, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelValorCus115)
                            .addComponent(jTextComplemento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(5, 5, 5))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonNovaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextNome, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LabelValorCus112)
                                    .addComponent(LabelValorCus51))
                                .addGap(37, 37, 37)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LabelValorCus119)
                                .addGap(1, 1, 1)
                                .addComponent(jTextRg, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelValorCus48)
                                    .addComponent(LabelValorCus120))
                                .addGap(1, 1, 1)
                                .addComponent(jDateDtNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(LabelValorCus49)
                                        .addGap(1, 1, 1)
                                        .addComponent(jTextUltimaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(LabelValorCus52)
                                        .addGap(1, 1, 1)
                                        .addComponent(jDateDtCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelValorCus122)
                                    .addComponent(LabelValorCus123))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(LabelValorCus116)
                                    .addGap(1, 1, 1)
                                    .addComponent(jTextBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LabelValorCus118)
                                        .addComponent(LabelValorCus117))
                                    .addGap(1, 1, 1)
                                    .addComponent(jTextCep, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(LabelValorCus50)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jComboUf, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(48, 48, 48))
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
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPaneCadastro.addTab("  Cadastro Paciente  ", jPanel5);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Consultas Realizadas  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jTextPesquisarConsulta.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextPesquisarConsulta.setForeground(new java.awt.Color(153, 153, 153));
        jTextPesquisarConsulta.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextPesquisarConsulta.setPreferredSize(new java.awt.Dimension(500, 25));
        jTextPesquisarConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPesquisarConsultaActionPerformed(evt);
            }
        });

        jButtonPesquisar1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonPesquisar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PesquisarCli.png"))); // NOI18N
        jButtonPesquisar1.setText("Pesquisar ");
        jButtonPesquisar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPesquisar1ActionPerformed(evt);
            }
        });

        jButtonListarConsultas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonListarConsultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/abrircli.png"))); // NOI18N
        jButtonListarConsultas.setText("Listar ");
        jButtonListarConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListarConsultasActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/visualizar.png"))); // NOI18N
        jButton7.setText("Visualizar Selecionado");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTableConsulta.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableConsulta.setDoubleBuffered(true);
        jTableConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableConsulta);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1)
                .addGap(1, 1, 1))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextPesquisarConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonPesquisar1)
                        .addGap(3, 3, 3)
                        .addComponent(jButtonListarConsultas)
                        .addGap(3, 3, 3)
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                        .addGap(5, 5, 5))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextPesquisarConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonPesquisar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonListarConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Exames/Laudos Realizados  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jTextPesquisaExame.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextPesquisaExame.setForeground(new java.awt.Color(153, 153, 153));
        jTextPesquisaExame.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextPesquisaExame.setPreferredSize(new java.awt.Dimension(500, 25));
        jTextPesquisaExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPesquisaExameActionPerformed(evt);
            }
        });

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

        jButton6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/visualizar.png"))); // NOI18N
        jButton6.setText("Visualizar Selecionado");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jTableExame.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome paciente", "Nome exame", "Descrição", "Data requisiçãp", "Data laudo", "Laudo gerado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableExame.setDoubleBuffered(true);
        jTableExame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableExameMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableExame);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jTextPesquisaExame, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jButtonPesquisar2)
                .addGap(3, 3, 3)
                .addComponent(jButton5)
                .addGap(3, 3, 3)
                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addGap(5, 5, 5))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane3)
                .addGap(5, 5, 5))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextPesquisaExame, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonPesquisar2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneCadastro.addTab("  Histórico Paciente  ", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneCadastro)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPesquisaActionPerformed
        int codigo = 0;
        List lista = new ArrayList();
        try {
            codigo = Integer.parseInt(jTextPesquisa.getText());
            if (!pacienteDAO.getPaciente(codigo).isEmpty()) {
                pacienteBean.setCodigo(codigo);
                pacienteBean.setNome(pacienteDAO.getPaciente(codigo).get(0).getNome());
                pacienteBean.setTelefone(pacienteDAO.getPaciente(codigo).get(0).getTelefone());
                pacienteBean.setAtivo(pacienteDAO.getPaciente(codigo).get(0).getAtivo());
                pacienteBean.setUltimaConsulta(pacienteDAO.getPaciente(codigo).get(0).getUltimaConsulta());
                lista.add(pacienteBean);
                model = new PacienteTableModel(lista);
                jTablePesquisa.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
            } else {
                JOptionPane.showMessageDialog(null, "Paciente não cadastrado!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            try {
                pacienteDAO.getPaciente(jTextPesquisa.getText());
                this.numeroLinhas = pacienteDAO.getNumeroLinhas();
                if (!pacienteDAO.getPaciente(jTextPesquisa.getText()).isEmpty()) {
                    for (int i = 0; i < pacienteDAO.getPaciente(jTextPesquisa.getText()).size(); i++) {
                        PacienteBean p = new PacienteBean();
                        p.setCodigo(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getCodigo());
                        p.setNome(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getNome());
                        p.setTelefone(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getTelefone());
                        p.setAtivo(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getAtivo());
                        p.setUltimaConsulta(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getUltimaConsulta());
                        lista.add(p);
                    }
                    model = new PacienteTableModel(lista);
                    jTablePesquisa.setModel(model);
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                } else {
                    JOptionPane.showMessageDialog(null, "Paciente não cadastrado!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jTextPesquisaActionPerformed

    private void jButtonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisarActionPerformed
        if (tipo != 1) {
            int codigo = 0;
            List lista = new ArrayList();
            try {
                codigo = Integer.parseInt(jTextPesquisa.getText());
                if (!pacienteDAO.getPaciente(codigo).isEmpty()) {
                    pacienteBean.setCodigo(codigo);
                    pacienteBean.setNome(pacienteDAO.getPaciente(codigo).get(0).getNome());
                    pacienteBean.setTelefone(pacienteDAO.getPaciente(codigo).get(0).getTelefone());
                    pacienteBean.setAtivo(pacienteDAO.getPaciente(codigo).get(0).getAtivo());
                    pacienteBean.setUltimaConsulta(pacienteDAO.getPaciente(codigo).get(0).getUltimaConsulta());
                    lista.add(pacienteBean);
                    model = new PacienteTableModel(lista);
                    jTablePesquisa.setModel(model);
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                } else {
                    JOptionPane.showMessageDialog(null, "Paciente não cadastrado!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                try {
                    pacienteDAO.getPaciente(jTextPesquisa.getText());
                    this.numeroLinhas = pacienteDAO.getNumeroLinhas();
                    if (!pacienteDAO.getPaciente(jTextPesquisa.getText()).isEmpty()) {
                        for (int i = 0; i < pacienteDAO.getPaciente(jTextPesquisa.getText()).size(); i++) {
                            PacienteBean p = new PacienteBean();
                            p.setCodigo(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getCodigo());
                            p.setNome(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getNome());
                            p.setTelefone(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getTelefone());
                            p.setAtivo(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getAtivo());
                            p.setUltimaConsulta(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getUltimaConsulta());
                            lista.add(p);
                        }
                        model = new PacienteTableModel(lista);
                        jTablePesquisa.setModel(model);
                        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                        jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    } else {
                        JOptionPane.showMessageDialog(null, "Paciente não cadastrado!", "Erro", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {

            int codigo = 0;
            List lista = new ArrayList();
            try {
                codigo = Integer.parseInt(jTextPesquisa.getText());
                if (!pacienteDAO.getPaciente(codigo).isEmpty()) {
                    pacienteBean.setCodigo(codigo);
                    pacienteBean.setNome(pacienteDAO.getPaciente(codigo).get(0).getNome());
                    pacienteBean.setTelefone(pacienteDAO.getPaciente(codigo).get(0).getTelefone());
                    pacienteBean.setAtivo(pacienteDAO.getPaciente(codigo).get(0).getAtivo());
                    pacienteBean.setUltimaConsulta(pacienteDAO.getPaciente(codigo).get(0).getUltimaConsulta());
                    lista.add(pacienteBean);
                    model = new PacienteTableModel(lista);
                    jTablePesquisa.setModel(model);
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                } else {
                    JOptionPane.showMessageDialog(null, "Paciente não cadastrado!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                try {
                    pacienteDAO.getPaciente(jTextPesquisa.getText());
                    this.numeroLinhas = pacienteDAO.getNumeroLinhas();
                    if (!pacienteDAO.getPaciente(jTextPesquisa.getText()).isEmpty()) {
                        for (int i = 0; i < pacienteDAO.getPaciente(jTextPesquisa.getText()).size(); i++) {
                            PacienteBean p = new PacienteBean();
                            p.setCodigo(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getCodigo());
                            p.setNome(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getNome());
                            p.setTelefone(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getTelefone());
                            p.setAtivo(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getAtivo());
                            p.setUltimaConsulta(pacienteDAO.getPaciente(jTextPesquisa.getText()).get(i).getUltimaConsulta());
                            lista.add(p);
                        }
                        model = new PacienteTableModel(lista);
                        jTablePesquisa.setModel(model);
                        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                        jTablePesquisa.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                        jTablePesquisa.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    } else {
                        JOptionPane.showMessageDialog(null, "Paciente não cadastrado!", "Erro", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            jButtonAdicionar.setEnabled(false);
        }

    }//GEN-LAST:event_jButtonPesquisarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (tipo != 1) {
            atualiza_tabela();
        } else {
            atualiza_tabela();
            jButtonAdicionar.setEnabled(false);
        }

        //System.out.println(pacienteDAO.getBanco());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButtonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoActionPerformed
       
        this.jButtonNovo.setEnabled(false);
        this.ativaCampo();
        this.limpaCampo();
        this.jButtonGravar.setEnabled(true);
        this.jButtonEditar.setEnabled(false);
        this.jButtonExcluir.setEnabled(false);
        this.jButtonSair.setEnabled(true);
        jTabbedPaneCadastro.setEnabledAt(0, false);
        this.setNovoCodigo();


    }//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        this.ativaCampo();
        this.jButtonGravar.setEnabled(true);
        this.jButtonExcluir.setEnabled(true);
        this.jButtonEditar.setEnabled(false);
        this.jButtonSair.setEnabled(true);
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        try {
            int codigo = Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + "");
            if (JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir " + jTextNome.getText() + "?\nEsses dados serão perdidos!", "Excluir",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                pacienteDAO.deletaPaciente(codigo);
                desativaCampo();
                limpaCampo();
                atualiza_tabela();
                this.jButtonGravar.setEnabled(false);
                this.jButtonNovo.setEnabled(true);
                this.jButtonSair.setEnabled(false);
                this.jButtonEditar.setEnabled(false);
                this.jButtonExcluir.setEnabled(false);
                jTabbedPaneCadastro.setEnabledAt(0, true);
                jTabbedPaneCadastro.setEnabledAt(2, false);
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

            if ((jTextNome.getText().equals("")) || (jDateDtNascimento.getDate() == null) || (jTextTelefone.getText().equals("")) || (jTextRg.getText().equals("")) || (jTextCpf.getText().equals("")) || (jTextEndereco.getText().equals("")) || (jTextNumero.getText().equals("")) || (jTextCep.getText().equals("")) || (jTextBairro.getText().equals("")) || (jTextCidade.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Campo(s) vazio(s)! \nPreenchimento obrigatorio de todos os campos!", "Altessmann - Informação", JOptionPane.ERROR_MESSAGE);

            } else {

                if (ValidaCPF.isCPF(CPF) == true) {
                    jTextCpf.setText(ValidaCPF.imprimeCPF(CPF));

                    //verifica email
                    if (ValidaEmail.isEmail(Email) == true) {

                        boolean tryToInsert = false;
                        int codigo = 0;
                        byte[] foto = null;
                        try {
                            boolean ativo = false;
                            if ((jComboAtivo.getSelectedItem() + "").equals("Ativo")) {
                                ativo = true;
                            }
                            if (jDateDtNascimento.getCalendar().getTime() != null) {
                                if (!flag) {// inserir novo paciente
                                    codigo = pacienteDAO.setNovoCodigo();
                                    //tryToInsert = true;
                                    if (image != null) {
                                        foto = getByteArray(image);
                                    } else {
                                        foto = getByteArray(ImageIO.read(new File("icones\\sem_foto.jpg")));
                                        //foto=null;
                                    }
                                    //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-//TABELA PACIENTE_CONVENIO SENDO PREENCHIDA
                                    for (int i = 0; i < model2.getRowCount(); i++) {
                                        if ((boolean) model2.getValueAt(i, 3)) {
                                            pacienteDAO.setPaciente_Convenio(codigo, Integer.parseInt(model2.getValueAt(i, 0) + ""), Double.parseDouble(model2.getValueAt(i, 2) + ""));
                                        }
                                    }

                                    //TESTA CPF IGUAL
                                    if (!pacienteDAO.retriveCPF(jTextCpf.getText()).equals(jTextCpf.getText())) {
                                        pacienteDAO.setPaciente(codigo, jTextNome.getText(), jTextEmail.getText(), ativo, jDateDtNascimento.getDate(), jTextTelefone.getText(), jTextRg.getText(), jTextCpf.getText(), jComboSexo.getSelectedItem() + "", jTextEndereco.getText(), jTextNumero.getText(), jTextComplemento.getText(), jTextCep.getText(), jTextBairro.getText(), jTextCidade.getText(), jComboUf.getSelectedItem() + "", jTextObs.getText(), foto, jComboAtivo.getSelectedItem().toString(), pacienteBean.getTentativas());
                                        desativaCampo();
                                        limpaCampo();
                                        //atualiza_tabela();
                                        this.jButtonGravar.setEnabled(false);
                                        this.jButtonNovo.setEnabled(true);
                                        this.jButtonSair.setEnabled(false);
                                        jTabbedPaneCadastro.setEnabledAt(0, true);
                                        jTabbedPaneCadastro.setEnabledAt(2, false);
                                    } else {
                                        if (JOptionPane.showConfirmDialog(null, " O CPF Digitado já esta cadastrado em nosso sistema\n Este CPF pode ser de um responsável do paciente\n Deseja Salvar o cadastro com CPF de um Responsável? ", " Salvar Cadastro",
                                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                            pacienteDAO.setPaciente(codigo, jTextNome.getText(), jTextEmail.getText(), ativo, jDateDtNascimento.getDate(), jTextTelefone.getText(), jTextRg.getText(), jTextCpf.getText(), jComboSexo.getSelectedItem() + "", jTextEndereco.getText(), jTextNumero.getText(), jTextComplemento.getText(), jTextCep.getText(), jTextBairro.getText(), jTextCidade.getText(), jComboUf.getSelectedItem() + "", jTextObs.getText(), foto, jComboAtivo.getSelectedItem().toString(), pacienteBean.getTentativas());
                                            desativaCampo();
                                            limpaCampo();
                                            //atualiza_tabela();
                                            this.jButtonGravar.setEnabled(false);
                                            this.jButtonNovo.setEnabled(true);
                                            this.jButtonSair.setEnabled(false);
                                            jTabbedPaneCadastro.setEnabledAt(0, true);
                                            jTabbedPaneCadastro.setEnabledAt(2, false);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "CPF JÁ CADASTRADO!" + "\n" + "VERIFIQUE O CPF DIGITADO!", "Altessmann - Informação", JOptionPane.ERROR_MESSAGE);

                                        }

                                    }
                                } else {// atualizar paciente
                                    //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-/
                                    try {
                                        codigo = Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + "");
                                        if (image != null) {
                                            foto = getByteArray(image);
                                        } else {
                                            foto = getByteArray(ImageIO.read(new File("icones\\sem_foto.jpg")));
                                        }
                                        pacienteDAO.deletaPaciente_Convenio(codigo); //DELETA TODAS AS LINHAS COM CONVENIO
                                        //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
                                        for (int i = 0; i < model2.getRowCount(); i++) {//DEPOIS INSERE TODAS DENOVO, CONFORME FOI SELECIONADO
                                            if ((boolean) model2.getValueAt(i, 3)) {
                                                pacienteDAO.setPaciente_Convenio(codigo, Integer.parseInt(model2.getValueAt(i, 0) + ""), Double.parseDouble(model2.getValueAt(i, 2) + ""));
                                            }
                                        }
                                        pacienteDAO.updatePaciente(codigo, jTextNome.getText(), jTextEmail.getText(), ativo, jDateDtNascimento.getDate(), jTextTelefone.getText(), jTextRg.getText(), jTextCpf.getText(), jComboSexo.getSelectedItem() + "", jTextEndereco.getText(), jTextNumero.getText(), jTextComplemento.getText(), jTextCep.getText(), jTextBairro.getText(), jTextCidade.getText(), jComboUf.getSelectedItem() + "", jTextObs.getText(), foto, jComboAtivo.getSelectedItem().toString(), pacienteBean.getTentativas());
                                        this.desativaCampo();
                                        //atualiza_tabela();
                                        this.jButtonGravar.setEnabled(false);
                                        this.jButtonNovo.setEnabled(true);
                                        this.jButtonSair.setEnabled(false);
                                        this.jButtonEditar.setEnabled(false);
                                        this.jButtonExcluir.setEnabled(false);
                                        this.limpaCampo();
                                        jTabbedPaneCadastro.setSelectedIndex(0);
                                        jTabbedPaneCadastro.setEnabledAt(0, true);
                                        jTabbedPaneCadastro.setEnabledAt(2, false);

                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada na tabela!\n" + e, "Erro", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Campo data Nascimento não pode ser nulo!", "Erro", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            //if (tryToInsert) {
                            //pacienteDAO.decrementaValor(codigo - 1);
                            // }
                            // Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
                            // JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.WARNING_MESSAGE);
                        } catch (IOException ex) {
                            Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
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

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed

        this.desativaCampo();
        this.jButtonGravar.setEnabled(false);
        this.jButtonNovo.setEnabled(true);
        this.jButtonSair.setEnabled(false);
        this.jButtonEditar.setEnabled(false);
        this.jButtonExcluir.setEnabled(false);
        this.limpaCampo();
        jTabbedPaneCadastro.setEnabledAt(0, true);
        jTabbedPaneCadastro.setEnabledAt(2, false);


    }//GEN-LAST:event_jButtonSairActionPerformed

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

    private void jButtonNovaFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovaFotoActionPerformed
        System.gc();
        webcam.run();
        webcam.setLocationRelativeTo(this);
        webcam.recebeid(this);


    }//GEN-LAST:event_jButtonNovaFotoActionPerformed

    private void jTextEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextEmailActionPerformed

    private void jTextTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextTelefoneActionPerformed

    private void jTextPesquisarConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPesquisarConsultaActionPerformed
        try {
            getConsultasPaciente(Integer.parseInt(jTextPesquisarConsulta.getText()), (int) jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor, informe um código de consulta válido para a pesquisa!", "Pesquisa", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jTextPesquisarConsultaActionPerformed

    private void jButtonPesquisar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisar1ActionPerformed
        try {
            getConsultasPaciente(Integer.parseInt(jTextPesquisarConsulta.getText()), (int) jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor, informe um código de consulta válido para a pesquisa!", "Pesquisa", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonPesquisar1ActionPerformed

    private void jButtonListarConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListarConsultasActionPerformed

        getConsultasPaciente((int) jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0));

    }//GEN-LAST:event_jButtonListarConsultasActionPerformed

    private void jTextPesquisaExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPesquisaExameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPesquisaExameActionPerformed

    private void jButtonPesquisar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisar2ActionPerformed
        int codigo = 0;
        List lista = new ArrayList();
        try {
            codigo = Integer.parseInt(jTextPesquisaExame.getText());
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
                exameModel = new ExameTableModel(lista);
                jTableExame.setModel(exameModel);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableExame.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(5).setCellRenderer(esquerda);

            } else {
                JOptionPane.showMessageDialog(null, "Não existem exames com o código '" + codigo + "' cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor, informe um código de consulta válido para a pesquisa!", "Pesquisa", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonPesquisar2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int codigo = Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + "");
        List lista = new ArrayList();
        try {
            if (!exameDAO.getExamePaciente(codigo).isEmpty()) {
                boolean laudoGerado = false;
                if (!exameDAO.getExamePaciente(codigo).get(0).getDtLaudo().equals("")) {
                    laudoGerado = true;
                }
                if (exameBean == null) {
                    exameBean = new ExameBean();
                }
                exameBean.setCodigo(exameDAO.getExamePaciente(codigo).get(0).getCodigo());
                exameBean.setNomePaciente(exameDAO.getExamePaciente(codigo).get(0).getNomePaciente());
                exameBean.setNomeTipoExame(exameDAO.getExamePaciente(codigo).get(0).getNomeTipoExame());
                exameBean.setDescricaoTipoExame(exameDAO.getExamePaciente(codigo).get(0).getDescricaoTipoExame());
                exameBean.setDtRequisicao(exameDAO.getExamePaciente(codigo).get(0).getDtRequisicao());
                exameBean.setDtLaudo(exameDAO.getExamePaciente(codigo).get(0).getDtLaudo());
                exameBean.setLaudoGerado(laudoGerado);
                lista.add(exameBean);
                exameModel = new ExameTableModel(lista);
                jTableExame.setModel(exameModel);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableExame.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                jTableExame.getColumnModel().getColumn(5).setCellRenderer(esquerda);

            } else {
                JOptionPane.showMessageDialog(null, "Não existem exames cadastrados!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroPacienteNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (funcao != 0) {
            try {
                examesNew.historicoPacienteGetExame(Integer.parseInt(exameModel.getValueAt(jTableExame.getSelectedRow(), 0) + ""));
                examesNew.jButtonCancelar.setEnabled(false);
                examesNew.setVisible(true);

            } catch (Exception ex) {
                Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            /* if (this.tipo == paraConsulta) {
                cadastroConsultasNew.getPalavraPaciente(Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + ""));
                this.dispose();
            } else if (this.tipo == paraExame) {
                examesNew.getPalavraExame(Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + ""));
                this.dispose();
            }*/
            cadastroConsultasNew.historicoPacienteGetConsulta(funcao, (int) jTableConsulta.getValueAt(jTableConsulta.getSelectedRow(), 0));
            cadastroConsultasNew.jButtonCancelar.setEnabled(false);
            cadastroConsultasNew.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButtonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarActionPerformed
        try {
            if (this.tipo == paraConsulta) {
                cadastroConsultasNew.getPalavraPaciente(Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + ""));
                this.dispose();
            } else if (this.tipo == paraExame) {
                examesNew.getPalavraExame(Integer.parseInt(jTablePesquisa.getValueAt(jTablePesquisa.getSelectedRow(), 0) + ""));
                this.dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAdicionarActionPerformed

    private void jTextUltimaConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextUltimaConsultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextUltimaConsultaActionPerformed

    private void jTablePesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePesquisaMouseClicked
        /*        if (tipo != 1 || tipo!=2) {
            jTabbedPaneCadastro.setSelectedIndex(1);
            jTabbedPaneCadastro.setEnabledAt(0, false);
            jTabbedPaneCadastro.setEnabledAt(2, true);
            jButtonEditar.setEnabled(true);
            jButtonNovo.setEnabled(false);
            jButtonSair.setEnabled(true);
            jButtonExcluir.setEnabled(false);
            jTableConvenio.setEnabled(false);
        } else {
            jButtonAdicionar.setEnabled(true);

        }*/
        if (tipo == 1 || tipo == 2) {
            jButtonAdicionar.setEnabled(true);
        } else {
            jTabbedPaneCadastro.setSelectedIndex(1);
            jTabbedPaneCadastro.setEnabledAt(0, false);
            jTabbedPaneCadastro.setEnabledAt(2, true);
            jButtonEditar.setEnabled(true);
            jButtonNovo.setEnabled(false);
            jButtonSair.setEnabled(true);
            jButtonExcluir.setEnabled(false);
            jTableConvenio.setEnabled(false);
        }
        jTabbedPaneCadastro.grabFocus();
    }//GEN-LAST:event_jTablePesquisaMouseClicked

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged


    }//GEN-LAST:event_formWindowStateChanged

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        cadastroConsultasNew.jButtonAdicionarPaciente.setEnabled(true);
    }//GEN-LAST:event_formWindowClosed

    private void jTableConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableConsultaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableConsultaMouseClicked

    private void jTableExameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableExameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableExameMouseClicked

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
            java.util.logging.Logger.getLogger(CadastroPacienteNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroPacienteNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroPacienteNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroPacienteNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroPacienteNew(0).setVisible(true);
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
    private javax.swing.JLabel LabelValorCus122;
    private javax.swing.JLabel LabelValorCus123;
    private javax.swing.JLabel LabelValorCus48;
    private javax.swing.JLabel LabelValorCus49;
    private javax.swing.JLabel LabelValorCus50;
    private javax.swing.JLabel LabelValorCus51;
    private javax.swing.JLabel LabelValorCus52;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    public javax.swing.JButton jButtonAdicionar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JButton jButtonListarConsultas;
    private javax.swing.JButton jButtonNovaFoto;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonPesquisar;
    private javax.swing.JButton jButtonPesquisar1;
    private javax.swing.JButton jButtonPesquisar2;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JComboBox jComboAtivo;
    private javax.swing.JComboBox jComboSexo;
    private javax.swing.JComboBox jComboUf;
    private com.toedter.calendar.JDateChooser jDateDtCadastro;
    private com.toedter.calendar.JDateChooser jDateDtNascimento;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabelFoto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JTabbedPane jTabbedPaneCadastro;
    private javax.swing.JTable jTableConsulta;
    private javax.swing.JTable jTableConvenio;
    private javax.swing.JTable jTableExame;
    private javax.swing.JTable jTablePesquisa;
    private javax.swing.JTextField jTextBairro;
    private javax.swing.JTextField jTextCep;
    private javax.swing.JTextField jTextCidade;
    private javax.swing.JTextField jTextComplemento;
    private javax.swing.JTextField jTextCpf;
    private javax.swing.JTextField jTextEmail;
    private javax.swing.JTextField jTextEndereco;
    private javax.swing.JTextField jTextNome;
    private javax.swing.JTextField jTextNumero;
    private javax.swing.JTextArea jTextObs;
    private javax.swing.JTextField jTextPesquisa;
    private javax.swing.JTextField jTextPesquisaExame;
    private javax.swing.JTextField jTextPesquisarConsulta;
    private javax.swing.JTextField jTextRg;
    private javax.swing.JTextField jTextTelefone;
    private javax.swing.JTextField jTextUltimaConsulta;
    // End of variables declaration//GEN-END:variables
}
