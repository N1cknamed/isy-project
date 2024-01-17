package framework;

import server.ServerController;

import java.util.function.BiFunction;
import java.util.function.Function;

public class PlayerFactoryBuilder {

    private Function<Character, Player> cliPlayerConstructor;
    private Function<Character, Player> guiPlayerConstructor;
    private Function<Character, Player> aiPlayerConstructor;
    private Function<Character, Player> serverPlayerConstructor;

    public PlayerFactoryBuilder withCliPlayerConstructor(Function<Character, Player> cliPlayerConstructor) {
        this.cliPlayerConstructor = cliPlayerConstructor;
        return this;
    }

    public PlayerFactoryBuilder withGuiPlayerConstructor(Function<Character, Player> guiPlayerConstructor) {
        this.guiPlayerConstructor = guiPlayerConstructor;
        return this;
    }

    public PlayerFactoryBuilder withAIPlayerConstructor(Function<Character, Player> aiPlayerConstructor) {
        this.aiPlayerConstructor = aiPlayerConstructor;
        return this;
    }

    public PlayerFactoryBuilder withServerPlayerConstructor(Function<Character, Player> serverPlayerConstructor) {
        this.serverPlayerConstructor = serverPlayerConstructor;
        return this;
    }

    public PlayerFactory build(PlayerType playerType) {
        switch (playerType) {
            case CLI:
                if (cliPlayerConstructor == null) throw new IllegalArgumentException("No constructor defined for PlayerType CLI.");
                return cliPlayerConstructor::apply;
            case GUI:
                if (guiPlayerConstructor == null) throw new IllegalArgumentException("No constructor defined for PlayerType GUI.");
                return guiPlayerConstructor::apply;
            case AI:
                if (aiPlayerConstructor == null) throw new IllegalArgumentException("No constructor defined for PlayerType AI.");
                return aiPlayerConstructor::apply;
            case SERVER:
                if (serverPlayerConstructor == null) throw new IllegalArgumentException("No constructor defined for PlayerType SERVER.");
                return serverPlayerConstructor::apply;
            default:
                throw new IllegalArgumentException("Invalid PlayerType.");
        }
    }
}
