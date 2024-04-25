package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("recompense.fxml"));
            Parent root = loader.load();

            // Create a new stage to display the loaded scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } }

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

    public void createp(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("publication.fxml"));
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

    public void gotolistp(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("list_publication.fxml"));
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