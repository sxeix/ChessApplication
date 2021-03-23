import javafx.util.Pair;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
public class MoveValidator {

    //being lazy and cba to fill out the rest of these lol. you can see what I've tried to do though
    public ArrayList<Pair> initiateValidate(ChessPiece p, ArrayList<ChessPiece> pieces){
        switch(p.getType()){
            case ROOK:
                return legalRookMoves(p, pieces);
            case BISHOP:
                return legalBishopMoves(p, pieces);
        }
        return legalRookMoves(p, pieces);
    }

    public ArrayList<Pair> legalRookMoves(ChessPiece p, ArrayList<ChessPiece> pieces){
        ArrayList<Pair> coords= new ArrayList<>();
        coords.add(new Pair<>(5, 2));
        coords.add(new Pair<>(5, 3));
        coords.add(new Pair<>(5, 4));
        coords.add(new Pair<>(5, 5));
        return coords;
    }

    public ArrayList<Pair> legalBishopMoves(ChessPiece p, ArrayList<ChessPiece> pieces){
        ArrayList<Pair> coords= new ArrayList<>();
        coords.add(new Pair<>(3, 2));
        coords.add(new Pair<>(3, 3));
        coords.add(new Pair<>(3, 4));
        coords.add(new Pair<>(3, 5));
        return coords;
    }

}
