package ttt;

import Framework.Game;
import Framework.GameSubscriber;
import Framework.Player;
import Gui.TictactoeGui;

import java.awt.*;

public class TttGuiSubscriber implements GameSubscriber {

    @Override
    public void onGameStarted(Game game) {
        TictactoeGui.updateButtonsFromOutside(game);
    }

    @Override
    public void onGameUpdated(Game game) {
        TictactoeGui.updateButtonsFromOutside(game);
    }

    @Override
    public void onGameEnded(Game game) {
        if (game.getWinner() != null) {
            TictactoeGui.winningButtonsFromOutside(game);
        }
    }

    @Override
    public void onPlayerMove(Player player, Point move) {

    }
}
