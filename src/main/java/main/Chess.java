package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gui.StartupScreen;

public class Chess extends Application {

    public static Scene scene;

    @Override
    public void start(Stage stage) {
        StartupScreen start = new StartupScreen();
        start.initStartupScreen();
        scene = new Scene(start.getDisplay(), 600, 660);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Chess");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}