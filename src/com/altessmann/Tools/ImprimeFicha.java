/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.Tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class ImprimeFicha {

    public void Imprime(String filename, String ficha) {
        Process child;
        String comandos;
        int i;
        boolean bStatus = false;
        Calendar calendar = new GregorianCalendar();
        String d, m, a, hor, min, seg;
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            d = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            d = calendar.get(Calendar.DAY_OF_MONTH) + "";
        }
        if (calendar.get(Calendar.MONTH) + 1 < 10) {
            m = "0" + (calendar.get(Calendar.MONTH) + 1);
        } else {
            m = (calendar.get(Calendar.MONTH) + 1) + "";
        }
        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
            hor = "0" + calendar.get(Calendar.HOUR_OF_DAY);
        } else {
            hor = calendar.get(Calendar.HOUR_OF_DAY) + "";
        }
        if (calendar.get(Calendar.MINUTE) < 10) {
            min = "0" + calendar.get(Calendar.MINUTE);
        } else {
            min = calendar.get(Calendar.MINUTE) + "";
        }
        if (calendar.get(Calendar.SECOND) < 10) {
            seg = "0" + calendar.get(Calendar.SECOND);
        } else {
            seg = calendar.get(Calendar.SECOND) + "";
        }

        try {
            //Cria arquivo com conteudo a ser impresso e da ordem de impressão.
            File file = new File(filename);
            FileWriter writer = new FileWriter(new File(filename));
            PrintWriter saida = new PrintWriter(writer, true);
            saida.println("Climed\n");
            saida.println(d + "/" + m + "/" + calendar.get(Calendar.YEAR) + "  " + hor + ":" + min + ":" + seg);
            saida.println("\nSua senha é: " + ficha);
            saida.println("\nAgurade sua chamada");
            writer.close();

            if (bStatus == false) {
                comandos = "print " + filename;
                child = Runtime.getRuntime().exec(comandos);
                bStatus = true;

            }

            if (bStatus == true) {
                Thread.sleep(500);
                file.delete();
            }

        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Não foi possível enviar requisição para a impressora.\nErro: " + ioe.toString(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (InterruptedException ie) {
            JOptionPane.showMessageDialog(null,
                    "Erro na Thread MAIN. Contate a área de TI." + "/n"
                    + "Código: " + ie.toString(),
                    "Aviso!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
