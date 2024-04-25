package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.MyDataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class RecompenseController {
    @FXML
    private TextField nomRecompenseField;

    @FXML
    private TextField niveauField;

    @FXML
    private TextArea descriptionField;
    public void saveRecompense(ActionEvent event) {
        if (validateInput()) {
        String nomRecompense = nomRecompenseField.getText();
        String niveau = niveauField.getText();
        String description = descriptionField.getText();

        // Get database connection from MyDataBase class
        MyDataBase myDataBase = MyDataBase.getInstance();
        Connection connection = myDataBase.getConnection();

        // SQL query to insert a new Recompense
        String insertQuery = "INSERT INTO recompense (nom_recp, niveau, description_recp) VALUES (?, ?, ?)";

        try {
            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // Set the parameters for the PreparedStatement
            preparedStatement.setString(1, nomRecompense);
            preparedStatement.setString(2, niveau);
            preparedStatement.setString(3, description);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            // Close the PreparedStatement
            preparedStatement.close();
            if (rowsAffected > 0) {
                // Show success alert
                showAlert(Alert.AlertType.INFORMATION, "Success", "Recompense added successfully!");
            } else {
                // Show failure alert
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add Recompense.");
            }
            // Optionally, you can add a success message or perform other actions after inserting the Recompense
            System.out.println("Recompense added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
        } }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean validateInput() {
        // Add validation logic for each input field
        if (nomRecompenseField.getText().isEmpty() || niveauField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
            // Show an error alert if any field is empty
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
            return false;
        }
        // Add more validation logic if needed
        return true;
    }

    public void gotolist(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("list_recompense.fxml"));
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
}
