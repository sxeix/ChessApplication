import enums.ColourEnum;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
public class BoardHandler {

    @NonNull
    private final Chessboard board;

    public void initialisePlayerColour(){
        if(board.getPlayerColour().equals(ColourEnum.BLACK)){
            board.getBoard().setRotate(180); board.getPane().setRotate(180);
            board.getBoard().getChildren()
                    .stream()
                    .filter(x -> x instanceof ChessPiece)
                    .forEach(x -> x.setRotate(180));
        }
    }

    public void onClickHighlight(MouseEvent event){
        if(board.getPlayerColour().equals(ColourEnum.BLACK)){
            GridPane.setRowIndex(board.getHighlighted(), 7 - (int)event.getSceneY()/board.getPxSquareEdge());
            GridPane.setColumnIndex(board.getHighlighted(), 7 - (int)event.getSceneX()/board.getPxSquareEdge());
        }
        else{
            GridPane.setRowIndex(board.getHighlighted(), (int)event.getSceneY()/board.getPxSquareEdge());
            GridPane.setColumnIndex(board.getHighlighted(), (int)event.getSceneX()/board.getPxSquareEdge());
        }
    }

    public void highlightMovement(MouseEvent e){
        if(boundsCheck(e.getX(), e.getY())){
            GridPane.setRowIndex(board.getHighlighted(), (int)e.getY()/board.getPxSquareEdge());
            GridPane.setColumnIndex(board.getHighlighted(), (int)e.getX()/board.getPxSquareEdge());
        }
    }

    public boolean boundsCheck(double xCoord, double yCoord){
        return xCoord > 0 && xCoord < board.getPxSideLength() && yCoord > 0 && yCoord < board.getPxSideLength();
    }

    public boolean isValidDrop(Point coords, MouseEvent e){
        if(!boundsCheck(e.getSceneX(), e.getSceneY())) return false;
        return board.getPlayerColour().equals(ColourEnum.BLACK) ?
                7 - coords.getX() == (int)e.getSceneX()/board.getPxSquareEdge() && 7 - coords.getY() == (int)e.getSceneY()/this.board.getPxSquareEdge() :
                coords.getX() == (int)e.getSceneX()/board.getPxSquareEdge() && coords.getY() == (int)e.getSceneY()/this.board.getPxSquareEdge();
    }
}
