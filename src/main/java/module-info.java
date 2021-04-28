module grapics {
    requires javafx.controls;
    requires javafx.fxml;
    requires jfxutils;

    opens grapics to javafx.fxml;
    exports grapics;
}