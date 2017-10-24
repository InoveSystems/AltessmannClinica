/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.DAO;

import com.altessmann.Bean.ContasReceberBean;
import com.altessmann.ConnecionFactory.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class ContasReceberDAO {

    Connection conexao;
    Logger logs;
    private int numeroLinhas;

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public ContasReceberDAO() {
        try {
            this.conexao = ConnectionFactory.openConnection();
        } catch (Exception ex) {
            Logger.getLogger(ContasReceberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ContasReceberBean> getContasReceber(int codigo) throws SQLException {
        List<ContasReceberBean> lista = new ArrayList<ContasReceberBean>();
        String sql = "select debitoreceber.codigo,paciente.codigo,initcap(paciente.nome),debitoreceber.codConsulta,paciente.ativo,debitoreceber.dtVencimento,debitoreceber.dtCriacao,(debitoreceber.valortotal/debitoreceber.parcelas),debitoreceber.quitado,paciente.caminhofoto,debitoreceber.dtatualizacao from debitoreceber inner join consulta on debitoreceber.codconsulta=consulta.codigo inner join paciente on consulta.codpaciente=paciente.codigo where debitoreceber.codigo=" + codigo + " order by debitoreceber.dtVencimento ";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ContasReceberBean contasReceberBean = new ContasReceberBean();
            contasReceberBean.setCodigo(rs.getInt(1));
            contasReceberBean.setCodPaciente(rs.getInt(2));
            contasReceberBean.setNomePaciente(rs.getString(3));
            contasReceberBean.setCodConsulta(rs.getInt(4));
            contasReceberBean.setAtivo(rs.getBoolean(5));
            contasReceberBean.setDtVencimente(rs.getDate(6));
            contasReceberBean.setDtCriacao(rs.getDate(7));
            contasReceberBean.setValorTotal(rs.getDouble(8));
            contasReceberBean.setQuitado(rs.getBoolean(9));
            contasReceberBean.setCaminhoFoto(rs.getString(10));
            contasReceberBean.setDtAtualizacao(rs.getDate(11));
            lista.add(contasReceberBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ContasReceberBean> getDebitoReceber(Date data) throws SQLException {
        List<ContasReceberBean> lista = new ArrayList<ContasReceberBean>();
        String sql = "select debitoreceber.codigo,consulta.codigo,initcap(paciente.nome),(debitoreceber.valortotal/debitoreceber.parcelas),debitoreceber.dtvencimento,debitoreceber.quitado from debitoreceber inner join consulta on debitoreceber.codConsulta=consulta.codigo inner join paciente on consulta.codpaciente=paciente.codigo where dtVencimento='" + data + "' order by dtVencimento";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ContasReceberBean contasReceberBean = new ContasReceberBean();
            contasReceberBean.setCodigo(rs.getInt(1));
            contasReceberBean.setNomePaciente(rs.getString(3));
            contasReceberBean.setCodConsulta(rs.getInt(2));
            contasReceberBean.setDtVencimente(rs.getDate(5));
            contasReceberBean.setValorTotal(rs.getDouble(4));
            contasReceberBean.setQuitado(rs.getBoolean(6));
            lista.add(contasReceberBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ContasReceberBean> getDebitoReceber(String nome) throws SQLException {
        List<ContasReceberBean> lista = new ArrayList<ContasReceberBean>();
        String sql = "select debitoreceber.codigo,consulta.codigo,initcap(paciente.nome),(debitoreceber.valortotal/debitoreceber.parcelas),debitoreceber.dtvencimento,debitoreceber.quitado from debitoreceber inner join consulta on debitoreceber.codConsulta=consulta.codigo inner join paciente on consulta.codpaciente=paciente.codigo where paciente.nome like lower('" + nome + "%') order by dtVencimento";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ContasReceberBean contasReceberBean = new ContasReceberBean();
            contasReceberBean.setCodigo(rs.getInt(1));
            contasReceberBean.setNomePaciente(rs.getString(3));
            contasReceberBean.setCodConsulta(rs.getInt(2));
            contasReceberBean.setDtVencimente(rs.getDate(5));
            contasReceberBean.setValorTotal(rs.getDouble(4));
            contasReceberBean.setQuitado(rs.getBoolean(6));
            lista.add(contasReceberBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ContasReceberBean> getDebitoReceber(int codigo) throws SQLException {
        List<ContasReceberBean> lista = new ArrayList<ContasReceberBean>();
        String sql = "select debitoreceber.codigo,consulta.codigo,initcap(paciente.nome),(debitoreceber.valortotal/debitoreceber.parcelas),debitoreceber.dtvencimento,debitoreceber.quitado from debitoreceber inner join consulta on debitoreceber.codConsulta=consulta.codigo inner join paciente on consulta.codpaciente=paciente.codigo where paciente.codigo=" + codigo + " order by dtVencimento";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ContasReceberBean contasReceberBean = new ContasReceberBean();
            contasReceberBean.setCodigo(rs.getInt(1));
            contasReceberBean.setNomePaciente(rs.getString(3));
            contasReceberBean.setCodConsulta(rs.getInt(2));
            contasReceberBean.setDtVencimente(rs.getDate(5));
            contasReceberBean.setValorTotal(rs.getDouble(4));
            contasReceberBean.setQuitado(rs.getBoolean(6));
            lista.add(contasReceberBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }
    
    public void quitarContaReceber(int codigo){
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update debitoReceber set quitado=true, dtAtualizacao= now() where codigo=" + codigo;
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Débito quitado!", "Dados gravados", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
