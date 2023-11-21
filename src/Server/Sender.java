package Server;

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
        out.println(message);
    }
}
