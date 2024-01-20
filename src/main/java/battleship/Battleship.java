package battleship;

import battleship.players.BattleshipCliPlayer;
import framework.PlayerFactoryBuilder;

public class Battleship {

    private Battleship() {
    }

    public static PlayerFactoryBuilder getPlayerFactoryBuilder() {
        return new PlayerFactoryBuilder()
                .withCliPlayerConstructor(BattleshipCliPlayer::new);
    }
}
