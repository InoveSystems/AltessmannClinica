/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.DAO;

import Logs.LogsExceptions;
import com.altessmann.Bean.ConsultaBean;
import com.altessmann.Bean.ConvenioBean;
import java.util.Date;
import com.altessmann.ConnecionFactory.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import com.altessmann.Bean.PacienteBean;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.postgresql.util.PSQLException;

/**
 *
 * @author Guilherme
 */
public class PacienteDAO {

    Connection conexao;
    Logger logs;
    LogsExceptions log = new LogsExceptions();
    private int numeroLinhas;

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public PacienteDAO() {
        try {
            this.conexao = ConnectionFactory.openConnection();
        } catch (PSQLException ex) {
            log.ExceptionsTratamento(14);
        } catch (Exception ex) {
            log.ExceptionsTratamento(14);
        }
    }

    public int setNovoCodigo() throws SQLException {
        Statement st = conexao.createStatement();
        String sqlDLL = "select nextval('seq1paciente')";
        ResultSet rs = st.executeQuery(sqlDLL);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);

        }
        st.close();
        rs.close();
        return id;
    }

    public int decrementaValor(int valor) {
        try {
            Statement st = conexao.createStatement();
            String sqlDLL = "select setval('seq1paciente'," + valor + ")";
            ResultSet rs = st.executeQuery(sqlDLL);
            int id = 0;
            while (rs.next()) {
                id = rs.getInt(1);
            }
            st.close();
            rs.close();
            return id;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados!\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            return 0;
        }

    }

    public int getUltimoCodigo() throws SQLException {
        Statement st = conexao.createStatement();
        String sqlDLL = "select last_value FROM seq1paciente ";
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
        String sqlDLL = "select tentativas from paciente where codigo=" + cod + " ";
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

    public List<PacienteBean> getPaciente(int codigo) throws SQLException {
        java.util.List<PacienteBean> lista = new ArrayList<PacienteBean>();
        String sql = "SELECT "
                + "paciente.codigo,"
                + "initcap(paciente.nome),"
                + "paciente.email,"
                + "paciente.ultimaConsulta,"
                + "paciente.dtNascimento,"
                + "paciente.dtCadastro,"
                + "paciente.dtAtualizacao,"
                + "paciente.telefone,"
                + "paciente.RG,"
                + "paciente.CPF,"
                + "paciente.sexo,"
                + "paciente.endereco,"
                + "paciente.numero,"
                + "paciente.complemento,"
                + "paciente.CEP,"
                + "paciente.bairro,"
                + "paciente.cidade,"
                + "paciente.UF,"
                + "paciente.obs,"
                + "paciente.foto,"
                + "paciente.status,"
                + "paciente.tentativas "
                + "from paciente where paciente.codigo=" + codigo;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            PacienteBean pacienteBean = new PacienteBean();
            pacienteBean.setCodigo(rs.getInt(1));
            pacienteBean.setNome(rs.getString(2));
            pacienteBean.setEmail(rs.getString(3));
            pacienteBean.setUltimaConsulta(rs.getDate(4));
            pacienteBean.setDtNascimento(rs.getDate(5));
            pacienteBean.setDtCadastro(rs.getDate(6));
            pacienteBean.setDtAtualizacao(rs.getDate(7));
            pacienteBean.setTelefone(rs.getString(8));
            pacienteBean.setRg(rs.getString(9));
            pacienteBean.setCpf(rs.getString(10));
            pacienteBean.setSexo(rs.getString(11));
            pacienteBean.setEndereco(rs.getString(12));
            pacienteBean.setNumero(rs.getString(13));
            pacienteBean.setComplemento(rs.getString(14));
            pacienteBean.setCep(rs.getString(15));
            pacienteBean.setBairro(rs.getString(16));
            pacienteBean.setCidade(rs.getString(17));
            pacienteBean.setUf(rs.getString(18));
            pacienteBean.setObs(rs.getString(19));
            pacienteBean.setFoto(rs.getBytes(20));
            pacienteBean.setAtivo(rs.getString(21));
            pacienteBean.setTentativas(rs.getInt(22));
            lista.add(pacienteBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public String retriveCPF(String CPF) {
        String cpf = "";
        Statement stm;
        try {
            stm = this.conexao.createStatement();

            ResultSet rs;
            String sql = "SELECT cpf FROM paciente WHERE cpf='" + CPF.toUpperCase() + "'";
            rs = stm.executeQuery(sql);

            while (rs.next()) {               
                cpf = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cpf;
    }

    public List<PacienteBean> getConvenioPaciente(int codPaciente) throws SQLException {
        java.util.List<PacienteBean> lista = new ArrayList<PacienteBean>();
        String sql = "select codConvenio,porcentagem from paciente_convenio where codPaciente=" + codPaciente;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            PacienteBean pacienteBean = new PacienteBean();
            pacienteBean.setCodConvenio(rs.getInt(1));
            pacienteBean.setConvenioPorcentagem(rs.getDouble(2));
            lista.add(pacienteBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ConsultaBean> getConsulta(int codConsulta, int codPaciente) throws SQLException {
        java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
        String sql = "select consulta.codigo,"
                + "initcap(paciente.nome),"
                + "initcap(funcionario.nome),"
                + "consulta.dataconsulta,"
                + "consulta.horario,"
                + "consulta.sigla, "
                + "consulta.tipo, "
                + "consulta.numeroFicha,"
                + "confconsulta.nome,"
                + "consulta.status "
                + "from consulta inner join paciente on paciente.codigo=consulta.codpaciente "
                + "inner join funcionario on consulta.codmedico=funcionario.codigo "
                + "inner join confconsulta on consulta.codTipoConsulta=confConsulta.codigo "
                + "where consulta.codigo=" + codConsulta + "and paciente.codigo=" + codPaciente;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ConsultaBean consultaBean = new ConsultaBean();
            consultaBean.setCodigo(rs.getInt(1));
            consultaBean.setPacienteNome(rs.getString(2));
            consultaBean.setFuncionarioNome(rs.getString(3));
            consultaBean.setDtConsulta(rs.getDate(4));
            consultaBean.setHorario(rs.getTime(5));
            consultaBean.setSigla(rs.getString(6));
            consultaBean.setTipo(rs.getInt(7));
            consultaBean.setNumeroFicha(rs.getInt(8));
            consultaBean.setTipoConsultaNome(rs.getString(9));
            consultaBean.setStatus(rs.getString(10));
            lista.add(consultaBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ConsultaBean> getConsulta(int codPaciente) throws SQLException {
        java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
        String sql = "select consulta.codigo,"
                + "initcap(paciente.nome),"
                + "initcap(funcionario.nome),"
                + "consulta.dataconsulta,"
                + "consulta.horario, "
                + "consulta.sigla, "
                + "consulta.tipo, "
                + "consulta.numeroFicha,"
                + "confconsulta.nome,"
                + "consulta.status "
                + "from consulta inner join paciente on paciente.codigo=consulta.codpaciente "
                + "inner join funcionario on consulta.codmedico=funcionario.codigo "
                + "inner join confconsulta on consulta.codTipoConsulta=confConsulta.codigo "
                + "where paciente.codigo=" + codPaciente;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ConsultaBean consultaBean = new ConsultaBean();
            consultaBean.setCodigo(rs.getInt(1));
            consultaBean.setPacienteNome(rs.getString(2));
            consultaBean.setFuncionarioNome(rs.getString(3));
            consultaBean.setDtConsulta(rs.getDate(4));
            consultaBean.setHorario(rs.getTime(5));
            consultaBean.setSigla(rs.getString(6));
            consultaBean.setTipo(rs.getInt(7));
            consultaBean.setNumeroFicha(rs.getInt(8));
            consultaBean.setTipoConsultaNome(rs.getString(9));
            consultaBean.setStatus(rs.getString(10));
            lista.add(consultaBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public boolean reqExames(int cod) {
        boolean status = false;
        try {
            String sql = "select consulta_exame.codExame from consulta_exame inner join consulta on consulta.codigo=consulta_exame.codConsulta where consulta.codigo=" + cod;
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (rs.getInt(1) != 0) {
                    status = true;
                    break;
                }
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public List<ConvenioBean> getConvenio() throws SQLException {
        java.util.List<ConvenioBean> lista = new ArrayList<ConvenioBean>();
        String sql = "select codigo,nome,porcentagem from convenio order by nome";
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

    public void setPaciente(int codigo, String nome, String email, boolean ativo, Date dtNascimento, String telefone, String rg, String cpf, String sexo, String endereco, String numero, String complemento, String cep, String bairro, String cidade, String uf, String obs, byte[] foto, String status, int tentativas) {
        java.sql.Date dataSql = new java.sql.Date(dtNascimento.getTime());
        java.sql.Date dataCadastro = new java.sql.Date(new GregorianCalendar().getTime().getTime());
        try {
            InputStream fis = new ByteArrayInputStream(foto);
            PreparedStatement ps = conexao.prepareStatement("insert into paciente ("
                    + "codigo,"
                    + "nome,"
                    + "email,"
                    + "dtNascimento,"
                    + "dtCadastro,"
                    + "telefone,"
                    + "RG,"
                    + "CPF,"
                    + "sexo,"
                    + "endereco,"
                    + "numero,"
                    + "complemento,"
                    + "CEP,"
                    + "bairro,"
                    + "cidade,"
                    + "UF,"
                    + "obs,"
                    + "foto,"
                    + "status,"
                    + "tentativas)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, codigo);
            ps.setString(2, nome.toLowerCase());
            ps.setString(3, email);
            ps.setDate(4, dataSql);
            ps.setDate(5, dataCadastro);
            ps.setString(6, telefone);
            ps.setString(7, rg);
            ps.setString(8, cpf);
            ps.setString(9, sexo);
            ps.setString(10, endereco);
            ps.setString(11, numero);
            ps.setString(12, complemento);
            ps.setString(13, cep);
            ps.setString(14, bairro);
            ps.setString(15, cidade);
            ps.setString(16, uf);
            ps.setString(17, obs);
            ps.setBinaryStream(18, fis, foto.length);
            ps.setString(19, status);
            ps.setInt(20, tentativas);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!", "Dados inseridos", JOptionPane.INFORMATION_MESSAGE);
            ps.close();
            fis.close();

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updatePaciente(int codigo, String nome, String email, boolean ativo, Date dtNascimento, String telefone, String rg, String cpf, String sexo, String endereco, String numero, String complemento, String cep, String bairro, String cidade, String uf, String obs, byte[] foto, String status, int tentativas) {
        java.sql.Date dataSql = new java.sql.Date(dtNascimento.getTime());
        java.sql.Date dataCadastro = new java.sql.Date(new GregorianCalendar().getTime().getTime());
        try {
            InputStream fis = new ByteArrayInputStream(foto);
            PreparedStatement ps = conexao.prepareStatement("update paciente set "
                    + "nome=?,"
                    + "email=?,"
                    + "dtNascimento=?,"
                    + "dtCadastro=?,"
                    + "telefone=?,"
                    + "RG=?,"
                    + "CPF=?,"
                    + "sexo=?,"
                    + "endereco=?,"
                    + "numero=?,"
                    + "complemento=?,"
                    + "CEP=?,"
                    + "bairro=?,"
                    + "cidade=?,"
                    + "UF=?,"
                    + "obs=?,"
                    + "foto=?,"
                    + "status=?,"
                    + "tentativas=?"
                    + " where codigo=?");
            ps.setString(1, nome.toLowerCase());
            ps.setString(2, email);
            ps.setDate(3, dataSql);
            ps.setDate(4, dataCadastro);
            ps.setString(5, telefone);
            ps.setString(6, rg);
            ps.setString(7, cpf);
            ps.setString(8, sexo);
            ps.setString(9, endereco);
            ps.setString(10, numero);
            ps.setString(11, complemento);
            ps.setString(12, cep);
            ps.setString(13, bairro);
            ps.setString(14, cidade);
            ps.setString(15, uf);
            ps.setString(16, obs);
            ps.setBinaryStream(17, fis, foto.length);
            ps.setString(18, status);
            ps.setInt(19, tentativas);
            ps.setInt(20, codigo);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!", "Dados inseridos", JOptionPane.INFORMATION_MESSAGE);
            ps.close();
            fis.close();

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setPaciente_Convenio(int codPaciente, int codConvenio, double porcentagem) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "insert into paciente_convenio (codpaciente,codconvenio,porcentagem) values(" + codPaciente + "," + codConvenio + "," + porcentagem + ");";
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizaPaciente_Convenio(int codPaciente, int codConvenio, double porcentagem) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update paciente_convenio set codConvenio=" + codConvenio + ",porcentagem=" + porcentagem + " where codPaciente=" + codPaciente;
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<PacienteBean> getPaciente(String nome) throws SQLException {
        java.util.List<PacienteBean> lista = new ArrayList<PacienteBean>();
        String sql = "select codigo,initcap(nome),telefone,status,ultimaConsulta from paciente where nome like lower('" + nome + "%') order by nome";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            PacienteBean pacienteBean = new PacienteBean();
            pacienteBean.setCodigo(rs.getInt(1));
            pacienteBean.setNome(rs.getString(2));
            pacienteBean.setTelefone(rs.getString(3));
            pacienteBean.setAtivo(rs.getString(4));
            pacienteBean.setUltimaConsulta(rs.getDate(5));
            lista.add(pacienteBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public void deletaPaciente_Convenio(int codigo) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "delete from paciente_convenio where codpaciente=" + codigo;
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletaPaciente(int codigo) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "delete from paciente where codigo=" + codigo + "";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados do Paciente, com o código " + codigo + " foram excluidos com sucesso.", "Excluir", JOptionPane.WARNING_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*
    public void setFoto(byte[] data) throws SQLException, IOException {
        InputStream fis = new ByteArrayInputStream(data);
        System.out.println("dsssdsssssssssssssssssssssssssssssssssss");
        PreparedStatement ps = conexao.prepareStatement("INSERT INTO images VALUES (?, ?)");
        ps.setString(1, Arrays.toString(data));
        ps.setBinaryStream(2, fis, data.length);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "inserido", "lwgal", JOptionPane.INFORMATION_MESSAGE);
        ps.close();
        fis.close();
    }

    public byte[] getFotoStatement() {
        System.out.println("dfsdfsdfsdfsd");
        byte[] imgBytes = null;
        try {
            String sql = "select img from images";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    imgBytes = rs.getBytes(1);
                    // use the stream in some way here

                }
                st.close();
                rs.close();
            } else {
                System.out.println("nao tem porra nenhuma");
            }
            return imgBytes;
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imgBytes;
    }
     */
    public String getBanco() {
        String x = null;
        try {
            String sql = "SELECT current_database()";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    x = rs.getString(1);
                    // use the stream in some way here

                }
                st.close();
                rs.close();
            } else {
                System.out.println("nao tem porra nenhuma");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }
}
