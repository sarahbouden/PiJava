package Controllers.Challenge;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

import entities.Activite;
import entities.Challenge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceActivite;
import services.ServiceChallenge;

public class UpdateChallenge {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private Challenge selectedChallenge;
    private ServiceChallenge serviceChallenge;

    @FXML
    private DatePicker Date_Debut;

    @FXML
    private DatePicker Date_Fin;

    @FXML
    private TextField Objectifs;

    @FXML
    private TextField Titre;

    int id;
    public UpdateChallenge() {
        serviceChallenge = new ServiceChallenge();
    }

    @FXML
    void UpdateCh(ActionEvent event) {
        LocalDate startDate = Date_Debut.getValue();
        LocalDate endDate = Date_Fin.getValue();

        // Convert LocalDate to java.util.Date
        java.util.Date dateDebut = Date.from(   startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.util.Date dateFin = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        selectedChallenge.setTitre_ch(Titre.getText());
        selectedChallenge.setObjectif_ch(Objectifs.getText());
        selectedChallenge.setDate_debut_ch(dateDebut);
        selectedChallenge.setDate_fin_ch(dateFin);

        try {
            // Call the modifier method in the ServiceActivite to update the activite in the database
            serviceChallenge.modifier(selectedChallenge);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Activite Modifier avec succéss");
            alert.show();
            // Close the update form after saving
            // You can implement this part based on your application's flow
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @FXML
    void initialize() {
        assert Date_Debut != null : "fx:id=\"Date_Debut\" was not injected: check your FXML file 'UpdateChallenge.fxml'.";
        assert Date_Fin != null : "fx:id=\"Date_Fin\" was not injected: check your FXML file 'UpdateChallenge.fxml'.";
        assert Objectifs != null : "fx:id=\"Objectifs\" was not injected: check your FXML file 'UpdateChallenge.fxml'.";
        assert Titre != null : "fx:id=\"Titre\" was not injected: check your FXML file 'UpdateChallenge.fxml'.";

    }

    public void initData(Challenge selectedCh) {
        selectedChallenge = selectedCh;

        Titre.setText(selectedCh.getTitre_ch());

        Date_Debut.setValue(LocalDate.parse(selectedCh.getDate_debut_ch().toString()));
        Date_Fin.setValue(LocalDate.parse(selectedCh.getDate_fin_ch().toString()));
        Objectifs.setText(selectedCh.getObjectif_ch());

    }
}
