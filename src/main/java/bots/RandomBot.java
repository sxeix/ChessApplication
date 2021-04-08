package bots;

import enums.ColourEnum;
import javafx.util.Pair;
import main.ChessPiece;
import main.MoveValidator;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This bot only makes random moves
 */
public class RandomBot extends ChessBot {

    public RandomBot(ColourEnum colour) {
        super(colour);
    }


    /**
     * This method takes the current board state and calculates a random move.
     * Null is returned if there are no moves left.
     *
     * @param pieces that are on the board in this current state
     * @param validator that can be used to get the potential moves
     * @return A pair object containing the ChessPiece to be moved and the coordinates of it
     */
    @Override
    public Pair<ChessPiece, Point> makeMove(ArrayList<ChessPiece> pieces, MoveValidator validator) {
        System.out.println("random move turn");
        final var rand = new Random();
        final var botPieces = pieces
                .stream()
                .filter(p -> p.getColour().equals(this.colour))
                .collect(Collectors.toList());
        Collections.shuffle(botPieces);
        for(var piece: botPieces) {
            validator.calculateLegalMoves(piece, pieces);
            var potentialMoves = piece.getPotentialMoves();
            if (potentialMoves.size() != 0) {
                return new Pair<>(piece, potentialMoves.get(rand.nextInt(potentialMoves.size())));
            }
        }
        return null;
    }

}
