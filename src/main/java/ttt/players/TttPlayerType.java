package ttt.players;

import framework.PlayerType;

public class TttPlayerType extends PlayerType {

    public static final TttPlayerType AI = new TttPlayerType("AI", true);
    public static final TttPlayerType CLI = new TttPlayerType("CLI", true);
    public static final TttPlayerType GUI = new TttPlayerType("GUI", true);
    public static final TttPlayerType SERVER = new TttPlayerType("SERVER", false);


    public TttPlayerType(String name, boolean isLocal) {
        super(name, isLocal);
    }
}
