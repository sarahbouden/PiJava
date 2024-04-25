package tn.esprit.pidev22.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.pidev22.entities.Offre;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OfferCardController {
    @FXML
    private ImageView imageView;
    @FXML
    private Label nomLabel;
    @FXML
    private Label descLabel;
    @FXML
    private Label prixLabel;
    @FXML
    private Label dateLabel;

    public void setData(Offre offre) {
        nomLabel.setText(offre.getNom_offre());
        descLabel.setText(offre.getDescription_offre());
        prixLabel.setText("Price: " + offre.getPrix());
        dateLabel.setText("Expiration Date: " + offre.getDate_exp().toString());

        // Load and set image
        if (offre.getPhoto_url() != null && !offre.getPhoto_url().isEmpty()) {
            try {
                Image image = new Image(new FileInputStream(offre.getPhoto_url()));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                // Handle file not found exception
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
