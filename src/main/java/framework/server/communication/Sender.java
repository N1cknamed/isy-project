package framework.server.communication;

import java.io.PrintWriter;

public class Sender implements Runnable{

    private PrintWriter out;

    public Sender(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void run() {
        System.out.println("Sender started");
    }

    public void sendMessage(String message) {
        System.out.println("SENDING TO SERVER: " + message);
        out.println(message);
    }
}
