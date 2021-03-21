import enums.ColourEnum;
import enums.PieceEnum;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

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

    private PieceEnum type;

    private ColourEnum colour;

    public ChessPiece(PieceEnum t, ColourEnum c, Integer x, Integer y, Integer px) {
        super();
        this.xCoord = x;
        this.yCoord = y;
        this.type = t;
        this.colour = c;
        this.setImage(getPieceImage());
        this.setSmooth(true);
        // Sizes to tile
        this.setFitHeight(px);
        this.setFitWidth(px);
        this.setCursor(Cursor.HAND);
        GridPane.setRowIndex(this, this.getXCoord());
        GridPane.setColumnIndex(this, this.getYCoord());
    }

    public Image getPieceImage() {
        ClassLoader loader = Chessboard.class.getClassLoader();
        return new Image("Images/" + getPieceString() + "_" + getColourString() + ".png");
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
}
