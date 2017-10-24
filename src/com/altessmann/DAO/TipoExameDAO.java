/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.DAO;

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
import com.altessmann.Bean.TipoExameBean;

/**
 *
 * @author Guilherme
 */
public class TipoExameDAO {

    Connection conexao;
    Logger logs;
    private int numeroLinhas;

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public TipoExameDAO() {
        try {
            this.conexao = ConnectionFactory.openConnection();
        } catch (Exception ex) {
           
        }
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

    public void setTipoExame(String nome, String descricao,double valor ,int tentativas) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "insert into tipoexame (nome,descricao,dtCriacao,valor,tentativas) values(lower('" + nome + "'),'" + descricao + "',now(),"+valor+"," + tentativas + ")";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizaTipoExame(int codigo, String nome, String descricao,double valor, int tentativas) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update tipoexame set nome=lower('" + nome + "'),descricao='" + descricao + "',dtAtualizacao=now(),valor="+valor+",tentativas=" + tentativas + "where codigo=" + codigo;
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<TipoExameBean> getTipoExame(int codigo) throws SQLException {
        java.util.List<TipoExameBean> lista = new ArrayList<TipoExameBean>();
        String sql = "select codigo,initcap(nome),descricao,tentativas,valor from tipoExame where codigo=" + codigo;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            TipoExameBean tipoExameBean = new TipoExameBean();
            tipoExameBean.setCodigo(rs.getInt(1));
            tipoExameBean.setNome(rs.getString(2));
            tipoExameBean.setDescricao(rs.getString(3));
            tipoExameBean.setTentativas(rs.getInt(4));
            tipoExameBean.setValor(rs.getDouble(5));
            lista.add(tipoExameBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<TipoExameBean> getTipoExame() throws SQLException {
        List<TipoExameBean> lista = new ArrayList<TipoExameBean>();
        String sql = "select codigo,initcap(nome),descricao,valor from tipoExame";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            TipoExameBean tipoExameBean = new TipoExameBean();
            tipoExameBean.setCodigo(rs.getInt(1));
            tipoExameBean.setNome(rs.getString(2));
            tipoExameBean.setDescricao(rs.getString(3));
            tipoExameBean.setValor(rs.getDouble(4));
            lista.add(tipoExameBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }
    
    public List<TipoExameBean> getTipoExame(String nome) throws SQLException {
        List<TipoExameBean> lista = new ArrayList<TipoExameBean>();
        String sql = "select codigo,initcap(nome),descricao,valor from tipoExame where nome like lower('" + nome + "%')";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            TipoExameBean tipoExameBean = new TipoExameBean();
            tipoExameBean.setCodigo(rs.getInt(1));
            tipoExameBean.setNome(rs.getString(2));
            tipoExameBean.setDescricao(rs.getString(3));
            tipoExameBean.setValor(rs.getDouble(4));
            lista.add(tipoExameBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public void deletaTipoExame(int codigo) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "delete from tipoExame where codigo=" + codigo + "";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados do Tipo de Exame, com o código " + codigo + " foram excluidos com sucesso.", "Excluir", JOptionPane.WARNING_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
