package bots;

import enums.ColourEnum;
import enums.PieceEnum;
import components.ChessPieceComponent.ChessPiece;
import application.MoveValidator;
import org.junit.Test;
import java.awt.*;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChessBotTest {

    private final RandomBot randomBlackBot = new RandomBot(ColourEnum.BLACK);
    private final RandomBot randomWhiteBot = new RandomBot(ColourEnum.WHITE);

    @Test
    public void random_black_bot_valid_move_test() {
        final var validator = new MoveValidator();
        final var boardPieces = new ArrayList<ChessPiece>();

        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 3, 4, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 5, 4, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 4, 3, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 4, 5, 75));

        final var move = randomBlackBot.makeMove(boardPieces, validator);

        assertThat(move, notNullValue());
        assertThat(move.getKey(), instanceOf(ChessPiece.class));
        assertThat(move.getValue(), instanceOf(Point.class));
    }

}
