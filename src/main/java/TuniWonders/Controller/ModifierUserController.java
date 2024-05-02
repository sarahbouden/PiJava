package TuniWonders.Controller;

import TuniWonders.entities.PasswordManager;
import TuniWonders.entities.User;
import TuniWonders.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

public class ModifierUserController implements Initializable {
    @FXML
    private TextField AdresseF;

    @FXML
    private TextField CINF;

    @FXML
    private TextField EmailF;

    @FXML
    private Button ModifyB;

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
    private final UserService us = new UserService();
    private int ID; ;
    public void setPassedId(int ID) {
        this.ID = ID;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user= new User();
        user=us.rechercheUser(this.ID);
        UserNameF.setText(user.getUsername());
        CINF.setText(String.valueOf(user.getCin()));
        EmailF.setText(user.getEmail());
        AdresseF.setText(user.getAdresse());
        PhoneNumF.setText(String.valueOf(user.getNum_tel()));
        PasswordF.setText(user.getPassword());
        VPasswordF.setText(user.getVpwd());


    }
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
    void modifierUser(ActionEvent event) {
        int id = this.ID;
        System.out.println(id);
        User u = us.rechercheUser(id);
        if (u == null) {
            showAlert("Error", "User not found");
            return;
        }

        String role = u.getRoles();
        String status=u.getStatus();
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
            showAlert("Error", "CIN must contain 8 numbers");
        }
        if (Adresse== null ) {
            showAlert("Error", "Adresse field is empty");
        }
        if (PhoneNum == 0 ) {
            showAlert("Error", "Phone Number field is empty");
        }
        if (PhoneNum < 00000000 || PhoneNum >99999999) {
            showAlert("Error", "Phone Number must contain 8 numbers");
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
        else{
            User user=new User(this.ID,Username,HPassword,Vpassword,role,CIN,Email,Adresse,PhoneNum,status);
            try {
                us.modifier(user);
                Stage stage = (Stage) ModifyB.getScene().getWindow();
            }
            catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }


    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        System.out.println(message);
        alert.showAndWait();
    }


}
