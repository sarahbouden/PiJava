package TuniWonders.Controller;

import TuniWonders.entities.PasswordManager;
import TuniWonders.entities.User;
import TuniWonders.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterAdminController {
    @FXML
    private TextField AdresseF;

    @FXML
    private Button AjouterAdminB;

    @FXML
    private TextField CINF;

    @FXML
    private TextField EmailF;

    @FXML
    private TextField PasswordF;

    @FXML
    private TextField PhoneNumF;

    @FXML
    private Button ShowUsersB;

    @FXML
    private TextField UserNameF;

    @FXML
    private TextField VPasswordF;
    private final UserService us=new UserService();

    @FXML
    void AfficherUsers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Dashboard.fxml"));
            Parent root = loader.load();
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void AjouterAdmin(ActionEvent event) {
        String Username= UserNameF.getText();
        int CIN = Integer.parseInt(CINF.getText());
        String Email = EmailF.getText();
        String Adresse = AdresseF.getText();
        int PhoneNum= Integer.parseInt(PhoneNumF.getText());
        String Password=PasswordF.getText();
        String HPassword= PasswordManager.hashPassword(PasswordF.getText());
        String Vpassword= VPasswordF.getText();
        if (Username== null ) {
            showAlert("Error", "UserName field is empty");
        }
        if (CIN == 0 ) {
            showAlert("Error", "CIN field is empty");
        }
        if (CIN < 00000000 || CIN >99999999) {
            showAlert("Error", "CIN must contain 8 digits");
        }
        if (Adresse== null ) {
            showAlert("Error", "Adresse field is empty");
        }
        if (PhoneNum == 0 ) {
            showAlert("Error", "Phone Number field is empty");
        }
        if (PhoneNum < 00000000 || PhoneNum >99999999) {
            showAlert("Error", "Phone Number must contain 8 digits");
        }
        if (Password== null ) {
            showAlert("Error", "Password field is empty");
        }
        if (Vpassword== null ) {
            showAlert("Error", "Verify Password field is empty");
        }
        if (!Vpassword.equals(Password) ) {
            showAlert("Error", "The passwords do not match");
        }
        else {
            String role="ROLE_ADMIN";
            String status="ACTIVE";
            User user=new User(Username,HPassword,Vpassword,role,CIN,Email,Adresse,PhoneNum,status);
            try {
                us.ajouter(user);
            }
            catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                System.out.println(e.getMessage());
                alert.show();
            }
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
