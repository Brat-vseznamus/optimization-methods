module grapics {
    requires javafx.controls;
    requires javafx.fxml;
    requires jfxutils;
    requires com.jfoenix;

    opens graphics to javafx.fxml;
    exports graphics;
}