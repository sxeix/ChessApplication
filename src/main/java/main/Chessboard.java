package main;

import bots.ChessBot;
import bots.RandomBot;
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
import java.util.Objects;

import static enums.ColourEnum.WHITE;

@RequiredArgsConstructor
public class Chessboard {

    @NonNull
    @Getter
    private  final Integer pxSideLength;

    @NonNull
    @Getter
    private final ColourEnum playerColour;

    @NonNull
    private ChessBot chessBot;

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

    private ColourEnum turnColour = WHITE;

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
//        this.chessBot = new RandomBot(playerColour.equals(WHITE) ? ColourEnum.BLACK : WHITE);
        setPieces();
        initialisePlayerColour();
        movementControl();
        if(!playerColour.equals(WHITE)) botTurn();
    }

    public void setPieces() {
        final var colours = new ColourEnum[]{ColourEnum.BLACK, WHITE};
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
            if (piece.getColour() == this.playerColour) {
                piece.setOnMousePressed((MouseEvent event) -> {
                    if(!validateColour(piece)) return;
                    validator.calculateLegalMoves(piece, this.pieces);
                    onClickHighlight(event);
                    board.getChildren().remove(piece);
                    drawLegalMoves(piece);
                    pane.getChildren().add(piece); pane.setVisible(true);
                    highlighted.setVisible(true);

                    pane.setOnMouseDragged((MouseEvent e) -> {
                        highlightMovement(e);
                        piece.relocate(e.getX() - this.pxSquareEdge/2, e.getY() - this.pxSquareEdge/2);
                    });

                    piece.setOnMouseReleased((MouseEvent e) -> {
                        if(!validateColour(piece)) return;
                        pane.getChildren().clear(); pane.setVisible(false);
                        highlighted.setVisible(false);
                        dropPiece(piece, e);
                        board.getChildren().add(piece);
                        board.setOnMouseDragged(null);
                        if (turnColour != playerColour) botTurn();
                    });
                });
            }
        }
    }


    /**
     * This method will take a piece and a new set of coordinates for it to be moved to
     *
     * @param piece that is to be relocated
     * @param x coordinate to be moved to
     * @param y coordinate to be moved to
     */
    public void movePiece(ChessPiece piece, Integer x, Integer y) {
        this.board.getChildren().remove(piece);
        piece.moveTo(x, y);
        this.board.getChildren().add(piece);
    }

    /**
     * This method is called when it is the bot's turn to make a move
     */
    public void botTurn() {
        var botMove = chessBot.makeMove(this.pieces, validator);
        assert(Objects.requireNonNull(botMove).getKey() != null);
        movePiece(botMove.getKey(), (int) botMove.getValue().getX(), (int) botMove.getValue().getY());
        takePiece(botMove.getKey());
        turnColour = turnColour.equals(WHITE) ? ColourEnum.BLACK : WHITE;
    }

    public void drawLegalMoves(ChessPiece piece){
        for(Point coords: piece.getPotentialMoves()){
//        for(Point coords: validator.calculateThreatMoves(pieces, WHITE, 0)){
            Circle high = new Circle(this.pxSquareEdge/6);
            high.setFill(Color.GREY);
            high.relocate(((int)coords.getX()) * this.pxSquareEdge + this.pxSquareEdge/3, ((int)coords.getY()) * this.pxSquareEdge + this.pxSquareEdge/3);
            pane.getChildren().add(high);
        }
    }

    public void dropPiece(ChessPiece piece, MouseEvent e){
        for(Point coords: piece.getPotentialMoves()){
            if(isValidDrop(coords, e)){
                updatePieces(piece, (int)coords.getX(), (int)coords.getY());
                turnColour = turnColour.equals(WHITE) ? ColourEnum.BLACK : WHITE;
                takePiece(piece);
            }
        }
        GridPane.setRowIndex(piece, piece.getYCoord()); GridPane.setColumnIndex(piece, piece.getXCoord());
    }

    /**
     * This method takes in a piece that has been moved to a valid position
     * and checks that there is no piece below it, if there is a piece below then it is taken
     *
     * @param piece dropped on a coordinate
     */
    public void takePiece(ChessPiece piece) {
        board.getChildren()
                .stream()
                .filter(x -> x instanceof ChessPiece)
                .filter(boardPiece ->
                        (((ChessPiece) boardPiece).getYCoord().equals(piece.getYCoord()))
                        && (((ChessPiece) boardPiece).getXCoord().equals(piece.getXCoord()))
                        && !((ChessPiece) boardPiece).getColour().equals(piece.getColour()))
                .findFirst()
                .ifPresent(pieceBelow -> {
                    board.getChildren().remove(pieceBelow);
                    pieces.remove(pieceBelow);
                });
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

    public void initialisePlayerColour(){
        if(playerColour.equals(ColourEnum.BLACK)){
            board.setRotate(180); pane.setRotate(180);
            board.getChildren()
                    .stream()
                    .filter(x -> x instanceof ChessPiece)
                    .forEach(x -> x.setRotate(180));
        }
    }

    public void onClickHighlight(MouseEvent event){
        if(playerColour.equals(ColourEnum.BLACK)){
            GridPane.setRowIndex(highlighted, 7 - (int)event.getSceneY()/pxSquareEdge);
            GridPane.setColumnIndex(highlighted, 7 - (int)event.getSceneX()/pxSquareEdge);
        }
        else{
            GridPane.setRowIndex(highlighted, (int)event.getSceneY()/pxSquareEdge);
            GridPane.setColumnIndex(highlighted, (int)event.getSceneX()/pxSquareEdge);
        }
    }

    public void highlightMovement(MouseEvent e){
        if(isOnBoard(e.getX(), e.getY())){
            GridPane.setRowIndex(highlighted, (int)e.getY()/pxSquareEdge);
            GridPane.setColumnIndex(highlighted, (int)e.getX()/pxSquareEdge);
        }
    }

    public boolean isOnBoard(double xCoord, double yCoord){
        return xCoord > 0 && xCoord < pxSideLength && yCoord > 0 && yCoord < pxSideLength;
    }

    public boolean isValidDrop(Point coords, MouseEvent e){
        if(!isOnBoard(e.getSceneX(), e.getSceneY())) return false;
        return playerColour.equals(ColourEnum.BLACK) ?
                7 - coords.getX() == (int)e.getSceneX()/pxSquareEdge && 7 - coords.getY() == (int)e.getSceneY()/pxSquareEdge :
                coords.getX() == (int)e.getSceneX()/pxSquareEdge && coords.getY() == (int)e.getSceneY()/pxSquareEdge;
    }
}
