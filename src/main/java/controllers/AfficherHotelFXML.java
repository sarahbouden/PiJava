package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

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
    private ScrollPane ftScrollPane;
    private ServiceHotel serviceHotel = new ServiceHotel();

    @FXML
    void initialize() {
        assert Form != null : "fx:id=\"Form\" was not injected: check your FXML file 'AfficherHotelFXML.fxml'.";
        assert Grid != null : "fx:id=\"Grid\" was not injected: check your FXML file 'AfficherHotelFXML.fxml'.";
        assert ftScrollPane != null : "fx:id=\"ftScrollPane\" was not injected: check your FXML file 'AfficherHotelFXML.fxml'.";
        File brandingFile = new File("src/main/img/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());

        afficherProduits();
    }
    private void afficherProduits() {
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





}
