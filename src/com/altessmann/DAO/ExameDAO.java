/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.DAO;

import com.altessmann.Bean.ArquivoBean;
import com.altessmann.Bean.ExameBean;
import com.altessmann.Bean.FuncionarioBean;
import com.altessmann.ConnecionFactory.ConnectionFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class ExameDAO {

    Connection conexao;
    Logger logs;
    private int numeroLinhas;

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public ExameDAO() {
        try {
            this.conexao = ConnectionFactory.openConnection();
        } catch (Exception ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int setNovoCodigo() throws SQLException {
        Statement st = conexao.createStatement();
        String sqlDLL = "select nextval('seq1exame')";
        ResultSet rs = st.executeQuery(sqlDLL);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);

        }
        st.close();
        rs.close();
        return id;
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

    public void setExame(int codigo, int codMedico, int codPaciente, int codTipoExame, String laudo, Date dtRequisicao, Date dtLaudo, int maiorNumeroArquivo, int tentativas) {
        java.sql.Date dataRequisicaoSql = new java.sql.Date(dtRequisicao.getTime());
        java.sql.Date dataLaudoSql = new java.sql.Date(dtLaudo.getTime());
        java.sql.Date dataAtualSql = new java.sql.Date((new GregorianCalendar().getTime()).getTime());
        try (
                PreparedStatement ps = conexao.prepareStatement("insert into exame ("
                        + "codigo,"
                        + "codMedico,"
                        + "codpaciente,"
                        + "codtipoexame,"
                        + "laudo,"
                        + "dtrequisicao,"
                        + "dtlaudo,"
                        + "dtcriacao,"
                        + "maiorNumeroArquivo,"
                        + "tentativas)"
                        + " VALUES (?,?,?,?,?,?,?,?,?,?)")) {
            ps.setInt(1, codigo);
            ps.setInt(2, codMedico);
            ps.setInt(3, codPaciente);
            ps.setInt(4, codTipoExame);
            ps.setString(5, laudo);
            ps.setDate(6, dataRequisicaoSql);
            ps.setDate(7, dataLaudoSql);
            ps.setDate(8, dataAtualSql);
            ps.setInt(9, maiorNumeroArquivo);
            ps.setInt(10, tentativas);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!", "Dados inseridos", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setArquivo(int codigo, String nome, int codExame, byte[] arquivo, String data, String hora) {
        //java.sql.Date dataAtualSql = new java.sql.Date((new GregorianCalendar().getTimeInMillis()));
        //java.sql.Time horaAtualSql = new java.sql.Time(new GregorianCalendar().getTimeInMillis()); isso funciona mas decidi colocar timestamp dai pega tudo no banco
        try (
                InputStream fis = new ByteArrayInputStream(arquivo);
                PreparedStatement ps = conexao.prepareStatement("insert into arquivo ("
                        + "codigo,"
                        + "nome,"
                        + "codExame,"
                        + "dtinsercao,"
                        + "hora,"
                        + "imagem)"
                        + " VALUES (?,?,?,?,?,?)")) {
            ps.setInt(1, codigo);
            ps.setString(2, nome);
            ps.setInt(3, codExame);
            ps.setString(4, data);
            ps.setString(5, hora);
            ps.setBinaryStream(6, fis, arquivo.length);
            ps.executeUpdate();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ArquivoBean> getArquivo(int codExame) {
        java.util.List<ArquivoBean> lista = new ArrayList<ArquivoBean>();
        try {
            String sql = "SELECT "
                    + "codigo,"
                    + "nome,"
                    + "imagem,"
                    + "dtInsercao,"
                    + "hora "
                    + "from arquivo where codexame=" + codExame;
            Statement st;
            st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ArquivoBean arquivoBean = new ArquivoBean();
                arquivoBean.setCodigoArquivo(rs.getInt(1));
                arquivoBean.setNome(rs.getString(2));
                arquivoBean.setArquivo(rs.getBytes(3));
                arquivoBean.setData(rs.getString(4));
                arquivoBean.setHora(rs.getString(5));
                lista.add(arquivoBean);

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public void deletaArquivos(int codExame) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "delete from arquivo where codExame=" + codExame + "";
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizaExame(int codigo, int codMedico, int codPaciente, int codTipoExame, String laudo, Date dtLaudo, int maiorNumeroArquivo, int tentativas) {
        Calendar calendar = new GregorianCalendar();
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update exame set codMedico=" + codMedico + ",codPaciente=" + codPaciente + ",codTipoExame=" + codTipoExame + ",laudo='" + laudo + "',dtLaudo='" + dtLaudo + "',maiorNumeroArquivo=" + maiorNumeroArquivo + ",tentativas=" + tentativas + " where codigo=" + codigo;
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ExameBean> getExame(int codigo) throws SQLException {
        List<ExameBean> lista = new ArrayList<ExameBean>();
        String sql = "SELECT "
                + "exame.codigo, "
                + "paciente.codigo, "
                + "paciente.nome, "
                + "funcionario.codigo, "
                + "funcionario.nome, "
                + "tipoExame.codigo, "
                + "tipoExame.nome, "
                + "tipoExame.descricao, "
                + "exame.dtRequisicao, "
                + "exame.dtLaudo, "
                + "exame.laudo, "
                + "exame.maiorNumeroArquivo, "
                + "exame.tentativas "
                + "from paciente inner join exame on "
                + "paciente.codigo=exame.codpaciente inner join funcionario on "
                + "funcionario.codigo=exame.codMedico inner join tipoexame on "
                + "tipoexame.codigo=exame.codtipoexame where exame.codigo=" + codigo;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ExameBean exameBean = new ExameBean();
            exameBean.setCodigo(rs.getInt(1));
            exameBean.setCodPaciente(rs.getInt(2));
            exameBean.setNomePaciente(rs.getString(3));
            exameBean.setCodMedico(rs.getInt(4));
            exameBean.setNomeMedico(rs.getString(5));
            exameBean.setCodTipoExame(rs.getInt(6));
            exameBean.setNomeTipoExame(rs.getString(7));
            exameBean.setDescricaoTipoExame(rs.getString(8));
            exameBean.setDtRequisicao(rs.getDate(9));
            exameBean.setDtLaudo(rs.getDate(10));
            exameBean.setLaudo(rs.getString(11));
            exameBean.setMaiorNumeroArquivo(rs.getInt(12));
            exameBean.setTentativas(rs.getInt(13));
            lista.add(exameBean);
        }
        rs.close();
        st.close();
        return lista;
    }
    
    public List<ExameBean> getExamePaciente(int codPaciente) throws SQLException {
        List<ExameBean> lista = new ArrayList<ExameBean>();
        String sql = "SELECT "
                + "exame.codigo, "
                + "paciente.codigo, "
                + "paciente.nome, "
                + "funcionario.codigo, "
                + "funcionario.nome, "
                + "tipoExame.codigo, "
                + "tipoExame.nome, "
                + "tipoExame.descricao, "
                + "exame.dtRequisicao, "
                + "exame.dtLaudo, "
                + "exame.laudo, "
                + "exame.maiorNumeroArquivo, "
                + "exame.tentativas "
                + "from paciente inner join exame on "
                + "paciente.codigo=exame.codpaciente inner join funcionario on "
                + "funcionario.codigo=exame.codMedico inner join tipoexame on "
                + "tipoexame.codigo=exame.codtipoexame where paciente.codigo=" + codPaciente;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ExameBean exameBean = new ExameBean();
            exameBean.setCodigo(rs.getInt(1));
            exameBean.setCodPaciente(rs.getInt(2));
            exameBean.setNomePaciente(rs.getString(3));
            exameBean.setCodMedico(rs.getInt(4));
            exameBean.setNomeMedico(rs.getString(5));
            exameBean.setCodTipoExame(rs.getInt(6));
            exameBean.setNomeTipoExame(rs.getString(7));
            exameBean.setDescricaoTipoExame(rs.getString(8));
            exameBean.setDtRequisicao(rs.getDate(9));
            exameBean.setDtLaudo(rs.getDate(10));
            exameBean.setLaudo(rs.getString(11));
            exameBean.setMaiorNumeroArquivo(rs.getInt(12));
            exameBean.setTentativas(rs.getInt(13));
            lista.add(exameBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ExameBean> getExame(String nome) throws SQLException {
        List<ExameBean> lista = new ArrayList<ExameBean>();
        String sql = "SELECT "
                + "exame.codigo, "
                + "initcap(paciente.nome), "
                + "initcap(tipoExame.nome), "
                + "tipoExame.descricao, "
                + "exame.dtRequisicao, "
                + "exame.dtLaudo, "
                + "exame.laudo "
                + "from paciente inner join exame on "
                + "paciente.codigo=exame.codpaciente inner join tipoexame on "
                + "tipoexame.codigo=exame.codtipoexame where paciente.nome like lower('" + nome + "%') order by paciente.nome";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ExameBean exameBean = new ExameBean();
            exameBean.setCodigo(rs.getInt(1));
            exameBean.setNomePaciente(rs.getString(2));
            exameBean.setNomeTipoExame(rs.getString(3));
            exameBean.setDescricaoTipoExame(rs.getString(4));
            exameBean.setDtRequisicao(rs.getDate(5));
            exameBean.setDtLaudo(rs.getDate(6));
            exameBean.setLaudo(rs.getString(7));
            lista.add(exameBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public void deletaExame(int codigo) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "delete from exame where codigo=" + codigo + "";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados do exame, com o código " + codigo + " foram excluidos com sucesso.", "Excluir", JOptionPane.WARNING_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
