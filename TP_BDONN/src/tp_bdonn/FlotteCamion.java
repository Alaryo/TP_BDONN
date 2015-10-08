/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_bdonn;

import java.sql.*;

/**
 *
 * @author victo
 */
public class FlotteCamion {

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
            int chauffeur_ID = FlotteCamion.getChauffeurID(con, nom, prenom);

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
            } else{
                System.out.println(prenom + " " + nom + " a bien été attribué au camion " + immatriculation);
            }
            

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        System.out.println("");
    }
}
