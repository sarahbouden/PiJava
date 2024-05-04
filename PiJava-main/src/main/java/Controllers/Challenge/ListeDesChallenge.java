package Controllers.Challenge;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Controllers.Activite.UpdateActivite;
import entities.Activite;
import entities.Challenge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ServiceActivite;
import services.ServiceChallenge;

public class ListeDesChallenge {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Challenge, Date> Date_Debut;

    @FXML
    private TableColumn<Challenge, Date> Date_Fin;

    @FXML
    private TableColumn<Challenge, String> Objectifs;

    @FXML
    private TableColumn<Challenge, ?> Participants;

    @FXML
    private TableColumn<Challenge, String> Titre_Ch;

    @FXML
    private TableView<Challenge> tableCh;
    @FXML
    private TableColumn<Challenge, Integer> Identifiant;

    @FXML
    private Label welcomeLBL;
    @FXML
    private final ServiceChallenge CS = new ServiceChallenge();

    @FXML
    void AjouterCh(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterChallenge.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Event");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void DeleteCh(ActionEvent event) {
        Challenge selectedChallenge = tableCh.getSelectionModel().getSelectedItem();

        if (selectedChallenge != null) {
            try {
                // Delete the selected activite from the database
                CS.supprimer(selectedChallenge.getId());

                // Remove the selected activite from the TableView
                tableCh.getItems().remove(selectedChallenge);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the SQLException (e.g., show an alert dialog)
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete the selected activite. Please try again.");
                alert.showAndWait();
            }
        } else {
            // If no activite is selected, show a message to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an activite to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    void UpdateCh(ActionEvent event) {
        // Retrieve the selected activite from the table
        Challenge selectedCh = tableCh.getSelectionModel().getSelectedItem();

        if (selectedCh != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateChallenge.fxml"));
            Parent root;
            try {
                root = loader.load();
                UpdateChallenge controller = loader.getController();
                controller.initData(selectedCh); // Pass the selected activite to the update form
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Update Challenge");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // If no activite is selected, show a message to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an activite to update.");
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        try {
            List<Challenge> challenges = CS.afficher();

            if (challenges.isEmpty()) {
                // Handle empty data scenario
                // You might want to display a message or disable the TableView
                return;
            }

            ObservableList<Challenge> observableList = FXCollections.observableList(challenges);

            // Ensure that the TableView is initialized before setting items
            tableCh.setItems(observableList);

            // Make sure property names in PropertyValueFactory match the properties of Activities class
            Identifiant.setCellValueFactory(new PropertyValueFactory<>("id"));
            Titre_Ch.setCellValueFactory(new PropertyValueFactory<>("titre_ch"));
            Date_Debut.setCellValueFactory(new PropertyValueFactory<>("date_debut_ch"));
            Date_Fin.setCellValueFactory(new PropertyValueFactory<>("date_fin_ch"));
            Objectifs.setCellValueFactory(new PropertyValueFactory<>("objectif_ch"));

        } catch (SQLException e) {
            // Handle SQLException
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
