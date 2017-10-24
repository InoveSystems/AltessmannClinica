/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.Impressao;

import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.filechooser.FileSystemView;

public class PdfCriar {

    public void criarpdf(String ficha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println(sdf.format(new Date()));
        Document document = new Document(PageSize.B4, 10, 10, 10, 10);
        try {
            String diretorioUsuario = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            FileOutputStream fis = new FileOutputStream("C:/Altessmann/Temp/SENHAS.pdf");
            PdfWriter.getInstance(document, fis);
            document.open();
            // adicionando um parágrafo ao documento 
            Font f = new Font(FontFamily.HELVETICA, 16, Font.NORMAL);
            document.add(new Paragraph("             ..:: Climed ::..", f));
            Font g = new Font(FontFamily.HELVETICA, 15, Font.NORMAL);
            document.add(new Paragraph("                   SENHA ", g));
            Font H = new Font(FontFamily.HELVETICA, 40, Font.BOLD);
            document.add(new Paragraph("   " + ficha, H));
            document.add(new Paragraph(" ", f));
            document.add(new Paragraph("          " + sdf.format(new Date()), f));
            document.add(new Paragraph("--------------------------------------------", f));
            document.add(new Paragraph("..::  www.altessmann.com.br  ::..    ", f));
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        document.close();
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
