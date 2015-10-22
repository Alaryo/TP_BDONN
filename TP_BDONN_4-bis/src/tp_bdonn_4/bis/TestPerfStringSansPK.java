/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_bdonn_4.bis;

import java.sql.*;
import java.util.Random;

/**
 *
 * @author Hicham DAHER et Victor ENAUD
 */
public class TestPerfStringSansPK {

    public static void sansClefCrea(Connection con) {
        long debutRequete = System.nanoTime();

        try {
            String query = "INSERT INTO string_sans_pk(string) VALUES(?)";

            PreparedStatement stmt = con.prepareStatement(query);
            Random generateurAleatoire = new Random();

            for (int i = 1; i <= 10000; i++) {

                //int entier = generateurAleatoire.nextInt(1000);
                String string = "String" + i;

                stmt.setString(1, string);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

        long finRequete = System.nanoTime();

        System.out.println("Le temps de création de 10 000 string "
                + "aléatoires dans une table sans PK est :" + (finRequete - debutRequete));
        System.out.println("");
    }

    public static void sansClefSelectSimple(Connection con) {
        long debutRequete = System.nanoTime();

        try {
            String query = "SELECT COUNT(string) FROM string_sans_pk";

            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet res = stmt.executeQuery();

            if (res.last()) {
                long rowcount = res.getRow();
                System.out.println("Le nombre de tuples retournés est : " + rowcount);
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

        long finRequete = System.nanoTime();

        System.out.println("Le temps de SELECT(*) de String dans une table sans PK "
                + "est :" + (finRequete - debutRequete));
        System.out.println("");
    }

    public static void sansClefSelectCondition(Connection con) {
        long debutRequete = System.nanoTime();

        try {
            String query = "SELECT COUNT(string) FROM string_sans_pk WHERE string LIKE '1100'";

            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet res = stmt.executeQuery();

            if (res.last()) {
                long rowcount = res.getRow();
                System.out.println("Le nombre de tuples retournés est : " + rowcount);
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

        long finRequete = System.nanoTime();

        System.out.println("Le temps de SELECT les strings terminant par 100"
                + "dans une table sans PK est :" + (finRequete - debutRequete));
        System.out.println("");
    }

    public static void sansClefSelectOrdre(Connection con) {
        long debutRequete = System.nanoTime();

        try {
            String query = "SELECT string FROM string_sans_pk WHERE string LIKE '%100' ORDER BY string";

            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet res = stmt.executeQuery();

            if (res.last()) {
                long rowcount = res.getRow();
                System.out.println("Le nombre de tuples retournés est : " + rowcount);
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

        long finRequete = System.nanoTime();

        System.out.println("Le temps de SELECT des strings en ordonnant par ordre croissant "
                + "dans une table sans PK est :" + (finRequete - debutRequete));
        System.out.println("");
        System.out.println("");
    }
}
