package Controllers.Activite;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import entities.Activite;
import entities.Challenge;
import entities.comment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.ServiceComment;

public class DetailActivite {
    public void setActivite(Activite activite) {
        this.activite = activite;
    }
    @FXML
    private ListView<String> commentList;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label challenge;

    @FXML
    private Label DescriptionAct;
    @FXML
    private TextArea commentArea;



    @FXML
    private ImageView img;

    @FXML
    private Label locationActivite;

    @FXML
    private Label nomActivite;

    @FXML
    private Label typeActivite;
    private Activite activite;
    private final ServiceComment serviceComment = new ServiceComment();




    public void initialize(Activite activite) {
        // Set the activite object to the class variable
        this.activite = activite;

        // Set image if available
        if (activite.getImage_name() != null && !activite.getImage_name().isEmpty()) {
            Image image = new Image(new File(activite.getImage_name()).toURI().toString());
            img.setImage(image);
        }

        // Set other properties
        DescriptionAct.setText(activite.getDescription_act());
        nomActivite.setText(activite.getNom_act());
        typeActivite.setText(activite.getType_act());
        locationActivite.setText(activite.getLocation_act());

        // Display comments
        afficherCommentairesActivite(activite);
    }

/*  // Affichage des challenges dans le label
        StringBuilder challengesText = new StringBuilder();
        for (Challenge c : challenges) {
            challengesText.append(c.toString()).append("\n");
        }
        challenge.setText(challengesText.toString());*/

    @FXML
    void handleCommentSubmit(ActionEvent event) {
        // Récupérer le contenu du commentaire depuis la zone de texte
        String commentContent = commentArea.getText();

        // Créer un nouvel objet de commentaire avec le contenu, la date actuelle et l'activite_id
        comment newComment = new comment();
        newComment.setContent(commentContent);
        newComment.setCreated_at(new Date());
        newComment.setId(activite.getId()); // Set the activite_id

        // Ajouter le commentaire à la base de données
        try {
            serviceComment.ajouter(newComment);
        } catch (SQLException e) {
            // Gérer l'exception, par exemple en affichant un message d'erreur
            e.printStackTrace();
        }

        // Ajouter le commentaire à la ListView avec son temps écoulé
        LocalDateTime now = LocalDateTime.now();
        String timeAgo = calculateTimeAgo(now, newComment.getCreated_at().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        // Modifier le contenu du commentaire pour inclure le temps écoulé
        newComment.setContent(newComment.getContent() + " (" + timeAgo + ")");

        // Ajouter le commentaire à la ListView
        commentList.getItems().add(String.valueOf(newComment));


        // Effacer le contenu de la zone de texte après avoir ajouté le commentaire
        commentArea.clear();
    }


    // Fonction pour calculer le temps écoulé depuis la création du commentaire
    private String calculateTimeAgo(LocalDateTime currentTime, LocalDateTime commentTime) {
        Duration duration = Duration.between(commentTime, currentTime);
        long minutes = duration.toMinutes();
        long hours = duration.toHours();

        if (hours > 0) {
            return "il y a " + hours + " heure(s)";
        } else {
            return "il y a " + minutes + " minute(s)";
        }
    }
    public void afficherCommentairesActivite(Activite activite) {
        // Récupérer les commentaires de l'activité spécifique depuis la base de données
        List<comment> comments = serviceComment.getCommentsByActivity(activite);

        // Ajouter les commentaires à la ListView avec leur temps écoulé
        LocalDateTime now = LocalDateTime.now();
        for (comment comment : comments) {
            String timeAgo = calculateTimeAgo(now, comment.getCreated_at().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            // Modifier le contenu du commentaire pour inclure le temps écoulé
            comment.setContent(comment.getContent() + " (" + timeAgo + ")");
            // Ajouter le commentaire à la ListView
            commentList.getItems().add(String.valueOf(comment));
        }
    }






}

