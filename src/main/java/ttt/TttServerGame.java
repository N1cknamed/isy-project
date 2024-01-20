package ttt;

import framework.Player;
import framework.server.ServerGame;
import framework.server.ServerGameController;
import framework.server.communication.Response;

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
    public Player getLoser() {
        return getAllPlayers().stream().filter(p -> p != winner).findFirst().orElseThrow();
    }

    @Override
    public void handleServerResponse(Response response) {
    }

    @Override
    public boolean prePlayerMove(Player player) {
        return true;
    }
}
