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

/**
 *
 * @author Ritiele Aldeburg
 */
public class PainelDAO {

    Connection conexao;
    Logger logs;

    public PainelDAO() {
        try {
            this.conexao = ConnectionFactory.openConnection();
        } catch (Exception ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet retriveTotal() throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT * FROM painel";
        rs = stm.executeQuery(sql);
        return rs;
    }

    public ResultSet retriveCompleto(int cod) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT consulta.status,paciente.foto,paciente.nome,consulta.peso,consulta.pressao,consulta.altura,confconsulta.nome as nometipoconsulta,consulta.codMedico from consulta inner join paciente on paciente.codigo=consulta.codpaciente inner join confconsulta on confconsulta.codigo=consulta.codtipoconsulta where consulta.codigo=" + cod;
        rs = stm.executeQuery(sql);
        return rs;
    }

    public ResultSet retriveTipoConvenio(int cod) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "Select convenio.nome as convenionome from convenio inner join consulta on consulta.codConvenio=convenio.codigo where consulta.codigo=" + cod;
        rs = stm.executeQuery(sql); 
        return rs;
    }
}
