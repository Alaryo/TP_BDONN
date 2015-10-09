/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_bdonn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hicham DAHER et Victor ENAUD
 */
public class Requetes {

    public static int getChauffeurID(Connection con, String nom, String prenom) {
        try {
            String query = "SELECT Chauffeur_ID"
                    + " FROM Chauffeur WHERE Chauffeur_Prenom = ? AND Chauffeur_Nom = ?";

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, prenom);
            stmt.setString(2, nom);

            ResultSet res = stmt.executeQuery();
            res.next();
            return res.getInt("Chauffeur_ID");

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return -1;
        }

    }

    public static void callFlotte(Connection con, String immatriculation) {
        try {
            String query = "SELECT Camion.Immatriculation,Chauffeur.Chauffeur_Nom, Chauffeur.Chauffeur_Prenom"
                    + " FROM Camion NATURAL FULL OUTER JOIN Chauffeur WHERE camion.Immatriculation LIKE ? ORDER BY Chauffeur.Chauffeur_Prenom";

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, immatriculation);
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                if (res.getString("chauffeur_nom") == null) {
                    System.out.println(res.getString("immatriculation")
                            + " n'a pas de chauffeur attitré");
                } else {
                    System.out.println(res.getString("immatriculation") + " attitré à "
                            + res.getString("chauffeur_nom") + " " + res.getString("chauffeur_prenom"));
                }
            }
            System.out.println("");
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public static void callFlotteReserve(Connection con) {
        try {
            String query = "SELECT Immatriculation FROM Camion WHERE Chauffeur_ID IS NULL";

            PreparedStatement stmt = con.prepareStatement(query);
            //stmt.setInt(1, null);

            ResultSet res = stmt.executeQuery();

            System.out.println("Camions en réserve :");

            while (res.next()) {
                System.out.println(res.getString("immatriculation"));

            }
            stmt.close();

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        System.out.println("");

    }

    public static void callLivraison(Connection con, String immatriculation, String date_tournee) {
        try {
            String query = "SELECT Entreprise.Entreprise_Nom, Produit.Produit_Nom, Palette_Poids, Livraison.Livraison_Date, Entrepot.Entrepot_Adresse, Entrepot.Entrepot_Ville"
                    + "	FROM Livraison"
                    + "		INNER JOIN Commande ON (Commande.Commande_ID = Livraison.Commande_ID)"
                    + "		INNER JOIN Entreprise ON (Entreprise.Entreprise_ID = Commande.Entreprise_ID)"
                    + "		INNER JOIN Palette ON (Palette.Livraison_ID = Livraison.Livraison_ID)"
                    + "		INNER JOIN Produit ON (Produit.Produit_ID = Palette.Produit_ID)"
                    + "		INNER JOIN Entrepot ON (Entrepot.Entrepot_ID = Commande.Entrepot_ID)"
                    + "		INNER JOIN Ordre_Mission ON (Ordre_Mission.Ordre_ID = Livraison.Ordre_ID)"
                    + "		INNER JOIN Chauffeur ON (Chauffeur.Chauffeur_ID = Ordre_Mission.Chauffeur_ID)"
                    + "		INNER JOIN Conduit ON (Conduit.Chauffeur_ID = Chauffeur.Chauffeur_ID)"
                    + "               WHERE Conduit.Immatriculation LIKE ?"
                    + "			  AND date_trunc('day', livraison.livraison_date) = cast(? as timestamp)"
                    + "			  AND date_trunc('day', conduit.conduit_date_debut) = cast(? as timestamp)";

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, "AC-543-AG");
            stmt.setString(2, date_tournee);
            stmt.setString(3, date_tournee);

            ResultSet res = stmt.executeQuery();

            if (!res.isBeforeFirst()) {
                System.out.println("Le camion " + immatriculation + " n'a pas de livraison prévue le " + date_tournee);
            } else {
                while (res.next()) {
                    System.out.println("Le camion " + immatriculation + " a les livraisons suivantes prévues pour le " + date_tournee + " :");
                    System.out.println(res.getString("Entreprise_Nom") + " recevra "
                            + res.getString("Palette_Poids") + " kg de " + res.getString("Produit_nom")
                            + " le " + res.getString("Livraison_Date") + " livrés à "
                            + res.getString("Entrepot_Adresse") + " à " + res.getString("Entrepot_Ville"));
                }
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        System.out.println("");
    }

    public static void commandeNonPrevue(Connection con) {
        try {
            String query = "SELECT Entreprise.Entreprise_Nom, Produit.Produit_Nom, CommandeContient.Quantite, CommandeContient.Quantite_Unite, Commande.Commande_Date, Entrepot.Entrepot_Adresse,Entrepot.Entrepot_Ville"
                    + "               FROM Commande"
                    + "		NATURAL FULL OUTER JOIN Livraison"
                    + "		INNER JOIN Entreprise ON (Entreprise.Entreprise_ID = Commande.Entreprise_ID)"
                    + "		INNER JOIN CommandeContient ON (CommandeContient.Commande_ID = Commande.Commande_ID)"
                    + "		INNER JOIN Produit ON (Produit.Produit_ID = CommandeContient.Produit_ID)"
                    + "		INNER JOIN Entrepot ON (Entrepot.Entrepot_ID = Commande.Entrepot_ID)"
                    + "               WHERE Livraison_ID IS NULL;";

            PreparedStatement stmt = con.prepareStatement(query);

            ResultSet res = stmt.executeQuery();

            if (!res.isBeforeFirst()) {
                System.out.println("Toutes les commandes ont une livraison prévue");
            } else {
                while (res.next()) {
                    System.out.println("Les commandes suivantes n'ont pas de livraison prévue :");
                    System.out.println(res.getString("Entreprise_Nom") + " a commandé "
                            + res.getString("Quantite") + res.getString("Quantite_Unite") + " de " + res.getString("Produit_nom")
                            + " le " + res.getString("Commande_Date") + " à livrer au "
                            + res.getString("Entrepot_Adresse") + " à " + res.getString("Entrepot_Ville"));
                }
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        System.out.println("");
    }

    public static void commandeChauffeur(Connection con, String nom, String prenom, boolean livree) {
        try {
            String query = "SELECT Livraison.Livraison_ID, Livraison.Livraison_Date, Entreprise.Entreprise_Nom"
                    + "         FROM Chauffeur"
                    + "		INNER JOIN Ordre_Mission ON (Ordre_Mission.Chauffeur_ID = Chauffeur.Chauffeur_ID)"
                    + "		INNER JOIN Livraison ON (Livraison.Ordre_ID = Ordre_Mission.Ordre_ID)"
                    + "		INNER JOIN Commande ON (Commande.Commande_ID = Livraison.Commande_ID)"
                    + "		INNER JOIN Entreprise ON (Entreprise.Entreprise_ID = Commande.Entreprise_ID)"
                    + "               WHERE Livraison_Livree = ? AND Chauffeur_Nom = ? AND Chauffeur_Prenom = ?;";

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setBoolean(1, livree);
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            ResultSet res = stmt.executeQuery();

            if (!res.isBeforeFirst()) {
                System.out.println("Henry LEROC'H n'a effectué aucune livraison");
            } else {
                while (res.next()) {

                    System.out.println("Henry LEROC'H a effectué la livraison " + res.getString("Livraison_ID")
                            + " le " + res.getString("Livraison_Date") + " pour " + res.getString("Entreprise_nom"));
                }
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        System.out.println("");
    }

    public static void creationChauffeur(Connection con, String nom, String prenom) {
        try {
            String query = "INSERT INTO Chauffeur(Chauffeur_ID, Chauffeur_Nom, Chauffeur_Prenom)"
                    + " VALUES (nextval('seq_chauffeur'), ?, ?)";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, "DE RIV");
            stmt.setString(2, "Gerald");

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        System.out.println("");
    }

    public static void attributionCamion(Connection con, String nom, String prenom, String immatriculation) {
        try {
            int chauffeur_ID = Requetes.getChauffeurID(con, nom, prenom);

            String query = "UPDATE Camion SET Chauffeur_ID = ?"
                    + "WHERE Camion.Immatriculation = ? AND Camion.Chauffeur_ID IS NULL";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, chauffeur_ID);
            stmt.setString(2, immatriculation);

            stmt.executeUpdate();

            int res = stmt.getUpdateCount();

            if (res == 0) {
                System.err.println("Le camion semble être déjà attribué");
                callFlotte(con, immatriculation);
            } else {
                System.out.println(prenom + " " + nom + " a bien été attribué au camion " + immatriculation);
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        System.out.println("");
    }

    public static void commandesPartiellementLivrees(Connection con) {
        try {
            String query1 = "SELECT Produit.Produit_ID, Livraison.Livraison_ID, Entreprise.Entreprise_Nom, Produit.Produit_Nom, CommandeContient.Quantite, CommandeContient.Quantite_Unite, Commande.Commande_Date, Entrepot.Entrepot_Adresse,Entrepot.Entrepot_Ville"
                    + "               FROM Commande"
                    + "		NATURAL FULL OUTER JOIN Livraison"
                    + "		INNER JOIN Entreprise ON (Entreprise.Entreprise_ID = Commande.Entreprise_ID)"
                    + "		INNER JOIN CommandeContient ON (CommandeContient.Commande_ID = Commande.Commande_ID)"
                    + "		INNER JOIN Produit ON (Produit.Produit_ID = CommandeContient.Produit_ID)"
                    + "		INNER JOIN Entrepot ON (Entrepot.Entrepot_ID = Commande.Entrepot_ID)";

            String query2 = "SELECT Palette.Livraison_ID, Palette.Produit_ID, sum(Palette.Palette_Poids)"
                    + "         FROM Palette GROUP BY Palette.Livraison_ID, Palette.Produit_ID";

            PreparedStatement stmt1 = con.prepareStatement(query1);
            PreparedStatement stmt2 = con.prepareStatement(query2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet res1 = stmt1.executeQuery();
            ResultSet res2 = stmt2.executeQuery();

            while (res1.next()) {
                boolean egal = true;

//                System.out.println("Test 0");
//                System.out.println(res1.getInt("Quantite"));
                while (res2.next()) {
//                    System.out.println("Test 0a");
//                    System.out.println(res1.getInt("Quantite"));
//                    System.out.println("Test 0b");
//                    System.out.println(res2.getInt("sum"));
                    if (res1.getInt("Livraison_ID") == res2.getInt("Livraison_ID")) {
//                        System.out.println("Test 1");
//                        System.out.println(res2.getInt("sum"));
                        if (res1.getInt("Produit_ID") == res2.getInt("Produit_ID")) {
//                            System.out.println("Test 2");
                            if (res1.getInt("Quantite") != res2.getInt("sum")) {
                                System.out.println(res1.getString("Entreprise_Nom") + " a commandé "
                                        + res1.getString("Quantite") + res1.getString("Quantite_Unite") + " de " + res1.getString("Produit_nom")
                                        + " le " + res1.getString("Commande_Date") + " et n'a été livré que de " + res2.getInt("sum") + " kg");

//                                System.out.println("Test 3");
//                                System.out.println(res1.getInt("Quantite"));
//                                System.out.println(res2.getInt("sum"));
                            }
                        }
                    }
                }
                res2.beforeFirst();
            }

//            if (!res.isBeforeFirst()) {
//                System.out.println("Toutes les commandes ont une livraison prévue");
//            } else {
//                while (res.next()) {
//                    System.out.println("Les commandes suivantes n'ont pas de livraison prévue :");
//                    System.out.println(res.getString("Entreprise_Nom") + " a commandé "
//                            + res.getString("Quantite") + res.getString("Quantite_Unite") + " de " + res.getString("Produit_nom")
//                            + " le " + res.getString("Commande_Date") + " à livrer au "
//                            + res.getString("Entrepot_Adresse") + " à " + res.getString("Entrepot_Ville"));
//                }
//            }
            stmt1.close();
            stmt2.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        System.out.println("");
    }
}
