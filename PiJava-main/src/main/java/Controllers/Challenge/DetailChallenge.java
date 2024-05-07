package Controllers.Challenge;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import entities.Challenge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DetailChallenge {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;



    @FXML
    private Label DDch;

    @FXML
    private Label DFch;

    @FXML
    private Label objectifch;

    @FXML
    private Label titrech;


    @FXML
    private void joinnow() {
        try {
            // Load the joinevent.fxml file and set it as the scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/joinChallenge.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            JoinChallenge controller = loader.getController();



            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential exceptions
        }
    }

    @FXML

        public void initialize(String titre_ch, Date date_debut_ch, Date date_fin_ch, String objectif_ch  ) {


        titrech.setText(titre_ch);
        objectifch.setText(objectif_ch);
        // Conversion des dates en chaînes de caractères
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateDebutString = sdf.format(date_debut_ch);
        String dateFinString = sdf.format(date_fin_ch);

        DDch.setText(dateDebutString);
        DFch.setText(dateFinString);

    }

}
