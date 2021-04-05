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

public class RandomBot extends ChessBot {

    public RandomBot(ColourEnum colour) {
        super(colour);
    }

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
