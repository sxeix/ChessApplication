import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChessboardTest {

    @Test
    public void init_board_test() {
        Chessboard testBoard = new Chessboard(600);
        assertThat(testBoard, notNullValue());
    }

}
