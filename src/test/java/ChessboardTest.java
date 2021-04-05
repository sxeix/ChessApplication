import bots.RandomBot;
import enums.ColourEnum;
import main.Chessboard;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ChessboardTest {

    @Test
    public void init_board_test_black() {
        Chessboard testBoard = new Chessboard(600, ColourEnum.BLACK, new RandomBot(ColourEnum.WHITE));
        testBoard.initBoard();
        assertThat(testBoard.getBoard(), notNullValue());
        assertThat(testBoard.getPlayerColour(), is(ColourEnum.BLACK));
    }

    @Test
    public void init_board_test_white() {
        Chessboard testBoard = new Chessboard(600, ColourEnum.WHITE, new RandomBot(ColourEnum.BLACK));
        testBoard.initBoard();
        assertThat(testBoard.getBoard(), notNullValue());
        assertThat(testBoard.getPlayerColour(), is(ColourEnum.WHITE));
    }

}
