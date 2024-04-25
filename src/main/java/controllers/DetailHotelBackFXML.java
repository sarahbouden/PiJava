package controllers;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ServiceHotel;

public class DetailHotelBackFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox HboxLiv;

    @FXML
    private Label desc;

    @FXML
    private Label id;

    @FXML
    private ImageView img;

    @FXML
    private Label loc;

    @FXML
    private Label nom;
    private Hotel hotel;

    private Hotel hoteltodelete;
    private ServiceHotel sh = new ServiceHotel();
    @FXML
   /* void Modifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierHotel.fxml"));
            Parent root = loader.load();
           // ModifierHotelFXML modifierReservationController = loader.getController();
           // modifierReservationController.setHotel(hotel);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }*/
        void modif(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierHotel.fxml"));
                Parent root = loader.load();
                ModifierHotelFXML modifierController = loader.getController();
                modifierController.setHotel(hotel);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
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
    void Supprimer(ActionEvent event) throws SQLException {
        if (hotel != null) {
            // Perform deletion logic
            System.out.println("Deleting Hotel with ID: " + hotel.getId());

            // Call your service to delete the hotel
            try {
                sh.supprimer(hotel);
                // Optionally, you can display a confirmation message
                afficherAlerte(Alert.AlertType.INFORMATION, "Suppression réussie", "L'hôtel a été supprimé avec succès.");
            } catch (SQLException e) {
                // Handle any potential SQL exception
                e.printStackTrace();
                afficherAlerte(Alert.AlertType.ERROR, "Erreur de suppression", "Une erreur s'est produite lors de la suppression de l'hôtel.");
            }
        } else {
            // Show a warning if no hotel is selected for deletion
            afficherMessage("Aucune sélection", "Veuillez sélectionner un hôtel à supprimer.", Alert.AlertType.WARNING);
        }
    }

    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    public void initializeData(Hotel hotel) {
        this.hotel = hotel;
        // Affichage du nom du produit dans le champ de texte nomP
        id.setText(String.valueOf(hotel.getId()));
        // Affichage du prix du produit dans le champ de texte prixP
        nom.setText(hotel.getName_h());
        loc.setText(hotel.getLocation());
        //prixPrd.setText(String.valueOf(produit.getPrix_produit()));
        desc.setText(hotel.getDescription());

        // Récupération de l'URL de l'image du produit à partir de la base de données
        String imageUrl = retrieveImageUrlFromDatabase(hotel.getId());
        if (imageUrl != null) {
            // Chargement de l'image depuis l'URL et affichage dans ImageView ftImgProds
            Image imgs = new Image(imageUrl);
            img.setImage(imgs);
        }
    }


    // Méthode pour récupérer l'URL de l'image du produit à partir de la base de données
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
        return img;
    }

    @FXML
    void initialize() {
        assert HboxLiv != null : "fx:id=\"HboxLiv\" was not injected: check your FXML file 'DetailHotelBack.fxml'.";
        assert desc != null : "fx:id=\"desc\" was not injected: check your FXML file 'DetailHotelBack.fxml'.";
        assert id != null : "fx:id=\"id\" was not injected: check your FXML file 'DetailHotelBack.fxml'.";
        assert img != null : "fx:id=\"img\" was not injected: check your FXML file 'DetailHotelBack.fxml'.";
        assert loc != null : "fx:id=\"loc\" was not injected: check your FXML file 'DetailHotelBack.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'DetailHotelBack.fxml'.";
        getFtImgProds();

    }

}
