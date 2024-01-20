package framework;

public class PlayerType {

    private final String name;
    private final boolean isLocal;

    public PlayerType(String name, boolean isLocal) {
        this.name = name;
        this.isLocal = isLocal;
    }

    public String getName() {
        return name;
    }

    public boolean isLocal() {
        return isLocal;
    }
}
