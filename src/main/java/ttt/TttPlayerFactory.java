package ttt;

import Framework.Player;
import Framework.PlayerFactory;

public class TttPlayerFactory implements PlayerFactory {
    @Override
    public Player build(char symbol) {
        return new TttCLIPlayer(symbol);
    }
}
