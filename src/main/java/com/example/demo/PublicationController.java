package com.example.demo;

import entities.Publication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.MyDataBase;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PublicationController {
    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionArea;

    @FXML
    private TextField imagePathField;

    @FXML
    private TextField ratingField;

    @FXML
    private TextField sumField;

    public void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(imagePathField.getScene().getWindow());
        if (selectedFile != null) {
            imagePathField.setText(((File) selectedFile).getAbsolutePath());
        }
    }


    public void createPublication(ActionEvent event) {
        // Get data from fields
        String title = titleField.getText();
        String description = descriptionArea.getText();
        String imageUrl = imagePathField.getText();
        float rating = 0;
        float sum = 0;
        try {
            rating = Float.parseFloat(ratingField.getText());
            sum = Float.parseFloat(sumField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Input", "Rating and Sum fields must contain numeric values.");
            return; // Stop execution if parsing fails
        }

        // Validate that all fields are set
        if (title.isEmpty() || description.isEmpty() || imageUrl.isEmpty() || ratingField.getText().isEmpty() || sumField.getText().isEmpty()) {
            showAlert("Error", "Missing Fields", "Please fill in all fields.");
            return;
        }

        // Create Publication object
        Publication publication = new Publication(title, description, imageUrl, rating, sum);

        // Add publication to the database
        MyDataBase db = MyDataBase.getInstance();
        Connection connection = db.getConnection();
        if (connection != null) {
            String query = "INSERT INTO publication (titre, description, url_ressource, rating, somme) VALUES (?, ?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, publication.getTitre());
                preparedStatement.setString(2, publication.getDescription());
                preparedStatement.setString(3, publication.getUrl_ressource());
                preparedStatement.setFloat(4, publication.getRating());
                preparedStatement.setFloat(5, publication.getSomme());
                preparedStatement.executeUpdate();
                System.out.println("Publication created and added to the database: " + publication);
                clearFields();
            } catch (SQLException e) {
                System.out.println("Error adding publication to the database: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to establish database connection.");
        }
    }



    public void cancelCreation(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        try {
            Parent root = loader.load();
            if (root == null) {
                System.out.println("FXML file not found or could not be loaded.");
                return;
            }

            // Additional setup if needed
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        titleField.clear();
        descriptionArea.clear();
        imagePathField.clear();
        ratingField.clear();
        sumField.clear();
    }
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
