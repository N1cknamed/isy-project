package ttt;

import java.awt.Point;

import Framework.Game;
import Framework.Player;

public class TttAIPlayer implements Player {

    private final char symbol;
    private final TttAI ai;

    public TttAIPlayer(char symbol) {
        this.symbol = symbol;
        this.ai = new TttAI(symbol, symbol == 'x' ? 'o' : 'x');
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Point doMove(Game game) {
        int[] temp = ai.findBestMove(game);
        return new Point(temp[1], temp[0]);
    }


    
}
