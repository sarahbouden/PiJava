package controllers;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entities.Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import services.ServiceHotel;

public class AfficherHotelBack {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane gridhotel;
    private ServiceHotel serviceHotel = new ServiceHotel();

    @FXML
    private PieChart pieChart;
    @FXML
    private Button stat;
    @FXML
    void actualiser(ActionEvent event) {
        // Effacer le contenu actuel de la GridPane
        gridhotel.getChildren().clear();

        // Réafficher les produits en appelant la méthode afficherProduits()
        afficher();
    }

    @FXML
    void Ajout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterHotelFXML.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println(e.getMessage());
            //   afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }

    }

    @FXML
    void afficherRes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservationFXML.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println(e.getMessage());
            //   afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }

    }
    private void afficher() {
        try {
            List<Hotel> hotels = serviceHotel.afficher();

            int column = 0;
            int row = 0;

            for (Hotel hotel : hotels) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailHotelBack.fxml"));
                Region hotelView = loader.load();

                DetailHotelBackFXML controller = loader.getController();
                controller.initializeData(hotel);

                // Ajouter un gestionnaire d'événements pour l'image
                ImageView imageView = controller.getFtImgProds();
                int hotelId = hotel.getId();

                gridhotel.add(hotelView, column, row);

                column++;
                if (column == 1) {
                    column = 0;
                    column++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 /*   private void generateHotelStatistics() throws SQLException {
        Map<Integer, Integer> ratingFrequency = new HashMap<>();
        List<Hotel> hotels = serviceHotel.afficher(); // Supposons une méthode pour récupérer tous les hôtels

        // Compter la fréquence de chaque note d'hôtel
        for (Hotel hotel : hotels) {
            int rating = hotel.getRating();
            ratingFrequency.put(rating, ratingFrequency.getOrDefault(rating, 0) + 1);
        }

        // Préparer les données pour le PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Integer> entry : ratingFrequency.entrySet()) {
            pieChartData.add(new PieChart.Data(String.valueOf(entry.getKey()), entry.getValue()));
        }

        // Initialiser pieChart si null
        if (pieChart == null) {
            pieChart = new PieChart();
            // Ajouter pieChart à votre layout si nécessaire
        }

        // Définir les données du PieChart
        pieChart.setData(pieChartData);
    }*/


    @FXML
    void stat(ActionEvent event) {
        // Récupérer les données sur les statistiques des hôtels depuis votre base de données
        int oneStarCount = serviceHotel.getCountByRating(1);
        int twoStarCount = serviceHotel.getCountByRating(2);
        int threeStarCount = serviceHotel.getCountByRating(3);
        int fourStarCount = serviceHotel.getCountByRating(4);
        int fiveStarCount = serviceHotel.getCountByRating(5);

        System.out.println("Nombre d'hôtels avec une étoile : " + oneStarCount);
        System.out.println("Nombre d'hôtels avec deux étoiles : " + twoStarCount);
        System.out.println("Nombre d'hôtels avec trois étoiles : " + threeStarCount);
        System.out.println("Nombre d'hôtels avec quatre étoiles : " + fourStarCount);
        System.out.println("Nombre d'hôtels avec cinq étoiles : " + fiveStarCount);

        // Créer une liste de données pour le PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
           /*     new PieChart.Data("Une étoile", oneStarCount),
                new PieChart.Data("Deux étoiles", twoStarCount),
                new PieChart.Data("Trois étoiles", threeStarCount),
                new PieChart.Data("Quatre étoiles", fourStarCount),
                new PieChart.Data("Cinq étoiles", fiveStarCount)*/
        if (oneStarCount > 0)
            pieChartData.add(new PieChart.Data("Une étoile (" + oneStarCount + " hôtels)", oneStarCount));

        if (twoStarCount > 0)
            pieChartData.add(new PieChart.Data("Deux étoiles (" + twoStarCount + " hôtels)", twoStarCount));

        if (threeStarCount > 0)
            pieChartData.add(new PieChart.Data("Trois étoiles (" + threeStarCount + " hôtels)", threeStarCount));

        if (fourStarCount > 0)
            pieChartData.add(new PieChart.Data("Quatre étoiles (" + fourStarCount + " hôtels)", fourStarCount));

        if (fiveStarCount > 0)
            pieChartData.add(new PieChart.Data("Cinq étoiles (" + fiveStarCount + " hôtels)", fiveStarCount));


        // Effacer les données existantes du PieChart
        pieChart.getData().clear();

        // Ajouter les nouvelles données au PieChart
        pieChart.getData().addAll(pieChartData);
    }



    @FXML
        void initialize () {
            assert gridhotel != null : "fx:id=\"gridhotel\" was not injected: check your FXML file 'AfficherHotelBack.fxml'.";

            afficher();
        }

    }