package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entities.Hotel;
import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceHotel;
import services.ServiceReservation;

public class ModifierReservationFXML implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;



    @FXML
    private DatePicker Datef;

    @FXML
    private DatePicker dateD;


    @FXML
    private TextField nbr;

    @FXML
    private ComboBox<String> type;
    private Reservation reservation;
    private final ServiceHotel hotel= new ServiceHotel();


    @FXML
    void modifierRes(ActionEvent event) {
        if ( dateD.getValue() == null || Datef.getValue() == null || type.getValue().isEmpty() || nbr.getText().isEmpty()) {
            // Affichez une alerte si un champ est vide
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }
        try {
            LocalDate dated = dateD.getValue();
            LocalDate datef = Datef.getValue();
            int nombre = Integer.parseInt(nbr.getText());
            String typer = type.getValue();

            // Ajoutez ici les mêmes vérifications que dans AjouterReservation
            LocalDate today = LocalDate.now();

            if (dated.isBefore(today)) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "La date de début doit être aujourd'hui ou ultérieure.");
                return;
            }

            if (datef.isBefore(dated) || datef.isAfter(dated.plusDays(40))) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "La date de fin doit être entre la date de début et 40 jours après.");
                return;
            }

            if (nombre > 8) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Le nombre de personnes ne peut pas dépasser 8.");
                return;
            }

            Reservation Res = new Reservation(dated, datef, nombre, typer);
            ServiceReservation SR = new ServiceReservation();
            SR.modifier(Res);
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            afficherAlerte(Alert.AlertType.ERROR, "Erreur SQL", e.getMessage());
        } catch (NumberFormatException e) {
            // Gérer les exceptions de conversion de nombre
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir des valeurs numériques valides.");
        }
    }
   /* public void setHotelId(int hotelId) {
        this.hotel=hotel;
        id2.setText(String.valueOf(hotelId));
    }*/
    public void setReservation(Reservation reservation) {
        Hotel hotel1= new Hotel();
        dateD.setValue(reservation.getDate_debut_r());
        Datef.setValue((reservation.getDate_fin_r()));
        nbr.setText(String.valueOf(reservation.getNbr_perso()));
        type.setValue(reservation.getType_room());
    }
 /*   public void setHotelId(int hotelId) {
        this.id2 = id2;
        id2.setText(String.valueOf(hotelId));
    }*/

    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservationFXML.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // Récupérer le stage actuel
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void initialize() {
        assert Datef != null : "fx:id=\"Datef\" was not injected: check your FXML file 'ModifierReservationFXML.fxml'.";
        assert dateD != null : "fx:id=\"dateD\" was not injected: check your FXML file 'ModifierReservationFXML.fxml'.";
        assert nbr != null : "fx:id=\"nbr\" was not injected: check your FXML file 'ModifierReservationFXML.fxml'.";
        assert type != null : "fx:id=\"type\" was not injected: check your FXML file 'ModifierReservationFXML.fxml'.";

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type.setItems(FXCollections.observableArrayList("Single ","Double","Triple","Quadriple"));
    }
}
