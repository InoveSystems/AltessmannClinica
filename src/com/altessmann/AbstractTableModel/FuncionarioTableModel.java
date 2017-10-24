/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.AbstractTableModel;

import com.altessmann.Bean.FuncionarioBean;
import com.altessmann.Bean.PacienteBean;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Guilherme
 */
public class FuncionarioTableModel extends AbstractTableModel {

    private final int codigo = 0;
    private final int nome = 1;
    private final int telefone = 2;
    private final int email = 3;
    private final int funcao = 4;

//lista dos produtos que serão exibidos
    private List funcionarios;

    public FuncionarioTableModel() {
        funcionarios = new ArrayList();
    }

    public FuncionarioTableModel(List lista) {
        this();
        funcionarios.addAll(lista);
    }

    public void addPaciente(List lista) {
        funcionarios.addAll(lista);
    }

    @Override
    public int getRowCount() {
//cada produto na lista será uma linha
        return funcionarios.size();
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
            case email:
                return "Email";
            case funcao:
                return "Função";
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
            case email:
                return String.class;
            case funcao:
                return String.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
//pega o produto da linha
        FuncionarioBean p = (FuncionarioBean) funcionarios.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                return p.getCodigo();
            case nome:
                return p.getNome();
            case telefone:
                return p.getTelefone();
            case email:
                return p.getEmail();
            case funcao:
                switch (p.getFuncao()) {
                    case 0:
                        return "Secretária";
                    case 1:
                        return "Médico";
                }
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        FuncionarioBean p = (FuncionarioBean) funcionarios.get(rowIndex);
        switch (columnIndex) {
            case codigo:
                p.setCodigo(Integer.parseInt(aValue.toString()));
            case nome:
                p.setNome(aValue.toString());
            case telefone:
                p.setTelefone(aValue.toString());
            case email:
                p.setEmail(aValue.toString());
            case funcao:
                p.setFuncao(Integer.parseInt(aValue.toString()));
        }

        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
//no nosso caso todas vão ser editáveis, entao retorna true pra todas
        return false;
    }

    public void inserir(PacienteBean p) {
        funcionarios.add(p);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        funcionarios.remove(pos);

        fireTableDataChanged();
    }

    public void refresh() {
        fireTableDataChanged();
    }

    public void limpaTabela() {
        funcionarios.removeAll(funcionarios);
        fireTableDataChanged();
    }

    public void excluir(PacienteBean p) {
        funcionarios.remove(p);

        fireTableDataChanged();
    }

    public FuncionarioBean getPaciente(int pos) {
        if (pos < 0 || pos >= funcionarios.size()) {
            return null;
        }

        return (FuncionarioBean) funcionarios.get(pos);
    }

    public void removeRow(int row) {
        // remove a row from your internal data structure
        fireTableRowsDeleted(row, row);
    }
}
