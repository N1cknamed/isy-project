package ttt;

import Framework.Player;
import Framework.PlayerFactory;

public class TttPlayerFactory implements PlayerFactory {
    private char symbol;
    public TttPlayerFactory(char symbol) {
        this.symbol = symbol;
    }
    @Override
    public Player build() {
        return new TttCLIPlayer(symbol);
    }
}
