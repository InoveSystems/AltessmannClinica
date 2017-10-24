/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

import com.altessmann.Bean.TipoExameBean;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Guilherme
 */
public class TipoExameTableModel extends AbstractTableModel {

    private final int codigo = 0;
    private final int nome = 1;
    private final int descricao = 2;
    private final int valor = 3;

//lista dos produtos que serão exibidos
    private List tipoExames;

    public TipoExameTableModel() {
        tipoExames = new ArrayList();
    }

    public TipoExameTableModel(List lista) {
        this();
        tipoExames.addAll(lista);
    }

    public void addTipoExame(List lista) {
        tipoExames.addAll(lista);
    }

    @Override
    public int getRowCount() {
//cada produto na lista será uma linha
        return tipoExames.size();
    }

    @Override
    public int getColumnCount() {
//vamos exibir só Nome e Quantidade, então são 2 colunas
        return 4;
    }

    @Override
    public String getColumnName(int column) {
//qual o nome da coluna
        switch (column) {
            case codigo:
                return "Código";
            case nome:
                return "Nome";
            case descricao:
                return "Descrição";
            case valor:
                return "Valor";
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
            case descricao:
                return String.class;
            case valor:
                return double.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
//pega o produto da linha
        TipoExameBean c = (TipoExameBean) tipoExames.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                return c.getCodigo();
            case nome:
                return c.getNome();
            case descricao:
                return c.getDescricao();
            case valor:
                return c.getValor();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        TipoExameBean c = (TipoExameBean) tipoExames.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                c.setCodigo(Integer.parseInt(aValue.toString()));
            case nome:
                c.setNome(aValue.toString());
            case descricao:
                c.setDescricao(aValue.toString());
            case valor:
                c.setValor(Double.parseDouble(aValue.toString()));
        }

        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
//no nosso caso todas vão ser editáveis, entao retorna true pra todas
        return false;
    }

    public void inserir(TipoExameBean p) {
        tipoExames.add(p);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        tipoExames.remove(pos);
        fireTableDataChanged();
    }

    public void excluir(TipoExameBean p) {
        tipoExames.remove(p);
        fireTableDataChanged();
    }

    public void limpaTabela() {
        tipoExames.removeAll(tipoExames);
        fireTableDataChanged();
    }

    public TipoExameBean getPaciente(int pos) {
        if (pos < 0 || pos >= tipoExames.size()) {
            return null;
        }

        return (TipoExameBean) tipoExames.get(pos);
    }
    public void removeRow(int row) {
        // remove a row from your internal data structure
        fireTableRowsDeleted(row, row);
    }
}
