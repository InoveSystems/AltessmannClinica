package com.altessmann.ConnecionFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class ConnectionFactory {

    public static Connection con;
    public static Statement s;
    public static ResultSet rs;

    public static Connection openConnection() throws SQLException {

        //Se não houver conexão, retorna uma nova instanciada
        if (con == null) {
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
            String password=null;
            while (scanner.hasNext()) {
                ip = scanner.next();
                servidor = scanner.next();
                user=scanner.next();
                password=scanner.next();
            }

            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("asdnasudsad");
            }
            
//            try {
                
                //con = DriverManager.getConnection("jdbc:postgresql://" + ip + "/" + servidor + "", "altessmann", "ibanez010102");
                con = DriverManager.getConnection("jdbc:postgresql://" + ip + "/" + servidor, user, password);
                //con = DriverManager.getConnection("jdbc:postgresql://altessmann.postgresql.dbaas.com.br/altessmann", "altessmann", "ibanez010102");
                //con=DriverManager.getConnection("jdbc:postgresql://ec2-23-21-80-230.compute-1.amazonaws.com:5432/d9733v92ihfd7f?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory","ebakxporaqglgj","4da8fc7f12c120d69b0ce4887b53ce3ef5664c8af483ec6828eafcb32301d88f");
                //con = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "12345678+");
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados!", "", JOptionPane.ERROR_MESSAGE);
//                Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }

        return con;

    }

    public static void closeConnection(Connection con, Statement ps, ResultSet rs) throws SQLException {
        if (con != null) {
            con.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (rs != null) {
            rs.close();
        }
    }

    public static void closeConnection(Connection con, Statement ps) throws SQLException {
        if (con != null) {
            con.close();
        }
        if (ps != null) {
            ps.close();
        }
    }

    public static void closeConnection(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}
