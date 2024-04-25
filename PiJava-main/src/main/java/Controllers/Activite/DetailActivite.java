package Controllers.Activite;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Activite;
import entities.Challenge;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DetailActivite {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label challenge;

    @FXML
    private Label DescriptionAct;

    @FXML
    private ImageView img;

    @FXML
    private Label locationActivite;

    @FXML
    private Label nomActivite;

    @FXML
    private Label typeActivite;
    private Activite activite;



    public void initialize(String nomAct, String imageName, String descriptionAct, String typeAct, String locationAct , List<Challenge> challenges ) {

        if (imageName != null && !imageName.isEmpty()) {
            Image image = new Image(new File(imageName).toURI().toString());
            img.setImage(image);
        }

            DescriptionAct.setText(descriptionAct);
            nomActivite.setText(nomAct);
            typeActivite.setText(typeAct);
            locationActivite.setText(locationAct);
        // Affichage des challenges dans le label
        StringBuilder challengesText = new StringBuilder();
        for (Challenge c : challenges) {
            challengesText.append(c.toString()).append("\n");
        }
        challenge.setText(challengesText.toString());

        }
    }

