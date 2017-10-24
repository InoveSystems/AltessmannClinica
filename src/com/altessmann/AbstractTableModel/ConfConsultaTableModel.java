/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

import com.altessmann.Bean.ConfConsultaBean;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 
 * @author Guilherme
 */
public class ConfConsultaTableModel extends AbstractTableModel {

    private final int codigo = 0;
    private final int nome = 1;
    private final int valor = 2;

//lista dos produtos que serão exibidos
    private List confConsultas;

    public ConfConsultaTableModel() {
        confConsultas = new ArrayList();
    }

    public ConfConsultaTableModel(List lista) {
        this();
        confConsultas.addAll(lista);
    }

    public void addConfConsulta(List lista) {
        confConsultas.addAll(lista);
    }

    @Override
    public int getRowCount() {
//cada produto na lista será uma linha
        return confConsultas.size();
    }

    @Override
    public int getColumnCount() {
//vamos exibir só Nome e Quantidade, então são 2 colunas
        return 3;
    }

    @Override
    public String getColumnName(int column) {
//qual o nome da coluna
        switch (column) {
            case codigo:
                return "Código";
            case nome:
                return "Nome";
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
            case valor:
                return Double.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
//pega o produto da linha
        ConfConsultaBean c = (ConfConsultaBean) confConsultas.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                return c.getCodigo();
            case nome:
                return c.getNome();
            case valor:
                return c.getValor();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        ConfConsultaBean c = (ConfConsultaBean) confConsultas.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                c.setCodigo(Integer.parseInt(aValue.toString()));
            case nome:
                c.setNome(aValue.toString());
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

    public void inserir(ConfConsultaBean p) {
        confConsultas.add(p);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        confConsultas.remove(pos);

        fireTableDataChanged();
    }

    public void excluir(ConfConsultaBean p) {
        confConsultas.remove(p);

        fireTableDataChanged();
    }

    public ConfConsultaBean getPaciente(int pos) {
        if (pos < 0 || pos >= confConsultas.size()) {
            return null;
        }

        return (ConfConsultaBean) confConsultas.get(pos);
    }
}
