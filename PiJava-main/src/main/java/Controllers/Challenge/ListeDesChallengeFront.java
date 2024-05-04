package Controllers.Challenge;

import Controllers.Activite.CardController;
import entities.Challenge;
import javafx.event.ActionEvent;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ServiceChallenge;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListeDesChallengeFront  {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void AfficherStatistiqueAct(ActionEvent event) {

    }


    @FXML
    public void initialize() {
        try {
            loadArticles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}