package Controllers.Challenge;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ModifierChallenge {



    @FXML
    private DatePicker Data_Debut;

    @FXML
    private DatePicker Date_Fin;

    @FXML
    private TextField Objectifs;

    @FXML
    private TextField Titre;

    @FXML
    void AfficherCh(ActionEvent event) {

    }

    @FXML
    void modifierCh(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert Data_Debut != null : "fx:id=\"Data_Debut\" was not injected: check your FXML file 'ModifierChallenge.fxml'.";
        assert Date_Fin != null : "fx:id=\"Date_Fin\" was not injected: check your FXML file 'ModifierChallenge.fxml'.";
        assert Objectifs != null : "fx:id=\"Objectifs\" was not injected: check your FXML file 'ModifierChallenge.fxml'.";
        assert Titre != null : "fx:id=\"Titre\" was not injected: check your FXML file 'ModifierChallenge.fxml'.";

    }

}
