package tests;

import entities.Activite;
import entities.Challenge;
import services.ServiceActivite;
import services.ServiceChallenge;
import utils.MyDataBase;

import java.util.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
       Challenge challenge = new Challenge(1, "Sahra", new Date(), new Date(), "evenement");
        Challenge challenge1 = new Challenge("Sahra", new Date(), new Date(), "Randonnée", null, null);

        ServiceChallenge serviceChallenge = new ServiceChallenge();
        try {
            serviceChallenge.ajouter(challenge1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
      /*  try {

            serviceChallenge.modifier(challenge);
            System.out.println("Challenge modifié avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(serviceChallenge.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {

            serviceChallenge.supprimer(challenge.getId());
            System.out.println("Challenge supp avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }*/

    }
}

