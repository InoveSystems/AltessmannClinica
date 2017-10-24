/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.DAO;

import com.altessmann.Bean.ConsultorioBean;
import com.altessmann.ConnecionFactory.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class ConsultorioDAO {

    Connection conexao;

    public ConsultorioDAO() {
        try {
            this.conexao = ConnectionFactory.openConnection();
        } catch (Exception ex) {
            Logger.getLogger(ConsultorioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ConsultorioBean> getConsultorio() throws SQLException {
        List<ConsultorioBean> lista = new ArrayList<ConsultorioBean>();
        String sql = "select initcap(nome),initcap(endereco),initcap(bairro),initcap(cidade),upper(uf),telefone,crm,mensagem,dtAtualizacao from consultorio";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ConsultorioBean consultorioBean = new ConsultorioBean();
            consultorioBean.setNome(rs.getString(1));
            consultorioBean.setEndereco(rs.getString(2));
            consultorioBean.setBairro(rs.getString(3));
            consultorioBean.setCidade(rs.getString(4));
            consultorioBean.setUf(rs.getString(5));
            consultorioBean.setTelefone(rs.getString(6));
            consultorioBean.setCrm(rs.getString(7));
            consultorioBean.setMensagem(rs.getString(8));
            consultorioBean.setDtAtualizacao(rs.getDate(9));
            lista.add(consultorioBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public void atualizaConsultorio(String nome, String endereco, String bairro, String cidade, String uf, String telefone, String crm, String mensagem) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update consultorio set nome='" + nome + "',endereco='" + endereco + "', bairro='" + bairro + "',  cidade='" + cidade + "',uf='" + uf + "',telefone='" + telefone + "',crm='" + crm + "',mensagem='" + mensagem + "',dtAtualizacao=now()";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
