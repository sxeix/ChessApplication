import javafx.util.Pair;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;

@NoArgsConstructor
public class MoveValidator {

    //being lazy and cba to fill out the rest of these lol. you can see what I've tried to do though
    public ArrayList<Point> getValidMoves(ChessPiece p, ArrayList<ChessPiece> pieces){
        switch(p.getType()){
            case ROOK:
                return legalRookMoves(p, pieces);
            case BISHOP:
                return legalBishopMoves(p, pieces);
            default:
                return legalRookMoves(p, pieces);
        }
    }

    public ArrayList<Point> legalRookMoves(ChessPiece p, ArrayList<ChessPiece> pieces){
        ArrayList<Point> coords= new ArrayList<>();
        coords.add(new Point(5, 2));
        coords.add(new Point(5, 3));
        coords.add(new Point(5, 4));
        coords.add(new Point(5, 5));
        return coords;
    }

    public ArrayList<Point> legalBishopMoves(ChessPiece p, ArrayList<ChessPiece> pieces){
        ArrayList<Point> coords= new ArrayList<>();
        coords.add(new Point(3, 2));
        coords.add(new Point(3, 3));
        coords.add(new Point(3, 4));
        coords.add(new Point(3, 5));
        return coords;
    }

}