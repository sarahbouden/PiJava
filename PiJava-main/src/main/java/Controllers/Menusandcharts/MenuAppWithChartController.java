package Controllers.Menusandcharts;

import entities.Activite;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import services.ServiceActivite;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MenuAppWithChartController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private final ServiceActivite activiteService = new ServiceActivite();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setCenter(buildPieChart());
    }

    @FXML
    private void handleShowBarChart() {
        borderPane.setCenter(buildBarChart());
    }

    @FXML
    private void handleShowPieChart() {
        borderPane.setCenter(buildPieChart());
    }

    private BarChart<String, Number> buildBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Location of activities");

        // Créer l'axe Y avec le label
        // NumberAxis yAxis = new NumberAxis();
        // yAxis.setLabel("Nombre de Services");

        // Créer le BarChart avec les axes
        // BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Remplacer les données fictives par vos données réelles
        // Implémentez votre logique pour récupérer et afficher les données du BarChart ici

        return null;
    }

    private PieChart buildPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            List<Activite> activites = activiteService.afficher();

            // Map pour stocker le nombre d'activités par location
            Map<String, Integer> locationCount = new HashMap<>();

            for (Activite activite : activites) {
                String location = activite.getLocation_act();
                locationCount.put(location, locationCount.getOrDefault(location, 0) + 1);
            }

            // Ajouter les données au graphique
            for (String location : locationCount.keySet()) {
                pieChartData.add(new PieChart.Data(location, locationCount.get(location)));
            }

            PieChart pieChart = new PieChart(pieChartData);
            pieChart.setTitle("Statistiques des activités par location");

            // Attacher des info-bulles
            createTooltips(pieChart);

            // Lier la valeur et l'étiquette sur chaque tranche de tarte pour refléter les changements
            pieChartData.forEach(data ->
                    data.nameProperty().bind(Bindings.concat(data.getName(), " ", data.pieValueProperty(), " "))
            );

            ContextMenu contextMenu = new ContextMenu();
            MenuItem miSwitchToBarChart = new MenuItem("Basculer vers le Graphique en Barres");
            contextMenu.getItems().add(miSwitchToBarChart);

            // Ajouter un gestionnaire d'événements pour afficher le menu contextuel
            pieChart.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        contextMenu.show(pieChart, event.getScreenX(), event.getScreenY());
                    }
                }
            });

            // Ajouter un gestionnaire d'événements pour basculer vers le graphique en barres
            miSwitchToBarChart.setOnAction(event -> borderPane.setCenter(buildBarChart()));

            return pieChart;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Crée des info-bulles pour toutes les entrées de données
     *
     * @param pieChart Le PieChart auquel attacher les info-bulles
     */
    private void createTooltips(PieChart pieChart) {
        for (PieChart.Data data : pieChart.getData()) {
            String msg = Long.toString((long) data.getPieValue());

            Tooltip tp = new Tooltip(msg);
            tp.setShowDelay(Duration.seconds(0));

            Tooltip.install(data.getNode(), tp);

            // Mettre à jour les données de l'info-bulle lorsqu'elles changent
            data.pieValueProperty().addListener((observable, oldValue, newValue) ->
            {
                tp.setText(Long.toString((long) newValue));
            });
        }
    }
}
