package framework.server.communication;

public class ServerController {

    private final Message message;
    private final Object waitForOut;
    private final Client client;
    private final Sender sender;

    public ServerController(String host, int port) {
        this.message = new Message();
        this.waitForOut = new Object();
        this.client = startClient(host, port);
        this.sender = startSender();
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    public Client startClient(String host, int port) {
        Client c = new Client(message, waitForOut, host, port);
        Thread cThread = new Thread(c);
        cThread.start();
        return c;
    }

    public Sender startSender() {
        synchronized (waitForOut) {
            try {
                waitForOut.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Sender s = new Sender(client.getOutStream());
        Thread sThread = new Thread(s);
        sThread.start();
        return s;
    }

    public Message getMessage() {
        return message;
    }
}
