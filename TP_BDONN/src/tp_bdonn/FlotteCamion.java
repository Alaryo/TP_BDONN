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
                    + " FROM Camion NATURAL FULL OUTER JOIN Chauffeur WHERE chauffeur_nom LIKE ?";

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, "%");
            ResultSet res = stmt.executeQuery();

            while (res.next()) {

                System.out.println("Info : " + res.getString("immatriculation") + " affilié à "
                        + res.getString("chauffeur_nom") + " " + res.getString("chauffeur_prenom"));
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }
}
