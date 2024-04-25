package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ServiceReservation;

public class DetailReservationFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox HboxLiv;

    @FXML
    private Label date1;

    @FXML
    private Label date2;

    @FXML
    private Label nbr;

    @FXML
    private Label type;
    private Reservation reservation;
    private Reservation ResToDelete;
    private Hotel hotel;

    private ServiceReservation sr = new ServiceReservation();
    @FXML
    void Modifier(ActionEvent event) {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReservationFXML.fxml"));
            Parent root = loader.load();
            ModifierReservationFXML modifierReservationController = loader.getController();
            modifierReservationController.setHotelId(hotel.getId());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }

    }

    @FXML
    void Supprimer(ActionEvent event) {
        try {
            if (ResToDelete != null) {
                // Perform deletion logic
                sr.supprimer(ResToDelete);

                // Clear the LivToDelete reference to indicate that no delivery is selected
                ResToDelete = null;

                // Refresh the displayed list of deliveries (assuming you have a method to do this)
                // For example, if you have a method to reload the deliveries, you can call it here
                // refreshDeliveryList();

                // Show a message to indicate successful deletion
                afficherMessage("Suppression réussie", "La reservation a été supprimée avec succès.", Alert.AlertType.INFORMATION);
            } else {
                // Show a warning if no Livraison is set for deletion
                afficherMessage("Aucune sélection", "Veuillez sélectionner une reservation à supprimer.", Alert.AlertType.WARNING);
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de la reservation : " + e.getMessage());
        }
        refreshreser();
    }
    private void refreshreser() {

        date1.setText("");
        date2.setText("");
        nbr.setText("");
        type.setText("");
        HboxLiv.getChildren().clear();


    }
    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
    public void setData(Reservation reservation){
        this.reservation = reservation;
        date1.setText(String.valueOf(reservation.getDate_debut_r()));
        date2.setText(String.valueOf(reservation.getDate_fin_r()));
        nbr.setText(String.valueOf(reservation.getNbr_perso()));
        type.setText(reservation.getType_room());

        this.ResToDelete = reservation;
    }
    @FXML
    void initialize() {
        assert HboxLiv != null : "fx:id=\"HboxLiv\" was not injected: check your FXML file 'DetailReservationFXML.fxml'.";
        assert date1 != null : "fx:id=\"date1\" was not injected: check your FXML file 'DetailReservationFXML.fxml'.";
        assert date2 != null : "fx:id=\"date2\" was not injected: check your FXML file 'DetailReservationFXML.fxml'.";
        assert nbr != null : "fx:id=\"nbr\" was not injected: check your FXML file 'DetailReservationFXML.fxml'.";
        assert type != null : "fx:id=\"type\" was not injected: check your FXML file 'DetailReservationFXML.fxml'.";

    }

}
