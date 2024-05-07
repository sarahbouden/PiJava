package Controllers.Challenge;

import Controllers.Activite.CardController;
import Controllers.Activite.DetailActivite;
import entities.Activite;
import entities.Challenge;
import javafx.event.ActionEvent;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ServiceChallenge;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListeDesChallengeFront  {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private DatePicker Date_Fin;



    @FXML
    private DatePicker date_debut;
    @FXML
    private HBox BoxAct;

    @FXML
    private BorderPane FrontCh;

    private ServiceChallenge serviceChallenge = new ServiceChallenge();



    private int column = 0 ;
    private int row = 1 ;

    private void loadArticles() throws SQLException {
        List<Challenge> challenges = serviceChallenge.afficher();

        for (Challenge challenge : challenges)
        {
            try
            {

                // Charge chaque carte des services et l'ajoute au GridPane
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CardChallenge.fxml"));
                HBox box = loader.load();
                CardChallenge controller = loader.getController();

                controller.setService(challenge);
                box.setOnMouseClicked(event -> handleCardClick(challenge));
                BoxAct.getChildren().add(box);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void AfficherAct(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeDesActivitesFront.fxml"));
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }

    public void AppliquerFiltre(ActionEvent actionEvent) throws SQLException {
        filterChallenges();
    }
    private void filterChallenges() throws SQLException {
        LocalDate debut = date_debut.getValue();
        LocalDate fin = Date_Fin.getValue();

        if (debut != null && fin != null) {
            List<Challenge> challenges = serviceChallenge.afficher()
                    .stream()
                    .filter(challenge -> {
                        // Convertir java.sql.Date en LocalDate
                        java.util.Date debutDate = new java.util.Date(challenge.getDate_debut_ch().getTime());
                        java.util.Date finDate = new java.util.Date(challenge.getDate_fin_ch().getTime());
                        LocalDate debutCh = debutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate finCh = finDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        return debutCh.isAfter(debut) && finCh.isBefore(fin);
                    })
                    .collect(Collectors.toList());

            // Effacer les anciens affichages
            BoxAct.getChildren().clear();

            // Afficher les challenges filtrés
            for (Challenge challenge : challenges) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/CardChallenge.fxml"));
                    HBox box = loader.load();
                    CardChallenge controller = loader.getController();
                    controller.setService(challenge);
                    BoxAct.getChildren().add(box);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Dates non sélectionnées");
            alert.setContentText("Veuillez sélectionner à la fois une date de début et une date de fin pour filtrer les challenges.");
            alert.showAndWait();
        }
    }
    private void handleCardClick(Challenge challenge) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailChallenge.fxml"));
            Parent root = loader.load();

            // Pass data to the DetailViewController
            DetailChallenge controller = loader.getController();
            controller.initialize(challenge.getTitre_ch(),challenge.getDate_debut_ch(),challenge.getDate_fin_ch(),challenge.getObjectif_ch());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}