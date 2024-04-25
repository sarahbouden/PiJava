package com.example.demo;

import entities.Publication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import utils.MyDataBase;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PublicationsViewController implements Initializable {

    @FXML
    private FlowPane flowPane;

    private Connection connection; // Hold the connection throughout database operations

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            connection = MyDataBase.getInstance().getConnection(); // Establish connection
            loadAndDisplayPublications();

    }

    private void loadAndDisplayPublications() {
        List<Publication> publications = getAllPublicationsFromDatabase(connection);
        showPublications(publications);
    }

    private List<Publication> getAllPublicationsFromDatabase(Connection connection) {
        List<Publication> publications = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM publication");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("titre");
                String description = resultSet.getString("description");
                String urlResource = resultSet.getString("url_ressource");
                float rating = resultSet.getFloat("rating");
                float sum = resultSet.getFloat("somme");

                System.out.println("Publication retrieved from database: " + title);

                Publication publication = new Publication(id, title, description, urlResource, rating, sum);
                publications.add(publication);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publications;
    }

    private void showPublications(List<Publication> publications) {
        flowPane.getChildren().clear(); // Clear existing content

        for (Publication publication : publications) {
            try {
                // Create and display publication cards
                VBox card = new VBox();
                card.getStyleClass().add("publication-card");

                // Add publication title
                Label titleLabel = new Label(publication.getTitre());
                titleLabel.setFont(new Font("Arial", 14));

                // Add publication description
                Label descriptionLabel = new Label(publication.getDescription());

                // Add publication image (assuming you have an imageUrl field in Publication class)
                ImageView imageView = new ImageView(new Image(publication.getUrl_ressource()));
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);

                // Add buttons for delete and update
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(event -> {
                    deletePublication(connection, publication); // Pass connection and publication
                    System.out.println("Delete button clicked for publication: " + publication.getTitre());
                });

                Button updateButton = new Button("Update");
                updateButton.setOnAction(event -> {
                    openUpdateView(publication);
                    System.out.println("Update button clicked for publication: " + publication.getTitre());
                });

                // Add components to the card
                card.getChildren().addAll(titleLabel, descriptionLabel, imageView, deleteButton, updateButton);

                // Add the card to the FlowPane
                flowPane.getChildren().add(card);
            } catch (Exception e) {
                System.err.println("Error creating publication card: "  + e.getMessage());
                e.printStackTrace();
            }
        }
    }



    private void deletePublication(Connection connection, Publication publication) {
        String query = "DELETE FROM publication WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, publication.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Publication deleted: " + publication.getTitre());
                loadAndDisplayPublications(); // Refresh the display
            } else {
                System.out.println("No publication found or already deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting publication: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void openUpdateView(Publication publication) {
        try {
            // Create a new stage for the update view
            Stage updateStage = new Stage();
            updateStage.setTitle("Update Publication");

            // Load update.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("update-view.fxml"));
            Parent updateView = loader.load();

            // Get the controller instance
            UpdateViewController updateController = loader.getController();

            // Set publication to be updated
            updateController.setPublication(publication);

            // Set the scene for the stage
            Scene updateScene = new Scene(updateView);
            updateStage.setScene(updateScene);

            // Show the update stage
            updateStage.show();
        } catch (Exception e) {
            System.err.println("Error opening update view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void modifierRes(ActionEvent event) {
    }
}


