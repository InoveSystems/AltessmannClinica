/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.altessmann.Tools;

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
 * @author Milton
 */

public class BancoExterno {
    
    Connection conexao;
    Logger logs = Logger.getLogger(BancoExterno.class.getName());
    private Object ex;
    public BancoExterno(){
    
        try {
            this.conexao=ConnectionFactory.openConnection();
        } catch (Exception ex) {
            Logger.getLogger(BancoExterno.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    public boolean getValidacao(String serial) throws SQLException{
        boolean serialValido=false;
        Statement st = conexao.createStatement();
        String sqlDLL = "select serial from validacao where serial ='" + serial + "'";
        ResultSet rs = st.executeQuery(sqlDLL);
        try {

            while (rs.next()) {
                String sv = rs.getString(1);
                if(serial.equals(sv)){
                    serialValido=true;
                }
            }
            st.close();
            return serialValido;
            
        } catch (SQLException ex) {
            Logger.getLogger(BancoExterno.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!", "Erro", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        
        return false;
    }
}
