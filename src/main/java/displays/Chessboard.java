package displays;

import application.MoveValidator;
import bots.ChessBot;
import components.ChessPieceComponent.ChessPiece;
import components.PawnPromotionComponent.PawnPromotionComponent;
import components.PawnPromotionComponent.RenderPawnPromotionComponent;
import components.PiecesTakenComponent.PiecesTakenComponent;
import enums.ColourEnum;
import enums.Direction;
import enums.PieceEnum;
import javafx.geometry.Pos;
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

import static enums.ColourEnum.BLACK;
import static enums.ColourEnum.WHITE;

@RequiredArgsConstructor
public class Chessboard {

    @NonNull
    @Getter
    private final Integer pxSideLength;

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

    private PiecesTakenComponent whiteTaken, blackTaken;

    private final PawnPromotionComponent pawnPromo = new PawnPromotionComponent(100);

    private boolean botWait = false;

    private final Chessboard chessboard = this;

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
        grid.getChildren().add(highlighted);
        this.board = grid;
        setPieces();
        initialisePlayerColour();
        this.overlay.getChildren().addAll(whiteTaken, blackTaken, this.board, pane);
        setupPawnPromotionComponent();
        movementControl();
        if(!playerColour.equals(WHITE)) botTurn();
    }

    /**
     * Sets up the pawn promotion component onto the board.
     */
    public void setupPawnPromotionComponent() {
        this.pawnPromo.setAlignment(Pos.CENTER);
        this.overlay.getChildren().add(this.pawnPromo);
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
                        if (turnColour != playerColour && !this.botWait) botTurn();
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
                takePiece(piece);
                turnColour = turnColour.equals(WHITE) ? ColourEnum.BLACK : WHITE;
                if (piece.getType().equals(PieceEnum.PAWN) && (coords.getY() == 0 || coords.getY() == 7)) {
                    pawnPromotion(piece);
                }
            }
        }
        GridPane.setRowIndex(piece, piece.getYCoord()); GridPane.setColumnIndex(piece, piece.getXCoord());
    }


    /**
     * This method displays the pawnPromotion button and indicates that the bot is to await making a turn
     *
     * @param pawn piece to be promoted
     */
    public void pawnPromotion(ChessPiece pawn) {
        setBotWait(true);
        this.pawnPromo.setVisible(pawn, new RenderPawnPromotionComponent() {
            @Override
            public void render(PawnPromotionComponent pawnPromotionComponent, ChessPiece piece, ChessPiece pawnToUpdate) {
                pawnPromotionComponent.setVisible(false);
                board.getChildren().remove(pawnToUpdate);
                pawnToUpdate.promotePawn(piece.getType());
                board.getChildren().add(pawnToUpdate);
                chessboard.setBotWait(false);
            }
        });
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
                    if(turnColour.equals(playerColour))
                        whiteTaken.addPiece((ChessPiece) pieceBelow);
                    else
                        blackTaken.addPiece((ChessPiece) pieceBelow);
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
            whiteTaken = new PiecesTakenComponent(WHITE, Direction.RIGHT);
            blackTaken = new PiecesTakenComponent(BLACK, Direction.LEFT);
            blackTaken.setTranslateY(-(pxSideLength + whiteTaken.getSquareSize()));
            whiteTaken.setRotate(180); blackTaken.setRotate(180);
            whiteTaken.enableBackground(); blackTaken.enableBackground();

            board.setRotate(180); pane.setRotate(180);
            board.setTranslateY(-whiteTaken.getSquareSize()); pane.setTranslateY(-whiteTaken.getSquareSize());
            board.getChildren()
                    .stream()
                    .filter(x -> x instanceof ChessPiece)
                    .forEach(x -> x.setRotate(180));
        }
        else{
            whiteTaken = new PiecesTakenComponent(WHITE, Direction.LEFT);
            blackTaken = new PiecesTakenComponent(BLACK, Direction.RIGHT);
            whiteTaken.enableBackground(); blackTaken.enableBackground();
            whiteTaken.setTranslateY(pxSideLength + whiteTaken.getSquareSize());

            board.setTranslateY(whiteTaken.getSquareSize()); pane.setTranslateY(whiteTaken.getSquareSize());
        }
    }

    public void onClickHighlight(MouseEvent event){
        int yCoord = (int)event.getSceneY() - whiteTaken.getSquareSize();
        if(playerColour.equals(ColourEnum.BLACK)){
            GridPane.setRowIndex(highlighted, 7 - yCoord/pxSquareEdge);
            GridPane.setColumnIndex(highlighted, 7 - (int)event.getSceneX()/pxSquareEdge);
        }
        else{
            GridPane.setRowIndex(highlighted, yCoord/pxSquareEdge);
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
        int yCoord = (int)e.getSceneY() - whiteTaken.getSquareSize();
        return playerColour.equals(ColourEnum.BLACK) ?
                7 - coords.getX() == (int)e.getSceneX()/pxSquareEdge && 7 - coords.getY() == yCoord/pxSquareEdge :
                coords.getX() == (int)e.getSceneX()/pxSquareEdge && coords.getY() == yCoord/pxSquareEdge;
    }

    public void setBotWait(Boolean b) {
        this.botWait = b;
        if (!b) {
            this.botTurn();
        }
    }
}
