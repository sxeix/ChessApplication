import enums.ColourEnum;
import enums.PieceEnum;
import org.junit.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

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
        assertThat(validator.legalPawnMoves(piece, pieces).size(), equalTo(points.length));
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
        assertThat(validator.legalPawnMoves(piece, pieces).size(), equalTo(points.length));
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
        assertThat(validator.legalPawnMoves(piece, pieces).size(), equalTo(points.length));
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
        assertThat(validator.legalPawnMoves(piece, pieces).size(), equalTo(points.length));
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
        assertThat(validator.legalPawnMoves(piece, pieces).size(), equalTo(points.length));
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
        assertThat(validator.legalPawnMoves(piece, pieces).size(), equalTo(points.length));
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
        assertThat(validMoves.size(), equalTo(points.length));
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
        assertThat(validMoves.size(), equalTo(points.length));
    }

    // KING VALIDATION TESTS
    @Test
    public void king_all_moves_test() {
        final var validator = new MoveValidator();
        final var boardPieces = new ArrayList<ChessPiece>();
        final var king = new ChessPiece(PieceEnum.KING, ColourEnum.BLACK, 2, 2, 75);
        final var secondKing = new ChessPiece(PieceEnum.KING, ColourEnum.WHITE, 6, 6, 75);
        boardPieces.add(secondKing);

        final var points = new Point[]{
                new Point(king.getXCoord(), king.getYCoord() - 1),
                new Point(king.getXCoord(), king.getYCoord() + 1),
                new Point(king.getXCoord() - 1, king.getYCoord() + 1),
                new Point(king.getXCoord() - 1, king.getYCoord()),
                new Point(king.getXCoord() - 1, king.getYCoord() - 1),
                new Point(king.getXCoord() + 1, king.getYCoord() + 1),
                new Point(king.getXCoord() + 1, king.getYCoord()),
                new Point(king.getXCoord() + 1, king.getYCoord() - 1)
        };

        final var validMoves = validator.legalKingMoves(king, boardPieces);
        assertThat(validMoves, containsInAnyOrder(points));
        assertThat(validMoves.size(), equalTo(points.length));
    }

    @Test
    public void king_no_moves_test() {
        final var validator = new MoveValidator();
        final var boardPieces = new ArrayList<ChessPiece>();
        final var king = new ChessPiece(PieceEnum.KING, ColourEnum.BLACK, 2, 2, 75);
        final var secondKing = new ChessPiece(PieceEnum.KING, ColourEnum.WHITE, 7, 7, 75);

        boardPieces.add(secondKing);
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 1, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 2, 1, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 3, 1, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 2, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 3, 2, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 1, 3, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 2, 3, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 3, 3, 75));

        final var points = new Point[]{};

        final var validMoves = validator.legalKingMoves(king, boardPieces);
        assertThat(validMoves, containsInAnyOrder(points));
        assertThat(validMoves.size(), equalTo(points.length));
    }

    @Test
    public void king_mixed_moves_test() {
//        Will test if a pawn contests a point, or if a point is under threat by another piece
        final var validator = new MoveValidator();
        final var boardPieces = new ArrayList<ChessPiece>();
        final var king = new ChessPiece(PieceEnum.KING, ColourEnum.BLACK, 2, 2, 75);
        final var secondKing = new ChessPiece(PieceEnum.KING, ColourEnum.WHITE, 6, 6, 75);

        boardPieces.add(secondKing);
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.WHITE, 3, 4, 75));
        boardPieces.add(new ChessPiece(PieceEnum.BISHOP, ColourEnum.WHITE, 3, 5, 75));

        final var points = new Point[]{
                new Point(king.getXCoord(), king.getYCoord() - 1),
                new Point(king.getXCoord() - 1, king.getYCoord()),
                new Point(king.getXCoord() - 1, king.getYCoord() - 1),
                new Point(king.getXCoord() + 1, king.getYCoord() + 1),
                new Point(king.getXCoord() + 1, king.getYCoord()),
                new Point(king.getXCoord() + 1, king.getYCoord() - 1)
        };

        final var validMoves = validator.legalKingMoves(king, boardPieces);
        assertThat(validMoves, containsInAnyOrder(points));
        assertThat(validMoves.size(), equalTo(points.length));
    }


    // ROOK VALIDATION TESTS
    @Test
    public void rook_all_moves_test() {
        final var validator = new MoveValidator();
        final var emptyBoardPieces = new ArrayList<ChessPiece>();
        final var rook = new ChessPiece(PieceEnum.ROOK, ColourEnum.BLACK, 4, 4, 75);

        final var points = new Point[]{
                new Point(rook.getXCoord(), rook.getYCoord() - 4),
                new Point(rook.getXCoord(), rook.getYCoord() - 3),
                new Point(rook.getXCoord(), rook.getYCoord() - 2),
                new Point(rook.getXCoord(), rook.getYCoord() - 1),

                new Point(rook.getXCoord(), rook.getYCoord() + 3),
                new Point(rook.getXCoord(), rook.getYCoord() + 2),
                new Point(rook.getXCoord(), rook.getYCoord() + 1),

                new Point(rook.getXCoord() - 4, rook.getYCoord()),
                new Point(rook.getXCoord() - 3, rook.getYCoord()),
                new Point(rook.getXCoord() - 2, rook.getYCoord()),
                new Point(rook.getXCoord() - 1, rook.getYCoord()),

                new Point(rook.getXCoord() + 3, rook.getYCoord()),
                new Point(rook.getXCoord() + 2, rook.getYCoord()),
                new Point(rook.getXCoord() + 1, rook.getYCoord()),
        };

        final var validMoves = validator.legalRookMoves(rook, emptyBoardPieces);
        assertThat(validMoves, containsInAnyOrder(points));
        assertThat(validMoves.size(), equalTo(points.length));
    }

    @Test
    public void rook_few_moves_test() {
        final var validator = new MoveValidator();
        final var boardPieces = new ArrayList<ChessPiece>();
        final var rook = new ChessPiece(PieceEnum.ROOK, ColourEnum.BLACK, 4, 4, 75);

        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.WHITE, 3, 4, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.WHITE, 5, 4, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.WHITE, 4, 3, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.WHITE, 4, 5, 75));

        final var points = new Point[]{
                new Point(rook.getXCoord(), rook.getYCoord() - 1),
                new Point(rook.getXCoord(), rook.getYCoord() + 1),
                new Point(rook.getXCoord() - 1, rook.getYCoord()),
                new Point(rook.getXCoord() + 1, rook.getYCoord())
        };

        final var validMoves = validator.legalRookMoves(rook, boardPieces);
        assertThat(validMoves, containsInAnyOrder(points));
        assertThat(validMoves.size(), equalTo(points.length));
    }

    @Test
    public void rook_no_moves_test() {
        final var validator = new MoveValidator();
        final var boardPieces = new ArrayList<ChessPiece>();
        final var rook = new ChessPiece(PieceEnum.ROOK, ColourEnum.BLACK, 4, 4, 75);

        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 3, 4, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 5, 4, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 4, 3, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 4, 5, 75));

        final var points = new Point[]{};

        final var validMoves = validator.legalRookMoves(rook, boardPieces);
        assertThat(validMoves, containsInAnyOrder(points));
        assertThat(validMoves.size(), equalTo(points.length));
    }


    // BISHOP VALIDATION TESTS
    @Test
    public void bishop_all_moves_test() {
        final var validator = new MoveValidator();
        final var emptyBoardPieces = new ArrayList<ChessPiece>();
        final var bishop = new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 4, 4, 75);

        final var points = new Point[]{
                new Point(bishop.getXCoord() - 4, bishop.getYCoord() - 4),
                new Point(bishop.getXCoord() - 3, bishop.getYCoord() - 3),
                new Point(bishop.getXCoord() - 2, bishop.getYCoord() - 2),
                new Point(bishop.getXCoord() - 1, bishop.getYCoord() - 1),

                new Point(bishop.getXCoord() - 3, bishop.getYCoord() + 3),
                new Point(bishop.getXCoord() - 2, bishop.getYCoord() + 2),
                new Point(bishop.getXCoord() - 1, bishop.getYCoord() + 1),

                new Point(bishop.getXCoord() + 3, bishop.getYCoord() - 3),
                new Point(bishop.getXCoord() + 2, bishop.getYCoord() - 2),
                new Point(bishop.getXCoord() + 1, bishop.getYCoord() - 1),

                new Point(bishop.getXCoord() + 3, bishop.getYCoord() + 3),
                new Point(bishop.getXCoord() + 2, bishop.getYCoord() + 2),
                new Point(bishop.getXCoord() + 1, bishop.getYCoord() + 1),
        };

        final var validMoves = validator.legalBishopMoves(bishop, emptyBoardPieces);
        assertThat(validMoves, containsInAnyOrder(points));
        assertThat(validMoves.size(), equalTo(points.length));
    }

    @Test
    public void bishop_no_moves_test() {
        final var validator = new MoveValidator();
        final var boardPieces = new ArrayList<ChessPiece>();
        final var bishop = new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 4, 4, 75);

        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 5, 5, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 5, 3, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 3, 5, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 3, 3, 75));

        final var points = new Point[]{};

        final var validMoves = validator.legalBishopMoves(bishop, boardPieces);
        assertThat(validMoves, containsInAnyOrder(points));
        assertThat(validMoves.size(), equalTo(points.length));
    }

    @Test
    public void bishop_few_moves_test() {
        final var validator = new MoveValidator();
        final var boardPieces = new ArrayList<ChessPiece>();
        final var bishop = new ChessPiece(PieceEnum.PAWN, ColourEnum.BLACK, 4, 4, 75);

        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.WHITE, 5, 5, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.WHITE, 5, 3, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.WHITE, 3, 5, 75));
        boardPieces.add(new ChessPiece(PieceEnum.PAWN, ColourEnum.WHITE, 3, 3, 75));

        final var points = new Point[]{
                new Point(bishop.getXCoord() - 1, bishop.getYCoord() - 1),
                new Point(bishop.getXCoord() - 1, bishop.getYCoord() + 1),
                new Point(bishop.getXCoord() + 1, bishop.getYCoord() - 1),
                new Point(bishop.getXCoord() + 1, bishop.getYCoord() + 1),
        };

        final var validMoves = validator.legalBishopMoves(bishop, boardPieces);
        assertThat(validMoves, containsInAnyOrder(points));
        assertThat(validMoves.size(), equalTo(points.length));
    }

}
