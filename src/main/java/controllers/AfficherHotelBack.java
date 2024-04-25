package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import services.ServiceHotel;

public class AfficherHotelBack {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane gridhotel;
    private ServiceHotel serviceHotel = new ServiceHotel();


    @FXML
    void actualiser(ActionEvent event) {
        // Effacer le contenu actuel de la GridPane
        gridhotel.getChildren().clear();

        // Réafficher les produits en appelant la méthode afficherProduits()
        afficher();
    }

    @FXML
    void Ajout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterHotelFXML.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println(e.getMessage());
            //   afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }

    }

    @FXML
    void afficherRes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservationFXML.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println(e.getMessage());
            //   afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }

    }
    private void afficher() {
        try {
            List<Hotel> hotels = serviceHotel.afficher();

            int column = 0;
            int row = 0;

            for (Hotel hotel : hotels) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailHotelBack.fxml"));
                Region hotelView = loader.load();

                DetailHotelBackFXML controller = loader.getController();
                controller.initializeData(hotel);

                // Ajouter un gestionnaire d'événements pour l'image
                ImageView imageView = controller.getFtImgProds();
                int hotelId = hotel.getId();

                gridhotel.add(hotelView, column, row);

                column++;
                if (column == 1) {
                    column = 0;
                    column++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        @FXML
        void initialize () {
            assert gridhotel != null : "fx:id=\"gridhotel\" was not injected: check your FXML file 'AfficherHotelBack.fxml'.";

            afficher();
        }

    }