/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.DAO;

import com.altessmann.Bean.AtendimentoBean;
import com.altessmann.Bean.ConfConsultaBean;
import com.altessmann.Bean.ConsultaBean;
import com.altessmann.Bean.ConvenioBean;
import com.altessmann.Bean.FuncionarioBean;
import com.altessmann.Bean.PacienteBean;
import com.altessmann.Bean.TipoExameBean;
import com.altessmann.ConnecionFactory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
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
public class ConsultaDAO {

    Connection conexao;
    Logger logs;
    private int numeroLinhas;
    private double porcentagem;
    private boolean atualizar;// se estiver em 1 atualiza a tabela principal consultaMain

    public boolean isAtualizar() {
        return atualizar;
    }

    public void setAtualizar(boolean atualizar) {
        this.atualizar = atualizar;
    }

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }

    public ConsultaDAO() throws SQLException {
       // try {
            this.conexao = ConnectionFactory.openConnection();
      //  } catch (Exception ex) {
          //  Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
      //  }
    }

    public int getUltimoCodigo() throws SQLException {
        int id = 0;
        Statement st = conexao.createStatement();
        String sqlDLL = "select last_value from seq1consulta";
        ResultSet rs = st.executeQuery(sqlDLL);
        try {
            while (rs.next()) {
                id = rs.getInt(1);
            }
            st.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(com.altessmann.DAO.PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!", "Erro", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

        }

        return id;
    }

    /*
     public int ContaCod(int cod) throws SQLException {
     Statement st = conexao.createStatement();
     String sqlDLL = "select tentativas from consulta where codigo=" + cod + " ";
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

     public List<String> getHorario(Date dtConsulta) throws SQLException {
     List<String> lista = new ArrayList<String>();
     String[] array = new String[18];
     array[0] = "09:00:00";
     array[1] = "09:30:00";
     array[2] = "10:00:00";
     array[3] = "10:30:00";
     array[4] = "11:00:00";
     array[5] = "11:30:00";
     array[6] = "13:00:00";
     array[7] = "13:30:00";
     array[8] = "14:00:00";
     array[9] = "14:30:00";
     array[10] = "15:00:00";
     array[11] = "15:30:00";
     array[12] = "16:00:00";
     array[13] = "16:30:00";
     array[14] = "17:00:00";
     array[15] = "17:30:00";
     array[16] = "18:00:00";
     array[17] = "18:30:00";

     Statement st = conexao.createStatement();
     String sqlDLL = "select horario from consulta where dtconsulta='" + dtConsulta + "'";
     ResultSet rs = st.executeQuery(sqlDLL);
     numeroLinhas = 0;

     while (rs.next()) {
     Time count;
     count = rs.getTime(1);
     String stringHora = count + "";

     for (int i = 0; i <= 17; i++) {

     if (array[i].equals(stringHora)) {

     array[i] = "indisponivel";

     }
     }
     }
     for (int i = 0; i <= 17; i++) {
     if (!array[i].equals("indisponivel")) {
     lista.add(array[i] + "\n");
     numeroLinhas++;
     }

     }
     st.close();
     rs.close();
     return lista;

     }
     */
    public List<ConfConsultaBean> getConfConsulta() throws SQLException {
        List<ConfConsultaBean> lista = new ArrayList<ConfConsultaBean>();
        String sql = "select codigo,nome from confconsulta order by codigo";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ConfConsultaBean confConsultaBean = new ConfConsultaBean();
            confConsultaBean.setCodigo(rs.getInt(1));
            confConsultaBean.setNome(rs.getString(2));
            lista.add(confConsultaBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public double getValorConfConsulta(int cod) throws SQLException {
        String sql = "select valor from confconsulta where codigo=" + cod + "";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        double valor = 0;
        while (rs.next()) {
            valor = rs.getDouble(1);
        }
        rs.close();
        st.close();
        return valor;
    }

    public void setConsulta(int codPaciente, int codTipoConsulta, Time horario, double valorConsulta, Date dtConsulta, int tentativas) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "insert into consulta (codPaciente,codTipoConsulta,horario,valorConsulta,dtConsulta,dtCriacao,finalizada,tentativas) values(" + codPaciente + "," + codTipoConsulta + ",'" + horario + "'," + valorConsulta + ",'" + dtConsulta + "',now(),false," + tentativas + " )";
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizaConsulta(int codigo, int codPaciente, int codTipoConsulta, Time horario, double valorConsulta, Date dtConsulta, int tentativas) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update consulta set codPaciente=" + codPaciente + ",codTipoConsulta=" + codTipoConsulta + ",horario= '" + horario + "',valorConsulta= " + valorConsulta + ",dtConsulta= '" + dtConsulta + "', dtAtualizacao=now(),tentativas=" + tentativas + " where codigo=" + codigo + "";
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<PacienteBean> getPaciente(String nome) throws SQLException {
        List<PacienteBean> lista = new ArrayList<PacienteBean>();
        String sql = "select codigo,initcap(nome),status,telefone,ultimaconsulta from paciente where nome like lower('" + nome + "%') order by nome";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            PacienteBean pacienteBean = new PacienteBean();
            pacienteBean.setCodigo(rs.getInt(1));
            pacienteBean.setNome(rs.getString(2));
            pacienteBean.setAtivo(rs.getString(3));
            pacienteBean.setTelefone(rs.getString(4));
            pacienteBean.setUltimaConsulta(rs.getDate(5));
            lista.add(pacienteBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<FuncionarioBean> getMedico() throws SQLException {
        List<FuncionarioBean> lista = new ArrayList<FuncionarioBean>();
        String sql = "select codigo,initcap(nome) from funcionario where funcao = 1";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            FuncionarioBean funcionarioBean = new FuncionarioBean();
            funcionarioBean.setCodigo(rs.getInt(1));
            funcionarioBean.setNome(rs.getString(2));
            lista.add(funcionarioBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ConfConsultaBean> getTipoConsulta() throws SQLException {
        List<ConfConsultaBean> lista = new ArrayList<ConfConsultaBean>();
        String sql = "select codigo,nome,valor from ConfConsulta";
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

    public List<PacienteBean> getConvenio(int codigo) throws SQLException {
        List<PacienteBean> lista = new ArrayList<PacienteBean>();
        String sql = "select convenio.codigo,convenio.nome,paciente_convenio.porcentagem from paciente_convenio inner join convenio on paciente_convenio.codconvenio=convenio.codigo where paciente_convenio.codpaciente=" + codigo;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            PacienteBean pacienteBean = new PacienteBean();
            pacienteBean.setCodConvenio(rs.getInt(1));
            pacienteBean.setNomeConvenio(rs.getString(2));
            pacienteBean.setConvenioPorcentagem(rs.getDouble(3));
            lista.add(pacienteBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;

    }

    /*public double getPorcConvenio(int codConvenio, int codPaciente) throws SQLException {
     double porcentagem = 0;
     String sql = "select porcentagem from paciente_convenio where (codpaciente=" + codPaciente + ") and (codconvenio=" + codConvenio + ")";
     Statement st = this.conexao.createStatement();
     ResultSet rs = st.executeQuery(sql);
     while (rs.next()) {
     porcentagem = rs.getDouble(1);
     }
     rs.close();
     st.close();
     return porcentagem;
     }*/
    public double getPorcConvenio(int codConvenio) throws SQLException {
        double porcentagem = 0;
        String sql = "select porcentagem from convenio where codigo=" + codConvenio;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            porcentagem = rs.getDouble(1);
        }
        rs.close();
        st.close();
        return porcentagem;
    }

    public double getValorConsulta(int codTipoConsulta) throws SQLException {
        double valor = 0;
        String sql = "select valor from confconsulta where codigo=" + codTipoConsulta;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            valor = rs.getDouble(1);
        }
        rs.close();
        st.close();
        return valor;
    }

    //public List<ConvenioBean> 
    public List<PacienteBean> getPaciente(int codigo) throws SQLException {
        List<PacienteBean> lista = new ArrayList<PacienteBean>();
        //String sql = "select paciente.codigo,initcap(paciente.nome),paciente.ativo,paciente.caminhofoto,convenio.porcentagem from paciente inner join convenio on paciente.codConvenio=convenio.codigo where paciente.codigo=" + codigo + "";
        String sql = "SELECT codigo, initcap(nome),foto,sexo,telefone,email,cpf,rg,ultimaConsulta from paciente where codigo=" + codigo;
        /*+ "paciente.codigo,"
         + "initcap(paciente.nome),"
         + "paciente.ativo,"
         + "paciente.caminhofoto,"
         + "paciente.sexo,"
         + "paciente.telefone,"
         + "paciente.email,"
         + "convenio.codigo,"
         + "convenio.nome,"
         + "paciente_convenio.porcentagem "
         + "from paciente_convenio inner join paciente on paciente_convenio.codPaciente=paciente.codigo "
         + "inner join convenio on paciente_convenio.codconvenio = convenio.codigo where paciente.codigo=" + codigo;*/
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            PacienteBean pacienteBean = new PacienteBean();
            pacienteBean.setCodigo(rs.getInt(1));
            pacienteBean.setNome(rs.getString(2));
            pacienteBean.setFoto(rs.getBytes(3));
            pacienteBean.setSexo(rs.getString(4));
            pacienteBean.setTelefone(rs.getString(5));
            pacienteBean.setEmail(rs.getString(6));
            pacienteBean.setCpf(rs.getString(7));
            pacienteBean.setRg(rs.getString(8));
            pacienteBean.setUltimaConsulta(rs.getDate(9));
            /*pacienteBean.setCodigo(rs.getInt(1));
             pacienteBean.setNome(rs.getString(2));
             pacienteBean.setAtivo(rs.getBoolean(3));
             pacienteBean.setCaminhoFoto(rs.getString(4));
             pacienteBean.setSexo(rs.getString(5));
             pacienteBean.setTelefone(rs.getString(6));
             pacienteBean.setEmail(rs.getString(7));
             pacienteBean.setCodConvenio(rs.getInt(8));
             pacienteBean.setNomeConvenio(rs.getString(9));
             pacienteBean.setConvenioPorcentagem(rs.getDouble(10));*/
            lista.add(pacienteBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    /*public List<PacienteBean> getPaciente(int codigo) throws SQLException {
     List<PacienteBean> lista = new ArrayList<PacienteBean>();
     String sql = "select codigo,nome,telefone,email,sexo from paciente where codigo=" + codigo + "";
     Statement st = this.conexao.createStatement();
     ResultSet rs = st.executeQuery(sql);
     numeroLinhas = 0;
     while (rs.next()) {
     PacienteBean pacienteBean = new PacienteBean();
     pacienteBean.setCodigo(rs.getInt(1));
     pacienteBean.setNome(rs.getString(2));
     pacienteBean.setAtivo(rs.getBoolean(3));
     pacienteBean.setCaminhoFoto(rs.getString(4));
     this.setPorcentagem(rs.getDouble(5));
     lista.add(pacienteBean);
     numeroLinhas++;
     }
     rs.close();
     st.close();
     return lista;
     }
     public String[] testaHorario(Date data) throws SQLException {
     int c = 0;
     int u = 0;
     String[] array = new String[18];
     array[0] = "09:00:00";
     array[1] = "09:30:00";
     array[2] = "10:00:00";
     array[3] = "10:30:00";
     array[4] = "11:00:00";
     array[5] = "11:30:00";
     array[6] = "13:00:00";
     array[7] = "13:30:00";
     array[8] = "14:00:00";
     array[9] = "14:30:00";
     array[10] = "15:00:00";
     array[11] = "15:30:00";
     array[12] = "16:00:00";
     array[13] = "16:30:00";
     array[14] = "17:00:00";
     array[15] = "17:30:00";
     array[16] = "18:00:00";
     array[17] = "18:30:00";

     Statement st = conexao.createStatement();
     String sqlDLL = "select horario from consulta where dtconsulta='" + data + "'";
     ResultSet rs = st.executeQuery(sqlDLL);

     while (rs.next()) {
     numeroLinhas++;
     Time count;
     count = rs.getTime(1);
     String stringHora = count + "";

     for (int i = 0; i <= 17; i++) {

     if (array[i].equals(stringHora)) {

     array[i] = "indisponivel";

     }
     }
     }

     String[] x = new String[18];
     for (int i = 0; i <= 17; i++) {
     if (!array[i].equals("indisponivel")) {
     x[u] = array[i];
     u++;
     }

     }
     st.close();
     rs.close();
     return x;
     }

     /*public List<ConsultaBean> getConsulta(int cod) throws SQLException {
     java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
     String sql = "select codPaciente,codTipoConsulta,horario,valorConsulta,dtConsulta,finalizada,tentativas from consulta where codigo=" + cod + "";
     Statement st = this.conexao.createStatement();
     ResultSet rs = st.executeQuery(sql);
     numeroLinhas = 0;
     while (rs.next()) {
     ConsultaBean consultaBean = new ConsultaBean();
     consultaBean.setCodPaciente(rs.getInt(1));
     consultaBean.setCodTipoConsulta(2);
     consultaBean.setHorario(rs.getTime(3));
     consultaBean.setValorConsulta(rs.getDouble(4));
     consultaBean.setDtConsulta(rs.getDate(5));
     consultaBean.setFinalizada(rs.getBoolean(6));
     consultaBean.setTentativas(rs.getInt(7));
     lista.add(consultaBean);
     numeroLinhas++;
     }
     rs.close();
     st.close();
     return lista;
     }*/

 /*public List<ConsultaBean> getConsulta(Date dtConsulta) throws SQLException {
     java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();//(finalizada =false) and 
     String sql = "select consulta.codigo,initcap(paciente.nome),consulta.dtConsulta,consulta.horario,confconsulta.nome from consulta inner join paciente on paciente.codigo=consulta.codpaciente inner join confconsulta on confconsulta.codigo=consulta.codtipoconsulta where (consulta.dtconsulta='" + dtConsulta + "')";
     Statement st = this.conexao.createStatement();
     ResultSet rs = st.executeQuery(sql);
     numeroLinhas = 0;
     while (rs.next()) {
     ConsultaBean consultaBean = new ConsultaBean();
     consultaBean.setCodigo(rs.getInt(1));
     consultaBean.setPacienteNome(rs.getString(2));
     consultaBean.setDtConsulta(rs.getDate(3));
     consultaBean.setHorario(rs.getTime(4));
     consultaBean.setTipoConsultaNome(rs.getString(5));
     lista.add(consultaBean);
     numeroLinhas++;
     }
     rs.close();
     st.close();
     return lista;
     }*/
    public List<ConsultaBean> getConsultaUser(int cod) throws SQLException {
        java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
        String sql = "SELECT "
                + "consulta.codigo, "
                + "consulta.codpaciente, "
                + "paciente.nome, "
                + "paciente.telefone, "
                + "paciente.email, "
                + "consulta.codMedico, "
                + "initcap(funcionario.nome), "
                + "consulta.codTipoConsulta, "
                + "confconsulta.nome, "
                + "consulta.codConvenio, "
                + "consulta.valorDesconto, "
                + "consulta.valorTotal, "
                + "consulta.foiChamado, "
                + "consulta.peso, "
                + "consulta.pressao, "
                + "consulta.altura "
                + "from consulta inner join "
                + "paciente on consulta.codPaciente= paciente.codigo inner join "
                + "confconsulta on consulta.codTipoConsulta= confconsulta.codigo inner join "
                + "funcionario on consulta.codMedico= funcionario.codigo where consulta.codigo=" + cod;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ConsultaBean consultaBean = new ConsultaBean();
            consultaBean.setCodigo(rs.getInt(1));
            consultaBean.setCodPaciente(rs.getInt(2));
            consultaBean.setPacienteNome(rs.getString(3));
            consultaBean.setPacienteTelefone(rs.getString(4));
            consultaBean.setPacienteEmail(rs.getString(5));
            consultaBean.setCodMedico(rs.getInt(6));
            consultaBean.setMedicoNome(rs.getString(7));
            consultaBean.setCodTipoConsulta(rs.getInt(8));
            consultaBean.setTipoConsultaNome(rs.getString(9));
            consultaBean.setCodConvenio(rs.getInt(10));
            consultaBean.setConvenioDesconto(rs.getDouble(11));
            consultaBean.setValorConsulta(rs.getDouble(12));
            consultaBean.setFoiChamado(rs.getBoolean(13));
            consultaBean.setPeso(rs.getDouble(14));
            consultaBean.setPressao(rs.getDouble(15));
            consultaBean.setAltura(rs.getDouble(16));
            lista.add(consultaBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ConsultaBean> getConsultaRoot(int cod) throws SQLException {
        java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
        String sql = "SELECT "
                + "consulta.codigo, "
                + "consulta.codpaciente, "
                + "paciente.nome, "
                + "paciente.telefone, "
                + "paciente.email, "
                + "consulta.codMedico, "
                + "funcionario.nome, "
                + "consulta.codTipoConsulta, "
                + "confconsulta.nome, "
                + "consulta.codConvenio, "
                + "consulta.valorDesconto, "
                + "consulta.valorTotal, "
                + "consulta.foiChamado, "
                + "consulta.peso, "
                + "consulta.pressao, "
                + "consulta.altura,"
                + "consulta.AtestadoTexto,"
                + "consulta.diagnostico,"
                + "consulta.prescricao,"
                + "consulta.atestadoTipo,"
                + "consulta.atestadoDias,"
                + "consulta.tentativas "
                + "from consulta inner join "
                + "paciente on consulta.codPaciente= paciente.codigo inner join "
                + "confconsulta on consulta.codTipoConsulta= confconsulta.codigo inner join "
                + "funcionario on consulta.codMedico= funcionario.codigo where consulta.codigo=" + cod;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ConsultaBean consultaBean = new ConsultaBean();
            consultaBean.setCodigo(rs.getInt(1));
            consultaBean.setCodPaciente(rs.getInt(2));
            consultaBean.setPacienteNome(rs.getString(3));
            consultaBean.setPacienteTelefone(rs.getString(4));
            consultaBean.setPacienteEmail(rs.getString(5));
            consultaBean.setCodMedico(rs.getInt(6));
            consultaBean.setMedicoNome(rs.getString(7));
            consultaBean.setCodTipoConsulta(rs.getInt(8));
            consultaBean.setTipoConsultaNome(rs.getString(9));
            consultaBean.setCodConvenio(rs.getInt(10));
            consultaBean.setConvenioDesconto(rs.getDouble(11));
            consultaBean.setValorConsulta(rs.getDouble(12));
            consultaBean.setFoiChamado(rs.getBoolean(13));
            consultaBean.setPeso(rs.getDouble(14));
            consultaBean.setPressao(rs.getDouble(15));
            consultaBean.setAltura(rs.getDouble(16));
            consultaBean.setAtestadoTexto(rs.getString(17));
            consultaBean.setDiagnostico(rs.getString(18));
            consultaBean.setPrescricao(rs.getString(19));
            consultaBean.setAtestadoTipo(rs.getString(20));
            consultaBean.setAtestadoDias(rs.getString(21));
            consultaBean.setTentativas(rs.getInt(22));
            lista.add(consultaBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public String getNomeConvenio(int cod) throws SQLException {
        String nome = null;
        java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
        String sql = "select nome from convenio where codigo =" + cod;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            nome = rs.getString(1);
        }
        rs.close();
        st.close();
        return nome;
    }

    public String getNomeTipoConsutla(int cod) throws SQLException {
        String nome = null;
        java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
        String sql = "select nome from confconsulta where codigo =" + cod;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            nome = rs.getString(1);
        }
        rs.close();
        st.close();
        return nome;
    }

    public String getSatusConsulta(int cod) throws SQLException {
        String status = "";

        return status;
    }

    public List<ConsultaBean> getConsultaPesquisaUser(int cod) throws SQLException {
        java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
        String sql = "SELECT "
                + "consulta.codigo, "
                + "initcap(paciente.nome), "
                + "initcap(funcionario.nome), "
                + "consulta.dataconsulta, "
                + "confconsulta.nome, "
                + "consulta.numeroficha, "
                + "consulta.tipo, "
                + "consulta.sigla, "
                + "consulta.status, "
                + "consulta.horario "
                + "from consulta inner join "
                + "paciente on consulta.codPaciente= paciente.codigo inner join "
                + "confconsulta on consulta.codTipoConsulta= confconsulta.codigo inner join "
                + "funcionario on consulta.codMedico= funcionario.codigo where consulta.codigo=" + cod;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {

            ConsultaBean consultaBean = new ConsultaBean();
            consultaBean.setCodigo(rs.getInt(1));
            consultaBean.setPacienteNome(rs.getString(2));
            consultaBean.setMedicoNome(rs.getString(3));
            consultaBean.setDtConsulta(rs.getDate(4));
            consultaBean.setTipoConsultaNome(rs.getString(5));
            consultaBean.setNumeroFicha(rs.getInt(6));
            consultaBean.setTipo(rs.getInt(7));
            consultaBean.setSigla(rs.getString(8));
            consultaBean.setStatus(rs.getString(9));
            consultaBean.setHorario(rs.getTime(10));
            lista.add(consultaBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ConsultaBean> getConsultaPesquisaUser(String nome) throws SQLException {
        java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
        String sql = "SELECT "
                + "consulta.codigo, "
                + "initcap(paciente.nome), "
                + "initcap(funcionario.nome), "
                + "consulta.dataconsulta, "
                + "confconsulta.nome, "
                + "consulta.numeroficha, "
                + "consulta.tipo, "
                + "consulta.sigla, "
                + "consulta.status, "
                + "consulta.horario "
                + "from consulta inner join "
                + "paciente on consulta.codPaciente= paciente.codigo inner join "
                + "confconsulta on consulta.codTipoConsulta= confconsulta.codigo inner join "
                + "funcionario on consulta.codMedico= funcionario.codigo where paciente.nome like lower('" + nome + "%') order by paciente.nome";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ConsultaBean consultaBean = new ConsultaBean();
            consultaBean.setCodigo(rs.getInt(1));
            consultaBean.setPacienteNome(rs.getString(2));
            consultaBean.setMedicoNome(rs.getString(3));
            consultaBean.setDtConsulta(rs.getDate(4));
            consultaBean.setTipoConsultaNome(rs.getString(5));
            consultaBean.setNumeroFicha(rs.getInt(6));
            consultaBean.setSigla(rs.getString(8));
            consultaBean.setTipo(rs.getInt(7));
            consultaBean.setStatus(rs.getString(9));
            consultaBean.setHorario(rs.getTime(10));
            lista.add(consultaBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ConsultaBean> getConsultaPesquisaUser(Date data) throws SQLException {
        java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
        String sql = "SELECT "
                + "consulta.codigo, "
                + "initcap(paciente.nome), "
                + "initcap(funcionario.nome), "
                + "consulta.dataconsulta, "
                + "confconsulta.nome, "
                + "consulta.numeroficha, "
                + "consulta.tipo, "
                + "consulta.sigla, "
                + "consulta.status, "
                + "consulta.horario "
                + "from consulta inner join "
                + "paciente on consulta.codPaciente= paciente.codigo inner join "
                + "confconsulta on consulta.codTipoConsulta= confconsulta.codigo inner join "
                + "funcionario on consulta.codMedico= funcionario.codigo where consulta.dataconsulta='" + data + "' order by consulta.codigo desc";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ConsultaBean consultaBean = new ConsultaBean();
            consultaBean.setCodigo(rs.getInt(1));
            consultaBean.setPacienteNome(rs.getString(2));
            consultaBean.setMedicoNome(rs.getString(3));
            consultaBean.setDtConsulta(rs.getDate(4));
            consultaBean.setTipoConsultaNome(rs.getString(5));
            consultaBean.setNumeroFicha(rs.getInt(6));
            consultaBean.setSigla(rs.getString(8));
            consultaBean.setTipo(rs.getInt(7));
            consultaBean.setStatus(rs.getString(9));
            consultaBean.setHorario(rs.getTime(10));
            lista.add(consultaBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<ConsultaBean> getConsultaFinalizada(Date dtConsulta) throws SQLException {
        java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
        String sql = "select consulta.codigo,initcap(paciente.nome),consulta.dtConsulta,consulta.horario,confconsulta.nome from consulta inner join paciente on paciente.codigo=consulta.codpaciente inner join confconsulta on confconsulta.codigo=consulta.codtipoconsulta where consulta.dtconsulta='" + dtConsulta + "'";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ConsultaBean consultaBean = new ConsultaBean();
            consultaBean.setCodigo(rs.getInt(1));
            consultaBean.setPacienteNome(rs.getString(2));
            consultaBean.setDtConsulta(rs.getDate(3));
            consultaBean.setHorario(rs.getTime(4));
            consultaBean.setTipoConsultaNome(rs.getString(5));
            lista.add(consultaBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    /* public List<ConsultaBean> getConsulta(String nome) throws SQLException {
     java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();//(finalizada =false) and 
     String sql = "select consulta.codigo,initcap(paciente.nome),consulta.dtConsulta,consulta.horario,confconsulta.nome from consulta inner join paciente on paciente.codigo=consulta.codpaciente inner join confconsulta on confconsulta.codigo=consulta.codtipoconsulta where paciente.nome like lower('" + nome + "%') order by nome";
     Statement st = this.conexao.createStatement();
     ResultSet rs = st.executeQuery(sql);
     numeroLinhas = 0;
     while (rs.next()) {
     ConsultaBean consultaBean = new ConsultaBean();
     consultaBean.setCodigo(rs.getInt(1));
     consultaBean.setPacienteNome(rs.getString(2));
     consultaBean.setDtConsulta(rs.getDate(3));
     consultaBean.setHorario(rs.getTime(4));
     consultaBean.setTipoConsultaNome(rs.getString(5));
     lista.add(consultaBean);
     numeroLinhas++;
     }
     rs.close();
     st.close();
     return lista;
     }
     */
    public List<ConsultaBean> getConsultaCod(int cod) throws SQLException {
        java.util.List<ConsultaBean> lista = new ArrayList<ConsultaBean>();//(finalizada =false) and 
        String sql = "select consulta.codigo,initcap(paciente.nome),consulta.dtConsulta,consulta.horario,confconsulta.nome from consulta inner join paciente on paciente.codigo=consulta.codpaciente inner join confconsulta on confconsulta.codigo=consulta.codtipoconsulta where consulta.codigo = " + cod;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            ConsultaBean consultaBean = new ConsultaBean();
            consultaBean.setCodigo(rs.getInt(1));
            consultaBean.setPacienteNome(rs.getString(2));
            consultaBean.setDtConsulta(rs.getDate(3));
            consultaBean.setHorario(rs.getTime(4));
            consultaBean.setTipoConsultaNome(rs.getString(5));
            lista.add(consultaBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    /*
     public String getCaminhoFoto(int codConsulta) throws SQLException {
     String x = null;
     Statement st = this.conexao.createStatement();
     ResultSet rs = st.executeQuery("select paciente.caminhoFoto from paciente inner join consulta on paciente.codigo=consulta.codpaciente where consulta.codigo=" + codConsulta);
     numeroLinhas = 0;
     while (rs.next()) {
     x = rs.getString(1);
     }
     rs.close();
     st.close();
     return x;

     }

     public String getNomePaciente(int cod) throws SQLException {
     Statement st = this.conexao.createStatement();
     ResultSet rs = st.executeQuery("select nome from paciente where codigo=" + cod);
     numeroLinhas = 0;
     String x = rs.getString(1);
     rs.close();
     st.close();
     return x;
     }

     public String getNomeConfConsulta(int cod) throws SQLException {
     Statement st = this.conexao.createStatement();
     ResultSet rs = st.executeQuery("select nome from confconsulta where codigo=" + cod);
     String x = rs.getString(1);
     rs.close();
     st.close();
     return x;
     }

     public String getHorarioConsultaMain(Date data) throws SQLException {
     List<ConsultaBean> lista = new ArrayList<ConsultaBean>();
     String sql = "select consulta.codigo,initcap(paciente.nome),consulta.horario from consulta inner join paciente on paciente.codigo=consulta.codpaciente where dtconsulta='" + data + "' order by horario";
     Statement st = this.conexao.createStatement();
     ResultSet rs = st.executeQuery(sql);
     numeroLinhas = 0;
     Calendar cal = new GregorianCalendar();
     String proximaConsulta = "Não há mais consultas marcadas para hoje!";
     while (rs.next()) {
     if (rs.getTime(3).getHours() > cal.get(Calendar.HOUR_OF_DAY)) {
     proximaConsulta = rs.getString(2) + " - " + rs.getTime(3);
     numeroLinhas++;
     break;
     } else if (rs.getTime(3).getHours() == cal.get(Calendar.HOUR_OF_DAY)) {
     if (rs.getTime(3).getMinutes() > cal.get(Calendar.MINUTE)) {
     proximaConsulta = rs.getString(2) + " - " + rs.getTime(3);
     numeroLinhas++;
     break;

     }
     }
     }
     rs.close();
     st.close();
     return proximaConsulta;
     }
     */
    public void deletaConsulta(int codigo) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "delete from consulta where codigo=" + codigo + "";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados da consulta, com o código " + codigo + " foram excluidos com sucesso.", "Excluir", JOptionPane.WARNING_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*
     public void setDiagnostico(int codConsulta, String texto) throws SQLException {
     try {
     Statement st = conexao.createStatement();
     String sqlDDL = "update consulta set diagnostico='" + texto + "' where codigo=" + codConsulta;
     int ret = st.executeUpdate(sqlDDL);
     JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!", "Dados gravados", JOptionPane.INFORMATION_MESSAGE);
     st.close();
     } catch (SQLException ex) {
     Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
     }
     }

     public String getDiagnostico(int codConsulta) throws SQLException {
     String texto = null;
     String sql = "select diagnostico from consulta where codigo=" + codConsulta;
     Statement st = this.conexao.createStatement();
     ResultSet rs = st.executeQuery(sql);
     while (rs.next()) {
     texto = rs.getString(1);
     }
     rs.close();
     st.close();
     return texto;
     }

     public void setPrescricao(int codConsulta, String texto) throws SQLException {
     try {
     Statement st = conexao.createStatement();
     String sqlDDL = "update consulta set prescricao='" + texto + "' where codigo=" + codConsulta;
     int ret = st.executeUpdate(sqlDDL);
     JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!", "Dados gravados", JOptionPane.INFORMATION_MESSAGE);
     st.close();
     } catch (SQLException ex) {
     Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
     }
     }

     public String getPrescricao(int codConsulta) throws SQLException {
     String texto = null;
     String sql = "select prescricao from consulta where codigo=" + codConsulta;
     Statement st = this.conexao.createStatement();
     ResultSet rs = st.executeQuery(sql);
     while (rs.next()) {
     texto = rs.getString(1);
     }
     rs.close();
     st.close();
     return texto;
     }*/

 /* public void setFinalConsulta(int codConsulta, String formaPagamento) {
     try {
     Statement st = conexao.createStatement();
     String sqlDDL = "update consulta set formaPagamento='" + formaPagamento + "',finalizada=true where codigo=" + codConsulta;
     int ret = st.executeUpdate(sqlDDL);
     st.close();
     } catch (SQLException ex) {
     Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
     }
     }*/

 /* public void setFinalConsulta(int codConsulta, int parcelas, double valorTotal, Date dtVencimento, int tentativas) {
     try {
     Statement st = conexao.createStatement();
     String sqlDDL = "insert into debitoreceber (codConsulta,quitado,parcelas,dtVencimento,valorTotal,dtCriacao,tentativas) values(" + codConsulta + ",false," + parcelas + ",'" + dtVencimento + "'," + valorTotal + ",now()," + tentativas + ")";
     int ret = st.executeUpdate(sqlDDL);
     st.close();
     } catch (SQLException ex) {
     Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     */
    public void setUltimaConsultaPaciente(int cod) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update paciente set ultimaconsulta=now() where codigo=" + cod;
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //01/05/2017
    public int getUltimaChamada(int codMedico, int tipo) {
        Calendar calendar = new GregorianCalendar();
        int ultimaChamada = 0;
        String data = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        try {
            String sql = "select max(numeroFicha) from consulta where (codMedico=" + codMedico + ") and (tipo=" + tipo + ") and dataConsulta='" + data + "'";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ultimaChamada = rs.getInt(1);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ultimaChamada;
    }

    public void setConsulta(int codPaciente, int codMedico, int codTipoConsulta, int codConvenio, double valorDesconto, double valorConsulta, int numeroFicha, String sigla, int tipo, double peso, double pressao, double altura, String atestadoTexto, String diagnostico, String prescricao, String atestadoTipo, String atestadoDias, Calendar dataLimiteVolta, boolean revisao, int tentativas) {
        Calendar calendar = new GregorianCalendar();
        String date = dataLimiteVolta.get(Calendar.YEAR) + "-" + (dataLimiteVolta.get(Calendar.MONTH) + 1) + "-" + dataLimiteVolta.get(Calendar.DAY_OF_MONTH);
        System.out.println(date);
        DateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        Date data2 = null;
        try {
            data2 = (Date) formatter.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        String hora = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date data = null;
        Time time = null;
        try {
            data = format.parse(hora);
            time = new Time(data.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date dataCadastro = new java.sql.Date(new GregorianCalendar().getTime().getTime());
        PreparedStatement ps;
        try {
            ps = conexao.prepareStatement("insert into consulta ("
                    + "codPaciente,"//1
                    + "codMedico,"//2
                    + "codTipoConsulta,"//3
                    + "codConvenio,"//4
                    + "valorDesconto,"//5
                    + "valorTotal,"//6
                    + "numeroFicha,"//7
                    + "sigla,"//8
                    + "tipo,"//9
                    + "dataConsulta,"//10
                    + "status,"//11
                    + "horaMarcacao,"//12
                    + "foiChamado,"//13
                    + "peso,"//14
                    + "pressao,"//15
                    + "altura,"//16
                    + "atestadoTexto,"
                    + "diagnostico,"
                    + "prescricao,"
                    + "atestadoTipo,"
                    + "atestadoDias,"
                    + "dataLimiteVolta,"
                    + "revisao,"
                    + "tentativas) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, codPaciente);
            ps.setInt(2, codMedico);
            ps.setInt(3, codTipoConsulta);
            ps.setInt(4, codConvenio);
            ps.setDouble(5, valorDesconto);
            ps.setDouble(6, valorConsulta);
            ps.setInt(7, numeroFicha);
            ps.setString(8, sigla);
            ps.setInt(9, tipo);
            ps.setDate(10, dataCadastro);
            ps.setString(11, "Aguardando chamada...");
            ps.setTime(12, time);
            ps.setBoolean(13, false);
            ps.setDouble(14, peso);
            ps.setDouble(15, pressao);
            ps.setDouble(16, altura);
            ps.setString(17, atestadoTexto);
            ps.setString(18, diagnostico);
            ps.setString(19, prescricao);
            ps.setString(20, atestadoTipo);
            ps.setString(21, atestadoDias);
            ps.setDate(22, new java.sql.Date(dataLimiteVolta.getTime().getTime()));
            ps.setBoolean(23, revisao);
            ps.setInt(24, tentativas);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {

            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizaConsultaUser(int codigo, int codPaciente, int codMedico, int codTipoConsulta, int codConvenio, double valorDesconto, double valorTotal, int numeroFicha, String sigla, int tipo, double peso, double pressao, double altura, int tentativas) throws SQLException {
        Calendar calendar = new GregorianCalendar();
        String hora = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date data;
        Time time = null;
        try {
            data = format.parse(hora);
            time = new Time(data.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date dataCadastro = new java.sql.Date(new GregorianCalendar().getTime().getTime());
        PreparedStatement ps;
        ps = conexao.prepareStatement("update consulta set "
                + "codPaciente=?,"
                + "codTipoConsulta=?,"
                + "codConvenio=?,"
                + "codMedico=?,"
                + "valorDesconto=?,"
                + "valorTotal=?,"
                + "foiChamado=?,"
                + "horario=?,"
                + "sigla=?,"
                + "numeroFicha=?,"
                + "tipo=?,"
                + "dataConsulta=?,"
                + "horaMarcacao=?,"
                + "status=?,"
                + "peso=?,"
                + "pressao=?,"
                + "altura=?,"
                + "tentativas=? "
                + "where codigo=?");

        ps.setInt(1, codPaciente);
        ps.setInt(2, codTipoConsulta);
        ps.setInt(3, codConvenio);
        ps.setInt(4, codMedico);
        ps.setDouble(5, valorDesconto);
        ps.setDouble(6, valorTotal);
        ps.setBoolean(7, false);
        ps.setTime(8, null);
        ps.setString(9, sigla);
        ps.setInt(10, numeroFicha);
        ps.setInt(11, tipo);
        ps.setDate(12, dataCadastro);
        ps.setTime(13, time);
        ps.setString(14, "Aguardando chamada...");
        ps.setDouble(15, peso);
        ps.setDouble(16, pressao);
        ps.setDouble(17, altura);
        ps.setInt(18, tentativas);
        ps.setInt(19, codigo);
        ps.executeUpdate();
        ps.close();

    }

    public void atualizaConsultaRoot(int codigo, int codPaciente, int codMedico, int codTipoConsulta, int codConvenio, double valorDesconto, double valorTotal, boolean foiChamado, double peso, double pressao, double altura, String atestadoTexto, String diagnostico, String prescricao, String atestadoTipo, String atestadoDias, int tentativas) throws SQLException {
        Calendar calendar = new GregorianCalendar();
        String hora = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date data;
        Time time = null;
        try {
            data = format.parse(hora);
            time = new Time(data.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date dataCadastro = new java.sql.Date(new GregorianCalendar().getTime().getTime());
        PreparedStatement ps;
        ps = conexao.prepareStatement("update consulta set "
                + "codPaciente=?,"
                + "codTipoConsulta=?,"
                + "codConvenio=?,"
                + "codMedico=?,"
                + "valorDesconto=?,"
                + "valorTotal=?,"
                + "foiChamado=?,"
                + "horario=?,"
                + "dataConsulta=?,"
                + "horaMarcacao=?,"
                + "status=?,"
                + "peso=?,"
                + "pressao=?,"
                + "altura=?,"
                + "atestadoTexto=?,"
                + "diagnostico=?,"
                + "prescricao=?,"
                + "atestadoTipo=?,"
                + "atestadoDias=?,"
                + "tentativas=? "
                + "where codigo=?");

        ps.setInt(1, codPaciente);
        ps.setInt(2, codTipoConsulta);
        ps.setInt(3, codConvenio);
        ps.setInt(4, codMedico);
        ps.setDouble(5, valorDesconto);
        ps.setDouble(6, valorTotal);
        ps.setBoolean(7, foiChamado);
        ps.setTime(8, null);
        ps.setDate(9, dataCadastro);
        ps.setTime(10, time);
        ps.setString(11, "Atendida");
        ps.setDouble(12, peso);
        ps.setDouble(13, pressao);
        ps.setDouble(14, altura);
        ps.setString(15, atestadoTexto);
        ps.setString(16, diagnostico);
        ps.setString(17, prescricao);
        ps.setString(18, atestadoTipo);
        ps.setString(19, atestadoDias);
        ps.setInt(20, tentativas);
        ps.setInt(21, codigo);
        ps.executeUpdate();
        ps.close();

    }

    public void setExameConsulta(int codConsulta, int codExame) throws SQLException {
        PreparedStatement ps;
        ps = conexao.prepareStatement("insert into consulta_exame("
                + "codConsulta,"
                + "codExame) "
                + "values (?,?)");
        ps.setInt(1, codConsulta);
        ps.setInt(2, codExame);
        ps.executeUpdate();
        ps.close();

    }

    public void deletaConsulta_Exame(int codigo) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "delete from consulta_exame where codConsulta=" + codigo;
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<TipoExameBean> getExameConsulta(int codConsulta) throws SQLException {
        List<TipoExameBean> lista = new ArrayList<TipoExameBean>();
        String sql = "select "
                + "tipoExame.codigo,"
                + "tipoExame.nome,"
                + "tipoExame.valor,"
                + "tipoExame.descricao "
                + "from consulta_exame inner join tipoexame on "
                + "tipoExame.codigo=consulta_exame.codExame where consulta_exame.codConsulta=" + codConsulta;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            TipoExameBean tipoExameBean = new TipoExameBean();
            tipoExameBean.setCodigo(rs.getInt(1));
            tipoExameBean.setNome(rs.getString(2));
            tipoExameBean.setValor(rs.getDouble(3));
            tipoExameBean.setDescricao(rs.getString(4));
            lista.add(tipoExameBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public void setStatusFinalizadaConsulta(int codigo) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update consulta set status ='Finalizada!',concluido=true where codigo=" + codigo;
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public boolean haveConsulta(int tipo, int codMedico) {
        boolean status = false;
        Calendar calendar = new GregorianCalendar();
        String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        try {
            String sql = "select codigo from consulta where (dataConsulta='" + date + "') and (codMedico=" + codMedico + ") and (foiChamado=false) and (tipo=" + tipo + ") order by numeroFicha";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (rs.getInt(1) != 0) {
                    status = true;
                }
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public int getCodConsulta(int codMedico) {
        int cod = 0;
        Calendar calendar = new GregorianCalendar();
        String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        try {
            String sql = "select codigo from consulta where (dataConsulta='" + date + "') and (codMedico=" + codMedico + ") and (foiChamado=false) order by numeroFicha";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cod = rs.getInt(1);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cod;
    }

    //editei ela
    public int getCodConsulta(int tipo, int codMedico) {
        int cod = 0;
        Calendar calendar = new GregorianCalendar();
        String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        try {
//            String sql = "select codigo from consulta where (dataConsulta='" + date + "') and (codMedico=" + codMedico + ") and (foiChamado=false) and (tipo=" + tipo + ")and (numeroFicha="+codFicha+") order by numeroFicha ";
            String sql = "select codigo from consulta where (dataConsulta='" + date + "') and (codMedico=" + codMedico + ") and (foiChamado=false) and (tipo=" + tipo + ") order by numeroFicha ";

            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cod = rs.getInt(1);
                if (cod != 0) {
                    break;
                }
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cod;
    }

    public int getCodPaciente(int codConsulta) {
        int cod = 0;
        try {
            String sql = "select codPaciente from consulta where codigo=" + codConsulta;
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cod = rs.getInt(1);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cod;
    }

    public boolean foiChamado(int codConsulta) {
        boolean foi = false;
        try {
            String sql = "select foichamado from consulta where codigo=" + codConsulta;
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                foi = rs.getBoolean(1);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foi;
    }

    public int getUltimaChamadaCombo(int codMedico) {
        int cod = 0;
        Calendar calendar = new GregorianCalendar();
        String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        try {
            String sql = "select codigo, numeroFicha from consulta where (dataConsulta='" + date + "') and (codMedico=" + codMedico + ") and (foiChamado=true) and (concluido=false) order by horario";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cod = rs.getInt(1);
                //System.out.println(rs.getInt(2));
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cod;
    }

    public boolean getTesteRevisao(int codPaciente) {
        boolean teste = false;
        try {
            String sql = "select dataLimiteVolta - current_date, dataConsulta from consulta where codPaciente=" + codPaciente + " and revisao=false order by dataConsulta desc ";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    teste = true;
                }
                break;
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teste;
    }

    public String getConvenioRevisao(int codPaciente) {
        String teste = "";
        try {
            String sql = "select consulta.codConvenio,convenio.nome from consulta inner join convenio on convenio.codigo=consulta.codconvenio where consulta.codPaciente=" + codPaciente + " and consulta.revisao=false order by consulta.dataConsulta desc ";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                teste = rs.getInt(1) + "- " + rs.getString(2);
                break;
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teste;
    }

    public String getMedicoRevisao(int codPaciente) {
        String teste = "";
        try {
            String sql = "select consulta.codMedico,initcap(funcionario.nome) from consulta inner join funcionario on funcionario.codigo=consulta.codMedico where consulta.codPaciente=" + codPaciente + " and consulta.revisao=false order by consulta.dataConsulta desc ";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                teste = rs.getInt(1) + "- " + rs.getString(2);
                break;
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teste;
    }

    public String getTipoConsultaRevisao(int codPaciente) {
        String teste = "";
        try {
            String sql = "select consulta.codTipoConsulta,confConsulta.nome from consulta inner join confconsulta on confconsulta.codigo=consulta.codTipoConsulta where consulta.codPaciente=" + codPaciente + " and consulta.revisao=false order by consulta.dataConsulta desc ";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                teste = rs.getInt(1) + "- " + rs.getString(2);
                break;
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teste;
    }

    public void setConsutlasSemRevisao(int codPaciente) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update consulta set revisao = true where codpaciente=" + codPaciente;
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String callNextEmergencia(int codMedico) {
        String ficha = null;
        int codConsulta = 0;
        int numeroFicha = 0;
        String tipo = null;
        String sigla = null;
        String zeros = "";
        Calendar calendar = new GregorianCalendar();
        String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        try {
            String sql = "select tipo,sigla,numeroFicha,codigo from consulta where (dataConsulta='" + date + "') and (codMedico=" + codMedico + ") and (foiChamado=false) and (tipo=0) order by numeroFicha";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                numeroFicha = rs.getInt(3);
                codConsulta = rs.getInt(4);
                sigla = rs.getString(2);
                if (numeroFicha >= 100) {
                    zeros = "0";
                } else if (numeroFicha >= 10) {
                    zeros = "00";
                } else {
                    zeros = "000";
                }
                switch (rs.getInt(1)) {
                    case 0:
                        tipo = "E";
                        break;
                    case 1:
                        tipo = "P";
                        break;
                    default:
                        tipo = "N";
                        break;
                }
                break;
            }
            rs.close();
            st.close();
            setFoiChamado(codConsulta);
            atualizaStatusEHora(codConsulta);
            if (sigla != null) {
                return sigla + tipo + zeros + numeroFicha;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public String callNextNormal(int codMedico) {
        String ficha = null;
        int numeroFicha = 0;
        int codConsulta = 0;
        String tipo = null;
        String sigla = null;
        String zeros = "";
        Calendar calendar = new GregorianCalendar();
        String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        try {
            String sql = "select tipo,sigla,numeroFicha,codigo from consulta where (dataConsulta='" + date + "') and (codMedico=" + codMedico + ") and (foiChamado=false) and (tipo=2) order by numeroFicha";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                numeroFicha = rs.getInt(3);
                sigla = rs.getString(2);
                codConsulta = rs.getInt(4);
                if (numeroFicha >= 100) {
                    zeros = "0";
                } else if (numeroFicha >= 10) {
                    zeros = "00";
                } else {
                    zeros = "000";
                }
                switch (rs.getInt(1)) {
                    case 0:
                        tipo = "E";
                        break;
                    case 1:
                        tipo = "P";
                        break;
                    default:
                        tipo = "N";
                        break;
                }
                break;
            }
            rs.close();
            st.close();
            setFoiChamado(codConsulta);
            atualizaStatusEHora(codConsulta);
            if (sigla != null) {
                return sigla + tipo + zeros + numeroFicha;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public String callNextPrioritario(int codMedico) {
        String ficha = null;
        int numeroFicha = 0;
        int codConsulta = 0;
        String tipo = null;
        String sigla = null;
        String zeros = "";
        Calendar calendar = new GregorianCalendar();
        String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        try {
            String sql = "select tipo,sigla,numeroFicha,codigo from consulta where (dataConsulta='" + date + "') and (codMedico=" + codMedico + ") and (foiChamado=false) and (tipo=1) order by numeroFicha";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                numeroFicha = rs.getInt(3);
                sigla = rs.getString(2);
                codConsulta = rs.getInt(4);
                if (numeroFicha >= 100) {
                    zeros = "0";
                } else if (numeroFicha >= 10) {
                    zeros = "00";
                } else {
                    zeros = "000";
                }
                switch (rs.getInt(1)) {
                    case 0:
                        tipo = "E";
                        break;
                    case 1:
                        tipo = "P";
                        break;
                    default:
                        tipo = "N";
                        break;
                }
                break;
            }
            rs.close();
            st.close();
            setFoiChamado(codConsulta);
            atualizaStatusEHora(codConsulta);
            if (sigla != null) {
                return sigla + tipo + zeros + numeroFicha;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public void setFoiChamado(int codConsulta) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update consulta set foiChamado = true,concluido=false where codigo=" + codConsulta;
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public String getSalaMedico(int codMedico) {
        String sala = "";
        try {
            String sql = "select sala from funcionario where codigo=" + codMedico;
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                sala = rs.getString(1);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sala;
    }

    public void atualizaStatusEHora(int codConsulta) {
        try {
            Calendar calendar = new GregorianCalendar();
            Statement st = conexao.createStatement();
            String sqlDDL = "update consulta set status ='Em atendimento',horario='" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "' where codigo=" + codConsulta;
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public int getConsultasAtendidas(int codMedico) throws SQLException {
        int numero = 0;
        Calendar calendar = new GregorianCalendar();
        String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        String sql = "select count(codigo) from consulta where dataConsulta='" + date + "' and foiChamado=true and consulta.codMedico=" + codMedico;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            numero = rs.getInt(1);
        }
        rs.close();
        st.close();
        return numero;
    }

    public List<AtendimentoBean> getAtendimento() throws SQLException {
        List<AtendimentoBean> lista = new ArrayList<AtendimentoBean>();
        Calendar calendar = new GregorianCalendar();
        String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        String sql = "select funcionario.codigo,funcionario.nome,count(consulta.foiChamado) from consulta inner join funcionario on consulta.codMedico=funcionario.codigo where consulta.dataConsulta='" + date + "' group by funcionario.nome,funcionario.codigo";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            AtendimentoBean atendimentoBean = new AtendimentoBean();
            atendimentoBean.setCodMedico(rs.getInt(1));
            atendimentoBean.setNomeMedico(rs.getString(2));
            atendimentoBean.setConsultasTotais(rs.getInt(3));
            lista.add(atendimentoBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public boolean getUltimaChamadaCombonew(int codMedico) {
        boolean existe = false;
        Calendar calendar = new GregorianCalendar();
        String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        try {
            String sql = "select codigo, numeroFicha from consulta where (dataConsulta='" + date + "') and (codMedico=" + codMedico + ") and (foiChamado=true) and (concluido=false) order by horario";
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                existe = true;
                break;
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }

    public void atualizaStatusParaTodasInterfaces(String sala, String atualChamada, String ultimaChamada, String penultimaChamada, String antePenultimaChamada,int codConsultaAtual) {
       if(codConsultaAtual==0){
           try {
            Calendar calendar = new GregorianCalendar();
            Statement st = conexao.createStatement();
            String sqlDDL = "update painel set sala='" + sala + "',"
                    + "atualchamada='" + atualChamada + "',"
                    + "ultimachamada='" + ultimaChamada + "',"
                    + "penultimachamada='" + penultimaChamada + "',"
                    + "antepenultimachamada='" + antePenultimaChamada + "',"                    
                    + "dataAtual=now()";
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       }else{
        try {
            Calendar calendar = new GregorianCalendar();
            Statement st = conexao.createStatement();
            String sqlDDL = "update painel set sala='" + sala + "',"
                    + "atualchamada='" + atualChamada + "',"
                    + "ultimachamada='" + ultimaChamada + "',"
                    + "penultimachamada='" + penultimaChamada + "',"
                    + "antepenultimachamada='" + antePenultimaChamada + "',"
                    + "codConsultaAtual= "+codConsultaAtual+","
                    + "dataAtual=now()";
            int ret = st.executeUpdate(sqlDDL);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    }
    
    
    

}
