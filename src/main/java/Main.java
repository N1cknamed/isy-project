import framework.Game;
import framework.GameController;
import ttt.TttGame;
import ttt.TttAiPlayer;
import ttt.TttCliSubscriber;
import ttt.TttPlayerFactory;


public class Main {
    public static void main(String[] args) {
        Game game = new TttGame();
        GameController controller = new GameController(game, TttAiPlayer::new, new TttPlayerFactory());
        controller.registerSubscriber(new TttCliSubscriber());
        controller.gameLoop();
    }
}
