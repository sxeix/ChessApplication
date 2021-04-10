package components.tools;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoxGenerator {

    public static Rectangle createBox(int i, Integer squareSize) {
        Rectangle rec = new Rectangle();
        rec.setWidth(squareSize);
        rec.setHeight(squareSize);
        rec.setFill(i % 2 == 0 ? Color.DARKSLATEGRAY: Color.GREEN);
        GridPane.setRowIndex(rec, 0);
        GridPane.setColumnIndex(rec, i);
        return rec;
    }

    public static Rectangle createBox(int i, Integer squareSize, Color colour) {
        Rectangle rec = new Rectangle();
        rec.setWidth(squareSize);
        rec.setHeight(squareSize);
        rec.setFill(colour);
        GridPane.setRowIndex(rec, 0);
        GridPane.setColumnIndex(rec, i);
        return rec;
    }
    public static Rectangle createBox(int i, Integer squareSize, Color colourA, Color colourB) {
        Rectangle rec = new Rectangle();
        rec.setWidth(squareSize);
        rec.setHeight(squareSize);
        rec.setFill(i % 2 == 0 ? colourA : colourB);
        GridPane.setRowIndex(rec, 0);
        GridPane.setColumnIndex(rec, i);
        return rec;
    }
}
