package framework.server;

public interface ServerChallengeHandler {

    void handleChallengeReceived(ServerChallenge challenge);
}
