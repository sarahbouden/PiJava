package controllers;

import entities.Hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

   /* public class RatingDAOController {

        private final String url = "jdbc:mysql://localhost:3306/tuniwonders";
        private final String user = "root";
        private final String password = "";

        public void mettreAJourNote(int id, int nouvelleNote) {
            String query = "UPDATE hotel SET rating = ? WHERE id = ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, nouvelleNote);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/
   public class RatingDAOController {
       private Hotel hotel;

       private final String url = "jdbc:mysql://localhost:3306/tuniwonders";
       private final String user = "root";
       private final String password = "";

       public void mettreAJourNote(int id, int rating) {
           String query = "UPDATE hotel SET rating = ? WHERE id = ?";
           try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
               pstmt.setInt(1, rating);
               pstmt.setInt(2, id);

               System.out.println("ID de l'hôtel : " + id);
               System.out.println("Nouvelle note : " + rating);

               int rowsUpdated = pstmt.executeUpdate();
               if (rowsUpdated > 0) {
                   System.out.println("La note a été mise à jour avec succès !");
               } else {
                   System.out.println("Aucune mise à jour effectuée. Vérifiez l'ID de l'hôtel.");
               }
           } catch (SQLException e) {
               System.err.println("Erreur lors de la mise à jour de la note : " + e.getMessage());
           }
       }

   }


