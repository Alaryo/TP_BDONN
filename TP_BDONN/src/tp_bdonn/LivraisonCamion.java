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
public class LivraisonCamion {
        public static void callLivraison(Connection con) {
        try {
            String query = "SELECT Entreprise.Entreprise_Nom, Produit.Produit_Nom, Palette_Poids, Livraison.Livraison_Date, Entrepot.Entrepot_Adresse, Entrepot.Entrepot_Ville" +
"	FROM Livraison" +
"		INNER JOIN Commande ON (Commande.Commande_ID = Livraison.Commande_ID)" +
"		INNER JOIN Entreprise ON (Entreprise.Entreprise_ID = Commande.Entreprise_ID)" +
"		INNER JOIN Palette ON (Palette.Livraison_ID = Livraison.Livraison_ID)" +
"		INNER JOIN Produit ON (Produit.Produit_ID = Palette.Produit_ID)" +
"		INNER JOIN Entrepot ON (Entrepot.Entrepot_ID = Commande.Entrepot_ID)" +
"		INNER JOIN Ordre_Mission ON (Ordre_Mission.Ordre_ID = Livraison.Ordre_ID)" +
"		INNER JOIN Chauffeur ON (Chauffeur.Chauffeur_ID = Ordre_Mission.Chauffeur_ID)" +
"		INNER JOIN Conduit ON (Conduit.Chauffeur_ID = Chauffeur.Chauffeur_ID)" +
"               WHERE Conduit.Immatriculation LIKE ?" +
"			  AND date_trunc('day', livraison.livraison_date) = '2015-11-15'" +
"			  AND date_trunc('day', conduit.conduit_date_debut) = '2015-11-15';";

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, "AC-543-AG");
            ResultSet res = stmt.executeQuery();

            while (res.next()) {

                System.out.println("Info : " + res.getString("Entreprise_Nom") + " recevra "
                        + res.getString("Palette_Poids") + " kg de " + res.getString("Produit_nom")
                        + " le " + res.getString("Livraison_Date") + " livrés à "
                        + res.getString("Entrepot_Adresse") + " à " + res.getString("Entrepot_Ville"));
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        System.out.println("");
    }
    
}
