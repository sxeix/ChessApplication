package bots;

import enums.ColourEnum;
import javafx.util.Pair;
import main.ChessPiece;
import main.MoveValidator;

import java.awt.*;
import java.util.ArrayList;

public abstract class ChessBot{

    protected ColourEnum colour;

    public ChessBot(ColourEnum c) {
        this.colour = c;
    }

    public abstract Pair<ChessPiece, Point> makeMove(ArrayList<ChessPiece> pieces, MoveValidator validator);
}
