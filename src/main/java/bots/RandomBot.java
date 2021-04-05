package bots;

import enums.ColourEnum;

public class RandomBot extends ChessBot{

    public RandomBot(ColourEnum colour) {
        super(colour);
    }

    @Override
    public void makeMove() {
        System.out.println("random move turn");
    }
}
