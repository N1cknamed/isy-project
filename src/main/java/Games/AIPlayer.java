package Games;

import Ai.TttAI;

public class AIPlayer implements Player {

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public int[] makeMove(Tictactoe game) {
        TttAI ai;
        if (game.getPlayer() == 'x') {
            ai = new TttAI('x', 'o');
        } else {
            ai = new TttAI('o', 'x');
        }
        return ai.findBestMove(game);
    }

    @Override
    public void updateBoard(Tictactoe game) {
    }
}
