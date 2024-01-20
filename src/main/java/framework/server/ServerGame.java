package framework.server;

import framework.Game;
import framework.Player;
import framework.server.communication.Response;

public interface ServerGame extends Game {

    void forceWin(Player winner);

    default Player getLocalPlayer() {
        return getAllPlayers().stream()
                .filter(p -> p.getPlayerType().isLocal())
                .findAny()
                .orElseThrow();
    }

    default ServerPlayer getServerPlayer() {
        return getAllPlayers().stream()
                .filter(p -> !p.getPlayerType().isLocal())
                .map(p -> (ServerPlayer) p)
                .findAny()
                .orElseThrow();
    }

    void handleServerResponse(Response response);

    boolean prePlayerMove(Player player);
}
