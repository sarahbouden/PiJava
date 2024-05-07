package controllers;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import entities.Hotel;
import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DetailHotelFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label descH;

    @FXML
    private Button reserver;
    @FXML
    private Button rating;

    @FXML
    private ImageView ftImgProds;

    @FXML
    private Label nomH;

    @FXML
    private Label locat;

    @FXML
    private AnchorPane res;
    private Hotel hotel;

    public Reservation reservation;

   @FXML
    void rating(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RatingController.fxml"));
            Parent root = loader.load();
            RatingController controller = loader.getController();
            controller.setHotel(hotel);
            rating.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
            //   afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }


    }

    @FXML
    void Reserver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddReservationFXML.fxml"));
            Parent root = loader.load();
            AddReservationFXML controller = loader.getController();
            // Définir l'ID de l'hôtel dans le contrôleur du formulaire d'ajout de réservation
          controller.setHotelId(hotel.getId());
            reserver.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
            //   afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }


    }


    public void initializeData(Hotel hotel) {
        this.hotel = hotel;
        // Affichage du nom du produit dans le champ de texte nomP
        nomH.setText(hotel.getName_h());
        // Affichage du prix du produit dans le champ de texte prixP
        locat.setText(hotel.getLocation());
        //prixPrd.setText(String.valueOf(produit.getPrix_produit()));
        descH.setText(hotel.getDescription());

        // Récupération de l'URL de l'image du produit à partir de la base de données
        String imageUrl = retrieveImageUrlFromDatabase(hotel.getId());
        if (imageUrl != null) {
            // Chargement de l'image depuis l'URL et affichage dans ImageView ftImgProds
            Image img = new Image(imageUrl);
            ftImgProds.setImage(img);
        }
    }

    // Méthode pour récupérer l'URL de l'image  à partir de la base de données
    private String retrieveImageUrlFromDatabase(int hotelId) {
        String imageUrl = null;
        try {
            // Établissement de la connexion à la base de données MySQL
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tuniwonders", "root", "");
            // Préparation de la requête SQL pour récupérer l'URL de l'image en fonction de l'ID du produit
            PreparedStatement statement = connection.prepareStatement("SELECT photo_url FROM hotel WHERE id= ?");
            statement.setInt(1, hotelId);
            // Exécution de la requête SQL et récupération du résultat dans un objet ResultSet
            ResultSet resultSet = statement.executeQuery();
            // Vérification si un résultat est retourné
            if (resultSet.next()) {
                // Récupération de l'URL de l'image à partir du résultat de la requête
                imageUrl = resultSet.getString("photo_url");
            }
            // Fermeture du ResultSet, du statement et de la connexion à la base de données
            resultSet.close();
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            // Gestion des exceptions lors de l'accès à la base de données
            e.printStackTrace();
        }
        // Renvoi de l'URL de l'image récupérée (ou null si aucune image n'a été trouvée)
        return imageUrl;
    }


    public ImageView getFtImgProds() {
        return ftImgProds;
    }
    @FXML
    void initialize() {
        assert descH != null : "fx:id=\"descH\" was not injected: check your FXML file 'DetailHotelFXML.fxml'.";
        assert reserver != null : "fx:id=\"reserver\" was not injected: check your FXML file 'DetailHotelFXML.fxml'.";
        assert ftImgProds != null : "fx:id=\"ftImgProds\" was not injected: check your FXML file 'DetailHotelFXML.fxml'.";
        assert nomH != null : "fx:id=\"nomH\" was not injected: check your FXML file 'DetailHotelFXML.fxml'.";
        assert locat != null : "fx:id=\"prixHt\" was not injected: check your FXML file 'DetailHotelFXML.fxml'.";
        assert res != null : "fx:id=\"prod\" was not injected: check your FXML file 'DetailHotelFXML.fxml'.";

        getFtImgProds();
    }

}
