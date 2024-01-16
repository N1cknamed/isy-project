package framework;

import server.Response;

public interface ServerGame extends Game {

    String getGameType();

    void forceWin(Player winner);

    Player getLocalPlayer();

    ServerPlayer getServerPlayer();

    void handleServerResponse(Response response);
}
