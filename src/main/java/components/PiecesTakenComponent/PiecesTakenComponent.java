package components.PiecesTakenComponent;

import enums.ColourEnum;
import enums.Direction;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import components.ChessPieceComponent.ChessPiece;

/**
 * This class has the purpose of displaying the pieces taken. Functionality is simple and just adds
 * a piece to the display. This class is an extension of GridPane.
 */
public class PiecesTakenComponent extends GridPane {
    // TODO: Tidy attributes and make sure all have a meaningful purpose (is this.size necessary?)
    @Getter
    private ColourEnum colour;

    private Direction fillFrom = Direction.LEFT;

    private Integer boxIndex = 0;

    private Integer increment = 1;

    // ArraySize
    @Setter
    private Integer size = 19;

    // Size of each square
    @Getter
    @Setter
    private Integer squareSize = 30;

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

    /**
     * This init function handles settings that need to be catered for before anything else.
     * In this case at the moment it only caters for flipping the order of insertion
     */
    public void init() {
        if (this.fillFrom != null && this.fillFrom.equals(Direction.RIGHT)) {
            this.boxIndex = this.size;
            this.increment = -1;
        }
    }

    /**
     * Adds a background, purely for developing as of now, this method will need to be updated for practical use
     */
    public void enableBackground() {
        // TODO: Update this method to have a nicer background, this is only temporary
        for (var x = 0; x <= size; x++) {
            Rectangle rec = new Rectangle();
            rec.setWidth(this.squareSize);
            rec.setHeight(this.squareSize);
            rec.setFill(x % 2 == 0 ? Color.DARKSLATEGRAY: Color.GREEN);
            GridPane.setRowIndex(rec, 0);
            GridPane.setColumnIndex(rec, x);
            this.getChildren().addAll(rec);
        }
    }

    /**
     * Adds a piece at the next index and increments the index to point at the next free space for the next call.
     *
     * @param pc the ChessPiece to be added
     */
    public void addPiece(ChessPiece pc) {
        pc.disableMovement();
        pc.resize(this.squareSize);
        GridPane.setRowIndex(pc, 0);
        GridPane.setColumnIndex(pc, this.boxIndex);
        this.getChildren().add(pc);
        this.boxIndex = this.boxIndex + this.increment;
    }

}
