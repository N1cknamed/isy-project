package framework;

import java.awt.*;

public interface GameSubscriber {
    void onGameStarted(Game game);
    void onGameUpdated(Game game);
    void onGameEnded(Game game);
    void onPlayerMove(Player player, Point move);
}
