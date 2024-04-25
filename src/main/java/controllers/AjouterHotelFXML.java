package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entities.Hotel;
import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceHotel;
import services.ServiceReservation;

public class AjouterHotelFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btImg;

    @FXML
    private TextField desc;

    @FXML
    private ImageView ftImg;

    @FXML
    private TextField ftUrl;

    @FXML
    private TextField location_hotel;

    @FXML
    private TextField nom_hotel;
    @FXML
    private Button idBA;
    @FXML
    void AfficherHotel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherHotelBack.fxml"));
            Parent root = loader.load();
            btImg.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            //   afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }
    }

    @FXML
    void AjouterHotel(ActionEvent event) {
        if (nom_hotel.getText().isEmpty() || location_hotel.getText().isEmpty() || desc.getText().isEmpty()) {
            // Affichez une alerte si un champ est vide
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérification que les champs ne contiennent que des caractères alphabétiques
        if (!nom_hotel.getText().matches("[\\p{L}]+") || !location_hotel.getText().matches("[\\p{L}]+") || !desc.getText().matches("[\\p{L}]+")) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Les champs Nom, Location et Description doivent contenir uniquement des lettres.");
            return;
        }


        try {
            String nom = nom_hotel.getText();
            String location = location_hotel.getText();
            String description = desc.getText();
            String url = ftUrl.getText();

            Hotel hot = new Hotel(nom, location, description, url);
            ServiceHotel SH = new ServiceHotel();
            SH.ajouter(hot);
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "SQL ERROR", e.getMessage());
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "NUMBER FORMAT EXCEPTION", e.getMessage());
        }
    }
    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }


    @FXML
    void selectioner(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String imageUrl = selectedFile.toURI().toString();
            ftUrl.setText(imageUrl);
            Image image = new Image(imageUrl);
            ftImg.setImage(image);
        }
    }

    @FXML
    void initialize() {
        assert btImg != null : "fx:id=\"btImg\" was not injected: check your FXML file 'AjouterHotelFXML.fxml'.";
        assert desc != null : "fx:id=\"desc\" was not injected: check your FXML file 'AjouterHotelFXML.fxml'.";
        assert ftImg != null : "fx:id=\"ftImg\" was not injected: check your FXML file 'AjouterHotelFXML.fxml'.";
        assert ftUrl != null : "fx:id=\"ftUrl\" was not injected: check your FXML file 'AjouterHotelFXML.fxml'.";
        assert location_hotel != null : "fx:id=\"location_hotel\" was not injected: check your FXML file 'AjouterHotelFXML.fxml'.";
        assert nom_hotel != null : "fx:id=\"nom_hotel\" was not injected: check your FXML file 'AjouterHotelFXML.fxml'.";

    }

}
