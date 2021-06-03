module graphics {
    requires javafx.controls;
    requires javafx.fxml;
    requires jfxutils;
    requires com.jfoenix;
    requires lombok;

    opens graphics.applications to javafx.fxml;
    opens graphics.controllers to javafx.fxml;
    exports graphics.applications;
    exports graphics.controllers;
    exports linear;
    exports slau.methods;
    exports slau.utils;
    exports expression;
    exports newton;
    exports newton.quasi;
    exports newton.utils;
}