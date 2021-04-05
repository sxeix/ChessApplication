package bots;
import enums.ColourEnum;
import lombok.SneakyThrows;
import java.util.concurrent.TimeUnit;

public class RandomBot extends ChessBot {

    public RandomBot(ColourEnum colour) {
        super(colour);
    }

    @Override
    public void makeMove() {
        System.out.println("random move turn");
    }

}
