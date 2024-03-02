package battleship.subscribers;

import framework.Game;
import framework.GameSubscriber;
import framework.Player;
import gui.BattleShipsGui;

import java.awt.*;

public class BattleshipGuiSubscriber implements GameSubscriber {

    @Override
    public void onGameStarted(Game game) {
        if (game.getCurrentPlayer().getPlayerType().isLocal() && game.getCurrentPlayer().getPlayerType().getName() != "GUI") { // != "GUI" because otherwise would only get non GUI players
            BattleShipsGui.updateButtonsFromOutside(game);
        }
    }

    @Override
    public void onGameUpdated(Game game) {
        if (game.getCurrentPlayer().getPlayerType().isLocal() && game.getCurrentPlayer().getPlayerType().getName() != "GUI") {
            BattleShipsGui.updateButtonsFromOutside(game);
        }
    }

    public void onShipPlaced(Game game) {
        if (game.getCurrentPlayer().getPlayerType().isLocal() && game.getCurrentPlayer().getPlayerType().getName() != "GUI") {
            BattleShipsGui.updateButtonsFromOutside(game);
        }
    }

    @Override
    public void onGameEnded(Game game) {

    }

    @Override
    public void onPlayerMove(Player player, Point move) {

    }
}
