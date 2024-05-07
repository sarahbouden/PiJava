package controllers;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entities.Hotel;
import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceHotel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ModifierHotelFXML {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private Button btImg;

    @FXML
    private TextField description;

    @FXML
    private ImageView ftImg;

    @FXML
    private TextField ftUrl;

    @FXML
    private TextField id;

    @FXML
    private TextField locat;

    @FXML
    private TextField name;

    @FXML
    private TextField photo;
    private Hotel hotel;

    @FXML
    void modifierHot(ActionEvent event) {
        if (name.getText().isEmpty()||locat.getText().isEmpty()||description.getText().isEmpty()) {
            // Affichez une alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        }
        try {
            id.setText(String.valueOf(hotel.getId()));
            name.setText(hotel.getName_h());
            locat.setText(hotel.getLocation());
            description.setText(hotel.getDescription());
            ftUrl.setText(hotel.getPhoto_url());

            Hotel hot = new Hotel(id, name, locat, description,ftUrl);
            ServiceHotel SR = new ServiceHotel();
            SR.modifier(hot);
        }catch (SQLException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL ERROR");
            alert.show();
        }
        catch (NumberFormatException e)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NUMBER FORMAT EXCEPTON");
            alert.show();
        }

    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
        id.setText(String.valueOf(hotel.getId()));
        name.setText(hotel.getName_h());
        locat.setText(hotel.getLocation());
        description.setText(hotel.getDescription());
        ftUrl.setText(hotel.getPhoto_url());
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
        assert btImg != null : "fx:id=\"btImg\" was not injected: check your FXML file 'ModifierHotel.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'ModifierHotel.fxml'.";
        assert ftImg != null : "fx:id=\"ftImg\" was not injected: check your FXML file 'ModifierHotel.fxml'.";
        assert ftUrl != null : "fx:id=\"ftUrl\" was not injected: check your FXML file 'ModifierHotel.fxml'.";
        assert id != null : "fx:id=\"id\" was not injected: check your FXML file 'ModifierHotel.fxml'.";
        assert location != null : "fx:id=\"location\" was not injected: check your FXML file 'ModifierHotel.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'ModifierHotel.fxml'.";
        assert photo != null : "fx:id=\"photo\" was not injected: check your FXML file 'ModifierHotel.fxml'.";

    }

}
