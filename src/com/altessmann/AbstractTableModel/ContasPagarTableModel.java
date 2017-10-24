/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

import com.altessmann.Bean.ContasPagarBean;
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
public class ContasPagarTableModel extends AbstractTableModel {

    private final int codigo = 0;
    private final int nome = 1;
    private final int valor = 2;
    private final int dtVencimento = 3;
    private final int quitado = 4;

//lista dos produtos que serão exibidos
    private List contasPagar;

    public ContasPagarTableModel() {
        contasPagar = new ArrayList();
    }

    public ContasPagarTableModel(List lista) {
        this();
        contasPagar.addAll(lista);
    }

    public void addPaciente(List lista) {
        contasPagar.addAll(lista);
    }

    @Override
    public int getRowCount() {
//cada produto na lista será uma linha
        return contasPagar.size();
    }

    @Override
    public int getColumnCount() {
//vamos exibir só Nome e Quantidade, então são 2 colunas
        return 5;
    }

    @Override
    public String getColumnName(int column) {
//qual o nome da coluna
        switch (column) {
            case codigo:
                return "Código";
            case nome:
                return "Nome/Razão social";
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
        ContasPagarBean c = (ContasPagarBean) contasPagar.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                return c.getCodigo();
            case nome:
                return c.getNomeFornecedor();
            case valor:
                return c.getValor();
            case dtVencimento:
                return c.getDtVencimento();
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
        ContasPagarBean c = (ContasPagarBean) contasPagar.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                c.setCodigo(Integer.parseInt(aValue.toString()));
            case nome:
                c.setNomeFornecedor(aValue.toString());
            case valor:
                c.setValor(Double.parseDouble(aValue.toString()));
            case dtVencimento:
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = sdf.parse(aValue.toString());
                    c.setDtVencimento(date);
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
        contasPagar.add(p);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        contasPagar.remove(pos);

        fireTableDataChanged();
    }

    public void limpaTabela() {
        contasPagar.removeAll(contasPagar);
        fireTableDataChanged();
    }

    public void excluir(PacienteBean p) {
        contasPagar.remove(p);

        fireTableDataChanged();
    }

    public PacienteBean getPaciente(int pos) {
        if (pos < 0 || pos >= contasPagar.size()) {
            return null;
        }

        return (PacienteBean) contasPagar.get(pos);
    }

    public void removeRow(int row) {
        // remove a row from your internal data structure
        fireTableRowsDeleted(row, row);
    }
}
