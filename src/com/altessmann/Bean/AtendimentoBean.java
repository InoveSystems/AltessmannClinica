/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.Bean;

/**
 *
 * @author Guilherme
 */
public class AtendimentoBean {
    private int codMedico,ConsultasAtendidas,ConsultasTotais,ConsultasRestantes;
    private String nomeMedico;

    public int getCodMedico() {
        return codMedico;
    }

    public void setCodMedico(int codMedico) {
        this.codMedico = codMedico;
    }

    public int getConsultasAtendidas() {
        return ConsultasAtendidas;
    }

    public void setConsultasAtendidas(int ConsultasAtendidas) {
        this.ConsultasAtendidas = ConsultasAtendidas;
    }

    public int getConsultasTotais() {
        return ConsultasTotais;
    }

    public void setConsultasTotais(int ConsultasTotais) {
        this.ConsultasTotais = ConsultasTotais;
    }

    public int getConsultasRestantes() {
        return ConsultasRestantes;
    }

    public void setConsultasRestantes(int ConsultasRestantes) {
        this.ConsultasRestantes = ConsultasRestantes;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }
    
    
}
