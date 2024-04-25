package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import services.ServiceReservation;

public class AfficherReservationFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox ReservationContainer;


    @FXML
    private TextField idrecher;

    @FXML
    void actualiser(ActionEvent event) {

    }

    @FXML
    void rechercher(ActionEvent event) {

    }

    @FXML
    private final ServiceReservation serres = new ServiceReservation();



    private boolean ReservationContainsKeyword(Reservation res, String keyword) {
        // Vérifier si l'ID de la livraison contient le mot-clé
        return String.valueOf(res.getId()).contains(keyword);
    }
    private VBox createReservationCard(Reservation reservation) {
        VBox card = new VBox();
        card.setStyle("-fx-border-color: black; -fx-padding: 10;");

        Label dated = new Label("date debut" + reservation.getDate_debut_r());
        Label datef = new Label("date fin " + reservation.getDate_fin_r());
        Label nbr = new Label("nombre " + reservation.getNbr_perso());
        Label type = new Label("type " + reservation.getType_room());

        card.getChildren().addAll(dated, datef, nbr,type);

        return card;
    }
    private void displayReservation(List<Reservation> reservations) {
        for (Reservation res : reservations) {
            VBox card = createReservationCard(res);
            //LivraisonContainer.getChildren().add(card);
            //  oenProductDetailsPage(commande);
            openLivraisonDetailsPage(res);

        }
    }
    private void openLivraisonDetailsPage(Reservation reservation) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DetailReservationFXML.fxml"));
            Parent root = fxmlLoader.load();

            DetailReservationFXML controller = fxmlLoader.getController();
            controller.setData(reservation);

            // Instead of creating a new stage and scene, add the root to the existing container
            ReservationContainer.getChildren().add(root);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier FXML", e);
        }
    }

    @FXML
    void initialize() {
        assert ReservationContainer != null : "fx:id=\"ReservationContainer\" was not injected: check your FXML file 'AfficherReservationFXML.fxml'.";
        assert idrecher != null : "fx:id=\"idrecher\" was not injected: check your FXML file 'AfficherReservationFXML.fxml'.";
        List<Reservation> reservations;
        try {
            reservations = serres.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayReservation(reservations);
    }

}
