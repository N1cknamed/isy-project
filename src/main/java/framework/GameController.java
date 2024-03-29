package framework;

import java.awt.*;
import java.util.ArrayList;

public class GameController {
    private ArrayList<GameSubscriber> subscribers = new ArrayList<>();
    private Game game;
    private PlayerFactory playerFactory1;
    private PlayerFactory playerFactory2;

    public GameController(Game game, PlayerFactory playerFactory1, PlayerFactory playerFactory2) {
        this.game = game;
        this.playerFactory1 = playerFactory1;
        this.playerFactory2 = playerFactory2;
    }

    public void gameLoop() {
        game.start(playerFactory1, playerFactory2);

        for (GameSubscriber i : subscribers) {
            i.onGameStarted(game);
        }

        while(!game.hasEnded()){
            Player currentPlayer = game.getCurrentPlayer();
            Point move;
            try {
                move = currentPlayer.doMove(game);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (!game.isValidMove(move)) {
                throw new RuntimeException("illegal move (" + move + ")");
            }

            game.doMove(move);

            for (GameSubscriber i : subscribers) {
                i.onGameUpdated(game);
                i.onPlayerMove(currentPlayer, move);
            }
            game.nextPlayer();
        }

        for (GameSubscriber i : subscribers) {
            i.onGameEnded(game);
        }
    }

    public void registerSubscriber(GameSubscriber subscriber) {
        subscribers.add(subscriber);
    }
}
