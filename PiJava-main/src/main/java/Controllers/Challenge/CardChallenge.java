package Controllers.Challenge;

import entities.Challenge;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardChallenge {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label DD;

    @FXML
    private Label DF;

    @FXML
    private HBox boxch;

    @FXML
    private Label objectifsCh;

    @FXML
    private Label titrech;

    private Challenge challenge;

    private String[] Colors = {"#abb3ff", "#bfc5ff", "#b0beff"};



    public void setService(Challenge challenge) {
        this.challenge = challenge;
        if (challenge != null) {
            titrech.setText(challenge.getTitre_ch());
            DD.setText(challenge.getDate_debut_ch().toString());
            DF.setText(challenge.getDate_fin_ch().toString());
            objectifsCh.setText(challenge.getObjectif_ch());

        }
        String randomColor = Colors[(int) (Math.random() * Colors.length)];
        boxch.setStyle("-fx-background-color: " + randomColor +
                "; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
    }




}