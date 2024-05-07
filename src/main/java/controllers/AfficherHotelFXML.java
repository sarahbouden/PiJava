package controllers;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label; // Assurez-vous d'importer la classe Label de JavaFX

import entities.Hotel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.control.TextField;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceHotel;


public class AfficherHotelFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane Form;

    @FXML
    private GridPane Grid;
    @FXML
    private Button statistique;
    @FXML
    private ScrollPane ftScrollPane;
    private ServiceHotel serviceHotel = new ServiceHotel();

    @FXML
    private TextField txt_search;

    @FXML
    void initialize() {
        assert Form != null : "fx:id=\"Form\" was not injected: check your FXML file 'AfficherHotelFXML.fxml'.";
        assert Grid != null : "fx:id=\"Grid\" was not injected: check your FXML file 'AfficherHotelFXML.fxml'.";
        assert statistique != null : "fx:id=\"stat\" was not injected: check your FXML file 'AfficherHotelBack.fxml'.";
        assert ftScrollPane != null : "fx:id=\"ftScrollPane\" was not injected: check your FXML file 'AfficherHotelFXML.fxml'.";
        File brandingFile = new File("src/main/img/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterTable(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        afficher();
    }

    public void afficher() {
        try {
            List<Hotel> hotels = serviceHotel.afficher();

            int column = 0;
            int row = 0;

            for (Hotel hotel : hotels) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailHotelFXML.fxml"));
                Region produitView = loader.load();

                DetailHotelFXML controller = loader.getController();
                controller.initializeData(hotel);

                // Ajouter un gestionnaire d'événements pour l'image
                ImageView imageView = controller.getFtImgProds();
                int hotelId = hotel.getId();

                Grid.add(produitView, column, row);

                column++;
                if (column == 1) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private GridPane statistique(Hotel hotel) {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        String imagePath = hotel.getPhoto_url();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image("file:" + imagePath);
            imageView.setImage(image);
        } else {
            System.out.println("Image path is null or empty.");
        }

        Label nameLabel = new Label(hotel.getName_h());
        Label locationLabel = new Label("Location: " + hotel.getLocation());
        Label descriptionLabel = new Label("Description: " + hotel.getDescription());

        grid.add(imageView, 0, 0);
        grid.add(nameLabel, 1, 0);
        grid.add(locationLabel, 1, 1);
        grid.add(descriptionLabel, 1, 2);

        return grid;
    }


    private void filterTable(String keyword) throws SQLException {
        ObservableList<Hotel> filteredList = FXCollections.observableArrayList();
        List<Hotel> hotels = serviceHotel.afficher();

        // Filtrer les hôtels en fonction du mot-clé
        for (Hotel hotel : hotels) {
            if (hotel.getName_h().toLowerCase().contains(keyword.toLowerCase()) ||
                    hotel.getLocation().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(hotel);
            }
        }

        // Effacer les anciens éléments du GridPane
        Grid.getChildren().clear();

        int column = 0;
        int row = 0;

        // Ajouter les éléments filtrés au GridPane
        for (Hotel hotel : filteredList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailHotelFXML.fxml"));
            Region produitView;
            try {
                produitView = loader.load();
                DetailHotelFXML controller = loader.getController();
                controller.initializeData(hotel);
                ImageView imageView = controller.getFtImgProds();
                Grid.add(produitView, column, row);
                column++;
                if (column == 1) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
