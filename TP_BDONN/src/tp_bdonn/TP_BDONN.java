/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_bdonn;

/**
 *
 * @author victo
 */
import java.sql.*;

public class TP_BDONN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");

            String dbURL = "jdbc:postgresql://localhost:5432/TP_BDONN";
            String user = "postgres";
            String password = "BDONN01";

            Connection con = DriverManager.getConnection(dbURL, user, password);
            
            // Requête A
            FlotteCamion.callFlotte(con);
            
            // Requête B
            
            
            // Requête C
            
            
            // Requête D
            
            
            // Requête E
            
            
            // Requête F
            
            
            // Requête G

            con.close();
            
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " + e.getMessage());
        } catch(SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

}
