/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

import com.altessmann.Bean.ExameBean;
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
public class ExameTableModel extends AbstractTableModel {

    private final int codigo = 0;
    private final int nomePaciente = 1;
    private final int nomeExame = 2;
    private final int descricaoExame = 3;
    private final int dtRequisicao = 4;
    private final int dtLaudo = 5;
    private final int laudoGerado = 6;

//lista dos produtos que serão exibidos
    private List exames;

    public ExameTableModel() {
        exames = new ArrayList();
    }

    public ExameTableModel(List lista) {
        this();
        exames.addAll(lista);
    }

    public void addConsulta(List lista) {
        exames.addAll(lista);
    }

    @Override
    public int getRowCount() {
//cada produto na lista será uma linha
        return exames.size();
    }

    @Override
    public int getColumnCount() {
//vamos exibir só Nome e Quantidade, então são 2 colunas
        return 7;
    }

    @Override
    public String getColumnName(int column) {
//qual o nome da coluna
        switch (column) {
            case codigo:
                return "Código";
            case nomePaciente:
                return "Nome paciente";
            case nomeExame:
                return "Nome exame";
            case descricaoExame:
                return "Descrição";
            case dtRequisicao:
                return "Data requisição";
            case dtLaudo:
                return "Data laudo";
            case laudoGerado:
                return "Laudo gerado";
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
            case nomePaciente:
                return String.class;
            case nomeExame:
                return String.class;
            case descricaoExame:
                return String.class;
            case dtRequisicao:
                return String.class;
            case dtLaudo:
                return String.class;
            case laudoGerado:
                return Boolean.class;
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
        ExameBean c = (ExameBean) exames.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                return c.getCodigo();
            case nomePaciente:
                return c.getNomePaciente();
            case nomeExame:
                return c.getNomeTipoExame();
            case descricaoExame:
                return c.getDescricaoTipoExame();
            case dtRequisicao:
                String x = toCalendar(c.getDtRequisicao()).get(Calendar.DAY_OF_MONTH) + "/" + (toCalendar(c.getDtRequisicao()).get(Calendar.MONTH) + 1) + "/" + toCalendar(c.getDtRequisicao()).get(Calendar.YEAR);
                return x;
            case dtLaudo:
                String y = toCalendar(c.getDtLaudo()).get(Calendar.DAY_OF_MONTH) + "/" + (toCalendar(c.getDtLaudo()).get(Calendar.MONTH) + 1) + "/" + toCalendar(c.getDtLaudo()).get(Calendar.YEAR);
                return y;
            case laudoGerado:
                return c.isLaudoGerado();
            default:
                return "";
                
        }
        
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        ExameBean c = (ExameBean) exames.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                c.setCodigo(Integer.parseInt(aValue.toString()));
            case nomePaciente:
                c.setNomePaciente(aValue.toString());
            case nomeExame:
                c.setNomeTipoExame(aValue.toString());
            case descricaoExame:
                c.setDescricaoTipoExame(aValue.toString());
            case dtRequisicao:
                try {
                    Date data = new java.sql.Date(format.parse(aValue.toString()).getTime());
                    c.setDtRequisicao(data);
                } catch (ParseException ex) {
                    Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            case dtLaudo: {
                try {
                    Date data = new java.sql.Date(format.parse(aValue.toString()).getTime());
                    c.setDtLaudo(data);
                } catch (ParseException ex) {
                    Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case laudoGerado:
                c.setLaudoGerado(Boolean.parseBoolean(aValue.toString()));
        }

        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
//no nosso caso todas vão ser editáveis, entao retorna true pra todas
        return false;
    }

    public void inserir(ExameBean p) {
        exames.add(p);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        exames.remove(pos);

        fireTableDataChanged();
    }

    public void excluir(ExameBean p) {
        exames.remove(p);

        fireTableDataChanged();
    }

    public void limpaTabela() {
        exames.removeAll(exames);
        fireTableDataChanged();
    }

    public ExameBean getConsulta(int pos) {
        if (pos < 0 || pos >= exames.size()) {
            return null;
        }

        return (ExameBean) exames.get(pos);
    }
}
