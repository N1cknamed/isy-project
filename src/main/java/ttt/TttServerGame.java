package ttt;

import framework.Player;
import framework.server.ServerGame;
import framework.server.ServerGameController;
import framework.server.ServerPlayer;
import server.Response;
import server.ServerController;

public class TttServerGame extends TttGame implements ServerGame {

    private boolean hasEnded = false;
    private Player winner = null;

    public TttServerGame(ServerGameController connection) {
        super();
    }

    @Override
    public boolean hasEnded() {
        return hasEnded;
    }

    @Override
    public void forceWin(Player winner) {
        this.winner = winner;
        hasEnded = true;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public void handleServerResponse(Response response) {
        System.out.println("Received and ignoring an unhandled server response (" + response.getCommand() + ")");
    }
}
