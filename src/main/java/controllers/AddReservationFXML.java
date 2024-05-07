package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import services.Payment;
import services.ServiceReservation;
import services.SmsSender;

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

    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    @FXML
    void AfficherReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservationFXML.fxml"));
            Parent root = loader.load();
//            modifierReservationController.setHotelId(hotel.getId());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }
    }

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
        Payment p = new Payment();
        int priceLong = (int) (Integer.parseInt(nbr.getText().replace("Total: ",""))*0.32) *100;
        p.processPayment(priceLong);
        webEngine.load("https://dashboard.stripe.com/test/payments");
        StackPane root = new StackPane();
        root.getChildren().addAll(webView);

        // Create a Scene and add the StackPane to it
        Scene scene = new Scene(root, 800, 600);
        Stage primaryStage = new Stage();
        // Set the Scene to the Stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Load Web Page on Button Click");
        primaryStage.show();
        //SMS ADMIN
        ServiceReservation serviceReservation = new ServiceReservation();
        SmsSender.SendSms("28095631","la reservation est confirmée");

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

