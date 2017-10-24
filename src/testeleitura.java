/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ritiele Aldeburg
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class testeleitura {

	public static void main(String args[]) {
            int cont=0,cont1=0;
		System.out.println("Inicio");
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		File file = new File("Producao.pdf");
		try {
			PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
                        //Começa a leitura do arquivo a partir da página informada
                        // neste exemplo é a página "1"
			pdfStripper.setStartPage(1);

			pdfStripper.setEndPage(pdfStripper.getEndPage());
			String parsedText = pdfStripper.getText(pdDoc);

			Scanner s = new Scanner(parsedText);
			s.useDelimiter(" ");

			String linha = "";
			while (s.hasNext()) {
                            //linha = s.next();
                            if(linha.equals("120,00")){	
                                cont=cont+1;
				System.out.println("120: "+cont);                                
                            }
                            if(linha.equals("77,00")){	
                                cont1=cont1+1;
				System.out.println("77: "+cont1);                                
                            }
                                 linha = s.next();
				//linha = s.next();				
				//System.out.println(linha);				
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Fim");
                System.out.println(cont1*77+cont*120);
	}
}