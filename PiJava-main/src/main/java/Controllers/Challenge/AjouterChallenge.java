package Controllers.Challenge;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import animatefx.animation.Shake;
import entities.Activite;
import entities.Challenge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import services.ServiceActivite;
import services.ServiceChallenge;

public class AjouterChallenge {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker Date_Debut;

    @FXML
    private DatePicker Date_Fin;

    @FXML
    private TextField Objectifs;

    @FXML
    private TextField Titre;

    @FXML
    void AjouterCh(ActionEvent event) {

        // Récupérer les valeurs des champs
        String titre = Titre.getText();
        String objectifs = Objectifs.getText();
        LocalDate dateDebut = Date_Debut.getValue();
        LocalDate dateFin = Date_Fin.getValue();

        // Vérifier si tous les champs sont remplis
        if (titre.isEmpty() || objectifs.isEmpty() || dateDebut == null || dateFin == null) {
            shakeEmptyFields();
            // Afficher un message d'erreur ou gérer le cas où un champ est vide
            // Par exemple :
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
            return;
        }
        // Convertir les LocalDate en java.util.Date
        Date javaDateDebut = java.sql.Date.valueOf(dateDebut);
        Date javaDateFin = java.sql.Date.valueOf(dateFin);

        // Créer un objet Challenge avec les valeurs des champs
        Challenge nouveauChallenge = new Challenge(titre, objectifs,javaDateDebut, javaDateFin);

        // Appeler le service pour ajouter le challenge à la base de données
        ServiceChallenge serviceChallenge = new ServiceChallenge();
        try {
            serviceChallenge.ajouter(nouveauChallenge);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Challenge inséré avec succès.");
            alert.show();
            initialize();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de l'insertion du challenge : " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    void initialize() {
        assert Date_Debut != null : "fx:id=\"Date_Debut\" was not injected: check your FXML file 'AjouterChallenge.fxml'.";
        assert Date_Fin != null : "fx:id=\"Date_Fin\" was not injected: check your FXML file 'AjouterChallenge.fxml'.";
        assert Objectifs != null : "fx:id=\"Objectifs\" was not injected: check your FXML file 'AjouterChallenge.fxml'.";
        assert Titre != null : "fx:id=\"Titre\" was not injected: check your FXML file 'AjouterChallenge.fxml'.";

    }
    @FXML
    void pickdate(ActionEvent event) {
        if (Date_Debut.getValue() != null && Date_Fin.getValue() != null) {
            System.out.println("Selected start date: " + Date_Debut.getValue());
            System.out.println("Selected end date: " + Date_Fin.getValue());
        } else {
            System.out.println("Please select both start and end dates.");
        }
    }
    private void shakeEmptyFields() {
        if (Titre.getText().isEmpty()) {
            Titre.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            applyShakeAnimation(Titre);
        }
        if (Date_Debut.getValue() == null) {
            // Si aucune date n'est sélectionnée
            Date_Debut.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            applyShakeAnimation(Date_Debut.getEditor());
        }
        if (Objectifs.getText().isEmpty()) {
            Objectifs.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            applyShakeAnimation(Objectifs);
        }

        if (Date_Fin.getValue() == null) {
            // Si aucune date n'est sélectionnée
            Date_Fin.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            applyShakeAnimation(Date_Fin.getEditor());
        }
    }

    // Méthode pour appliquer l'animation Shake à un champ spécifique
    private void applyShakeAnimation(TextField textField) {
        Shake shake = new Shake(textField);
        shake.setResetOnFinished(true);
        shake.play();
    }


}
