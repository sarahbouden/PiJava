package Controllers.Activite;

import entities.Activite;

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

public class CardController {
    @FXML
    private Label DescriptionAct;

    @FXML
    private HBox box;

    @FXML
    private ImageView img;

    @FXML
    private Label locationAct;

    @FXML
    private Label nomAct;

    @FXML
    private Label typeAct;


    private Activite activite;

    private String[] Colors = {" #ffbe8a", " #ffbe8a", "#ffbe8a"};



    public void setService(Activite activite) {
        this.activite = activite;
        if (activite != null) {
            nomAct.setText(activite.getNom_act());
            Image imageFile = new Image(new File(activite.getImage_name()).toURI().toString());

            img.setImage(imageFile);
            locationAct.setText(activite.getLocation_act());

            DescriptionAct.setText(activite.getDescription_act());
            typeAct.setText(activite.getType_act());

        }
        String randomColor = Colors[(int) (Math.random() * Colors.length)];
        box.setStyle("-fx-background-color: " + randomColor +
                "; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
    }




}