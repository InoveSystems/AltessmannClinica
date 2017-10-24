/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.DAO;

import com.altessmann.Bean.ConfConsultaBean;
import com.altessmann.ConnecionFactory.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class ConfConsultaDAO {

    Connection conexao;
    Logger logs;
    private int numeroLinhas;

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public ConfConsultaDAO() {
        try {
            this.conexao = ConnectionFactory.openConnection();
        } catch (Exception ex) {
            Logger.getLogger(ConfConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getUltimoCodigo() throws SQLException {
        Statement st = conexao.createStatement();
        String sqlDLL = "select max(codigo) from confconsulta";
        ResultSet rs = st.executeQuery(sqlDLL);
        try {
            int id = 0;
            while (rs.next()) {
                id = rs.getInt(1);
            }
            st.close();
            rs.close();

            return id;

        } catch (SQLException ex) {
            Logger.getLogger(com.altessmann.DAO.PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!", "Erro", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

        }

        return 0;
    }

    public int ContaCod(int cod) throws SQLException {
        Statement st = conexao.createStatement();
        String sqlDLL = "select tentativas from confconsulta where codigo=" + cod + " ";
        ResultSet rs = st.executeQuery(sqlDLL);
        try {
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
                if (count == 0) {
                    count++;
                }
                st.close();
                rs.close();
                return count;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!", "Erro", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        return 0;
    }

    public void setConfConsulta(String nome, double valor, int tentativas) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "insert into confconsulta (nome,valor,dtCriacao,tentativas) values(lower('" + nome + "')," + valor + ",now()," + tentativas + ")";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizaConfConsulta(int codigo, String nome, double valor, int tentativas) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update confconsulta set nome='" + nome + "',valor=" + valor + ",dtAtualizacao=now(),tentativas=" + tentativas + " where codigo=" + codigo + "";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ConfConsultaBean> getConfConsulta(int codigo) throws SQLException {
        java.util.List<ConfConsultaBean> lista = new ArrayList<ConfConsultaBean>();
        String sql = "select codigo,initcap(nome),valor,dtAtualizacao,dtCriacao,tentativas from confconsulta where codigo=" + codigo;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ConfConsultaBean confConsultaBean = new ConfConsultaBean();
            confConsultaBean.setCodigo(rs.getInt(1));
            confConsultaBean.setNome(rs.getString(2));
            confConsultaBean.setValor(rs.getDouble(3));
            confConsultaBean.setDtAtualizacao(rs.getDate(4));
            confConsultaBean.setDtCriacao(rs.getDate(5));
            confConsultaBean.setTentativas(rs.getInt(6));
            lista.add(confConsultaBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ConfConsultaBean> getConfConsulta() throws SQLException {
        List<ConfConsultaBean> lista = new ArrayList<ConfConsultaBean>();
        String sql = "select codigo,initcap(nome),valor from confconsulta";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ConfConsultaBean confConsultaBean = new ConfConsultaBean();
            confConsultaBean.setCodigo(rs.getInt(1));
            confConsultaBean.setNome(rs.getString(2));
            confConsultaBean.setValor(rs.getDouble(3));
            lista.add(confConsultaBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public void deletaConvenio(int codigo) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "delete from confconsulta where codigo=" + codigo + "";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados Tipo de Consulta, com o código " + codigo + " foram excluidos com sucesso.", "Excluir", JOptionPane.WARNING_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
