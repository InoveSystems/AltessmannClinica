package com.altessmann.Impressao;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;
import java.io.File;
import javax.print.PrintService;
import javax.swing.filechooser.FileSystemView;

public class Imprimir {

    private PrinterJob pjob = null;
    static String imp = "AltessmannImp";
    private PrintService impressora = null;

    public void main(String[] args) throws IOException, PrinterException {
        // imp = "3DSTimp";
//        String diretorioUsuario = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
//        System.out.println("teste"+diretorioUsuario);
//        FileInputStream fis = new FileInputStream(diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Config" + File.separator + "SENHAS.pdf");
//        Imprimir printPDFFile = new Imprimir(fis, "SENHAS.pdf");
//        printPDFFile.print();
    }

    public Imprimir(InputStream inputStream, String jobName) throws IOException, PrinterException {
        byte[] pdfContent = new byte[inputStream.available()];
        inputStream.read(pdfContent, 0, inputStream.available());
        initialize(pdfContent, jobName);
    }

    public Imprimir(byte[] content, String jobName) throws IOException, PrinterException {
        initialize(content, jobName);
    }

    private void initialize(byte[] pdfContent, String jobName) throws IOException, PrinterException {
        ByteBuffer bb = ByteBuffer.wrap(pdfContent);
        PDFFile pdfFile = new PDFFile(bb);
        PDFPrintPage pages = new PDFPrintPage(pdfFile);
        PrintService[] pservices = PrinterJob.lookupPrintServices();
        //System.out.println(pservices.length);
        if (pservices.length > 0) {
            for (PrintService ps : pservices) {
                System.out.println("Impressora Encontrada: " + ps.getName());
                if (ps.getName().contains(imp)) {
                    System.out.println("Impressora Selecionada: " + ps.getName());
                    impressora = ps;
                    break;
                }
            }
        }
        if (impressora != null) {
            pjob = PrinterJob.getPrinterJob();
            pjob.setPrintService(impressora);
            PageFormat pf = PrinterJob.getPrinterJob().defaultPage();
            pjob.setJobName(jobName);
            Book book = new Book();
            book.append(pages, pf, pdfFile.getNumPages());
            pjob.setPageable(book);
            Paper paper = new Paper();
            paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
            pf.setPaper(paper);
        }
    }

    public void print() throws PrinterException {
        if (impressora != null) {
            System.out.println("IMPRIMINDO....");
            pjob.print();
            System.out.println("IMPRESSÃO FINALIZADA!");
        }

    }
}

class PDFPrintPage implements Printable {

    private PDFFile file;

    PDFPrintPage(PDFFile file) {
        this.file = file;
    }

    @Override
    public int print(Graphics g, PageFormat format, int index) throws PrinterException {
        int pagenum = index + 1;
        if ((pagenum >= 1) && (pagenum <= file.getNumPages())) {
            Graphics2D g2 = (Graphics2D) g;
            PDFPage page = file.getPage(pagenum);
            Rectangle imageArea = new Rectangle((int) format.getImageableX(), (int) format.getImageableY(),
                    (int) format.getImageableWidth(), (int) format.getImageableHeight());
            g2.translate(0, 0);
            PDFRenderer pgs = new PDFRenderer(page, g2, imageArea, null, null);
            try {
                page.waitForFinish();
                pgs.run();
            } catch (InterruptedException ie) {
                System.out.println(ie.toString());
            }
            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }
}
