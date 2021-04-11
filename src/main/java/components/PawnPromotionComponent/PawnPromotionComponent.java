package components.PawnPromotionComponent;

import components.ChessPieceComponent.ChessPiece;
import enums.ColourEnum;
import enums.PieceEnum;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;

import static components.tools.BoxGenerator.createBox;

public class PawnPromotionComponent extends GridPane {
    // We should have this option pop up right in the center

    private ColourEnum colour;

    private Integer squareSize = 30;

    private ArrayList<ChessPiece> pieces;

    private ChessPiece selectedPiece;

    public PawnPromotionComponent() {
        super.setVisible(false);
    }

    public PawnPromotionComponent(Integer sqrSize) {
        this.squareSize = sqrSize;
        super.setVisible(false);
    }

    public void setVisible(ColourEnum c) {
        this.colour = c;
        this.init();
        super.setVisible(true);
    }

    private void init() {
        // Set up component
        this.drawComponentFrame();
        this.generatePieces();
        this.populateComponent();
    }

    private void drawComponentFrame() {
        for (var i = 0; i < 4; i++) {
            this.getChildren().add(createBox(i, this.squareSize, Color.DARKMAGENTA));
        }
    }

    private void generatePieces() {
        this.pieces = new ArrayList<>(Arrays.asList(
                new ChessPiece(PieceEnum.QUEEN, this.colour, 0, 0, this.squareSize),
                new ChessPiece(PieceEnum.KNIGHT, this.colour, 0, 0, this.squareSize),
                new ChessPiece(PieceEnum.BISHOP, this.colour, 0, 0, this.squareSize),
                new ChessPiece(PieceEnum.ROOK, this.colour, 0, 0, this.squareSize)
        ));

        for(var piece: this.pieces) {
            piece.setOnMouseClicked((MouseEvent e) -> {
                this.setVisible(false);
                this.selectedPiece = piece;
            });
        }
    }

    private void populateComponent() {
        for (var i = 0; i < 4; i++) {
            GridPane.setRowIndex(this.pieces.get(i), 0);
            GridPane.setColumnIndex(this.pieces.get(i), i);
            this.getChildren().add(this.pieces.get(i));
        }
    }

    public ChessPiece getSelectedPiece(int x, int y) {
        this.selectedPiece.moveTo(x,y);
        return this.selectedPiece;
    }

}
