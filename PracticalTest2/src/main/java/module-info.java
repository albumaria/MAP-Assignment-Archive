module com.example.a7 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires jdk.jdi;

    opens gui to javafx.fxml;
    exports gui;
}