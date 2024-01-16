package ttt;

import framework.Game;
import framework.GameSubscriber;
import framework.Player;
import gui.TttGui;

import java.awt.*;

public class TttGuiSubscriber implements GameSubscriber {

    @Override
    public void onGameStarted(Game game) {
        TttGui.updateButtonsFromOutside(game);
    }

    @Override
    public void onGameUpdated(Game game) {
        TttGui.updateButtonsFromOutside(game);
    }

    @Override
    public void onGameEnded(Game game) {
        TttGame tttGame = (TttGame) game;
        if (game.getWinner() != null) {
            TttGui.winningButtonsFromOutside();
        }
    }

    @Override
    public void onPlayerMove(Player player, Point move) {

    }
}
