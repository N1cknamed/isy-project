package ttt.players;

import framework.PlayerType;
import framework.server.ServerPlayer;

public class TttServerPlayer extends ServerPlayer {

    public TttServerPlayer(char symbol) {
        super(symbol);
    }

    @Override
    public PlayerType getPlayerType() {
        return TttPlayerType.SERVER;
    }
}
