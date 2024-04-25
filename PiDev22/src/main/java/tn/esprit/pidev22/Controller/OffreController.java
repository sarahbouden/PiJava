package tn.esprit.pidev22.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import tn.esprit.pidev22.entities.Offre;
import tn.esprit.pidev22.entities.Partenaire;
import tn.esprit.pidev22.services.ServicePartenaire;
import tn.esprit.pidev22.services.ServicesOffre;
import tn.esprit.pidev22.utils.MyDataBase;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OffreController implements Initializable {

    @FXML
    private TableView<Offre> tableView;
    @FXML
    private TableColumn<Offre, String> colNomOffre;
    @FXML
    private TableColumn<Offre, String> colDescription;
    @FXML
    private TableColumn<Offre, String> partenaire;

    @FXML
    private TableColumn<Offre, String> colPhoto;
    @FXML
    private TableColumn<Offre, Double> colPrix;
    @FXML
    private TableColumn<Offre, Integer> colId;

    @FXML
    private TextField txtNomOffre;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtDateExp;
    @FXML
    private TextField idx;
    @FXML
    private TextField recherche;
    @FXML
    private TextField txtCreatedAt;
    @FXML
    private TextField txtPhoto;
    @FXML
    private TextField txtPrix;
    @FXML
    private ComboBox<String> comm;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnFileChooser;
    ObservableList<Offre> ListOffre;


    private ServicesOffre serviceOffre;

    private Connection connection;

    public OffreController() {
        connection = MyDataBase.getInstance().getConnection();
        serviceOffre = new ServicesOffre();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ServicePartenaire servicePartenaire= new ServicePartenaire();

        setupTableColumns();
        loadTableData();
        ChercheFichier();
        comm.setItems(servicePartenaire.RecupCombo());
        System.out.println(comm.getValue());
        idx.setVisible(false);
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                onEdit();

            }
        });

    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomOffre.setCellValueFactory(new PropertyValueFactory<>("nom_offre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description_offre"));

        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colPhoto.setCellValueFactory(new PropertyValueFactory<>("photo_url"));
        partenaire.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Offre, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Offre, String> param) {
                return new SimpleStringProperty(param.getValue().getPartenaire_id().getNom_p());
            }
        });


        colPhoto.setCellFactory(col -> new TableCell<Offre, String>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
            }

            @Override
            protected void updateItem(String photoUrl, boolean empty) {
                super.updateItem(photoUrl, empty);
                if (empty || photoUrl == null || photoUrl.trim().isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        Image image = new Image(new File(photoUrl).toURI().toString(), true);
                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        Logger.getLogger(OffreController.class.getName()).log(Level.SEVERE, "Error loading image: " + photoUrl, e);
                        setGraphic(null);
                    }
                }
            }
        });
    }

    private void loadTableData() {
        tableView.setItems(serviceOffre.RecupBase());
    }

    @FXML
    private void handleFileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            txtPhoto.setText(file.getAbsolutePath().replace("\\", "\\\\"));
        }
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        try {
            ServicePartenaire servicePartenaire = new ServicePartenaire();

            if (validateInput()) {
                Offre p = new Offre();
                p.setNom_offre(txtNomOffre.getText());
                p.setPrix(Double.valueOf(txtPrix.getText()));
                p.setPhoto_url(txtPhoto.getText());
                p.setDescription_offre(txtDescription.getText());
                p.setPartenaire_id(servicePartenaire.SelectPartenairebyname(comm.getValue()));
                p.setDate_exp(Date.valueOf(txtDateExp.getText()));

                serviceOffre.ajouter(p);

                loadTableData();

                // Clear input fields after successful addition
                txtDescription.clear();
                txtPrix.clear();
                txtPhoto.clear();
                txtDateExp.clear();
                txtNomOffre.clear();

                System.out.println("Offer added successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PartenaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validateInput() {
        if (txtNomOffre.getText().isEmpty() || txtDescription.getText().isEmpty() || txtDateExp.getText().isEmpty() || txtPrix.getText().isEmpty() || txtPhoto.getText().isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return false;
        }
        if (!isNameUnique(txtNomOffre.getText())) {
            showAlert("Error", "Offer name must be unique.");
            return false;
        }

        // Validate date format and check if it's superior to the current date
        try {
            Date dateExp = Date.valueOf(txtDateExp.getText());
            Date currentDate = new Date(System.currentTimeMillis());

            if (dateExp.before(currentDate)) {
                showAlert("Error", "Date must be greater than or equal to the current date.");
                return false;
            }
        } catch (IllegalArgumentException e) {
            showAlert("Error", "Invalid date format. Please enter the date in yyyy-mm-dd format.");
            return false;
        }

        return true;
    }
    private boolean isNameUnique(String name) {
        // Query your database to check if the offer name already exists
        // If it exists, return false; otherwise, return true
        // You can use your serviceOffre or any other class to perform this database query
        try {
            return serviceOffre.isOfferNameUnique(name); // Assuming you have a method in ServicesOffre to check name uniqueness
        } catch (SQLException ex) {
            Logger.getLogger(OffreController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void onEdit() {

        java.sql.Connection cnx;
        cnx = MyDataBase.getInstance().getConnection();

        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Offre f = tableView.getSelectionModel().getSelectedItem();
            String id=String.valueOf(f.getId());
            idx.setText(id);
            txtNomOffre.setText(f.getNom_offre());
            txtDateExp.setText(String.valueOf(f.getDate_exp()));
            txtPrix.setText(String.valueOf(f.getPrix()));
            txtPhoto.setText(f.getPhoto_url());
            txtDescription.setText(f.getDescription_offre());

        }
    }
    @FXML
    private void handleUpdate(ActionEvent event) {
        try {
            ServicePartenaire servicePartenaire = new ServicePartenaire();

            int id = Integer.parseInt(idx.getText());  // Ensure this is capturing the correct ID
            String name = txtNomOffre.getText();
            Date dateExp = Date.valueOf(txtDateExp.getText());  // This will throw IllegalArgumentException if format is wrong
            String description = txtDescription.getText();
            double price = Double.parseDouble(txtPrix.getText());  // Catch format exceptions
            String photo = txtPhoto.getText();

            Partenaire partenaire = servicePartenaire.SelectPartenairebyname(comm.getValue());  // Ensure you are getting the right partenaire

            if (partenaire == null) {
                System.out.println("No partner found with the name: " + comm.getValue());
                return;
            }

            Offre updatedOffre = new Offre(id, partenaire, name, description, dateExp, null, photo, price);  // Assuming Offre constructor

            serviceOffre.modifier(updatedOffre);

            loadTableData();  // Refresh table

            System.out.println("Offer updated successfully");
        } catch (SQLException e) {
            Logger.getLogger(OffreController.class.getName()).log(Level.SEVERE, "SQL Error: ", e);
        } catch (Exception e) {
            Logger.getLogger(OffreController.class.getName()).log(Level.SEVERE, "Error: ", e);
        }
    }



    public void ChercheFichier(){
        Offre f = new Offre();

        colNomOffre.setCellValueFactory(new PropertyValueFactory<>("nom_offre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description_offre"));

        ListOffre = serviceOffre.RecupBase();
        tableView.setItems(serviceOffre.RecupBase());
        FilteredList<Offre> filtreddata;
        filtreddata = new FilteredList<>(ListOffre ,b ->true);
        recherche.textProperty().addListener((observable,oldValue,newValue)->{
            filtreddata.setPredicate((u  ->  {

                if((newValue ==null) || newValue.isEmpty())
                { return true;}

                String lowerCaseFilter = newValue.toLowerCase();
                if (u.getNom_offre().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                } else if (u.getDescription_offre().toLowerCase().contains(lowerCaseFilter))
                {return true;}



                return false;
            }));
        });

        SortedList<Offre> srt = new SortedList<>(filtreddata);
        srt.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(srt);
    }
    @FXML
    private void handleDelete(ActionEvent event) throws SQLException {
        // Implement delete logic here

        String idf=idx.getText();
        int i=Integer.valueOf(idf);

        serviceOffre.supprimer(i);

        loadTableData();
    }
}
