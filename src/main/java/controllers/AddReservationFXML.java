package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceReservation;

public class AddReservationFXML implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker Datef;

    @FXML
    private DatePicker dateD;

    @FXML
    private TextField idhotel;

    @FXML
    private TextField nbr;

    @FXML
    private ComboBox<String> type;

    private Reservation reservation;



    @FXML
    void AjouterReservation(ActionEvent event) {
        LocalDate today = LocalDate.now();

        if (dateD.getValue() == null || Datef.getValue() == null || type.getValue().isEmpty() || nbr.getText().isEmpty()) {
            // Affichez une alerte si un champ est vide
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }
        int idh;
        idh = Integer.parseInt(idhotel.getText());
        LocalDate dated = dateD.getValue();
        LocalDate datef = Datef.getValue();
        int nombre;
        try {
            nombre = Integer.parseInt(nbr.getText());
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Le nombre de personnes doit être un nombre entier.");
            return;
        }

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

        try {
            String typer = type.getValue();
            Reservation Res = new Reservation(idh,dated, datef, nombre, typer);


                ServiceReservation SR = new ServiceReservation();
            SR.ajouter(Res);
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Réservation ajoutée avec succès.");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur SQL", e.getMessage());
        }

    }


    public void setHotelId(int hotelId) {
        this.idhotel = idhotel;
        idhotel.setText(String.valueOf(hotelId));
    }


    public void setReservation(Reservation reservation) {
        idhotel.setText(String.valueOf(reservation.getId_hotel_id()));

    }

  private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }


    @FXML
    void initialize() {
        assert Datef != null : "fx:id=\"Datef\" was not injected: check your FXML file 'AddReservationFXML.fxml'.";
        assert dateD != null : "fx:id=\"dateD\" was not injected: check your FXML file 'AddReservationFXML.fxml'.";
        assert nbr != null : "fx:id=\"nbr\" was not injected: check your FXML file 'AddReservationFXML.fxml'.";
        assert type != null : "fx:id=\"type\" was not injected: check your FXML file 'AddReservationFXML.fxml'.";
        assert idhotel != null : "fx:id=\"nbr\" was not injected: check your FXML file 'AddReservationFXML.fxml'.";

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type.setItems(FXCollections.observableArrayList("Single ","Double","Triple","Quadriple"));
    }

}

