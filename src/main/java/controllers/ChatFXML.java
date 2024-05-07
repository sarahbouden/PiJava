package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ChatFXML implements Initializable {

    private static final int MAX_ALERTS = 3;
    private static final int BAN_DURATION_MINS = 5;
    private static final String[] FORBIDDEN_WORDS = {"fuck", "shutup", "damn","ferme la"};
    @FXML
    private TextArea textchat;
    @FXML
    private TextField ASK;
    private Map<String, String> responses;
    @FXML
    private Button Home;
    private int alertCount = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        responses = new HashMap<>();
        responses.put("bonjour", "comment puis je vous aidez");
        responses.put("c quoi application", "TuniWonders");
        responses.put("comment je fais une reservation", "bien sur il suffit d'appyuier sur le bouton réserver et remplir les données de votre reservation ");
        responses.put("je peux payer en ligne?", "bien sur il suffit d'appyuier sur le bouton réserver, remplir les données de votre reservation et lorsque vous enregistrez une ^page de paiement apparait ");
        responses.put("quand est ce que vous etes ouverts?", "on est toujours a votre disposition ");
        responses.put("salut", "comment puis je vous aidez ");
        responses.put("who are you ?", "je suis un bot créer par Hala ");
        responses.put("TuniWonders?", "une appplication web de tourisme en Tunisie");
        responses.put("il y a des hotels a hammamet ?", "veuillez vérifier la page des hotels ");
        responses.put("j'ai une question", "Bien sûr! Je suis ici pour vous aider. Quelle est votre question ?");

    }

    @FXML
    private void UserA(ActionEvent event) throws Exception {
        String input = ASK.getText();
        String response = responses.getOrDefault(input, "désolé j'ai pas la réponse ");
        for (String forbiddenWord : FORBIDDEN_WORDS) {
            if (input.contains(forbiddenWord)) {
                alertCount++;
                if (alertCount == MAX_ALERTS) {
                    // Ban the user for 5 minutes
                    ASK.setDisable(true);
                    Alert alert = new Alert(AlertType.ERROR, "Vous avez été banni ");
                    alert.showAndWait();
                    Platform.exit();
                } else {
                    Alert alert = new Alert(AlertType.WARNING, "Le message contient un gros mot. Attention!");
                    alert.showAndWait();
                }
                ASK.clear();
                return;
            }
        }
        textchat.appendText("User: " + input + "\n");
        textchat.appendText("Chatbot: " + response + "\n\n");
        ASK.clear();
    }

    @FXML
    private void OnHomeClicked(ActionEvent event) {
       /* try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BackHome.fxml"));
            Parent root = loader.load();
            Home.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AjouterReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}
