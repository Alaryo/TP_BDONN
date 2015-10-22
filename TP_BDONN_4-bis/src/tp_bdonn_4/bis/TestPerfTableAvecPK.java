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
public class TestPerfTableAvecPK {

    public static void sansClefCrea(Connection con) {
        long debutRequete = System.nanoTime();

        try {
            String query = "INSERT INTO table100_avec_pk(entier, string) VALUES(?, ?)";

            PreparedStatement stmt = con.prepareStatement(query);
            Random generateurAleatoire = new Random();

            for (int i = 1; i <= 100; i++) {

                String string = new String();
                
                int entier = generateurAleatoire.nextInt(10);
                switch(entier){
                    case 0:
                        string = "aaaaaaaaaa";
                        break;
                    
                    case 1:
                        string = "bbbbbbbbbb";
                        break;
                    
                    case 2:
                        string = "cccccccccc";
                        break;
                    
                    case 3:
                        string = "dddddddddd";
                        break;
                    
                    case 4:
                        string = "eeeeeeeeee";
                        break;
                    
                    case 5:
                        string = "ffffffffff";
                        break;
                    
                    case 6:
                        string = "gggggggggg";
                        break;
                    
                    case 7:
                        string = "hhhhhhhhhh";
                        break;
                    
                    case 8:
                        string = "iiiiiiiiii";
                        break;
                    
                    case 9:
                        string = "kkkkkkkkkk";
                        break;
                        
                }

                stmt.setInt(1, i);
                stmt.setString(2, string);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        
        try {
            String query = "INSERT INTO table10000_avec_pk(entier, string) VALUES(?, ?)";

            PreparedStatement stmt = con.prepareStatement(query);
            Random generateurAleatoire = new Random();

            for (int i = 1; i <= 10000; i++) {

                String string = new String();
                
                int entier = generateurAleatoire.nextInt(10);
                switch(entier){
                    case 0:
                        string = "aaaaaaaaaa";
                        break;
                    
                    case 1:
                        string = "bbbbbbbbbb";
                        break;
                    
                    case 2:
                        string = "cccccccccc";
                        break;
                    
                    case 3:
                        string = "dddddddddd";
                        break;
                    
                    case 4:
                        string = "eeeeeeeeee";
                        break;
                    
                    case 5:
                        string = "ffffffffff";
                        break;
                    
                    case 6:
                        string = "gggggggggg";
                        break;
                    
                    case 7:
                        string = "hhhhhhhhhh";
                        break;
                    
                    case 8:
                        string = "iiiiiiiiii";
                        break;
                    
                    case 9:
                        string = "kkkkkkkkkk";
                        break;
                        
                }

                stmt.setInt(1, i);
                stmt.setString(2, string);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

        long finRequete = System.nanoTime();

        System.out.println("Le temps de création de 100 et 10 000 entrées dans "
                + "chaque table sans PK est : " + (finRequete - debutRequete));
        System.out.println("");
    }

    public static void sansClefSelectSimple(Connection con) {
        long debutRequete = System.nanoTime();

        try {
            String query = "SELECT COUNT(table10000_avec_pk.entier) "
                    + "FROM table10000_avec_pk "
                    + "INNER JOIN table100_avec_pk ON (table100_avec_pk.string = table10000_avec_pk.string)";

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

        System.out.println("Le temps de SELECT(*) de String dans un inner join "
                + "de tables sans PK est :" + (finRequete - debutRequete));
        System.out.println("");
    }

    public static void sansClefSelectCondition(Connection con) {
        long debutRequete = System.nanoTime();

        try {
            String query = "SELECT COUNT(table10000_avec_pk.entier) "
                    + "FROM table10000_avec_pk "
                    + "INNER JOIN table100_avec_pk ON (table100_avec_pk.string = table10000_avec_pk.string) "
                    + "WHERE table10000_avec_pk.entier = 20";

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

        System.out.println("Le temps de SELECT les strings terminant par a dans "
                + "un inner join de tables sans PK : " + (finRequete - debutRequete));
        System.out.println("");
    }

    public static void sansClefSelectOrdre(Connection con) {
        long debutRequete = System.nanoTime();

        try {
            String query = "SELECT table10000_avec_pk.entier "
                    + "FROM table10000_avec_pk "
                    + "INNER JOIN table100_avec_pk ON (table100_avec_pk.string = table10000_avec_pk.string) "
                    + "WHERE table10000_avec_pk.entier = 20 ORDER BY table10000_avec_pk.entier";

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
                + "dans un inner join de tables sans PK est :" + (finRequete - debutRequete));
        System.out.println("");
        System.out.println("");
    }
}
