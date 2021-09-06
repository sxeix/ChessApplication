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

public class RandomAggressiveBot extends ChessBot {

    public RandomAggressiveBot(ColourEnum c) {
        super(c);
    }

    @Override
    public Pair<ChessPiece, Point> makeMove(ArrayList<ChessPiece> pieces, MoveValidator validator) {
        System.out.println("randAggroBot move turn");
        final var rand = new Random();

        final var botPieces = pieces
                .stream()
                .filter(p -> p.getColour().equals(this.colour))
                .collect(Collectors.toList());
        ArrayList<Pair<ChessPiece, ArrayList<Point>>> pieceMoveList = new ArrayList<>();

        for (var piece : botPieces) {
            validator.calculateLegalMoves(piece, pieces);
            if (piece.getPotentialMoves().size() != 0) {
                Pair<ChessPiece, ArrayList<Point>> pair = new Pair<>(piece, piece.getPotentialMoves());
                pieceMoveList.add(pair);
            }
        }

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
