package ttt.players;

import java.awt.Point;

import framework.Game;
import framework.Player;
import framework.PlayerType;
import ttt.TttAi;

public class TttAiPlayer implements Player {

    private final char symbol;
    private final TttAi ai;

    public TttAiPlayer(char symbol) {
        this.symbol = symbol;
        this.ai = new TttAi(symbol, symbol == 'x' ? 'o' : 'x');
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.AI;
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
