/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.DAO;

import com.altessmann.Bean.ContasPagarBean;
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
public class ContasPagarDAO {

    Connection conexao;
    Logger logs;
    private int numeroLinhas;

    public Connection getConexao() {
        return conexao;
    }

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public ContasPagarDAO() {
        try {
            this.conexao = ConnectionFactory.openConnection();
        } catch (Exception ex) {
            Logger.getLogger(ContasPagarDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getUltimoCodigo() throws SQLException {
        Statement st = conexao.createStatement();
        String sqlDLL = "select max(codigo) from debitopagar";
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
        String sqlDLL = "select tentativas from debitopagar where codigo=" + cod + " ";
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

    public void setContasPagar(String nomeFornecedor, Date dtVencimento, String nfe, String codBarras, String banco, int tentativas, double valor) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = " insert into debitopagar (nomeFornecedor,dtVencimento,nfe,banco,dtCriacao,tentativas,valor,quitado,codBarras) values(lower('" + nomeFornecedor + "'), '" + dtVencimento + "','" + nfe + "','" + banco + "',now()," + tentativas + "," + valor + ",false,'" + codBarras + "')";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ContasPagarBean> getContasPagar(int codigo) throws SQLException {
        List<ContasPagarBean> lista = new ArrayList<ContasPagarBean>();
        String sql = "select codigo, initcap(nomeFornecedor),dtVencimento,nfe, banco, dtCriacao, dtAtualizacao,tentativas,quitado,valor,codBarras from debitopagar where codigo= " + codigo;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ContasPagarBean contasPagarBean = new ContasPagarBean();
            contasPagarBean.setCodigo(rs.getInt(1));
            contasPagarBean.setNomeFornecedor(rs.getString(2));
            contasPagarBean.setDtVencimento(rs.getDate(3));
            contasPagarBean.setNfe(rs.getString(4));
            contasPagarBean.setBanco(rs.getString(5));
            contasPagarBean.setDtCriacao(rs.getDate(6));
            contasPagarBean.setDtAtualizacao(rs.getDate(7));
            contasPagarBean.setTentativas(rs.getInt(8));
            contasPagarBean.setQuitado(rs.getBoolean(9));
            contasPagarBean.setValor(rs.getDouble(10));
            contasPagarBean.setCodBarras(rs.getString(11));
            lista.add(contasPagarBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public void atualizaContasPagar(int codigo, String nomeFornecedor, Date dtVencimento, String nfe, String codBarras, String banco, int tentativas, double valor) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update debitopagar set nomeFornecedor=lower('" + nomeFornecedor + "'),codBarras='" + codBarras + "',dtVencimento='" + dtVencimento + "',nfe='" + nfe + "',banco='" + banco + "',valor=" + valor + ",tentativas=" + tentativas + " where codigo=" + codigo;
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ContasPagarBean> getDebitoPagar(Date data) throws SQLException {
        List<ContasPagarBean> lista = new ArrayList<ContasPagarBean>();
        String sql = "select codigo,initcap(nomeFornecedor),dtVencimento,quitado,valor from debitopagar where dtvencimento ='" + data + "' order by dtVencimento";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ContasPagarBean contasPagarBean = new ContasPagarBean();
            contasPagarBean.setCodigo(rs.getInt(1));
            contasPagarBean.setNomeFornecedor(rs.getString(2));
            contasPagarBean.setDtVencimento(rs.getDate(3));
            contasPagarBean.setQuitado(rs.getBoolean(4));
            contasPagarBean.setValor(rs.getDouble(5));
            lista.add(contasPagarBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ContasPagarBean> getDebitoPagar(String nome) throws SQLException {
        List<ContasPagarBean> lista = new ArrayList<ContasPagarBean>();
        String sql = "select codigo,initcap(nomeFornecedor),dtVencimento,quitado,valor from debitopagar where nomeFornecedor like lower('" + nome + "%') order by dtVencimento";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ContasPagarBean contasPagarBean = new ContasPagarBean();
            contasPagarBean.setCodigo(rs.getInt(1));
            contasPagarBean.setNomeFornecedor(rs.getString(2));
            contasPagarBean.setDtVencimento(rs.getDate(3));
            contasPagarBean.setQuitado(rs.getBoolean(4));
            contasPagarBean.setValor(rs.getDouble(5));
            lista.add(contasPagarBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ContasPagarBean> getDebitoPagar(int codigo) throws SQLException {
        List<ContasPagarBean> lista = new ArrayList<ContasPagarBean>();
        String sql = "select codigo,initcap(nomeFornecedor),dtVencimento,quitado,valor from debitopagar where codigo=" + codigo + " order by dtVencimento";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ContasPagarBean contasPagarBean = new ContasPagarBean();
            contasPagarBean.setCodigo(rs.getInt(1));
            contasPagarBean.setNomeFornecedor(rs.getString(2));
            contasPagarBean.setDtVencimento(rs.getDate(3));
            contasPagarBean.setQuitado(rs.getBoolean(4));
            contasPagarBean.setValor(rs.getDouble(5));
            lista.add(contasPagarBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public void quitarContaPagar(int codigo) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update debitoPagar set quitado=true, dtAtualizacao= now() where codigo=" + codigo;
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Débito quitado!", "Dados gravados", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getContasPagar(Date data) {
        int c = 0;
        try {
            Statement st = conexao.createStatement();
            String sql = "select codigo from debitopagar where dtvencimento='" + data + "' and quitado= false";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                c++;
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

}
