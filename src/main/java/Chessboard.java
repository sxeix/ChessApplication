import enums.ColourEnum;
import enums.PieceEnum;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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

    @Getter
    private StackPane overlay;

    private Pane pane;

    private ArrayList<ChessPiece> pieces = new ArrayList<>();

    public void initBoard() {
        GridPane grid = new GridPane();
        overlay = new StackPane();
        pane = new Pane();
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
        this.board = grid;
        this.overlay.getChildren().addAll(this.board);
        setPieces();
    }

    public void setPieces() {
        ChessPiece king = new ChessPiece(PieceEnum.KING, ColourEnum.BLACK,4,0, pxSquareEdge);
        ChessPiece queen = new ChessPiece(PieceEnum.QUEEN, ColourEnum.WHITE,3,7, pxSquareEdge);
        ChessPiece bishop = new ChessPiece(PieceEnum.BISHOP, ColourEnum.BLACK,2,0, pxSquareEdge);
        ChessPiece knight = new ChessPiece(PieceEnum.KNIGHT, ColourEnum.WHITE,6,7, pxSquareEdge);
        pieces.add(king);
        pieces.add(queen);
        pieces.add(bishop);
        pieces.add(knight);

        // Adds to board
        for(ChessPiece piece: pieces) {
            board.getChildren().add(piece);

            // Smooth Movement
            piece.setOnMousePressed((MouseEvent event) -> {
                board.getChildren().remove(piece);
                overlay.getChildren().add(pane);
                pane.getChildren().add(piece);

                pane.setOnMouseDragged((MouseEvent e) -> {
                    piece.relocate(e.getX() - this.pxSquareEdge/2, e.getY() - this.pxSquareEdge/2);
                    piece.setXCoord((int) e.getX()/pxSquareEdge); piece.setYCoord((int) e.getY()/pxSquareEdge);
                });
                piece.setOnMouseReleased((MouseEvent e) -> {
                    //just a tiny thing I added to take another piece. probably a better way to do it? idk
                    for(ChessPiece p: pieces)
                        if (p.getXCoord().equals(piece.getXCoord()) && p.getYCoord().equals(piece.getYCoord()) && p != piece)
                            board.getChildren().remove(p);

                    overlay.getChildren().remove(pane);
                    GridPane.setRowIndex(piece, piece.getYCoord());
                    GridPane.setColumnIndex(piece, piece.getXCoord());
                    board.getChildren().add(piece);
                    board.setOnMouseDragged(null);
                });
            });
        }
    }
}
