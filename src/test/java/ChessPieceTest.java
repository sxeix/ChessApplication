import enums.ColourEnum;
import enums.PieceEnum;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChessPieceTest {

    @Test
    public void create_piece_test() {
        ChessPiece queen = new ChessPiece(PieceEnum.QUEEN, ColourEnum.WHITE,6,4, 75);
    }

}
