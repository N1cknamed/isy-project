package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{

    String host;
    int port;
    private Message message;
    private Object waitForOut;
    private PrintWriter outStream;

    public Client(Message message, Object waitForOut, String host, int port) {
        this.host = host;
        this.port = port;
        this.message = message;
        this.waitForOut = waitForOut;
    }

    @Override
    public void run() {
        System.out.println("Client started");
        startClient(host, port);
    }

    private void startClient(String host, int port) {
        try {
            Socket gameSocket = new Socket(host, port);
            PrintWriter out = new PrintWriter(gameSocket.getOutputStream(), true);
            synchronized (waitForOut) {
                try {
                    outStream = out;
                    waitForOut.notify();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(gameSocket.getInputStream()));

            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                message.push(serverMessage);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PrintWriter getOutStream() {
        return outStream;
    }
}
