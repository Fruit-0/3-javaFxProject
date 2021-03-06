module com.fruit.javafxtool {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires java.logging;
    requires poi;
    requires poi.ooxml;

    opens com.fruit.javafxtool to javafx.fxml;
    exports com.fruit.javafxtool;
}