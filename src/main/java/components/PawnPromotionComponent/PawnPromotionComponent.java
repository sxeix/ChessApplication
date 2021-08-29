package components.PawnPromotionComponent;

import components.ChessPieceComponent.ChessPiece;
import displays.Chessboard;
import enums.ColourEnum;
import enums.PieceEnum;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;

import static components.tools.BoxGenerator.createBox;

/**
 * This component displays the pawn promotion options GridPane.
 *
 * TODO: review this component to try and remove this cyclic dependency with the Chessboard class.
 */
public class PawnPromotionComponent extends GridPane {
    // We should have this option pop up right in the center

    private ColourEnum colour;

    private Integer squareSize = 30;

    private ArrayList<ChessPiece> pieces;

    private ChessPiece pawnToUpdate;


    public PawnPromotionComponent() {
        super.setVisible(false);
    }

    public PawnPromotionComponent(Integer sqrSize) {
        this.squareSize = sqrSize;
        super.setVisible(false);
    }

    /**
     * This method is used to make the pawn promotion appear
     *
     * @param pawn to be promoted
     * @param board the GridPane that contains the pieces
     * @param cb the chessboard to be able to trigger the bot again
     */
    public void setVisible(ChessPiece pawn, GridPane board, Chessboard cb) {
        this.colour = pawn.getColour();
        this.pawnToUpdate = pawn;
        this.init(board, cb);
        super.setVisible(true);
    }

    private void init(GridPane board, Chessboard cb) {
        // Set up component
        this.drawComponentFrame();
        this.generatePieces(board, cb);
        this.populateComponent();
    }

    /**
     * Adds the boxes to the component, creates the frame
     */
    private void drawComponentFrame() {
        for (var i = 0; i < 4; i++) {
            this.getChildren().add(createBox(i, this.squareSize, Color.GRAY));
        }
    }

    /**
     * Generates the pieces that are to be displayed on the GridPane
     * Also sets the onclick functionality
     *
     * @param board the GridPane that the ChesssPiece selected will be added to
     * @param cb the chessboard so that the bot can be enabled again and the game can continue.
     */
    private void generatePieces(GridPane board, Chessboard cb) {
        this.pieces = new ArrayList<>(Arrays.asList(
                new ChessPiece(PieceEnum.QUEEN, this.colour, 0, 0, this.squareSize),
                new ChessPiece(PieceEnum.KNIGHT, this.colour, 0, 0, this.squareSize),
                new ChessPiece(PieceEnum.BISHOP, this.colour, 0, 0, this.squareSize),
                new ChessPiece(PieceEnum.ROOK, this.colour, 0, 0, this.squareSize)
        ));

        for(var piece: this.pieces) {
            piece.setOnMouseClicked((MouseEvent e) -> {
                this.setVisible(false);
                board.getChildren().remove(this.pawnToUpdate);
                this.pawnToUpdate.promotePawn(piece.getType());
                board.getChildren().add(this.pawnToUpdate);
                cb.setBotWait(false);
            });
        }
    }

    /**
     * Adds the pieces to the GridPane
     */
    private void populateComponent() {
        for (var i = 0; i < 4; i++) {
            GridPane.setRowIndex(this.pieces.get(i), 0);
            GridPane.setColumnIndex(this.pieces.get(i), i);
            this.getChildren().add(this.pieces.get(i));
        }
    }

}
