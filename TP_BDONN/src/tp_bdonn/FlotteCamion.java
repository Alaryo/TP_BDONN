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

    public static void callFlotte(Connection con) {
        try {
            String query = "SELECT Camion.Immatriculation,Chauffeur.Chauffeur_Nom, Chauffeur.Chauffeur_Prenom"
                    + " FROM Camion NATURAL FULL OUTER JOIN Chauffeur WHERE camion.Immatriculation LIKE ? ORDER BY Chauffeur.Chauffeur_Prenom";

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, "%");
            ResultSet res = stmt.executeQuery();

            System.out.println("Flotte de camions de l'entreprise :");

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
}
