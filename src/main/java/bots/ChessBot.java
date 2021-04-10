package bots;

import enums.ColourEnum;
import javafx.util.Pair;
import components.ChessPieceComponent.ChessPiece;
import application.MoveValidator;
import java.awt.*;
import java.util.ArrayList;

/**
 * This is an abstract class of a bot.
 *
 * To create a new bot just do
 *      public class ...Bot extends ChessBot {
 * and just implement the methods that are required.
 *
 * A new bot can then be added to the application without altering any other code.
 */
public abstract class ChessBot{

    protected ColourEnum colour;

    public ChessBot(ColourEnum c) {
        this.colour = c;
    }

    /**
     * This method takes the current board state and calculates a move. Null is returned if there are no moves left.
     *
     * @param pieces that are on the board in this current state
     * @param validator that can be used to get the potential moves
     * @return A pair object containing the ChessPiece to be moved and the coordinates of it
     */
    public abstract Pair<ChessPiece, Point> makeMove(ArrayList<ChessPiece> pieces, MoveValidator validator);
}
