package ttt;

import framework.Player;
import framework.PlayerFactory;
import ttt.players.TttCliPlayer;

public class TttPlayerFactory implements PlayerFactory {
    @Override
    public Player build(char symbol) {
        return new TttCliPlayer(symbol);
    }
}
