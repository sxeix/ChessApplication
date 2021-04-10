package components.tools;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoxGenerator {

    public static Rectangle createBox(int i, Integer squareSize) {
        return composeBox(i, squareSize, i % 2 == 0 ? Color.DARKSLATEGRAY: Color.GREEN);
    }

    public static Rectangle createBox(int i, Integer squareSize, Color colour) {
        return composeBox(i, squareSize, colour);
    }
    public static Rectangle createBox(int i, Integer squareSize, Color colourA, Color colourB) {
        return composeBox(i, squareSize, i % 2 == 0 ? colourA : colourB);
    }

    private static Rectangle composeBox(int i, Integer squareSize, Color colour) {
        Rectangle rec = new Rectangle();
        rec.setWidth(squareSize);
        rec.setHeight(squareSize);
        rec.setFill(colour);
        GridPane.setRowIndex(rec, 0);
        GridPane.setColumnIndex(rec, i);
        return rec;
    }
}
