/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_bdonn;

/**
 *
 * @author Hicham DAHER et Victor ENAUD
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
            System.out.println("Flotte de camions de l'entreprise :");
            Requetes.callFlotte(con, "%");
            
            // Requête B
            Requetes.callLivraison(con, "AC-543-AG", "2015-11-15");
            
            // Requête C
            Requetes.callFlotteReserve(con);
            
            // Requête D            
            Requetes.commandeNonPrevue(con);
            
            // Requête E
            Requetes.commandesPartiellementLivrees(con);
            
            // Requête F
            Requetes.commandeChauffeur(con, "LEROC'H", "Henry", true);
            
            // Requête G
            //Requetes.creationChauffeur(con, "DE RIV", "Gerald");
            //Requetes.attributionCamion(con, "DE RIV", "Gerald", "AM-654-TU");

            con.close();
            
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " + e.getMessage());
        } catch(SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

}
