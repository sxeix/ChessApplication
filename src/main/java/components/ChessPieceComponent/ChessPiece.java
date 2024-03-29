package components.ChessPieceComponent;

import enums.ColourEnum;
import enums.PieceEnum;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import java.awt.*;
import java.util.ArrayList;

/*
* This class is an extension of the ImageView class which is stored in a space on the grid.
* the reason for this class is to attach the coordinates to the image and format the image as desired.
*/
public class ChessPiece extends ImageView {
    @Getter
    @Setter
    private Integer xCoord;
    @Getter
    @Setter
    private Integer yCoord;

    @Getter
    @Setter
    private PieceEnum type;

    @Getter
    private final ColourEnum colour;

    @Getter
    @Setter
    private ArrayList<Point> potentialMoves = new ArrayList<>();

    @Getter
    private Integer moveNum = 0;

    public ChessPiece(PieceEnum t, ColourEnum c, Integer x, Integer y, Integer px) {
        super();
        this.xCoord = x;
        this.yCoord = y;
        this.type = t;
        this.colour = c;
        Image pieceImage = getPieceImage();
        if (pieceImage != null) {
            this.setImage(pieceImage);
            this.setSmooth(true);
            // Sizes to tile
            this.setFitHeight(px);
            this.setFitWidth(px);
            this.setCursor(Cursor.HAND);
            GridPane.setRowIndex(this, this.getYCoord());
            GridPane.setColumnIndex(this, this.getXCoord());
        }
    }

    public void promotePawn(PieceEnum t) {
        if (!this.type.equals(PieceEnum.PAWN)) return;
        this.setType(t);
        Image pieceImage = getPieceImage();
        this.setImage(pieceImage);
    }

    public Image getPieceImage() {
        try {
            return new Image("Images/" + getPieceString() + "_" + getColourString() + ".png");
        } catch (Exception e) {
            System.out.println(e + " --- Image not found");
        }
        return null;
    }

    public String getPieceString() {
        // Converts the PieceEnum to string
        return switch (this.type) {
            case ROOK -> "rook";
            case BISHOP -> "bishop";
            case QUEEN -> "queen";
            case KING -> "king";
            case PAWN -> "pawn";
            case KNIGHT -> "knight";
        };
    }

    public String getColourString() {
        // Converts the ColourEnum to string
        return this.colour == ColourEnum.WHITE ? "white" : "black";
    }

    // Not used anywhere yet but might be useful in the future :)
    public Point getCurrentPos() {
        return new Point(this.xCoord,this.yCoord);
    }

    public void moveTo(int x, int y) {
        this.moveNum++;
        setXCoord(x);
        setYCoord(y);
        GridPane.setRowIndex(this, this.getYCoord());
        GridPane.setColumnIndex(this, this.getXCoord());
    }

    public void disableMovement() {
        this.setOnMousePressed(null);
        this.setOnMouseReleased(null);
        this.setCursor(Cursor.DEFAULT);
    }

    public void resize(Integer newSize) {
        this.setFitHeight(newSize);
        this.setFitWidth(newSize);
    }

    // Just for debugging purposes
    public void prettyPrintCoord() {
        System.out.println("(" + (this.xCoord + 1) + "," + (8 - this.yCoord) + ")");
    }

}
