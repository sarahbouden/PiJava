package tn.esprit.pidev22.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import tn.esprit.pidev22.entities.Offre;
import tn.esprit.pidev22.services.ServicesOffre;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class offerListController implements Initializable {
    @FXML
    private Pagination pagination;
    @FXML
    private Button navigate;

    private final ServicesOffre sr = new ServicesOffre();
    private final int itemsPerPage = 3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int totalOffers = sr.RecupBase().size();
        pagination.setPageCount((int) Math.ceil((double) totalOffers / itemsPerPage));
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        List<Offre> offers = sr.RecupBase();
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, offers.size());

        for (int i = startIndex; i < endIndex; i++) {
            Offre offre = offers.get(i);
            flowPane.getChildren().add(createOfferCard(offre));
        }

        return flowPane;
    }

    private Node createOfferCard(Offre offre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/pidev22/OfferCard.fxml"));
            AnchorPane card = loader.load();
            OfferCardController controller = loader.getController();
            controller.setData(offre);
            return card;
        } catch (IOException ex) {
            Logger.getLogger(offerListController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @FXML
    void navigate(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/tn/esprit/pidev22/Partenaire.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(offerListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
