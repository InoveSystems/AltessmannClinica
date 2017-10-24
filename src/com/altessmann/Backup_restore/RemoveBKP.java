/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.Backup_restore;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorge
 */
public class RemoveBKP {   
  List<String> lista = new ArrayList<String>(9);  
   public RemoveBKP(){   
                    String dir = "C:/Altessmann/BackupSistema/";  

                    File diretorio = new File(dir);
                    File fList[] = diretorio.listFiles();

                        if(fList.length != 0 && fList.length > 8 ){
                                //JOptionPane.showMessageDialog(null,"Numero de arquivos no diretorio : " + fList.length );

                                for ( int i = 0; i < fList.length; i++ ){
                                //JOptionPane.showMessageDialog(null,fList[i].getName());
                                lista.add(fList[i].getName());
                                }

                                //lista.remove(Collections.min(lista)); //remove o menor da array 
                                //lista.remove(Collections.max(lista)); //remove o maior da array 
                                String x = Collections.min(lista);
                                lista.remove(Collections.min(lista)); //remove o menor da array 

                                String nome = "C:/Altessmann/BackupSistema/"+x;  
                                File f = new File(nome);  
                                f.delete();
                                //JOptionPane.showMessageDialog(null,"removida "+lista);
                                //char inicial = 0;
                                //for ( int i = 0; i < lista.size(); i++ ){
                                //inicial = lista.get(i).charAt(0); //pega o primeiro número da array
                                //lista.get(i)
                                //JOptionPane.showMessageDialog(null,inicial);
                                //}
                               int m = 0;  
                               String recebe; 
                               String result;
                                while (m < lista.size())
                                {
                                //recebe=JOptionPane.showInputDialog("Digite uma frase: ");    
                                  recebe = lista.get(m); 

                                    result = recebe.replace(" ", " ").substring(1); //remove o primeiro número da string recebe
                                //String result=recebe.replace(" ", "-").substring(1, recebe.length()-1);

                                m++;
                                result = m + result;
                                // Arquivo ou diretório com nome antigo
                                File file = new File("C:/Altessmann/BackupSistema/"+recebe);
                                //// Arquivo ou diretório com novo nome
                                File file2 = new File("C:/Altessmann/BackupSistema/"+result);
                                // Renomeando arquivo ou diretório
                                boolean success = file.renameTo(file2);
                                if (!success) {
                                    JOptionPane.showMessageDialog(null,"Falha no momento de renomear");
                                }

                                }  
                    
                    
  /*
    String y = Collections.min(lista);
                    //JOptionPane.showMessageDialog(null,"y "+y.charAt(0));
                    String recebe;    
            
        //recebe=JOptionPane.showInputDialog("Digite uma frase: ");    
          recebe = y;  
        
        String result = recebe.replace(" ", " ").substring(1); 
        //String result=recebe.replace(" ", "-").substring(1, recebe.length()-1);
            
        JOptionPane.showMessageDialog(null,"Resultado "+result);
        result = 1+result;
                    JOptionPane.showMessageDialog(null,"Resultado concatenado "+result);
*/

            }        
                
   }   
      
//      public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new RemoveBKP();
//            }
//        });
//    }
      
    public void removerArquivos(File f) {
        // Se o arquivo passado for um diretório
        if (f.isDirectory()) {
                /* Lista todos os arquivos do diretório em um array 
                   de objetos File */
                File[] files = f.listFiles();
                // Identa a lista (foreach) e deleta um por um
                for (File file : files) {
                        file.delete();
                }
        }
    }
    
    public void removerTodosArquivos(File f) {
     if (f.isDirectory()) {
         File[] files = f.listFiles();
         for (File file : files) {
             removerArquivos(file);
         }
     }
     f.delete();
  }
}

