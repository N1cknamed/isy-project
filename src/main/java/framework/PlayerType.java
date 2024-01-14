package framework;

public enum PlayerType {

    CLI(true, true),
    GUI(true, true),
    AI(false, true),
    SERVER(false, false);

    private final boolean isHuman;
    private final boolean isLocal;

    PlayerType(boolean isHuman, boolean isLocal) {
        this.isHuman = isHuman;
        this.isLocal = isLocal;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public boolean isLocal() {
        return isLocal;
    }
}
