import enums.ColourEnum;
import enums.PieceEnum;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@RequiredArgsConstructor
public class Chessboard {
    @NonNull
    private Integer pxSideLength;

    private static Integer SIDE_SQUARES = 8;

    private Integer pxSquareEdge;

    @Getter
    private GridPane board;

    private ArrayList<ChessPiece> pieces = new ArrayList<>();

    public void initBoard() {
        GridPane grid = new GridPane();
        this.pxSquareEdge = this.pxSideLength / 8;
        var colour = 0;
        for (var x = 0; x < SIDE_SQUARES; x++) {
            colour++;
            for (var y = 0; y < SIDE_SQUARES; y++) {
                Rectangle rec = new Rectangle();
                rec.setWidth(this.pxSquareEdge);
                rec.setHeight(this.pxSquareEdge);
                rec.setFill(colour % 2 == 0 ? Color.DARKSLATEGRAY: Color.WHITESMOKE);
                GridPane.setRowIndex(rec, x);
                GridPane.setColumnIndex(rec, y);
                grid.getChildren().addAll(rec);
                colour++;
            }
        }
        setPieces(grid);
        this.board = grid;
    }

    public void setPieces(GridPane grid) {
        ChessPiece king = new ChessPiece(PieceEnum.KING, ColourEnum.BLACK,3,4, pxSquareEdge);
        ChessPiece queen = new ChessPiece(PieceEnum.QUEEN, ColourEnum.WHITE,6,4, pxSquareEdge);
        pieces.add(king);
        pieces.add(queen);
        // Adds to board
        for(ChessPiece piece: pieces) {
            grid.getChildren().add(piece);
            piece.setOnMousePressed((MouseEvent event) -> {
                board.setOnMouseDragged((MouseEvent e) -> {
                    GridPane.setRowIndex(piece, (int) e.getY()/75);
                    GridPane.setColumnIndex(piece, (int) e.getX()/75);
                });
                piece.setOnMouseReleased((MouseEvent e) -> {
                    board.setOnMouseDragged(null);
                });
            });
        }
    }
}
