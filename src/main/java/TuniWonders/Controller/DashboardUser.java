package TuniWonders.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import TuniWonders.entities.User;
import TuniWonders.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DashboardUser implements Initializable {

        @FXML
        private Label ActiviteLabel;

        @FXML
        private Button AddAdminB;

        @FXML
        private TableColumn<User, String> AdresseCol;

        @FXML
        private TableColumn<User, Integer> CINCol;

        @FXML
        private Label ChallengeLabel;

        @FXML
        private Button DeleteBtn;

        @FXML
        private Button EditBtn;

        @FXML
        private TableColumn<User, String> EmailCol;

        @FXML
        private Label HotelsLabel;

        @FXML
        private TableColumn<User, Integer> IdCol;

        @FXML
        private TableColumn<User, Integer> NumTlpCol;

        @FXML
        private Label OffreLabel;

        @FXML
        private Label PartenaireLabel;

        @FXML
        private TableColumn<User, String> PasswordCol;

        @FXML
        private Label PublicationLabel;

        @FXML
        private TableColumn<User, String> rolesCol;

        @FXML
        private TableColumn<User, String> UserNameCol;

        @FXML
        private Label UtilisateursLabel;

        @FXML
        private TableView<User> tableView;

        @FXML
        private Label welcomeLBL;
        private final UserService us = new UserService();
        ObservableList<User> observableList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<User> personneList = us.afficher();
            observableList = FXCollections.observableList(personneList);

            tableView.setItems(observableList);
            IdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
            UserNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            PasswordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
            CINCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
            EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            AdresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            NumTlpCol.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
            rolesCol.setCellValueFactory(new PropertyValueFactory<>("roles"));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

        /*@FXML
        void initialize() {


        }*/

        @FXML
        void AddAdminNav(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AjouterAdmin.fxml"));
                Parent root = loader.load();
                Scene currentScene = ((Node) event.getSource()).getScene();
                currentScene.setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        }

        @FXML
        void Delete(ActionEvent event) {
            try {
                User p = tableView.getSelectionModel().getSelectedItem();
                us.supprimer(p.getId());
                observableList.remove(p);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        @FXML
        void modifierNav(ActionEvent event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ModifierUser.fxml"));
                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ModifierUserController.class) {
                        ModifierUserController modifierUserController = new ModifierUserController();
                        User p = tableView.getSelectionModel().getSelectedItem();
                        int id = p.getId();
                        modifierUserController.setPassedId(id);
                        return modifierUserController;
                    } else {
                        return new ModifierUserController();
                    }
                });
                Parent root = loader.load();
                Scene currentScene = ((Node) event.getSource()).getScene();
                currentScene.setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }



}

