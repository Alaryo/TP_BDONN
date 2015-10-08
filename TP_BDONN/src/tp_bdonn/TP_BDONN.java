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
import java.util.logging.Level;
import java.util.logging.Logger;

public class TP_BDONN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");

            // String dbURL = "jdbc:postgresql://localhost:5432/TP_BDONN";
            // String user = "postgres";
            // String password = "deadlands";
            String dbURL = "jdbc:postgresql://localhost:5432/TP_BDONN";
            String user = "postgres";
            String password = "BDONN01";

            Connection con = DriverManager.getConnection(dbURL, user, password);

            String query = "SELECT * FROM entreprise WHERE entreprise_nom LIKE ?";
            // String query = "SELECT * FROM entreprise WHERE entreprise_nom LIKE '%'";
            //String query1 = "SELECT Camion.Immatriculation,Chauffeur.Chauffeur_Nom, Chauffeur.Chauffeur_Prenom"
            //        + "     FROM Camion NATURAL FULL OUTER JOIN Chauffeur";
            
            PreparedStatement stmt = con.prepareStatement(query);
            //Statement stmt = con.createStatement();
            
            stmt.setString(1, "%");

            ResultSet res = stmt.executeQuery();
            //ResultSet res = stmt.executeQuery(query1);

            while (res.next()) {
                  System.out.println("Info :" + res.getString("entreprise_nom"));
            //    System.out.println("Info : " + res.getString("immatriculation") + " affilié à "
            //            + res.getString("chauffeur_nom") + " " + res.getString("chauffeur_prenom"));
            }
            
            stmt.close();
            con.close();
            
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " + e.getMessage());
        } catch(SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

}
