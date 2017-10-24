/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.altessmann.Tools;


import java.awt.*;
import java.awt.print.*;
import javax.print.attribute.standard.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.swing.JOptionPane;


public class Impressao implements Printable {

    private String string;

    //Construtor da classe
    public Impressao(String string) {
        this.string = string;
    }

    public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {

        if (pi >= 1) {
            return Printable.NO_SUCH_PAGE;
        }

        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.BLACK);
       
        String[] lines = string.split("\n");
        int y = 100;
        FontMetrics fm = g.getFontMetrics();
        for (int i=0; i < lines.length; i++)
        {
            g.drawString(lines[i], 100, y);
            y += fm.getHeight();
        }

        return Printable.PAGE_EXISTS;
    }
    
    public void  imprime(String texto){
        //Obter o serviço de impressão da impressora default
        PrintService ps = PrintServiceLookup.lookupDefaultPrintService();

        //Define os parâmetros para impressão
        AttributeSet aset = new HashAttributeSet();
        aset.add(new Copies(1));

        //Obter o job de impressão
        PrinterJob pj = PrinterJob.getPrinterJob();
       
        try {
            pj.setPrintService(ps) ;
        }
        catch(PrinterException e){
        }

        Printable pt;

        String string = "Consulta agendada com sucesso!";
       
        pt = new Impressao(texto);
        pj.setPrintable(pt);

        try {
            pj.print();
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(null,"Erro na impressão!","Erro",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {

    }
}