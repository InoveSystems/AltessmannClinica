/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

import com.altessmann.Bean.ConvenioBean;
import com.altessmann.Bean.PacienteBean;
import java.util.ArrayList;
;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Guilherme
 */


public class Convenio_na_consultaTableModel extends AbstractTableModel {

    private final int codigo = 0;
    private final int titulo = 1;
    private final int desconto = 2;
    private final int seleciona = 3;

//lista dos produtos que serão exibidos
    private List convenios;

    public Convenio_na_consultaTableModel() {
        convenios = new ArrayList();
    }

    public Convenio_na_consultaTableModel(List lista) {
        this();
        convenios.addAll(lista);
    }

    public void addPaciente(List lista) {
        convenios.addAll(lista);
    }

    @Override
    public int getRowCount() {
//cada produto na lista será uma linha
        return convenios.size();
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
            case titulo:
                return "Título";
            case desconto:
                return "Desconto";
            case seleciona:
                return "Seleciona";
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
            case titulo:
                return String.class;
            case desconto:
                return Double.class;
            case seleciona:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
//pega o produto da linha
        ConvenioBean c = (ConvenioBean) convenios.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                return c.getCodigo();
            case titulo:
                return c.getNome();
            case desconto:
                return c.getPorcentagem();
            case seleciona:
                return c.isSeleciona();
            default:
                return "";
        }
    }

//*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        ConvenioBean c = (ConvenioBean) convenios.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                c.setCodigo(Integer.parseInt(aValue.toString()));
            case titulo:
                c.setNome(aValue.toString());
            case desconto:
                c.setPorcentagem(Double.parseDouble(aValue.toString()));
            case seleciona:
                /*if (Double.parseDouble(getValueAt(rowIndex, desconto) + "") == 0) {
                    double porcentagem = 0;
                    String porc = JOptionPane.showInputDialog(null, "Informe a porcentagem para '" + this.getValueAt(rowIndex, titulo) + "':", "Informar porcentagem", JOptionPane.PLAIN_MESSAGE);
                    try {
                        porcentagem = Double.parseDouble(porc);
                        this.setValueAt(porcentagem + "", rowIndex, desconto);
                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(null, "Porcentagem inválida!", "", JOptionPane.ERROR_MESSAGE);
                        this.setValueAt(0.1 + "", rowIndex, desconto);
                        this.setValueAt(false , rowIndex, seleciona);
                    }
                }*/
                c.setSeleciona(Boolean.parseBoolean(aValue.toString()));
        }

        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
//no nosso caso todas vão ser editáveis, entao retorna true pra todas
        switch (columnIndex) {
            case seleciona:
                return true;
            default:
                return false;
        }
    }

    public void inserir(PacienteBean p) {
        convenios.add(p);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        convenios.remove(pos);

        fireTableDataChanged();
    }

    public void limpaTabela() {
        convenios.removeAll(convenios);
        fireTableDataChanged();
    }

    public void excluir(PacienteBean p) {
        convenios.remove(p);

        fireTableDataChanged();
    }

    public PacienteBean getPaciente(int pos) {
        if (pos < 0 || pos >= convenios.size()) {
            return null;
        }

        return (PacienteBean) convenios.get(pos);
    }
}
