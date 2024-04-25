module tn.esprit.pidev22 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens tn.esprit.pidev22 to javafx.fxml;
    exports tn.esprit.pidev22;
    exports tn.esprit.pidev22.Controller;
    opens tn.esprit.pidev22.Controller to javafx.fxml;
    opens tn.esprit.pidev22.entities to javafx.base; // Add this line
}
