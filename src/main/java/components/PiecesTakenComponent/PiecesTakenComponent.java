package components.PiecesTakenComponent;

import enums.ColourEnum;
import enums.Direction;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import main.ChessPiece;

public class PiecesTakenComponent extends GridPane {

    @Getter
    private ColourEnum colour;

    private Direction fillFrom = Direction.LEFT;

    private Integer boxIndex = 0;

    private Integer increment = 1;

    @Setter
    private Integer size = 16;

    public PiecesTakenComponent(ColourEnum colour, Direction fillFromDirection) {
        this.colour = colour;
        this.fillFrom = fillFromDirection;
        this.init();
    }
    public PiecesTakenComponent(ColourEnum colour) {
        this.colour = colour;
        this.fillFrom = null;
        this.init();
    }

    public void init() {
        if (this.fillFrom != null && this.fillFrom.equals(Direction.RIGHT)) {
            this.boxIndex = this.size;
            this.increment = -1;
        }
    }

    public void enableBackground() {
        for (var x = 0; x <= size; x++) {
            Rectangle rec = new Rectangle();
            rec.setWidth(20);
            rec.setHeight(20);
            rec.setFill(x % 2 == 0 ? Color.DARKSLATEGRAY: Color.GREEN);
            GridPane.setRowIndex(rec, 0);
            GridPane.setColumnIndex(rec, x);
            this.getChildren().addAll(rec);
        }
    }

    public void addPiece(ChessPiece pc) {
        pc.resize(20);
        GridPane.setRowIndex(pc, 0);
        GridPane.setColumnIndex(pc, this.boxIndex);
        pc.disableCursor();
        this.getChildren().add(pc);
        this.boxIndex = this.boxIndex + this.increment;
//        this.boxIndex++;
    }


}
