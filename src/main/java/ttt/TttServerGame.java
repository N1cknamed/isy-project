package ttt;

import framework.Player;
import framework.ServerGame;
import framework.ServerPlayer;
import server.Response;

public class TttServerGame extends TttGame implements ServerGame {

    private boolean hasEnded = false;
    private Player winner = null;

    @Override
    public String getGameType() {
        return "Tic-tac-toe";
    }

    @Override
    public boolean hasEnded() {
        return hasEnded || super.hasEnded();
    }

    @Override
    public void forceWin(Player winner) {
        this.winner = winner;
        hasEnded = true;
    }

    @Override
    public Player getWinner() {
        if (winner == null) {
            return super.getWinner();
        } else {
            return winner;
        }
    }

    @Override
    public Player getLocalPlayer() {
        return getAllPlayers().stream()
                .filter(p -> p.getPlayerType().isLocal())
                .findAny()
                .orElseThrow();
    }

    @Override
    public ServerPlayer getServerPlayer() {
        return getAllPlayers().stream()
                .filter(p -> !p.getPlayerType().isLocal())
                .map(p -> (ServerPlayer) p)
                .findAny()
                .orElseThrow();
    }

    @Override
    public void handleServerResponse(Response response) {
        System.out.println("Received and ignoring an unhandled server response (" + response.getCommand() + ")");
    }
}
