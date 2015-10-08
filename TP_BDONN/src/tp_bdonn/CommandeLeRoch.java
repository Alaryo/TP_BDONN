/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_bdonn;

import java.sql.*;

/**
 *
 * @author Hicham
 */
public class CommandeLeRoch {

    public static void callLeRoch(Connection con) {
        try {
            String query = "SELECT Livraison.Livraison_ID, Livraison.Livraison_Date, Entreprise.Entreprise_Nom"
                    + "         FROM Chauffeur"
                    + "		INNER JOIN Ordre_Mission ON (Ordre_Mission.Chauffeur_ID = Chauffeur.Chauffeur_ID)"
                    + "		INNER JOIN Livraison ON (Livraison.Ordre_ID = Ordre_Mission.Ordre_ID)"
                    + "		INNER JOIN Commande ON (Commande.Commande_ID = Livraison.Commande_ID)"
                    + "		INNER JOIN Entreprise ON (Entreprise.Entreprise_ID = Commande.Entreprise_ID)"
                    + "               WHERE Livraison_Livree = TRUE AND Chauffeur_Nom LIKE ? AND Chauffeur_Prenom LIKE ?;";

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, "LE ROC'H");
            stmt.setString(2, "Henry");
            ResultSet res = stmt.executeQuery();
            
            if (res == null){
                System.out.println("Info : Henry LEROC'H n'a effectué aucune livraison");
            }

            while (res.next()) {

                System.out.println("Info : " + " Henry LEROC'H a effectué la livraison " + res.getString("Livraison_ID") 
                        + " le " + res.getString("Livraison_Date") + " pour " + res.getString("Entreprise_nom"));
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        System.out.println("");
    }

}
