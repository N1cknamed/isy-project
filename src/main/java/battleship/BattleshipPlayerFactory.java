package battleship;

import Framework.Player;
import Framework.PlayerFactory;

public class BattleshipPlayerFactory implements PlayerFactory {
    @Override
    public BattleshipPlayer build(char symbol) {
        return new BattleshipCliPlayer(symbol);
    }
}
