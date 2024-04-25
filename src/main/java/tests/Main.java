package tests;

import entities.Hotel;
import entities.Reservation;
import services.ServiceHotel;
import services.ServiceReservation;
import utils.MyDataBase;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        MyDataBase.getInstance();
        Hotel c =new Hotel("Palmbeach", "hame","haloul","photo");
        ServiceHotel SH= new ServiceHotel();

    /* try {
            SH.ajouter(c);
        }catch (SQLException e){
            System.out.println("Erreur"+e.getMessage());
        }
   /*  try {  Hotel h = new Hotel();
            h.setId(26);
            SH.supprimer(h);
            System.out.println("Hotel supprimé : " + c);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'hotel' : " + e.getMessage());
        }
       /* c.setId(26);
        c.setDescription("mbh");
       try {
            SH.modifier(c);
            System.out.println("Hotel modifié : " + c);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }*/

        ///// RESERVATION
        LocalDate date1 = LocalDate.of(2024, 2, 1);
        LocalDate date2 = LocalDate.of(2024, 3, 1);
// Supprimer la conversion de java.util.Date vers java.sql.Date
        Reservation r = new Reservation(date1, date2, 8, "Single");

     ServiceReservation SR = new ServiceReservation();
        // Ajouter une réservation
 /*  try {
                SR.ajouter(r);
          System.out.println("Reservation ajoutée : " + r);

      }catch (SQLException e){
                System.out.println("Erreur"+e.getMessage());
        }

   /*    try {
           Reservation v = new Reservation();
            v.setId(26);
            SR.supprimer(v);
            System.out.println("Reservation supprimé : " + v);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la reservation' : " + e.getMessage());
        }*/



       try {
           r.setId(102);
            SR.modifier(r);
            System.out.println("Reservation modifié : " + r);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
        // Afficher toutes les réservations
     /* try {
            System.out.println("Liste des réservations : ");
                System.out.println(SR.afficher());

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des réservations : " + e.getMessage());
        }*/
    }
}
