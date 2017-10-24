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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.postgresql.util.PSQLException;

/**
 *
 * @author Guilherme
 */
public class FuncionarioDAO {

    Connection conexao;
    Logger logs;
    private int numeroLinhas;

    public Connection getConexao() {
        return conexao;
    }

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public FuncionarioDAO() throws Exception {
        this.conexao = ConnectionFactory.openConnection();
    }

    public int getUltimoCodigo() throws SQLException {
        Statement st = conexao.createStatement();
        String sqlDLL = "select max(codigo) from funcionario";
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
        String sqlDLL = "select tentativas from funcionario where codigo=" + cod + " ";
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

    public List<FuncionarioBean> getFuncionario(int codigo) throws SQLException {
        List<FuncionarioBean> lista = new ArrayList<FuncionarioBean>();
        String sql = "select codigo,initcap(nome),funcao,crm,rg,cpf,dtNascimento,email,sexo,endereco,numero,complemento,bairro,cep,cidade,uf,usuario,senha,dtCadastro,dtAtualizacao,sala,telefone,sigla from funcionario where codigo=" + codigo;
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            FuncionarioBean funcionarioBean = new FuncionarioBean();
            funcionarioBean.setCodigo(rs.getInt(1));
            funcionarioBean.setNome(rs.getString(2));
            funcionarioBean.setFuncao(rs.getInt(3));
            funcionarioBean.setCrm(rs.getString(4));
            funcionarioBean.setRg(rs.getString(5));
            funcionarioBean.setCpf(rs.getString(6));
            funcionarioBean.setDtNascimento(rs.getDate(7));
            funcionarioBean.setEmail(rs.getString(8));
            funcionarioBean.setSexo(rs.getString(9));
            funcionarioBean.setEndereco(rs.getString(10));
            funcionarioBean.setNumero(rs.getString(11));
            funcionarioBean.setComplemento(rs.getString(12));
            funcionarioBean.setBairro(rs.getString(13));
            funcionarioBean.setCep(rs.getString(14));
            funcionarioBean.setCidade(rs.getString(15));
            funcionarioBean.setUf(rs.getString(16));
            funcionarioBean.setUsuario(rs.getString(17));
            funcionarioBean.setSenha(rs.getString(18));
            funcionarioBean.setDtCadastro(rs.getDate(19));
            funcionarioBean.setDtAtualizacao(rs.getDate(20));
            funcionarioBean.setSala(rs.getString(21));
            funcionarioBean.setTelefone(rs.getString(22));
            funcionarioBean.setSigla(rs.getString(23));
            lista.add(funcionarioBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public void setFuncionario(String nome, int funcao, String crm, String rg, String cpf, Date dtNascimento, String email, String sexo, String endereco, String numero, String complemento, String bairro, String cep, String cidade, String uf, String usuario, String senha, String sala, String telefone, String sigla, int tentativas) {

        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "insert into funcionario (nome,funcao,crm,rg,cpf,dtNascimento,email,sexo,endereco,numero,complemento,bairro,cep,cidade,uf,usuario,senha,dtCadastro,sala,telefone,sigla,tentativas) values(lower('" + nome + "')," + funcao + ",'" + crm + "','" + rg + "','" + cpf + "','" + dtNascimento + "','" + email + "','" + sexo + "','" + endereco + "','" + numero + "','" + complemento + "','" + bairro + "','" + cep + "','" + cidade + "','" + uf + "','" + usuario + "','" + senha + "',now(),'" + sala + "','" + telefone + "','" + sigla + "'," + tentativas + ")";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!", "Dados gravados", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void atualizaFuncionario(int codigo, String nome, int funcao, String crm, String rg, String cpf, Date dtNascimento, String email, String sexo, String endereco, String numero, String complemento, String bairro, String cep, String cidade, String uf, String usuario, String senha, String sala, String telefone, String sigla, int tentativas) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "update funcionario set nome=lower('" + nome + "'),funcao=" + funcao + ",crm='" + crm + "',rg='" + rg + "',cpf='" + cpf + "',dtNascimento='" + dtNascimento + "',email='" + email + "',sexo='" + sexo + "',endereco='" + endereco + "',numero='" + numero + "',complemento='" + complemento + "',bairro='" + bairro + "',cep='" + cep + "',cidade='" + cidade + "',uf='" + uf + "',dtAtualizacao=now(),sala='" + sala + "',telefone='" + telefone + "',sigla='" + sigla + "',tentativas='" + tentativas + "',usuario='" + usuario + "', senha=" + senha + " where codigo=" + codigo;
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Dados gravados", JOptionPane.INFORMATION_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean existeUsuario(String usuario) throws SQLException {
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery("select usuario from funcionario");
        while (rs.next()) {
            if (rs.getString(1).equals(usuario)) {
                rs.close();
                st.close();
                return true;
            }
        }
        rs.close();
        st.close();
        return false;
    }

    public List<FuncionarioBean> getMedico() throws SQLException {
        List<FuncionarioBean> lista = new ArrayList<FuncionarioBean>();
        String sql = "select codigo,nome from funcionario where funcao = 2";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            System.out.println(rs.getInt(1));
            FuncionarioBean funcionarioBean = new FuncionarioBean();
            funcionarioBean.setCodigo(rs.getInt(1));
            funcionarioBean.setNome(rs.getString(2));
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<FuncionarioBean> getFuncionario(String nome) throws SQLException {
        List<FuncionarioBean> lista = new ArrayList<FuncionarioBean>();
        String sql = "select codigo,initcap(nome),telefone,email,funcao from funcionario where nome like lower('" + nome + "%') order by nome";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            FuncionarioBean funcionarioBean = new FuncionarioBean();
            funcionarioBean.setCodigo(rs.getInt(1));
            funcionarioBean.setNome(rs.getString(2));
            funcionarioBean.setTelefone(rs.getString(3));
            funcionarioBean.setEmail(rs.getString(4));
            funcionarioBean.setFuncao(rs.getInt(5));
            lista.add(funcionarioBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<FuncionarioBean> getMedico(String nome) throws SQLException {
        List<FuncionarioBean> lista = new ArrayList<FuncionarioBean>();
        String sql = "select codigo,initcap(nome),telefone,email,funcao from funcionario where (nome like lower('" + nome + "%')) and (funcao=1) order by nome";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        numeroLinhas = 0;
        while (rs.next()) {
            FuncionarioBean funcionarioBean = new FuncionarioBean();
            funcionarioBean.setCodigo(rs.getInt(1));
            funcionarioBean.setNome(rs.getString(2));
            funcionarioBean.setTelefone(rs.getString(3));
            funcionarioBean.setEmail(rs.getString(4));
            funcionarioBean.setFuncao(rs.getInt(5));
            lista.add(funcionarioBean);
            numeroLinhas++;
        }
        rs.close();
        st.close();
        return lista;
    }

    public List<FuncionarioBean> getMedico(int codigo) throws SQLException {
        List<FuncionarioBean> lista = new ArrayList<FuncionarioBean>();
        String sql = "select codigo,initcap(nome) from funcionario where (codigo= " + codigo + " ) and (funcao=1)";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            FuncionarioBean funcionarioBean = new FuncionarioBean();
            funcionarioBean.setCodigo(rs.getInt(1));
            funcionarioBean.setNome(rs.getString(2));
            lista.add(funcionarioBean);
        }
        rs.close();
        st.close();
        return lista;
    }

    public boolean existeSigla(String sigla, int tipo, int codFuncionario, int funcao) {
        try {
            if (funcao == 0) {
                return false;
            }
            boolean existe = false;
            String sql;
            if (tipo == 1) {
                sql = "select sigla from funcionario";
            } else {
                sql = "select sigla from funcionario where codigo !=" + codFuncionario;
            }
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (sigla.equals(rs.getString(1))) {
                    rs.close();
                    st.close();
                    return true;
                }
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public String getSigla(int codMedico) {
        String sigla = null;
        try {

            String sql = "select sigla from funcionario where codigo = " + codMedico;
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                sigla = rs.getString(1);
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return sigla;
    }

    public void deletaFuncionario(int codigo) {
        try {
            Statement st = conexao.createStatement();
            String sqlDDL = "delete from funcionario where codigo=" + codigo + "";
            int ret = st.executeUpdate(sqlDDL);
            JOptionPane.showMessageDialog(null, "Dados do Funcionario, com o código " + codigo + " foram excluidos com sucesso.", "Excluir", JOptionPane.WARNING_MESSAGE);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
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
