/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

import com.altessmann.Bean.ArquivoBean;
import com.altessmann.Bean.AtendimentoBean;
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
public class AtendimentoTableModel extends AbstractTableModel {

    private final int codMedico = 0;
    private final int nomeMedico = 1;
    private final int consultasAtendidas = 2;
    private final int consultasRestantes = 3;
    private final int consultasTotais = 4;

//lista dos produtos que serão exibidos
    private List dados;

    public AtendimentoTableModel() {
        dados = new ArrayList();
    }

    public AtendimentoTableModel(List lista) {
        this();
        dados.addAll(lista);
    }

    public void addConsulta(List lista) {
        dados.addAll(lista);
    }

    @Override
    public int getRowCount() {
//cada produto na lista será uma linha
        return dados.size();
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
            case codMedico:
                return "Código";
            case nomeMedico:
                return "Nome Médico";
            case consultasAtendidas:
                return "Consultas atendidas";
            case consultasRestantes:
                return "Consultas restantes";
            case consultasTotais:
                return "Total de consultas";
            default:
                return "";
        }
    }

    @Override
    public Class getColumnClass(int columnIndex) {
//retorna a classe que representa a coluna
        switch (columnIndex) {
            case codMedico:
                return Integer.class;
            case nomeMedico:
                return String.class;
            case consultasAtendidas:
                return Integer.class;
            case consultasRestantes:
                return Integer.class;
            case consultasTotais:
                return Integer.class;
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
        AtendimentoBean c = (AtendimentoBean) dados.get(rowIndex);
        switch (columnIndex) {
            case codMedico:
                return c.getCodMedico();
            case nomeMedico:
                return c.getNomeMedico();
            case consultasAtendidas:
                return c.getConsultasAtendidas();
            case consultasRestantes:
                return c.getConsultasRestantes();
            case consultasTotais:
                return c.getConsultasTotais();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        /*ArquivoBean c = (ArquivoBean) arquivos.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                c.setCodigo(aValue.toString());
            case local:
                c.setLocal(aValue.toString());
            case data:
                SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
                 {
                    try {
                        Date data = new java.sql.Date(format.parse(aValue.toString()).getTime());
                        c.setData(data);
                    } catch (ParseException ex) {
                        Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            case hora: {
                try {
                    SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
                    Date data;
                    data = formatador.parse(aValue + "");
                    Time time = new Time(data.getTime());
                    c.setData(time);
                } catch (ParseException ex) {
                    Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        fireTableDataChanged();*/
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
//no nosso caso todas vão ser editáveis, entao retorna true pra todas
        return false;
    }

    public void inserir(ArquivoBean p) {
        dados.add(p);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        dados.remove(pos);

        fireTableDataChanged();
    }

    public void excluir(ArquivoBean p) {
        dados.remove(p);

        fireTableDataChanged();
    }

    public void limpaTabela() {
        dados.removeAll(dados);
        fireTableDataChanged();
    }

    public ArquivoBean getConsulta(int pos) {
        if (pos < 0 || pos >= dados.size()) {
            return null;
        }

        return (ArquivoBean) dados.get(pos);
    }
}
