module graphics {
    requires javafx.controls;
    requires javafx.fxml;
    requires jfxutils;
    requires com.jfoenix;

    opens graphics to javafx.fxml;
    exports graphics;
    exports linear;
    exports slau.methods;
    exports slau.utils;
}