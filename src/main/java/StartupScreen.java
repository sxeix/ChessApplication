import enums.ColourEnum;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StartupScreen {

    @Getter
    private Pane display;

    public void initStartupScreen(){
        display = new Pane();
        Button blackSelect = new Button();
        Button whiteSelect = new Button();
        blackSelect.setGraphic(new ImageView("Images/king_black.png"));
        whiteSelect.setGraphic(new ImageView("Images/king_white.png"));
        blackSelect.relocate(175, 200);
        whiteSelect.relocate(350,200);
        whiteSelect.setStyle("-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color; -fx-background-insets: 0, 1, 2; -fx-background-radius: 5, 4, 3;");
        blackSelect.setStyle("-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color; -fx-background-insets: 0, 1, 2; -fx-background-radius: 5, 4, 3;");

        Label title = new Label("Sample Text Here");
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        title.setMinSize(600 ,50); title.setLayoutY(50); title.setAlignment(Pos.CENTER);

        Label colorPick = new Label("Please Select Black or White");
        colorPick.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        colorPick.setMinSize(600 ,50); colorPick.setLayoutY(150); colorPick.setAlignment(Pos.CENTER);

        display.getChildren().addAll(whiteSelect, blackSelect, title, colorPick);

        blackSelect.setOnMouseClicked((MouseEvent e) ->{
            Chessboard chessboard = new Chessboard(600, ColourEnum.BLACK);
            chessboard.initBoard();
            Chess.scene.setRoot(chessboard.getOverlay());
        });

        whiteSelect.setOnMouseClicked((MouseEvent e) ->{
            Chessboard chessboard = new Chessboard(600, ColourEnum.WHITE);
            chessboard.initBoard();
            Chess.scene.setRoot(chessboard.getOverlay());
        });
    }
}
