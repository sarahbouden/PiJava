package Controllers.Challenge;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.TwilioService;

public class JoinChallenge {
    private final TwilioService twilioService = new TwilioService("ACe1a37db6f4d67b10418ce32825bbff3a", "e2307d7bea11690ee2ed8c65a7057bd3", "+12708060568");

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField cinTextField;



    // Method to set the event name


    @FXML
    private void submitForm() {
        String phoneNumber = phoneNumberTextField.getText();
        String name = nameTextField.getText();
        String cin = cinTextField.getText();

        // Construct message with event name
        String message = String.format("Thank you, %s, for joining ! Your ID card number is %s.", name, cin);

        // Send SMS
        twilioService.sendSms(phoneNumber, message);

        // Show confirmation message
        showAlert("Success", "Thank you! Your participation has been submitted successfully.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
