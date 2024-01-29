package battleship.subscribers;

import battleship.BattleshipGame;
import framework.Game;
import framework.GameSubscriber;
import framework.Player;
import gui.BattleShipsGui;

import java.awt.*;

public class BattleshipGuiSubscriber implements GameSubscriber {
    @Override
    public void onGameStarted(Game game) {

    }

    @Override
    public void onGameUpdated(Game game) {
        BattleShipsGui.updateButtonsFromOutside(game);
    }

    @Override
    public void onGameEnded(Game game) {

    }

    @Override
    public void onPlayerMove(Player player, Point move) {

    }
}
