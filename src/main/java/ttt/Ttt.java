package ttt;

import framework.PlayerFactoryBuilder;

public class Ttt {

    private Ttt() {
    }

    public static PlayerFactoryBuilder getPlayerFactoryBuilder() {
        return new PlayerFactoryBuilder()
                .withCliPlayerConstructor(TttCliPlayer::new)
                .withGuiPlayerConstructor(TttGuiPlayer::new)
                .withAIPlayerConstructor(TttAiPlayer::new);
    }
}
