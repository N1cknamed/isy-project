package battleship;

import battleship.players.BattleshipCliPlayer;
import battleship.players.BattleshipPlayer;
import framework.PlayerFactory;

public class BattleshipPlayerFactory implements PlayerFactory {
    @Override
    public BattleshipPlayer build(char symbol) {
        return new BattleshipCliPlayer(symbol);
    }
}
