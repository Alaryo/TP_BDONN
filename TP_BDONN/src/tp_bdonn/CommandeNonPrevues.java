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
public class CommandeNonPrevues {

    public static void callCommandeNonPrevue(Connection con) {
        try {
            String query = "SELECT Entreprise.Entreprise_Nom, Produit.Produit_Nom, CommandeContient.Quantite, CommandeContient.Quantite_Unite, Commande.Commande_Date, Entrepot.Entrepot_Adresse,Entrepot.Entrepot_Ville" +
"               FROM Commande" +
"		NATURAL FULL OUTER JOIN Livraison" +
"		INNER JOIN Entreprise ON (Entreprise.Entreprise_ID = Commande.Entreprise_ID)" +
"		INNER JOIN CommandeContient ON (CommandeContient.Commande_ID = Commande.Commande_ID)" +
"		INNER JOIN Produit ON (Produit.Produit_ID = CommandeContient.Produit_ID)" +
"		INNER JOIN Entrepot ON (Entrepot.Entrepot_ID = Commande.Entrepot_ID)" +
"               WHERE Livraison_ID IS NULL;";

            PreparedStatement stmt = con.prepareStatement(query);

            //stmt.setString(1, "%");
            ResultSet res = stmt.executeQuery();

            while (res.next()) {

                System.out.println("Info : " + res.getString("Entreprise_Nom") + " a commandé "
                        + res.getString("Quantite") + res.getString("Quantite_Unite") + " de " + res.getString("Produit_nom")
                        + " le " + res.getString("Commande_Date") + " à livrer au "
                        + res.getString("Entrepot_Adresse") + " à " + res.getString("Entrepot_Ville"));
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        System.out.println("");
    }

}
