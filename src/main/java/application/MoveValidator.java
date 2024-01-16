package application;

import components.ChessPieceComponent.ChessPiece;
import enums.ColourEnum;
import enums.PieceEnum;
import lombok.NoArgsConstructor;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

@NoArgsConstructor
public class MoveValidator {

    //being lazy and cba to fill out the rest of these lol. you can see what I've tried to do though
    public void calculateLegalMoves(ChessPiece p, ArrayList<ChessPiece> pieces) {
        switch (p.getType()) {
            case ROOK -> p.setPotentialMoves(legalRookMoves(p, pieces));
            case BISHOP -> p.setPotentialMoves(legalBishopMoves(p, pieces));
            case PAWN -> p.setPotentialMoves(legalPawnMoves(p, pieces));
            case KNIGHT -> p.setPotentialMoves(legalKnightMoves(p, pieces));
            case QUEEN -> p.setPotentialMoves(legalQueenMoves(p, pieces));
            case KING -> p.setPotentialMoves(legalKingMoves(p, pieces));
        }
    }

    public ArrayList<Point> legalRookMoves(ChessPiece rook, ArrayList<ChessPiece> pieces) {
        int x = rook.getXCoord(); int y = rook.getYCoord();
        ArrayList<Point> moves = new ArrayList<>();
        for(int i = 1; i < 8; i++){
            moves.add(new Point(x + i, y)); moves.add(new Point(x - i, y));
            moves.add(new Point(x, y + i)); moves.add(new Point(x, y - i));
        }
        pieces.stream()
                .filter(potentialMove -> moves.contains(potentialMove.getCurrentPos()))
                .forEach(invalidMove -> {
                    if (invalidMove.getColour() == rook.getColour()) moves.remove(invalidMove.getCurrentPos());
                    if (invalidMove.getXCoord().equals(x)) {
                        if (invalidMove.getYCoord() > y) {
                            for (int i = invalidMove.getYCoord() + 1; i < 8; i++) moves.remove(new Point(x, i));
                        } else {
                            for (int i = invalidMove.getYCoord() - 1; i >= 0; i--) moves.remove(new Point(x, i));
                        }
                    } else {
                        if (invalidMove.getXCoord() > x) {
                            for (int i = invalidMove.getXCoord() + 1; i < 8; i++) moves.remove(new Point(i, y));
                        } else {
                            for (int i = invalidMove.getXCoord() - 1; i >= 0; i--) moves.remove(new Point(i, y));
                        }
                    }
                });
        return removeIllegalPositions(moves);
    }

    public ArrayList<Point> legalBishopMoves(ChessPiece bishop, ArrayList<ChessPiece> pieces) {
        int x = bishop.getXCoord(); int y = bishop.getYCoord();
        ArrayList<Point> moves = new ArrayList<>();
        for(int i = 1; i < 8; i++){
            moves.add(new Point(x + i, y + i)); moves.add(new Point(x - i, y - i));
            moves.add(new Point(x - i, y + i)); moves.add(new Point(x + i, y - i));
        }
        pieces.stream()
                .filter(potentialMove -> moves.contains(potentialMove.getCurrentPos()))
                .forEach(invalidMove -> {
                    if (invalidMove.getColour() == bishop.getColour()) moves.remove(invalidMove.getCurrentPos());
                    if (invalidMove.getXCoord() > x) {
                        if (invalidMove.getYCoord() > y) {
                            for (int i = invalidMove.getYCoord() + 1, j = invalidMove.getXCoord() + 1; i < 8 && j < 8; i++, j++)
                                moves.remove(new Point(j, i));
                        } else {
                            for (int i = invalidMove.getYCoord() - 1, j = invalidMove.getXCoord() + 1; i >= 0 && j < 8; i--, j++)
                                moves.remove(new Point(j, i));
                        }
                    } else {
                        if (invalidMove.getYCoord() > y) {
                            for (int i = invalidMove.getYCoord() + 1, j = invalidMove.getXCoord() - 1; i < 8 && j >= 0; i++, j--)
                                moves.remove(new Point(j, i));
                        } else {
                            for (int i = invalidMove.getYCoord() - 1, j = invalidMove.getXCoord() - 1; i >= 0 && j >= 0; i--, j--)
                                moves.remove(new Point(j, i));
                        }
                    }
                });
        return removeIllegalPositions(moves);
    }

    public ArrayList<Point> legalQueenMoves(ChessPiece queen, ArrayList<ChessPiece> pieces) {
        ArrayList<Point> moves = new ArrayList<>();
        moves.addAll(legalBishopMoves(queen, pieces));
        moves.addAll(legalRookMoves(queen, pieces));
        return moves;
    }

    /**
     * Conditions:
     * Cannot move to a position if a piece of same colour is already there
     * Cannot move to a point if it is currently under threat by the other pieces of alternate colour
     * --- Need to update this method for edge cases such as castling ---
     */
    public ArrayList<Point> legalKingMoves(ChessPiece king, ArrayList<ChessPiece> pieces) {
        int x = king.getXCoord(); int y = king.getYCoord();
        // There is probably a much nicer way to do this than what I have come up with, however this can be refactored once testing is in place
        var dangerousMoves = calculateThreatMoves(pieces, king.getColour());
        final var potentialMoves = new ArrayList<Point>();
        for (int i = x - 1; i < x + 2; i++)
            for (int j = y - 1; j < y + 2; j++)
                if (!(i == king.getXCoord() && j == king.getYCoord()))
                    potentialMoves.add(new Point(i,j));

        dangerousMoves.forEach(potentialMoves::remove);
        pieces.stream()
                .filter(potentialMove -> potentialMoves.contains(potentialMove.getCurrentPos()) && potentialMove.getColour() == king.getColour())
                .forEach(invalidMove -> potentialMoves.remove(invalidMove.getCurrentPos()));
        ChessPiece otherKing = pieces.stream()
                .filter(piece -> piece.getType() == PieceEnum.KING && king.getColour() != piece.getColour())
                .findFirst()
                .orElse(null);
        ArrayList<Point> invalidMoves = new ArrayList<>();
        potentialMoves.forEach(potentialMove ->{
                    for (int i = (int)potentialMove.getX() - 1; i < (int)potentialMove.getX() + 2; i++)
                        for (int j = (int)potentialMove.getY() - 1; j < (int)potentialMove.getY() + 2; j++)
                            if (otherKing.getCurrentPos().equals(new Point(i, j))) invalidMoves.add(potentialMove);
                });
        potentialMoves.removeAll(invalidMoves);

        return removeIllegalPositions(potentialMoves);
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
        return removeIllegalPositions(moves);
    }

    public ArrayList<Point> allPawnCaptureMoves(ChessPiece p) {
        ArrayList<Point> captureMoves = new ArrayList<>();
        captureMoves.add(new Point(p.getXCoord() - 1, (p.getColour() == ColourEnum.WHITE ? p.getYCoord() - 1 : p.getYCoord() + 1)));
        captureMoves.add(new Point(p.getXCoord() + 1, (p.getColour() == ColourEnum.WHITE ? p.getYCoord() - 1 : p.getYCoord() + 1)));
        return removeIllegalPositions(captureMoves);
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
        return removeIllegalPositions(resolveLegalMoves(moves, illegalMoves));
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

    public ArrayList<Point> removeIllegalPositions(ArrayList<Point> moves){
        moves.removeIf(this::isOffGrid);
        return moves;
    }

    public boolean isOffGrid(Point move){
        return move.getX() > 7 || move.getX() < 0 || move.getY() > 7 || move.getY() < 0;
    }

    public ArrayList<Point> calculateThreatMoves(ArrayList<ChessPiece> pieces, ColourEnum curMoveColour) {
        ArrayList<Point> threatMoves = new ArrayList<>();
        pieces.stream()
                .filter(piece -> piece.getColour() != curMoveColour)
                .forEach(piece -> {
                    switch (piece.getType()) {
                        case ROOK -> threatMoves.addAll(legalRookMoves(piece, pieces));
                        case BISHOP -> threatMoves.addAll(legalBishopMoves(piece, pieces));
                        case PAWN -> threatMoves.addAll(allPawnCaptureMoves(piece));
                        case KNIGHT -> threatMoves.addAll(legalKnightMoves(piece, pieces));
                        case QUEEN -> threatMoves.addAll(legalQueenMoves(piece, pieces));
                        default -> System.out.println("Invalid piece type");
                    }
                });
        return new ArrayList<>(threatMoves);
    }
}
