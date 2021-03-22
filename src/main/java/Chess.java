import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Chess extends Application {

    @Override
    public void start(Stage stage) {
        Chessboard chessboard = new Chessboard(600);
        chessboard.initBoard();
        Scene scene = new Scene(chessboard.getOverlay(), 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}