import enums.ColourEnum;
import enums.PieceEnum;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public class MoveValidatorTest {
// --- Use these points and uncomment ones that you wish to expect from the validation method ---
//    final var points = new Point[]{
//            new Point(
//                    piece.getXCoord(),
//                    (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 1 : piece.getYCoord() + 1)
//            ),
//            new Point(
//                    piece.getXCoord(),
//                    (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 2 : piece.getYCoord() + 2)
//            ),
//                new Point(
//                        (piece.getColour() == ColourEnum.WHITE ? piece.getXCoord() + 1 : piece.getXCoord() - 1),
//                        (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 1 : piece.getYCoord() + 1)
//                ),
//            new Point(
//                    (piece.getColour() == ColourEnum.WHITE ? piece.getXCoord() - 1 : piece.getXCoord() + 1),
//                    (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 1 : piece.getYCoord() + 1)
//            )
//    };
// PAWN VALIDATION TESTS
    @Test
    public void pawn_front_both_test() {
        final var validator = new MoveValidator();
        final ChessPiece[] piecesArr = {
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75)
        };
        var piece = new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75);
        final var pieces = new ArrayList<ChessPiece>(Arrays.asList(piecesArr));
        final var points = new Point[]{
                new Point(
                        piece.getXCoord(),
                        (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 1 : piece.getYCoord() + 1)
                ),
                new Point(
                        piece.getXCoord(),
                        (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 2 : piece.getYCoord() + 2)
                )
        };
        assertThat(validator.legalPawnMoves(piece, pieces), containsInAnyOrder(points));
    }

    @Test
    public void pawn_front_one_test() {
        final var validator = new MoveValidator();
        final ChessPiece[] piecesArr = {
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75),
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 6, 75)
        };
        var piece = new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75);
        final var pieces = new ArrayList<ChessPiece>(Arrays.asList(piecesArr));
        final var points = new Point[]{
                new Point(
                        piece.getXCoord(),
                        (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 1 : piece.getYCoord() + 1)
                )
        };
        assertThat(validator.legalPawnMoves(piece, pieces), containsInAnyOrder(points));
    }

    @Test
    public void pawn_none_test() {
        final var validator = new MoveValidator();
        final ChessPiece[] piecesArr = {
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75),
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 5, 75)
        };
        var piece = new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75);
        final var pieces = new ArrayList<ChessPiece>(Arrays.asList(piecesArr));
        final var points = new Point[]{};
        assertThat(validator.legalPawnMoves(piece, pieces), containsInAnyOrder(points));
    }

    @Test
    public void pawn_side_one_same_test() {
        final var validator = new MoveValidator();
        final ChessPiece[] piecesArr = {
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75),
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 2, 5, 75)
        };
        var piece = new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75);
        final var pieces = new ArrayList<ChessPiece>(Arrays.asList(piecesArr));
        final var points = new Point[]{
                new Point(
                        piece.getXCoord(),
                        (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 1 : piece.getYCoord() + 1)
                ),
                new Point(
                        piece.getXCoord(),
                        (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 2 : piece.getYCoord() + 2)
                )
        };
        assertThat(validator.legalPawnMoves(piece, pieces), containsInAnyOrder(points));
    }

    @Test
    public void pawn_side_one_enemy_test() {
        final var validator = new MoveValidator();
        final ChessPiece[] piecesArr = {
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75),
                new ChessPiece(PieceEnum.PAWN, ColourEnum.WHITE, 2, 5, 75)
        };
        var piece = new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75);
        final var pieces = new ArrayList<ChessPiece>(Arrays.asList(piecesArr));
        final var points = new Point[]{
                new Point(
                        piece.getXCoord(),
                        (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 1 : piece.getYCoord() + 1)
                ),
                new Point(
                        piece.getXCoord(),
                        (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 2 : piece.getYCoord() + 2)
                ),
                new Point(
                        (piece.getColour() == ColourEnum.WHITE ? piece.getXCoord() - 1 : piece.getXCoord() + 1),
                        (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 1 : piece.getYCoord() + 1)
                )
        };
        assertThat(validator.legalPawnMoves(piece, pieces), containsInAnyOrder(points));
    }

    @Test
    public void pawn_after_first_move_test() {
        final var validator = new MoveValidator();
        final ChessPiece[] piecesArr = {
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75)
        };
        var piece = new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 4, 75);
        piece.moveTo(1,5);
        final var pieces = new ArrayList<ChessPiece>(Arrays.asList(piecesArr));
        final var points = new Point[]{
                new Point(
                        piece.getXCoord(),
                        (piece.getColour() == ColourEnum.WHITE ? piece.getYCoord() - 1 : piece.getYCoord() + 1)
                )
        };
        assertThat(validator.legalPawnMoves(piece, pieces), containsInAnyOrder(points));
    }

// KNIGHT VALIDATION TESTS
    @Test
    public void knight_all_moves_test() {
        final var validator = new MoveValidator();
        final var emptyBoardPieces = new ArrayList<ChessPiece>();
        final var knight = new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 4, 4, 75);

        final var points = new Point[]{
                new Point(knight.getXCoord() + 2, knight.getYCoord() - 1),
                new Point(knight.getXCoord() + 2, knight.getYCoord() + 1),
                new Point(knight.getXCoord() - 2, knight.getYCoord() - 1),
                new Point(knight.getXCoord() - 2, knight.getYCoord() + 1),
                new Point(knight.getXCoord() + 1, knight.getYCoord() + 2),
                new Point(knight.getXCoord() - 1, knight.getYCoord() + 2),
                new Point(knight.getXCoord() + 1, knight.getYCoord() - 2),
                new Point(knight.getXCoord() - 1, knight.getYCoord() - 2),
        };

        final var validMoves = validator.legalKnightMoves(knight, emptyBoardPieces);
        assertThat(validMoves, containsInAnyOrder(points));
    }

    @Test
    public void knight_no_moves_test() {
        final var validator = new MoveValidator();
        final var knight = new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 4, 4, 75);

        final var boardPieces = new ChessPiece[]{
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, knight.getXCoord() + 2, knight.getYCoord() - 1, 75),
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, knight.getXCoord() + 2, knight.getYCoord() + 1, 75),
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, knight.getXCoord() - 2, knight.getYCoord() - 1, 75),
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, knight.getXCoord() - 2, knight.getYCoord() + 1, 75),
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, knight.getXCoord() + 1, knight.getYCoord() + 2, 75),
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, knight.getXCoord() - 1, knight.getYCoord() + 2, 75),
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, knight.getXCoord() + 1, knight.getYCoord() - 2, 75),
                new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, knight.getXCoord() - 1, knight.getYCoord() - 2, 75),
        };

        final var pieces = new ArrayList<>(Arrays.asList(boardPieces));
        final var points = new Point[]{};
        final var validMoves = validator.legalKnightMoves(knight, pieces);
        assertThat(validMoves, containsInAnyOrder(points));
    }

}
