import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Chess extends Application {

    @Override
    public void start(Stage stage) {
        Label l = new Label("ChessApplication by James and Royston");
        Scene scene = new Scene(new StackPane(l), 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}