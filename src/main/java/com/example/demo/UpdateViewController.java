package com.example.demo;

import entities.Publication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.MyDataBase;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateViewController  implements Initializable
{
    @FXML
    private Label titleLabel;

    @FXML
    private TextField titleField;
    private PublicationsViewController publicationsViewController;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField imageUrlField;

    @FXML
    private Button saveButton;



    @FXML
    private Button cancelButton;


    private Publication publicationToUpdate; // Hold the publication for update
    public UpdateViewController(PublicationsViewController publicationsViewController) {
        this.publicationsViewController = publicationsViewController;
    }
    public UpdateViewController() {
        // Optional: Initialize any fields that don't require the PublicationsViewController
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Handle button clicks
        saveButton.setOnAction(event -> saveChanges());
        cancelButton.setOnAction(event -> cancelUpdate());
    }

    public void setPublication(Publication publication) {
        this.publicationToUpdate = publication; // Store publication to update

        // Pre-fill form fields with publication data
        titleField.setText(publication.getTitre());
        descriptionField.setText(publication.getDescription());
        imageUrlField.setText(publication.getUrl_ressource());
    }

    @FXML
    private void saveChanges() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String imageUrl = imageUrlField.getText();

        // Update publication object with new values
        publicationToUpdate.setTitre(title);
        publicationToUpdate.setDescription(description);
        publicationToUpdate.setUrl_ressource(imageUrl);

        // Update publication in the database
        MyDataBase db = MyDataBase.getInstance();
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE publication SET titre = ?, description = ?, url_ressource = ? WHERE id = ?")) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, imageUrl);
            preparedStatement.setInt(4, publicationToUpdate.getId());
            preparedStatement.executeUpdate();

            System.out.println("Publication updated: " + publicationToUpdate.getTitre());

            // Close update view after successful update (optional)
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            System.out.println("Error updating publication: " + e.getMessage());
        }
    }


    @FXML
    private void cancelUpdate() {
        // Close update view on cancel (optional)
        // ... (code to close update stage/window)
    }

    public void cancelAction(ActionEvent event) {
    }
}
