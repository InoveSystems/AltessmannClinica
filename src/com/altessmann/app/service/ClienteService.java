/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.app.service;

import Logs.LogsExceptions;
import com.altessmann.ConnecionFactory.ConnectionFactory;
import com.altessmann.app.bean.ChatMessage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ritiele Aldeburg
 */
public class ClienteService {

    private Socket socket;
    private ObjectOutputStream output;
    private Logs.LogsExceptions exception = new LogsExceptions();

    public Socket connect() {
        try {

            Scanner scanner = null;
            try {
                scanner = new Scanner(new FileReader("ip.ini")).useDelimiter(";");

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo 'ip.ini' por favor, reeinstale o programa (seus dados não serão perdidos) ou entre em contanto com os programadores.", "Erro ao ler arquivo", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ip = null;
            String servidor = null;
            String user = null;
            String password = null;
            while (scanner.hasNext()) {
                ip = scanner.next();
                servidor = scanner.next();
                user = scanner.next();
                password = scanner.next();
            }

            this.socket = new Socket(ip, 5555);
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (UnknownHostException ex) {
            exception.ExceptionsTratamento(2);
            System.exit(0);
        } catch (IOException ex) {
            exception.ExceptionsTratamento(2);
        }

        return socket;
    }

    public void send(ChatMessage message) {
        try {
            output.writeObject(message); //envia msg
        } catch (IOException ex) {
            // Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {

        }

    }
}
