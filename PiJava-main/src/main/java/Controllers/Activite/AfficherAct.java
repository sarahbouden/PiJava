package Controllers.Activite;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.Activite;
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

public class AfficherAct {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> DescriptionAct;

    @FXML
    private TableView<Activite> ListedesAct;

    @FXML
    private TableColumn<?, ?> LocationAct;

    @FXML
    private TableColumn<?, ?> NameAct;

    @FXML
    private TableColumn<?, ?> TypeAct;

    @FXML
    private TableColumn<?, ?> img;
    @FXML
    private TableColumn<?, ?> challengeAct;
    @FXML
    private TableColumn<?, ?> idAct;

    @FXML
    private Label welcomeLBL;
    @FXML
    private final ServiceActivite AS = new ServiceActivite();

    @FXML
    void AjouterAct(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterActivite.fxml"));
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
    void DeleteAct(ActionEvent event) {
        Activite selectedActivite = ListedesAct.getSelectionModel().getSelectedItem();

        if (selectedActivite != null) {
            try {
                // Delete the selected activite from the database
                AS.supprimer(selectedActivite.getId());

                // Remove the selected activite from the TableView
                ListedesAct.getItems().remove(selectedActivite);

                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Activité supprimée avec succès.");
                alert.showAndWait();
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
    void UpdateAct(ActionEvent event) {
        // Retrieve the selected activite from the table
        Activite selectedActivite = ListedesAct.getSelectionModel().getSelectedItem();

        if (selectedActivite != null) {
            // Open the update activite form with the selected activite for update
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateActivite.fxml"));
            Parent root;
            try {
                root = loader.load();
                UpdateActivite controller = loader.getController();
                controller.initData(selectedActivite); // Pass the selected activite to the update form
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Update Activite");
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
            void initialize () {
                try {
                    List<Activite> activities = AS.afficher();

                    if (activities.isEmpty()) {
                        // Handle empty data scenario
                        // You might want to display a message or disable the TableView
                        return;
                    }

                    ObservableList<Activite> observableList = FXCollections.observableList(activities);

                    // Ensure that the TableView is initialized before setting items
                    ListedesAct.setItems(observableList);

                    // Make sure property names in PropertyValueFactory match the properties of Activities class
                    idAct.setCellValueFactory(new PropertyValueFactory<>("id"));
                    NameAct.setCellValueFactory(new PropertyValueFactory<>("nom_act"));
                    img.setCellValueFactory(new PropertyValueFactory<>("image_name"));
                    TypeAct.setCellValueFactory(new PropertyValueFactory<>("type_act"));
                    LocationAct.setCellValueFactory(new PropertyValueFactory<>("location_act"));
                    DescriptionAct.setCellValueFactory(new PropertyValueFactory<>("description_act"));


                } catch (SQLException e) {
                    // Handle SQLException
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        }

