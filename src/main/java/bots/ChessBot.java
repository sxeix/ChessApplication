package bots;

import enums.ColourEnum;

public abstract class ChessBot {

    private ColourEnum colour;

    public ChessBot(ColourEnum c) {
        this.colour = c;
    }

    public abstract void makeMove();
}
