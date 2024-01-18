package framework.server;

import framework.Game;
import framework.Player;
import server.Response;

public interface ServerGame extends Game {

    void forceWin(Player winner);

    Player getLocalPlayer();

    ServerPlayer getServerPlayer();

    void handleServerResponse(Response response);
}
