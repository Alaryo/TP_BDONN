/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_bdonn_4.bis;

/**
 *
 * @author Hicham DAHER et Victor ENAUD
 */
import java.sql.*;

public class TP_BDONN_4Bis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");

            String dbURL = "jdbc:postgresql://localhost:5432/TP_BDONN_4bis";
            String user = "postgres";
            String password = "BDONN01";

            Connection con = DriverManager.getConnection(dbURL, user, password);

            // Table d'entier sans clef primaire
            TestPerfEntierSansPK.sansClefCrea(con);

            TestPerfEntierSansPK.sansClefSelectSimple(con);

            TestPerfEntierSansPK.sansClefSelectCondition(con);

            TestPerfEntierSansPK.sansClefSelectOrdre(con);

            // Table d'entier avec clef primaire
            TestPerfEntierAvecPK.sansClefCrea(con);

            TestPerfEntierAvecPK.sansClefSelectSimple(con);

            TestPerfEntierAvecPK.sansClefSelectCondition(con);

            TestPerfEntierAvecPK.sansClefSelectOrdre(con);

            // Table de string sans clef primaire
            TestPerfStringSansPK.sansClefCrea(con);

            TestPerfStringSansPK.sansClefSelectSimple(con);

            TestPerfStringSansPK.sansClefSelectCondition(con);

            TestPerfStringSansPK.sansClefSelectOrdre(con);

            // Table de string avec clef primaire
            TestPerfStringAvecPK.sansClefCrea(con);

            TestPerfStringAvecPK.sansClefSelectSimple(con);

            TestPerfStringAvecPK.sansClefSelectCondition(con);

            TestPerfStringAvecPK.sansClefSelectOrdre(con);
            
            // Table de tables sans clef primaire
            TestPerfTableSansPK.sansClefCrea(con);

            TestPerfTableSansPK.sansClefSelectSimple(con);

            TestPerfTableSansPK.sansClefSelectCondition(con);

            TestPerfTableSansPK.sansClefSelectOrdre(con);

            // Table de tables avec clef primaire
            TestPerfTableAvecPK.sansClefCrea(con);

            TestPerfTableAvecPK.sansClefSelectSimple(con);

            TestPerfTableAvecPK.sansClefSelectCondition(con);

            TestPerfTableAvecPK.sansClefSelectOrdre(con);

            con.close();

        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {

        }
    }

}
