import enums.ColourEnum;
import enums.PieceEnum;
import main.ChessPiece;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChessPieceTest {

    @Test
    public void create_piece_test() {
        ChessPiece queen = new ChessPiece(PieceEnum.QUEEN, ColourEnum.WHITE,6,4, 75);
        assertThat(queen.getColourString(), equalTo("white"));
        assertThat(queen.getPieceString(), equalTo("queen"));
        assertThat(queen.getType(), equalTo(PieceEnum.QUEEN));
        assertThat(queen.getColour(), equalTo(ColourEnum.WHITE));
        assertThat(queen.getXCoord(), equalTo(6));
        assertThat(queen.getYCoord(), equalTo(4));
    }

}
