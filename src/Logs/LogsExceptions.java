/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logs;

import javax.swing.JOptionPane;

/**
 *
 * @author Ritiele Aldeburg
 */
public class LogsExceptions {

    public void ExceptionsTratamento(int codigo) {
        switch (codigo) {
            case 1:
                JOptionPane.showMessageDialog(null, "Erro ao criar IPConfig! \nEntre em contado com o administrador do sistema! \nAltessmann - www.altessmann.com", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Não foi possivel conectar ao servidor de mensagens! \nO Sistema será fechado!! \nEntre em contado com o administrador do sistema! \nAltessmann - www.altessmann.com", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Erro ao salvar os dados no banco! \nEntre em contado com o administrador do sistema! \nAltessmann - www.altessmann.com", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 4:
                JOptionPane.showMessageDialog(null, "Erro ao excluir os dados do banco! \nEntre em contado com o administrador do sistema! \nAltessmann - www.altessmann.com", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 5:
                JOptionPane.showMessageDialog(null, "Erro ao atualizar dados na tabela! \nEntre em contado com o administrador do sistema! \nAltessmann - www.altessmann.com", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 6:
                JOptionPane.showMessageDialog(null, "org.postgresql.Driver nao encontrado! \nEntre em contado com o administrador do sistema! \nAltessmann - www.altessmann.com", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 7:
                JOptionPane.showMessageDialog(null, "Erro ao pesquisar no banco! \nEntre em contado com o administrador do sistema! \nAltessmann - www.altessmann.com", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 8:
                JOptionPane.showMessageDialog(null, "Verifique os dados pesquisados! \nEntre em contado com o administrador do sistema! \nAltessmann - www.altessmann.com", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 9:
                JOptionPane.showMessageDialog(null, "Verifique o campo Numero! \nTipo de valor INVALIDO!", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 10:
                JOptionPane.showMessageDialog(null, "Campo(s) vazio(s)! \nPreenchimento obrigatorio de todos os campos!", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 11:
                JOptionPane.showMessageDialog(null, "                       ATENÇÃO!! \nAo errar sua senha pela terceira tentativa \no sistema sera bloqueado automaticamente! ", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 12:
                JOptionPane.showMessageDialog(null, "Verifique o campo Desconto! \nTipo de valor INVALIDO!", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 13:
                JOptionPane.showMessageDialog(null, "Verifique o campo Valor! \nTipo de valor INVALIDO!", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;
            case 14:
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados! \nEntre em contato com o administrator do sistema!", "Altessmann Sistemas - Informação", JOptionPane.ERROR_MESSAGE);
                break;

        }
    }
}
