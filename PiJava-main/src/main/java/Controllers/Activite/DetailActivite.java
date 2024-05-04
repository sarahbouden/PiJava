package Controllers.Activite;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    private ListView<comment> commentList;

    @FXML
    private ImageView img;

    @FXML
    private Label locationActivite;

    @FXML
    private Label nomActivite;

    @FXML
    private Label typeActivite;
    private Activite activite;
    private ServiceComment serviceComment;


    public void initialize(String nomAct, String imageName, String descriptionAct, String typeAct, String locationAct, List<Challenge> challenges, List<comment> comments) {
        if (imageName != null && !imageName.isEmpty()) {
            Image image = new Image(new File(imageName).toURI().toString());
            img.setImage(image);
        }

        DescriptionAct.setText(descriptionAct);
        nomActivite.setText(nomAct);
        typeActivite.setText(typeAct);
        locationActivite.setText(locationAct);

        // Retrieve the comments for the given activity
        ServiceComment serviceComment = new ServiceComment();
        List<comment> activityComments = serviceComment.getCommentsByActivity(activite);

        // Set the comments as the items for the commentList ListView
        commentList.getItems().clear();
        commentList.getItems().addAll(comments);

        // Update the list of comments for the activity
        activityComments.addAll(comments);
        updateActivityComments(activityComments);
    }

    private void updateActivityComments(List<comment> comments) {
        // Implement the logic to update the activity's comments in the database
    }


}

