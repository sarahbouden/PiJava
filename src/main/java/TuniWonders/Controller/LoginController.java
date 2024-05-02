package TuniWonders.Controller;

import TuniWonders.entities.User;
import TuniWonders.services.UserService;
import TuniWonders.utils.MyDataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField EmailField;

    @FXML
    private Button LoginBtn;

    @FXML
    private PasswordField PasswordField;
    private final UserService us = new UserService();

    @FXML
    void Login(ActionEvent event) {
        User user = new User();
        String email=EmailField.getText();
        String password=PasswordField.getText();
        try {
            user=us.login(email,password);
            if(user.getStatus().equals("ACTIVE")){
                if(user.getRoles().equals("USER_ADMIN")){
                    redirect("/User/Dashboard.fxml",event);
                }
                else {
                    redirect("/User/Dashboard.fxml",event);
                }
            }
            else {
                redirect("/User/bannedUser.fxml",event);
            }


        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }
    void redirect(String path,ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
