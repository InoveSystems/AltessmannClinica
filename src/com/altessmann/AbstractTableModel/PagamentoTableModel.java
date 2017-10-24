/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

import com.altessmann.Bean.ArquivoBean;
import com.altessmann.Bean.PagamentoBean;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Guilherme
 */
public class PagamentoTableModel extends AbstractTableModel {

    private final int tipo = 0;
    private final int valor = 1;

//lista dos produtos que serão exibidos
    private List arquivos;

    public PagamentoTableModel() {
        arquivos = new ArrayList();
    }

    public PagamentoTableModel(List lista) {
        this();
        arquivos.addAll(lista);
    }

    @Override
    public int getRowCount() {
//cada produto na lista será uma linha
        return arquivos.size();
    }

    @Override
    public int getColumnCount() {
//vamos exibir só Nome e Quantidade, então são 2 colunas
        return 2;
    }

    @Override
    public String getColumnName(int column) {
//qual o nome da coluna
        switch (column) {
            case tipo:
                return "Tipo";
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
            case tipo:
                return String.class;
            case valor:
                return Double.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    DecimalFormat formatoDois = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2);
        formatoDois.setParseBigDecimal(true);
        PagamentoBean c = (PagamentoBean) arquivos.get(rowIndex);
        switch (columnIndex) {
            case tipo:
                return c.getTipo();
            case valor:
                return c.getValor();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PagamentoBean c = (PagamentoBean) arquivos.get(rowIndex);
        switch (columnIndex) {
            case tipo:
                c.setTipo(aValue.toString());
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

    public void inserir(ArquivoBean p) {
        arquivos.add(p);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        arquivos.remove(pos);

        fireTableDataChanged();
    }

    public void excluir(ArquivoBean p) {
        arquivos.remove(p);

        fireTableDataChanged();
    }

    public void limpaTabela() {
        arquivos.removeAll(arquivos);
        fireTableDataChanged();
    }

  
    public ArquivoBean getConsulta(int pos) {
        if (pos < 0 || pos >= arquivos.size()) {
            return null;
        }

        return (ArquivoBean) arquivos.get(pos);
    }
}
