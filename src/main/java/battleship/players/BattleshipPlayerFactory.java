package battleship.players;

import framework.PlayerFactory;

import static battleship.players.BattleshipPlayerType.*;

public class BattleshipPlayerFactory implements PlayerFactory {

    private final BattleshipPlayerType type;

    public BattleshipPlayerFactory(BattleshipPlayerType type) {
        this.type = type;
    }

    @Override
    public BattleshipPlayer build(char symbol) {
        if (type.isAi()) {
            return new BattleshipAIPlayer(symbol, type);
        } else if (type == CLI) {
            return new BattleshipCliPlayer(symbol);
        } else if (type == GUI) {
            throw new UnsupportedOperationException("Not implemented yet.");
        } else if (type == SERVER) {
            return new BattleshipServerPlayer(symbol);
        } else {
            throw new IllegalArgumentException("Invalid player type '" + type + "'.");
        }
    }

}
