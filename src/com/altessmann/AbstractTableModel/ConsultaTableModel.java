/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

import com.altessmann.Bean.ConsultaBean;
import com.altessmann.Bean.PacienteBean;
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
public class ConsultaTableModel extends AbstractTableModel {

    private final int codigo = 0;
    private final int nomePaciente = 1;
    private final int nomeMedico = 2;
    private final int dtConsulta = 3;
    private final int horario = 4;
    private final int tipoConsulta = 5;
    private final int ficha = 6;
    private final int status = 7;
    private final int reqexames = 8;

//lista dos produtos que serão exibidos
    private List consultas;

    public ConsultaTableModel() {
        consultas = new ArrayList();
    }

    public ConsultaTableModel(List lista) {
        this();
        consultas.addAll(lista);
    }

    public void addConsulta(List lista) {
        consultas.addAll(lista);
    }

    @Override
    public int getRowCount() {
//cada produto na lista será uma linha
        return consultas.size();
    }

    @Override
    public int getColumnCount() {
//vamos exibir só Nome e Quantidade, então são 2 colunas
        return 9;
    }

    @Override
    public String getColumnName(int column) {
//qual o nome da coluna
        switch (column) {
            case codigo:
                return "Código";
            case nomePaciente:
                return "Paciente";
            case nomeMedico:
                return "Médico";
            case dtConsulta:
                return "Data consulta";
            case horario:
                return "Horário";
            case tipoConsulta:
                return "Tipo consulta";
            case ficha:
                return "Ficha";
            case status:
                return "Status da consulta";
            case reqexames:
                return "Requisição de exames";
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
            case nomeMedico:
                return String.class;
            case dtConsulta:
                return String.class;
            case horario:
                return String.class;
            case tipoConsulta:
                return String.class;
            case ficha:
                return String.class;
            case status:
                return String.class;
            case reqexames:
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
        ConsultaBean c = (ConsultaBean) consultas.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                return c.getCodigo();
            case nomePaciente:
                return c.getPacienteNome();
            case nomeMedico:
                return c.getMedicoNome();
            case dtConsulta:
                String x = toCalendar(c.getDtConsulta()).get(Calendar.DAY_OF_MONTH) + "/" + (toCalendar(c.getDtConsulta()).get(Calendar.MONTH) + 1) + "/" + toCalendar(c.getDtConsulta()).get(Calendar.YEAR);
                return x;
            case horario:
                
                return c.getHorario();
            case tipoConsulta:
                return c.getTipoConsultaNome();
            case ficha:
                String zeros;
                if (c.getNumeroFicha() >= 100) {
                    zeros = "0";
                } else if (c.getNumeroFicha() >= 10) {
                    zeros = "00";
                } else {
                    zeros = "000";
                }
                String tipo;
                switch (c.getTipo()) {
                    case 0:
                        tipo = "E";
                        break;
                    case 1:
                        tipo = "P";
                        break;
                    default:
                        tipo = "N";
                        break;
                }

                return c.getSigla() + tipo + zeros + c.getNumeroFicha();
            case status:

                return c.getStatus();
            case reqexames:

                return c.isReqExame();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ConsultaBean c = (ConsultaBean) consultas.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                c.setCodigo(Integer.parseInt(aValue.toString()));
            case nomePaciente:
                c.setPacienteNome(aValue.toString());
            case dtConsulta:
                SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
                 {
                    try {
                        Date data = new java.sql.Date(format.parse(aValue.toString()).getTime());
                        c.setDtConsulta(data);
                    } catch (ParseException ex) {
                        Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            case horario: {
                try {
                    SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
                    Date data;
                    data = formatador.parse(aValue + "");
                    Time time = new Time(data.getTime());
                    c.setHorario(time);
                } catch (ParseException ex) {
                    Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case tipoConsulta:
                c.setTipoConsultaNome(aValue.toString());
        }

        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
//no nosso caso todas vão ser editáveis, entao retorna true pra todas
        return false;
    }

    public void inserir(ConsultaBean p) {
        consultas.add(p);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        consultas.remove(pos);

        fireTableDataChanged();
    }

    public void excluir(ConsultaBean p) {
        consultas.remove(p);

        fireTableDataChanged();
    }

    public void limpaTabela() {
        consultas.removeAll(consultas);
        fireTableDataChanged();

    }

    public PacienteBean getConsulta(int pos) {
        if (pos < 0 || pos >= consultas.size()) {
            return null;
        }

        return (PacienteBean) consultas.get(pos);
    }

    

}
