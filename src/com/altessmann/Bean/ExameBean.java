/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.Bean;

import java.util.Date;

/**
 *
 * @author Guilherme
 */
public class ExameBean {

    private int codigo, codTipoExame, codMedico, codPaciente, tentativas, maiorNumeroArquivo;
    private String nomeTipoExame, nomeMedico, nomePaciente, laudo, descricaoTipoExame;
    private Date dtRequisicao, dtLaudo;
    private boolean laudoGerado;

    public int getMaiorNumeroArquivo() {
        return maiorNumeroArquivo;
    }

    public void setMaiorNumeroArquivo(int maiorNumeroArquivo) {
        this.maiorNumeroArquivo = maiorNumeroArquivo;
    }

    public int getCodigo() {
        return codigo;
    }

    public boolean isLaudoGerado() {
        return laudoGerado;
    }

    public void setLaudoGerado(boolean laudoGerado) {
        this.laudoGerado = laudoGerado;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricaoTipoExame() {
        return descricaoTipoExame;
    }

    public void setDescricaoTipoExame(String descricaoTipoExame) {
        this.descricaoTipoExame = descricaoTipoExame;
    }

    public int getCodTipoExame() {
        return codTipoExame;
    }

    public void setCodTipoExame(int codTipoExame) {
        this.codTipoExame = codTipoExame;
    }

    public int getCodMedico() {
        return codMedico;
    }

    public void setCodMedico(int codMedico) {
        this.codMedico = codMedico;
    }

    public int getCodPaciente() {
        return codPaciente;
    }

    public void setCodPaciente(int codPaciente) {
        this.codPaciente = codPaciente;
    }

    public int getTentativas() {
        return tentativas;
    }

    public void setTentativas(int tentativas) {
        this.tentativas = tentativas;
    }

    public String getNomeTipoExame() {
        return nomeTipoExame;
    }

    public void setNomeTipoExame(String nomeTipoExame) {
        this.nomeTipoExame = nomeTipoExame;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getLaudo() {
        return laudo;
    }

    public void setLaudo(String laudo) {
        this.laudo = laudo;
    }

    public Date getDtRequisicao() {
        return dtRequisicao;
    }

    public void setDtRequisicao(Date dtRequisicao) {
        this.dtRequisicao = dtRequisicao;
    }

    public Date getDtLaudo() {
        return dtLaudo;
    }

    public void setDtLaudo(Date dtLaudo) {
        this.dtLaudo = dtLaudo;
    }
}
