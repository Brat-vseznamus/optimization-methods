module grapics {
    requires javafx.controls;
    requires javafx.fxml;
    requires jfxutils;
    requires com.jfoenix;

    opens grapics to javafx.fxml;
    exports grapics;
}