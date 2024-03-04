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
        if (game.getCurrentPlayer().getPlayerType().isLocal()) {
            BattleShipsGui.updateButtonsFromOutside(game);
        }
    }

    @Override
    public void onGameUpdated(Game game) {
        if (game.getCurrentPlayer().getPlayerType().isLocal()) {
            BattleShipsGui.updateButtonsFromOutside(game);
        }
    }

    public void onShipPlaced(Game game) {
        if (game.getCurrentPlayer().getPlayerType().isLocal()) {
            BattleShipsGui.updateButtonsFromOutside(game);
        }
    }

    @Override
    public void onGameEnded(Game game) {
        BattleshipGame btlshpgm = (battleship.BattleshipGame) game;
        if(btlshpgm.getWinner() != null){
            BattleShipsGui.updateWinningBoard(btlshpgm);
        }

    }

    @Override
    public void onPlayerMove(Player player, Point move) {

    }
}
