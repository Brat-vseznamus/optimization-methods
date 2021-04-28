module grapics {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires jfxutils;
    requires jfreechart;

    opens grapics to javafx.fxml;
    exports grapics;
}