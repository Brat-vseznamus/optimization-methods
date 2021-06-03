package graphics.applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class AppLab1 extends Application {
    public static Stage getStage() {
        return stage;
    }

    private static Stage stage;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        stage = primaryStage;
        final Parent root = FXMLLoader.load(getClass().getResource("/xml/Lab1.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
