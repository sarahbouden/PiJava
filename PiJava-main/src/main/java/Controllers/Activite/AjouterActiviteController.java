package Controllers.Activite;

import java.awt.event.MouseEvent;
import java.io.File;
import animatefx.animation.Shake;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.Challenge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

import entities.Activite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import services.ServiceActivite;
import services.ServiceChallenge;

public class AjouterActiviteController {

    @FXML
    private TextField Name;

    @FXML
    private TextField description;

    @FXML
    private TextField img;

    @FXML
    private TextField locationAct;
    @FXML
    private ListView<Challenge> selectCh;

    @FXML
    private TextField type;

    private Stage stage;

        @FXML
        void initialize() {
            try {
                // Populate the selectactivity ListView with existing activities
                ServiceChallenge serviceChallenge = new ServiceChallenge();
                List<Challenge> allChallenges = serviceChallenge.afficher();
                selectCh.getItems().addAll(allChallenges);
                selectCh.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    @FXML
    void AfficherAct(ActionEvent event) {
        try {
            // Charger le fichier FXML de la vue de la liste des activités
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListeActivites.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);

            // Récupérer la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions liées au chargement de la vue
            // Afficher un message d'erreur ou gérer l'exception selon les besoins
        }

    }


    public void AjouterAct(ActionEvent actionEvent) {
        // Récupérer les valeurs des champs
        String nom = Name.getText();
        String desc = description.getText();
        String image = img.getText();
        String lieu = locationAct.getText();
        String categorie = type.getText();

        // Vérifier si tous les champs sont remplis
        if (nom.isEmpty() || desc.isEmpty() || image.isEmpty() || lieu.isEmpty() || categorie.isEmpty()) {
            // Animation pour les champs vides
            shakeEmptyFields();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
            return;
        }
        List<Challenge> selectedchallenge = selectCh.getSelectionModel().getSelectedItems();



        // Vérifier si le nom de l'activité existe déjà dans la base de données
        ServiceActivite serviceActivite = new ServiceActivite();
        try {
            if (serviceActivite.nomExisteDeja(nom)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Le nom de l'activité existe déjà dans la base de données.");
                alert.show();
                return;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la vérification du nom de l'activité : " + e.getMessage());
            alert.show();
            return;
        }

        // Valider les saisies des champs qui doivent être de type String
        if (!isAlpha(nom) || !isAlpha(lieu) || !isAlpha(categorie) ||!isAlpha(desc)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Les champs nom, lieu et catégorie ne doivent contenir que des lettres.");
            alert.show();
            return;
        }

        // Créer un objet Activite avec les valeurs des champs
        Activite nouvelleActivite = new Activite(nom, desc, image, lieu, categorie);

        // Appeler le service pour ajouter l'activité à la base de données
        try {
          int actId=  serviceActivite.ajouterAvecId(nouvelleActivite);
          //  int actId = nouvelleActivite.getId();

            // Associate the selected activities with the event by adding entries to the events_activities table
            for (Challenge challenge1 : selectedchallenge) {
                ServiceActivite.addActiviteChallenge(actId, challenge1.getId());
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Activité insérée avec succès.");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de l'insertion de l'activité : " + e.getMessage());
            alert.show();
        }
    }

    // Méthode utilitaire pour vérifier si une chaîne contient uniquement des lettres
    private boolean isAlpha(String s) {
        return s != null && s.matches("[a-zA-Z]+");
    }


    public void PickImg(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            // Set the path of the selected image file to the image text field
            img.setText(file.getAbsolutePath());
        }
    }
    // Méthode pour appliquer l'animation Shake aux champs vides
    private void shakeEmptyFields() {
        if (Name.getText().isEmpty()) {
            Name.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            applyShakeAnimation(Name);
        }
        if (description.getText().isEmpty()) {
            description.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            applyShakeAnimation(description);
        }
        if (img.getText().isEmpty()) {
            img.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            applyShakeAnimation(img);
        }
        if (locationAct.getText().isEmpty()) {
            locationAct.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            applyShakeAnimation(locationAct);
        }
        if (type.getText().isEmpty()) {
            type.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            applyShakeAnimation(type);
        }
    }

    // Méthode pour appliquer l'animation Shake à un champ spécifique
    private void applyShakeAnimation(TextField textField) {
        Shake shake = new Shake(textField);
        shake.setResetOnFinished(true);
        shake.play();
    }


}



