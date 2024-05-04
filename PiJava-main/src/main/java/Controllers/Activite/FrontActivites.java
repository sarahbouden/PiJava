package Controllers.Activite;

import entities.Activite;
import entities.comment;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import javafx.scene.Scene;
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

public class FrontActivites  {



    @FXML
    private HBox BoxAct;
    @FXML
    private BorderPane borderPane;
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
            ServiceComment serviceComment = new ServiceComment();
            List<comment> comments = serviceComment.getCommentsByActivity(activite);
            controller.initialize(activite.getNom_act(), activite.getImage_name(), activite.getDescription_act(), activite.getType_act(), activite.getLocation_act(), activite.getChallenges(), comments);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}