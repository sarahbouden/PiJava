package Controllers.Activite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceActivite;
import entities.Activite;

import java.io.File;
import java.sql.SQLException;

public class UpdateActivite {

    private ServiceActivite serviceActivite;
    private Activite selectedActivite;

    @FXML
    private TextField Nameu;

    @FXML
    private TextField descriptionu;

    @FXML
    private TextField imgu;

    @FXML
    private TextField locationActu;

    @FXML
    private TextField typeu;
    private Stage stage;



    public void initData(Activite activite) {
        selectedActivite = activite;
        // Set  the fields with the selected activite's data
        Nameu.setText(selectedActivite.getNom_act());
        descriptionu.setText(selectedActivite.getDescription_act());
        imgu.setText(selectedActivite.getImage_name());
        locationActu.setText(selectedActivite.getLocation_act());
        typeu.setText(selectedActivite.getType_act());
    }


    @FXML
    void UpdateAct(ActionEvent event) {
        // Update the selected activite with the new data
        selectedActivite.setNom_act(Nameu.getText());
        selectedActivite.setDescription_act(descriptionu.getText());
        selectedActivite.setImage_name(imgu.getText());
        selectedActivite.setLocation_act(locationActu.getText());
        selectedActivite.setType_act(typeu.getText());

        try {
            // Call the modifier method in the ServiceActivite to update the activite in the database
            serviceActivite.modifier(selectedActivite);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Activite Modifier avec succéss");
            alert.show();
            initialize();
            // Close the update form after saving
            // You can implement this part based on your application's flow
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public void Pickimage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            // Set the path of the selected image file to the image text field
            imgu.setText(file.getAbsolutePath());
        }
    }
    @FXML
    void initialize() {
        serviceActivite = new ServiceActivite();
    }
}
