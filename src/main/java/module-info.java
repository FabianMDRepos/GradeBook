module com.gradebook.gradebook {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;

    opens com.gradebook.gradebook to javafx.fxml;
    exports com.gradebook.gradebook;
}