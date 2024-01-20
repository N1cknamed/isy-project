package ttt.players;

import framework.Player;
import framework.PlayerFactory;
import framework.PlayerType;

import static ttt.players.TttPlayerType.*;

public class TttPlayerFactory implements PlayerFactory {

    private final PlayerType type;

    public TttPlayerFactory(PlayerType type) {
        this.type = type;
    }

    @Override
    public Player build(char symbol) {
        if (type == AI) {
            return new TttAiPlayer(symbol);
        } else if (type == CLI) {
            return new TttCliPlayer(symbol);
        } else if (type == GUI) {
            return new TttGuiPlayer(symbol);
        } else if (type == SERVER) {
            return new TttServerPlayer(symbol);
        } else {
            throw new IllegalArgumentException("Invalid player type '" + type + "'.");
        }
    }
}
