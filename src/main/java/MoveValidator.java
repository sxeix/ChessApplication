import enums.ColourEnum;
import enums.PieceEnum;
import javafx.util.Pair;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
public class MoveValidator {

    //being lazy and cba to fill out the rest of these lol. you can see what I've tried to do though
    public void calculateLegalMoves(ChessPiece p, ArrayList<ChessPiece> pieces) {
        switch (p.getType()) {
            case ROOK -> p.setPotentialMoves(legalRookMoves(p, pieces));
            case BISHOP -> p.setPotentialMoves(legalBishopMoves(p, pieces));
            case PAWN -> p.setPotentialMoves(legalPawnMoves(p, pieces));
            default -> p.setPotentialMoves(legalRookMoves(p, pieces));
        }
    }

    public ArrayList<Point> legalRookMoves(ChessPiece p, ArrayList<ChessPiece> pieces) {
        ArrayList<Point> coords = new ArrayList<>();
        coords.add(new Point(5, 2));
        coords.add(new Point(5, 3));
        coords.add(new Point(5, 4));
        coords.add(new Point(5, 5));
        return coords;
    }

    public ArrayList<Point> legalBishopMoves(ChessPiece p, ArrayList<ChessPiece> pieces) {
        ArrayList<Point> coords = new ArrayList<>();
        coords.add(new Point(3, 2));
        coords.add(new Point(3, 3));
        coords.add(new Point(3, 4));
        coords.add(new Point(3, 5));
        return coords;
    }


    /**
     * Conditions:
     * If it's past that piece's first move then it cannot move more than 1
     * if piece of any colour is in-front, it can not go forwards at all
     * can only go to the side if piece is other colour
     * can tidy up this messy logic another day, just want to get it working for now
     */
    public ArrayList<Point> legalPawnMoves(ChessPiece p, ArrayList<ChessPiece> pieces) {
        // Current piece being moved coordinate
        System.out.println("start " + p.getCurrentPos());
        pieces.stream().filter(x -> x.getColour() == ColourEnum.WHITE && x.getType() == PieceEnum.PAWN).forEach(x -> System.out.print(x.getCurrentPos()));
        HashSet<Point> illegalMoves = new HashSet<>();
        var legalMoves = new ArrayList<Point>();
        final var boardPoints = toPoints(pieces);
        final var potentialMoves = new Point[]{
                new Point(
                        p.getXCoord(),
                        (p.getColour() == ColourEnum.WHITE ? p.getYCoord() - 1 : p.getYCoord() + 1)
                ),
                new Point(
                        p.getXCoord(),
                        (p.getColour() == ColourEnum.WHITE ? p.getYCoord() - 2 : p.getYCoord() + 2)
                ),
                new Point(
                        (p.getColour() == ColourEnum.WHITE ? p.getXCoord() + 1 : p.getXCoord() - 1),
                        (p.getColour() == ColourEnum.WHITE ? p.getYCoord() - 1 : p.getYCoord() + 1)
                ),
                new Point(
                        (p.getColour() == ColourEnum.WHITE ? p.getXCoord() - 1 : p.getXCoord() + 1),
                        (p.getColour() == ColourEnum.WHITE ? p.getYCoord() - 1 : p.getYCoord() + 1)
                )
        };
        final var moves = new ArrayList<Point>(Arrays.asList(potentialMoves));

        System.out.println("the starting moves are");
        moves.forEach(System.out::println);

        pieces.forEach(piece -> {
            if (comparePoints(piece.getCurrentPos(), moves.get(0))) {
                illegalMoves.add(moves.get(0));
                illegalMoves.add(moves.get(1));
            }
            if (comparePoints(piece.getCurrentPos(), moves.get(1))) {
                illegalMoves.add(moves.get(1));
            }
            if(comparePoints(piece.getCurrentPos(), moves.get(2)) && piece.getColour().equals( p.getColour())) {
                illegalMoves.add(moves.get(2));
            }
            if(comparePoints(piece.getCurrentPos(), moves.get(3)) && piece.getColour().equals(p.getColour())) {
                illegalMoves.add(moves.get(3));
            }
        });
        // Is left empty
        if (!boardPoints.contains(moves.get(2))) {
            illegalMoves.add(moves.get(2));
        }
        // Is right empty
        if (!boardPoints.contains(moves.get(3))) {
            illegalMoves.add(moves.get(3));
        }
        // Is it first move
        if(p.getMoveNum() != 0) {
            illegalMoves.add(moves.get(1));
        }

        System.out.println("the illegal moves are");
        illegalMoves.forEach(System.out::println);
        for(Point move: moves) {
            if (!illegalMoves.contains(move)) {
                legalMoves.add(move);
            }
        }
        return legalMoves;
    }

    public ArrayList<Point> toPoints(ArrayList<ChessPiece> arr) {
        var before = new ArrayList<>(arr);
        var after = new ArrayList<Point>();
        before.forEach(x -> after.add(x.getCurrentPos()));
        return after;
    }

    public boolean comparePoints(Point point, Point otherPoint) {
        return point.getX() == otherPoint.getX() && point.getY() == otherPoint.getY();
    }
}
