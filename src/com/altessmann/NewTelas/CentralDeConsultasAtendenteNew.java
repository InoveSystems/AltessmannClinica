/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.NewTelas;

import Logs.LogsExceptions;
import com.altessmann.AbstractTableModel.AtendimentoTableModel;
import com.altessmann.AbstractTableModel.ConsultaTableModel;
import com.altessmann.Bean.AtendimentoBean;
import com.altessmann.Bean.ConsultaBean;
import com.altessmann.Bean.FuncionarioBean;
import com.altessmann.Bean.PainelBean;
import com.altessmann.DAO.ConsultaDAO;
import com.altessmann.DAO.FuncionarioDAO;
import com.altessmann.DAO.PainelDAO;
import com.altessmann.app.bean.ChatMessage;
import com.altessmann.app.bean.ChatMessage.Action;
import com.altessmann.app.service.ClienteService;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Ritiele Aldeburg
 */
public class CentralDeConsultasAtendenteNew extends javax.swing.JFrame {

    private Socket socket;
    private ChatMessage message;
    private ClienteService service;
    private ConsultaDAO consultaDAO;
    private ConsultaBean consultaBean = new ConsultaBean();
    private FuncionarioDAO funcionarioDAO;
    private FuncionarioBean funcionarioBean = new FuncionarioBean();
    private FuncionarioBean func = new FuncionarioBean();
    private AtendimentoTableModel model;
    private ConsultaTableModel modelConsulta;
    private LogsExceptions exception = new LogsExceptions();
    private int codigoUltimaConsultaConsulta = 0;

    /**
     * Creates new form CentralDeConsultasNew
     */
    public CentralDeConsultasAtendenteNew() {
        try {
            this.consultaDAO = new ConsultaDAO();
        } catch (SQLException ex) {
            //  Logger.getLogger(CentralDeConsultasAtendenteNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        setExtendedState(MAXIMIZED_BOTH);
        initComponents();

        txtAreaReceive.setLineWrap(true);
        txtAreaReceive.setWrapStyleWord(true);
        txtAreaSend.setLineWrap(true);
        txtAreaSend.setWrapStyleWord(true);
        atualizaTabela();
        this.getMedico();
        tabela();
        try {
            this.funcionarioDAO = new FuncionarioDAO();
        } catch (Exception ex) {
            Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public CentralDeConsultasAtendenteNew(FuncionarioBean Funcionario) {
        try {
            this.consultaDAO = new ConsultaDAO();

            initComponents();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            txtAreaReceive.setLineWrap(true);
            txtAreaReceive.setWrapStyleWord(true);
            txtAreaSend.setLineWrap(true);
            txtAreaSend.setWrapStyleWord(true);
            this.getMedico();
            this.getConsultas();
            this.func = Funcionario;
            txtName.setText(func.getUsuario());
            this.funcionarioDAO = new FuncionarioDAO();
        } catch (Exception ex) {
            Logger.getLogger(ExamesNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void limpaPaciente() {
        jTextPressaoPaciente.setText(null);
        jTextPesoPaciente.setText(null);
        jTextAlturaPaciente.setText(null);
        jTextConvenio.setText(null);
        jTextTipoConsulta.setText(null);
        jTextNomePaciente.setText(null);
        jTextStatusConsulta.setText(null);
        jLabelFoto.setIcon(null);
        jTextCodConsulta.setText(null);
    }

//    public void medicoselect() {
//        List lista = new ArrayList();
//        jComboSelecionaMedico.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (e.getClickCount() == 1) {
//
//                    int codMedico = 0, posicao;
//                    String separado = null, atual;
//                    if (jComboSelecionaMedico.getSelectedIndex() != -1) {
//                        atual = jComboSelecionaMedico.getSelectedItem() + "";
//                        posicao = 0;
//                        char[] a = atual.toCharArray();
//                        for (int i = 0; i < atual.length(); i++) {
//                            if (a[i] == '-') {
//                                posicao = i;
//                            }
//                        }
//                        separado = atual.substring(0, posicao);
//                        codMedico = Integer.parseInt(separado);
//
//                    }
//                    System.out.println("" + consultaDAO.getCodConsulta(codMedico));
//                    if ((consultaDAO.getCodConsulta(codMedico)) != 0) {
//                        getDadosConsulta(consultaDAO.getCodConsulta(codMedico));
//                    } else {
//                        System.out.println("caindo aqui");
//                        limpaPaciente();
//                    }
//                    if (consultaDAO.getUltimaChamadaCombonew(codMedico)) {     //se o medico selecionado estiver em atendimento
//                        jButtonFinalizar.setEnabled(true);
//                        jButtonChamarProximo.setEnabled(false);
//                    } else {
//                        jButtonFinalizar.setEnabled(false);
//                        jButtonChamarProximo.setEnabled(true);
//                    }
//
//                }
//
//            }
//        });
//    }
    public void tabela() {
        /*int codMedico = 0, posicao, codigo;
        String separado = null, atual;
        if (jComboSelecionaMedico.getSelectedIndex() != -1) {
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
        if (consultaDAO.haveConsulta(0, codMedico)) {
            //getDadosConsultaCombo(consultaDAO.getCodConsulta(0, codMedico));
            codigo = consultaDAO.getCodConsulta(2, codMedico);
            jButtonChamarProximo.setEnabled(true);
        } else {
            if (consultaDAO.haveConsulta(1, codMedico)) {
                //getDadosConsultaCombo(consultaDAO.getCodConsulta(1, codMedico));
                jButtonChamarProximo.setEnabled(true);
                codigo = consultaDAO.getCodConsulta(1, codMedico);
            } else {
                if (consultaDAO.haveConsulta(2, codMedico)) {
                    //getDadosConsultaCombo(consultaDAO.getCodConsulta(2, codMedico));
                    jButtonChamarProximo.setEnabled(true);
                    codigo = consultaDAO.getCodConsulta(2, codMedico);
                }
            }
            //jButtonFinalizar.setEnabled(true);

        }
         */
    }

    public void atualizaTabela() {
        new Thread() {
            @Override
            public void run() {
                try {
                    for (;;) {
                        getAtendimento();
                        getConsultas();
                        Thread.sleep(1000);
                    }

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

            }
        }.start();
    }

    public void getMedico() {
        try {
            jComboSelecionaMedico.removeAllItems();
            for (int i = 0; i < consultaDAO.getMedico().size(); i++) {
                jComboSelecionaMedico.addItem(consultaDAO.getMedico().get(i).getCodigo() + "- " + consultaDAO.getMedico().get(i).getNome());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getConsultas() {
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
                jTableConsultas.setModel(modelConsulta);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableConsultas.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableConsultas.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableConsultas.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTableConsultas.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTableConsultas.getColumnModel().getColumn(4).setCellRenderer(esquerda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroConsultaNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setConsultaConcluida(int codMedico) {
        if (consultaDAO.getUltimaChamadaCombo(codMedico) != 0) {
            consultaDAO.setStatusFinalizadaConsulta(consultaDAO.getUltimaChamadaCombo(codMedico));
        }
    }

    public void callNext() {
        int codMedico = 0, posicao;
        String separado = null, atual;
        if (jComboSelecionaMedico.getSelectedIndex() != -1) {
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

        if (consultaDAO.haveConsulta(0, codMedico)) {
            jLabelSala.setText("SALA " + consultaDAO.getSalaMedico(codMedico));
            setConsultaConcluida(codMedico);
            jLabelAntePenultimaChamada.setText(jLabelPenultimaChamada.getText());
            jLabelPenultimaChamada.setText(jLabelUltimaChamada.getText());
            jLabelUltimaChamada.setText(jLabelChamadaAtual.getText());
            getDadosConsulta(consultaDAO.getCodConsulta(0, codMedico)); // o getcodconsulta deveria retornar o codigo da consulta proxima e nao a final
            call(consultaDAO.callNextEmergencia(codMedico));
            jButtonChamarProximo.setEnabled(false);
            jButtonFinalizar.setEnabled(true);
        } else if (consultaDAO.haveConsulta(1, codMedico)) {
            jLabelSala.setText("SALA " + consultaDAO.getSalaMedico(codMedico));
            setConsultaConcluida(codMedico);
            jLabelAntePenultimaChamada.setText(jLabelPenultimaChamada.getText());
            jLabelPenultimaChamada.setText(jLabelUltimaChamada.getText());
            jLabelUltimaChamada.setText(jLabelChamadaAtual.getText());
            getDadosConsulta(consultaDAO.getCodConsulta(1, codMedico));
            call(consultaDAO.callNextPrioritario(codMedico));
            jButtonChamarProximo.setEnabled(false);
            jButtonFinalizar.setEnabled(true);
        } else {
            if (consultaDAO.haveConsulta(2, codMedico)) {
                jLabelSala.setText("SALA " + consultaDAO.getSalaMedico(codMedico));
                setConsultaConcluida(codMedico);
                jLabelAntePenultimaChamada.setText(jLabelPenultimaChamada.getText());
                jLabelPenultimaChamada.setText(jLabelUltimaChamada.getText());
                jLabelUltimaChamada.setText(jLabelChamadaAtual.getText());
                getDadosConsulta(consultaDAO.getCodConsulta(2, codMedico));
                call(consultaDAO.callNextNormal(codMedico));
                jButtonChamarProximo.setEnabled(false);
                jButtonFinalizar.setEnabled(true);
            } else {
                limpaPaciente();
                jButtonFinalizar.setEnabled(false);
                jButtonChamarProximo.setEnabled(true);
                consultaDAO.setStatusFinalizadaConsulta(consultaDAO.getUltimaChamadaCombo(codMedico));
                getConsultas();
                JOptionPane.showMessageDialog(null, "Não existem mais consultas para o dia!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        }
        getConsultas();
        atualizaStatusParaTodasInterfaces(codMedico);
    }

    public void atualizaStatusParaTodasInterfaces(int codMedico) {

        consultaDAO.atualizaStatusParaTodasInterfaces(jLabelSala.getText(), jLabelChamadaAtual.getText(), jLabelUltimaChamada.getText(), jLabelPenultimaChamada.getText(), jLabelAntePenultimaChamada.getText(), consultaDAO.getUltimaChamadaCombo(codMedico));
    }

    public void atualizaterminais() { //pede pra atualizar
        //ChatMessage menssage;
        //this.message = new ChatMessage();
        ///this.message.setAction(Action.ATUALIZA);
        ///service.send(this.message);

        String text = this.txtAreaSend.getText();
        String name = this.message.getName();
        this.message = new ChatMessage();
        this.message.setAction(Action.ATUALIZA);
        this.message.setName(name);
        this.message.setText(text);
        this.service.send(this.message);
        atualizaterminal();
    }

    public void atualizaterminal() { //atualiza campos
        int cod;
        int codMedico = 0, posicao;
        String separado = null, atual;
        ConsultaBean ConsultaAtual = new ConsultaBean();
        ConsultaAtual.setStatus("");
        ConsultaAtual.setPacienteNome("");
        ConsultaAtual.setPeso(0);
        ConsultaAtual.setPressao(0);
        ConsultaAtual.setAltura(0);
        ConsultaAtual.setTipoConsultaNome("");
        ConsultaAtual.setConvenioNome("");
        ConsultaAtual.setCodMedico(0);

        PainelDAO painelDao = new PainelDAO();
        PainelBean painel = new PainelBean();
        ResultSet rs;
        try {
            rs = painelDao.retriveTotal();
            while (rs.next()) {
                painel.setSala(rs.getString("sala"));
                painel.setAtualChamada(rs.getString("atualchamada"));
                painel.setUltimaChamada(rs.getString("ultimachamada"));
                painel.setPenultimaChamada(rs.getString("penultimachamada"));
                painel.setAntepenultimaChamada(rs.getString("antepenultimachamada"));
                painel.setData(rs.getDate("dataatual"));
                painel.setCod(rs.getInt("codconsultaatual"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PainelFichas.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        cod = painel.getCod();

        // PainelDAO painelDao = new PainelDAO();
        //PainelBean painel = new PainelBean();
        ResultSet rs1;
        try {
            rs1 = painelDao.retriveCompleto(cod);
            while (rs1.next()) {
                ConsultaAtual.setStatus(rs1.getString(1));
                ConsultaAtual.setFoto(rs1.getBytes(2));
                ConsultaAtual.setPacienteNome(rs1.getString(3));
                ConsultaAtual.setPeso(rs1.getDouble(4));
                ConsultaAtual.setPressao(rs1.getDouble(5));
                ConsultaAtual.setAltura(rs1.getDouble(6));
                ConsultaAtual.setTipoConsultaNome(rs1.getString(7));
                ConsultaAtual.setCodMedico(rs1.getInt(8));

            }
            rs1.close();
        } catch (SQLException ex) {
            Logger.getLogger(PainelFichas.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        ResultSet rs2;
        try {
            rs2 = painelDao.retriveTipoConvenio(cod);
            while (rs2.next()) {
                ConsultaAtual.setConvenioNome(rs2.getString("convenionome"));
            }
            rs2.close();
        } catch (SQLException ex) {
            Logger.getLogger(PainelFichas.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (jComboSelecionaMedico.getSelectedIndex() != -1) {
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

        if (ConsultaAtual.getCodMedico() == codMedico) {
            //System.out.println(ConsultaAtual.getStatus() + "\n" + ConsultaAtual.getPacienteNome() + "\n" + ConsultaAtual.getPeso() + "\n" + ConsultaAtual.getPressao() + "\n" + ConsultaAtual.getAltura() + "\n" + ConsultaAtual.getTipoConsultaNome() + "\n" + ConsultaAtual.getConvenioNome() + "\n");
            if (!ConsultaAtual.getStatus().equals("")) {
                jTextStatusConsulta.setText(ConsultaAtual.getStatus());
                jTextNomePaciente.setText(ConsultaAtual.getPacienteNome());
                jTextPesoPaciente.setText(ConsultaAtual.getPeso() + "");
                jTextPressaoPaciente.setText(ConsultaAtual.getPressao() + "");
                jTextAlturaPaciente.setText(ConsultaAtual.getAltura() + "");
                jTextTipoConsulta.setText(ConsultaAtual.getTipoConsultaNome());
                jTextConvenio.setText(ConsultaAtual.getConvenioNome());
                jTextCodConsulta.setText(cod + "");
                BufferedImage image = getBufferedImage(ConsultaAtual.getFoto());
                Image imagem = image.getScaledInstance(136, 148, 0);
                Icon icon = new ImageIcon(imagem);
                jLabelFoto.setIcon(icon);
                //atualizar imagem
            } else {
                jTextStatusConsulta.setText("Não há Consultas no momento!!");
                jTextNomePaciente.setText("");
                jTextPesoPaciente.setText("");
                jTextPressaoPaciente.setText("");
                jTextAlturaPaciente.setText("");
                jTextTipoConsulta.setText("");
                jTextConvenio.setText("");
                jTextCodConsulta.setText("");
            }

//            jLabelSala.setText(painel.getSala());
//            jLabelChamadaAtual.setText(painel.getAtualChamada());
//            jLabelUltimaChamada.setText(painel.getUltimaChamada());
//            jLabelPenultimaChamada.setText(painel.getPenultimaChamada());
//            jLabelAntePenultimaChamada.setText(painel.getAntepenultimaChamada());
            if (ConsultaAtual.getStatus().equals("Em atendimento")) {
                jButtonFinalizar.setEnabled(true);
                jButtonChamarProximo.setEnabled(false);
            } else {
                if (ConsultaAtual.getStatus().equals("Finalizada!")) {
                    jButtonFinalizar.setEnabled(false);
                    jButtonChamarProximo.setEnabled(true);
                } else {
                    // if(ConsultaAtual.getStatus().equals("Finalizada!")){

                }
            }
        }
        jLabelSala.setText(painel.getSala());
        jLabelChamadaAtual.setText(painel.getAtualChamada());
        jLabelUltimaChamada.setText(painel.getUltimaChamada());
        jLabelPenultimaChamada.setText(painel.getPenultimaChamada());
        jLabelAntePenultimaChamada.setText(painel.getAntepenultimaChamada());

    }

    public void call(String ficha) {
        new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        jLabelChamadaAtual.setText(ficha);
                        Thread.sleep(400);
                        jLabelChamadaAtual.setText("");
                        Thread.sleep(400);
                    }
                    jLabelChamadaAtual.setText(ficha);
                    getAtendimento();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

            }
        }.start();
    }

    public void getAtendimento() {
        List lista = new ArrayList();
        try {
            if (!consultaDAO.getAtendimento().isEmpty()) {
                for (int i = 0; i < consultaDAO.getAtendimento().size(); i++) {
                    AtendimentoBean c = new AtendimentoBean();
                    c.setCodMedico(consultaDAO.getAtendimento().get(i).getCodMedico());
                    c.setNomeMedico(consultaDAO.getAtendimento().get(i).getNomeMedico());
                    c.setConsultasAtendidas(consultaDAO.getConsultasAtendidas(consultaDAO.getAtendimento().get(i).getCodMedico()));
                    c.setConsultasRestantes(consultaDAO.getAtendimento().get(i).getConsultasTotais() - consultaDAO.getConsultasAtendidas(consultaDAO.getAtendimento().get(i).getCodMedico()));
                    c.setConsultasTotais(consultaDAO.getAtendimento().get(i).getConsultasTotais());
                    lista.add(c);
                }
                model = new AtendimentoTableModel(lista);
                jTableAtendimento.setModel(model);
                DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                jTableAtendimento.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                jTableAtendimento.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                jTableAtendimento.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                jTableAtendimento.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                jTableAtendimento.getColumnModel().getColumn(4).setCellRenderer(esquerda);

            } else {

            }
        } catch (SQLException ex) {
            Logger.getLogger(CadastroConsultaNew.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BufferedImage getBufferedImage(byte[] data) {
        InputStream in = new ByteArrayInputStream(data);
        BufferedImage bImageFromConvert = null;
        try {
            bImageFromConvert = ImageIO.read(in);

        } catch (IOException ex) {
            Logger.getLogger(CadastroConsultaNew.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return bImageFromConvert;
    }

    public void getDadosConsulta(int codConsulta) {
        int codPaciente = consultaDAO.getCodPaciente(codConsulta);
        DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2);
        formatoDois.setParseBigDecimal(true);
        try {
            jTextNomePaciente.setText(consultaDAO.getPaciente(codPaciente).get(0).getNome());
            BufferedImage image = getBufferedImage(consultaDAO.getPaciente(codPaciente).get(0).getFoto());
            Image imagem = image.getScaledInstance(136, 148, 0);
            Icon icon = new ImageIcon(imagem);
            jLabelFoto.setIcon(icon);
            jTextConvenio.setText(consultaDAO.getNomeConvenio(consultaDAO.getConsultaUser(codConsulta).get(0).getCodConvenio()));
            jTextPressaoPaciente.setText(formatoDois.format(consultaDAO.getConsultaUser(codConsulta).get(0).getPressao()));
            jTextPesoPaciente.setText(formatoDois.format(consultaDAO.getConsultaUser(codConsulta).get(0).getPeso()));
            jTextAlturaPaciente.setText(formatoDois.format(consultaDAO.getConsultaUser(codConsulta).get(0).getAltura()));
            jTextTipoConsulta.setText(consultaDAO.getNomeTipoConsutla(consultaDAO.getConsultaUser(codConsulta).get(0).getCodTipoConsulta()));
            jTextStatusConsulta.setText("Em atendimento");

        } catch (SQLException ex) {
            Logger.getLogger(CentralDeConsultasAtendenteNew.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getDadosConsultaCombo(int cod) {
        /*System.out.println(consultaDAO.foiChamado(cod));
        if (consultaDAO.foiChamado(cod)) {
            jButtonChamarProximo.setEnabled(false);
            jButtonFinalizar.setEnabled(true);
        } else {
            jButtonChamarProximo.setEnabled(true);
            jButtonFinalizar.setEnabled(false);
        }*/
    }

    private class ListenerSocket implements Runnable {

        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                this.input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {

            } catch (NullPointerException ex) {

            }
        }

        @Override
        public void run() {
            ChatMessage message = null;
            try {
                while ((message = (ChatMessage) input.readObject()) != null) {
                    Action action = message.getAction();

                    if (action.equals(Action.CONNECT)) {
                        connected(message);
                    } else if (action.equals(Action.DISCONNECT)) {
                        disconnected();
                        socket.close();
                    } else if (action.equals(Action.SEND_ONE)) {
                        if (!message.getText().equals(null)) {
                            System.out.println("::: " + message.getText() + " :::");
                        }
                        receive(message);
                    } else if (action.equals(Action.USERS_ONLINE)) {
                        refreshOnlines(message);
                    } else if (action.equals(Action.ATUALIZA)) { //se for essa a ação
                        atualizaterminal();
                    }
                }
            } catch (IOException ex) {
                // Logger.getLogger(CentralDeConsultasAtendenteNew.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                // Logger.getLogger(CentralDeConsultasAtendenteNew.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {

            }

        }
    }

    private void connected(ChatMessage message) {
        atualizaTabela();
        if (message.getText().equals("NO")) {
            this.txtName.setText("");
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao servidor de mensagens! \nUsuario já logado! \nEntre em contado com o administrador do sistema! \nAltessmann - www.altessmann.com", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.message = message;
        // this.btnConnectar.setEnabled(false);
        //this.txtName.setEditable(false);

        // this.btnSair.setEnabled(true);
        this.txtAreaSend.setEnabled(true);
        this.txtAreaReceive.setEnabled(true);
        this.btnEnviar.setEnabled(true);
        this.btnLimpar.setEnabled(true);
//        JOptionPane.showMessageDialog(this, "Você está conectado no chat!");
    }

    private void disconnected() {

        //this.btnConnectar.setEnabled(true);
        //this.txtName.setEditable(true);
        //this.btnSair.setEnabled(false);
        this.txtAreaSend.setEnabled(false);
        this.txtAreaReceive.setEnabled(false);
        this.btnEnviar.setEnabled(false);
        this.btnLimpar.setEnabled(false);
        this.txtAreaReceive.setText("");
        this.txtAreaSend.setText("");
//        JOptionPane.showMessageDialog(this, "Você saiu do chat!");
    }

    private void receive(ChatMessage message) {
        this.txtAreaReceive.append(message.getName() + ": " + message.getText() + "\n");
        txtAreaReceive.setCaretPosition(txtAreaReceive.getText().length());
    }

    private void refreshOnlines(ChatMessage message) {
        System.out.println(message.getSetOnlines().toString());
        Set<String> names = message.getSetOnlines();
        names.remove(message.getName());
        String[] array = (String[]) names.toArray(new String[names.size()]);
        this.listOnlines.setListData(array);
        this.listOnlines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listOnlines.setLayoutOrientation(JList.VERTICAL);
    }

    public void conect() {
        String name = this.func.getNome();
        if (!name.isEmpty()) {
            this.message = new ChatMessage();
            this.message.setAction(Action.CONNECT);
            this.message.setName(name);
            this.service = new ClienteService();
            this.socket = this.service.connect();
            new Thread(new ListenerSocket(this.socket)).start();
            this.service.send(message);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableConsultas = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAtendimento = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButtonChamarProximo = new javax.swing.JButton();
        jComboSelecionaMedico = new javax.swing.JComboBox();
        LabelValorCus121 = new javax.swing.JLabel();
        jButtonFinalizar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabelChamadaAtual = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabelUltimaChamada = new javax.swing.JLabel();
        jLabelPenultimaChamada = new javax.swing.JLabel();
        jLabelAntePenultimaChamada = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jLabelSala = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jLabelFoto = new javax.swing.JLabel();
        jTextNomePaciente = new javax.swing.JTextField();
        LabelValorCus126 = new javax.swing.JLabel();
        jTextPesoPaciente = new javax.swing.JTextField();
        LabelValorCus132 = new javax.swing.JLabel();
        jTextConvenio = new javax.swing.JTextField();
        LabelValorCus133 = new javax.swing.JLabel();
        jTextAlturaPaciente = new javax.swing.JTextField();
        LabelValorCus134 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        LabelValorCus122 = new javax.swing.JLabel();
        jTextStatusConsulta = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jTextCodConsulta = new javax.swing.JTextField();
        LabelValorCus135 = new javax.swing.JLabel();
        LabelValorCus123 = new javax.swing.JLabel();
        LabelValorCus53 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaReceive = new javax.swing.JTextArea();
        LabelValorCus136 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaSend = new javax.swing.JTextArea();
        btnEnviar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        txtName = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        listOnlines = new javax.swing.JList();
        jTextPressaoPaciente = new javax.swing.JTextField();
        jTextTipoConsulta = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Altessmann Sistemas - Central de Consultas");
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Consultas  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 16))); // NOI18N

        jTableConsultas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Médico", "Data Consulta", "Hora Consulta", "Tipo Consulta", "Ficha", "Status", "Requisição de exames"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableConsultas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableConsultas.setMaximumSize(new java.awt.Dimension(2147483647, 200));
        jTableConsultas.setMinimumSize(new java.awt.Dimension(770, 800));
        jTableConsultas.setRowHeight(20);
        jTableConsultas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableConsultas.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableConsultas);
        if (jTableConsultas.getColumnModel().getColumnCount() > 0) {
            jTableConsultas.getColumnModel().getColumn(0).setMinWidth(100);
            jTableConsultas.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTableConsultas.getColumnModel().getColumn(0).setMaxWidth(100);
            jTableConsultas.getColumnModel().getColumn(1).setMinWidth(200);
            jTableConsultas.getColumnModel().getColumn(1).setPreferredWidth(200);
            jTableConsultas.getColumnModel().getColumn(1).setMaxWidth(200);
            jTableConsultas.getColumnModel().getColumn(2).setMinWidth(200);
            jTableConsultas.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTableConsultas.getColumnModel().getColumn(2).setMaxWidth(200);
            jTableConsultas.getColumnModel().getColumn(3).setMinWidth(150);
            jTableConsultas.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTableConsultas.getColumnModel().getColumn(3).setMaxWidth(150);
            jTableConsultas.getColumnModel().getColumn(4).setMinWidth(150);
            jTableConsultas.getColumnModel().getColumn(4).setPreferredWidth(150);
            jTableConsultas.getColumnModel().getColumn(4).setMaxWidth(150);
            jTableConsultas.getColumnModel().getColumn(5).setMinWidth(150);
            jTableConsultas.getColumnModel().getColumn(5).setPreferredWidth(150);
            jTableConsultas.getColumnModel().getColumn(5).setMaxWidth(150);
            jTableConsultas.getColumnModel().getColumn(6).setMinWidth(120);
            jTableConsultas.getColumnModel().getColumn(6).setPreferredWidth(120);
            jTableConsultas.getColumnModel().getColumn(6).setMaxWidth(120);
            jTableConsultas.getColumnModel().getColumn(7).setMinWidth(100);
            jTableConsultas.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTableConsultas.getColumnModel().getColumn(7).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Médicos Disponiveis para Atendimento  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 16))); // NOI18N

        jTableAtendimento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Médico", "Já atendidas", "Restantes", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableAtendimento.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableAtendimento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableAtendimentoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableAtendimento);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2)
                .addGap(1, 1, 1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Gerencia Consulta  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jButtonChamarProximo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonChamarProximo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/salvarCliente (2).png"))); // NOI18N
        jButtonChamarProximo.setText("Chamar Próximo");
        jButtonChamarProximo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonChamarProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChamarProximoActionPerformed(evt);
            }
        });

        jComboSelecionaMedico.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jComboSelecionaMedico.setToolTipText("");
        jComboSelecionaMedico.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboSelecionaMedicoItemStateChanged(evt);
            }
        });
        jComboSelecionaMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboSelecionaMedicoActionPerformed(evt);
            }
        });

        LabelValorCus121.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus121.setText("Selecionar Médico:");

        jButtonFinalizar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButtonFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/confirmar.png"))); // NOI18N
        jButtonFinalizar.setText("Finalizar ");
        jButtonFinalizar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFinalizarActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), " Fichas Atendimento  ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabelChamadaAtual.setFont(new java.awt.Font("Arial", 1, 60)); // NOI18N
        jLabelChamadaAtual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelChamadaAtual.setText("--");

        jSeparator2.setToolTipText("");
        jSeparator2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabelUltimaChamada.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelUltimaChamada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelUltimaChamada.setText("--");

        jLabelPenultimaChamada.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelPenultimaChamada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPenultimaChamada.setText("--");

        jLabelAntePenultimaChamada.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelAntePenultimaChamada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAntePenultimaChamada.setText("--");

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator9.setToolTipText("");
        jSeparator9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabelSala.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelSala.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelSala.setText("--");

        jSeparator10.setToolTipText("");
        jSeparator10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelUltimaChamada, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelPenultimaChamada, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelAntePenultimaChamada, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelChamadaAtual, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabelSala, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelChamadaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelUltimaChamada, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabelPenultimaChamada))
                    .addComponent(jLabelAntePenultimaChamada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator8)
                    .addComponent(jSeparator7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fotoadd.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jTextNomePaciente.setEditable(false);
        jTextNomePaciente.setBackground(new java.awt.Color(255, 255, 255));
        jTextNomePaciente.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextNomePaciente.setForeground(new java.awt.Color(153, 153, 153));
        jTextNomePaciente.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextNomePaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNomePacienteActionPerformed(evt);
            }
        });

        LabelValorCus126.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus126.setText("Nome Completo:");

        jTextPesoPaciente.setEditable(false);
        jTextPesoPaciente.setBackground(new java.awt.Color(255, 255, 255));
        jTextPesoPaciente.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextPesoPaciente.setForeground(new java.awt.Color(153, 153, 153));
        jTextPesoPaciente.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextPesoPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPesoPacienteActionPerformed(evt);
            }
        });

        LabelValorCus132.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus132.setText("Peso:");

        jTextConvenio.setEditable(false);
        jTextConvenio.setBackground(new java.awt.Color(255, 255, 255));
        jTextConvenio.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextConvenio.setForeground(new java.awt.Color(153, 153, 153));
        jTextConvenio.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextConvenio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextConvenioActionPerformed(evt);
            }
        });

        LabelValorCus133.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus133.setText("Pressão Arterial:");

        jTextAlturaPaciente.setEditable(false);
        jTextAlturaPaciente.setBackground(new java.awt.Color(255, 255, 255));
        jTextAlturaPaciente.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextAlturaPaciente.setForeground(new java.awt.Color(153, 153, 153));
        jTextAlturaPaciente.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextAlturaPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextAlturaPacienteActionPerformed(evt);
            }
        });

        LabelValorCus134.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus134.setText("Altura:");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        LabelValorCus122.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus122.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelValorCus122.setText("Status da Consulta");

        jTextStatusConsulta.setEditable(false);
        jTextStatusConsulta.setBackground(new java.awt.Color(255, 255, 255));
        jTextStatusConsulta.setFont(new java.awt.Font("Arial", 0, 23)); // NOI18N
        jTextStatusConsulta.setForeground(new java.awt.Color(51, 153, 0));
        jTextStatusConsulta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextStatusConsulta.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextStatusConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextStatusConsultaActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextCodConsulta.setEditable(false);
        jTextCodConsulta.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextCodConsulta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextCodConsulta.setText("0000");
        jTextCodConsulta.setBorder(null);

        LabelValorCus135.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus135.setText("Cod:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(LabelValorCus135)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextCodConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LabelValorCus135, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextCodConsulta)
        );

        LabelValorCus123.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus123.setText("Tipo de Consuta:");

        LabelValorCus53.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus53.setText("Convenio Paciente:");

        txtAreaReceive.setEditable(false);
        txtAreaReceive.setColumns(20);
        txtAreaReceive.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        txtAreaReceive.setRows(6);
        jScrollPane3.setViewportView(txtAreaReceive);

        LabelValorCus136.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        LabelValorCus136.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelValorCus136.setText("Messenger - Usuario :");

        txtAreaSend.setColumns(20);
        txtAreaSend.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAreaSend.setRows(4);
        jScrollPane4.setViewportView(txtAreaSend);

        btnEnviar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/confirmar.png"))); // NOI18N
        btnEnviar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        btnLimpar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/close.png"))); // NOI18N
        btnLimpar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        txtName.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtName.setText("Usuario");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "  Onlines  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        listOnlines.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane5.setViewportView(listOnlines);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        );

        jTextPressaoPaciente.setEditable(false);
        jTextPressaoPaciente.setBackground(new java.awt.Color(255, 255, 255));
        jTextPressaoPaciente.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextPressaoPaciente.setForeground(new java.awt.Color(153, 153, 153));
        jTextPressaoPaciente.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextPressaoPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPressaoPacienteActionPerformed(evt);
            }
        });

        jTextTipoConsulta.setEditable(false);
        jTextTipoConsulta.setBackground(new java.awt.Color(255, 255, 255));
        jTextTipoConsulta.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jTextTipoConsulta.setForeground(new java.awt.Color(153, 153, 153));
        jTextTipoConsulta.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTextTipoConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextTipoConsultaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelValorCus121)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jComboSelecionaMedico, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jButtonFinalizar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonChamarProximo))
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(LabelValorCus123, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelValorCus53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(LabelValorCus132)
                                    .addComponent(jTextPesoPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addComponent(LabelValorCus133)
                                        .addGap(23, 23, 23))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextPressaoPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelValorCus134)
                                    .addComponent(jTextAlturaPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(LabelValorCus126, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextNomePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextStatusConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LabelValorCus122, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextTipoConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextConvenio, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(LabelValorCus136)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jScrollPane4)
                            .addGap(3, 3, 3)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(5, 5, 5)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(LabelValorCus121)
                                .addGap(1, 1, 1)
                                .addComponent(jComboSelecionaMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonFinalizar)
                                    .addComponent(jButtonChamarProximo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(LabelValorCus122)
                                        .addGap(1, 1, 1)
                                        .addComponent(jTextStatusConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(LabelValorCus126)
                                                .addGap(37, 37, 37))
                                            .addComponent(jTextNomePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(LabelValorCus132)
                                                .addGap(1, 1, 1)
                                                .addComponent(jTextPesoPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(LabelValorCus133)
                                                .addGap(37, 37, 37))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(LabelValorCus134)
                                                .addGap(1, 1, 1)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jTextAlturaPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextPressaoPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelValorCus123)
                                    .addComponent(LabelValorCus53))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextConvenio, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextTipoConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator3)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelValorCus136)
                                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(1, 1, 1)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnEnviar)
                                        .addComponent(jScrollPane4))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addComponent(btnLimpar)))))))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFinalizarActionPerformed
        int codMedico = 0, posicao, codigo = 0;
        String separado = null, atual;
        if (jComboSelecionaMedico.getSelectedIndex() != -1) {
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

        /*if (consultaDAO.haveConsulta(0, codMedico)) {
            //getDadosConsultaCombo(consultaDAO.getCodConsulta(0, codMedico));
            codigo = consultaDAO.getCodConsulta(0, codMedico);
            //consultaDAO.setStatusFinalizadaConsulta(codigo);
            jButtonChamarProximo.setEnabled(true);
            //System.out.println(consultaDAO.getUltimaChamadaCombo(0, codMedico));
        } else {
            if (consultaDAO.haveConsulta(1, codMedico)) {
                //getDadosConsultaCombo(consultaDAO.getCodConsulta(1, codMedico));
                jButtonChamarProximo.setEnabled(true);
                codigo = consultaDAO.getCodConsulta(1, codMedico);
                //consultaDAO.setStatusFinalizadaConsulta(codigo);
                //System.out.println(consultaDAO.getUltimaChamadaCombo(1, codMedico));
            } else {
                if (consultaDAO.haveConsulta(2, codMedico)) {
                    //getDadosConsultaCombo(consultaDAO.getCodConsulta(2, codMedico));
                    jButtonChamarProximo.setEnabled(true);
                    
                   // consultaDAO.setStatusFinalizadaConsulta(codigo);
                    //System.out.println(consultaDAO.getUltimaChamadaCombo(2, codMedico));
                }
            }
           }*/
        //codigo = consultaDAO.getCodConsulta(codMedico);
        jTextStatusConsulta.setText("Finalizada!");
        //System.out.println(codigo);
        consultaDAO.setStatusFinalizadaConsulta(consultaDAO.getUltimaChamadaCombo(codMedico));
        getConsultas();
        jButtonFinalizar.setEnabled(false);
        jButtonChamarProximo.setEnabled(true);
        atualizaterminais();
        //System.out.println(codigo);

    }//GEN-LAST:event_jButtonFinalizarActionPerformed

    private void jButtonChamarProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChamarProximoActionPerformed
        jButtonChamarProximo.setEnabled(false);
        callNext();
        atualizaterminais();
        //getConsultas();
    }//GEN-LAST:event_jButtonChamarProximoActionPerformed

    private void jTextNomePacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNomePacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNomePacienteActionPerformed

    private void jTextPesoPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPesoPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPesoPacienteActionPerformed

    private void jTextConvenioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextConvenioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextConvenioActionPerformed

    private void jTextAlturaPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextAlturaPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextAlturaPacienteActionPerformed

    private void jTextStatusConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextStatusConsultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextStatusConsultaActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        String text = this.txtAreaSend.getText();
        String name = this.message.getName();
        if (!text.isEmpty()) {
            this.message = new ChatMessage();

            if (this.listOnlines.getSelectedIndex() > -1) {
                this.message.setNameReserved((String) this.listOnlines.getSelectedValue());
                this.message.setAction(Action.SEND_ONE);
                //this.listOnlines.clearSelection();
            } else {
                this.message.setAction(Action.SEND_ALL);
            }

            if (!text.isEmpty()) {
                this.message.setName(name);
                this.message.setText(text);
                this.txtAreaReceive.append("--------------------------------\n");
                this.txtAreaReceive.append("Você disse: " + text + "\n");
                this.txtAreaReceive.append("--------------------------------\n");
                this.service.send(this.message);
                txtAreaReceive.setCaretPosition(txtAreaReceive.getText().length());
            }

            this.txtAreaSend.setText("");
        }
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        this.txtAreaSend.setText("");
    }//GEN-LAST:event_btnLimparActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        //ChatMessage message = new ChatMessage();
        this.message.setName(this.message.getName());
        this.message.setAction(Action.DISCONNECT);
        this.service.send(message);
        this.listOnlines.setModel(new DefaultListModel());
        disconnected();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

//        conect();
//        this.txtAreaSend.setEnabled(false);
//        this.txtAreaReceive.setEnabled(false);
//        this.btnEnviar.setEnabled(false);
//        this.btnLimpar.setEnabled(false);
//        this.txtAreaReceive.setText("");
//        this.txtAreaSend.setText("");
//        atualizaterminal();
        //jButtonFinalizar.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void jComboSelecionaMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboSelecionaMedicoActionPerformed

//        int codMedico = 0, posicao;
//        String separado = null, atual;
//        if (jComboSelecionaMedico.getSelectedIndex() != -1) {
//            atual = jComboSelecionaMedico.getSelectedItem() + "";// FAZ O MESMO PARA CONSULTA
//            posicao = 0;
//            char[] a = atual.toCharArray();
//            for (int i = 0; i < atual.length(); i++) {
//                if (a[i] == '-') {
//                    posicao = i;
//                }
//            }
//            separado = atual.substring(0, posicao);
//            codMedico = Integer.parseInt(separado);
//
//        }
//        /*   if (consultaDAO.haveConsulta(0, codMedico)) {
//            //getDadosConsultaCombo(consultaDAO.getCodConsulta(0, codMedico));
//            //jButtonChamarProximo.setEnabled(true);
//        } else {
//            if (consultaDAO.haveConsulta(1, codMedico)) {
//                //getDadosConsultaCombo(consultaDAO.getCodConsulta(1, codMedico));
//                //jButtonChamarProximo.setEnabled(true);
//            } else {
//                if (consultaDAO.haveConsulta(2, codMedico)) {
//                    //getDadosConsultaCombo(consultaDAO.getCodConsulta(2, codMedico));
//                    //jButtonChamarProximo.setEnabled(true);
//                }
//            }
//
//        }*/
//        if (consultaDAO.getUltimaChamadaCombo(codMedico) != 0) {
//            jButtonFinalizar.setEnabled(true);
//            jButtonChamarProximo.setEnabled(false);
//        } else {
//            jButtonFinalizar.setEnabled(false);
//            jButtonChamarProximo.setEnabled(true);
//        }
        //System.out.println(consultaDAO.foiFinalizada(consultaDAO.getCodConsulta(0, codMedico)));
    }//GEN-LAST:event_jComboSelecionaMedicoActionPerformed

    private void jTableAtendimentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAtendimentoMouseClicked

    }//GEN-LAST:event_jTableAtendimentoMouseClicked

    private void jTextPressaoPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPressaoPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPressaoPacienteActionPerformed

    private void jTextTipoConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextTipoConsultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextTipoConsultaActionPerformed

    private void jComboSelecionaMedicoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboSelecionaMedicoItemStateChanged
        int codMedico = 0, posicao;
        String separado = null, atual;
        if (jComboSelecionaMedico.getSelectedIndex() != -1) {
            atual = jComboSelecionaMedico.getSelectedItem() + "";
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

        if ((consultaDAO.getUltimaChamadaCombo(codMedico)) != 0) {
            getDadosConsulta(consultaDAO.getUltimaChamadaCombo(codMedico)); // AQUI ELE DEVE PREENCHER OS DADOS DA CONSULTA QUE TA EM ATENDIMENTO ELE TA MOSTRANDO A ULTIMA CONSULTA
        } else {
            limpaPaciente();
        }

        //getDadosConsulta(consultaDAO.getCodConsulta(codMedico));
        if (consultaDAO.getUltimaChamadaCombonew(codMedico)) {     //se o medico selecionado estiver em atendimento
            jButtonFinalizar.setEnabled(true);
            jButtonChamarProximo.setEnabled(false);
        } else {
            jButtonFinalizar.setEnabled(false);
            jButtonChamarProximo.setEnabled(true);
        }
    }//GEN-LAST:event_jComboSelecionaMedicoItemStateChanged

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        conect();
        this.txtAreaSend.setEnabled(false);
        this.txtAreaReceive.setEnabled(false);
        this.btnEnviar.setEnabled(false);
        this.btnLimpar.setEnabled(false);
        this.txtAreaReceive.setText("");
        this.txtAreaSend.setText("");
        atualizaterminal();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        this.message.setName(this.message.getName());
        this.message.setAction(Action.DISCONNECT);
        this.service.send(message);
        this.listOnlines.setModel(new DefaultListModel());
        disconnected();
    }//GEN-LAST:event_formWindowDeactivated

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
            java.util.logging.Logger.getLogger(CentralDeConsultasAtendenteNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CentralDeConsultasAtendenteNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CentralDeConsultasAtendenteNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CentralDeConsultasAtendenteNew.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CentralDeConsultasAtendenteNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelValorCus121;
    private javax.swing.JLabel LabelValorCus122;
    private javax.swing.JLabel LabelValorCus123;
    private javax.swing.JLabel LabelValorCus126;
    private javax.swing.JLabel LabelValorCus132;
    private javax.swing.JLabel LabelValorCus133;
    private javax.swing.JLabel LabelValorCus134;
    private javax.swing.JLabel LabelValorCus135;
    private javax.swing.JLabel LabelValorCus136;
    private javax.swing.JLabel LabelValorCus53;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton jButtonChamarProximo;
    private javax.swing.JButton jButtonFinalizar;
    private javax.swing.JComboBox jComboSelecionaMedico;
    private javax.swing.JLabel jLabelAntePenultimaChamada;
    private javax.swing.JLabel jLabelChamadaAtual;
    private javax.swing.JLabel jLabelFoto;
    private javax.swing.JLabel jLabelPenultimaChamada;
    private javax.swing.JLabel jLabelSala;
    private javax.swing.JLabel jLabelUltimaChamada;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTableAtendimento;
    private javax.swing.JTable jTableConsultas;
    private javax.swing.JTextField jTextAlturaPaciente;
    private javax.swing.JTextField jTextCodConsulta;
    private javax.swing.JTextField jTextConvenio;
    private javax.swing.JTextField jTextNomePaciente;
    private javax.swing.JTextField jTextPesoPaciente;
    private javax.swing.JTextField jTextPressaoPaciente;
    private javax.swing.JTextField jTextStatusConsulta;
    private javax.swing.JTextField jTextTipoConsulta;
    private javax.swing.JList listOnlines;
    private javax.swing.JTextArea txtAreaReceive;
    private javax.swing.JTextArea txtAreaSend;
    private javax.swing.JLabel txtName;
    // End of variables declaration//GEN-END:variables
}
