package Controllers.Activite;

import entities.Activite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import services.ServiceActivite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import services.ServiceComment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FrontActivites  {



    @FXML
    private HBox BoxAct;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ComboBox<String> stateFilterComboBox;
    private ServiceActivite serviceActivite = new ServiceActivite();



    private int column = 0 ;
    private int row = 1 ;

    private void loadArticles() throws SQLException {
        List<Activite> activites = serviceActivite.afficher();

        for (Activite activite : activites)
        {
            try
            {
                // Charge chaque carte des services et l'ajoute au GridPane
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Card.fxml"));
                HBox box = loader.load();
                CardController controller = loader.getController();

                controller.setService(activite);
                box.setOnMouseClicked(event -> handleCardClick(activite));
                BoxAct.getChildren().add(box);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void AfficherStats(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chart.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void AfficherCh(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeDesChallengeFront.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        try {
            loadArticles();
            initializeFilters();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void handleCardClick(Activite activite) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailActivite.fxml"));
            Parent root = loader.load();

            // Pass data to the DetailViewController
            DetailActivite controller = loader.getController();
            controller.initialize(activite);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initializeFilters() {
        ObservableList<String> statesList = FXCollections.observableArrayList(
                "Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba",
                "Kairouan", "Kasserine", "Kebili", "Kef", "Mahdia", "Manouba", "Medenine",
                "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine",
                "Tozeur", "Tunis", "Zaghouan"
        );
        stateFilterComboBox.setItems(statesList);
    }
    @FXML
    private void applyFilters(ActionEvent event) throws SQLException {
        String selectedState = stateFilterComboBox.getValue();

        List<Activite> filteredActivities = serviceActivite.afficher();

        if (selectedState != null && !selectedState.isEmpty()) {
            filteredActivities = filteredActivities.stream()
                    .filter(activity -> activity.getLocation_act().equalsIgnoreCase(selectedState))
                    .collect(Collectors.toList());
        }



        BoxAct.getChildren().clear();

        for (Activite activity : filteredActivities) {
            loadCard(activity);
        }
    }

    private void loadCard(Activite activite) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Card.fxml"));
            HBox box = loader.load();
            CardController controller = loader.getController();
            controller.setService(activite);
            box.setOnMouseClicked(event -> handleCardClick(activite));
            BoxAct.getChildren().add(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}