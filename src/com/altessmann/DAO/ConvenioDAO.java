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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import com.altessmann.Bean.ConvenioBean;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class ConvenioDAO {

    Connection conexao;
    Logger logs;
    private int numeroLinhas;

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public ConvenioDAO() {
        try {
            this.conexao = ConnectionFactory.openConnection();
        } catch (Exception ex) {
            Logger.getLogger(ConvenioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getUltimoCodigo() throws SQLException {
        Statement st = conexao.createStatement();
        String sqlDLL = "select max(codigo) from convenio";
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
        String sqlDLL = "select tentativas from convenio where codigo=" + cod + " ";
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

    public List<ConvenioBean> getConvenio(int codigo) throws SQLException {
        List<ConvenioBean> lista = new ArrayList<ConvenioBean>();
        String sql = "select codigo,initcap(nome),porcentagem,dtCriacao,dtAtualizacao,tentativas from convenio where codigo=" + codigo + "";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ConvenioBean convenioBean = new ConvenioBean();
            convenioBean.setCodigo(rs.getInt(1));
            convenioBean.setNome(rs.getString(2));
            convenioBean.setPorcentagem(rs.getDouble(3));
            convenioBean.setDtCadastro(rs.getDate(4));
            convenioBean.setDtAtualizacao(rs.getDate(5));
            convenioBean.setTentativas(rs.getInt(6));
            lista.add(convenioBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }
    public List<ConvenioBean> getConvenio() throws SQLException {
        List<ConvenioBean> lista = new ArrayList<ConvenioBean>();
        String sql = "select codigo,initcap(nome),porcentagem from convenio";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ConvenioBean convenioBean = new ConvenioBean();
            convenioBean.setCodigo(rs.getInt(1));
            convenioBean.setNome(rs.getString(2));
            convenioBean.setPorcentagem(rs.getDouble(3));
            lista.add(convenioBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

  

    public void setConvenio(String nome, double porcentagem,int tentativas) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "insert into convenio (nome,porcentagem,dtCriacao,tentativas) values(lower('" + nome + "')," + porcentagem + ",now()," + tentativas + ")";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizaConvenio(int codigo, String nome, double porcentagem,int tentativas) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update convenio set nome='" + nome + "',porcentagem=" + porcentagem + ",dtAtualizacao=now(),tentativas=" + tentativas + " where codigo=" + codigo + "";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deletaConvenio(int codigo) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "delete from convenio where codigo=" + codigo + "";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados do convenio, com o código " + codigo + " foram excluidos com sucesso.", "Excluir", JOptionPane.WARNING_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
