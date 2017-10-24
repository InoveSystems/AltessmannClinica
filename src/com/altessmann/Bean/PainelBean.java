/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.Bean;

import java.util.Date;

/**
 *
 * @author Ritiele Aldeburg
 */
public class PainelBean {

    private int cod;
    private String ultimaChamada, penultimaChamada, antepenultimaChamada, atualChamada, sala;
    private Date data;

    public PainelBean() {
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getUltimaChamada() {
        return ultimaChamada;
    }

    public void setUltimaChamada(String ultimaChamada) {
        this.ultimaChamada = ultimaChamada;
    }

    public String getPenultimaChamada() {
        return penultimaChamada;
    }

    public void setPenultimaChamada(String penultimaChamada) {
        this.penultimaChamada = penultimaChamada;
    }

    public String getAntepenultimaChamada() {
        return antepenultimaChamada;
    }

    public void setAntepenultimaChamada(String antepenultimaChamada) {
        this.antepenultimaChamada = antepenultimaChamada;
    }

    public String getAtualChamada() {
        return atualChamada;
    }

    public void setAtualChamada(String atualChamada) {
        this.atualChamada = atualChamada;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }
}
