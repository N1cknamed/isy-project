package framework.server;

import java.util.function.Consumer;

public class ServerChallenge {

    private final String challenger;
    private final String gameType;
    private final int challengeNumber;
    private final Consumer<ServerChallenge> acceptFunction;

    protected ServerChallenge(String challenger, String gameType, int challengeNumber, Consumer<ServerChallenge> acceptFunction) {
        this.challenger = challenger;
        this.gameType = gameType;
        this.challengeNumber = challengeNumber;
        this.acceptFunction = acceptFunction;
    }

    public String getChallenger() {
        return challenger;
    }

    public String getGameType() {
        return gameType;
    }

    public int getChallengeNumber() {
        return challengeNumber;
    }

    public void accept() {
        acceptFunction.accept(this);
    }
}
