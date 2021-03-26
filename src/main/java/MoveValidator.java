import enums.ColourEnum;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@NoArgsConstructor
public class MoveValidator {

    //being lazy and cba to fill out the rest of these lol. you can see what I've tried to do though
    public void calculateLegalMoves(ChessPiece p, ArrayList<ChessPiece> pieces) {
        switch (p.getType()) {
            case ROOK -> p.setPotentialMoves(legalRookMoves(p, pieces));
            case BISHOP -> p.setPotentialMoves(legalBishopMoves(p, pieces));
            case PAWN -> p.setPotentialMoves(legalPawnMoves(p, pieces));
            case KNIGHT -> p.setPotentialMoves(legalKnightMoves(p, pieces));
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
     * Cannot move to a position if a piece of same colour is already there
     */
    public ArrayList<Point> legalKnightMoves(ChessPiece knight, ArrayList<ChessPiece> pieces) {
        int x = knight.getXCoord(); int y = knight.getYCoord();
        final var potentialMoves = new Point[]{
                new Point(x + 2, y - 1), new Point(x + 2, y + 1),
                new Point(x - 2, y - 1), new Point(x - 2, y + 1),
                new Point(x + 1, y + 2), new Point(x - 1, y + 2),
                new Point(x + 1, y - 2), new Point(x - 1, y - 2)
        };
        final var moves = new ArrayList<>(Arrays.asList(potentialMoves));

        pieces.stream()
                .filter(potentialMove -> moves.contains(potentialMove.getCurrentPos()) && potentialMove.getColour() == knight.getColour())
                .forEach(invalidMove -> moves.remove(invalidMove.getCurrentPos()));
        return moves;
    }

    /**
     * Conditions:
     * If it's past that piece's first move then it cannot move more than 1
     * if piece of any colour is in-front, it can not go forwards at all
     * can only go to the side if piece is other colour
     * can tidy up this messy logic another day, just want to get it working for now
     */
    public ArrayList<Point> legalPawnMoves(ChessPiece p, ArrayList<ChessPiece> pieces) {
        ArrayList<Point> illegalMoves = new ArrayList<>();
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

        pieces.forEach(piece -> {
            if (comparePoints(piece.getCurrentPos(), moves.get(0))) {
                illegalMoves.add(moves.get(0));
                illegalMoves.add(moves.get(1));
            }
            if (comparePoints(piece.getCurrentPos(), moves.get(1))) {
                illegalMoves.add(moves.get(1));
            }
            if (comparePoints(piece.getCurrentPos(), moves.get(2)) && piece.getColour().equals(p.getColour())) {
                illegalMoves.add(moves.get(2));
            }
            if (comparePoints(piece.getCurrentPos(), moves.get(3)) && piece.getColour().equals(p.getColour())) {
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
        if (p.getMoveNum() != 0) {
            illegalMoves.add(moves.get(1));
        }
        return resolveLegalMoves(moves, illegalMoves);
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


    public ArrayList<Point> resolveLegalMoves(ArrayList<Point> potentialMoves, ArrayList<Point> illegalMoves) {
        var legalMoves = new ArrayList<Point>();
        potentialMoves.stream()
                .filter(move -> !illegalMoves.contains(move))
                .forEach(legalMoves::add);
        return legalMoves;
    }
}
