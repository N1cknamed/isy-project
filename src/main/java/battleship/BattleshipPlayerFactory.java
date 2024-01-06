package battleship;

import framework.PlayerFactory;

public class BattleshipPlayerFactory implements PlayerFactory {
    @Override
    public BattleshipPlayer build(char symbol) {
        return new BattleshipCliPlayer(symbol);
    }
}
