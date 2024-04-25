package com.example.demo;

import entities.Recompense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListRecompense {

    @FXML
    private ListView<Recompense> recompenseListView;
        @FXML
        private TextField  nomRecompenseField;
        @FXML
        private TextField niveauField;
        @FXML
        private TextField descriptionField;
    @FXML
    private VBox updateVBox;

    // Method to fetch data from the database and populate the ListView
    @FXML
    private void initialize() {
        refreshList();
    }

    // Method to refresh the data displayed in the ListView
    @FXML
    private void refreshList() {
        // Get database connection from MyDataBase class
        MyDataBase myDataBase = MyDataBase.getInstance();
        Connection connection = myDataBase.getConnection();

        // SQL query to retrieve data from the database including other fields
        String selectQuery = "SELECT nom_recp, niveau, description_recp FROM recompense";

        try {
            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            // Execute the query and get the ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            // Create a list to store the data retrieved from the database
            List<Recompense> recompenseList = new ArrayList<>();

            // Iterate through the ResultSet and add data to the list
            while (resultSet.next()) {
                String nomRecompense = resultSet.getString("nom_recp");
                String niveau = resultSet.getString("niveau");
                String description = resultSet.getString("description_recp");
                Recompense recompenseItem = new Recompense(nomRecompense, niveau, description);
                recompenseList.add(recompenseItem);
            }

            // Close the ResultSet and PreparedStatement
            resultSet.close();
            preparedStatement.close();

            // Populate the ListView with the data
            ObservableList<Recompense> items = FXCollections.observableArrayList(recompenseList);
            recompenseListView.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
        }
    }

    public void deleteSelected(ActionEvent event) {
        // Get the selected item from the ListView
        Recompense selectedRecompense = recompenseListView.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedRecompense != null) {
            // Get database connection from MyDataBase class
            MyDataBase myDataBase = MyDataBase.getInstance();
            Connection connection = myDataBase.getConnection();

            // SQL query to delete the selected item from the database
            String deleteQuery = "DELETE FROM recompense WHERE nom_recp = ?";

            try {
                // Create a PreparedStatement
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1, selectedRecompense.getNom_recp());

                // Execute the delete query
                int rowsAffected = preparedStatement.executeUpdate();

                // Close the PreparedStatement
                preparedStatement.close();

                if (rowsAffected > 0) {
                    // Remove the selected item from the ListView
                    recompenseListView.getItems().remove(selectedRecompense);
                } else {
                    System.out.println("cant do that");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle any SQL exceptions
            }
        }
    }

    @FXML
    private void updateSelected() {
        Recompense selectedRecompense = recompenseListView.getSelectionModel().getSelectedItem();

        if (selectedRecompense != null) {
            // Populate fields in the update VBox with selected item data
            nomRecompenseField.setText(selectedRecompense.getNom_recp());
            niveauField.setText(selectedRecompense.getNiveau());
            descriptionField.setText(selectedRecompense.getDescription_recp());

            // Make the update VBox visible
            updateVBox.setVisible(true);
        }
    }
    @FXML
    private void saveUpdate() {
        Recompense selectedRecompense = recompenseListView.getSelectionModel().getSelectedItem();
        if (selectedRecompense != null) {
            // Get database connection
            MyDataBase myDataBase = MyDataBase.getInstance();
            Connection connection = myDataBase.getConnection();

            // SQL query to update the selected item in the database based on its name
            String updateQuery = "UPDATE recompense SET niveau = ?, description_recp = ? WHERE nom_recp = ?";

            try {
                // Create PreparedStatement
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, niveauField.getText());
                preparedStatement.setString(2, descriptionField.getText());
                preparedStatement.setString(3, selectedRecompense.getNom_recp());

                // Execute update query
                int rowsAffected = preparedStatement.executeUpdate();
                preparedStatement.close();

                if (rowsAffected > 0) {
                    // Update selected item in the ListView
                    selectedRecompense.setNiveau(niveauField.getText());
                    selectedRecompense.setDescription_recp(descriptionField.getText());

                    // Hide update VBox
                    updateVBox.setVisible(false);

                    // Refresh the list to reflect the changes
                    refreshList();
                } else {
                    System.out.println("cant do that");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle any SQL exceptions
            }
        }
    }

    @FXML
    private void cancelUpdate() {
        // Reset fields and hide update VBox
        nomRecompenseField.setText("");
        niveauField.setText("");
        descriptionField.setText("");
        updateVBox.setVisible(false);
    }

    public void modifierRes(ActionEvent event) {
    }
}
