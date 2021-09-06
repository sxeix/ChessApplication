package bots;

import application.MoveValidator;
import components.ChessPieceComponent.ChessPiece;
import enums.ColourEnum;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import javafx.util.Pair;

/**
 * This bot only makes random moves with preference to attack
 */
public class RandomAggressiveBot extends ChessBot {

    public RandomAggressiveBot(ColourEnum c) {
        super(c);
    }

    /**
     * This method takes the current board state and calculates a random move but will preference attacking.
     * Null is returned if there are no moves left.
     *
     * @param pieces that are on the board in this current state
     * @param validator that can be used to get the potential moves
     * @return A pair object containing the ChessPiece to be moved and the coordinates of it
     */
    @Override
    public Pair<ChessPiece, Point> makeMove(ArrayList<ChessPiece> pieces, MoveValidator validator) {
        System.out.println("randAggroBot move turn");
        final var rand = new Random();

        final var botPieces = pieces
                .stream()
                .filter(p -> p.getColour().equals(this.colour))
                .collect(Collectors.toList());
        ArrayList<Pair<ChessPiece, ArrayList<Point>>> pieceMoveList = new ArrayList<>();
        
        botPieces.stream()
                .map(p -> {
                    validator.calculateLegalMoves(p, pieces); 
                    return p;
                })
                .filter(p -> p.getPotentialMoves().size() != 0)
                .forEach(p -> {
                    Pair<ChessPiece, ArrayList<Point>> pair = new Pair<>(p, p.getPotentialMoves());
                    pieceMoveList.add(pair);
                });
        
        if (pieceMoveList.size() == 0) return null;

        Collections.shuffle(pieceMoveList);

        for (var pair : pieceMoveList) {
            var piece = pair.getKey();
            var moves = pair.getValue();
            for (var move : moves) {
                if (moveTakesPiece(move, pieces)) {
                    return new Pair(piece, move);
                }
            }
        }

        var fallbackPiece = pieceMoveList.get(rand.nextInt(pieceMoveList.size()));
        return new Pair<>(
                fallbackPiece.getKey(),
                fallbackPiece.getValue().get(rand.nextInt(fallbackPiece.getValue().size()))
        );

    }

    private boolean moveTakesPiece(Point move, ArrayList<ChessPiece> pieces) {
        for (var piece : pieces) {
            if (piece.getCurrentPos().equals(move)) {
                return true;
            }
        }

        return false;
    }

}
