import enums.ColourEnum;
import enums.PieceEnum;
import org.junit.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MoveValidatorTest {
// PAWN VALIDATION TESTS
//
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
        final var expectedMoves = new ArrayList<Point>(Arrays.asList(points));
        assertThat(validator.legalPawnMoves(piece, pieces), is(expectedMoves));
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
        final var expectedMoves = new ArrayList<Point>(Arrays.asList(points));
        assertThat(validator.legalPawnMoves(piece, pieces), is(expectedMoves));
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
        final var expectedMoves = new ArrayList<Point>(Arrays.asList(points));
        assertThat(validator.legalPawnMoves(piece, pieces), is(expectedMoves));
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
        final var expectedMoves = new ArrayList<Point>(Arrays.asList(points));
        assertThat(validator.legalPawnMoves(piece, pieces), is(expectedMoves));
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
        final var expectedMoves = new ArrayList<Point>(Arrays.asList(points));
        assertThat(validator.legalPawnMoves(piece, pieces), is(expectedMoves));
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
        final var expectedMoves = new ArrayList<Point>(Arrays.asList(points));
        assertThat(validator.legalPawnMoves(piece, pieces), is(expectedMoves));
    }

}
