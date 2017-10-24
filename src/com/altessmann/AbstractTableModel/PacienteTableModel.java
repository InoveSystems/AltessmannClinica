/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

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
public class PacienteTableModel extends AbstractTableModel {

    private final int codigo = 0;
    private final int nome = 1;
    private final int telefone = 2;
    private final int ativo = 3;
    private final int ultimaConsulta = 4;

//lista dos produtos que serão exibidos
    private List pacientes;

    public PacienteTableModel() {
        pacientes = new ArrayList();
    }

    public PacienteTableModel(List lista) {
        this();
        pacientes.addAll(lista);
    }

    public void addPaciente(List lista) {
        pacientes.addAll(lista);
    }

    @Override
    public int getRowCount() {
//cada produto na lista será uma linha
        return pacientes.size();
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
                return "Nome";
            case telefone:
                return "Telefone";
            case ativo:
                return "Ativo";
            case ultimaConsulta:
                return "Última Consulta";
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
            case telefone:
                return String.class;
            case ativo:
                return String.class;
            case ultimaConsulta:
                return Date.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
//pega o produto da linha
        PacienteBean p = (PacienteBean) pacientes.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                return p.getCodigo();
            case nome:
                return p.getNome();
            case telefone:
                return p.getTelefone();
            case ativo:
                return p.getAtivo(); 
            case ultimaConsulta:
                return p.getUltimaConsulta();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        PacienteBean p = (PacienteBean) pacientes.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                p.setCodigo(Integer.parseInt(aValue.toString()));
            case nome:
                p.setNome(aValue.toString());
            case telefone:
                p.setTelefone(aValue.toString());
            case ativo:
                p.setAtivo(aValue.toString());
            case ultimaConsulta:
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = sdf.parse(aValue.toString());
                    p.setUltimaConsulta(date);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Erro - classe PacienteTableModel\n" + ex, "Erro!", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(PacienteTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
//no nosso caso todas vão ser editáveis, entao retorna true pra todas
        return false;
    }

    public void inserir(PacienteBean p) {
        pacientes.add(p);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        pacientes.remove(pos);

        fireTableDataChanged();
    }

    public void limpaTabela() {
        pacientes.removeAll(pacientes);
        fireTableDataChanged();
    }

    public void excluir(PacienteBean p) {
        pacientes.remove(p);

        fireTableDataChanged();
    }

    public PacienteBean getPaciente(int pos) {
        if (pos < 0 || pos >= pacientes.size()) {
            return null;
        }

        return (PacienteBean) pacientes.get(pos);
    }

    public void removeRow(int row) {
        // remove a row from your internal data structure
        fireTableRowsDeleted(row, row);
    }
}
