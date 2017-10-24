/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.DAO;

import com.altessmann.Bean.FuncionarioBean;
import com.altessmann.ConnecionFactory.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class LoginDAO {

    Connection conexao;

    public LoginDAO() {
        try {
            this.conexao = ConnectionFactory.openConnection();
        } catch (Exception ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet retriveUsuario(FuncionarioBean funcionario) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT * FROM funcionario WHERE usuario='" + funcionario.getUsuario() + "'";
        //  String sql = "SELECT * FROM funcionarios WHERE nome='" + funcionario.getNome() + "'";
        rs = stm.executeQuery(sql);
        return rs;
    }

}
