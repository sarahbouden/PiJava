package tn.esprit.pidev22.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import tn.esprit.pidev22.entities.Offre;
import tn.esprit.pidev22.entities.Partenaire;
import tn.esprit.pidev22.services.ServicePartenaire;
import tn.esprit.pidev22.utils.MyDataBase;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PartenaireController implements Initializable {

    @FXML
    private TableColumn<Partenaire, String> nom;
    @FXML
    private TableColumn<Partenaire, String> type;
    @FXML
    private TableColumn<Partenaire, String> desc;
    @FXML
    private TableColumn<Partenaire, String> adresse;
    @FXML
    private TableColumn<Partenaire, String> photo;
    @FXML
    private TableColumn<Partenaire, Integer> id;
    @FXML
    private TableView<Partenaire> tab;

    @FXML
    private TextField nom_p;
    @FXML
    private TextField type_p;
    @FXML
    private TextField desc_p;
    @FXML
    private TextField adresse_p;
    @FXML
    private TextField photo_p;
    @FXML
    private TextField idx;

    @FXML
    private Button add;
    @FXML
    private Button file;
    @FXML
    private Button del;
    @FXML
    private Button update;


    private Connection cnx;

    public PartenaireController(){
        cnx = MyDataBase.getInstance().getConnection(); // Assuming getInstance() returns a MyDataBase object and getConnection() returns a Connection.
    }
    ServicePartenaire sr = new ServicePartenaire();


    public void table(){

        id.setCellValueFactory( new PropertyValueFactory<>("id"));
        nom.setCellValueFactory( new PropertyValueFactory<>("nom_p"));
        type.setCellValueFactory(new PropertyValueFactory <>("type_p"));
        desc.setCellValueFactory(new PropertyValueFactory <>("description_p"));
        adresse.setCellValueFactory(new PropertyValueFactory <>("address"));

        photo.setCellFactory(col -> new TableCell<Partenaire, String>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitHeight(50); // Set the image view size as per your requirement
                imageView.setFitWidth(50);
            }

            @Override
            protected void updateItem(String photoUrl, boolean empty) {
                super.updateItem(photoUrl, empty);
                if (empty || photoUrl == null || photoUrl.trim().isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        // Normalize the path string to include proper file separators
                        String normalizedPath = photoUrl.replace("\\", "\\\\"); // For Windows path
                        System.out.println("Loading image from URL: " + normalizedPath); // Debugging line
                        Image image = new Image(new File(normalizedPath).toURI().toString(), true);
                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        System.out.println("Exception while loading image: " + e.getMessage()); // Debugging line
                        setGraphic(null);
                    }
                }
            }
        });

        photo.setCellValueFactory(new PropertyValueFactory<>("photo_url"));




        tab.setItems(sr.RecupBase());
        System.out.println(sr.RecupBase());

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        table();
        id.setVisible(false);
        idx.setVisible(false);
        tab.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                onEdit();

            }
        });
    }

    @FXML
    private void Ajoutefichier(ActionEvent event) {
        FileChooser f = new FileChooser();
        File S = f.showOpenDialog(null);
        if(S != null) {
            // Here we replace single backslashes with double backslashes
            String correctedPath = S.getAbsolutePath().replace("\\", "\\\\");
            photo_p.setText(correctedPath);
        }
    }


    @FXML
    private void ajouter(ActionEvent event) {
        // Trim input fields to remove leading and trailing spaces
        String nom = nom_p.getText().trim();
        String description = desc_p.getText().trim();
        String type = type_p.getText().trim();
        String adresse = adresse_p.getText().trim();
        String photo = photo_p.getText().trim();

        // Check if any of the input fields are empty
        if (nom.isEmpty() || description.isEmpty() || type.isEmpty() || adresse.isEmpty() || photo.isEmpty()) {
            // Display an alert dialog for empty fields
            showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields", "All input fields are required.");
            return;
        }

        // Check if the name is unique
        for (Partenaire existingPartenaire : sr.RecupBase()) {
            if (existingPartenaire.getNom_p().equalsIgnoreCase(nom)) {
                // Display an alert dialog for non-unique name
                showAlert(Alert.AlertType.ERROR, "Error", "Duplicate Name", "Name must be unique.");
                return;
            }
        }

        // If all conditions are met, proceed with adding the partenaire
        Partenaire p = new Partenaire();
        p.setNom_p(nom);
        p.setDescription_p(description);
        p.setType_p(type);
        p.setAddress(adresse);
        p.setPhoto_url(photo);

        try {
            sr.ajouter(p);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Partenaire Added", "Partenaire added successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(PartenaireController.class.getName()).log(Level.SEVERE, null, ex);
            // Handle any SQLExceptions here
            showAlert(Alert.AlertType.ERROR, "Error", "Database Error", "An error occurred while adding the partenaire.");
        }

        table();

        // Clear input fields after adding the partenaire
        nom_p.clear();
        type_p.clear();
        adresse_p.clear();
        desc_p.clear();
        photo_p.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void onEdit() {

        java.sql.Connection cnx;
        cnx = MyDataBase.getInstance().getConnection();

        if (tab.getSelectionModel().getSelectedItem() != null) {
            Partenaire f = tab.getSelectionModel().getSelectedItem();
            String id=String.valueOf(f.getId());
            idx.setText(id);
            nom_p.setText(f.getNom_p());
            adresse_p.setText(f.getAddress());
            desc_p.setText(f.getDescription_p());
            photo_p.setText(f.getPhoto_url());
            type_p.setText(f.getType_p());


        }
    }


    @FXML
    private void supprimer(ActionEvent event) throws SQLException {


        String idf=idx.getText();
        int i=Integer.valueOf(idf);

        sr.supprimer(i);

        table();

//        SuppRole(i);



      //  JOptionPane.showMessageDialog(null,"Le client a été supprimer avec succés");

    }

    @FXML
    private void modiff(ActionEvent event) throws SQLException {


        int i = Integer.valueOf(idx.getText());
        String name = nom_p.getText();
        String type = type_p.getText();
        String description = desc_p.getText();
        String adresse = adresse_p.getText();
        String photo = photo_p.getText();

        Partenaire d = new Partenaire(i,name,type,description,adresse,photo);



        sr.modifier(d);
        table();


    }



}
