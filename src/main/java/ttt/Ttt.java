package ttt;

import framework.PlayerFactoryBuilder;
import framework.ServerPlayer;
import ttt.players.TttAiPlayer;
import ttt.players.TttCliPlayer;
import ttt.players.TttGuiPlayer;

public class Ttt {

    private Ttt() {
    }

    public static PlayerFactoryBuilder getPlayerFactoryBuilder() {
        return new PlayerFactoryBuilder()
                .withCliPlayerConstructor(TttCliPlayer::new)
                .withGuiPlayerConstructor(TttGuiPlayer::new)
                .withAIPlayerConstructor(TttAiPlayer::new)
                .withServerPlayerConstructor(ServerPlayer::new);
    }
}
