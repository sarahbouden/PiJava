package controllers;
import entities.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import controllers.RatingDAOController;

public class RatingController {
    @FXML
    private ImageView emptyStar;

    @FXML
    private ImageView star1;

    @FXML
    private ImageView star2;

    @FXML
    private ImageView star3;

    @FXML
    private ImageView star4;

    @FXML
    private ImageView star5;

    private int rating = 0;
    private final int maxRating = 5;
    private RatingDAOController ratingDAO = new RatingDAOController();
    private Hotel hotel;
    private int idhotel; // L'identifiant de la sélectionnée

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
        this.idhotel = hotel.getId();
    }

    @FXML
    void handleStarClick(MouseEvent event) {
        ImageView clickedStar = (ImageView) event.getSource();
        int clickedIndex = Integer.parseInt(clickedStar.getId().substring(4)) - 1;

        // Supposons que vous avez l'identifiant du livreur dans une variable appelée "livreurId"
        int id = this.idhotel;;

        // Afficher les étoiles de notation pour la maison sélectionnée

        // Mettre à jour la note dans la base de données
        int nouvelleNote = clickedIndex + 1;
        ratingDAO.mettreAJourNote(id, nouvelleNote);

        // Mettre à jour l'affichage
        rating = clickedIndex + 1;
        updateRating();
    }



    private void updateRating() {
        ImageView[] stars = {star1, star2, star3, star4, star5};

        for (int i = 0; i < maxRating; i++) {
            if (i < rating) {
                stars[i].setImage(new Image("star.png"));
            } else {
                stars[i].setImage(new Image("empty_star.png"));
            }
        }
    }

    @FXML
    void initialize() {
        assert emptyStar != null : "fx:id=\"emptyStar\" was not injected: check your FXML file 'RatingController.fxml'.";
        assert star1 != null : "fx:id=\"star1\" was not injected: check your FXML file 'RatingController.fxml'.";
        assert star2 != null : "fx:id=\"star2\" was not injected: check your FXML file 'RatingController.fxml'.";
        assert star3 != null : "fx:id=\"star3\" was not injected: check your FXML file 'RatingController.fxml'.";
        assert star4 != null : "fx:id=\"star4\" was not injected: check your FXML file 'RatingController.fxml'.";
        assert star5 != null : "fx:id=\"star5\" was not injected: check your FXML file 'RatingController.fxml'.";

    }

}

















/*package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class
RatingController {

    @FXML
    private ImageView emptyStar;

    @FXML
    private ImageView star1;

    @FXML
    private ImageView star2;

    @FXML
    private ImageView star3;

    @FXML
    private ImageView star4;

    @FXML
    private ImageView star5;

    private int rating = 0;
    private final int maxRating = 5;

    @FXML
    void handleStarClick(MouseEvent event) {
        ImageView clickedStar = (ImageView) event.getSource();
        int clickedIndex = Integer.parseInt(clickedStar.getId().substring(4)) - 1;

        int livreurId = 272; // Remplacez ceci par l'identifiant réel du livreur

        // Mettre à jour la note dans la base de données
        int nouvelleNote = clickedIndex + 1;
       // ratingDAO.mettreAJourNoteLivreur(livreurId, nouvelleNote);
        // Mettre à jour l'affichage
        rating = clickedIndex + 1;
        updateRating();
    }

    private void updateRating() {
        ImageView[] stars = {star1, star2, star3, star4, star5};

        for (int i = 0; i < maxRating; i++) {
            if (i < rating) {
                stars[i].setImage(new Image("star.png"));
            } else {
                stars[i].setImage(new Image("empty_star.png"));
            }
        }
    }

    @FXML
    void initialize() {
        assert emptyStar != null : "fx:id=\"emptyStar\" was not injected: check your FXML file 'RatingController.fxml'.";
        assert star1 != null : "fx:id=\"star1\" was not injected: check your FXML file 'RatingController.fxml'.";
        assert star2 != null : "fx:id=\"star2\" was not injected: check your FXML file 'RatingController.fxml'.";
        assert star3 != null : "fx:id=\"star3\" was not injected: check your FXML file 'RatingController.fxml'.";
        assert star4 != null : "fx:id=\"star4\" was not injected: check your FXML file 'RatingController.fxml'.";
        assert star5 != null : "fx:id=\"star5\" was not injected: check your FXML file 'RatingController.fxml'.";

    }

}*/
