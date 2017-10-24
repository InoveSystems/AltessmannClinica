/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.Tools;

import com.altessmann.Bean.ConsultaBean;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class FilaConsulta {

    private List<ConsultaBean> consultas = new LinkedList<ConsultaBean>();

    public void push(ConsultaBean consultaBean) {
        this.consultas.add(consultaBean);
    }

    public ConsultaBean pop() {
        return this.consultas.remove(0);
    }

    public int size() {
        return consultas.size();
    }

}
