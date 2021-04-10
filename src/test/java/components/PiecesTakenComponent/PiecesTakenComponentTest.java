package components.PiecesTakenComponent;

import enums.ColourEnum;
import enums.PieceEnum;
import components.ChessPieceComponent.ChessPiece;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PiecesTakenComponentTest {

    @Test
    public void empty() {
        final var pcsTknComponent = new PiecesTakenComponent(ColourEnum.BLACK);
        assertThat(pcsTknComponent.getChildren().size(), equalTo(0));
    }

    @Test
    public void two_pieces() {
        final var pcsTknComponent = new PiecesTakenComponent(ColourEnum.BLACK);
        pcsTknComponent.addPiece(new ChessPiece(PieceEnum.KING, ColourEnum.BLACK, 0,0, 80));
        pcsTknComponent.addPiece(new ChessPiece(PieceEnum.QUEEN, ColourEnum.BLACK, 0,0, 80));
        assertThat(pcsTknComponent.getChildren().size(), equalTo(2));
    }

    @Test
    public void background_enabled() {
        final var pcsTknComponent = new PiecesTakenComponent(ColourEnum.BLACK);
        pcsTknComponent.enableBackground();
        assertThat(pcsTknComponent.getChildren().size(), equalTo(20));
    }

    @Test
    public void two_pieces_background_enabled() {
        final var pcsTknComponent = new PiecesTakenComponent(ColourEnum.BLACK);
        pcsTknComponent.enableBackground();
        pcsTknComponent.addPiece(new ChessPiece(PieceEnum.KING, ColourEnum.BLACK, 0,0, 80));
        pcsTknComponent.addPiece(new ChessPiece(PieceEnum.QUEEN, ColourEnum.BLACK, 0,0, 80));
        assertThat(pcsTknComponent.getChildren().size(), equalTo(22));
    }

}
