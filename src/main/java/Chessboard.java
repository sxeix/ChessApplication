import enums.ColourEnum;
import enums.PieceEnum;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.ArrayList;

@RequiredArgsConstructor
public class Chessboard {

    @NonNull
    @Getter
    private  final Integer pxSideLength;

    @NonNull
    @Getter
    private final ColourEnum playerColour;

    @Getter
    private Integer pxSquareEdge;

    @Getter
    private GridPane board;

    @Getter
    private StackPane overlay;

    @Getter
    private Pane pane;

    @Getter
    private Rectangle highlighted;

    private static final Integer SIDE_SQUARES = 8;

    private ArrayList<ChessPiece> pieces = new ArrayList<>();

    private final MoveValidator validator = new MoveValidator();

    private ColourEnum turnColour = ColourEnum.WHITE;

    private final BoardHandler handler = new BoardHandler(this);

    public void initBoard() {
        GridPane grid = new GridPane();
        overlay = new StackPane();
        pane = new Pane();
        pane.setVisible(false);
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
        highlighted = new Rectangle();
        highlighted.setWidth(this.pxSquareEdge);
        highlighted.setHeight(this.pxSquareEdge);
        highlighted.setVisible(false);
        highlighted.setFill(Color.GREY);
        this.board = grid;
        this.overlay.getChildren().addAll(this.board, pane);
        grid.getChildren().add(highlighted);
        setPieces();
        handler.initialisePlayerColour();
        movementControl();
    }

    public void setPieces() {
        final var colours = new ColourEnum[]{ColourEnum.BLACK, ColourEnum.WHITE};
        for(ColourEnum colour: colours) {
            addPiece(PieceEnum.KING, colour,4,colour == ColourEnum.BLACK ? 0 : 7);
            addPiece(PieceEnum.QUEEN, colour,3,colour == ColourEnum.BLACK ? 0 : 7);
            for(int i = 0; i < 8; i++) {
                addPiece(PieceEnum.PAWN, colour,i,colour == ColourEnum.BLACK ? 1 : 6);
            }
            for(int i = 0; i < 2; i++) {
                addPiece(PieceEnum.BISHOP, colour, i % 2 == 0 ? 2 : 5,colour == ColourEnum.BLACK ? 0 : 7);
            }
            for(int i = 0; i < 2; i++) {
                addPiece(PieceEnum.KNIGHT, colour, i % 2 == 0 ? 1 : 6,colour == ColourEnum.BLACK ? 0 : 7);
            }
            for(int i = 0; i < 2; i++) {
                addPiece(PieceEnum.ROOK, colour, i % 2 == 0 ? 0 : 7,colour == ColourEnum.BLACK ? 0 : 7);
            }
        }
    }

    public void addPiece(PieceEnum type, ColourEnum colour, int x, int y) {
        ChessPiece piece = new ChessPiece(type, colour,x,y, pxSquareEdge);
        pieces.add(piece);
        board.getChildren().add(piece);
    }

    private void movementControl() {
        for(ChessPiece piece: pieces) {
            // Smooth Movement
            piece.setOnMousePressed((MouseEvent event) -> {
                if(!validateColour(piece)) return;
                validator.calculateLegalMoves(piece, this.pieces);
                handler.onClickHighlight(event);
                board.getChildren().remove(piece);
                drawLegalMoves(piece);
                pane.getChildren().add(piece); pane.setVisible(true);
                highlighted.setVisible(true);

                pane.setOnMouseDragged((MouseEvent e) -> {
                    handler.highlightMovement(e);
                    piece.relocate(e.getX() - this.pxSquareEdge/2, e.getY() - this.pxSquareEdge/2);
                });

                piece.setOnMouseReleased((MouseEvent e) -> {
                    if(!validateColour(piece)) return;
                    pane.getChildren().clear(); pane.setVisible(false);
                    highlighted.setVisible(false);
                    dropPiece(piece, e);
                    board.getChildren().add(piece);
                    board.setOnMouseDragged(null);
                });
            });
        }
    }

    public void drawLegalMoves(ChessPiece piece){
        for(Point coords: piece.getPotentialMoves()){
            Circle high = new Circle(this.pxSquareEdge/6);
            high.setFill(Color.GREY);
            high.relocate(((int)coords.getX()) * this.pxSquareEdge + this.pxSquareEdge/3, ((int)coords.getY()) * this.pxSquareEdge + this.pxSquareEdge/3);
            pane.getChildren().add(high);
        }
    }

    public void dropPiece(ChessPiece piece, MouseEvent e){
        for(Point coords: piece.getPotentialMoves()){
            if(handler.isValidDrop(coords, e)){
                updatePieces(piece, (int)coords.getX(), (int)coords.getY());
                turnColour = turnColour.equals(ColourEnum.WHITE) ? ColourEnum.BLACK : ColourEnum.WHITE;
                board.getChildren()
                        .stream()
                        .filter(x -> x instanceof ChessPiece)
                        .filter(x -> ((ChessPiece) x).getXCoord().equals(piece.getXCoord()) && ((ChessPiece) x).getYCoord().equals(piece.getYCoord()))
                        .findFirst()
                        .ifPresent( pieceBelow -> {
                            board.getChildren().remove(pieceBelow);
                            pieces.remove(pieceBelow);
                        });
            }
        }
        GridPane.setRowIndex(piece, piece.getYCoord()); GridPane.setColumnIndex(piece, piece.getXCoord());
    }

    public void updatePieces(ChessPiece p, int x, int y ) {
        pieces.forEach(piece -> {
            if (validator.comparePoints(piece.getCurrentPos(), p.getCurrentPos())) {
                piece.moveTo(x,y);
            }
        });
    }

    public boolean validateColour(ChessPiece piece){
        return piece.getColour().equals(turnColour);
    }
}