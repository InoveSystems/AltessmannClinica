/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.Bean;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Guilherme
 */
public class ArquivoBean {

    private String codigo, data,hora,nome;
    private byte[] arquivo;
    private int codigoArquivo;
    

    public int getCodigoArquivo() {
        return codigoArquivo;
    }

    public void setCodigoArquivo(int codigoArquivo) {
        this.codigoArquivo = codigoArquivo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
    
    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

   

}
