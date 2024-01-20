package battleship;

import framework.Player;
import framework.PlayerFactory;
import framework.server.ServerGame;
import framework.server.ServerGameController;
import framework.server.ServerPlayer;
import server.Response;

public class BattleshipServerGame extends BattleshipGame implements ServerGame {

    private boolean hasEnded;
    private Player winner;
    private final ServerGameController controller;

    public BattleshipServerGame(ServerGameController controller) {
        super();
        this.controller = controller;
    }

    @Override
    public void forceWin(Player winner) {
        this.winner = winner;
        hasEnded = true;
    }

    @Override
    public void handleServerResponse(Response response) {

    }

    boolean localPlayerFirstMove = true;
    boolean serverPlayerFirstMove = true;
    @Override
    public boolean prePlayerMove(Player player) {
        if (player.getPlayerType().isLocal()) {
            if (localPlayerFirstMove) {
                BattleshipPlayer localPlayer = (BattleshipPlayer) player;
                for (Boat boat : localPlayer.getPlacedBoats()) {
                    int startIndex = controller.pointToIndex(boat.getStartCord());
                    int endIndex = controller.pointToIndex(boat.getEndCords());
                    controller.getServerController().sendMessage("place " + startIndex + " " + endIndex);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                localPlayerFirstMove = false;
                return false;
            }
        } else {
            if (serverPlayerFirstMove) {
                serverPlayerFirstMove = false;
                return false;
            }
        }
        return true;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public boolean hasEnded() {
        return hasEnded;
    }
}
