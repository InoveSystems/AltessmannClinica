/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

import com.altessmann.Bean.ArquivoBean;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Guilherme
 */
public class ArquivoTableModel extends AbstractTableModel {

    private final int codigo = 0;
    private final int nome = 1;
    private final int data = 2;
    private final int hora = 3;

//lista dos produtos que serão exibidos
    private List arquivos;

    public ArquivoTableModel() {
        arquivos = new ArrayList();
    }

    public ArquivoTableModel(List lista) {
        this();
        arquivos.addAll(lista);
    }

    public void addConsulta(List lista) {
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
        return 4;
    }

    @Override
    public String getColumnName(int column) {
//qual o nome da coluna
        switch (column) {
            case codigo:
                return "Código";
            case nome:
                return "Nome do arquivo";
            case data:
                return "Data";
            case hora:
                return "Horário";
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
            case data:
                return String.class;
            case hora:
                return String.class;
            default:
                return String.class;
        }
    }

    private static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
//pega o produto da linha
        ArquivoBean c = (ArquivoBean) arquivos.get(rowIndex);
        //String dia = toCalendar(c.getData()).get(Calendar.DAY_OF_MONTH) + "";
        //String mes = (toCalendar(c.getData()).get(Calendar.MONTH) + 1) + "";
        //String ano = toCalendar(c.getData()).get(Calendar.YEAR) + "";
        //String horas = toCalendar(c.getData()).get(Calendar.HOUR_OF_DAY) + "";
        //String minutos = toCalendar(c.getData()).get(Calendar.MINUTE) + "";
        //String segundos = toCalendar(c.getData()).get(Calendar.SECOND) + "";
        switch (columnIndex) {
            case codigo:
                return c.getCodigoArquivo();
            case nome:
                return c.getNome();
            case data:
                /*if (toCalendar(c.getData()).get(Calendar.DAY_OF_MONTH) < 10) {
                    dia = "0" + toCalendar(c.getData()).get(Calendar.DAY_OF_MONTH);
                }
                if ((toCalendar(c.getData()).get(Calendar.MONTH) + 1) < 10) {
                    mes = "0" + (toCalendar(c.getData()).get(Calendar.MONTH) + 1);
                }
                String x = dia + "/" + mes + "/" + ano;
                return x;*/
                return c.getData();
            case hora:
                /*if (toCalendar(c.getData()).get(Calendar.HOUR_OF_DAY) < 10) {
                    horas = "0" + toCalendar(c.getData()).get(Calendar.HOUR_OF_DAY);
                }
                if (toCalendar(c.getData()).get(Calendar.MINUTE) < 10) {
                    minutos = "0" + toCalendar(c.getData()).get(Calendar.MINUTE);
                }
                if (toCalendar(c.getData()).get(Calendar.SECOND) < 10) {
                    segundos = "0" + toCalendar(c.getData()).get(Calendar.SECOND);
                }
                String y = horas + ":" + minutos + ":" + segundos;
                return y;*/
                return c.getHora();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ArquivoBean c = (ArquivoBean) arquivos.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                c.setCodigoArquivo(Integer.parseInt(aValue.toString()));
            case nome:
                c.setNome(aValue.toString());
            case data:
                /*SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
                 {
                    try {
                        Date data = new java.sql.Date(format.parse(aValue.toString()).getTime());
                        c.setData(data);
                    } catch (ParseException ex) {
                        Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }*/
                c.setData(aValue.toString());
            case hora:/* {
                try {
                    SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
                    Date data;
                    data = formatador.parse(aValue + "");
                    Time time = new Time(data.getTime());
                    c.setData(time);
                } catch (ParseException ex) {
                    Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/
                c.setHora(aValue.toString());

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
