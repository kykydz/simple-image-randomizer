package org.file.filemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PhotoApps extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PhotoApps.class.getResource("photo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        Parent root = scene.getRoot();

        // Calculate preferred size based on layout bounds (same as before)
        double prefWidth = root.prefWidth(-1);
        double prefHeight = root.prefHeight(-1);

        // Adjust for window decorations (optional)
        prefWidth += 16;
        prefHeight += 39;

        stage.setWidth(prefWidth);
        stage.setHeight(prefHeight);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}