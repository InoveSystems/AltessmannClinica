/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

import com.altessmann.Bean.ContasReceberBean;
import com.altessmann.Bean.PacienteBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Guilherme
 */
public class ContasReceberTableModel extends AbstractTableModel {

    private final int codigo = 0;
    private final int codConsulta = 1;
    private final int nome = 2;
    private final int valor = 3;
    private final int dtVencimento = 4;
    private final int quitado = 5;

//lista dos produtos que serão exibidos
    private List contasReceber;

    public ContasReceberTableModel() {
        contasReceber = new ArrayList();
    }

    public ContasReceberTableModel(List lista) {
        this();
        contasReceber.addAll(lista);
    }

    public void addPaciente(List lista) {
        contasReceber.addAll(lista);
    }

    @Override
    public int getRowCount() {
//cada produto na lista será uma linha
        return contasReceber.size();
    }

    @Override
    public int getColumnCount() {
//vamos exibir só Nome e Quantidade, então são 2 colunas
        return 6;
    }

    @Override
    public String getColumnName(int column) {
//qual o nome da coluna
        switch (column) {
            case codigo:
                return "Código";
            case codConsulta:
                return "Cód. Consulta";
            case nome:
                return "Nome paciente";
            case valor:
                return "Valor";
            case dtVencimento:
                return "Data vencimento";
            case quitado:
                return "Quitado";
            default:
                return "";
        }
    }

    @Override
    public Class getColumnClass(int columnIndex) {
//retorna a classe que representa a coluna
        switch (columnIndex) {
            case codigo:
                return Integer.class;
            case codConsulta:
                return Integer.class;
            case nome:
                return String.class;
            case valor:
                return Double.class;
            case dtVencimento:
                return Date.class;
            case quitado:
                return String.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
//pega o produto da linha
        ContasReceberBean c = (ContasReceberBean) contasReceber.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                return c.getCodigo();
            case codConsulta:
                return c.getCodConsulta();
            case nome:
                return c.getNomePaciente();
            case valor:
                return c.getValorTotal();
            case dtVencimento:
                return c.getDtVencimente();
            case quitado:
                if (c.isQuitado()) {
                    return "SIM";
                } else {
                    return "NAO";
                }

            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ContasReceberBean c = (ContasReceberBean) contasReceber.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                c.setCodigo(Integer.parseInt(aValue.toString()));
            case codConsulta:
                c.setCodConsulta(Integer.parseInt(aValue.toString()));
            case nome:
                c.setNomePaciente(aValue.toString());
            case valor:
                c.setValorTotal(Double.parseDouble(aValue.toString()) * c.getParcelas());
            case dtVencimento:
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = sdf.parse(aValue.toString());
                    c.setDtVencimente(date);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Erro - classe ContasReceberTableModel\n" + ex, "Erro!", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(PacienteTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            case quitado:
                c.setQuitado(Boolean.getBoolean(aValue.toString()));
        }

        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
//no nosso caso todas vão ser editáveis, entao retorna true pra todas
        return false;
    }

    public void inserir(PacienteBean p) {
        contasReceber.add(p);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        contasReceber.remove(pos);

        fireTableDataChanged();
    }

    public void limpaTabela() {
        contasReceber.removeAll(contasReceber);
        fireTableDataChanged();
    }

    public void excluir(PacienteBean p) {
        contasReceber.remove(p);

        fireTableDataChanged();
    }

    public PacienteBean getPaciente(int pos) {
        if (pos < 0 || pos >= contasReceber.size()) {
            return null;
        }

        return (PacienteBean) contasReceber.get(pos);
    }

    public void removeRow(int row) {
        // remove a row from your internal data structure
        fireTableRowsDeleted(row, row);
    }
}
